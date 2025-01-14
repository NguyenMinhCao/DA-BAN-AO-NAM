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

                                    <a class="header__btn-link" href="/admin/orders">
                                        <span class="header__btn-icon"><i class="fa-solid fa-arrow-left"></i></span>
                                    </a>
                                    <div class="">
                                        <h4 style="line-height: 37.6px; margin-bottom: 0px;">Thêm mới mã giảm giá</h4>
                                    </div>
                                </div>
                                <div class="body-create d-flex" style="gap: 15px;">
                                    <div class="box-left d-flex flex-column" style="gap: 15px;">
                                        <div class="coupon-code-input container-background">
                                            <div
                                                class="header-input-code header-padding d-flex justify-content-between">
                                                <span class="heading-header">Mã khuyến mãi</span>
                                                <span style="color: rgb(0, 136, 255);">Tạo mã ngẫu nhiên</span>
                                            </div>
                                            <div class="pading-20 d-flex flex-column" style="gap: 5px;">
                                                <span>Mã khuyến mãi </span>
                                                <div class="input-code-value">
                                                    <input type="text" placeholder="VD: Coupon 10%">
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
                                                            <button class="btn-fixed">Số tiền</button>
                                                            <button class="btn-percient">%</button>
                                                        </div>
                                                    </div>

                                                    <div class="d-flex flex-column justify-content-end">
                                                        <span> </span>
                                                        <div class="border-common">
                                                            <input type="text" class="input-percent">
                                                            <span style="padding: 0px 14px;">%</span>
                                                        </div>
                                                    </div>
                                                    <div class="">
                                                        <span style="margin-bottom: 5px;display: block">Giá trị giảm tối
                                                            đa</span>
                                                        <div class="border-common">
                                                            <input type="text" class="input-max">
                                                            <span style="padding: 0px 14px;">đ</span>
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
                                                    <input type="radio">
                                                    <span>Không có</span>
                                                </div>
                                                <div class="" style="padding: 5px 0;">
                                                    <input type="radio">
                                                    <span>Tổng giá trị đơn hàng tối thiểu</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="container-background">
                                            <div
                                                class="header-input-code header-padding d-flex justify-content-between">
                                                <span class="heading-header">Giới hạn sử dụng</span>
                                            </div>

                                            <div class="pading-20">
                                                <div class="" style="padding: 5px 0;">
                                                    <input type="checkbox">
                                                    <span>Giới hạn sử dụng</span>
                                                </div>
                                                <div class="" style="padding: 5px 0;">
                                                    <input type="checkbox">
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
                                                        <input type="date" class="w-100 border-common"
                                                            style="padding:0 8px;">
                                                    </div>
                                                </div>
                                                <div class="" style="width: 50%;">
                                                    <div class="">
                                                        <span>Ngày kết thúc</span>
                                                    </div>
                                                    <div class="">
                                                        <input type="date" class="w-100 border-common"
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
                                                        <h5 style="margin-bottom: 0px;">29MCUTS5ZHHO</h5>
                                                    </div>
                                                    <div class="" style="padding: 16px;">
                                                        <ul>
                                                            <li>Giảm 10% tối đa 20,000₫ cho toàn bộ đơn hàng</li>
                                                            <li>Áp dụng từ 12/01/2025 14:30</li>
                                                            <li>Đã sử dụng 0 lần</li>
                                                            <li>Tổng giá trị đơn hàng được khuyến mại phải tối thiểu là
                                                                100,000₫</li>
                                                            <li>Mã được phép sử dụng 2 lần</li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="footer-create d-flex justify-content-end">
                                    <button id="btn-cancel-create-coupon">Hủy</button>
                                    <button id="btn-confirm-create-coupon">Tạo khuyến mại</button>
                                </div>
                            </div>
                        </div>
                    </main>
                    <jsp:include page="../layout/footer.jsp" />
                </div>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                crossorigin="anonymous"></script>
            <script src="/admin/js/scripts.js"></script>
        </body>

        </html>