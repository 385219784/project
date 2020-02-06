<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
 <meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <title>我的日程</title>
 <meta name="decorator" content="ani"/>
<!--
	说明：需要整合农历节气和节日，引入fullcalendar.js fullcalendar2.css
	不需要则引入：fullcalendar.min.js fullcalendar.css
-->
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
<%@include file="workAttenceCal.js" %>
<%@include file="workAttenceList.js" %>
<script type="text/javascript">
</script>
<style>

	#external-events {
		padding: 0 10px;
		background: #eee;
		text-align: left;
	}
		
	#external-events h4 {
		font-size: 16px;
		margin-top: 0;
		padding-top: 1em;
	}
		
	#external-events .fc-event {
		margin: 10px 0;
		cursor: pointer;
	}
		
	#external-events p {
		margin: 1.5em 0;
		font-size: 11px;
		color: #666;
	}
		
	#external-events p input {
		margin: 0;
		vertical-align: middle;
	}
</style>
</head>
<body>
    <div class="conter-wrapper">
	<div class="panel panel-default">
	<div class="panel-body">
		<!-- 搜索 -->
	
		<div class="row">
			
		
			<div class="col-sm-3">	
			<form:form id="searchForm"    class="form form-horizontal well clearfix">
			<input  type="hidden"  name="dateStr" id="dateStr"  value=''/>
		<div class="col-xs-12 col-sm-12 col-md-12">
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
			 <div class="col-xs-12 col-sm-12 col-md-12">
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
			</form:form>					
				<table id="workAttence"></table>
			</div>
			<div class="col-sm-9">
				<div id="calendar"></div>
			</div>
		</div>
    </div>
    </div>
    </div>
</body>
</html>