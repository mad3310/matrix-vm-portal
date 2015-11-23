/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require, exports, module) {
	var $ = require('jquery');
	var common = require('../../common');
	var cn = new common();

	var DataHandler = function() {
	};

	module.exports = DataHandler;

	DataHandler.prototype = {
		GetHclusterHandler : function(data) {
			var hcluster = data.data;
			var ul = $("[name='hclusterId']").parent('div').find('ul');
			for (var i = 0, len = hcluster.length; i < len; i++) {
				var li = $("<li class=\"bk-select-option\"><a href=\"javascript:;\" selectid=\""
						+ hcluster[i].id
						+ "\">"
						+ hcluster[i].hclusterNameAlias + "</a></li>");
				li.appendTo(ul);
			}
			cn.divselect();
		}
	}
});