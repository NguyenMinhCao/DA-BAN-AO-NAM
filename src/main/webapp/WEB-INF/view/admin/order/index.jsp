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
    <title>Bán Hàng - Admin</title>
    <link href="/admin/css/order/order.css" rel="stylesheet"/>
    <link href="/admin/css/styles.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/css/style1.css"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/common/toast.css">
</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp"/>

    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Bán hàng</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item active">
                        <a href="/admin" style="text-decoration: none;">Admin</a> /
                        Bán hàng
                    </li>
                </ol>

                <div class="invoice-container">
                    <div class="header">
                        <h1>Bán hàng</h1>
                        <button class="create-invoice-btn" id="btn-new-tab">+ Tạo hóa
                            đơn
                        </button>
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
                                        phẩm
                                    </button>
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
                            <!---------------------- hóa đơn chi tiết end ----------------------->
                            <hr class="product-customer">

                            <!---------------------------- Khách hàng và thông tin hóa đơn start ---------------------->
                            <div class="invoice-customer">
                                <div class="customer-header">
                                    <h5>Thông tin khách hàng</h5>
                                    <div id="select-customer">
                                        <button class="btn btn-danger" id="btn-delete-customer"
                                                disabled>Hủy
                                        </button>
                                        <button class="btn-select-customer"
                                                id="btn-choose-customer">Chọn khách
                                            hàng
                                        </button>
                                    </div>
                                </div>
                                <div class="customer-select">
                                    <div id="infoCustomer">
                                        <div id="infoDetail">
                                        </div>
                                    </div>
                                    <div id="nameCustomer">
                                        <span id="customer-name">Khách lẻ</span>
                                    </div>
                                </div>
                                <hr>
                                <div class="form-customer-invoice">
                                    <div style="width: 60%">
                                        <div style="width: 100%" class="form-customer modal">
                                            <div class="form-customer-item btn-choose-local">
                                                <h4>Thông tin khách hàng</h4>
                                                <button>Chọn địa chỉ</button>
                                            </div>
                                            <div class="form-customer-item logo-ghn">
                                                <img src="/img/Logo-GHN-Blue-Orange.webp" height="40px" width="auto">
                                            </div>
                                            <div class="form-customer-item name-customer">
                                                <div class="item">
                                                    <label>Họ tên khách hàng</label>
                                                    <input type="text" id="name" value="Nguyễn Thị Thùy Dương">
                                                </div>
                                                <div class="item">
                                                    <label>SDT</label>
                                                    <input type="text" id="phone" value="0473029182">
                                                </div>
                                            </div>
                                            <div class="form-customer-item address-customer">
                                                <div class="item">
                                                    <label>Địa chỉ chi tiết</label>
                                                    <input type="text" id="address" value="Địa chỉ 12">
                                                </div>
                                            </div>
                                            <div class="form-customer-item cty-customer">
                                                <div class="item">
                                                    <label for="city">Tỉnh/thành phố</label>
                                                    <select id="city">
                                                        <option>Sơn La</option>
                                                        <!-- Add other cities as options -->
                                                    </select>
                                                </div>
                                                <div class="item">
                                                    <label for="district">Quận/huyện</label>
                                                    <select id="district">
                                                        <option>Huyện Quỳnh Nhai</option>
                                                        <!-- Add other districts -->
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-customer-item subdistrict-customer">
                                                <div class="item">
                                                    <label for="subdistrict">Xã/phường/thị
                                                        trấn</label>
                                                    <select id="subdistrict">
                                                        <option>Xã Mường Giôn</option>
                                                        <!-- Add other subdistricts -->
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>



                                    <div class="form-invoice">
                                        <div class="form-check form-switch modal">
                                            <input class="form-check-input" type="checkbox" role="switch"
                                                   id="flexSwitchCheckDefault">
                                            <label class="form-check-label"
                                                   for="flexSwitchCheckDefault">Giao hàng</label>
                                        </div>
                                        <div class="form-invoice-item">
                                            <strong>Tổng tiền hàng:</strong>
                                            <span id="total-payment">0 VND</span>
                                        </div>
                                        <div class="form-invoice-item">
                                            <strong>Phương thức thanh toán:</strong>
                                            <div>
                                                <select id="payMethod">
                                                    <option value="CASH">Tiền mặt</option>
                                                    <option value="CARD">Chuyển khoản</option>
                                                    <option value="CASH_AND_CARD">Tiền mặt và chuyển khoản</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-invoice-item">
                                            <strong>Phiếu giảm giá:</strong>
                                            <div class="choose-voucher">
                                                <input id="voucher" type="text" placeholder="Giảm giá" disabled>
                                                <button id="openModalBtnAddVoucher">Chọn</button>
                                            </div>
                                        </div>
                                        <div class="form-invoice-item">
                                            <strong>Giảm giá:</strong>
                                            <span id="moneyDecrease">0 VND</span>
                                        </div>
                                        <div class="form-invoice-item">
                                            <strong>Khách thanh toán:</strong>
                                            <div>
                                                <input id="customer-payment" type="number"
                                                       placeholder="Tiền khách thanh toán">
                                            </div>
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
                            <div class="search-bar-product">
                                <div class="group-search-item search-name">
                                    <label>Tên sản phẩm</label>
                                    <input type="text" id="search-input-product" placeholder="Tìm kiếm sản phẩm...">
                                </div>
                                <div class="group-search-item search-size">
                                    <label>Kích cỡ</label>
                                    <select id="search-input-size">
                                        <option selected value="">Chọn kích cỡ</option>
                                    </select>
                                </div>
                                <div class="group-search-item search-color">
                                    <label>Màu sắc</label>
                                    <select id="search-input-color">
                                        <option selected value="">Chọn màu sắc</option>
                                    </select>
                                </div>
                                <div class="group-search-item search-category">
                                    <label>Danh mục</label>
                                    <select id="search-input-category">
                                        <option selected value="">Chọn danh mục</option>
                                    </select>
                                </div>
                            </div>
                            <div class="btn-search">
                                <button id="btn-search-product">Tìm kiếm</button>
                                <button id="btn-reset-product">Làm mới</button>
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
                            <h2>Danh sách khách hàng</h2>
                            <div class="search-bar">
                                <input type="text" id="search-input-customer"
                                       placeholder="Tìm kiếm sản phẩm...">
                                <button id="search-btn-customer" class="search-btn-customer">Tìm
                                    kiếm
                                </button>
                                <button id="btn-add-customer" class="btn-add-customer">Thêm
                                    mới
                                </button>
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
                                <p  id="contaiTotalPricePrint" style="display: none"><strong>Tổng Tiền hàng:</strong>
                                <p id="totalPricePrint"></p>
                                </p>
                                <p id="contaiTotalPriceVoucher" style="display: none"><strong>Giảm giá:</strong>
                                <p id="totalPriceVoucher"></p>
                                </p>
                                <p id="contaiTotalPricePayMent"><strong>Tổng tiền thành toán:</strong>
                                <p id="totalPricePayMent"></p>
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
                    <div class="btn-pay-note">
                        <div class="note-input-group">
                            <div class="input-group-prepend" title="Ghi chú">
                                <div class="input-group-text px-2"><i class="fas fa-pencil-alt"></i></div>
                            </div>
                            <textarea maxlength="255" title="Ghi chú" class="form-control" rows="2" id="description" placeholder="Ghi chú"></textarea>
                        </div>
                        <div class="btn-pay">
                            <button class="btn-order-payment" id="btn-order-payment" formmethod="post">Lưu hóa đơn</button>
                        </div>
                    </div>
                    <!---------------------- ô nhập số lượng khi mua end-------------------------------------->

                    <%------------------------         Form nhập thêm khách hàng mới start------------------------%>
                    <div class="form-add-customer" id="form-add-customer">
                        <div class="content-add-customer">
                            <h2>Thêm mới khách hàng</h2>
                            <div>
                                <!-- Họ và tên -->
                                <div class="row">
                                    <div class="input-group">
                                        <label for="fullnameCustomer">Họ và tên</label>
                                        <input type="text" id="fullnameCustomer" placeholder="Nhập họ và tên khách hàng">
                                    </div>
                                </div>
                                <!-- Số điện thoại và Email -->
                                <div class="row">
                                    <div class="input-group">
                                        <label for="emailCustomer">Email</label>
                                        <input type="email" id="emailCustomer" placeholder="Nhập email khách hàng">
                                    </div>
                                    <div class="input-group">
                                        <label for="phoneNumberAdd">Số điện thoại</label>
                                        <input type="tel" id="phoneNumberAdd" placeholder="Nhập sdt khách hàng">
                                    </div>
                                </div>

                                <!-- Địa chỉ và Quốc gia -->
                                <div class="row">
                                    <div class="input-group">
                                        <label for="addressAddDetail">Địa chỉ chi tiết</label>
                                        <input type="text" id="addressAddDetail" placeholder="Nhập địa chỉ chi tiết">
                                    </div>
                                    <div class="input-group">
                                        <label for="area">Khu vực</label>
                                        <select id="area">
                                            <option disabled selected>Chọn khu vực</option>
                                        </select>
                                    </div>
                                </div>

                                <!-- Khu vực và Phường/Xã -->
                                <div class="row">
                                    <div class="input-group">
                                        <label for="Districts">Quận/Huyện</label>
                                        <select id="Districts">
                                            <option disabled selected>Chọn Quận/Huyện</option>
                                        </select>
                                    </div>
                                    <div class="input-group">
                                        <label for="Wards">Phường/Xã</label>
                                        <select id="Wards">
                                            <option disabled selected>Chọn Phường/Xã</option>
                                        </select>
                                    </div>
                                </div>

                                <!-- Giới tính và Ngày sinh -->
                                <div class="row">
                                    <div class="input-group">
                                        <label for="gender">Giới tính</label>
                                        <select id="gender">
                                            <option disabled selected>Chọn giới tính</option>
                                            <option value="true">Nam</option>
                                            <option value="false">Nữ</option>
                                        </select>
                                    </div>
                                    <div class="input-group">
                                        <label for="dob">Ngày sinh</label>
                                        <input type="date" id="dob">
                                    </div>
                                </div>

                                <!-- Buttons -->
                                <div class="form-actions">
                                    <button type="reset" id="cancel-btn-add-customer" class="cancel-btn">Hủy (ESC)</button>
                                    <button class="add-btn" id="add-customer">Thêm</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%------------------------         Form nhập thêm khách hàng mới end ------------------------%>

                    <%------------------------         Form chọn voucher start --------------------------%>
                    <div class="modal-overlay-add-voucher" id="modalOverlayAddVoucher">
                        <!-- Nội dung modal -->
                        <div class="modal-content-add-voucher">
                            <div class="header-voucher">
                                <h3>Chọn voucher</h3>
                                <hr>
                            </div>

                            <div class="body-voucher">
                                <div class="search-voucher"
                                     style="display: flex;align-items: center; background: #f8f8f8; padding: 8px;justify-content: space-between;">
                                    <label
                                            style="margin-bottom: 0px; margin-left: 9px;">Mã
                                        voucher</label>
                                    <div class="input-with-validator">
                                        <input style="border:none; width: 200px; padding: 0px; outline: none;" type="text" value="" placeholder="Mã Voucher" maxlength="255" id="searchCoupon">
                                    </div>
                                    <button class="button-add-voucher">ÁP
                                        DỤNG
                                    </button>
                                </div>
                                <div class="voucher-list">
                                    <div style="margin: 10px 0;">
                                        <p style="margin-bottom: 0px; font-weight: bold;">
                                            Danh Sách Mã
                                        </p>
                                        <small>Có thể chọn một voucher</small>
                                    </div>
                                    <div id="contentCoupons">

                                    </div>
                                </div>

                            </div>
                            <div class="footer-voucher">
                                <button class="close-btn-add-voucher"
                                        id="closeModalBtnBackVouCher">TRỞ LẠI
                                </button>
                                <button class="OK-btn-add-voucher"
                                        id="closeModalBtnAddVouCher">OK
                                </button>
                            </div>
                        </div>
                    </div>
                    <%------------------------          Form chọn voucher end  --------------------------%>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp"/>
        <div id="toast">
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/admin/js/order/order.js"></script>
<script src="https://printjs-4de6.kxcdn.com/print.min.js"></script>
<script src="/common/toast.js"></script>
<script src="/admin/js/scripts.js"></script>
</body>

</html>