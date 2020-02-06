<%@ page contentType="text/html;charset=UTF-8" %>
<script>


var calendar;

$(document).ready(function () {

	//页面加载完初始化日历 
	calendar=$('#calendar').fullCalendar({
		//设置日历头部信息
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month'
		},
		events: function (start, end, timezone, callback) {
			var startStr=format(start,'yyyy-MM-dd');
			var endStr=format(end,'yyyy-MM-dd');
			
		    $.ajax({
		        url: '${ctx}/attence/workAttence/data',
		        dataType: 'json',
		        data:{'startStr':startStr,'endStr':endStr},
		        success: function(data) {
		            callback(data)
		        }
		    })
		},
		firstDay:1,//每行第一天为周一 
		editable: false,
		droppable: false, // this allows things to be dropped onto the calendar
		/*drop: function(date, event, ui, resourceId ) {
			var start, end;
			if(resourceId.intervalUnit == "week" || resourceId.intervalUnit == "day"){
				start=moment(date).format("YYYY-MM-DD HH:mm:ss")
				end = moment(date).add(2, 'hours').format("YYYY-MM-DD HH:mm:ss");
			}else{
				start=moment(date).format("YYYY-MM-DD")
				end = moment(date).add(1, 'day').format("YYYY-MM-DD");
			}
			
		    $.get('${ctx}/iim/myCalendar/add?start='+start+'&end='+end+"&title="+$.trim($(this).text())+"&color="+$(this).css("background-color"), (result)=>{
		    	
		    	if(result.success){
		    		$('#calendar').fullCalendar('refetchEvents');
		    		jp.info(result.msg);
		    	}

			});

			// Wed Jun 18 2014 
			//alert(event.title + " was dropped on " + event.start.format());
			// is the "remove after drop" checkbox checked?
			if ($('#drop-remove').is(':checked')) {
				// if so, remove the element from the "Draggable Events" list
				$(this).remove();
			}
		},*/
        locale: 'zh-cn',
       // timeFormat: 'H(:mm)' ,// uppercase H for 24-hour clock
		//点击某一天时促发
		dayClick: function(date, jsEvent, view) {
			//不允许填写以后的日期
			
			var dateDay= format(date,'yyyyMMdd');
			   if(dateDay>format(new Date(),'yyyyMMdd')){
				   jp.alert("还没到填写日期哦!");
				   return  ;
			   }
			var dateStr= format(date,'yyyy-MM-dd');
			var now = new Date($.ajax({async: false}).getResponseHeader("Date"));
			var pageTime=$("#dateStr").val();
			var nowStr= format(now,'yyyy-MM-dd');
			// 
			//超过3天进入
			if(Math.abs(datedifference(nowStr,dateStr))>=1000){
				 jp.openDialogView(dateStr, "${ctx}/attence/workAttence/form?dateStr="+dateStr,'900px', '600px', $('#workAttence'));	
					
			}else{
				jp.openDialog(dateStr, "${ctx}/attence/workAttence/form?dateStr="+dateStr,'900px', '600px', $('#workAttence')); 
			}
		
    	},
    	//事件鼠标离开
    	 eventMouseout: function () {
             layer.closeAll('tips'); //关闭所有的tips层 
         },
		//单击事件项时触发 
        eventClick: function(calEvent, jsEvent, view) { 
        	var dateStr= format(calEvent.start,'yyyy-MM-dd');
        	
      
			var now = new Date($.ajax({async: false}).getResponseHeader("Date"));
			var nowStr= format(now,'yyyy-MM-dd');
			 // 
			//超过10天进入
			if(Math.abs(datedifference(nowStr,dateStr))>=1000){
				 jp.openDialogView(dateStr, "${ctx}/attence/workAttence/form?dateStr="+dateStr,'900px', '600px', $('#workAttence'));	
					
			}else{
				jp.openDialog(dateStr, "${ctx}/attence/workAttence/form?dateStr="+dateStr,'900px', '600px', $('#workAttence'));
			}
        },
		
	/*	//拖动事件 
		eventDrop: function(event, delta, revertFunc) {
        	$.post("${ctx}/iim/myCalendar/drag",{id:event.id,daydiff:delta._days, minudiff:delta._milliseconds},function(result){ 
            	if(result.success){
            		jp.info(result.msg);
            	}
            	
        	}); 
    	},*/
		
		//日程事件的缩放
	/*	eventResize: function(event, delta, revertFunc) {
    		$.post("${ctx}/iim/myCalendar/resize",{id:event.id,daydiff:delta._days, minudiff:delta._milliseconds},function(result){ 
    			if(result.success){
            		jp.info(result.msg); 
        		}else{
        			jp.error(result.msg); 
        		}
    		}); 
		},*/
		//处理events的显示格式
		eventRender: function (event, element) {
		    element.html(event.title);
		},
		selectable: false, //不允许用户拖动 
	/*	select: function( startDate, endDate, allDay, jsEvent, view ){ 
	    	var start =moment(startDate).format("YYYY-MM-DD HH:mm:ss"); 
	    	var end =moment(endDate).format("YYYY-MM-DD HH:mm:ss"); 
	        jp.open({
		   	    type: 2,  
		   	    area: ['800px', '500px'],
		   	    title: '事件',
		   	    auto:true,
		   	    maxmin: true, //开启最大化最小化按钮
		   	    content: '${ctx}/iim/myCalendar/form?start='+start+'&end='+end ,
		   	    btn: ['确定', '关闭'],
		   	    yes: function(index, layero){
		   	    	var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		         	 	iframeWin.contentWindow.save(index, $('#calendar'));
		   		  },
		   	   cancel: function(index){ 
		   	      }
		   		}); 
		}*/ 
	});
	
	
	// 同步刷新表格
	$('#calendar').find('.fc-prev-button').click(function(){
		var m=$('#calendar').fullCalendar('getDate')._d;
		$("#dateStr").val(format(m,'yyyy-MM-dd'));
		$('#workAttence').bootstrapTable('refresh');		
	});
	     
	// 同步刷新表格
		$('#calendar').find('.fc-next-button').click(function(){
			var m=$('#calendar').fullCalendar('getDate')._d;
			$("#dateStr").val(format(m,'yyyy-MM-dd'));
			$('#workAttence').bootstrapTable('refresh');		
		});
	     
	     $('#calendar').find('.fc-today-button').click(function(){
				var m=$('#calendar').fullCalendar('getDate')._d;
				$("#dateStr").val(format(m,'yyyy-MM-dd'));
				$('#workAttence').bootstrapTable('refresh');		
			});

	
	$('#external-events .fc-event').each(function() {
		// store data so the calendar knows to render an event upon drop
		$(this).data('event', {
			title: $.trim($(this).text()), // use the element's text as the event title
			color:$(this).css("background-color")
			//stick: true // maintain when user navigates (see docs on the renderEvent method)
		});
		// make the event draggable using jQuery UI
		$(this).draggable({
			zIndex: 999,
			revert: true,      // will cause the event to go back to its
			revertDuration: 0  //  original position after the drag
		});

	});
	
	
	
});
function refresh(){
	  calendar.fullCalendar('refetchEvents');
	  calendar.fullCalendar('unselect');
	  $('#workAttence').bootstrapTable('refresh');	
}
function add(){
	var html = $("<div class='fc-event  bg-default'>"+$("#title").val()+"</div>");
	$(html).insertBefore($("#p"));
	$(html).draggable({
		zIndex: 999,
		revert: true,      // will cause the event to go back to its
		revertDuration: 0  //  original position after the drag
	});

}


	


//格式化日期
var format = function (time, format) {
    var t = new Date(time);
    var tf = function (i) { return (i < 10 ? '0' : '') + i };
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
        switch (a) {
            case 'yyyy':
                return tf(t.getFullYear());
                break;
            case 'MM':
                return tf(t.getMonth() + 1);
                break;
            case 'mm':
                return tf(t.getMinutes());
                break;
            case 'dd':
                return tf(t.getDate());
                break;
            case 'HH':
                return tf(t.getHours());
                break;
            case 'ss':
                return tf(t.getSeconds());
                break;
        }
    })
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