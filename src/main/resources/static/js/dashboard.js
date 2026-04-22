// ===== Midaxus dashboard.js =====
// Roles, navigation y drag-and-drop en un solo archivo.
// Requiere: auth.js ya cargado (provee getSession / clearSession)

// ─── Definición de roles ──────────────────────────────────────────────────────
const ROLES = {
  ADMIN: {
    badge: "ADMIN",
    menu: [
      { id:"dashboard",   icon:"fas fa-tachometer-alt",  label:"Dashboard"   },
      { id:"schedule",    icon:"fas fa-calendar-check",  label:"Schedule"    },
      { id:"courses",     icon:"fas fa-book",            label:"Courses"     },
      { id:"teachers",    icon:"fas fa-chalkboard-user", label:"Faculty"     },
      { id:"publication", icon:"fas fa-paper-plane",     label:"Publish"     },
      { id:"settings",    icon:"fas fa-cog",             label:"Settings"    }
    ],
    kpis: [
      { label:"Active Courses",    value:"42",  icon:"fas fa-book"      },
      { label:"Scheduled Classes", value:"128", icon:"fas fa-calendar"  },
      { label:"Faculty Members",   value:"65",  icon:"fas fa-users"     },
      { label:"Classrooms",        value:"28",  icon:"fas fa-door-open" }
    ],
    quickActions: [
      { label:"Generate Schedule", icon:"fas fa-magic",        fn: ()=>toast("Schedule generation started…","info")    },
      { label:"Validate All",      icon:"fas fa-check-double", fn: ()=>toast("All schedules validated ✓","success")    },
      { label:"Export PDF",        icon:"fas fa-file-pdf",     fn: ()=>toast("Exporting PDF…","info")                  }
    ],
    showConflictAlert: true,
    scheduleEditable: true
  },

  TEACHER: {
    badge: "TEACHER",
    menu: [
      { id:"dashboard",    icon:"fas fa-tachometer-alt", label:"Dashboard"     },
      { id:"schedule",     icon:"fas fa-calendar-check", label:"Full Schedule" },
      { id:"my-schedule",  icon:"fas fa-user-clock",     label:"My Schedule"   },
      { id:"availability", icon:"fas fa-calendar-times", label:"Availability"  }
    ],
    kpis: [
      { label:"My Courses",   value:"2",   icon:"fas fa-book"         },
      { label:"Weekly Hours", value:"12",  icon:"fas fa-clock"        },
      { label:"My Groups",    value:"2",   icon:"fas fa-users"        },
      { label:"Next Class",   value:"Mon", icon:"fas fa-calendar-day" }
    ],
    quickActions: [
      { label:"My Schedule",     icon:"fas fa-calendar", fn: ()=>navigateTo("my-schedule")  },
      { label:"Set Availability", icon:"fas fa-clock",   fn: ()=>navigateTo("availability") }
    ],
    showConflictAlert: false,
    scheduleEditable: false
  },

  STUDENT: {
    badge: "STUDENT",
    menu: [
      { id:"dashboard", icon:"fas fa-tachometer-alt", label:"Dashboard" },
      { id:"schedule",  icon:"fas fa-calendar-check", label:"Schedule"  },
      { id:"grades",    icon:"fas fa-star-half-alt",  label:"My Grades" }
    ],
    kpis: [
      { label:"Enrolled",     value:"3",   icon:"fas fa-book"           },
      { label:"Classes/Week", value:"8",   icon:"fas fa-calendar"       },
      { label:"Credits",      value:"12",  icon:"fas fa-graduation-cap" },
      { label:"GPA",          value:"3.9", icon:"fas fa-chart-line"     }
    ],
    quickActions: [
      { label:"My Grades", icon:"fas fa-star", fn: ()=>navigateTo("grades") }
    ],
    showConflictAlert: false,
    scheduleEditable: false,
    showStudentCourses: true
  }
};

