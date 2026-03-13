// Main JavaScript file
console.log('Midaxus Front loaded!');

// Example: Add event listeners
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM fully loaded');
    
    // Your code here
});

// Example function
function handleClick(event) {
    console.log('Button clicked:', event);
}

// Export for use in other scripts if needed
window.App = {
    handleClick: handleClick
};
