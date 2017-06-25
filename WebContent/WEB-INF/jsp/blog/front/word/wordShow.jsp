<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.List" %>
<%@ page import="com.hust.docMgr.blog.domain.WordBean" %>
<%
  pageContext.setAttribute("wordbasePath", request.getContextPath()+"/") ;
  String path = request.getContextPath();
%>

<html>
<head>
	<title>我的博客-查看留言</title>
	<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.10.2.min.js"></script>
	 <script type="text/javascript">
	 
	    $(document).ready(function(){
		    $(".wordclass").click(function() {
		    	//首先判断页面上有没有打开的form有就隐藏
		    	 var objtem=$("form[id*='form_']");
		  		 if(objtem!=null&&objtem.length>0){
		  			for(var i=0;i<objtem.length; i++){
		  				objtem[i].style.display="none";
		  			 }
		  		 }
			     
			     trId=$(this).attr('id');
			     var trIds= new Array();
			     trIds=trId.split("_");//得到该条的id 操作_父id_自己id
			     var objId="";
			     var objfirstId="";
			     if(trIds[2]==null){//回复第一条
			    	 objId=trIds[1];
			    	 objfirstId=trIds[1];
			     }else{
			    	 objfirstId=trIds[1];
			    	 objId=trIds[2];
			     }
			    
			     formObj= $("form_"+objId);
			     if(!document.getElementById("form_"+objId)){
			    	 if(trIds[2]==null){
			     	//双击出现回复框
				     $("#"+trId).after("<form id='form_"+objId+"' action='${wordbasePath}sys/word_front_addWordOther' method='post'>"+
				     			"<textarea name='wordContent' rows='2' cols='45' style='margin-left:50'></textarea>"+
				     			"<input type='hidden' name='parentId' value='"+objId+"'> "+
				     			"<input type='hidden' name='firstParentId' value='"+objfirstId+"'> "+
				     			"<input type='submit' onclick='javascript:commitForm("+objId+")' value='回复' style='width:50'>"+
		    					 "</form>");
			    	 }else{
			    		 $("#"+trId).after("<form id='form_"+objfirstId+"_"+objId+"' action='${wordbasePath}sys/word_front_addWordOther' method='post'>"+
					     			"<textarea name='wordContent' rows='2' cols='45' style='margin-left:50'></textarea>"+
					     			"<input type='hidden' name='parentId' value='"+objId+"'> "+
					     			"<input type='hidden' name='firstParentId' value='"+objfirstId+"'> "+
					     			"<input type='submit' onclick='javascript:commitForm("+objId+")' value='回复' style='width:50'>"+
			    					 "</form>");//<input type='reset' value='重置' style='width:50'>
			    	 }
			     }else{
			    	// if(formObj!=null&&formObj.length>0){
			   			//for(var i=0;i<obj.length; i++){
			   				//formObj[i].style.display="inline";
		   			 //}
			   		// }
			    	document.getElementById("form_"+objId).style.display="inline";
			     }		     
		    });
		})
		
		function commitForm(wordId){
	    	// $("#form_"+wordId).action="${path}/sys/word_front_addWordOther?parentId="+wordId;
	    	 $("#form_"+wordId).submit();
	    
	    	 //隐藏回复框
	    	 $("#form_"+wordId).style.display="none";
	    }
  		   
		function doExpand(wordId){
  		    obj=$("tr[id*='reply_"+wordId+"_']");
  		    if(obj==null||obj.length==0){
  		    	//通过ajax发送请求得到所有的留言回复
	  		    $.ajax({
		  			url:"${wordbasePath}sys/word_front_expandAll",
		  			data:{"wordId":wordId},
		  			type:"post",
		  			success: function(msg){//<s:property value="content"/>
		  			    
		  				if(msg!="false"){  
		  					
		  					//$("#oper_"+wordId).after("<tr id='reply_"+wordId+"_'><td style='text-indent:35' colspan='2' valign='top'>我回复林：好的</td></tr>");
		  					$("#oper_"+wordId).after(msg);
		  					$("#opeExp_"+wordId).html("<td><div style='margin-left:25'><a href='javascript:doHiden("+wordId+")'>收起全部</a></div></td>");
		  					
		  					//绑定双击事件
		  					 var obj=$("tr[id*='reply_"+wordId+"_']");
		  					 $(".wordclass").click(function() {
		  				    	//首先判断页面上有没有打开的form有就隐藏
		  				    	 var objtem=$("form[id*='form_']");
		  				  		 if(objtem!=null&&objtem.length>0){
		  				  			for(var i=0;i<objtem.length; i++){
		  				  				objtem[i].style.display="none";
		  				  			 }
		  				  		 }
		  					     
		  					     trId=$(this).attr('id');
		  					     var trIds= new Array();
		  					     trIds=trId.split("_");//得到该条的id 操作_父id_自己id
		  					     var objId=""
		  					    	var objfirstId="";
		  					     if(trIds[2]==null){//回复第一条
		  					    	 objId=trIds[1];
		  					    	 objfirstId=trIds[1];
		  					     }else{
		  					    	 objfirstId=trIds[1];
		  					    	 objId=trIds[2];
		  					     }
		  					    
		  					     formObj= $("form_"+objId);
		  					     if(!document.getElementById("form_"+objId)){
		  					    	 if(trIds[2]==null){
		  					     	//双击出现回复框
		  						     $("#"+trId).after("<form id='form_"+objId+"' action='${wordbasePath}sys/word_front_addWordOther' method='post'>"+
		  						     			"<textarea name='wordContent' rows='2' cols='45' style='margin-left:50'></textarea>"+
		  						     			"<input type='hidden' name='parentId' value='"+objId+"'> "+
		  						     			"<input type='hidden' name='firstParentId' value='"+objfirstId+"'> "+
		  						     			"<input type='submit' onclick='javascript:commitForm("+objId+")' value='回复' style='width:50'>"+
		  				    					 "</form>");
		  					    	 }else{
		  					    		 $("#"+trId).after("<form id='form_"+objfirstId+"_"+objId+"' action='${wordbasePath}sys/word_front_addWordOther' method='post'>"+
		  							     			"<textarea name='wordContent' rows='2' cols='45' style='margin-left:50'></textarea>"+
		  							     			"<input type='hidden' name='parentId' value='"+objId+"'> "+
		  							     			"<input type='hidden' name='firstParentId' value='"+objfirstId+"'> "+
		  							     			"<input type='submit' onclick='javascript:commitForm("+objId+")' value='回复' style='width:50'>"+
		  					    					 "</form>");
		  					    	 }
		  					     }else{
		  					  
		  					    	document.getElementById("form_"+objId).style.display="inline";
		  					     }		     
		  				    });
		  				} else{
		  					//alert("出现未知错误！"); 
		  					$("#opeExp_"+wordId).html("<td><div style='margin-left:25'><a href='javascript:doHiden("+wordId+")'>收起全部</a></div></td>");
		  				}
		  			},
		  			error: function(){
		  				//alert("出现未知错误！"); 
		  				$("#opeExp_"+wordId).html("<td><div style='margin-left:25'><a href='javascript:doHiden("+wordId+")'>收起全部</a></div></td>");
		  				
		  			}
		  		});
	  		   
  		    }
  		    else{
	  			 for(var i=0;i<obj.length; i++){
	  				obj[i].style.display="inline";
	  			 }
	  			$("#opeExp_"+wordId).html("<td><div style='margin-left:25'><a href='javascript:doHiden("+wordId+")'>收起全部</a></div></td>");
  		    }

		}
  	
  	
  	function doHiden(wordId){
  		 var obj=$("tr[id*='reply_"+wordId+"_']");
  		 var formObj=$("form[id*='form_']");
  		 if(obj!=null&&obj.length>0){
  			for(var i=0;i<obj.length; i++){
  				obj[i].remove();
  			 }
  		 }
  		if(formObj!=null&&formObj.length>0){
  			for(var i=0;i<formObj.length; i++){
  				formObj[i].style.display="none";
  			 }
  		 }
  		$("#opeExp_"+wordId).html("<td><div style='margin-left:25'><a href='javascript:doExpand("+wordId+")'>展开全部</a></div></td>");
  	}
  	</script>
