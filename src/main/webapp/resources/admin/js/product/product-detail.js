var productId = $('#product-id').attr("data-product-id");

//Show form
$(document).ready(function () {
    $("#div-quantity-color").hide();
    $("#div-quantity-size").hide();
    //Mở form add
    $('#showFormProductDetailAdd').click(function () {
        $('#AddProductDetailModal').modal('show');
    });

    //Đóng form add
    $('#closeFormProductDetailAdd').click(function () {
        $('#AddProductDetailModal').modal('hide');
    });
    //Đóng form update
    $('#closeFormProductDetailUpdate').click(function () {
        $('#UpdateProductDetailModal').modal('hide');
    });

    getListURL(productId);

});

//Lấy danh sách size add
function getSize(selectElement) {
    $("#select-size").html(''); // Xóa tất cả các option cũ
    var colorId = selectElement.value;

    if (!productId || !colorId) {
        alert("Thiếu dữ liệu sản phẩm hoặc màu sắc!");
        return;
    }

    $.ajax({
        type: "GET",
        url: "/admin/rest/product-detail/getListSizeAddProductDetail",
        data: { productId: productId, colorId: colorId },
        success: function (response) {
            console.log("Lấy danh sách Size thành công!");
            renderListSize(response);
        },
        error: function (error) {
            console.error("Lỗi khi lấy danh sách Size:", error);
            alert("Không thể lấy danh sách Size. Vui lòng thử lại!");
        }
    });
}

function renderListSize(listSize) {
    $("#select-size").append('<option value="">Chọn kích thước</option>');

    listSize.forEach(function (size) {
        var value = size.id;
        var name = size.name || size.sizeName;

        var sizeOption = $('<option>', { value: value, text: name });
        $("#select-size").append(sizeOption);
    });
}

//Lấy số lượng thông qua màu
function getQuantityByColor(selectElement) {

    var colorId = selectElement.value;

    var dataToSend = {
        productId: productId,
        colorId: colorId
    }

    $.ajax({
        type: "GET",
        url: "/admin/rest/product/quantityByColorId",
        contentType: "application/json",
        data: dataToSend,
        success: function (response) {
            console.log("Lấy số lượng Màu sắc thành công!");
            if (response === "") {
                $("#label-quantity-color").text(0);
            } else {
                $("#label-quantity-color").text(response);
            }

            $("#div-quantity-color").show();

        },
        error: function (error) {
            console.error("Lỗi khi lấy số lượng Màu sắc:", error);
            return false;
        }
    });
}

//Lấy số lượng thông qua màu
function getQuantityBySize(selectElement) {

    var sizeId = selectElement.value;

    var dataToSend = {
        productId: productId,
        sizeId: sizeId
    }

    $.ajax({
        type: "GET",
        url: "/admin/rest/product/quantityBySizeId",
        contentType: "application/json",
        data: dataToSend,
        success: function (response) {
            console.log("Lấy số lượng Kích thước thành công!");

            if (response === "") {
                $("#label-quantity-size").text(0);
            } else {
                $("#label-quantity-size").text(response);
            }

            $("#div-quantity-size").show();

        },
        error: function (error) {
            console.error("Lỗi khi lấy số lượng Kích thước:", error);
            return false;
        }
    });
}

function getListURL(productId) {
    $.ajax({
        type: "GET",
        url: "/admin/rest/image/findByProductId/" + productId,
        success: function (response) {
            console.log("Dữ liệu trả về từ API:", response);

            // Kiểm tra dữ liệu trả về và lấy đúng trường chứa URL
            listUrlImage = response.map(item => item.urlImage); // Lấy trường `urlImage` từ từng phần tử
            console.log("Danh sách URL ảnh:", listUrlImage);

            // Gọi hàm hiển thị ảnh
            displayImages(listUrlImage);
        },
        error: function (error) {
            console.error("Lỗi khi lấy danh sách ảnh:", error);
        }
    });
}


function displayImages(listUrlImage) {
    $("#thumbbox").empty();

    var baseUrl = window.location.origin;

    listUrlImage.forEach(function (url) {
        // Tạo đường dẫn đầy đủ
        var fullUrl = baseUrl + url;

        var imageElement = `
            <div class="image-product-container">
                <img src="${fullUrl}" alt="Product Image" class="thumbimage" />
            </div>
        `;
        $("#thumbbox").append(imageElement); // Thêm ảnh vào #thumbbox
    });
    console.log("Đã hiển thị ảnh:", listUrlImage);
}


