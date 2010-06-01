<%@ tag language="java" pageEncoding="utf-8" %>
<%@ attribute name="item" required="true" type="com.huateng.sumer.runtime.web.meta.MenuDefinition" description="需要显示的菜单对象"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:if test="${not empty menu.submenus}">
	<div dojoType="dijit.PopupMenuItem" >
		<span>${item.label}</span>
		<c:forEach items="${item.submenus}" var="sub">
			<sumer:menu-item item="${sub}"/>
		</c:forEach>
	</div>
</c:if>
<c:if test="${empty menu.submenus}">
	<spring:url value="${item.url}" var="href"/>
	<div dojoType="dijit.MenuItem" onClick="top.window.location='${href}'"><span>${item.label}</span></div>
</c:if>