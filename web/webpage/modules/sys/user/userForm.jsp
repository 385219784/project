<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="ani"/>
	<style type="text/css">
		/**
		 * 2018/03/03
		 * 查询配电所的css
		 */
		.checkbox-custom {
		    position: relative;
		    /* padding: 0 20px 0 25px; */
		    margin-bottom: 7px;
		    margin-top: 0;
		    display: inline-block;
		}
		/*
		将初始的checkbox的样式改变
		*/
		.checkbox-custom input[type="checkbox"] {
		    opacity: 0;/*将初始的checkbox隐藏起来*/
		    position: absolute;
		    cursor: pointer;
		    z-index: 2;
		    margin: -6px 0 0 0;
		    top: 50%;
		    left: 0px;
		}
		/*
		设计新的checkbox，位置
		*/
		.checkbox-custom label:before {
		    content: '';
		    position: absolute;
		    top: 50%;
		    left: 0;
		    margin-top: -9px;
		    width: 19px;
		    height: 18px;
		    display: inline-block;
		    border-radius: 2px;
		    border: 1px solid #bbb;
		    background: #fff;
		}
		/*
		点击初始的checkbox，将新的checkbox关联起来
		*/
		.checkbox-custom input[type="checkbox"]:checked +label:after {
		    position: absolute;
		    display: inline-block;
		    font-family: 'Glyphicons Halflings';
		    content: "\e013";
		    top: 42%;
		    left: 3px;
		    margin-top: -5px;
		    font-size: 11px;
		    line-height: 1;
		    width: 16px;
		    height: 16px;
		    color: #333;
		}
		.checkbox-custom label {
			margin-left:20px;
		    cursor: pointer;
		    line-height: 1.2;
		    font-weight: normal;/*改变了rememberme的字体*/
		    margin-bottom: 0;
		    text-align: left;
		}
	</style>
	<script type="text/javascript">
	var validateForm;
	var $table; // 父页面table表格id
	var $topIndex;//弹出窗口的 index
	function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	  if(validateForm.form()){
		  $table = table;
		  $topIndex = index;
		  jp.loading();
		  $("#inputForm").submit();
		  return true;
	  }

	  return false;
	}
	$(document).ready(function() {
		$("#no").focus();
		validateForm = $("#inputForm").validate({
			rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
			messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
			submitHandler : function(form) {
				jp.post("${ctx}/sys/user/save",$('#inputForm').serialize(),function(data){
	                    if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jp.success(data.msg);
            	  			
	                    }else{
            	  			jp.error(data.msg);
	                    }
	                    
	                    jp.close($topIndex);//关闭dialog
	            });
				
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
		
		$('#birth').datetimepicker({
			 format: "YYYY-MM-DD",
			 locale: moment.locale('zh-cn')
	    });
	});
	</script>
</head>
<body class="bg-white">
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered">
		   <tbody>
		      <tr>
		         <td class="width-15 active">	<label class="pull-right"><font color="red">*</font>头像：</label></td>
		         <td class="width-35"><form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
						<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/></td>
		         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>归属公司:</label></td>
		         <td class="width-35"><sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
		         			isAll="true"
						title="公司" url="/sys/office/treeData?type=1" allowClear="true" cssClass="form-control required"/></td>
		      </tr>
		      
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>归属部门:</label></td>
		         <td><sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
		         	paramName="parentId" paramValue="companyId"    
					 allowClear="true" title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" notAllowSelectParent="true"/></td>
		         <td class="active"><label class="pull-right"><font color="red">*</font>工号:</label></td>
		         <td><form:input path="no" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>姓名:</label></td>
		         <td><form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">*</font>登录名:</label></td>
		         <td><input id="oldLoginName" autocomplete="off" name="oldLoginName" type="hidden" value="${user.loginName}">
					 <form:input path="loginName" autocomplete="off" htmlEscape="false" maxlength="50" class="form-control required userName"/></td>
		      </tr>
		      
		      
		      <tr>
		         <td class="active"><label class="pull-right"><c:if test="${empty user.id}"><font color="red">*</font></c:if>密码:</label></td>
		         <td><input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="form-control ${empty user.id?'required':''}"/>
					<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if></td>
		         <td class="active"><label class="pull-right"><c:if test="${empty user.id}"><font color="red">*</font></c:if>确认密码:</label></td>
		         <td><input id="confirmNewPassword" name="confirmNewPassword" type="password"  class="form-control ${empty user.id?'required':''}" value="" maxlength="50" minlength="3" equalTo="#newPassword"/></td>
		      </tr>
		      
		       <tr>
		         <td class="active"><label class="pull-right">邮箱:</label></td>
		         <td><form:input path="email" htmlEscape="false" maxlength="100" class="form-control email"/></td>
		         <td class="active"><label class="pull-right">电话:</label></td>
		         <td><form:input path="phone" htmlEscape="false" maxlength="100" class="form-control"/></td>
		      </tr>
		      
		      <tr>
		         <td class="active"><label class="pull-right">手机:</label></td>
		         <td><form:input path="mobile" htmlEscape="false" maxlength="100" class="form-control"/></td>
		         <td class="active"><label class="pull-right">是否允许登录:</label></td>
		         <td><form:select path="loginFlag"  class="form-control">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select></td>
		      </tr>
		      
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>用户角色:</label></td>
		         <td colspan="3">
					<div class="row">
			         	<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" cssClass="i-checks required"
			         	element="div class='col-xs-4 col-sm-3 col-md-6'/><span></span"/>
			         	<label id="roleIdList-error" class="error" for="roleIdList"></label>
		         	</div>
		         </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">是否主管领导:</label></td>
		         <td>
		         	<form:select path="isSupper"  class="form-control">
						<form:options items="${fns:getDictList('is_supper')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
		         </td>
		         <td class="active"><label class="pull-right">是否项目负责人:</label></td>
		         <td>
		         	<form:select path="isPrincipal"  class="form-control">
						<form:options items="${fns:getDictList('is_supper')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
		         </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">出生日期:</label></td>
		         <td class="width-35">
		         	<div class='input-group form_datetime' id='birth'>
	                    <input type='text'  name="birth" class="form-control"  value="<fmt:formatDate value="${user.birth}" pattern="yyyy-MM-dd"/>"/>
	                    <span class="input-group-addon">
	                        <span class="glyphicon glyphicon-calendar"></span>
	                    </span>
	                </div>
		         </td>
		         <td class="active"><label class="pull-right">在岗状态:</label></td>
		         <td>
		         	<form:input path="userState" htmlEscape="false" maxlength="64" class="form-control"/>
		         </td>
		      </tr>
		      <tr>
		      	 <td class="active"><label class="pull-right">排序:</label></td>
		         <td>
		         	<form:input path="sort" htmlEscape="false" maxlength="5" class="form-control isIntGteZero"/>
		         </td>
		         <td class="active"></td><td></td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">专业:</label></td>
		         <td colspan="3">
					<div class="row">
			         	<form:checkboxes path="specialtyIdList" items="${allSpecialtys}" itemLabel="name" itemValue="id" htmlEscape="false" cssClass="i-checks"
			         	element="div class='col-xs-3 col-sm-2 col-md-1'/><span></span"/>
			         	<label id="specialtyIdList-error" class="error" for="specialtyIdList"></label>
		         	</div>
		         </td>
		      </tr>
		      <tr>
		      	 <td class="active"><label class="pull-right">备注:</label></td>
		         <td colspan="3"><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/></td>
		      </tr>
		      <c:if test="${not empty user.id}">
		       <tr>
		         <td class=""><label class="pull-right">创建时间:</label></td>
		         <td><span class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></span></td>
		         <td class=""><label class="pull-right">最后登陆:</label></td>
		         <td><span class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></span></td>
		      </tr>
		     </c:if>
		      </tbody>
		      </table>
	</form:form>
</body>
</html>