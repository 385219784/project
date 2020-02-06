<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>专业信息管理</title>
	<meta name="decorator" content="ani"/>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="specialty" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">专业名称：</label></td>
					<td class="width-35">
						${specialty.name}
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">排序：</label></td>
					<td class="width-35">
						${specialty.sort}
					</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>