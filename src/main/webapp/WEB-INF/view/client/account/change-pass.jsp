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
                            <div class="col-md-9" style="height: 450px;">
                                <div class="card">
                                    <div class="card-body" style="height: 450px;">
                                        <h4 class="" style="margin: 0; padding: 0">Đổi mật khẩu</h4>
                                        <span class="">Để đảm bảo tính bảo mật bạn vui lòng đặt lại mật khẩu với ít nhất
                                            8 kí tự</span>
                                        <hr>
                                        <form class="d-flex" method="post" action="/user/change-pass">
                                            <div class="">
                                                <div class="mb-3" style="display: none;">
                                                    <label class="form-label">Id</label>
                                                    <input type="text" class="form-control" />
                                                </div>
                                                <div class="mb-3">
                                                    <label for="name" class="form-label">Mật khẩu cũ</label>
                                                    <input type="text" class="form-control" id="name" />
                                                </div>

                                                <div class="mb-3">
                                                    <label for="email" class="form-label">Mật khẩu mới</label>
                                                    <input type="email" class="form-control" />
                                                </div>

                                                <div class="mb-3">
                                                    <label for="phone" class="form-label">Xác nhận lại mật khẩu</label>
                                                    <input type="text" class="form-control" />
                                                </div>
                                                <button type="submit" id="btn-update-user" class="btn btn-primary">Đặt
                                                    lại mật khẩu</button>
                                            </div>
                                        </form>
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

                </body>

                </html>