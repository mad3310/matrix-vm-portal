<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<%
  response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
  response.setHeader("Pragma", "no-cache"); //HTTP 1.0
  response.setDateHeader("Expires", 0); //防止代理服务器缓
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Highcharts Example</title>
<script src="${ctx}/static/ace/js/jquery.min.js"></script>
<script type="text/javascript">
function drawChart(obj,title,ip,ytitle,unit,xdata,ydata){
    $(obj).highcharts({
        title: {
            text: title
      
        },
        subtitle: {
            text: ip
         
        },
        xAxis: {
            categories: xdata 
        },
        yAxis: {
            title: {
                text: ytitle 
            }
        },
        tooltip: {
            valueSuffix: unit
        },
        series: ydata
    });

} 
var xdata =['2014-11-10 17:33:00', '2014-11-10 17:33:00', '2014-11-10 17:34:00', '2014-11-10 17:35:00', '2014-11-10 17:36:00', '2014-11-10 17:37:00','2014-11-10 17:38:00', '2014-11-10 17:39:00', '2014-11-10 17:40:00', '2014-11-10 17:41:00', '2014-11-10 17:42:00', '2014-11-10 17:43:00'];
var ydata = [{type: 'spline',name: '德国机房',data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]},{type: 'line',name: '美国机房',data: [9.0, 6.9, 9.5, 14.5, 24.2, 21.5, 25.2, 26.5, 12.3, 10.3, 1.9, 19.6]}];
var title = "测试";
var unit = "M/S";
var ytitle = "网速(M/S)";
$(function(){
	drawChart($('#container'),title,"192.168.30.49",ytitle,unit,xdata,ydata);
	drawChart($('#container1'),title,"192.168.30.49",ytitle,unit,xdata,ydata);
});
</script>
</head>
<body>
	<script src="${ctx}/static/scripts/highcharts/highcharts.js"></script>
	<script src="${ctx}/static/scripts/highcharts/themes/grid.js"></script>
	<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	<div id="container1" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
