$(document).ready(function () {
    $('#save').on('click', function(){
        addCustomer()
    })
    function addCustomer(){
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
            status : 'Kích hoạt',
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
        formData.append("json", new Blob([JSON.stringify(data)], {type: "application/json"}));
        $.ajax({
            url: `http://localhost:8080/api/admin/user/save/customer/multipart`,
            type: 'POST',
            data: formData,
            success: function() {
                notificationAddCusstomer('Thêm thành công', 'success')
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

    function validate(data){
        let emailRegex = /^[\w.%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        let phoneRegex = /([\+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\b/;
        if(!data.name || (!data.phoneNumber && !data.email)){
            notificationAddCusstomer("Họ tên, số điện thoại hoặc email không được để trống", warning)
        }
        // if(data)
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
})