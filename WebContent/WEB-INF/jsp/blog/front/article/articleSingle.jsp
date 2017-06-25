<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.hust.docMgr.blog.domain.ArticleBean" %>
<%@ page import="com.hust.docMgr.blog.domain.ReviewBean" %>
<%@ page import="java.util.*" %>
<%
	String path=request.getContextPath();
%>
<html>
<head>
	<title>我的博客-阅读文章</title>
</head>
<body>
    <center>
        <table width="778" height="600" border="0" cellspacing="0" cellpadding="0" bgcolor="#F0EAED">
            <tr height="281"><td colspan="2"><jsp:include page="../view/FrontTop.jsp" /></td></tr>
            <tr>
                <td width="230" valign="top"><jsp:include page="../view/FrontLeft.jsp"/></td>
                <td width="548" align="center" valign="top">
                    <% 
                    	ArticleBean single=(ArticleBean)session.getAttribute("readSingle");
                    	if(single==null)
                    		out.println("阅读文章失败！");
                    	else{
                    %>
                	<table border="0" width="100%" rules="all" cellpadding="0" cellspacing="0">
                	    <tr height="55" align="right">
                  	        <td background="images/blog/main_B.jpg">
		               	        发表时间：<%=single.getSdTime() %>&nbsp;&nbsp;&nbsp;&nbsp;
                  	        	评论：<%=single.getReview() %> 条&nbsp;&nbsp;&nbsp;&nbsp;
                	        	阅读：<%=single.getCount() %> 次&nbsp;&nbsp;&nbsp;&nbsp;
                	    	</td>
                	    </tr>
                	    
                	    <tr><td align="center"><b><font style="font-size:25px"><%=single.getTitle()%></font></b></td></tr>
                	    <tr height="40"><td align="right"><%=single.getCreate()%>：<%=single.getInfo() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
  
                	    <tr height="200"><td valign="top" style="padding-left:20;padding-right:20"><%=single.getContent() %></td></tr>
                	</table>
                	<a href="javascript:window.history.go(-1)">返回</a>
                	<hr width="95%" style="margin-top:5" size="1">
                	<% 
               				List reviewlist=(ArrayList)session.getAttribute("reviewlist");
	               			if(reviewlist==null||reviewlist.size()==0){
    	           				out.print("<p><li>该文章目前没有任何评论！</li>");
        	       			}
            	   			else{
                	       		int num=reviewlist.size(); 
                    	   		out.print("<table border='0' width='100%' rules='all' cellpadding='0' cellspacing='0'> ");
                      			out.print("<tr height='60'><td align='center'>【文章评论 共 "+num+" 条】</td></tr>");                            
               					int i=0;
	                           	while(i<reviewlist.size()){
    	                       		ReviewBean reviewSingle=(ReviewBean)reviewlist.get(i);            
                    %>
     							<tr height="50">
     								<td style="text-indent:20">▲ <b><%=reviewSingle.getReAuthor() %></b></td>
	     						</tr>
     							<tr align="right" height="20"><td width="40%"><%=reviewSingle.getReSdTime()%>&nbsp;&nbsp;</td></tr>
     							<tr height="60"><td style="text-indent:20" colspan="2" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;<%=reviewSingle.getReContent()%></td></tr> 						
    		    	            <tr height="1"><td background="images/line.jpg" colspan="2"></td></tr>
                    <%
                    	       		i++;
                        	   	}
                           		out.print("</table>");
            	   			}
                       	
                	%>
                	
                	<p><b>发表评论</b></p>
                	<form action="<%=path %>/sys/article_front_addReview?articleId=<%=single.getId() %>" method="post">
            	<!--	<input type="hidden" name="action" value="followAdd">-->
            		<table width="95%" border="0" cellspacing="8" cellpadding="0" style="margin-top:10">
            			<tr>
            				<td width="25%" align="center">评 论 者：</td>
            				<s:if test="%{#session.loginStaff!= null}">                            
                            	<td><input type="text" readonly="true" name="reAuthor" size="40" value="<s:property value='#session.loginStaff.loginName'/>"></td>
                        	</s:if>
                        	<s:else>
								<td><input type="text" name="reAuthor" size="40" value="匿名用户"></td>
							</s:else> 
            				
            			</tr>            			
            			<tr>
            				<td align="center">评论内容：</td>
            				<td><textarea name="reContent" rows="10" cols="50"></textarea></td>
            			</tr>
            			<tr>
            			 	<td></td>
            				<td>
            					<input type="submit" value="提交" style="width:50">
            					<input type="reset" value="重置" style="width:50">
            				</td>            				
            			</tr>
            		</table>
            		</form>  
            		<%
                    	}
            		%>              	
                </td>
            </tr>
            <tr height="100"><td colspan="2"><%@ include file="/WEB-INF/jsp/blog/front/view/FrontEnd.jsp" %></td></tr>
        </table>
    </center>
</body>
</html>