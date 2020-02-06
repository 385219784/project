<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			
		});
		
		//显示与隐藏密码
		function ShowHidn(obj,param){
			var type = $(param).attr("type");
			if(type == "password"){
				$(param).attr("type","text");//显示
				$(obj).attr("src","${ctxStatic}/img/openEye.png");
				$(obj).attr("title","显示");
			}else{
				$(param).attr("type","password");//隐藏
				$(obj).attr("src","${ctxStatic}/img/closeEye.png");
				$(obj).attr("title","隐藏");
			}
		}
	</script>
</head>
<body class="bg-white">
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modifyPwd"  method="post" class="form-horizontal form-group">
		<form:hidden path="id"/>
		<sys:message  content="${message}"/>
		<div class="control-group">
		</div>
		
		<div class="control-group">
			<label class="col-sm-3 control-label" ><font color="red">*</font>旧密码:<img src="${ctxStatic}/img/closeEye.png" onclick="ShowHidn(this,'#oldPassword')" title="隐藏" style="width:18px;padding-right:2px;"/></label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" onkeyup="this.value=this.value.replace(/[^\x00-\xff]/g,'')" value="" maxlength="50" minlength="3"  class="form-control  max-width-250 required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="col-sm-3 control-label"><font color="red">*</font>新密码:<img src="${ctxStatic}/img/closeEye.png" onclick="ShowHidn(this,'#newPassword')" title="隐藏" style="width:18px;padding-right:2px;"/></label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" onkeyup="this.value=this.value.replace(/[^\x00-\xff]/g,'')" value="" maxlength="50" minlength="6" class="form-control  max-width-250 required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>确认新密码:<img src="${ctxStatic}/img/closeEye.png" onclick="ShowHidn(this,'#confirmNewPassword')" title="隐藏" style="width:18px;padding-right:2px;"/></label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" onkeyup="this.value=this.value.replace(/[^\x00-\xff]/g,'')" value="" maxlength="50" minlength="6" class="form-control  max-width-250 required"></input>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" style="display:none" value="保 存"/>
		</div>
	</form:form>
</body>
</html>