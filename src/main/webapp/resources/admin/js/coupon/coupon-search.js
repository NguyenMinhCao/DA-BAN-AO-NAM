document.addEventListener('DOMContentLoaded', function () {
    // Lấy các phần tử tab và tbody của bảng
    const fetchAllCoupon = document.getElementById('fetchAllCoupon');
    const fetchCouponStillActive = document.getElementById('fetchCouponStillActive');
    const fetchCouponStopWorking = document.getElementById('fetchCouponStopWorking');
    const tabs = [fetchAllCoupon, fetchCouponStillActive, fetchCouponStopWorking];
    const couponTableBody = document.getElementById('couponTableBody');

    // Bộ lọc ngày
    const startDateFilter = document.getElementById('startDateFilter');
    const endDateFilter = document.getElementById('endDateFilter');

    // Ô tìm kiếm mã khuyến mãi
    const inputSearchCouponCode = document.getElementById('inputSearchCouponCode');

    // Chọn kích thước trang
    const selectPageSize = document.getElementById('selectPageSize');

    // Nút điều hướng trang
    const prevPageBtn = document.getElementById('prevPage');
    const nextPageBtn = document.getElementById('nextPage');
    const currentPageSpan = document.getElementById('currentPage');

    console.log('Prev Page Button:', prevPageBtn);
    console.log('Next Page Button:', nextPageBtn);
    console.log('Current Page Span:', currentPageSpan);


    // Biến lưu trữ trạng thái phân trang
    let currentPage = 1;
    let pageSize = parseInt(selectPageSize.value);

    // Tổng số trang (sẽ được cập nhật từ server)
    let totalPages = 1;

    // Hàm để loại bỏ lớp 'active' khỏi tất cả các tab
    function removeActiveClasses() {
        tabs.forEach(function (tab) {
            tab.classList.remove('active');
        });
    }



    // Hàm để chuyển đổi định dạng ngày và thêm phần thời gian
    function formatDateWithTime(dateStr, timeStr) {
        const dateParts = dateStr.split('-'); // [YYYY, MM, DD]
        const day = dateParts[2];
        const month = dateParts[1];
        const year = dateParts[0];
        // Chuyển đổi thời gian từ 'HH:MM:SS' sang 'HH:MM'
        const timeParts = timeStr.split(':'); // [HH, MM, SS]
        const hours = timeParts[0];
        const minutes = timeParts[1];
        return `${day}/${month}/${year} ${hours}:${minutes}`;
    }

    function formatDateWithTimeppp(dateStr, time = '') {
        const date = new Date(dateStr);
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng từ 0-11, nên cần +1
        const day = String(date.getDate()).padStart(2, '0');
        const year = date.getFullYear();
        return time ? `${month}/${day}/${year} ${time}` : `${month}/${day}/${year}`;
    }

    // Hàm để tạo các hàng trong bảng từ dữ liệu coupon
    function buildTableRows(coupons) {
        // Xóa nội dung hiện tại của tbody
        couponTableBody.innerHTML = '';

        if (coupons.length === 0) {
            // Nếu không có dữ liệu, hiển thị thông báo
            const noDataRow = document.createElement('tr');
            const noDataCell = document.createElement('td');
            noDataCell.colSpan = 5;
            noDataCell.textContent = 'Không có dữ liệu coupon để hiển thị.';
            noDataRow.appendChild(noDataCell);
            couponTableBody.appendChild(noDataRow);
            return;
        }

        // Tạo các hàng từ dữ liệu coupon
        coupons.forEach(function (coupon) {
            const row = document.createElement('tr');
            row.setAttribute('id-coupon', coupon.id);
            row.style.cursor = 'pointer';
            row.addEventListener('click', function () {
                openUrl(coupon.id);
            });

            // Cột Khuyến mại
            const promoCell = document.createElement('td');
            const couponCodeDiv = document.createElement('div');
            couponCodeDiv.className = 'coupon-code';
            const couponCodeSpan = document.createElement('span');
            couponCodeSpan.textContent = coupon.couponCode;
            couponCodeDiv.appendChild(couponCodeSpan);
            promoCell.appendChild(couponCodeDiv);

            const discountValueDiv = document.createElement('div');
            discountValueDiv.className = 'coupon-discount-value';
            const discountValueSpan = document.createElement('span');

            if (coupon.discountType === 'FIXED') {
                discountValueSpan.textContent = `Giảm ${formatCurrency(coupon.discountValueFixed)} cho toàn bộ đơn hàng`;
            } else if (coupon.discountType === 'PERCENTAGE') {
                discountValueSpan.textContent = `Giảm ${coupon.discountValuePercent}% tối đa ${formatCurrency(coupon.maximumReduction)}`;
            }
            discountValueDiv.appendChild(discountValueSpan);
            promoCell.appendChild(discountValueDiv);

            row.appendChild(promoCell);

            // Cột Trạng thái
            const statusCell = document.createElement('td');
            const statusSpan = document.createElement('span');
            if (coupon.status === true) {
                statusSpan.className = 'css-gr2olx';
                statusSpan.textContent = 'Đang hoạt động';
            } else {
                statusSpan.className = 'css-3u37zr';
                statusSpan.textContent = 'Ngừng áp dụng';
            }
            statusCell.appendChild(statusSpan);
            row.appendChild(statusCell);

            // Cột Đã dùng
            // const usedCell = document.createElement('td');
            // usedCell.innerHTML = `<span>${coupon.usageLimit}</span>`;
            // row.appendChild(usedCell);

            // Cột Ngày bắt đầu
            const startDateCell = document.createElement('td');
            startDateCell.innerHTML = `<span>${formatDate(coupon.startDate)}</span>`;
            row.appendChild(startDateCell);

            // Cột Ngày kết thúc
            const endDateCell = document.createElement('td');
            endDateCell.innerHTML = `<span>${formatDate(coupon.endDate)}</span>`;
            row.appendChild(endDateCell);

            // Thêm hàng vào tbody
            couponTableBody.appendChild(row);
        });
    }

    // Hàm để định dạng ngày từ định dạng DD/MM/YYYY HH:MM
    function formatDate(dateString) {
        return dateString; // Giữ nguyên định dạng đã được chuyển đổi trước đó
    }

    // Hàm để định dạng số thành dạng tiền tệ
    function formatCurrency(amount) {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
    }

    // Hàm để gửi yêu cầu và xử lý phản hồi
    function fetchCoupons(url) {
        // Hiển thị thông báo đang tải
        couponTableBody.innerHTML = '<tr><td colspan="5">Đang tải dữ liệu...</td></tr>';

        fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include' // Nếu cần gửi cookie
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json(); // Giả sử server trả về JSON
            })
            .then(data => {
                // Xử lý dữ liệu và hiển thị
                // Giả sử data.content là một mảng các đối tượng coupon và data.totalPages là tổng số trang
                console.log(data);
                buildTableRows(data.result);
                totalPages = data.meta.total;
                currentPage = data.meta.page;
                updatePaginationControls();
            })
            .catch(error => {
                console.error('Có lỗi xảy ra:', error);
                couponTableBody.innerHTML = '<tr><td colspan="5">Đã xảy ra lỗi khi tải dữ liệu.</td></tr>';
            });
    }

    // Hàm để mở URL khi click vào hàng trong bảng
    function openUrl(couponId) {
        // Ví dụ: chuyển hướng đến trang chi tiết coupon
        window.location.href = `/admin/coupon/${couponId}/edit`;
    }

    // Hàm debounce để trì hoãn việc thực thi hàm handleFilterChange
    function debounce(func, delay) {
        let timeout;
        return function () {
            const context = this;
            const args = arguments;
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(context, args), delay);
        };
    }

    // Hàm để xử lý khi bộ lọc ngày hoặc tìm kiếm thay đổi
    function handleFilterChange() {
        currentPage = 1; // Reset về trang đầu tiên khi bộ lọc thay đổi
        const url = buildRequestUrl();
        fetchCoupons(url);
    }

    // Hàm để lấy giá trị các bộ lọc và xây dựng URL
    function buildRequestUrl() {
        // Xác định tab hiện tại được chọn
        let activeTab = tabs.find(tab => tab.classList.contains('active'));
        let url = '/admin/coupon/search';

        // Lấy giá trị từ ô tìm kiếm
        const searchValue = inputSearchCouponCode.value.trim();

        // Lấy giá trị ngày bắt đầu và ngày kết thúc
        const startDateInput = startDateFilter.value;
        const endDateInput = endDateFilter.value;

        // Chuyển đổi định dạng ngày và thêm phần thời gian
        const startDate = startDateInput ? formatDateWithTimeppp(startDateInput, '00:00:00') : null;
        const endDate = endDateInput ? formatDateWithTimeppp(endDateInput, '23:59:59') : null;

        // Debug: Kiểm tra giá trị ngày và tìm kiếm
        console.log('Start Date:', startDate);
        console.log('End Date:', endDate);
        console.log('Search Value:', searchValue);
        console.log('Current Page:', currentPage);
        console.log('Page Size:', pageSize);

        // Thêm các tham số vào URL
        const params = new URLSearchParams();

        // Thêm các điều kiện lọc
        if (startDate) {
            params.append('filter', `startDate>: '${startDate}'`);

        }

        if (endDate) {
            params.append('filter', `endDate<: '${endDate}'`);
        }

        if (searchValue) {
            params.append('filter', encodeURIComponent(`couponCode~~'${searchValue}'`));
        }

        // Thêm tham số phân trang
        params.append('page', currentPage); // Pageable bắt đầu từ 0
        params.append('size', pageSize);

        // Thêm tham số status dựa trên tab hiện tại
        if (activeTab) {
            if (activeTab.id === 'fetchCouponStillActive') {
                params.append('filter', `status:${encodeURIComponent('true')}`);
            } else if (activeTab.id === 'fetchCouponStopWorking') {
                params.append('filter', `status:${encodeURIComponent('false')}`);
            }
            // Nếu là 'fetchAllCoupon', không thêm tham số status
        }

        // Xây dựng URL với các tham số
        if ([...params].length > 0) {
            url += '?' + params.toString();
        }

        return url;
    }


    // Hàm xử lý khi nhấp vào tab
    function handleTabClick(tab) {
        tab.addEventListener('click', function () {
            // Nếu tab đã được chọn, không làm gì cả
            if (this.classList.contains('active')) {
                return;
            }

            // Loại bỏ lớp 'active' khỏi tất cả các tab
            removeActiveClasses();

            // Thêm lớp 'active' vào tab được nhấp
            this.classList.add('active');

            // Xây dựng URL và gửi yêu cầu đến server
            const url = buildRequestUrl();
            fetchCoupons(url);
        });
    }

    // Thêm sự kiện click cho từng tab
    tabs.forEach(function (tab) {
        handleTabClick(tab);
    });

    // Hàm để cập nhật trạng thái các nút điều hướng trang
    function updatePaginationControls() {
        currentPageSpan.textContent = currentPage;

        // Vô hiệu hóa nút Trước nếu ở trang đầu
        if (currentPage <= 1) {
            prevPageBtn.disabled = true;
        } else {
            prevPageBtn.disabled = false;
        }

        // Vô hiệu hóa nút Tiếp theo nếu ở trang cuối
        if (currentPage >= totalPages) {
            nextPageBtn.disabled = true;
        } else {
            nextPageBtn.disabled = false;
        }
    }

    // Hàm để xử lý khi nhấp vào nút Trước
    prevPageBtn.addEventListener('click', function () {
        console.log('cao đfdfdd');
        if (currentPage > 1) {
            currentPage--;
            const url = buildRequestUrl();
            fetchCoupons(url);
        }
    });

    // Hàm để xử lý khi nhấp vào nút Tiếp theo
    nextPageBtn.addEventListener('click', function () {

        if (currentPage < totalPages) {
            currentPage++;
            const url = buildRequestUrl();
            fetchCoupons(url);
            console.log(currentPage, 'sdfdsfsdfsfdsdfffffffffffffffffffffffffffffffffffffffffffffff');
        }
    });

    // Hàm để xử lý khi chọn kích thước trang thay đổi
    selectPageSize.addEventListener('change', function () {
        pageSize = parseInt(this.value);
        currentPage = 1; // Reset về trang đầu tiên khi kích thước trang thay đổi
        const url = buildRequestUrl();
        fetchCoupons(url);
    });



    // Hàm để xử lý khi bộ lọc ngày hoặc tìm kiếm thay đổi
    function handleFilterChange() {
        currentPage = 1; // Reset về trang đầu tiên khi bộ lọc thay đổi
        const url = buildRequestUrl();
        fetchCoupons(url);
    }

    // Sử dụng debounce cho ô tìm kiếm (trì hoãn 500ms)
    const handleSearchInput = debounce(function () {
        handleFilterChange();
    }, 500);


    // Thêm sự kiện thay đổi cho các trường ngày
    startDateFilter.addEventListener('change', handleFilterChange);
    endDateFilter.addEventListener('change', handleFilterChange);

    // Thêm sự kiện nhập cho ô tìm kiếm mã khuyến mãi
    inputSearchCouponCode.addEventListener('input', handleSearchInput);

    // Hàm để mở URL khi click vào hàng trong bảng
    function openUrl(couponId) {
        // Ví dụ: chuyển hướng đến trang chi tiết coupon
        window.location.href = `/admin/coupon/${couponId}/edit`;
    }

    // Tự động tải dữ liệu cho tab mặc định khi tải trang
    fetchCoupons(buildRequestUrl());
});
