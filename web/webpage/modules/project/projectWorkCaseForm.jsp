<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>个人工作情况统计管理</title>
	<meta name="decorator" content="ani"/>
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
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/project/projectWorkCase/save",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jp.success(data.msg);
	                    	jp.close($topIndex);//关闭dialog

	                    }else{
            	  			jp.error(data.msg);
	                    }
					})
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
			
	        $('#finishDate').datetimepicker({
				 format: "YYYY-MM-DD",
				 locale: moment.locale('zh-cn')
		    });
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="projectWorkCase" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目编号：</label></td>
					<td class="width-35">
						<input type="hidden" name="proId" value="${projectWorkCase.proId}"/>
						<input type="hidden" name="specId" value="${projectWorkCase.specId}"/>
						<input type="hidden" name="userId" value="${projectWorkCase.userId}"/>
						${projectWorkCase.proNum}
					</td>
					<td class="width-15 active"></td>
					<td class="width-35">
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目名称：</label></td>
					<td class="width-35" colspan="3">
						${projectWorkCase.proName}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目类别：</label></td>
					<td class="width-35">
						${ fns:getDictLabel (projectWorkCase.proType, 'pro_type', '-')}
					</td>
					<td class="width-15 active"><label class="pull-right">设计类别：</label></td>
					<td class="width-35">
						${ fns:getDictLabel (projectWorkCase.designType, 'design_type', '-')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目负责人：</label></td>
					<td class="width-35">
						${projectWorkCase.principal}
					</td>
					<td class="width-15 active"><label class="pull-right">主管领导：</label></td>
					<td class="width-35">
						${projectWorkCase.supervisor}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">专业：</label></td>
					<td class="width-35">
						${projectWorkCase.specialty}
					</td>
					<td class="width-15 active"><label class="pull-right">状态：</label></td>
					<td class="width-35">
						${ fns:getDictLabel (projectWorkCase.state, 'pro_state', '-')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>进度：</label></td>
					<td class="width-35">
						<form:select path="plan" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('plan_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>计划完成时间：</label></td>
					<td class="width-35">
						<div class='input-group form_datetime' id='finishDate'>
		                    <input type='text'  name="finishDate" class="form-control required"  value="<fmt:formatDate value="${projectWorkCase.finishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>设计内容：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="content" htmlEscape="false" rows="4" maxlength="255"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">制约因素：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="difficulty" htmlEscape="false" rows="4" maxlength="255"    class="form-control"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>