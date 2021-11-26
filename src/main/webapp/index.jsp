<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <script src="indexTable.js"></script>

    <title>Cars sale</title>
</head>
<body onload="getItems('<c:out value="${user.id}"/>')">
<div class="container">
    <div class="row">
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
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <h3 id="header"></h3>
            </div>
            <div class="card-body">
                <form action="<c:url value="addItem.jsp"/>">
                    <div class="form-group row">
                        <div class="col-sm-5">
                            <button type="submit" class="btn btn-primary">Добавить объявление</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-body">
                <input type="checkbox" id="allItemsSelector" onclick="getItems('<c:out value="${user.id}"/>')"><label>&nbsp Все объявления</label>
                <input type="checkbox" id="withPhotoSelector" onclick="getItems('<c:out value="${user.id}"/>')" style="margin-left: 20px"><label>&nbsp Только с фото</label>
                <select id="sortSelector" onchange="getItems('<c:out value="${user.id}"/>')" style="margin-left: 20px">
                    <option selected value="date">Сортировать по дате</option>
                    <option value="mark">Сортировать по марке</option>
                    <option value="bodyType">Сортировать по типу кузова</option>
                    <option value="price">Сортировать по стоимости</option>
                </select>
                <table class="table table-bordered" style="table-layout: fixed">
                    <thead>
                    <tr>
                        <th style="text-align: center; width: 40px;">№</th>
                        <th style="text-align: center; width: 170px">Фото</th>
                        <th style="text-align: center">Марка</th>
                        <th style="text-align: center">Модель</th>
                        <th style="text-align: center">Тип кузова</th>
                        <th style="text-align: center; width: 20%">Описание</th>
                        <th style="text-align: center">Цена</th>
                        <th style="text-align: center">Дата</th>
                        <th style="text-align: center">Автор</th>
                        <th style="text-align: center">Статус</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>