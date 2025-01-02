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
                    <link rel="stylesheet" href="/css/bootstrap.css" />
                    <link rel="stylesheet" href="/vendors/linericon/style.css" />
                    <link rel="stylesheet" href="/css/font-awesome.min.css" />
                    <link rel="stylesheet" href="/css/themify-icons.css" />
                    <link rel="stylesheet" href="/vendors/owl-carousel/owl.carousel.min.css" />
                    <link rel="stylesheet" href="/vendors/lightbox/simpleLightbox.css" />
                    <link rel="stylesheet" href="/vendors/nice-select/css/nice-select.css" />
                    <link rel="stylesheet" href="/vendors/animate-css/animate.css" />
                    <link rel="stylesheet" href="/vendors/jquery-ui/jquery-ui.css" />
                    <!-- main css -->
                    <link rel="stylesheet" href="/css/style.css" />
                    <link rel="stylesheet" href="/css/responsive.css" />
                    <link rel="stylesheet" href="/css/style1.css" />
                    <link rel="stylesheet" href="/css/style-account.css" />

                </head>

                <body>
                    <!--================Header Menu Area =================-->
                    <jsp:include page="../layout/header.jsp" />
                    <!--================Header Menu Area =================-->


                    <div class="container mt-5" style="max-width: 1328px; margin-bottom: 120px;">
                        <div class="row">
                            <!-- Sidebar -->
                            <jsp:include page="../layout/sidebarAcc.jsp" />

                            <!-- Profile Section -->
                            <div class="col-md-9">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="" style="display: flex;">
                                            <h4 class="" style="margin: 0; padding: 0; flex: 1;">
                                                <p style="margin-top: 0;
                                                margin-bottom: 0;
                                                line-height: 29.2px;">Địa chỉ
                                                    của
                                                    tôi</p>
                                            </h4>
                                            <button style="padding: 0 7px; font-size: 12px; line-height: 31px;"
                                                class="main_btn" id="openModalBtnAddAddressAcc">Thêm địa
                                                chỉ mới
                                            </button>
                                        </div>
                                        <hr>
                                        <div style="font-size: 1.125rem;
                                        line-height: 1.75rem;
                                        margin-bottom: 30px; color: #000;">Địa chỉ</div>

                                        <c:forEach items="${arrAddressByUser}" var="address" varStatus="status">
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
                                                            <div class="ieb1A9">${address.address}</div>
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
                                            <c:if test="${!status.last}">
                                                <hr>
                                            </c:if>
                                        </c:forEach>

                                    </div>
                                    <!-- Bắt đầu nội dung box thêm địa chỉ -->
                                    <div class="modal-overlay-add-address" id="modalOverlayAddAddress">
                                        <div class="modal-content-add-address">
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
                                                                        style="margin-right: 10px;"
                                                                        name="address_select">
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

                                        </div>
                                    </div>
                                    <!-- Kết thúc nội dung box thêm địa chỉ -->


                                    <!-- Bắt đầu nội dung box cập nhật địa chỉ -->
                                    <div class="modal-overlay-update-address" id="modalOverlayUpdateAddress">
                                        <div class="modal-content-update-address">
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
                                                                            name="user_address_phone"
                                                                            id="userAddressPhone" value="">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="k81zo2">
                                                                <div class="B3Z66j">
                                                                    <div class="fD7jc0">
                                                                        <div class="RyrP3M">
                                                                            <input class="uU_7Kb" type="text"
                                                                                placeholder="Tỉnh/ Thành phố, Quận/Huyện, Phường/Xã"
                                                                                name="user_address" id="userAddress"
                                                                                value="">
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
                                                                                maxlength="128"
                                                                                id="userStreetAddress"></input>
                                                                        </div>
                                                                    </div>
                                                                    <div class="GDmj6q"></div>
                                                                </div>
                                                            </div>
                                                            <div class="htdX_R">
                                                                <label class="M64DVD">
                                                                    <input class="QDGDYv" type="checkbox"
                                                                        style="margin-right: 10px;"
                                                                        name="address_select">
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
                                        </div>
                                    </div>
                                    <!-- Kết thúc nội dung box cập nhật địa chỉ -->
                                </div>
                            </div>

                        </div>
                    </div>

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
                    <script src="/js/ajaxjs.js"></script>
                    <script src="/js/myjs.js"></script>
                    <script src="/js/js-account.js"></script>
                </body>

                </html>