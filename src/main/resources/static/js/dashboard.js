// ===== Midaxus dashboard.js =====
// Roles, navigation y drag-and-drop en un solo archivo.
// Requiere: auth.js ya cargado (provee getSession / clearSession)

// ─── Definición de roles ──────────────────────────────────────────────────────
const ROLES = {
  ADMIN: {
    badge: "ADMIN",
    roleTitle: "Administrador General",
    icon: "🛡️",
    theme: "from-red-600 to-red-700",
    menu: [
      { id:"dashboard",   icon:"fas fa-tachometer-alt",  label:"Dashboard"   },
      { id:"schedule",    icon:"fas fa-calendar-check",  label:"Schedule"    },
      { id:"courses",     icon:"fas fa-book",            label:"Gestión de Salones y Clases", desc:"Admin de Salones y Clases", emoji:"🏫" },
      { id:"teachers",    icon:"fas fa-chalkboard-user", label:"Gestión de Usuarios", desc:"Administrar estudiantes y profesores", emoji:"👥" },
      { id:"publication", icon:"fas fa-paper-plane",     label:"Publish"     },
      { id:"settings",    icon:"fas fa-cog",             label:"Settings"    }
    ],
    kpis: [
      { label:"Estudiantes Activos", value:"1,245", icon:"🎓", bg:"bg-blue-100" },
      { label:"Profesores",          value:"87",    icon:"👨‍🏫", bg:"bg-indigo-100" },
      { label:"Materias",            value:"156",   icon:"📄", bg:"bg-green-100" },
      { label:"Aulas",               value:"42",    icon:"🏫", bg:"bg-orange-100" }
    ],
    quickActions: [
      { label:"Gestión de Usuarios", desc:"Administrar estudiantes y profesores", emoji:"👥", fn: ()=>navigateTo("teachers") },
      { label:"Gestión de Materias", desc:"Catálogo de materias", emoji:"📚", fn: ()=>navigateTo("subjects") },
      { label:"Gestión de Sesiones", desc:"Admin de Salones y Clases", emoji:"🏫", fn: ()=>navigateTo("courses") },
      { label:"Diseñador de Horarios", desc:"Generar o editar horario manual", emoji:"✨", fn: ()=>navigateTo("schedule") },
      { label:"Configuración", desc:"Límites de Jornada y Almuerzo", emoji:"⚙️", fn: ()=>navigateTo("settings") }
    ],
    showConflictAlert: true,
    scheduleEditable: true
  },

  TEACHER: {
    badge: "TEACHER",
    roleTitle: "Profesor",
    icon: "👨‍🏫",
    theme: "from-indigo-600 to-indigo-700",
    menu: [
      { id:"dashboard",    icon:"fas fa-tachometer-alt", label:"Dashboard"     },
      { id:"schedule",     icon:"fas fa-calendar-check", label:"Full Schedule" },
      { id:"my-schedule",  icon:"fas fa-user-clock",     label:"My Schedule"   },
      { id:"availability", icon:"fas fa-calendar-times", label:"Availability"  }
    ],
    kpis: [
      { label:"My Courses",   value:"2",   icon:"fas fa-book"         },
      { label:"Weekly Hours", value:"12",  icon:"fas fa-clock"        },
      { label:"My Groups",    value:"2",   icon:"fas fa-users"        }
    ],
    quickActions: [
      { label:"Registrar Asistencia", desc:"Gestionar asistencias", emoji:"✏️", fn: ()=>toast("Módulo en construcción", "info") },
      { label:"Calificar", desc:"Ingresar notas", emoji:"📊", fn: ()=>navigateTo("grades") },
      { label:"Material Didáctico", desc:"Subir recursos", emoji:"📚", fn: ()=>toast("Módulo en construcción", "info") },
      { label:"Configuración", desc:"Ajustes de perfil", emoji:"⚙️", fn: ()=>toast("Módulo en construcción", "info") }
    ],
    showConflictAlert: false,
    scheduleEditable: false
  },

  STUDENT: {
    badge: "STUDENT",
    roleTitle: "Estudiante",
    icon: "🎓",
    theme: "from-blue-600 to-blue-700",
    menu: [
      { id:"dashboard", icon:"fas fa-tachometer-alt", label:"Dashboard" },
      { id:"schedule",  icon:"fas fa-calendar-check", label:"Schedule"  },
      { id:"grades",    icon:"fas fa-star-half-alt",  label:"My Grades" }
    ],
    kpis: [],
    quickActions: [
      { label:"Matricular Cursos", desc:"Inscribir materias", emoji:"➕", fn: ()=>openEnrollModal() },
      { label:"Ver Calificaciones", desc:"Consulta tus notas", emoji:"📄", fn: ()=>navigateTo("grades") },
      { label:"Calendario Académico", desc:"Fechas importantes", emoji:"📅", fn: ()=>navigateTo("schedule") },
      { label:"Notificaciones", desc:"Avisos y mensajes", emoji:"🔔", fn: ()=>toast("No tienes notificaciones", "info") }
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
      id: payload.id || payload.userId || payload.sub || rawAuth.id || payload.email,
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
  // Llenar el perfil en el Banner y Header
  document.getElementById("user-name").textContent = session.name || session.email;
  
  const roleSubtitle = document.getElementById("role-subtitle");
  if(roleSubtitle) roleSubtitle.textContent = cfg.roleTitle || cfg.badge;
  
  const profileIcon = document.getElementById("profile-icon");
  if(profileIcon) profileIcon.textContent = cfg.icon || "👤";
  
  const banner = document.getElementById("profile-banner");
  if(banner) banner.className = `text-white rounded-lg shadow-md p-6 mb-6 bg-gradient-to-r ${cfg.theme || 'from-gray-600 to-gray-700'}`;

  document.getElementById("btn-logout").addEventListener("click", () => {
    if (typeof clearSession === "function") clearSession();
    else sessionStorage.clear();
    window.location.href = "/login";
  });

  buildSidebar(cfg.menu); // Mantenido para compatibilidad o sub-navegación si aplica
  buildKPIs(cfg.kpis);
  buildQuickActions(cfg.quickActions);

  const sc = document.getElementById("student-courses");
  if (sc) sc.style.display = session.role === "STUDENT" ? "block" : "none";
  
  const tc = document.getElementById("teacher-courses");
  if (tc) tc.style.display = session.role === "TEACHER" ? "block" : "none";

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
  
  if (id === "subjects" && session.role === "ADMIN") {
    loadAdminSubjects();
  }
  
  if (id === "settings" && session.role === "ADMIN") {
    loadInstitutionPolicies();
  }
  
  if (id === "dashboard" && session.role === "STUDENT") {
    loadStudentCourses();
  }
  
  if (id === "dashboard" && session.role === "TEACHER") {
    loadTeacherCourses();
  }
  
  window.scrollTo(0,0);
}

// ─── Institution Policies (HU-5) ──────────────────────────────────────────────
async function loadInstitutionPolicies() {
  try {
    const res = await fetch('/api/policies', {
      headers: { 'Authorization': 'Bearer ' + rawAuth?.token }
    });
    if (res.ok) {
      const data = await res.json();
      if(data.classStartTime) document.getElementById('policy-class-start').value = data.classStartTime.substring(0,5);
      if(data.classEndTime) document.getElementById('policy-class-end').value = data.classEndTime.substring(0,5);
      if(data.lunchStartTime) document.getElementById('policy-lunch-start').value = data.lunchStartTime.substring(0,5);
      if(data.lunchEndTime) document.getElementById('policy-lunch-end').value = data.lunchEndTime.substring(0,5);
      
      if(data.standardCapacity) document.getElementById('policy-standard-capacity').value = data.standardCapacity;
      if(data.capacityTolerancePercent !== undefined) document.getElementById('policy-capacity-tolerance').value = data.capacityTolerancePercent;
      if(data.maxSessionsPerWeek !== undefined) document.getElementById('policy-max-sessions').value = data.maxSessionsPerWeek;
    }
  } catch (err) {
    console.error("Error loading policies", err);
  }
}

async function saveInstitutionPolicies() {
  const classStart = document.getElementById('policy-class-start').value;
  const classEnd = document.getElementById('policy-class-end').value;
  const lunchStart = document.getElementById('policy-lunch-start').value;
  const lunchEnd = document.getElementById('policy-lunch-end').value;
  
  const standardCap = document.getElementById('policy-standard-capacity').value;
  const capTolerance = document.getElementById('policy-capacity-tolerance').value;
  const maxSessions = document.getElementById('policy-max-sessions').value;

  const payload = {
    classStartTime: classStart ? classStart + ":00" : null,
    classEndTime: classEnd ? classEnd + ":00" : null,
    lunchStartTime: lunchStart ? lunchStart + ":00" : null,
    lunchEndTime: lunchEnd ? lunchEnd + ":00" : null,
    standardCapacity: standardCap ? parseInt(standardCap) : null,
    capacityTolerancePercent: capTolerance ? parseInt(capTolerance) : null,
    maxSessionsPerWeek: maxSessions ? parseInt(maxSessions) : null
  };

  try {
    const res = await fetch('/api/policies', {
      method: 'PUT',
      headers: { 
        'Authorization': 'Bearer ' + rawAuth?.token,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    });
    if (res.ok) {
      toast("Configuración guardada correctamente", "success");
    } else {
      toast("Error al guardar configuración", "error");
    }
  } catch (err) {
    console.error("Error saving policies", err);
    toast("Error de red", "error");
  }
}

// ─── Admin Subjects (HU-15) ───────────────────────────────────────────────────
async function loadAdminSubjects() {
  try {
    const res = await fetch('/api/subjects', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } });
    if (res.ok) {
      const subjects = await res.json();
      const tbody = document.getElementById("admin-subjects-table-body");
      tbody.innerHTML = "";
      
      subjects.forEach(subj => {
        tbody.innerHTML += `
          <tr class="hover:bg-gray-50">
            <td class="p-3 border-b font-mono text-sm">${subj.idSubject}</td>
            <td class="p-3 border-b font-medium text-gray-800">${subj.subjectName}</td>
            <td class="p-3 border-b text-center"><span class="bg-blue-100 text-blue-800 text-xs px-2 py-1 rounded-full">${subj.sessionPerWeek}</span></td>
            <td class="p-3 border-b text-gray-600">${subj.durationMinutes} min</td>
          </tr>
        `;
      });
    }
  } catch (e) {
    console.error("Error loading subjects", e);
  }
}

function openCreateSubjectModal() {
  document.getElementById("create-subject-id").value = "";
  document.getElementById("create-subject-name").value = "";
  document.getElementById("create-subject-sessions").value = "2";
  document.getElementById("modal-create-subject").style.display = "flex";
}

function closeCreateSubjectModal() {
  document.getElementById("modal-create-subject").style.display = "none";
}

async function createSubject() {
  const idSubject = document.getElementById("create-subject-id").value.trim().toUpperCase();
  const subjectName = document.getElementById("create-subject-name").value.trim();
  const sessionPerWeek = parseInt(document.getElementById("create-subject-sessions").value);
  
  if (!idSubject || !subjectName) {
    toast("El código y el nombre son obligatorios", "error");
    return;
  }
  if (sessionPerWeek < 1 || sessionPerWeek > 4) {
    toast("Las sesiones deben ser entre 1 y 4", "error");
    return;
  }
  
  const payload = {
    idSubject,
    subjectName,
    sessionPerWeek,
    durationMinutes: 120 // Regla de negocio fija
  };
  
  try {
    const res = await fetch('/api/subjects', {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + rawAuth?.token,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    });
    
    if (res.ok) {
      toast("Materia creada correctamente", "success");
      closeCreateSubjectModal();
      loadAdminSubjects();
    } else {
      const data = await res.json().catch(()=>null);
      toast(data?.message || "Error al crear la materia", "error");
    }
  } catch (e) {
    console.error(e);
    toast("Error de red", "error");
  }
}

// ─── Admin Teachers (HU-6) ────────────────────────────────────────────────────
let allSubjectsCache = [];

async function loadAdminTeachers() {
  try {
    const res = await fetch('/api/teachers', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } });
    if (res.ok) {
      const teachers = await res.json();
      const tbody = document.getElementById("admin-teachers-table-body");
      if(!tbody) return;
      tbody.innerHTML = "";
      
      teachers.forEach(t => {
        const comps = t.subjectsIds ? t.subjectsIds.length : 0;
        tbody.innerHTML += `
          <tr class="hover:bg-gray-50">
            <td class="p-3 border-b font-mono text-sm">${t.teacherCode}</td>
            <td class="p-3 border-b font-medium text-gray-800">${t.firstName} ${t.lastName}</td>
            <td class="p-3 border-b"><span class="bg-purple-100 text-purple-800 text-xs px-2 py-1 rounded-full">${comps} materias</span></td>
            <td class="p-3 border-b">
              <button class="text-purple-600 hover:text-purple-800" onclick="openTeacherDetailsModal('${t.teacherCode}', '${t.firstName} ${t.lastName}')">
                <i class="fas fa-edit"></i> Editar Perfil
              </button>
            </td>
          </tr>
        `;
      });
    }
  } catch(e) { console.error(e); }
}

