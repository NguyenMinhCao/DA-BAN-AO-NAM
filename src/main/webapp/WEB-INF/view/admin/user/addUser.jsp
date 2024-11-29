<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add User</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/admin/css/user.css"> <!-- Liên kết đến CSS -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/user.js"></script> <!-- Liên kết đến JS -->
</head>
<body class="container">
<div class="sidebar">
    <jsp:include page="/WEB-INF/view/admin/layout/sidebar.jsp"></jsp:include>
</div>

<div class="content-container">
    <form modelAttribute="user" action="${pageContext.request.contextPath}/admin/user/add" method="post" class="add-user-form" enctype="multipart/form-data">
        <div class="form-section">
            <h3>Thông Tin Người Dùng</h3>
            <div class="form-group">
                <label for="fullName">Tên:</label>
                <input type="text" name="fullName" class="form-control" id="fullName"/>
            </div>

            <div class="form-group">
                <label for="password">Mật Khẩu:</label>
                <input type="password" name="password" class="form-control" id="password"/>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" name="email" class="form-control" id="email"/>
            </div>
            <div class="form-group">
                <label for="phoneNumber">SĐT:</label>
                <input type="text" name="phoneNumber" class="form-control" id="phoneNumber"/>
            </div>
        </div>

        <div class="form-section">
            <h3>Thông Tin Khác</h3>
            <div class="form-group">
                <label for="avatar">Ảnh Đại Diện:</label>
                <input type="file" name="avatarFile" class="form-control-file" id="avatar"/>
            </div>
            <div class="form-group">
                <label for="address">Địa Chỉ:</label>
                <input type="text" name="address" class="form-control" id="address"/>
            </div>
            <div class="form-group">
                <label for="role">Chức Vụ:</label>
                <select name="role" class="form-control" id="role">
                    <c:forEach var="role" items="${listRoles}">
                        <option value="${role.id}">${role.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="status">Trạng Thái:</label>
                <select name="status" class="form-control" id="status">
                    <option value="true" ${user.address.status == true ? 'selected' : ''}>Đang Làm</option>
                    <option value="false" ${user.address.status == false ? 'selected' : ''}>Đã Nghỉ</option>
                </select>
            </div>
        </div>

        <div class="form-action">
            <button type="submit" class="btn btn-primary">Thêm Người Dùng</button>
        </div>
    </form>
</div>

</body>
</html>
