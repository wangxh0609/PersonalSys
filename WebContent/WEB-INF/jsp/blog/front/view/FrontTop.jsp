<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
    	<title>博客-页头</title>
		<base href="<%=basePath%>">	
		<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css">	    	
    </head>
	<body background="<%=path%>/images/blog/bg.jpg">
    	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        	<tr>
            	<td background="<%=path%>/images/blog/top.jpg">
           			<div style="margin-top:78;margin-left:30">
                       <s:if test="%{#session.revisitorStaff.headImg != null && #session.revisitorStaff.headImg !=''}">                            
                            <img id="test" src="/upload<s:property value='#session.revisitorStaff.headImg'/>" width="170" height="180" />
                        </s:if><s:else>
							<img src="${basePath}/images/home/gs09.png" width="170" height="180" />
						</s:else>                    			
                   	</div>
                	<table border="0" cellspacing="10" cellpadding="0" style="margin-top:-60;margin-left:350">
                    	<tr>                    		
                        	<td class="topTD"><a href="<%=request.getContextPath() %>/sys/blog_blogfrontIndex" class="topA"><span class="defineTop">博客首页</span></a> <span class="deTopShu">|</span></td>
	                        <td class="topTD"><a href="<%=basePath %>sys/article_front_articleList?typeId=1" class="topA"><span class="defineTop">我的文章</span></a> <span class="deTopShu">|</span></td>
    	                    <td class="topTD"><a href="<%=basePath %>sys/photo_front_photoList" class="topA"><span class="defineTop">我的相册</span></a> <span class="deTopShu">|</span></td>
        	                <td class="topTD"><a href="<%=basePath %>sys/friend_front_friendList" class="topA"><span class="defineTop">我的好友</span></a> <span class="deTopShu">|</span></td>
            	            <td class="topTD"><a href="<%=request.getContextPath() %>/sys/word_front_wordShow" class="topA"><span class="defineTop">留言板</span></a><span class="deTopShu"> |</span></td>
            	            <td class="topTD"><a href="<%=basePath %>sys/bloglogin_islogon" class="topA"><span class="defineTop">管理博客</span></a></td>
                	    </tr>
	                </table>
    	        </td>
        	</tr>
	    </table> 
	</body>
</html>