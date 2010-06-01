<%@ tag language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags"%>

<%@ attribute name="field" type="com.huateng.sumer.runtime.web.meta.AbstractField" required="true"%>
<%@ attribute name="readOnly" type="java.lang.Boolean" required="false"%>
<%@ attribute name="reference" type="java.lang.Object" %>
<%--用于判断转发--%>
<c:set var="field_type" value="${field.class.simpleName}"/>
<c:choose>
	<c:when test="${field_type eq 'LabelField'}">
		<%-- 标签字段 --%>
		<sumer:label-field field="${field}"/>
	</c:when>
	<c:when test="${field_type eq 'TextField'}">
		<%-- 文本字段 --%>
		<sumer:text-field field="${field}" readOnly="${readOnly}"/>
	</c:when>
	<c:when test="${field_type eq 'ButtonField'}">
		<%-- 按钮字段 --%>
		<sumer:button-field button="${field}" readOnly="${readOnly}"/>
	</c:when>
	<c:when test="${field_type eq 'NumberField'}">
		<%-- 数字字段 --%>
		<sumer:number-field field="${field}" readOnly="${readOnly}"/>
	</c:when>
	<c:when test="${field_type eq 'DateField'}">
		<%-- 日期字段 --%>
		<sumer:date-field field="${field}" readOnly="${readOnly}"/>
	</c:when>
	<c:when test="${field_type eq 'SelectField'}">
		<%-- 下拉框 --%>
		<sumer:select-field field="${field}" readOnly="${readOnly}" reference="${reference}"/>
	</c:when>
</c:choose>	