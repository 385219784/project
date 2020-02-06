<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>个人工作汇总</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="proPersonSumList.js" %>
	<script src="${pageContext.request.contextPath}/static/plugin/bootstrapTable/bootstrap-table-fixed-columns.js"></script>
	<link href="${pageContext.request.contextPath}/static/plugin/bootstrapTable/bootstrap-table-fixed-columns.css" rel="stylesheet" />
	<style type="text/css">
		#proSum{
		    table-layout: fixed;
		}
	</style>
	<script type="text/javascript">
		$(window).resize(function () {
			$("#proSum").bootstrapTable('resetView');
		});
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="workAttence" class="form form-horizontal well clearfix">
			 <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="开始时间：">开始时间：</label>
				<div class="col-xs-12">
					<div class='input-group date' id='workDateStart' >
	                   <input type='text'  name="workDateStart" id="start" class="form-control input-sm"  />
	                   <span class="input-group-addon">
	                       <span class="glyphicon glyphicon-calendar"></span>
	                   </span>
		            </div>	
				</div>
			</div>
			 <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="结束时间：">结束时间：</label>
				<div class="col-xs-12">
					<div class='input-group date' id='workDateEnd' >
	                   <input type='text'  name="workDateEnd" id="end" class="form-control input-sm"  />
	                   <span class="input-group-addon">
	                       <span class="glyphicon glyphicon-calendar"></span>
	                   </span>
		            </div>	
				</div>
			</div>
			<div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="专业人员：">专业人员：</label>
				<form:input path="user.name" htmlEscape="false"  class=" form-control input-sm"/>
			</div>
			<div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目编号：">项目编号：</label>
				<form:input path="proNum" htmlEscape="false"  class=" form-control input-sm"/>
			</div>
			  <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目名称：">项目名称：</label>
				<form:input path="proName" htmlEscape="false"  class=" form-control input-sm"/>
			</div>
			<div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目简称：">项目简称：</label>
				<form:input path="proShortened" htmlEscape="false"  class=" form-control input-sm"/>
			</div>
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
	<div id="toolbar">
	
		<a id="export" class="btn btn-warning" onclick="exportExcel()"><i class="fa fa-file-excel-o"></i> 导出</a>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
				
	</div>
		
	<!-- 表格 -->
	<table id="proSum"   data-toolbar="#toolbar">
	  
    <%--     <thead>
            <tr>
                <th data-rowspan="2">序号</th>
                <th data-rowspan="2">项目编号</th>
                <th data-rowspan="2">项目名称</th>
                <th data-rowspan="2">设计类别</th>
                <th data-rowspan="2">主管领导</th>
                <th data-rowspan="2">项目负责人</th>
                <th data-rowspan="2">总工日</th>
                <th data-rowspan="2">总加班小时</th>
              	<c:forEach items="${specList}" var="spec">
				 <th data-colspan="2">${spec.name}</th>
				</c:forEach>
            </tr>
            <tr>
            	<c:forEach items="${specList}" var="spec">
					<th >工日</th>
           			<th >加班</th> 
				</c:forEach>
            <tr>
        </thead> --%>
		
	
	
	
	
	
	</table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>