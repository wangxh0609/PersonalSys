<%@ page language="java" contentType="text/html; charset=gb2312"%>
<html>
<head>
	<title>博客-查看博主</title>
</head>
<body>
    <center>
        <table border="1">
            <tr><td colspan="2"><%@ include file="/WEB-INF/jsp/blog/front/view/FrontTop.jsp" %></td></tr>
            <tr>
                <td><jsp:include page="/WEB-INF/jsp/blog/front/view/FrontLeft.jsp"/></td>
                <td>浏览博主信息</td>
            </tr>
            <tr><td colspan="2"><%@ include file="/WEB-INF/jsp/blog/front/view/FrontEnd.jsp" %></td></tr>
        </table>
    </center>
</body>
</html>