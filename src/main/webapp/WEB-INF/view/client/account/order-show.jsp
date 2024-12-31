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
                                    <div class="tabs">
                                        <button class="tab-button" id="tab1-btn" onclick="openTab('tab1')">Tất
                                            cả</button>
                                        <button class="tab-button" id="tab2-btn" onclick="openTab('tab2')">Chờ xác
                                            nhận</button>
                                        <button class="tab-button" id="tab3-btn" onclick="openTab('tab3')">Vận
                                            chuyển</button>
                                        <button class="tab-button" id="tab4-btn" onclick="openTab('tab4')">Chờ giao
                                            hàng</button>
                                        <button class="tab-button" id="tab5-btn" onclick="openTab('tab5')">Hoàn
                                            thành</button>
                                        <button class="tab-button" id="tab6-btn" onclick="openTab('tab6')">Đã
                                            hủy</button>
                                        <button class="tab-button" id="tab7-btn" onclick="openTab('tab7')">Trả hàng/Hoàn
                                            tiền</button>
                                    </div>

                                </div>
                                <div class="card-body" style="padding: 0px; margin-top: 10px;">
                                    <div class="tab-content" id="tab1">
                                        <c:forEach items="${orderUsers}" var="order">
                                            <div style="margin-bottom: 20px; border: 1px solid #ddd;">
                                                <div class="" style="text-align: right; padding: 20px;">
                                                    ${order.status.description}
                                                </div>
                                                <hr style="margin-top: 0rem;margin-bottom: 0rem;">
                                                <c:forEach items="${order.orderDetails}" var="orderDetail"
                                                    varStatus="i">
                                                    <div>
                                                        <div class="order-item">
                                                            <div class="order-item-detail">
                                                                <img style="width: 80px; height: 80px;"
                                                                    src="/images/product/${orderDetail.product.images[0].imageUrl}"
                                                                    alt="">
                                                                <div class="order-item-name">
                                                                    <div class="name-product">
                                                                        <span class="DWVWOJ"
                                                                            tabindex="0">${orderDetail.product.name}</span>
                                                                    </div>
                                                                    <div class="category-product">
                                                                        <div class="rsautk"
                                                                            style="margin: 0 0 5px;color: rgba(0, 0, 0, .54);"
                                                                            tabindex="0">Phân loại
                                                                            hàng: Ghi,L
                                                                        </div>
                                                                        <div class="j3I_Nh" style="margin: 0 0 5px;"
                                                                            tabindex="0">
                                                                            x${order.orderDetails[i.index].quantity}
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="order-item-price" style="text-align: right;">
                                                                <div class="YRp1mm" style="margin-left: 12px;">
                                                                    <span class="nW_6Oi PNlXhK">
                                                                        <fmt:formatNumber type="number"
                                                                            value="${orderDetail.price}" />
                                                                        đ
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                                <hr>
                                                <div class="order-into-money">
                                                    <div class="NWUSQP">
                                                        <label class="juCcT0">Thành tiền:</label>
                                                        <div class="t7TQaf" tabindex="0"
                                                            aria-label="Thành tiền: ₫170.500">
                                                            <fmt:formatNumber type="number"
                                                                value="${order.totalAmount}" />
                                                            đ
                                                        </div>
                                                    </div>
                                                </div>
                                                <c:if test="${order.status == 'PENDING'}">
                                                    <div class="yyqgYp">
                                                        <div class="iwUeSD">
                                                            <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Chờ xác
                                                                    nhận</span></div>
                                                        </div>
                                                        <section class="po9nwN">
                                                            <h3 class="a11y-hidden"></h3>
                                                            <div class="aAXjeK">
                                                                <div><button
                                                                        class="stardust-button stardust-button--primary QY7kZh">Hủy
                                                                        đơn hàng</button></div>
                                                            </div>
                                                            <div class="hbQXWm"><a
                                                                    href="/user/purchase/cancellation/186904745262679"><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh IY1xwI">Xem
                                                                        chi tiết đơn hàng</button></a></div>
                                                            <div class="hbQXWm">
                                                                <div><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                        hệ Người bán</button></div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </c:if>

                                                <c:if test="${order.status == 'SHIPPING'}">
                                                    <div class="yyqgYp">
                                                        <div class="iwUeSD">
                                                            <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Đang
                                                                    được vận chuyển tới bạn</span></div>
                                                        </div>
                                                        <section class="po9nwN">
                                                            <h3 class="a11y-hidden"></h3>
                                                            <div class="aAXjeK">
                                                                <div><button
                                                                        class="stardust-button stardust-button--primary QY7kZh">Xem
                                                                        chi tiết đơn hàng</button></div>
                                                            </div>
                                                            <div class="hbQXWm">
                                                                <div><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                        hệ Người bán</button></div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </c:if>

                                                <c:if test="${order.status == 'OUT_FOR_DELIVERY'}">
                                                    <div class="yyqgYp">
                                                        <div class="yyqgYp">
                                                            <div class="iwUeSD">
                                                                <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Đơn
                                                                        hàng
                                                                        đang được giao</span></div>
                                                            </div>
                                                            <section class="po9nwN">
                                                                <h3 class="a11y-hidden"></h3>
                                                                <div class="aAXjeK">
                                                                    <div><button
                                                                            class="stardust-button stardust-button--primary QY7kZh">Xem
                                                                            chi tiết đơn hàng</button></div>
                                                                </div>
                                                                <div class="hbQXWm">
                                                                    <div><button
                                                                            class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                            hệ Người bán</button></div>
                                                                </div>
                                                            </section>
                                                        </div>
                                                </c:if>

                                                <c:if test="${order.status == 'COMPLETED'}">
                                                    <div class="yyqgYp">
                                                        <div class="iwUeSD">
                                                            <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Đã giao
                                                                    thành công</span></div>
                                                        </div>
                                                        <section class="po9nwN">
                                                            <h3 class="a11y-hidden"></h3>
                                                            <div class="aAXjeK">
                                                                <div><button
                                                                        class="stardust-button stardust-button--primary QY7kZh">Mua
                                                                        lại</button></div>
                                                            </div>
                                                            <div class="hbQXWm"><a
                                                                    href="/user/purchase/cancellation/186904745262679"><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh IY1xwI">Trả
                                                                        hàng</button></a></div>
                                                            <div class="hbQXWm">
                                                                <div><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                        hệ Người bán</button></div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </c:if>

                                                <c:if test="${order.status == 'CANCELED'}">
                                                    <div class="yyqgYp">
                                                        <div class="iwUeSD">
                                                            <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Đã hủy
                                                                    bởi
                                                                    bạn</span></div>
                                                        </div>
                                                        <section class="po9nwN">
                                                            <h3 class="a11y-hidden"></h3>
                                                            <div class="aAXjeK">
                                                                <div><button
                                                                        class="stardust-button stardust-button--primary QY7kZh">Mua
                                                                        lại</button></div>
                                                            </div>
                                                            <div class="hbQXWm"><a
                                                                    href="/user/purchase/cancellation/186904745262679"><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh IY1xwI">Xem
                                                                        chi tiết Hủy đơn</button></a></div>
                                                            <div class="hbQXWm">
                                                                <div><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                        hệ Người bán</button></div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </c:if>

                                                <c:if test="${order.status == 'RETURNED'}">
                                                    <div class="yyqgYp">
                                                        <div class="yyqgYp">
                                                            <div class="iwUeSD">
                                                                <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Đã
                                                                        hủy
                                                                        bởi
                                                                        bạn</span></div>
                                                            </div>
                                                            <section class="po9nwN">
                                                                <h3 class="a11y-hidden"></h3>
                                                                <div class="aAXjeK">
                                                                    <div><button
                                                                            class="stardust-button stardust-button--primary QY7kZh">Mua
                                                                            lại</button></div>
                                                                </div>
                                                                <div class="hbQXWm"><a
                                                                        href="/user/purchase/cancellation/186904745262679"><button
                                                                            class="stardust-button stardust-button--secondary QY7kZh IY1xwI">Xem
                                                                            chi tiết Hủy đơn</button></a></div>
                                                                <div class="hbQXWm">
                                                                    <div><button
                                                                            class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                            hệ Người bán</button></div>
                                                                </div>
                                                            </section>
                                                        </div>
                                                </c:if>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <div class="tab-content" id="tab2">
                                        <c:forEach items="${orderUsers}" var="order">
                                            <c:if test="${order.status == 'PENDING'}">
                                                <div style="margin-bottom: 20px; border: 1px solid #ddd;">
                                                    <div class="" style="text-align: right; padding: 20px;">
                                                        ${order.status.description}
                                                    </div>
                                                    <hr style="margin-top: 0rem;margin-bottom: 0rem;">
                                                    <c:forEach items="${order.orderDetails}" var="orderDetail"
                                                        varStatus="i">
                                                        <div>
                                                            <div class="order-item">
                                                                <div class="order-item-detail">
                                                                    <img style="width: 80px; height: 80px;"
                                                                        src="/images/product/${orderDetail.product.images[0].imageUrl}"
                                                                        alt="">
                                                                    <div class="order-item-name">
                                                                        <div class="name-product">
                                                                            <span class="DWVWOJ"
                                                                                tabindex="0">${orderDetail.product.name}</span>
                                                                        </div>
                                                                        <div class="category-product">
                                                                            <div class="rsautk"
                                                                                style="margin: 0 0 5px;color: rgba(0, 0, 0, .54);"
                                                                                tabindex="0">Phân loại
                                                                                hàng: Ghi,L
                                                                            </div>
                                                                            <div class="j3I_Nh" style="margin: 0 0 5px;"
                                                                                tabindex="0">
                                                                                x${order.orderDetails[i.index].quantity}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="order-item-price"
                                                                    style="text-align: right;">
                                                                    <div class="YRp1mm" style="margin-left: 12px;">
                                                                        <span class="nW_6Oi PNlXhK">
                                                                            <fmt:formatNumber type="number"
                                                                                value="${orderDetail.price}" />
                                                                            đ
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    <hr>
                                                    <div class="order-into-money">
                                                        <div class="NWUSQP">
                                                            <label class="juCcT0">Thành tiền:</label>
                                                            <div class="t7TQaf" tabindex="0"
                                                                aria-label="Thành tiền: ₫170.500">
                                                                <fmt:formatNumber type="number"
                                                                    value="${order.totalAmount}" />
                                                                đ
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="yyqgYp">
                                                        <div class="iwUeSD">
                                                            <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Chờ xác
                                                                    nhận</span></div>
                                                        </div>
                                                        <section class="po9nwN">
                                                            <h3 class="a11y-hidden"></h3>
                                                            <div class="aAXjeK">
                                                                <div><button
                                                                        class="stardust-button stardust-button--primary QY7kZh">Hủy
                                                                        đơn hàng</button></div>
                                                            </div>
                                                            <div class="hbQXWm"><a
                                                                    href="/user/purchase/cancellation/186904745262679"><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh IY1xwI">Xem
                                                                        chi tiết đơn hàng</button></a></div>
                                                            <div class="hbQXWm">
                                                                <div><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                        hệ Người bán</button></div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>

                                    <div class="tab-content" id="tab3">
                                        <c:forEach items="${orderUsers}" var="order">
                                            <c:if test="${order.status == 'SHIPPING'}">
                                                <div style="margin-bottom: 20px; border: 1px solid #ddd;">
                                                    <div class="" style="text-align: right; padding: 20px;">
                                                        ${order.status.description}
                                                    </div>
                                                    <hr style="margin-top: 0rem;margin-bottom: 0rem;">
                                                    <c:forEach items="${order.orderDetails}" var="orderDetail"
                                                        varStatus="i">
                                                        <div>
                                                            <div class="order-item">
                                                                <div class="order-item-detail">
                                                                    <img style="width: 80px; height: 80px;"
                                                                        src="/images/product/${orderDetail.product.images[0].imageUrl}"
                                                                        alt="">
                                                                    <div class="order-item-name">
                                                                        <div class="name-product">
                                                                            <span class="DWVWOJ"
                                                                                tabindex="0">${orderDetail.product.name}</span>
                                                                        </div>
                                                                        <div class="category-product">
                                                                            <div class="rsautk"
                                                                                style="margin: 0 0 5px;color: rgba(0, 0, 0, .54);"
                                                                                tabindex="0">Phân loại
                                                                                hàng: Ghi,L
                                                                            </div>
                                                                            <div class="j3I_Nh" style="margin: 0 0 5px;"
                                                                                tabindex="0">
                                                                                x${order.orderDetails[i.index].quantity}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="order-item-price"
                                                                    style="text-align: right;">
                                                                    <div class="YRp1mm" style="margin-left: 12px;">
                                                                        <span class="nW_6Oi PNlXhK">
                                                                            <fmt:formatNumber type="number"
                                                                                value="${orderDetail.price}" />
                                                                            đ
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    <hr>
                                                    <div class="order-into-money">
                                                        <div class="NWUSQP">
                                                            <label class="juCcT0">Thành tiền:</label>
                                                            <div class="t7TQaf" tabindex="0"
                                                                aria-label="Thành tiền: ₫170.500">
                                                                <fmt:formatNumber type="number"
                                                                    value="${order.totalAmount}" />
                                                                đ
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="yyqgYp">
                                                        <div class="iwUeSD">
                                                            <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Đang
                                                                    được vận chuyển tới bạn</span></div>
                                                        </div>
                                                        <section class="po9nwN">
                                                            <h3 class="a11y-hidden"></h3>
                                                            <div class="aAXjeK">
                                                                <div><button
                                                                        class="stardust-button stardust-button--primary QY7kZh">Xem
                                                                        chi tiết đơn hàng</button></div>
                                                            </div>
                                                            <div class="hbQXWm">
                                                                <div><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                        hệ Người bán</button></div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="tab-content" id="tab4">
                                        <c:forEach items="${orderUsers}" var="order">
                                            <c:if test="${order.status == 'OUT_FOR_DELIVERY'}">
                                                <div style="margin-bottom: 20px; border: 1px solid #ddd;">
                                                    <div class="" style="text-align: right; padding: 20px;">
                                                        ${order.status.description}
                                                    </div>
                                                    <hr style="margin-top: 0rem;margin-bottom: 0rem;">
                                                    <c:forEach items="${order.orderDetails}" var="orderDetail"
                                                        varStatus="i">
                                                        <div>
                                                            <div class="order-item">
                                                                <div class="order-item-detail">
                                                                    <img style="width: 80px; height: 80px;"
                                                                        src="/images/product/${orderDetail.product.images[0].imageUrl}"
                                                                        alt="">
                                                                    <div class="order-item-name">
                                                                        <div class="name-product">
                                                                            <span class="DWVWOJ"
                                                                                tabindex="0">${orderDetail.product.name}</span>
                                                                        </div>
                                                                        <div class="category-product">
                                                                            <div class="rsautk"
                                                                                style="margin: 0 0 5px;color: rgba(0, 0, 0, .54);"
                                                                                tabindex="0">Phân loại
                                                                                hàng: Ghi,L
                                                                            </div>
                                                                            <div class="j3I_Nh" style="margin: 0 0 5px;"
                                                                                tabindex="0">
                                                                                x${order.orderDetails[i.index].quantity}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="order-item-price"
                                                                    style="text-align: right;">
                                                                    <div class="YRp1mm" style="margin-left: 12px;">
                                                                        <span class="nW_6Oi PNlXhK">
                                                                            <fmt:formatNumber type="number"
                                                                                value="${orderDetail.price}" />
                                                                            đ
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    <hr>
                                                    <div class="order-into-money">
                                                        <div class="NWUSQP">
                                                            <label class="juCcT0">Thành tiền:</label>
                                                            <div class="t7TQaf" tabindex="0"
                                                                aria-label="Thành tiền: ₫170.500">
                                                                <fmt:formatNumber type="number"
                                                                    value="${order.totalAmount}" />
                                                                đ
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="yyqgYp">
                                                        <div class="iwUeSD">
                                                            <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Đơn hàng
                                                                    đang được giao</span></div>
                                                        </div>
                                                        <section class="po9nwN">
                                                            <h3 class="a11y-hidden"></h3>
                                                            <div class="aAXjeK">
                                                                <div><button
                                                                        class="stardust-button stardust-button--primary QY7kZh">Xem
                                                                        chi tiết đơn hàng</button></div>
                                                            </div>
                                                            <div class="hbQXWm">
                                                                <div><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                        hệ Người bán</button></div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>

                                    <div class="tab-content" id="tab5">
                                        <c:forEach items="${orderUsers}" var="order">
                                            <c:if test="${order.status == 'COMPLETED'}">
                                                <div style="margin-bottom: 20px; border: 1px solid #ddd;">
                                                    <div class="" style="text-align: right; padding: 20px;">
                                                        ${order.status.description}
                                                    </div>
                                                    <hr style="margin-top: 0rem;margin-bottom: 0rem;">
                                                    <c:forEach items="${order.orderDetails}" var="orderDetail"
                                                        varStatus="i">
                                                        <div>
                                                            <div class="order-item">
                                                                <div class="order-item-detail">
                                                                    <img style="width: 80px; height: 80px;"
                                                                        src="/images/product/${orderDetail.product.images[0].imageUrl}"
                                                                        alt="">
                                                                    <div class="order-item-name">
                                                                        <div class="name-product">
                                                                            <span class="DWVWOJ"
                                                                                tabindex="0">${orderDetail.product.name}</span>
                                                                        </div>
                                                                        <div class="category-product">
                                                                            <div class="rsautk"
                                                                                style="margin: 0 0 5px;color: rgba(0, 0, 0, .54);"
                                                                                tabindex="0">Phân loại
                                                                                hàng: Ghi,L
                                                                            </div>
                                                                            <div class="j3I_Nh" style="margin: 0 0 5px;"
                                                                                tabindex="0">
                                                                                x${order.orderDetails[i.index].quantity}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="order-item-price"
                                                                    style="text-align: right;">
                                                                    <div class="YRp1mm" style="margin-left: 12px;">
                                                                        <span class="nW_6Oi PNlXhK">
                                                                            <fmt:formatNumber type="number"
                                                                                value="${orderDetail.price}" />
                                                                            đ
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    <hr>
                                                    <div class="order-into-money">
                                                        <div class="NWUSQP">
                                                            <label class="juCcT0">Thành tiền:</label>
                                                            <div class="t7TQaf" tabindex="0"
                                                                aria-label="Thành tiền: ₫170.500">
                                                                <fmt:formatNumber type="number"
                                                                    value="${order.totalAmount}" />
                                                                đ
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="yyqgYp">
                                                        <div class="iwUeSD">
                                                            <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Đã giao
                                                                    thành công</span></div>
                                                        </div>
                                                        <section class="po9nwN">
                                                            <h3 class="a11y-hidden"></h3>
                                                            <div class="aAXjeK">
                                                                <div><button
                                                                        class="stardust-button stardust-button--primary QY7kZh">Mua
                                                                        lại</button></div>
                                                            </div>
                                                            <div class="hbQXWm"><a
                                                                    href="/user/purchase/cancellation/186904745262679"><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh IY1xwI">Trả
                                                                        hàng</button></a></div>
                                                            <div class="hbQXWm">
                                                                <div><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                        hệ Người bán</button></div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>

                                    <div class="tab-content" id="tab6">
                                        <c:forEach items="${orderUsers}" var="order">
                                            <c:if test="${order.status == 'CANCELED'}">
                                                <div style="margin-bottom: 20px; border: 1px solid #ddd;">
                                                    <div class="" style="text-align: right; padding: 20px;">
                                                        ${order.status.description}
                                                    </div>
                                                    <hr style="margin-top: 0rem;margin-bottom: 0rem;">
                                                    <c:forEach items="${order.orderDetails}" var="orderDetail"
                                                        varStatus="i">
                                                        <div>
                                                            <div class="order-item">
                                                                <div class="order-item-detail">
                                                                    <img style="width: 80px; height: 80px;"
                                                                        src="/images/product/${orderDetail.product.images[0].imageUrl}"
                                                                        alt="">
                                                                    <div class="order-item-name">
                                                                        <div class="name-product">
                                                                            <span class="DWVWOJ"
                                                                                tabindex="0">${orderDetail.product.name}</span>
                                                                        </div>
                                                                        <div class="category-product">
                                                                            <div class="rsautk"
                                                                                style="margin: 0 0 5px;color: rgba(0, 0, 0, .54);"
                                                                                tabindex="0">Phân loại
                                                                                hàng: Ghi,L
                                                                            </div>
                                                                            <div class="j3I_Nh" style="margin: 0 0 5px;"
                                                                                tabindex="0">
                                                                                x${order.orderDetails[i.index].quantity}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="order-item-price"
                                                                    style="text-align: right;">
                                                                    <div class="YRp1mm" style="margin-left: 12px;">
                                                                        <span class="nW_6Oi PNlXhK">
                                                                            <fmt:formatNumber type="number"
                                                                                value="${orderDetail.price}" />
                                                                            đ
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    <hr>
                                                    <div class="order-into-money">
                                                        <div class="NWUSQP">
                                                            <label class="juCcT0">Thành tiền:</label>
                                                            <div class="t7TQaf" tabindex="0"
                                                                aria-label="Thành tiền: ₫170.500">
                                                                <fmt:formatNumber type="number"
                                                                    value="${order.totalAmount}" />
                                                                đ
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="yyqgYp">
                                                        <div class="iwUeSD">
                                                            <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Đã hủy
                                                                    bởi
                                                                    bạn</span></div>
                                                        </div>
                                                        <section class="po9nwN">
                                                            <h3 class="a11y-hidden"></h3>
                                                            <div class="aAXjeK">
                                                                <div><button
                                                                        class="stardust-button stardust-button--primary QY7kZh">Mua
                                                                        lại</button></div>
                                                            </div>
                                                            <div class="hbQXWm"><a
                                                                    href="/user/purchase/cancellation/186904745262679"><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh IY1xwI">Xem
                                                                        chi tiết Hủy đơn</button></a></div>
                                                            <div class="hbQXWm">
                                                                <div><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                        hệ Người bán</button></div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>

                                    <div class="tab-content" id="tab7">
                                        <c:forEach items="${orderUsers}" var="order">
                                            <c:if test="${order.status == 'RETURNED'}">
                                                <div style="margin-bottom: 20px; border: 1px solid #ddd;">
                                                    <div class="" style="text-align: right; padding: 20px;">
                                                        ${order.status.description}
                                                    </div>
                                                    <hr style="margin-top: 0rem;margin-bottom: 0rem;">
                                                    <c:forEach items="${order.orderDetails}" var="orderDetail"
                                                        varStatus="i">
                                                        <div>
                                                            <div class="order-item">
                                                                <div class="order-item-detail">
                                                                    <img style="width: 80px; height: 80px;"
                                                                        src="/images/product/${orderDetail.product.images[0].imageUrl}"
                                                                        alt="">
                                                                    <div class="order-item-name">
                                                                        <div class="name-product">
                                                                            <span class="DWVWOJ"
                                                                                tabindex="0">${orderDetail.product.name}</span>
                                                                        </div>
                                                                        <div class="category-product">
                                                                            <div class="rsautk"
                                                                                style="margin: 0 0 5px;color: rgba(0, 0, 0, .54);"
                                                                                tabindex="0">Phân loại
                                                                                hàng: Ghi,L
                                                                            </div>
                                                                            <div class="j3I_Nh" style="margin: 0 0 5px;"
                                                                                tabindex="0">
                                                                                x${order.orderDetails[i.index].quantity}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="order-item-price"
                                                                    style="text-align: right;">
                                                                    <div class="YRp1mm" style="margin-left: 12px;">
                                                                        <span class="nW_6Oi PNlXhK">
                                                                            <fmt:formatNumber type="number"
                                                                                value="${orderDetail.price}" />
                                                                            đ
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    <hr>
                                                    <div class="order-into-money">
                                                        <div class="NWUSQP">
                                                            <label class="juCcT0">Thành tiền:</label>
                                                            <div class="t7TQaf" tabindex="0"
                                                                aria-label="Thành tiền: ₫170.500">
                                                                <fmt:formatNumber type="number"
                                                                    value="${order.totalAmount}" />
                                                                đ
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="yyqgYp">
                                                        <div class="iwUeSD">
                                                            <div><span aria-label="Đã hủy bởi bạn" tabindex="0">Đã hủy
                                                                    bởi
                                                                    bạn</span></div>
                                                        </div>
                                                        <section class="po9nwN">
                                                            <h3 class="a11y-hidden"></h3>
                                                            <div class="aAXjeK">
                                                                <div><button
                                                                        class="stardust-button stardust-button--primary QY7kZh">Mua
                                                                        lại</button></div>
                                                            </div>
                                                            <div class="hbQXWm"><a
                                                                    href="/user/purchase/cancellation/186904745262679"><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh IY1xwI">Xem
                                                                        chi tiết Hủy đơn</button></a></div>
                                                            <div class="hbQXWm">
                                                                <div><button
                                                                        class="stardust-button stardust-button--secondary QY7kZh">Liên
                                                                        hệ Người bán</button></div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
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
                    <script src="/js/myjs.js"></script>
                    <script src="/js/js-account.js"></script>
                    <script>
                        function openTab(tabId) {

                            tabbuttons = document.getElementsByClassName("tab-button");
                            for (i = 0; i < tabbuttons.length; i++) {
                                tabbuttons[i].style.borderBottom = "none";
                                tabbuttons[i].style.color = "#000";
                            }

                            // thêm border dưới button
                            var activeButton = document.getElementById(tabId + '-btn');
                            activeButton.style.borderBottom = '2px #4CAF50 solid';
                            activeButton.style.color = '#4CAF50';

                            // Lấy tất cả các nội dung tab và ẩn chúng
                            const contents = document.querySelectorAll('.tab-content');
                            contents.forEach(content => content.classList.remove('active'));

                            // Hiển thị nội dung của tab được chọn
                            const activeTab = document.getElementById(tabId);
                            activeTab.classList.add('active');

                        }
                        window.onload = function () {
                            openTab('tab1'); // Mở tab1 khi trang tải
                        }

                    </script>

                </body>

                </html>