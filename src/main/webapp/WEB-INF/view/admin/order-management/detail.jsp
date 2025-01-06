<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <title>Dashboard - SB Admin</title>
                    <link href="/admin/css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
                        rel="stylesheet">
                    <link href="/admin/css/order/order-management-detail.css" rel="stylesheet" />
                </head>

                <body class="sb-nav-fixed">
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <jsp:include page="../layout/sidebar.jsp" />

                        <div id="layoutSidenav_content">
                            <div class="container">
                                <div class="header" style="min-height: 64px;">
                                    <div class="header__btn">
                                        <a class="header__btn-link" href="/admin/orders">
                                            <span class="header__btn-icon"><i class="fa-solid fa-arrow-left"></i></span>
                                        </a>
                                    </div>
                                    <div class="order">
                                        <div class="order__status">
                                            <div class="code align-self-start">#1005</div>
                                            <div class="status align-self-center">
                                                <c:if test="${order.paymentStatus == 'PENDING'}">
                                                    <span class="payment-status--unpaid border-status status-pending">
                                                        Chưa thanh
                                                        toán
                                                    </span>
                                                </c:if>
                                                <c:if test="${order.paymentStatus == 'COMPLETED'}">
                                                    <span class="payment-status--unpaid border-status status-completed">
                                                        Đã thanh toán
                                                    </span>
                                                </c:if>
                                                <c:if test="${order.deliveryStatus == 'PENDING'}">
                                                    <span
                                                        class="shipping-status--delivered border-status status-pending">
                                                        Chưa xử lý giao hàng
                                                    </span>
                                                </c:if>
                                                <c:if test="${order.deliveryStatus != 'PENDING'}">
                                                    <span
                                                        class="shipping-status--delivered border-status status-pending">
                                                        Đã xử lý giao hàng
                                                    </span>
                                                </c:if>

                                            </div>
                                        </div>
                                        <div class="order__details">
                                            <span class="order__date sub-font">28/12/2024 14:02</span>
                                            <span class="order__store sub-font">Đặt hàng tại Cửa hàng chính</span>
                                        </div>
                                    </div>
                                    <div class="operation sub-font">
                                        <div class="operation__container">
                                            <c:if
                                                test="${order.paymentStatus == 'COMPLETED' && order.deliveryStatus == 'COMPLETED'}">
                                                <div class="return">
                                                    <a href="">
                                                        <span>
                                                            <span class="icon"><i class="fa-solid fa-box"></i></span>
                                                            <span class="text-black">Trả hàng</span>
                                                        </span>
                                                    </a>
                                                </div>
                                            </c:if>
                                            <c:if
                                                test="${order.paymentStatus == 'PENDING' && order.deliveryStatus == 'PENDING'}">
                                                <div class="return">
                                                    <a href="">
                                                        <span>
                                                            <span class="icon"><i class="fa-solid fa-pencil"></i></span>
                                                            <span class="text-black">Sửa đơn</span>
                                                        </span>
                                                    </a>
                                                </div>
                                            </c:if>
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
                                <div class="content d-flex w-100">
                                    <div class="d-flex flex-column width-70" style="gap: 15px;">
                                        <!-- danh sách sản phẩm trong đơn hàng -->
                                        <div class="order-details border-outline w-100">
                                            <div class="ui-card__section pad-20px-4">
                                                <div class="processing-status">

                                                    <c:if test="${order.deliveryStatus == 'PENDING'}">
                                                        <div class="status">
                                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                viewBox="0 0 24 24" focusable="false"
                                                                aria-hidden="true">
                                                                <g clip-path="url(#171__a)">
                                                                    <circle cx="12" cy="12" r="12" fill="#FFDF9B">
                                                                    </circle>
                                                                    <circle cx="12" cy="12" r="7" fill="#fff"
                                                                        stroke="#E49C06" stroke-dasharray="2 2"
                                                                        stroke-width="1.5"></circle>
                                                                </g>
                                                                <defs>
                                                                    <clipPath id="171__a">
                                                                        <path fill="#fff" d="M0 0h24v24h-24z"></path>
                                                                    </clipPath>
                                                                </defs>
                                                            </svg>
                                                        </div>
                                                        <div class="status-text ml-10"><span>Chưa xử lý</span>
                                                    </c:if>
                                                    <c:if test="${order.deliveryStatus != 'PENDING'}">
                                                        <div class="status">
                                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                viewBox="0 0 24 24" focusable="false"
                                                                aria-hidden="true">
                                                                <g clip-path="url(#176__a)">
                                                                    <circle cx="12" cy="12" r="10" fill="#fff"
                                                                        stroke="#CFF6E7" stroke-width="4"></circle>
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
                                                    </c:if>

                                                </div>
                                                <c:if test="${order.deliveryStatus == 'COMPLETED'}">
                                                    <div class="confirm-delivery ml-10 border-status status-completed">
                                                        <span>Đã giao
                                                            hàng</span>
                                                    </div>
                                                </c:if>
                                                <c:if test="${order.deliveryStatus == 'DELIVERY'}">
                                                    <div class="confirm-delivery ml-10 border-status status-pending">
                                                        <span>Chờ giao hàng</span>
                                                    </div>
                                                </c:if>
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
                                                    <c:forEach items="${order.orderDetails}" var="orderDetail"
                                                        varStatus="i">
                                                        <tr>
                                                            <td>
                                                                <div class="product-order d-flex">
                                                                    <div class="product-img">
                                                                        <img alt=""
                                                                            src="/images/product/${orderDetail.product.images[i.index].imageUrl}">
                                                                    </div>
                                                                    <div class="product-name d-flex">
                                                                        <span class="align-self-center"
                                                                            style="text-align: start;">${orderDetail.product.name}</span>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td><span
                                                                    class="product-quantity text-center">${orderDetail.quantity}</span>
                                                            </td>
                                                            <td>
                                                                <span class="product-price">
                                                                    <fmt:formatNumber type="number"
                                                                        value="${orderDetail.product.price}" />
                                                                    đ
                                                                </span>
                                                            </td>
                                                            <td>
                                                                <span class="total-price">
                                                                    <fmt:formatNumber type="number"
                                                                        value="${orderDetail.price}" />
                                                                    đ
                                                                </span>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <c:if test="${order.deliveryStatus == 'PENDING'}">
                                            <div class="order-is-pending pad-20px-4">
                                                <div class="d-flex justify-content-end">
                                                    <div class="push-transport-btn common-push-btn btn-confirm"
                                                        id="modalOpenOverlayPushTransport">
                                                        <span>Đẩy vận chuyển</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${order.deliveryStatus == 'DELIVERY'}">
                                            <div class="order-is-delivery pad-20px-4">
                                                <div class="d-flex justify-content-end">
                                                    <div class="cancel-delivery-btn change-status-order common-push-btn btn-cancel d-flex"
                                                        id="cancelDeliveryBtn">
                                                        <span class="align-self-center">Hủy giao hàng</span>
                                                    </div>
                                                    <div
                                                        class="confirm-delivery-btn common-push-btn btn-confirm change-status-order" id="confirmDelivery">
                                                        <span>Xác nhận đã giao</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                    <!-- trạng thái thanh toán -->
                                    <div class="payment-status border-outline w-100">
                                        <div class="header-payment">
                                            <div class="pad-20px-3">
                                                <div class="d-flex">
                                                    <c:if test="${order.paymentStatus == 'PENDING'}">
                                                        <div class="status">
                                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                viewBox="0 0 24 24" focusable="false"
                                                                aria-hidden="true">
                                                                <g clip-path="url(#171__a)">
                                                                    <circle cx="12" cy="12" r="12" fill="#FFDF9B">
                                                                    </circle>
                                                                    <circle cx="12" cy="12" r="7" fill="#fff"
                                                                        stroke="#E49C06" stroke-dasharray="2 2"
                                                                        stroke-width="1.5"></circle>
                                                                </g>
                                                                <defs>
                                                                    <clipPath id="171__a">
                                                                        <path fill="#fff" d="M0 0h24v24h-24z"></path>
                                                                    </clipPath>
                                                                </defs>
                                                            </svg>
                                                        </div>
                                                        <div class="status-text ml-10"><span>Chưa thanh toán</span>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${order.paymentStatus == 'COMPLETED'}">
                                                        <div class="status">
                                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                viewBox="0 0 24 24" focusable="false"
                                                                aria-hidden="true">
                                                                <g clip-path="url(#176__a)">
                                                                    <circle cx="12" cy="12" r="10" fill="#fff"
                                                                        stroke="#CFF6E7" stroke-width="4"></circle>
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
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="content-payment sub-font">
                                            <div class="total-cost d-flex justify-content-between">
                                                <div class="text-cost flex-grow-1" style="flex: 0 0 25%;">
                                                    <span>Tổng tiền hàng</span>
                                                </div>
                                                <div class="d-flex justify-content-between"
                                                    style="flex-grow: 2; font-size: 14px;">
                                                    <div class="product-quantity-cost"><span>${order.totalProducts} sản
                                                            phẩm</span></div>
                                                    <div class="total-price-cost">
                                                        <span class="text-end">
                                                            <fmt:formatNumber type="number"
                                                                value="${order.totalAmount}" />
                                                            đ
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="delivery-fee d-flex justify-content-between">
                                                <div class="text-delivery flex-grow-1" style="flex: 0 0 25%"><span>Phí
                                                        giao
                                                        hàng</span></div>
                                                <div class="d-flex justify-content-between" style="flex-grow: 2;">
                                                    <div class="method-delivery"><span>Giao hàng tận nơi</span></div>
                                                    <div class="money-delivery">
                                                        <span class="text-end">
                                                            <fmt:formatNumber type="number" value="${}" />
                                                            30.000đ
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="into-money d-flex justify-content-between">
                                                <div class="money-text"><span>Thành tiền</span></div>
                                                <div class="money-delivery">
                                                    <span class="text-end">
                                                        <fmt:formatNumber type="number" value="${order.totalAmount}" />
                                                        đ
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <c:if test="${order.paymentStatus == 'COMPLETED'}">
                                            <div class="footer-payment pad-20px-4 sub-font">
                                                <div class="d-flex">
                                                    <div class="payment-text flex-grow-1" style="flex: 0 0 25%">
                                                        <span>Khách
                                                            đã
                                                            trả</span>
                                                    </div>
                                                    <div class="d-flex justify-content-between" style="flex-grow: 2;">
                                                        <div class="payment-cod"><span>Thu hộ(COD)</span></div>
                                                        <div class="payment-amount">
                                                            <span class="text-end">905,000₫</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${order.paymentStatus == 'PENDING'}">
                                            <div class="footer-payment-pending pad-20px-4 sub-font">
                                                <div class="d-flex justify-content-end">
                                                    <!-- <div class="">sdf</div> -->
                                                    <div class="receive-money-btn common-push-btn btn-confirm"
                                                        id="modalOpenOverlayReceiveMoney">
                                                        <span>Nhận tiền</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                    <!-- lịch sử đơn hàng -->
                                    <div class="order-history border-outline d-flex flex-column w-100">
                                        <div class="pad-20px-3">
                                            <h2 class="title-font">Lịch sử đơn hàng</h2>
                                        </div>
                                        <div class="d-flex pad-20px-4 flex-grow-1" style="font-size: 14px;">
                                            <div class="d-flex flex-column justify-content-between flex-grow-1">
                                                <c:forEach items="${lstOrderHis}" var="orderHis">
                                                    <div class="operation-day">${orderHis.date}</div>
                                                    <c:forEach items="${orderHis.orderHisDetail}" var="orderHisDetail"
                                                        varStatus="status">
                                                        <div class="min-height46 d-flex" style="gap: 4px;">
                                                            <div class="progress-bar-his d-flex flex-column">
                                                                <div class="circle"></div>
                                                                <c:if test="${!status.last}">
                                                                    <div class="jamb"></div>
                                                                </c:if>
                                                            </div>
                                                            <div
                                                                class="progress-details d-flex justify-content-between">
                                                                <div class="processing-time">
                                                                    <span>${orderHisDetail.actionTime}</span>
                                                                </div>
                                                                <div class="describe-process d-flex">
                                                                    <div class="order-processor"
                                                                        style="min-width: 150px;">
                                                                        <span>${orderHisDetail.performedBy}</span>
                                                                    </div>
                                                                    <div class="detailed-description">
                                                                        <span>${orderHisDetail.description}</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </c:forEach>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex flex-column width-30" style="gap: 15px;">
                                    <!-- nguồn đơn -->
                                    <div class="single-source border-outline w-100">
                                        <div class="pad-20px-3">
                                            <h2 class="title-font">Nguồn đơn</h2>
                                        </div>
                                        <div class="pad-20px-4 d-flex">
                                            <c:if test="${order.orderSource == true}">
                                                <img src="https://bizweb.dktcdn.net/assets/admin/v3/website.svg"
                                                    alt="icon" style="width: 24px; height: 24px;">
                                                <span class="origin-soure">Website</span>
                                            </c:if>
                                            <c:if test="${order.orderSource == false}">
                                                <img src="https://bizweb.dktcdn.net/assets/admin/v3/draft_order.svg"
                                                    alt="icon" style="width: 24px; height: 24px;">
                                                <span class="origin-soure">Admin</span>
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- khách hàng -->
                                    <div class="customer border-outline w-100">
                                        <div class="pad-20px-3">
                                            <h2 class="title-font">Khách hàng</h2>
                                        </div>
                                        <div class="pad-20px-4 flex-grow-1 d-flex flex-column">
                                            <div class="order-history-customer">
                                                <p class="user-name mb-5p">${order.user.fullName}</p>
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
                                                <p class="user-name mb-5p sub-font">${order.user.email}</p>
                                                <p class="user-phone sub-font">${order.user.phoneNumber}</p>
                                            </div>
                                            <div class="delivery-address d-flex flex-grow-1">
                                                <div class="d-flex flex-column justify-content-between">
                                                    <div class="header-contact d-flex justify-content-between">
                                                        <p class="font-w450 ">Địa chỉ giao hàng</p>
                                                        <span><i class="fa-solid fa-pencil"></i></span>
                                                    </div>
                                                    <p class="user-name mb-5p sub-font">${order.address.fullName}</p>
                                                    <p class="user-phone mb-5p sub-font">${order.address.phoneNumber}
                                                    </p>
                                                    <p class="user-name mb-5p sub-font">${order.address.streetDetails}
                                                        ${order.address.address}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- ghi chú -->
                                    <div class="order-notes border-outline w-100">
                                        <div class="take-notes">
                                            <div class="pad-20px-3 header-notes d-flex justify-content-between">
                                                <p class="font-w450">Ghi chú</p>
                                                <span><i class="fa-solid fa-pencil"></i></span>
                                            </div>
                                            <p class="user-phone sub-font" style="padding: 0 20px;">
                                                <c:if test="${order.note != null}"><span>${order.note}</span></c:if>
                                                <c:if test="${order.note == null}"><span>Chưa có ghi chú</span></c:if>

                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- modal -->
                        <!-- Modal đẩy vận chuyển -->
                        <div class="modal-overlay-push-transport modal-overlay" id="modalOverlayPushTransport">
                            <div class="modal-overlay-push-transport-content modal-overlay-content">
                                <div class="header-modal d-flex justify-content-between">
                                    <h3>Xác nhận đẩy vận chuyển</h3>
                                    <span class="close-modal-icon btn-close-modal"><i
                                            class="fa-solid fa-xmark"></i></span>
                                </div>
                                <div class="content-modal d-flex flex-column justify-content-between">
                                    <div class="box-content-modal">
                                        <div class="text-content-modal">
                                            <span>Bạn có chắc chắn muốn đẩy đơn hàng cho đơn vị vận
                                                chuyển không? Vui lòng chọn hình thức giao hàng cho đơn
                                                hàng
                                                :
                                            </span>
                                            <div class="checkbox-content" style="padding: 2px 0;">
                                                <div class="">
                                                    <div class="" style="padding: 4px 0;">
                                                        <input type="radio" name="t"> <span>Giao
                                                            hàng</span>
                                                    </div>

                                                    <div class="" style="padding-left: 28px; ">
                                                        <select name="cars" id="cars"
                                                            style="border: 1px solid rgb(211, 213, 215); height: 36px; width: 315px; border-radius: 4px;outline: none;">
                                                            <option value="volvo">Volvo</option>
                                                            <option value="saab">Saab</option>
                                                            <option value="mercedes">Mercedes
                                                            </option>
                                                            <option value="audi">Audi</option>
                                                        </select>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="" style="padding: 4px 0;">
                                                <input type="radio" name="t"> <span>Nhận tại cửa
                                                    hàng</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="checked-email" style="margin: 4px 0;">
                                        <div class="">
                                            <input type="checkbox">
                                            <span>Gửi thông báo email đến khách hàng</span>
                                        </div>
                                    </div>
                                </div>

                                <div class="footer-modal">
                                    <div class="d-flex justify-content-end">
                                        <div class="common-push-btn btn-cancel d-flex btn-close-modal">
                                            <span class="align-self-center">Hủy</span>
                                        </div>
                                        <div class="common-push-btn btn-confirm change-status-order"
                                            id="confirmPushShipping">
                                            <span>Xác nhận</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- modal nhận tiền -->
                        <div class="modal-overlay-receive-money modal-overlay" id="modalOverlayReceiveMoney">
                            <div class="modal-overlay-receive-money-content modal-overlay-content">
                                <div class="header-modal d-flex justify-content-between">
                                    <h3>Xác nhận đẩy vận chuyển</h3>
                                    <span class="close-modal-icon btn-close-modal"><i
                                            class="fa-solid fa-xmark"></i></span>
                                </div>
                                <div class="content-modal">
                                    <div class="box-content-modal d-flex justify-content-between">
                                        <div class="box-payment-method-select">
                                            <span>Phương thức thanh toán</span>
                                            <select name="cars" id="cars"
                                                style="border: 1px solid rgb(211, 213, 215); height: 36px; width: 315px; border-radius: 4px;outline: none;">
                                                <option value="volvo">Volvo</option>
                                                <option value="saab">Saab</option>
                                                <option value="mercedes">Mercedes
                                                </option>
                                                <option value="audi">Audi</option>
                                            </select>
                                        </div>
                                        <div class="box-amount-received">
                                            <span>Số tiền nhận</span>
                                            <select name="cars" id="cars"
                                                style="border: 1px solid rgb(211, 213, 215); height: 36px; width: 315px; border-radius: 4px;outline: none;">
                                                <option value="volvo">Volvo</option>
                                                <option value="saab">Saab</option>
                                                <option value="mercedes">Mercedes
                                                </option>
                                                <option value="audi">Audi</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="footer-modal">
                                    <div class="d-flex justify-content-end">
                                        <div class="common-push-btn btn-cancel d-flex btn-close-modal">
                                            <span class="align-self-center">Hủy</span>
                                        </div>
                                        <div class="common-push-btn btn-confirm change-status-order"
                                            id="confirmReceiveMoney">
                                            <span>Nhận tiền</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                    <!-- </div> -->
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                        crossorigin="anonymous"></script>
                    <script src="/admin/js/scripts.js"></script>
                    <script src="/admin/js/order/order-management.js"></script>
                </body>

                </html>