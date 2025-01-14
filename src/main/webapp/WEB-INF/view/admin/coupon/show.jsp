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

                <div id="layoutSidenav_content" style="background-color: rgb(249, 249, 249); font-size: 14px;">
                    <main>
                        <div class="container-fluid px-4">
                            <div class="d-flex justify-content-between" style="margin-top: 10px;">
                                <h3>Danh sách khuyến mãi</h3>
                                <a class="css-67mbka e4zt08y7" id="order-create-btn" data-segment-control="true"
                                    href="/admin/orders/create">
                                    <span class="css-slup14 e4zt08y4">
                                        <span class="css-y5qmm9 e4zt08y3">
                                            <span class="css-1o24pcm e16p30ob1">
                                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                                    focusable="false" aria-hidden="true">
                                                    <path fill="currentColor" fill-rule="evenodd"
                                                        d="M12 2c-5.52 0-10 4.48-10 10s4.48 10 10 10 10-4.48 10-10-4.48-10-10-10m-1 5v4h-4v2h4v4h2v-4h4v-2h-4v-4zm-7 5c0 4.41 3.59 8 8 8s8-3.59 8-8-3.59-8-8-8-8 3.59-8 8"
                                                        clip-rule="evenodd"></path>
                                                </svg>
                                            </span>
                                        </span>
                                        <span class="css-1xdhyk6 e4zt08y0">Tạo khuyến mại
                                        </span>
                                    </span>
                                </a>
                            </div>

                            <div class="container mt-3 d-flex flex-column container-background"
                                style="padding-left: 0px;padding-right: 0px;">
                                <div class="tab-btns d-flex">
                                    <div class="btn-tab">
                                        <span>Tất cả</span>
                                    </div>
                                    <div class="btn-tab">
                                        <span>Đang áp dụng</span>
                                    </div>
                                    <div class="btn-tab">
                                        <span>Ngừng áp dụng</span>
                                    </div>
                                </div>
                                <div class="search-cell d-flex" style="gap: 15px;">
                                    <div class="d-flex">
                                        <div class="search-box-coupon border-input d-flex">
                                            <span class="icon-search css-rkie3g"><i
                                                    class="fa-solid fa-magnifying-glass"></i></span>
                                            <input id="" class="inputSearch" type="text"
                                                placeholder="Tìm theo mã khuyến mãi" style="">
                                        </div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="border-input input-date" style="margin-right: 10px;">
                                            <input type="date">
                                        </div>

                                        <div class="border-input input-date">
                                            <input type="date">
                                        </div>
                                    </div>
                                </div>
                                <div class="table-coupon">
                                    <table style="width: 100%;">
                                        <thead>
                                            <th>Khuyến mại</th>
                                            <th>Trạng thái</th>
                                            <th>Đã dùng</th>
                                            <th>Ngày bắt đầu</th>
                                            <th>Ngày kết thúc</th>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <div class="coupon-code">
                                                        <span>39834IDJISDJSI</span>
                                                    </div>
                                                    <div class="coupon-discount-value">
                                                        <span>Giảm 10% tối đa 20.000 đ</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <span class="css-gr2olx">Đang hoạt động</span>
                                                </td>
                                                <td><span>0</span></td>
                                                <td><span> 12/01/2025 14:30</span></td>
                                                <td><span> 12/01/2025 14:30</span></td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <div class="coupon-code">
                                                        <span>39834IDJISDJSI</span>
                                                    </div>
                                                    <div class="coupon-discount-value">
                                                        <span>Giảm 10% tối đa 20.000 đ</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <span class="css-gr2olx">Đang hoạt động</span>
                                                </td>
                                                <td><span>0</span></td>
                                                <td><span> 12/01/2025 14:30</span></td>
                                                <td><span> 12/01/2025 14:30</span></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="footer d-flex justify-content-between" style="padding: 20px;">
                                    <div class="" style="margin: auto 0;"><span>Từ 1 đến 1 trên tổng 1</span></div>
                                    <div class="d-flex" style="gap: 8px;">
                                        <div class="" style="margin: auto;">
                                            <span style="text-align: center;">Hiển thị</span>
                                        </div>
                                        <div class="">
                                            <select name="" id="slectPageSize">
                                                <option value="">5</option>
                                                <option value="">10</option>
                                                <option value="">20</option>
                                            </select>
                                        </div>
                                        <div class="" style="margin: auto 0;"><span>Kết quả</span></div>
                                    </div>
                                    <div class="" style="margin: auto 0;">
                                        <span>1</span>
                                        <span>1</span>
                                        <span>1</span>
                                    </div>
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
            <script src="/admin/js/coupon/coupon.js"></script>
        </body>

        </html>