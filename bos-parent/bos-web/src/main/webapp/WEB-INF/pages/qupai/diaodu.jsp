<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人工调度</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">

$(function(){
	$("#grid").datagrid({
		url : 'noticebillAction_pageQuery.action',
		toolbar : [ {
			id : 'diaodu',
			text : '人工调度',
			iconCls : 'icon-edit',
			handler : function() {
				var rows = $("#grid").datagrid("getSelections");
				if(rows.length != 1){
					//弹出提示
					$.messager.alert("提示信息","请选择一条数据进行操作！","warning");
				}else{
					var row = rows[0];
					var id = row.id;
					$("input[name=id]").val(id);
					$("#diaoduWindow").window('open');
				}
			}
		} ],
		columns : [ [ {
			field : 'id',
			title : '编号',
			width : 100,
			align : 'center'
		}, {
			field : 'delegater',
			title : '联系人',
			width : 100,
			align : 'center'
		}, {
			field : 'telephone',
			title : '电话',
			width : 100,
			align : 'center'
		}, {
			field : 'pickaddress',
			title : '取件地址',
			width : 100,
			align : 'center'
		}, {
			field : 'product',
			title : '商品名称',
			width : 100,
			align : 'center'
		},{
			field : 'ordertype',
			title : '分单类型',
			width : 100,
			align : 'center'
		} ] ],
		pageList: [10,20,30],
		pagination : true,
		striped : true,
		singleSelect: true,
		rownumbers : true,
		fit : true // 占满容器
	});

		// 点击保存按钮，为通知单 进行分单 --- 生成工单
		$("#save").click(function() {
			var v = $("#diaoduForm").form("validate");
			if(v){
				//$("#addStaffForm").form("submit");
				$("#diaoduForm").submit();
			}
		});
	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<table id="grid"></table>
	</div>
	<div class="easyui-window" title="人工调度" id="diaoduWindow" closed="true"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:100px;left:200px;width: 500px; height: 300px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="diaoduForm" action="noticebillAction_diaodu.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">人工调度</td>
					</tr>
					<tr>
						<td>通知单编号</td>
						<td><input type="hidden" name="id" id="id" /> <span
							id="noticebillIdView"></span>
					</tr>
					<tr>
						<td>选择取派员</td>
						<td>
							<input class="easyui-combobox" name="staff.id"  
    							data-options="valueField:'id',textField:'name',url:'staffAction_listajax.action'" />  
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>