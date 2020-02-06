<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>工作日报管理</title>
	<meta name="decorator" content="ani"/>
	<%@include file="workAttenceDetail.js" %>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@  include  file="item.js" %>
	<script type="text/javascript">
	var validateForm;
	var  workSum=0;	
	var $table; // 父页面table表格id
	var $topIndex;//弹出窗口的 index
	function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	  if(validateForm.form()){
		  $table = table;
		  $topIndex = index;
		
	  }else{
		  return ;
	  }
		
		
	  
	///hiddenClass  showClass
	
	//空白无法保存
	/*   if($(".showClass").length==0){
		  jp.alert("请填写后在保存!");
		  return false;
		  
	  } */
	  
	
		 workSum=0;	
		 var workBlooean=true;
	  //显示的数据
	  if($(".showClass").length>0){
		  $('.showClass').each(function(){
			  $(this).find("input.workClass").each(function(){
				  if(!isNumber($(this).val())){
					  $(this).val(0);
				  }else{
					 
				  }
				  workSum=accAdd(Number(workSum),Number($(this).val()));  
			  })  
			  
	  	})
	  }
	  
	  
	/*   if(workSum!=1){
		   jp.alert("工日之和必须等于1"); 
		   return;
	   } */
	  if(workSum>1){
		   jp.alert("工日之和不能大于1"); 
		   return;
	   }
	  
	  
	  if($(".hiddenClass").length>0){
		  $('.hiddenClass').each(function(){
			  $(this).find("input.delClass").each(function(){
					$(this).val("1"); 
			  })  
	  	})
	  }
	  
	  
	  
	
	
	  jp.loading();
	  $("#inputForm").submit();
	  return true;
	  return false;
	}
	$(document).ready(function() {
		validateForm = $("#inputForm").validate({
			submitHandler: function(form){
				if(workSum<1){
					  jp.confirm('您填写的工日为'+workSum+',确认保存么？', function(){
	                      	jp.loading();
	                    	jp.post("${ctx}/attence/workAttence/save",$('#inputForm').serialize(),function(data){
	    						if(data.success){
	    							var iframeWin=top[top.getActiveTab().attr("name")]; 
	    							iframeWin.refresh();
	    	                    	jp.success(data.msg);
	    	                    	jp.close($topIndex);//关闭dialog

	    	                    }else{
	    	        	  			jp.error(data.msg);
	    	                    }
	    					})
	                  	});
				}else{
					jp.post("${ctx}/attence/workAttence/save",$('#inputForm').serialize(),function(data){
						if(data.success){
							var iframeWin=top[top.getActiveTab().attr("name")]; 
							iframeWin.refresh();
	                    	jp.success(data.msg);
	                    	jp.close($topIndex);//关闭dialog

	                    }else{
	        	  			jp.error(data.msg);
	                    }
					})
				}
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
	
	});
	
	  function clearNoNum(obj){
		  obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
		  }

	  
	  
	  /**
	     * 消除js计算偏差   加法
	     */
	    function accAdd(arg1,arg2){
	  	var r1,r2,m;
	  	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	  	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	  	m=Math.pow(10,Math.max(r1,r2))
	  	return (arg1*m+arg2*m)/m
	  }
		//正则表达式
	   function isNumber(val){
	  	    var regPos = /^\d+(\.\d+)?$/; //非负浮点数
	  	    //"^(0|[1-9][0-9]*)$"
	  	    var regInt = / ^(0|[1-9][0-9]*)/; // 非负整数
	  	    if(regPos.test(val) || regInt.test(val)){
	  	       return  true;
	  	    }else{
	  	    	return  false;
	  	    }
	  	}
	   function createObject(id,proId,specId,workTime,overTime,dateStr)
	 	{
	 		var object = new Object();
	 		object.id = id;
	 		object.proId = proId;
	 		object.specId = specId;
	 		object.workTime=Number(workTime);
	 		object.overTime=Number(overTime);
	 		object.dateStr=dateStr;
	 		return object;
	 	}
	   
	   
	   
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
			$(list+idx).find(".form_datetime").each(function(){
				 $(this).datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
			    });
			});
		}
		
		
		//  删除行
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			var workTime=$(prefix+"_workTime");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				workTime.removeClass("workClass");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				workTime.addClass("workClass");
				$(obj).parent().parent().removeClass("error");
			}
		}
		 function datedifference(sDate1, sDate2) {    //sDate1和sDate2是2006-12-18格式  
		        var dateSpan,
		            tempDate,
		            iDays;
		        sDate1 = Date.parse(sDate1);
		        sDate2 = Date.parse(sDate2);
		        dateSpan = sDate2 - sDate1;
		        dateSpan = Math.abs(dateSpan);
		        iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
		        return iDays
		    };
	</script>
	
	<style type="text/css">
	#workDetail > tr >td {
