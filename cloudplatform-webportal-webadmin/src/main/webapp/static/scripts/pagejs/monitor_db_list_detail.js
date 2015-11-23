$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");
	queryMonitorDbInfo();
});

function queryMonitorDbInfo(){
	var ip = $("#vipaddr").val();
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		url :"/monitor/" + ip + "/db/status",
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;			
			var $tby = $("#db_detail_table");
            var data=data.data.response;
            if(data != null){
            	var data = data.db;
                var item = getItem(data);
                for (var i=0;i<item.length;i++ ){
                    /*创建tr*/
                    var tr = $("<tr></tr>");
                    tr.addClass(item[i]);
                    $tby.append(tr);

                   /* 获取数据并添加标记td*/
                    var unitData = data[item[i]];
                    var td = $("<td style=\"width: 20%;\">"
                            + item[i]
                            +  "<input type=\"text\" class=\"hidden\"/>"
                            + "</td>");
                    var inputId = item[i] + "Num";
                    var unitdataCount = getCount(unitData);
                    
                    /*隐藏input用来记录失败fail count*/
                    td.find("input").attr({"id": inputId});

                    /*为表格添加数据*/
                    if(unitData != null){
                        td.attr({"rowspan":unitdataCount + 1});                
                        $('.'+item[i]).append(td);
                        dataAppend(unitData,$("."+item[i]),inputId)
                    }else{
                        var td2 = $("<td>"
                            + unitData
                            + "</td>");
                        td2.attr({"colspan":2});
                        $('.'+item[i]).append(td).append(td2);
                    }
                }
            }else{
				$tby.html("<tr><td>没有查询到数据信息</td></tr>");
			}
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			$.gritter.add({
				title: '警告',
				text: errorThrown,
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
			return false;
		}
	});
}