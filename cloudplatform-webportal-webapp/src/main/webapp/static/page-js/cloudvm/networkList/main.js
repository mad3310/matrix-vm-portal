/**
 * Created by yaokuo on 2014/12/12.
 * dblist page
 */
define(function(require){
    var common = require('../../common');
    var dataHandler = require('./dataHandler');
    require("bootstrapValidator")($);
    
    
    var cn = new common(),
    	networkListHandler = new dataHandler(),
    	pFresh,
    	iFresh,
    	searchName='',
    	currentPage=1,
    	recordsPerPage=3,
    	activeTabName='vpc',
    	vpcModalEl=$('#vpc_modal'),
    	subnetModalEl=$('#subnet_modal'),
        vpcFormEl=$("#vpc_form"),
        subnetFormEl=$("#subnet_form"),
    	newVpcNameEl=$('#vpc_name'),
    	vpcRegionSelector=$('#vpc_region_selector');
    
    var asyncData=function () {
		var currentRegion= networkListHandler.getSelectedRegion();
		var baseUrl= activeTabName=='vpc'? '/osn/network/private/list':'/osn/subnet/private/list';	
		var queryParams='?region='+currentRegion+'&currentPage=' + currentPage +'&recordsPerPage=' + recordsPerPage+'&name='+searchName;
		var url = baseUrl+queryParams;
		var refreshCtl=activeTabName=='vpc'? refreshVpcCtl:refreshSubnetCtl;
		cn.GetData(url,refreshCtl);
	},
	initRegionSelector=function (){
    	var url='/osn/regions/group';
		return cn.GetData(url,networkListHandler.initRegionSelectorHandler);
    },
    initPaginator=function(){    	
    	var paginatorData=['.table-vpc #paginator','.table-subnet #paginator'];
    	for(var i=0,leng=paginatorData.length;i<leng;i++){
        	$(paginatorData[i]).bootstrapPaginator({
        		size:"small",
            	alignment:'right',
        		bootstrapMajorVersion:3,
        		numberOfPages: 3,
        		onPageClicked: function(e,originalEvent,type,page){
        			currentPage = page;
        			asyncData();
                }
        	});
    	}
    },
    initComponents=function (){
    	initRegionSelector();
    	initPaginator();
    },
    refreshVpcCtl=function(data) {
		networkListHandler.vpcListHandler(data);
        $('.vpc-operation').each(function(index,element){
        	$(element).on('click',function(e){
        		var vpcId= $(e.currentTarget.closest('tr')).find('input:checkbox[name=vpc_id]').val();
        		var fieldRegion= $(e.currentTarget.closest('tr')).find('.field-region').val();
        		var operationType=$(e.currentTarget).attr('class').split(' ')[1];
        		networkListHandler.operateHandler(vpcId,fieldRegion,operationType,asyncData);
        	});
        });

        pollingStatusChange(data);
        networkListHandler.resetSelectAllCheckbox();
	},
    refreshSubnetCtl=function(data) {
		networkListHandler.subnetListHandler(data);
        $('.subnet-operation').each(function(index,element){
        	$(element).on('click',function(e){
        		var subnetId= $(e.currentTarget.closest('tr')).find('input:checkbox[name=subnet_id]').val();
        		var fieldRegion= $(e.currentTarget.closest('tr')).find('.field-region').val();
        		var operationType=$(e.currentTarget).attr('class').split(' ')[1];
        		networkListHandler.operateHandler(subnetId,fieldRegion,operationType,asyncData);
        	});
        });

        pollingStatusChange(data);
        networkListHandler.resetSelectAllCheckbox();
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
			if(networkListHandler.goingStatusList.indexOf(disk.status)>-1){
				clearInterval(iFresh);
				disk.intervalStoped=false;
				disk.intervalObject=setInterval((function(currentDisk){
					return function(){
						var url = '/osv/region/'+currentDisk.region+'/volume/' + currentDisk.id;
						cn.GetData(url,function(data){
							if((data.result==1 && data.data===null) || networkListHandler.goingStatusList.indexOf(data.data.status)==-1){
								if(data.result==1 && data.data===null){//表示已删除
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
	},
	setSearchName=function(){
    	searchName=$('#networkName').val();;
	};
	
	/*禁用退格键退回网页*/
	window.onload=cn.DisableBackspaceEnter();	
	
	/*
	 * 可封装公共方法 begin
	 */
	//初始化checkbox
	$(document).on('click', 'th input:checkbox' , function(){
		var that = this;
		$(this).closest('.table-'+activeTabName).find('tr > td:first-child input:checkbox')
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
	
	vpcModalEl.modal({show:false});
	$("#show_vpc_modal_button").click(function() {		
		vpcModalEl.modal('toggle');
	});	
	
	subnetModalEl.modal({show:false});
	$("#show_subnet_modal_button").click(function() {		
		subnetModalEl.modal('toggle');
	});	
	
    $(".region-city-list input").change(function (){
    	var flavorRam= $(this).val();
    	asyncData();
    });
    $("#subnet_region_selector").change(function (element){
    	var region=$(element.target).val(),
			options=['<option value="">请选择私有网络</option>'],
			subnetVpcSelectorEl=$('#subnet_vpc_selector');
    	if(region!==''){
    		cn.GetData('/osn/network/private/list?region='+region,function(data){
    			if(data.result!==1) return;
    			for(var i=0,leng=data.data.data.length;i<leng;i++){
    				options.push('<option value="'+data.data.data[i].id+'">'+data.data.data[i].name+'</option>');
    			}
    			subnetVpcSelectorEl.html(options.join(''));
    		});
    	}
    	else{
    		subnetVpcSelectorEl.html(options.join(''));
    	}
    });
    $('#search').on('click',function(e){
    	setSearchName();
    	asyncData();
    });
	$("#networkName").keydown(function(e){
		if(e.keyCode==13){
			currentPage = 1;
			setSearchName();
			asyncData();
		}
	});
	
	$('.nav-tabs a').click(function (e) {
		  e.preventDefault();
		  var element=$(this);
		  activeTabName=element.attr('aria-controls');
		  element.tab('show');
		  asyncData();
	});
	
    if(document.getElementById("vpc_create_button").form == null){    //兼容IE form提交
        $("#vpc_create_button").click(function(){
            $("#vpc_form").submit();
        })
    }
    if(document.getElementById("subnet_create_button").form == null){    //兼容IE form提交
        $("#subnet_create_button").click(function(){
            $("#subnet_form").submit();
        })
    }
    
    vpcFormEl.bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: null,
            invalid: null,
            validating: null
        },
        fields: {
        	vpc_name: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '私有网络名称不能为空!'
                    }
                }
            },
            vpc_region_selector: {
                validators: {
                    notEmpty: {
                        message: '请选择地域!'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
		cn.doPost('/osn/network/private/create',{
	        region:vpcRegionSelector.val(),
	        name:newVpcNameEl.val()
	    }).then(function(data){
    		vpcModalEl.modal('toggle');
	    	if(data.result===1){
	    		vpcFormEl.data('bootstrapValidator').resetForm(true);
	    		cn.alertoolSuccess('私有网络创建成功');
	    		asyncData();
	    	}
	    	else{
	    		cn.alertoolDanger(data.msgs[0] || '私有网络创建失败');
	    	}
	    });
    });
    
    subnetFormEl.bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: null,
            invalid: null,
            validating: null
        },
        fields: {
        	subnet_name: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '子网名称不能为空!'
                    }
                }
            },
            subnet_region_selector: {
                validators: {
                    notEmpty: {
                        message: '请选择地域!'
                    }
                }
            },
            subnet_vpc_selector: {
                validators: {
                    notEmpty: {
                        message: '请选择私有网络!'
                    }
                }
            },
            subnet_cidr: {
                validators: {
                    notEmpty: {
                        message: '请输入网段!'
                    }
                }
            },
            subnet_gateway_ip: {
                validators: {
                    notEmpty: {
                        message: '请输入网关!'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
		cn.doPost('/osn/subnet/private/create',{
	        region:$('#subnet_region_selector').val(),
	        networkId:$('#subnet_vpc_selector').val(),
	        name:$('#subnet_name').val(),
	        cidr:$('#subnet_cidr').val(),
	        autoGatewayIp:false,
	        gatewayIp:$('#subnet_gateway_ip').val(),
	        enableDhcp:$('#subnet_enable_dhcp').val()
	    }).then(function(data){
    		subnetModalEl.modal('toggle');
	    	if(data.result===1){
	    		subnetFormEl.data('bootstrapValidator').resetForm(true);
	    		cn.alertoolSuccess('子网创建成功');
	    		asyncData();
	    	}
	    	else{
	    		cn.alertoolDanger(data.msgs[0] || '子网创建失败');
	    	}
	    });
    });
    
    cn.Tooltip();
    /* 初始化navbar-header-menu */
	cn.initNavbarMenu([{name : "云主机",herf : "/list/vm"},
	                   {name : "磁盘",herf : "/list/vm/disk"},
	                   {name : "网络",herf : "/list/vm/network",isActive:true}
	                   ]);	
    
    initComponents();
	
    setListAutoFresh();

});
