<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Dashboard - SB Admin</title>
    <link href="/admin/css/styles.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(() => {
            const avatarFile = $("#avatarFile");
            avatarFile.change(function (e) {
                const imgURL = URL.createObjectURL(e.target.files[0]);
                $("#avatarPreview").attr("src", imgURL);
                $("#avatarPreview").css({ "display": "block" });
            });
        });
    </script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <!-- <link rel="stylesheet" href="/css/demo.css"> -->
</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />

    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Dashboard</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item active">
                        <a href="/admin" style="text-decoration: none;">Dashboard</a> / View
                    </li>
                </ol>
                <div class="container mt-4">
                    <div class="row">
                        <!-- Thông tin giảm giá -->
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">Thông Tin Giảm Giá</div>
                                <div class="card-body">
                                    <form modelAttribute="promo" action="/hien-thiKM" method="get">
                                        <div class="mb-3">
                                            <label for="discountType" class="form-label">Tên Giảm Giá:</label>
                                            <input type="text" id="discountType" class="form-control" value="${promo.discountType}" readonly>
                                        </div>
                                        <div class="mb-3">
                                            <label for="discountValue" class="form-label">Phần Trăm Giảm(%):</label>
                                            <input type="number" id="discountValue" class="form-control" value="${promo.discountValue}" readonly>
                                        </div>
                                        <div class="mb-3">
                                            <label for="startDate" class="form-label">Ngày Bắt Đầu:</label>
                                            <input type="text" id="startDate" class="form-control" value="${promo.startDate}" readonly>
                                        </div>
                                        <div class="mb-3">
                                            <label for="endDate" class="form-label">Ngày Kết Thúc:</label>
                                            <input type="text" id="endDate" class="form-control" value="${promo.endDate}" readonly>
                                        </div>
                                        <button type="submit" class="btn btn-success">Lưu</button>
                                        <button type="button" class="btn btn-danger">Quay lại</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!-- Danh sách áp dụng -->
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">Danh Sách Áp Dụng</div>
                                <div class="card-body">
                                    <button class="btn btn-success mb-3">+</button>
                                    <div class="table-responsive">
                                        <table class="table table-bordered">
                                            <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Tên Sản Phẩm</th>
                                                <th>Ảnh</th>
                                                <th>Số Lượng Tồn</th>
                                                <th>Tính Năng</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>1</td>
                                                <td>Giày búp bê ballet</td>
                                                <td><img src="https://via.placeholder.com/80" alt="Ảnh sản phẩm" class="img-thumbnail"></td>
                                                <td>200</td>
                                                <td><button class="btn btn-danger">Xóa</button></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <nav>
                                        <ul class="pagination justify-content-end">
                                            <li class="page-item"><a class="page-link" href="#">Lùi</a></li>
                                            <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                            <li class="page-item"><a class="page-link" href="#">Tiếp</a></li>
                                        </ul>
                                    </nav>
                                    <button class="btn btn-danger">Gỡ giảm giá</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        <jsp:include page="../layout/footer.jsp" />
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
</body>

</html>