<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sumer" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html dir="ltr">
<head>
	<spring:url value="/images/huateng.ico" var="favicon" />
	<spring:url value="/css/default.css" var="main_css_url"/>
	<spring:url var="logo" value="/images/huateng-banner-logo.gif" />
	<spring:url var="dojo_url" value="/dojo" />
	<spring:url var="spinrg_js_url" value="/Spring.js.uncompressed.js" />
	<spring:url var="spring_dojo_url" value="/Spring-Dojo.js.uncompressed.js" />

	<link rel="SHORTCUT ICON" href="${favicon}" /> 
	<link rel="stylesheet" type="text/css" href="${dojo_url}/dijit/themes/tundra/tundra.css" />
	<link rel="stylesheet" type="text/css" href="${main_css_url}"/>	
	<script type="text/javascript" src="${dojo_url}/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
	<script type="text/javascript" src="${spinrg_js_url}"></script>
	<script type="text/javascript" src="${spring_dojo_url}"></script>
	<script type="text/javascript">
		dojo.require("dojo._base.query");
		dojo.require("dijit.MenuBar");
		dojo.require("dijit.PopupMenuBarItem");
		dojo.require("dijit.Menu");
		dojo.require("dijit.MenuItem");
		dojo.require("dijit.PopupMenuItem");
		dojo.require("dijit.TitlePane");
		dojo.require("dijit.layout.StackContainer");
		dojo.require("dijit.form.ValidationTextBox");
		dojo.require("dijit.form.Button");
		dojo.require("dijit.form.DateTextBox");
		dojo.require("dijit.form.NumberTextBox");
	</script>
	<title>${sumer_title}</title>
</head>

<body class=" tundra ">
	<div id="wrapper">
		<div id="banner"><img src="${logo}"/></div>
		<div dojoType="dijit.MenuBar" id="navigator" layoutAlign="left">
			<c:forEach items="${sumer_navigator}" var="menu">
				<div dojoType="dijit.PopupMenuBarItem">
					<span>${menu.label}</span>
					<div dojoType="dijit.Menu">
						<c:forEach items="${menu.submenus}" var="item">
							<sumer:menu-item item="${item}"/>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
		</div>
		<div dojoType="dijit.TitlePane" title="menu" id="menu" closable="false">模块菜单</div>
		<div id="main">
    		<tiles:insertAttribute name="body" />
		</div>
		
		<div id="footer">
			Version:0.1.0
		</div>
	</div>
</body>

</html>