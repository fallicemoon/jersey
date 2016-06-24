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

<title>新增進貨項目</title>
</head>
<body>
<c:import url="/header.jsp"/>
	<br/><br/>
	<c:forEach items="${sessionScope.errors}" var="error">
		<p style="color: red">${error}</p>
	</c:forEach>
	<c:remove var="errors" scope="session"/>
	
	<form action="/jersey/PurchaseCaseServlet" method="post" class="form-horizontal">
    <div class="form-group">
	<label for="inputEmail3" class="col-sm-2 control-label">商品編號/商品名稱：</label>
	    	<div class="col-sm-10">
	    	<a href="/jersey/purchaseCase/addCommodity.jsp?purchaseCaseId=0"><button type="button" class="btn btn-warning">匯入</button></a>
	    	<c:forEach items="${sessionScope.commodityIds}" var="commodityId">
	    		<jsp:include page="/CommodityServlet">
	    			<jsp:param value="getOne" name="action"/>
	    			<jsp:param value="${commodityId}" name="commodityId"/>
	    		</jsp:include>
	    	<span>${commodityId} - ${commodity.itemName} / </span>
	    	</c:forEach>
	    	</div>
	</div>
	
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">商家名稱：</label>
    	<div class="col-sm-10">
    	<jsp:include page="/StoreServlet">
    		<jsp:param value="getStores" name="action"/>
    	</jsp:include>
    	<select name="store">
    		<c:forEach items="${requestScope.stores}" var="vo">
    		<option value="${vo.storeId}">${vo.name}</option>
    		</c:forEach>
    	</select>
    	</div>
    </div>
        
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">進度：</label>
    	<div class="col-sm-10">
    	<select name="progress">
    		<option value="下單付款">下單付款</option>
    		<option value="商家出貨">商家出貨</option>
    		<option value="缺貨未退款">缺貨未退款</option>
    		<option value="缺貨退款">缺貨退款</option>
    		<option value="已達代運">已達代運</option>
    		<option value="代運寄回">代運寄回</option>
    		<option value="進貨完成">進貨完成</option>
    	</select>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">託運公司：</label>
    	<div class="col-sm-10">
    	<jsp:include page="/StoreServlet">
    		<jsp:param value="getShippingCompanys" name="action"/>
    	</jsp:include>
    	<select name="shippingCompany">
    		<c:forEach items="${requestScope.shippingCompanys}" var="vo">
    		<option value="${vo.storeId}">${vo.name}</option>
    		</c:forEach>
    	</select>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Tracking number：</label>
    	<div class="col-sm-10">
    	<input type="text" name="trackingNumber">
    	</div>
    </div>

    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Tracking number link：</label>
    	<div class="col-sm-10">
    	<input type="text" name="trackingNumberLink" value="${requestScope.purchaseCase.trackingNumberLink}">
    	</div>
    </div>
        
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">代運人：</label>
    	<div class="col-sm-10">
    	<input type="text" name="agent">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">代運Tracking number：</label>
    	<div class="col-sm-10">
    	<input type="text" name="agentTrackingNumber">
    	</div>
    </div>

    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">代運Tracking number link：</label>
    	<div class="col-sm-10">
    	<input type="text" name="agentTrackingNumberLink">
    	</div>
    </div>
        
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">成本：</label>
    	<div class="col-sm-10">
    	<input type="text" name="cost" value="0">請輸入數字!
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">國際運費：</label>
    	<div class="col-sm-10">
    	<input type="text" name="agentCost" value="0">請輸入數字!
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
          <input type="checkbox" name="isAbroad" value="true"> 是否為代購case
        </label>
      </div>
    </div>
  	</div>
	

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