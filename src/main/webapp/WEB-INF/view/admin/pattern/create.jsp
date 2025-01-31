<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <title>Create Color</title>
    <link href="/admin/css/styles.css" rel="stylesheet" />
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="/common/toast.css">


</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4 text-dark">Thêm size</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"><a href="/admin"
                                                   class="text-decoration-none">Dashboard</a></li>
                    <li class="breadcrumb-item active">Thêm thương hiệu</li>
                </ol>
                <div class="container mt-5">
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="card shadow-sm">
                                <div class="card-header">
                                    <h3 class="mb-0">Thêm thương hiệu</h3>
                                </div>
                                <div class="card-body">
                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-danger">
                                            <span>${errorMessage}</span>
                                        </div>
                                    </c:if>

                                    <form:form method="post" action="/admin/pattern/create"
                                               modelAttribute="newPattern" enctype="multipart/form-data">
                                        <div class="mb-3">
                                            <label class="form-label"> :</label>
                                            <form:input type="text"
                                                        class="form-control ${newBindingResult.hasFieldErrors('patternName') ? 'is-invalid' : ''}"
                                                        path="patternName" placeholder="Enter " />
                                            <form:errors path="patternName" cssClass="invalid-feedback" />
                                        </div>

                                        <div class="d-flex justify-content-between">
                                            <button type="submit" class="btn btn-primary"
                                                    id="confirmSaveButton">
                                                <i class="fas fa-save"></i> Save
                                            </button>
                                            <a href="/admin/pattern" class="btn btn-warning">
                                                <i class="fas fa-arrow-left"></i> Back
                                            </a>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp" />
        <div id="toast"></div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>
<script src="/common/toast.js"></script>
</body>

</html>