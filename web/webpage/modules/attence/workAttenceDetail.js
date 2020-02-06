<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	
	
	var  userId=$("#userId").val();
	var  dateTime=$("#dateStr").val();
	
	$('#workDetail').bootstrapTable({	 
		  //请求方法
               method: 'get',
               //类型json
               dataType: "json",
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               //showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       //showExport: true,
    	       height: $(document.body).height(),
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
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
               pageSize: 50,  
               //可供选择的每页的行数（*）    
               pageList: [50,100,200,300,400,500],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/attence/workAttence/getDetail?userId="+userId+"&dateStr="+dateTime,
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
               onClickRow: function(row, $el){
               },
               onLoadSuccess:function(data){
            	if(data.rows.length>0){           		
            		// 隐藏赋值
            		var  i=0;
            		$('.edit1').each(function(){
    					$(this).parent().append("<input type='hidden'   class='proclass'  id='proId"+i+"'   value='"+data.rows[i].proId+"'/>");
    					$(this).parent().append("<input type='hidden'   id='specId"+i+"'   value='"+data.rows[i].specId+"'/>");
    					if(data.rows[i].id!=undefined){
            				$(this).parent().append("<input type='hidden'   id='id"+i+"'    value='"+data.rows[i].id+"'/>");
            			}else{
            				$(this).parent().append("<input type='hidden'  id='id"+i+"'    value=''/>");
            			};
    					i++;
    				})
            	}   
               },
               columns: [
            {
		        field: 'proNum',
		        title: '项目编号',
		        align:'left'
               }
			,{
		        field: 'proName',
		        title: '项目名称',
		        align:'left'
		    }
			,{
		        field: 'specName',
		        title: '专业类别',
		    }
			,{
		        field: 'workTime',
		        title: '工日',
		        formatter:function(value, row , index){
		        		if(value>=0){
			        		return 	"<input type='text'  onkeyup='clearNoNum(this)'   maxlength='4'  class='form-control  worktimeclass' id='workTime"+index+"'  value='"+value+"'/>";
			        	}else{
			        		return 	"<input type='text'  onkeyup='clearNoNum(this)'  maxlength='4' class='form-control  worktimeclass' id='workTime"+index+"'   value=''/>";
			        	}
		         }
		    }
			,{
		        field: 'overTime',
		        title: '加班小时',
		        cellStyle:formatCol1,
		        formatter:function(value, row , index){
				      if(value>=0){
				    	  return 	"<input type='text'  onkeyup='clearNoNum(this)'  maxlength='4' class='form-control    overtimeclass' id='overTime"+index+"'    value='"+value+"'/>";
				       }else{
				    	   return 	"<input type='text'   onkeyup='clearNoNum(this)'  maxlength='4' class='form-control    overtimeclass' id='overTime"+index+"'      value=''/>";
				       }
				  }
		    }
		     ]
		
		});
	
	function formatCol1(value, row, index) {
	    return {
	    	classes: 'edit1'
	    }
	}
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端
		  $('#workDetail').bootstrapTable("toggleView");
		}
	  
	  $('#workDetail').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#projectTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#projectTable').bootstrapTable('getSelections').length!=1);
        });
		  
	
	 
	
		
	});
		
  function getIdSelections() {
        return $.map($("#workDetail").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  

	  function clearNoNum(obj){
		  obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
		  
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