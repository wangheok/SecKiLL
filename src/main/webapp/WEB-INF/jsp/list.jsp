<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--Import JSTL--%>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>SecKill List Page</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>SecKill List</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Product Name</th>
                    <th>In Stock</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Create Time</th>
                    <th>Detail</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="secKill" items="${secKillList}">
                    <tr>
                        <td>${secKill.name}</td>
                        <td>${secKill.number}</td>
                        <td>
                            <fmt:formatDate value="${secKill.startTime}"
                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${secKill.endTime}"
                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${secKill.createTime}"
                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <a class="btn btn-info" href="/seckill/${secKill.secKillId}/detail" target="_blank">Detail</a>
                        </td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
<!-- Import jQuery before importing bootstrap.min.js -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<!-- Latest Bootstrap core JavaScript file -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</html>
