<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="labelName" type="java.lang.String" required="true" description="输入框名称（Name）"%>
<%@ attribute name="labelValue" type="java.lang.String" required="true" description="输入框值（Name）"%>
<%@ attribute name="fieldLabels" type="java.lang.String" required="true" description="表格Th里显示的名字"%>
<%@ attribute name="fieldKeys" type="java.lang.String" required="true" description="表格Td里显示的值"%>
<%@ attribute name="searchLabels" type="java.lang.String" required="true" description="表格Td里显示的值"%>
<%@ attribute name="searchKeys" type="java.lang.String" required="true" description="表格Td里显示的值"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="数据地址"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="isMultiSelected" type="java.lang.Boolean" required="false" description="css样式"%>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>
<%@ attribute name="paramValue" type="java.lang.String" required="false" description="是否有参数，有参数则填写控件id"%>
<%@ attribute name="paramName" type="java.lang.String" required="false" description="是否有参数，有参数则填写参数名称如xx.xx"%>
<%@ attribute name="msg" type="java.lang.String" required="false" description="提示信息"%>
<%@ attribute name="callback" type="java.lang.Boolean" required="false" description="回调函数"%>
<%@ attribute name="userPowerCallback" type="java.lang.Boolean" required="false" description="用电信息回调函数"%>
<%@ attribute name="userIdCallback" type="java.lang.Boolean" required="false" description="用户信息回调函数"%>
<%@ attribute name="idCardId" type="java.lang.String" required="false" description=""%>
<%@ attribute name="defaultValue" type="java.lang.String" required="false" description=""%>
<%@ attribute name="powerGridInfo" type="java.lang.Boolean" required="false" description="回调函数"%>
<%@ attribute name="idx" type="java.lang.String" required="false" description="下标"%>
<%@ attribute name="userId" type="java.lang.String" required="false" description="用户id"%>
<%@ attribute name="clear" type="java.lang.Boolean" required="false" description="是否清除"%>
<%@ attribute name="clearParam" type="java.lang.String" required="false" description="清理项id"%>
	<input id="${id}Id" name="${name}"  type="hidden" value="${value}"/>
	<div class="input-group" style="width: 100%">
		<input id="${id}Name"  name="${labelName }" ${allowInput?'':'readonly="readonly"'}  type="text" value="${labelValue}" data-msg-required="${dataMsgRequired}"
		class="${cssClass}" style="${cssStyle}"/>
       		 <span class="input-group-btn">
	       		 <button type="button"  id="${id}Button" class="btn <c:if test="${fn:contains(cssClass, 'input-sm')}"> btn-sm </c:if><c:if test="${fn:contains(cssClass, 'input-lg')}"> btn-lg </c:if>  btn-primary ${disabled} ${hideBtn ? 'hide' : ''}"><i class="fa fa-search"></i>
	             </button> 
	               <button type="button" id="${id}DelButton" class="close" data-dismiss="alert" style="position: absolute; top: 5px; right: 53px; z-index: 999; display: block;">×</button>
       		 </span>
       		
    </div>
	 <label id="${id}Name-error" class="error" for="${id}Name" style="display:none"></label>
<script type="text/javascript">
$(document).ready(function(){
	$("#${id}Button, #${id}Name").click(function(){
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		var param = "";
		if ("${paramValue}"!="" && "${paramName}"!="" && $("#${paramValue}").val() != "") {
			param = "?" + "${paramName}=" + $("#${paramValue}").val();
		}
		
		if ("${msg}" != "" && $("#${paramValue}").val() == "") {
			jp.warning("${msg}");
			return;
		}

		top.layer.open({
		    type: 2,  
		    area: ['800px', '500px'],
		    title:"${title}",
		    auto:true,
		    name:'friend',
		    content: "${ctx}/tag/progridselect?url="+encodeURIComponent("${url}")+param+"&fieldLabels="+encodeURIComponent(encodeURIComponent("${fieldLabels}"))+"&fieldKeys="+encodeURIComponent("${fieldKeys}")+"&searchLabels="+encodeURIComponent(encodeURIComponent("${searchLabels}"))+"&searchKeys="+encodeURIComponent("${searchKeys}")+"&isMultiSelected=${isMultiSelected? true:false}",
		    btn: ['确定', '关闭'],
		    yes: function(index, layero){
		    	 var iframeWin = layero.find('iframe')[0].contentWindow; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		    	 var items = iframeWin.getSelections();
		    	 if(items == ""){
			    	jp.warning("必须选择一条数据!");
			    	return;
		    	 }
		    	 var ids = [];
		    	 var names = [];
		    	 for(var i=0; i<items.length; i++){
		    		 var item = items[i];
		    		 ids.push(item.id);
		    		 names.push(item${fn:substring(labelName, fns:lastIndexOf(labelName, '.'), fn:length(labelName))})
		    	 }
		    	 
		    	 $("#${id}Id").val(ids.join(","));
		    	 $("#${id}Name").val(names.join(","));
		    	 
		    	 if("${callback}"){
		    		 callback("${idx}");
		    	 }
		    	 
		    	 if("${powerGridInfo}"){
		    		 powerGridInfo("${idx}",ids.join(","));
		    	 }
		    	 
		    	 if("${userPowerCallback}"){
		    		 userPowerCallback(ids.join(","));
		    	 }
		    	 
		    	 if("${userIdCallback}"){
		    		if($("#oldUserId").val() != ids.join(",")){
						$("#oldUserId").val(ids.join(","));
						//清除用电信息
						$("#useIdId").val("");
						$("#useIdName").val("");
						$("#ammeterPrice").text("");
		    		}
		    	 }
		    	 
				 top.layer.close(index);//关闭对话框。
			  },
			  cancel: function(index){ 
		       }
		}); 
	})
	$("#${id}DelButton").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 清除	
		$("#${id}Id").val("");
		$("#${id}Name").val("");
		$("#${id}Name").focus();
		//清除用电信息
		$("#useIdId").val("");
		$("#useIdName").val("");

		//是否清理
		if("${clear}"){
			//清理项
			$("#${clearParam}").val("");
		}
	});
})
</script>
