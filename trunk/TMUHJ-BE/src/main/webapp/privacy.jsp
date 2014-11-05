<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/jsp/layout/css.jsp" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>跨司署電子資料庫索引查詢平台</title>
<script type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//
	});
</script>
</head>

<body>
	<c:if test="${empty login.userId}">
		<%
			response.sendRedirect(request.getContextPath()
						+ "/authorization/userEntry.action");
		%>
	</c:if>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/jsp/layout/menu.jsp" />
		<div id="container">
			<div id="main_b_box">
				<!-- 內容開始 -->
				<div class="privacy">
					<p>
						<strong>隱私權保護政策的適用範圍</strong> <br />
						隱私權保護政策內容，包括本網站如何處理在您使用網站服務時收集到的個人識別資料。 <br /> <br />
						隱私權保護政策不適用於本網站以外的相關連結網站 ，也不適用於非本網站所委託或參與管理的人員。<br /> <br />
					</p>
					<p>
						<strong>資料的蒐集與使用方式</strong> <br />
						為了在本網站上提供您最佳的互動性服務，可能會請您提供相關個人的資料，其範圍如下： <br />
						本網站在您使用服務信箱、問卷調查等互動性功能時，會保留您所提供的姓名、電子郵件地址、連絡方式及使用時間等。 <br /> <br />
						於一般瀏覽時，伺服器會自行記錄相關行徑，包括您使用連線設備的IP位址、使用時間、使用的瀏覽器、瀏覽及點選資料記錄等，做為我們增進網站服務的參考依據，此記錄為內部應用，決不對外公佈。 <br />
						<br />
						為提供精確的服務，我們會將收集的問卷調查內容進行統計與分析，分析結果之統計數據或說明文字呈現，除供內部研究外，我們會視需要公佈統計數據及說明文字，但不涉及特定個人之資料。 <br />
						<br /> 除非取得您的同意或其他法令之特別規定，本網站絕不會將您的個人資料揭露予第三人或使用於蒐集目的以外之其他用途。<br />
						<br />
					</p>
					<p>
						<strong>資料之保護</strong><br />
						本網站主機均設有防火牆、防毒系統等相關的各項資訊安全設備及必要的安全防護措施，加以保護網站及您的個人資料採用嚴格的保護措施，只由經過授權的人員才能接觸您的個人資料，相關處理人員皆簽有保密合約，如有違反保密義務者，將會受到相關的法律處分。 <br />
						<br />
						如因業務需要有必要委託本部相關單位提供服務時，本網站亦會嚴格要求其遵守保密義務，並且採取必要檢查程序以確定其將確實遵守。 <br />
						<br /> <br />
					</p>
					<p>
						<strong>網站對外的相關連結 </strong><br />
						本網站的網頁提供其他網站的網路連結，您也可經由本網站所提供的連結，點選進入其他網站。但該連結網站不適用本網站的隱私權保護政策，您必須參考該連結網站中的隱私權保護政策。 <br />
						<br />
					</p>
					<p>
						<strong>Cookie之使用</strong><br />
						為了提供您最佳的服務，本網站會在您的電腦中放置並取用我們的Cookie，若您不願接受Cookie的寫入，您可在您使用的瀏覽器功能項中設定隱私權等級為高，即可拒絕Cookie的寫入，但可能會導至網站某些功能無法正常執行
						。 <br /> <br />
					</p>
					<p>
						<strong>隱私權保護政策之修正 </strong><br />
						本網站隱私權保護政策將因應需求隨時進行修正，修正後的條款將刊登於網站上。
					</p>
				</div>

				<!-- 內容結束 -->
			</div>
		</div>
		<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
	</div>
</body>
</html>
