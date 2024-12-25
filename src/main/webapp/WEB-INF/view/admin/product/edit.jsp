<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>Update Product</title>
    <link href="/admin/css/styles.css" rel="stylesheet"/>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <style>


        .product-image {
            width: 200px;
            height: 200px;
            object-fit: cover;
        }

        #avatarPreviews img {
            width: 200px;
            height: 200px;
            object-fit: cover;

        }
    </style>
    <script>
        $(document).ready(() => {
            const avatarFiles = $("#avatarFiles");
            avatarFiles.change(function (e) {
                // Clear previous previews
                $("#avatarPreviews").empty();

                // Display preview for each file
                for (let i = 0; i < e.target.files.length; i++) {
                    const imgURL = URL.createObjectURL(e.target.files[i]);
                    const previewElement = $('<img />', {
                        src: imgURL,
                        class: 'preview-img',
                        style: 'max-height: 250px; margin: 5px; display: inline-block;',
                        alt: `preview ${i + 1}`
                    });
                    $("#avatarPreviews").append(previewElement);
                }
            });
        });
    </script>
    <script>
        // Validation for Quantity input
        function validateQuantity(input) {
            input.value = input.value.replace(/[^0-9]/g, '').replace(/^0+/, '');
        }
    </script>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Update Product</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"><a href="/admin" style="text-decoration: none;">Dashboard</a></li>
                    <li class="breadcrumb-item active">Update Product</li>
                </ol>
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h3>Update Product</h3>
                            <hr>

                            <!-- Display Error Message -->
                            <c:if test="${not empty errorMessage}">
                                <div class="alert alert-danger">
                                    <span>${errorMessage}</span>
                                </div>
                            </c:if>

                            <form:form  action="/admin/product/edit/${product.id}"  class="row g-3" method="post"
                                       modelAttribute="product" enctype="multipart/form-data">
                                <!-- Name -->
                                <div class="col-md-6">
                                    <label class="form-label">Name:</label>
                                    <form:input type="text"
                                                class="form-control ${newBindingResult.hasFieldErrors('name') ? 'is-invalid' : ''}"
                                                path="name"/>
                                    <form:errors path="name" cssClass="invalid-feedback"/>
                                </div>

                                <!-- Price -->
                                <div class="col-md-6">
                                    <label class="form-label">Price:</label>
                                    <form:input
                                            type="number"
                                            class="form-control ${newBindingResult.hasFieldErrors('price') ? 'is-invalid' : ''}"
                                            path="price"
                                            min="0"
                                            step="0.001"/>
                                    <form:errors path="price" cssClass="invalid-feedback"/>
                                </div>

                                <!-- Quantity -->
                                <div class="col-md-6">
                                    <label class="form-label">Quantity:</label>
                                    <form:input
                                            type="number"
                                            class="form-control ${newBindingResult.hasFieldErrors('quantity') ? 'is-invalid' : ''}"
                                            path="quantity"
                                            oninput="validateQuantity(this)"/>
                                    <form:errors path="quantity" cssClass="invalid-feedback"/>
                                </div>

                                <!-- Description -->
                                <div class="col-md-12">
                                    <label class="form-label">Description:</label>
                                    <form:textarea class="form-control" path="description" rows="3"></form:textarea>
                                </div>

                                <!-- Category -->
                                <div class="col-md-6">
                                    <label class="form-label">Category:</label>
                                    <form:select class="form-control" path="category">
                                        <form:options items="${categories}" itemValue="id" itemLabel="categoryName"/>
                                    </form:select>
                                </div>

                                <!-- Size -->
                                <div class="col-md-6">
                                    <label class="form-label">Size:</label>
                                    <form:select class="form-control" path="size">
                                        <form:options items="${sizes}" itemValue="id" itemLabel="sizeName"/>
                                    </form:select>
                                </div>

                                <!-- Color -->
                                <div class="col-md-6">
                                    <label class="form-label">Color:</label>
                                    <form:select class="form-control" path="color">
                                        <form:options items="${colors}" itemValue="id" itemLabel="colorName"/>
                                    </form:select>
                                </div>

                                <!-- Material -->
                                <div class="col-md-6">
                                    <label class="form-label">Material:</label>
                                    <form:select class="form-control" path="material">
                                        <form:options items="${materials}" itemValue="id" itemLabel="materialName"/>
                                    </form:select>
                                </div>

                                <div class="col-md-6">
                                    <label class="form-label">Origin:</label>
                                    <form:select class="form-control" path="origin">
                                        <form:options items="${origins}" itemValue="originId" itemLabel="originName"/>
                                    </form:select>
                                </div>

                                <!-- Pattern -->
                                <div class="col-md-6">
                                    <label class="form-label">Pattern:</label>
                                    <form:select class="form-control" path="pattern">
                                        <form:options items="${patterns}" itemValue="id" itemLabel="patternName"/>
                                    </form:select>
                                </div>

                                <!-- Avatar Display (Current Image) -->
                                <div class="col-md-12">
                                    <label for="avatarFiles" class="form-label">Current Avatar:</label>
                                    <c:if test="${not empty product.images}">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <h5>Images:</h5>
                                                <div class="product-image-container">
                                                    <c:forEach var="image" items="${product.images}">
                                                        <img src="${pageContext.request.contextPath}/images/product/${image.imageUrl}"
                                                             class="img-fluid product-image" alt="Product Image">
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>

                                <!-- Avatar Upload -->
                                <div class="col-md-12">
                                    <label for="avatarFiles" class="form-label">New Avatar (Optional):</label>
                                    <input class="form-control" type="file" id="avatarFiles" name="getImgFiles"
                                           accept=".png, .jpg, .jpeg" multiple/>
                                </div>

                                <!-- Avatar Previews -->
                                <div class="col-12 mb-3" id="avatarPreviews"></div>

                                <!-- Submit Button -->
                                <div class="col-md-12">
                                    <button type="submit" class="btn btn-primary">Update</button>
                                    <a class="btn btn-warning" href="/admin/product" style="margin-left: 10px;">Back</a>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp"/>
    </div>
</div>
</body>
</html>