</head>
<body>
    <center>
        <table width="778" height="600" border="0" cellspacing="0" cellpadding="0" bgcolor="#F0EAED">
            <tr height="281"><td colspan="2"><jsp:include page="../view/FrontTop.jsp" /></td></tr>
            <tr>
                <td width="230" valign="top"><jsp:include page="../view/FrontLeft.jsp"/></td>
                <td width="548" align="center" valign="top">
                
                	<table width="95%" border="0" cellspacing="0" cellpadding="0" style="word-break:break-all">
                  	<% 
			        	List wordlist1=(List)request.getAttribute("wordList"); 
            			if(wordlist1==null||wordlist1.size()==0)
			            	out.print("<tr height='60'><td align='center'>暂无留言可显示！</td></tr>");
            			else{
                     		int num=wordlist1.size();     
                    		out.print("<tr height='60'><td align='center'>【我的留言 共 "+num+" 条】</td></tr>");    
            			}
                        %>
                    </table>
                	<form action="<%=path %>/sys/word_front_addWordOther" method="post">
            		<!-- <input type="hidden" name="action" value="add"> -->
            		<table width="95%" border="0" cellspacing="3" cellpadding="0" style="margin-top:2">
            			<tr height="30"><td colspan="2" align="center"><b>给我留言</b></td></tr>
            			<tr>
            				<td width="25%" align="center">留 言 者：</td>
            				<td><input type="text" readonly="true" name="wordAuthor" size="40" value="<s:property value='#session.loginStaff.loginName'/>"></td>
            			</tr>            			
            			<tr>
            				<td width="25%" align="center">留言标题：</td>
            				<td><input type="text" name="wordTitle" value="给你留言" size="60"></td>
            			</tr>
            			<tr>
            				<td align="center">留言内容：</td>
            				<td><textarea name="wordContent" rows="5" cols="50"></textarea></td>
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
                	<table width="95%" border="0" cellspacing="0" cellpadding="0" style="word-break:break-all">
                  	<% 
			        	List wordlist=(List)request.getAttribute("wordList"); 
            			if(!(wordlist==null||wordlist.size()==0)){
			            
                     		int num=wordlist.size();     
                    		       
            				int i=0;
                        	while(i<wordlist.size()){
                         		WordBean wordSingle=(WordBean)wordlist.get(i);            
                        %>
 						<tr height="50">
 							<td style="text-indent:20">
 							    ▲ <b><%=wordSingle.getWordAuthor() %></b>
 							</td>
 						</tr>
 						<tr align="right" height="20"><td width="40%"><%=wordSingle.getWordTime()%>&nbsp;&nbsp;</td></tr>
 						<tr class="wordclass" id="oper_<%=wordSingle.getId() %>" height="20"><td style="text-indent:20" colspan="2" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;<%=wordSingle.getWordContent()%></td></tr> 

 						<%
 							if (wordSingle.getParentId()==-1){
 								out.print("<tr id='opeExp_"+wordSingle.getId()+"' height='30'><td><div style='margin-left:25'><a href='javascript:doExpand("+wordSingle.getId()+")'>展开全部</a></div></td></tr>");    
 							}
 						%>						
		                <tr height="1"><td background="<%=path%>/images/blog/line.jpg" colspan="2"></td></tr>
                        <%
                        			i++;
                        		}
                        	}
            			%>
            		</table>
            		
                </td>
            </tr>
            <tr height="100"><td colspan="2"><%@ include file="/WEB-INF/jsp/blog/front/view/FrontEnd.jsp" %></td></tr>
        </table>
    </center>
</body>
</html>