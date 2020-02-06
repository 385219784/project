<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(function(){
	var str = "";
		str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
		str = str + "	<input id='rememberMe1' type='checkbox' onclick='rememberMe(this)' class='rememberMe' checked='checked' value='1'>";
		str = str + "	<label for='rememberMe1'>正在进行</label>";
		str = str + "</div>";
		str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
		str = str + "	<input id='rememberMe2' type='checkbox' onclick='rememberMe(this)' class='rememberMe' checked='checked' value='2'>";
		str = str + "	<label for='rememberMe2'>待评审</label>";
		str = str + "</div>";
		str = str + "<div class='checkbox-custom checkbox-default left-spacing'>";
		str = str + "	<input id='rememberMe3' type='checkbox' onclick='rememberMe(this)' class='rememberMe' checked='checked' value='3'>";
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
</script>