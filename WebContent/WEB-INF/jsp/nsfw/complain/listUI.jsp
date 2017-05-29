<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>投诉受理管理</title>
    <script type="text/javascript" src="${basePath }js/datepicker/WdatePicker.js"></script>
    <script type="text/javascript">
  	var list_url = "${basePath}nsfw/complain_listUI";
  	//搜索
  	function doSearch(){
  		//重置页号
  		$("#pageNo").val(1);
  		document.forms[0].action = list_url;
  		document.forms[0].submit();
  	}
  	//受理
  	function doDeal(compId){
  		document.forms[0].action = "${basePath}nsfw/complain_dealUI.action?complain.compId=" + compId;
  		document.forms[0].submit();
  	}
    </script>
</head>
<body class="rightBody">


<form name="form1" enctype="multipart/form-data" action="${pageContext.request.contextPath }/servlet/uploadServlet1" method="post" >
		<input type="text" name="name"/><br/>
		<input type="file" name="docFile"/><br/>
		<input type="submit" value="上传"/><br/>
</form>

</body>
</html>