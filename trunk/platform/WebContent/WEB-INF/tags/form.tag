<%@ tag language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute  name="form" type="com.huateng.sumer.runtime.web.meta.FormDefinition" required="true"%>
<%@ attribute  name="modelName" type="java.lang.String" required="true"%>
<%@ attribute  name="reference" type="java.util.Map" required="false"%>
<%@ attribute  name="prefix" type="java.lang.String" required="false"%>
 
<form:form modelAttribute="${modelName}">
	<form:errors cssClass="errors" delimiter="&lt;p/&gt;"/>
	<c:forEach var="layout" items="${form.layouts}" varStatus="varStatus">
		<c:choose>
			<c:when test="${layout.class.simpleName eq 'PanelLayout'}">
				<sumer:panel-layout layout="${layout}" prefix="${prefix}_layout${varStatus.index}"/>
			</c:when>
			<c:when test="${layout.class.simpleName eq 'BrowserLayout'}">
				<sumer:browser-layout layout="${layout}" prefix="${prefix}_layout${varStatus.index}"/>
			</c:when>
		</c:choose>
	</c:forEach>
</form:form>
