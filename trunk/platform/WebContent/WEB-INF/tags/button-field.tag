<%@ tag language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="button" type="com.huateng.sumer.runtime.web.meta.ButtonField" required="true"%>
<%@ attribute name="readOnly" type="java.lang.Boolean" required="false"%>

<input dojoType="dijit.form.Button" label="${button.label}" type="submit" id="_eventId_${button.event}" name="_eventId_${button.event}" value="${button.label}"/>
<c:if test="${button.ajax}">
<%--目前还有问题 --%>
	<script>
	Spring.addDecoration(new Spring.AjaxEventDecoration({elementId:'_eventId_${button.event}', event:'onclick',formId:'${sumer_model_name}'}));
	</script>
</c:if>