async function openTeacherDetailsModal(teacherCode, teacherName) {
  document.getElementById("edit-teacher-id").value = teacherCode;
  document.getElementById("edit-teacher-name").innerText = teacherName;
  
  document.getElementById("teacher-availability-list").innerHTML = "";
  document.getElementById("teacher-competences-list").innerHTML = "<p class='text-sm text-gray-500'>Cargando...</p>";
  
  document.getElementById("modal-teacher-details").style.display = "flex";
  
  try {
    if (allSubjectsCache.length === 0) {
      const sRes = await fetch('/api/subjects', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } });
      if (sRes.ok) allSubjectsCache = await sRes.json();
    }
    
    const tRes = await fetch('/api/teachers', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } });
    let teacher = null;
    if (tRes.ok) {
      const teachers = await tRes.json();
      teacher = teachers.find(t => t.teacherCode === teacherCode);
    }
    
    if (!teacher) return;
    
    // Render Competences (Checkboxes)
    const compBox = document.getElementById("teacher-competences-list");
    compBox.innerHTML = "";
    allSubjectsCache.forEach(subj => {
      const isChecked = teacher.subjectsIds && teacher.subjectsIds.includes(subj.idSubject) ? "checked" : "";
      compBox.innerHTML += `
        <label class="flex items-center gap-2 text-sm bg-white p-2 border rounded cursor-pointer hover:bg-gray-50">
          <input type="checkbox" class="subj-checkbox" value="${subj.idSubject}" ${isChecked}>
          <span class="font-mono text-xs text-gray-500">${subj.idSubject}</span> ${subj.subjectName}
        </label>
      `;
    });
    
    // Render Availabilities
    if (teacher.availabilities) {
      teacher.availabilities.forEach(av => {
        addTeacherAvailabilityRowDirect(av.dayOfWeek, av.startTime.substring(0,5), av.endTime.substring(0,5));
      });
    }
    
  } catch(e) { console.error(e); }
}

