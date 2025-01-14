document.addEventListener('DOMContentLoaded', function () {
    // Chọn các nút
    const btnFixed = document.querySelector('.btn-fixed');
    const btnPercient = document.querySelector('.btn-percient');

    // Chọn các phần để hiển thị/ẩn
    const fixedSection = document.getElementById('fixed-section');
    const percentageSection = document.getElementById('percentage-section');

    // Hàm hiển thị phần Giảm Giá Theo Số Tiền
    function showFixed() {
        // Thêm lớp vào nút được nhấn
        btnFixed.classList.add('back-ground-focus');
        btnPercient.classList.remove('back-ground-focus');

        // Hiển thị phần Giảm Giá Theo Số Tiền và ẩn phần Giảm Giá Theo Phần Trăm
        fixedSection.style.display = 'block';
        percentageSection.style.setProperty('display', 'none', 'important');
    }


    // Hàm hiển thị phần Giảm Giá Theo Phần Trăm
    function showPercentage() {
        // Thêm lớp vào nút được nhấn
        btnPercient.classList.add('back-ground-focus');
        btnFixed.classList.remove('back-ground-focus');

        // Hiển thị phần Giảm Giá Theo Phần Trăm và ẩn phần Giảm Giá Theo Số Tiền

        fixedSection.style.setProperty('display', 'none', 'important');
        percentageSection.style.display = 'block';
    }

    // Gắn sự kiện nhấp chuột vào các nút
    btnFixed.addEventListener('click', showFixed);
    btnPercient.addEventListener('click', showPercentage);

    // Khởi tạo hiển thị dựa trên loại khuyến mại hiện tại
    // Giả sử phía server đã đặt nút phù hợp với lớp 'back-ground-focus'
    if (btnFixed.classList.contains('back-ground-focus')) {
        showFixed();
    } else if (btnPercient.classList.contains('back-ground-focus')) {
        showPercentage();
    }


    // Chọn các radio button
    const radioNothing = document.getElementById('checked-nothing');
    const radioYes = document.getElementById('checked-yes');

    // Chọn phần nhập liệu cần hiển thị/ẩn
    const minimumValueInput = document.getElementById('minimum-value-input');

    // Hàm để cập nhật hiển thị dựa trên lựa chọn radio
    function updateMinimumValueDisplay() {
        if (radioYes.checked) {
            // Nếu chọn "Tổng giá trị đơn hàng tối thiểu", hiển thị phần nhập liệu
            minimumValueInput.style.display = 'inline-flex';
        } else {
            // Nếu chọn "Không có", ẩn phần nhập liệu
            minimumValueInput.style.display = 'none';
        }
    }

    // Gắn sự kiện thay đổi cho các radio button
    radioNothing.addEventListener('change', updateMinimumValueDisplay);
    radioYes.addEventListener('change', updateMinimumValueDisplay);

    // Khởi tạo hiển thị dựa trên trạng thái ban đầu
    updateMinimumValueDisplay();
})