<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Dashboard - SB Admin</title>
    <link href="/admin/css/styles.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(() => {
            const avatarFile = $("#avatarFile");
            avatarFile.change(function (e) {
                const imgURL = URL.createObjectURL(e.target.files[0]);
                $("#avatarPreview").attr("src", imgURL);
                $("#avatarPreview").css({ "display": "block" });
            });
        });
    </script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <!-- <link rel="stylesheet" href="/css/demo.css"> -->
</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />

    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Dashboard</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item active">
                        <a href="/admin" style="text-decoration: none;">Dashboard</a> / Update
                    </li>
                </ol>
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h3>Detail promotions</h3>
                            <hr>
                            <form modelAttribute="promo" action="/khuyen-mai/update/${promo.id}" method="post">
                                <div class="form-group">
                                    <b class="bi bi-person"> <label>Discount Type</label></b>
                                    <input type="text" name="discountType" class="form-control" value="${promo.discountType}">
                                    <%--                                    <form:errors path="discountType"/>--%>
                                </div>
                                <br>
                                <div class="form-group">
                                    <b class="bi bi-key-fill"> <label>Start Date</label></b>
                                    <input type="text" name="startDate" class="form-control" value="${promo.startDate}">
                                    <%--                                    <form:errors path="startDate"/>--%>
                                </div>
                                <br>
                                <div class="form-group">
                                    <b class="bi bi-person-arms-up"> <label>End Date</label></b>
                                    <input type="text" name="endDate" class="form-control" value="${promo.endDate}" >
                                    <%--                                    <form:errors path="endDate"/>--%>
                                </div>
                                <div class="form-group">
                                    <b class="bi bi-person-arms-up"> <label>Promtion code</label></b>
                                    <input type="text" name="promotionCode" class="form-control" value="${promo.promotionCode}" >
                                    <%--                                    <form:errors path="promotionCode"/>--%>
                                </div>
                                <br>
                                <br>
                                <div class="mb-3">
                                    <b class="bi bi-gender-ambiguous"><label>Status</label></b>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="status" value="true" ${promo.status == "true" ? "checked" : ""}>
                                        <label class="form-check-label">Hoạt động</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="status" value="false" ${promo.status == "false" ? "checked" : ""}>
                                        <label class="form-check-label">Không hoạt động</label>
                                    </div>
                                </div>
                                <br>
                                <button type="submit" class="btn btn-primary">Update</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp" />
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
</body>

</html>