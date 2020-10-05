<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Excess</th>
    </tr>
    <c:forEach items="${listMealTo}" var="mealsTo">
        <tr style="color:${ (mealsTo.isExcess() == true ? 'green' : 'red')}">
            <td>${mealsTo.getDate()} ${mealsTo.getTime()}</td>
            <td>${mealsTo.getDescription() }</td>
            <td>${mealsTo.getCalories()}</td>
            <td>${mealsTo.isExcess()}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