function closeTeacherDetailsModal() {
  document.getElementById("modal-teacher-details").style.display = "none";
}

function addTeacherAvailabilityRow() {
  const day = document.getElementById("new-avail-day").value;
  const start = document.getElementById("new-avail-start").value;
  const end = document.getElementById("new-avail-end").value;
  
  if(!start || !end) {
    toast("Selecciona hora de inicio y fin", "error");
    return;
  }
  if(start >= end) {
    toast("La hora de inicio debe ser menor a la hora de fin", "error");
    return;
  }
  
  addTeacherAvailabilityRowDirect(day, start, end);
}

function addTeacherAvailabilityRowDirect(day, start, end) {
  const ul = document.getElementById("teacher-availability-list");
  const li = document.createElement("li");
  li.className = "flex justify-between items-center bg-white p-2 border rounded text-sm avail-item";
  li.innerHTML = `
    <span class="font-medium text-gray-700 avail-day w-24">${day}</span>
    <span class="text-gray-600 avail-time"><span class="avail-start">${start}</span> - <span class="avail-end">${end}</span></span>
    <button type="button" class="text-red-500 hover:text-red-700" onclick="this.parentElement.remove()">
      <i class="fas fa-trash"></i>
    </button>
  `;
  ul.appendChild(li);
}

