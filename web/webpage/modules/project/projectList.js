<%@ page contentType="text/html;charset=UTF-8" %>
<script>
//项目一览表
$(document).ready(function() {
	//表头信息
	var headAll=new Array();
	var head0={
		    checkbox: true
		};
	var head1={
			title : '序号',
			valign : 'middle',
		    width:"50px"
	        ,formatter:function(value, row , index){
	        	return index+1;
	        }
		};
	var head2={
			title : '项目编号',
			field : 'num',
			valign : 'middle',
		    width:"90px",
	        sortable: true,
	        formatter:function(value, row , index){
	        	return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
	        }
		};
	var head3={
			title : '项目简称',
			field : 'proShortened',
			valign : 'middle',
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
		};
	var head4={
			title : '项目类别',
			field : 'type',
			valign : 'middle',
	        sortable: true,
	        width:"90px",
	        formatter:function(value, row , index){
	        	return jp.getDictLabel(${fns:toJson(fns:getDictList('pro_type'))}, value, "-");
	        }
		};
	/*var head5={
			title : '设计阶段',
			field : 'designType',
			valign : 'middle',
	        sortable: true,
	        width:"110px",
	        formatter:function(value, row , index){
	        	return jp.getDictLabel(${fns:toJson(fns:getDictList('design_type'))}, value, "-");
	        }
		};*/
	var head6={
			title : '主管领导',
			field : 'supervisor',
			valign : 'middle',
	        sortable: true,
	        width:"95px"
	        ,formatter:function(value, row , index){
	        	if(value != null && value != ''){
		        	if(value.length > 7){
			        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,7) +"</a></abbr>";
		        	}
	        	}
	        	return value;
	         }
		};
	var head7={
			title : '项目负责人',
			field : 'principal',
			valign : 'middle',
	        sortable: true,
	        width:"95px"
        	,formatter:function(value, row , index){
	        	if(value != null && value != ''){
		        	if(value.length > 7){
			        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,7) +"</a></abbr>";
		        	}
	        	}
	        	return value;
	         }
		};
	var head8={
			title : '进度要求',
			field : 'demand',
			valign : 'middle',
		    width:"90px",
		    sortable: true
		};
	var head9={
			title : '状态',
			field : 'state',
			valign : 'middle',
	        width:"70px",
		    sortable: true,
	        formatter:function(value, row , index){
	        	return jp.getDictLabel(${fns:toJson(fns:getDictList('pro_state'))}, value, "-");
	        }
		};
	var head10={
			title : '进度',
			field : 'planState',
			valign : 'middle',
	        width:"70px",
	        sortable: true,
	        formatter:function(value, row , index){
	        	return jp.getDictLabel(${fns:toJson(fns:getDictList('plan_state'))}, value, "-");
	        }
		};
	var head11={
			title : '上年度计奖比例',
			field : 'content',
			valign : 'middle',
	        sortable: true,
	        width:"160px",
	        formatter:function(value, row , index){
	        	if(value != null && value != ''){
		        	if(value.length > 11){
			        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,11) +"</a></abbr>";
		        	}
	        	}
	        	return value;
	        }
		};
	var head12={
			title : '归档状态',
			field : 'question',
			valign : 'middle',
	        sortable: true,
	        width:"160px",
	        formatter:function(value, row , index){
	        	if(value != null && value != ''){
		        	if(value.length > 11){
			        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,11) +"</a></abbr>";
		        	}
	        	}
	        	return value;
	        }
		};
	var head13={
			title : '任务单编号',
			field : 'feature',
			valign : 'middle',
	        sortable: true,
	        width:"360px",
	        formatter:function(value, row , index){
	        	if(value != null && value != ''){
		        	if(value.length > 27){
			        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,27) +"</a></abbr>";
		        	}
	        	}
	        	return value;
	        }
		};
	headAll.push(head1,head2,head3,head4/*,head5*/,head6,head7,head8,head9,head10);
	//动态获取列
    $.ajax({ 
         type: "post", 
         url: "${ctx}/attence/workAttence/getAllSpec", 
         cache:false, 
         async:false, 
         success: function(data){
			for(var  i=0;i<data.length;i++){
				var  zy=data[i];
				var  head=	  		
					{
						title : zy.name,
						field : "spec"+zy.colNum,
						align : 'left',
						valign : 'middle',
				        width:"80px",
				        formatter:function(value, row , index){
				        	if(value != null && value != ''){
					        	if(value.length > 12){
						        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,12) +"</a></abbr>";
					        	}
				        	}
				        	return value;
				        }
					};
				headAll.push(head);	
			}	
  		}
   	});
	headAll.push(head11,head12,head13);
	$('#projectTable').bootstrapTable({
		 
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
               leftFixedColumns: true,
               //左侧固定几列
               leftFixedNumber: 9,
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
               pageSize: 100,  
               //可供选择的每页的行数（*）    
               pageList: [100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/project/project/data",
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
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                   	edit(row.id);
                   } else if($el.data("item") == "proceed"){
                	   updateState(row.id,1,'正在进行',row);
                   }else if($el.data("item") == "review"){
                	   updateState(row.id,2,'待评审',row);
                   }else if($el.data("item") == "reply"){
                	   updateState(row.id,3,'待批复',row);
                   }else if($el.data("item") == "stop"){
                	   updateState(row.id,4,'暂停',row);
                   }else if($el.data("item") == "finish"){
                	   updateState(row.id,5,'完成',row);
                   }else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该项目信息记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/project/project/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#projectTable').bootstrapTable('refresh');
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
               columns :headAll,
		
		});
		
	$('#startDate1').datetimepicker({
		 format: "YYYY-MM-DD",
		 locale: moment.locale('zh-cn')
   });
	
	$('#startDate2').datetimepicker({
		 format: "YYYY-MM-DD",
		 locale: moment.locale('zh-cn')
   });
	
	$('#endDate1').datetimepicker({
		 format: "YYYY-MM-DD",
		 locale: moment.locale('zh-cn')
  });
	
	$('#endDate2').datetimepicker({
		 format: "YYYY-MM-DD",
		 locale: moment.locale('zh-cn')
  });
	
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#projectTable').bootstrapTable("toggleView");
		}
	  
	  $('#projectTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#projectTable').bootstrapTable('getSelections').length);
            $('.state').prop('disabled', ! $('#projectTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#projectTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/project/project/import/template';
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
		  $('#projectTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  //项目状态
		  $("#proState").val("1");
		  $("#rememberMe2").removeAttr("checked");
		  $("#rememberMe3").removeAttr("checked");
		  $("#rememberMe4").removeAttr("checked");
		  $("#rememberMe5").removeAttr("checked");
		  //项目类别
		  $("#proType").val("7,9,10,11");
		  $("#rememberMe01").removeAttr("checked");
		  $("#rememberMe02").removeAttr("checked");
		  $("#rememberMe03").removeAttr("checked");
		  $("#rememberMe04").removeAttr("checked");
		  $('#projectTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#projectTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){
	  var id = getIdSelections();
	  /*jp.get("${ctx}/project/project/getProState?ids=" + id, function(data){
	  		if(data == 5){
	  			jp.info("该项目已完成,不可删除!");
	  			return;
	  		}*/
		jp.confirm('确认要删除该项目信息记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/project/project/deleteAll?ids=" + id, function(data){
         	  		if(data.success){
         	  			$('#projectTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
	 /* });*/
	
  }
   function add(){
	  jp.openDialog('新增项目信息<span style="color:red;">（项目编号规则：1.只输入字母,系统自动生成序号&nbsp;2.输入中包含数字则自动生成序号失效）</span>', "${ctx}/project/project/form",'850px', '500px', $('#projectTable'));
  }
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
  	/*jp.get("${ctx}/project/project/getProState?ids=" + id, function(data){
  		if(data == 5){
  			jp.info("该项目已完成,不可修改!");
  			return;
  		}*/
  		<shiro:hasPermission name="project:project:edit">
  	  jp.openDialog('编辑项目信息', "${ctx}/project/project/form?id=" + id,'850px', '500px', $('#projectTable'));
  	   </shiro:hasPermission>
  	/*});*/
  }
	   
  
  function view(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="project:project:view">
	  jp.openDialogView('查看项目信息', "${ctx}/project/project/view?id=" + id,'800px', '500px', $('#projectTable'));
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
   * 改变项目状态
   * @param state
   * @returns
   */
  function updateState(id,state,info,row){
	  if(id == undefined || id == null || id == ""){
		  id = getIdSelections();
	  }
	  if(state == 1){
		  if(row.state == 3){
			jp.info("该项目已待批复,不能改变状态为正在进行!");
			return;
		  }
	  }
	  //if(state == 5){
		  jp.confirm("确认要改变该项目的状态为"+ info +"吗？", function(){
				jp.loading();  	
				jp.get("${ctx}/project/project/updateState?ids=" + id + "&state="+ state, function(data){
	       	  		if(data.success){
	       	  			$('#projectTable').bootstrapTable('refresh');
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	        	   
			})
	  /*}else{
		  jp.get("${ctx}/project/project/getProState?ids=" + id, function(data){
			  if(data != 5){
				  jp.confirm("确认要改变该项目的状态为"+ info +"吗？", function(){
						jp.loading();  	
						jp.get("${ctx}/project/project/updateState?ids=" + id + "&state="+ state, function(data){
			       	  		if(data.success){
			       	  			$('#projectTable').bootstrapTable('refresh');
			       	  			jp.success(data.msg);
			       	  		}else{
			       	  			jp.error(data.msg);
			       	  		}
			       	  	})
			        	   
					})
			  }else{
				  var num = id.toString().indexOf(",");
				  if(num != -1){
					  jp.info("您勾选的项目记录中存在已完成的项目,不可批量修改项目状态!");
				  }else{
					  jp.info("您勾选的项目已完成,不可修改项目状态!");
				  }
				  return;
			  }
		  });
	  }*/
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
	 	$("#searchForm").attr("action","${ctx}/project/project/export?"+param);
	 	$("#searchForm").submit();
		
	}
</script>