<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>专业信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if($("#sortFlag").val() != "1"){
			  if(validateForm.form()){
				  $table = table;
				  $topIndex = index;
				  jp.loading();
				  $("#inputForm").submit();
				  return true;
			  }
		  }else{
				jp.info("已存在的排序数值，请重新填写!");
		  }
		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/project/specialty/save",$('#inputForm').serialize(),function(data){
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
			
		});
		
		//判断是否重复的排序
		function findSort(obj){
			var sort = $(obj).val();
			if($("#oldSort").val() != sort){
				jp.get("${ctx}/project/specialty/findSort?sort="+sort,function(data){
					if(data > 0){
						jp.info("已存在的排序数值，请重新填写!");
						$("#sortFlag").val("1");
					}else{
						$("#sortFlag").val("0");
					}
				});
			}else{
				$("#sortFlag").val("0");
			}
		}
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="specialty" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>专业名称：</label></td>
					<td class="width-35">
						<input type="hidden" id="sortFlag" value="0"/>
						<form:input path="name" htmlEscape="false" maxlength="64"    class="form-control required"/>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>排序：</label></td>
					<td class="width-35">
						<input type="hidden" id="oldSort" value="${specialty.sort}"/>
						<form:input path="sort" htmlEscape="false" maxlength="5"  onblur="findSort(this)"  class="form-control isIntGteZero required"/>
					</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>