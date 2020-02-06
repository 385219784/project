<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目信息管理</title>
	<meta name="decorator" content="ani"/>
	<link href="${pageContext.request.contextPath}/webpage/modules/project/proState/proState.css"  rel="stylesheet" />
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
			/* if($("#id").val() == null || $("#id").val() == ""){
				$("#num").attr("maxlength","3").addClass("isEnglish");
			} */
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/project/project/save",$('#inputForm').serialize(),function(data){
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

			$('#demand').datetimepicker({
				 format: "YYYY-MM-DD",
				 locale: moment.locale('zh-cn')
		    });
			
			$('#startDate').datetimepicker({
				 format: "YYYY-MM-DD",
				 locale: moment.locale('zh-cn')
		    });
			
			$('#endDate').datetimepicker({
				 format: "YYYY-MM-DD",
				 locale: moment.locale('zh-cn')
		    });
			
			/* //根据项目类别查找设计类别
			if($("#proType").val().trim() != null && $("#proType").val().trim() != ""){
				var proType = $("#proType").val();
				$("#oldProTypeValue").val(proType);
				jp.post("${ctx}/sys/dict/getSonTypes",{typeValue:proType,parentType:"pro_type",sonType:"design_type"},
	    		function(data){
					var str = "<option value=''></option>";
					if(data != '' && data != null){
		    			for(var i=0; i<data.length; i++){
		    				str = str + "<option value="+data[i].value+">"+data[i].label+"</option>";
		    			}
					}
	    			$("#designType").html(str);
	    			$("#designType").val($("#designValue").val());
	    		});
			} */
			
		});
		
		//项目类别被点击
		/* function proTypes(obj){
			//得到选中的id
			var proTypeValue = $(obj).val();
			//判断这次和上次选中的值是否一样
			if(proTypeValue != $("#oldProTypeValue").val()){
				$("#oldProTypeValue").val(proTypeValue);
				jp.post("${ctx}/sys/dict/getSonTypes",{typeValue:proTypeValue,parentType:"pro_type",sonType:"design_type"},
	    		function(data){
					var str = "<option value=''></option>";
					if(data != '' && data != null){
		    			for(var i=0; i<data.length; i++){
		    				str = str + "<option value="+data[i].value+">"+data[i].label+"</option>";
		    			}
					}
	    			$("#designType").html(str);
	    		});
			}
		} */
		
		function proState(oldState,obj){
			var newState = $(obj).val();
			if(oldState == 3 && newState == 1){
				$("#state").val("3");
				jp.info("该项目已待批复,不能改变状态为正在进行!");
			}
		}
		
		//全选
		function selectAll(obj,idx){
			//全选
			if($(obj).is(":checked")){
				$(".select"+idx).each(function(){
					$(this).next().addClass("checked");
					$(this).next().children(":first").prop("checked","checked");
				});
			}else{
				$(".select"+idx).each(function(){
					$(this).next().removeClass("checked");
					$(this).next().children(":first").removeAttr("checked");
				});
			}
		}
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="project" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>项目编号：</label></td>
					<td class="width-35">
						<form:input path="num" htmlEscape="false" maxlength="64"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>项目简称：</label></td>
					<td class="width-35">
						<form:input path="proShortened" htmlEscape="false" maxlength="64"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>项目名称：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="name" htmlEscape="false" maxlength="64"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>项目类别：</label></td>
					<td class="width-35">
						<!-- <input type="hidden" id="oldProTypeValue"/> --> 
						<form:select id="proType" path="type" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('pro_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>状态：</label></td>
					<td class="width-35">
						<form:select path="state" class="form-control required" onchange="proState('${project.state}',this)">
							<form:options items="${fns:getDictList('pro_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<%-- <td class="width-15 active"><label class="pull-right"><font color="red">*</font>设计阶段：</label></td>
					<td class="width-35">
						<input type="hidden" id="designValue" value="${project.designType}"/> 
						<form:select id="designType" path="designType" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('design_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td> --%>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>进度：</label></td>
					<td class="width-35">
						<form:select path="planState" class="form-control required">
							<form:option value=""></form:option>
							<form:options items="${fns:getDictList('plan_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">进度要求：</label></td>
					<td class="width-35">
						<div class='input-group form_datetime' id='demand'>
		                    <input type='text'  name="demand" class="form-control"  value="<fmt:formatDate value="${project.demand}" pattern="yyyy-MM-dd"/>"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>起始时间：</label></td>
					<td class="width-35">
						<div class='input-group form_datetime' id='startDate'>
		                    <input type='text'  name="startDate" class="form-control required"  value="<fmt:formatDate value="${project.startDate}" pattern="yyyy-MM-dd"/>"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>终止时间：</label></td>
					<td class="width-35">
						<div class='input-group form_datetime' id='endDate'>
		                    <input type='text'  name="endDate" class="form-control required"  value="<fmt:formatDate value="${project.endDate}" pattern="yyyy-MM-dd"/>"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" style="display:table-cell; vertical-align:middle"><label class="pull-right"><font color="red">*</font>主管领导：</label></td>
					<td class="width-35" colspan="3">
						<div class="row">
							<form:checkboxes path="supervisorIdList" items="${allSupervisor}" itemLabel="userName" itemValue="userId" htmlEscape="false" cssClass="i-checks required"
							element="div class='col-xs-3 col-sm-2 col-md-1'/><span></span"/>
							<label id="supervisorIdList-error" class="error" for="supervisorIdList"></label>
						</div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" style="display:table-cell; vertical-align:middle"><label class="pull-right"><font color="red">*</font>项目负责人：</label></td>
					<td class="width-35" colspan="3">
						<div class="row">
							<form:checkboxes path="principalIdList" items="${allPrincipal}" itemLabel="userName" itemValue="userId" htmlEscape="false" cssClass="i-checks required"
							element="div class='col-xs-3 col-sm-2 col-md-1'/><span></span"/>
							<label id="principalIdList-error" class="error" for="principalIdList"></label>
						</div>
						<%-- <sys:userselect id="principal" name="principal.id" value="${project.principal.id}" labelName="principal.name" labelValue="${project.principal.name}"
							    cssClass="form-control required"/> --%>
					</td>
				</tr>
				<c:forEach items="${allSpecialtyUser}" var="specialtyUser" varStatus="st">
					<tr>
						<td class="width-15 active text-right" style="display:table-cell; vertical-align:middle">
							<%-- <label class="pull-right">${specialtyUser.specName}：</label> --%>
							<div class="checkbox-custom checkbox-default left-spacing">
								<input id="rememberMe${st.index}" type="checkbox" onclick="selectAll(this,'${st.index}')" class="rememberMe">
								<label for="rememberMe${st.index}" style="font-wight:bold;">${specialtyUser.specName}:</label>
							</div>
						</td>
						<td class="width-35" colspan="3">
							<div class="row">
								<form:checkboxes path="specialtyUserIdList" items="${specialtyUser.supervisorList}" itemLabel="userName" itemValue="userId" htmlEscape="false" cssClass="i-checks"
								element="div class='col-xs-3 col-sm-2 col-md-1'/><span class='select${st.index}'></span"/>
								<label id="specialtyUserIdList-error" class="error" for="specialtyUserIdList"></label>
							</div>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="width-15 active"><label class="pull-right">上年度计奖比例：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="content" htmlEscape="false" rows="4" maxlength="255"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">归档状态：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="question" htmlEscape="false" rows="4" maxlength="255"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">任务单编号：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="feature" htmlEscape="false" rows="4" maxlength="255"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>