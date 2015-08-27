<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <%  
session.invalidate();  
response.sendRedirect("http://localhost:8088/cas/logout?service=http://localhost:8088/com_web");  
%> 