async function saveTeacherDetails() {
  const teacherId = document.getElementById("edit-teacher-id").value;
  
  // Get checked subjects
  const checkboxes = document.querySelectorAll(".subj-checkbox:checked");
  const subjectsIds = Array.from(checkboxes).map(cb => cb.value);
  
  // Get availabilities
  const availItems = document.querySelectorAll(".avail-item");
  const availabilities = Array.from(availItems).map(item => {
    return {
      dayOfWeek: item.querySelector(".avail-day").innerText,
      startTime: item.querySelector(".avail-start").innerText + ":00",
      endTime: item.querySelector(".avail-end").innerText + ":00"
    };
  });
  
  const payload = {
    subjectsIds,
    availabilities
  };
  
  try {
    const res = await fetch('/api/teachers/' + teacherId, {
      method: 'PUT',
      headers: {
        'Authorization': 'Bearer ' + rawAuth?.token,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    });
    
    if (res.ok) {
      toast("Perfil docente actualizado", "success");
      closeTeacherDetailsModal();
      loadAdminTeachers();
    } else {
      toast("Error al actualizar perfil", "error");
    }
  } catch(e) {
    console.error(e);
    toast("Error de red", "error");
  }
}

// ─── Student Courses (HU-13) ──────────────────────────────────────────────────
async function loadStudentCourses() {
  const grid = document.getElementById("student-courses-grid");
  if (!grid) return;
  grid.innerHTML = '<div class="col-span-full text-center py-4 text-blue-600 animate-pulse">Cargando tus clases...</div>';

  try {
    let studentId = session.id;
    // Resolver el studentId real si la sesión tiene el email
    try {
      const sRes = await fetch('/api/students', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } });
      if (sRes.ok) {
         const students = await sRes.json();
         const me = students.find(s => s.email === session.email);
         if (me && me.studentId) studentId = me.studentId;
      }
    } catch(e) { console.warn("No se pudo resolver StudentId desde la API"); }

    const res = await fetch(`/api/enrollments/student/${studentId}/courses`, { 
      headers: { 'Authorization': 'Bearer ' + rawAuth?.token } 
    });
    
    if (!res.ok) throw new Error("Error fetching student courses");
    const courses = await res.json();
    
    if (courses.length === 0) {
      grid.innerHTML = `
        <div class="col-span-full flex flex-col items-center justify-center py-10 text-gray-400">
           <span class="text-4xl mb-3">📭</span>
           <p>No tienes cursos inscritos en este periodo académico.</p>
        </div>
      `;
      return;
    }

    grid.innerHTML = "";
    courses.forEach(cg => {
      grid.innerHTML += `
        <div class="border border-blue-100 bg-blue-50/30 rounded-lg p-5">
          <div class="flex justify-between items-start mb-2">
            <h4 class="font-bold text-blue-800">${cg.subjectId || "Materia " + cg.code}</h4>
            <span class="bg-green-100 text-green-800 text-xs px-2 py-1 rounded-full font-medium">Inscrito</span>
          </div>
          <p class="text-sm text-gray-600 mb-1">Grupo: <strong>${cg.code}</strong></p>
          <p class="text-sm text-gray-600">Profesor: ${cg.teacherId || "Sin asignar"}</p>
        </div>
      `;
    });
  } catch (err) {
    console.error("Error loading student courses", err);
    grid.innerHTML = '<div class="col-span-full text-red-500 bg-red-50 p-4 rounded-md">Ocurrió un error al cargar tus materias inscritas.</div>';
  }
}

// ─── Teacher Courses ──────────────────────────────────────────────────────────
async function loadTeacherCourses() {
  const grid = document.getElementById("teacher-courses-grid");
  if (!grid) return;
  grid.innerHTML = '<div class="col-span-full text-center py-4 text-indigo-600 animate-pulse">Cargando grupos asignados...</div>';

  try {
    let teacherCode = session.id;
    // Resolver el teacherCode real si la sesión tiene el email
    try {
      const tRes = await fetch('/api/teachers', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } });
      if (tRes.ok) {
         const teachers = await tRes.json();
         const me = teachers.find(t => t.email === session.email);
         if (me && me.teacherCode) teacherCode = me.teacherCode;
      }
    } catch(e) { console.warn("No se pudo resolver TeacherCode desde la API"); }

    const res = await fetch(`/api/course-groups/teacher/${teacherCode}`, { 
      headers: { 'Authorization': 'Bearer ' + rawAuth?.token } 
    });
    
    if (!res.ok) throw new Error("Error fetching teacher courses");
    const courses = await res.json();
    
    if (courses.length === 0) {
      grid.innerHTML = '<div class="col-span-full text-gray-500 italic">No tienes cursos asignados para este periodo.</div>';
      return;
    }

    grid.innerHTML = "";
    courses.forEach(cg => {
      grid.innerHTML += `
        <div class="bg-white border-l-4 border-indigo-500 rounded-lg p-5 shadow-sm hover:shadow-md transition">
          <h3 class="text-lg font-bold text-gray-800">${cg.subjectId || "Materia " + cg.code}</h3>
          <div class="mt-4 flex justify-between items-center text-sm text-gray-600">
            <span class="bg-indigo-100 text-indigo-800 py-1 px-3 rounded-full font-medium">Grupo ${cg.code}</span>
            <span class="flex items-center gap-1"><i class="fas fa-users"></i> Cupos: <span class="font-semibold text-gray-900">${cg.capacity}</span></span>
          </div>
        </div>
      `;
    });
  } catch (err) {
    console.error("Error loading teacher courses", err);
    grid.innerHTML = '<div class="col-span-full text-red-500 bg-red-50 p-4 rounded-md">Ocurrió un error al cargar tus grupos asignados.</div>';
  }
}

