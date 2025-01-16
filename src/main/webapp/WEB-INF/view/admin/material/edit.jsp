<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh sửa màu sắc</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link href="/admin/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<c:if test="${param.success == 'true'}">
    <script>
        Swal.fire({
            icon: 'success',
            title: 'Thành công!',
            text: 'Màu sắc đã được cập nhật thành công.',
            showConfirmButton: true
        });
    </script>
</c:if>

<c:if test="${not empty errorMessage}">
    <script>
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: '${errorMessage}',
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
                <h1 class="mt-4">Chỉnh sửa chất liệu</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item">
                        <a href="/admin" style="text-decoration: none;">Dashboard</a>
                    </li>
                    <li class="breadcrumb-item active">Chỉnh sửa chất liệu</li>
                </ol>

                <div class="card">
                    <div class="card-header">
                        <h3>Cập nhật thông tin </h3>
                    </div>
                    <div class="card-body">
                        <form action="/admin/material/edit/${material.id}" method="post">
                            <input type="hidden" name="id" value="${material.id}" />

                            <div class="mb-3">
                                <label for="materialName" class="form-label">Tên Chất liệu</label>
                                <input type="text" id="materialName" name="materialName" class="form-control" value="${material.materialName}" required />
                            </div>

                            <div class="mb-3">
                                <label for="status" class="form-label">Trạng thái</label>
                                <select id="status" name="status" class="form-select">
                                    <option value="0" ${material.status == '0' ? 'selected' : ''}>Hoạt động</option>
                                    <option value="1" ${material.status == '1' ? 'selected' : ''}>Không hoạt động</option>
                                </select>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="/admin/material" class="btn btn-warning">Quay lại</a>
                                <button type="submit" class="btn btn-primary">Cập nhật</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp" />
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="/admin/js/scripts.js"></script>
</body>
</html>
