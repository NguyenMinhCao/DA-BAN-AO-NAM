<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Dashboard - SB Admin</title>
                <link href="/admin/css/order/order.css" rel="stylesheet" />
                <link href="/admin/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />

                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Order</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active">
                                        <a href="/admin" style="text-decoration: none;">Admin</a> /
                                        Order
                                    </li>
                                </ol>

                                <div class="invoice-container">
                                    <div class="header">
                                        <h1>Bán hàng</h1>
                                        <button class="create-invoice-btn" id="btn-new-tab">+ Tạo hóa
                                            đơn</button>
                                    </div>
                                    <div class="tabs">
                                        <button class="add-tab">|</button>
                                    </div>
                                    <div class="content-tab">
                                        <div class="tab-content active" id="tab-1">
                                            <div class="invoice-add-product">
                                                <!------------------ hóa đơn chi tiết start ------------------>
                                                <div class="invoice-header">
                                                    <button class="btn-add-product" id="btn-choose-product">Thêm sản
                                                        phẩm</button>
                                                </div>
                                                <div class="product-table-container">
                                                    <table class="table">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">STT</th>
                                                                <th scope="col">Sản phẩm</th>
                                                                <th scope="col">Số lượng</th>
                                                                <th scope="col">Tổng tiền</th>
                                                                <th scope="col">Thao tác</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="product-list-invoice">

                                                        </tbody>
                                                    </table>
                                                </div>
                                                <div class="invoice-footer">
                                                    <span>Tổng tiền: </span>
                                                    <span id="total-price-table-invoice" style="color: red">
                                                        0<p>VND</p></span>
                                                </div>
                                            </div>
                                            <!-- hóa đơn chi tiết end -->
                                            <hr class="product-customer">

                                            <!---------------------------- Khách hàng và thông tin hóa đơn start ---------------------->
                                            <div class="invoice-customer">
                                                <div class="customer-header">
                                                    <h5>Khách hàng</h5>
                                                    <div id="select-customer">
                                                        <button class="btn btn-danger" id="btn-delete-customer"
                                                            disabled>Hủy</button>
                                                        <button class="btn-select-customer"
                                                            id="btn-choose-customer">Chọn khách
                                                            hàng</button>
                                                    </div>
                                                </div>
                                                <div class="customer-select">
                                                    <div id="infoCustomer">
                                                        <strong>Tên khách hàng</strong>
                                                        <div id="infoDetail">
                                                        </div>
                                                    </div>
                                                    <div id="nameCustomer">
                                                        <span id="customer-name">Khách lẻ</span>
                                                    </div>
                                                    <div class="form-check form-switch">
                                                        <input class="form-check-input" type="checkbox" role="switch"
                                                            id="flexSwitchCheckDefault">
                                                        <label class="form-check-label"
                                                            for="flexSwitchCheckDefault">Giao hàng</label>
                                                    </div>
                                                </div>
                                                <hr>
                                                <div class="form-customer-invoice">
                                                    <div class="form-customer">
                                                        <div class="form-customer-item name-customer">
                                                            <input type="text" id="name" value="Nguyễn Thị Thùy Dương"
                                                                disabled>
                                                        </div>
                                                        <div class="form-customer-item phone-number-customer">
                                                            <input type="text" id="phone" value="0473029182" disabled>
                                                        </div>
                                                        <div class="form-customer-item address-customer">
                                                            <input type="text" id="address" value="Địa chỉ 12" disabled>
                                                        </div>
                                                        <diV class="form-customer-item cty-customer">
                                                            <label for="city">Tỉnh/thành phố</label>
                                                            <select id="city">
                                                                <option>Sơn La</option>
                                                                <!-- Add other cities as options -->
                                                            </select>
                                                        </diV>
                                                        <div class="form-customer-item district-customer">
                                                            <label for="district">Quận/huyện</label>
                                                            <select id="district">
                                                                <option>Huyện Quỳnh Nhai</option>
                                                                <!-- Add other districts -->
                                                            </select>
                                                        </div>
                                                        <div class="form-customer-item subdistrict-customer">
                                                            <label for="subdistrict">Xã/phường/thị
                                                                trấn</label>
                                                            <select id="subdistrict">
                                                                <option>Xã Mường Giôn</option>
                                                                <!-- Add other subdistricts -->
                                                            </select>
                                                        </div>
                                                        <div class="form-customer-item additional-address-customer">
                                                            <label for="additional-address">Địa chỉ cụ
                                                                thể</label>
                                                            <input type="text" id="additional-address" value="aaaaa"
                                                                disabled>
                                                        </div>
                                                        <div class="form-customer-item note-customer">
                                                            <label for="note">Ghi chú</label>
                                                            <input type="text" id="note">
                                                        </div>
                                                    </div>
                                                    <div class="form-invoice">
                                                        <h4>Thông tin hóa đơn</h4>
                                                        <div class="form-invoice-item">
                                                            <strong>Tổng tiền hàng:</strong>
                                                            <span id="total-payment">0 VND</span>
                                                        </div>
                                                        <div class="form-invoice-item">
                                                            <strong>Phiếu giảm giá:</strong>
                                                            <input id="" type="number" placeholder="Giảm giá">
                                                            <button>Chọn</button>
                                                        </div>
                                                        <div class="form-invoice-item">
                                                            <strong>Giảm giá:</strong>
                                                            <span>0 VND</span>
                                                        </div>
                                                        <div class="form-invoice-item">
                                                            <strong>Khách thanh toán:</strong>
                                                            <input id="customer-payment" type="number"
                                                                placeholder="Tiền khách thanh toán">
                                                        </div>
                                                        <div class="form-invoice-item">
                                                            <strong>Tiền khách còn thiếu:</strong>
                                                            <span id="remaining-money">0 VND</span>
                                                        </div>
                                                        <div class="form-invoice-item">
                                                            <strong>Tiền thừa:</strong>
                                                            <span id="return-money">0 VND</span>
                                                        </div>
                                                        <div class="form-invoice-item">
                                                            <strong>Tổng thanh toán:</strong>
                                                            <strong class="form-invoice-total-amount"
                                                                id="form-invoice-total-amount">0
                                                                VND</strong>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Khách hàng và thông tin hóa đơn end -->
                                        </div>
                                    </div>
                                    <!--------------------------- Table chọn sản phẩm start ---------------------------->
                                    <div id="product-modal" class="modal">
                                        <div class="modal-content">
                                            <h2>Danh sách sản phẩm</h2>
                                            <div class="search-bar">
                                                <input type="text" id="search-input-product"
                                                    placeholder="Tìm kiếm sản phẩm...">
                                                <button id="btn-search-product" class="btn-search-product">Tìm
                                                    kiếm</button>
                                            </div>
                                            <div class="select-product">
                                                <table>
                                                    <thead>
                                                        <tr>
                                                            <th>STT</th>
                                                            <th>Tên sản phẩm</th>
                                                            <th>Ảnh</th>
                                                            <th>Size</th>
                                                            <th>Số lượng</th>
                                                            <th>Màu sắc</th>
                                                            <th>Đơn giá</th>
                                                            <th>Thao tác</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="product-table">
                                                        <!-- Dữ liệu sẽ được thêm qua JavaScript -->
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div id="pagination-product" class="pagination"></div>
                                            <button id="btn-close-product-modal" class="close-btn">Đóng</button>
                                        </div>
                                    </div>
                                    <!--------------------- Table chọn sản phẩm end ------------------------------>

                                    <!--------------------- Table chọn khách hàng start ------------------------------>
                                    <div id="customer-modal" class="modal">
                                        <div class="modal-content">
                                            <h2>Danh sách sản phẩm</h2>
                                            <div class="search-bar">
                                                <input type="text" id="search-input-customer"
                                                    placeholder="Tìm kiếm sản phẩm...">
                                                <button id="search-btn-customer" class="search-btn-customer">Tìm
                                                    kiếm</button>
                                                <button id="btn-add-customer" class="btn-add-customer">Thêm
                                                    mới</button>
                                            </div>
                                            <div class="select-product">
                                                <table>
                                                    <thead>
                                                        <tr>
                                                            <th>STT</th>
                                                            <th>Tên khách hàng</th>
                                                            <th>SDT</th>
                                                            <th>Thao tác</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="customer-list">
                                                        <!-- Dữ liệu sẽ được thêm qua JavaScript -->
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div id="pagination-customer" class="pagination"></div>
                                            <button id="btn-close-customer-modal" class="close-btn">Đóng</button>
                                        </div>
                                    </div>
                                    <!--------------------- Table chọn khách hàng end ------------------------------>

                                    <!---------------------- In hóa đơn start -------------------------------------->
                                    <div id="invoice" class="print-invoice">
                                        <div class="center-content">
                                            <table class="table table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>Sản Phẩm</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="table_print">
                                                </tbody>
                                            </table>
                                            <div class="total-amount">
                                                <p><strong>Tổng Tiền:</strong>
                                                <p id="totalPricePrint"></p>
                                                </p>
                                            </div>
                                            <p>Cảm ơn quý khách !</p>
                                            <p>Hẹn gặp lại !</p>
                                        </div>
                                    </div>
                                    <!---------------------- In hóa đơn end -------------------------------------->

                                    <!---------------------- ô nhập số lượng khi mua start-------------------------------------->
                                    <div class="quantity-modal hidden">
                                        <div class="modal-container">
                                            <h3>Số lượng sản phẩm</h3>
                                            <div class="quantity-input">
                                                <button class="minus-btn">-</button>
                                                <input type="number" min="1" value="1" id="quantity-value">
                                                <button class="plus-btn">+</button>
                                            </div>
                                            <div class="action-buttons">
                                                <button class="cancel-btn">Hủy</button>
                                                <button class="order-btn">Đặt hàng</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="btn-pay">
                                        <button onclick="localtt()">Xóa</button>
                                        <button class="btn-order-payment" id="btn-order-payment" formmethod="post">Xác
                                            nhận thanh
                                            toán</button>
                                    </div>
                                    <!---------------------- ô nhập số lượng khi mua end-------------------------------------->
                                </div>

                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
                    crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.js"></script>
                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                <script src="/admin/js/order/order.js"></script>
                <script src="https://printjs-4de6.kxcdn.com/print.min.js"></script>
            </body>

            </html>