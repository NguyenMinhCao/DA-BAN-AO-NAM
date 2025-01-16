
var selectColor, selectSize;
var listUrlImage = [];
var listProductDetail = [];


$(document).ready(function () {
    //Mở form add
    $('#quick_edit').click(function () {
        if (listProductDetail.length === 0) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi!',
                text: 'Danh sách biến thể trống!'
            });
        } else {
            $('.modal-title').text("Chỉnh Sửa Nhanh");
            $('#QuickEditModal').modal('show');
        }
    });

    //Đóng form add
    $('#saveQuickEditModal').click(function () {
        $('#QuickEditModal').modal('hide');
    });

    // Khởi tạo Select màu sắc và kích thước
    selectColor = $('#select-colors').selectize({
        maxItems: null,
        valueField: 'id',
        labelField: 'title',
        searchField: 'title',
        create: false
    });

    selectSize = $('#select-sizes').selectize({
        maxItems: null,
        valueField: 'id',
        labelField: 'title',
        searchField: 'title',
        create: false
    });

    // Gọi API để lấy thông tin từ server
    $.ajax({
        url: '/admin/rest/add-product',
        type: 'GET',
        success: function (data) {
            // Tạo options màu sắc từ danh sách màu trong productListResponse
            var colorOptions = data.colors.map(function (color) {
                return {id: color.id, title: color.colorName};
            });

            // Tạo options kích thước từ kích thước trong productListResponse
            var sizeOptions = data.sizes.map(function (size) {
                return {id: size.id, title: size.sizeName};
            });

            // Cập nhật options của màu sắc
            selectColor[0].selectize.clearOptions();
            selectColor[0].selectize.addOption(colorOptions);
            selectColor[0].selectize.refreshItems();

            // Cập nhật options của kích thước
            selectSize[0].selectize.clearOptions();
            selectSize[0].selectize.addOption(sizeOptions);
            selectSize[0].selectize.refreshItems();
        },
        error: function (err) {
            console.error('Error call API:', err);
        }
    });

});

function quickEdit() {
    var quantity = $("#quick-edit-quantity").val();
    var price = $("#quick-edit-price").val();

    if (quantity == 0 || price == 0) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Vui lòng nhập đầy đủ thông tin!!'
        });
        return;
    }

    $(".gia_ban").each(function(){
        $(this).val(price);
    });

    $(".so_luong").each(function(){
        $(this).val(quantity);
    });

    for (var i = 0; i < listProductDetail.length; i++) {
        // Lấy giá trị mới từ input tương ứng và cập nhật vào productDetail
        listProductDetail[i].quantity = quantity;
        listProductDetail[i].price = price;
    }

    console.log(listProductDetail);

    //Đóng form add
    $('#closeQuickEditModal').click(function () {
        $('#QuickEditModal').modal('hide');
    });

}
var variants = [
    { color: "Red", size: "M", images: [] },
    { color: "Blue", size: "L", images: [] }
];

function readURL(input, variantIndex) {
    if (input.files && input.files.length > 0) {
        for (var i = 0; i < input.files.length; i++) {
            var formData = new FormData();
            formData.append("file", input.files[i]);

            // Gửi ảnh lên server
            $.ajax({
                url: '/upload/image', // Địa chỉ endpoint xử lý tải ảnh
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function(response) {
                    // Nhận URL ảnh trả về từ server
                    var imageUrl = response.imageUrl; // URL ảnh từ server
                    console.log("Image URL:", imageUrl);

                    var variant = variants[variantIndex];
                    var isDuplicate = variant.images.some(function (img) {
                        return img === imageUrl;
                    });

                    if (!isDuplicate) {
                        variant.images.push(imageUrl);
                        console.log("Updated Variant Images:", variant.images);

                        var imageContainer = $(
                            '<div class="image-product-container">' +
                            '   <img src="' + imageUrl + '" alt="Thumb image" class="thumbimage"/>' +
                            '   <a class="removeimg" href="javascript:" style="display: inline"></a>' +
                            '</div>'
                        );

                        $("#thumbbox-" + variantIndex).append(imageContainer);

                        imageContainer.find(".removeimg").on("click", function () {
                            var removedImage = $(this).closest(".image-product-container").find("img").attr("src");
                            variant.images = variant.images.filter(function (img) {
                                return img !== removedImage;
                            });
                            console.log("Updated Variant Images After Remove:", variant.images);
                            $(this).closest(".image-product-container").remove();
                        });
                    }
                },
                error: function() {
                    alert("Có lỗi khi tải ảnh lên.");
                }
            });
        }

    }

    // Thay đổi giao diện khi có ảnh
    $(".Choicefile").css('background', '#14142B');



    console.log(listUrlImage);
    // Hiển thị các phần tử khi có ảnh
    $(".Choicefile").css('background', '#14142B');
}


