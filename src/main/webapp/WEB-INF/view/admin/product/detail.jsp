<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>Product Detail</title>
    <link href="/admin/css/styles.css" rel="stylesheet"/>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>
        .product-image {
            width: 200px;
            height: 200px;
            object-fit: cover;
        }

        .container {
            margin-left: 200px;
        }
    </style>
</head>
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4 content-container">
                <h1 class="mt-4">Product Detail</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"><a href="/admin" style="text-decoration: none;">Dashboard</a></li>
                    <li class="breadcrumb-item active">Product Detail</li>
                </ol>
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-md-12 col-12 mx-auto">
                            <h3>${product.name}</h3>
                            <hr>

                            <div class="row">
                                <div class="col-md-6">
                                    <h5>Price:</h5>
                                    <p>${product.price}</p>
                                </div>
                                <div class="col-md-6">
                                    <h5>Quantity:</h5>
                                    <p>${product.quantity}</p>
                                </div>
                                <div class="col-md-12">
                                    <h5>Short Description:</h5>
                                    <p>${product.shortDesc}</p>
                                </div>
                                <div class="col-md-12">
                                    <h5>Detailed Description:</h5>
                                    <p>${product.detailDesc}</p>
                                </div>
                                <div class="col-md-6">
                                    <h5>Category:</h5>
                                    <p>${product.category.categoryName}</p>
                                </div>
                                <div class="col-md-6">
                                    <h5>Material:</h5>
                                    <p>${product.material.materialName}</p>
                                </div>
                                <div class="col-md-6">
                                    <h5>Origin:</h5>
                                    <p>${product.origin.originName}</p>
                                </div>
                                <div class="col-md-6">
                                    <h5>Size:</h5>
                                    <p>${product.size.sizeName}</p>
                                </div>
                                <div class="col-md-6">
                                    <h5>Color:</h5>
                                    <p>${product.color.colorName}</p>
                                </div>
                                <div class="col-md-6">
                                    <h5>Pattern:</h5>
                                    <p>${product.pattern.patternName}</p>
                                </div>
                            </div>

                            <c:if test="${not empty product.promotions}">
                                <h5>Promotions:</h5>
                                <ul>
                                    <c:forEach var="promotion" items="${product.promotions}">
                                        <li>${promotion.name}</li>
                                    </c:forEach>
                                </ul>
                            </c:if>

                            <c:if test="${not empty product.images}">
                                <div class="row">
                                    <div class="col-md-12">
                                        <h5>Images:</h5>
                                        <div class="product-image-container">
                                            <c:forEach var="image" items="${product.images}">
                                                <img src="${pageContext.request.contextPath}/images/product/${image.imageUrl}"
                                                     class="img-fluid product-image" alt="Product Image">
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <a href="/admin/product" class="btn btn-primary mt-3">Back to Product List</a>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp"/>
    </div>
</div>
</body>
</html>
