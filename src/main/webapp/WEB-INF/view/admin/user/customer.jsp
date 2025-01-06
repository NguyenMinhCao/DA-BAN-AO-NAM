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
    <link href="/admin/css/user/customer.css" rel="stylesheet"/>
    <link href="/admin/css/styles.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/css/style1.css"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" rel="stylesheet">
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp"/>

    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h3 class="mt-4">Danh sách khách hàng</h3>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item active">
                        <a href="/admin" style="text-decoration: none;">Admin</a> /
                        Customer
                    </li>
                    <div class="invoice-container">
                        <div class="status-add">
                            <div class="radio-status">
                                <button id="btnNewCustomer">+Thêm mới khách hàng</button>
                            </div>
                            <div class="radio-status">
                                <input type="radio" name="statusCustomer" checked>
                                <label>Tất cả</label>
                            </div>
                            <div class="radio-status">
                                <input type="radio" name="statusCustomer">
                                <label>Hoạt động</label>
                            </div>
                            <div class="radio-status">
                                <input type="radio" name="statusCustomer">
                                <label>Không hoạt động</label>
                            </div>
                            <div class="search">
                                <input type="text" id="search" placeholder="Nhập tên để tìm kiếm" class="form-control">
                                <button id="btnSearch" class="btn btn-primary">Tìm kiếm</button>
                            </div>
                        </div>
                        <div class="container-table">
                            <table class="table" id="tableSample">
                                <thead class="table-light">
                                <tr>
                                    <th>Ảnh</th>
                                    <th>Họ tên</th>
                                    <th>SDT</th>
                                    <th>Email</th>
                                    <th>Trạng thái</th>
                                    <th>Tính năng</th>
                                </tr>
                                </thead>
                                <tbody >
                                </tbody>
                            </table>
                        </div>
                        <div id="pagination-customer" class="pagination"></div>
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
<script src="https://printjs-4de6.kxcdn.com/print.min.js"></script>
<script src="/admin/js/user/customer.js"></script>
</body>
</html>