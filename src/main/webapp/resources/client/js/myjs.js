
function submitLogoutForm() {
    document.getElementById('logoutForm').submit();
}

document.getElementById('file-input').addEventListener('change', function (event) {
    // Kiểm tra xem người dùng đã chọn tệp chưa
    const file = event.target.files[0];
    if (file) {
        // Kiểm tra dung lượng tệp (giới hạn 1MB)
        if (file.size > 1024 * 1024) {
            alert("Dung lượng tệp vượt quá 1MB. Vui lòng chọn tệp nhỏ hơn.");
            return;
        }

        // Tạo URL cho ảnh được chọn
        const reader = new FileReader();
        reader.onload = function (e) {
            // Lấy phần tử label và đặt background-image là ảnh đã chọn
            const imageLabel = document.querySelector('.cW0oBM');
            imageLabel.style.backgroundImage = `url(${e.target.result})`;
        };
        reader.readAsDataURL(file); // Đọc nội dung tệp
    }
});

// Xử lý khi nhấn nút "Chọn ảnh"
document.getElementById('choose-file-btn').addEventListener('click', function () {
    document.getElementById('file-input').click(); // Kích hoạt input file ẩn
});
