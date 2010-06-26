<%@ tag language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="field" type="com.huateng.sumer.runtime.web.meta.DateField" required="true"%>
<%@ attribute name="readOnly" type="java.lang.Boolean" required="false"%>
<spring:bind path="${field.path}">
	<c:choose>
		<c:when test="${field.readOnly or readOnly}">
			<fmt:formatDate type="${field.formatType}" dateStyle="${field.dateStyle}" timeStyle="${field.timeStyle}" value="${status.value}"/>
		</c:when>
		<c:otherwise>
			<input type="text" id="${prefix}_${status.expression}" name="${status.expression}" value="${status.value}"
				dojoType="dijit.form.DateTextBox" promptMessage="${field.prompt}" required="${field.mandatory}"	/>
		</c:otherwise>
	</c:choose>
</spring:bind>