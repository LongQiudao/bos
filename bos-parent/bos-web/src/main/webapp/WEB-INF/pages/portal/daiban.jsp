<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div style="padding:10px;">
	<table class="easyui-datagrid" style="height:auto">
		<thead>
			<tr>
				<th data-options="field:'priority'">优先级</th>
				<th data-options="field:'state'">状态</th>
				<th data-options="field:'subject'">主题</th>
				<th data-options="field:'type'">单据类型</th>
				<th data-options="field:'sender'">发送人</th>
				<th data-options="field:'senddate'">发送日期</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>重要</td>
				<td>未查看</td>
				<td>写代码</td>
				<td>好多代码都没敲</td>
				<td>益达</td>
				<td>2019-1-18 15:32:23</td>
			</tr>
			<tr>
				<td>重要</td>
				<td>未查看</td>
				<td>敲代码</td>
				<td>好多代码没写</td>
				<td>大老鼠</td>
				<td>2018-01-04 15:32:23</td>
			</tr>
		</tbody>
	</table>
</div>