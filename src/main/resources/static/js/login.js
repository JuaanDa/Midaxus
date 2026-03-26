// Login Page JavaScript

function showNotification(message, type = 'info') {

  const notification = document.createElement('div');

  notification.style.cssText = `
    position: fixed;
    top: 20px;
    right: 20px;
    padding: 1rem 1.5rem;
    border-radius: 6px;
    font-weight: 600;
    color: white;
    z-index: 1000;
    animation: slideIn 0.3s ease-out;
  `;

  if (type === 'success') {
    notification.style.backgroundColor = '#3b9a95';
  } else if (type === 'error') {
    notification.style.backgroundColor = '#930606';
  } else {
    notification.style.backgroundColor = '#2196F3';
  }

  notification.innerHTML = message;

  document.body.appendChild(notification);

  setTimeout(() => {
    notification.style.animation = 'slideOut 0.3s ease-out';
    setTimeout(() => notification.remove(), 300);
  }, 3000);
}


document.addEventListener('DOMContentLoaded', () => {

  const loginForm = document.getElementById('loginForm');

  if (!loginForm) return;

  loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const remember = document.getElementById('remember').checked;

    if (!email || !password) {
      showNotification('Por favor complete todos los campos', 'error');
      return;
    }

    try {

      const response = await fetch('/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          email: email,        // ✔ CORREGIDO
          password: password   // ✔ CORREGIDO
        })
      });

      const data = await response.json();
      console.log("Respuesta del servidor:", data);

      // Validación REAL del éxito
      if (!data.success) {
        showNotification(data.message, 'error');
        return;
      }

      showNotification('Login exitoso, redirigiendo...', 'success');

      if (remember) {
        localStorage.setItem("user", JSON.stringify(data));
      }

      setTimeout(() => {
        window.location.href = "dashboard.html";
      }, 1200);

    } catch (error) {
      console.error("Error login:", error);
      showNotification('Error en el servidor', 'error');
    }
  });

});


// Animaciones de notificación

const style = document.createElement('style');

style.textContent = `
@keyframes slideIn {
  from {
    transform: translateX(400px);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
}

@keyframes slideOut {
  from {
    transform: translateX(0);
    opacity: 1;
  }
  to {
    transform: translateX(400px);
    opacity: 0;
  }
}
`;

document.head.appendChild(style);