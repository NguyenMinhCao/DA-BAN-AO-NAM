<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <!-- Required meta tags -->
                    <meta charset="utf-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <link rel="icon" href="img/favicon.png" type="image/png" />
                    <title>Eiser ecommerce</title>
                    <!-- Bootstrap CSS -->
                    <link rel="stylesheet" href="css/bootstrap.css" />
                    <link rel="stylesheet" href="vendors/linericon/style.css" />
                    <link rel="stylesheet" href="css/font-awesome.min.css" />
                    <link rel="stylesheet" href="css/themify-icons.css" />
                    <link rel="stylesheet" href="vendors/owl-carousel/owl.carousel.min.css" />
                    <link rel="stylesheet" href="vendors/lightbox/simpleLightbox.css" />
                    <link rel="stylesheet" href="vendors/nice-select/css/nice-select.css" />
                    <link rel="stylesheet" href="vendors/animate-css/animate.css" />
                    <link rel="stylesheet" href="vendors/jquery-ui/jquery-ui.css" />
                    <!-- main css -->
                    <link rel="stylesheet" href="css/style.css" />
                    <link rel="stylesheet" href="css/responsive.css" />
                    <link rel="stylesheet" href="css/style1.css" />
                    <style>
                        .out_button_area .checkout_btn_inner .main_btn a {
                            color: #fff;
                        }

                        /* Ẩn nút radio gốc */
                        input[type="radio"] {
                            appearance: none;
                            -webkit-appearance: none;
                            -moz-appearance: none;
                            width: 20px;
                            height: 20px;
                            border-radius: 50%;
                            border: 2px solid #ccc;
                            position: relative;
                            cursor: pointer;
                            transition: all 0.3s ease;
                        }

                        /* Tạo hiệu ứng dấu chấm giữa */
                        input[type="radio"]:checked::after {
                            content: "";
                            height: 10px;
                            width: 10px;
                            border-radius: 50%;
                            background: #71cd14;
                            display: inline-block;
                            position: absolute;
                            right: 3px;
                            top: 50%;
                            transform: translateY(-50%);
                        }

                        /* Khi hover vào nút */
                        input[type="radio"]:hover {
                            border-color: #888;
                        }

                        /* Ẩn các nút radio gốc */
                        .radio-input-payment {
                            display: none;
                        }

                        /* Kiểu cho phần hiển thị bên ngoài nút radio */
                        .radio-btn-payment {
                            padding: 10px 20px;
                            border: 2px solid #ccc;
                            cursor: pointer;
                            margin-right: 10px;
                            border-radius: 5px;
                            transition: background-color 0.3s ease;
                        }

                        /* Khi radio được chọn */
                        .radio-input-payment:checked+.radio-btn-payment {
                            border-color: #71cd14;
                            color: #71cd14;
                        }

                        .table-responsive table th {
                            text-align: center;
                        }

                        .table-responsive table th:first-child {
                            text-align: start;
                        }

                        .table-responsive table th:nth-child(2) {
                            text-align: center;
                        }

                        .table-responsive table th:nth-child(3) {
                            text-align: center;
                            width: 130px;
                        }

                        .table-responsive table th:nth-child(4) {
                            text-align: center;
                            width: 100px;
                        }

                        .table-responsive table th:nth-child(5) {
                            width: 200px;
                        }

                        .table-responsive table th:nth-child(6) {
                            width: 100px;
                        }
                    </style>
                </head>

                <body>
                    <!--================Header Menu Area =================-->
                    <jsp:include page="../layout/header.jsp" />
                    <!--================Header Menu Area =================-->

                    <!--================Home Banner Area =================-->
                    <section class="banner_area">
                        <div class="banner_inner d-flex align-items-center">
                            <div class="container">
                                <div class="banner_content d-md-flex justify-content-between align-items-center">
                                    <div class="mb-3 mb-md-0">
                                        <h2>Cart</h2>
                                        <p>Very us move be blessed multiply night</p>
                                    </div>
                                    <div class="page_link">
                                        <a href="/">Home</a>
                                        <a href="cart.html">Cart</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <!--================End Home Banner Area =================-->

                    <!--================Cart Area =================-->
                    <c:if test="${sessionScope.sum == 0}">
                        <div class="cart-container">
                            <div class="cart-sub">
                                <div class="cart-icon">
                                    <img src="img/logo-cart-null.png" style="width: 108px; height: 100px;"
                                        alt="Empty Cart" />
                                </div>
                                <p class="cart-text">Giỏ hàng của bạn còn trống</p>
                                <button class="cart-button"><a href="/products" style="color: #fff;">MUA
                                        NGAY</a></button>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.sum > 0}">
                        <section class="cart_area">
                            <div class="container">
                                <div class="cart_inner">
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Sản phẩm</th>
                                                    <th scope="col"><span>Phân loại hàng</span></th>
                                                    <th scope="col"><span>Giá</span></th>
                                                    <th scope="col"><span>Số lượng</span></th>
                                                    <th scope="col"><span>Tổng tiền</span></th>
                                                    <th scope="col"><span>Thao tác</span></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:set var="sumInCart" value="0" />
                                                <c:forEach items="${lstCartDetail}" var="cartDetail" varStatus="i">
                                                    <tr>
                                                        <td>
                                                            <div class="media">
                                                                <div class="d-flex">
                                                                    <img style="height: 98px; width: 106px; border: none;"
                                                                        src="images/product/${cartDetail.productDetail.images[i.index].urlImage}"
                                                                        alt="" />
                                                                </div>
                                                                <div class="media-body">
                                                                    <p>${cartDetail.productDetail.product.name}</p>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td style="text-align: center;">
                                                            <div class="">
                                                                <span>
                                                                    ${cartDetail.productDetail.color.colorName},
                                                                    ${cartDetail.productDetail.size.sizeName}
                                                                </span>
                                                            </div>
                                                        </td>
                                                        <td style="text-align: center;">
                                                            <h5 class="product-price">
                                                                <fmt:formatNumber type="number"
                                                                    value="${cartDetail.productDetail.price}" />
                                                                đ
                                                            </h5>
                                                        </td>
                                                        <!-- tăng giảm số lượng sản phẩm giỏ hàng -->
                                                        <td style="text-align: center;">
                                                            <div class="product_count">
                                                                <input type="text" name="qty"
                                                                    value="${cartDetail.quantity}" title="Quantity:"
                                                                    class="input-text qty"
                                                                    data-cart-detail-id="${cartDetail.id}"
                                                                    data-cart-detail-price="${cartDetail.productDetail.price}" />
                                                                <button class="increase btn-plus items-count"
                                                                    type="button">
                                                                    <i class="lnr lnr-chevron-up"></i>
                                                                </button>
                                                                <button class="reduced btn-minus items-count"
                                                                    type="button">
                                                                    <i class="lnr lnr-chevron-down"></i>
                                                                </button>
                                                            </div>
                                                        </td>
                                                        <td style="text-align: center;">
                                                            <h5 data-cart-detail-id="${cartDetail.id}">
                                                                <fmt:formatNumber type="number"
                                                                    value="${cartDetail.price}" />
                                                                đ
                                                            </h5>
                                                        </td>
                                                        <td style="text-align: center;">
                                                            <div>
                                                                <a href="/remove-product-from-cart/${cartDetail.productDetail.id}"
                                                                    style="display: block; color: #71cd14;">Xóa</a>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <c:set var="sumInCart" value="${cartDetail.cart.totalProducts}" />
                                                </c:forEach>

                                                <tr class="shipping_area">
                                                    <td colspan="4" style="vertical-align:top;">
                                                        <h5>Chọn đơn vị vận chuyển</h5>
                                                    </td>
                                                    <td colspan="2">
                                                        <div class="shipping_box">
                                                            <ul class="list"
                                                                style="display: inline-block; margin-right: 19px;">
                                                                <li>
                                                                    <p style="display: inline;margin-right: 14px;"> Giao
                                                                        hàng hỏa tốc: đ50.000 </p>
                                                                    <input type="radio" name="shipping_method"
                                                                        id="expressDelivery" value="EXPRESS" <c:if
                                                                        test="${sessionScope.shippingMethodInOrder == 'EXPRESS'}">checked
                    </c:if>>
                    </li>
                    <li>
                        <p style="display: inline;margin-right: 14px;">Giao
                            hàng
                            nhanh: đ30.000 </p>
                        <input type="radio" name="shipping_method" id="fastDelivery" value="FAST" <c:if
                            test="${sessionScope.shippingMethodInOrder == 'FAST'}">checked
                        </c:if>>
                    </li>
                    <li>
                        <p style="display: inline;margin-right: 14px;">Giao
                            hàng
                            tiết kiệm: đ20.000</p>
                        <input type="radio" name="shipping_method" id="economyDelivery" value="SAVE" <c:if
                            test="${sessionScope.shippingMethodInOrder == 'SAVE'}">checked
                        </c:if>>
                    </li>
                    </ul>
                    </div>
                    </td>
                    </tr>

                    <tr>
                        <td class="td-voucher-add" colspan="4">
                            <p><i style="color: orange; font-size: 11px;" class="ti-layout-width-default"></i></p>
                            <p style="margin-left: 5px;">Voucher</p>
                            <p style="font-weight: bold;">
                                <button class="open-btn-add-voucher" id="openModalBtnAddVoucher"
                                    style="font-weight: 400;">Chọn hoặc nhập mã
                                </button>
                            </p>
                        </td>
                        <!-- <td colspan="1"></td> -->
                        <td colspan="2">
                            <div class="" style="margin-left: -19px;">
                                <h5 style="display: inline-block;">Tổng thanh toán (
                                    <%=pageContext.getAttribute("sumInCart")%> sản
                                        phẩm):
                                </h5>
                                <h5 style="color: #71cd14;display: inline-block;" data-cart-total-price="${totalPrice}">
                                    <fmt:formatNumber type="number" value="${totalPrice}" />
                                    đ
                                </h5>
                            </div>

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
                                            <label style="margin-bottom: 0px; margin-left: 9px;">Mã
                                                voucher</label>
                                            <div class="input-with-validator">
                                                <input style="border:none; width: 200px; padding: 0px; outline: none;"
                                                    type="text" value="" placeholder="Mã Shopee Voucher"
                                                    maxlength="255">
                                            </div>
                                            <button class="button-add-voucher">ÁP
                                                DỤNG</button>
                                        </div>
                                        <div class="voucher-list">
                                            <div style="margin: 10px 0;">
                                                <p style="margin-bottom: 0px; font-weight: bold;">
                                                    Danh Sách Mã
                                                </p>
                                                <small>Có thể chọn một voucher</small>
                                            </div>
                                            <c:forEach items="${listPromotions}" var="pro">
                                                <div class="voucher-item">
                                                    <div class="hhiiii"
                                                        style="display: flex; justify-content: center; align-items: center; position: relative; background-color: #71cd14;">
                                                        <div class="vm3TF0"
                                                            style="display: flex; justify-content: center; align-items: center; width: 90px; height: 90px;">
                                                            <img class="e52C78 nh7RxM"
                                                                style="width: 45px; height: 45px; border-radius: 50%;"
                                                                src="/img/voucher.png" alt="Logo">
                                                        </div>
                                                    </div>

                                                    <div class="voucher-details">
                                                        <div class="voucher-exp">Giảm tối đa
                                                            ${pro.discountValueFixed}k | Đơn tối
                                                            thiểu 0₫<br>HSD:
                                                            27.11.2024</div>
                                                    </div>
                                                    <div class="voucher-checkbox" style="padding-right: 15px;">
                                                        <input type="radio" id="voucherSelect" name="voucher-select"
                                                            value="${pro.id}" />
                                                    </div>
                                                </div>
                                                <!-- <c:set var="discountValueFixed"
                                                                                value="${listPromotions[0].discountValueFixed}" /> -->
                                            </c:forEach>
                                        </div>

                                    </div>
                                    <div class="footer-voucher">
                                        <button class="close-btn-add-voucher" id="closeModalBtnAddVouCher">TRỞ
                                            LẠI</button>
                                        <button class="OK-btn-add-voucher" id="closeModalBtnAddVouCher">OK</button>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr class="out_button_area">
                        <td colspan="1"></td>
                        <td colspan="5">
                            <div style="margin-left: 465px; display: inline-block; width: 300px;">
                                <div style="display: flex; justify-content: space-between; height: 27px;">
                                    <span>Tổng tiền hàng :</span>
                                    <p data-cart-total-price="${totalPrice}">
                                        <fmt:formatNumber type="number" value="${totalPrice}" />
                                        đ
                                    </p>
                                </div>
                                <div style="display: flex; justify-content: space-between; height: 27px;">
                                    <span>Tổng giảm giá :</span>
                                    <p id="total-discount">
                                        <c:if test="${promotionInOrder.discountValue == null}">
                                            0
                                        </c:if>
                                        <c:if test="${promotionInOrder.discountValue != null}">
                                            ${promotionInOrder.discountValue}
                                        </c:if> đ
                                    </p>
                                </div>
                                <div style="display: flex; justify-content: space-between; height: 27px;">
                                    <span>Tổng tiền phí vận chuyển :</span>
                                    <p id="total-shipping-cost">
                                        <fmt:formatNumber type="number" value="${shippingPrice}" />
                                        đ
                                    </p>
                                </div>
                                <div style="display: flex; justify-content: space-between; height: 27px;">
                                    <span>Tổng thanh toán :</span>
                                    <p id="total-payment">
                                        <fmt:formatNumber type="number" value="${sessionScope.totalPayment}" />
                                        đ
                                    </p>
                                </div>
                            </div>
                        </td>
                    </tr>

                    <tr class="out_button_area">
                        <td colspan="2"></td>
                        <td colspan="4">
                            <div class="checkout_btn_inner">
                                <a class="gray_btn" href="/products">Tiếp tục mua sắm</a>
                                <a class="main_btn" href="/order">Đi đến thanh toán</a>
                            </div>
                        </td>
                    </tr>

                    </tbody>
                    </table>
                    </div>
                    </div>
                    </div>
                    </section>
                    </c:if>

                    <!--================End Cart Area =================-->

                    <!--================ start footer Area  =================-->
                    <jsp:include page="../layout/footer.jsp" />
                    <!--================ End footer Area  =================-->

                    <!-- Optional JavaScript -->
                    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
                    <!-- <script src="/js/jquery-3.2.1.min.js"></script>
                    <script src="/js/popper.js"></script>
                    <script src="/js/bootstrap.min.js"></script>
                    <script src="/js/stellar.js"></script>
                    <script src="/vendors/lightbox/simpleLightbox.min.js"></script>
                    <script src="/vendors/nice-select/js/jquery.nice-select.min.js"></script>
                    <script src="/vendors/isotope/imagesloaded.pkgd.min.js"></script>
                    <script src="/vendors/isotope/isotope-min.js"></script>
                    <script src="/vendors/owl-carousel/owl.carousel.min.js"></script>
                    <script src="/js/jquery.ajaxchimp.min.js"></script>
                    <script src="/js/mail-script.js"></script>
                    <script src="/vendors/jquery-ui/jquery-ui.js"></script>
                    <script src="/vendors/counter-up/jquery.wa  ypoints.min.js"></script>
                    <script src="/vendors/counter-up/jquery.counterup.js"></script> -->
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                    <script src="/js/myjs.js"></script>
                    <script src="/js/ajaxjs.js"></script>
                </body>

                </html>