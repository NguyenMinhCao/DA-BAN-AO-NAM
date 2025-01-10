<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Danh Sách Biến Thể Sản Phẩm</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/css/selectize.min.css" rel="stylesheet">
    <script src="/admin/ckeditor/ckeditor.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/js/standalone/selectize.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

    <link href="/admin/css/styles.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/product/product-detail.css">
</head>

<body onload="time()" class="app sidebar-mini rtl">
<jsp:include page="../layout/header.jsp" />
<!-- Sidebar menu--><div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
    <div id="layoutSidenav_content">
<main class="content">
    <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
            <li class="breadcrumb-item active"><a href="#"><b>Danh Sách Biến Thể Sản Phẩm</b></a></li>
        </ul>
        <div id="clock"></div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <h3 class="tile-title">Thông tin Sản Phẩm</h3>
                <div class="tile-body">
                    <form class="row">
                        <!-- ID Sản Phẩm -->
                        <div class="col-md-12">
                            <label class="control-label">ID sản phẩm: </label>
                            <label id="product-id" data-product-id="${product.id}">${product.id}</label>
                        </div>

                        <!-- Tên Sản Phẩm -->
                        <div class="col-md-12">
                            <label class="control-label">Tên sản phẩm: </label>
                            <label>${product.name}</label>
                        </div>

                        <div class="col-md-12">
                            <label class="control-label">Ảnh sản phẩm:</label>
                            <div id="thumbbox" class="d-flex flex-wrap"></div>
                        </div>

                        <!-- Số lượng -->
                        <div class="col-md-12">
                            <label class="control-label">Thống kê số lượng:</label>

                            <!-- Số lượng thông qua màu -->
                            <div class="d-flex row-quantity">
                                <div class="col-md-2 color-quantity">
                                    <select class="form-control" id="select-color-quantity" onchange="getQuantityByColor(this)">
                                        <option value="" disabled selected hidden>Chọn màu</option>
                                        <c:forEach var="color" items="${listColor}">
                                            <option value="${color.id}">${color.colorName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-2 div-quantity" id="div-quantity-color">
                                    <label id="label-quantity-color" class="label-quantity"></label>
                                </div>
                            </div>

                            <!-- Số lượng thông qua kích thước -->
                            <div class="d-flex row-quantity">
                                <div class="col-md-2 color-quantity">
                                    <select class="form-control" id="select-size-quantity" onchange="getQuantityBySize(this)">
                                        <option value="" disabled selected hidden>Chọn kích thước</option>
                                        <c:forEach var="size" items="${listSize}">
                                            <option value="${size.id}">${size.sizeName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-2 div-quantity" id="div-quantity-size">
                                    <label id="label-quantity-size" class="label-quantity"></label>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <a class="btn btn-cancel" href="${pageContext.request.contextPath}/admin/product">Quay lại</a>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="tile-body">
                    <div class="row element-button">
                        <div class="col-sm-2">
                            <a class="btn btn-add btn-sm" id="showFormProductDetailAdd" title="Thêm"><i class="fas fa-plus"></i> Tạo mới</a>
                        </div>
                    </div>

                    <!-- Modal Update Biến Thể -->
                    <div class="modal fade" id="UpdateProductDetailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Chỉnh sửa thông tin Biến Thể</h5>
                                </div>
                                <div class="row modal-body">
                                    <div class="form-group col-md-12 product-detail-infor">
                                        <label class="control-label" id="product-detail-color">Màu Sắc: </label>
                                        <br>
                                        <label class="control-label" id="product-detail-size">Kích Thước: </label>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Số Lượng</label>
                                        <input class="form-control" type="number" id="product-detail-quantity" min="0">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Giá (VNĐ)</label>
                                        <input class="form-control" type="number" id="product-detail-price" min="0">
                                    </div>
                                </div>
                                <div class="form-group modal-footer">
                                    <button type="button" class="btn btn-success" onclick="saveUpdate()">Lưu</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="closeFormProductDetailUpdate">Đóng</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Modal Add Biến Thể -->
                    <div class="modal fade" id="AddProductDetailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Thêm Biến Thể</h5>
                                </div>
                                <div class="row modal-body">
                                    <div class="form-group col-md-6">
                                        <label for="select-color" class="control-label">Màu Sắc</label>
                                        <div class="d-flex">
                                            <select class="form-control" id="select-color" onchange="getColor(this)">
                                                <option value="" disabled selected hidden>-- Chọn màu sắc --</option>
                                                <c:forEach var="color" items="${listColor}">
                                                    <option value="${color.id}">${color.colorName}</option>
                                                </c:forEach>
                                            </select>
                                            <button id="showFormColor" class="btn add-button" type="button"><i class="fa-solid fa-plus fa-2xl add-icon"></i></button>
                                        </div>
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label for="select-size" class="control-label">Kích thước</label>
                                        <div class="d-flex">
                                            <select class="form-control" id="select-size" onchange="getSize(this)">
                                                <option value="" disabled selected hidden>-- Chọn kích thước --</option>
                                                <c:forEach var="size" items="${listSize}">
                                                    <option value="${size.id}">${size.sizeName}</option>
                                                </c:forEach>
                                            </select>
                                            <button id="showFormSize" class="btn add-button" type="button"><i class="fa-solid fa-plus fa-2xl add-icon"></i></button>
                                        </div>
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label class="control-label">Số Lượng</label>
                                        <input class="form-control" type="number" id="add-product-detail-quantity">
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label class="control-label">Giá (VNĐ)</label>
                                        <input class="form-control" type="number" id="add-product-detail-price">
                                    </div>
                                </div>
                                <div class="form-group modal-footer">
                                    <button type="button" class="btn btn-success" onclick="saveAdd()">Lưu</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="closeFormProductDetailAdd">Đóng</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Table -->
                    <table class="table table-hover table-bordered js-copytextarea" cellpadding="0" cellspacing="0" border="0" id="sampleTable">
                        <thead>
                        <tr>
                            <th class="stt" width="30">STT</th>
                            <th class="mau_sac">Màu Sắc</th>
                            <th class="kich_thuoc">Kích Thước</th>
                            <th class="so_luong" width="130">Số Lượng</th>
                            <th class="gia_ban" width="220">Giá Bán</th>
                            <!-- Thêm các cột khác nếu cần -->
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="productDetail" items="${lists}">
                            <tr>
                                <td>${productDetail.id}</td>
                                <td>${productDetail.colorName}</td>
                                <td>${productDetail.sizeName}</td>
                                <td>${productDetail.quantity}</td>
                                <td>${productDetail.price}</td>
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
<script>
    $(document).ready(function () {
        // Lấy giá trị productId từ backend
        var productId = "${product.id}";

        if (!productId) {
            console.error("Product ID is not available.");
            return;
        }

        // Gọi hàm lấy danh sách URL ảnh
        getListURL(productId);
    });
</script>
<script src="${pageContext.request.contextPath}/admin/js/product/product-detail.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/category/category.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/color/color.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/material/material.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/size/size.js"></script>

</body>
</html>
