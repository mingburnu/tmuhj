<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
var saveForm = "";
var updateForm = "";
$(document).ready(function() {
	saveForm = $("form#apply_ipRange_save").html();
	updateForm = $("form#apply_ipRange_update").html();
});

	//重設所有欄位(清空)
	function resetData() {
		$("form#apply_ipRange_save").html(saveForm);
		$("form#apply_ipRange_update").html(updateForm);
	}

	//遞交表單
	function submitData() {
		closeDetail_2();
		if ($("form#apply_ipRange_save").length != 0) {
			var data = $('form#apply_ipRange_save').serialize();
			goDetail_2("<c:url value = '/'/>crud/apply.ipRange.save.action?entity.customer.serNo=${entity.customer.serNo}",
					'客戶-IP Range新增', data);
		} else {
			var data = $('form#apply_ipRange_update').serialize();
			goDetail_2("<c:url value = '/'/>crud/apply.ipRange.update.action?entity.serNo=${entity.serNo}&entity.customer.serNo=${entity.customer.serNo}&entity.listNo=${entity.listNo}",
					'客戶-IP Range修改', data);
		}
	}

</script>
</head>
<body>
	<c:choose>
		<c:when test="${empty entity.serNo }">
			<s:form namespace="/crud" action="apply.ipRange.save">
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">用戶名稱<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.ipRangeStart"
								cssClass="input_text" />~<s:textfield name="entity.ipRangeEnd"
								cssClass="input_text" /></td>
					</tr>
				</table>
				<div class="button_box">
					<div class="detail-func-button">
						<a class="state-default" onclick="closeDetail_2();">取消</a> &nbsp;<a
							class="state-default" onclick="resetData();">重設</a>&nbsp; <a
							class="state-default" onclick="submitData();">確認</a>
					</div>
				</div>
				<div class="detail_note">
					<div class="detail_note_title">Note</div>
					<div class="detail_note_content">
						<span class="required">(&#8226;)</span>為必填欄位
					</div>

				</div>
			</s:form>
		</c:when>
		<c:otherwise>
			<s:form namespace="/crud" action="apply.ipRange.update">
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">ID</th>
						<td>${entity.listNo}</td>
					</tr>
					<tr>
						<th width="130">用戶名稱<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.ipRangeStart"
								cssClass="input_text" />~<s:textfield name="entity.ipRangeEnd"
								cssClass="input_text" /></td>
					</tr>
				</table>
				<div class="button_box">
					<div class="detail-func-button">
						<a class="state-default" onclick="closeDetail_2();">取消</a> &nbsp;<a
							class="state-default" onclick="resetData();">重設</a>&nbsp; <a
							class="state-default" onclick="submitData();">確認</a>
					</div>
				</div>
				<div class="detail_note">
					<div class="detail_note_title">Note</div>
					<div class="detail_note_content">
						<span class="required">(&#8226;)</span>為必填欄位
					</div>
				</div>
			</s:form>
		</c:otherwise>
	</c:choose>
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