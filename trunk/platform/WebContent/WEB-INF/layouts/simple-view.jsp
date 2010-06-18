<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags" %>
<c:forEach items="${sumer_forms}" var="form">
	<sumer:form modelName="${sumer_model_name}" form="${form}"/>
</c:forEach>
