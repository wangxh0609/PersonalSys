<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.hust.docMgr.blog.domain.ArticleTypeBean" %>
<% ArrayList typelist=(ArrayList)session.getAttribute("artTypeList");
	String articlebasePath=request.getContextPath();
%>
<jsp:useBean id="modifySingle" class="com.hust.docMgr.blog.domain.ArticleBean" scope="request"/>
<html>
<head>
	<title>博客后台-修改文章</title>
	<script type="text/javascript" charset="utf-8" src="<%=articlebasePath %>/js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=articlebasePath %>/js/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="<%=articlebasePath %>/js/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
    <center>
        <table width="778" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="word-break:break-all">
            <tr><td colspan="2"><%@ include file="../view/AdminTop.jsp" %></td></tr>
            <tr>
                <td  valign="top"><jsp:include page="../view/AdminLeft.jsp"/></td>
                <td align="center" valign="top">
                	<form action="<%=basePath %>/sys/article_admin_articleModify" method="post">
                	<input type="hidden" name="type" value="doModify">
                	<input type="hidden" name="id" value="<%=modifySingle.getId() %>">
                	<table border="0" width="100%" cellspacing="0" cellpadding="8">
                		<tr height="30"><td colspan="2">【修改文章】</td></tr>
                		<tr>
                			<td align="center" width="20%">文章ID：</td>
                			<td><%=modifySingle.getId() %></td>
                		</tr>
                		<tr>
                			<td align="center">文章类别：</td>
                			<td>
                				<select name="articleBean.typeId" style="width:100">
                					<option value=""></option>
                					<% 
                						if(typelist!=null&&typelist.size()!=0){
                							for(int i=0;i<typelist.size();i++){
                								ArticleTypeBean typeBean=(ArticleTypeBean)typelist.get(i);
                								if(typeBean.getId()==modifySingle.getTypeId()){
                					%>
                					<option value="<%=typeBean.getId()%>" selected><%=typeBean.getTypeName() %></option>
                					<%
                								}
                								else{
                									
                					%>
                					<option value="<%=typeBean.getId()%>"><%=typeBean.getTypeName() %></option>
                					<%
                								}
                							}
                						}
                					%>
                				</select>
                			</td>
                		</tr>
                		<tr>
                			<td align="center">文章标题：</td>
                			<td><input type="text" name="articleBean.title" value="<%=modifySingle.getTitle() %>" size="77"></td>
                		</tr>
                		<tr>
                			<td align="center">文章来源：</td>
                			<td>
                				<select name="articleBean.create" style="width:100">
                					<option value=""/>
                					<%	if(modifySingle.getCreate().equals("原创")){ %>
                					<option value="原创" selected>原创</option>
                					<option value="摘自">摘自</option>
                					<%	
                						}
	                					if(modifySingle.getCreate().equals("摘自")){
	                				%>
	                				<option value="原创">原创</option>
                					<option value="摘自" selected>摘自</option>		
	                				<%	} %>
                				</select>
                			</td>
                		</tr>
	               		<tr>
                			<td align="center">文章描述：</td>
                			<td><input type="text" name="articleBean.info" value="<%=modifySingle.getInfo()%>" size="77"></td>
                		</tr>
                		<tr>
                			<td align="center" valign="top">文章内容：</td>
                			<td>
                				<script id="editor" type="text/plain" name="articleBean.content" style="width:600px;height:250px;"><%=modifySingle.getContent() %></script>
                				<!--  <textarea name="articleBean.content" rows="7" cols="65"></textarea>-->
                			</td>
                		</tr>
                		<tr height="25">
                			<td colspan="2" align="center">
                				<input type="submit" value="保存" class="btn_bg">
                				<input type="reset" value="重置" class="btn_bg">
                			</td>
                		</tr>
                	</table>
                	</form>
                </td>
            </tr>
            <tr><td colspan="2"><%@ include file="../view/AdminEnd.jsp" %></td></tr>
        </table>
    </center>
</body>
<script>
   		window.UEDITOR_HOME_URL = "<%=basePath%>js/ueditor/";
    	var ue = UE.getEditor('editor');
</script>
</html>