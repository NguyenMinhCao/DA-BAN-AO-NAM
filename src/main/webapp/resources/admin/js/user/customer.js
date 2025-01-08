$(document).ready(function () {
    const tokenGHN = '39546c64-c9e1-11ef-a7a0-fe411cc1f02739546c64-c9e1-11ef-a7a0-fe411cc1f027'

    fetchCustomer(0)
    function fetchCustomer(page) {
        let search = $('#search').val()
        $.ajax({
            url: `http://localhost:8080/api/admin/user/get/customers?page=${page}&limit=10`,
            type: 'GET',
            data: {keyword: search},
            success: function (response) {
                renderCustomer(response.content);
                renderPagination(response.totalPages, response.number + 1, 'pagination-customer');
            },
            error: function (error) {
                console.log("Error:", error);
            }
        });
    }

    function renderCustomer(data) {
        $('#tableSample tbody').empty()
        if (data.length > 0) {
            data.forEach(data => {
                $('#tableSample tbody').append(
                    `
                    <tr>
                        <td><img src="/images/avatar/anhmau.jpg" height="100px" width="auto" alt="lỗi ảnh"></td>
                        <td>${data.fullName}</td>
                        <td>${data.phoneNumber}</td>
                        <td>${data.email}</td>
                        <td>${data.status}</td>
                        <td>
                            <button>
                                <i class="fa-solid fa-location-dot"></i>
                            </button>
                            <button>
                                <i class="fa-solid fa-wrench"></i>
                            </button>
                            <button>
                                <i class="fa-solid fa-circle-info"></i>
                            </button>
                        </td> 
                    </tr>
                         
                 `
                )
            })
        }
    }

    function renderPagination(totalPages, currentPage, idTable) {
        const paginationContainer = document.getElementById(idTable);
        paginationContainer.innerHTML = '';

        const createPageItem = (text, isActive = false, isDisabled = false, page) => {
            const pageItem = document.createElement('div');
            pageItem.className = `page-item${isActive ? ' active' : ''}${isDisabled ? ' disabled' : ''}`;
            pageItem.textContent = text;
            if (!isDisabled) {
                pageItem.onclick = () => {
                    fetchCustomer(page - 1)
                    renderPagination(totalPages, page, idTable);
                };
            }

            return pageItem;
        };
        if (currentPage > 1) {
            paginationContainer.appendChild(createPageItem('Prev', false, false, currentPage - 1));
        } else {
            paginationContainer.appendChild(createPageItem('Prev', false, true));
        }
        const startPage = Math.max(1, currentPage - 1);
        const endPage = Math.min(totalPages, currentPage + 1);

        if (startPage > 1) {
            paginationContainer.appendChild(createPageItem(1, false, false, 1));
            if (startPage > 2) {
                paginationContainer.appendChild(createPageItem('...', false, true));
            }
        }
        for (let i = startPage; i <= endPage; i++) {
            paginationContainer.appendChild(createPageItem(i, i === currentPage, false, i));
        }
        if (endPage < totalPages) {
            if (endPage < totalPages - 1) {
                paginationContainer.appendChild(createPageItem('...', false, true));
            }
            paginationContainer.appendChild(createPageItem(totalPages, false, false, totalPages));
        }
        if (currentPage < totalPages) {
            paginationContainer.appendChild(createPageItem('Next', false, false, currentPage + 1));
        } else {
            paginationContainer.appendChild(createPageItem('Next', false, true));
        }
    }
    $('#btnSearch').on('click', function(){
        fetchCustomer(0)
    })
    $('#btnNewCustomer').on('click', function(){
        window.location.href = "/admin/customer/create";
    })

    //******************** add khách hàng
    function addCustomer(){
        let name = $('')
    }
});