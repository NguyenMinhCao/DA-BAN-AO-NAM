$(document).ready(function () {
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
    $('#save').on('click', function () {
        addCustomer()
    })

    function addCustomer() {
        let emailRegex = /^[\w.%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        let phoneRegex = /([\+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\b/;
        let name = $('#customerName').val()
        let phone = $('#customerPhone').val()
        let email = $('#customerEmail').val()
        let dob = $('#customerDob').val()
        let gender = $('input[name="customerGender"]:checked').val();
        let fileInput = $('#customerImage')[0];
        const passwordValue = document.getElementById('customerPassword').value;
        let file = fileInput?.files[0];
        let data = {
            email: email,
            fullName: name,
            phoneNumber: phone,
            gender: gender,
            dateOfBirth: dob,
            status: true,
            role: {
                id: 3
            },
            password: passwordValue,
        }
        if (!phone || !email || !name) {
            notificationAddCusstomer("Không để số điện thoại, tên, email trống", 'waring')
            return;
        }
        if (!phoneRegex.test(phone)) {
            notificationAddCusstomer("Số điện thoại không đúng", 'warning')
            return;
        }
        if (!emailRegex.test(email)) {
            notificationAddCusstomer("email không đúng", 'warning')
            return;
        }
        let formData = new FormData();
        if (fileGlobal) {
            formData.append('file', fileGlobal);
        }
        formData.append("user", new Blob([JSON.stringify(data)], {type: "application/json"}));
        let promis = $.ajax({
            url: `/api/admin/user/update/customer`,
            type: 'PUT',
            data: formData,
            processData: false,
            contentType: false,
            success: function () {
                notificationAddCusstomer('Thêm thành công', 'success')
                fileGlobal = null
            },
            error: function (xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                notificationAddCusstomer(errorMessages, 'error')
                console.error('HTTP Status:', xhr.status); // Mã trạng thái HTTP
                console.error('Response Text:', xhr.responseText); // Nội dung phản hồi
                console.error('Error:', error); // Mô tả lỗi
            }
        });
        promis.done(function(){
            window.location.href = "/admin/staff";
        });
    }


    function notificationAddCusstomer(message, icon) {
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