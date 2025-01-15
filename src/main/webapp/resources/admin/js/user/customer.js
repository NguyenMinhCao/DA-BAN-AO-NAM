$(document).ready(function () {
    const tokenGHN = '39546c64-c9e1-11ef-a7a0-fe411cc1f02739546c64-c9e1-11ef-a7a0-fe411cc1f027'
    let listAddress  = []
    let listCustomer = []
    let addressModal = document.getElementById('containerAddressModal');
    let updateCustomerModal = document.getElementById('form-add-customer');
    const selectedRadio = $('input[name="statusCustomer"]:checked').val();
    const addAddressModal = document.getElementById('form-add-address')
    fetchCustomer(0, selectedRadio)

    $('input[name="statusCustomer"]').on('change', function () {
        const selectedValue = $(this).val();
        console.log(selectedValue + " trạng thái")
        fetchCustomer(0, selectedValue)
    });
    function toggleModal(modalElement, show) {
        if (show) {
            modalElement.style.display = 'flex';
        } else {
            modalElement.style.display = 'none';
        }
    }
    function fetchCustomer(page, status) {
        let search = $('#search').val()
        $.ajax({
            url: `/api/admin/user/get/customers?page=${page}&limit=10`,
            type: 'GET',
            data: {keyword: search, status: status},
            success: function (response) {
                listCustomer = []
                response.content.forEach(item => listCustomer.push(item))
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
                                <button data-id="${data.id}" class="btn btn-location" title="Vị chí">
                                    <i class="fa-solid fa-location-dot"></i>
                                </button>
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
                    fetchCustomer(page - 1, selectedRadio)
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
        const selectedRadio = $('input[name="statusCustomer"]:checked').val();
        fetchCustomer(0, selectedRadio)
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
                renderAddress(userID)
            },
            error: function(){

            },
        })
    }
    function renderAddress(userID){
        document.getElementById('btnAddDress').setAttribute('data-userId', userID)
        let contaiAddress = $('#bodyTableAddress')
        contaiAddress.empty()
        if(listAddress && listAddress.length > 0){
            listAddress.forEach(item => {
                const newRow = document.createElement("tr");
                newRow.innerHTML =
                `
                <tr>
                    <td>${item.phoneNumber}</td>
                    <td>${item.fullName}</td>
                    <td>${item.streetDetails}, ${item.ward}, ${item.district}, ${item.city}</td>
                    <td>
                        <button data-id="${item.id}" data-userId = ${item.userId} class="btn btn-edit btn-edit-address" title="Chỉnh sửa">
                            <i class="fa-solid fa-wrench"></i>
                        </button>
                    </td>
                </tr>
            `
                contaiAddress.append(newRow)
                newRow.querySelector('.btn-edit-address').addEventListener('click', function(){
                    let addressId = this.getAttribute('data-id')
                    let userId = this.getAttribute('data-userId')
                    toggleModal(addAddressModal, true)
                    fuelInfoAddress(addressId)
                    document.getElementById('add-address').setAttribute('data-addressId', addressId)
                    document.getElementById('add-address').setAttribute('data-userId', userId)
                    console.log(addressId)
                })
            })
        }else{
            contaiAddress.append('<tr><td colspan="2">Khách hàng này chưa có địa chỉ<td></tr>')
        }
    }
    $('#add-address').on('click', function(){
        let idUser = this.getAttribute('data-userId')
        let idAddress = this.getAttribute('data-addressId')
        console.log('data-idAddress' + idAddress + " "+idUser)
        creatAddress(idUser, idAddress)
    })

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
    function validate(){
        let fullnameCustomer = $('#fullnameCustomer').val()
        let emailCustomer = $('#emailCustomer').val()
        let phoneNumberAdd = $('#phoneNumberAdd').val()
        let emailRegex = /^[\w.%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        let phoneRegex = /([\+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\b/;
        if(!fullnameCustomer || !phoneNumberAdd){
            notificationAddCusstomer("Họ tên, số điện thoại không được để trống", 'warning')
            return false
        }
        if(emailCustomer && !emailRegex.test(emailCustomer)){
            notificationAddCusstomer("Email không đúng định dạng", 'warning')
            return false
        }
        if(phoneNumberAdd && !phoneRegex.test(phoneNumberAdd)){
            notificationAddCusstomer("Số điện thoại không đúng định dạng", 'warning')
            return false
        }
        return true
    }
    function updateCustomer(){
        let idUser = $('#fullnameCustomer').attr('data-UserId')
        let customerUpdate = listCustomer.find(user => user.id == idUser)
        let emailCustomer = null
        let fullnameCustomer = $('#fullnameCustomer').val()
        let phoneNumberAdd = null
        if(!$('#emailCustomer').val() == customerUpdate.email){
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
        validate(name, phoneNumberAdd, emailCustomer)
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
                const selectedRadio = $('input[name="statusCustomer"]:checked').val();
                fetchCustomer(0, selectedRadio)
                notificationAddCusstomer('Sửa thành công', 'success')
                fileGlobal = null
                toggleModal(updateCustomerModal, false)
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                notificationAddCusstomer(errorMessages, 'warning')
                console.error('Error fetching districts data:', error);
            }
        });
    }
    $('#update-customer').on('click', function(){
        if(!validate()){
            return
        }
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
    function notificationAddCusstomer(message, icon){
        const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.style.zIndex = 3000;
                toast.onmouseenter = Swal.stopTimer;
                toast.onmouseleave = Swal.resumeTimer;
            }
        });
        Toast.fire({
            icon: icon,
            title: message
        });
    }
    function updateStatus(idUser, status){
        $.ajax({
            url: `/api/admin/user/update/status/customer?id=${idUser}&status=${status}`,
            type: 'PUT',
            success: function() {
                notificationAddCusstomer('Sửa thành công', 'success')
                fetchCustomer(0)
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

    $('#cancel-btn-add-address').on('click', function(){
        toggleModal(addAddressModal, false)
    })
    $('#btnAddDress').on('click', function(){
        let idUser = this.getAttribute('data-userId')
        toggleModal(addAddressModal, true)
        console.log(idUser + " id user")
        document.getElementById('add-address').setAttribute('data-userId', idUser)
    })
    fetchLocation()
    function fetchLocation(){
        const apiUrl = 'https://provinces.open-api.vn/api/p';
        $.ajax({
            url: apiUrl,
            method: 'GET',
            success: function(data) {
                data.forEach(function(value) {
                    $('#area').append(`<option value="${value.code}">${value.name}</option>`);
                });
            },
            error: function(error) {
                console.error('Error fetching provinces data:', error);
            }
        });
    }
    function fetchDistricts(districtsCode) {
        const apiUrlDistricts = `https://provinces.open-api.vn/api/p/${districtsCode}/?depth=2`;
        return $.ajax({
            url: apiUrlDistricts,
            method: 'GET',
            success: function(data) {
                let districts = data.districts;
                $('#Districts').empty().append('<option value="">Select District</option>');
                districts.forEach(function(value) {
                    $('#Districts').append(`<option value="${value.code}">${value.name}</option>`);
                });
            },
            error: function(error) {
                console.error('Error fetching districts data:', error);
            }
        });
    }
    function fetchWards(wardCode) {
        const apiUrlWards = `https://provinces.open-api.vn/api/d/${wardCode}/?depth=2`;
        return $.ajax({
            url: apiUrlWards,
            method: 'GET',
            success: function(data) {
                let wards = data.wards;
                $('#Wards').empty().append('<option value="">Select Ward</option>');
                wards.forEach(function(value) {
                    $('#Wards').append(`<option value="${value.code}">${value.name}</option>`);
                });
            },
            error: function(error) {
                console.error('Error fetching wards data:', error);
            }
        });
    }

    $('#area').on('change', function(e) {
        fetchDistricts(e.target.value);
    });

    $('#Districts').on('change', function(e) {
        fetchWards(e.target.value);
    });

    function creatAddress(idUser, idAddress){
        let phoneRegex = /([\+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\b/;
        let name = $('#fullnameAddress').val()
        let phoneNumber = $('#phoneNumberAddress').val()
        let city = $("#area  option:selected").text()
        let district = $('#Districts option:selected').text()
        let ward = $('#Wards  option:selected').text()
        let locationDetail = $('#addressAddDetail').val()
        if(!name || !phoneNumber || !city || !district || !ward || !locationDetail){
            notificationAddCusstomer("Hãy nhập, chọn đầy đủ dữ liệu", "warning", 3000)
            return;
        }
        if(!phoneRegex.test(phoneNumber)){
            notificationAddCusstomer("Số điện thoại không đúng định dạng", "warning", false)
            return;
        }
        let data ={
            id : idAddress? idAddress : null,
            fullName : name,
            phoneNumber : phoneNumber,
            ward : ward,
            district : district,
            city : city,
            streetDetails : locationDetail,
            user: {
                id : idUser
            }
        }
        Swal.fire({
            title: "Cảnh báo?",
            text: "Bạn chắc chắn chứ!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Đồng ý",
            cancelButtonText: "Hủy",
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: '/api/admin/user/update/address',
                    method: 'PUT',
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: function(data) {
                        notificationAddCusstomer('Thêm mới thành công', 'success', false)
                        fetchAddressByIdUser(idUser)
                        toggleModal(addAddressModal, false)
                        document.getElementById('add-address').setAttribute('data-addressId', '')
                    },
                    error: function(error) {
                        console.error('Error fetching wards data:', error);
                    }
                });
            }
        });
    }
    function fuelInfoAddress(addressId){
        let addressUpdate = listAddress.find(data => data.id == addressId)
        $('#fullnameAddress').val(addressUpdate.fullName)
        $('#phoneNumberAddress').val(addressUpdate.phoneNumber)
        $('#addressAddDetail').val(addressUpdate.streetDetails)
        const selectElement = document.getElementById('area');
        const options = selectElement.options;  // Lấy tất cả các option trong select
        for (let i = 0; i < options.length; i++) {
            if (options[i].textContent === addressUpdate.city) {
                selectElement.value = options[i].value;
                fetchDistricts(selectElement.value).done(function(districts) {
                    const selectDistricts = document.getElementById('Districts');
                    const optionDistricts = selectDistricts.options;  // Lấy tất cả các option trong select
                    console.log(optionDistricts)
                    for (let i = 0; i < optionDistricts.length; i++) {
                        if (optionDistricts[i].textContent == addressUpdate.district) {
                            selectDistricts.value = optionDistricts[i].value;
                            fetchWards(selectDistricts.value).done(function(districts) {
                                const selectWards = document.getElementById('Wards');
                                const optionWards = selectWards.options;  // Lấy tất cả các option trong select
                                console.log(optionWards)
                                for (let i = 0; i < optionWards.length; i++) {
                                    if (optionWards[i].textContent == addressUpdate.ward) {
                                        selectWards.value = optionWards[i].value;
                                    }}
                            })
                        }
                    }
                })

            }
        }

        $('#Districts').text(addressUpdate.district)
        $('#Wards').text(addressUpdate.ward)
    }
});