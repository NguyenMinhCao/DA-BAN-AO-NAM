document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('searchInput');
    const tabContents = document.querySelectorAll('.tab-content');
    // const selectPageSize = document.getElementById('selectPageSize');
    // var quantityDisplay = document.querySelector('.page-display');


    // Hàm thêm event listeners cho các tab
    function initializeTabs() {
        const tabs = ['tab1', 'tab2', 'tab3', 'tab4', 'tab5', 'tab6', 'tab7'];

        tabs.forEach(tabId => {
            const tabButton = document.getElementById(`${tabId}-btn`);
            if (tabButton) {
                tabButton.addEventListener('click', () => {
                    openTab(tabId);
                });
            } else {
                console.error(`Element với id "${tabId}-btn" không tồn tại.`);
            }
        });
    }

    // Hàm mở tab và gọi performSearch với tabId được chọn
    window.openTab = function (tabId) {
        const tabContents = document.querySelectorAll('.tab-content');
        const activeTab = document.getElementById(tabId);
        const tabButtons = document.getElementsByClassName('css-1hfoem2');
        const tabBtn = document.getElementById(`${tabId}-btn`);

        if (!activeTab || !tabBtn) {
            console.error(`Tab hoặc nút tab với id "${tabId}" không tồn tại.`);
            return;
        }

        for (i = 0; i < tabButtons.length; i++) {
            tabButtons[i].style.borderBottom = "none";
            tabButtons[i].style.color = "rgb(116, 124, 135)";
        }

        tabBtn.style.borderBottom = '2px #4CAF50 solid';
        tabBtn.style.color = '#4CAF50';

        // Loại bỏ style active khỏi tất cả các tab
        Array.from(tabButtons).forEach(button => {
            button.classList.remove('tab-active');
            button.classList.add('tab-inactive');
        });

        // Thêm style active cho tab được chọn
        tabBtn.classList.add('tab-active');
        tabBtn.classList.remove('tab-inactive');

        // Ẩn tất cả các nội dung tab
        tabContents.forEach(tabContent => {
            tabContent.style.display = 'none';
        });

        // Hiển thị nội dung của tab được chọn
        activeTab.style.display = 'table-row-group';

        // Gọi performSearch với tabId được chọn và reset page về 1
        const size = getCurrentSize();
        const page = getCurrentPage();
        performSearch(tabId, page, size);
    }

    // Hàm mở URL chi tiết đơn hàng


    // Hàm lấy giá trị size từ selectPageSize
    function getCurrentSize() {
        const selectPageSize = document.getElementById('selectPageSize');
        return selectPageSize ? selectPageSize.value : 10; // Mặc định size là 10 nếu không tìm thấy
    }

    // Hàm lấy giá trị page từ quantityDisplay
    function getCurrentPage() {
        const quantityDisplay = document.querySelector('.quantity-display');
        return quantityDisplay ? parseInt(quantityDisplay.textContent, 10) || 1 : 1;
    }

    // Hàm lấy tab đang active
    function getActiveTab() {
        const activeButton = document.querySelector('.css-1hfoem2.tab-active');
        if (activeButton) {
            const tabId = activeButton.id.replace('-btn', '');
            return tabId;
        }
        return null;
    }

    window.performSearch = function (selectedTab = null, page = 1, size = 10) {
        const startDateInput = document.getElementById('start-date').value;
        const endDateInput = document.getElementById('end-date').value;

        // Chuyển đổi định dạng ngày
        const startDate = startDateInput ? formatDateWithTime(startDateInput, '00:00:00') : null;
        const endDate = endDateInput ? formatDateWithTime(endDateInput, '23:59:59') : null;

        // Kiểm tra tính hợp lệ của ngày
        if (startDate && endDate) {
            const start = new Date(startDate);
            const end = new Date(endDate);
            if (start > end) {
                toast({
                    title: "Thất bại!",
                    message: "Ngày bắt đầu không được lớn hơn ngày kết thúc.",
                    type: "error",
                    duration: 1700
                });
                return;
            }
        }

        let filters = [];

        if (startDate && endDate) {
            // Sử dụng toán tử >= và <= để bao gồm cả ngày bắt đầu và ngày kết thúc
            filters.push(`createdAt >: '${startDate}' and createdAt <: '${endDate}'`);
        } else if (startDate) {
            filters.push(`createdAt >: '${startDate}'`);
        } else if (endDate) {
            filters.push(`createdAt <: '${endDate}'`);
        }

        // Thêm điều kiện filter dựa trên tab được chọn
        if (selectedTab) {
            let tabFilter = '';
            switch (selectedTab) {
                case 'tab2':
                    tabFilter = `deliveryStatus:'PENDING' and orderStatus!'CANCELED'`;
                    break;
                case 'tab3':
                    tabFilter = `deliveryStatus:'DELIVERY' and orderStatus!'CANCELED'`;
                    break;
                case 'tab4':
                    tabFilter = `deliveryStatus:'COMPLETED' and orderStatus!'CANCELED'`;
                    break;
                case 'tab5':
                    tabFilter = `orderStatus:'CANCELED'`;
                    break;
                case 'tab6':
                    tabFilter = `paymentStatus:'PENDING' and orderStatus!'CANCELED'`;
                    break;
                case 'tab7':
                    tabFilter = `paymentStatus:'PARTIALREFUND' and orderStatus!'CANCELED'`;
                    break;
                case 'tab1':
                    // Tab "Tất cả" không thêm filter nào hoặc có thể thêm filter cơ bản
                    break;
                default:
                    console.warn(`Không có filter cho tab "${selectedTab}".`);
            }

            if (tabFilter) {
                filters.push(tabFilter);
            }
        }

        // Kết hợp các điều kiện bằng toán tử 'and'
        let filterQuery = filters.join(' and ');

        // Xây dựng URL truy vấn
        let queryUrl = `http://localhost:8080/admin/orders/search?page=${page}&size=${size}&sort=createdAt,asc`;

        if (filterQuery) {
            const encodedFilter = encodeURIComponent(filterQuery);
            queryUrl += `&filter=${encodedFilter}`;
        }

        console.log(`Truy vấn: ${queryUrl}`); // Hiển thị truy vấn để kiểm tra

        // Gửi yêu cầu HTTP và xử lý kết quả
        fetch(queryUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                // Xử lý dữ liệu trả về
                console.log(data);
                updateTable(data);
            })
            .catch(error => {
                console.error('Error:', error);
                // Hiển thị thông báo lỗi (tuỳ chọn)
                toast({
                    title: "Lỗi!",
                    message: error.message,
                    type: "error",
                    duration: 2000
                });
            });
    }

    // Hàm khởi tạo khi trang được tải
    window.onload = function () {
        openTab('tab1'); // Mở tab1 khi trang tải
    }

    function formatDateWithTime(dateStr, time = '') {
        const date = new Date(dateStr);
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng từ 0-11, nên cần +1
        const day = String(date.getDate()).padStart(2, '0');
        const year = date.getFullYear();
        return time ? `${month}/${day}/${year} ${time}` : `${month}/${day}/${year}`;
    }


    // Thêm sự kiện lắng nghe cho cả hai trường ngày
    document.getElementById('start-date').addEventListener('change', performSearch);
    document.getElementById('end-date').addEventListener('change', performSearch);


    // Hàm để gửi yêu cầu tìm kiếm
    function fetchSearchResults(query) {
        // Sử dụng Fetch API để gửi yêu cầu GET tới server
        fetch(`/admin/orders/search?filter=id:${encodeURIComponent(query)}`)
            .then(response => {
                if (!response.ok) {
                    // Kiểm tra nếu mã trạng thái là 400
                    if (response.status === 400) {
                        throw new Error('Bad Request: Vui lòng kiểm tra lại truy vấn tìm kiếm.');
                    }
                    // Kiểm tra các lỗi HTTP khác nếu cần
                    throw new Error(`Error: ${response.status} ${response.statusText}`);
                }
                return response.json();
            })
            .then(data => {
                // Xử lý dữ liệu trả về và cập nhật bảng
                console.log(data);
                updateTable(data);
            })
            .catch(error => {
                // Xử lý các lỗi mạng hoặc lỗi đã ném ở trên
                console.log('Error fetching search results:', error.message);
                // Bạn có thể hiển thị thông báo lỗi cho người dùng ở đây
                // Ví dụ: showErrorToUser(error.message);
            });
    }


    function updateTable(orders) {
        // Xóa tất cả các hàng hiện tại trong các tab
        tabContents.forEach(tab => {
            tab.innerHTML = '';
        });

        // Lặp qua danh sách đơn hàng và thêm vào các tab tương ứng
        orders.result.forEach(order => {
            const paymentStatus = order.paymentStatus;
            const orderStatus = order.orderStatus;
            const deliveryStatus = order.deliveryStatus;

            // Chuẩn hóa giá trị để so sánh
            const normalizedPaymentStatus = paymentStatus.trim().toUpperCase();
            const normalizedOrderStatus = orderStatus.trim().toUpperCase();
            const normalizedDeliveryStatus = deliveryStatus.trim().toUpperCase();

            console.log(`Processing Order ID: ${order.id}`);
            console.log(`Normalized deliveryStatus: "${normalizedDeliveryStatus}"`);
            console.log(`Normalized orderStatus: "${normalizedOrderStatus}"`);
            console.log(`Normalized paymentStatus: "${normalizedPaymentStatus}"`);

            const targetTabs = []; // Mảng để lưu trữ tất cả các tab mục tiêu cho đơn hàng này

            // Xác định các tab tương ứng dựa trên các trạng thái
            if (normalizedDeliveryStatus === 'DELIVERY') {
                const tab3 = document.getElementById('tab3');
                if (tab3) {
                    targetTabs.push(tab3);
                    console.log(`Order ID ${order.id} added to tab3`);
                } else {
                    console.error('Element with id "tab3" not found.');
                }
            } else if (normalizedDeliveryStatus === 'PENDING') {
                const tab2 = document.getElementById('tab2');
                if (tab2) {
                    targetTabs.push(tab2);
                    console.log(`Order ID ${order.id} added to tab2`);
                } else {
                    console.error('Element with id "tab2" not found.');
                }
            } else if (normalizedDeliveryStatus === 'COMPLETED') {
                const tab4 = document.getElementById('tab4');
                if (tab4) {
                    targetTabs.push(tab4);
                    console.log(`Order ID ${order.id} added to tab4`);
                } else {
                    console.error('Element with id "tab4" not found.');
                }
            }

            if (normalizedOrderStatus === 'CANCELED') {
                const tab5 = document.getElementById('tab5');
                if (tab5) {
                    targetTabs.push(tab5);
                    console.log(`Order ID ${order.id} added to tab5`);
                } else {
                    console.error('Element with id "tab5" not found.');
                }
            }

            if (normalizedPaymentStatus === 'PENDING') {
                const tab6 = document.getElementById('tab6');
                if (tab6) {
                    targetTabs.push(tab6);
                    console.log(`Order ID ${order.id} added to tab6`);
                } else {
                    console.error('Element with id "tab6" not found.');
                }
            } else if (normalizedPaymentStatus === 'PARTIALREFUND') {
                const tab7 = document.getElementById('tab7');
                if (tab7) {
                    targetTabs.push(tab7);
                    console.log(`Order ID ${order.id} added to tab7`);
                } else {
                    console.error('Element with id "tab7" not found.');
                }
            }

            console.log(targetTabs + '<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>');

            const deliveryStatusClass = (normalizedDeliveryStatus !== 'PENDING')
                ? 'css-1e8zcwn'
                : 'css-2kmgkw';

            let deliveryStatusText = '';
            if (normalizedDeliveryStatus === 'PENDING') {
                deliveryStatusText = 'Chưa xử lý giao hàng';
            } else {
                deliveryStatusText = 'Đã xử lý giao hàng';
            }

            const paymentStatusClass = (normalizedPaymentStatus !== 'PENDING')
                ? 'css-1e8zcwn'
                : 'css-2kmgkw';

            let paymentStatusText = '';
            if (normalizedPaymentStatus === 'PENDING') {
                paymentStatusText = 'Chưa thanh toán';
            } else if (normalizedPaymentStatus === 'COMPLETED') {
                paymentStatusText = 'Đã thanh toán';
            } else if (normalizedPaymentStatus === 'REFUNDED') {
                paymentStatusText = 'Đã hoàn tiền';
            } else {
                paymentStatusText = 'Đã hoàn tiền một phần';
            }

            // Tạo dòng bảng mới
            const row = document.createElement('tr');
            row.classList.add('css-1n5r022');
            row.setAttribute('onclick', `openUrl('${order.id}')`);

            const formattedAmount = formatVND(order.totalAmount);

            // Tạo và thêm các ô vào dòng bảng
            row.innerHTML = `                                 
                <td class="css-166t3yd e160qq0x2">
                    <div class="css-1xdhyk6 e1f1ig8p0">
                        <a class="css-1w5iufu ehy6p5f0" href="/admin/orders/${order.id}">
                            <div style="display: flex;">
                                <span class="css-dbt8sz e1nigx955"><span class="css-1qvvvsu enzfz0r0">#</span></span>
                                <span class="css-dbt8sz e1nigx955"><span class="css-1qvvvsu enzfz0r0">${order.id}</span></span>
                            </div>
                        </a>
                    </div>
                </td>
                <td class="css-166t3yd e160qq0x2"><span class="css-dbt8sz e1nigx955">${order.createAt}</span></td>
                <td class="css-166t3yd e160qq0x2">
                    <span class="css-dbt8sz e1nigx955">
                        <span class="css-1qvvvsu enzfz0r0">
                            <div style="width: 125px;">
                                <span class="css-ii5m0c e7ltd9p0">${order.fullName}</span>
                            </div>
                        </span>
                    </span>
                </td>
                <td class="css-166t3yd e160qq0x2">
                    <span class="css-dbt8sz e1nigx955">
                        <span class="css-1qvvvsu enzfz0r0">
                            <div style="width: 125px;">
                                <span class="css-ii5m0c e7ltd9p0">${order.orderSource}</span>
                            </div>
                        </span>
                    </span>
                </td>
                <td class="css-166t3yd e160qq0x2">
                    <div class="css-4fkh5d e1ehoxn20">
                        <div class="css-thtl67 e14jmgg0">
                            <span class="css-dbt8sz e1nigx955">
                                <span class="css-1qvvvsu enzfz0r0">${formattedAmount} 
                                </span>
                            </span>
                        </div>
                    </div>
                </td>
                <td class="css-166t3yd e160qq0x2">
                    <div class="css-k9p4ua e1ehoxn20">
                        <div class="css-thtl67 e14jmgg0">
                            <span class="${paymentStatusClass} e8ptwd0">
                                <span class="css-z8vxi5 enzfz0r0">${paymentStatusText}</span>
                            </span>
                        </div>
                    </div>
                </td>
                <td class="css-166t3yd e160qq0x2">
                    <div class="css-k9p4ua e1ehoxn20">
                        <div class="css-thtl67 e14jmgg0">
                            <span class="${deliveryStatusClass} e8ptwd0">
                                <span class="css-z8vxi5 enzfz0r0">${deliveryStatusText}</span>
                            </span>
                        </div>
                    </div>
                </td>
            `;

            // Thêm dòng vào tab "Tất cả"
            const targetTabAll = document.getElementById('tab1');
            if (targetTabAll) {
                targetTabAll.appendChild(row.cloneNode(true)); // Clone để thêm vào nhiều tab
            } else {
                console.error('Element with id "tab1" not found.');
            }

            // Thêm dòng vào từng tab tương ứng
            targetTabs.forEach(tab => {
                tab.appendChild(row.cloneNode(true));
            });
        });
        var dispalyDetailPage = document.getElementById("dispaly-detail-page");
        if (dispalyDetailPage) {
            dispalyDetailPage.textContent = `Từ ${orders.meta.page} đến ${orders.meta.currentPageElements} trên tổng ${orders.meta.total}`;
        } else {
            console.log("Không tìm thấy phần tử với ID 'dispaly-detail-page'.");
        }
    }



    // formatVND
    function formatVND(amount) {
        return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + ' đ';
    }

    // Debounce để hạn chế số lượng yêu cầu gửi đi khi người dùng nhập nhanh
    function debounce(func, delay) {
        let timeout;
        return function (...args) {
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(this, args), delay);
        };
    }

    // Xử lý sự kiện nhập liệu với debounce 300ms
    searchInput.addEventListener('input', debounce(function (event) {
        const query = event.target.value.trim();
        if (query.length > 0) {
            fetchSearchResults(query);
        } else {
            // Nếu ô tìm kiếm trống, tải lại dữ liệu gốc hoặc làm gì đó phù hợp
            location.reload(); // Hoặc bạn có thể tái tải dữ liệu gốc bằng AJAX
        }
    }, 300));


    // Hàm thêm event listeners cho các điều khiển phân trang
    function initializePagination() {
        const pageControls = document.querySelectorAll('.css-18c5rtc.e1lvcblw0');

        pageControls.forEach(control => {
            const decreaseBtn = control.querySelector('.decrease-btn');
            const increaseBtn = control.querySelector('.increase-btn');
            const pageDisplay = control.querySelector('.quantity-display'); // Sử dụng 'quantity-display' theo mã của bạn

            if (!pageDisplay) {
                console.error('Element với class "quantity-display" không tồn tại.');
                return;
            }

            // Hàm cập nhật số lượng trang
            const updatePage = (change) => {
                let currentPage = parseInt(pageDisplay.textContent, 10);
                currentPage += change;

                // Đảm bảo số lượng không nhỏ hơn 1
                if (currentPage < 1) {
                    currentPage = 1;
                }

                pageDisplay.textContent = currentPage;
                return currentPage;
            };

            // Thêm sự kiện cho nút giảm
            decreaseBtn.addEventListener('click', () => {
                const newPage = updatePage(-1);
                const selectedTab = getActiveTab();
                const size = getCurrentSize();
                performSearch(selectedTab, newPage, size);
            });

            // Thêm sự kiện cho nút tăng
            increaseBtn.addEventListener('click', () => {
                const newPage = updatePage(1);
                const selectedTab = getActiveTab();
                const size = getCurrentSize();
                performSearch(selectedTab, newPage, size);
            });
        });
    }

    // Hàm thêm event listeners cho selectPageSize
    function initializeSizeSelection() {
        const selectPageSize = document.getElementById('selectPageSize');
        if (selectPageSize) {
            selectPageSize.addEventListener('change', () => {
                const selectedSize = selectPageSize.value;
                const selectedTab = getActiveTab();
                performSearch(selectedTab, 1, selectedSize); // Reset page về 1 khi thay đổi size
            });
        } else {
            console.error('Element với id "selectPageSize" không tồn tại.');
        }
    }



    window.onload = function () {
        initializeTabs(); // Thêm event listeners cho các tab
        initializePagination(); // Thêm event listeners cho phân trang
        initializeSizeSelection(); // Thêm event listeners cho selectPageSize
        openTab('tab1'); // Mở tab1 khi trang tải
    }
});

