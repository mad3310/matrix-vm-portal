/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../../common');
    var cn = new common();
    
    cn.divSelect();//初始化创建页select功能

    /*禁用退格键退回网页*/
    window.onload=cn.DisableBackspaceEnter();

    /*按钮组件封装 --begin*/
	$(document).on('click', '.bk-buttontab .bk-button-primary' , function(e){
        if(!$(this).hasClass("disabled")){
            $(this).parent().find(".bk-button-primary").removeClass("bk-button-current");
            $(this).addClass("bk-button-current");
            if($(this).parent().find(".hide").length > 0 ){
                var val = $(this).val();
                var hiddenInput=$(this).parent().find(".hide");
                if(val!==hiddenInput.val()){
                    hiddenInput.val(val);
                    hiddenInput.trigger('change');
                }	
            }
        }
        return false;//阻止表单的自动提交
	});
	/*按钮组件封装 --end*/    


	var sizeInputEl=$('.yundisk-size-input');
	var numberInputEl=$('.bk-number-input');
	sizeInputEl.on('blur', function(e){        
        if(!cn.validateInputNum(sizeInputEl.val())){
        	sizeInputEl.val('1');
        }
        $('#buy-disk-size').text(sizeInputEl.val()+'GB');
	});
	
	cn.NumberInput(1,10,1,function(e){
        $('#buy-disk-count').text(numberInputEl.val()+'个');
	});

    /*表单验证 --begin*/
	$('#monthPurchaseBotton').on('click',function(e){
        var regionName = $("[name = regionName]").val();
        var url = '/osv/region/'+regionName+'/volume-create';
    	var data =  {
						        size: sizeInputEl.val(),
						        count: numberInputEl.val(),
						    };
		cn.PostData(url, data, function (data) {
			if(data.result===1){
	               location.href = "/list/vm/disk";	
			}
			else{
				$('#submitResult').text(data.msgs[0]||'创建云主机失败！');
			}
         });
	});

    
    /*表单验证 --end*/

    /*加载数据*/
    var DataHandler = require('./dataHandler');
    var dataHandler = new DataHandler(require);
    var initRegionCityNames = function (){
    	var url='/osv/regions/group';
    	cn.GetData(url,dataHandler.getRegionCityname);
    };
    $(".bk-buttontab-regioncitys input").change(function (){
    	var regionCityName= $(this).val();
    	dataHandler.getRegion(regionCityName);
    });
    $("input[name='regionName']").change(function (){
    	var regionName= $(this).val();
    	dataHandler.setBuyRegionName(regionName);
    });
     initRegionCityNames();
});
