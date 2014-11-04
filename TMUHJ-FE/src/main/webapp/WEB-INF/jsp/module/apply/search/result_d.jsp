<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/WEB-INF/jsp/layout/css.jsp" />
<jsp:include page="/WEB-INF/jsp/layout/art.jsp" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>跨司署電子資料庫索引查詢平台</title>
</head>

<body>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/jsp/layout/menu.jsp" />

		<div id="container">
			<div id="main_b_box">
				<!-- 內容開始 -->
				<div class="result">


					<div class="pager">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<s:form action="apply.customer.query.action">
									<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
										筆記錄， 每頁顯示筆數 <select name="recordPerPage"
										id="apply_customer_query_action_recordPerPage"
										onchange="document.getElementById('apply_customer_query_action');this.form.submit();">
											<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
											<option value="5">5</option>
											<option value="10">10</option>
											<option value="20">20</option>
											<option value="50">50</option>
											<option value="100">100</option>
									</select> <input type="hidden" name="keywords" value="${keywords }" />

									</td>
								</s:form>
								<td align="right" class="p_02"><jsp:include
										page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.customer.query" />
										<jsp:param name="pager" value="${ds.pager}" />
										<jsp:param name="keywords" value="${keywords}" />
										<jsp:param name="recordPerPage"
											value="${ds.pager.recordPerPage}" />
									</jsp:include></td>
							</tr>
						</table>
					</div>

					<div class="list">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr valign="top">
								<th width="40">序號</th>
								<th>單位名稱 / 聯絡人</th>
								<th>電話 / E-Mail</th>
								<th>資料庫</th>
								<th>電子書</th>
								<th>期刊</th>
							</tr>
							<c:forEach var="item" items="${ds.results}" varStatus="status">
								<c:set var="num" scope="session" value="${(status.index+1)%2}" />
								<c:set var="orderInt" scope="session"
									value="${ds.pager.offset+(status.index+1)}" />
								<c:set var="ownJorunal">
									<s:url namespace="/crud" action="apply.journal.ownerJournal">
										<s:param name="cusSerNo">${item.serNo}</s:param>
									</s:url>
								</c:set>
								<c:set var="ownDb">
									<s:url namespace="/crud" action="apply.database.ownerDb">
										<s:param name="cusSerNo">${item.serNo}</s:param>
									</s:url>
								</c:set>
								<c:set var="ownEbook">
									<s:url namespace="/crud" action="apply.ebook.ownerEbook">
										<s:param name="cusSerNo">${item.serNo}</s:param>
									</s:url>
								</c:set>
								<c:choose>
									<c:when test="${num > 0}">
										<tr valign="top">
											<td>${orderInt}</td>
											<td><div>${item.name}</div>
												<div>${item.contactUserName}</div></td>
											<td><div>${item.tel}</div>
												<div>${item.email}</div></td>
											<td><a href="${ownDb}">${item.dbAmount}</a></td>
											<td><a href="${ownEbook}">${item.ebookAmount}</a></td>
											<td><a href="${ownJorunal}">${item.journalAount}</a></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr valign="top" class="odd">
											<td>${orderInt}</td>
											<td><div>${item.name}</div>
												<div>${item.contactUserName}</div></td>
											<td><div>${item.tel}</div>
												<div>${item.email}</div></td>
											<td><a href="${ownDb}">${item.dbAmount}</a></td>
											<td><a href="${ownEbook}">${item.ebookAmount}</a></td>
											<td><a href="${ownJorunal}">${item.journalAount}</a></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</table>
					</div>


					<div class="pager">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<s:form action="apply.customer.query.action">
									<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
										筆記錄， 每頁顯示筆數 <select name="recordPerPage"
										id="apply_customer_query_action_recordPerPage"
										onchange="document.getElementById('apply_customer_query_action');this.form.submit();">
											<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
											<option value="5">5</option>
											<option value="10">10</option>
											<option value="20">20</option>
											<option value="50">50</option>
											<option value="100">100</option>
									</select> <input type="hidden" name="keywords" value="${keywords }" />

									</td>
								</s:form>
								<td align="right" class="p_02"><jsp:include
										page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.customer.query" />
										<jsp:param name="pager" value="${ds.pager}" />
										<jsp:param name="keywords" value="${keywords}" />
										<jsp:param name="recordPerPage"
											value="${ds.pager.recordPerPage}" />
									</jsp:include></td>
							</tr>
						</table>
					</div>

				</div>
				<!-- 內容結束 -->
			</div>
		</div>

		<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />

	</div>
</body>
</html>