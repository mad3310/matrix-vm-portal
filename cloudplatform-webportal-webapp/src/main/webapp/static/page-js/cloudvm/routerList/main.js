/**
 * Created by yaokuo on 2014/12/12.
 * dblist page
 */
define(function(require){
    var common = require('../../common');
    var dataHandler = require('./dataHandler');
    
    
    var cn = new common(),
    	routerListHandler = new dataHandler(),
    	searchName='',
    	pFresh,
    	iFresh,
    	currentPage=1,
    	recordsPerPage=3;
    
    var asyncData=function () {
		var currentRegion= routerListHandler.getSelectedRegion();
		var baseUrl= '/osn/router/list';
		var queryParams='?region='+currentRegion+'&currentPage=' + currentPage +'&recordsPerPage=' + recordsPerPage+'&name='+searchName;
		var url = baseUrl+queryParams;
		cn.GetData(url,refreshCtl);
	},
	initRegionSelector=function (){
    	var url='/osn/regions/group';
		return cn.GetData(url,routerListHandler.initRegionSelectorHandler);
    },
    initComponents=function (){
    	initRegionSelector();
    },
    refreshCtl=function(data) {
		routerListHandler.routerListHandler(data);
        $('.router-operation').each(function(index,element){
        	$(element).on('click',function(e){
        		var routerId= $(e.currentTarget.closest('tr')).find('input:checkbox[name=router_id]').val();
        		var fieldRegion= $(e.currentTarget.closest('tr')).find('.field-region').val();
        		var operationType=$(e.currentTarget).attr('class').split(' ')[1];
        		routerListHandler.operateRouter(routerId,fieldRegion,operationType,asyncData);
        	});
        });

        pollingStatusChange(data);
        routerListHandler.resetSelectAllCheckbox();
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
		setRouterStatus=function(routerId,displayStatus){
			var rowEl=$('input:checkbox[value='+routerId+']').closest('tr');
			rowEl.find('.router-display-status').text(displayStatus);
		};
		for(var i=0,leng=data.data.data.length;i<leng;i++){
			var router=data.data.data[i];
			if(routerListHandler.goingStatusList.indexOf(router.status)>-1){
				clearInterval(iFresh);
				router.intervalStoped=false;
				router.intervalObject=setInterval((function(currentRouter){
					return function(){
						var url = '/osv/region/'+currentRouter.region+'/volume/' + currentRouter.id;
						cn.GetData(url,function(data){
							if((data.result==1 && data.data===null) || routerListHandler.goingStatusList.indexOf(data.data.status)==-1){
								if(data.result==1 && data.data===null){//表示已删除
									setRouterStatus(currentRouter.id,'已删除');
								}
								else{
									setRouterStatus(data.data.id,cn.TranslateStatus(data.data.status));
								}
								currentRouter.intervalStoped=true;
								clearInterval(currentRouter.intervalObject);
								if(isAllIntervalStoped()){
									setListAutoFresh();
									asyncData();
								}
							}
						});
					};
				})(router),10000);
				intervalRefList[router.id]=router;
			}
		}
	},
	setSearchName=function(){
    	searchName=$('#routerName').val();
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
    
    $('#search').on('click',function(e){
    	setSearchName();
    	asyncData();
    });
	$("#routerName").keydown(function(e){
		if(e.keyCode==13){
			currentPage = 1;
			setSearchName();
			asyncData();
		}
	});
    
    
    cn.Tooltip();
    /* 初始化navbar-header-menu */
	cn.initNavbarMenu([{name : "云主机",herf : "/list/vm"},
	                   {name : "磁盘",herf : "/list/vm/disk"},
	                   {name : "网络",herf : "/list/vm/network"},
	                   {name : "路由",herf : "/list/vm/router",isActive:true}
	                   ]);	
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
