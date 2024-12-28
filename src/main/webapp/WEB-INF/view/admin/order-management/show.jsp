<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Dashboard - SB Admin</title>
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <link href="https://cdn.jsdelivr.net/npm/@sweetalert2/theme-dark@4/dark.css" rel="stylesheet">
                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

                <link href="/admin/css/styles.css" rel="stylesheet" />
                <link href="/admin/css/order/order-management.css" rel="stylesheet" />
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />

                    <div id="layoutSidenav_content" style="background-color: rgb(249, 249, 249);">
                        <main>
                            <div class="container-fluid px-4">
                                <div class="d-flex justify-content-between align-items-center">
                                    <h3 class="mt-3">Danh sách hóa đơn</h3>
                                    <div class="css-llur1c e106wl7i1">
                                        <a class="css-67mbka e4zt08y7" id="order-create-btn" data-segment-control="true"
                                            href="/admin/orders/create">
                                            <span class="css-slup14 e4zt08y4">
                                                <span class="css-y5qmm9 e4zt08y3">
                                                    <span class="css-1o24pcm e16p30ob1">
                                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none"
                                                            viewBox="0 0 24 24" focusable="false" aria-hidden="true">
                                                            <path fill="currentColor" fill-rule="evenodd"
                                                                d="M12 2c-5.52 0-10 4.48-10 10s4.48 10 10 10 10-4.48 10-10-4.48-10-10-10m-1 5v4h-4v2h4v4h2v-4h4v-2h-4v-4zm-7 5c0 4.41 3.59 8 8 8s8-3.59 8-8-3.59-8-8-8-8 3.59-8 8"
                                                                clip-rule="evenodd"></path>
                                                        </svg>
                                                    </span>
                                                </span>
                                                <span class="css-1xdhyk6 e4zt08y0">Tạo đơn
                                                    hàng
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                </div>

                                <div class="container mt-3 px-0">
                                    <!-- tab -->
                                    <ul class="tablist-order d-flex">
                                        <li>
                                            <button class="css-1hfoem2"><span class="css-1h1oecx e19wcowe2">Tất
                                                    cả</span>
                                            </button>
                                        </li>
                                        <li>
                                            <button class="css-1hfoem2"><span class="css-1h1oecx e19wcowe2">Chưa xử lý
                                                    giao
                                                    hàng
                                                </span>
                                            </button>
                                        </li>
                                        <li>
                                            <button class="css-1hfoem2"><span class="css-1h1oecx e19wcowe2">Chở lấy hàng
                                                </span>
                                            </button>
                                        </li>
                                        <li>
                                            <button class="css-1hfoem2"><span class="css-1h1oecx e19wcowe2">Đang chờ
                                                    giao
                                                    hàng</span>
                                            </button>
                                        </li>
                                        <li>
                                            <button class="css-1hfoem2"><span class="css-1h1oecx e19wcowe2">Chưa thanh
                                                    toán</span>
                                            </button>
                                        </li>
                                    </ul>
                                    <!-- filter -->
                                    <div class="filter-cells">
                                        <!-- ô nhập tìm kiếm -->
                                        <div class="css-1o5a78p e1ax7ei21">
                                            <div id="UITextField1-Prefix" class="css-1yb7d7m e1ax7ei24"><span
                                                    class="css-rkie3g e16p30ob1"><svg xmlns="http://www.w3.org/2000/svg"
                                                        fill="none" viewBox="0 0 24 24" focusable="false"
                                                        aria-hidden="true">
                                                        <path fill="currentColor" fill-rule="evenodd"
                                                            d="M14.891 13.477a6.002 6.002 0 0 0-9.134-7.72 6 6 0 0 0 7.72 9.134l5.715 5.716 1.415-1.415zm-2.063-6.305a4 4 0 1 1-5.656 5.656 4 4 0 0 1 5.656-5.656"
                                                            clip-rule="evenodd"></path>
                                                    </svg></span></div><input id="UITextField1"
                                                placeholder="Tìm kiếm theo mã đơn hàng, vận đơn, SĐT khách hàng"
                                                autocomplete="off" type="text" class="css-1ybyoqp e1ax7ei25" value="">
                                            <div data-segment-control="true" class="css-exmh99 e1ax7ei26"></div>
                                        </div>
                                        <!-- ô chọn -->
                                        <div class="css-plw1da e29dk0t2">
                                            <div><span class="css-6srwa1 es0yzet0"><button type="button"
                                                        data-segment-control="true" class="css-1vssf6j e4zt08y8"
                                                        tabindex="0" aria-controls="UIPopover8" aria-owns="UIPopover8"
                                                        aria-expanded="false" data-state="closed"><span
                                                            class="css-slup14 e4zt08y4"><span
                                                                class="css-1xdhyk6 e4zt08y0">Trạng thái giao
                                                                hàng</span><span class="css-15fzge e4zt08y2"><span
                                                                    class="css-1o24pcm e16p30ob1"><svg
                                                                        xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                        viewBox="0 0 24 24" focusable="false"
                                                                        aria-hidden="true">
                                                                        <path fill="currentColor" d="m7 9.5 5 5 5-5z">
                                                                        </path>
                                                                    </svg></span></span></span></button></span></div>
                                            <div><span class="css-2f3rpz es0yzet0"><button type="button"
                                                        data-segment-control="true" class="css-1vssf6j e4zt08y8"
                                                        tabindex="0" aria-controls="UIPopover9" aria-owns="UIPopover9"
                                                        aria-expanded="false" data-state="closed"><span
                                                            class="css-slup14 e4zt08y4"><span
                                                                class="css-1xdhyk6 e4zt08y0">Ngày tạo</span><span
                                                                class="css-15fzge e4zt08y2"><span
                                                                    class="css-1o24pcm e16p30ob1"><svg
                                                                        xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                        viewBox="0 0 24 24" focusable="false"
                                                                        aria-hidden="true">
                                                                        <path fill="currentColor" d="m7 9.5 5 5 5-5z">
                                                                        </path>
                                                                    </svg></span></span></span></button></span>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- table -->
                                    <div class="">
                                        <table class="css-18ohjh3 e1dc61xd0" style="border: 2;">
                                            <thead>
                                                <tr>
                                                    <th class="css-cnbeka">Mã đơn hàng</th>
                                                    <th class="css-cnbeka">Ngày tạo</th>
                                                    <th class="css-cnbeka">Khách hàng</th>
                                                    <th class="css-cnbeka">Nguồn đơn</th>
                                                    <th class="css-cnbeka">Thành tiền</th>
                                                    <th class="css-cnbeka">Trạng thái thanh toán</th>
                                                    <th class="css-cnbeka">Trạng thái xử lý</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${lstOrder}" var="order" varStatus="i">
                                                    <tr class="css-1n5r022">
                                                        <td class="css-166t3yd e160qq0x2">
                                                            <div class="css-1xdhyk6 e1f1ig8p0"><a
                                                                    class="css-1w5iufu ehy6p5f0"
                                                                    href="/admin/orders/15816991">
                                                                    <div style="display: flex;"><span
                                                                            class="css-dbt8sz e1nigx955"><span
                                                                                class="css-1qvvvsu enzfz0r0">#</span></span><span
                                                                            class="css-dbt8sz e1nigx955"><span
                                                                                class="css-1qvvvsu enzfz0r0">${order.id}</span></span>
                                                                    </div>
                                                                </a>
                                                            </div>
                                                        </td>
                                                        <td class="css-166t3yd e160qq0x2"><span
                                                                class="css-dbt8sz e1nigx955">
                                                                ${formattedDate[i.index]}
                                                            </span>
                                                        </td>
                                                        <td class="css-166t3yd e160qq0x2"><span
                                                                class="css-dbt8sz e1nigx955"><span
                                                                    class="css-1qvvvsu enzfz0r0">
                                                                    <div style="width: 125px;"><span
                                                                            class="css-ii5m0c e7ltd9p0">${order.user.fullName}</span>
                                                                    </div>
                                                                </span></span>
                                                        </td>
                                                        <td class="css-166t3yd e160qq0x2"><span
                                                                class="css-dbt8sz e1nigx955"><span
                                                                    class="css-1qvvvsu enzfz0r0">
                                                                    <div style="width: 125px;"><span
                                                                            class="css-ii5m0c e7ltd9p0">
                                                                            <c:if test="${order.orderSource == true}">
                                                                                Website
                                                                            </c:if>
                                                                            <c:if test="${order.orderSource == false}">
                                                                                Tại quầy
                                                                            </c:if>
                                                                        </span></div>
                                                                </span></span>
                                                        </td>
                                                        <td class="css-166t3yd e160qq0x2">
                                                            <div class="css-4fkh5d e1ehoxn20">
                                                                <div class="css-thtl67 e14jmgg0"><span
                                                                        class="css-dbt8sz e1nigx955"><span
                                                                            class="css-1qvvvsu enzfz0r0">
                                                                            <fmt:formatNumber type="number"
                                                                                value="${order.totalAmount}" />
                                                                            đ
                                                                        </span></span>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td class="css-166t3yd e160qq0x2">
                                                            <div class="css-1huj7c9 e1ehoxn20">
                                                                <div class="css-thtl67 e14jmgg0"><span
                                                                        class="css-2kmgkw e8ptwd0"><span
                                                                            class="css-ljhpxb e8ptwd1"><span
                                                                                class="css-1o24pcm e16p30ob1"><svg
                                                                                    viewBox="0 0 20 20" fill="none">
                                                                                    <path fill-rule="evenodd"
                                                                                        clip-rule="evenodd"
                                                                                        d="M10 13C11.66 13 13 11.66 13 10C13 8.34 11.66 7 10 7C8.34 7 7 8.34 7 10C7 11.66 8.34 13 10 13ZM6 10C6 12.21 7.79 14 10 14C12.21 14 14 12.21 14 10C14 7.79 12.21 6 10 6C7.79 6 6 7.79 6 10Z"
                                                                                        fill="currentColor"></path>
                                                                                </svg></span></span><span
                                                                            class="css-z8vxi5 enzfz0r0">Chưa thanh
                                                                            toán</span></span>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td class="css-166t3yd e160qq0x2">
                                                            <div class="css-k9p4ua e1ehoxn20">
                                                                <div class="css-thtl67 e14jmgg0"><span
                                                                        class="css-2kmgkw e8ptwd0"><span
                                                                            class="css-ljhpxb e8ptwd1"><span
                                                                                class="css-1o24pcm e16p30ob1"><svg
                                                                                    viewBox="0 0 20 20" fill="none">
                                                                                    <path fill-rule="evenodd"
                                                                                        clip-rule="evenodd"
                                                                                        d="M10 13C11.66 13 13 11.66 13 10C13 8.34 11.66 7 10 7C8.34 7 7 8.34 7 10C7 11.66 8.34 13 10 13ZM6 10C6 12.21 7.79 14 10 14C12.21 14 14 12.21 14 10C14 7.79 12.21 6 10 6C7.79 6 6 7.79 6 10Z"
                                                                                        fill="currentColor"></path>
                                                                                </svg></span></span><span
                                                                            class="css-z8vxi5 enzfz0r0">Chưa xử lý giao
                                                                            hàng</span>
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- pagination -->
                                    <div class="css-10rtstj">
                                        <div class="css-12jjl5s">
                                            <div class="css-thtl67 e14jmgg0"><span class="css-3ciuc3 enzfz0r0">Từ 1 đến
                                                    5
                                                    trên tổng 5</span></div>
                                            <div class="css-1iytplx e14jmgg0">
                                                <div class="css-17oe0xs e1ehoxn20">
                                                    <div class="css-thtl67 e14jmgg0"><span style="margin-right: 7px;"
                                                            class="css-3ciuc3 enzfz0r0">Hiển
                                                            thị</span></div>
                                                    <div class="css-thtl67 e14jmgg0">
                                                        <div>
                                                            <div class="css-0 e1d2ile02">
                                                                <div class="css-bjn8wh e115wh4o5"><input
                                                                        id="UISelect232" role="combobox" readonly=""
                                                                        inputmode="none" autocomplete="off"
                                                                        autocorrect="off" autocapitalize="none"
                                                                        spellcheck="false" aria-invalid="false"
                                                                        aria-required="false"
                                                                        class="css-1w94bv2 e115wh4o3" value="20"
                                                                        tabindex="0" aria-controls="UIPopover82"
                                                                        aria-owns="UIPopover82" aria-expanded="false"
                                                                        data-state="closed"><span
                                                                        data-segment-control="true"
                                                                        class="css-k1knd3 e115wh4o4"></span>
                                                                    <div class="css-2ry0el e115wh4o2">
                                                                        <span class="css-1dflzr4 e115wh4o0">20</span>
                                                                        <span class="css-0 e115wh4o1"><span
                                                                                class="css-rkie3g e16p30ob1"><svg
                                                                                    xmlns="http://www.w3.org/2000/svg"
                                                                                    fill="none" viewBox="0 0 24 24"
                                                                                    focusable="false"
                                                                                    aria-hidden="true">
                                                                                    <path fill="currentColor"
                                                                                        d="m7 9.5 5 5 5-5z"></path>
                                                                                </svg>
                                                                            </span>
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="css-thtl67 e14jmgg0"><span style="margin-left: 7px;"
                                                            class="css-3ciuc3 enzfz0r0">Kết
                                                            quả</span></div>
                                                </div>
                                            </div>
                                            <div class="css-thtl67 e14jmgg0">
                                                <div class="css-18c5rtc e1lvcblw0"><button disabled=""
                                                        class="css-1u0jvzv e1lvcblw2"><span
                                                            class="css-rkie3g e16p30ob1"><svg
                                                                xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                viewBox="0 0 24 24" focusable="false"
                                                                aria-hidden="true">
                                                                <path fill="currentColor"
                                                                    d="m14.298 5.99-6.01 6.01 6.01 6.01 1.414-1.414-4.596-4.596 4.596-4.596z">
                                                                </path>
                                                            </svg></span></button><button
                                                        class="css-1abf0ql e1lvcblw2">1</button><button disabled=""
                                                        class="css-1u0jvzv e1lvcblw2"><span
                                                            class="css-rkie3g e16p30ob1"><svg
                                                                xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                viewBox="0 0 24 24" focusable="false"
                                                                aria-hidden="true">
                                                                <path fill="currentColor"
                                                                    d="m9.702 18.01 6.01-6.01-6.01-6.01-1.414 1.414 4.596 4.596-4.596 4.596z">
                                                                </path>
                                                            </svg></span></button>
                                                </div>
                                            </div>
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
                <script src="/admin/js/order/order-management.js"></script>
            </body>

            </html>