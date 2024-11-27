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
                                                    <th scope="col">Giá</th>
                                                    <th scope="col">Số lượng</th>
                                                    <th scope="col">Tổng tiền</th>
                                                    <th scope="col">Thao tác</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:set var="sumInCart" value="0" />
                                                <c:forEach items="${lstCartDetail}" var="cartDetail">
                                                    <tr>
                                                        <td>
                                                            <div class="media">
                                                                <div class="d-flex">
                                                                    <img style="height: 98px; width: 106px; border: none;"
                                                                        src="images/product/${cartDetail.product.images[0].imageUrl}"
                                                                        alt="" />
                                                                </div>
                                                                <div class="media-body">
                                                                    <p>${cartDetail.product.name}</p>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <h5>
                                                                <fmt:formatNumber type="number"
                                                                    value="${cartDetail.product.price}" />
                                                                đ
                                                            </h5>
                                                        </td>
                                                        <td>
                                                            <div class="product_count">
                                                                <input type="text" name="qty" id="sst" maxlength="12"
                                                                    value="${cartDetail.quantity}" title="Quantity:"
                                                                    class="input-text qty" />
                                                                <button class="increase items-count" type="button">
                                                                    <i class="lnr lnr-chevron-up"></i>
                                                                </button>
                                                                <button class="reduced items-count" type="button">
                                                                    <i class="lnr lnr-chevron-down"></i>
                                                                </button>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <h5>
                                                                <fmt:formatNumber type="number"
                                                                    value="${cartDetail.price}" />
                                                                đ
                                                            </h5>
                                                        </td>

                                                        <td>
                                                            <div>
                                                                <a href="/remove-product-from-cart/${cartDetail.product.id}"
                                                                    style="display: block;margin-left: 15px; color: #71cd14;">Xóa</a>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <c:set var="sumInCart" value="${cartDetail.cart.sum}" />
                                                </c:forEach>

                                                <tr class="shipping_area">
                                                    <td colspan="3" style="vertical-align:top;">
                                                        <h5>Chọn đơn vị vận chuyển</h5>
                                                    </td>
                                                    <td colspan="2">
                                                        <div class="shipping_box">
                                                            <ul class="list">
                                                                <li>
                                                                    <a href="#">Giao hàng hỏa tốc: đ50.000</a>
                                                                </li>
                                                                <li>
                                                                    <a href="#">Giao hàng nhanh: đ30.000</a>
                                                                </li>
                                                                <li class="active">
                                                                    <a href="#">Giao hàng tiết kiệm: đ20.000</a>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td class="td-voucher-add" colspan="3">
                                                        <p><i style="color: orange; font-size: 11px;"
                                                                class="ti-layout-width-default"></i></p>
                                                        <p style="margin-left: 5px;">Voucher</p>
                                                        <p style="font-weight: bold;">
                                                            <button class="open-btn-add-voucher"
                                                                id="openModalBtnAddVoucher"
                                                                style="font-weight: 400;">Chọn hoặc nhập mã
                                                            </button>
                                                        </p>
                                                    </td>
                                                    <!-- <td colspan="1"></td> -->
                                                    <td colspan="2">
                                                        <h5 style="display: inline-block;">Tổng thanh toán (
                                                            <%=pageContext.getAttribute("sumInCart")%> sản
                                                                phẩm):
                                                        </h5>
                                                        <h5 style="color: #71cd14;display: inline-block;">
                                                            <fmt:formatNumber type="number" value="${totalPrice}" />
                                                            đ
                                                        </h5>
                                                        <div class="modal-overlay-add-voucher"
                                                            id="modalOverlayAddVoucher">
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
                                                                                Danh Sách Mã</p>
                                                                            <small>Có thể chọn một voucher</small>
                                                                        </div>

                                                                        <div class="voucher-item">
                                                                            <img src="https://go-korea.com/wp-content/themes/kadence-child/img/icon-su-kien.png"
                                                                                alt="Placeholder for Image">
                                                                            <div class="voucher-details">
                                                                                <div class="voucher-exp">Giảm tối đa
                                                                                    300k | Đơn tối thiểu 0₫<br>HSD:
                                                                                    27.11.2024</div>
                                                                            </div>
                                                                            <div class="voucher-checkbox">
                                                                                <input type="radio" name="voucher" />
                                                                            </div>
                                                                        </div>

                                                                        <div class="voucher-item">
                                                                            <img src="" alt="Placeholder for Image">
                                                                            <div class="voucher-details">
                                                                                <div class="voucher-exp">Giảm tối đa
                                                                                    300k | Đơn tối thiểu 0₫<br>HSD:
                                                                                    27.11.2024</div>
                                                                            </div>
                                                                            <div class="voucher-checkbox">
                                                                                <input type="radio" name="voucher" />
                                                                            </div>
                                                                        </div>

                                                                        <div class="voucher-item">
                                                                            <img src="" alt="Placeholder for Image">
                                                                            <div class="voucher-details">
                                                                                <div class="voucher-exp">Giảm tối đa 37k
                                                                                    | Đơn tối thiểu 0₫<br>HSD:
                                                                                    27.11.2024</div>
                                                                            </div>
                                                                            <div class="voucher-checkbox">
                                                                                <input type="radio" name="voucher" />
                                                                            </div>
                                                                        </div>
                                                                        <!-- Thêm nhiều class="voucher-item" để kiểm tra cuộn -->
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

                                                <tr class="out_button_area">
                                                    <td colspan="2"></td>
                                                    <td colspan="3">
                                                        <div class="checkout_btn_inner">
                                                            <a class="gray_btn" href="/products">Tiếp tục mua sắm</a>
                                                            <a class="main_btn" href="/order">Đi đến thanh toán</a>
                                                        </div>
                                                    </td>
                                                </tr>

                                                <!-- <tr class="bottom_button">
                                                    <td>
                                                        <a class="gray_btn" href="#">Update Cart</a>
                                                    </td>
    
                                                    <td colspan="4">
                                                        <div class="cupon_text">
                                                            <input type="text" placeholder="Coupon Code" />
                                                            <a class="main_btn" href="#">Apply</a>
                                                            <a class="gray_btn" href="#">Close Coupon</a>
                                                        </div>
                                                    </td>
                                                </tr> -->


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
                    <script src="/js/jquery-3.2.1.min.js"></script>
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
                    <script src="/vendors/counter-up/jquery.waypoints.min.js"></script>
                    <script src="/vendors/counter-up/jquery.counterup.js"></script>
                    <script src="/js/theme.js"></script>
                    <script src="/js/myjs.js"></script>
                    <!-- <script>
                        // Lấy các phần tử
                        const modalOverlayAddVoucher = document.getElementById('modalOverlayAddVoucher');
                        const openModalBtnAddVoucher = document.getElementById('openModalBtnAddVoucher');
                        const closeModalBtnAddVouCher = document.getElementById('closeModalBtnAddVouCher');

                        // Sự kiện mở modal
                        openModalBtnAddVoucher.addEventListener('click', () => {
                            modalOverlayAddVoucher.style.display = 'block'; // Hiển thị modal
                        });

                        // Sự kiện đóng modal
                        closeModalBtnAddVouCher.addEventListener('click', () => {
                            modalOverlayAddVoucher.style.display = 'none'; // Ẩn modal
                        });

                        // Đóng modal khi click ra ngoài nội dung
                        modalOverlayAddVoucher.addEventListener('click', (e) => {
                            if (e.target === modalOverlayAddVoucher) {
                                modalOverlayAddVoucher.style.display = 'none'; // Ẩn modal
                            }
                        });
                    </script> -->
                </body>

                </html>