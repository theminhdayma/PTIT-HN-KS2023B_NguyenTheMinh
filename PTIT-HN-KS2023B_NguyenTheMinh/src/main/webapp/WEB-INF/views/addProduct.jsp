<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Thêm sản phẩm</title>
</head>
<body>
<h1>Thêm sản phẩm</h1>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<form:form method="POST" action="${pageContext.request.contextPath}/product/addProduct"
           modelAttribute="productDto" enctype="multipart/form-data">
    <table>
        <tr>
            <td>Tên sản phẩm:</td>
            <td>
                <form:input path="name"/>
                <form:errors path="name" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td>Giá:</td>
            <td>
                <form:input path="price"/>
                <form:errors path="price" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td>Mô tả:</td>
            <td>
                <form:textarea path="description"/>
            </td>
        </tr>
        <tr>
            <td>Trạng thái:</td>
            <td>
                <form:select path="status">
                    <form:options items="${statusList}" />
                </form:select>
                <form:errors path="status" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td>Danh mục:</td>
            <td>
                <form:select path="categoryId">
                    <form:options items="${categories}" itemValue="id" itemLabel="name"/>
                </form:select>
                <form:errors path="categoryId" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td>Ảnh:</td>
            <td>
                <form:input path="imageUrl" type="file"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Thêm sản phẩm"/>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
