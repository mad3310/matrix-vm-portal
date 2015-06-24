/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../../common');
    var cn = new common();
	var vmId = $("#vmId").val();
	var region = $("#region").val();
     /* 初始化navbar-header-menu */
	cn.initNavbarMenu([{
				name : "云主机",
				herf : "/list/vm"
			}]);
			
	$("#right-head").click(function(e){
		e = e ? e : window.event;
		var target = e.target || e.srcElement;
		switch(target.id){
			case "stop-vm-btn":
				stopVm();
				break;
			case "start-vm-btn":
				startVm();
				break;	
		}
	});	
			
	    
	function stopVm(){
		var title = "确认";
        var text = "您确定要停止虚拟机（"+$("#vmName").html()+"）？";
        cn.DialogBoxInit(title,text,function(){
    		var url="/ecs/region/"+region+"/vm-stop";
			var data={
				vmId:vmId
			};
			cn.PostData(url,data,function(){
				cn.alertoolSuccess("虚拟机已停止");
				 getVmInfo();
			});
        });
	};
	function startVm(){
		var title = "确认";
        var text = "您确定要启动虚拟机（"+$("#vmName").html()+"）？";
        cn.DialogBoxInit(title,text,function(){
    		var url="/ecs/region/"+region+"/vm-start";
			var data={
				vmId:vmId
			};
			cn.PostData(url,data,function(){
				cn.alertoolSuccess("虚拟机已启动");
				 getVmInfo();
			});
        });
		
	};
    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var basicInfoHandler = new dataHandler();
    
    /*初始化侧边栏菜单*/
    var index = [1,0];
    cn.Sidebar(index);//index为菜单中的排序(1-12)
    /*
     * 加载vm基础信息
     */
    getVmInfo();
    function getVmInfo(){
	    var url = "/ecs/region/"+region+"/vm/"+vmId;
	    cn.GetData(url,basicInfoHandler.resCountHandler);
    }
});
