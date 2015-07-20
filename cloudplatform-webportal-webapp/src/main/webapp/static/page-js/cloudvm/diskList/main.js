/**
 * Created by yaokuo on 2014/12/12.
 * dblist page
 */
define(function(require){
    var common = require('../../common');
    var dataHandler = require('./dataHandler');
    
    
    var cn = new common(),
    	diskListHandler = new dataHandler(),
    	pFresh,
    	iFresh,
    	currentPage=1,
    	recordsPerPage=3;
    
    var asyncData=function () {
		var currentRegion=diskListHandler.getSelectedRegion();
		var baseUrl = '/osv/region';	
		var queryParams="?currentPage=" + currentPage +"&&recordsPerPage=" + recordsPerPage;
		var url = currentRegion!='All' ? baseUrl+'/'+currentRegion+queryParams:baseUrl+queryParams;
		cn.GetData(url,refreshCtl);
	},
	initRegionSelector=function (){
    	var url='/osv/regions/group';
		return cn.GetData(url,diskListHandler.initRegionSelectorHandler);
    },
    initComponents=function (){
    	initRegionSelector();
    },
    refreshCtl=function(data) {
		diskListHandler.DiskListHandler(data);
        $('.disk-operation').each(function(index,element){
        	$(element).on('click',function(e){
        		var diskId= $(e.currentTarget.closest('tr')).find('input:checkbox[name=disk_id]').val();
        		var fieldRegion= $(e.currentTarget.closest('tr')).find('.field-region').val();
        		var operationType=$(e.currentTarget).attr('class').split(' ')[1];
        		diskListHandler.operateDisk(diskId,fieldRegion,operationType,asyncData);
        	});
        });

        pollingStatusChange(data);
        diskListHandler.resetSelectAllCheckbox();
	},
    setListAutoFresh=function(){
		iFresh = setInterval(asyncData,cn.dbListRefreshTime);
	},
	pollingStatusChange=function(data){
		if(data.result!==1 || data.data.data.length<=0) return;
		var intervalRefList={};
		var isAllIntervalStoped=function(){
			var result=true;
			for(var p in intervalRefList){
				if(intervalRefList[p].intervalStoped==false){
					result=false;
					break;
				}
			}
			return result;
		},
		setDiskStatus=function(diskId,displayStatus){
			var rowEl=$('input:checkbox[value='+diskId+']').closest('tr');
			rowEl.find('.disk-display-status').text(displayStatus);
		};
		for(var i=0,leng=data.data.data.length;i<leng;i++){
			var disk=data.data.data[i];
			if(diskListHandler.goingStatusList.indexOf(disk.status)>-1){
				clearInterval(iFresh);
				disk.intervalStoped=false;
				disk.intervalObject=setInterval((function(currentDisk){
					return function(){
						var url = '/osv/region/'+currentDisk.region+'/volume/' + currentDisk.id;
						cn.GetLocalData(url,function(data){
							if(data.result==0 || diskListHandler.goingStatusList.indexOf(data.data.status)==-1){
								if(data.result==0){//表示已删除
									setDiskStatus(currentDisk.id,'已删除');
								}
								else{
									setDiskStatus(data.data.id,cn.TranslateStatus(data.data.status));
								}
								currentDisk.intervalStoped=true;
								clearInterval(currentDisk.intervalObject);
								if(isAllIntervalStoped()){
									setListAutoFresh();
									asyncData();
								}
							}
						});
					};
				})(disk),10000);
				intervalRefList[disk.id]=disk;
			}
		}
	};    
	
	/*禁用退格键退回网页*/
	window.onload=cn.DisableBackspaceEnter();	
	
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
    /*按钮组件封装 --begin*/
	$(document).on('click', '.region-city-list button' , function(e){
		e.preventDefault();
        if(!$(this).hasClass("btn-success")){
            $(this).parent().find("button").removeClass("btn-success");
            $(this).addClass("btn-success");
            if($(this).parent().find("input:hidden").length > 0 ){
                var val = $(this).val();
                var hiddenInput=$(this).parent().find("input:hidden");
                if(val!==hiddenInput.val()){
                    hiddenInput.val(val);
                    hiddenInput.trigger('change');
                }	
            }
        }
	});
	/*按钮组件封装 --end*/
	
	$("#refresh").click(function() {		
		asyncData();
	});	
	
    $(".region-city-list input").change(function (){
    	var flavorRam= $(this).val();
    	asyncData();
    });
    
    
    cn.Tooltip();
    /* 初始化navbar-header-menu */
	cn.initNavbarMenu([{name : "云主机",herf : "/list/vm"},
	                   {name : "磁盘",herf : "/list/vm/disk",isActive:true}]);
	//初始化分页组件
	$('#paginator').bootstrapPaginator({
		size:"small",
    	alignment:'right',
		bootstrapMajorVersion:3,
		numberOfPages: 3,
		onPageClicked: function(e,originalEvent,type,page){
			currentPage = page;
        	asyncData();
        }
	});
    
    initComponents();
	
    setListAutoFresh();

});
