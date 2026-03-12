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
    notification.innerHTML = '<i class="fas fa-check-circle"></i> ' + message;
  } else if (type === 'error') {
    notification.style.backgroundColor = '#930606';
    notification.innerHTML = '<i class="fas fa-exclamation-circle"></i> ' + message;
  } else {
    notification.style.backgroundColor = '#2196F3';
    notification.innerHTML = '<i class="fas fa-info-circle"></i> ' + message;
  }
  
  document.body.appendChild(notification);
  
  setTimeout(() => {
    notification.style.animation = 'slideOut 0.3s ease-out';
    setTimeout(() => notification.remove(), 300);
  }, 3000);
}

document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');

    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const remember = document.getElementById('remember').checked;

            // Simple validation
            if (!email || !password) {
                showNotification('Please fill in all fields', 'error');
                return;
            }

            // Here you would normally send data to your backend
            console.log('Login attempt:', {
                email: email,
                password: password,
                remember: remember
            });

            showNotification('Login successful! Redirecting...', 'success');
            
            // Redirect to dashboard
            setTimeout(() => {
                window.location.href = 'dashboard.html';
            }, 1500);
        });
    }
});

// Add animations
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