// ─── Datos del horario ────────────────────────────────────────────────────────
const TIME_SLOTS = ["08:00-09:30","09:45-11:15","12:00-13:30","14:00-15:30","16:00-17:30"];
const DAYS       = ["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];

let scheduleData = {
  "08:00-09:30":  { Monday:{code:"MAT101",group:"G01",teacher:"Dr. Johnson",room:"301"}, Wednesday:{code:"PHY201",group:"G02",teacher:"Dr. Smith",room:"205"} },
  "09:45-11:15":  { Tuesday:{code:"MAT101",group:"G02",teacher:"Dr. Johnson",room:"301"}, Thursday:{code:"CHEM100",group:"G01",teacher:"Dr. Lewis",room:"114"} },
  "12:00-13:30":  { Friday:{code:"BIO150",group:"G01",teacher:"Dr. Brown",room:"108"} },
  "14:00-15:30":  { Monday:{code:"ENG102",group:"G03",teacher:"Prof. Davis",room:"202"}, Wednesday:{code:"HIST120",group:"G01",teacher:"Dr. Wilson",room:"310"} },
  "16:00-17:30":  {}
};

let pool = [
  { code:"MATH201", group:"G01", teacher:"Dr. Park",    room:"—" },
  { code:"CS101",   group:"G02", teacher:"Prof. White", room:"—" }
];

let dragSrc    = null;
let selectedEl = null;

function decodeJWT(token) {
  try {
    return JSON.parse(atob(token.split('.')[1]));
  } catch(e) {
    return null;
  }
}

const stored = localStorage.getItem("user") || sessionStorage.getItem("user");
let rawAuth = stored ? JSON.parse(stored) : null;
let session = null;

if (rawAuth && rawAuth.token) {
  const payload = decodeJWT(rawAuth.token);
  if (payload) {
    session = {
      role: payload.role.replace("ROLE_", "").toUpperCase(),
      email: payload.email,
      name: payload.name,
      initials: payload.name ? payload.name[0].toUpperCase() : "U"
    };
  }
}

if (!session) {
  console.error("Sesión inválida o expirada");
  window.location.href = "/login";
  throw new Error("Abort");
}

const cfg = ROLES[session.role];
if (!cfg) {
  console.error("Rol no reconocido:", session.role);
  window.location.href = "/login";
}
  // Navbar
  document.getElementById("user-initials").textContent = session.initials || session.role[0];
  document.getElementById("user-name").textContent     = session.name || session.email;
  const rb = document.getElementById("role-badge");
  rb.textContent = cfg.badge;
  rb.className   = "role-badge " + cfg.badge;

  document.getElementById("btn-logout").addEventListener("click", () => {
    if (typeof clearSession === "function") clearSession();
    else sessionStorage.clear();
    window.location.href = "/login";
  });

  buildSidebar(cfg.menu);
  buildKPIs(cfg.kpis);
  buildQuickActions(cfg.quickActions);

  const ca = document.getElementById("conflict-alert");
  if (ca) ca.style.display = cfg.showConflictAlert ? "flex" : "none";

  const sc = document.getElementById("student-courses");
  if (sc) sc.style.display = cfg.showStudentCourses ? "block" : "none";

  buildAvailGrid();
  navigateTo("dashboard");

// Sesión de demo sin auth.js (cambia el rol para probar)
function demoSession() {
 // return { role:"STUDENT", name:"Demo Student", initials:"DS", email:"demo@midaxus.com" };
 return { role:"TEACHER", name:"Demo Teacher", initials:"DT", email:"demo@midaxus.com" };
  //return { role:"ADMIN", name:"Demo Admin", initials:"DA", email:"demo@midaxus.com" };
}


// ─── Navegación ───────────────────────────────────────────────────────────────
function navigateTo(id) {
  document.querySelectorAll(".screen").forEach(s => { s.classList.remove("active"); s.style.display="none"; });
  const tgt = document.getElementById("screen-" + id);
  if (tgt) { tgt.classList.add("active"); tgt.style.display="block"; }

  document.querySelectorAll("#sidebar-menu li").forEach(li => li.classList.toggle("active", li.dataset.id===id));

  if (id === "schedule") {
    const sessionObj = (typeof getSession==="function" ? getSession() : null) || demoSession();
    const editable = ROLES[sessionObj.role]?.scheduleEditable || false;
    buildScheduleUI(editable);
    buildScheduleGrid(editable);
  }
  
  if (id === "courses" && session.role === "ADMIN") {
    loadAdminCourses();
  }
  
  if (id === "dashboard" && session.role === "STUDENT") {
    loadStudentCourses();
  }
  
  window.scrollTo(0,0);
}

// ─── Student Courses (HU-13) ──────────────────────────────────────────────────
async function loadStudentCourses() {
  try {
    const [coursesRes, teachersRes] = await Promise.all([
      fetch('/api/course-groups', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } }),
      fetch('/api/teachers', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } })
    ]);
    
    let courses = [];
    let teachers = [];
    if (coursesRes.ok) courses = await coursesRes.json();
    if (teachersRes.ok) teachers = await teachersRes.json();
    
    const tbody = document.getElementById("student-courses-table-body");
    if (!tbody) return;
    tbody.innerHTML = "";
    
    if (courses.length === 0) {
      tbody.innerHTML = `<tr><td colspan="5" style="text-align:center;">No hay sesiones disponibles.</td></tr>`;
      return;
    }
    
    courses.forEach(cg => {
      const teacher = teachers.find(t => t.teacherId === cg.teacherId || t.teacherCode === cg.teacherId);
      const teacherName = teacher ? teacher.name : (cg.teacherId || "Sin asignar");
      const cuposDisp = cg.capacity > 0 ? cg.capacity : 0;
      const status = cuposDisp > 0 ? '<span class="badge green">Disponible</span>' : '<span class="badge red">Lleno</span>';
      
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${cg.code || "N/A"}</td>
        <td>${cg.subjectId || "Materia"}</td>
        <td>${teacherName}</td>
        <td>${cuposDisp}</td>
        <td>${status}</td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error("Error loading student courses", err);
  }
}

