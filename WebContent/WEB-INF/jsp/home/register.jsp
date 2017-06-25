<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>   
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD>
<STYLE>
.cla1 {
FONT-SIZE: 12px; COLOR: #4b4b4b; LINE-HEIGHT: 18px; TEXT-DECORATION: none
}
.login_msg{
	font-family:serif;
}
.login_msg .msg{
	background-color: #acf;
	font-family: 微软雅黑;
}
.login_msg .btn{
	background-color: #9be;
	width: 85px;
	font-size: 18px;
	font-family: 微软雅黑;
}
.register_title{
	font-size: 32px;
	font-family: 微软雅黑;
	color:#02d;
}
.login_msg_field{
	font-family: 微软雅黑;
}

</STYLE>

<TITLE>新用户注册</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<LINK href="${pageContext.request.contextPath}/css/style.css" type=text/css rel=stylesheet>

<script type="text/javascript">	
	function setClean(){
		document.getElementById("loginName").value = "";
		document.getElementById("loginPwd").value = "";
	}
</script>
<META content="MSHTML 6.00.2600.0" name=GENERATOR></HEAD>

<BODY leftMargin=0 topMargin=0 marginwidth="0" marginheight="0" background="${pageContext.request.contextPath}/images/home/rightbg.jpg">

<div ALIGN="center">
    
	<table border="0" width="1140px" cellspacing="0" cellpadding="0" id="table1">
		<tr>
			<td height="93"></td>
			<td></td>
		</tr>
		<tr>
			<td background="${pageContext.request.contextPath}/images/home/right.jpg"  width="740" height="412"></td>
			<td class="login_msg" width="400">
				
				<s:form cssClass="login_msg_field" namespace="/sys" action="staffAction_register">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="register_title">新用户注册</span><br/><br/>	
					<s:actionerror/>
   					<s:fielderror cssStyle="color:red"/>				
					用&nbsp;&nbsp;户&nbsp;&nbsp;名：<s:textfield id="loginName" name="loginName" cssClass="msg" cssStyle="color: #767676" size="31"/><br/><br/>
					密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：<s:password id="loginPwd" name="loginPwd" cssClass="msg"  cssStyle="color: #767676" size="31"/><br/><br/>
					确认密码：<s:password id="reNewPassword" name="reNewPassword" cssClass="msg"  cssStyle="color: #767676" size="31"/><br/><br/>
					姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：<s:textfield id="staffName" name="staffName" cssClass="msg" cssStyle="color: #767676" size="31"/><br/><br/>
					性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：<s:radio list="{'男','女'}" name="gender"></s:radio><br/><br/>
				<!--出生日期：<s:date name="onDutyDate" format="yyyy-MM-dd" var="myDate"/><s:textfield  cssClass="msg" name="onDutyDate" value="%{#myDate}" onfocus="c.showMoreDay=true; c.show(this);" size="31"></s:textfield><br/><br/>  -->											
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="btn" type="submit" value=" 注册 "/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="btn" type="reset" value="取消 "/>
				</s:form>			
			</td>
		</tr>
	</table>
</div>
</BODY></HTML>