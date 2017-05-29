<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>信息发布管理</title>
    <script type="text/javascript">
  	//全选、全反选
	function doSelectAll(){
		// jquery 1.6 前
		//$("input[name=selectedRow]").attr("checked", $("#selAll").is(":checked"));
		//prop jquery 1.6+建议使用
		$("input[name=selectedRow]").prop("checked", $("#selAll").is(":checked"));		
	}
  	//新增
  	function doAdd(){
  		document.forms[0].action = "${basePath}/uiActionThree_nsfw_info_addUI";
  		document.forms[0].submit();
  	}
  	//编辑
  	function doEdit(id){
  		document.forms[0].action = "${basePath}nsfw/info_editUI.action?info.infoId=" + id;
  		document.forms[0].submit();
  	}
  	//删除
  	function doDelete(id){
  		document.forms[0].action = "${basePath}nsfw/InfoDbAction_deleteById?infoId="+id;
  		document.forms[0].submit();
  	}
  	//批量删除
  	function doDeleteAll(){
  		obj=document.getElementsByName("selectedRow");
  		check_val = [];
  		for(k in obj){
  			if(obj[k].checked){
  				check_val.push(obj[k].value);
  			}
  		}
  		
  		document.forms[0].action = "${basePath}nsfw/InfoDbAction_deleteSelected?infoId="+check_val;
  		document.forms[0].submit();
  	}
  	//异步发布信息,信息的id及将要改成的信息状态
  	function doPublic(infoId, state){
  		//1、更新信息状态
  		$.ajax({
  			url:"${basePath}nsfw/InfoDbAction_publicInfo",
  			data:{"infoId":infoId, "state":state},
  			type:"post",
  			success: function(msg){
  				//2、更新状态栏、操作栏的显示值
  				if("更新状态成功" == msg){
  					if(state == 1){//说明信息状态已经被改成 发布，状态栏显示 发布，操作栏显示 停用
  						$("#show_"+infoId).html("发布");
  						$("#oper_"+infoId).html('<a href="javascript:doPublic(\''+infoId+'\',0)">停用</a>');
  					} else {
  						$("#show_"+infoId).html("停用");
  						$("#oper_"+infoId).html('<a href="javascript:doPublic(\''+infoId+'\',1)">发布</a>');
  					}
  				} else {alert("更新信息状态失败！");}
  			},
  			error: function(){
  				alert("更新信息状态失败！");
  			}
  		});
  	}
  	var list_url = "${basePath}nsfw/InfoDbActionTwo_listUI";
  	//搜索
  	function doSearch(){
  		//重置页号
  		$("#pageNo").val(1);
  		document.forms[0].action = list_url;
  		document.forms[0].submit();
  	}
  	
  	function doViewPwd(infoId){
  		//1、更新信息状态
  		$.ajax({
  			url:"${basePath}nsfw/InfoDbActionTwo_ViewPwd",
  			data:{"infoId":infoId},
  			type:"post",
  			success: function(msg){//<s:property value="content"/>
  			    //alert(msg+"hah");
  				//2、更新状态栏、操作栏的显示值
  				if(msg!=""){  					
  					$("#pwd_"+infoId).html(msg);
  					$("#oper1_"+infoId).html('<a href="javascript:doHiden(\''+infoId+'\')">隐藏</a>');
  				} 
  			},
  			error: function(){
  				//alert("更新信息状态失败！");
  			}
  		});
  	}
  	function doHiden(infoId){
  		$("#oper1_"+infoId).html('<a href="javascript:doViewPwd(\''+infoId+'\')">显示</a>');
  		$("#pwd_"+infoId).html("****");
  	}
  	
    </script>
</head>
<body class="rightBody">
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>信息发布管理</strong></div> </div>
                <div class="search_art">
                    <li>
                                                            账号：<s:textfield name="accountNum" cssClass="s_text" id="infoTitle"  cssStyle="width:160px;"/>
                    </li>
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
                    <li style="float:right;">
                        <input type="button" value="新增" class="s_button" onclick="doAdd()"/>&nbsp;
                        <input type="button" value="删除" class="s_button" onclick="doDeleteAll()"/>&nbsp;
                    </li>
                </div>

                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td width="30" align="center"><input type="checkbox" id="selAll" onclick="doSelectAll()" /></td>
                            <td align="center">账号</td>
                            <td width="120" align="center">信息分类</td>
                            <td width="120" align="center">密码</td>
                            <td width="120" align="center">创建人</td>
                            <td width="140" align="center">创建时间</td>
                            <td width="80" align="center">状态</td>
                            <td width="120" align="center">操作</td>
                        </tr>
                        <s:iterator value="#allInfoDb" status="st">
                            <tr <s:if test="#st.odd"> bgcolor="f8f8f8" </s:if> >
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='infoId'/>"/></td>
                                <td align="center"><s:property value="accountNum"/></td>
                                <td align="center"><s:property value="type"/></td>
                                <td id="pwd_<s:property value='infoId'/>" align="center">****</td><!-- <s:property value="content"/> -->
                                <td align="center"><s:property value="creator"/></td>
                                <td align="center"><s:date name="createTime" format="yyyy-MM-dd HH:mm"/></td>
                                <td id="show_<s:property value='infoId'/>" align="center"><s:property value="state==1?'发布':'停用'"/></td>
                                <td align="center">
                                	<span  id="oper_<s:property value='infoId'/>">
                                	<s:if test="state==1">
                                		<a href="javascript:doPublic('<s:property value='infoId'/>',0)">停用</a>
                                	</s:if><s:else>
                                		<a href="javascript:doPublic('<s:property value='infoId'/>',1)">发布</a>
                                	</s:else>
                                	</span>
                                    <!--a href="javascript:doEdit('<s:property value='infoId'/>')">编辑</a>  -->
                                    <span id="oper1_<s:property value='infoId'/>">
                                    <a href="javascript:doViewPwd('<s:property value='infoId'/>')">显示</a>
                                    </span>
                                    <a href="javascript:doDelete('<s:property value='infoId'/>')">删除</a>
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </div>
        	<jsp:include page="/common/pageNavigator.jsp"/>
        </div>
    </div>
</form>

</body>
</html>