// 1. Logic chuyển ô bằng phím Enter
const userNameInput = document.getElementById('userName');
const passInput = document.getElementById('pass');

userNameInput.addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); 
        passInput.focus(); 
    }
});

// 2. Logic Mắt nhắm / Mắt mở
const toggleBtn = document.getElementById('toggleBtn');
const eyeIcon = document.getElementById('eyeIcon');

toggleBtn.addEventListener('click', function() {
    const isPassword = passInput.getAttribute('type') === 'password';
    
    if (isPassword) {
        passInput.setAttribute('type', 'text');
        // Đổi sang mắt mở
        eyeIcon.classList.remove('bi-eye-slash');
        eyeIcon.classList.add('bi-eye');
    } else {
        passInput.setAttribute('type', 'password');
        // Đổi sang mắt nhắm
        eyeIcon.classList.remove('bi-eye');
        eyeIcon.classList.add('bi-eye-slash');
    }
    
    passInput.focus();
});