$(document).ready(function () {
    $(".Choicefile").bind('click', function () {
        $("#uploadfile").click();
    });
})


function checkInputShowList(productName, productCategory, productMaterial, productColor, productSize, productDescription, productPattern, productOrigin) {
    console.log("Kiểm tra đầu vào:", {
        productName,
        productCategory,
        productMaterial,
        productColor,
        productSize,
        productDescription,
        productPattern,
        productOrigin
    });

    // Kiểm tra tên sản phẩm
    if (!productName || productName.trim() === "") {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Vui lòng nhập Tên sản phẩm!'
        });
        return false;
    }

    if (!checkDuplicateProduct(productName)) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Tên Sản phẩm đã tồn tại!'
        });
        return false;
    }

    // Kiểm tra danh mục
    if (!productCategory || productCategory.trim() === "") {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Vui lòng chọn Danh mục!'
        });
        return false;
    }

    // Kiểm tra chất liệu
    if (!productMaterial || productMaterial.trim() === "") {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Vui lòng chọn Chất liệu!'
        });
        return false;
    }
    // Kiểm tra màu sắc
    if (!productColor || !Array.isArray(productColor) || productColor.length === 0) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Vui lòng chọn Màu sắc!'
        });
        return false;
    }

    // Kiểm tra kích thước
    if (!productSize || !Array.isArray(productSize) || productSize.length === 0) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Vui lòng chọn Kích thước!'
        });
        return false;
    }
    if (!productOrigin || productOrigin.trim() === "") {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Vui lòng chọn Nguồn gốc!'
        });
        return false;
    }

    // Kiểm tra mẫu sản phẩm (kiểm tra đúng)
    if (!productPattern || productPattern.trim() === "") {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Vui lòng chọn Mẫu sản phẩm!'
        });
        return false;
    }

    // Kiểm tra mô tả sản phẩm (kiểm tra đúng)
    if (!productDescription || productDescription.trim() === "") {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Vui lòng nhập Mô tả sản phẩm!'
        });
        return false;
    }

    // Kiểm tra nguồn gốc





    // Tất cả kiểm tra đều hợp lệ
    return true;
}

function showListProductDetail() {
    // Clear listProductDetail
    listProductDetail = [];

    var productName = $("#input-product-name").val();
    var productCategory = $("#select-category").val();
    var productPattern = $("#select-pattern").val();  // Kiểm tra lại đây để không bị nhầm
    var productOrigin = $("#select-origin").val();
    var productMaterial = $("#select-material").val();  // Kiểm tra lại giá trị đúng của chất liệu
    var productDescription = CKEDITOR.instances['input-product-description'].getData();  // Kiểm tra mô tả chính xác

    // Lấy thông tin chi tiết về các mục đã chọn từ dropdown màu sắc
    var selectedColorItems = selectColor[0].selectize.items.map(function (item) {
        var option = selectColor[0].selectize.getItem(item);
        return {id: item, name: option.text()};
    });

    // Lấy thông tin chi tiết về các mục đã chọn từ dropdown kích thước
    var selectedSizeItems = selectSize[0].selectize.items.map(function (item) {
        var option = selectSize[0].selectize.getItem(item);
        return {id: item, name: option.text()};
    });

    // Kiểm tra đầu vào
    if (!checkInputShowList(productName, productCategory, productMaterial, selectedColorItems, selectedSizeItems, productDescription, productPattern, productOrigin)) {
        return;
    } else {
        renderListProductDetail(selectedColorItems, selectedSizeItems, 10, 1000000);
    }
}


