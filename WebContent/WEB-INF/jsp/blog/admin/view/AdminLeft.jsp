<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
	String basePath=request.getContextPath();
%>
<html>
    <head>
    	<title>博客后台-侧栏</title>
    </head>
<body>
    <table width="193" height="401" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
        <tr>
        	<td width="191" height="401" valign="top">
        		<table width="100%" border="0" cellpadding="0" cellspacing="0">
        			<tr><td height="32" colspan="4" align="center" background="images/blog/adminLmenu_1.jpg" class="word_menuHead">文章管理</td></tr>
        			<tr valign="bottom"><td height="27" colspan="4" bgcolor="#33FF33" style="text-indent:30" class="tableBorder_B"><a href="<%=basePath%>/sys/article_admin_articleAdd">★发表文章</a></td>
        			</tr>
        			<tr valign="bottom"><td height="27" colspan="4" bgcolor="#33FF33" style="text-indent:30"><a href="<%=basePath%>/sys/article_admin_articleList">★浏览/修改/删除文章</td>
        			</tr>
        			<tr><td height="30" colspan="4" align="center" background="images/blog/adminLmenu_2.jpg" class="word_menuHead">文章类别管理</td>
        			</tr>
        			<tr valign="bottom"><td height="27" colspan="4" bgcolor="#33FF33" style="text-indent:30" class="tableBorder_B"><a href="<%=basePath%>/sys/article_admin_articleTypeAdd">★添加类别</a></td>
        			</tr>
        			<tr valign="bottom"><td height="27" colspan="4" bgcolor="#33FF33" style="text-indent:30"><a href="<%=basePath%>/sys/article_admin_articleTypeList">★浏览/修改/删除类别</a></td>
        			</tr>
        			<tr><td height="30" colspan="4" align="center" background="images/blog/adminLmenu_2.jpg" class="word_menuHead">相册管理</td>
        			</tr>
        			<tr valign="bottom"><td height="27" colspan="4" bgcolor="#33FF33" style="text-indent:30" class="tableBorder_B"><a href="<%=basePath%>/sys/photo_admin_photoUpload">★上传照片</a></td>
        			</tr>
        			<tr valign="bottom"><td height="27" colspan="4" bgcolor="#33FF33" style="text-indent:30"><a href="<%=basePath%>/sys/photo_admin_adminphotoList">★浏览/删除照片</a></td>
        			</tr>
        		
        			<tr><td height="30" colspan="4" align="center" background="images/blog/adminLmenu_2.jpg" class="word_menuHead">好友管理</td>
        			</tr>
        			<tr valign="bottom"><td height="27" colspan="4" bgcolor="#33FF33" style="text-indent:30"class="tableBorder_B"><a href="<%=basePath%>/sys/friend_admin_friendAdd">★添加好友</a></td>
        			</tr>
        			
        			<tr valign="bottom"><td height="27" colspan="4" bgcolor="#33FF33" style="text-indent:30"><a href="<%=basePath%>/sys/friend_admin_adminListFriend">★浏览/修改/删除好友</a></td>
        			</tr> 
        			
        			<tr><td height="30" colspan="4" align="center" background="images/blog/adminLmenu_2.jpg" class="word_menuHead">留言管理</td>
        			</tr>
        			<tr valign="bottom"><td height="27" colspan="4" bgcolor="#33FF33" style="text-indent:30"><a href="<%=basePath%>/sys/word_admin_wordList">★浏览/删除留言</a></td></tr>
        			
        		<!--<tr><td height="30" colspan="4" align="center" background="images/blog/adminLmenu_2.jpg" class="word_menuHead"></td>
        			</tr>
        			<tr valign="bottom"><td height="27" colspan="4" bgcolor="#33FF33" style="text-indent:30"class="tableBorder_B"></td>
        			</tr>  -->	
        			<tr valign="bottom"><td height="27" colspan="4" bgcolor="#33FF33" style="text-indent:30"></td>
        			</table>
   		    </td>
       	    <td valign="top" bgcolor="#FFFFFF" class="tableBorder_R">&nbsp;</td>
        </tr>
</table> 
</body>
</html>