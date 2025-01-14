<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

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
                                    <a style="display: inline-block;padding-left: 9px;" class="nav-link" href="#">Tài
                                        Khoản
                                        Của Tôi</a>
                                </li>
                                <div class="nav-item-account" style="margin-left: 15px;">
                                    <li class="nav-item">
                                        <a class="nav-link nav-link-select" href="/user/profile">Hồ Sơ</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link nav-link-select" href="/user/address">Địa chỉ</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link nav-link-select" href="/user/change-pass">Đổi Mật Khẩu</a>
                                    </li>
                                </div>
                                <li class="nav-item">
                                    <i style="font-size: 17px;color: #0044ad;" class="ti-receipt"></i>
                                    <a style="display: inline-block;padding-left: 9px;" class="nav-link nav-link-select"
                                        href="/user/orders">Đơn
                                        mua</a>
                                </li>
                                <li class="nav-item">
                                    <i style="font-size: 17px;color: #0044ad;" class="ti-package"></i>
                                    <a style="display: inline-block;padding-left: 9px;" class="nav-link nav-link-select"
                                        href="#">Kho
                                        voucher</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>