$(document).ready(function () {
    // Lắng nghe sự thay đổi trên các input có name='shipping_method'
    $("input[name='shipping_method']").change(function () {
        var selectedShippingMethod = $("input[name='shipping_method']:checked").val(); // lấy phương thức giao hàng đã chọn

        // Tạo đối tượng JSON để gửi lên server
        var data = {
            shippingMethod: selectedShippingMethod
        };

        // Gửi AJAX request
        $.ajax({
            url: '/order/update',  // Đường dẫn API
            method: 'POST',           // Phương thức gửi dữ liệu
            contentType: 'application/json',  // Đảm bảo dữ liệu được gửi dưới dạng JSON
            data: JSON.stringify(data),      // Chuyển đối tượng JSON thành chuỗi JSON
            success: function (response) {

                console.log('Dữ liệu đã được gửi thành công!', response);

                // Định dạng kết quả cuối cùng sau khi tính toán
                var formattedTotalPayment = new Intl.NumberFormat('vi-VN').format(response.totalPayment);
                var formattedShippingPrice = new Intl.NumberFormat('vi-VN').format(response.shippingPrice);

                // Cập nhật giao diện
                $('#total-shipping-cost').text(formattedShippingPrice + ' đ');  // Cập nhật phí vận chuyển
                $('#total-payment').text(formattedTotalPayment + ' đ');  // Cập nhật tổng tiền thanh toán

            },
            error: function (xhr, status, error) {
                console.error("Lỗi: " + xhr.responseText);
                console.error("Mã lỗi: " + xhr.status);
                console.error("Thông báo lỗi: " + error);
            }
        });
    });

    // Lắng nghe sự thay đổi trên các input có name='voucher-select' (voucher)
    $("input[name='voucher-select']").change(function () {
        var selectedPromotion = $("input[name='voucher-select']:checked").val(); // lấy mã khuyến mãi đã chọn

        // Tạo đối tượng JSON để gửi lên server
        var data = {
            promotionId: selectedPromotion
        };

        // Gửi AJAX request
        $.ajax({
            url: '/order/update',  // Đường dẫn API
            method: 'POST',           // Phương thức gửi dữ liệu
            contentType: 'application/json',  // Đảm bảo dữ liệu được gửi dưới dạng JSON
            data: JSON.stringify(data),      // Chuyển đối tượng JSON thành chuỗi JSON
            success: function (response) {

                console.log('Dữ liệu đã được gửi thành công!', response);


                var formattedTotalDiscount = new Intl.NumberFormat('vi-VN').format(response.discountValue);
                var formattedTotalPayment = new Intl.NumberFormat('vi-VN').format(response.totalPayment);

                $('#total-discount').text(formattedTotalDiscount + ' đ');  // Cập nhật phí vận chuyển
                $('#total-payment').text(formattedTotalPayment + ' đ');  // Cập nhật tổng tiền thanh toán


            },
            error: function (error) {
                console.error('Lỗi khi gửi dữ liệu:', error);
            }
        });
    });


    $("input[name='payment-method']").change(function () {
        var selectedPaymentMethod = $("input[name='payment-method']:checked").val();

        // Tạo đối tượng JSON để gửi lên server
        var data = {
            paymentMethod: selectedPaymentMethod
        };

        // Gửi AJAX request
        $.ajax({
            url: '/order/update',  // Đường dẫn API
            method: 'POST',           // Phương thức gửi dữ liệu
            contentType: 'application/json',  // Đảm bảo dữ liệu được gửi dưới dạng JSON
            data: JSON.stringify(data),      // Chuyển đối tượng JSON thành chuỗi JSON
            success: function (response) {

                console.log('Dữ liệu đã được gửi thành công!', response);
            },
            error: function (error) {
                console.error('Lỗi khi gửi dữ liệu:', error);
            }
        });
    });

    // thay đổi địa chỉ
    $('.OK-btn-change-address').click(function () {
        var selectedAddress = $("input[name='address-select']:checked").val();
        var data = {
            addressId: selectedAddress
        };
        // Gửi AJAX request
        $.ajax({
            url: '/order/update',  // Đường dẫn API
            method: 'POST',           // Phương thức gửi dữ liệu
            contentType: 'application/json',  // Đảm bảo dữ liệu được gửi dưới dạng JSON
            data: JSON.stringify(data),      // Chuyển đối tượng JSON thành chuỗi JSON
            success: function (response) {

                console.log('Dữ liệu đã được gửi thành công!', response);
                $('#fullNameAndPhoneNumber').text(response.addressById.fullName + ' ' + response.addressById.phoneNumber);  // Cập nhật phí vận chuyển
                $('#streetDetailsAndAdress').text(response.addressById.streetDetails + ' ' + response.addressById.address);  // Cập nhật tổng tiền thanh toán
                // Kiểm tra trạng thái và hiển thị hoặc ẩn phần tử "Mặc định"
                if (response.addressById.status) {
                    $('.dIzOca').show();  // Nếu status là true, hiển thị phần tử
                } else {
                    $('.dIzOca').hide();  // Nếu status là false, ẩn phần tử
                }
            },
            error: function (error) {
                console.error('Lỗi khi gửi dữ liệu:', error);
            }
        });
    });

    // thêm địa chỉ
    $('.btn-add-address').click(function () {
        var nameUser = $("input[name='user_address_fullname']").val();
        var phoneUser = $("input[name='user_address_phone']").val();
        var streetAdressUser = $("input[name='user_street_address']").val();
        var cityAddress = $("input[name='user_address_city$']").val();
        var districtAddress = $("input[name='user_address_district$']").val();
        var wardAddress = $("input[name='user_address_ward$']").val();
        var isCheckedAddress = $("input[name='address_select']").is(':checked');

        var data = {
            fullName: nameUser,
            phoneNumber: phoneUser,
            ward: wardAddress,
            streetDetails: streetAdressUser,
            status: isCheckedAddress,
            district: districtAddress,
            city: cityAddress
        };
        // Gửi AJAX request
        $.ajax({
            url: '/user/address',  // Đường dẫn API
            method: 'POST',           // Phương thức gửi dữ liệu
            contentType: 'application/json',  // Đảm bảo dữ liệu được gửi dưới dạng JSON
            data: JSON.stringify(data),      // Chuyển đối tượng JSON thành chuỗi JSON
            success: function (response) {
                console.log('Dữ liệu đã được gửi thành công!', response);
                localStorage.setItem("needToOpenModal", "true");
                window.location.reload(true);
            },
            error: function (error) {
                console.error('Lỗi khi gửi dữ liệu:', error);
            }
        });

    });

    if (localStorage.getItem("needToOpenModal") === "true") {
        $('#openModalBtnAddress').click();
        localStorage.removeItem("needToOpenModal"); // Clean up
    }

    $('.openModalBtnUpdateAddress').click(function () {
        var selectedAddress = $(this).val(); // Lấy giá trị của nút được nhấn

        // Gửi AJAX request
        $.ajax({
            url: '/user/address-detail',  // Đường dẫn API
            method: 'GET',         // Phương thức gửi dữ liệu
            // Không cần contentType cho yêu cầu GET với tham số truy vấn
            data: { idAddress: selectedAddress }, // Truyền tham số qua URL
            success: function (response) {
                console.log('Dữ liệu đã được gửi thành công!', response);
                $('#userAddressFullName').val(response.fullName);
                $('#userAddress').val(response.address);
                $('#userAddressId').val(response.idAddress);
                $('#userAddressPhone').val(response.phoneNumber);
                // $('#userAddressFullName').val(response.status);
                $('#userStreetAddress').val(response.streetDetails);
            },
            error: function (error) {
                console.error('Lỗi khi gửi dữ liệu:', error);
            }
        });
    });

    $('.btn-update-address').click(function () {
        var idAddress = $("input[id='userAddressId']").val();
        var nameUser = $("input[id='userAddressFullName']").val();
        var phoneUser = $("input[id='userAddressPhone']").val();
        var addressUser = $("input[id='userAddress']").val();
        var streetAdressUser = $("input[id='userStreetAddress']").val();
        var isCheckedAddress = $("input[name='address_select']").is(':checked');

        var data = {
            idAddress: idAddress,
            fullName: nameUser,
            phoneNumber: phoneUser,
            address: addressUser,
            streetDetails: streetAdressUser,
            status: isCheckedAddress
        };
        // Gửi AJAX request
        $.ajax({
            url: '/user/address',  // Đường dẫn API
            method: 'PUT',           // Phương thức gửi dữ liệu
            contentType: 'application/json',  // Đảm bảo dữ liệu được gửi dưới dạng JSON
            data: JSON.stringify(data),      // Chuyển đối tượng JSON thành chuỗi JSON
            success: function (response) {
                console.log('Dữ liệu đã được gửi thành công!', response);
                localStorage.setItem("needToOpenModalUpdate", "true");
                window.location.reload(true);
            },
            error: function (error) {
                console.error('Lỗi khi gửi dữ liệu:', error);
            }
        });

    });
    if (localStorage.getItem("needToOpenModalUpdate") === "true") {
        $('#openModalBtnAddress').click();
        localStorage.removeItem("needToOpenModalUpdate"); // Clean up
    }

});
