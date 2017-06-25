<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>信息发布管理</title>
    <script type="text/javascript" charset="utf-8" src="${basePath }js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath }js/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${basePath }js/ueditor/lang/zh-cn/zh-cn.js"></script>

    <script>
   		window.UEDITOR_HOME_URL = "${basePath }js/ueditor/";
    	var ue = UE.getEditor('editor');
    	
    </script>
</head>
<body class="rightBody">
<form id="form" name="form" action="${basePath }nsfw/InfoDbActionTwo_addUI" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>信息发布管理</strong>&nbsp;-&nbsp;新增信息</div></div>
    <div class="tableH2">新增信息</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">信息分类：</td>
            <td><s:select name="type" list="{'QQ','微信','花生壳','CSDN','百度'}"/></td><!-- list="#infoTypeMap" -->
            <td class="tdBg" width="200px">来源：</td>
            <td><s:textfield name="source"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">使用账号：</td>
            <td colspan="3"><s:textfield name="accountNum" cssStyle="width:90%"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">使用密码：</td>
            <td colspan="3"><s:textfield name="content" cssStyle="width:90%"/><!--  s:textarea id="editor" name="content" cssStyle="width:90%;height:160px;" />--></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td colspan="3"><s:textarea name="memo" cols="90" rows="3"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">创建人：</td>
            <td>
            	<s:property value="#session.loginStaff.loginName"/>
            	<s:hidden name="creator" value="%{#session.loginStaff.loginName}"/>
            </td>
            <td class="tdBg" width="200px">创建时间：</td>
            <td>
            	<s:textfield id="createId" readonly="true" name="createTime"></s:textfield>
             	<!--<s:date name="createTime" format="yyyy-MM-dd HH:mm"/>
             	<s:hidden id="createId" name="createTime"/>-->
            </td>
        </tr>
    </table>
    <!-- 默认信息状态为 发布 -->
    <s:hidden name="state" value="1"/>
    <div class="tc mt20">
        <input type="submit" class="btnB2" value="保存" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
<script>
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3),//
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

	var time=document.getElementById("createId");
	time.value=new Date().Format("yyyy-MM-dd");
</script>

</body>
</html>