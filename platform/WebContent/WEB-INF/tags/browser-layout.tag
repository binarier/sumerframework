<%@ tag language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="layout" type="com.huateng.sumer.runtime.web.meta.BrowserLayout" required="true"%>
<%@ attribute name="prefix" type="java.lang.String" required="false"%>

<div class="browse_layout">
	<h1>${layout.caption}</h1>
	<c:set var="headerCells" value="${layout.headerCells}"/>
	<c:set var="bodyCells" value="${layout.bodyCells}"/>
	<spring:nestedPath path="${layout.nestedPath}">
		<table>
			<!-- 表头 -->
			<thead>
				<c:forEach var="row" items="${headerCells}">
					<tr>
						<c:forEach var="cell" items="${row}" varStatus="varStatus">
							<th colspan="${cell.field.colSpan}" width="${cell.width}">
								<sumer:field field="${cell.field}" readOnly="true"/>
							</th>
						</c:forEach>
					</tr>
				</c:forEach>
			</thead>
			<!-- 数据 -->
			<tbody>
				<spring:bind path="${layout.dataPath}">
					<%--为了避免大范围bind标签，先把值存下来 --%>
					<c:set var="browser_data" value="${status.value}"/>
				</spring:bind>
				<c:forEach var="item" items="${browser_data}" varStatus="varStatus">
					<!-- 处理一条数据 -->
					<spring:nestedPath path="${layout.dataPath}[${varStatus.index}]">
						<c:forEach var="row" items="${bodyCells}">
							<tr>
								<c:forEach var="cell" items="${row}">
									<!-- 显示field -->
									<td colspan="${cell.field.colSpan}" width="${cell.width}">
										<sumer:field field="${cell.field}" readOnly="true" reference="${sumer_reference[layout][cell.field]}"/>
									</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</spring:nestedPath>
				</c:forEach>
			</tbody>
			<!-- 分页 -->
			<c:if test="${layout.paginated}">
				<tfoot>
					<tr>
						<td colspan="${layout.columns}">
							<sumer:pagination path="${layout.paginationPath}"/>
						</td>
					</tr>
				</tfoot>
			</c:if>
		</table>
	</spring:nestedPath>
</div>
