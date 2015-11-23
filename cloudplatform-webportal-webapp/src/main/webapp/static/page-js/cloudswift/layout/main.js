/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../../common');
    var cn = new common();
    cn.initNavbarMenu([{
			name : "开放存储服务 OSS",
			herf : "/list/oss"
		}]);
    var options = {// 拖动条初始化参数 全局变量
		'stepSize' : 50,// 步长
		'lev1' : 206,// 拖动条第一块长度==css width
		'lev2' : 309,// 拖动条第二块长度==css width
		'lev3' : 412,// 拖动条第三块长度==css width
		'min':1,
		'grade1' : 500,// 三段设置，倍数关系2
		'grade2' : 800,
		'grade3' : 1024,
		'unit' : 'GB'
	};
	// drag bar
	cn.dragBarInite(options);
	cn.barClickDrag(options);
	cn.barDrag(options);
	
	var _up = $('.mem-num-up');
	var _down = $('.mem-num-down');
	_up.click(function(event) {
		var _memSize = $('.memSize');
		var val = _memSize.val();
		var temp = parseInt(val) + options.stepSize;
		if (temp >= options.grade3) {
			_up.addClass('bk-number-disabled');
		} else {
			_up.removeClass('bk-number-disabled');
			if (temp > options.stepSize) {
				_down.removeClass('bk-number-disabled');
			} else {
				_down.addClass('bk-number-disabled');
			}
		}
		_memSize.val(temp);
		cn.inputChge(options);
	});
	_down.click(function(event) {
		var _memSize = $('.memSize');
		var val = _memSize.val();
		var temp = parseInt(val) - options.stepSize;
		if (temp <= options.stepSize) {
			_down.addClass('bk-number-disabled');
		} else {
			_down.removeClass('bk-number-disabled');
			if (temp < options.grade3) {
				_up.removeClass('bk-number-disabled');
			} else {
				_up.addClass('bk-number-disabled');
			}
		}
		_memSize.val(temp);
		cn.inputChge(options);
	});
	$('.memSize').change(function(event) {
		cn.inputChge(options);
	});
	
    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var basicInfoHandler = new dataHandler();
    
    /*初始化侧边栏菜单*/
    var index = [1,0];
    cn.Sidebar(index);//index为菜单中的排序(1-12)
 
   $("#modifyCommit").click(function(){
   		var text=$(this).text();
   		$(this).addClass('disabled').text('配置中...')
   		var data={
   			id:$(swiftId).val(),
   			visibilityLevel:$("[name = visibilityLevel]").parent().parent().find(":checked").val(),
   			storeSize:$(".memSize").val()
   		}
   		cn.PostData("/oss/config",data,function(data){
   			$("#myModal").modal('hide');
   			if(data.result == 1){
				cn.alertoolSuccess("配置已生效.");
	   			getSwiftConfig();
	   			cn.RefreshIfame();
   			}else{
				cn.alertoolWarnning(data.msgs);
	   			getSwiftConfig();
   			}
   		},'#modifyCommit',text);
   })
/*获取swift当前配置信息*/
	var dataHandler = require('./dataHandler');
    var ossHandler = new dataHandler();
    
    getSwiftConfig();
    function getSwiftConfig() {
		var url = "/oss/"+$("#swiftId").val();
		cn.GetData(url, ossHandler.SwiftConfigHandler);
	}
});
