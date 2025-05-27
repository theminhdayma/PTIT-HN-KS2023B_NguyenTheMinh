<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Chỉnh sửa danh mục</title>
</head>
<body>
<h1>Chỉnh sửa danh mục</h1>

<c:if test="${not empty error}">
    <div style="color: red;">${error}</div>
</c:if>

<form:form method="POST" action="${pageContext.request.contextPath}/category/editCategory/${categoryId}" modelAttribute="categoryDto">
    <table>
        <tr>
            <td>Tên danh mục:</td>
            <td>
                <form:input path="name" />
                <form:errors path="name" cssStyle="color: red;" />
            </td>
        </tr>
        <tr>
            <td>Mô tả:</td>
            <td>
                <form:textarea path="description" rows="4" cols="50" />
                <form:errors path="description" cssStyle="color: red;" />
            </td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Cập nhật danh mục"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
