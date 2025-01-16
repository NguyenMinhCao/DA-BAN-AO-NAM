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
                    <link rel="stylesheet" href="css/bootstrap.css" />
                    <link rel="stylesheet" href="vendors/linericon/style.css" />
                    <link rel="stylesheet" href="css/font-awesome.min.css" />
                    <link rel="stylesheet" href="css/themify-icons.css" />
                    <link rel="stylesheet" href="vendors/owl-carousel/owl.carousel.min.css" />
                    <link rel="stylesheet" href="vendors/lightbox/simpleLightbox.css" />
                    <link rel="stylesheet" href="vendors/nice-select/css/nice-select.css" />
                    <link rel="stylesheet" href="vendors/animate-css/animate.css" />
                    <link rel="stylesheet" href="vendors/jquery-ui/jquery-ui.css" />
                    <!-- main css -->
                    <link rel="stylesheet" href="css/style.css" />
                    <link rel="stylesheet" href="css/responsive.css" />
                    <link rel="stylesheet" href="css/style1.css" />
                </head>

                <body>
                    <!--================Header Menu Area =================-->
                    <jsp:include page="../layout/header.jsp" />
                    <!--================Header Menu Area =================-->

                    <!--================Home Banner Area =================-->
                    <section class="banner_area">
                        <div class="banner_inner d-flex align-items-center">
                            <div class="container">
                                <div class="banner_content d-md-flex justify-content-between align-items-center">
                                    <div class="mb-3 mb-md-0">
                                        <h2>Danh mục cửa hàng</h2>
                                    </div>
                                    <div class="page_link">
                                        <a href="index.html">Home</a>
                                        <a href="category.html">Shop</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <!--================End Home Banner Area =================-->

                    <!--================Category Product Area =================-->
                    <section class="cat_product_area section_gap">
                        <div class="container">
                            <div class="row flex-row-reverse">
                                <div class="col-lg-9">
                                    <div class="product_top_bar">
                                        <div class="left_dorp">
                                            <select class="sorting">
                                                <option value="1">Default sorting</option>
                                                <option value="2">Default sorting 01</option>
                                                <option value="4">Default sorting 02</option>
                                            </select>
                                            <select class="show">
                                                <option value="1">Show 12</option>
                                                <option value="2">Show 14</option>
                                                <option value="4">Show 16</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="latest_product_inner">
                                        <div class="row">
                                            <c:forEach items="${listProducts}" var="product">
                                                <div class="col-lg-4 col-md-6">
                                                    <div class="single-product">
                                                        <div class="product-img">
                                                            <img class="card-img"
                                                                src="images/product/${product.productDetails[0].images[0].urlImage}"
                                                                alt="" />
                                                            <div class="p_icon">
                                                                <a href="/product/${product.id}">
                                                                    <i class="ti-eye"></i>
                                                                </a>
                                                                <a href="#">
                                                                    <i class="ti-heart"></i>
                                                                </a>
                                                                <a href="/add-product-to-cart/${product.id}">
                                                                    <i class="ti-shopping-cart"></i>
                                                                </a>
                                                            </div>
                                                        </div>
                                                        <div class="product-btm">
                                                            <a href="/product/detail/${product.id}" class="d-block">
                                                                <h4>${product.name}</h4>
                                                            </a>
                                                            <div class="mt-3">
                                                                <span class="mr-4">
                                                                    <fmt:formatNumber type="number"
                                                                        value="${product.productDetails[0].price}" /> đ
                                                                </span>
                                                                <del>$35.00</del>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-3">
                                    <div class="left_sidebar_area">
                                        <aside class="left_widgets p_filter_widgets">
                                            <div class="l_w_title">
                                                <h3>Danh mục </h3>
                                            </div>
                                            <div class="widgets_inner">
                                                <ul class="list">
                                                    <li>
                                                        <a href="#">Sản phẩm mới</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Sản phẩm nổi bật</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Áo thun</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Áo sơ mi</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Áo khoác</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Áo polo</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Áo da</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </aside>

                                        <aside class="left_widgets p_filter_widgets">
                                            <div class="l_w_title">
                                                <h3>Thương hiệu</h3>
                                            </div>
                                            <div class="widgets_inner">
                                                <ul class="list">
                                                    <li>
                                                        <a href="#">Louis Vuitton</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Owen</a>
                                                    </li>
                                                    <li class="active">
                                                        <a href="#">Top4man</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Biluxury</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Novelty</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </aside>

                                        <aside class="left_widgets p_filter_widgets">
                                            <div class="l_w_title">
                                                <h3>Màu sắc</h3>
                                            </div>
                                            <div class="widgets_inner">
                                                <ul class="list">
                                                    <c:forEach items="colorList" var="color">
                                                        <li>
                                                            <input type="radio"> <span>${color.name}</span>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </aside>

                                        <aside class="left_widgets p_filter_widgets">
                                            <div class="l_w_title">
                                                <h3>Khoảng giá </h3>
                                            </div>
                                            <div class="widgets_inner">
                                                <div class="range_item">
                                                    <div id="slider-range"></div>
                                                    <div class="">
                                                        <label for="amount">Price : </label>
                                                        <input type="text" id="amount" readonly />
                                                    </div>
                                                </div>
                                            </div>
                                        </aside>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <!--================End Category Product Area =================-->

                    <!--================ start footer Area  =================-->
                    <jsp:include page="../layout/footer.jsp" />
                    <!--================ End footer Area  =================-->

                    <!-- Optional JavaScript -->
                    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
                    <script src="js/jquery-3.2.1.min.js"></script>
                    <script src="js/popper.js"></script>
                    <script src="js/bootstrap.min.js"></script>
                    <script src="js/stellar.js"></script>
                    <script src="vendors/lightbox/simpleLightbox.min.js"></script>
                    <script src="vendors/nice-select/js/jquery.nice-select.min.js"></script>
                    <script src="vendors/isotope/imagesloaded.pkgd.min.js"></script>
                    <script src="vendors/isotope/isotope-min.js"></script>
                    <script src="vendors/owl-carousel/owl.carousel.min.js"></script>
                    <script src="js/jquery.ajaxchimp.min.js"></script>
                    <script src="js/mail-script.js"></script>
                    <script src="vendors/jquery-ui/jquery-ui.js"></script>
                    <script src="vendors/counter-up/jquery.waypoints.min.js"></script>
                    <script src="vendors/counter-up/jquery.counterup.js"></script>
                    <script src="js/theme.js"></script>
                    <script src="/js/myjs.js"></script>
                </body>

                </html>