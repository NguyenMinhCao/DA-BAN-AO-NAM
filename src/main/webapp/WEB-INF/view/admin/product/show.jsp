<<<<<<< HEAD
<<<<<<< HEAD
<%--<%@page contentType="text/html" pageEncoding="UTF-8" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ page import="java.net.URLEncoder" %>--%>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
=======
<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page import="java.net.URLEncoder" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
>>>>>>> 2299bd1b23544ec87c60536a979040c48b829689

                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="" />
                    <meta name="author" content="" />
                    <title>Dashboard - SB Admin</title>
                    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
                    <link href="/admin/css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                </head>

                <body class="sb-nav-fixed">
                    <c:if test="${param.success == 'true'}">
                        <script>
                            Swal.fire({
                                icon: 'success',
                                title: 'Thành công!',
                                text: 'Sản phẩm đã được tạo thành công.',
                                showConfirmButton: true
                            });
                        </script>
                    </c:if>
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <jsp:include page="../layout/sidebar.jsp" />
                        <div id="layoutSidenav_content">
                            <main>
                                <div class="container-fluid px-4">
                                    <h1 class="mt-4">Dashboard</h1>
                                    <ol class="breadcrumb mb-4">
                                        <li class="breadcrumb-item active">
                                            <a href="/admin" style="text-decoration: none;">Dashboard</a> / Product
                                        </li>
                                    </ol>
                                    <div class="container mt-5">
                                        <div class="d-flex justify-content-between">
                                            <h3>Sản Phẩm</h3>
                                            <a href="/admin/product/create" class="btn btn-primary">Create a product</a>
                                        </div>
                                        <hr>
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Số thứ tự </th>
                                                    <th scope="col">Tên</th>
                                                    <th scope="col">Ảnh</th>
                                                    <th scope="col">Giá </th>
                                                    <th scope="col">Số lượng</th>
                                                    <th scope="col">Tính năng</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${productPage.content}" var="product">
                                                    <tr>
                                                        <th scope="row">${product.id}</th>
                                                        <td>${product.name}</td>
                                                        <td class="text-center">
                                                            <c:if test="${not empty product.images}">
                                                                <c:forEach var="image" items="${product.images}">
                                                                    <c:if test="${image.isMain}">
                                                                        <img src="${pageContext.request.contextPath}/images/product/${fn:escapeXml(URLEncoder.encode(image.imageUrl, 'UTF-8'))}"
                                                                            class="img-thumbnail"
                                                                            style="max-width: 100px;">
                                                                    </c:if>
                                                                </c:forEach>
                                                            </c:if>
                                                        </td>
                                                        <td>${product.price}</td>
                                                        <td>${product.quantity}</td>
                                                        <td>
                                                            <a href="/admin/product/detail/${product.id}"
                                                                class="btn btn-success" title="View">
                                                                <i class="fas fa-eye"></i> <!-- Icon mắt cho "View" -->
                                                            </a>
                                                            <a href="/admin/product/edit/${product.id}"
                                                                class="btn btn-warning" title="Update">
                                                                <i class="fas fa-edit"></i>
                                                                <!-- Icon bút chì cho "Update" -->
                                                            </a>
                                                            <a href="/admin/product/view-detele/${product.id}"
                                                                class="btn btn-danger" title="Delete">
                                                                <i class="fas fa-trash-alt"></i>
                                                                <!-- Icon thùng rác cho "Delete" -->
                                                            </a>
                                                        </td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>

                                        <!-- Pagination -->
                                        <div class="pagination">
                                            <!-- Nút "Trang trước" -->
                                            <c:if test="${productPage.hasPrevious()}">
                                                <a href="/admin/product?page=${productPage.number - 1}">Trang trước</a>
                                            </c:if>

                                            <!-- Hiển thị số trang -->
                                            <span>Trang ${productPage.number + 1} / ${productPage.totalPages}</span>

                                            <!-- Nút "Trang sau" -->
                                            <c:if test="${productPage.hasNext()}">
                                                <a href="/admin/product?page=${productPage.number + 1}">Trang sau</a>
                                            </c:if>
                                        </div>


