/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../../common');
    var cn = new common();
	var vmId = $("#vmId").val();
	var region = $("#region").val();
	var operState = 'free';
	var vmStatus = '';
     /* 初始化navbar-header-menu */
	cn.initNavbarMenu([{name : "云主机",herf : "/list/vm",isActive:true},
	                   {name : "磁盘",herf : "/list/vm/disk"}]);
			
	$("#right-head").click(function(e){
		e = e ? e : window.event;
		var target = e.target || e.srcElement;
		switch(target.id){
			case "stop-vm-btn":
				if(operState == 'free'&& vmStatus!="SHUTOFF"){
					stopVm();
					break;
				}else if(vmStatus == "SHUTOFF"){
					cn.alertoolWarnning("虚拟机已经停止，不能执行此操作。");
					break;
				}else{
					cn.alertoolWarnning("虚拟机正在"+operState+",请稍后再试.");
					break;
				}
			case "start-vm-btn":
				if(operState == 'free'&& vmStatus!="ACTIVE"){
					startVm();
					break;	
				}else if(vmStatus == "ACTIVE"){
					cn.alertoolWarnning("虚拟机已经运行，不能执行此操作。");
					break;
				}else{
					cn.alertoolWarnning("虚拟机正在"+operState+",请稍后再试.");
					break;
				}
	}
	});	
			
	    
	function stopVm(){
		var title = "确认";
        var text = "您确定要停止虚拟机（"+$("#vmName").html()+"）？";
        cn.DialogBoxInit(title,text,function(){
        	operState = "停止";
        	cn.alertoolSuccess("正在停止虚拟机...");
    		var url="/ecs/region/"+region+"/vm-stop";
			var data={
				vmId:vmId
			};
			cn.PostData(url,data,function(){
				cn.alertoolSuccess("虚拟机已停止");
				operState = "free";
				 getVmInfo();
			});
        });
	};
	function startVm(){
		var title = "确认";
        var text = "您确定要启动虚拟机（"+$("#vmName").html()+"）？";
        cn.DialogBoxInit(title,text,function(){
        	operState = "启动";
        	cn.alertoolSuccess("正在启动虚拟机...");
    		var url="/ecs/region/"+region+"/vm-start";
			var data={
				vmId:vmId
			};
			cn.PostData(url,data,function(){
				cn.alertoolSuccess("虚拟机已启动");
				operState = "free";
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
	    var ajax = cn.GetData(url,basicInfoHandler.resCountHandler);
	    ajax.done(function(){
	    	vmStatus = basicInfoHandler.getVmStatus();
	    });
    };
});
