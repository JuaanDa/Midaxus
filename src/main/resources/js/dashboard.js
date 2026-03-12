// Midaxus Dashboard JavaScript

document.addEventListener('DOMContentLoaded', function() {
  initializeDashboard();
});

function initializeDashboard() {
  setupSidebarNavigation();
  setupFormHandlers();
  setupSessionCardHandlers();
  setupButtonHandlers();
}

// Sidebar Navigation
function setupSidebarNavigation() {
  const sidebarItems = document.querySelectorAll('.sidebar li');
  
  sidebarItems.forEach(item => {
    item.addEventListener('click', function(e) {
      // Only handle clicks on items with links
      const link = this.querySelector('a');
      if (link) {
        e.preventDefault();
        
        // Remove active class from all items
        sidebarItems.forEach(li => li.classList.remove('active'));
        
        // Add active class to current item
        this.classList.add('active');
        
        // Navigate to the target
        const targetId = link.getAttribute('href').substring(1);
        navigateToScreen(targetId);
      }
    });
  });

  // Set initial active state
  const initialActive = document.querySelector('.sidebar li.active');
  if (initialActive) {
    const link = initialActive.querySelector('a');
    if (link) {
      const targetId = link.getAttribute('href').substring(1);
      navigateToScreen(targetId);
    }
  }
}

function navigateToScreen(screenId) {
  // Hide all screens
  const screens = document.querySelectorAll('.screen');
  screens.forEach(screen => screen.style.display = 'none');
  
  // Show target screen
  const targetScreen = document.getElementById(screenId);
  if (targetScreen) {
    targetScreen.style.display = 'block';
    window.scrollTo(0, 0);
  }
}

// Form Handlers
function setupFormHandlers() {
  const inputFields = document.querySelectorAll('.input-field');
  const selectFields = document.querySelectorAll('.select-field');
  
  inputFields.forEach(field => {
    field.addEventListener('focus', function() {
      this.style.borderColor = '#3b9a95';
    });
    
    field.addEventListener('blur', function() {
      if (this.value === '') {
        this.style.borderColor = '#d4e3f0';
      }
    });
  });
  
  selectFields.forEach(field => {
    field.addEventListener('change', function() {
      console.log('Selected: ' + this.value);
    });
  });
}

// Session Card Handlers
function setupSessionCardHandlers() {
  const sessionCards = document.querySelectorAll('.session-card');
  
  sessionCards.forEach(card => {
    card.addEventListener('click', function(e) {
      e.stopPropagation();
      
      // Remove selection from other cards
      sessionCards.forEach(c => c.style.opacity = '1');
      
      // Highlight selected card
      this.style.opacity = '0.8';
      this.style.transform = 'scale(1.05)';
      
      // Update inspector panel
      updateInspectorPanel(this);
    });
  });
}

function updateInspectorPanel(card) {
  const inspectorPanel = document.querySelector('.inspector-panel');
  if (inspectorPanel) {
    const courseInfo = card.textContent.trim();
    console.log('Selected course: ' + courseInfo);
  }
}

// Button Handlers
function setupButtonHandlers() {
  // Primary buttons
  const primaryButtons = document.querySelectorAll('.btn-primary');
  primaryButtons.forEach(btn => {
    btn.addEventListener('click', function(e) {
      e.preventDefault();
      handlePrimaryButtonClick(this);
    });
  });
  
  // Secondary buttons
  const secondaryButtons = document.querySelectorAll('.btn-secondary');
  secondaryButtons.forEach(btn => {
    btn.addEventListener('click', function(e) {
      e.preventDefault();
      handleSecondaryButtonClick(this);
    });
  });
  
  // Outline buttons
  const outlineButtons = document.querySelectorAll('.btn-outline');
  outlineButtons.forEach(btn => {
    btn.addEventListener('click', function(e) {
      e.preventDefault();
      handleOutlineButtonClick(this);
    });
  });
}

function handlePrimaryButtonClick(button) {
  const buttonText = button.textContent.trim().toLowerCase();
  
  if (buttonText.includes('save')) {
    showNotification('Changes saved successfully!', 'success');
  } else if (buttonText.includes('generate')) {
    showNotification('Schedule generation started...', 'info');
  } else if (buttonText.includes('publish')) {
    showNotification('Schedule published successfully!', 'success');
  } else if (buttonText.includes('add')) {
    showNotification('Item added successfully!', 'success');
  } else if (buttonText.includes('merge')) {
    showNotification('Sections merged successfully!', 'success');
  }
  
  // Add visual feedback
  button.style.transform = 'scale(0.98)';
  setTimeout(() => {
    button.style.transform = 'scale(1)';
  }, 200);
}

function handleSecondaryButtonClick(button) {
  const buttonText = button.textContent.trim().toLowerCase();
  console.log('Secondary action: ' + buttonText);
}

function handleOutlineButtonClick(button) {
  const buttonText = button.textContent.trim().toLowerCase();
  console.log('Outline action: ' + buttonText);
}

// Notification System
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
  
  .session-card {
    transition: opacity 0.2s, transform 0.2s;
  }
  
  .btn-primary, .btn-secondary, .btn-outline {
    transition: transform 0.2s;
  }
`;
document.head.appendChild(style);

// Keyboard shortcuts
document.addEventListener('keydown', function(e) {
  // Escape key to deselect
  if (e.key === 'Escape') {
    const sessionCards = document.querySelectorAll('.session-card');
    sessionCards.forEach(c => {
      c.style.opacity = '1';
      c.style.transform = 'scale(1)';
    });
  }
});

// Export functions for testing
window.showNotification = showNotification;
window.navigateToScreen = navigateToScreen;
