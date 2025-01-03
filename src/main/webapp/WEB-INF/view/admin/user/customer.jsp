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
    <link rel="stylesheet" href="https://cdn.datatables.net/2.1.8/css/dataTables.dataTables.min.css">
</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp"/>

    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Danh sách khách hàng</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item active">
                        <a href="/admin" style="text-decoration: none;">Admin</a> /
                        Customer
                    </li>
                    <div class="invoice-container">
                        <div class="status-add">
                            <div class="radio-status">
                                <button>+Thêm mới khách hàng</button>
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
                        </div>
                        <div class="container-table">
                            <table id="sampleTable">
                                <thead>
                                <tr>
                                    <th>Ảnh</th>
                                    <th>Họ tên</th>
                                    <th>SDT</th>
                                    <th>Email</th>
                                    <th>Trạng thái</th>
                                    <th>Tính năng</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
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

<script>
    let data = [
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        },
        {
            image: 'simple',
            name: 'Vũ Tuấn Phong',
            sdt: '0328822592',
            email: 'vutuanphong1782004@gmail.com',
            status: false
        }
    ]
    for (let i = 0; i < data.length; i++) {
        let row = `
        <tr>
<td>${data[i].image}</td>
<td>${data[i].name}</td>
<td>${data[i].sdt}</td>
<td>${data[i].email}</td>
<td>${data[i].status}</td>
</tr>
        `
        console.log(row)
        $('#sampleTable tbody').append(row)
    }
    // $('#sampleTable').DataTable();
</script>
</body>
</html>