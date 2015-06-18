/**
 * Created by yaokuo on 2014/12/14.
 * dblist page 
 */
define(function(require,exports,module){
    var $ = require('jquery');
    /*
	 * 引入相关js，使用分页组件
	 */
	require('bootstrap');
	require('paginator')($);
	
    var common = require('../../common');
    var cn = new common();
   
    
    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        DbListHandler : function(data){
        	$(".data-tr").remove();
        	
            var $tby = $('#tby');
            var array = data.data;
            if(array.length == 0){
            	cn.emptyBlock($tby);
            }else{
            	 if($("#noData").length > 0){
            		 $("#noData").remove();
            	 }
                for(var i= 0, len= array.length;i<len;i++){
                	var tdList= [];
                	tdList.push("<td width=\"10\">"+ 
                            		"<input type=\"checkbox\" name=\"vm_id\" value= \""+array[i].id+"\">"+
                            	"</td>");
                    var vmName = "";
                    if(cn.Displayable(array[i].status)){
                    	vmName = "<a href=\"/detail/vm/"+array[i].id+"\">" + array[i].name + "</a>"
                    }else{
                    	vmName = "<span class=\"text-explode font-disabled\">"+array[i].name+"</span>"
                    }
                    tdList.push("<td class=\"padding-left-32\">"+ vmName+"</td>");
                    tdList.push("<td class=\"padding-left-32\">"+ array[i].image.name+"</td>");
                    tdList.push("<td class=\"padding-left-32\">"+ array[i].ipAddresses.join(',')+"</td>");
                    tdList.push("<td class=\"padding-left-32\">"+
                    			[array[i].flavor.name,array[i].flavor.ram+' 内存',array[i].flavor.vcpus+' 虚拟内核',array[i].flavor.disk+'G 硬盘',].join('|')+
                    			"</td>");
                    tdList.push("<td class=\"padding-left-32\">"+ array[i].status+"</td>");
                    tdList.push("<td class='hidden-xs'>"+
                            		"<span>"+array[i].region+"</span>"+
                             	"</td>");
                    tdList.push('<td class="text-right hidden-xs"><a href="/detail/vm/'+array[i].id+'">管理</a>|<a class="vm-remove" href="javascript:void(0);">删除</a></td>');
                    tdList.unshift("<tr class='data-tr'>");
                    tdList.push("</tr>");
                    
                    $tby.append(tdList.join(""));
                    $('.vm-remove').each(function(index,element){
                    	$(element).on('click',function(e){
                    		var vmId= e.currentTarget.colset('tr').find('input:checkbox[name=vm_id]').val();
                    		var currentRegion=$('select#region_selector').val();
                    		var removeVmUrl = '/ecs/region/'+currentRegion+'/vm-delete';
                    		cn.PostData(removeVmUrl,{},function(data){
                    			//TODO 修改删除提示
                    			alert('删除成功');
                    		});
                    	});
                    });
                 }
            }
       
        },
        /*进度条进度控制*/
	    progress : function(dbId,data,asyncData){
	    	var data = data.data;	    	
   	        var unitLen = 100 / 10;
   	        var $obj = $("#prg" + dbId);
   	        var $prg = $obj.find(".progress-bar");
   	       	var pWidth = unitLen * data;
           	if( data == 1){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在准备安装环境...");
           	}else if (data > 1 && data <= 3){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在检查安装环境...");
           	}else if (data > 3 && data <= 6){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在初始化数据库服务...");
           	}else if (data > 6 && data < 10){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在创建数据库...");
           	}else if (data == 0 || data >= 10){
           		$prg.css({"width": "100%"});
           		$prg.html("100%");
           		$obj.next().html("创建完成");
           		asyncData();
           	}else if(data == -1){
           		$prg.css({"width": "100%"});
           		$prg.html("100%");
           		$obj.next().html("创建失败");
           		asyncData();
           	}else{
           		asyncData();
           	}
	   	},
	   	initRegionSelectorHandler:function(data){
   			$('#region_selector').empty();
	   		if(data.result==1&&data.data.length){
	   			var optionList=[];
	   			for(var i=0,len=data.data.length;i<len;i++){
		   			optionList.push('<option value="'+data.data[i]+'">'+data.data[i]+'</option>');
	   			}
	   			$('#region_selector').append(optionList.join(''));
	   		}
	   	}
    }
});