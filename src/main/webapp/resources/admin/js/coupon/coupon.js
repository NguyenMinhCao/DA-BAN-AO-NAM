document.addEventListener('DOMContentLoaded', function () {

    const btnUpdateCreateCoupon = document.getElementById('btn-update-create-coupon');
    // Chọn các nút
    const btnFixed = document.querySelector('.btn-fixed');
    const btnPercient = document.querySelector('.btn-percient');

    // Chọn các phần để hiển thị/ẩn
    const fixedSection = document.getElementById('fixed-section');
    const percentageSection = document.getElementById('percentage-section');


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

    // Chọn checkbox và phần nhập liệu
    const usageLimitCheckbox = document.getElementById('usageLimitCheckbox');
    const usageLimitInputDiv = document.getElementById('usageLimitInputDiv');

    // Hàm để cập nhật hiển thị dựa trên trạng thái của checkbox
    function updateUsageLimitDisplay() {
        if (usageLimitCheckbox.checked) {
            console.log('cao dep trai')
            // Nếu checkbox được chọn, hiển thị phần nhập liệu
            usageLimitInputDiv.style.display = 'block';
        } else {
            // Nếu checkbox không được chọn, ẩn phần nhập liệu
            usageLimitInputDiv.style.display = 'none';
        }
    }

    // Gắn sự kiện thay đổi cho checkbox
    usageLimitCheckbox.addEventListener('change', updateUsageLimitDisplay);

    // Khởi tạo hiển thị dựa trên trạng thái ban đầu của checkbox
    updateUsageLimitDisplay();


    // Chọn các phần tử cần thiết
    const valueCodeInnput = document.getElementById('value-code-input');
    const displayHeaderCode = document.getElementById('display-header-code');

    // Hàm cập nhật nội dung của thẻ h5 dựa trên giá trị của input
    function updateHeaderCode() {
        // Lấy giá trị hiện tại của input và cập nhật thẻ h5
        displayHeaderCode.textContent = valueCodeInnput.value;
    }

    // Gắn sự kiện 'input' để theo dõi khi người dùng nhập liệu
    valueCodeInnput.addEventListener('input', updateHeaderCode);


    // Hàm để tạo chuỗi ngẫu nhiên gồm 12 ký tự (A-Z, 0-9)
    function generateRandomCode(length) {
        const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
        let result = '';
        for (let i = 0; i < length; i++) {
            const randomIndex = Math.floor(Math.random() * characters.length);
            result += characters.charAt(randomIndex);
        }
        return result;
    }

    const createCodeBtn = document.getElementById('createCodeRenderNew');

    createCodeBtn.addEventListener('click', function () {
        const randomCode = generateRandomCode(12);
        valueCodeInnput.value = randomCode;
        updateHeader(); // Cập nhật thẻ h5 ngay lập tức
    });

    function updateHeader() {
        displayHeaderCode.textContent = valueCodeInnput.value;
    }


    // Chọn các phần tử cần thiết trong danh sách
    const reducedType = document.querySelector('.reduced-type');
    const maximumReduction = document.querySelector('.maximum-reduction');
    const inputMax = document.querySelector('.input-max');

    // Hàm khi nhấn nút "Số tiền"
    function showFixed() {
        // Thiết lập text cho reduced-type
        reducedType.textContent = 'đ';

        // Thêm lớp back-ground-focus cho nút "Số tiền" và loại bỏ khỏi nút "%"
        btnFixed.classList.add('back-ground-focus');
        btnPercient.classList.remove('back-ground-focus');

        // Hiển thị phần Giảm Giá Theo Số Tiền và ẩn phần Giảm Giá Theo Phần Trăm
        fixedSection.style.display = 'block';
        percentageSection.style.setProperty('display', 'none', 'important');

        // Xóa giá trị trong trường input-max khi chọn "Số tiền"
        inputMax.value = '';
        maximumReduction.style.display = 'none';
    }

    // Hàm khi nhấn nút "%"
    function showPercient() {
        // Thiết lập text cho reduced-type
        reducedType.textContent = '%';

        // Thêm lớp back-ground-focus cho nút "%" và loại bỏ khỏi nút "Số tiền"
        btnPercient.classList.add('back-ground-focus');
        btnFixed.classList.remove('back-ground-focus');

        fixedSection.style.setProperty('display', 'none', 'important');
        percentageSection.style.display = 'block';
    }

    // Gắn sự kiện click cho các nút
    btnFixed.addEventListener('click', showFixed);
    btnPercient.addEventListener('click', showPercient);

    // Khởi tạo trạng thái ban đầu dựa trên lớp back-ground-focus
    if (btnPercient.classList.contains('back-ground-focus')) {
        showPercient();
    } else if (btnFixed.classList.contains('back-ground-focus')) {
        showFixed();
    }

    // Gắn sự kiện click cho các nút
    btnFixed.addEventListener('click', showFixed);
    btnPercient.addEventListener('click', showPercient);

    // Chọn các phần tử trong danh sách giảm giá
    const reducedValue = document.querySelector('.reduced-value');

    // Chọn các trường nhập liệu
    const inputValueReduces = document.querySelectorAll('.input-value-reduce');


    // Hàm cập nhật giá trị giảm giá vào reduced-value khi nhập vào input-value-reduce
    function updateReducedValue(event) {
        reducedValue.textContent = event.target.value || '0'; // Nếu không nhập gì, hiển thị 0
    }

    // Gắn sự kiện input cho các trường input-value-reduce
    inputValueReduces.forEach(function (input) {
        input.addEventListener('input', updateReducedValue);
    });

    // Hàm cập nhật hiển thị maximum-reduction khi nhập vào input-max
    function updateMaximumReduction(event) {
        const value = event.target.value.trim();
        if (value !== '') {
            maximumReduction.textContent = 'tối đa ' + value + 'đ';
            maximumReduction.style.display = 'inline';
        } else {
            maximumReduction.textContent = '';
            maximumReduction.style.display = 'none';
        }
    }

    // Gắn sự kiện input cho trường input-max
    inputMax.addEventListener('input', updateMaximumReduction);

    // Khởi tạo trạng thái ban đầu dựa trên lớp back-ground-focus
    if (btnPercient.classList.contains('back-ground-focus')) {
        showPercient();
    } else if (btnFixed.classList.contains('back-ground-focus')) {
        showFixed();
    }

    // Chọn phần tử <li id="conditionsApply">
    const conditionsApply = document.getElementById('conditionsApply');

    console.log(conditionsApply)
    // Hàm cập nhật hiển thị dựa trên lựa chọn radio
    function updateConditionsApply() {
        if (radioNothing.checked) {
            // Khi chọn "Không có"
            conditionsApply.style.display = 'none';
            minimumValueInput.style.display = 'none';
        } else if (radioYes.checked) {
            // Khi chọn "Tổng giá trị đơn hàng tối thiểu"
            conditionsApply.style.display = 'list-item';
            minimumValueInput.style.display = 'inline-flex';
        }
    }

    // Gắn sự kiện change cho các radio button
    radioNothing.addEventListener('change', updateConditionsApply);
    radioYes.addEventListener('change', updateConditionsApply);

    // Gọi hàm để thiết lập trạng thái ban đầu
    updateConditionsApply();

    // Chọn span để hiển thị giá trị tối thiểu trong <li id="conditionsApply">
    const minimumOrderValueSpan = document.getElementById('minimum-order-value');

    // Hàm cập nhật giá trị tối thiểu trong <li id="conditionsApply">
    function updateMinimumOrderValue(event) {
        const value = event.target.value.trim();
        if (value !== '') {
            // Định dạng giá trị với dấu phẩy phân cách hàng nghìn
            const formattedValue = formatCurrency(value);
            minimumOrderValueSpan.textContent = 'Tổng giá trị đơn hàng được khuyến mại phải tối thiểu là ' + formattedValue + 'đ';
        } else {
            minimumOrderValueSpan.textContent = 'Tổng giá trị đơn hàng được khuyến mại phải tối thiểu là 0đ';
        }
    }

    // Gắn sự kiện input cho trường input-minium-total-value
    const inputMinimumTotalValue = document.querySelector('.input-minium-total-value');
    if (inputMinimumTotalValue) {
        inputMinimumTotalValue.addEventListener('input', updateMinimumOrderValue);
    } else {
        console.error('Không tìm thấy phần tử với class "input-minium-total-value".');
    }

    // Hàm định dạng số thành định dạng tiền tệ với dấu phẩy phân cách hàng nghìn
    function formatCurrency(value) {
        const numericValue = parseInt(value.replace(/\D/g, ''), 10);
        if (isNaN(numericValue)) return '0';
        return new Intl.NumberFormat('vi-VN', { style: 'decimal', minimumFractionDigits: 0 }).format(numericValue);
    }

    // Chọn các checkbox
    const customerLimitCheckbox = document.getElementById('customerLimitCheckbox');

    // Chọn các phần tử <li> cần hiển thị/ẩn
    const numberUsedLi = document.getElementById('numberUsed');
    const limitedUserLi = document.getElementById('limited-user');

    // Chọn ô nhập liệu và span liên quan
    const inputUsageLimit = document.getElementById('inputUsageLimit');
    const numberTimesUsedSpan = document.querySelector('.number-times-used');
    const limitedOneTimeSpan = document.querySelector('.limited-one-time');

    // Hàm hiển thị hoặc ẩn <li id="numberUsed">
    function toggleNumberUsed() {
        if (usageLimitCheckbox.checked) {
            numberUsedLi.style.display = 'list-item';
        } else {
            numberUsedLi.style.display = 'none';
        }
    }

    // Hàm hiển thị hoặc ẩn <li id="limited-user">
    function toggleLimitedUser() {
        if (customerLimitCheckbox.checked) {
            limitedUserLi.style.display = 'list-item';
        } else {
            limitedUserLi.style.display = 'none';
        }
    }

    // Hàm cập nhật số lần sử dụng trong <li id="numberUsed">
    function updateNumberUsed() {
        const value = inputUsageLimit.value.trim();
        if (value !== '') {
            numberTimesUsedSpan.textContent = formatCurrency(value);
        } else {
            numberTimesUsedSpan.textContent = '1'; // Giá trị mặc định
        }
    }

    // Gắn sự kiện cho checkbox "Giới hạn sử dụng"
    usageLimitCheckbox.addEventListener('change', toggleNumberUsed);

    // Gắn sự kiện cho checkbox "Giới hạn mỗi khách hàng..."
    customerLimitCheckbox.addEventListener('change', toggleLimitedUser);

    // Gắn sự kiện cho ô nhập liệu "inputUsageLimit"
    inputUsageLimit.addEventListener('input', updateNumberUsed);

    // Khởi tạo trạng thái ban đầu
    toggleNumberUsed();
    toggleLimitedUser();
    updateNumberUsed();


    // Chọn các trường nhập ngày
    const selectStartDate = document.getElementById('selectStartDate');
    const selectEndDate = document.getElementById('selectEndDate');

    // Chọn phần tử <li> để hiển thị ngày áp dụng
    const startDateandEndDateLi = document.getElementById('startDateandEndDate');

    // Chọn các span để hiển thị ngày
    const startDateDisSpan = document.querySelector('.startDateDis');
    const endDateDisSpan = document.querySelector('.endDateDis');

    // Hàm để định dạng ngày theo định dạng mong muốn
    function formatDate(dateString) {
        if (!dateString) return '';

        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng trong JS bắt đầu từ 0
        const year = date.getFullYear();

        return `${day}/${month}/${year}`;
    }

    // Hàm cập nhật ngày trong các span và hiển thị <li>
    function updateDates() {
        const startDate = selectStartDate.value;
        const endDate = selectEndDate.value;

        if (startDate || endDate) {
            // Nếu cả hai ngày đều được chọn, kiểm tra tính hợp lệ
            if (startDate && endDate) {
                if (new Date(endDate) < new Date(startDate)) {
                    alert('Ngày kết thúc không thể trước ngày bắt đầu.');
                    endDateDisSpan.textContent = '---';
                    selectEndDate.value = '';
                    return;
                }
            }

            // Cập nhật các span với giá trị ngày đã chọn
            startDateDisSpan.textContent = startDate ? formatDate(startDate) : '---';
            endDateDisSpan.textContent = endDate ? formatDate(endDate) : '---';

            // Hiển thị phần tử <li>
            startDateandEndDateLi.style.display = 'list-item';
        } else {
            // Nếu không có ngày nào được chọn, ẩn phần tử <li>
            startDateandEndDateLi.style.display = 'none';
        }
    }
    // Gắn sự kiện 'change' cho các trường nhập ngày
    selectStartDate.addEventListener('change', updateDates);
    selectEndDate.addEventListener('change', updateDates);

    // Khởi tạo trạng thái ban đầu
    updateDates();

    const btnConfirmCreateCoupon = document.getElementById('btn-confirm-create-coupon')

    const valueCodeInput = document.getElementById('value-code-input');

    // Các ô input mới
    const fixedDiscountInput = document.getElementById('fixed-discount-input') || document.querySelector('#fixed-section .input-value-reduce');
    const percentageDiscountInput = document.getElementById('percentage-discount-input') || document.querySelector('#percentage-section .input-value-reduce');
    const maxDiscountInput = document.getElementById('max-discount-input');

    // Hàm kiểm tra mã khuyến mại
    function validateCouponCode(code) {
        // Kiểm tra độ dài
        if (code.length !== 12) {
            return false;
        }

        // Kiểm tra ký tự: chỉ a-z, A-Z, 0-9
        const regex = /^[A-Za-z0-9]{12}$/;
        return regex.test(code);
    }

    // Hàm kiểm tra giá trị giảm giá theo fixed
    function validateFixedDiscount(value) {
        console.log(value)
        if (value === '') {
            return { valid: false, message: 'Giá trị giảm giá trực tiếp không được để trống.' };
        }

        const number = Number(value);
        if (isNaN(number)) {
            return { valid: false, message: 'Giá trị giảm giá  trực tiếp  phải là số.' };
        }

        if (number <= 0) {
            return { valid: false, message: 'Giá trị giảm giá trực tiếp phải lớn hơn 0.' };
        }

        return { valid: true };
    }

    // Hàm kiểm tra giá trị giảm giá theo phần trăm
    function validatePercentageDiscount(value) {
        if (value === '') {
            return { valid: false, message: 'Giá trị giảm giá theo phần trăm không được để trống.' };
        }

        const number = Number(value);
        if (isNaN(number)) {
            return { valid: false, message: 'Giá trị giảm giá theo phần trăm phải là số.' };
        }

        if (number < 0 || number > 100) {
            return { valid: false, message: 'Giá trị giảm giá theo phần trăm phải từ 0 đến 100.' };
        }

        return { valid: true };
    }

    // Hàm kiểm tra giá trị giảm tối đa
    function validateMaxDiscount(value) {
        if (value === '') {
            return { valid: false, message: 'Giá trị giảm tối đa không được để trống.' };
        }

        const number = Number(value);
        if (isNaN(number)) {
            return { valid: false, message: 'Giá trị giảm tối đa phải là số.' };
        }

        if (number <= 0) {
            return { valid: false, message: 'Giá trị giảm tối đa phải lớn hơn 0.' };
        }

        return { valid: true };
    }

    // Hàm kiểm tra tổng giá trị đơn hàng tối thiểu
    function validateMinimumOrderValue(value) {

        if (value === '') {
            return { valid: false, message: 'Tổng giá trị đơn hàng tối thiểu không được để trống.' };
        }

        const number = Number(value);
        if (isNaN(number)) {
            return { valid: false, message: 'Tổng giá trị đơn hàng tối thiểu phải là số.' };
        }

        if (number <= 0) {
            return { valid: false, message: 'Tổng giá trị đơn hàng tối thiểu phải lớn hơn 0.' };
        }

        return { valid: true };
    }

    // Hàm kiểm tra giới hạn sử dụng
    function validateUsageLimit(value) {
        if (value === '') {
            return { valid: false, message: 'Giới hạn sử dụng không được để trống.' };
        }

        const number = Number(value);
        if (isNaN(number)) {
            return { valid: false, message: 'Giới hạn sử dụng phải là số.' };
        }

        if (!Number.isInteger(number) || number <= 0) {
            return { valid: false, message: 'Giới hạn sử dụng phải là số nguyên dương.' };
        }

        return { valid: true };
    }

    // Hàm kiểm tra ngày bắt đầu và kết thúc
    function validateDates(startDate, endDate) {
        if (startDate === '' || endDate === '') {
            return { valid: false, message: 'Vui lòng chọn cả ngày bắt đầu và ngày kết thúc.' };
        }

        const start = new Date(startDate);
        const end = new Date(endDate);

        if (end < start) {
            return { valid: false, message: 'Ngày kết thúc không thể trước ngày bắt đầu.' };
        }

        return { valid: true };
    }

    // Hàm hiển thị thông báo thành công
    function showSuccess(message) {
        toast({
            title: "Thành công!",
            message: message,
            type: "success",
            duration: 1700
        });
    }

    // Hàm hiển thị thông báo lỗi
    function showError(title, message) {
        toast({
            title: title,
            message: message,
            type: "error",
            duration: 1700
        });
    }

    // Hàm xử lý khi nhấn nút xác nhận
    async function handleConfirmClickValid() {
        const btnFixed = document.querySelector('.btn-fixed')
        const btnPercient = document.querySelector('.btn-percient')
        let isValid = true;

        const code = valueCodeInput.value.trim();
        const fixedDiscount = fixedDiscountInput ? fixedDiscountInput.value.trim() : '';
        const percentageDiscount = percentageDiscountInput ? percentageDiscountInput.value.trim() : '';
        const maxDiscount = maxDiscountInput ? maxDiscountInput.value.trim() : '';
        const usageLimit = inputUsageLimit ? inputUsageLimit.value.trim() : '';
        const startDate = selectStartDate.value.trim();
        const endDate = selectEndDate.value.trim();

        // Các ô radio và input tổng giá trị đơn hàng tối thiểu
        const radioYes = document.getElementById('checked-yes');
        const minimumOrderValueInput = document.querySelector('.input-minium-total-value');

        const minimumOrderValue = minimumOrderValueInput ? minimumOrderValueInput.value.trim() : '';

        // Kiểm tra mã khuyến mại
        if (code === '') {
            showError("Thất bại!", "Mã khuyến mại không được để trống.");
            isValid = false;
            return;
        } else if (!validateCouponCode(code)) {
            showError("Thất bại!", "Mã khuyến mại phải gồm chính xác 12 ký tự (a-z, A-Z, 0-9).");
            isValid = false;
            return;
        }

        let discountTypeReq = true;
        // Kiểm tra giá trị giảm giá theo fixed
        if (btnFixed.classList.contains('back-ground-focus')) {
            discountTypeReq = true;
            if (fixedDiscountInput) { // Kiểm tra nếu ô input này tồn tại
                const fixedValidation = validateFixedDiscount(fixedDiscount);
                if (!fixedValidation.valid) {
                    showError("Thất bại!", fixedValidation.message);
                    isValid = false;
                    return;
                }
            }
        }

        // Kiểm tra giá trị giảm giá theo phần trăm
        if (btnPercient.classList.contains('back-ground-focus')) {
            discountTypeReq = false;
            if (percentageDiscountInput) { // Kiểm tra nếu ô input này tồn tại
                const percentageValidation = validatePercentageDiscount(percentageDiscount);
                if (!percentageValidation.valid) {
                    showError("Thất bại!", percentageValidation.message);
                    isValid = false;
                    return;
                }
            }

            // Kiểm tra giá trị giảm tối đa
            if (maxDiscountInput) { // Kiểm tra nếu ô input này tồn tại
                const maxValidation = validateMaxDiscount(maxDiscount);
                if (!maxValidation.valid) {
                    showError("Thất bại!", maxValidation.message);
                    isValid = false;
                    return;
                }
            }
        }

        // Kiểm tra tổng giá trị đơn hàng tối thiểu nếu được chọn
        if (radioYes.checked) {
            const minOrderValidation = validateMinimumOrderValue(minimumOrderValue);
            console.log(minimumOrderValueInput)
            if (!minOrderValidation.valid) {
                showError("Thất bại!", minOrderValidation.message);
                isValid = false;
                return;
            }
        }

        // Kiểm tra giới hạn sử dụng nếu checkbox được chọn
        if (usageLimitCheckbox.checked) {
            const usageLimitValidation = validateUsageLimit(usageLimit);
            if (!usageLimitValidation.valid) {
                showError("Thất bại!", usageLimitValidation.message);
                isValid = false;
            }
        }

        // Kiểm tra ngày bắt đầu và kết thúc
        const dateValidation = validateDates(startDate, endDate);
        if (!dateValidation.valid) {
            showError("Thất bại!", dateValidation.message);
            isValid = false;
        }

        const dataSent = {
            couponCode: code,
            discountValuePercent: percentageDiscount,
            discountValueFixed: maxDiscount,
            maximumReduction: usageLimit,
            discountValueFixed: fixedDiscount,
            startDate: startDate,
            endDate: endDate,
            minimumValue: minimumOrderValue,
            discountType: discountTypeReq
        };

        // Nếu tất cả các trường hợp đều hợp lệ, tiếp tục xử lý tạo khuyến mại
        if (isValid) {
            try {
                const response = await fetch(`/admin/coupon/search?filter=couponCode:'${encodeURIComponent(code)}'`);
                const result = await response.json();

                console.log(result.result)

                if (result.result.length > 0) {
                    showError("Thất bại!", "Mã khuyến mại đã tồn tại. Vui lòng chọn mã khác.");
                    return;
                }

                const createResponse = await fetch('/admin/coupon/add', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(dataSent)
                });

                // const createResult = await createResponse.json();
                // console.log(createResult)
                // Giả sử tạo thành công
                showSuccess('Tạo khuyến mại thành công với mã: ' + code);
                setTimeout(() => {
                    window.location.assign('http://localhost:8080/admin/coupon');
                }, 1200);
                // Bỏ trống các ô nhập liệu sau khi tạo thành công
                // valueCodeInput.value = '';
                // if (fixedDiscountInput) fixedDiscountInput.value = '';
                // if (percentageDiscountInput) percentageDiscountInput.value = '';
                // if (maxDiscountInput) maxDiscountInput.value = '';
            } catch (error) {
                console.error('Lỗi khi tạo khuyến mại:', error);
                showError("Thất bại!", "Đã xảy ra lỗi khi tạo khuyến mại. Vui lòng thử lại sau.");
            }
        }
    }
    if (btnConfirmCreateCoupon) {
        btnConfirmCreateCoupon.addEventListener('click', handleConfirmClickValid);
    }

    // Hàm xử lý khi nhấn nút update
    async function handleConfirmClickValidUpdate() {
        const btnFixed = document.querySelector('.btn-fixed')
        const btnPercient = document.querySelector('.btn-percient')
        let isValid = true;

        const code = valueCodeInput.value.trim();
        const fixedDiscount = fixedDiscountInput ? fixedDiscountInput.value.trim() : '';
        const percentageDiscount = percentageDiscountInput ? percentageDiscountInput.value.trim() : '';
        const maxDiscount = maxDiscountInput ? maxDiscountInput.value.trim() : '';
        const usageLimit = inputUsageLimit ? inputUsageLimit.value.trim() : '';
        const startDate = selectStartDate.value.trim();
        const endDate = selectEndDate.value.trim();

        // Các ô radio và input tổng giá trị đơn hàng tối thiểu
        const radioYes = document.getElementById('checked-yes');
        const minimumOrderValueInput = document.querySelector('.input-minium-total-value');

        const minimumOrderValue = minimumOrderValueInput ? minimumOrderValueInput.value.trim() : '';

        // Kiểm tra mã khuyến mại
        if (code === '') {
            showError("Thất bại!", "Mã khuyến mại không được để trống.");
            isValid = false;
            return;
        } else if (!validateCouponCode(code)) {
            showError("Thất bại!", "Mã khuyến mại phải gồm chính xác 12 ký tự (a-z, A-Z, 0-9).");
            isValid = false;
            return;
        }

        let discountTypeReq = true;
        // Kiểm tra giá trị giảm giá theo fixed
        if (btnFixed.classList.contains('back-ground-focus')) {
            discountTypeReq = true;
            if (fixedDiscountInput) { // Kiểm tra nếu ô input này tồn tại
                const fixedValidation = validateFixedDiscount(fixedDiscount);
                if (!fixedValidation.valid) {
                    showError("Thất bại!", fixedValidation.message);
                    isValid = false;
                    return;
                }
            }
        }

        // Kiểm tra giá trị giảm giá theo phần trăm
        if (btnPercient.classList.contains('back-ground-focus')) {
            discountTypeReq = false;
            if (percentageDiscountInput) { // Kiểm tra nếu ô input này tồn tại
                const percentageValidation = validatePercentageDiscount(percentageDiscount);
                if (!percentageValidation.valid) {
                    showError("Thất bại!", percentageValidation.message);
                    isValid = false;
                    return;
                }
            }

            // Kiểm tra giá trị giảm tối đa
            if (maxDiscountInput) { // Kiểm tra nếu ô input này tồn tại
                const maxValidation = validateMaxDiscount(maxDiscount);
                if (!maxValidation.valid) {
                    showError("Thất bại!", maxValidation.message);
                    isValid = false;
                    return;
                }
            }
        }

        // Kiểm tra tổng giá trị đơn hàng tối thiểu nếu được chọn
        if (radioYes.checked) {
            const minOrderValidation = validateMinimumOrderValue(minimumOrderValue);
            console.log(minimumOrderValueInput)
            if (!minOrderValidation.valid) {
                showError("Thất bại!", minOrderValidation.message);
                isValid = false;
                return;
            }
        }

        // Kiểm tra giới hạn sử dụng nếu checkbox được chọn
        if (usageLimitCheckbox.checked) {
            const usageLimitValidation = validateUsageLimit(usageLimit);
            if (!usageLimitValidation.valid) {
                showError("Thất bại!", usageLimitValidation.message);
                isValid = false;
            }
        }

        // Kiểm tra ngày bắt đầu và kết thúc
        const dateValidation = validateDates(startDate, endDate);
        if (!dateValidation.valid) {
            showError("Thất bại!", dateValidation.message);
            isValid = false;
        }

        const codeCouponUpdate = btnUpdateCreateCoupon.getAttribute('code-coupon-by-id')
        const idCouponUpdate = btnUpdateCreateCoupon.getAttribute('id-coupon-by-id')


        console.log('sdfsfsdfdsfdsffsd', codeCouponUpdate)
        console.log('sdfsfsdfdsfdsffsd', idCouponUpdate)
        console.log('sdfsfsdfdsfdsffsd', code)
        const dataSent = {
            couponCode: code,
            discountValuePercent: percentageDiscount,
            discountValueFixed: maxDiscount,
            maximumReduction: usageLimit,
            discountValueFixed: fixedDiscount,
            startDate: startDate,
            endDate: endDate,
            minimumValue: minimumOrderValue,
            discountType: discountTypeReq
        };

        // Nếu tất cả các trường hợp đều hợp lệ, tiếp tục xử lý tạo khuyến mại
        if (isValid) {
            try {

                if (code !== codeCouponUpdate) {
                    const response = await fetch(`/admin/coupon/search?filter=couponCode:'${encodeURIComponent(code)}'`);
                    const result = await response.json();

                    console.log(result.result)

                    if (result.result.length > 0) {
                        showError("Thất bại!", "Mã khuyến mại đã tồn tại. Vui lòng chọn mã khác.");
                        return;
                    }
                }


                const createResponse = await fetch('/admin/coupon/' + idCouponUpdate + '/edit', {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(dataSent)
                });

                // const createResult = await createResponse.json();
                // console.log(createResult)
                // Giả sử tạo thành công
                showSuccess('Tạo khuyến mại thành công với mã: ' + code);
                setTimeout(() => {
                    window.location.assign('http://localhost:8080/admin/coupon');
                }, 1200);
                // Bỏ trống các ô nhập liệu sau khi tạo thành công
                // valueCodeInput.value = '';
                // if (fixedDiscountInput) fixedDiscountInput.value = '';
                // if (percentageDiscountInput) percentageDiscountInput.value = '';
                // if (maxDiscountInput) maxDiscountInput.value = '';
            } catch (error) {
                console.error('Lỗi khi tạo khuyến mại:', error);
                showError("Thất bại!", "Đã xảy ra lỗi khi tạo khuyến mại. Vui lòng thử lại sau.");
            }
        }
    }

    // Gắn sự kiện click cho nút xác nhận
    if (btnUpdateCreateCoupon) {
        btnUpdateCreateCoupon.addEventListener('click', handleConfirmClickValidUpdate);

    }
})