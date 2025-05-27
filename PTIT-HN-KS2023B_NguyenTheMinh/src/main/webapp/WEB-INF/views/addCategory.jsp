<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Thêm danh mục</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
<h1>Thêm danh mục</h1>

<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>

<form:form method="POST" action="${pageContext.request.contextPath}/category/addCategory" modelAttribute="categoryDto">
    <table>
        <tr>
            <td>Tên danh mục:</td>
            <td>
                <form:input path="name" />
                <form:errors path="name" cssClass="error" />
            </td>
        </tr>
        <tr>
            <td>Mô tả:</td>
            <td>
                <form:textarea path="description" rows="4" cols="50" />
                <form:errors path="description" cssClass="error" />
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Thêm danh mục"/>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