// ─── Admin Courses (HU-13) ───────────────────────────────────────────────────
let adminCoursesData = [];
let allTeachersData = [];

async function loadAdminCourses() {
  try {
    const [coursesRes, teachersRes, subjectsRes] = await Promise.all([
      fetch('/api/course-groups', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } }),
      fetch('/api/teachers', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } }),
      fetch('/api/subjects', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } })
    ]);
    
    if (coursesRes.ok) adminCoursesData = await coursesRes.json();
    if (teachersRes.ok) allTeachersData = await teachersRes.json();
    if (subjectsRes.ok) window.allSubjectsList = await subjectsRes.json();
    
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
    const teacher = allTeachersData.find(t => t.id === cg.teacherId || t.teacherId === cg.teacherId || t.teacherCode === cg.teacherId);
    const teacherName = teacher ? ((teacher.firstName || teacher.userName || "Prof.") + (teacher.lastName ? " " + teacher.lastName : "")) : (cg.teacherId || "Sin asignar");
    
    // Buscar nombre de la materia
    const subj = window.allSubjectsList?.find(s => s.idSubject === cg.code || s.idSubject === cg.subjectId);
    const subjName = subj ? subj.subjectName : (cg.subjectId || "Materia");
    
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${cg.code || "N/A"}</td>
      <td>${subjName}</td>
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
  
  // Filtrar profesores con contrato activo (Few-Shot Pattern: startDate != null o status active)
  let activeTeachers = allTeachersData.filter(t => t.startDate !== null && t.startDate !== undefined);
  
  // Fallback: Si en la base de datos de prueba ningún profesor tiene startDate, mostramos todos.
  if (activeTeachers.length === 0) {
      activeTeachers = allTeachersData;
  }

  activeTeachers.forEach(t => {
    const opt = document.createElement("option");
    opt.value = t.teacherId || t.teacherCode;
    const fullName = (t.firstName || t.userName || "Prof.") + (t.lastName ? " " + t.lastName : "");
    opt.textContent = fullName;
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

// ─── LÓGICA DE MATRÍCULA DE ESTUDIANTES (HU-18) ───
let enrollableCourses = [];

async function openEnrollModal() {
  const select = document.getElementById("enroll-course-select");
  if (!select) return;
  select.innerHTML = '<option value="">Cargando clases disponibles...</option>';
  document.getElementById("modal-enroll-course").style.display = "flex";

  try {
    const [coursesRes, subjectsRes] = await Promise.all([
      fetch('/api/course-groups', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } }),
      fetch('/api/subjects', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } })
    ]);

    if (coursesRes.ok && subjectsRes.ok) {
      enrollableCourses = await coursesRes.json();
      const allSubj = await subjectsRes.json();

      select.innerHTML = '<option value="">Seleccione una clase...</option>';
      enrollableCourses.forEach(cg => {
        // Ignorar llenos (opcional si hubiese enrolledCount)
        const subj = allSubj.find(s => s.idSubject === cg.subjectId || s.idSubject === cg.code);
        const subjName = subj ? subj.subjectName : cg.subjectId;
        const opt = document.createElement("option");
        opt.value = cg.courseGroupId;
        opt.textContent = `${subjName} (Grupo ${cg.code}) - Cupos: ${cg.capacity}`;
        select.appendChild(opt);
      });
    } else {
      throw new Error("No se pudo cargar la data");
    }
  } catch (err) {
    console.error(err);
    select.innerHTML = '<option value="">Error cargando opciones</option>';
  }
}

function closeEnrollModal() {
  document.getElementById("modal-enroll-course").style.display = "none";
}

async function saveEnrollment() {
  const courseGroupId = document.getElementById("enroll-course-select").value;
  if (!courseGroupId) {
    toast("Selecciona una materia", "error");
    return;
  }

  let studentId = session.email || session.id; // Infalible
  try {
      const sRes = await fetch('/api/students', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } });
      if (sRes.ok) {
         const students = await sRes.json();
         const me = students.find(s => s.email === session.email);
         if (me && me.id) studentId = me.id; // Usar siempre el UUID si se encuentra en la BD
         if (me && me.studentId) studentId = me.studentId;
      }
  } catch(e) {
      console.warn("No se pudo obtener listado de students, usando fallback", e);
  }

  const dto = {
    studentId: studentId,
    courseGroupId: courseGroupId,
    status: "ENROLLED"
  };

  try {
    const res = await fetch('/api/enrollments', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + rawAuth?.token
      },
      body: JSON.stringify(dto)
    });

    if (res.ok) {
      toast("Te has matriculado exitosamente 🎉", "success");
      closeEnrollModal();
      loadStudentCourses(); // Refrescar las clases matriculadas
    } else {
      const errText = await res.text();
      alert("Error al matricular: " + errText); // Debug alert
      toast("Error: " + errText, "error");
    }
  } catch (err) {
    toast("Error de red", "error");
  }
}

