<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.hust.docMgr.blog.domain.ArticleTypeBean" %>
<% ArrayList typelist=(ArrayList)session.getAttribute("artTypeList"); %>
<%
	String basePath=request.getContextPath();
%>
<html>
<head>
	<title>博客后台-发表文章</title>
	 <script type="text/javascript" charset="utf-8" src="<%=basePath %>/js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>/js/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>/js/ueditor/lang/zh-cn/zh-cn.js"></script>
    
</head>
<body background="<%=basePath%>/images/blog/bg_addArticle.jpg">
    <center>
        <table width="778" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="word-break:break-all">
            <tr><td colspan="2"></td></tr>
            <tr>
                <td valign="top"></td>
                <td align="center" valign="top">
                	<form action="<%=basePath %>/sys/article_admin_addArticle" method="post">
                	<!--<input type="hidden" name="action" value="add">  -->
                	<table border="0" width="100%" cellspacing="0" cellpadding="8">
                		<tr height="50"><td colspan="2">【发表文章】</td></tr>
                		<tr>
                			<td align="center" width="20%">文章类别：</td>
                			<td>
                				<select name="articleBean.typeId" style="width:100">
                					<option value=""/>
                					<% 
                						if(typelist!=null&&typelist.size()!=0){
                							for(int i=0;i<typelist.size();i++){
                								ArticleTypeBean typeBean=(ArticleTypeBean)typelist.get(i);
                					%>
                					<option value="<%=typeBean.getId()%>"><%=typeBean.getTypeName() %></option>
                					<%
                							}
                						}
                					%>
                				</select>
                			</td>
                		</tr>
                		<tr>
                			<td align="center">文章标题：</td>
                			<td><input type="text" name="articleBean.title" size="20"></td>
                		</tr>
                		<tr>
                			<td align="center">文章来源：</td>
                			<td>
                				<select name="articleBean.create" style="width:100">
                					<option value=""/>
                					<option value="原创">原创</option>
                					<option value="摘自">摘自</option>
                				</select>
                			</td>
                		</tr>
	               		<tr>
                			<td align="center">文章描述：</td>
                			<td><input type="text" name="articleBean.info" size="20"></td>
                		</tr>
                		<tr>
                			<td align="center" valign="top">文章内容：</td>
                			<td> 
                			<script id="editor" type="text/plain" name="articleBean.content" style="width:600px;height:250px;"></script>               		                		
                			</td>
                		</tr>
                		<tr height="30">
                			<td colspan="2" align="center">
                				<input type="submit" class="btn_bg" value="保存">
                				<input type="reset"  class="btn_bg" value="重置">
                			</td>
                		</tr>
                	</table>
                	</form>
                </td>
            </tr>
            <tr><td colspan="2"></td></tr>
        </table>
    </center>
</body>

<script>
   		window.UEDITOR_HOME_URL = "<%=basePath%>js/ueditor/";
    	var ue = UE.getEditor('editor');
</script>
</html>