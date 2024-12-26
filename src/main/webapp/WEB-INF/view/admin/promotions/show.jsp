<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
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
                        <a href="/admin" style="text-decoration: none;">Dashboard</a> / Discount
                    </li>
                </ol>
                <div class="container mt-5">
                    <div class="d-flex justify-content-between">
                        <h3>Table discount</h3>
                        <a href="/view-add" class="btn btn-primary">Create a discount</a>
                    </div>
                    <hr>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Discount Type</th>
                            <th scope="col">Start Date</th>
                            <th scope="col">End Date</th>
                            <th scope="col">Discount value</th>
                            <th scope="col">Status</th>
                            <th scope="col">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listKM.content}" var="km">
                            <tr>
                                <th scope="row">${km.id}</th>
                                <td>${km.discountType}</td>
                                <td>${km.startDate}</td>
                                <td>${km.endDate}</td>
                                <td>${km.discountValue}</td>
                                <td>${km.status}</td>
                                <td>
                                    <a href="/view-updateKM/${km.id}" class="btn btn-success">View</a>
                                    <a href="/view-update/${km.id}" class="btn btn-warning">Update</a>
                                    <a href="/remove/${km.id}" class="btn btn-danger">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <nav aria-label="Page navigation example">
                        <ul class="pagination">
                            <c:if test="${listKM != null && listKM.totalPages > 0}">
                                <c:forEach begin="0" end="${listSanPham.totalPages - 1}" varStatus="loop">
                                    <li class="page-item">
                                        <a class="page-link" href="/hien-thiKM?page=${loop.begin + loop.count - 1}">
                                                ${loop.begin + loop.count}
                                        </a>
                                    </li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp" />


    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"
        crossorigin="anonymous"></script>
<script src="/admin/js/chart-area-demo.js"></script>
<script src="/admin/js/chart-bar-demo.js"></script>
<script src="/admin/js/chart-pie-demo.js"></script>
<script src="/admin/js/datatables-demo.js"></script>
<script src="/admin/js/datatables-simple-demo.js"></script>
<script src="/admin/js/scripts.js"></script>
</body>

</html>