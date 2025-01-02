<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <title>Dashboard - SB Admin</title>
            <link href="/admin/css/styles.css" rel="stylesheet" />
            <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="/admin/css/order/order-management-detail.css" rel="stylesheet" />
        </head>

        <body class="sb-nav-fixed">
            <jsp:include page="../layout/header.jsp" />
            <div id="layoutSidenav">
                <jsp:include page="../layout/sidebar.jsp" />

                <div id="layoutSidenav_content">
                    <div class="container">
                        <div class="header">
                            <div class="header__btn">
                                <a class="header__btn-link" href="/admin/orders">
                                    <span class="header__btn-icon"><i class="fa-solid fa-arrow-left"></i></span>
                                </a>
                            </div>
                            <div class="order">
                                <div class="order__status">
                                    <div class="code">#1005</div>
                                    <div class="status">
                                        <span class="payment-status--unpaid">Chưa thanh
                                            toán</span>
                                        <span class="shipping-status--delivered">Đã xử lý
                                            giao
                                            hàng</span>
                                    </div>
                                </div>
                                <div class="order__details">
                                    <span class="order__date">28/12/2024 14:02</span>
                                    <span class="order__store">Đặt hàng tại Cửa hàng chính</span>
                                </div>
                            </div>
                            <div class="operation">
                                <div class="operation__container">
                                    <div class="return">
                                        <a href="">
                                            <span>
                                                <span class="icon"><i class="fa-solid fa-box"></i></span>
                                                <span class="text-black">Trả hàng</span>
                                            </span>
                                        </a>
                                    </div>
                                    <div class="print-invoice">
                                        <a href="#">
                                            <span>
                                                <span class="icon"><i class="fa-solid fa-print"></i></span>
                                                <span class="text-black">In hóa đơn</span>
                                            </span>
                                        </a>
                                    </div>
                                    <div class="cancel-order">
                                        <a href="#">
                                            <span>
                                                <span class="icon"><i class="fas fa-ban"></i></span>
                                                <span class="text-black">Hủy đơn hàng</span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- danh sách sản phẩm trong đơn hàng -->
                        <div class="order-details border-outline">
                            <div class="ui-card__section pad-20px-4">
                                <div class="processing-status">
                                    <div class="status">
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                            focusable="false" aria-hidden="true">
                                            <g clip-path="url(#176__a)">
                                                <circle cx="12" cy="12" r="10" fill="#fff" stroke="#CFF6E7"
                                                    stroke-width="4"></circle>
                                                <path fill="#0DB473" fill-rule="evenodd"
                                                    d="M4 12c0-4.416 3.584-8 8-8s8 3.584 8 8-3.584 8-8 8-8-3.584-8-8m6.4 1.736 5.272-5.272 1.128 1.136-6.4 6.4-3.2-3.2 1.128-1.128z"
                                                    clip-rule="evenodd"></path>
                                            </g>
                                            <defs>
                                                <clipPath id="176__a">
                                                    <path fill="#fff" d="M0 0h24v24h-24z"></path>
                                                </clipPath>
                                            </defs>
                                        </svg>
                                    </div>
                                    <div class="status-text ml-10"><span>Đã xử lý giao hàng</span>
                                    </div>
                                    <div class="confirm-delivery ml-10 border-status"><span>Đã giao hàng</span>
                                    </div>
                                </div>
                                <div class="delivery-date d-flex mt-8p">
                                    <div class="text-delivery mw-88 sub-font">
                                        Ngày giao
                                    </div>
                                    <div class="text-date sub-font">
                                        : 29/12/2024 16:03:05
                                    </div>
                                </div>
                                <div class="transport d-flex mt-8p">
                                    <div class="text-transport mw-88 sub-font">
                                        Vận chuyển
                                    </div>
                                    <div class="text-transport sub-font">
                                        : 29/12/2024 16:03:05
                                    </div>
                                </div>
                            </div>
                            <div class="table-order">
                                <table class="w-100">
                                    <thead>
                                        <tr>
                                            <th>Sản phẩm</th>
                                            <th>Số lượng</th>
                                            <th>Đơn giá</th>
                                            <th>Thành tiền</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div class="product-order d-flex">
                                                    <div class="product-img">
                                                        <img alt=""
                                                            src="https://bizweb.dktcdn.net/thumb/thumb/100/543/961/products/anh20ab197256d4146c3baf4530b9b.png?v=1735106878793">
                                                    </div>
                                                    <div class="product-name d-flex">
                                                        <span class="align-self-center">Kem dưỡng da
                                                            Laneige</span>
                                                    </div>
                                                </div>
                                            </td>
                                            <td><span class="product-quantity text-center">1</span></td>
                                            <td><span class="product-price">80,000₫</span></td>
                                            <td><span class="total-price">80,000₫</span></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="product-order d-flex">
                                                    <div class="product-img">
                                                        <img alt=""
                                                            src="https://bizweb.dktcdn.net/thumb/thumb/100/543/961/products/anh20ab197256d4146c3baf4530b9b.png?v=1735106878793">
                                                    </div>
                                                    <div class="product-name d-flex">
                                                        <span class="align-self-center">Kem dưỡng da Laneige</span>
                                                    </div>
                                                </div>
                                            </td>
                                            <td><span class="product-quantity text-center">1</span></td>
                                            <td><span class="product-price">80,000₫</span></td>
                                            <td><span class="total-price">80,000₫</span></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="product-order d-flex">
                                                    <div class="product-img">
                                                        <img alt=""
                                                            src="https://bizweb.dktcdn.net/thumb/thumb/100/543/961/products/anh20ab197256d4146c3baf4530b9b.png?v=1735106878793">
                                                    </div>
                                                    <div class="product-name d-flex">
                                                        <span class="align-self-center">Kem dưỡng da Laneige</span>
                                                    </div>
                                                </div>
                                            </td>
                                            <td><span class="product-quantity text-center">1</span></td>
                                            <td><span class="product-price">80,000₫</span></td>
                                            <td><span class="total-price">80,000₫</span></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- nguồn đơn -->
                        <div class="single-source border-outline">
                            <div class="pad-20px-3">
                                <h2 class="title-font">Nguồn đơn</h2>
                            </div>
                            <div class="pad-20px-4 d-flex">
                                <img src="https://bizweb.dktcdn.net/assets/admin/v3/draft_order.svg" alt="icon"
                                    style="width: 24px; height: 24px;">
                                <span class="origin-soure">Admin</span>
                            </div>
                        </div>
                        <!-- khách hàng -->
                        <div class="customer border-outline">
                            <div class="pad-20px-3">
                                <h2 class="title-font">Khách hàng</h2>
                            </div>
                            <div class="pad-20px-4 flex-grow-1 d-flex flex-column">
                                <div class="order-history-customer">
                                    <p class="user-name mb-5p">Lê khả ái</p>
                                    <div class="total-spending d-flex justify-content-between">
                                        <p class="text-spending mb-5p">Tổng chi tiêu (2 đơn hàng)</p>
                                        <span class="total-spending">1,600,000₫</span>
                                    </div>
                                    <div class="nearest-menu d-flex justify-content-between">
                                        <p class="text-nearest">Đơn gần nhất</p>
                                        <span class="order-nearest">#1007</span>
                                    </div>
                                </div>
                                <div class="contact-information">
                                    <div class="header-contact d-flex justify-content-between">
                                        <p class="font-w450">Thông tin liên hệ</p>
                                        <span><i class="fa-solid fa-pencil"></i></span>
                                    </div>
                                    <p class="user-name mb-5p">vasitiducha@hotmail.com</p>
                                    <p class="user-phone">0837438383</p>
                                </div>
                                <div class="delivery-address d-flex flex-grow-1">
                                    <div class="d-flex flex-column justify-content-between">
                                        <div class="header-contact d-flex justify-content-between">
                                            <p class="font-w450 ">Địa chỉ giao hàng</p>
                                            <span><i class="fa-solid fa-pencil"></i></span>
                                        </div>
                                        <p class="user-name mb-0">lê khả ái</p>
                                        <p class="user-phone mb-0">0837438383</p>
                                        <p class="user-name mb-0">thôn 3 , nhà 34, Phường Phú Thịnh, Thị xã Sơn Tây, Hà
                                            Nội, Vietnam</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- trạng thái thanh toán -->
                        <div class="payment-status border-outline">
                            <div class="header-payment">
                                <div class="pad-20px-3">
                                    <div class="d-flex">
                                        <div class="status">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                                focusable="false" aria-hidden="true">
                                                <g clip-path="url(#176__a)">
                                                    <circle cx="12" cy="12" r="10" fill="#fff" stroke="#CFF6E7"
                                                        stroke-width="4"></circle>
                                                    <path fill="#0DB473" fill-rule="evenodd"
                                                        d="M4 12c0-4.416 3.584-8 8-8s8 3.584 8 8-3.584 8-8 8-8-3.584-8-8m6.4 1.736 5.272-5.272 1.128 1.136-6.4 6.4-3.2-3.2 1.128-1.128z"
                                                        clip-rule="evenodd"></path>
                                                </g>
                                                <defs>
                                                    <clipPath id="176__a">
                                                        <path fill="#fff" d="M0 0h24v24h-24z"></path>
                                                    </clipPath>
                                                </defs>
                                            </svg>
                                        </div>
                                        <div class="status-text ml-10"><span>Đã thanh toán</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="content-payment">
                                <div class="total-cost d-flex justify-content-between">
                                    <div class="text-cost flex-grow-1" style="flex: 0 0 25%;">
                                        <span>Tổng tiền hàng</span>
                                    </div>
                                    <div class="d-flex justify-content-between" style="flex-grow: 2;">
                                        <div class="product-quantity-cost"><span>5 sản phẩm</span></div>
                                        <div class="total-price-cost">
                                            <span class="text-end">865,000₫</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="delivery-fee d-flex justify-content-between">
                                    <div class="text-delivery flex-grow-1" style="flex: 0 0 25%"><span>Phí giao
                                            hàng</span></div>
                                    <div class="d-flex justify-content-between" style="flex-grow: 2;">
                                        <div class="method-delivery"><span>Giao hàng tận nơi</span></div>
                                        <div class="money-delivery">
                                            <span class="text-end">40,000₫</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="into-money d-flex justify-content-between">
                                    <div class="money-text"><span>Thành tiền</span></div>
                                    <div class="money-delivery">
                                        <span class="text-end">905,000₫</span>
                                    </div>
                                </div>
                            </div>
                            <div class="footer-payment pad-20px-4">
                                <div class="d-flex">
                                    <div class="payment-text flex-grow-1" style="flex: 0 0 25%"><span>Khách đã
                                            trả</span></div>
                                    <div class="d-flex justify-content-between" style="flex-grow: 2;">
                                        <div class="payment-cod"><span>Thu hộ(COD)</span></div>
                                        <div class="payment-amount">
                                            <span class="text-end">905,000₫</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- ghi chú -->
                        <div class="order-notes border-outline">
                            <div class="take-notes">
                                <div class="pad-20px-3 header-notes d-flex justify-content-between">
                                    <p class="font-w450">Ghi chú</p>
                                    <span><i class="fa-solid fa-pencil"></i></span>
                                </div>
                                <p class="user-phone" style="padding: 0 20px;">Chưa có ghi chú</p>
                            </div>
                        </div>
                        <!-- lịch sử đơn hàng -->
                        <div class="order-history border-outline">

                        </div>
                    </div>
                    <jsp:include page="../layout/footer.jsp" />
                </div>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                crossorigin="anonymous"></script>
            <script src="/admin/js/scripts.js"></script>
        </body>

        </html>