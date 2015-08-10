/**
 * Created by yaokuo on 2014/12/12.
 * dblist page
 */
define(function(require){
    var common = require('../../common');
    var dataHandler = require('./dataHandler');
    
    
    var cn = new common(),
    	diskListHandler = new dataHandler(),
    	vmId=$("#vmId").val(),
    	region=$("#region_id").val(),
    	diskNameSelectorEl=$("#disk_name_selector"),
    	modalAttachDiskEl=$('#modal_attach_disk');
    
    var asyncData=function () {
    	cn.GetData('/ecs/region/'+region+'/vm/'+vmId,refreshCtl);
	},
    initComponents=function (){
		asyncData();
    },
    refreshCtl=function(data) {
		diskListHandler.DiskListHandler(data);
        $('.disk-operation').each(function(index,element){
        	$(element).on('click',function(e){
        		var diskId= $(e.currentTarget.closest('tr')).find('input:checkbox[name=disk_id]').val();
        		var fieldRegion= $(e.currentTarget.closest('tr')).find('.field-region').val();
        		var operationType=$(e.currentTarget).attr('class').split(' ')[1];
        		diskListHandler.operateDisk(diskId,vmId,fieldRegion,operationType,asyncData);
        	});
        });
        diskListHandler.resetSelectAllCheckbox();
	},
	initAttachDiskSelector=function(){
		diskNameSelectorEl.empty();
		var url='/osv/region/'+region;
		cn.GetLocalData(url,function(data){
			if(data.result==0 || data.data.data.length<=0) return;
			var optionHtmls=[];
			for(var i=0,leng=data.data.data.length;i<leng;i++){
				if(data.data.data[i].status=='available'){
					optionHtmls.push(['<option value="',data.data.data[i].id,'">',data.data.data[i].id.split('-')[0],'  / ',data.data.data[i].size,'GB','</option>'].join(''));
				}
			}
			diskNameSelectorEl.append(optionHtmls.join(''));
		});
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
	
	$("#refresh").click(function() {		
		asyncData();
	});	
	
	$('.btn-attach-disk').on('click',function(e){
		modalAttachDiskEl.modal('show');
	});
	$('.btn-exec-attach').on('click',function(e){
	    var url='/ecs/region/'+region+'/vm-attach-volume';
	    cn.PostData(url,{
	        vmId:vmId,
	        volumeId:diskNameSelectorEl.val()
	    },function(data){
	    	if(data.result==1){
	    		modalAttachDiskEl.modal('hide');
	    		cn.alertoolSuccess('挂载成功。');
	    		asyncData();
	    	}
	    	else{
	    		cn.alertoolDanger(data.msgs[0]||'挂载失败失败！');
	    	}
	    });
	});
    
    cn.Tooltip();
    
    modalAttachDiskEl.modal({
  	  backdrop:false,
  	  show:false
    });
    modalAttachDiskEl.on('show.bs.modal', function (event) {
		initAttachDiskSelector();
    });
    
    initComponents();
	
});
