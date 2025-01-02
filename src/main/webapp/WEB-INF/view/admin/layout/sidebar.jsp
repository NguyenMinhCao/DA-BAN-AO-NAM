<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Core</div>
                            <a class="nav-link" href="/admin/order/order-statistics">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Dashboard
                            </a>
                            <div class="sb-sidenav-menu-heading">Quản lý</div>

                            <!-- Order -->
                            <a class="nav-link collapsed" href="/admin/order" data-bs-target="#collapseLayoutsProduct"
                                aria-expanded="false" aria-controls="collapseLayoutsProduct">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Bán hàng tại quầy
                            </a>

                            <!-- Sản phẩm -->
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse"
                                data-bs-target="#collapseLayoutsProduct" aria-expanded="false"
                                aria-controls="collapseLayoutsProduct">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Quản lý sản phẩm
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayoutsProduct" aria-labelledby="headingOne"
                                data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="/admin/product">Danh sách</a>
                                    <a class="nav-link" href="/admin/product/create">Thêm sản phẩm</a>
                                </nav>
                            </div>

                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse"
                                data-bs-target="#collapseLayoutsNhanVien" aria-expanded="false"
                                aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Quản lý nhân viên
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayoutsNhanVien" aria-labelledby="headingOne"
                                data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="layout-static.html">Danh sách</a>
                                    <a class="nav-link" href="layout-sidenav-light.html">Thêm sản phẩm</a>
                                </nav>
                            </div>

                            <a class="nav-link collapsed" href="/admin/color" data-bs-target="#collapseLayoutsProduct"
                               aria-expanded="false" aria-controls="collapseLayoutsProduct">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Quản lý màu sắc
                            </a>
                            <a class="nav-link collapsed" href="/admin/size" data-bs-target="#collapseLayoutsProduct"
                               aria-expanded="false" aria-controls="collapseLayoutsProduct">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Quản lý kích cỡ
                            </a>
                            <a class="nav-link collapsed" href="/admin/origin" data-bs-target="#collapseLayoutsProduct"
                               aria-expanded="false" aria-controls="collapseLayoutsProduct">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Quản lý nguồn gốc
                            </a>
                            <a class="nav-link collapsed" href="/admin/category" data-bs-target="#collapseLayoutsProduct"
                               aria-expanded="false" aria-controls="collapseLayoutsProduct">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Quản lý danh mục
                            </a>
                            <a class="nav-link collapsed" href="/admin/material" data-bs-target="#collapseLayoutsProduct"
                               aria-expanded="false" aria-controls="collapseLayoutsProduct">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Quản lý chất liệu
                            </a>
                            <!-- Khuyến mại -->
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse"
                               data-bs-target="#collapseLayoutsPromotions" aria-expanded="false"
                               aria-controls="collapseLayoutsPromotions">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"> </i></div>
<%--                                <a href="/admin/promotions/show.jsp"></a>--%>
                                Quản lý khuyến mại
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayoutsPromotions" aria-labelledby="headingOne"
                                 data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="/hien-thiKM">Danh sách</a>
                                    <a class="nav-link" href="/view-add">Thêm khuyến mại</a>
                                </nav>
                            </div>

                            <!-- ..... -->
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse"
                                data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
                                <div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                                Quản lý khách hàng
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                                data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse"
                                        data-bs-target="#pagesCollapseAuth" aria-expanded="false"
                                        aria-controls="pagesCollapseAuth">
                                        Authentication
                                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                                    </a>
                                    <div class="collapse" id="pagesCollapseAuth" aria-labelledby="headingOne"
                                        data-bs-parent="#sidenavAccordionPages">
                                        <nav class="sb-sidenav-menu-nested nav">
                                            <a class="nav-link" href="login.html">Login</a>
                                            <a class="nav-link" href="register.html">Register</a>
                                            <a class="nav-link" href="password.html">Forgot Password</a>
                                        </nav>
                                    </div>
                                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse"
                                        data-bs-target="#pagesCollapseError" aria-expanded="false"
                                        aria-controls="pagesCollapseError">
                                        Error
                                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                                    </a>
                                    <div class="collapse" id="pagesCollapseError" aria-labelledby="headingOne"
                                        data-bs-parent="#sidenavAccordionPages">
                                        <nav class="sb-sidenav-menu-nested nav">
                                            <a class="nav-link" href="401.html">401 Page</a>
                                            <a class="nav-link" href="404.html">404 Page</a>
                                            <a class="nav-link" href="500.html">500 Page</a>
                                        </nav>
                                    </div>
                                </nav>
                            </div>
                            <div class="sb-sidenav-menu-heading">Addons</div>
                            <a class="nav-link" href="charts.html">
                                <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                                Charts
                            </a>
                            <a class="nav-link" href="tables.html">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Tables
                            </a>
                        </div>

                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Logged in as:</div>
                        Start Bootstrap
                    </div>
                </nav>
            </div>
        </div>