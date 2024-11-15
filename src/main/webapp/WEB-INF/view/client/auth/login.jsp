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

                <!-- Font Icon -->
                <link rel="stylesheet"
                    href="https://cdnjs.cloudflare.com/ajax/libs/material-design-iconic-font/2.2.0/css/material-design-iconic-font.min.css">


                <!-- Main css -->
                <link rel="stylesheet" href="css/style-auth.css">
            </head>

            <body>
                <div class="main" style="padding: 80px 0px;">
                    <!-- Sing in  Form -->
                    <section class="sign-in">
                        <div class="container">
                            <div class="signin-content">
                                <div class="signin-image">
                                    <figure><img src="images/auth/signin-image.jpg" alt="sing up image"></figure>
                                    <a href="/register" class="signup-image-link">Create an account</a>
                                </div>

                                <div class="signin-form">
                                    <div class="error-wrap">
                                        <h2 class="form-title">Sign in</h2>
                                        <c:if test="${param.error != null}">
                                            <div class="my-2 error-login" style="color: red;">Invalid email or password.
                                            </div>
                                        </c:if>
                                        <c:if test="${param.logout != null}">
                                            <div class="my-2 error-login" style="color: seagreen;">Logout success.
                                            </div>
                                        </c:if>
                                    </div>

                                    <form method="POST" action="/login" class="register-form" id="login-form">
                                        <div class="form-group">
                                            <label for="your_name"><i
                                                    class="zmdi zmdi-account material-icons-name"></i></label>
                                            <input type="email" name="username" id="your_name"
                                                placeholder="name@example.com" />
                                        </div>
                                        <div class="form-group">
                                            <label for="inputPassword"><i class="zmdi zmdi-lock"></i></label>
                                            <input type="password" name="password" id="inputPassword"
                                                placeholder="Password" />
                                        </div>
                                        <div>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        </div>
                                        <div class="form-group">
                                            <input type="checkbox" name="remember-me" id="remember-me"
                                                class="agree-term" />
                                            <label for="remember-me"
                                                class="label-agree-term"><span><span></span></span>Remember
                                                me</label>
                                        </div>
                                        <div class="form-group form-button">
                                            <input type="submit" name="signin" id="signin" class="form-submit"
                                                value="Log in" />
                                        </div>
                                    </form>
                                    <div class="social-login">
                                        <span class="social-label">Or login with</span>
                                        <ul class="socials">
                                            <li><a href="#"><i class="display-flex-center zmdi zmdi-facebook"></i></a>
                                            </li>
                                            <li><a href="#"><i class="display-flex-center zmdi zmdi-twitter"></i></a>
                                            </li>
                                            <li><a href="#"><i class="display-flex-center zmdi zmdi-google"></i></a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>

                <!-- JS -->
                <script src="vendors/jquery/jquery.min.js"></script>
            </body><!-- This templates was made by Colorlib (https://colorlib.com) -->

            </html>