<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Dashboard - SB Admin</title>
            <link href="/admin/css/styles.css" rel="stylesheet" />
            <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            <link href="https://cdn.jsdelivr.net/npm/@sweetalert2/theme-dark@4/dark.css" rel="stylesheet">

            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="/admin/css/coupon/coupon.css" rel="stylesheet" />
            <link rel="stylesheet" href="/common/toast.css">
        </head>

        <body class="sb-nav-fixed">
            <jsp:include page="../layout/header.jsp" />
            <div id="layoutSidenav">
                <jsp:include page="../layout/sidebar.jsp" />

                <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid" style="padding: 0 80px;">
                            <div class="container mt-5 d-flex flex-column"
                                style="margin-top: 0px !important; padding:0 0 !important">
                                <div class="header-create d-flex">

                                    <a class="header__btn-link" href="/admin/coupon">
                                        <span class="header__btn-icon"><i class="fa-solid fa-arrow-left"></i></span>
                                    </a>
                                    <div class="d-flex">
                                        <h4 style="line-height: 37.6px; margin-bottom: 0px;">${couponById.couponCode}
                                        </h4>
                                        <c:if test="${couponById.status == true}">
                                            <span class="css-gr2olx" style="margin-left: 10px; align-self: center;">Đang
                                                hoạt
                                                động</span>
                                        </c:if>
                                        <c:if test="${couponById.status == false}">
                                            <span class="css-3u37zr"
                                                style="text-align:center;margin-left:10px; align-self: center;">Ngừng
                                                áp dụng</span>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="body-create d-flex" style="gap: 15px;">
                                    <div class="box-left d-flex flex-column" style="gap: 15px;">
                                        <div class="coupon-code-input container-background">
                                            <div
                                                class="header-input-code header-padding d-flex justify-content-between">
                                                <span class="heading-header">Mã khuyến mãi</span>
                                                <span style="color: rgb(0, 136, 255);" id="createCodeRenderNew">Tạo mã
                                                    ngẫu nhiên</span>
                                            </div>
                                            <div class="pading-20 d-flex flex-column" style="gap: 5px;">
                                                <span>Mã khuyến mãi </span>
                                                <div class="input-code-value">
                                                    <input type="text" value="${couponById.couponCode}"
                                                        placeholder=" VD: Coupon 10%" id="value-code-input">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="container-background">
                                            <div
                                                class="header-input-code header-padding d-flex justify-content-between">
                                                <span class="heading-header">Giá trị</span>
                                            </div>
                                            <div class="pading-20 coupon-value">
                                                <div class="d-flex" style="gap: 15px;">
                                                    <div class="">
                                                        <span style="margin-bottom: 5px;display: block">Giá trị khuyến
                                                            mại</span>
                                                        <div class="border-common d-flex" style="width: 139px;">
                                                            <!-- theo dữ server trả về  -->
                                                            <c:if test="${couponById.discountType == 'PERCENTAGE'}">
                                                                <button class="btn-fixed">Số tiền</button>
                                                                <button
                                                                    class="btn-percient back-ground-focus">%</button>
                                                            </c:if>
                                                            <!-- theo dữ server trả về  -->
                                                            <c:if test="${couponById.discountType == 'FIXED'}">
                                                                <button class="btn-fixed back-ground-focus">Số
                                                                    tiền</button>
                                                                <button class="btn-percient">%</button>
                                                            </c:if>
                                                        </div>
                                                    </div>

                                                    <!-- Phần Giảm Giá Theo fixed -->
                                                    <div id="fixed-section"
                                                        class="d-flex flex-column justify-content-end">
                                                        <span> </span>
                                                        <div class="border-common">
                                                            <input type="text" value="${couponById.discountValueFixed}"
                                                                class="input-percent input-value-reduce">
                                                            <span style="padding: 0px 14px;">đ</span>
                                                        </div>
                                                    </div>

                                                    <!-- Phần Giảm Giá Theo Phần Trăm -->
                                                    <div id="percentage-section" class="discount-section d-flex"
                                                        style="gap: 15px;">
                                                        <div class="d-flex flex-column justify-content-end">
                                                            <span> </span>
                                                            <div class="border-common">
                                                                <input type="text"
                                                                    value="${couponById.discountValuePercent}"
                                                                    class="input-percent input-value-reduce">
                                                                <span style="padding: 0px 14px;">%</span>
                                                            </div>
                                                        </div>
                                                        <div class="">
                                                            <span style="margin-bottom: 5px; display: block">Giá trị
                                                                giảm tối đa</span>
                                                            <div class="border-common">
                                                                <input type="text" class="input-max" id="inputMaxValue"
                                                                    value="${couponById.maximumReduction}">
                                                                <span style="padding: 0px 14px;">đ</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="container-background">
                                            <div
                                                class="header-input-code header-padding d-flex justify-content-between">
                                                <span class="heading-header">Điều kiện áp dụng</span>
                                            </div>

                                            <div class="pading-20">
                                                <div class="" style="padding: 5px 0;">
                                                    <c:if test="${couponById.minimumValue == null}">
                                                        <input type="radio" checked id="checked-nothing"
                                                            name="minimumValueOption">
                                                    </c:if>
                                                    <c:if test="${couponById.minimumValue != null}">
                                                        <input type="radio" id="checked-nothing"
                                                            name="minimumValueOption">
                                                    </c:if>
                                                    <span>Không có</span>
                                                </div>

                                                <div class="minimum-total-value">
                                                    <div class="">
                                                        <input type="radio" name="minimumValueOption" id="checked-yes"
                                                            checked>
                                                        <span>Tổng giá trị đơn hàng tối thiểu</span>
                                                    </div>
                                                    <div class="border-common enter-minium-value"
                                                        id="minimum-value-input">
                                                        <input type="text" value="${couponById.maximumReduction}"
                                                            class="input-max input-minium-total-value">
                                                        <span class="span-display-dong">đ</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="container-background" style="display: none;">
                                            <div
                                                class="header-input-code header-padding d-flex justify-content-between">
                                                <span class="heading-header">Giới hạn sử dụng</span>
                                            </div>

                                            <div class="pading-20">
                                                <div class="css-padding-5px">
                                                    <input type="checkbox" id="usageLimitCheckbox">
                                                    <span>Giới hạn sử dụng</span>
                                                    <div class="" id="usageLimitInputDiv">
                                                        <input type="text" value="${couponById.maximumReduction}"
                                                            class="input-max border-common" placeholder="1"
                                                            id="inputUsageLimit">
                                                    </div>

                                                </div>
                                                <div class="css-padding-5px">
                                                    <input type="checkbox" id="customerLimitCheckbox">
                                                    <span>Giới hạn mỗi khách hàng chỉ được sử dụng mã giảm giá này 1
                                                        lần</span>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="container-background">
                                            <div
                                                class="header-input-code header-padding d-flex justify-content-between">
                                                <span class="heading-header">Thời gian</span>
                                            </div>

                                            <div class="pading-20 d-flex" style="gap: 15px;">
                                                <div class="" style="width: 50%;">
                                                    <div class="">
                                                        <span>Ngày bắt đầu</span>
                                                    </div>
                                                    <div class="">
                                                        <input type="datetime-local" id="selectStartDate"
                                                            value="${startDate}" class="w-100 border-common"
                                                            style="padding:0 8px;">
                                                    </div>
                                                </div>
                                                <div class="" style="width: 50%;">
                                                    <div class="">
                                                        <span>Ngày kết thúc</span>
                                                    </div>
                                                    <div class="">
                                                        <input type="datetime-local" id="selectEndDate"
                                                            value="${endDate}" class="w-100 border-common"
                                                            style="padding:0 5px;">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="box-right container-background d-flex flex-column">
                                        <div class="container-background">
                                            <div
                                                class="header-input-code header-padding d-flex justify-content-between">
                                                <span class="heading-header">Tổng quan khuyến mại</span>
                                            </div>

                                            <div class="pading-20 coupon-details">
                                                <div class="coupon-detail-text">
                                                    <div class="pading-16 header-coupon-detail">
                                                        <h5 style="margin-bottom: 0px;" id="display-header-code">
                                                            ${couponById.couponCode}</h5>
                                                    </div>
                                                    <div class="" style="padding: 16px;">
                                                        <ul>
                                                            <li>Giảm
                                                                <span class="reduced-value">0</span>
                                                                <span class="reduced-type"></span>
                                                                <span class="maximum-reduction"
                                                                    style="display: none;">tối đa</span>
                                                                cho toàn bộ đơn hàng
                                                            </li>
                                                            <li id="conditionsApply">
                                                                <span id="minimum-order-value">Tổng giá trị đơn hàng
                                                                    được khuyến mại phải tối
                                                                    thiểu là 1₫
                                                                </span>
                                                            </li>
                                                            <li id="numberUsed">
                                                                Mã được phép sử dụng
                                                                <span class="number-times-used">1</span>
                                                                lần
                                                            </li>
                                                            <li id="limited-user">
                                                                Mỗi khách
                                                                hàng được sử dụng
                                                                <span class="limited-one-time">1</span> lần
                                                            </li>
                                                            <li id="startDateandEndDate">
                                                                Áp dụng từ <span class="startDateDis"></span> đến <span
                                                                    class="endDateDis"></span>
                                                            </li>
                                                            <li>Đã sử dụng 0 lần</li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="footer-create d-flex justify-content-end">
                                    <button id="btn-cancel-create-coupon">Hủy</button>
                                    <button code-coupon-by-id="${couponById.couponCode}"
                                        id-coupon-by-id="${couponById.id}" id="btn-update-create-coupon">Lưu</button>
                                </div>
                            </div>
                        </div>
                    </main>
                    <jsp:include page="../layout/footer.jsp" />
                    <div id="toast"></div>
                </div>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                crossorigin="anonymous"></script>
            <script src="/admin/js/scripts.js"></script>
            <script src="/common/toast.js"></script>
            <script src="/admin/js/coupon/coupon.js"></script>
        </body>

        </html>