function renderListProductDetail(selectedColorItems, selectedSizeItems, quantity, price) {
    var sttCounter = 1;

    // Lấy giá trị của ô nhập liệu Tên sản phẩm
    var productName = $("#input-product-name").val();

    var listProductDetailShow = $("#listProductDetail");

    listProductDetailShow.empty();

    for (var i = 0; i < selectedColorItems.length; i++) {
        for (var j = 0; j < selectedSizeItems.length; j++) {

            var productDetail = {
                productId: null,
                quantity: quantity,
                price: price,
                colorId: selectedColorItems[i].id,
                sizeId: selectedSizeItems[j].id,
                image: ""
            };

            listProductDetail.push(productDetail);

            var productDetailRow = $(
                '<tr>\n' +
                '    <td class="stt">' + sttCounter + '</td>\n' +
                '    <td class="ten_danh_muc">' + productName + '</td>\n' +
                '    <td class="mau_sac">' + selectedColorItems[i].name + '</td>\n' +
                '    <td class="kich_thuoc">' + selectedSizeItems[j].name + '</td>\n' +
                '    <td class="anh_san_pham"><input type="file" class="product-image-input" accept="image/*"></td>\n' +
                '    <td class="so_luong"><input class="product-detail-input so_luong" type="number" value="' + productDetail.quantity + '" min="0"></td>\n' +
                '    <td class="gia_ban"><input class="product-detail-input gia_ban" type="number" value="' + productDetail.price + '" min="0"></td>\n' +
                '    <td class="table-td-center tinh_nang">\n' +
                '        <button class="btn btn-primary btn-sm trash" type="button" title="Xóa">' +
                '            <i class="fas fa-trash-alt"></i> Xóa\n' +
                '        </button>\n' +
                '    </td>' +
                '</tr>'
            );

            productDetailRow.data('productDetail', productDetail);

            $(productDetailRow).appendTo("#listProductDetail");

            sttCounter++;

            // Xử lý sự kiện chọn ảnh
            $(productDetailRow).find('.product-image-input').on('change', function () {
                var fileInput = this;
                if (fileInput.files && fileInput.files[0]) {
                    var formData = new FormData();
                    formData.append("file", fileInput.files[0]);

                    // Gửi ảnh lên server
                    $.ajax({
                        url: '/upload/image',
                        type: 'POST',
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function (response) {
                            var imageUrl = response.imageUrl;
                            var row = $(fileInput).closest('tr');
                            var productDetail = row.data('productDetail');

                            // Cập nhật URL ảnh trong productDetail
                            productDetail.image = imageUrl;
                            row.data('productDetail', productDetail);

                            listUrlImage.push(imageUrl);

                            $(fileInput).closest('td').html('<img src="' + imageUrl + '" class="product-image-preview" style="width: 50px; height: 50px;">');

                            console.log("Danh sách URL hình ảnh: ", listUrlImage);
                        },
                        error: function () {
                            alert("Có lỗi khi tải ảnh lên.");
                        }
                    });
                }
            });


            // Xử lý sự kiện thay đổi số lượng và giá
            $(productDetailRow).find('.product-detail-input').on('input', function () {
                var row = $(this).closest('tr');
                var productDetail = row.data('productDetail');

                if ($(this).hasClass('so_luong')) {
                    productDetail.quantity = $(this).val();
                } else if ($(this).hasClass('gia_ban')) {
                    productDetail.price = $(this).val();
                }

                row.data('productDetail', productDetail);
                var indexInList = row.index();
                listProductDetail[indexInList] = productDetail;

                console.log(listProductDetail);
            });

            // Xử lý sự kiện xóa
            $(productDetailRow).find('.trash').click(function () {
                var indexInList = $(this).closest('tr').index();
                listProductDetail.splice(indexInList, 1);
                $(this).closest('tr').remove();
                updateSTT();

                console.log(listProductDetail);
            });
        }
    }
}
// Hàm để cập nhật lại giá trị STT cho tất cả các hàng trong bảng
function updateSTT() {
    $('#listProductDetail tr').each(function (index, row) {
        $(row).find('.stt').text(index + 1);
    });
}

