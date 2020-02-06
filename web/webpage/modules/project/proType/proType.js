<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(function(){
	var str = "";
		str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
		str = str + "	<input id='rememberMe01' type='checkbox' onclick='rememberMe1(this)' class='rememberMe1' value='7'>";
		str = str + "	<label for='rememberMe01'>后期服务</label>";
		str = str + "</div>";
		str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
		str = str + "	<input id='rememberMe02' type='checkbox' onclick='rememberMe1(this)' class='rememberMe1' value='9'>";
		str = str + "	<label for='rememberMe02'>外委</label>";
		str = str + "</div>";
		str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
		str = str + "	<input id='rememberMe03' type='checkbox' onclick='rememberMe1(this)' class='rememberMe1' value='10'>";
		str = str + "	<label for='rememberMe03'>课题</label>";
		str = str + "</div>";
		str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
		str = str + "	<input id='rememberMe04' type='checkbox' onclick='rememberMe1(this)' class='rememberMe1' value='11'>";
		str = str + "	<label for='rememberMe04'>报奖</label>";
		str = str + "</div>";
	
	$("#proTypeInfo").html(str)
});
	
	//监听所有的复选框
	function rememberMe1(obj){
		var proType = "";
		for(var i=0 ;i<$(".rememberMe1").length; i++){
			if(!$($(".rememberMe1")[i]).is(":checked")){
				proType = proType + $($(".rememberMe1")[i]).val() + ",";
			}
		}
		if(proType != ""){
			proType = proType.substring(0,proType.length-1);
		}
		$("#proType").val(proType);
		$($("#proTypeInfo").attr("tableId")).bootstrapTable('refresh');
	}
</script>