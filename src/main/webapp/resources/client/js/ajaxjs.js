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

});
