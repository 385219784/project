<%@ page contentType="text/html;charset=UTF-8" %>
<script>
//个人项目
$(document).ready(function() {
	$('#projectWorkCaseTable').bootstrapTable({
		 
		  //请求方法
               method: 'get',
               //类型json
               dataType: "json",
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               //showToggle: true,
               //显示 内容列下拉框
    	       //showColumns: true,
    	       //显示到处按钮
    	       //showExport: true,
    	       height: $(document.body).height(),
    	       //显示切换分页按钮
    	       //showPaginationSwitch: true,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否固定左侧列
               //leftFixedColumns: true,
               //左侧固定几列
               //leftFixedNumber: 3,
               //是否显示行间隔色
               striped: true,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
               cache: false,    
               //是否显示分页（*）  
               pagination: true,   
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数（*）   
               pageSize: 50,  
               //可供选择的每页的行数（*）    
               pageList: [50,100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/project/projectWorkCase/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                   return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               //contextMenuTrigger:"right",//pc端 按右键弹出菜单
               //contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               //contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                   	edit(row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该个人工作情况统计记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/project/projectWorkCase/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#projectWorkCaseTable').bootstrapTable('refresh');
                   	  			jp.success(data.msg);
                   	  		}else{
                   	  			jp.error(data.msg);
                   	  		}
                   	  	})
                   	   
                   	});
                      
                   } 
               },
              
               onClickRow: function(row, $el){
               },
               //单击单元格修改
               onClickCell:function(field, value, row, $el){
               	clickTd(field, value, row, $el);
               },
               //数据加载成功之后
               onLoadSuccess:function(data){
   		    	   for(var i = 0; i<data.rows.length; i++){
   		    		$('#finishDate'+i).datetimepicker({
	   					 format: "YYYY-MM-DD",
	   					 locale: moment.locale('zh-cn')
	   			    });
            	   }
               },
               columns: [/*{
		        checkbox: true
		       
		    },*/{
                title: '序号',//标题  可不加
                align: "center",
                width : '50px',formatter:function(value, row , index){
		        	return index+1;
		        }
            }
			,{
		        field: 'proNum',
		        title: '项目编号',
		        width:"80px",
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'proShortened',
		        title: '项目简称',
		        width:"170px",
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if(value != null && value != ''){
			        	if(value.length > 13){
				        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,13) +"</a></abbr>";
			        	}
		        	}
		        	return value;
		         }
		       
		    }
			,{
		        field: 'proType',
		        title: '项目类别',
		        width:"80px",
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('pro_type'))}, value, "-");
		        }
		       
		    }
			/*,{
		        field: 'designType',
		        title: '设计类别',
		        sortable: true,
		        width:"110px",
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('design_type'))}, value, "-");
		        }
		       
		    }*/
			,{
		        field: 'supervisor',
		        title: '主管领导',
		        width:"95px",
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if(value != null && value != ''){
			        	if(value.length > 7){
				        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,7) +"</a></abbr>";
			        	}
		        	}
		        	return value;
		         }
		       
		    }
			,{
		        field: 'principal',
		        title: '项目负责人',
		        width:"95px",
		        sortable: true
	        	,formatter:function(value, row , index){
		        	if(value != null && value != ''){
			        	if(value.length > 7){
				        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,7) +"</a></abbr>";
			        	}
		        	}
		        	return value;
		         }
		       
		    }
			,{
		        field: 'specialty',
		        title: '专业',
		        width:"70px",
		        sortable: true
		       
		    }
			,{
		        field: 'state',
		        title: '项目状态',
		        width:"80px",
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('pro_state'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'specState',
		        title: '专业状态',
		        width:"105px",
		        formatter:function(value, row , index){
		        	var oldValue = value;
		        	//项目没完成
		        	if(row.state != '5'){
			        	var str = ""; 
	        			if(value == 1){
	        				str = str +"<option value='1' selected>正在进行</option>";
	        			}else{
	        				str = str +"<option value='1'>正在进行</option>";
	        			}
			        	if(value == 2){
			        		str = str +"<option value='2' selected>待评审</option>";
	        			}else{
	        				str = str +"<option value='2'>待评审</option>";
	        			}
			        	if(value == 3){
			        		str = str +"<option value='3' selected>待批复</option>";
	        			}else{
	        				str = str +"<option value='3'>待批复</option>";
	        			}
			        	if(value == 4){
			        		str = str +"<option value='4' selected>暂停</option>";
	        			}else{
	        				str = str +"<option value='4'>暂停</option>";
	        			}
			        	if(value == 5){
			        		str = str +"<option value='5' selected>完成</option>";
	        			}else{
	        				str = str +"<option value='5'>完成</option>";
	        			}
			        	value = "<select id='specState"+ index +"' class='form-control input-sm' onChange=\"onChan('"+ row.id +"','"+ index +"')\">" +
			        			"<option value='0'></option>" + str +"</select>";
			        	return value;
		        	}else{
		        		return jp.getDictLabel(${fns:toJson(fns:getDictList('pro_state'))}, oldValue, "-");
		        	}
		        }
		       
		    }
			,{
		        field: 'plan',
		        title: '进度',
		        align: 'right',
		        width:"90px",
		        formatter:function(value, row , index){
		        	var oldValue = value;
		        	//项目没完成
		        	if(row.state != '5'){
			        	var str = "";
			        	if(value == 1){
			        		str = str +"<option value='1' selected>10%</option>";
	        			}else{
	        				str = str +"<option value='1'>10%</option>";
	        			}
			        	if(value == 2){
			        		str = str +"<option value='2' selected>20%</option>";
	        			}else{
	        				str = str +"<option value='2'>20%</option>";
	        			}
			        	if(value == 3){
			        		str = str +"<option value='3' selected>30%</option>";
	        			}else{
	        				str = str +"<option value='3'>30%</option>";
	        			}
			        	if(value == 4){
			        		str = str +"<option value='4' selected>40%</option>";
	        			}else{
	        				str = str +"<option value='4'>40%</option>";
	        			}
			        	if(value == 5){
			        		str = str +"<option value='5' selected>50%</option>";
	        			}else{
	        				str = str +"<option value='5'>50%</option>";
	        			}
			        	if(value == 6){
			        		str = str +"<option value='6' selected>60%</option>";
	        			}else{
	        				str = str +"<option value='6'>60%</option>";
	        			}
			        	if(value == 7){
			        		str = str +"<option value='7' selected>70%</option>";
	        			}else{
	        				str = str +"<option value='7'>70%</option>";
	        			}
			        	if(value == 8){
			        		str = str +"<option value='8' selected>80%</option>";
	        			}else{
	        				str = str +"<option value='8'>80%</option>";
	        			}
			        	if(value == 9){
			        		str = str +"<option value='9' selected>90%</option>";
	        			}else{
	        				str = str +"<option value='9'>90%</option>";
	        			}
			        	if(value == 10){
			        		str = str +"<option value='10' selected>已完成</option>";
	        			}else{
	        				str = str +"<option value='10'>已完成</option>";
	        			}
			        	
			        	value = "<select id='plan"+ index +"' class='form-control input-sm' onChange=\"onChan('"+ row.id +"','"+ index +"')\">" +
			        			"<option value='0'></option>" + str +"</select>";
			        	return value;
		        	}else{
		        		return jp.getDictLabel(${fns:toJson(fns:getDictList('plan_state'))}, oldValue, "-");
		        	}
		        }
		       
		    }
			,{
		        field: 'content',
		        width:"160px",
		        title: '设计内容',
		        cellStyle:formatCol1,
		        formatter:function(value, row , index){
		        	if(typeof(value) == 'undefined'){
		        		value = "";
		        	}
		        	//项目没完成
		        	if(row.state != '5'){
			        	return "<span class='sContent'>" +
			        				"<abbr id='sContent1"+ index +"' title='"+ value +"'><a style='color:#676a6c;' id='sContent2"+ index +"' >"+ formatterValue(value,11) +"</a></abbr>"+
			        		   "</span>"+
			        	"<input type='hidden' id='oldContent"+ index +"' value='"+ value +"'/>"+
			        	"<div style='position:relative;'>" +
	        				"<textarea rows='10' cols='5' maxlength='255' style='position:absolute;display:none;top:0px;left:0px;' id='iContent"+ index +"' onBlur=\"onBlurs('"+ row.id +"',this,'#sContent1"+index+"','#sContent2"+index+"','#iContent"+index+"','11','#oldContent"+ index +"')\" class='iContent form-control input-sm'>"+ value +"</textarea>";
			        	"</div>";
			        }else{
		        		if(value.length > 15){
				        	return "<abbr title='"+ value +"'><a style='color:#676a6c;'>"+ formatterValue(value,11) +"</a></abbr>";
			        	}
		        		return value;
		        	}
		        }
		       
		    }
			,{
		        field: 'finishDate',
		        width:"95px",
		        title: '计划完成时间',
		        formatter:function(value, row , index){
		        	//项目没完成
		        	if(row.state != '5'){
			        	return "<input type='hidden' id='oldDate"+ index +"' value='"+ value +"'/>"+
							   "<input type='text' id='finishDate"+ index +"' name='demand"+ index +"' class='form-control required input-sm'  value='"+ value +"' onBlur=\"finishDates('"+ row.id +"','"+ index +"')\"/>";
			        }else{
			        	return value;
			        }
		        }
		        	
		       
		    }
			,{
		        field: 'difficulty',
		        width:"210px",
		        title: '制约因素',
		        cellStyle:formatCol2,
		        formatter:function(value, row , index){
		        	if(typeof(value) == 'undefined'){
		        		value = "";
		        	}
		        	//项目没完成
		        	if(row.state != '5'){
			        	return "<span id='sDifficulty"+ index +"' class='sDifficulty'>" +
			        				"<abbr id='sDifficulty1"+ index +"' title='"+ value +"'><a style='color:#676a6c;' id='sDifficulty2"+ index +"' >"+ formatterValue(value,15) +"</a></abbr>"+
			        		   "</span>"+
					    "<input type='hidden' id='oldDifficulty"+ index +"' value='"+ value +"'/>"+
	        			"<div style='position:relative;'>" +
	        				"<textarea rows='10' cols='5' maxlength='255' style='position:absolute;display:none;top:0px;left:0px;' id='iDifficulty"+ index +"' onBlur=\"onBlurs('"+ row.id +"',this,'#sDifficulty1"+index+"','#sDifficulty2"+index+"','#iDifficulty"+index+"','15','#oldDifficulty"+index+"')\" class='iDifficulty form-control input-sm'>"+ value +"</textarea>" +
	        			"</div>";
		        	}else{
		        		if(value.length > 15){
				        	return "<abbr title='"+ value +"'><a style='color:#676a6c;'>"+ formatterValue(value,15) +"</a></abbr>";
			        	}
		        		return value;
		        	}
		        }
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#projectWorkCaseTable').bootstrapTable("toggleView");
		}
	  
	  $('#projectWorkCaseTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#projectWorkCaseTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#projectWorkCaseTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/project/projectWorkCase/import/template';
				  },
			    btn2: function(index, layero){
				        var inputForm =top.$("#importForm");
				        var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
				        inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
				        inputForm.onsubmit = function(){
				        	jp.loading('  正在导入，请稍等...');
				        }
				        inputForm.submit();
					    jp.close(index);
				  },
				 
				  btn3: function(index){ 
					  jp.close(index);
	    	       }
			}); 
		});
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#projectWorkCaseTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $("#proState").val("1,2,3");
		  $("#rememberMe4").removeAttr("checked");
		  $("#rememberMe5").removeAttr("checked");
		  $('#projectWorkCaseTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#projectWorkCaseTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该个人工作情况记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/project/projectWorkCase/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#projectWorkCaseTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function add(){
	  jp.openDialog('新增个人工作情况', "${ctx}/project/projectWorkCase/form",'800px', '500px', $('#projectWorkCaseTable'));
  }
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
  	  jp.get("${ctx}/project/projectWorkCase/getProState?ids="+id,function(data){
  		 if(data == 5){
  		   jp.info("选中的项目记录已完成,不可修改!");
  		   return;
  		 }
  		<shiro:hasPermission name="project:projectWorkCase:edit">
		  jp.openDialog('编辑个人工作情况', "${ctx}/project/projectWorkCase/form?id=" + id,'800px', '500px', $('#projectWorkCaseTable'));
		   </shiro:hasPermission>
  	  });
  }
  
  function view(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	  <shiro:hasPermission name="project:projectWorkCase:view">
	  jp.openDialogView('查看个人工作情况', "${ctx}/project/projectWorkCase/view?id=" + id,'800px', '500px', $('#projectWorkCaseTable'));
	  </shiro:hasPermission>
  }
  /**
   * 格式化value
   * @param value
   * @returns
   */
  function formatterValue(value,length){
	  if(value != null && value != ''){
		  value = value.substring(0,length);
	  }
	  return value;
  }
  
  /**
	 * 根据查询结果导出excel
	 * @returns
	 */
	function exportExcel(){
		var order = $(".desc");
		var param = "";
		if (order.length > 0 ) {
			var field = order.parent().attr("data-field");
			param = 'orderBy='+field+ " "+  "desc";
		}
		order = $(".asc");
		if (order.length > 0 ) {
			var field = order.parent().attr("data-field");
			param = 'orderBy='+field+ " "+  "asc";
		}
	 	$("#searchForm").attr("action","${ctx}/project/projectWorkCase/export?"+param);
	 	$("#searchForm").submit();
		
	}
	
	//给需要编辑的列加上一个class
	function formatCol1(value, row, index) {
	    return {
	    	classes: 'edit1'
	    }
	}
	
	function formatCol2(value, row, index) {
	    return {
	    	classes: 'edit2'
	    }
	}
	
	//单击单元格
	function clickTd(field, value, row, $el){
		  inputHide(); spanShow();
		  //点击的列为增量
		  if($el.hasClass("edit1") || $el.hasClass("edit2")){
			  //项目没完成可填写
			  if(row.state != '5'){
				  $($el).find("span").hide();
				  $($el).find("textarea").show().focus();
	  		  }
		  }
	  }
	  
	  //隐藏输入框
	  function inputHide(){
		  $(".iDifficulty").hide();
		  $(".iContent").hide();
		  
	  }
	  
	  //显示span标签
	  function spanShow(){
		  $(".sDifficulty").show();
		  $(".sContent").show();
	  }
	  
	//输入框失去焦点
	  function onBlurs(id,obj,spanId1,spanId2,inputId,length,oldValue){
		  inputHide(); spanShow();
		  //获得当前元素下标
		  var idx = parseInt($(obj).attr("id").replace(/[^0-9]/ig,""));
		  //判断用户有没有修改值
		  if($(spanId2).text().trim() != $(inputId).val()){
			  $(spanId1).prop("title",$(inputId).val());
			  $(spanId2).text(formatterValue($(inputId).val(),length));
		  }
		  //改变了内容
		  if($(oldValue).val() != $(inputId).val().trim()){
			  $(oldValue).val($(inputId).val().trim())
			  //保存当前行
			  saveLine(id,idx);
		  }
	  }
	  
	  //保存当前行
	  function onChan(id,idx){
		  //保存当前行
		  saveLine(id,idx);
	  }
	  
	  //计划完成时间
	  function finishDates(id,index){
		  var oldDate = $("#oldDate"+index).val();
		  var newDate = $("#finishDate"+index).val();
		  if(oldDate != newDate){
			  $("#oldDate"+index).val(newDate);
			  saveLine(id,index);
		  }
	  }
	  
	  //保存当前行
	  function saveLine(id,idx){
		  jp.post("${ctx}/project/projectWorkCase/save",{id:id,specState:$("#specState"+idx).val(),
			  plan:$("#plan"+idx).val(),content:$("#iContent"+idx).val(),finishDate:$("#finishDate"+idx).val(),
			  difficulty:$("#iDifficulty"+idx).val()},
		  function(data){
				if(data.success){
					$("#successInfo").text(data.msg);
					setTimeout("clear()",3000);
				}else{
    	  			jp.error(data.msg);
				}  
		  });
	  }
	  
	  function clear(){
		  $("#successInfo").text("");
	  }
</script>