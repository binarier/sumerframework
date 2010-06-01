<%@ tag language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="path" type="java.lang.String" required="true"%>

<spring:nestedPath path="${path}">
	<!-- 取数据 -->
	<spring:bind path="currentPage">
		<c:set var="currentPage_status" value="${status}"/>
	</spring:bind>
	<spring:bind path="pageCount">
		<c:set var="pageCount_status" value="${status}"/>
	</spring:bind>
	<spring:bind path="pageSize">
		<c:set var="pageSize_status" value="${status}"/>
	</spring:bind>
	<spring:bind path="pageEvent">
		<c:set var="pageEvent_status" value="${status}"/>
	</spring:bind>
	
	<!-- 生成界面 -->
	<c:out value="当前${currentPage_status.value + 1}"/>
	<c:if test="${pageCount_status.value != -1}">
		<c:out value="/${pageCount_status.value}"/>
	</c:if>
	<c:out value="页"/>
	<c:if test="${currentPage_status.value gt 0}">
		<a href="${flowExecutionUrl}&amp;_eventId=${pageEvent_status.value}&amp;${currentPage_status.expression}=0">首页</a>
		<a href="${flowExecutionUrl}&amp;_eventId=${pageEvent_status.value}&amp;${currentPage_status.expression}=${currentPage_status.value-1}">上一页</a>
	</c:if>
	<c:if test="${pageCount_status.value eq -1 or currentPage_status.value lt pageCount_status.value - 1}">
		<a href="${flowExecutionUrl}&amp;_eventId=${pageEvent_status.value}&amp;${currentPage_status.expression}=${currentPage_status.value+1}">下一页</a>
	</c:if>
	<c:if test="${pageCount_status.value != -1 and currentPage_status.value lt pageCount_status.value - 1}">
		<a href="${flowExecutionUrl}&amp;_eventId=${pageEvent_status.value}&amp;${currentPage_status.expression}=${pageCount_status.value-1}">末页</a>
	</c:if>
</spring:nestedPath>
