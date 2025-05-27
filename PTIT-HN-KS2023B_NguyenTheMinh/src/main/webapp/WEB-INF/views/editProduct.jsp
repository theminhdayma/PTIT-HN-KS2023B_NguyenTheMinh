<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Chỉnh sửa sản phẩm</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
<h1>Chỉnh sửa sản phẩm</h1>

<form:form method="POST"
           action="${pageContext.request.contextPath}/product/editProduct/${productId}"
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
                <form:textarea path="description" rows="4" cols="50"/>
                <form:errors path="description" cssClass="error"/>
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
            <td>Ảnh hiện tại:</td>
            <td>
                <c:if test="${not empty product.imageUrl}">
                    <img width="100" height="100" src="${pageContext.request.contextPath}/${product.imageUrl}" alt="Ảnh sản phẩm"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Chọn ảnh mới:</td>
            <td>
                <form:input path="imageUrl" type="file"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Cập nhật"/>
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>
