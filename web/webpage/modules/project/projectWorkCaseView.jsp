<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>个人工作情况统计管理</title>
	<meta name="decorator" content="ani"/>
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
					<td class="width-15 active"><label class="pull-right">主管领导：</label></td>
					<td class="width-35">
						${projectWorkCase.supervisor}
					</td>
					<td class="width-15 active"><label class="pull-right">项目负责人：</label></td>
					<td class="width-35">
						${projectWorkCase.principal}
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
					<td class="width-15 active"><label class="pull-right">进度：</label></td>
					<td class="width-35">
						${ fns:getDictLabel (projectWorkCase.plan, 'plan_state', '-')}
					</td>
					<td class="width-15 active"><label class="pull-right">计划完成时间：</label></td>
					<td class="width-35">
						<fmt:formatDate value="${projectWorkCase.finishDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">设计内容：</label></td>
					<td class="width-35" colspan="3">
						${projectWorkCase.content}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">制约因素：</label></td>
					<td class="width-35" colspan="3">
						${projectWorkCase.difficulty}
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>