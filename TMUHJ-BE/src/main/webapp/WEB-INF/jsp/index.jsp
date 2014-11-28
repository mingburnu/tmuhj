<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>跨司署電子資料庫索引查詢平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link href="<c:url value = '/'/>resources/css/air-ui-cms.css"
	rel="stylesheet" type="text/css" />
<link href="<c:url value = '/'/>resources/css/jquery.autocomplete.css"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value = '/'/>resources/css/smoothness/jquery-ui-1.7.2.custom.css"
	rel="stylesheet" type="text/css" />
<link href="<c:url value = '/'/>resources/css/jquery.datepick.css"
	rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery-1.3.2.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery-ui-1.7.2.custom.min.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery.bgiframe.pack.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/common.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery.form.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery.autocomplete.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery.datepick.js">
	
</script>
<script type="text/javascript">
	$(document).ready(function() {
		showMenuItems('1');
	});
</script>
<%--<script language="javascript" type="text/javascript">
	$(document).ready(function() {
		showTabsContain("A");
	});

	function showTabsContain(argTarget) {
		var arr = new Array();
		arr[arr.length] = "A";
		arr[arr.length] = "B";
		arr[arr.length] = "C";
		for (var i = 0; i < arr.length; i++) {
			if (argTarget == arr[i]) {
				currentTab = argTarget;
				$("#TabsContain_" + arr[i]).show();
				$("#tabs-items_" + arr[i]).removeClass("tabs-items");
				$("#tabs-items_" + arr[i] + " span").removeClass(
						"tabs-items-span");
				$("#tabs-items_" + arr[i]).addClass("tabs-items-hover");
				$("#tabs-items_" + arr[i] + " span").addClass(
						"tabs-items-hover-span");
			} else {
				$("#TabsContain_" + arr[i]).hide();
				$("#tabs-items_" + arr[i]).removeClass("tabs-items-hover");
				$("#tabs-items_" + arr[i] + " span").removeClass(
						"tabs-items-hover-span");
				$("#tabs-items_" + arr[i]).addClass("tabs-items");
				$("#tabs-items_" + arr[i] + " span")
						.addClass("tabs-items-span");
			}
		}
	}
	//打開Alert畫面之函式
	function goAlert(argTitle, argMsg, argBtnFunc) {
		$("#div_Alert").show();
		UI_Resize();
		$(window).scrollTop(0);
		$("#div_Alert .content > .header > .title").html(argTitle);
		$("#div_Alert .content > .contain").html(argMsg);
		$("#div_Alert .content > .func-button").empty();
		if (argBtnFunc) {
			if (argBtnFunc.trueText && argBtnFunc.trueFunc) {
				$("#div_Alert .content > .func-button").append(
						'<a id="true_btn" class="state-default" onclick="closeAlert();">'
								+ argBtnFunc.trueText + '</a>&nbsp;&nbsp;');
				$("#div_Alert .content > .func-button > #true_btn").bind(
						'click', argBtnFunc.trueFunc);
			}
			if (argBtnFunc.falseText && argBtnFunc.falseFunc) {
				$("#div_Alert .content > .func-button").append(
						'<a id="false_btn" class="state-default" onclick="closeAlert();">'
								+ argBtnFunc.falseText + '</a>&nbsp;&nbsp;');
				$("#div_Alert .content > .func-button > #false_btn").bind(
						'click', argBtnFunc.falseFunc);
			}
		} else {
			$("#div_Alert .content > .func-button")
					.append(
							'<a class="state-default" onclick="closeAlert();"><s:text name="button.close"/></a>');
		}
	}
</script>--%>
</head>
<body>
	<div id="div-wrapper">
		<jsp:include page="/WEB-INF/jsp/layout/header.jsp" />
		<div id="div-middle">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td align="left" valign="top" width="175"><jsp:include
								page="/WEB-INF/jsp/layout/menu.jsp" /></td>
						<td align="left" valign="top">
							<div id="div-contain">
								<div class="tabs-box">
									<div>
										<a id="tabs-items_A" class="tabs-items-hover"
											onclick="showTabsContain('A')"><span
											class="tabs-items-hover-span">系統公告</span></a>
									</div>
								</div>
								<div id="div_nav">
									目前位置：<span>系統公告</span>
								</div>
								<div class="list-box">
									<table cellspacing="1" class="list-table">
										<tbody>
											<tr>
												<td>
													<table width="100%" border="0" cellspacing="2"
														cellpadding="0">
														<tbody>
															<tr>
																<td align="center" valign="top" width="40">
																	<div class="cal_cal">
																		<div class="cal_month">03月</div>
																		<div class="cal_day">24</div>
																	</div>
																</td>
																<td align="left" valign="top">
																	<div class="cal_title">系統上線</div>
																	<div class="cal_content">歡迎蒞臨!</div>
																	<div class="cal_time">發佈時間:2010年03月24日 PM 12:00</div>
																</td>
															</tr>
														</tbody>
													</table>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
	</div>
</body>
</html>