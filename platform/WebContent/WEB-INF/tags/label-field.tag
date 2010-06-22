<%@ tag language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="field" type="com.huateng.sumer.runtime.web.meta.LabelField" required="true"%>
<c:out value="${field.label}" escapeXml="${field.escapeXml}"/>
<c:if test="${field.mandatoryMark}">
	<span class="mandatory">*</span>
</c:if>