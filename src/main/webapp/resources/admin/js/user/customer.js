$(document).ready(function () {
    const tokenGHN = '39546c64-c9e1-11ef-a7a0-fe411cc1f02739546c64-c9e1-11ef-a7a0-fe411cc1f027'
    let listAddress  = []
    let listCustomer = []
    let addressModal = document.getElementById('containerAddressModal');
    let updateCustomerModal = document.getElementById('form-add-customer');
    fetchCustomer(0)

    function toggleModal(modalElement, show) {
        if (show) {
            modalElement.style.display = 'flex';
        } else {
            modalElement.style.display = 'none';
        }
    }
    function fetchCustomer(page) {
        let search = $('#search').val()
        $.ajax({
            url: `/api/admin/user/get/customers?page=${page}&limit=10`,
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
        listCustomer = []
        $('#tableSample tbody').empty()
        if (data.length > 0) {
            data.forEach(data => {
                listCustomer.push(data)
                const newRow = document.createElement("tr");
                newRow.innerHTML =
                    `
                    <tr>
                        <td><img src="/images/avatar/${data.avatar}" height="100px" width="auto" alt="lỗi ảnh"></td>
                        <td>${data.fullName}</td>
                        <td>${data.phoneNumber}</td>
                        <td>${data.email}</td>
                        <td>${data.status}</td>
                        <td>
                            <button data-id="${data.id}" class="btn btn-location" title="Vị chí">
                                <i class="fa-solid fa-location-dot"></i>
                            </button>
                            <button data-id="${data.id}" class="btn btn-info" title="Thông tin chi tiết">
                                <i class="fa-solid fa-circle-info"></i>
                            </button>
                            <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">
                            </div>
                        </td> 
                    </tr>
                         
                 `
                $('#tableSample tbody').append(newRow)

                newRow.querySelector(".btn-location").addEventListener("click", function(e){
                    const userId = e.currentTarget.getAttribute('data-id')
                    console.log(userId + ": id của user được chọn")
                    toggleModal(addressModal, true)
                    fetchAddressByIdUser(userId)
                });
                newRow.querySelector(".btn-info").addEventListener("click", function(e) {
                    const userId = e.currentTarget.getAttribute('data-id')
                    console.log(userId + ": id của user được chọn")
                    toggleModal(updateCustomerModal, true)
                    fuelData(userId)
                });
            });
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

    //******************** Địa chỉ

    $('#btnCloseModalAddDress').on('click', function(){
        toggleModal(addressModal, false)
    })
    function fetchAddressByIdUser(userID){
        $.ajax({
            url : `/api/admin/user/get/address/${userID}`,
            type: "GET",
            success: function(response){
                console.log("lấy dữ liệu thành công")
                listAddress = []
                response.forEach(item => {
                    listAddress.push(item)
                    console.log(item.fullName)
                })
                renderAddress()
            },
            error: function(){

            },
        })
    }
    function renderAddress(){
        let contaiAddress = $('#bodyTableAddress')
        contaiAddress.empty()
        if(listAddress && listAddress.length > 0){
            listAddress.forEach(item => {
                let newRow = `
                <tr>
                    <td>${item.phoneNumber}</td>
                    <td>${item.fullName}</td>
                    <td>${item.streetDetails}, ${item.ward}, ${item.district}, ${item.city}</td>
                    <td>${item.status = true? 'Hoạt động': 'Không hoạt động'}</td>
                    <td>
                        <button class="btn btn-edit" title="Chỉnh sửa">
                            <i class="fa-solid fa-wrench"></i>
                        </button>
                    </td>
                </tr>
            `
                contaiAddress.append(newRow)
            })
        }else{
            contaiAddress.append('<tr><td colspan="2">Khách hàng này chưa có địa chỉ<td></tr>')
        }
    }

//     Chỉnh sửa khách hàng
    $('#cancel-btn-add-customer').on('click', function(){
        toggleModal(updateCustomerModal, false)
    })
    function fuelData(idUser){
        let fullnameCustomer = $('#fullnameCustomer')
        let emailCustomer = $('#emailCustomer')
        let phoneNumberAdd = $('#phoneNumberAdd')
        let gender = $('#gender')
        let dob = $('#dob')
        let customerUpdate = listCustomer.find(user => user.id == idUser)
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
        let fullnameCustomer = $('#fullnameCustomer').val()
        let idUser = $('#fullnameCustomer').attr('data-UserId')
        let emailCustomer = $('#emailCustomer').val()
        let phoneNumberAdd = $('#phoneNumberAdd').val()
        let gender = $('#gender').val()
        let dob = $('#dob').val()
        let data = {
            id:idUser,
            email: emailCustomer,
            fullName: fullnameCustomer,
            phoneNumber: phoneNumberAdd,
            gender:gender,
            dateOfBirth: dob,
            status : 'Kích hoạt'
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
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                console.error('HTTP Status:', xhr.status);
                console.error('Response Text:', xhr.responseText);
                console.error('Error:', error);
            }
        });
        fetchCustomer(0)
    }
    $('#update-customer').on('click', function(){
        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, delete it!"
        }).then((result) => {
            if (result.isConfirmed) {
                updateCustomer()
            }
        });
    })
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
});