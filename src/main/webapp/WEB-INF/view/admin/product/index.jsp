<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html xmlns="http://www.w3.org/1999/xhtml">

            <head>
                <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
                <script src="/admin/ckeditor/ckeditor.js"></script>
                <title> Sản phẩm</title>
                <link href="/admin/css/styles.css" rel="stylesheet" />
                <link rel="stylesheet" type="text/css"
                    href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.min.css">
                <link rel="stylesheet" href="/admin/css/product/product.css">
            </head>

            <body onload="time()" class="app sidebar-mini rtl">
                <!-- Navbar-->
                <jsp:include page="../layout/header.jsp" />
                <!-- Sidebar menu-->
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">

                        <main class="app-content">
                            <div class="container-fluid">
                                <h1 class="mt-4">Dashboard</h1>
                                <ol class="breadcrumb mb-">
                                    <li class="breadcrumb-item active">
                                        <a href="/admin" style="text-decoration: none;">Dashboard</a>/Product
                                    </li>
                                </ol>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="tile">
                                        <div class="tile-body">
                                            <div class="row element-button mb-3">
                                                <div class="col-sm-2">
                                                    <a class="btn btn-add btn-sm"
                                                        href="${pageContext.request.contextPath}/admin/product/add"
                                                        id="showFormAdd" title="Thêm">
                                                        <i class="fas fa-plus"></i> Tạo mới
                                                    </a>
                                                </div>
                                            </div>

                                            <!-- Modal Sản Phẩm -->
                                            <div class="modal fade" id="ProductModal" tabindex="-1" role="dialog"
                                                aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                <div class="modal-dialog" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">Chỉnh sửa thông tin Sản Phẩm</h5>
                                                        </div>
                                                        <div class="row modal-body">
                                                            <!--Tên sản phẩm-->
                                                            <div class="form-group col-md-6">
                                                                <label class="control-label">Tên sản phẩm</label>
                                                                <input class="form-control" type="text"
                                                                    id="product-name">
                                                            </div>

                                                            <!--Danh mục-->
                                                            <div class="form-group col-md-6">
                                                                <label class="control-label">Danh mục</label>
                                                                <select class="form-control" id="product-category">
                                                                    <c:forEach var="category" items="${listCategory}">
                                                                        <option value="${category.id}">
                                                                            ${category.categoryName}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>

                                                            <div class="form-group col-md-6">
                                                                <label class="control-label">Nguồn gốc</label>
                                                                <select class="form-control" id="product-origin">
                                                                    <c:forEach var="origin" items="${listOrigin}">
                                                                        <option value="${origin.originId}">
                                                                            ${origin.originName}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>

                                                            <div class="form-group col-md-6">
                                                                <label class="control-label">Mẫu sản phẩm</label>
                                                                <select class="form-control" id="product-pattern">
                                                                    <c:forEach var="pattern" items="${listPattern}">
                                                                        <option value="${pattern.id}">
                                                                            ${pattern.patternName}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>


                                                            <!--Chất liệu-->
                                                            <div class="form-group col-md-6">
                                                                <label class="control-label">Chất liệu</label>
                                                                <select class="form-control" id="product-material">
                                                                    <c:forEach var="material" items="${listMaterial}">
                                                                        <option value="${material.id}">
                                                                            ${material.materialName}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>

                                                            <!--ảnh-->
                                                            <div class="form-group col-md-12">
                                                                <label class="control-label">Ảnh sản phẩm</label>
                                                                <div id="myfileupload">
                                                                    <input type="file" id="uploadfile"
                                                                        name="ImageUpload" multiple
                                                                        onchange="readURL(this)" />
                                                                </div>
                                                                <div id="thumbbox" class="d-flex">

                                                                </div>

                                                            </div>


                                                            <!--Mô tả-->
                                                            <div class="form-group col-md-12">
                                                                <label class="control-label">Mô tả sản phẩm</label>
                                                                <textarea class="form-control ckeditor"
                                                                    id="product-description"></textarea>
                                                            </div>
                                                        </div>
                                                        <div class="form-group modal-footer">
                                                            <button type="button" class="btn btn-success"
                                                                onclick="save()">Lưu</button>
                                                            <button type="button" class="btn btn-secondary"
                                                                data-dismiss="modal" id="closeFormProduct">Đóng</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Table -->
                                            <table class="table table-hover table-bordered js-copytextarea"
                                                cellpadding="0" cellspacing="0" border="0" id="sampleTable">


                                                <thead>
                                                    <tr>
                                                        <th class="stt" width="20">STT</th>
                                                        <th class="ten_san_pham" width="250">Tên Sản Phẩm</th>
                                                        <th class="anh">Ảnh</th>
                                                        <th class="mo_ta" width="250">Mô Tả</th>
                                                        <th class="so_luong_ton" width="90">Số Lượng Tồn</th>
                                                        <th class="trang_thai" width="90">Trạng Thái</th>
                                                        <th class="tinh_nang" width="90">Tính Năng</th>
                                                    </tr>
                                                </thead>

                                                <tbody>
                                                    <c:if test="${empty lists}">
                                                        <tr>
                                                            <td colspan="7" class="text-center">Danh sách trống!</td>
                                                        </tr>
                                                    </c:if>

                                                    <c:forEach var="product" items="${lists}" varStatus="status">
                                                        <tr>
                                                            <td class="stt">${status.index + 1}</td>
                                                            <!-- Sử dụng status.index để lấy chỉ số của vòng lặp -->
                                                            <td class="ten_san_pham">${product.name}</td>
                                                            <td class="anh"><img src="/images/product/${product.image}"
                                                                    alt="Hình ảnh sản phẩm"></td>
                                                            <td class="mo_ta">${product.description}</td>
                                                            <td class="so_luong_ton">${product.quantity}</td>
                                                            <td class="trang_thai">
                                                                <label class="toggle">
                                                                    <input type="checkbox" onclick="toggleStatus(this)"
                                                                        data-product-id="${product.id}"
                                                                        id="${product.id}" ${product.status==0
                                                                        ? 'checked' : '' }>
                                                                    <span class="slider"></span>
                                                                </label>
                                                            </td>
                                                            <td class="table-td-center tinh_nang">
                                                                <button class="btn btn-info btn-sm" title="Chi tiết"
                                                                    data-product-id="${product.id}"
                                                                    onclick="getListProductDetail(this)">
                                                                    <i class="fa-solid fa-eye"></i> Xem
                                                                </button>

                                                                <button class="btn btn-warning btn-sm edit"
                                                                    title="Chỉnh sửa" data-product-id="${product.id}"
                                                                    onclick="updateProductForm(this)">
                                                                    <i class="fas fa-edit"></i> Sửa
                                                                </button>
                                                            </td>

                                                        </tr>
                                                    </c:forEach>

                                                </tbody>
                                            </table>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>

                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <!-- Tải jQuery trước -->
                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                <script type="text/javascript" charset="utf8"
                    src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
                <script>


                    $(document).ready(function () {
                        $('#sampleTable').DataTable();
                    });
                </script>
                <script src="/admin/js/product/product.js"></script>
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