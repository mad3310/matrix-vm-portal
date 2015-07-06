<%@ page language="java" pageEncoding="UTF-8"%>
<!-- /section:settings.box -->
<div class="page-content-area">
  <div class="row">
    <div class="widget-box widget-color-blue ui-sortable-handle queryOption collapsed">
      <script>
	$(window).load(function() {
	var iw=document.body.clientWidth;
	if(iw>767){//md&&lg
	$('.queryOption').removeClass('collapsed');
	}
	});
      </script>
      <div class="widget-header hidden-md hidden-lg">
	<h5 class="widget-title">字典查询条件</h5>
	<div class="widget-toolbar">
	  <a href="#" data-action="collapse">
	    <i class="ace-icon fa fa-chevron-down"></i>
	  </a>
	</div>
      </div>
      <div class="widget-body">
	<div class="page-header">
	  <div class="input-group pull-right">
	    <form class="form-inline">
	      <div class="form-group  col-sm-6 col-xs-12 col-md-4">
		<input type="text" class="form-control" id="seDictName"
		       placeholder="字典名称">
	      </div>
	      <div class="form-group  col-sm-6 col-xs-12 col-md-4">
		<input type="text" class="form-control" id="seDictType"
		       placeholder="字典类型">
	      </div>
	    <!--   <div class="form-group  col-sm-6 col-xs-12 col-md-3">
		<input type="text" class="form-control" id="seDictDescn"
		       placeholder="字典描述">
	      </div> -->
	      <div class="form-group  col-sm-6 col-xs-12 col-md-4">
		<button class="btn btn-sm btn-primary btn-search" id="dictSearch" type="button">
		  <i class="ace-icon fa fa-search"></i>搜索
		</button>
		<button class="btn btn-sm" type="button" id="dictClearSearch">清空</button>
	      </div>
	    </form>
	  </div>
	</div>
      </div>
    </div>
    <!-- /.page-header -->
    <div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
      <div class="widget-header">
	<h5 class="widget-title">字典列表</h5>
	<div class="widget-toolbar no-border">
	  <button class="btn btn-white btn-primary btn-xs" data-toggle="modal" data-target="#add-dict-modal">
	    <i class="ace-icont fa fa-plus"></i>
	    添加字典
	  </button>
	</div>
      </div>
      <div class="widget-body">
	<div class="widget-main no-padding">
	  <table id="mcluster_list" class="table table-striped table-bordered table-hover">
	    <thead>
	      <tr>
		<th class="center">
		  <label class="position-relative">
		    <input type="checkbox" class="ace" />
		    <span class="lbl"></span>
		  </label>
		</th>
		<th>名称</th>
		<th class="hidden-480">类型</th>
		<th class="hidden-480">描述</th>
		<th>操作</th>
	      </tr>
	    </thead>
	    <tbody id="tby">
	    </tbody>
	  </table>
	</div>
      </div>
    </div>
    <div id="pageControlBar" class="col-xs-12">
      <input type="hidden" id="totalPage_input" />
      <ul class="pager">
	<li><a href="javascript:void(0);" id="firstPage">&laquo&nbsp;首页</a></li>
	<li><a href="javascript:void(0);" id="prevPage" >上一页</a></li>
	<li><a href="javascript:void(0);" id="nextPage">下一页</a></li>
	<li><a href="javascript:void(0);" id="lastPage">末页&nbsp;&raquo</a></li>
	<li class="hidden-480"><a>共<lable id="totalPage"></lable>页</a></li>
	<li class="hidden-480"><a>第<lable id="currentPage"></lable>页</a></li>
	<li class="hidden-480"><a>共<lable id="totalRows"></lable>条记录</a></li>
      </ul>
    </div>
  </div>
  <div class="modal fade" id="add-dict-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="margin-top:157px">
    <div class="modal-dialog">
      <div class="modal-content">
	<div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span aria-hidden="true"><i class="ace-icon fa fa-times-circle"></i></span>
            <span class="sr-only">关闭</span>
          </button>
          <h4 class="modal-title">添加字典</h4>
        </div>
	<form id="add-dict-form" name="add-dict-form" class="form-horizontal" role="form">
          <div class="form-group">
	    <label class="col-sm-12 col-xs-12 col-md-4 control-label" for="mcluster_name">名称</label>
	    <div class="col-sm-10 col-xs-10 col-md-6">
	      <input class="form-control" name="dictName" id="dictName" type="text" />
	    </div>
	    <!-- <label class="control-label">
	      <a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="" style="cursor:pointer; text-decoration:none;">
		<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
	      </a>
	    </label> -->
	  </div>
	  <div class="form-group">
	    <label class="col-sm-12 col-xs-12 col-md-4 control-label" for="hcluster">类型</label>
	    <div class="col-sm-10 col-xs-10 col-md-6">
	      <input class="form-control" name="dictType" id="dictType" type="text" />
	    </div>
	    <label class="control-label" for="hcluster">
	      <a id="hclusterHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="类型为数字（0-99）" style="cursor:pointer; text-decoration:none;">
		<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
	      </a>
	    </label>
	  </div>
	  <div class="form-group">
	    <label class="col-sm-12 col-xs-12 col-md-4 control-label" for="hcluster">字典描述</label>
	    <div class="col-sm-10 col-xs-10 col-md-6">
	      <textarea class="form-control" name="dictDescn" id="dictDescn" type="text" ></textarea>
	    </div>
	    <!-- <label class="control-label" for="hcluster">
	      <a id="hclusterHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="" style="cursor:pointer; text-decoration:none;">
	        <i class="ace-icon fa fa-question-circle blue bigger-125"></i>
	      </a>
	    </label> -->
	  </div>
	  <div class="modal-footer">
	    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
	    <button id="add-dict-botton" type="submit" class="btn btn-sm btn-primary">创建</button>
	  </div>
	</form>
      </div>
    </div>
  </div>
  <div class="modal fade" id="modify-dict-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="margin-top:157px">
    <div class="modal-dialog">
      <div class="modal-content">
	<div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span aria-hidden="true"><i class="ace-icon fa fa-times-circle"></i></span>
            <span class="sr-only">关闭</span>
          </button>
          <h4 class="modal-title">修改字典</h4>
        </div>
	<form id="modify-dict-form" name="modify-dict-form" class="form-horizontal" role="form">
          <input type="hidden" value="" id="modifyDictId"/>
          <div class="form-group">
	    <label class="col-sm-12 col-xs-12 col-md-4 control-label" for="mcluster_name">名称</label>
	    <div class="col-sm-10 col-xs-10 col-md-6">
	      <input class="form-control" name="modifyDictName" id="modifyDictName" type="text" />
	    </div>
	    <label class="control-label">
	      <a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="" style="cursor:pointer; text-decoration:none;">
		<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
	      </a>
	    </label>
	  </div>
	  <div class="form-group">
	    <label class="col-sm-12 col-xs-12 col-md-4 control-label" for="hcluster">类型</label>
	    <div class="col-sm-10 col-xs-10 col-md-6">
	      <input class="form-control" name="modifyDictType" id="modifyDictType" type="text" />
	    </div>
	    <label class="control-label" for="hcluster">
	      <a id="hclusterHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="" style="cursor:pointer; text-decoration:none;">
		<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
	      </a>
	    </label>
	  </div>
          <div class="form-group">
	    <label class="col-sm-12 col-xs-12 col-md-4 control-label" for="hcluster">字典描述</label>
	    <div class="col-sm-10 col-xs-10 col-md-6">
	      <textarea class="form-control" name="modifyDictDescn" id="modifyDictDescn" type="text" ></textarea>
	    </div>
	    <label class="control-label" for="hcluster">
	      <a id="hclusterHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="" style="cursor:pointer; text-decoration:none;">
	        <i class="ace-icon fa fa-question-circle blue bigger-125"></i>
	      </a>
	    </label>
          </div>
	  <div class="modal-footer">
	    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
	    <button id="modify-dict-botton" type="submit" class="btn btn-sm btn-primary">修改</button>
	  </div>
	</form>
      </div>
    </div>
  </div>
  <div id="dialog-confirm" class="hide">
    <div id="dialog-confirm-content" class="alert alert-info bigger-110"></div>
    <div class="space-6"></div>
    <p id="dialog-confirm-question" class="bigger-110 bolder center grey"></p>
  </div>
</div>
<!-- /.page-content-area -->

<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/ace/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/static/ace/js/jquery.dataTables.bootstrap.js"></script>

<script src="${ctx}/static/scripts/pagejs/common/dict_mgr.js"></script>
