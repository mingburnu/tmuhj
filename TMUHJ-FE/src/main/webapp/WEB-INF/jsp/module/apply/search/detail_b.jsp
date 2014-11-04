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
<div class="detail">

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_03">
  <tr>
    <td class="t_01">題名</td>
    <td class="t_02">${ebook.bookName}</td>
  </tr>
  <tr>
    <td class="t_01">作者</td>
    <td class="t_02">${ebook.autherName}, ${ebook.authers}</td>
  </tr>
  <tr>
    <td class="t_01">出版社</td>
    <td class="t_02">${ebook.publishName}</td>
  </tr>
  <tr>
    <td class="t_01">內容描述</td>
    <td class="t_02">-</td>
  </tr>
  <tr>
    <td class="t_01">版本</td>
    <td class="t_02">${ebook.version}</td>
  </tr>
  <tr>
    <td class="t_01">ISBN</td>
    <td class="t_02">${ebook.isbn}</td>
  </tr>
  <tr>
    <td class="t_01">類型</td>
    <td class="t_02">${ebook.style}</td>
  </tr>
  <tr>
    <td class="t_01">出版地</td>
    <td class="t_02">${ebook.publication}台灣</td>
  </tr>
  <tr>
    <td class="t_01">出版日期</td>
    <td class="t_02">${ebook.pubDate}</td>
  </tr>
  <tr>
    <td class="t_01">語文</td>
    <td class="t_02">${ebook.languages}</td>
  </tr>
  <tr>
    <td class="t_01">美國國家<BR />圖書館分類</td>
    <td class="t_02">${ebook.bookInfoIntegral}</td>
  </tr>
</table>

<div align="center"><a class="btn_01" href="javascript:history.go(-1);">回 上 一 頁</a></div>

</div>
<!-- 內容結束 -->
	</div>
</div>

<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />

</div>
</body>
</html>