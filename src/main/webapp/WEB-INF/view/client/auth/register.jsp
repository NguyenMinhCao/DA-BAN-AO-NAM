<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta http-equiv="X-UA-Compatible" content="ie=edge">
                <title>Sign Up Form by Colorlib</title>

                <!-- font icon -->
                <link rel="stylesheet"
                    href="https://cdnjs.cloudflare.com/ajax/libs/material-design-iconic-font/2.2.0/css/material-design-iconic-font.min.css">

                <!-- Main css -->
                <link rel="stylesheet" href="css/style-auth.css">
            </head>

            <body>

                <div class="main" style="padding: 80px 0;">
                    <!-- Sign up form -->
                    <section class="signup">
                        <div class="container">
                            <div class="signup-content">
                                <div class="signup-form">
                                    <h2 class="form-title">Sign up</h2>
                                    <form:form method="POST" modelAttribute="registerUser" action="/register"
                                        class="register-form">
                                        <c:set var="errorFullName">
                                            <form:errors path="fullName" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorEmail">
                                            <form:errors path="email" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorPassWord">
                                            <form:errors path="password" cssClass="invalid-feedback" />
                                        </c:set>
                                        <c:set var="errorConfirmPass">
                                            <form:errors path="confirmPassword" cssClass="invalid-feedback" />
                                        </c:set>
                                        <div class="form-group">
                                            <label for="name"><i class="zmdi zmdi-account material-icons-name"></i>
                                            </label>

                                            <form:input
                                                class="form-control ${not empty errorFullName ? 'is-invalid':''}"
                                                type="text" path="fullName" id="name" placeholder="Your Name" />
                                            ${errorFullName}

                                        </div>
                                        <div class="form-group">
                                            <label for="email"><i class="zmdi zmdi-email"></i></label>
                                            <form:input class="form-control ${not empty errorEmail ? 'is-invalid':''}"
                                                type="email" path="email" id="email" placeholder="Your Email" />
                                            ${errorEmail}
                                        </div>
                                        <div class="form-group">
                                            <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                            <form:input
                                                class="form-control ${not empty errorPassWord ? 'is-invalid':''}"
                                                type="password" path="password" id="pass" placeholder="Password" />
                                            ${errorPassWord}
                                        </div>
                                        <div class="form-group">
                                            <label for="re-pass"><i class="zmdi zmdi-lock-outline"></i></label>
                                            <form:input
                                                class="form-control ${not empty errorConfirmPass ? 'is-invalid':''}"
                                                type="password" path="confirmPassword" id="re_pass"
                                                placeholder="Repeat your password" />
                                            ${errorConfirmPass}
                                        </div>
                                        <div class="form-group">
                                            <input type="checkbox" name="agree-term" id="agree-term"
                                                class="agree-term" />
                                            <label for="agree-term" class="label-agree-term"><span><span></span></span>I
                                                agree all
                                                statements in <a href="#" class="term-service">Terms of
                                                    service</a></label>
                                        </div>
                                        <div class="form-group form-button">
                                            <input type="submit" id="signup" class="form-submit" value="Register" />
                                        </div>
                                    </form:form>
                                </div>
                                <div class="signup-image">
                                    <figure><img src="images/auth/signup-image.jpg" alt="sing up image"></figure>
                                    <a href="/login" class="signup-image-link">I am already member</a>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>

                <!-- JS -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="vendors/jquery/jquery.min.js"></script>
            </body><!-- This templates was made by Colorlib (https://colorlib.com) -->

            </html>