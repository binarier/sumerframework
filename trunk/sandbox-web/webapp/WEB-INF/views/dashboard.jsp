<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="dashboard">
    <script type="text/javascript">
        dojo.require("dijit.form.Button");
        dojo.require("dijit.Dialog");
        dojo.require("dijit.layout.TabContainer");
        dojo.require("dijit.layout.ContentPane");
    </script>
    <div id="center" dojoType="dijit.layout.TabContainer">
        <div id="shortcut" dojoType="dijit.layout.ContentPane" title="快速通道">
        	<ul>
        		<c:forEach items="${sumer_dashboard_shortcuts}" var="shortcut" varStatus="status">
			        <spring:url value="${shortcut.url}" var="href"/>
		            <spring:url value="/images/dashboard/${shortcut.name}" var="img"/>
			        <li>
			          	<div class="area">
				          	<div class="link">
				           		<a href="${href}">
				           			<img src="${img}" alt="${shortcut.label}">
				           		</a>
				           	</div>
				           	<div class="link">
								<a href="${href}"><c:out value="${shortcut.label}"/></a>
				           	</div>
			           	</div>
			        </li>
		        </c:forEach>
	        </ul>
        </div>
	</div>
	<div id="right">
		<div id="user" dojoType="dijit.TitlePane" title="用户信息">
			当前用户：<sec:authentication property="principal.username"/>
		</div>
		<div>
		   	<script type="text/javascript">
				dojo.require("dijit.dijit"); // loads the optimized dijit layer
				dojo.require("dijit._Calendar");
			</script>
			<div dojoType="dijit._Calendar" id="calendar"></div>
		</div>
	</div>
</div>