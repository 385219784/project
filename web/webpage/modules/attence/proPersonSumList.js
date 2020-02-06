<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	
	
	


	
	//表头信息
	var headAll=new Array();
	//第一行
	var  headTop=new Array();
	
	var  head={
			title : '序号',
			valign : 'middle',
		    width:"50px",
			colspan: 1,
			rowspan: 2,
            formatter: function (value, row, index) {
                return index + 1;
            }
		};
	var  head0={
			title : '专业人员',
			field : 'user.name',
			valign : 'middle',
		    width:"60px",
			colspan: 1,
			rowspan: 2
			};
	var  head1={
	title : '项目编号',
	field : 'pro.num',
	valign : 'middle',
    width:"65px",
	colspan: 1,
	rowspan: 2
	};
	var  head2={
			title : '项目简称',
			field : 'pro.proShortened',
			valign : 'middle',
	        width:"170px",
			colspan: 1,
			rowspan: 2
			,formatter:function(value, row , index){
	        	if(value != null && value != ''){
		        	if(value.length > 13){
			        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,13) +"</a></abbr>";
		        	}
	        	}
	        	return value;
	         }
			};
	var  head3={
			title : '设计类别',
			field : 'pro.designType',
			valign : 'middle',
	        width:"100px",
			colspan: 1,
			rowspan: 2,
			formatter:function(value, row , index){
    			return jp.getDictLabel(${fns:toJson(fns:getDictList('design_type'))}, value, "-");
			}
	};
					
			var  head4={
					title : '主管领导',
					field : 'pro.supervisor',
					valign : 'middle',
			        width:"95px",
					colspan: 1,
					rowspan: 2
					,formatter:function(value, row , index){
			        	if(value != null && value != ''){
				        	if(value.length > 7){
					        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,7) +"</a></abbr>";
				        	}
			        	}
			        	return value;
			         }
					};
			var  head5={
					title : '项目负责人',
					field : 'pro.principal',
					valign : 'middle',
			        width:"95px",
					colspan: 1,
					rowspan: 2
					,formatter:function(value, row , index){
			        	if(value != null && value != ''){
				        	if(value.length > 7){
					        	return "<abbr title='"+ value +"' class='initialism'><a style='color:#676a6c;'>"+ formatterValue(value,7) +"</a></abbr>";
				        	}
			        	}
			        	return value;
			         }
					};

			var  head6={
					title : '总工日',
					field : 'sumWorkTime',
					align : 'right',
					valign : 'middle',
			        width:"55px",
					colspan: 1,
					rowspan: 2
					};

	
			var  head7={
					title : '<center>总加班</center><center>(小时)</center>',
					field : 'sumOverTime',
					align : 'right',
					valign : 'middle',
			        width:"55px",
					colspan: 1,
					rowspan: 2
					};
     var headButton=new  Array(); 

     headTop.push(head,head0,head1,head2,head3,head4,head5,head6,head7);
	//动态获取列
     $.ajax({ 
    	         type: "post", 
    	         url: "${ctx}/attence/workAttence/getAllSpec", 
    	         cache:false, 
    	         async:false, 
    	         success: function(data){
	  					for(var  i=0;i<data.length;i++){
	  						var  zy=data[i];
	  						var  first=	  		
	  							{
	  								title : zy.name,
	  								align : 'center',
	  								valign : 'middle',
							        width:"80px",
	  								colspan: 2,
	  								rowspan: 1
	  							};
					  			var  second=	  		
					  				{
					  					title : '工日',
					  					field : 'work'+zy.colNum,
					  					align : 'right',
								        width:"40px",
					  					valign : 'middle'
					  				};
						  			var  three=	  		
					  				{
					  					title : '加班',
					  					field : 'over'+zy.colNum,
					  					align : 'right',
								        width:"40px",
					  					valign : 'middle'
					  				}
					  			headTop.push(first);
					  			headButton.push(second,three);
	  		}	
	  	}
    	 });
     	
	  	headAll.push(headTop,headButton);

	  	console.log(JSON.stringify(headAll))
	$('#proSum').bootstrapTable({
		 
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
               //是否固定左侧列
               //leftFixedColumns: true,
               //左侧固定几列
               //leftFixedNumber: 7,
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
               url: "${ctx}/attence/workAttence/proPersonSumData",
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
               
               columns :headAll,
               
        /*       columns: [{
		        field: 'createBy.name',
		        title: '专业人员',
		        sortable: true
		       
		    }
            ,{
   		        field: 'proNum',
   		        title: '项目编号',
   		        sortable: true
   		       
   		    }
            ,{
   		        field: 'proName',
   		        title: '项目名称',
   		        sortable: true
   		       
   		    }
			,{
		        field: 'workTime',
		        title: '项目工日',
		        sortable: true
		       
		    }
			,{
		        field: 'overTime',
		        title: '加班小时',
		        sortable: true
		       
		    }
		     ]*/
		
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

		 
		  $('#proSum').bootstrapTable("toggleView");
		}
	  
	  $('#proSum').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
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
		  $('#proSum').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#proSum').bootstrapTable('refresh');
		});
		
		
	});

	function getSpec(){		
		
		
		
	}


		
  function getIdSelections() {
        return $.map($("#proSum").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  
  	/**
  	 * 导出
  	 * @returns
  	 */
	function exportExcel(){
	 	$("#searchForm").attr("action","${ctx}/attence/workAttence/exportProPersonSum");
	 	$("#searchForm").submit();
		
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
</script>