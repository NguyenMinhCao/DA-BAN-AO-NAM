function toggleStatus(checkbox) {
    var patternId = checkbox.getAttribute("data-pattern-id");
    // Gửi yêu cầu AJAX để cập nhật trạng thái của màu sắc
    //Sử dụng jQuery
    $.ajax({
        type: "POST",
        url: "/admin/rest/pattern/setStatus/" + patternId,
        success: function (response) {
            // Xử lý thành công, nếu cần
            console.log("Cập nhật trạng thái thành công");

            // Hiển thị thông báo thành công sử dụng SweetAlert2
            Swal.fire({
                icon: 'success',
                title: 'Thành công!',
                text: 'Trạng thái đã được cập nhật thành công!'
            });

        },
        error: function (error) {
            // Xử lý lỗi, nếu cần
            console.error("Lỗi khi cập nhật trạng thái");

            // Hiển thị thông báo thất bại sử dụng SweetAlert2
            Swal.fire({
                icon: 'error',
                title: 'Lỗi!',
                text: 'Có lỗi xảy ra khi cập nhật trạng thái.'
            });
        }
    });
}
$.ajax({
    type: "POST",
    url: "/admin/rest/pattern/setStatus/" + pattern,
    beforeSend: function () {
        console.log("Gửi yêu cầu cập nhật trạng thái cho originId:", pattern);
    },
    success: function (response) {
        console.log("Cập nhật trạng thái thành công:", response);
        Swal.fire({
            icon: 'success',
            title: 'Thành công!',
            text: 'Trạng thái đã được cập nhật thành công!'
        });
    },
    error: function (error) {
        console.error("Lỗi khi cập nhật trạng thái:", error.responseText);
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Có lỗi xảy ra khi cập nhật trạng thái.'
        });
    }
});