<<<<<<< HEAD
<%--                        </ul>--%>
<%--                    </nav>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </main>--%>
<%--        <jsp:include page="../layout/footer.jsp" />--%>
<%--    </div>--%>
<%--</div>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>--%>
<%--<script src="/admin/js/chart-area-demo.js"></script>--%>
<%--<script src="/admin/js/chart-bar-demo.js"></script>--%>
<%--<script src="/admin/js/chart-pie-demo.js"></script>--%>
<%--<script src="/admin/js/datatables-demo.js"></script>--%>
<%--<script src="/admin/js/datatables-simple-demo.js"></script>--%>
<%--<script src="/admin/js/scripts.js"></script>--%>
<%--</body>--%>
<%--</html>--%>
=======
<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page import="java.net.URLEncoder" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="" />
                    <meta name="author" content="" />
                    <title>Dashboard - SB Admin</title>
                    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
                    <link href="/admin/css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                </head>

                <body class="sb-nav-fixed">
                    <c:if test="${param.success == 'true'}">
                        <script>
                            Swal.fire({
                                icon: 'success',
                                title: 'Thành công!',
                                text: 'Sản phẩm đã được tạo thành công.',
                                showConfirmButton: true
                            });
                        </script>
                    </c:if>
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <jsp:include page="../layout/sidebar.jsp" />
                        <div id="layoutSidenav_content">
                            <main>
                                <div class="container-fluid px-4">
                                    <h1 class="mt-4">Dashboard</h1>
                                    <ol class="breadcrumb mb-4">
                                        <li class="breadcrumb-item active">
                                            <a href="/admin" style="text-decoration: none;">Dashboard</a> / Product
                                        </li>
                                    </ol>
                                    <div class="container mt-5">
                                        <div class="d-flex justify-content-between">
                                            <h3>Sản Phẩm</h3>
                                            <a href="/admin/product/create" class="btn btn-primary">Create a product</a>
                                        </div>
                                        <hr>
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Số thứ tự </th>
                                                    <th scope="col">Tên</th>
                                                    <th scope="col">Ảnh</th>
                                                    <th scope="col">Giá </th>
                                                    <th scope="col">Số lượng</th>
                                                    <th scope="col">Tính năng</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${productPage.content}" var="product">
                                                    <tr>
                                                        <th scope="row">${product.id}</th>
                                                        <td>${product.name}</td>
                                                        <td class="text-center">
                                                            <c:if test="${not empty product.images}">
                                                                <c:forEach var="image" items="${product.images}">
                                                                    <c:if test="${image.isMain}">
                                                                        <img src="${pageContext.request.contextPath}/images/product/${fn:escapeXml(URLEncoder.encode(image.imageUrl, 'UTF-8'))}"
                                                                            class="img-thumbnail"
                                                                            style="max-width: 100px;">
                                                                    </c:if>
                                                                </c:forEach>
                                                            </c:if>
                                                        </td>
                                                        <td>${product.price}</td>
                                                        <td>${product.quantity}</td>
                                                        <td>
                                                            <a href="/admin/product/detail/${product.id}"
                                                                class="btn btn-success" title="View">
                                                                <i class="fas fa-eye"></i> <!-- Icon mắt cho "View" -->
                                                            </a>
                                                            <a href="/admin/product/edit/${product.id}"
                                                                class="btn btn-warning" title="Update">
                                                                <i class="fas fa-edit"></i>
                                                                <!-- Icon bút chì cho "Update" -->
                                                            </a>
                                                            <a href="/admin/product/view-detele/${product.id}"
                                                                class="btn btn-danger" title="Delete">
                                                                <i class="fas fa-trash-alt"></i>
                                                                <!-- Icon thùng rác cho "Delete" -->
                                                            </a>
                                                        </td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>

                                        <!-- Pagination -->
                                        <div class="pagination">
                                            <!-- Nút "Trang trước" -->
                                            <c:if test="${productPage.hasPrevious()}">
                                                <a href="/admin/product?page=${productPage.number - 1}">Trang trước</a>
                                            </c:if>

                                            <!-- Hiển thị số trang -->
                                            <span>Trang ${productPage.number + 1} / ${productPage.totalPages}</span>

                                            <!-- Nút "Trang sau" -->
                                            <c:if test="${productPage.hasNext()}">
                                                <a href="/admin/product?page=${productPage.number + 1}">Trang sau</a>
                                            </c:if>
                                        </div>


=======
>>>>>>> 2299bd1b23544ec87c60536a979040c48b829689
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

<<<<<<< HEAD
                </html>
>>>>>>> master
=======
                </html>
>>>>>>> 2299bd1b23544ec87c60536a979040c48b829689
