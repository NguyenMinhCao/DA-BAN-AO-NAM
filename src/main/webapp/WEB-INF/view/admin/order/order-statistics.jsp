<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <link href="${pageContext.request.contextPath}/Admin/css/order/order-stats.css" rel="stylesheet"> <!-- Đường dẫn CSS của bạn -->

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Statistics</title>

    <!-- Link thư viện Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <!-- Link thư viện Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Link file CSS tùy chỉnh -->
    <link href="/admin/css/styles.css" rel="stylesheet" />

    <link href="/admin/css/order-statstics.css" rel="stylesheet" />
</head>
<body>
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
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
        <div class="col-md-6">
            <div class="stat-box">
                <h4>Tổng Doanh Thu</h4>
                <p id="totalRevenue">${totalRevenue}</p> <!-- Hiển thị tổng doanh thu từ controller -->
            </div>
        </div>
        <div class="col-md-6">
            <div class="stat-box">
                <h4>Doanh Thu Tháng Này</h4>
                <p id="monthlyRevenue">${monthlyRevenue}</p> <!-- Hiển thị doanh thu tháng này từ controller -->
            </div>
        </div>
    </div>

    <!-- Thêm 2 div hiển thị doanh thu hôm nay và năm nay -->
    <div class="row mb-4">
        <div class="col-md-6">
            <div class="stat-box">
                <h4>Doanh Thu Hôm Nay</h4>
                <p id="todayRevenue">${todayRevenue}</p> <!-- Hiển thị doanh thu hôm nay -->
            </div>
        </div>
        <div class="col-md-6">
            <div class="stat-box">
                <h4>Doanh Thu Năm Nay</h4>
                <p id="yearlyRevenue">${yearlyRevenue}</p> <!-- Hiển thị doanh thu năm nay -->
            </div>
        </div>
    </div>

    <!-- Biểu đồ thống kê đơn hàng -->
    <h2>Thống Kê Đơn Hàng và Sản Phẩm Bán Được</h2>
    <canvas id="orderChart" width="400" height="200"></canvas>
    <script>
        console.log('${orderStatisticsJson}');

        var orderData = [];
        try {
            if ('${orderStatisticsJson}' && '${orderStatisticsJson}' !== 'null' && '${orderStatisticsJson}' !== '') {
                orderData = JSON.parse('${orderStatisticsJson}');
            } else {
                console.error('Invalid JSON data or empty response from server.');
            }
        } catch (e) {
            console.error('Error parsing JSON:', e);
        }

        console.log(orderData);  // Kiểm tra giá trị sau khi parse JSON

        if (orderData && orderData.length > 0) {
            var ctx = document.getElementById('orderChart').getContext('2d');
            var months = [];
            var orderCounts = [];
            var totalQuantity = [];

            orderData.forEach(function(item) {
                months.push(item.month);
                orderCounts.push(item.orderCount);
                totalQuantity.push(item.totalQuantity || 0);
            });

            var orderChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: months,
                    datasets: [{
                        label: 'Số Đơn Hàng',
                        data: orderCounts,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }, {
                        label: 'Số Sản Phẩm Bán Được',
                        data: totalQuantity,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        } else {
            console.warn("No data available for the chart.");
        }
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
                <td>${product.name}</td>
                <td>${product.size.sizeName}</td>
                <td>${product.color.colorName}</td>
                <td>${product.price}</td>
                <td>${product.quantity}</td>
                <td>${product.price * product.quantity}</td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
    <div class="pagination">
        <c:if test="${lowStockProductsPage.hasPrevious()}">
            <a href="?page=${lowStockProductsPage.number - 1}">Trang trước</a>
        </c:if>
        <span>Trang ${lowStockProductsPage.number + 1} / ${lowStockProductsPage.totalPages}</span>
        <c:if test="${lowStockProductsPage.hasNext()}">
            <a href="?page=${lowStockProductsPage.number + 1}">Trang sau</a>
        </c:if>
    </div>


</div>
        </main>
        <jsp:include page="../layout/footer.jsp" />
    </div>
</body>
</html>
