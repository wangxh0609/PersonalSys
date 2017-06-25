<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.List" %>
<%@page import="com.hust.docMgr.blog.domain.MasterBean"%>
<%@page import="com.hust.docMgr.blog.domain.WordBean"%>
<%@page import="com.hust.docMgr.blog.domain.ArticleBean"%>
<%@page import="com.hust.docMgr.blog.domain.FriendBean"%>
<%
  String path = request.getContextPath();
%>
<html>
    <head>
    	<title>博客-侧栏</title>
    </head>
<body>
    <center>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr height="29"><td background="<%=path %>/images/blog/left_T.jpg" style="text-indent:45;padding-top:8"><b><font color="white">博主信息</font></b></td></tr>
                    <tr height="40">
                    	<td background="<%=path %>/images/blog/left_M.jpg" style="text-indent:30">
                    		博主姓名：<s:property value="#session.revisitorStaff.staffName"/>
                    	</td>
                    </tr>
                    <tr height="40">
                    	<td background="<%=path %>/images/blog/left_M.jpg" style="text-indent:30">博主性别：<s:property value="#session.revisitorStaff.gender"/></td>
                    </tr> 
                    <tr height="40">
                    	<td background="<%=path %>/images/blog/left_M.jpg" style="text-indent:30">QQ号码：<s:property value="#session.revisitorStaff.phoneNum"/>
                    </tr>                    
                    <tr height="16"><td background="<%=path %>/images/blog/left_E.jpg" bgcolror="";></td></tr>
                </table>
            </td>
        </tr>
        <tr>
            <td valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr height="29"><td background="<%=path %>/images/blog/left_T.jpg" style="text-indent:45;padding-top:8"><b><font color="white">最新留言</font></b></td></tr>
                    <%
                        List wordlist=(List)session.getAttribute("wordList");
                    	if(wordlist==null||wordlist.size()==0)
                    		out.print("<tr height='100'><td align='center' background='"+path+"/images/blog/left_M.jpg'>没有留言可显示！</td></tr>");
                    	else{
                    		int i=0;
                    		while(i<wordlist.size()){
                    			WordBean wordSingle=(WordBean)wordlist.get(i);
                    %>
                    <tr height="25" valign="bottom">
                    	<td style="text-indent:25" background="<%=path %>/images/blog/left_M.jpg">
                    		<a href="<%=request.getContextPath() %>/sys/word_front_wordShow" ><%=wordSingle.getWordTitle(15)%></a>
                    	</td>
                    </tr>
                    <%	
	                    		i++;
                    		}
                    	} 
                    %>
                    <tr height="16"><td background="<%=path %>/images/blog/left_E.jpg"></td></tr>
                </table>
            </td>
        </tr>
        <tr>
            <td valign="top">
                <table width="100%" height="60" border="0" cellspacing="0" cellpadding="0">
                    <tr height="29"><td background="<%=path %>/images/blog/left_T.jpg" style="text-indent:45;padding-top:8"><b><font color="white">博主推荐</font></b></td></tr>
                    <%
                        List artTJlist=(List)session.getAttribute("artTJList");
                    	if(artTJlist==null||artTJlist.size()==0)
                    		out.print("<tr height='100'><td align='center' background='"+path+"/images/blog/left_M.jpg'>没有推荐文章可显示！</td></tr>");
                    	else{
                    		int i=0;
                    		while(i<artTJlist.size()){
                    			ArticleBean articleSingle=(ArticleBean)artTJlist.get(i);
                    %>
                    <tr height="25" valign="bottom">
                    	<td style="text-indent:25" background="<%=path %>/images/blog/left_M.jpg">
                    		<a href="<%=path %>/sys/article_front_articleSingle?id=<%=articleSingle.getId()%>"><%=articleSingle.getTitle(15) %></a>
                    	</td>
                    </tr>
                    <%	
	                    		i++;
                    		}
                    	} 
                    %>
                    <tr height="16"><td background="<%=path %>/images/blog/left_E.jpg"></td></tr>
                </table>
            </td>
        </tr>
        <tr>
            <td valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr height="29"><td background="<%=path %>/images/blog/left_T.jpg" style="text-indent:45;padding-top:8"><b><font color="white">好友博客</font></b></td></tr>
                    <%
                        List friendlist=(List)session.getAttribute("friendList");
                    	if(artTJlist==null||friendlist.size()==0){                      		
                    		out.print("<tr height='100'><td align='center' background='"+path+"/images/blog/left_M.jpg'>博主目前还未添加任何好友！</td></tr>");
                    	}
                    	else{
                    		int i=0;
                    		while(i<friendlist.size()){
                    			FriendBean friendSingle=(FriendBean)friendlist.get(i);
                    %>
                    <tr height="25" valign="bottom">
                    	<td style="text-indent:25" background="<%=path %>/images/blog/left_M.jpg">
                    		<a href="<%=friendSingle.getBlog() %>"><%=friendSingle.getName() %></a>
                    	</td>
                    </tr>
                    <%	
	                    		i++;
                    		}
                    	} 
                    %>
                    <tr height="16"><td background="<%=path %>/images/blog/left_E.jpg"></td></tr>
                </table>
            </td>
        </tr>
        <tr><td></td></tr>
    </table>
    </center> 
</body>
</html>