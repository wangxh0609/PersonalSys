<%@ page language="java" contentType="text/html; charset=gb2312"%>
<html>
<head>
	<title>博客后台首页</title>
</head>
<body>
    <center>
        <table border="1">
            <tr><td colspan="2"><%@ include file="/WEB-INF/jsp/blog/admin/view/AdminTop.jsp" %></td></tr>
            <tr>
                <td><jsp:include page="/WEB-INF/jsp/blog/admin/view/AdminLeft.jsp"/></td>
                <td>后台主页内容</td>
            </tr>
            <tr><td colspan="2"><%@ include file="/WEB-INF/jsp/blog/admin/view/AdminEnd.jsp" %></td></tr>
        </table>
    </center>
</body>
</html>