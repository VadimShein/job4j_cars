<%@ page language="java" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <script src="indexTable.js"></script>
</head>
<body>
<div class="container">
    <ul class="nav">
        <c:if test="${user.name == null}">
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="login.jsp"/>">Войти</a>
            </li>
        </c:if>
        <c:if test="${user.name != null}">
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="logout.do"/>"> <c:out value="${user.name}"/> | Выйти</a>
            </li>
        </c:if>
    </ul>
</div>
<div class="container">
    <h2>Загрузка фото</h2>
    <form action="<c:url value='/upload.do'/>" method="post" enctype="multipart/form-data">
        <div class="checkbox">
    <input type="file" name="<c:out value="${param.itemId}"/>" title="Фото не выбрано" class="downloadedPhoto"/>
        </div>
        <button type="submit" class="btn btn-primary" onclick="return validatePhoto()">Загрузить</button>
    </form>
</div>
</body>
</html>