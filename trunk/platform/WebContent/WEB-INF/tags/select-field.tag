<%@ tag language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="field" type="com.huateng.sumer.runtime.web.meta.SelectField" required="true"%>
<%@ attribute name="readOnly" type="java.lang.Boolean" required="false"%>
<%@ attribute name="reference" type="java.lang.Object" %>
<c:choose>
	<c:when test="${field.readOnly or readOnly}">
		<spring:bind path="${field.path}">
			<c:out value="${reference[status.value]==null?status.value:reference[status.value]}"/>
		</spring:bind>
	</c:when>
	<c:otherwise>
		<form:select path="${field.path}">
			<form:options items="${reference}"/>
		</form:select>
	</c:otherwise>
</c:choose>
<spring:bind path="${field.path}">
	<c:if test="${status.error}">
		<div style="color:red">
			${status.errorMessages[0]}
		</div>
	</c:if>
</spring:bind>