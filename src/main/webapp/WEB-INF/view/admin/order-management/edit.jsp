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
                <link href="/admin/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <link href="https://cdn.jsdelivr.net/npm/@sweetalert2/theme-dark@4/dark.css" rel="stylesheet">
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>

                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <link href="/admin/css/order/order-management-edit.css" rel="stylesheet" />
                <link rel="stylesheet" href="/common/toast.css">
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />

                    <div id="layoutSidenav_content">
                        <div class="container">
                            <div class="header-edit" style="height: 31.6px;">
                                <div class="header__btn">
                                    <a class="header__btn-link" href="/admin/orders">
                                        <span class="header__btn-icon"><i class="fa-solid fa-arrow-left"></i></span>
                                    </a>
                                </div>
                                <h5 class="d-flex align-self-center" style="margin-bottom: 0;">Sửa đơn #${order.id}</h5>
                            </div>
                            <div class="content-edit d-flex w-100">
                                <div class="edit-left d-flex flex-column">
                                    <div class="edit-product border-outline">
                                        <div class="">
                                            <div class="header-text"><span>Thêm sản phẩm</span></div>
                                            <div class="product-seacrch d-flex justify-content-between">
                                                <div class="input-product d-flex flex-grow-1">
                                                    <div class="d-flex input-align align-self-center flex-grow-1">
                                                        <span class="icon-search"><i
                                                                class="fa-solid fa-magnifying-glass"></i></span>
                                                        <input id="inputSearchProduct" class="flex-grow-1" type="text"
                                                            placeholder="Tìm theo tên sản phẩm" style="width: 100%;">
                                                        <div class="table-select-product">
                                                            <div
                                                                class="display-product-detail-search d-flex flex-column">
                                                                <c:forEach items="${lstProductDetail}" var="proDetail">
                                                                    <div class="product-detail-select"
                                                                        id-product-detail="${proDetail.id}"
                                                                        name-product-select="${proDetail.product.name}"
                                                                        size-product-detail="${proDetail.size.sizeName}"
                                                                        color-product-detail="${proDetail.color.colorName}"
                                                                        id-order-data="${order.id}">
                                                                        <div class="product-img">
                                                                            <img alt=""
                                                                                src="/images/product/${proDetail.images[0].urlImage}">
                                                                        </div>
                                                                        <div
                                                                            class="d-flex flex-grow-1 justify-content-between">
                                                                            <div class="name-product-detail">
                                                                                <div>
                                                                                    <span>${proDetail.product.name}</span>
                                                                                </div>
                                                                                <div><span>${proDetail.size.sizeName},
                                                                                        ${proDetail.color.colorName}</span>
                                                                                </div>
                                                                            </div>
                                                                            <div class="price-product-detail">
                                                                                <div style="text-align: end;">
                                                                                    <span>
                                                                                        <fmt:formatNumber type="number"
                                                                                            value="${proDetail.price}" />
                                                                                        đ
                                                                                    </span>
                                                                                </div>
                                                                                <div style="margin-top: 4px;"><span>Có
                                                                                        thể
                                                                                        bán:
                                                                                        ${proDetail.quantity}</span>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- <div class="btn-select-product">
                                                    <button><span>Chọn nhiều</span></button>
                                                </div> -->
                                            </div>
                                            <div class="table-product">
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
                                                                    <div class="product-edit d-flex">
                                                                        <div class="product-img">
                                                                            <img alt=""
                                                                                src="/images/product/${orderDetail.productDetail.images[i.index].urlImage}">
                                                                        </div>
                                                                        <!-- data -->
                                                                        <div class="idProduct" style="display: none;">
                                                                            ${orderDetail.productDetail.id}</div>
                                                                        <div class="idOrderDetail"
                                                                            style="display: none;">${orderDetail.id}
                                                                        </div>
                                                                        <!-- data -->
                                                                        <div class="product-name d-flex flex-column">
                                                                            <span class="align-self-start"
                                                                                style="text-align: start;">
                                                                                ${orderDetail.productDetail.product.name}
                                                                                <span
                                                                                    style="text-align: start; font-size: 12px; color: rgb(116, 124, 135);;">${orderDetail.productDetail.color.colorName},
                                                                                    ${orderDetail.productDetail.size.sizeName}
                                                                                </span>
                                                                            </span>
                                                                            <div class="edit-order-btn sub-font d-flex">
                                                                                <span class="openModalEdit">Chỉnh số
                                                                                    lượng</span>
                                                                                <span
                                                                                    quantity-product-detail="${orderDetail.quantity}"
                                                                                    class="openModalRemove">Xóa sản
                                                                                    phẩm</span>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                                <td><span
                                                                        class="quantity-pro-order">${orderDetail.quantity}</span>
                                                                </td>
                                                                <td>
                                                                    <span class="product-price">
                                                                        <fmt:formatNumber type="number"
                                                                            value="${orderDetail.productDetail.price}" />
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
                                                    </tbody>
                                                    </c:forEach>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- <div class="display-payment border-outline">
                                        <div class="header-text"><span>Thanh toán</span></div>
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
                                                            <fmt:formatNumber type="number" value="" />
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
                                    </div> -->
                                </div>
                            </div>
                        </div>

                        <!-- modal -->
                        <div class="modal-overlay-edit-order modal-overlay" id="modalOverlayEditOrder">
                            <div class="modal-overlay-edit-order-content modal-overlay-content">
                                <div class="header-modal d-flex justify-content-between">
                                    <h3>Chỉnh sửa số lượng</h3>
                                    <span class="close-modal-icon btn-close-modal"><i
                                            class="fa-solid fa-xmark"></i></span>
                                </div>
                                <div class="content-modal">
                                    <div class="box-content-modal d-flex flex-column" style="gap: 15px;">
                                        <div class="">
                                            <span>Chỉnh sửa số lượng sản phẩm</span>
                                        </div>
                                        <div class="d-flex" style="gap: 12px;">
                                            <div class="d-flex flex-column w-50">
                                                <span style="padding: 5px 0;">Số lượng</span>
                                                <div class="input-quantity-product d-flex">
                                                    <input id="inputQuantity" type="text">
                                                </div>
                                            </div>
                                            <div class="d-flex flex-column w-50">
                                                <span style="padding: 5px 0;">Có thể bán</span>
                                                <span id="data-total-product" style="line-height: 36px;">5</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="footer-modal">
                                    <div class="d-flex justify-content-end">
                                        <div class="btn-cancel d-flex btn-close-modal">
                                            <span class="align-self-center">Hủy</span>
                                        </div>
                                        <div class="btn-confirm change-status-order" id="confirmEdit">
                                            <span>Xác nhận</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- modal remove product -->
                        <div class="modal-overlay-remove-product modal-overlay" id="modalOverlayRemoveProduct">
                            <div class="modal-overlay-remove-product-content modal-overlay-content"
                                style="width: 480px;">
                                <div class="header-modal d-flex justify-content-between">
                                    <h3>Xác nhận xóa sản phẩm khỏi đơn hàng</h3>
                                    <span class="close-modal-icon btn-close-modal"><i
                                            class="fa-solid fa-xmark"></i></span>
                                </div>
                                <div class="content-modal">
                                    <div class="box-content-modal d-flex flex-column" style="gap: 15px;">
                                        <div class="">
                                            <span id="originalQuantity">Số lượng ban đầu là 1</span>
                                        </div>
                                        <div class="d-flex" style="gap: 12px;">
                                            <div class="d-flex  w-50">
                                                <input type="checkbox" name="" id="isRestockingCheck">
                                                <span id="warehouse">Nhập kho 1 sản phẩm</span>
                                            </div>
                                            <div class="d-flex w-50">

                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="footer-modal">
                                    <div class="d-flex justify-content-end">
                                        <div class="btn-cancel d-flex btn-close-modal">
                                            <span class="align-self-center">Hủy</span>
                                        </div>
                                        <div class="btn-confirm change-status-order" id="confirmRemove">
                                            <span>Xác nhận</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- modal confirm add product detail -->
                        <div class="modal-overlay-confirm-add-product modal-overlay" id="modalOverlayConfirmAddProduct">
                            <div class="modal-overlay-add-product-content modal-overlay-content" style="width: 480px;">
                                <div class="header-modal d-flex justify-content-between">
                                    <h3>Xác nhận thêm sản phẩm</h3>
                                    <span class="close-modal-icon btn-close-modal"><i
                                            class="fa-solid fa-xmark"></i></span>
                                </div>
                                <div class="content-modal">
                                    <div class="box-content-modal">
                                        <div class="" style="margin-bottom: 15px;">
                                            <span id="textProductDetail">Xác nhận thêm sản phẩm </span>
                                        </div>
                                        <div class="">
                                            <span style="margin-bottom: 5px; display: block;">Số lượng</span>
                                            <div class="input-quantity-product d-flex">
                                                <input style="flex-grow: 1;" id="inputQuantityAdd" type="text">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="footer-modal">
                                    <div class="d-flex justify-content-end">
                                        <div class="btn-cancel d-flex btn-close-modal" id="cancelAddProductButton">
                                            <span class="align-self-center">Hủy</span>
                                        </div>
                                        <div class="btn-confirm change-status-order" id="confirmAddProductButton">
                                            <span>Xác nhận</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <jsp:include page="../layout/footer.jsp" />
                        <div id="toast"></div>
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/admin/js/scripts.js"></script>
                <script src="/common/toast.js"></script>
                <script src="/admin/js/order/order-management-edit.js"></script>
            </body>

            </html>