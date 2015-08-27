<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<html>
<body>
<h2>Hello World! <shiro:principal/>  ${basePath }</h2>  
<shiro:hasRole name="admin">123123</shiro:hasRole>
<shiro:hasPermission name="user"></shiro:hasPermission>
<a href="${basePath }logout.do">退出</a>
</body>
</html>
