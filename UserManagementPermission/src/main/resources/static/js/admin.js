document.addEventListener("DOMContentLoaded", function() {
    // 1. Đồng hồ Real-time ở Sidebar
    const clock = document.getElementById('realtime-clock');
    if (clock) {
        // Viết hàm cập nhật giờ
        const updateTime = () => {
            clock.textContent = new Date().toLocaleTimeString('vi-VN', { hour12: false });
        };
        updateTime(); // Gọi ngay lập tức 1 lần để không bị trễ 1s hiển thị 00:00:00
        setInterval(updateTime, 1000); // Sau đó mới lặp mỗi giây
    }

    // 2. Logic Hiện/Ẩn mật khẩu (Mắt thần)
    document.querySelectorAll('.toggle-pwd').forEach(btn => {
        btn.addEventListener('click', function() {
            const input = this.closest('.input-group').querySelector('input');
            const icon = this.querySelector('i');
            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.replace('bi-eye-slash', 'bi-eye');
                icon.classList.add('text-primary');
            } else {
                input.type = 'password';
                icon.classList.replace('bi-eye', 'bi-eye-slash');
                icon.classList.remove('text-primary');
            }
        });
    });

    // 3. Tự động đóng Alert thành công sau 5s
    const successAlert = document.querySelector('.alert-success');
    if (successAlert) {
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(successAlert);
            bsAlert.close();
        }, 5000);
    }

    // 4. Submit ảnh đại diện ngay khi chọn file
    const avatarInput = document.getElementById('avatarInput');
    if (avatarInput) {
        avatarInput.addEventListener('change', function() {
            if(this.value) document.getElementById('avatarForm').submit();
        });
    }
    // --- FIX BUG CHỐNG TRÌNH DUYỆT TỰ ĐIỀN LẠI MẬT KHẨU ---
    const urlParams = new URLSearchParams(window.location.search);
    const passModal = document.getElementById('changePassModal');
    
    if (passModal) {
        // 1. KHI LOAD TRANG: Nếu không phải đang báo lỗi, ép xóa sạch mọi ô nhập liệu
        if (!urlParams.has('openModal')) {
            const inputs = passModal.querySelectorAll('input');
            inputs.forEach(input => {
                input.value = ''; // Tiêu diệt cache của trình duyệt
                input.removeAttribute('value');
            });
        }

        // 2. KHI ĐÓNG MODAL (Bấm Hủy hoặc dấu X): Dọn dẹp như cũ
        passModal.addEventListener('hidden.bs.modal', function () {
            // Xóa input
            this.querySelectorAll('input').forEach(input => {
                input.value = '';
                if(input.type === 'text') input.type = 'password'; 
            });

            // Ẩn lỗi
            this.querySelectorAll('.text-danger').forEach(msg => msg.style.display = 'none');
            
            // Đóng mắt thần
            this.querySelectorAll('.toggle-pwd i').forEach(icon => {
                icon.classList.replace('bi-eye', 'bi-eye-slash');
            });

            // Xóa param trên URL để F5 không bị mở lại
            const url = new URL(window.location);
            url.searchParams.delete('openModal');
            window.history.replaceState({}, '', url);
        });
    }
    // FIX BUG 2: Reset Form và ẩn lỗi khi đóng Modal
    const changePassModal = document.getElementById('changePassModal');
    if (changePassModal) {
        changePassModal.addEventListener('hidden.bs.modal', function () {
            // 1. Tìm và xóa hết giá trị trong các ô input
            const inputs = this.querySelectorAll('input');
            inputs.forEach(input => {
                input.value = '';
                if(input.type === 'text') input.type = 'password'; // Trả về dạng ẩn
            });

            // 2. Tìm và ẩn toàn bộ các dòng báo lỗi (th:if tạo ra các div này)
            const errorMessages = this.querySelectorAll('.text-danger');
            errorMessages.forEach(msg => msg.style.display = 'none');
            
            // 3. Đưa các icon mắt thần về trạng thái đóng
            const icons = this.querySelectorAll('.toggle-pwd i');
            icons.forEach(icon => {
                icon.classList.replace('bi-eye', 'bi-eye-slash');
                icon.classList.remove('text-primary');
            });

            // 4. Xóa tham số openModal trên thanh địa chỉ để tránh reload tự mở lại
            const url = new URL(window.location);
            url.searchParams.delete('openModal');
            window.history.replaceState({}, '', url);
        });
    }
    // 3. Dọn dẹp Modal (Dùng chung cho Đổi Pass và Thêm User)
    const modalsToClean = ['changePassModal', 'addUserModal'];
    
    modalsToClean.forEach(modalId => {
        const modalEl = document.getElementById(modalId);
        if (modalEl) {
            modalEl.addEventListener('hidden.bs.modal', function () {
                // Xóa input
                this.querySelectorAll('input').forEach(input => {
                    input.value = '';
                    if(input.type === 'text' && input.name.toLowerCase().includes('pass')) {
                        input.type = 'password'; 
                    }
                });

                // Reset Select box về mặc định
                this.querySelectorAll('select').forEach(select => select.selectedIndex = 0);

                // Ẩn các dòng báo lỗi màu đỏ
                this.querySelectorAll('.text-danger, .alert-danger').forEach(msg => msg.style.display = 'none');
                
                // Trả icon mắt thần về ban đầu
                this.querySelectorAll('.toggle-pwd i').forEach(icon => {
                    icon.classList.replace('bi-eye', 'bi-eye-slash');
                    icon.classList.remove('text-primary');
                });

                // Xóa param trên URL (openModal hoặc openAddModal)
                const url = new URL(window.location);
                url.searchParams.delete('openModal');
                url.searchParams.delete('openAddModal');
                window.history.replaceState({}, '', url);
            });
        }
    });
    // Hàm nhồi dữ liệu vào Modal Sửa User
    window.fillEditModal = function(button) {
        // 1. Rút dữ liệu từ cái nút vừa bị bấm
        const id = button.getAttribute('data-id');
        const fullName = button.getAttribute('data-fullname');
        const position = button.getAttribute('data-position');
        const isEnabled = button.getAttribute('data-isenabled'); // Lấy isEnabled

        // 2. Nhồi vào các ô Input trong Modal
        document.getElementById('editUserId').value = id;
        document.getElementById('editFullName').value = fullName;
        
        // Xử lý Select box Chức vụ
        const positionSelect = document.getElementById('editPosition');
        positionSelect.value = (position && position !== 'null') ? position : '';

        // Xử lý Select box Trạng thái Khóa/Mở khóa
        document.getElementById('editIsEnabled').value = isEnabled;
    };
    const editUserForm = document.getElementById('editUserForm');
    
    if (editUserForm) {
        editUserForm.addEventListener('submit', function(event) {
            const fullNameInput = document.getElementById('editFullName');
            
            // 1. Cắt khoảng trắng 2 đầu và thay nhiều khoảng trắng ở giữa thành 1
            let fullName = fullNameInput.value.trim().replace(/\s+/g, ' ');
            fullNameInput.value = fullName; // Đẩy ngược lại input cho đẹp

            // 2. Báo lỗi nếu bỏ trống
            if (!fullName) {
                alert("Họ và tên không được bỏ trống!");
                event.preventDefault(); // Chặn gửi form
                return;
            }

            // 3. Kiểm tra định dạng (Chỉ cho phép chữ cái và khoảng trắng, hỗ trợ tiếng Việt)
            const nameRegex = /^[\p{L}\s]+$/u; 
            if (!nameRegex.test(fullName)) {
                alert("Họ tên sai định dạng! Không được chứa số hoặc ký tự đặc biệt.");
                event.preventDefault();
                return;
            }

            // 4. Cảnh báo nếu không chọn Nhóm/Vai trò
            const position = document.getElementById('editPosition').value;
            if (!position || position.trim() === '') {
                const isConfirm = confirm("Bạn chưa chọn Vai trò/Nhóm cho người dùng này. Vẫn tiếp tục lưu?");
                if (!isConfirm) {
                    event.preventDefault();
                }
            }
        });
    }
});