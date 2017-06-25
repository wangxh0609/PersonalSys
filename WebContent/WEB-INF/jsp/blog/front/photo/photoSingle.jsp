<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.hust.docMgr.blog.domain.PhotoBean" %>
<html>
<head><title>博客-查看相片</title></head>
<body>
<%
   PhotoBean single=(PhotoBean)request.getAttribute("photoSingle");
   if(single==null)
	   out.print("查看图片失败！");
   else{
%>
   <img src="/upload<%=single.getPhotoAddr()%>" >
<% } %>
</body>
</html>