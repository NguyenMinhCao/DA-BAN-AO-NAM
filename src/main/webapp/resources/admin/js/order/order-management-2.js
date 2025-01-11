document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('searchInput');
    const tabContents = document.querySelectorAll('.tab-content');


    function performSearch() {
        const startDateInput = document.getElementById('start-date').value;
        const endDateInput = document.getElementById('end-date').value;
        const page = 1; // Có thể tùy chỉnh hoặc lấy từ input
        const size = 10; // Có thể tùy chỉnh hoặc lấy từ input
    
        // Chuyển đổi định dạng ngày
        const startDate = startDateInput ? formatDateWithTime(startDateInput, '00:00:00') : null;
        const endDate = endDateInput ? formatDateWithTime(endDateInput, '23:59:59') : null;
    
        // Kiểm tra tính hợp lệ của ngày
        if (startDate && endDate) {
            // So sánh ngày bằng cách chuyển đổi về đối tượng Date
            const start = new Date(startDate);
            const end = new Date(endDate);
            if (start > end) {
                toast({
                    title: "Thất bại!",
                    message: "Ngày bắt đầu không lớn hơn ngày kết thúc.",
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
    
        // Kết hợp các điều kiện bằng toán tử 'and'
        let filterQuery = filters.join(' and ');
    
        // Xây dựng URL truy vấn
        let queryUrl = `http://localhost:8080/admin/orders/search?page=${page}&size=${size}`;
    
        if (filterQuery) {
            // URL-encode câu truy vấn để đảm bảo tính hợp lệ của URL
            const encodedFilter = encodeURIComponent(filterQuery);
            queryUrl += `&filter=${encodedFilter}`;
        }
    
        // Hiển thị truy vấn (tuỳ chọn)
    
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
            let targetTabAll = document.getElementById('tab1');
            let targetTab = null;

            if (deliveryStatus === 'PENDING') {
                targetTab = document.getElementById('tab2');
            } else if (deliveryStatus === 'DELIVERY') {
                targetTab = document.getElementById('tab3');
            } else if (deliveryStatus === 'COMPLETED') {
                targetTab = document.getElementById('tab4');
            } else if (orderStatus === 'CANCELED') {
                targetTab = document.getElementById('tab5');
            }
            else if (paymentStatus === 'PENDING') {
                targetTab = document.getElementById('tab6');
            }
            else if (paymentStatus === 'PARTIALREFUND') {
                targetTab = document.getElementById('tab7');
            }

            // Tạo dòng bảng mới
            const row = document.createElement('tr');
            row.classList.add('css-1n5r022');
            row.setAttribute('onclick', `openUrl('${order.id}')`);

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
                        <div style="width: 125px;"><span class="css-ii5m0c e7ltd9p0">
                        ${order.fullName}
                    </span>
                </td>
                <td class="css-166t3yd e160qq0x2">
                    <span class="css-dbt8sz e1nigx955">
                        <div style="width: 125px;">
                            <span class="css-ii5m0c e7ltd9p0">
                                ${order.orderSource ? 'Website' : 'Tại quầy'}
                            </span>
                        </div>
                    </span>
                </td>
                <td class="css-166t3yd e160qq0x2">
                    <div class="css-4fkh5d e1ehoxn20">
                        <div class="css-thtl67 e14jmgg0">
                            <span class="css-dbt8sz e1nigx955">
                                <span class="css-1qvvvsu enzfz0r0">
                                    ${order.totalAmount} đ
                                </span>
                            </span>
                        </div>
                    </div>
                </td>
                <td class="css-166t3yd e160qq0x2">
                                                                <div class="css-k9p4ua e1ehoxn20">
                                                                    <div class="css-thtl67 e14jmgg0">
                                                                        <span class="css-2kmgkw e8ptwd0">
                                                                            <span class="css-z8vxi5 enzfz0r0">
                                                                                Chưa thanh toán
                                                                            </span>
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="css-166t3yd e160qq0x2">
                                                                <div class="css-k9p4ua e1ehoxn20">
                                                                    <div class="css-thtl67 e14jmgg0">
                                                                        <span class="css-2kmgkw e8ptwd0">
                                                                            <span class="css-z8vxi5 enzfz0r0">
                                                                                Chưa xử lý
                                                                                giao
                                                                                hàng
                                                                            </span>
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </td>
            `;

            // Thêm dòng vào tab "Tất cả"
            targetTabAll.appendChild(row.cloneNode(true)); // Clone để thêm vào nhiều tab

            // Nếu có targetTab, thêm dòng vào tab tương ứng
            if (targetTab) {
                targetTab.appendChild(row);
            }
        });

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
});

