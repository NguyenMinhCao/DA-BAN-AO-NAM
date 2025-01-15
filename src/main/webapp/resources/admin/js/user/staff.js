$(document).ready(function () {
    let listStaff = []
    fetchStaff(0, null)

    $('#btnNewStaff').on('click', function(){
        window.location.href = "/admin/staff/create";
    })
    $('#btnSearch').on('click', function(){
       fetchStaff(0)
    })
    function fetchStaff(page, status) {
        let search = $('#search').val()
        $.ajax({
            url: `/api/admin/user/get/staffs?page=${page}&limit=10`,
            type: 'GET',
            data: {keyword: search, status: status},
            success: function (response) {
                listStaff = []
                response.content.forEach(item => listStaff.push(item))
                renderStaff(response.content);
            },
            error: function (error) {
                console.log("Error:", error);
            }
        });
    }

    function renderStaff(data) {
        $('#tableSample tbody').empty()
        if (data.length > 0) {
            data.forEach(data => {
                const newRow = document.createElement("tr");
                newRow.innerHTML =
                    `
                    <tr>
                        <td><img src="/images/avatar/${data.avatar}" height="100px" width="auto" alt="lỗi ảnh"></td>
                        <td>${data.fullName}</td>
                        <td>${data.phoneNumber}</td>
                        <td>${data.email}</td>
                        <td>
                            <p class="${data.status ? 'status-active' : 'status-inactive'}" >${data.status ? "Hoạt động" : "Không hoạt động"}</p>
                        </td>
                        <td class="table-button">
                            <div style="display: flex">                     
                                <button data-id="${data.id}" class="btn btn-info" title="Thông tin chi tiết">
                                    <i class="fa-solid fa-circle-info"></i>
                                </button>
                                <div class="form-check form-switch" style="margin: 12px 0px 0px 3px">
                                    <input class="form-check-input" data-id="${data.id}" type="checkbox" role="switch" id="checkBoxStatus" ${data.status ? "checked" : ""}>
                                </div>
                            </div>                         
                        </td> 
                    </tr>
                         
                 `
                $('#tableSample tbody').append(newRow)

                newRow.querySelector(".btn-info").addEventListener("click", function(e) {
                    const userId = e.currentTarget.getAttribute('data-id')
                    console.log(userId + ": id của user được chọn")
                });
                newRow.querySelector(".form-check-input").addEventListener("change", function(e) {
                    const isChecked = $(this).is(':checked');
                    const customerId = $(this).data('id'); // Lấy ID khách hàng từ thuộc tính data-id
                    console.log(customerId + ": id của khách hangd")
                    if (!isChecked) {
                        Swal.fire({
                            title: "Cảnh báo?",
                            text: "Bạn chắc chắn muốn thay đổi chứ!",
                            icon: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#3085d6",
                            cancelButtonColor: "#d33",
                            confirmButtonText: "Đồng ý",
                            cancelButtonText: "Hủy",
                        }).then((result) => {
                            if (result.isConfirmed) {
                            }else{
                                $(this).prop('checked', true);
                                return
                            }
                        });
                    }else{
                        Swal.fire({
                            title: "Cảnh báo?",
                            text: "Bạn chắc chắn muốn thay đổi chứ!",
                            icon: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#3085d6",
                            cancelButtonColor: "#d33",
                            confirmButtonText: "Đồng ý",
                            cancelButtonText: "Hủy",
                        }).then((result) => {
                            if (result.isConfirmed) {
                            }else{
                                $(this).prop('checked', false);
                                return
                            }
                        });
                    }
                });
            });
        }
    }
})