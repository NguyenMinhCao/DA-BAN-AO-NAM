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
                    <link rel="stylesheet" href="css/flaticon.css" />
                    <link rel="stylesheet" href="vendors/owl-carousel/owl.carousel.min.css" />
                    <link rel="stylesheet" href="vendors/lightbox/simpleLightbox.css" />
                    <link rel="stylesheet" href="vendors/nice-select/css/nice-select.css" />
                    <link rel="stylesheet" href="vendors/animate-css/animate.css" />
                    <link rel="stylesheet" href="vendors/jquery-ui/jquery-ui.css" />
                    <!-- main css -->
                    <link rel="stylesheet"
                        href="https://cdnjs.cloudflare.com/ajax/libs/material-design-iconic-font/2.2.0/css/material-design-iconic-font.min.css">

                    <link rel="stylesheet" href="css/style.css" />
                    <link rel="stylesheet" href="css/responsive.css" />
                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@7/swiper-bundle.min.css" />
                    <link rel="stylesheet" href="css/style1.css" />

                </head>

                <body>
                    <!--================Header Menu Area =================-->
                    <jsp:include page="../layout/header.jsp" />
                    <!--================Header Menu Area =================-->

                    <!--================Home Banner Area =================-->
                    <section class="home_banner_area mb-40">
                        <div class="banner_inner d-flex align-items-center">
                            <div class="container">
                                <div class="banner_content row">
                                    <div class="col-lg-12">
                                        <p class="sub text-uppercase">men Collection</p>
                                        <h3><span>Show</span> Your <br />Personal <span>Style</span></h3>
                                        <h4>Fowl saw dry which a above together place.</h4>
                                        <a class="main_btn mt-40" href="#">Xem bộ sưu tập</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <!--================End Home Banner Area =================-->

                    <!-- Start feature Area -->
                    <section class="feature-area section_gap_bottom_custom">
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-3 col-md-6">
                                    <div class="single-feature">
                                        <a href="#" class="title">
                                            <i class="flaticon-money"></i>
                                            <h3>Đảm bảo hoàn tiền</h3>
                                        </a>
                                        <p>Shall open divide a one</p>
                                    </div>
                                </div>

                                <div class="col-lg-3 col-md-6">
                                    <div class="single-feature">
                                        <a href="#" class="title">
                                            <i class="flaticon-truck"></i>
                                            <h3>Giao hàng miễn phí</h3>
                                        </a>
                                        <p>Shall open divide a one</p>
                                    </div>
                                </div>

                                <div class="col-lg-3 col-md-6">
                                    <div class="single-feature">
                                        <a href="#" class="title">
                                            <i class="flaticon-support"></i>
                                            <h3>Luôn luôn hỗ trợ</h3>
                                        </a>
                                        <p>Shall open divide a one</p>
                                    </div>
                                </div>

                                <div class="col-lg-3 col-md-6">
                                    <div class="single-feature">
                                        <a href="#" class="title">
                                            <i class="flaticon-blockchain"></i>
                                            <h3>Thanh toán an toán</h3>
                                        </a>
                                        <p>Shall open divide a one</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <!-- End feature Area -->

                    <!--================ Feature Product Area =================-->
                    <section class="feature_product_area section_gap_bottom_custom">
                        <div class="container">
                            <div class="row justify-content-center">
                                <div class="col-lg-12">
                                    <div class="main_title">
                                        <h2><span>SẢN PHẨM NỔI BẬT</span></h2>
                                        <p>Bring called seed first of third give itself now ment</p>
                                    </div>
                                </div>
                            </div>

                            <div class="swiper-container" style="overflow: hidden;">
                                <div class="swiper-wrapper">
                                    <c:forEach items="${listProducts}" var="product">
                                        <div class="col-lg-4 col-md-6 swiper-slide">
                                            <div class="single-product">
                                                <div class="product-img">
                                                    <img class="img-fluid w-100"
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
                                                    <a href="/product/${product.id}" class="d-block">
                                                        <h4>${product.name}</h4>
                                                    </a>
                                                    <div class="mt-3">
                                                        <span class="mr-4">
                                                            <fmt:formatNumber type="number"
                                                                value="${product.productDetails[0].price}" />
                                                            đ
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <div class="swiper-button-next"></div>
                                <div class="swiper-button-prev"></div>
                            </div>
                        </div>
                    </section>
                    <!--================ End Feature Product Area =================-->

                    <!--================ Offer Area =================-->
                    <section class="offer_area">
                        <div class="container">
                            <div class="row justify-content-center">
                                <div class="offset-lg-4 col-lg-6 text-center">
                                    <div class="offer_content">
                                        <h3 class="text-uppercase mb-40">all men’s collection</h3>
                                        <h2 class="text-uppercase">50% off</h2>
                                        <a href="#" class="main_btn mb-20 mt-5">Discover Now</a>
                                        <p>Limited Time Offer</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <!--================ End Offer Area =================-->

                    <!--================ New Product Area =================-->
                    <section class="new_product_area section_gap_top section_gap_bottom_custom">
                        <div class="container">
                            <div class="row justify-content-center">
                                <div class="col-lg-12">
                                    <div class="main_title">
                                        <h2><span>sản phẩm mới</span></h2>
                                        <p>Mang hạt giống được gọi là thứ nhất của thứ ba cho chính nó bây giờ ment</p>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="new_product">
                                        <h5 class="text-uppercase">collection of 2019</h5>
                                        <h3 class="text-uppercase">Men’s summer t-shirt</h3>
                                        <div class="product-img">
                                            <img class="img-fluid" src="img/product/new-product/new-product1.png"
                                                alt="" />
                                        </div>
                                        <h4>$120.70</h4>
                                        <a href="#" class="main_btn">Add to cart</a>
                                    </div>
                                </div>

                                <div class="col-lg-6 mt-5 mt-lg-0">
                                    <div class="row">
                                        <div class="col-lg-6 col-md-6">
                                            <div class="single-product">
                                                <div class="product-img">
                                                    <img class="img-fluid w-100"
                                                        src="images/product/1731672478985-ao-polo-nam-theu-typo-dream-and-achieve-form-regular__7__6bedc865b3284db7907c5d1d721d19a8_master.jpg"
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
                                                    <a href="#" class="d-block">
                                                        <h4>Nike latest sneaker</h4>
                                                    </a>
                                                    <div class="mt-3">
                                                        <span class="mr-4">$25.00</span>
                                                        <del>$35.00</del>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-lg-6 col-md-6">
                                            <div class="single-product">
                                                <div class="product-img">
                                                    <img class="img-fluid w-100"
                                                        src="images/product/1731672992394-ao-polo-nam-tron-co-v-orgnls-basic-form-regular__8__b05f4e00a5614a90b14d3fe3f51c2071_master.jpg"
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
                                                    <a href="#" class="d-block">
                                                        <h4>Men’s denim jeans</h4>
                                                    </a>
                                                    <div class="mt-3">
                                                        <span class="mr-4">$25.00</span>
                                                        <del>$35.00</del>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-lg-6 col-md-6">
                                            <div class="single-product">
                                                <div class="product-img">
                                                    <img class="img-fluid w-100"
                                                        src="images/product/1731672478985-ao-polo-nam-theu-typo-dream-and-achieve-form-regular__7__6bedc865b3284db7907c5d1d721d19a8_master.jpg"
                                                        alt="" />
                                                    <div class="p_icon">
                                                        <a href="/product/${product.id}">
                                                            <i class="ti-eye"></i>
                                                        </a>
                                                        <a href="#">
                                                            <i class="ti-heart"></i>
                                                        </a>
                                                        <a href="#">
                                                            <i class="ti-shopping-cart"></i>
                                                        </a>
                                                    </div>
                                                </div>
                                                <div class="product-btm">
                                                    <a href="#" class="d-block">
                                                        <h4>quartz hand watch</h4>
                                                    </a>
                                                    <div class="mt-3">
                                                        <span class="mr-4">$25.00</span>
                                                        <del>$35.00</del>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-lg-6 col-md-6">
                                            <div class="single-product">
                                                <div class="product-img">
                                                    <img class="img-fluid w-100"
                                                        src="images/product/1731673176594-id-008734a__1__08e5c852013245f28ba0154d9212afb8_master.jpg"
                                                        alt="" />
                                                    <div class="p_icon">
                                                        <a href="/product/${product.id}">
                                                            <i class="ti-eye"></i>
                                                        </a>
                                                        <a href="#">
                                                            <i class="ti-heart"></i>
                                                        </a>
                                                        <a href="#">
                                                            <i class="ti-shopping-cart"></i>
                                                        </a>
                                                    </div>
                                                </div>
                                                <div class="product-btm">
                                                    <a href="#" class="d-block">
                                                        <h4>adidas sport shoe</h4>
                                                    </a>
                                                    <div class="mt-3">
                                                        <span class="mr-4">$25.00</span>
                                                        <del>$35.00</del>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <!--================ End New Product Area =================-->

                    <!--================ Inspired Product Area =================-->
                    <section class="inspired_product_area section_gap_bottom_custom">
                        <div class="container">
                            <div class="row justify-content-center">
                                <div class="col-lg-12">
                                    <div class="main_title">
                                        <h2><span>Tất cả sản phẩm</span></h2>
                                        <p>Bring called seed first of third give itself now ment</p>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <c:forEach items="${listProducts}" var="product">
                                    <div class="col-lg-3 col-md-6">
                                        <div class="single-product">
                                            <div class="product-img">
                                                <img class="img-fluid w-100"
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
                                                            value="${product.productDetails[0].price}" />
                                                        đ
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </section>
                    <!--================ End Inspired Product Area =================-->

                    <!--================ Start Blog Area =================-->
                    <!-- <section class="blog-area section-gap">
                        <div class="container">
                            <div class="row justify-content-center">
                                <div class="col-lg-12">
                                    <div class="main_title">
                                        <h2><span>bài viết mới nhất</span></h2>
                                        <p>Bring called seed first of third give itself now ment</p>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-lg-4 col-md-6">
                                    <div class="single-blog">
                                        <div class="thumb">
                                            <img class="img-fluid" src="img/b1.jpg" alt="">
                                        </div>
                                        <div class="short_details">
                                            <div class="meta-top d-flex">
                                                <a href="#">By Admin</a>
                                                <a href="#"><i class="ti-comments-smiley"></i>2 Comments</a>
                                            </div>
                                            <a class="d-block" href="single-blog.html">
                                                <h4>Ford clever bed stops your sleeping
                                                    partner hogging the whole</h4>
                                            </a>
                                            <div class="text-wrap">
                                                <p>
                                                    Let one fifth i bring fly to divided face for bearing the divide
                                                    unto seed winged
                                                    divided light
                                                    Forth.
                                                </p>
                                            </div>
                                            <a href="#" class="blog_btn">Learn More <span
                                                    class="ml-2 ti-arrow-right"></span></a>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-4 col-md-6">
                                    <div class="single-blog">
                                        <div class="thumb">
                                            <img class="img-fluid" src="img/b2.jpg" alt="">
                                        </div>
                                        <div class="short_details">
                                            <div class="meta-top d-flex">
                                                <a href="#">By Admin</a>
                                                <a href="#"><i class="ti-comments-smiley"></i>2 Comments</a>
                                            </div>
                                            <a class="d-block" href="single-blog.html">
                                                <h4>Ford clever bed stops your sleeping
                                                    partner hogging the whole</h4>
                                            </a>
                                            <div class="text-wrap">
                                                <p>
                                                    Let one fifth i bring fly to divided face for bearing the divide
                                                    unto seed winged
                                                    divided light
                                                    Forth.
                                                </p>
                                            </div>
                                            <a href="#" class="blog_btn">Learn More <span
                                                    class="ml-2 ti-arrow-right"></span></a>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-4 col-md-6">
                                    <div class="single-blog">
                                        <div class="thumb">
                                            <img class="img-fluid" src="img/b3.jpg" alt="">
                                        </div>
                                        <div class="short_details">
                                            <div class="meta-top d-flex">
                                                <a href="#">By Admin</a>
                                                <a href="#"><i class="ti-comments-smiley"></i>2 Comments</a>
                                            </div>
                                            <a class="d-block" href="single-blog.html">
                                                <h4>Ford clever bed stops your sleeping
                                                    partner hogging the whole</h4>
                                            </a>
                                            <div class="text-wrap">
                                                <p>
                                                    Let one fifth i bring fly to divided face for bearing the divide
                                                    unto seed winged
                                                    divided light
                                                    Forth.
                                                </p>
                                            </div>
                                            <a href="#" class="blog_btn">Learn More <span
                                                    class="ml-2 ti-arrow-right"></span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section> -->
                    <!--================ End Blog Area =================-->

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
                    <script src="vendors/counter-up/jquery.waypoints.min.js"></script>
                    <script src="vendors/counter-up/jquery.counterup.js"></script>
                    <script src="js/mail-script.js"></script>
                    <script src="js/theme.js"></script>
                    <script src="/js/myjs.js"></script>
                    <!-- Swiper JS -->
                    <script src="https://cdn.jsdelivr.net/npm/swiper@7/swiper-bundle.min.js"></script>
                    <!-- Optional: Include jQuery if you need it for other scripts -->
                    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
                    <!-- Optional: Include Bootstrap JS if you need it for other components -->
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
                    <script>
                        var swiper = new Swiper('.swiper-container', {
                            slidesPerView: 3,  // Số slide hiển thị cùng lúc
                            spaceBetween: 0,  // Khoảng cách giữa các slide
                            loop: false,       // Tắt chế độ lặp nếu không cần thiết
                            centeredSlides: false,
                            navigation: {
                                nextEl: '.swiper-button-next',
                                prevEl: '.swiper-button-prev',
                            },
                            breakpoints: {
                                // Điều chỉnh số slide hiển thị ở các kích thước màn hình khác nhau
                                0: {
                                    slidesPerView: 1,
                                },
                                576: {
                                    slidesPerView: 2,
                                },
                                992: {
                                    slidesPerView: 3,
                                },
                            },
                        });

                    </script>
                </body>

                </html>