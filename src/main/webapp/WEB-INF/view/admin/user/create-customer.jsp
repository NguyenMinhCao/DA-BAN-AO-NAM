<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Dashboard - SB Admin</title>
    <link href="/admin/css/user/create-customer.css" rel="stylesheet"/>
    <link href="/admin/css/styles.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/css/style1.css"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" rel="stylesheet">
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.datatables.net/2.1.8/css/dataTables.dataTables.min.css">
</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp"/>

    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h3 class="mt-4">Thêm mới khách hàng</h3>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item active">
                        <a href="/admin" style="text-decoration: none;">Admin</a> /
                        Customer
                    </li>
                    <div class="container">
                        <div class="base-information">
                            <h4>Thông tin cơ bản</h4>
                            <div class="form-group-wrapper">
                                <div class="item-group">
                                    <label for="customerName" class="form-label">Họ tên</label>
                                    <input type="text" class="form-control" id="customerName"
                                           placeholder="Nhập họ tên" autocomplete="on">
                                </div>
                                <div class="item-group">
                                    <label for="customerPhone" class="form-label">SDT</label>
                                    <input type="text" class="form-control" id="customerPhone"
                                           placeholder="Nhập số điện thoại" autocomplete="on">
                                </div>
                            </div>
                            <div class="form-group-wrapper">
                                <div class="item-group">
                                    <label for="customerEmail" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="customerEmail"
                                           placeholder="Nhập email" autocomplete="on">
                                </div>
                                <div class="item-group">
                                    <label for="customerDob" class="form-label">Ngày sinh</label>
                                    <input type="date" class="form-control" id="customerDob">
                                </div>
                            </div>
                            <div class="form-group-wrapper">
                                <div class="item-group">
                                    <label class="" for="customerImage">Ảnh</label>
                                    <input type="file" class="form-control" id="customerImage">
                                </div>
                                <div class="item-group gender">
                                    <label for="customerDob" class="form-label">Giới tính</label>
                                    <input type="radio" name="customerGender" class="form-check-input" value="true">Nam
                                    <input type="radio" name="customerGender" class="form-check-input" value="false">Nữ
                                </div>
                            </div>
                        </div>
                        <div class="address-information">
                            <h4>Địa chỉ nhận hàng</h4>
                            <div class="form-group-wrapper">
                                <div class="item-group">
                                    <label for="customerName" class="form-label">Họ tên</label>
                                    <input type="text" class="form-control" id="addressCustomerName"
                                           placeholder="Nhập họ tên">
                                </div>
                                <div class="item-group">
                                    <label for="customerPhone" class="form-label">SDT</label>
                                    <input type="text" class="form-control" id="addressCustomerPhone"
                                           placeholder="Nhập số điện thoại">
                                </div>
                            </div>
                            <div class="form-group-wrapper">
                                <div class="item-group">
                                    <label for="customerProvince" class="form-label">Tỉnh/Thành</label>
                                    <select id="customerProvince" class="form-select">
                                        <option disabled>select-province</option>
                                    </select>
                                </div>
                                <div class="item-group">
                                    <label for="customerDistrict" class="form-label">Quận/Huyện</label>
                                    <select id="customerDistrict" class="form-select">
                                        <option disabled>select-district</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group-wrapper">
                                <div class="item-group">
                                    <label for="customerWard" class="form-label">Phường/Xã</label>
                                    <select id="customerWard" class="form-select">
                                        <option disabled>select-ward</option>
                                    </select>
                                </div>
                                <div class="item-group gender">
                                    <label for="addressDetail" class="form-label">Địa chỉ chi tiết</label>
                                    <input type="text" class="form-control" id="addressDetail"
                                           placeholder="Nhập địa chỉ chi tiết">
                                </div>
                            </div>
                        </div>
                        <div class="btn-add">
                            <button id="save">Lưu</button>
                        </div>
                    </div>
                </ol>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp"/>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/2.1.8/js/dataTables.min.js"></script>
<script src="https://printjs-4de6.kxcdn.com/print.min.js"></script>
<script src="/admin/js/user/create-customer.js"></script>
</body>
</html>