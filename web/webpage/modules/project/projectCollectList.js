<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	//个人项目汇总
	$('#projectCollectTable').bootstrapTable({
		 
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
               pageSize: 200,  
               //可供选择的每页的行数（*）    
               pageList: [200,300,400,500],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/project/projectWorkCase/datas",
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
                   	  			$('#projectCollectTable').bootstrapTable('refresh');
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
               columns: [/*{
		        checkbox: true
		       
		    },*//*{
                field: 'serial',//可不加
                title: '序号',//标题  可不加
                align: "center",
                width : '50px'
            }
			,*/{
 		        field: 'specialtyUser',
 		        title: '专业人员',
		        width:"80px",
 		        sortable: true
 		       
 		    }
			/*,{
		        field: 'proNum',
		        title: '项目编号',
		        width:"80px",
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
		         }
		       
		    }*/
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
		        title: '设计阶段',
		        sortable: true,
		        width:"110px",
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('design_type'))}, value, "-");
		        }
		       
		    }*/
			/*,{
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
		       
		    }*/
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
		        width:"70px",
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('pro_state'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'plan',
		        title: '进度',
		        width:"50px",
		        align: 'right',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('plan_state'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'content',
		        width:"160px",
		        title: '设计内容',
		        formatter:function(value, row , index){
		        	if(value != null && value != ''){
			        	if(value.length > 11){
				        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,11) +"</a></abbr>";
			        	}
		        	}
		        	return value;
		        }
		       
		    }
			,{
		        field: 'finishDate',
		        width:"90px",
		        title: '计划完成时间'
		       
		    }
			,{
		        field: 'difficulty',
		        width:"210px",
		        title: '制约因素',
		        formatter:function(value, row , index){
		        	if(value != null && value != ''){
			        	if(value.length > 15){
				        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,15) +"</a></abbr>";
			        	}
		        	}
		        	return value;
		        }
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#projectCollectTable').bootstrapTable("toggleView");
		}
	  
	  $('#projectCollectTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#projectCollectTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#projectCollectTable').bootstrapTable('getSelections').length!=1);
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
		  $('#projectCollectTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $("#proState").val("1");
		  $("#rememberMe2").removeAttr("checked");
		  $("#rememberMe3").removeAttr("checked");
		  $("#rememberMe4").removeAttr("checked");
		  $("#rememberMe5").removeAttr("checked");
		  $('#projectCollectTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#projectCollectTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function view(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	  <shiro:hasPermission name="project:projectCollect:view">
	  jp.openDialogView('查看', "${ctx}/project/projectWorkCase/views?id=" + id,'800px', '500px', $('#projectCollectTable'));
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
	 	$("#searchForm").attr("action","${ctx}/project/projectWorkCase/exportFile?"+param);
	 	$("#searchForm").submit();
		
	}
</script>