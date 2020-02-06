<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>项目管理系统</title>
	<meta name="decorator" content="ani"/>
	<script src="${ctxStatic}/plugin/js-menu/contabs.js"></script>
	<script src="${ctxStatic}/common/js/fullscreen.js"></script>
	<link id="theme-tab" href="${ctxStatic}/plugin/js-menu/menuTab-${cookie.theme.value==null?'blue':cookie.theme.value}.css" rel="stylesheet" />
	<%@ include file="/webpage/include/systemInfoSocket-init.jsp"%>
	<%-- <%@ include file="/webpage/include/layIM-init.jsp"%> --%>
	
</head>

<body class="">
	<aside id="sidebar">
	<div class="sidenav-outer">
		<div class="side-widgets">
			<div class="text-center" style="padding-top:10px;">
				<div class="clearfix" style="display:inline-block;margin:0 auto;">
					<div style="display:inline-block;float:right;">
						<ul class="nav navbar-nav">
							<li class="dropdown">
								<%-- <a href="#"   class="dropdown-toggle" data-toggle="dropdown" role="button"><span class="glyphicon glyphicon-envelope" style="color:#ffffff;"></span><span class="badge badge-green">${noReadCount}</span></a> --%>
								
								 <ul class="dropdown-menu animated fadeIn" style="right: -20px;">
									<%-- <li class="messages-top text-center" style="font-size: 10px;color: gray;">
										你有  ${noReadCount} 封未读邮件.
									</li> --%>
			                          
			                            	 <c:forEach items="${mailPage.list}" var="mailBox">
			                            	 
			                            	  <li class="dropdown-messages">
					                            	 <a class="J_menuItem" title="站内信" href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
						                                           
														<div class="message-sender">
															${mailBox.sender.name }<small class="pull-right">${fns:getTime(mailBox.sendtime)}前</small>
														</div>
														<div class="message-header">
															 ${mailBox.mail.overview} <small class="pull-right">
				                                            <fmt:formatDate value="${mailBox.sendtime}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
														</div>
														
													</a>
				                                </li>
				                               
			                                </c:forEach>
			                                <li class=" text-center">
			                                        <a class="J_menuItem" href="${ctx}/iim/mailBox/list?orderBy=sendtime desc">
			                                           <font color='gray'> <i class="fa fa-envelope"></i>  查看所有邮件</font>
			                                        </a>
			                                </li>
			                            </ul>  
						
							</li> 
							<li class="dropdown">
								<a  href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">
									<span class="glyphicon glyphicon-bell" style="color:#ffffff;"></span><span class="badge badge-red">${count }</span>
								</a>
								<a href="${ctx}/logout" >
									<span class="badge badge-red">安全退出</span>
								</a>
								<ul class="dropdown-menu animated fadeIn" style="right: -20px;">
									<li class="messages-top text-center" style="font-size: 10px;color: gray;">
										你有 ${count } 条新通知.	 
									</li>
									    
			                     <c:forEach items="${page.list}" var="oaNotify">
			                     
			                     	<li class="dropdown-notifications">
										<a class="J_menuItem" href="${ctx}/oa/oaNotify/form?id=${oaNotify.id}&isSelf=true">
											<div class="notification">
												<i class="fa fa-bell-o"></i> ${fns:abbr(oaNotify.title,50)}
												<span class="pull-right text-muted small">${fns:getTime(oaNotify.updateDate)}前</span>
											</div>
										</a>
									</li>
			                      
								 </c:forEach>
								</ul> 
							</li>
						</ul>
					</div>
					<div style="display:inline-block;float:right;">
						<a  href="#"><img class="img-circle user-avatar"  src="<c:if test="${fns:getUser().photo == null || fns:getUser().photo==''}">${ctxStatic}/common/images/flat-avatar.png</c:if> <c:if test="${fns:getUser().photo != null && fns:getUser().photo!=''}">${fns:getUser().photo}</c:if>" class="user-avatar" /></a>
					</div>
				</div> 	
				<li class="dropdown admin-dropdown"  >
					<a href="#" class="dropdown-toggle"  data-toggle="dropdown" role="button" aria-expanded="false">
						<font color="white">${fns:getUser().name}<b class="caret"></b></font>
					</a>
					<ul class="dropdown-menu animated fade in" style="left:20px;right: 20px;" role="menu">
						  	 <li><a class="J_menuItem" href="${ctx}/sys/user/imageEdit">修改头像</a>
	                         </li>
	                         <li><a class="J_menuItem" href="${ctx }/sys/user/info">个人资料</a>
	                         </li>
	                         <%-- <li><a class="J_menuItem" href="${ctx }/iim/contact/index">我的通讯录</a>
	                         </li>
	                         <li><a class="J_menuItem" href="${ctx }/iim/mailBox/list">信箱</a>
	                         </li> 
	                         <hr/> --%>
	                         <li><a href="${ctx}/logout">安全退出</a>
	                         </li>
					</ul>
				</li>
			</div>
				
			<div class="widgets-content">
				<div class="menu">
					<div class="menu-body">
								<ul class="nav nav-pills nav-stacked sidenav" id="1">
							<t:aniMenu  menu="${fns:getTopMenu()}"></t:aniMenu>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</aside>	
