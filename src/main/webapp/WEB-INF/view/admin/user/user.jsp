<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/admin/css/user.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/user.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="sidebar">
    <jsp:include page="/WEB-INF/view/admin/layout/sidebar.jsp"></jsp:include>
</div>

<div style="margin-left: 260px; padding: 20px;">
    <h2>User List</h2>

    <!-- Hiển thị thông báo thành công hoặc thất bại bằng SweetAlert2 -->
    <c:if test="${not empty successMessage}">
        <script type="text/javascript">
            Swal.fire({
                icon: 'success',
                title: 'Thành công',
                text: '${successMessage}',
                showConfirmButton: false,
                timer: 1500
            });
        </script>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <script type="text/javascript">
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: '${errorMessage}',
                showConfirmButton: false,
                timer: 1500
            });
        </script>
    </c:if>

    <!-- Tìm kiếm người dùng -->
    <input type="text" id="searchInput" onkeyup="searchTable()" placeholder="Search for names, emails, etc." style="margin-bottom: 20px; padding: 8px; width: 300px;">

    <!-- Bảng hiển thị người dùng -->
    <table class="table" id="userTable">
        <thead>
        <tr>
            <th scope="col">STT</th>
            <th scope="col">Tên</th>
            <th scope="col">Email</th>
            <th scope="col">SĐT</th>
            <th scope="col">Ảnh</th>
            <th scope="col">Địa Chỉ</th>
            <th scope="col">Chức Vụ</th>
            <th scope="col">Trạng Thái</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listUser}" var="us" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td>${us.fullName}</td>
                <td>${us.email}</td>
                <td>${us.phoneNumber}</td>
                <td>
                    <c:if test="${not empty us.avatar}">
                        <img src="${pageContext.request.contextPath}/uploads/${us.avatar}" alt="Avatar" class="rounded-circle" width="50" height="50"/>
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty us.address}">
                        <c:forEach items="${us.address}" var="addr">
                            <p>${addr.fullName}</p>
                        </c:forEach>
                    </c:if>
                </td>
                <td>${us.role.name}</td>
                <td>
                    <c:choose>
                        <c:when test="${us.address[0].status == true}">Đang làm</c:when>
                        <c:otherwise>Đã nghỉ</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a href="/user/update/${us.id}">Update</a> |
                    <!-- Form xử lý xóa -->
                    <form id="removeForm-${us.id}" action="${pageContext.request.contextPath}/admin/user/remove" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${us.id}" />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> <!-- CSRF Token -->
                        <button type="button" onclick="confirmDelete(${us.id})" style="border:none; background:none; color:blue; text-decoration:underline; cursor:pointer;">Remove</button>
                    </form> |
                    <a href="/user/detail/${us.id}">Detail</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script type="text/javascript">
    function confirmDelete(userId) {
        Swal.fire({
            title: 'Bạn có chắc chắn muốn xóa người dùng này?',
            text: "Bạn sẽ không thể khôi phục lại thông tin người dùng!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xóa',
            cancelButtonText: 'Hủy'
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById('removeForm-' + userId).submit();
            }
        });
    }
</script>
</body>
</html>
