<%--<%@page contentType="text/html" pageEncoding="UTF-8" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
<%--    <meta charset="utf-8"/>--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>--%>
<%--    <title>Create Product</title>--%>
<%--    <link href="/admin/css/styles.css" rel="stylesheet"/>--%>
<%--    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">--%>
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>--%>
<%--    <style>--%>
<%--        #avatarPreviews img {--%>
<%--            width: 200px;--%>
<%--            height: 200px;--%>
<%--            object-fit: cover;--%>
<%--            margin-right: 10px;--%>
<%--        }--%>
<%--    </style>--%>
<%--    <script>--%>
<%--        $(document).ready(() => {--%>
<%--            const avatarFiles = $("#avatarFiles");--%>
<%--            avatarFiles.change(function (e) {--%>
<%--                $("#avatarPreviews").empty();--%>
<%--                for (let i = 0; i < e.target.files.length; i++) {--%>
<%--                    const imgURL = URL.createObjectURL(e.target.files[i]);--%>
<%--                    const previewElement = $('<img />', {--%>
<%--                        src: imgURL,--%>
<%--                        class: 'preview-img',--%>
<%--                        style: 'max-height: 250px; margin: 5px; display: inline-block;',--%>
<%--                        alt: `preview ${i + 1}`--%>
<%--                    });--%>
<%--                    $("#avatarPreviews").append(previewElement);--%>
<%--                }--%>
<%--            });--%>
<%--        });--%>
<%--    </script>--%>
<%--    <script>--%>
<%--        function validateQuantity(input) {--%>
<%--            input.value = input.value.replace(/[^0-9]/g, '').replace(/^0+/, '');--%>
<%--        }--%>
<%--    </script>--%>
<%--    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>--%>
<%--</head>--%>
<%--<body class="sb-nav-fixed">--%>
<%--<jsp:include page="../layout/header.jsp"/>--%>
<%--<div id="layoutSidenav">--%>
<%--    <jsp:include page="../layout/sidebar.jsp"/>--%>
<%--    <div id="layoutSidenav_content">--%>
<%--        <main>--%>
<%--            <div class="container-fluid`1` px-4">--%>
<%--                <h1 class="mt-4">Create Product</h1>--%>
<%--                <ol class="breadcrumb mb-4">--%>
<%--                    <li class="breadcrumb-item"><a href="/admin" style="text-decoration: none;">Dashboard</a></li>--%>
<%--                    <li class="breadcrumb-item active">Create Product</li>--%>
<%--                </ol>--%>
<%--                <div class="container mt-5">--%>
<%--                    <div class="row">--%>
<%--                        <div class="col-md-6 col-12 mx-auto">--%>
<%--                            <h3>Create Product</h3>--%>
<%--                            <hr>--%>

<%--                            <c:if test="${not empty errorMessage}">--%>
<%--                                <div class="alert alert-danger">--%>
<%--                                    <span>${errorMessage}</span>--%>
<%--                                </div>--%>
<%--                            </c:if>--%>

<%--                            <form:form class="row g-3" method="post" action="/admin/product/create"--%>
<%--                                       modelAttribute="newProduct" enctype="multipart/form-data">--%>
<%--                                <div class="col-md-6">--%>
<%--                                    <label class="form-label">Name:</label>--%>
<%--                                    <form:input type="text"--%>
<%--                                                class="form-control ${newBindingResult.hasFieldErrors('name') ? 'is-invalid' : ''}"--%>
<%--                                                path="name"/>--%>
<%--                                    <form:errors path="name" cssClass="invalid-feedback"/>--%>
<%--                                </div>--%>

<%--                                <div class="col-md-6">--%>
<%--                                    <label class="form-label">Price:</label>--%>
<%--                                    <form:input--%>
<%--                                            type="number"--%>
<%--                                            class="form-control ${newBindingResult.hasFieldErrors('price') ? 'is-invalid' : ''}"--%>
<%--                                            path="price"--%>
<%--                                            min="0"--%>
<%--                                            step="0.001"/>--%>
<%--                                    <form:errors path="price" cssClass="invalid-feedback"/>--%>
<%--                                </div>--%>

