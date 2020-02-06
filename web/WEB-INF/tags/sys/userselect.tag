<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="false" description="隐藏域值（ID）"%>
<%@ attribute name="labelName" type="java.lang.String" required="fasle" description="输入框名称（Name）"%>
<%@ attribute name="labelValue" type="java.lang.String" required="false" description="输入框值（Name）"%>
<%@ attribute name="isMultiSelected" type="java.lang.Boolean" required="false" description="是否允许多选"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="hideBtn" type="java.lang.Boolean" required="false" description="是否显示按钮"%>
<%@ attribute name="isJoint" type="java.lang.Boolean" required="false" description="是否显示多次选择的所有用户"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>
<%@ attribute name="callback" type="java.lang.Boolean" required="false" description="回调函数"%>
<%@ attribute name="idx" type="java.lang.String" required="false" description="下标，和回调函数配合使用"%>
<%@ attribute name="dataMsgRequired" type="java.lang.String" required="false" description=""%>
	<input id="${id}Id" name="${name}" class="${cssClass} form-control" type="hidden" value="${value}"/>
	<div class="input-group" style="width:100%">
		<input id="${id}Name" name="${labelName}" ${allowInput?'':'readonly="readonly"'}  type="text"  value="${labelValue}" data-msg-required="${dataMsgRequired}"
		class="${cssClass}" style="${cssStyle}"/>
       		 <span class="input-group-btn">
	              <button type="button"  id="${id}Button" class="btn <c:if test="${fn:contains(cssClass, 'input-sm')}"> btn-sm </c:if><c:if test="${fn:contains(cssClass, 'input-lg')}"> btn-lg </c:if>  btn-primary ${disabled} ${hideBtn ? 'hide' : ''}"><i class="fa fa-search"></i>
	              </button>
	             <button type="button" id="${id}DelButton" class="close" data-dismiss="alert" style="position: absolute; top: 5px; right: 53px; z-index: 999; display: block;">×</button>
       		 </span>
       		
    </div>
	 <label id="${id}Name-error" class="error" for="${id}Name" style="display:none"></label>
<script type="text/javascript">
	$("#${id}Button, #${id}Name").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 正常打开	
		
		jp.openUserSelectDialog(${isMultiSelected? true:false},function(ids,names){
			if("${isJoint}"){
				//已选择的用户
				var oldUserId = $("#${id}Id").val();
				var oldUserName = $("#${id}Name").val();
				//新的用户
				var userId = oldUserId;
				var userName = oldUserName;
				//新选择的用户不为空
				if(ids.replace(/u_/ig,"") != null && ids.replace(/u_/ig,"") != ''){
					//新选择的用户数组
					var userIds = ids.replace(/u_/ig,"").split(",");
					var userNames = names.split(",");
					for(var i=0; i<userIds.length; i++){
						//已选择的用户不为空
						if(oldUserId != null && oldUserId != ''){
							//已选择的用户中不包含新选择的用户,执行添加操作
							if(oldUserId.indexOf(userIds[i]) < 0){
								userId = userId + "," + userIds[i];
								userName = userName + "," + userNames[i];
							}
						}else{
							userId = ids.replace(/u_/ig,"");
							userName = names;
						}
					}
					
					$("#${id}Id").val(userId);
					$("#${id}Name").val(userName);
					$("#${id}Name").focus();
				}
			}else{
				$("#${id}Id").val(ids.replace(/u_/ig,""));
				$("#${id}Name").val(names);
				$("#${id}Name").focus();
				
				if("${callback}"){
					callback(ids.replace(/u_/ig,""),"${idx}");
				}
			}
		})
	});
	
	$("#${id}DelButton").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 清除	
		$("#${id}Id").val("");
		$("#${id}Name").val("");
		$("#${id}Name").focus();
	
	});
</script>