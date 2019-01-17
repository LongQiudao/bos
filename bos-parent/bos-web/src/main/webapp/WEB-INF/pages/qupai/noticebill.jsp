<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务通知单</title>
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

	function doRepeat(){
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length == 0){
			//没有选中记录，弹出提示
			$.messager.alert("提示信息","请选择需要进行追单的选项！","warning");
		}else{
			//选中了取派员,弹出确认框
			$.messager.confirm("删除确认","你确定要进行追单吗？",function(r){
				if(r){
					
					var array = new Array();
					//确定,发送请求
					//获取所有选中的取派员的id
					for(var i=0;i<rows.length;i++){
						var staff = rows[i];//json对象
						var id = staff.id;
						array.push(id);
					}
					var ids = array.join(",");//1,2,3,4,5
					location.href = "workbillAction_repeatBatch.action?ids="+ids;
					
				}
			});
		}
	}
	
	function doCancel(){
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length == 0){
			//没有选中记录，弹出提示
			$.messager.alert("提示信息","请选择需要进行消单的选项！","warning");
		}else{
			//选中了取派员,弹出确认框
			$.messager.confirm("删除确认","你确定要进行消单吗？",function(r){
				if(r){
					
					var array = new Array();
					//确定,发送请求
					//获取所有选中的取派员的id
					for(var i=0;i<rows.length;i++){
						var staff = rows[i];//json对象
						var id = staff.id;
						array.push(id);
					}
					var ids = array.join(",");//1,2,3,4,5
					location.href = "workbillAction_cancelBatch.action?ids="+ids;
					
				}
			});
		}
	}
	
	function doSearch(){
		$('#searchWindow').window("open");
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-repeat',
		text : '追单',
		iconCls : 'icon-redo',
		handler : doRepeat
	}, {
		id : 'button-cancel',	
		text : '销单',
		iconCls : 'icon-cancel',
		handler : doCancel
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'type',
		title : '工单类型',
		width : 120,
		align : 'center'
	}, {
		field : 'pickstate',
		title : '取件状态',
		width : 120,
		align : 'center'
	}, {
		field : 'buildtime',
		title : '工单生成时间',
		width : 150,
		align : 'center',
		formatter :function toDate(data,row,index) {
			      var date = new Date();
			      date.setTime(data.time);
			      var y = date.getFullYear();
			      var m = date.getMonth()+1;
			      m = m<10?'0'+m:m;
			      var s = date.getSeconds();
			      var d = date.getDate();
			      d = d<10?("0"+d):d;
			      var h = date.getHours();
			      h = h<10?("0"+h):h;
			      var M = date.getMinutes();
			      M = M<10?("0"+M):M;
			      var S = date.getSeconds();
			      S = S<10?("0"+S):s;
			      var str = y+"-"+m+"-"+d+" "+h+":"+M+":"+S;
			      return str;
			 }
	}, {
		field : 'attachbilltimes',
		title : '追单次数',
		width : 120,
		align : 'center'
	}, {
		field : 'name',
		title : '取派员',
		width : 100,
		align : 'center',
		formatter : function(data,row ,index){
			return row.staff.name;
		}
	}, {
		field : 'telephone',
		title : '联系方式',
		width : 100,
		align : 'center',
		formatter : function(data,row ,index){
			return row.staff.telephone;
		}
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList: [10,30,50,100],
			pagination : true,
			toolbar : toolbar,
			url :  "workbillAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		//修改工单
		$('#editWorkbillWindow').window({
	        title: '修改工单',
	        width: 400,
	        modal: true,//遮罩效果
	        shadow: true,//阴影效果
	        closed: true,//关闭
	        height: 400,
	        resizable:false
	    });
		// 查询分区
		$('#searchWindow').window({
	        title: '查询工单',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		//定义一个工具方法，用于将指定的form表单中所有的输入项转为json数据{key:value,key:value}
		$.fn.serializeJson=function(){  
            var serializeObj={};  
            var array=this.serializeArray();
            $(array).each(function(){  
                if(serializeObj[this.name]){  
                    if($.isArray(serializeObj[this.name])){  
                        serializeObj[this.name].push(this.value);  
                    }else{  
                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
                    }  
                }else{  
                    serializeObj[this.name]=this.value;   
                }  
            });  
            return serializeObj;  
        }; 
		
		$("#btn").click(function(){
			//将指定的form表单中所有的输入项转为json数据{key:value,key:value}
			var p = $("#searchForm").serializeJson();
			console.info(p);
			//调用数据表格的load方法，重新发送一次ajax请求，并且提交参数
			$("#grid").datagrid("load",p);
			//关闭查询窗口
			$("#searchWindow").window("close");
		});
		$("#btn2").click(function(){
			$("#editworkbillForm").submit();
			$("#editWorkbillWindow").window("close");
		});
	});

	function doDblClickRow(rowIndex, rowData){
		
		$('#editWorkbillWindow').window("open");
		//使用form表单对象的load方法回显数据
		$("#editworkbillForm").form("load",rowData);
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	
	<!-- 查询工单 -->
	<div class="easyui-window" title="查询窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm" >
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>客户电话</td>
						<td><input type="text" name="noticebill.telephone" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>取派员</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>联系方式</td>
						<td><input type="text" name="telephone" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">保存</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 修改工单 -->
	<div class="easyui-window" title="修改窗口" id="editWorkbillWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="editworkbillForm" action="workbillAction_edit.action">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
					</tr>
					<tr>
						<td>取派员</td>
						<td>
							<input class="easyui-combobox" name="staff.id"  
    							data-options="valueField:'id',textField:'name',url:'staffAction_listajax.action'" />  
						</td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn2" href="#" class="easyui-linkbutton" >保存</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>