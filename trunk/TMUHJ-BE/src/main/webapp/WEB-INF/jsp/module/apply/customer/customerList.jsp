<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>使用者列表</title>
<link id="style_main" rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/default.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/common.js"></script>
<style type="text/css">
.table_browse th {
	font-size: 16px;
}

.table_browse td {
	font-size: 16px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {

		//registerListForm("listForm", true); // 含排序的樣式調整
	});
<%--function confirmDel(id) {
		var r = confirm("是否確定要刪除使用者「" + id + "」？");

		if (r == true) {
			// do delete...
			ajaxPost({
				url : "userinfo_delete.action",
				data : "userinfo_id=" + id,
				success : function(msg) {
					// myAlert(msg);
					location.reload();
				}
			});
		}
	}--%>
	
</script>
</head>
<body>
	<%@include file="/WEB-INF/jsp/layout/header.jsp"%>
	<%@include file="/WEB-INF/jsp/layout/menu.jsp"%>

	<%-- List區塊 --%>
	<div id="contaner">
		<div id="contaner_box">
			<!--<s:form action="apply.customer.delete" method="GET">-->
			<!--<s:hidden name="p" />
				<s:hidden name="sortBy" />
				<s:hidden name="asc" />-->
			<div class="pageTitle">查詢資料列表</div>
			<div style="width: 700px;">
				<div class="content_box">
					<!--列表上方區塊 start -->
					<div class="table_browse_top">
						<a class="button_02" href="crud/apply.customer.query.action"><span>新增</span></a>
					</div>
					<!--列表上方區塊 end -->

					<!--表格內容 start -->
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="table_browse">
						<thead>
						<tr>
							<th class="th_first"><a name="sortable" id="id"><span>用戶代碼</span></a></th>
							<th><a name="sortable" id="name"><span>用戶名稱</span></a></th>
							<th width="90"><a name="sortable" id="tel"><span>聯絡人</span></a></th>
							<th width="80"><a name="sortable" id="role"><span>建立者ID</span></a></th>
							<th width="80"><a name="sortable" id="role"><span>最後修改者</span></a></th>
							<th width="100"><span>&nbsp;</span></th>
						</tr>
						</thead>
						<tbody>
						<c:forEach var="item" items="${ds.results}" varStatus="status">
							<c:set var="editPage">
								<s:url namespace="/crud" action="apply.customer.query">
									<s:param name="entity.serNo">${item.serNo}</s:param>
								</s:url>
							</c:set>
							<tr>
								<td align="left" class="td_first" nowrap>${item.engName }</td>
								<td align="left" nowrap>${item.name }&nbsp;</td>
								<td align="left" nowrap>${item.contactUserName }&nbsp;</td>
								<td align="left" nowrap>${item.cUid }&nbsp;</td>
								<td align="left" nowrap>${item.uUid }&nbsp;</td>
								<td align="center" nowrap><a class="button_02"
									href="${editPage}"><span>修改</span></a></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<!--表格內容 end -->

					<!--分頁 start -->
					<div class="table_browse_pages">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="left"><s:iterator value="pages" var="page">
										<s:if test="#page==currentPage">
											<span class="pages_a_site">${page}</span>
										</s:if>
										<s:else>
											<a class="pages_a" href="javascript:goPage(${page});"><span>${page}</span></a>
										</s:else>
									</s:iterator></td>
								<td align="right">共<span class="total_num">${pager.totalRecord}</span>個使用者
								</td>
							</tr>
						</table>
					</div>
					<!--分頁 end -->
				</div>
			</div>
			<!--</s:form>-->
		</div>
	</div>
	<!--<table class="table table-hover" style="width: 100%">
		<thead>
			<tr class="list-head">
				<th>使用者代號</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${ds.results}" varStatus="status">
				<tr>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>-->
</body>
</html>