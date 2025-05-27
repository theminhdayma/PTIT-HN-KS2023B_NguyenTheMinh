<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Danh sách sản phẩm</title>
</head>
<body>
<h1>Danh sách sản phẩm</h1>
<form action="${pageContext.request.contextPath}/product/searchProduct" method="get">
    <input type="text" name="keyword" placeholder="Tìm kiếm sản phẩm..." />
    <button type="submit">Tìm kiếm</button>
</form>
<a href="${pageContext.request.contextPath}/product/addProduct">Thêm mới</a>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên sản phẩm</th>
        <th>Mô tả</th>
        <th>Giá</th>
        <th>Ảnh</th>
        <th>Mã danh mục</th>
        <th>Ngày tạo</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>${product.price}</td>
            <td>
                <c:if test="${not empty product.imageUrl}">
                    <img width="100" height="100" class="product-img" src="${pageContext.request.contextPath}/${product.imageUrl}" alt="${product.name}"/>
                </c:if>
                <c:if test="${empty product.imageUrl}">
                    Không có ảnh
                </c:if>
            </td>
            <td>
                ${product.categoryId}
            </td>
            <td>
                ${product.createdAt}
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/product/editProduct/${product.id}">Sửa</a> |
                <a href="${pageContext.request.contextPath}/product/deleteProduct/${product.id}"
                   onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?');">Xóa</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
