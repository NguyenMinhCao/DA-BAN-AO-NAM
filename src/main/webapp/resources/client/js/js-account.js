document.addEventListener('DOMContentLoaded', function () {
    const navLinks = document.querySelectorAll('.nav-link-select');
    const currentPath = window.location.pathname;

    navLinks.forEach((link) => {
        if (link.getAttribute('href') === currentPath) {
            link.style.color = '#4CAF50';
        } else {
            link.style.color = '#000';
        }

    })

});