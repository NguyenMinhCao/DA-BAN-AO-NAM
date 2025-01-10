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
                    <link rel="stylesheet" href="css/themify-icons.css" />
                    <!-- main css -->
                    <link rel="stylesheet" href="css/style.css" />
                    <link rel="stylesheet" href="css/responsive.css" />
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

                        .table-responsive .table th {
                            text-align: center;
                        }

                        .table-responsive .table th:first-child {
                            text-align: start;
                        }

                        .table-responsive .table th:nth-child(2) {
                            text-align: center;
                        }

                        .table-responsive .table th:nth-child(3) {
                            text-align: center;
                        }

                        .table-responsive .table th:last-child {
                            text-align: center;
                        }
                    </style>

                    <link rel="stylesheet" href="css/style1.css" />
                </head>

                <body>
                    <c:set var="discountTotal" value="0" />
                    <c:set var="shippingTotal" value="0" />
                    <c:set var="discountValue" value="0" />
                    <!--================Header Menu Area =================-->
                    <header class="header_area">
                        <div class="top_menu">
                            <div class="container">
                                <div class="row">
                                    <div class="col-lg-7">
                                        <div class="float-left">
                                            <p>Phone: +01 256 25 235</p>
                                            <p>email: info@eiser.com</p>
                                        </div>
                                    </div>

                                    <div class="col-lg-5">
                                        <div class="float-right">
                                            <ul class="right_side">
                                                <li>
                                                    <a href="#">
                                                        ${pageContext.request.userPrincipal.name}
                                                    </a>
                                                </li>
                                                <li>
                                                    <c:if test="${empty pageContext.request.userPrincipal}">
                                                        <a href="/login">
                                                            Đăng nhập
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${not empty pageContext.request.userPrincipal}">
                                                        <a href="#" onclick="submitLogoutForm(); return false;">
                                                            Đăng xuất
                                                        </a>
                                                    </c:if>
                                                    <form id="logoutForm" action="/logout" method="post"
                                                        style="display:none;">
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                            value="${_csrf.token}" />
                                                    </form>
                                                </li>
                                                <li>
                                                    <a href="contact.html">
                                                        Contact Us
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="main_menu">
                            <div class="container">
                                <nav class="navbar navbar-expand-lg navbar-light w-100">
                                    <!-- Brand and toggle get grouped for better mobile display -->
                                    <a style="height: 80px;"
                                        class="navbar-brand logo_h d-flex align-items-center justify-content-center"
                                        href="/">
                                        <img src="img/logo.png" alt="" />
                                        <h1 class="cE_Tbx">Thanh toán</h1>
                                    </a>
                                </nav>
                            </div>
                        </div>
                    </header>
                    <!--================Header Menu Area =================-->

                    <!--================Home Banner Area =================-->
                    <!-- <section class="banner_area">
                        <div class="banner_inner d-flex align-items-center">
                            <div class="container">
                                <div class="banner_content d-md-flex justify-content-between align-items-center">
                                    <div class="mb-3 mb-md-0">
                                        <h2>Thanh toán</h2>
                                        <p>Very us move be blessed multiply night</p>
                                    </div>
                                    <div class="page_link">
                                        <a href="/">Home</a>
                                        <a href="cart.html">Thanh toán</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section> -->
                    <!--================End Home Banner Area =================-->

                    <!--================Cart Area =================-->
                    <section class="cart_area" style="background-color: #f6f6f6;">

                        <div class="container">
                            <!-- Bắt đầu hiển thị địa chỉ -->
                            <div class="container-place" style="background-color: #fff; margin-bottom: 20px;">
                                <div class="line-top"></div>
                                <div class="place">
                                    <div class="place-title">
                                        <div class="location-icon">
                                            <i class="ti-location-pin" aria-hidden="true"></i>
                                        </div>

                                        <h4 style="color: #71cd14;">Địa chỉ nhận hàng</h4>
                                    </div>
                                    <div class="place-detail">
                                        <div>
                                            <div class="place-detail-one">
                                                <div class="PzGLCh" id="fullNameAndPhoneNumber">
                                                    ${address.fullName}
                                                    ${address.phoneNumber} 
                                                </div>
                                                <div class="a9c4OR" style="margin-left: 20px; display: inline-block;"
                                                    id="streetDetailsAndAdress">
                                                    ${address.streetDetails}, ${address.ward}, ${address.district},
                                                    ${address.city}
                                                </div>
                                                <c:choose>
                                                    <c:when test="${address.status == true}">
                                                        <div class="dIzOca" style="display: inline-block;">Mặc định
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="dIzOca" style="display: none;">Mặc định
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <button class="VNkBIJ" id="openModalBtnAddress" style="color: #71cd14;">Thay
                                            đổi</button>
                                    </div>
                                </div>
                            </div>
                            <!-- Kết thúc hiển thị địa chỉ -->

                            <!-- Bắt đầu nội dung box địa chỉ -->
                            <div class="modal-overlay-address" id="modalOverlayAddress">
                                <!-- Nội dung modal -->
                                <div class="modal-content-address">
                                    <div class="header-address">
                                        <h3 style="font-size: 20px;font-weight: 500;">Địa chỉ của tôi</h3>
                                    </div>

                                    <div class="body-address">
                                        <c:forEach items="${arrAddressByUser}" var="address">
                                            <div class="address-item"
                                                style="display: flex; padding: 16px 0; border-top: 1px solid rgba(0, 0, 0, .09);">
                                                <div class="LX32OX">
                                                    <div class="stardust-radio stardust-radio--checked">
                                                        <input type="radio" name="address-select" value="${address.id}"
                                                            <c:if test="${address.status == true}">
                                                        checked
                                                        </c:if>/>
                                                    </div>
                                                </div>
                                                <div class="z6dXMD">
                                                    <div class="" style="display: flex;">
                                                        <div class="h44KA2 ZnXbv2">
                                                            <span class="knfOzn kI16mM">
                                                                <div class="bzNVjQ">${address.fullName}</div>
                                                            </span>
                                                            <div class="_BEX6X"></div>
                                                            <div role="row" class="qVJxSe EdB7nx ieb1A9">(+84)
                                                                ${address.phoneNumber}
                                                            </div>
                                                        </div>
                                                        <div class="MM8UDO">
                                                            <button class="zN45gZ openModalBtnUpdateAddress"
                                                                value="${address.id}" name="id-update-address">Cập
                                                                nhật
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <div class="CRFtuZ kdrlZs">
                                                        <div class="h44KA2 ZnXbv2">
                                                            <div class="glTVDN">
                                                                <div class="ieb1A9">${address.streetDetails}</div>
                                                            </div>
                                                        </div>
                                                        <div class="p2Hy8c MM8UDO"></div>
                                                    </div>

                                                    <div class="K9nwA9 ieb1A9" <c:if test="${address.status != true}">
                                                        style="display: none;"
                                                        </c:if>>
                                                        <span class="Fq8OwQ lVoK9N mHRnNW">Mặc
                                                            định
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                        <button class="sHL4SN Yt_Dxy hmAUGb" id="openModalBtnAddAddress">
                                            <svg viewBox="0 0 10 10" class="u2BTTb">
                                                <path stroke="none"
                                                    d="m10 4.5h-4.5v-4.5h-1v4.5h-4.5v1h4.5v4.5h1v-4.5h4.5z">
                                                </path>
                                            </svg>
                                            Thêm Địa Chỉ Mới
                                        </button>
                                    </div>

                                    <hr>
                                    <div class="footer-address">
                                        <div style="margin-left: 142px;">
                                            <button class="close-btn-add-address" id="closeModalBtnAddress">HỦY</button>
                                            <button type="submit" class="OK-btn-change-address"
                                                id="closeModalBtnAddressXn">XÁC
                                                NHẬN</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Kết thúc nội dung box địa chỉ -->


                            <!-- Bắt đầu nội dung box thêm địa chỉ -->
                            <div class="modal-overlay-add-address" id="modalOverlayAddAddress">
                                <!-- <div class="modal-content-add-address">
                                    <div class="header-add-address">
                                        <h3 style="font-size: 20px;font-weight: 500;">Thêm địa chỉ</h3>
                                    </div>
                                    <hr style="margin-top: 0;">

                                    <div class="add-body-address">
                                        <form>
                                            <div class="GIYGxC">
                                                <div class="xBS0bh">
                                                    <div class="k81zo2">
                                                        <div class="aXMKUl gGu3qC">
                                                            <div class="aWf_k3">
                                                                <div class="K5cdoq">Họ và tên</div>
                                                                <input class="uU_7Kb" type="text"
                                                                    placeholder="Họ và tên" maxlength="64"
                                                                    name="user_address_fullname" value="">
                                                            </div>
                                                        </div>
                                                        <div class="P49sO4"></div>
                                                        <div class="aXMKUl oawiV9">
                                                            <div class="aWf_k3">
                                                                <div class="K5cdoq">Số điện thoại</div>
                                                                <input class="uU_7Kb" type="text"
                                                                    placeholder="Số điện thoại"
                                                                    name="user_address_phone" value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="k81zo2">
                                                        <div class="B3Z66j">
                                                            <div class="fD7jc0">
                                                                <div class="RyrP3M">
                                                                    <input class="uU_7Kb" type="text"
                                                                        placeholder="Tỉnh/ Thành phố, Quận/Huyện, Phường/Xã"
                                                                        name="user_address" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="k81zo2">
                                                        <div class="HtRrP7">
                                                            <div class="yb8LRL _6FQEs4 WsMrm9">
                                                                <div class="NCOcN7">
                                                                    <div class="bMYo7S">Địa chỉ cụ thể</div>
                                                                    <input class="zKGLlL" rows="2"
                                                                        placeholder="Địa chỉ cụ thể"
                                                                        name="user_street_address"
                                                                        maxlength="128"></input>
                                                                </div>
                                                            </div>
                                                            <div class="GDmj6q"></div>
                                                        </div>
                                                    </div>
                                                    <div class="htdX_R">
                                                        <label class="M64DVD">
                                                            <input class="QDGDYv" type="checkbox"
                                                                style="margin-right: 10px;" name="address_select">
                                                            <div class="QoOTGC"></div>
                                                            Đặt làm địa chỉ mặc đinh
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="footer-add-address">
                                        <div style="margin-left: 142px;">
                                            <button type="reset" class="close-btn-add-address"
                                                id="closeModalBtnAddAddress">HỦY</button>
                                            <button type="submit" id="closeModalBtnAddAddressXn"
                                                class="btn-add-address">HOÀN THÀNH</button>
                                        </div>
                                    </div>

                                </div> -->
                            </div>
                            <!-- Kết thúc nội dung box thêm địa chỉ -->


                            <!-- Bắt đầu nội dung box cập nhật địa chỉ -->
                            <div class="modal-overlay-update-address" id="modalOverlayUpdateAddress">
                                <!-- <div class="modal-content-update-address">
                                    <div class="header-update-address">
                                        <h3 style="font-size: 20px;font-weight: 500;">Cập nhật địa chỉ</h3>
                                    </div>
                                    <hr>

                                    <div class="body-update-address">
                                        <form>
                                            <div class="GIYGxC">
                                                <input id="userAddressId" style="display: none;">
                                                <div class="xBS0bh">
                                                    <div class="k81zo2">
                                                        <div class="aXMKUl gGu3qC">
                                                            <div class="aWf_k3">
                                                                <div class="K5cdoq">Họ và tên</div>
                                                                <input class="uU_7Kb" type="text"
                                                                    placeholder="Họ và tên" maxlength="64"
                                                                    name="user_address_fullname"
                                                                    id="userAddressFullName">
                                                            </div>
                                                        </div>
                                                        <div class="P49sO4"></div>
                                                        <div class="aXMKUl oawiV9">
                                                            <div class="aWf_k3">
                                                                <div class="K5cdoq">Số điện thoại</div>
                                                                <input class="uU_7Kb" type="text"
                                                                    placeholder="Số điện thoại"
                                                                    name="user_address_phone" id="userAddressPhone"
                                                                    value="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="k81zo2">
                                                        <div class="B3Z66j">
                                                            <div class="fD7jc0">
                                                                <div class="RyrP3M">
                                                                    <input class="uU_7Kb" type="text"
                                                                        placeholder="Tỉnh/ Thành phố, Quận/Huyện, Phường/Xã"
                                                                        name="user_address" id="userAddress" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="k81zo2">
                                                        <div class="HtRrP7">
                                                            <div class="yb8LRL _6FQEs4 WsMrm9">
                                                                <div class="NCOcN7">
                                                                    <div class="bMYo7S">Địa chỉ cụ thể</div>
                                                                    <input class="zKGLlL" rows="2"
                                                                        placeholder="Địa chỉ cụ thể"
                                                                        name="user_street_address" maxlength="128"
                                                                        id="userStreetAddress"></input>
                                                                </div>
                                                            </div>
                                                            <div class="GDmj6q"></div>
                                                        </div>
                                                    </div>
                                                    <div class="htdX_R">
                                                        <label class="M64DVD">
                                                            <input class="QDGDYv" type="checkbox"
                                                                style="margin-right: 10px;" name="address_select">
                                                            <div class="QoOTGC"></div>
                                                            Đặt làm địa chỉ mặc đinh
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>

                                    <div class="footer-update-address">
                                        <div style="margin-left: 142px;">
                                            <button class="close-btn-add-address"
                                                id="closeModalBtnUpdateAddress">HỦY</button>
                                            <button type="submit" class="btn-update-address"
                                                id="closeModalBtnUpdateAddressXn">XÁC
                                                NHẬN</button>
                                        </div>
                                    </div>
                                </div> -->
                            </div>
                            <!-- Kết thúc nội dung box cập nhật địa chỉ -->

                            <div class="cart_inner">
                                <div class="table-responsive">
                                    <table class="table" style="background-color: #fff;">
                                        <thead>
                                            <tr>
                                                <th scope="col">Sản phẩm</th>
                                                <th scope="col"><span>Giá</span></th>
                                                <th scope="col"><span>Số lượng</span></th>
                                                <th scope="col"><span>Tổng tiền</span></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="sumInCart" value="0" />
                                            <!-- Bắt đầu hiển thị sản phẩm trong giỏ hàng -->
                                            <c:forEach items="${lstCartDetail}" var="cartDetail">
                                                <tr>
                                                    <td>
                                                        <div class="media">
                                                            <div class="d-flex">
                                                                <img style="height: 98px; width: 106px; border: none;"
                                                                    src="images/product/${cartDetail.productDetail.images[0].urlImage}"
                                                                    alt="" />
                                                            </div>
                                                            <div class="media-body">
                                                                <p>${cartDetail.productDetail.product.name}</p>
                                                            </div>
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
                                                            <input type="text" name="qty" value="${cartDetail.quantity}"
                                                                title="Quantity:" class="input-text qty"
                                                                data-cart-detail-id="${cartDetail.id}"
                                                                data-cart-detail-price="${cartDetail.productDetail.price}" />
                                                            <button class="increase btn-plus items-count" type="button">
                                                                <i class="lnr lnr-chevron-up"></i>
                                                            </button>
                                                            <button class="reduced btn-minus items-count" type="button">
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
                                                </tr>
                                                <c:set var="sumInCart" value="${cartDetail.cart.totalProducts}" />
                                            </c:forEach>
                                            <!-- Kết thúc hiển thị sản phẩm trong giỏ hàng -->

                                            <!-- Bắt đầu đơn vị vận chuyển  -->
                                            <tr class="shipping_area">
                                                <td colspan="2" style="vertical-align:top;">
                                                    <h5>Chọn đơn vị vận chuyển</h5>
                                                </td>
                                                <td colspan="2">
                                                    <div class="shipping_box">
                                                        <ul class="list"
                                                            style="display: inline-block; margin-right: 40px;">
                                                            <li>
                                                                <p style="display: inline;margin-right: 14px;"> Giao
                                                                    hàng hỏa tốc: đ50.000 </p>
                                                                <input type="radio" name="shipping_method"
                                                                    id="express-delivery" value="EXPRESS" <c:if
                                                                    test="${sessionScope.shippingMethodInOrder == 'EXPRESS'}">
                                                                checked
                                                                </c:if>>
                                                            </li>
                                                            <li>
                                                                <p style="display: inline;margin-right: 14px;">Giao
                                                                    hàng
                                                                    nhanh: đ30.000 </p>
                                                                <input type="radio" name="shipping_method"
                                                                    id="fast_delivery" value="FAST" <c:if
                                                                    test="${sessionScope.shippingMethodInOrder == 'FAST'}">
                                                                checked
                                                                </c:if>>
                                                            </li>
                                                            <li>
                                                                <p style="display: inline;margin-right: 14px;">Giao
                                                                    hàng
                                                                    tiết kiệm: đ20.000</p>
                                                                <input type="radio" name="shipping_method"
                                                                    id="economy_delivery" value="SAVE" <c:if
                                                                    test="${sessionScope.shippingMethodInOrder == 'SAVE'}">
                                                                checked
                                                                </c:if>>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </td>
                                            </tr>
                                            <!-- Kết thúc đơn vị vận chuyển  -->

                                            <!-- Bắt đầu chọn voucher -->
                                            <tr>
                                                <td class="td-voucher-add" colspan="2">
                                                    <p style="margin-left: 9px;"><i
                                                            style="color: orange; font-size: 11px;"
                                                            class="ti-layout-width-default"></i></p>
                                                    <p style="margin-left: 5px;">Voucher</p>
                                                    <p style="font-weight: bold;">
                                                        <button class="open-btn-add-voucher" id="openModalBtnAddVoucher"
                                                            style="font-weight: 400;">Chọn hoặc nhập mã
                                                        </button>
                                                    </p>
                                                </td>
                                                <td colspan="2">
                                                    <div class="" style="text-align: end; margin-right: 42px;">
                                                        <h5 style="display: inline-block;">Tổng số tiền (
                                                            <%=pageContext.getAttribute("sumInCart")%> sản
                                                                phẩm):
                                                        </h5>
                                                        <h5 style="color: #71cd14;display: inline-block;"
                                                            data-cart-total-price="${totalPrice}">
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
                                                                    <label
                                                                        style="margin-bottom: 0px; margin-left: 9px;">Mã
                                                                        voucher</label>
                                                                    <div class="input-with-validator">
                                                                        <input
                                                                            style="border:none; width: 200px; padding: 0px; outline: none;"
                                                                            type="text" value=""
                                                                            placeholder="Mã Shopee Voucher"
                                                                            maxlength="255">
                                                                    </div>
                                                                    <button class="button-add-voucher">ÁP
                                                                        DỤNG</button>
                                                                </div>
                                                                <div class="voucher-list">
                                                                    <div style="margin: 10px 0;">
                                                                        <p
                                                                            style="margin-bottom: 0px; font-weight: bold;">
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
                                                                                        src="/img/voucher.png"
                                                                                        alt="Logo">
                                                                                </div>
                                                                            </div>

                                                                            <div class="voucher-details">
                                                                                <div class="voucher-exp">Giảm tối đa
                                                                                    ${pro.discountValue}k | Đơn tối
                                                                                    thiểu 0₫<br>HSD:
                                                                                    27.11.2024</div>
                                                                            </div>
                                                                            <div class="voucher-checkbox"
                                                                                style="padding-right: 15px;">
                                                                                <input type="radio"
                                                                                    name="voucher-select"
                                                                                    value="${pro.id}"
                                                                                    data-discount="${pro.discountValue}"
                                                                                    <c:if
                                                                                    test="${promotionInOrder.id == pro.id}">
                                                                                checked
                                                                                </c:if>/>
                                                                            </div>
                                                                        </div>
                                                                        <!-- <c:set var="discountValue"
                                                                            value="${listPromotions[0].discountValue}" /> -->
                                                                    </c:forEach>
                                                                </div>

                                                            </div>
                                                            <div class="footer-voucher">
                                                                <button class="close-btn-add-voucher"
                                                                    id="closeModalBtnAddVouCher">TRỞ LẠI</button>
                                                                <button class="OK-btn-add-voucher"
                                                                    id="closeModalBtnAddVouCher">OK</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <!-- Kết thúc chọn voucher -->

                                        </tbody>
                                    </table>
                                    <table class="table" style="margin-top: 20px; background-color: #fff;">
                                        <tbody>
                                            <!-- Bắt đầu chọn phương thức thanh toán -->
                                            <tr class="out_button_area">
                                                <td style="display: block;">
                                                    <h4
                                                        style="margin-bottom: 0px; margin-right: 10px;  margin-left: 10px;display: inline-block;">
                                                        Phương thức
                                                        thanh toán</h4>
                                                    <div class="payment-methods" style="display: inline;">
                                                        <label class="radio-label">
                                                            <input type="radio" name="payment-method"
                                                                id="cash-on-delivery" class="radio-input-payment"
                                                                value="COD" checked <c:if
                                                                test="${paymentMethodInOrder == 'COD'}">
                                                            checked
                                                            </c:if>>
                                                            <span class="radio-btn-payment">Thanh toán khi nhận
                                                                hàng</span>
                                                        </label>
                                                        <label class="radio-label">
                                                            <input type="radio" name="payment-method" id="vnpay"
                                                                class="radio-input-payment" value="VNPAY" <c:if
                                                                test="${paymentMethodInOrder == 'VNPAY'}">
                                                            checked
                                                            </c:if>>
                                                            <span class="radio-btn-payment">VN PAY</span>
                                                        </label>
                                                    </div>

                                                </td>
                                                <td></td>
                                            </tr>
                                            <!-- Kết thúc chọn phương thức thanh toán -->

                                            <!-- Bắt đầu tính tiền trong đơn hàng -->
                                            <tr class="out_button_area">
                                                <td></td>
                                                <td>
                                                    <div
                                                        style="margin-left: 184px; display: inline-block; width: 300px;">
                                                        <div
                                                            style="display: flex; justify-content: space-between; height: 27px;">
                                                            <span>Tổng tiền hàng :</span>
                                                            <p data-cart-total-price="${totalPrice}">
                                                                <fmt:formatNumber type="number" value="${totalPrice}" />
                                                                đ
                                                            </p>
                                                        </div>
                                                        <div
                                                            style="display: flex; justify-content: space-between; height: 27px;">
                                                            <span>Tổng giảm giá :</span>
                                                            <p id="total-discount">
                                                                <c:if test="${promotionInOrder.discountValue == null}">
                                                                    0
                                                                </c:if>
                                                                <c:if test="${promotionInOrder.discountValue != null}">
                                                                    <fmt:formatNumber type="number"
                                                                        value="${promotionInOrder.discountValue}" />
                                                                </c:if>đ
                                                            </p>
                                                        </div>
                                                        <div
                                                            style="display: flex; justify-content: space-between; height: 27px;">
                                                            <span>Tổng tiền phí vận chuyển :</span>
                                                            <p id="total-shipping-cost">
                                                                <fmt:formatNumber type="number"
                                                                    value="${shippingPrice}" />
                                                                đ
                                                            </p>
                                                        </div>
                                                        <div
                                                            style="display: flex; justify-content: space-between; height: 27px;">
                                                            <span>Tổng thanh toán :</span>
                                                            <p id="total-payment">
                                                                <fmt:formatNumber type="number"
                                                                    value="${sessionScope.totalPayment}" />
                                                                đ
                                                            </p>
                                                        </div>
                                                    </div>

                                                </td>
                                            </tr>
                                            <!-- Kết thúc tính tiền trong đơn hàng -->

                                            <!-- Bắt đầu nút ấn đặt hàng -->
                                            <tr class="out_button_area">
                                                <td>
                                                    <div class="" style="margin-left: 10px; ">
                                                        Nhấn "Đặt hàng" đồng nghĩa với việc bạn đồng ý tuân theo
                                                        Điều
                                                        khoản Shopee
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="checkout_btn_inner"
                                                        style="margin-left: 187px; display: inline-block;">
                                                        <a class="gray_btn" href="/cart">Giỏ hàng</a>
                                                        <form style="display: inline-block;" action="/order-checkout"
                                                            method="POST">
                                                            <button type="submit" class="main_btn">Đặt
                                                                hàng</button>
                                                        </form>

                                                    </div>
                                                </td>
                                            </tr>
                                            <!-- Kết thúc nút ấn đặt hàng -->
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </section>
                    <!--================End Cart Area =================-->

                    <!--================ start footer Area  =================-->
                    <jsp:include page="../layout/footer.jsp" />
                    <!--================ End footer Area  =================-->

                    <!-- Optional JavaScript -->
                    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                    <script src="/js/myjs.js"></script>
                    <script src="/js/ajaxjs.js"></script>

                </body>

                </html>