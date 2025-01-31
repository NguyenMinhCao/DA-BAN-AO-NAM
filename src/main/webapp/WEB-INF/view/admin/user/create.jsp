<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <!-- Latest compiled JavaScript -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <!-- <link rel="stylesheet" href="/css/demo.css"> -->
            </head>

            <body class="sb-nav-fixed">

                <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 class="mt-4">Dashboard</h1>
                            <ol class="breadcrumb mb-4">
                                <li class="breadcrumb-item active">
                                    <a href="/admin" style="text-decoration: none;">Dashboard</a> / Create
                                </li>
                            </ol>
                            <div class="container mt-5">
                                <div class="row">
                                    <div class="col-md-6 col-12 mx-auto">
                                        <h3>Create User</h3>
                                        <hr>
                                        <form:form class="row g-3" method="post" action="/admin/user/create-user"
                                            modelAttribute="newUser">
                                            <div class="col-md-6">
                                                <label class="form-label">Email:</label>
                                                <form:input type="email" class="form-control" path="email" />
                                            </div>
                                            <div class="col-md-6">
                                                <label class="form-label">Password:</label>
                                                <form:input type="password" class="form-control" path="password" />
                                            </div>
                                            <div class="col-md-6">
                                                <label class="form-label">Phone number:</label>
                                                <form:input type="text" class="form-control" path="phoneNumber" />
                                            </div>
                                            <div class="col-md-6">
                                                <label class="form-label">Full Name:</label>
                                                <form:input type="text" class="form-control" path="fullName" />
                                            </div>
                                            <div class="col-md-6">
                                                <label class="form-label">Role:</label>
                                                <form:select class="form-select" aria-label="Default select example"
                                                    path="role.name">
                                                    <form:option value="ADMIN">Admin</form:option>
                                                    <form:option value="USER">User</form:option>
                                                </form:select>
                                            </div>
                                            <div class="col-md-12">
                                                <button type="submit" class="btn btn-primary">Create</button>
                                                <a style="margin-left: 10px;" class="btn btn-warning"
                                                    href="/admin/user">Back</a>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </main>
                </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
            </body>

            </html>