<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
//刪除後引導頁面
$(document).ready(function() {
	if($("table.list-table:eq(1) tbody tr").length==2&&$("form#apply_ipRange_list input#listForm_currentPageHeader").val()>1){
		 gotoPage_detail($("form#apply_ipRange_list input#listForm_currentPageHeader").val()-1);
	};
});
	
	//新增IP Range
	function goAdd_detail() {
		var url = "<c:url value = '/'/>/crud/apply.ipRange.query.action";
		var data ='entity.cusSerNo='+'<%=request.getParameter("entity.cusSerNo")%>';
		goDetail_2(url, 'IP Range管理-新增', data);
	}

	//IP Range編輯
	function goUpdate_detail(listNo,serNo) {
		var url = "<c:url value = '/'/>crud/apply.ipRange.query.action";
		var data = 'entity.serNo=' + serNo +'&entity.listNo='+listNo;
		goDetail_2(url, 'IP Range管理-修改', data);
	}

	//單筆刪除
	function goDel_detail(serNo) {
		 var f = {
			        trueText:'是',
			        trueFunc:function(){
			            var url = "<c:url value = '/'/>crud/apply.ipRange.delete.action";
			            var data = $('#apply_ipRange_list').serialize()+'&entity.serNo='+serNo+'&pager.offset='+'${ds.pager.offset}'+'&pager.currentPage='+'${ds.pager.currentPage}'+'&pager.offsetPoint'+'${ds.pager.offset}';
			            goDetail_Main(url,'',data);
			        },
			        falseText:'否',
			        falseFunc:function(){
			            //不進行刪除...
			        }
			    };
		goAlert('提醒', '確定要刪除此筆資料嗎?', f);
	}

	//GoPage
	function gotoPage_detail(page) {
		var totalPage = $("span.totalNum:eq(1)").html();
		var recordPerPage = "${ds.pager.recordPerPage}";
		var offset = parseInt(recordPerPage) * (parseInt(page) - 1);
		if (parseInt(page) < 1) {
			page = 1;
		} else if (parseInt(page) > parseInt(totalPage)) {
			page = totalPage;
		}
		goDetail_Main('<c:url value = '/'/>crud/apply.ipRange.list.action',
				'#apply_ipRange_list', '&pager.offset='+offset+'&pager.currentPage='+page+'&pager.offsetPoint'+offset);
	}

	//變更顯示筆數
	function chagePageSize_detail(recordPerPage,recordPoint) {
		goDetail_Main('<c:url value = '/'/>crud/apply.ipRange.list.action',
				'#apply_ipRange_list', '&recordPerPage='+recordPerPage+'&recordPoint='+recordPoint);
	}
</script>
</head>
<body>
	<s:form action="apply.ipRange.list" namespace="/crud" method="post">
		<input type="hidden" name="entity.cusSerNo" value="<%=request.getParameter("entity.cusSerNo")%>"/>
		<div class="list-box">
			<div class="list-buttons">
				<a class="state-default" onclick="goAdd_detail();">新增</a>
			</div>
			<table cellspacing="1" class="list-table">
				<tbody>
					<tr>
						<td colspan="4" class="topic">IP Range管理</td>
					</tr>
					<tr>
						<th>NO.</th>
						<th>IP Range</th>
						<th>操作</th>
					</tr>
					<c:forEach var="item" items="${ds.results}" varStatus="status">
						<c:set var="orderInt" scope="session"
							value="${ds.pager.offset+(status.index+1)}" />
						<tr>
							<td align="center">${orderInt}</td>
							<td align="center">${item.ipRangeStart}~${item.ipRangeEnd}</td>
							<td align="center"><a class="state-default2"
								onclick="goUpdate_detail(${orderInt},${item.serNo});"><span
									class="icon-default icon-edit"></span>修改</a> <a
								class="state-default2" onclick="goDel_detail(${item.serNo});"><span
									class="icon-default icon-delete"></span>刪除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="page-box" align="right">
				<table border="0" cellspacing="0" cellpadding="0">
					<tbody>
						<tr>
							<td align="left" class="p_02"><jsp:include page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.ipRange.list" />
										<jsp:param name="pager" value="${ds.pager}" />
										<jsp:param name="recordPerPage"
											value="${ds.pager.recordPerPage}" />
										<jsp:param name="detail" value="1" />
									</jsp:include></td>
							<td><c:set var="pageFactor"
										value="${ds.pager.totalRecord/ds.pager.recordPerPage}" /> <c:set
										var="totalPage">
										<fmt:formatNumber type="number" pattern="#"
											value="${pageFactor+(1-(pageFactor%1))%1}" />
									</c:set> 每頁顯示 <select name="recordPerPage" id="listForm_pageSize"
									onchange="chagePageSize_detail(this.value,${ds.pager.recordPoint })">
										<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
										<option value="5">5</option>
										<option value="10">10</option>
										<option value="20">20</option>
										<option value="50">50</option>
								</select> 筆紀錄, 第 <input id="listForm_currentPageHeader"
									value="${ds.pager.currentPage }" type="number" min="1"
									max="${totalPage }" onchange="gotoPage_detail(this.value)"> 頁,
									共<span class="totalNum">${totalPage }</span>頁</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="detail_note">
				<div class="detail_note_title">Note</div>
				<div class="detail_note_content"></div>
			</div>
		</div>
	</s:form>

	<s:if test="hasActionMessages()">
		<script language="javascript" type="text/javascript">
			var msg = "";
			<s:iterator value="actionMessages">
			msg += '<s:property escape="false"/><br>';
			</s:iterator>;
			goAlert('訊息', msg);
		</script>
	</s:if>
	<s:if test="hasActionErrors()">
		<script language="javascript" type="text/javascript">
			var msg = "";
			<s:iterator value="actionErrors">
			msg += '<s:property escape="false"/><br>';
			</s:iterator>;
			goAlert('訊息', msg);
		</script>
	</s:if>
</body>
</html>