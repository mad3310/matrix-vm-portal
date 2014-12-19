/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
	var $ = require('jquery');
	require('highcharts')($);
    var common = require('../common');
    var cn = new common();
    
    /*初始化侧边栏菜单*/
    var index = [2,0];
    cn.Sidebar(index);//index为菜单中的排序(1-12)
   
    /*初始化监控数据*/
    /*加载数据*/
	var cType = "area";
	var cTitle = "Mcluster Chart Demo";
	var cSubtitle = "cpu usage";
	var cxAxis = ['1750', '1800', '1850', '1900', '1950', '1999', '2050'];
	var cyAxis = '';
	var seriesData = [{
		type: cType,
		name: "cpu",
		pointInterval: 24 * 3600 * 1000,
		pointStart: Date.UTC(2006, 0, 01),
		data: [0.9999, 0.8445, 0.8444, 0.8451,    0.8418, 0.8264,    0.8258, 0.8232,    0.8233, 0.8258, 0.8283, 0.8278, 0.8256, 0.8292,    0.8239, 0.8239,    0.8245, 0.8265,    0.8261, 0.8269, 0.8273, 0.8244, 0.8244, 0.8172,    0.8139, 0.8146, 0.8164, 0.82,    0.8269, 0.8269 ]
		},
		{
			type: cType,
			name:"memeory",
			pointInterval: 24 * 3600 * 1000,
			pointStart: Date.UTC(2006, 0, 01),
			data:[0.71,0.55,0.06,0.77,0.68,0.6,0.27,0.07,0.89,0.75,0.98,0.87,0.74,0.96,0.64,0.55,0.66,0.94,0.2,0.34,0.28,0.63,0.38,0.46,0.31,0.08,0.88,0.3,0.95,0.68]

		},
		{
			type: cType,
			name:"memeory",
			pointInterval: 24 * 3600 * 1000,
			pointStart: Date.UTC(2006, 0, 01),
			data:[0.27,0.37,0.04,0.23,0.38,0.09,0.22,0.43,0.26,0.42,0.09,0.19,0.17,0.13,0.07,0.49,0.02,0.48,0.19,0.26,0.42,0.09,0.19,0.17,0.13,0.07,0.49,0.02,0.48,0.19]

		}];

	cn.Charts(cType,cTitle,cSubtitle,cxAxis,cyAxis,seriesData);

    var dataHandler = require('./dataHandler');
    //var basicInfoHandler = new dataHandler();
   // cn.GetData("/static/page-js/basicInfo/analogData/dblist.json",basicInfoHandler.BasicInfoHandler);
});