<section id="body-container" class="animation">
             
	
		<!--选项卡  -->
		<div class="main-container" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
					<div id="breadcrumbs" class="${cookie.tab.value!=false?'breadcrumbs':'un-breadcrumbs'}">
						<%-- <div class="content-tabs">
							<button id="hideMenu" class="roll-nav roll-left-0 J_tabLeft"><i class="fa fa-align-justify"></i>
							</button>
							<button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
							</button>
							<nav class="page-tabs J_menuTabs">
								<div class="page-tabs-content">
									 <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/home">首页</a>
								</div>
							</nav>
							<button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
							</button>
							<a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
		            	</div> --%>
					</div>
	
					<%-- <div class="J_mainContent"  id="content-main" style="${cookie.tab.value!=false?'height:calc(100% - 40px)':'height:calc(100%)'}"> --%>
					<div class="J_mainContent"  id="content-main" style="${cookie.tab.value!=false?'height:calc(100%)':'height:calc(100%)'}">
             		<iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/home" frameborder="1" data-id="${ctx}/home" seamless></iframe>
				</div>
            </div>
        </div>
    </div>
            
           <div class="footer">
                <div class="pull-left"><a href="javascript:java(0)">湖南省交通规划勘察设计院</a> &copy; 2015-2025</div>
            </div>
          
</section>
            
            


<script>

		
$(function(){
	$('#calendar2').fullCalendar({
			eventClick: function(calEvent, jsEvent, view) {
				alert('Event: ' + calEvent.title);
				alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
				alert('View: ' + view.name);
			}
		});
		$('#rtlswitch').click(function() {
			$('body').toggleClass('rtl');
			var hasClass = $('body').hasClass('rtl');
		});
		$('.theme-picker').click(function() {
			changeTheme($(this).attr('data-theme'));
		});
		$('#showMenu').click(function() {
		    $('body').toggleClass('push-right');
		});
    $('#hideMenu').click(function () {
           $('body').removeClass('push-right')
        $('body').toggleClass('push-left');
    });
		$("#switchTab").change(function(){
			if($("#switchTab").is(':checked')){
				 $("#breadcrumbs").attr("class","breadcrumbs");
				 $("#content-main").css("height","calc(100% - 40px)")
				 $.get('${pageContext.request.contextPath}/tab/true?url='+window.top.location.href,function(result){  });
			}else{
				 $("#breadcrumbs").attr("class","un-breadcrumbs");
				 $("#content-main").css("height","calc(100%)")
				 $.get('${pageContext.request.contextPath}/tab/false?url='+window.top.location.href,function(result){  });
			}
		});
		
	//是否弱口令
	var flag = '${sessionScope.isUpPwd}';
	if(flag == "true"){
		jp.open({
		    type: 1, 
		    closeBtn:0 ,
		    area: ['350px', '260px'],
		    title:"当前密码过于简单,请修改密码",
		    content: '<div class="row" style="width:350px;">'
	            +'<div class="col-sm-12" style="margin-top:10px;">'
	            +'	<div class="input-group">'
	            +'		<span class="input-group-addon" style="width:90px;"><font color="red">*</font>旧密码:</span>'
	            +'		<input id="oldPassword" type="password" maxlength="50" minlength="3" autofocus="autofocus" class="form-control required" placeholder="请输入旧密码">'
	            +'	</div>'
	            +'</div>'
	            +'<div class="col-sm-12">'
	            +'	<div style="height:10px;">'
	            +'	</div>'
	            +'</div>'
	            +'<div class="col-sm-12">'
	            +'	<div class="input-group">'
	            +'		<span class="input-group-addon" style="width:90px;"><font color="red">*</font>新密码:</span>'
	            +'		<input id="newPassword" type="password" maxlength="50" minlength="6" class="form-control required" placeholder="请输入新密码">'
	            +'	</div>'
	            +'</div>'
	            +'<div class="col-sm-12">'
	            +'	<div style="height:10px;">'
	            +'	</div>'
	            +'</div>'
	            +'<div class="col-sm-12">'
	            +'	<div class="input-group">'
	            +'		<span class="input-group-addon" style="width:90px;"><font color="red">*</font>确认密码:</span>'
	            +'		<input id="confirmNewPassword" type="password" maxlength="50" minlength="6" class="form-control required" placeholder="请确认新密码">'
	            +'	</div>'
	            +'</div>'
	            +'</div>'
	        ,
		    success: function(index, layero){
	            $(document).keyup(function(event){
	                if(event.keyCode ==13){
	                	submitForm();
	                }
	            });
	        },
		    btn: ['确定'],
		    yes: function(index, layero){
		    	submitForm();
		    	 /* var body = top.layer.getChildFrame('body', index);
		    	 var inputForm = $(body).find('#inputForm');
		         var btn = body.find('#btnSubmit');
		         var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
		         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
			     if(inputForm.valid()){
			    	 if(inputForm.find("#newPassword").val() != inputForm.find("#confirmNewPassword").val()){
							jp.error("输入的2次新密码不一致，请重新输入！")
							return;
						}
			    	 var patrn = /^[0-9]*$/;
			    	 if(patrn.test(inputForm.find("#newPassword").val())){
			    		 jp.error("密码不能为纯数字！");
			    		 return;
			    	 }
			    	 jp.post("${ctx}/sys/user/savePwd?oldPassword="+inputForm.find("#oldPassword").val()+"&newPassword="+inputForm.find("#newPassword").val(),$('#inputForm').serialize(),function(data){
			                    if(data.success){
			                    	jp.success(data.msg);
			                    	jp.close(index);//关闭dialog
			                    }else{
		            	  			jp.error(data.msg);
			                    }
			            });
		          }else{
			          return;
		          } */
				
				
			  },
			  cancel: function(index){ 
    	       }
		});
	}
});


