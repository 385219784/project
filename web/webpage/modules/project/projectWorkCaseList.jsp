<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>个人项目</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="projectWorkCaseList.js" %><script src="${pageContext.request.contextPath}/static/plugin/bootstrapTable/bootstrap-table-fixed-columns.js"></script>
	<link href="${pageContext.request.contextPath}/static/plugin/bootstrapTable/bootstrap-table-fixed-columns.css" rel="stylesheet" />
	
	<link href="${pageContext.request.contextPath}/webpage/modules/project/proState/proState.css"  rel="stylesheet" />
	<%@include file="/webpage/modules/project/proState/proState.js" %>
	<style type="text/css">
		#projectWorkCaseTable{
		    table-layout: fixed;
		}
	</style>
	<script type="text/javascript">
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
		
		$(function(){
			//查找所有专业类别
			jp.get("${ctx}/project/specialty/getSpecialtyAll",function(data){
				var str = "<option value=''></option>";
				if(data != '' && data != null){
	    			for(var i=0; i<data.length; i++){
	    				str = str + "<option value="+data[i].id+">"+data[i].name+"</option>";
	    			}
				}
    			$("#specialty").html(str);
			});
			
			$('#startDate').datetimepicker({
				 format: "YYYY-MM-DD",
				 locale: moment.locale('zh-cn')
		   });
			
			$('#endDate').datetimepicker({
				 format: "YYYY-MM-DD",
				 locale: moment.locale('zh-cn')
		   });
		});
		
		$(window).resize(function () {
			$("#projectWorkCaseTable").bootstrapTable('resetView');
		});
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<!-- <div class="panel-heading">
		<h3 class="panel-title">个人工作情况统计列表</h3>
	</div> -->
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="projectWorkCase" class="form form-horizontal well clearfix">
			<div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="开始时间：">开始时间：</label>
				<div class="col-xs-12">
					<div class='input-group date' id='startDate' >
	                   <input type='text'  name="startDate" id="start" class="form-control input-sm"  />
	                   <span class="input-group-addon">
	                       <span class="glyphicon glyphicon-calendar"></span>
	                   </span>
		            </div>	
				</div>
			</div>
			 <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="终止时间：">终止时间：</label>
				<div class="col-xs-12">
					<div class='input-group date' id='endDate' >
	                   <input type='text'  name="endDate" id="end" class="form-control input-sm"  />
	                   <span class="input-group-addon">
	                       <span class="glyphicon glyphicon-calendar"></span>
	                   </span>
		            </div>	
				</div>
			</div>
			 <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目编号：">项目编号：</label>
				<form:input path="proNum" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			</div>
			 <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目名称：">项目名称：</label>
				<form:input path="proName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			</div>
			<div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目简称：">项目简称：</label>
				<form:input path="proShortened" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			</div>
			 <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目类别：">项目类别：</label>
				<form:select path="proType"  class="form-control m-b input-sm">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pro_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			<%--  <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="设计类别：">设计类别：</label>
				<form:select id="designType" path="designType"  class="form-control m-b input-sm">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('design_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div> --%>
			 <%-- <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目负责人：">项目负责人：</label>
				<form:input path="principal" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			</div> --%>
			<div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="专业：">专业：</label>
				<select id="specialty" name="specialty" class="form-control m-b input-sm">
				</select>
			</div>
			 <%-- <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目状态：">项目状态：</label>
				<form:select path="state"  class="form-control m-b input-sm">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pro_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div> --%>
			<!-- 项目状态 -->
			<input type="hidden" id="proState" name="state" value="1,2,3">
		 <div class="col-xs-6 col-sm-4 col-md-3">
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-xs"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-xs" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar" style="width:350%;">
			<%-- <shiro:hasPermission name="project:projectWorkCase:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="project:projectWorkCase:export">
				<a id="export" class="btn btn-warning" onclick="exportExcel()"><i class="fa fa-file-excel-o"></i> 导出</a>
			</shiro:hasPermission>
        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
				<i class="fa fa-search"></i> 检索
			</a>
			<!-- 项目状态 -->
			<span id="proStateInfo" tableId="#projectWorkCaseTable"></span>
			<a id="successInfo" style="margin-left:50px;color:red;"></a>
	</div>
		
	<!-- 表格 -->
	<table id="projectWorkCaseTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<%-- <shiro:hasPermission name="project:projectWorkCase:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission> --%>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>