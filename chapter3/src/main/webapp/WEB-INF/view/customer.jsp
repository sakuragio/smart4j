<%--
  Created by IntelliJ IDEA.
  User: wangteng
  Date: 2019/5/30
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>客户列表</title>
</head>
<body>
    <h1>客户列表</h1>
    <h3>${BASE}</h3>
    <table>
        <thead>
            <tr>
                <th>客户名称</th>
                <th>联系人</th>
                <th>联系电话</th>
                <th>email</th>
                <th>备注</th>
                <th>操作</th>

            </tr>
        </thead>
        <tbody>
            <c:forEach items="${customerList}" var="customer">
                <tr>
                    <td>${customer.name}</td>
                    <td>${customer.contact}</td>
                    <td>${customer.telephone}</td>
                    <td>${customer.email}</td>
                    <td>${customer.remark}</td>
                    <td>
                        <a href="javascript:;">编辑</a>
                        <a href="javascript:;">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>


</body>
</html>
