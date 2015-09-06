/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../../common');
    var cn = new common();
    var $ = require("jquery");

    require("bootstrapValidator")($);
    cn.initNavbarMenu([{
                name : "Le云控制台",
                herf : "/dashboard"
            }]);
	/* 防止误操作退出创建页面 */
	cn.AddBeforeunloadListener();

    if(document.getElementById("monthPurchaseBotton").form == null){    //兼容IE form提交
        $("#monthPurchaseBotton").click(function(){
            $("#monthPurchaseForm").submit();
        })
    }

    /*按钮组件封装 --begin*/
    // $(".bk-button-primary").unbind('click').click(function () {
    //     cosnole.log('a')
    //     if(!$(this).hasClass("disabled")){
    //         $(this).parent().find(".bk-button-primary").removeClass("bk-button-current");
    //         $(this).addClass("bk-button-current");
    //         if($(this).parent().find(".hide").length > 0 ){
    //             var val = $(this).val();
    //             $(this).parent().find(".hide").val(val);
    //         }
    //     }
    // })
    /*按钮组件封装 --end*/

    /*表单验证 --begin*/
    $("#monthPurchaseForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            dbName: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '数据库名称不能为空!'
                    },
                    stringLength: {
                        max: 16,
                        message: '数据库名名过长!'
                    }, regexp: {
                        regexp: /^((?!^monitor$)([a-zA-Z_]+[a-zA-Z_0-9]*))$/,
                        message: "请输入字母数字或'_',数据库名不能以数字开头且数据库名称不能命名为monitor."
                    }/*,
                    remote: {
                        message: '数据库名已存在!',
                        url: '/db/validate'
                    }*/
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var dbName = $("[name = 'dbName']").val();
        var hclusterId = $("[name = 'hclusterId']").val();
        var engineType = $("[name = 'engineType']").val();
        var linkType = $("[name = 'linkType']").val();
        var isCreateAdmin = $("[name = 'isCreateAdmin']").val();
        var storageSize = $("[name = 'storageSize']").val();
        var memorySize = $("[name = 'memorySize']").val();
        var formData = {"dbName":dbName,"linkType":linkType,"engineType":engineType,"hclusterId":hclusterId,"isCreateAdmin":isCreateAdmin,"storageSize":storageSize,"memorySize":memorySize};
        CreateDb(formData);
    });
    /*表单验证 --end*/

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var createDbHandler = new dataHandler();
    // GetHcluster();
    function GetHcluster(){
        var url="/hcluster/rds";
        cn.GetData(url,createDbHandler.GetHclusterHandler);
    }
    /*创建数据库*/
    function CreateDb(data){
    	cn.RemoveBeforeunloadListener();
        var url="/db";
        cn.PostData(url,data, function () {
            location.href = "/list/db";
        });
    }

    // 手机端 购买页面 适配
    $('.step-next').unbind('click').click(function(event) {
        $(this).parent().addClass('hide').next().removeClass('config-buy');
        $('body').scrollTop(0)
    });
    $('.step-forward').unbind('click').click(function(event) {
        $('.step-next').parent().removeClass('hide');
        $(this).parent().addClass('config-buy');
    });
    $('.connect-help a').unbind('click').click(function(event) {
        var _target=$(this).parent().next();
        if(_target.hasClass('hide')){
            _target.removeClass('hide')
        }else{
            _target.addClass('hide')
        }
        
    });

    //billing 2015-08-31
    $.ajax({
        url: '/billing/product/1',
        type: 'get',
        // dataType:'json',
        success:function(data){
            var _data=data.data;
            //地域&可用区赋值
            var _region=$('div[self-tag="region"]');
            var _available=$('div[self-tag="available"]');
            var _regionArray=_data.region;
            var _regionHtml='';
            for(var i in _regionArray){
                _regionHtml=_regionHtml+"<button class=\" bk-button bk-button-primary\" self-rely="+_regionArray[i].value+"><div><span>"+_regionArray[i].text+"</span></div></button>";
                var _availableArray=_regionArray[i].area;
                var _availableHtml='';
                for(var j in _availableArray){
                    _availableHtml=_availableHtml+ "<li class='bk-select-option'><a href='javascript:;' selectid='"+_availableArray[j].value+"'>"+_availableArray[j].text+"</a></li>";
                }
                _available.find('.sleHid').append("<div class='divselect hidden' self-rely="+_regionArray[i].value+"><span self-tag='available'></span><ul style='display:none;'>"+_availableHtml+"</ul></div>")
            }
            _region.html(_regionHtml);
            _region.children('button:eq(0)').addClass('bk-button-current');
            $('#region').val(_region.children('button.bk-button-current').attr('self-rely'));
            var _availableIndex=_region.children('button.bk-button-current').attr('self-rely');
            _available.find('.sleHid').children('.divselect[self-rely='+_availableIndex+']').removeClass('hidden');
           //end 联动可用区赋值

           //db 配置
           var _dbArray=_data.rds_db;
           var _dbConfig=$('div[self-tag="dbConfig"]');
           var _dbVersion=$('div[self-tag="dbVersion"]');
           var _dbEngine=$('div[self-tag="dbEngine"]');
           var _dbLinkType=$('div[self-tag="dbLinkType"]');
           var _dbAccount=$('div[self-tag="dbAccount"]');
           var _dbConfigHtml='';
           for(var k in _dbArray){
                _dbConfigHtml=_dbConfigHtml+ "<button class=\" bk-button bk-button-primary\" self-rely="+_dbArray[k].value+"><div><span>"+_dbArray[k].text+"</a></li>";
                var _dbVersionArray=_dbArray[k].db_version;
                var _dbEngineArray=_dbArray[k].db_engine;
                var _dbLinkTypeArray=_dbArray[k].db_linkType;
                var _dbAccountArray=_dbArray[k].db_account;
                var _dbVersionHtml='',_dbEngineHtml='',_dbLinkTypeHtml='',_dbAccountHtml='';
                for(var k2 in _dbVersionArray){//版本
                    _dbVersionHtml=_dbVersionHtml+"<li class='bk-select-option'><a href='javascript:;' selectid='"+_dbVersionArray[k2].value+"'>"+_dbVersionArray[k2].text+"</a></li>";
                }
                _dbVersion.find('.sleHid').append("<div class='divselect hidden' self-rely="+_dbArray[k].value+"><span self-tag='dbVersion'></span><ul style='display:none;'>"+_dbVersionHtml+"</ul></div>");
                $('#dbVersion').unbind('trigger').trigger('change');
                //engine
                modalChildAppend(_dbEngine,_dbEngineArray,_dbArray,k);
                //linkType
                modalChildAppend(_dbLinkType,_dbLinkTypeArray,_dbArray,k);
                //account
                modalChildAppend(_dbAccount,_dbAccountArray,_dbArray,k);
            }
            _dbConfig.html(_dbConfigHtml);
            _dbConfig.children('button:eq(0)').addClass('bk-button-current');
            $('#dbConfig').val(_dbConfig.children('button.bk-button-current').attr('self-rely'));
            var _relyIndex=_dbConfig.children('button.bk-button-current').attr('self-rely');
            //rely-version
            _dbVersion.find('.sleHid').children('.divselect[self-rely='+_relyIndex+']').removeClass('hidden');
            //rely-engine
            modalChildAppendShow(_dbEngine,_relyIndex,'dbEngine');
            //rely-linkType
            modalChildAppendShow(_dbLinkType,_relyIndex,'dbLinkType');
            //rely-account
            modalChildAppendShow(_dbAccount,_relyIndex,'dbAccount');
            //end db 配置
            //存储配置
            var _dbStorage=$('div[self-tag="dbStorage"]');
            var _dbStorageData=_data.rds_storage[0];
            _dbStorage.find('.sleHid').append("<div class='divselect'><span self-tag='dbStorage'></span><ul style='display:none;'>"+storageInite(_dbStorageData,5)+"</ul></div>")
            //内存
            var _dbRam=$('div[self-tag="dbRam"]');
            var _dbRamArray=_data.rds_ram;
            var _dbRamHtml='';
            for(var m in _dbRamArray){
                _dbRamHtml=_dbRamHtml+"<li class='bk-select-option'><a href='javascript:;' selectid='"+_dbRamArray[m].value+"'>"+_dbRamArray[m].text+"</a></li>";
            }
            _dbRam.find('.sleHid').append("<div class='divselect'><span self-tag='dbRam'></span><ul style='display:none;'>"+_dbRamHtml+"</ul></div>");
            //end 存储配置
        }
    })
    .done(function(data){
        cn.divselect();//初始化所有divselect 下拉框
        //联动地域&可用区
        var _current=$('div[self-tag="region"]');
        var _available=$('div[self-tag="available"]');
        _current.unbind('click').click(function(event) {
            var _src=event.srcElement || event.target;
            var _this=$(_src).closest('button');
            if(_this.hasClass('bk-button-current')){

            }else{
                _this.removeClass('disabled').addClass('bk-button-current');
                _this.siblings().removeClass('bk-button-current');
            }
            var _avaiIndex=_this.attr('self-rely');
            $('#region').val(_avaiIndex).unbind('trigger').trigger('change');
            _available.find('.sleHid').children('.divselect[self-rely='+_avaiIndex+']').removeClass('hidden').siblings('.divselect').addClass('hidden');
            _available.find(".divselect:visible").find('ul li').first().click();
            
        });//end 联动地域&可用区

        //联动数据库
        var _current=$('div[self-tag="dbConfig"]');
        var _dbVersion=$('div[self-tag="dbVersion"]');
        var _dbEngine=$('div[self-tag="dbEngine"]');
        var _dbLinkType=$('div[self-tag="dbLinkType"]');
        var _dbAccount=$('div[self-tag="dbAccount"]');
        _current.unbind('click').click(function(event) {//联动db & version & engine & linkType
            var _src=event.srcElement || event.target;
            var _this=$(_src).closest('button');
            if(_this.hasClass('bk-button-current')){

            }else{
                _this.removeClass('disabled').addClass('bk-button-current');
                _this.siblings().removeClass('bk-button-current');
            }
            var _relyIndex=_this.attr('self-rely');
            $('#dbConfig').val(_relyIndex).unbind('trigger').trigger('change');
            //version-下拉组件
            _dbVersion.find('.sleHid').children('.divselect[self-rely='+_relyIndex+']').removeClass('hidden').siblings('.divselect').addClass('hidden');
            _dbVersion.find(".divselect:visible").find('ul li').first().click();
            //engine-同一
            modalChildInite(_dbEngine,_relyIndex,'dbEngine');
            //linkType-同一
            modalChildInite(_dbLinkType,_relyIndex,'dbLinkType');
            //account-同一
            modalChildInite(_dbAccount,_relyIndex,'dbAccount');
        });//end db&配置
        butModalClick(_dbEngine,'dbEngine');//engine
        butModalClick(_dbLinkType,'dbLinkType');//linkType
        butModalClick(_dbAccount,'dbAccount');//account
        //end 联动数据库&配置
        //order_num 设置
        var _orderNum=data.data.order_num[0].value;
        dbNum(1,_orderNum);

        billing();
        var timeM='';
        $('input').unbind('change').change(function(event) {//触发计费接口
            //判断 tai 的范围
            var _tai=$('.tai-num').val();
            ifTaiValid(_tai,_orderNum)
            //触发计费接口
            console.log($(this).val());
            $('#configMoney').text('正在计算...')
            if(timeM){ 
                console.log(timeM)
                clearTimeout(timeM); 
            }else{
            }
            timeM=setTimeout(function(){
                billing();
            },500); 
        });
    });
    function modalChildAppend(obj,objArray,objParent,objParentIndex){
        var _childHtml='';
        for(var k3 in objArray){//引擎
            _childHtml=_childHtml+"<button class='bk-button bk-button-primary' self-rely='"+objParent[objParentIndex].value+"' self-input-value='"+objArray[k3].value+"'><div><span>"+objArray[k3].text+"</span></div>"
        }
        obj.append("<div class='bk-buttontab hidden' self-rely='"+objParent[objParentIndex].value+"'>"+_childHtml+"</div>");
    }
    function modalChildAppendShow(obj,_relyIndex,id){
        obj.children('div[self-rely='+_relyIndex+']').removeClass('hidden').siblings().addClass('hidden');
        var _Button=obj.children('div:visible').children('button:eq(0)')
        _Button.addClass('bk-button-current');
        $('#'+id).val(_Button.attr('self-input-value')).unbind('trigger').trigger('change');
        // $('#'+id).unbind('trigger').trigger('change');
    }
    function modalChildInite(obj,_relyIndex,id){
        var _Child=obj.children('div[self-rely='+_relyIndex+']');
        _Child.removeClass('hidden').siblings('div').addClass('hidden');
        _Child.children('button:eq(0)').addClass('bk-button-current');
        $('#'+id).val(_Child.children('button.bk-button-current').attr('self-input-value')).unbind('trigger').trigger('change');
        // $('#'+id).unbind('trigger').trigger('change');
    }
    function butModalClick(obj,id){
        obj.unbind('click').click(function(event) {
            event.stopPropagation();
            var _src=event.srcElement || event.target;
            var _this=$(_src).closest('button');
            if(_this.hasClass('bk-button-current')){

            }else{
                _this.removeClass('disabled').addClass('bk-button-current');
                _this.siblings().removeClass('bk-button-current');
            }
            var _relyIndex=_this.attr('self-input-value');
            $('#'+id).val(_relyIndex).unbind('trigger').trigger('change');

        });
    }
    function dbNum(numStep,_orderNum){
        var _upT = $('.bk-number-up:visible');
        var _downT = $('.bk-number-down:visible');
        var _taiNum = $('.tai-num');
        _upT.unbind('click').click(function(event) {
            event.stopPropagation();
            var val = _taiNum.val();
            val = parseInt(val);
            val = val + numStep;
            if (val <1) {
                val = 1;
                _upT.removeClass('bk-number-disabled');
                _downT.addClass('bk-number-disabled');
            } else if (val>_orderNum) {
                val=_orderNum;
                _upT.addClass('bk-number-disabled');
                _downT.removeClass('bk-number-disabled');
            } else {
                // 合法范围
                _upT.removeClass('bk-number-disabled');
                _downT.removeClass('bk-number-disabled');
            }
            _taiNum.val(val).unbind('trigger').trigger('change');
        });
        _downT.unbind('click').click(function(event) {
            event.stopPropagation();
            var val = _taiNum.val();
            val = parseInt(val);
            val = val-numStep;
            if (val <1) {
                val = 1;
                _upT.removeClass('bk-number-disabled');
                _downT.addClass('bk-number-disabled');
            } else if (val>_orderNum) {
                val=_orderNum;
                _upT.addClass('bk-number-disabled');
                _downT.removeClass('bk-number-disabled');
            } else {
                // 合法范围
                _upT.removeClass('bk-number-disabled');
                _downT.removeClass('bk-number-disabled');
            }
            _taiNum.val(val).unbind('trigger').trigger('change');
        });
    }
    function ifTaiValid(_tai,_orderNum){
        _tai = parseInt(_tai);
        if(_tai<1){
            $('.bk-number-down:visible').addClass('bk-number-disabled');
            $('.bk-number-up:visible').removeClass('bk-number-disabled');
            $('.tai-num').val(1); 
        }else if(_tai>_orderNum){
             $('.bk-number-up:visible').addClass('bk-number-disabled');
            $('.bk-number-down:visible').removeClass('bk-number-disabled');
            $('.tai-num').val(_orderNum); 
        }
    }
    function storageInite(obj,step){
        var _val=obj.value;
        _val=parseInt(_val);
        switch(obj.unit){
            case 'GB':
                break;
            case 'MB':
                _val=_val/1024;
                break;
            case 'KB':
                _val=_val/1024/1024;
                break;
            case 'B':
                _val=_val/1024/1024/1024;
                break;
            default:
                _val=_val/1024/1024/1024;
                break;
        }
        var _dbStorageHtml='';
        for(var i=5;i<=_val;i=i+step){
            var ram=i*1024*1024*1024
            _dbStorageHtml=_dbStorageHtml+"<li class='bk-select-option'><a href='javascript:;' selectid='"+ram+"'>"+i+"GB</a></li>";
        }
        return _dbStorageHtml;
    }
    function billing(){
        var _region=$('#region').val();
        var _area=$('#available').val();
        var _rds_ram=$('#dbRam').val();
        var _order_time=$('#dbTime').val();
        var _rds_db=$('#dbConfig').val();
        var _db_version=$('#dbVersion').val();
        var _db_account=$('#dbAccount').val();
        var _db_engine=$('#dbEngine').val();
        var _db_linkType=$('#dbLinkType').val();
        var _order_num=$('#dbNum').val();
        var _rds_storage=$('#dbStorage').val();
        var postData={
            "region":_region,
            "area":_area,
            "rds_ram":_rds_ram,
            "order_time":_order_time,
            "rds_db":_rds_db,
            "db_version":_db_version,
            "db_account":_db_account,
            "db_engine":_db_engine,
            "db_linkType":_db_linkType,
            "order_num":_order_num,
            "rds_storage":_rds_storage
        }
        console.log(postData)
        var _configMoney=$('#configMoney')
        $.ajax({
            url: '/billing/price/1',
            type: 'post',
            data:postData,
            success:function(data){
                if(data.result==0){
                    _configMoney.text('正在计算...'); 
                }else{
                    _configMoney.text(data.data); 
                }
                
            }
        });
    }
});
