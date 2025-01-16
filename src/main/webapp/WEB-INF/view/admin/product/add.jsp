<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

        <!DOCTYPE html>
        <html>

        <head>
            <link href="/admin/css/styles.css" rel="stylesheet" />
            <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link rel="stylesheet" href="/admin/css/product/product.css">
            <script src="https://cdn.jsdelivr.net/npm/moment@2.29.1/moment.min.js"></script>

            <link href="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/css/selectize.min.css"
                rel="stylesheet">
            <script src="/admin/ckeditor/ckeditor.js"></script>
            <script src="/admin/ckeditor/lang/vi.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

            <script
                src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/js/standalone/selectize.min.js"></script>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
                rel="stylesheet">
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

            <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/product/add-product.css">
            <link href="/admin/css/styles.css" rel="stylesheet" />

        </head>

        <body onload="time()" class="app sidebar-mini rtl">
            <jsp:include page="../layout/header.jsp" />
            <!-- Sidebar menu-->
            <div id="layoutSidenav">
                <jsp:include page="../layout/sidebar.jsp" />
                <div id="layoutSidenav_content">

                    <main class="content">
                        <div class="app-title">
                            <ul class="app-breadcrumb breadcrumb">
                                <li class="breadcrumb-item">Danh sách sản phẩm</li>
                                <li class="breadcrumb-item"><a href="#">Thêm sản phẩm</a></li>
                            </ul>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="tile">
                                    <h3 class="tile-title">Tạo mới sản phẩm</h3>
                                    <div class="tile-body">
                                        <form class="row">
                                            <div class="form-group col-md-3">
                                                <label class="control-label">Tên sản phẩm</label>
                                                <input class="form-control" id="input-product-name" type="text" />
                                            </div>

                                            <div class="form-group col-md-3">
                                                <label for="select-category" class="control-label">Danh mục</label>
                                                <div class="d-flex">
                                                    <select class="form-control" id="select-category">
                                                        <option value="" disabled selected hidden>-- Chọn danh mục --
                                                        </option>
                                                        <c:forEach var="category" items="${listCategory}">
                                                            <option value="${category.id}">${category.categoryName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <button id="showFormCategory" class="btn add-button"
                                                        type="button"><i
                                                            class="fa-solid fa-plus fa-2xl add-icon"></i></button>
                                                </div>
                                            </div>

                                            <div class="form-group col-md-3">
                                                <label for="select-material" class="control-label">Chất liệu</label>
                                                <div class="d-flex">
                                                    <select class="form-control" id="select-material">
                                                        <option value="" disabled selected hidden>-- Chọn chất liệu --
                                                        </option>
                                                        <c:forEach var="material" items="${listMaterial}">
                                                            <option value="${material.id}">${material.materialName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <button id="showFormMaterial" class="btn add-button"
                                                        type="button"><i
                                                            class="fa-solid fa-plus fa-2xl add-icon"></i></button>
                                                </div>
                                            </div>

                                            <div class="form-group col-md-3">
                                                <label for="select-colors" class="control-label">Màu sắc</label>
                                                <div class="d-flex">
                                                    <select class="form-control" id="select-colors"
                                                        placeholder="Chọn màu sắc..."></select>
                                                    <button id="showFormColor" class="btn add-button" type="button"><i
                                                            class="fa-solid fa-plus fa-2xl add-icon"></i></button>
                                                </div>
                                            </div>

                                            <div class="form-group col-md-3">
                                                <label for="select-sizes" class="control-label">Kích thước</label>
                                                <div class="d-flex">
                                                    <select class="form-control" id="select-sizes"
                                                        placeholder="Chọn kích thước..."></select>
                                                    <button id="showFormSize" class="btn add-button" type="button"><i
                                                            class="fa-solid fa-plus fa-2xl add-icon"></i></button>
                                                </div>
                                            </div>

                                            <div class="form-group col-md-3">
                                                <label for="select-origin" class="control-label">Nguồn gốc</label>
                                                <div class="d-flex">
                                                    <select class="form-control" id="select-origin">
                                                        <option value="" disabled selected hidden>-- Chọn nguồn gốc --
                                                        </option>
                                                        <c:forEach var="origin" items="${listOrigin}">
                                                            <option value="${origin.originId}">${origin.originName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <%-- <button id="showFormCategory" class="btn add-button"
                                                        type="button"><i
                                                            class="fa-solid fa-plus fa-2xl add-icon"></i></button>--%>
                                                </div>
                                            </div>

                                            <div class="form-group col-md-3">
                                                <label for="select-pattern" class="control-label">Mẫu sản phẩm</label>
                                                <div class="d-flex">
                                                    <select class="form-control" id="select-pattern">
                                                        <option value="" disabled selected hidden>-- Chọn mẫu --
                                                        </option>
                                                        <c:forEach var="pattern" items="${listPattern}">
                                                            <option value="${pattern.id}">${pattern.patternName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <%-- <button id="showFormCategory" class="btn add-button"
                                                        type="button"><i
                                                            class="fa-solid fa-plus fa-2xl add-icon"></i></button>--%>
                                                </div>
                                            </div>
                                            <div class="form-group col-md-12">
                                                <label class="control-label">Mô tả sản phẩm</label>
                                                <textarea class="form-control ckeditor"
                                                    id="input-product-description"></textarea>
                                            </div>
                                        </form>
                                    </div>
                                    <button class="btn btn-save" type="button" onclick="showListProductDetail()">Danh
                                        sách biến thể</button>
                                    <a class="btn btn-cancel"
                                        href="${pageContext.request.contextPath}/admin/product">Hủy bỏ</a>
                                </div>
                            </div>
                        </div>

                        <div class="app-content" style="margin-left: 0px;">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="tile">
                                        <h3 class="tile-title">Danh sách biến thể</h3>
                                        <button class="btn btn-primary btn-sm edit" type="button" id="quick_edit">Chỉnh
                                            sửa nhanh <i class="fas fa-edit"></i></button>
                                        <div class="tile-body">
                                            <form class="row">
                                                <div class="form-group col-md-12">
                                                    <table class="table table-hover table-bordered js-copytextarea"
                                                        cellpadding="0" cellspacing="0" border="0" id="sampleTable">
                                                        <thead>
                                                            <tr>
                                                                <th class="stt" width="30">STT</th>
                                                                <th class="ten_san_pham">Tên Sản Phẩm</th>
                                                                <th class="mau_sac" width="120">Màu Sắc</th>
                                                                <th class="kich_thuoc" width="90">Kích Thước</th>
                                                                <th class="anh_san_pham" width="90">Ảnh</th>
                                                                <th class="so_luong" width="130">Số Lượng</th>
                                                                <th class="gia_ban" width="220">Giá Bán</th>
                                                                <th class="tinh_nang" width="110">Tính Năng</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="listProductDetail">
                                                            <!-- Content populated by JavaScript or from the server -->
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </form>
                                        </div>
                                        <a class="btn btn-save" type="button" onclick="save()">Lưu</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Modal chỉnh sửa nhanh -->
                        <div class="modal fade" id="QuickEditModal" tabindex="-1" role="dialog"
                            aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Chỉnh sửa nhanh</h5>
                                    </div>
                                    <div class="row modal-body ">

                                        <!--Số Lượng-->
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Số Lượng</label>
                                            <input class="form-control" type="number" id="quick-edit-quantity">
                                        </div>

                                        <!--Giá-->
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Giá bán (VNĐ)</label>
                                            <input class="form-control" type="number" id="quick-edit-price">
                                        </div>

                                    </div>
                                    <div class="form-group modal-footer">
                                        <button type="button" class="btn btn-success" id="saveQuickEditModal"
                                            onclick="quickEdit()">Lưu</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                            id="closeQuickEditModal">Đóng
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="modal fade" id="CategoryModal" tabindex="-1" role="dialog"
                            aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Thêm Danh Mục</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <!-- Your form fields for category name and description go here -->
                                        <form id="categoryForm">
                                            <div class="form-group">
                                                <label for="categoryName">Tên Danh Mục:</label>
                                                <input type="text" class="form-control" id="categoryName"
                                                    name="categoryName" placeholder="Tên danh mục...">
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary"
                                            onclick="saveCategory()">Lưu</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                            id="closeFormCategory">Đóng
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Modal Màu Sắc -->
                        <div class="modal fade" id="ColorModal" tabindex="-1" role="dialog"
                            aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Thêm Màu Sắc</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <!-- Your form fields for color name and description go here -->
                                        <form id="colorForm">
                                            <div class="form-group">
                                                <label for="colorName">Tên Màu Sắc:</label>
                                                <input type="text" class="form-control" id="colorName" name="colorName"
                                                    placeholder="Tên màu sắc...">
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary" onclick="saveColor()">Lưu</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                            id="closeFormColor">Đóng</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Modal Chất Liệu -->
                        <div class="modal fade" id="MaterialModal" tabindex="-1" role="dialog"
                            aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Thêm Chất Liệu</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <!-- Your form fields for material name and description go here -->
                                        <form id="materialForm">
                                            <div class="form-group">
                                                <label for="materialName">Tên Chất Liệu:</label>
                                                <input type="text" class="form-control" id="materialName"
                                                    name="materialName" placeholder="Tên chất liệu...">
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary"
                                            onclick="saveMaterial()">Lưu</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                            id="closeFormMaterial">Đóng
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Modal Kích Thước -->
                        <div class="modal fade" id="SizeModal" tabindex="-1" role="dialog"
                            aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Thêm Kích Thước</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <!-- Your form fields for size name and description go here -->
                                        <form id="sizeForm">
                                            <div class="form-group">
                                                <label for="sizeName">Kích Thước:</label>
                                                <input type="text" class="form-control" id="sizeName" name="sizeName"
                                                    placeholder="Kích thước...">
                                            </div>

                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary" onclick="saveSize()">Lưu</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                            id="closeFormSize">Đóng</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <jsp:include page="../layout/footer.jsp" />
                    </main>
                </div>
            </div>
            <script src="${pageContext.request.contextPath}/admin/js/product/product-add.js"></script>
            <script src="${pageContext.request.contextPath}/admin/js/category/category.js"></script>
            <script src="${pageContext.request.contextPath}/admin/js/color/color.js"></script>
            <script src="${pageContext.request.contextPath}/admin/js/material/material.js"></script>
            <script src="${pageContext.request.contextPath}/admin/js/size/size.js"></script>
        </body>

        </html>