// ─── CARGAR CLASES INSCRITAS DEL ESTUDIANTE (HU-18) ───
async function loadStudentCourses() {
  const grid = document.getElementById("student-courses-grid");
  if (!grid) return;

  grid.innerHTML = '<p class="text-gray-400 text-center col-span-full py-8">⏳ Cargando tus clases...</p>';

  // Primero resolver el studentId real del usuario logueado
  let studentId = session.email || session.id;
  try {
    const sRes = await fetch('/api/students', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } });
    if (sRes.ok) {
      const students = await sRes.json();
      const me = students.find(s => s.email === session.email);
      if (me && me.id) studentId = me.id;
      if (me && me.studentId) studentId = me.studentId;
    }
  } catch(e) { console.warn("Fallback studentId", e); }

  try {
    // Cargar cursos matriculados + materias + profesores en paralelo
    const [coursesRes, subjectsRes, teachersRes] = await Promise.all([
      fetch(`/api/enrollments/student/${studentId}/courses`, { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } }),
      fetch('/api/subjects', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } }),
      fetch('/api/teachers', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } })
    ]);

    if (!coursesRes.ok) {
      grid.innerHTML = '<p class="text-gray-400 text-center col-span-full py-8">📭 No tienes cursos inscritos en este periodo académico.</p>';
      return;
    }

    const courses = await coursesRes.json();
    const subjects = subjectsRes.ok ? await subjectsRes.json() : [];
    const teachers = teachersRes.ok ? await teachersRes.json() : [];

    if (!courses || courses.length === 0) {
      grid.innerHTML = '<p class="text-gray-400 text-center col-span-full py-8">📭 No tienes cursos inscritos en este periodo académico.</p>';
      return;
    }

    // Colores para las tarjetas
    const colors = [
      { bg: "bg-blue-50", border: "border-blue-200", badge: "bg-blue-100 text-blue-700", icon: "text-blue-600" },
      { bg: "bg-green-50", border: "border-green-200", badge: "bg-green-100 text-green-700", icon: "text-green-600" },
      { bg: "bg-purple-50", border: "border-purple-200", badge: "bg-purple-100 text-purple-700", icon: "text-purple-600" },
      { bg: "bg-orange-50", border: "border-orange-200", badge: "bg-orange-100 text-orange-700", icon: "text-orange-600" },
      { bg: "bg-pink-50", border: "border-pink-200", badge: "bg-pink-100 text-pink-700", icon: "text-pink-600" },
      { bg: "bg-teal-50", border: "border-teal-200", badge: "bg-teal-100 text-teal-700", icon: "text-teal-600" }
    ];

    grid.innerHTML = courses.map((cg, i) => {
      const c = colors[i % colors.length];
      const subj = subjects.find(s => s.idSubject === cg.subjectId || s.idSubject === cg.code);
      const subjName = subj ? subj.subjectName : (cg.subjectId || "Materia");
      const teacher = teachers.find(t => t.id === cg.teacherId || t.teacherId === cg.teacherId || t.teacherCode === cg.teacherId);
      const teacherName = teacher ? ((teacher.firstName || teacher.userName || "Prof.") + (teacher.lastName ? " " + teacher.lastName : "")) : "Por asignar";

      return `
        <div class="${c.bg} ${c.border} border rounded-lg p-5 hover:shadow-lg transition-all duration-300">
          <div class="flex items-start justify-between mb-3">
            <span class="${c.badge} text-xs font-bold px-2 py-1 rounded-full">Grupo ${cg.code || "—"}</span>
            <span class="bg-green-100 text-green-700 text-xs font-bold px-2 py-1 rounded-full">✅ Inscrito</span>
          </div>
          <h4 class="text-lg font-bold text-gray-800 mb-2">${subjName}</h4>
          <div class="space-y-1 text-sm text-gray-600">
            <p><i class="fas fa-chalkboard-teacher ${c.icon} mr-2"></i>${teacherName}</p>
            <p><i class="fas fa-users ${c.icon} mr-2"></i>Cupos: ${cg.capacity || "—"}</p>
          </div>
        </div>
      `;
    }).join("");

  } catch (err) {
    console.error("Error cargando cursos del estudiante:", err);
    grid.innerHTML = '<p class="text-red-400 text-center col-span-full py-8">❌ Error cargando tus clases. Intenta recargar.</p>';
  }
}

// ─── CARGAR CLASES ASIGNADAS DEL DOCENTE (HU-17) ───
async function loadTeacherCourses() {
  const grid = document.getElementById("teacher-courses-grid");
  if (!grid) return;

  grid.innerHTML = '<p class="text-gray-400 text-center col-span-full py-8">⏳ Cargando tus clases...</p>';

  let teacherId = session.id;
  try {
    const tRes = await fetch('/api/teachers', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } });
    if (tRes.ok) {
      const teachers = await tRes.json();
      const me = teachers.find(t => t.email === session.email);
      if (me && me.teacherCode) teacherId = me.teacherCode;
      else if (me && me.id) teacherId = me.id;
    }
  } catch(e) { console.warn("Fallback teacherId", e); }

  try {
    const [coursesRes, subjectsRes] = await Promise.all([
      fetch(`/api/course-groups/teacher/${teacherId}`, { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } }),
      fetch('/api/subjects', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } })
    ]);

    if (!coursesRes.ok) {
      grid.innerHTML = '<p class="text-gray-400 text-center col-span-full py-8">📭 No tienes clases asignadas actualmente.</p>';
      return;
    }

    const courses = await coursesRes.json();
    const subjects = subjectsRes.ok ? await subjectsRes.json() : [];

    if (!courses || courses.length === 0) {
      grid.innerHTML = '<p class="text-gray-400 text-center col-span-full py-8">📭 No tienes clases asignadas actualmente.</p>';
      return;
    }

    grid.innerHTML = courses.map((cg, i) => {
      const subj = subjects.find(s => s.idSubject === cg.subjectId || s.idSubject === cg.code);
      const subjName = subj ? subj.subjectName : (cg.subjectId || "Materia");

      return `
        <div class="bg-indigo-50 border border-indigo-200 rounded-lg p-5 hover:shadow-lg transition-all duration-300">
          <div class="flex items-start justify-between mb-3">
            <span class="bg-indigo-100 text-indigo-700 text-xs font-bold px-2 py-1 rounded-full">Grupo ${cg.code || "—"}</span>
            <span class="bg-indigo-100 text-indigo-600 text-xs font-bold px-2 py-1 rounded-full">👨‍🏫 Asignado</span>
          </div>
          <h4 class="text-lg font-bold text-gray-800 mb-2">${subjName}</h4>
          <div class="space-y-1 text-sm text-gray-600">
            <p><i class="fas fa-users text-indigo-600 mr-2"></i>Cupos: ${cg.capacity || "—"}</p>
          </div>
        </div>
      `;
    }).join("");

  } catch (err) {
    console.error("Error cargando cursos del docente:", err);
    grid.innerHTML = '<p class="text-red-400 text-center col-span-full py-8">❌ Error cargando tus clases.</p>';
  }
}

