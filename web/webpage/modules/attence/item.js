<%@ page contentType="text/html;charset=UTF-8" %>
<script>
/**
 * 2018/03/03 查询考勤的js
 */
$(function(){
	/*
	 * jp.get("${ctx}/attence/item/allItem", function(data){ var str = ""; str =
	 * str + "<div >"; //class='accordion-group' str = str + "<div
	 * id='collapseThree' >"; //class='accordion-body collapse' str = str + "<div
	 * >"; //class='accordion-inner' str = str + "<div
	 * style='margin-left:-5px;width:104%;'>"; //class='container' str = str + "<div
	 * class='row'>"; for(var i=0; i<data.length; i++){ str = str + "<div
	 * class='col-lg-2 col-md-2 col-sm-3 col-xs-6'>"; str = str + "<div
	 * class='checkbox-custom checkbox-default'>"; str = str + "<input
	 * type='checkbox' onclick='rememberMe(this)' class='kqlb"+data[i].sort+"'
	 * id='rememberMe"+data[i].sort+"' >"; str = str + "<label
	 * for='rememberMe"+i+"' class='item"+ data[i].sort +"'>"+ data[i].name +"</label>";
	 * str = str + "</div>"; str = str + "</div>"; } str = str + "</div>";
	 * str = str + "</div>"; str = str + "</div>"; str = str + "</div>"; str =
	 * str + "</div>"; $("#items").html(str); });
	 */

		var checkStr=$("#checkStr").val();
		var  checkArr =new Array();
		checkArr=checkStr.split(",");
		if(checkArr.length>0){
			for(var i=0;i<checkArr.length;i++){
				if(checkArr[i]>0){
					$("#rememberMe"+checkArr[i]).prop("checked",true);
				}
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
		
	});
	
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

	// 监听所有的复选框
	function rememberMe(obj){
		// 考察
		if($(obj).hasClass("kqlb1")){
			if($(obj).is(":checked")){
				$('#inspect').css('display', 'block');
				$('#inspect').removeClass("hiddenClass");
				$('#inspect').addClass("showClass");
			}else{
				$("#inspect").css('display','none');
				$('#inspect').removeClass("showClass");
				$('#inspect').addClass("hiddenClass");
			}
		// 培训
		}else if($(obj).hasClass("kqlb5")){
			
			if($(obj).is(":checked")){
				$('#train').css('display', 'block');
				$('#train').removeClass("hiddenClass");
				$('#train').addClass("showClass");
				
			}else{
				$("#train").css('display','none');	
				$('#train').removeClass("showClass");
				$('#train').addClass("hiddenClass");
			}
		// 内业
		}else if($(obj).hasClass("kqlb10")){
			
			if($(obj).is(":checked")){
				$('#ny').css('display', 'block');
				$('#ny').removeClass("hiddenClass");
				$('#ny').addClass("showClass");
			}else{
				$("#ny").css('display','none');
				$('#ny').removeClass("showClass");
				$('#ny').addClass("hiddenClass");
			}
		// 外业
		}else if($(obj).hasClass("kqlb15")){
			if($(obj).is(":checked")){
				$('#wy').css('display', 'block');
				$('#wy').removeClass("hiddenClass");
				$('#wy').addClass("showClass");
			}else{
				$("#wy").css('display','none');
				$('#wy').removeClass("showClass");
				$('#wy').addClass("hiddenClass");
			}
		// 党工团
		/*}else if($(obj).hasClass("kqlb20")){
			if($(obj).is(":checked")){
				$('#dgt').css('display', 'block');
				$('#dgt').removeClass("hiddenClass");
				$('#dgt').addClass("showClass");
			}else{
				$("#dgt").css('display','none');
				$('#dgt').removeClass("showClass");
				$('#dgt').addClass("hiddenClass");
			}*/
		// 零星事物
		}else if($(obj).hasClass("kqlb25")){
			if($(obj).is(":checked")){
				$('#siteWork').css('display', 'block');
				$('#siteWork').removeClass("hiddenClass");
				$('#siteWork').addClass("showClass");
			}else{
				$("#siteWork").css('display','none');
				$('#siteWork').removeClass("showClass");
				$('#siteWork').addClass("hiddenClass");
			}
		// 事假
		}else if($(obj).hasClass("kqlb30")){
			if($(obj).is(":checked")){
				$('#casualLeave').css('display', 'block');
				$('#casualLeave').removeClass("hiddenClass");
				$('#casualLeave').addClass("showClass");
			}else{
				$("#casualLeave").css('display','none');
				$('#casualLeave').removeClass("showClass");
				$('#casualLeave').addClass("hiddenClass");
			}
		// 年假
		}else if($(obj).hasClass("kqlb35")){
			if($(obj).is(":checked")){
				$('#yearLeave').css('display', 'block');
				$('#yearLeave').removeClass("hiddenClass");
				$('#yearLeave').addClass("showClass");
				
			}else{
				$("#yearLeave").css('display','none');
				$('#yearLeave').removeClass("showClass");
				$('#yearLeave').addClass("hiddenClass");
			}
		// 病假
		}else if($(obj).hasClass("kqlb40")){
			if($(obj).is(":checked")){
				$('#sickLeave').css('display', 'block');
				$('#sickLeave').removeClass("hiddenClass");
				$('#sickLeave').addClass("showClass");
				
			}else{
				$("#sickLeave").css('display','none');
				$('#sickLeave').removeClass("showClass");
				$('#sickLeave').addClass("hiddenClass");
			}
		}else if($(obj).hasClass("kqlb45")){
			if($(obj).is(":checked")){
				$('#cw').css('display', 'block');
				$('#cw').removeClass("hiddenClass");
				$('#cw').addClass("showClass");
				
			}else{
				$("#cw").css('display','none');
				$('#cw').removeClass("showClass");
				$('#cw').addClass("hiddenClass");
			}
		}else if($(obj).hasClass("kqlb50")){
			if($(obj).is(":checked")){
				$('#sw').css('display', 'block');
				$('#sw').removeClass("hiddenClass");
				$('#sw').addClass("showClass");
				
			}else{
				$("#sw").css('display','none');
				$('#sw').removeClass("showClass");
				$('#sw').addClass("hiddenClass");
			}
		}else if($(obj).hasClass("kqlb55")){
			if($(obj).is(":checked")){
				$('#jy').css('display', 'block');
				$('#jy').removeClass("hiddenClass");
				$('#jy').addClass("showClass");
				
			}else{
				$("#jy").css('display','none');
				$('#jy').removeClass("showClass");
				$('#jy').addClass("hiddenClass");
			}
		}else if($(obj).hasClass("kqlb60")){
			if($(obj).is(":checked")){
				$('#rcgl').css('display', 'block');
				$('#rcgl').removeClass("hiddenClass");
				$('#rcgl').addClass("showClass");
			}else{
				$("#rcgl").css('display','none');
				$('#rcgl').removeClass("showClass");
				$('#rcgl').addClass("hiddenClass");
			}
		}else if($(obj).hasClass("kqlb65")){
			if($(obj).is(":checked")){
				$('#cc').css('display', 'block');
				$('#cc').removeClass("hiddenClass");
				$('#cc').addClass("showClass");
			}else{
				$("#cc").css('display','none');
				$('#cc').removeClass("showClass");
				$('#cc').addClass("hiddenClass");
			}
		}else if($(obj).hasClass("kqlb70")){
			if($(obj).is(":checked")){
				$('#xx').css('display', 'block');
				$('#xx').removeClass("hiddenClass");
				$('#xx').addClass("showClass");
			}else{
				$("#xx").css('display','none');
				$('#xx').removeClass("showClass");
				$('#xx').addClass("hiddenClass");
			}
		}
		
	}
</script>