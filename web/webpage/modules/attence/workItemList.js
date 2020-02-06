<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	//个人工日汇总
	$('#workItemlTale').bootstrapTable({
		 
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
               pageSize: 25,  
               //可供选择的每页的行数（*）    
               pageList: [25,50],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/attence/item/workData",
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
               },
              
               onClickRow: function(row, $el){
               },
               columns: [
            [
	            /*{
                   //field: 'Number',//可不加
                   title: '序号',//标题  可不加
                   valign: "middle",
                   align: "center",
                   headWidth:'50px',
                   width : '50px',
					colspan: 1,
					rowspan: 2,
                   formatter: function (value, row, index) {
                       return index + 1;
                   }
	            }
	            ,*/{
			        field: 'user.name',
			        title: '专业人员',
			        align: 'left',
			    	valign : 'middle',
					colspan: 1,
					rowspan: 2
			       
			    }
	            ,{
 					title : '总工日',
 					field : 'sumWorkTime',
 					align : 'right',
 					valign : 'middle',
 					colspan: 1,
 					rowspan: 2
	 			}
	 			,{
 					title : '<center>总加班</center><center>(小时)</center>',
 					field : 'sumOverTime',
 					align : 'right',
 					valign : 'middle',
 					colspan: 1,
 					rowspan: 2
	 			}

                ,{
                title : '<center>折算</center><center>总工日</center>',
                field : 'overTimeDay',
                align : 'right',
                valign : 'middle',
                colspan: 1,
                rowspan: 2
            }
	            ,{
	   		        title: '内业',
			        align: 'center',
	   		        valign : 'middle',
					colspan: 2,
					rowspan: 1
	   		       
	   		    }
	           ,{
	   		        title: '设代',
			        align: 'center',
	   		        valign : 'middle',
					colspan: 2,
					rowspan: 1
	   		       
	   		    }
				/*,{
			        title: '党工团',
			        align: 'center',
	   		        valign : 'middle',
					colspan: 2,
					rowspan: 1
			       
			    }*/
				,{
			        title: '零星事务',
			        align: 'center',
	   		        valign : 'middle',
					colspan: 2,
					rowspan: 1
			       
			    }
				,{
			        field: 'train.workTime',
			        title: '培训',
			        align: 'center',
	   		        valign : 'middle',
					colspan: 2,
					rowspan: 1
			       
			    }
			

				,{
			        title: '办公室',
			        align: 'center',
	   		        valign : 'middle',
					colspan: 2,
					rowspan: 1
			       
			    }
				,{
			        title: '出差',
			        align: 'center',
	   		        valign : 'middle',
					colspan: 2,
					rowspan: 1
			       
			    }
				,{
			        title: '学习',
			        align: 'center',
	   		        valign : 'middle',
					colspan: 2,
					rowspan: 1
			       
			    }
                ,{
                title: '其他1',
                align: 'center',
                valign : 'middle',
                colspan: 2,
                rowspan: 1

            }
                ,{
                title: '其他2',
                align: 'center',
                valign : 'middle',
                colspan: 2,
                rowspan: 1

            }
                ,{
                title: '其他3',
                align: 'center',
                valign : 'middle',
                colspan: 2,
                rowspan: 1

            }
				,{
			        field: 'inspect.workTime',
			        title: '考察',
			        align: 'right',
	   		        valign : 'middle',
					colspan: 1,
					rowspan: 2
			       
			    }
				,{
			        field: 'yearLeave.workTime',
			        title: '年假',
			        align: 'right',
	   		        valign : 'middle',
					colspan: 1,
					rowspan: 2
			       
			    }
				,{
			        field: 'sickLeave.workTime',
			        title: '病假',
			        align: 'right',
	   		        valign : 'middle',
					colspan: 1,
					rowspan: 2
			       
			    }
				,{
			        field: 'casualLeave.workTime',
			        title: '事假',
			        align: 'right',
	   		        valign : 'middle',
					colspan: 1,
					rowspan: 2
			       
			    }	
			],
		    [
		     	{
					title : '工日',
					field : 'officePro.workTime',
					align : 'right',
					valign : 'middle'
				}
	  			,{
					title : '加班',
					field : 'officePro.overTime',
					align : 'right',
					valign : 'middle'
	  			}
	  			,{
					title : '工日',
					field : 'outPro.workTime',
					align : 'right',
					valign : 'middle'
				}
	  			,{
					title : '加班',
					field : 'outPro.overTime',
					align : 'right',
					valign : 'middle'
	  			}
	  		/*	,{
					title : '工日',
					field : 'orgPro.workTime',
					align : 'right',
					valign : 'middle'
				}
	  			,{
					title : '加班',
					field : 'orgPro.overTime',
					align : 'right',
					valign : 'middle'
	  			}*/
	  			,{
					title : '工日',
					field : 'siteWork.workTime',
					align : 'right',
					valign : 'middle'
				}
	  			,{
					title : '加班',
					field : 'siteWork.overTime',
					align : 'right',
					valign : 'middle'
	  			}
	  			,{
					title : '工日',
					field : 'train.workTime',
					align : 'right',
					valign : 'middle'
				}
	  			,{
					title : '加班',
					field : 'train.overTime',
					align : 'right',
					valign : 'middle'
	  			}

	  			,{
					title : '工日',
					field : 'rcgl.workTime',
					align : 'right',
					valign : 'middle'
				}
	  			,{
					title : '加班',
					field : 'rcgl.overTime',
					align : 'right',
					valign : 'middle'
	  			}
	  			,{
					title : '工日',
					field : 'cc.workTime',
					align : 'right',
					valign : 'middle'
				}
	  			,{
					title : '加班',
					field : 'cc.overTime',
					align : 'right',
					valign : 'middle'
	  			}
	  			,{
					title : '工日',
					field : 'xx.workTime',
					align : 'right',
					valign : 'middle'
				}
	  			,{
					title : '加班',
					field : 'xx.overTime',
					align : 'right',
					valign : 'middle'
	  			}
                ,{
                title : '工日',
                field : 'cw.workTime',
                align : 'right',
                valign : 'middle'
            }
                ,{
                title : '加班',
                field : 'cw.overTime',
                align : 'right',
                valign : 'middle'
            }
                ,{
                title : '工日',
                field : 'sw.workTime',
                align : 'right',
                valign : 'middle'
            }
                ,{
                title : '加班',
                field : 'sw.overTime',
                align : 'right',
                valign : 'middle'
            }
                ,{
                title : '工日',
                field : 'jy.workTime',
                align : 'right',
                valign : 'middle'
            }
                ,{
                title : '加班',
                field : 'jy.overTime',
                align : 'right',
                valign : 'middle'
            }
	  		]
		   ]
		
		});
		
	$('#workDateStart').datetimepicker({
		 format: "YYYY-MM",
		 locale: moment.locale('zh-cn')
	});
	
	$('#workDateEnd').datetimepicker({
		 format: "YYYY-MM",
		 locale: moment.locale('zh-cn')
	});
	
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#workItemlTale').bootstrapTable("toggleView");
		}
	  
	  $('#workItemlTale').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/attence/workAttence/import/template';
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
		  $('#workItemlTale').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#workItemlTale').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#workItemlTale").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
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
	 	$("#searchForm").attr("action","${ctx}/attence/item/exportWorkData?"+param);
	 	$("#searchForm").submit();
		
	}
</script>