async function openCreateScheduleSessionModal() {
  const select = document.getElementById("create-session-course");
  select.innerHTML = '<option value="">Cargando...</option>';
  document.getElementById("modal-create-schedule-session").style.display = "flex";

  try {
    let courses = [...adminCoursesData];
    let subjects = [];
    
    // Always load subjects to ensure they can select any subject
    const res = await fetch('/api/subjects', { headers: { 'Authorization': 'Bearer ' + rawAuth?.token } });
    if (res.ok) {
        subjects = await res.json();
    }

    select.innerHTML = '<option value="">Seleccione un curso o materia...</option>';
    
    // First: existing courses
    courses.forEach(cg => {
      const opt = document.createElement("option");
      opt.value = cg.courseGroupId || cg.code;
      
      // Intentar buscar el nombre de la materia si cg.subjectId es un ID
      let subjName = cg.subjectId || 'Materia';
      const subj = subjects.find(s => s.idSubject === cg.code || s.idSubject === cg.subjectId);
      if (subj) subjName = subj.subjectName;

      opt.textContent = `${cg.code || 'N/A'} - ${subjName} (Curso Activo)`;
      opt.dataset.code = cg.code;
      opt.dataset.teacher = cg.teacherId || '';
      select.appendChild(opt);
    });

    // Second: subjects that do not have a course yet
    subjects.forEach(s => {
      if (!courses.some(cg => cg.code === s.idSubject)) {
        const opt = document.createElement("option");
        opt.value = s.idSubject; // will act as code
        opt.textContent = `${s.idSubject} - ${s.subjectName} (Nueva Materia)`;
        opt.dataset.code = s.idSubject;
        opt.dataset.teacher = '';
        select.appendChild(opt);
      }
    });

    if (courses.length === 0 && subjects.length === 0) {
        select.innerHTML = '<option value="">No hay materias creadas (Catálogo vacío)</option>';
    }
  } catch(e) {
      console.error(e);
      select.innerHTML = '<option value="">Error cargando datos</option>';
  }
}

function closeCreateScheduleSessionModal() {
  document.getElementById("modal-create-schedule-session").style.display = "none";
}

async function saveNewScheduleSession() {
  const courseSelect = document.getElementById("create-session-course");
  const courseGroupId = courseSelect.value;
  if (!courseGroupId) {
    toast("Debe seleccionar un curso", "error");
    return;
  }
  
  const selectedOption = courseSelect.options[courseSelect.selectedIndex];
  const courseCode = selectedOption.dataset.code;
  const teacher = selectedOption.dataset.teacher;
  const day = document.getElementById("create-session-day").value;
  const slot = document.getElementById("create-session-slot").value;

  const payload = {
      courseGroupId: courseGroupId,
      courseCode: courseCode,
      day: day,
      slot: slot,
      teacher: teacher
  };

  try {
      const res = await fetch('/api/schedule-sessions', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + rawAuth?.token
          },
          body: JSON.stringify(payload)
      });
      
      if (res.ok) {
          const data = await res.json();
          toast("Sesión creada exitosamente", "success");
          closeCreateScheduleSessionModal();
          
          if (data.warnings && data.warnings.length > 0) {
              data.warnings.forEach(w => {
                  toast(w, "warning");
              });
          }
      } else {
          toast("Error al crear la sesión", "error");
      }
  } catch (err) {
      console.error(err);
      toast("Error de red", "error");
  }
}

window.navigateTo = navigateTo;

// ─── Sidebar ──────────────────────────────────────────────────────────────────
function buildSidebar(menu) {
  // Sidebar removida en favor del diseño de Tailwind (Header + Quick Actions Grid)
}

// ─── KPIs ─────────────────────────────────────────────────────────────────────
function buildKPIs(kpis) {
  const g = document.getElementById("kpi-grid");
  if (!g) return;
  
  if (!kpis || kpis.length === 0) {
    g.style.display = 'none';
    return;
  }
  
  g.style.display = 'grid';
  g.innerHTML = kpis.map(k => `
    <div class="bg-white rounded-xl shadow-md p-6 flex items-center gap-4 transition-transform hover:-translate-y-1">
      <div class="${k.bg || 'bg-blue-100 text-blue-600'} p-4 rounded-full w-14 h-14 flex items-center justify-center text-2xl">
        ${k.icon}
      </div>
      <div>
        <p class="text-gray-500 text-sm font-medium">${k.label}</p>
        <p class="text-2xl font-bold text-gray-800">${k.value}</p>
      </div>
    </div>`).join("");
}