function save() {
    if (listProductDetail.length === 0) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Danh sách biến thể trống!'
        });
        return;
    }

    var productName = $("#input-product-name").val();
    var productCategory = $("#select-category").val();
    var productMaterial = $("#select-material").val();
    var productOrigin = $("#select-origin").val();
    var productPattern = $("#select-pattern").val();

    var productDescription = CKEDITOR.instances['input-product-description'].getData();
    productDescription = removeHtmlTags(productDescription);  // Nếu muốn loại bỏ thẻ HTML

    if (!productName || !productCategory || !productMaterial || !productOrigin || !productPattern) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Vui lòng điền đầy đủ thông tin!'
        });
        return;
    }

    var productStatus = 0;

    var dataToSend = {
        name: productName,
        description: productDescription,
        categoryId: productCategory,
        materialId: productMaterial,
        originId: productOrigin,
        patternId: productPattern,
        status: productStatus
    }

    // Gửi yêu cầu AJAX
    $.ajax({
        type: "POST",
        url: "/admin/rest/product/add",
        contentType: "application/json",
        data: JSON.stringify(dataToSend),
        success: function (response) {
            var productId = response.id;

            // Xử lý lưu chi tiết sản phẩm
            if (!saveProductDetail(productId)) {
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi!',
                    text: 'Có lỗi xảy ra khi lưu Danh sách biến thể!'
                });
                return;
            }

            Swal.fire({
                icon: 'success',
                title: 'Thành công!',
                text: 'Lưu Sản phẩm thành công!',
                didClose: function () {
                    window.location.href = "/admin/product";
                }
            });
        },
        error: function (error) {
            console.error("Lỗi khi lưu Sản phẩm:", error);

            Swal.fire({
                icon: 'error',
                title: 'Lỗi!',
                text: 'Có lỗi xảy ra khi lưu Sản phẩm!'
            });
        }
    });
}

function removeHtmlTags(str) {
    return str.replace(/<[^>]*>/g, ''); // Loại bỏ tất cả các thẻ HTML
}

// Check trùng Tên danh mục
function checkDuplicateProduct(productName) {
    var isDuplicateName;
    // Gửi yêu cầu AJAX để kiểm tra trùng tên danh mục
    $.ajax({
        type: "POST",
        url: "/admin/rest/add-product/checkDuplicateName",
        contentType: "application/json",
        data: JSON.stringify({name: productName}),
        async: false,
        success: function (response) {
            console.log("Kiểm tra trùng tên Sản phẩm thành công!");
            isDuplicateName = response.isDuplicateName;
        },
        error: function (error) {
            console.error("Lỗi khi kiểm tra trùng tên Sản phẩm:", error);
        }
    });
    return isDuplicateName;
}

function saveImage(productDetailId) {
    if (!listUrlImage || listUrlImage.length === 0) {
        console.error("Danh sách URL hình ảnh rỗng, không có dữ liệu để gửi.");
        return false;
    }

    for (var i = 0; i < listUrlImage.length; i++) {
        var isMainImage = (i === 0) ? 1 : 0; // Ảnh đầu tiên có is_main = 1, các ảnh còn lại có is_main = 0

        var dataToSend = {
            detailId: productDetailId,
            urlImage: listUrlImage[i],
            status: 0,
            isMain: isMainImage // Thêm thuộc tính isMain
        };

        console.log("Dữ liệu gửi:", dataToSend);
        console.log("Dữ liệu gửi:", JSON.stringify(dataToSend)); // In dữ liệu gửi đi trước khi gửi AJAX

        $.ajax({
            type: "POST",
            url: "/admin/rest/image/add",
            contentType: "application/json",
            data: JSON.stringify(dataToSend),
            success: function (response) {
                console.log("Lưu Ảnh thành công!");
            },
            error: function (error) {
                console.error("Lỗi khi lưu Ảnh:", error);
            }
        });
    }
    return true;
}

function saveProductDetail(productId) {
    for (var i = 0; i < listProductDetail.length; i++) {
        var dataToSend = {
            productId: productId,
            quantity: listProductDetail[i].quantity,
            price: listProductDetail[i].price,
            colorId: listProductDetail[i].colorId,
            sizeId: listProductDetail[i].sizeId,
            status: 0
        }

        console.log("Dữ liệu gửi:", JSON.stringify(dataToSend)); // In dữ liệu gửi đi trước khi gửi AJAX

        // Gửi yêu cầu AJAX
        $.ajax({
            type: "POST",
            url: "/admin/rest/product-detail/add",
            contentType: "application/json",
            data: JSON.stringify(dataToSend),
            success: function (response) {

                console.log("Lưu Danh sách biến thể thành công!");
                console.log("Lưu Sản phẩm thành công!");
                console.log("Phản hồi từ server:", response);
                var productId = response.id;
                if (!saveImage(productId)) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Lỗi!',
                        text: 'Có lỗi xảy ra khi lưu ảnh!'
                    });
                    return;
                }
            },
            error: function (error) {
                console.error("Lỗi khi lưu sách biến thể:", error);
                return false;
            }
        });
    }
    return true;
}