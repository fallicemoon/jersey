<%@page import="sellCase.model.SellCaseVO"%>
<%@page import="sellCase.model.SellCaseDAO"%>
<%@page import="purchaseCase.model.PurchaseCaseVO"%>
<%@page import="java.util.List"%>
<%@page import="purchaseCase.model.PurchaseCaseDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商家</title>
</head>
<c:import url="/WEB-INF/pages/header.jsp"/>
<body>
<%-- 	<c:if test="${param.action=='updateOne'}"> --%>
<%-- 	<jsp:useBean id="store" class="store.model.StoreVO" scope="request"/> --%>
<%-- 	<jsp:setProperty property="*" name="store"/> --%>
<%-- 	<jsp:forward page="/StoreServlet"> --%>
<%-- 		<jsp:param value="update" name="action"/> --%>
<%-- 	</jsp:forward> --%>
<%-- 	</c:if> --%>
	
<%-- 	<c:if test="${param.storeId == null}"> --%>
<%-- 		<c:redirect context="/jersey" url="/store/list.jsp" /> --%>
<%-- 	</c:if> --%>
	<br/><br/>
	
<%-- 	<c:if test="${param.storeId != null}"> --%>
<%-- 		<jsp:include page="/StoreServlet"> --%>
<%-- 			<jsp:param name="action" value="getOne" /> --%>
<%-- 			<jsp:param name="storeId" value="${param.storeId}" /> --%>
<%-- 		</jsp:include> --%>
<%-- 	</c:if> --%>

	<form action="/jersey/StoreServlet" method="post" class="form-horizontal">
	<input type="hidden" name="action" value="update">
    <input type="hidden" name="storeId" value="${store.storeId}">
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">商家類別:</label>
    	<div class="col-sm-10">
    	<select name="type" class="form-control" style="width: 14%">
    		<c:if test="${store.type=='store'}">
    		<option value="store" selected="selected">商店</option>
    		<option value="shippingCompany">託運公司</option>
    		</c:if>
    		<c:if test="${store.type=='shippingCompany'}">
    		<option value="store">商店</option>
    		<option value="shippingCompany" selected="selected">託運公司</option>
    		</c:if>
    	</select>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">商家名稱：</label>
    	<div class="col-sm-10">
    	<input type="text" name="name" value="${store.name}">
    	</div>
    </div>

	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">
			<button type="submit" class="btn btn-warning" >修改</button>
		</label>
	</div>
	</form>

<c:import url="/WEB-INF/pages/footer.jsp"></c:import>
</body>
</html>