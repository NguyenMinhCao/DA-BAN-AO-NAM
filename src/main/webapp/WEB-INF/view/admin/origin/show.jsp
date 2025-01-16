<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <title>Dashboard - Quản lý nguồn gốc</title>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="/admin/ckeditor/ckeditor.js"></script>
    z
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link href="/admin/css/styles.css" rel="stylesheet" />
    <link href="/admin/css/origin/origin.css" rel="stylesheet" />

    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">
<c:if test="${param.success == 'true'}">
    <script>
        Swal.fire({
            icon: 'success',
            title: 'Thành công!',
            text: 'Màu sắc đã được tạo thành công.',
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
                <h1 class="mt-4">Quản lý nguồn gốc</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item active">
                        <a href="/admin" style="text-decoration: none;">Dashboard</a> / Nguồn gốc
                    </li>
                </ol>

                <div class="container mt-5">
                    <div class="d-flex justify-content-between align-items-center">
                        <h3>Danh sách:</h3>
                        <form method="get" action="/admin/origin" class="d-flex align-items-center me-4">
                            <input
                                    type="text"
                                    name="originName"
                                    value="${originName}"
                                    placeholder="Tìm kiếm "
                                    class="form-control form-control-md me-3"
                                    style="width: 280px; border-radius: 6px; font-size: 1rem;"
                            >
                            <button type="submit" class="btn btn-success btn-md d-flex align-items-center px-3 py-2" style="font-size: 1rem;">
                                <i class="bi bi-search me-2"></i> Tìm kiếm
                            </button>
                        </form>
                        <a href="/admin/origin/create" class="btn btn-primary btn-md d-flex align-items-center px-3 py-2" style="font-size: 1rem;">
                            <i class="bi bi-plus-circle me-2"></i> Thêm mới
                        </a>
                    </div>

                    <hr>

                    <c:if test="${empty originPage.content}">
                        <div class="alert alert-warning text-center" role="alert">
                            Không có nguồn gốc nào phù hợp với từ khóa "<strong>${originName}</strong>".
                        </div>
                    </c:if>

                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">Số thứ tự</th>
                            <th scope="col">Tên</th>
                            <th scope="col">Trạng thái</th>
                            <th scope="col">Tính năng</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${originPage.content}" var="origin">
                            <tr>
                                <th scope="row">${origin.originId}</th>
                                <td>${origin.originName}</td>
                                <td class="trang_thai">
                                    <label class="toggle">
                                        <input type="checkbox" onclick="toggleStatus(this)"
                                               data-origin-id="${origin.originId}"
                                               id="${origin.originId}" ${origin.status == 0 ? 'checked' : ''}>
                                        <span class="slider"></span>
                                    </label>
                                </td>
                                <td>
                                    <a href="/admin/origin/edit/${origin.originId}" class="btn btn-warning" title="Cập nhật">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <c:if test="${not empty originPage}">

                                <c:if test="${originPage.hasPrevious()}">
                                    <li class="page-item">
                                        <a class="page-link" href="/admin/origin?originName=${originName}&page=${originPage.number - 1}">&laquo;</a>
                                    </li>
                                </c:if>

                                <c:if test="${originPage.totalPages > 0}">
                                    <c:forEach var="i" begin="0" end="${originPage.totalPages - 1}">
                                        <li class="page-item ${originPage.number == i ? 'active' : ''}">
                                            <a class="page-link" href="/admin/origin?originName=${originName}&page=${i}">
                                                    ${i + 1}
                                            </a>
                                        </li>
                                    </c:forEach>
                                </c:if>

                                <c:if test="${originPage.hasNext()}">
                                    <li class="page-item">
                                        <a class="page-link" href="/admin/origin?originName=${originName}&page=${originPage.number + 1}">&raquo;</a>
                                    </li>
                                </c:if>
                            </c:if>
                        </ul>
                    </nav>

                </div>
            </div>
        </main>

        <jsp:include page="../layout/footer.jsp" />
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="/admin/js/scripts.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/origin/origin.js"></script>

</body>
</html>