// ─── Quick Actions / Opciones Principales ─────────────────────────────────────
function buildQuickActions(actions) {
  const box = document.getElementById("quick-actions-box");
  if (!box) return;
  box.innerHTML = "";
  
  actions.forEach(a => {
    const btn = document.createElement("button");
    btn.className = "bg-gray-50 hover:bg-gray-100 border border-gray-200 rounded-xl p-4 text-left transition-colors group";
    btn.innerHTML = `
      <div class="flex items-center gap-3 mb-2">
        <span class="text-2xl">${a.emoji || '⚡'}</span>
        <h4 class="font-bold text-gray-800 group-hover:text-blue-600 transition-colors">${a.label}</h4>
      </div>
      <p class="text-sm text-gray-500">${a.desc || ''}</p>
    `;
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

function checkConsecutiveDays(courseCode, courseGroup, toDay, fromDay) {
  const toIdx = DAYS.indexOf(toDay);
  if (toIdx === -1) return true;
  
  let scheduledDays = [];
  for (const slot in scheduleData) {
    for (const d in scheduleData[slot]) {
      if (d === fromDay) continue; // skip the origin if moving
      
      const s = scheduleData[slot][d];
      if (s && s.code === courseCode && s.group === courseGroup) {
        if (!scheduledDays.includes(d)) scheduledDays.push(d);
      }
    }
  }
  
  const totalSessions = scheduledDays.length + 1;
  
  // HU-12: Policy only for 2 or 3 weekly sessions
  if (totalSessions !== 2 && totalSessions !== 3) {
    return true;
  }
  
  let hasAdjacent = false;
  for (const d of scheduledDays) {
    const dIdx = DAYS.indexOf(d);
    if (Math.abs(dIdx - toIdx) === 1) {
      hasAdjacent = true;
      break;
    }
  }
  
  if (hasAdjacent) {
    const msg = `Asignación en días consecutivos detectada para ${courseCode} (${courseGroup}).\n\nPara un aprendizaje óptimo, la política institucional sugiere alternar días para cursos de 2 o 3 sesiones semanales.\n\n¿Deseas registrar la excepción y agendar de todos modos?`;
    if(!confirm(msg)) {
      toast("Asignación cancelada. Selecciona un día alternativo.", "info");
      return false;
    }
  }
  
  return true;
}

async function cellDrop(e) {
  e.preventDefault();
  const cell = e.currentTarget;
  cell.classList.remove("over","clash");
  const { slot:toSlot, day:toDay } = cell.dataset;

  let movedItem = null;

  if (dragSrc?.from === "cell") {
    const { slot:fromSlot, day:fromDay } = dragSrc;
    if (fromSlot===toSlot && fromDay===toDay) { dragSrc=null; return; }
    const s = scheduleData[fromSlot]?.[fromDay];
    if (!s) { dragSrc=null; return; }
    
    // We will let the backend handle the soft warning, but if we have local blocking validations we keep them here.
    // We remove local block for checkConsecutiveDays since backend handles it as Soft Warning.
    
    const existing = scheduleData[toSlot]?.[toDay];
    if (!scheduleData[toSlot]) scheduleData[toSlot] = {};
    scheduleData[toSlot][toDay] = s;
    if (existing) scheduleData[fromSlot][fromDay] = existing;
    else          delete scheduleData[fromSlot][fromDay];
    movedItem = s;
    toast(`${s.code} → ${toDay} ${toSlot}`, "success");

  } else if (dragSrc?.from === "pool") {
    const item = pool[dragSrc.idx];
    if (!item) { dragSrc=null; return; }
    if (scheduleData[toSlot]?.[toDay]) { toast("That cell is occupied","error"); dragSrc=null; return; }
    
    if (!scheduleData[toSlot]) scheduleData[toSlot] = {};
    scheduleData[toSlot][toDay] = { ...item };
    pool.splice(dragSrc.idx, 1);
    movedItem = item;
    toast(`${item.code} scheduled on ${toDay} ${toSlot}`, "success");
  }

  dragSrc = null;
  buildScheduleGrid(true);
  buildPool();
  hideInspector();

  // Call API to save the session and check for soft warnings
  if (movedItem) {
      try {
          const payload = {
              courseGroupId: movedItem.group, // Assuming this maps
              courseCode: movedItem.code,
              day: toDay,
              slot: toSlot,
              teacher: movedItem.teacher
          };
          
          const res = await fetch('/api/schedule-sessions', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ' + rawAuth?.token
              },
              body: JSON.stringify(payload)
          });
          
          if (res.ok) {
              const data = await res.json();
              if (data.warnings && data.warnings.length > 0) {
                  data.warnings.forEach(w => {
                      // Soft warning alert!
                      toast(w, "warning");
                  });
              }
          }
      } catch (err) {
          console.error("Failed to save schedule session to API", err);
      }
  }
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
  if (type === "warning") {
      t.style.backgroundColor = "#fff3cd";
      t.style.color = "#856404";
      t.style.borderLeft = "4px solid #ffeeba";
  }
  const icons = { success:"fa-check-circle", error:"fa-exclamation-circle", info:"fa-info-circle", warning:"fa-exclamation-triangle" };
  t.innerHTML = `<i class="fas ${icons[type]||icons.info}"></i> ${msg}`;
  root.appendChild(t);
  setTimeout(() => { t.style.transition=".3s"; t.style.opacity="0"; t.style.transform="translateX(28px)"; }, 3000);
  setTimeout(() => t.remove(), 3400);
}
window.toast = toast;
