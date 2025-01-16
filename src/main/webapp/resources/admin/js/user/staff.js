$(document).ready(function () {
    let listStaff = []
    let updateStaffModal = document.getElementById('form-add-customer')
    fetchStaff(0, null)

    $('#btnNewStaff').on('click', function(){
        window.location.href = "/admin/staff/create";
    })
    $('#btnSearch').on('click', function(){
       fetchStaff(0)
    })
    $('#cancel-btn-add-customer').on('click', function(){
        toggleModal(updateStaffModal, false)
    })
    function fetchStaff(page, status) {
        let search = $('#search').val()
        $.ajax({
            url: `/api/admin/user/get/staffs?page=${page}&limit=5`,
            type: 'GET',
            data: {keyword: search, status: status},
            success: function (response) {
                listStaff = []
                response.content.forEach(item => listStaff.push(item))
                renderStaff(response.content);
                renderPagination(response.totalPages, response.number + 1, 'pagination-customer')
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
                    toggleModal(updateStaffModal, true)
                    fuelData(userId)
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
                                updateStatus(customerId, false)
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
                                updateStatus(customerId, true)
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

    function toggleModal(modalElement, show) {
        if (show) {
            modalElement.style.display = 'flex';
        } else {
            modalElement.style.display = 'none';
        }
    }

    function fuelData(idUser){
        let fullnameCustomer = $('#fullnameCustomer')
        let emailCustomer = $('#emailCustomer')
        let phoneNumberAdd = $('#phoneNumberAdd')
        let gender = $('#gender')
        let dob = $('#dob')
        let customerUpdate = listStaff.find(user => user.id == idUser)
        setImageBackground(`/images/avatar/${customerUpdate.avatar}`);
        if(customerUpdate){
            fullnameCustomer.val(customerUpdate.fullName)
            fullnameCustomer.attr('data-UserId', idUser);
            emailCustomer.val(customerUpdate.email)
            phoneNumberAdd.val(customerUpdate.phoneNumber)
            console.log(String(customerUpdate.gender) + " giới tính")
            gender.val(String(customerUpdate.gender))
            let dateOfBirth = customerUpdate.dateOfBirth;
            console.log(dateOfBirth)
            if(dateOfBirth){
                let year = dateOfBirth[0];
                let month = dateOfBirth[1] - 1;
                let day = dateOfBirth[2] +1;
                let formattedDate = new Date(year, month, day).toISOString().split('T')[0];
                dob.val(formattedDate)
                console.log(formattedDate)
            }
        }
    }

    function updateCustomer(){
        let idUser = $('#fullnameCustomer').attr('data-UserId')
        let customerUpdate = listStaff.find(user => user.id == idUser)
        let emailCustomer = null;
        let fullnameCustomer = $('#fullnameCustomer').val()
        let phoneNumberAdd = null
        if(!($('#emailCustomer').val() == customerUpdate.email)){
            emailCustomer = $('#emailCustomer').val()
            console.log(emailCustomer + " mail")
        }
        if(!($('#phoneNumberAdd').val().trim() == customerUpdate.phoneNumber.trim())){
            console.log($('#phoneNumberAdd').val()+ " số điện thoại mới, " + customerUpdate.phoneNumber + ": số điện thoại cũ")
            phoneNumberAdd = $('#phoneNumberAdd').val()
        }
        console.log($('#phoneNumberAdd').val()+ " số điện thoại mới, " + customerUpdate.phoneNumber + ": số điện thoại cũ")
        let gender = $('#gender').val()
        let dob = $('#dob').val()
        if(!validate()){
            return
        }
        let data = {
            id:idUser,
            email: emailCustomer,
            fullName: fullnameCustomer,
            phoneNumber: phoneNumberAdd,
            gender:gender,
            dateOfBirth: dob,
            status : true
        }
        let formData = new FormData();
        formData.append('file', fileGlobal);
        formData.append("user", new Blob([JSON.stringify(data)], {type: "application/json"}));
        $.ajax({
            url: `/api/admin/user/update/customer`,
            type: 'PUT',
            data: formData,
            processData: false,
            contentType: false,
            success: function() {
                notificationAddCusstomer('Sửa thành công', 'success')
                fileGlobal = null
                toggleModal(updateStaffModal, false)
                fetchStaff(0)
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                notificationAddCusstomer(errorMessages, 'warning')
                console.error('Error fetching districts data:', error);
            }
        });
    }

    function validate(){
        let name = $('#fullnameCustomer').val()
        let phone = $('#phoneNumberAdd').val()
        let email = $('#emailCustomer').val()
        let emailRegex = /^[\w.%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        let phoneRegex = /([\+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\b/;

        if (!phone || !email || !name) {
            notificationAddCusstomer("Không để số điện thoại, tên, email trống", 'warning')
            return false;
        }
        if (!phoneRegex.test(phone)) {
            notificationAddCusstomer("Số điện thoại không đúng", 'warning')
            return false;
        }
        if (!emailRegex.test(email)) {
            notificationAddCusstomer("email không đúng", 'warning')
            return false;
        }
        return true;
    }

    function notificationAddCusstomer(message, icon){
        const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.style.zIndex = 2000;
                toast.onmouseenter = Swal.stopTimer;
                toast.onmouseleave = Swal.resumeTimer;
            }
        });
        Toast.fire({
            icon: icon,
            title: message
        });
    }
    const uploadContainer = document.getElementById('uploadContainer');
    const imageInput = document.getElementById('imageInput');
    const removeBtn = document.getElementById('removeBtn');
    // Hàm đặt ảnh nền
    function setImageBackground(imageUrl) {
        if (imageUrl) {
            uploadContainer.style.backgroundImage = `url(${imageUrl})`;
            removeBtn.style.visibility = "visible";
        } else {
            uploadContainer.style.backgroundImage = "";
            removeBtn.style.visibility = "hidden";
        }
    }

    let fileGlobal = null;
    // Xử lý khi chọn ảnh từ input file
    // Hàm xử lý khi chọn ảnh từ input file
    imageInput.addEventListener('change', function () {
        fileGlobal = null
        const file = this.files[0];
        fileGlobal = file
        if (file) {
            const objectUrl = URL.createObjectURL(file); // Tạo URL từ file
            setImageBackground(objectUrl); // Đặt URL làm ảnh nền
            console.log(fileGlobal)
        }
    });

// Khi xóa ảnh, cũng cần thu hồi URL
    removeBtn.addEventListener('click', function (e) {
        e.stopPropagation();
        URL.revokeObjectURL(uploadContainer.style.backgroundImage); // Thu hồi URL
        imageInput.value = ""; // Reset giá trị input file
        setImageBackground(""); // Xóa ảnh nền
    })

    // Khi nhấp vào khung, kích hoạt input file
    uploadContainer.addEventListener('click', function () {
        imageInput.click();
    });
    $('#update-customer').on('click', function () {
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
                updateCustomer()
            }else{
                return
            }
        });
    })
    function updateStatus(idUser, status){
        $.ajax({
            url: `/api/admin/user/update/status/customer?id=${idUser}&status=${status}`,
            type: 'PUT',
            success: function() {
                notificationAddCusstomer('Sửa thành công', 'success')
                fetchStaff(0)
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                // notificationAddCusstomer(errorMessages, 'error')
                console.error('HTTP Status:', xhr.status); // Mã trạng thái HTTP
                console.error('Response Text:', xhr.responseText); // Nội dung phản hồi
                console.error('Error:', error); // Mô tả lỗi
            }
        });
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
                    const selectedRadio = $('input[name="statusCustomer"]:checked').val();
                    // fetchCustomer(page - 1, selectedRadio)
                    fetchStaff(page - 1)
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
})