height: 25px;
max-height: 35px;
}
	
	
	</style>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="workItem"  class="form-horizontal">
		<sys:message content="${message}"/>
		<input  type="hidden" id="userId"  value='${userId}'/>
		<input  type="hidden" id="checkStr"  value='${checkStr}'/>
 		<input  type="hidden" id="dateStr" name="dateStr" value='${dateStr}'/> 
		<div  style="width:100%;height:100%">
	<div id="items">
	<div>
	<div id="collapseThree">
	<div>
	<div style="margin-left:-5px;width:104%;">
	<div class="row">
	<div class="col-lg-1 col-md-1 col-sm-1 col-xs-6">

	</div>
	
	
	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb10" id="rememberMe10">
	<label for="rememberMe2" class="item10">内业</label>
	</div>
	</div>
	
	
	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb15" id="rememberMe15">
	<label for="rememberMe3" class="item15">设代</label>
	</div>
	</div>
	
		<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb65" id="rememberMe65">
	<label for="rememberMe12" class="item40">出差</label>
	</div>
	</div>
	
	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb1" id="rememberMe1">
	<label for="rememberMe0" class="item1">考察</label>
	</div>
	</div>
	
	<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb5" id="rememberMe5">
	<label for="rememberMe1" class="item5">培训</label>
	</div>
	</div>
	
	
	
	
	<div class="col-lg-1 col-md-1 col-sm-1 col-xs-6">

	</div>
	
	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb25" id="rememberMe25">
	<label for="rememberMe5" class="item25">零星事务</label>
	</div>
	</div>
	
	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb30" id="rememberMe30"
	><label for="rememberMe6" class="item30">事假</label>
	</div>
	</div>
	
	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb35" id="rememberMe35">
	<label for="rememberMe7" class="item35">年假</label>
	</div>
	</div>
	
	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb40" id="rememberMe40">
	<label for="rememberMe8" class="item40">病假</label>
	</div>
	</div>


		<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
			<div class="checkbox-custom checkbox-default">
				<input type="checkbox" onclick="rememberMe(this)" class="kqlb60" id="rememberMe60">
				<label for="rememberMe12" class="item40">办公室</label>
			</div>
		</div>

		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-6">

		</div>

		<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
			<div class="checkbox-custom checkbox-default">
				<input type="checkbox" onclick="rememberMe(this)" class="kqlb70" id="rememberMe70">
				<label for="rememberMe12" class="item40">学习</label>
			</div>
		</div>


	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb45" id="rememberMe45">
	<label for="rememberMe9" class="item40">其他1</label>
	</div>
	</div>
	


	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb50" id="rememberMe50">
	<label for="rememberMe10" class="item40">其他2</label>
	</div>
	</div>
	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb55" id="rememberMe55">
	<label for="rememberMe11" class="item40">其他3 </label>
	</div>
	</div>




	
		
	<!-- <div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
	<div class="checkbox-custom checkbox-default">
	<input type="checkbox" onclick="rememberMe(this)" class="kqlb20" id="rememberMe20">
	<label for="rememberMe4" class="item20">党工团</label>
	</div>
	</div> -->


	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	
		
			

	
	
		<c:choose>  
   <c:when  test="${workItem.officeProList==null}">
   		<div  class="hiddenClass "      id="ny"  style="display:none;border:1px dashed #000; margin-top:10px;" >
   </c:when>
   <c:otherwise>
    	<div  class="showClass"   id="ny"  style="border:1px dashed #000; margin-top:10px;" >
   </c:otherwise>
   </c:choose>

	<div class="tabs-container"  style="padding:10px">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">内业：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#officeProList', officeProListRowIdx, officeProListTpl);officeProListRowIdx = officeProListRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>项目简称</th>
						<th>专业名称</th>
						<th style="width:100px;">工日</th>
						<th style="width:100px;">加班小时</th>
						<th>工作内容</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="officeProList">
				</tbody>
			</table>
			<script type="text/template" id="officeProListTpl">//<!--
				<tr id="outProList{{idx}}">
					<td class="hide">
						<input id="officeProList{{idx}}_id" name="officeProList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="officeProList{{idx}}_delFlag"  class="delClass" name="officeProList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>

					<td>
						<sys:progridselect url="${ctx}/project/project/dataUser" id="officeProList{{idx}}_project" name="officeProList[{{idx}}].project.id" value="{{row.project.id}}" labelName="officeProList{{idx}}.project.proShortened" labelValue="{{row.project.proShortened}}"
							 title="选择项目" cssClass="form-control required" fieldLabels="项目简称|编号|项目类别" fieldKeys="proShortened|num|type" searchLabels="项目简称" searchKeys="proShortened" ></sys:progridselect>
					</td>

					<td>
						<sys:gridselect url="${ctx}/project/specialty/dataList" id="officeProList{{idx}}_specialty" name="officeProList[{{idx}}].specialty.id" value="{{row.specialty.id}}" labelName="officeProList{{idx}}.specialty.name" labelValue="{{row.specialty.name}}"
							 title="选择专业" cssClass="form-control required"   msg="请先选择项目"  paramValue="officeProList{{idx}}_projectId"   paramName="projectId"     fieldLabels="专业名称" fieldKeys="name" searchLabels="专业名称" searchKeys="name" ></sys:gridselect>
					</td>

					<td>
						<input id="officeProList{{idx}}_workTime"    onkeyup="clearNoNum(this)"  name="officeProList[{{idx}}].workTime" type="text" value="{{row.workTime}}"   max="1"  min="0"  maxlength="4"  minlength="0"   class="form-control   workClass number"/>
					</td>

					<td>
						<input id="officeProList{{idx}}_overTime"    onkeyup="clearNoNum(this)"  name="officeProList[{{idx}}].overTime" type="text" value="{{row.overTime}}"   max="8"    maxlength="3"   class="form-control    overClass number"/>
					</td>

				

					<td>
						<input id="officeProList{{idx}}_remarks" name="officeProList[{{idx}}].remarks" type="text" value="{{row.remarks}}"   maxlength="64"  class="form-control "/>
					</td>
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#officeProList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var officeProListRowIdx = 0, officeProListTpl = $("#officeProListTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(workItem.officeProList)};
					for (var i=0; i<data.length; i++){
						addRow('#officeProList', officeProListRowIdx, officeProListTpl, data[i]);
						officeProListRowIdx = officeProListRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</div>
	
	
	 <c:choose>  
   <c:when  test="${workItem.outProList==null}">
   <div  class="hiddenClass"  id="wy"   style="display:none;border:1px dashed #000; margin-top:10px" >
   </c:when>
   <c:otherwise>
   <div  class="showClass"  id="wy"    style="border:1px dashed #000;margin-top:10px" >
   </c:otherwise>
   </c:choose>
	<div class="tabs-container"  style="padding:10px">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">设代：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#outProList', outProListRowIdx, outProListTpl);outProListRowIdx = outProListRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>项目简称</th>
						<th>专业名称</th>
						<th style="width:100px;">工日</th>
						<th style="width:100px;">加班小时</th>
						<th>工作内容</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="outProList">
				</tbody>
			</table>
			<script type="text/template" id="outProListTpl">//<!--
				<tr id="outProList{{idx}}">
					<td class="hide">
						<input id="outProList{{idx}}_id" name="outProList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="outProList{{idx}}_delFlag"      class="delClass"    name="outProList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>

					<td>
						<sys:progridselect url="${ctx}/project/project/dataUser" id="outProList{{idx}}_project" name="outProList[{{idx}}].project.id" value="{{row.project.id}}" labelName="outProList{{idx}}.project.proShortened" labelValue="{{row.project.proShortened}}"
							 title="选择项目" cssClass="form-control required" fieldLabels="项目简称|编号|项目类别" fieldKeys="proShortened|num|type" searchLabels="项目简称" searchKeys="proShortened" ></sys:progridselect>
					</td>

					<td>
						<sys:gridselect url="${ctx}/project/specialty/dataList" id="outProList{{idx}}_specialty" name="outProList[{{idx}}].specialty.id" value="{{row.specialty.id}}" labelName="outProList{{idx}}.specialty.name" labelValue="{{row.specialty.name}}"
							 title="选择专业" cssClass="form-control required"   msg="请先选择项目"  paramValue="outProList{{idx}}_projectId"   paramName="projectId"     fieldLabels="专业名称" fieldKeys="name" searchLabels="专业名称" searchKeys="name" ></sys:gridselect>
					</td>

					<td>
						<input id="outProList{{idx}}_workTime"     onkeyup="clearNoNum(this)"  name="outProList[{{idx}}].workTime" type="text" value="{{row.workTime}}"   max="1"  min="0"  maxlength="4"  minlength="0"   class="form-control    workClass number"/>
					</td>

					<td>
						<input id="outProList{{idx}}_overTime"    onkeyup="clearNoNum(this)"  name="outProList[{{idx}}].overTime" type="text" value="{{row.overTime}}"   max="8"    maxlength="3"   class="form-control     overClass  number"/>
					</td>

					<td>
						<input id="outProList{{idx}}_remarks" name="outProList[{{idx}}].remarks" type="text" value="{{row.remarks}}"   maxlength="64"  class="form-control "/>
					</td>
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#outProList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var outProListRowIdx = 0, outProListTpl = $("#outProListTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(workItem.outProList)};
					for (var i=0; i<data.length; i++){
						addRow('#outProList', outProListRowIdx, outProListTpl, data[i]);
						outProListRowIdx = outProListRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</div> 
	
	
	
		
	<%-- <c:choose>  
   <c:when  test="${workItem.orgProList==null}">
   	<div  class="hiddenClass"  id="dgt"  style="display:none;border:1px dashed #000; margin-top:10px"  >
   </c:when>
   <c:otherwise>
    	<div  class="showClass"  id="dgt"  style="border:1px dashed #000; margin-top:10px"  >
   </c:otherwise>
   </c:choose>
	<div class="tabs-container"  style="padding:10px">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">党工团：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#orgProList', orgProListRowIdx, orgProListTpl);orgProListRowIdx = orgProListRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>项目简称</th>
						<th>专业名称</th>
						<th style="width:100px;">工日</th>
						<th style="width:100px;">加班小时</th>
						<th>工作内容</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="orgProList">
				</tbody>
			</table>
			<script type="text/template" id="orgProListTpl">//<!--
				<tr id="orgProList{{idx}}">
					<td class="hide">
						<input id="orgProList{{idx}}_id" name="orgProList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="orgProList{{idx}}_delFlag"  class="delClass" name="orgProList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>

					<td>
						<sys:progridselect url="${ctx}/project/project/dataUserDgt" id="orgProList{{idx}}_project" name="orgProList[{{idx}}].project.id" value="{{row.project.id}}" labelName="orgProList{{idx}}.project.proShortened" labelValue="{{row.project.proShortened}}"
							 title="选择项目" cssClass="form-control required" fieldLabels="项目简称|编号|项目类别" fieldKeys="proShortened|num|type" searchLabels="项目简称" searchKeys="proShortened" ></sys:progridselect>
					</td>

					<td>
						<sys:gridselect url="${ctx}/project/specialty/dataList" id="orgProList{{idx}}_specialty" name="orgProList[{{idx}}].specialty.id" value="{{row.specialty.id}}" labelName="orgProList{{idx}}.specialty.name" labelValue="{{row.specialty.name}}"
							 title="选择专业" cssClass="form-control required"   msg="请先选择项目"  paramValue="orgProList{{idx}}_projectId"   paramName="projectId"     fieldLabels="专业名称" fieldKeys="name" searchLabels="专业名称" searchKeys="name" ></sys:gridselect>
					</td>

					<td>
						<input id="orgProList{{idx}}_workTime"    onkeyup="clearNoNum(this)"  name="orgProList[{{idx}}].workTime" type="text" value="{{row.workTime}}"   max="1"  min="0"  maxlength="4"  minlength="0"   class="form-control   workClass number"/>
					</td>

					<td>
						<input id="orgProList{{idx}}_overTime"    onkeyup="clearNoNum(this)"  name="orgProList[{{idx}}].overTime" type="text" value="{{row.overTime}}"   max="8"    maxlength="3"   class="form-control   overClass number"/>
					</td>


					<td>
						<input id="orgProList{{idx}}_remarks" name="orgProList[{{idx}}].remarks" type="text" value="{{row.remarks}}"   maxlength="64"  class="form-control "/>
					</td>
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#orgProList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var orgProListRowIdx = 0, orgProListTpl = $("#orgProListTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(workItem.orgProList)};
					for (var i=0; i<data.length; i++){
						addRow('#orgProList', orgProListRowIdx, orgProListTpl, data[i]);
						orgProListRowIdx = orgProListRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</div> --%>
	
	
	
	
	<c:choose>  
   <c:when  test="${workItem.ccList==null}">
   <div  class="hiddenClass"  id="cc"   style="display:none;border:1px dashed #000; margin-top:10px" >
   </c:when>
   <c:otherwise>
   <div  class="showClass"  id="cc"    style="border:1px dashed #000;margin-top:10px" >
   </c:otherwise>
   </c:choose>
	<div class="tabs-container"  style="padding:10px">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">出差：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#ccList', ccListRowIdx, ccListTpl);ccListRowIdx = ccListRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>项目简称</th>
						<th>专业名称</th>
						<th style="width:100px;">工日</th>
						<th style="width:100px;">加班小时</th>
						<th>工作内容</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="ccList">
				</tbody>
			</table>
			<script type="text/template" id="ccListTpl">//<!--
				<tr id="outProList{{idx}}">
					<td class="hide">
						<input id="ccList{{idx}}_id" name="ccList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="ccList{{idx}}_delFlag"      class="delClass"    name="ccList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>

					<td>
						<sys:progridselect url="${ctx}/project/project/dataUser" id="ccList{{idx}}_project" name="ccList[{{idx}}].project.id" value="{{row.project.id}}" labelName="ccList{{idx}}.project.name" labelValue="{{row.project.name}}"
							 title="选择项目" cssClass="form-control" fieldLabels="项目简称|编号|项目类别" fieldKeys="proShortened|num|type" searchLabels="项目简称" searchKeys="proShortened" ></sys:progridselect>
					</td>

					<td>
						<sys:gridselect url="${ctx}/project/specialty/dataList" id="ccList{{idx}}_specialty" name="ccList[{{idx}}].specialty.id" value="{{row.specialty.id}}" labelName="ccList{{idx}}.specialty.name" labelValue="{{row.specialty.name}}"
							 title="选择专业" cssClass="form-control required"   msg="请先选择项目"  paramValue="ccList{{idx}}_projectId"   paramName="projectId"     fieldLabels="专业名称" fieldKeys="name" searchLabels="专业名称" searchKeys="name" ></sys:gridselect>
					</td>

					<td>
						<input id="ccList{{idx}}_workTime"     onkeyup="clearNoNum(this)"  name="ccList[{{idx}}].workTime" type="text" value="{{row.workTime}}"   max="1"  min="0"  maxlength="4"  minlength="0"   class="form-control  workClass   number"/>
					</td>

					<td>
						<input id="ccList{{idx}}_overTime"    onkeyup="clearNoNum(this)"  name="ccList[{{idx}}].overTime" type="text" value="{{row.overTime}}"   max="8"    maxlength="3"   class="form-control     overClass  number"/>
					</td>

					<td>
						<input id="ccList{{idx}}_remarks" name="ccList[{{idx}}].remarks" type="text" value="{{row.remarks}}"   maxlength="64"  class="form-control "/>
					</td>
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#ccList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var ccListRowIdx = 0, ccListTpl = $("#ccListTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(workItem.ccList)};
					for (var i=0; i<data.length; i++){
						addRow('#ccList', ccListRowIdx, ccListTpl, data[i]);
						ccListRowIdx = ccListRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</div>
	
	
	
	
	
	
	

		<c:choose>  
   <c:when  test="${workItem.inspect==null}">
   		<div  class="hiddenClass"    id="inspect"  style="display:none;border:1px dashed #000; margin-top:10px" >
   </c:when>
   <c:otherwise>
    	<div  class="showClass"    id="inspect"  style="border:1px dashed #000; margin-top:10px" >
   </c:otherwise>
   </c:choose>
		<div class="tabs-container" style="padding:10px">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">考察：</a>
                </li>
         </ul>
		<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>工日</th>
						<th>工作内容</th>
					</tr>
				</thead>
				<tbody id="inspectBody">
				<tr>
						<td class="hide"><form:input path="inspect.id"   type="hidden"/><form:input path="inspect.delFlag"      class="delClass" type="hidden"/></td>
						<td><form:input path="inspect.workTime" htmlEscape="false" maxlength="4"   onkeyup="clearNoNum(this)"   max="1" class="form-control   workClass  number required"/></td>
						<td>
						<form:input path="inspect.remarks"  type="text"  maxlength="64" class="form-control"/>
						</td>
				</tr>
				</tbody>
			</table>
			</div>
	</div>
  

		<c:choose>  
   <c:when  test="${workItem.train==null}">
   		<div  class="hiddenClass"    id="train" style="display:none;border:1px dashed #000;margin-top:10px" >
   </c:when>
   <c:otherwise>
    	<div  class="showClass"    id="train" style="border:1px dashed #000;margin-top:10px">
   </c:otherwise>
   </c:choose>
 
		<div class="tabs-container" style="padding:10px">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">培训：</a>
                </li>
         </ul>
		<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>工日</th>
						<th>加班小时</th>
						<th>工作内容</th>
					</tr>
				</thead>
				<tbody id="trainBody">
				<tr>
						<td class="hide"><form:input path="train.id"   type="hidden"/><form:input path="train.delFlag"      class="delClass" type="hidden"/></td>
						<td><form:input path="train.workTime" htmlEscape="false" maxlength="4"   onkeyup="clearNoNum(this)"   max="1" class="form-control  workClass  number required"/></td>
						<td><form:input path="train.overTime" htmlEscape="false" maxlength="4"  onkeyup="clearNoNum(this)"  max="8"  class="form-control   overClass "/></td>
						<td>
						<td>
						<form:input path="train.remarks" type="text"  maxlength="64" class="form-control "/>
						</td>
				</tr>
				</tbody>
			</table>
			</div>
	</div>
 
 
 
 
  	<c:choose>  
   <c:when  test="${workItem.siteWork==null}">
   			<div  class="hiddenClass"    id="siteWork" style="display:none;border:1px dashed #000;margin-top:10px" >
   </c:when>
   <c:otherwise>
    		<div  class="showClass"    id="siteWork"  style="border:1px dashed #000;margin-top:10px">
   </c:otherwise>
   </c:choose>
		<div class="tabs-container" style="padding:10px">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">零星事物：</a>
                </li>
         </ul>
		<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>工日</th>
						<th>加班小时</th>
						<th>工作内容</th>
					</tr>
				</thead>
				<tbody id="siteWorkBody">
				<tr>
						<td class="hide"><form:input path="siteWork.id"   type="hidden"/><form:input path="siteWork.delFlag"      class="delClass" type="hidden"/></td>
						<td><form:input path="siteWork.workTime" htmlEscape="false" maxlength="4"   onkeyup="clearNoNum(this)"   max="1" class="form-control  workClass  number "/></td>
						<td><form:input path="siteWork.overTime" htmlEscape="false" maxlength="4"  onkeyup="clearNoNum(this)"  max="8"  class="form-control   overClass "/></td>
						<td>
						<form:input path="siteWork.remarks" type="text"  maxlength="64" class="form-control "/>
						</td>
				</tr>
				</tbody>
			</table>
			</div>
	</div>
 
 
 
 
 



		<c:choose>  
   <c:when  test="${workItem.casualLeave==null}">
   		<div  class="hiddenClass"    id="casualLeave" style="display:none;border:1px dashed #000;margin-top:10px" >
   </c:when>
   <c:otherwise>
    	<div  class="showClass"    id="casualLeave"  style=" border:1px dashed #000;margin-top:10px">
   </c:otherwise>
   </c:choose>
		<div class="tabs-container" style="padding:10px">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">事假：</a>
                </li>
         </ul>
		<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>工日</th>
						<th>工作内容</th>
					</tr>
				</thead>
				<tbody id="casualLeaveBody">
				<tr>
						<td class="hide"><form:input path="casualLeave.id"   type="hidden"/><form:input path="casualLeave.delFlag"      class="delClass" type="hidden"/></td>
						<td><form:input path="casualLeave.workTime" htmlEscape="false" maxlength="4"   onkeyup="clearNoNum(this)"   max="1" class="form-control workClass   number required"/></td>
						<td>
						<form:input path="casualLeave.remarks" type="text"  maxlength="64" class="form-control "/>
						</td>
				</tr>
				</tbody>
			</table>
			</div>
	</div>
  

	
	
 		<c:choose>  
   <c:when  test="${workItem.yearLeave==null}">
   		<div  class="hiddenClass"    id="yearLeave" style="display:none; border:1px dashed #000;margin-top:10px"  >
   </c:when>
   <c:otherwise>
    	<div  class="showClass"    id="yearLeave"  style=" border:1px dashed #000;margin-top:10px"  >
   </c:otherwise>
   </c:choose>
		<div class="tabs-container"  style="padding:10px">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">年假：</a>
                </li>
         </ul>
		<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>工日</th>
						<th>工作内容</th>
					</tr>
				</thead>
				<tbody id="yearLeaveBody">
				<tr>
						<td class="hide"><form:input path="yearLeave.id"   type="hidden"/><form:input path="yearLeave.delFlag"      class="delClass" type="hidden"/></td>
						<td><form:input path="yearLeave.workTime" htmlEscape="false" maxlength="4"   onkeyup="clearNoNum(this)"   max="1" class="form-control  workClass  number required"/></td>
						<td>
						<form:input path="yearLeave.remarks" type="text"  maxlength="64" class="form-control "/>
						</td>
				</tr>
				</tbody>
			</table>
			</div>
	</div>
  
	
		<c:choose>  
   <c:when  test="${workItem.sickLeave==null}">
   		<div  class="hiddenClass"    id="sickLeave" style="display:none;border:1px dashed #000;margin-top:10px" >
   </c:when>
   <c:otherwise>
    	<div  class="showClass"    id="sickLeave"  style="border:1px dashed #000;margin-top:10px" >
   </c:otherwise>
   </c:choose>
		
		<div class="tabs-container" style="padding:10px">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">病假：</a>
                </li>
         </ul>
		<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>工日</th>
						<th>工作内容</th>
					</tr>
				</thead>
				<tbody id="sickLeaveBody">
				<tr>
						<td class="hide"><form:input path="sickLeave.id"   type="hidden"/><form:input path="sickLeave.delFlag"      class="delClass" type="hidden"/></td>
						<td><form:input path="sickLeave.workTime" htmlEscape="false" maxlength="4"   onkeyup="clearNoNum(this)"   max="1" class="form-control  workClass  number required"/></td>
						<td>
						<form:input path="sickLeave.remarks" type="text"  maxlength="64" class="form-control "/>
						</td>
				</tr>
				</tbody>
			</table>
			</div>
	</div>
  
  
  
  
	
	
	
  
  
  
  
  
  	<c:choose>  
   <c:when  test="${workItem.cw==null}">
   			<div  class="hiddenClass"    id="cw" style="display:none;border:1px dashed #000;margin-top:10px" >
   </c:when>
   <c:otherwise>
    		<div  class="showClass"    id="cw"  style="border:1px dashed #000;margin-top:10px">
   </c:otherwise>
   </c:choose>
		<div class="tabs-container" style="padding:10px">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">其他1：</a>
                </li>
         </ul>
		<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>工日</th>
						<th>加班小时</th>
						<th>工作内容</th>
					</tr>
				</thead>
				<tbody id="cwBody">
				<tr>
						<td class="hide"><form:input path="cw.id"   type="hidden"/><form:input path="cw.delFlag"      class="delClass" type="hidden"/></td>
						<td><form:input path="cw.workTime" htmlEscape="false" maxlength="4"   onkeyup="clearNoNum(this)"   max="1" class="form-control  workClass  number "/></td>
						<td><form:input path="cw.overTime" htmlEscape="false" maxlength="4"  onkeyup="clearNoNum(this)"  max="8"  class="form-control   overClass "/></td>
						<td>
						<form:input path="cw.remarks" type="text"  maxlength="64" class="form-control "/>
						</td>
				</tr>
				</tbody>
			</table>
			</div>
	</div>
	
	
	
	
	
	
	
  	<c:choose>  
   <c:when  test="${workItem.sw==null}">
   			<div  class="hiddenClass"    id="sw" style="display:none;border:1px dashed #000;margin-top:10px" >
   </c:when>
   <c:otherwise>
    		<div  class="showClass"    id="sw"  style="border:1px dashed #000;margin-top:10px">
   </c:otherwise>
   </c:choose>
		<div class="tabs-container" style="padding:10px">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">其他2：</a>
                </li>
         </ul>
		<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>工日</th>
						<th>加班小时</th>
					
						<th>工作内容</th>
					</tr>
				</thead>
				<tbody id="swBody">
				<tr>
						<td class="hide"><form:input path="sw.id"   type="hidden"/><form:input path="sw.delFlag"      class="delClass" type="hidden"/></td>
						<td><form:input path="sw.workTime" htmlEscape="false" maxlength="4"   onkeyup="clearNoNum(this)"   max="1" class="form-control  workClass  number "/></td>
						<td><form:input path="sw.overTime" htmlEscape="false" maxlength="4"  onkeyup="clearNoNum(this)"  max="8"  class="form-control   overClass "/></td>
						<td>
					<form:input path="sw.remarks" type="text"  maxlength="64" class="form-control "/>
						</td>
				</tr>
				</tbody>
			</table>
			</div>
	</div>
	
	
	
	
	
	
	
	
	
	
  	<c:choose>  
   <c:when  test="${workItem.jy==null}">
   			<div  class="hiddenClass"    id="jy" style="display:none;border:1px dashed #000;margin-top:10px" >
   </c:when>
   <c:otherwise>
    		<div  class="showClass"    id="jy"  style="border:1px dashed #000;margin-top:10px">
   </c:otherwise>
   </c:choose>
		<div class="tabs-container" style="padding:10px">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">其他3：</a>
                </li>
         </ul>
		<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>工日</th>
						<th>加班小时</th>
						<th>工作内容</th>
					</tr>
				</thead>
				<tbody id="jyBody">
				<tr>
						<td class="hide"><form:input path="jy.id"   type="hidden"/><form:input path="jy.delFlag"      class="delClass" type="hidden"/></td>
						<td><form:input path="jy.workTime" htmlEscape="false" maxlength="4"   onkeyup="clearNoNum(this)"   max="1" class="form-control  workClass  number "/></td>
						<td><form:input path="jy.overTime" htmlEscape="false" maxlength="4"  onkeyup="clearNoNum(this)"  max="8"  class="form-control   overClass "/></td>
				
						<td>
						<form:input path="jy.remarks" type="text"  maxlength="64" class="form-control "/>
						</td>
				</tr>
				</tbody>
			</table>
			</div>
	</div>
	
	
	
	
	
	
	
	
	
  	<c:choose>  
   <c:when  test="${workItem.rcgl==null}">
   			<div  class="hiddenClass"    id="rcgl" style="display:none;border:1px dashed #000;margin-top:10px" >
   </c:when>
   <c:otherwise>
    		<div  class="showClass"    id="rcgl"  style="border:1px dashed #000;margin-top:10px">
   </c:otherwise>
   </c:choose>
		<div class="tabs-container" style="padding:10px">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">办公室：</a>
                </li>
         </ul>
		<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>工日</th>
						<th>加班小时</th>
						<th>工作内容</th>
					</tr>
				</thead>
				<tbody id="rcglBody">
				<tr>
						<td class="hide"><form:input path="rcgl.id"   type="hidden"/><form:input path="rcgl.delFlag"      class="delClass" type="hidden"/></td>
						<td><form:input path="rcgl.workTime" htmlEscape="false" maxlength="4"   onkeyup="clearNoNum(this)"   max="1" class="form-control  workClass  number "/></td>
						<td><form:input path="rcgl.overTime" htmlEscape="false" maxlength="4"  onkeyup="clearNoNum(this)"  max="8"  class="form-control   overClass "/></td>
					
						<td>
						<form:input path="rcgl.remarks" type="text"  maxlength="64" class="form-control "/>
						</td>
				</tr>
				</tbody>
			</table>
			</div>
	</div>
	
	
	
	
	
  	<c:choose>  
   <c:when  test="${workItem.xx==null}">
   			<div  class="hiddenClass"    id="xx" style="display:none;border:1px dashed #000;margin-top:10px" >
   </c:when>
   <c:otherwise>
    		<div  class="showClass"    id="xx"  style="border:1px dashed #000;margin-top:10px">
   </c:otherwise>
   </c:choose>
		<div class="tabs-container" style="padding:10px">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">学习：</a>
                </li>
         </ul>
		<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>工日</th>
						<th>加班小时</th>
						<th>工作内容</th>
					</tr>
				</thead>
				<tbody id="xxBody">
				<tr>
						<td class="hide"><form:input path="xx.id"   type="hidden"/><form:input path="xx.delFlag"      class="delClass" type="hidden"/></td>
						<td><form:input path="xx.workTime" htmlEscape="false" maxlength="4"   onkeyup="clearNoNum(this)"   max="1" class="form-control  workClass  number "/></td>
						<td><form:input path="xx.overTime" htmlEscape="false" maxlength="4"  onkeyup="clearNoNum(this)"  max="8"  class="form-control   overClass "/></td>
					
						<td>
						<form:input path="xx.remarks" type="text"  maxlength="64" class="form-control "/>
						</td>
				</tr>
				</tbody>
			</table>
			</div>
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 
	</div>
	</form:form>
</body>
</html>