function submitForm(){
	var patrn = /^[0-9]*$/;
	if($("#oldPassword").val().trim() == null || $("#oldPassword").val().trim() == ''){
		jp.info("请填写旧密码!");
		$("#oldPassword").focus();
		return;
	}else if($("#newPassword").val().trim() == null || $("#newPassword").val().trim() == ''){
		jp.info("请填写新密码!");
		$("#newPassword").focus();
		return;
	}else if(patrn.test($("#newPassword").val())){
		jp.info("新密码不能为纯数字!");
		$("#newPassword").focus();
		return;
	}else if($("#confirmNewPassword").val().trim() == null || $("#confirmNewPassword").val().trim() == ''){
		jp.info("请再次确认新密码!");
		$("#confirmNewPassword").focus();
		return;
	}else if(patrn.test($("#confirmNewPassword").val())){
		jp.info("新密码不能为纯数字!");
		$("#confirmNewPassword").focus();
		return;
	}else if($("#newPassword").val().trim() != $("#confirmNewPassword").val().trim()){
		jp.info("输入的2次新密码不一致，请重新输入!");
		$("#newPassword").focus();
		return;
	}else{
		jp.post("${ctx}/sys/user/savePwd",{oldPassword:$("#oldPassword").val().trim(),newPassword:$("#newPassword").val().trim()},
		function(data){
            if(data.success){
            	jp.close();//关闭dialog
            	jp.success(data.msg);
            	$(document).unbind("keyup");
            }else{
	  			jp.error(data.msg);
	  			$("#oldPassword").focus();
            }
    	});
	}
}


function changeTheme(theme) {
	var link = $('<link>')
	.appendTo('head')
	.attr({type : 'text/css', rel : 'stylesheet'})
	.attr('href', '${ctxStatic}/common/css/app-'+theme+'.css');

	
	var tabLink = $('<link>')
	.appendTo('head')
	.attr({type : 'text/css', rel : 'stylesheet'})
	.attr('href', '${ctxStatic}/plugin/js-menu/menuTab-'+theme+'.css');
	
	var childrenLink= $('<link>').appendTo($(".J_iframe").contents().find("head"))
	.attr({type : 'text/css', rel : 'stylesheet'})
	.attr('href', '${ctxStatic}/common/css/app-'+theme+'.css');
	
	
	 $.get('${pageContext.request.contextPath}/theme/'+theme+'?url='+window.top.location.href,function(result){
		 
		 $('#theme').remove();
		 $('#theme-tab').remove();
		 $(".J_iframe").contents().find("#theme").remove();
		 link.attr("id","theme");
		 childrenLink.attr("id","theme");
		 tabLink.attr("id","theme-tab")
	 });
		
}
</script>

</body>
</html>