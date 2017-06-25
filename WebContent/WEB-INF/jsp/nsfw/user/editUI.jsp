<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/struts-tags" prefix="s"%>  
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>用户管理</title>
    <script type="text/javascript" src="${basePath }js/datepicker/WdatePicker.js"></script>
        <script type="text/javascript">
        var vResult = false;
    	//校验帐号唯一
    	
    	function doVerify(){
    		//1、获取帐号
    		var account = $("#loginName").val();
    		//alert(account);
    		if(account != ""){
    			//2、校验 
    			$.ajax({
    				url:"${basePath}user/userAction_verifyAccount?operation=modify",
    				data: {"loginName": account},
    				type: "post",
    				async: false,//非异步
    				success: function(msg){
    					if("fale" == msg){
    						//notMatchType  picTooBig  notHead
    						//帐号已经存在
    						alert("帐号已经存在。请使用其它帐号！");
    						//定焦
    						$("#loginName").focus();
    						vResult = false;
    					}else if("notMatchType"==msg){
    						alert("只能上传jpg|jpeg|gif|bmp|png|ico格式的头像文件！");
    						//定焦
    						$("#headImg").focus();
    						vResult = false;
    					}
						else if("picTooBig"==msg){
							alert("头像文件不能大于1M");
							$("#headImg").focus();
							vResult = false;					
						}
						else if("notHead"==msg){
							alert("头像文件不存在或不是文件！");
							$("#headImg").focus();
							vResult = false;
						}
    					else {
    						vResult = true;
    					}
    				}
    			});
    		}
    	}
    	//提交表单
    	function doSubmit(){
    		var name = $("#loginName");
    		if(name.val() == ""){
    			alert("帐名不能为空！");
    			name.focus();
    			return false;
    		}
    		var password = $("#loginPwd");
    		if(password.val() == ""){
    			alert("密码不能为空！");
    			password.focus();
    			return false;
    		}
    		//帐号校验
    		//doVerify();
    		if(true){
	    		//提交表单
	    		document.forms[0].submit();
    		}
    	}
    </script>
</head>
<body class="rightBody">
<form id="form" name="form" action="${basePath }user/userAction_editSave" method="post" enctype="multipart/form-data">
	<s:hidden name="operation" value="modify"/>
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>用户管理</strong>&nbsp;-&nbsp;编辑用户</div></div>
    <div class="tableH2">编辑用户</div>
    <s:actionerror/>
   	<s:fielderror cssStyle="color:red"/>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <!-- <tr>
            <td class="tdBg" width="200px">所属部门：</td>
            <td><s:select name="user.dept" list="#{'部门A':'部门A','部门B':'部门B' }"/></td>
        </tr> -->
        <tr>
            <td class="tdBg" width="200px">头像：</td>
            <td>
                <s:if test="%{staff.headImg != null && staff.headImg != ''}">
                    <img src="/upload<s:property value='staff.headImg'/>" width="100" height="100"/>
                    <s:hidden name="staff.headImg"/>
                </s:if>
                <input type="file" name="headImg"/>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">用户名：</td>
            <td><s:textfield id="staffName" name="staff.staffName"/> </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">帐号：</td>
            <td><s:textfield id="loginName" name="staff.loginName" readonly="true"/></td><!-- onchange="doVerify()" -->
        </tr>
        <tr>
            <td class="tdBg" width="200px">密码：</td>
            <td><s:textfield id="loginPwd" name="staff.loginPwd" value="****"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">性别：</td>
            <td><s:radio list="#{'男':'男','女':'女'}" name="staff.gender" value="staff.gender" /></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">角色：</td>
           
        </tr>
        <tr>
            <td class="tdBg" width="200px">电子邮箱：</td>
            <td><s:textfield name="staff.email"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">手机号：</td>
            <td><s:textfield name="staff.phoneNum"/></td>
        </tr>        
        <tr>
            <td class="tdBg" width="200px">生日：</td>
            <td>
            <s:textfield id="birthday" name="staff.onDutyDate" readonly="true" 
            onfocus="WdatePicker({'skin':'whyGreen','dateFmt':'yyyy-MM-dd'});" >
            	<s:param name="value"><s:date name="staff.onDutyDate" format="yyyy-MM-dd"/></s:param>
            </s:textfield>
            </td>
        </tr>
		<tr>
            <td class="tdBg" width="200px">状态：</td>
            <td><s:radio list="#{'1':'有效','0':'无效'}" name="staff.state" value="staff.state"/></td>
        </tr>
        <!-- 
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td><s:textarea name="user.memo" cols="75" rows="3"/></td>
        </tr> -->
    </table>
    <s:hidden name="staff.staffId"/>
    <div class="tc mt20">
        <input type="button" class="btnB2" value="保存" onclick="doSubmit()" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
</body>
</html>