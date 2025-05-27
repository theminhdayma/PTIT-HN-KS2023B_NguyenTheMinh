
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Danh sách danh mục</title>
</head>
<body>
<h1>Danh sách danh mục</h1>
<c:if test="${not empty error}">
    <div style="color: red; font-weight: bold;">
            ${error}
    </div>
</c:if>

<form action="${pageContext.request.contextPath}/category/searchCategory" method="get">
    <input type="text" name="keyword" placeholder="Tìm kiếm danh mục..." />
    <button type="submit">Tìm kiếm</button>
</form>
<a href="${pageContext.request.contextPath}/category/addCategory">Thêm mới</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Mô tả</th>
        <th>Thao tác</th>
    </tr>

    <c:choose>
        <c:when test="${not empty categories}">
            <c:forEach var="category" items="${categories}">
                <tr>
                    <td>${category.id}</td>
                    <td>${category.name}</td>
                    <td>${category.description}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/category/editCategory/${category.id}">Chỉnh sửa</a> |
                        <a href="${pageContext.request.contextPath}/category/deleteCategory/${category.id}"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?');">Xoá</a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>

        <c:otherwise>
            <tr>
                <td colspan="4" style="text-align: center; color: red;">Danh sách danh mục trống</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>
</body>
</html>
