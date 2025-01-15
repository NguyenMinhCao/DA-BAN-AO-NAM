
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <link href="${pageContext.request.contextPath}/Admin/css/order/order-stats.css" rel="stylesheet">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Statistics</title>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="/admin/css/styles.css" rel="stylesheet"/>
    <link href="/admin/css/order-statstics.css" rel="stylesheet"/>
</head>
<body>
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main>
            <div class="container">
                <div class="container-fluid px-1">
                    <h1 class="mt-4">Dashboard</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item active">
                            <a href="/admin" style="text-decoration: none;">Dashboard</a> / Product
                        </li>
                    </ol>
                </div>


                <div class="row mb-4">
                    <div class="col-md-3">
                        <div class="stat-box">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-dollar-sign fa-2x text-primary me-3"></i>
                                <div>
                                    <h4>Tổng Doanh Thu</h4>
                                    <p id="totalRevenue">
                                        <span>${totalRevenue}</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stat-box">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-calendar-day fa-2x text-success me-3"></i>
                                <div>
                                    <h4>Doanh Thu Tháng Này</h4>
                                    <p id="monthlyRevenue">
                                        <span>${monthlyRevenue}</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stat-box">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-calendar-alt fa-2x text-warning me-3"></i>
                                <div>
                                    <h4>Doanh Thu Hôm Nay</h4>
                                    <p id="todayRevenue">
                                        <span>${todayRevenue}</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stat-box">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-calendar-year fa-2x text-info me-3"></i>
                                <div>
                                    <h4>Doanh Thu Năm Nay</h4>
                                    <p id="yearlyRevenue">
                                        <span>${yearlyRevenue}</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-3">
                        <div class="stat-box">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-cogs fa-2x text-danger me-3"></i>
                                <div>
                                    <h4>Số sản phẩm quản lí</h4>
                                    <p id="totalProduct">
                                        <span>${totalProduct}</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stat-box">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-exclamation-triangle fa-2x text-warning me-3"></i>
                                <div>
                                    <h4>Số sản phẩm sắp hết hàng</h4>
                                    <p id="totalLowProduct">
                                        <span>${totalLowProduct}</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stat-box">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-calendar-alt fa-2x text-warning me-3"></i>
                                <div>
                                    <h4>Đơn hàng hôm nay</h4>
                                    <p id="totalTodayOrderCount">
                                        <span>${totalTodayOrderCount}</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stat-box">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-calendar-alt fa-2x text-warning me-3"></i>
                                <div>
                                    <h4>Doanh Thu Hôm Nay</h4>
                                    <p id="">
                                        <span>0</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <h2>Thống Kê Đơn Hàng và Sản Phẩm Bán Được</h2>
            <canvas id="orderChart" width="400" height="200"></canvas>
            <script>
                async function fetchOrderStatistics() {
                    try {
                        const response = await fetch('/api/admin/order/order-statistics');
                        if (!response.ok) {
                            throw new Error(`HTTP error! Status: ${response.status}`);
                        }
                        const data = await response.json();
                        updateChart(data);
                    } catch (error) {
                        console.error('Error fetching order statistics:', error);
                    }
                }

                function updateChart(data) {
                    const months = [];
                    const orderCounts = [];
                    const totalQuantity = [];

                    data.forEach(item => {
                        months.push(item.month);
                        orderCounts.push(item.orderCount);
                        totalQuantity.push(item.totalQuantity || 0);
                    });

                    if (orderChart) {
                        orderChart.data.labels = months;
                        orderChart.data.datasets[0].data = orderCounts;
                        orderChart.data.datasets[1].data = totalQuantity;
                        orderChart.update();
                    }
                }

                // Khởi tạo biểu đồ
                let orderChart = null;
                window.addEventListener('DOMContentLoaded', () => {
                    const ctx = document.getElementById('orderChart').getContext('2d');
                    orderChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: [],
                            datasets: [
                                {
                                    label: 'Số Đơn Hàng',
                                    data: [],
                                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                                    borderColor: 'rgba(75, 192, 192, 1)',
                                    borderWidth: 1
                                },
                                {
                                    label: 'Số Sản Phẩm Bán Được',
                                    data: [],
                                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                                    borderColor: 'rgba(255, 99, 132, 1)',
                                    borderWidth: 1
                                }
                            ]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });

                    fetchOrderStatistics();
                });
            </script>

            <h2 class="mt-4">Sản phẩm sắp hết hàng</h2>

            <table class="table table-bordered mt-3">
                <thead>
                <tr>
                    <th>Tên sản phẩm</th>
                    <th>Size</th>
                    <th>Màu</th>
                    <th>Giá</th>
                    <th>Số lượng còn lại</th>
                    <th>Tiền</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${lowStockProducts}">
                    <tr>
                        <td>${product.productName}</td>
                        <td>${product.productDetail.size.sizeName}</td>
                        <td>${product.productDetail.color.colorName}</td>
                        <td>
                            <fmt:formatNumber value="${product.productDetail.price}" type="currency" currencySymbol="₫" />
                        </td>
                        <td>${product.productDetail.quantity}</td>
                        <td>
                            <fmt:formatNumber value="${product.productDetail.price * product.productDetail.quantity}"
                                              type="currency"
                                              currencySymbol="₫" />
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
            <div class="d-flex justify-content-center mt-3">
                <nav aria-label="Pagination">
                    <ul class="pagination">
                        <c:if test="${lowStockProductsPage.hasPrevious()}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${lowStockProductsPage.number - 1}" aria-label="Trang trước">
                                    <
                                </a>
                            </li>
                        </c:if>
                        <li class="page-item disabled">
                <span class="page-link">
                    Trang ${lowStockProductsPage.number + 1} / ${lowStockProductsPage.totalPages}
                </span>
                        </li>

                        <c:if test="${lowStockProductsPage.hasNext()}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${lowStockProductsPage.number + 1}" aria-label="Trang sau">
                                    >
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>


        </main>
    <jsp:include page="../layout/footer.jsp"/>
</div>
</body>
</html>