$(document).ready(function () {
    var productId = 1; // Thay bằng ID sản phẩm thực tế
    getListURL(productId); // Gọi hàm lấy URL ảnh
});

function updateProductDetailForm(element) {
    var productDetailId = element.getAttribute("data-product-detail-id")
    var productDetailColor = element.getAttribute("data-product-detail-color")
    var productDetailSize = element.getAttribute("data-product-detail-size")

    // Thêm thuộc tính productDetailId vào modal để update
    $('#UpdateProductDetailModal').attr('product-detail-id-update', productDetailId);

    // Thực hiện AJAX request để lấy dữ sản phẩm mục từ backend
    $.ajax({
        type: 'GET',
        url: '/admin/rest/product-detail/formUpdate/' + productDetailId,
        success: function (productDetail) {
            // Hiển thị hộp thoại modal
            $('#UpdateProductDetailModal').modal('show');

            // Điền dữ liệu vào các trường biểu mẫu
            $('#product-detail-color').text("Màu sắc: " + productDetailColor);
            $('#product-detail-size').text("Kích thước: " + productDetailSize);
            // $('#product-detail-weight').val(productDetail.weight);
            $('#product-detail-quantity').val(productDetail.quantity);
            $('#product-detail-price').val(productDetail.price);
        },
        error: function (error) {
            console.log('Error fetching productDetail data:', error);
            // Xử lý lỗi nếu cần
        }
    });
}

function saveUpdate() {
    var productDetailId = $('#UpdateProductDetailModal').attr("product-detail-id-update");
    // var productDetailWeight = $('#product-detail-weight').val();
    var productDetailQuantity = $("#product-detail-quantity").val();
    var productDetailPrice = $("#product-detail-price").val();
    var productDetailStatus = 0;

    if (productDetailQuantity < 0 || productDetailPrice <= 0) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Thông tin không hợp lệ!'
        });
        return;
    }

    var dataToSend = {
        id: productDetailId,
        // weight: productDetailWeight,
        quantity: productDetailQuantity,
        price: productDetailPrice,
        status: productDetailStatus
    }

    // Gửi yêu cầu AJAX
    $.ajax({
        type: "PUT",
        url: "/admin/rest/product-detail/update/" + productDetailId,
        contentType: "application/json",
        data: JSON.stringify(dataToSend),
        success: function (response) {
            console.log("Lưu Biến Thể thành công!");

            Swal.fire({
                icon: 'success',
                title: 'Thành công!',
                text: 'Lưu Biến Thể thành công!',
                didClose: function () {
                    location.reload();
                }
            });
        },
        error: function (error) {
            console.error("Lỗi khi lưu Biến Thể:", error);

            Swal.fire({
                icon: 'error',
                title: 'Lỗi!',
                text: 'Có lỗi xảy ra khi lưu Biến Thể!'
            });
        }
    });
}

function saveAdd() {
    var productDetailColor = $('#select-color').val();
    var productDetailSize = $('#select-size').val();
    // var productDetailWeight = $('#add-product-detail-weight').val();
    var productDetailQuantity = $("#add-product-detail-quantity").val();
    var productDetailPrice = $("#add-product-detail-price").val();
    var productDetailStatus = 0;

    if (productDetailColor == null || productDetailSize == null || productDetailQuantity == 0 || productDetailPrice == 0) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Vui lòng nhập đầy đủ thông tin!!'
        });
        return;
    }

    var dataToSend = {
        productId: productId,
        colorId: productDetailColor,
        sizeId: productDetailSize,
        // weight: productDetailWeight,
        quantity: productDetailQuantity,
        price: productDetailPrice,
        status: productDetailStatus
    }

    // Gửi yêu cầu AJAX
    $.ajax({
        type: "POST",
        url: "/admin/rest/product-detail/add",
        contentType: "application/json",
        data: JSON.stringify(dataToSend),
        success: function (response) {
            console.log("Lưu Biến Thể thành công!");

            Swal.fire({
                icon: 'success',
                title: 'Thành công!',
                text: 'Lưu Biến Thể thành công!',
                didClose: function () {
                    location.reload();
                }
            });
        },
        error: function (error) {
            console.error("Lỗi khi lưu Biến Thể:", error);

            Swal.fire({
                icon: 'error',
                title: 'Lỗi!',
                text: 'Có lỗi xảy ra khi lưu Biến Thể!'
            });
        }
    });
}