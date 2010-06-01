<%@ tag language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="field" type="com.huateng.sumer.runtime.web.meta.NumberField" required="true"%>
<%@ attribute name="readOnly" type="java.lang.Boolean" required="false"%>

<spring:bind path="${field.path}">
	<c:choose>
		<c:when test="${field.readOnly or readOnly}">
			<fmt:formatNumber type="number" value="${status.value}" maxFractionDigits="${field.fraction}" pattern="${field.pattern}"/>
		</c:when>
		<c:otherwise>
			
			<input type="text" id="${prefix}_${status.expression}" name="${status.expression}" value="${status.value}" maxlength="${field.maxLength}"
				dojoType="dijit.form.NumberTextBox" promptMessage="请输入${field.label}" required="${field.mandatory}"
			/>
		</c:otherwise>
	</c:choose>
</spring:bind>