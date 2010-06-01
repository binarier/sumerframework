<%@ tag language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="layout" type="com.huateng.sumer.runtime.web.meta.PanelLayout" required="true"%>
<%@ attribute name="prefix" type="java.lang.String" required="false"%>

<div class="panel_layout">
	<h1>${layout.caption}</h1>
	<c:set var="cells" value="${layout.extractedCells}"/>
	<c:if test="${not empty cells}">
		<spring:nestedPath path="${layout.nestedPath}">
			<table>
				<c:forEach var="row" items="${cells}">
					<tr>
						<c:forEach var="cell" items="${row}">
							<td colspan="${cell.field.colSpan}" width="${cell.width}">
								<sumer:field field="${cell.field}" readOnly="layout.readOnly" reference="${sumer_reference[layout][cell.field]}"/>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>          
		</spring:nestedPath>
	</c:if>	
</div>
