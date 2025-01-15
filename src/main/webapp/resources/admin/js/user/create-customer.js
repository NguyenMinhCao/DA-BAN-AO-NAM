$(document).ready(function () {
    $('#save').on('click', function(){
        addCustomer()
    })
    function addCustomer(){
        if(!validate()){
            return;
        }
        // thông tin cơ bản
        let name = $('#customerName').val()
        let phone = $('#customerPhone').val()
        let email = $('#customerEmail').val()
        let dob = $('#customerDob').val()
        let gender = $('input[name="customerGender"]:checked').val();
        let fileInput = $('#customerImage')[0];
        let file = fileInput?.files[0];
        // thông tin nhận hàng
        let addressName = $('#addressCustomerName').val()
        let addressPhone = $('#addressCustomerPhone').val()
        let province =  $('#customerProvince option:selected').text();
        let district = $('#customerDistrict option:selected').text();
        let ward = $('#customerWard option:selected').text();
        let addressDetail = $('#addressDetail').val()
        let data = {
            email : email,
            fullName : name,
            phoneNumber : phone,
            gender : gender,
            dateOfBirth : dob,
            status : true,
            address : [
                {
                    fullName : addressName,
                    phoneNumber : addressPhone,
                    ward: ward,
                    city : province,
                    district : district,
                    streetDetails : addressDetail
                }
            ]
        }
        let formData = new FormData();
        if(file){
            formData.append('file', file);
        }
        formData.append("user", new Blob([JSON.stringify(data)], {type: "application/json"}));
        $.ajax({
            url: `/api/admin/user/save/customer/multipart`,
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function() {
                notificationAddCusstomer('Thêm thành công', 'success')
                window.location.href = "/admin/customer";
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                notificationAddCusstomer(errorMessages, 'error')
                console.error('HTTP Status:', xhr.status); // Mã trạng thái HTTP
                console.error('Response Text:', xhr.responseText); // Nội dung phản hồi
                console.error('Error:', error); // Mô tả lỗi
            }
        });
    }

    function validate(){
        let name = $('#customerName').val()
        let phone = $('#customerPhone').val()
        let email = $('#customerEmail').val()
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
    fetchLocation()
    //fetch địa chỉ cho các ô select
    function fetchLocation(){
        const apiUrl = 'https://provinces.open-api.vn/api/p';
        $.ajax({
            url: apiUrl,
            method: 'GET',
            success: function(data) {
                data.forEach(function(value) {
                    $('#customerProvince').append(`<option value="${value.code}">${value.name}</option>`);
                });
            },
            error: function(error) {
                console.error('Error fetching provinces data:', error);
            }
        });
    }
    function fetchDistricts(districtsCode) {
        const apiUrlDistricts = `https://provinces.open-api.vn/api/p/${districtsCode}/?depth=2`;
        $.ajax({
            url: apiUrlDistricts,
            method: 'GET',
            success: function(data) {
                let districts = data.districts;
                $('#customerDistrict').empty().append('<option value="">Select District</option>');
                districts.forEach(function(value) {
                    $('#customerDistrict').append(`<option value="${value.code}">${value.name}</option>`);
                });
            },
            error: function(error) {
                console.error('Error fetching districts data:', error);
            }
        });
    }
    function fetchWards(wardCode) {
        const apiUrlWards = `https://provinces.open-api.vn/api/d/${wardCode}/?depth=2`;
        $.ajax({
            url: apiUrlWards,
            method: 'GET',
            success: function(data) {
                let wards = data.wards;
                $('#customerWard').empty().append('<option value="">Select Ward</option>');
                wards.forEach(function(value) {
                    $('#customerWard').append(`<option value="${value.code}">${value.name}</option>`);
                });
            },
            error: function(error) {
                console.error('Error fetching wards data:', error);
            }
        });
    }

    $('#customerProvince').on('change', function(e) {
        fetchDistricts(e.target.value);
    });

    $('#customerDistrict').on('change', function(e) {
        fetchWards(e.target.value);
    });

})