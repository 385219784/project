<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目一览表</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="projectList.js" %>
	<link href="${pageContext.request.contextPath}/webpage/modules/project/proType/proType.css"  rel="stylesheet" />
	<%@include file="/webpage/modules/project/proType/proType.js" %>
	<script src="${pageContext.request.contextPath}/static/plugin/bootstrapTable/bootstrap-table-fixed-columns.js"></script>
	<link href="${pageContext.request.contextPath}/static/plugin/bootstrapTable/bootstrap-table-fixed-columns.css" rel="stylesheet" />
	
	<style type="text/css">
		#projectTable{
		    table-layout: fixed;
		}
	</style>
	<script type="text/javascript">
		/* //项目类别被点击
		function proType(obj){
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
			var str = "";
			str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
			str = str + "	<input id='rememberMe1' type='checkbox' onclick='rememberMe(this)' class='rememberMe' checked='checked' value='1'>";
			str = str + "	<label for='rememberMe1'>正在进行</label>";
			str = str + "</div>";
			str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
			str = str + "	<input id='rememberMe2' type='checkbox' onclick='rememberMe(this)' class='rememberMe' value='2'>";
			str = str + "	<label for='rememberMe2'>待评审</label>";
			str = str + "</div>";
			str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
			str = str + "	<input id='rememberMe3' type='checkbox' onclick='rememberMe(this)' class='rememberMe' value='3'>";
			str = str + "	<label for='rememberMe3'>待批复</label>";
			str = str + "</div>";
			str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
			str = str + "	<input id='rememberMe4' type='checkbox' onclick='rememberMe(this)' class='rememberMe' value='4'>";
			str = str + "	<label for='rememberMe4'>暂停</label>";
			str = str + "</div>";
			str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
			str = str + "	<input id='rememberMe5' type='checkbox' onclick='rememberMe(this)' class='rememberMe' value='5'>";
			str = str + "	<label for='rememberMe5'>完成</label>";
			str = str + "</div>";
		
			$("#proStateInfo").html(str)
		});
	
		//监听所有的复选框
		function rememberMe(obj){
			var proState = "";
			for(var i=0 ;i<$(".rememberMe").length; i++){
				if($($(".rememberMe")[i]).is(":checked")){
					proState = proState + $($(".rememberMe")[i]).val() + ",";
				}
			}
			if(proState != ""){
				proState = proState.substring(0,proState.length-1);
			}else{
				proState = "9999999999999999999999";
			}
			$("#proState").val(proState);
			$($("#proStateInfo").attr("tableId")).bootstrapTable('refresh');
		}
	
		$(window).resize(function () {
			$("#projectTable").bootstrapTable('resetView');
		});
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<!-- <div class="panel-heading">
		<h3 class="panel-title">项目信息列表</h3>
	</div> -->
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="project" class="form form-horizontal well clearfix">
			<div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="开始起始时间：">开始起始时间：</label>
				<div class="col-xs-12">
					<div class='input-group date' id='startDate1' >
	                   <input type='text'  name="startDate1" class="form-control input-sm"  />
	                   <span class="input-group-addon">
	                       <span class="glyphicon glyphicon-calendar"></span>
	                   </span>
		            </div>	
				</div>
			</div>
			<div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="开始结束时间：">开始结束时间：</label>
				<div class="col-xs-12">
					<div class='input-group date' id='startDate2' >
	                   <input type='text'  name="startDate2" class="form-control input-sm"  />
	                   <span class="input-group-addon">
	                       <span class="glyphicon glyphicon-calendar"></span>
	                   </span>
		            </div>	
				</div>
			</div>
			 <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="终止起始时间：">终止起始时间：</label>
				<div class="col-xs-12">
					<div class='input-group date' id='endDate1' >
	                   <input type='text'  name="endDate1" class="form-control input-sm"  />
	                   <span class="input-group-addon">
	                       <span class="glyphicon glyphicon-calendar"></span>
	                   </span>
		            </div>	
				</div>
			</div>
			<div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="终止结束时间：">终止结束时间：</label>
				<div class="col-xs-12">
					<div class='input-group date' id='endDate2' >
	                   <input type='text'  name="endDate2" class="form-control input-sm"  />
	                   <span class="input-group-addon">
	                       <span class="glyphicon glyphicon-calendar"></span>
	                   </span>
		            </div>	
				</div>
			</div>
			 <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目编号：">项目编号：</label>
				<form:input path="num" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			</div>
			 <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目名称：">项目名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			</div>
			<%-- <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目简称：">项目简称：</label>
				<form:input path="proShortened" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			</div> --%>
			 <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目类别：">项目类别：</label>
				<form:select path="type"  class="form-control m-b input-sm">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pro_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			<%--  <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="设计阶段：">设计阶段：</label>
				<form:select id="designType" path="designType"  class="form-control m-b input-sm">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('design_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div> --%>
			<div class="col-xs-6  col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="主管领导：">主管领导：</label>
				<form:input path="supervisor" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			</div>
			<div class="col-xs-6  col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="项目负责人：">项目负责人：</label>
				<form:input path="principal" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			</div>
			<div class="col-xs-6  col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="人员名称：">人员名称：</label>
				<form:input path="user.name" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			</div>
			<%--  <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="状态：">状态：</label>
				<form:select path="state"  class="form-control m-b input-sm">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pro_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div> --%>
			<!-- 项目状态 -->
			<input type="hidden" id="proState" name="state" value="1">
			<!-- 项目状态 -->
			<input type="hidden" id="proType" name="proType" value="7,9,10,11">
			<%-- <div class="col-xs-6 col-sm-4 col-md-3">
				<label class="label-item single-overflow pull-left" title="进度：">进度：</label>
				<form:select path="planState"  class="form-control m-b input-sm">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('plan_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div> --%>
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
			<shiro:hasPermission name="project:project:add">
				<a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="project:project:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="project:project:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="project:project:review">
				<button class="state btn btn-info" disabled onclick="updateState('',2,'待评审')">
	            	<i class="glyphicon glyphicon-time"></i> 待评审
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="project:project:reply">
				<button class="state btn btn-primary" disabled onclick="updateState('',3,'待批复')">
	            	<i class="glyphicon glyphicon-time"></i> 待批复
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="project:project:stop">
				<button class="state btn btn-remove" disabled onclick="updateState('',4,'暂停')">
	            	<i class="glyphicon glyphicon-stop"></i> 暂停
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="project:project:finish">
				<button class="state btn btn-success" disabled onclick="updateState('',5,'完成')">
	            	<i class="glyphicon glyphicon-ok"></i> 完成
	        	</button>
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="project:project:export">
				<a id="export" class="btn btn-warning" onclick="exportExcel()"><i class="fa fa-file-excel-o"></i> 导出</a>
			</shiro:hasPermission>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
			<!-- 项目状态 -->
			<span id="proStateInfo" tableId="#projectTable"></span>
			<!-- 项目类别-->
			<span id="proTypeInfo" tableId="#projectTable"></span>
		 </div>
		
	<!-- 表格 -->
	<table id="projectTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="project:project:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="project:project:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="project:project:proceed">
        <li data-item="proceed"><a>正在进行</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="project:project:review">
        <li data-item="review"><a>待评审</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="project:project:reply">
        <li data-item="reply"><a>待批复</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="project:project:stop">
        <li data-item="stop"><a>暂停</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="project:project:finish">
        <li data-item="finish"><a>完成</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>