// ─── Admin Courses (HU-13) ───────────────────────────────────────────────────
let adminCoursesData = [];
let allTeachersData = [];

async function loadAdminCourses() {
  try {
    const [coursesRes, teachersRes] = await Promise.all([
      fetch('/api/course-groups', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } }),
      fetch('/api/teachers', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } })
    ]);
    
    if (coursesRes.ok) adminCoursesData = await coursesRes.json();
    if (teachersRes.ok) allTeachersData = await teachersRes.json();
    
    renderAdminCourses();
    populateTeacherSelect();
  } catch (err) {
    console.error("Error loading courses or teachers", err);
    toast("Error cargando sesiones", "error");
  }
}

function renderAdminCourses() {
  const tbody = document.getElementById("admin-courses-table-body");
  if (!tbody) return;
  tbody.innerHTML = "";
  
  if (adminCoursesData.length === 0) {
    tbody.innerHTML = `<tr><td colspan="5" style="text-align:center;">No hay sesiones registradas.</td></tr>`;
    return;
  }
  
  adminCoursesData.forEach(cg => {
    // Buscar nombre del profesor para mostrar
    const teacher = allTeachersData.find(t => t.teacherId === cg.teacherId || t.teacherCode === cg.teacherId);
    const teacherName = teacher ? teacher.name : (cg.teacherId || "Sin asignar");
    
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${cg.code || "N/A"}</td>
      <td>${cg.subjectId || "Materia"}</td>
      <td>${teacherName}</td>
      <td>${cg.capacity || 0}</td>
      <td>
        <button class="btn-outline" onclick="openEditSessionModal('${cg.courseGroupId}')"><i class="fas fa-edit"></i> Asignar</button>
      </td>
    `;
    tbody.appendChild(tr);
  });
}

function populateTeacherSelect() {
  const select = document.getElementById("edit-session-teacher");
  if (!select) return;
  
  // Limpiar y dejar el default
  select.innerHTML = '<option value="">Seleccione un profesor...</option>';
  
  allTeachersData.forEach(t => {
    const opt = document.createElement("option");
    opt.value = t.teacherId || t.teacherCode;
    opt.textContent = t.name;
    select.appendChild(opt);
  });
}

function openEditSessionModal(courseGroupId) {
  const cg = adminCoursesData.find(c => c.courseGroupId === courseGroupId);
  if (!cg) return;
  
  document.getElementById("edit-session-id").value = cg.courseGroupId;
  document.getElementById("edit-session-teacher").value = cg.teacherId || "";
  document.getElementById("edit-session-capacity").value = cg.capacity || 30;
  
  document.getElementById("modal-edit-session").style.display = "flex";
}

function closeEditSessionModal() {
  document.getElementById("modal-edit-session").style.display = "none";
}

async function saveSessionAssignment() {
  const id = document.getElementById("edit-session-id").value;
  const teacherId = document.getElementById("edit-session-teacher").value;
  const capacity = document.getElementById("edit-session-capacity").value;
  
  if (!teacherId) {
    toast("Debe seleccionar un profesor", "error");
    return;
  }
  
  const cg = adminCoursesData.find(c => c.courseGroupId === id);
  if (!cg) return;
  
  // Actualizar el DTO
  cg.teacherId = teacherId;
  cg.capacity = parseInt(capacity, 10);
  
  try {
    const res = await fetch('/api/course-groups/' + id, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + rawAuth?.token
      },
      body: JSON.stringify(cg)
    });
    
    if (res.ok) {
      toast("Sesión asignada correctamente", "success");
      closeEditSessionModal();
      loadAdminCourses(); // Refrescar la tabla
    } else {
      toast("Error al guardar la sesión", "error");
    }
  } catch(err) {
    console.error(err);
    toast("Error de red", "error");
  }
}

window.navigateTo = navigateTo;

// ─── Sidebar ──────────────────────────────────────────────────────────────────
function buildSidebar(menu) {
  const ul = document.getElementById("sidebar-menu");
  ul.innerHTML = "";
  menu.forEach(item => {
    const li = document.createElement("li");
    li.dataset.id = item.id;
    li.innerHTML  = `<i class="${item.icon}"></i><span>${item.label}</span>`;
    li.addEventListener("click", () => navigateTo(item.id));
    ul.appendChild(li);
  });
}

// ─── KPIs ─────────────────────────────────────────────────────────────────────
function buildKPIs(kpis) {
  const g = document.getElementById("kpi-grid");
  if (!g) return;
  const cols = kpis.length <= 2 ? 2 : kpis.length === 3 ? 3 : 4;
  g.style.gridTemplateColumns = `repeat(${cols},1fr)`;
  g.innerHTML = kpis.map(k=>`
    <div class="kpi-card">
      <span class="label"><i class="${k.icon}" style="margin-right:.3rem;color:var(--teal);"></i>${k.label}</span>
      <div class="value">${k.value}</div>
    </div>`).join("");
}

// ─── Quick Actions ────────────────────────────────────────────────────────────
function buildQuickActions(actions) {
  const box = document.getElementById("quick-actions-box");
  if (!box) return;
  box.innerHTML = `<h3><i class="fas fa-bolt"></i> Quick Actions</h3>`;
  actions.forEach(a => {
    const btn = document.createElement("button");
    btn.className = "btn-primary";
    btn.innerHTML = `<i class="${a.icon}"></i> ${a.label}`;
    btn.addEventListener("click", a.fn);
    box.appendChild(btn);
  });
}

// ─── Availability grid ────────────────────────────────────────────────────────
function buildAvailGrid() {
  const g = document.getElementById("avail-grid");
  if (!g) return;
  const days  = ["Mon","Tue","Wed","Thu","Fri","Sat"];
  const times = ["08:00","09:45","12:00","14:00","16:00"];
  let html = `<div class="avail-time"></div>`;
  days.forEach(d  => html += `<div class="avail-header">${d}</div>`);
  times.forEach(t => {
    html += `<div class="avail-time">${t}</div>`;
    days.forEach(() => html += `<div class="avail-cell" onclick="this.classList.toggle('on')"></div>`);
  });
  g.innerHTML = html;
}

// ─── Schedule UI (toolbar + pool) ─────────────────────────────────────────────
function buildScheduleUI(editable) {
  const sub = document.getElementById("schedule-subtitle");
  if (sub) sub.textContent = editable
      ? "Drag and drop to reschedule · Spring 2026"
      : "View-only · Spring 2026";

  const tb = document.getElementById("schedule-toolbar");
  if (tb) {
    tb.innerHTML = editable
        ? `<button class="btn-sm" onclick="toast('Ready to add class','info')"><i class="fas fa-plus"></i> Add Class</button>
         <button class="btn-sm" onclick="toast('All validated ✓','success')"><i class="fas fa-check-double"></i> Validate</button>
         <button class="btn-sm" onclick="toast('Exporting…','info')"><i class="fas fa-download"></i> Export</button>
         <span style="margin-left:auto;font-size:.78rem;color:var(--muted);">
           <i class="fas fa-hand-paper"></i> Drag cards · drop to pool to unschedule</span>`
        : `<span style="font-size:.82rem;color:var(--muted);">
           <i class="fas fa-eye"></i> View-only – contact admin to request changes</span>`;
  }

  const pz = document.getElementById("pool-zone");
  if (pz) pz.style.display = editable ? "block" : "none";
  if (editable) buildPool();
}

// ─── Schedule Grid ────────────────────────────────────────────────────────────
function buildScheduleGrid(editable) {
  const body = document.getElementById("grid-body");
  if (!body) return;
  body.innerHTML = "";

  TIME_SLOTS.forEach(slot => {
    const row = document.createElement("div");
    row.className = "grid-row";

    const tc = document.createElement("div");
    tc.className = "time-cell";
    tc.textContent = slot;
    row.appendChild(tc);

    DAYS.forEach(day => {
      const cell = document.createElement("div");
      cell.className = "drop-cell";
      cell.dataset.slot = slot;
      cell.dataset.day  = day;

      if (editable) {
        cell.addEventListener("dragover",  cellDragOver);
        cell.addEventListener("dragleave", cellDragLeave);
        cell.addEventListener("drop",      cellDrop);
      }

      const s = scheduleData[slot]?.[day];
      if (s) cell.appendChild(makeCard(s, slot, day, editable));
      row.appendChild(cell);
    });

    body.appendChild(row);
  });
}

function makeCard(s, slot, day, editable) {
  const c = document.createElement("div");
  c.className   = "session-card" + (editable ? "" : " readonly");
  c.dataset.slot = slot;
  c.dataset.day  = day;
  c.innerHTML    = `<strong>${s.code}</strong> ${s.group}
    <span class="sub">${s.teacher}</span>
    <span class="room"><i class="fas fa-map-marker-alt" style="font-size:.6rem;"></i> ${s.room}</span>`;

  if (editable) {
    c.setAttribute("draggable","true");
    c.addEventListener("dragstart", e => {
      dragSrc = { from:"cell", slot, day };
      c.classList.add("dragging");
      e.dataTransfer.effectAllowed = "move";
    });
    c.addEventListener("dragend", () => c.classList.remove("dragging"));
  }
  c.addEventListener("click", e => { e.stopPropagation(); selectCard(c, s, editable); });
  return c;
}

// ─── Pool ─────────────────────────────────────────────────────────────────────
function buildPool() {
  const el = document.getElementById("pool-slots");
  const ct = document.getElementById("pool-count");
  if (!el) return;
  el.innerHTML = "";
  if (ct) ct.textContent = pool.length;

  pool.forEach((item, idx) => {
    const c = document.createElement("div");
    c.className = "pool-card";
    c.setAttribute("draggable","true");
    c.innerHTML = `<i class="fas fa-grip-vertical" style="color:#ccc;margin-right:.3rem;font-size:.7rem;"></i>${item.code} ${item.group}`;
    c.addEventListener("dragstart", e => {
      dragSrc = { from:"pool", idx };
      c.classList.add("dragging");
      e.dataTransfer.effectAllowed = "move";
    });
    c.addEventListener("dragend", () => c.classList.remove("dragging"));
    el.appendChild(c);
  });
}

// Pool drop target — declarado en HTML con ondrop="onPoolDrop(event)"
function onPoolDrop(e) {
  e.preventDefault();
  document.getElementById("pool-zone")?.classList.remove("drag-over");
  if (dragSrc?.from !== "cell") return;

  const s = scheduleData[dragSrc.slot]?.[dragSrc.day];
  if (!s) return;
  pool.push(s);
  delete scheduleData[dragSrc.slot][dragSrc.day];
  dragSrc = null;
  toast(`${s.code} moved to unscheduled pool`, "info");
  buildScheduleGrid(true);
  buildPool();
  hideInspector();
}
window.onPoolDrop = onPoolDrop;

// ─── Cell drag handlers ───────────────────────────────────────────────────────
function cellDragOver(e) {
  e.preventDefault(); e.dataTransfer.dropEffect = "move";
  const cell = e.currentTarget;
  const { slot, day } = cell.dataset;
  const occupied = scheduleData[slot]?.[day];
  const same = dragSrc?.from==="cell" && dragSrc.slot===slot && dragSrc.day===day;
  cell.classList.remove("over","clash");
  if (same) return;
  (occupied && dragSrc?.from==="pool") ? cell.classList.add("clash") : cell.classList.add("over");
}
function cellDragLeave(e) { e.currentTarget.classList.remove("over","clash"); }

function cellDrop(e) {
  e.preventDefault();
  const cell = e.currentTarget;
  cell.classList.remove("over","clash");
  const { slot:toSlot, day:toDay } = cell.dataset;

  if (dragSrc?.from === "cell") {
    const { slot:fromSlot, day:fromDay } = dragSrc;
    if (fromSlot===toSlot && fromDay===toDay) { dragSrc=null; return; }
    const s = scheduleData[fromSlot]?.[fromDay];
    if (!s) { dragSrc=null; return; }
    const existing = scheduleData[toSlot]?.[toDay];
    if (!scheduleData[toSlot]) scheduleData[toSlot] = {};
    scheduleData[toSlot][toDay] = s;
    if (existing) scheduleData[fromSlot][fromDay] = existing;
    else          delete scheduleData[fromSlot][fromDay];
    toast(`${s.code} → ${toDay} ${toSlot}`, "success");

  } else if (dragSrc?.from === "pool") {
    const item = pool[dragSrc.idx];
    if (!item) { dragSrc=null; return; }
    if (scheduleData[toSlot]?.[toDay]) { toast("That cell is occupied","error"); dragSrc=null; return; }
    if (!scheduleData[toSlot]) scheduleData[toSlot] = {};
    scheduleData[toSlot][toDay] = { ...item };
    pool.splice(dragSrc.idx, 1);
    toast(`${item.code} scheduled on ${toDay} ${toSlot}`, "success");
  }

  dragSrc = null;
  buildScheduleGrid(true);
  buildPool();
  hideInspector();
}

// ─── Inspector ────────────────────────────────────────────────────────────────
function selectCard(el, s, editable) {
  if (selectedEl) selectedEl.classList.remove("selected");
  selectedEl = el;
  el.classList.add("selected");

  const panel = document.getElementById("inspector");
  const info  = document.getElementById("inspector-info");
  const acts  = document.getElementById("inspector-actions");
  if (!panel) return;
  panel.style.display = "block";
  info.innerHTML = `<i class="fas fa-info-circle" style="color:var(--teal);margin-right:.4rem;"></i>
    <strong>${s.code}</strong> · ${s.teacher} · Room <strong>${s.room}</strong> · ${el.dataset.day} ${el.dataset.slot}`;
  acts.innerHTML = editable
      ? `<button class="btn-outline" onclick="alert('Edit coming soon')">Edit</button>
       <button class="btn-outline danger" onclick="removeCard('${el.dataset.slot}','${el.dataset.day}')">Remove</button>`
      : "";
}

function hideInspector() {
  if (selectedEl) { selectedEl.classList.remove("selected"); selectedEl = null; }
  const p = document.getElementById("inspector");
  if (p) p.style.display = "none";
}

function removeCard(slot, day) {
  const s = scheduleData[slot]?.[day];
  if (!s || !confirm(`Remove ${s.code} from ${day} ${slot}?`)) return;
  pool.push(s);
  delete scheduleData[slot][day];
  hideInspector();
  toast(`${s.code} removed`, "info");
  buildScheduleGrid(true);
  buildPool();
}
window.removeCard = removeCard;

document.addEventListener("click", e => {
  if (!e.target.closest(".session-card") && !e.target.closest(".inspector")) hideInspector();
});

// ─── Toast ────────────────────────────────────────────────────────────────────
function toast(msg, type="info") {
  const root = document.getElementById("toast-root");
  if (!root) return;
  const t = document.createElement("div");
  t.className = "toast " + type;
  const icons = { success:"fa-check-circle", error:"fa-exclamation-circle", info:"fa-info-circle" };
  t.innerHTML = `<i class="fas ${icons[type]||icons.info}"></i> ${msg}`;
  root.appendChild(t);
  setTimeout(() => { t.style.transition=".3s"; t.style.opacity="0"; t.style.transform="translateX(28px)"; }, 3000);
  setTimeout(() => t.remove(), 3400);
}
window.toast = toast;
