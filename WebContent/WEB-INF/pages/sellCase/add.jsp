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

<title>新增出貨項目</title>
</head>
<body>
<c:import url="/header.jsp"/>
	<br/><br/>
	<c:forEach items="${sessionScope.errors}" var="error">
		<p style="color: red">${error}</p>
	</c:forEach>
	<c:remove var="errors" scope="session"/>
	
	<form action="/jersey/SellCaseServlet" method="post" class="form-horizontal">
	<div class="form-group">
	<label for="inputEmail3" class="col-sm-2 control-label">進貨編號/商家名稱：</label>
	    	<div class="col-sm-10">
	    	<a href="/jersey/sellCase/addPurchaseCase.jsp?sellCaseId=0"><button type="button" class="btn btn-warning">匯入</button></a>
	    	<c:forEach items="${sessionScope.purchaseCaseIds}" var="purchaseCaseId">
	    		<jsp:include page="/PurchaseCaseServlet">
	    			<jsp:param value="getOne" name="action"/>
	    			<jsp:param value="${purchaseCaseId}" name="purchaseCaseId"/>
	    		</jsp:include>
	    		<span>${purchaseCaseId} - ${purchaseCase.store} / </span>
	    	</c:forEach>
	    	</div>
	</div>
	
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">運送方式：</label>
    	<div class="col-sm-10">
    	<select name="transportMethod">
    		<option value="面交">面交</option>
    		<option value="郵局">郵局</option>
    		<option value="ezShip">ezShip</option>
    		<option value="7-11">7-11</option>
    	</select>
    	</div>
    </div>

    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">收件人：</label>
    	<div class="col-sm-10">
    	<input type="text" name="addressee">
    	</div>
    </div>

    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">手機</label>
    	<div class="col-sm-10">
    	<input type="text" name="phone">
    	</div>
    </div>
        
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">地址/店名：</label>
    	<div class="col-sm-10">
    	<input type="text" name="address">
    	</div>
    </div>
        
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Tracking number：</label>
    	<div class="col-sm-10">
    	<input type="text" name="trackingNumber">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">運費：</label>
    	<div class="col-sm-10">
    	<input type="text" name="transportCost" value="0">請輸入數字!
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">總價：</label>
    	<div class="col-sm-10">
    	<input type="text" name="income" value="0">請輸入數字!
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">已收額：</label>
    	<div class="col-sm-10">
    	<input type="text" name="collected" value="0">請輸入數字!
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">備註：</label>
    	<div class="col-sm-10">
    	<input type="text" name="description">
    	</div>
    </div>
        
    <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label>
          <input type="checkbox" name="isShipping" value="true"> 是否已出貨
        </label>
      </div>
    </div>
  	</div>
	
	<div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label>
          <input type="checkbox" name="isChecked" value="true"> 是否已查收
        </label>
      </div>
    </div>
  	</div>
  	
	<c:if test="${param.error != null}">${param.error}</c:if>
	<input type="hidden" name="action" value="create">
	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">
			<button type="submit" class="btn btn-success" >新增</button>
		</label>
	</div>
	</form>

<c:import url="/footer.jsp"></c:import>
</body>
</html>