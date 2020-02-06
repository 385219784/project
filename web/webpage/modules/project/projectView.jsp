<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目信息管理</title>
	<meta name="decorator" content="ani"/>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="project" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目编号：</label></td>
					<td class="width-35">
						${project.num}
					</td>
					<td class="width-15 active"><label class="pull-right">项目简称：</label></td>
					<td class="width-35">
						${project.proShortened}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目名称：</label></td>
					<td class="width-35" colspan="3">
						${project.name}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目类别：</label></td>
					<td class="width-35">
						${ fns:getDictLabel (project.type, 'pro_type', '-')}
					</td>
					<%-- <td class="width-15 active"><label class="pull-right">设计类别：</label></td>
					<td class="width-35">
						${ fns:getDictLabel (project.designType, 'design_type', '-')}
					</td> --%>
					<td class="width-15 active"><label class="pull-right">状态：</label></td>
					<td class="width-35">
						${ fns:getDictLabel (project.state, 'pro_state', '-')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">进度：</label></td>
					<td class="width-35">
						${ fns:getDictLabel (project.planState, 'plan_state', '-')}
					</td>
					<td class="width-15 active"><label class="pull-right">进度要求：</label></td>
					<td class="width-35">
						<fmt:formatDate value="${project.demand}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<!-- <tr>
					<td class="width-15 active"></td>
					<td class="width-35">
					</td>
				</tr> -->
				<tr>
					<td class="width-15 active"><label class="pull-right">起始时间：</label></td>
					<td class="width-35">
						<fmt:formatDate value="${project.startDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td class="width-15 active"><label class="pull-right">终止时间：</label></td>
					<td class="width-35">
						<fmt:formatDate value="${project.endDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">主管领导：</label></td>
					<td class="width-35" colspan="3">
						${project.supervisor}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目负责人：</label></td>
					<td class="width-35" colspan="3">
						${project.principal}
					</td>
				</tr>
				<c:forEach items="${allSpecialtyUser}" var="specialtyUser" varStatus="st">
					<tr>
						<td class="width-15 active"><label class="pull-right">${specialtyUser.specName}：</label></td>
						<td class="width-35" colspan="3">
							${specialtyUser.userName}
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="width-15 active"><label class="pull-right">上年度计奖比例：</label></td>
					<td class="width-35" colspan="3">
						${project.content}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">归档状态：</label></td>
					<td class="width-35" colspan="3">
						${project.question}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">任务单编号：</label></td>
					<td class="width-35" colspan="3">
						${project.feature}
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>