<%--                                <div class="col-md-6">--%>
<%--                                    <label class="form-label">Quantity:</label>--%>
<%--                                    <form:input--%>
<%--                                            type="number"--%>
<%--                                            class="form-control ${newBindingResult.hasFieldErrors('quantity') ? 'is-invalid' : ''}"--%>
<%--                                            path="quantity"--%>

<%--                                            oninput="validateQuantity(this)"/>--%>
<%--                                    <form:errors path="quantity" cssClass="invalid-feedback"/>--%>
<%--                                </div>--%>


<%--                                <div class="col-md-12">--%>
<%--                                    <label class="form-label">Description:</label>--%>
<%--                                    <form:textarea class="form-control" path="description" rows="3"></form:textarea>--%>
<%--                                </div>--%>

<%--                                <div class="col-md-6">--%>
<%--                                    <label class="form-label">Category:</label>--%>
<%--                                    <form:select class="form-control" path="category">--%>
<%--                                        <form:options items="${categories}" itemValue="id" itemLabel="categoryName"/>--%>
<%--                                    </form:select>--%>
<%--                                </div>--%>

<%--                                <div class="col-md-6">--%>
<%--                                    <label class="form-label">Size:</label>--%>
<%--                                    <form:select class="form-control" path="size">--%>
<%--                                        <form:options items="${sizes}" itemValue="id" itemLabel="sizeName"/>--%>
<%--                                    </form:select>--%>
<%--                                </div>--%>

<%--                                <div class="col-md-6">--%>
<%--                                    <label class="form-label">Color:</label>--%>
<%--                                    <form:select class="form-control" path="color">--%>
<%--                                        <form:options items="${colors}" itemValue="id" itemLabel="colorName"/>--%>
<%--                                    </form:select>--%>
<%--                                </div>--%>

<%--                                <div class="col-md-6">--%>
<%--                                    <label class="form-label">Material:</label>--%>
<%--                                    <form:select class="form-control" path="material">--%>
<%--                                        <form:options items="${materials}" itemValue="id" itemLabel="materialName"/>--%>
<%--                                    </form:select>--%>
<%--                                </div>--%>
<%--                                <div class="col-md-6">--%>
<%--                                    <label class="form-label">Origin:</label>--%>
<%--                                    <form:select class="form-control" path="origin">--%>
<%--                                        <form:options items="${origins}" itemValue="originId" itemLabel="originName"/>--%>
<%--                                    </form:select>--%>
<%--                                </div>--%>

<%--                                <div class="col-md-6">--%>
<%--                                    <label class="form-label">Pattern:</label>--%>
<%--                                    <form:select class="form-control" path="pattern">--%>
<%--                                        <form:options items="${patterns}" itemValue="id" itemLabel="patternName"/>--%>
<%--                                    </form:select>--%>
<%--                                </div>--%>

<%--                                <div class="col-md-12">--%>
<%--                                    <label for="avatarFiles" class="form-label">Avatar:</label>--%>
<%--                                    <input class="form-control" type="file" id="avatarFiles" name="getImgFiles"--%>
<%--                                           accept=".png, .jpg, .jpeg" multiple/>--%>
<%--                                </div>--%>

<%--                                <div class="col-12 mb-3" id="avatarPreviews"></div>--%>

<%--                                <div class="col-md-12">--%>
<%--                                    <button type="submit" class="btn btn-primary">Create</button>--%>
<%--                                    <a class="btn btn-warning" href="/admin/product" style="margin-left: 10px;">Back</a>--%>
<%--                                </div>--%>
<%--                            </form:form>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </main>--%>
<%--        <jsp:include page="../layout/footer.jsp"/>--%>
<%--    </div>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
