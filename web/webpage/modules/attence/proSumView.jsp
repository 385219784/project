<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目工日汇总详情</title>
	<meta name="decorator" content="ani"/>
</head>
<body class="bg-white">
	<form:form id="inputForm" modelAttribute="item" class="form-horizontal">
		<table class="table table-bordered">
		   <tbody>
		   		<tr>
		   			<td class="text-center">序号</td>
		   			<td class="text-center">专业人员</td>
		   			<td class="text-center">总工时</td>
		   			<td class="text-center">总加班(小时)</td>
		   		</tr>
				<c:forEach items="${workAttenceList}" var="workAttence" varStatus="st">
					<tr>
			   			<td class="text-center">${st.index+1}</td>
			   			<td class="text-center">${workAttence.user.name}</td>
			   			<td class="text-right">${workAttence.sumWorkTime}</td>
			   			<td class="text-right">${workAttence.sumOverTime}</td>
		   			</tr>
				</c:forEach>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>