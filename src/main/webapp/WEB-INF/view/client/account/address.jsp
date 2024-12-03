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
                            <div class="col-md-3">
                                <div class="card" style="height: 450px;">
                                    <div class="profile-pic text-center" style="margin-top: 25px;">
                                        <img src="/images/avatar/${userByEmail.avatar}" alt="Profile Picture"
                                            class="rounded-circle img-fluid" style="width: 69px;height: 69px ;">
                                        <p style="margin-bottom: 0px;">${userByEmail.fullName}</p>
                                    </div>
                                    <div class="card-body" style="padding: 0px; margin-left:70px; margin-top: 12px;">
                                        <ul class="nav flex-column">
                                            <li class="nav-item">
                                                <i style="font-size: 17px;color: #0044ad;" class="ti-user"></i>
                                                <a style="display: inline-block;padding-left: 9px;" class="nav-link"
                                                    href="#">Tài Khoản
                                                    Của Tôi</a>
                                            </li>
                                            <div class="nav-item-account" style="margin-left: 15px;">
                                                <li class="nav-item">
                                                    <a class="nav-link" href="/user/profile">Hồ Sơ</a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link" href="/user/address">Địa chỉ</a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link" href="#">Đổi Mật Khẩu</a>
                                                </li>

                                            </div>
                                            <li class="nav-item">
                                                <i style="font-size: 17px;color: #0044ad;" class="ti-receipt"></i>
                                                <a style="display: inline-block;padding-left: 9px;" class="nav-link"
                                                    href="/user/orders">Đơn mua</a>
                                            </li>
                                            <li class="nav-item">
                                                <i style="font-size: 17px;color: #0044ad;" class="ti-package"></i>
                                                <a style="display: inline-block;padding-left: 9px;" class="nav-link"
                                                    href="#">Kho voucher</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <!-- Profile Section -->
                            <div class="col-md-9" style="height: 450px;">
                                <div class="card">
                                    <div class="card-body" style="height: 450px;">
                                        <h4 class="" style="margin: 0; padding: 0">Địa chỉ của tôi</h4>
                                        <button style="float: right;">Thêm địa chỉ mới</button>
                                        <hr>
                                        <form:form class=" d-flex" method="post" action="/user/account-update"
                                            modelAttribute="userByEmail" enctype="multipart/form-data">
                                            <div class="">
                                                <div class="mb-3" style="display: none;">
                                                    <label class="form-label">Id</label>
                                                    <form:input type="text" class="form-control" path="id" />
                                                </div>
                                                <div class="mb-3">
                                                    <label for="name" class="form-label">Tên</label>
                                                    <form:input type="text" class="form-control" id="name"
                                                        path="fullName" />
                                                </div>

                                                <div class="mb-3">
                                                    <label for="email" class="form-label">Email</label>
                                                    <form:input type="email" class="form-control" path="email" />
                                                </div>

                                                <div class="mb-3">
                                                    <label for="phone" class="form-label">Số điện thoại</label>
                                                    <form:input type="text" class="form-control" path="phoneNumber" />
                                                </div>

                                                <fieldset class="mb-3">
                                                    <label style="margin-right: 107px;">Giới tính</label>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" value="true"
                                                            name="gender" ${userByEmail.gender==true?'checked':''}>
                                                        <label class="form-check-label">Nam</label>
                                                    </div>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" value="false"
                                                            name="gender" ${!userByEmail.gender==true?'checked':''}>
                                                        <label class="form-check-label">Nữ</label>
                                                    </div>
                                                </fieldset>

                                                <div class="mb-3">
                                                    <label for="dob" class="form-label">Ngày sinh</label>
                                                    <div class="d-flex-date"
                                                        style="justify-content: space-between; display: flex;">
                                                        <select class="form-select me-2" id="day">
                                                            <option style="display: none;">Ngày</option>
                                                            <c:forEach var="i" begin="1" end="30" step="1">
                                                                <option>${i}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <select class="form-select me-2" id="month">
                                                            <option>Tháng</option>
                                                            <c:forEach var="i" begin="1" end="12" step="1">
                                                                <option>Tháng ${i}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <select class="form-select" id="yeariii">
                                                            <option>Năm</option>
                                                            <c:forEach var="i" begin="1950" end="2024" step="1">
                                                                <option>Năm ${i}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <button type="submit" class="btn btn-primary">Lưu</button>
                                            </div>

                                            <div class="input-user-photo" style="border-left: .0625rem solid #efefef;">
                                                <div class="TJWfNh">
                                                    <div class="nMPYiw" role="header">
                                                        <label for="file-input" class="cW0oBM"
                                                            style="background-image: url('/images/avatar/${userByEmail.avatar}')"></label>
                                                        <input id="file-input" type="file" accept=".jpg,.jpeg,.png"
                                                            style="display: none;" name="getImgFile">
                                                    </div>
                                                    <button type="button" class="btn btn-light btn--m btn--inline"
                                                        id="choose-file-btn">Chọn ảnh</button>
                                                    <div class="T_8sqN">
                                                        <div class="JIExfq">Dụng lượng file tối đa 1 MB</div>
                                                        <div class="JIExfq">Định dạng:.JPEG, .PNG</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form:form>
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

                </body>

                </html>