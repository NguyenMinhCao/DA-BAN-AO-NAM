<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                        <link rel="stylesheet" href="../css/style1.css" />
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
                                            <h2>Sản phẩm chi tiết</h2>
                                            <p>Very us move be blessed multiply night</p>
                                        </div>
                                        <div class="page_link">
                                            <a href="index.html">Home</a>
                                            <a href="single-product.html">Product Details</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <!--================End Home Banner Area =================-->

                        <!--================Single Product Area =================-->
                        <div class="product_image_area">
                            <div class="container">
                                <div class="row s_product_inner">
                                    <div class="col-lg-6">
                                        <div class="s_product_img">
                                            <div id="carouselExampleIndicators" class="carousel slide"
                                                data-ride="carousel">
                                                <ol class="carousel-indicators">
                                                    <li data-target="#carouselExampleIndicators" data-slide-to="0"
                                                        class="active">
                                                        <img style="width: 60px;"
                                                            src="/images/product/${product.productDetails[0].images[0].urlImage}"
                                                            alt="" />
                                                    </li>
                                                    <li data-target="#carouselExampleIndicators" data-slide-to="1">
                                                        <img style="width: 60px;"
                                                            src="/images/product/${product.productDetails[1].images[0].urlImage}"
                                                            alt="" />
                                                    </li>
                                                    <li data-target="#carouselExampleIndicators" data-slide-to="2">
                                                        <img style="width: 60px;"
                                                            src="/images/product/${product.productDetails[2].images[0].urlImage}"
                                                            alt="" />
                                                    </li>
                                                </ol>
                                                <div class="carousel-inner">
                                                    <div class="carousel-item active">
                                                        <img class="d-block w-100"
                                                            src="/images/product/${product.productDetails[0].images[0].urlImage}"
                                                            alt="First slide" />
                                                    </div>
                                                    <div class="carousel-item">
                                                        <img class="d-block w-100"
                                                            src="/images/product/${product.productDetails[1].images[0].urlImage}"
                                                            alt="Second slide" />
                                                    </div>
                                                    <div class="carousel-item">
                                                        <img class="d-block w-100"
                                                            src="/images/product/${product.productDetails[2].images[0].urlImage}"
                                                            alt="Third slide" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-5 offset-lg-1">
                                        <div class="s_product_text">
                                            <h3>${product.name}</h3>
                                            <h2>
                                                <fmt:formatNumber type="number" value="${lstProductDetail[0].price}" />
                                                đ
                                            </h2>
                                            <ul class="list">
                                                <li>
                                                    <a class="active" href="#">
                                                        <span>Chất liệu</span> : ${product.material.materialName}</a>
                                                </li>
                                                <li>
                                                    <a href="#"> <span>Trạng thái</span> : Còn hàng</a>
                                                </li>
                                            </ul>
                                            <p style="margin-bottom: 20px;">
                                                ${product.description}
                                            </p>

                                            <div class="box-select-variant">
                                                <div class="box-colors">
                                                    <div class="text-select"><span>Màu sắc</span></div>
                                                    <div class="d-flex" style="gap: 10px;">
                                                        <c:forEach items="${lstColorInProDetail}" var="color"
                                                            varStatus="i">
                                                            <div class="select-box-colors d-flex"
                                                                idColorInColor="${color.id}"
                                                                idProductColor="${productIdColor}">
                                                                <div class="box-select">
                                                                    <span>${color.colorName}</span>
                                                                </div>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="box-sizes">
                                                    <div class="text-select"><span>Kích cỡ</span></div>
                                                    <div class="d-flex" style="gap: 10px;">
                                                        <c:forEach items="${lstSizeInProDetail}" var="size"
                                                            varStatus="i">
                                                            <div class="select-box-sizes" idSizeInSize="${size.id}"
                                                                idProductSize="${productIdSize}">
                                                                <span class="name-size">${size.sizeName}
                                                                </span>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="product_count">
                                                <label for="qty">Quantity:</label>
                                                <input type="text" name="qty" id="quantityProductSelect" maxlength="12"
                                                    value="${quantityProduct}" title="Quantity:"
                                                    class="input-text qty" />
                                                <button
                                                    onclick="var result = document.getElementById('sst'); var sst = result.value; if( !isNaN( sst )) result.value++;return false;"
                                                    class="increase items-count" type="button">
                                                    <i class="lnr lnr-chevron-up"></i>
                                                </button>
                                                <button
                                                    onclick="var result = document.getElementById('sst'); var sst = result.value; if( !isNaN( sst ) && sst > 0 ) result.value--; return false;"
                                                    class="reduced items-count" type="button">
                                                    <i class="lnr lnr-chevron-down"></i>
                                                </button>

                                            </div>
                                            <div class="card_area">
                                                <a class="main_btn" id="btnAddProductToCart" href="#">Add to Cart</a>
                                                <a class="icon_btn" href="#">
                                                    <i class="lnr lnr lnr-diamond"></i>
                                                </a>
                                                <a class="icon_btn" href="#">
                                                    <i class="lnr lnr lnr-heart"></i>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--================End Single Product Area =================-->

                        <!--================Product Description Area =================-->
                        <!--================End Product Description Area =================-->

                        <!--================ start footer Area  =================-->
                        <footer class="footer-area section_gap" style="margin-top: 400px;">
                            <div class="container">
                                <div class="row">
                                    <div class="col-lg-2 col-md-6 single-footer-widget">
                                        <h4>Top Products</h4>
                                        <ul>
                                            <li><a href="#">Managed Website</a></li>
                                            <li><a href="#">Manage Reputation</a></li>
                                            <li><a href="#">Power Tools</a></li>
                                            <li><a href="#">Marketing Service</a></li>
                                        </ul>
                                    </div>
                                    <div class="col-lg-2 col-md-6 single-footer-widget">
                                        <h4>Quick Links</h4>
                                        <ul>
                                            <li><a href="#">Jobs</a></li>
                                            <li><a href="#">Brand Assets</a></li>
                                            <li><a href="#">Investor Relations</a></li>
                                            <li><a href="#">Terms of Service</a></li>
                                        </ul>
                                    </div>
                                    <div class="col-lg-2 col-md-6 single-footer-widget">
                                        <h4>Features</h4>
                                        <ul>
                                            <li><a href="#">Jobs</a></li>
                                            <li><a href="#">Brand Assets</a></li>
                                            <li><a href="#">Investor Relations</a></li>
                                            <li><a href="#">Terms of Service</a></li>
                                        </ul>
                                    </div>
                                    <div class="col-lg-2 col-md-6 single-footer-widget">
                                        <h4>Resources</h4>
                                        <ul>
                                            <li><a href="#">Guides</a></li>
                                            <li><a href="#">Research</a></li>
                                            <li><a href="#">Experts</a></li>
                                            <li><a href="#">Agencies</a></li>
                                        </ul>
                                    </div>
                                    <div class="col-lg-4 col-md-6 single-footer-widget">
                                        <h4>Newsletter</h4>
                                        <p>You can trust us. we only send promo offers,</p>
                                        <div class="form-wrap" id="mc_embed_signup">
                                            <form target="_blank"
                                                action="https://spondonit.us12.list-manage.com/subscribe/post?u=1462626880ade1ac87bd9c93a&amp;id=92a4423d01"
                                                method="get" class="form-inline">
                                                <input class="form-control" name="EMAIL"
                                                    placeholder="Your Email Address" onfocus="this.placeholder = ''"
                                                    onblur="this.placeholder = 'Your Email Address '" required=""
                                                    type="email">
                                                <button class="click-btn btn btn-default">Subscribe</button>
                                                <div style="position: absolute; left: -5000px;">
                                                    <input name="b_36c4fd991d266f23781ded980_aefe40901a" tabindex="-1"
                                                        value="" type="text">
                                                </div>

                                                <div class="info"></div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="footer-bottom row align-items-center">
                                    <p class="footer-text m-0 col-lg-8 col-md-12">
                                        <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                        Copyright &copy;
                                        <script>document.write(new Date().getFullYear());</script> All rights reserved |
                                        This template is
                                        made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a
                                            href="https://colorlib.com" target="_blank">Colorlib</a>
                                        <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                    </p>
                                    <div class="col-lg-4 col-md-12 footer-social">
                                        <a href="#"><i class="fa fa-facebook"></i></a>
                                        <a href="#"><i class="fa fa-twitter"></i></a>
                                        <a href="#"><i class="fa fa-dribbble"></i></a>
                                        <a href="#"><i class="fa fa-behance"></i></a>
                                    </div>
                                </div>
                            </div>
                        </footer>
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
                        <script src="/js/detail-pro.js"></script>
                    </body>

                    </html>