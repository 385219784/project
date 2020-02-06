<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="ani"/>
	<style>

		#body-container {
			margin-left: 0px !important;
			/**padding: 10px;*/
			margin-top: 0px !important;
			overflow-x: hidden!important;
			transition: all 0.2s ease-in-out !important;
			height: 100% !important;
		}
	</style>
</head>
<body class="">
<div id="body-container" class="wrapper wrapper-content">
	<div class="conter-wrapper home-container">
		
	</div>
</div>
<script src="vendor/ckeditor/ckeditor.js" type="text/javascript"></script>
<script src="js/vendor.js"></script>

<script>
    $(function(){
        $('#calendar2').fullCalendar({
            eventClick: function(calEvent, jsEvent, view) {
                alert('Event: ' + calEvent.title);
                alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
                alert('View: ' + view.name);
            }
        });

        $('#rtlswitch').click(function() {
            console.log('hello');
            $('body').toggleClass('rtl');

            var hasClass = $('body').hasClass('rtl');

            $.get('/api/set-rtl?rtl='+ (hasClass ? 'rtl': ''));

        });
        $('.theme-picker').click(function() {
            changeTheme($(this).attr('data-theme'));
        });
        $('#showMenu').click(function() {
            $('body').toggleClass('push-right');
        });

    });
    function changeTheme(the) {
        $("#current-theme").remove();
        $('<link>')
            .appendTo('head')
            .attr('id','current-theme')
            .attr({type : 'text/css', rel : 'stylesheet'})
            .attr('href', '/css/app-'+the+'.css');
    }
</script>

<script>
    $(function(){
        setTimeout(function() {
            var chart = c3.generate({
                bindto: '#lineChart',
                data: {
                    columns: [
                        ['搜索引擎', 30, 200, 100, 400, 150, 250],
                        ['自主访问', 50, 120, 210, 140, 115, 425],
                        ['友情链接', 40, 150, 98, 300, 175, 100]
                    ]
                },
                color: {
                    pattern: ['#3CA2E0','#5CB85C','#F1B35B']
                },
                axis: {
                    x: {
                        show: false
                    },
                    y: {
                        show: false
                    },
                }
            });
        }, 275);
        setTimeout(function() {
            $('#world-map').vectorMap({
                backgroundColor: '#FFF',
                regionStyle: {
                    initial: {
                        fill: 'black',
                        "fill-opacity": 1,
                        stroke: 'none',
                        "stroke-width": 0,
                        "stroke-opacity": 1
                    },
                    hover: {
                        "fill-opacity": 0.8,
                        cursor: 'pointer',
                    },
                    selected: {
                        fill: 'red'
                    },
                    selectedHover: {
                    }
                }
            });
        }, 275);
        setTimeout(function() {
            var chart2 = c3.generate({
                bindto: '#cbar',
                data: {
                    columns: [
                        [10,40,20,90,35,70,10,50,20,80,60,10,20,40,70]
                    ],
                    type:'bar'
                },
                bar: {
                    width: {
                        ratio: 0.5 // this makes bar width 50% of length between ticks
                    }
                },
                color: {
                    pattern: ['#DB5B57']
                },
                labels: true,
                legend: {
                    show: 0
                },
                axis: {
                    x: {
                        show: false
                    },
                    y: {
                        show: false
                    },
                }
            });

        }, 275);
        setTimeout(function() {
            var chart = c3.generate({
                bindto: '#pie',
                data: {
                    // iris data from R
                    columns: [
                        ['data1', 11],
                        ['data2', 23],
                        ['data3', 66]
                    ],
                    type : 'pie',
                },
                color: {
                    pattern: ['#5CB85C','#F0AD4E','#3CA2E0']
                },
                legend: {
                    show: 0
                },
            });

        }, 275);
    });
</script>

</body>
</html>