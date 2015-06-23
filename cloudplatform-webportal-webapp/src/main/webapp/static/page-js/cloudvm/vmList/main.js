/**
 * Created by yaokuo on 2014/12/12.
 * dblist page
 */
define(function(require){
	var pFresh,iFresh;
    var common = require('../../common');
    var cn = new common();
    
    cn.Tooltip();
     /* 初始化navbar-header-menu */
	cn.initNavbarMenu([{
				name : "云主机",
				herf : "/list/vm"
			}]);
    
	/*禁用退格键退回网页*/
	window.onload=cn.DisableBackspaceEnter();

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var vmListHandler = new dataHandler();
    /*
     * 初始化数据
     */
    initComponents();
	
	$("#refresh").click(function() {		
		asyncData();
	});	
	
	/*
	 * 可封装公共方法 begin
	 */
	//初始化checkbox
	$(document).on('click', 'th input:checkbox' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
	/*
	 * 可封装公共方法 end
	 */
	
	//加载列表数据
	function asyncData(page) {
		var currentRegion=vmListHandler.getSelectedRegion();
		var baseUrl = '/ecs/region';	
		var url = currentRegion!='All' ? baseUrl+'/'+currentRegion:baseUrl;
		cn.GetData(url,refreshCtl);		
	}
	function refreshCtl(data) {
		vmListHandler.VmListHandler(data);
        $('.vm-remove').each(function(index,element){
        	$(element).on('click',function(e){
        		var vmId= $(e.currentTarget.closest('tr')).find('input:checkbox[name=vm_id]').val();
                var title = "确认";
                var text = "您确定要删除该虚拟机吗？";
                cn.DialogBoxInit(title,text,function(){
            		var currentRegion=vmListHandler.getSelectedRegion();
            		var removeVmUrl = '/ecs/region/'+currentRegion+'/vm-delete';
            		cn.PostData(removeVmUrl,{
            	        vmId: vmId
            	    },function(data){
            	    	var refresh =setInterval(function(){
            	    		if($('#tby input:checkbox[value='+vmId+']').length){
                	    		asyncData();
            	    		}else{
            	    			clearInterval(refresh);
            	    		}
            	    	},200);
            		});
                    	
                });
        	});
        });
		if ($(".vm-building").length == 0){
			if(pFresh){
				clearInterval(pFresh);
			}
			if(iFresh){
				clearInterval(iFresh);
			}
			iFresh = setInterval(asyncData,cn.dbListRefreshTime);
		}else{
			asyncProgressData();
			if(iFresh){
				clearInterval(iFresh);
			}
			if(pFresh){
				clearInterval(pFresh);
			}
			pFresh = setInterval(asyncProgressData,10000);
		}
	}	
	 /*进度条数据刷新*/
	function asyncProgressData(){
		$("input[name = progress_vm_id]").each(function(){
			var vmId = $(this).val();
			var currentRegion=vmListHandler.getSelectedRegion();
			function progress_func(data){
				vmListHandler.progress(vmId,data,asyncData);
			}
			var url = '/ecs/region/'+currentRegion+'/vm/' + vmId;
			cn.GetLocalData(url,progress_func);
		});
	}
    function initComponents(){
    	initRegionSelector().then(function(data){
    		$('#region_selector').on('change',function(e){
    			asyncData();
    		});
    		asyncData();
    	});
    }
    function initRegionSelector(){
    	var url = '/ecs/regions';
		return cn.GetData(url,vmListHandler.initRegionSelectorHandler);
    }

});
