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
                                        <h4 class="" style="margin: 0; padding: 0">Hồ Sơ Của Tôi</h4>
                                        <span class="">Quản lý thông tin hồ sơ để bảo mật tài khoản</span>
                                        <hr>
                                        <form:form class="d-flex" method="post" action="/user/account-update"
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
                                                        <select class="form-select me-2" id="day" name="day">
                                                            <option>Ngày</option>
                                                            <c:forEach var="i" begin="1" end="30" step="1">
                                                                <option value="${i}" <c:if
                                                                    test="${userByEmail.dateOfBirth.dayOfMonth == i}">
                                                                    selected
                                                                    </c:if>>Ngày ${i}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <select class="form-select me-2" id="month" name="month">
                                                            <option>Tháng</option>
                                                            <c:forEach var="i" begin="1" end="12" step="1">
                                                                <option value="${i}" <c:if
                                                                    test="${userByEmail.dateOfBirth.monthValue == i}">
                                                                    selected
                                                                    </c:if>>Tháng ${i}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <select class="form-select" id="year" name="year">
                                                            <option>Năm</option>
                                                            <c:forEach var="i" begin="1950" end="2024" step="1">
                                                                <option value="${i}" <c:if
                                                                    test="${userByEmail.dateOfBirth.year == i}">
                                                                    selected
                                                                    </c:if>>Năm ${i}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <input type="hidden" path="dateOfBirth" name="dateOfBirth"
                                                    id="dateOfBirth" />

                                                <button type="submit" id="btn-update-user"
                                                    class="btn btn-primary">Lưu</button>
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
                    <script src="/js/js-account.js"></script>

                </body>

                </html>