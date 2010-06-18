<%@ tag language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="field" type="com.huateng.sumer.runtime.web.meta.TextField" required="true"%>
<%@ attribute name="readOnly" type="java.lang.Boolean" required="false"%>
<spring:bind path="${field.path}">
	<c:out value="${field.prefix}"/>
	<c:choose>
		<c:when test="${field.readOnly or readOnly}">
			<c:out value="${status.value}"/>
		</c:when>
		<c:otherwise>
				<input type="text" id="${prefix}_${status.expression}" name="${status.expression}" value="${status.value}" size="${field.size}" maxlength="${field.maxLength}" 
					dojoType="dijit.form.ValidationTextBox" promptMessage="请输入${field.label}" required="${field.mandatory}"/>
		</c:otherwise>
	</c:choose>
	<c:out value="${field.suffix}"/>
	<!-- 字段校验错误信息 -->
	<c:if test="${status.error}">
		<div style="color:red">
			${status.errorMessages[0]}
		</div>
	</c:if>
</spring:bind>
