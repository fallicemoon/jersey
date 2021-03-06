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
<title>修改進貨項目</title>
</head>
<c:import url="/WEB-INF/pages/header.jsp"/>
<body>
	<br/><br/>
	<c:forEach items="${requestScope.errors}" var="error">
		<p style="color: red">${error}</p>
	</c:forEach>
	
	<form action="/jersey/PurchaseCaseServlet" method="post" class="form-horizontal">
	<input type="hidden" name="action" value="update">

    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">進貨編號：</label>
    	<div class="col-sm-10">
    	<input type="hidden" name="purchaseCaseId" value="${purchaseCase.purchaseCaseId}">${purchaseCase.purchaseCaseId}
    	</div>
    </div>
        
    <div class="form-group">
	<label for="inputEmail3" class="col-sm-2 control-label">商品編號/商品名稱：</label>
	    <div class="col-sm-10">
	    	<c:forEach items="${purchaseCase.commoditys}" var="commodity">
	    		${commodity.commodityId}-${commodity.itemName}
	    	</c:forEach>
	    </div>
	</div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">商家名稱：</label>
    	<div class="col-sm-10">
    	<select name="store">
    		<c:forEach items="${applicationScope.store}" var="vo">
    			<c:choose>
    			<c:when test="${vo.storeId==purchaseCase.store.storeId}"><option value="${vo.storeId}" selected="selected">${vo.name}</option></c:when>
				<c:otherwise><option value="${vo.storeId}">${vo.name}</option></c:otherwise>	
    			</c:choose>
    		</c:forEach>
    	</select>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">進度：</label>
    	<div class="col-sm-10">
    	<select name="progress" >
    		<c:choose>
    		<c:when test="${purchaseCase.progress=='下單付款'}"><option value="下單付款" selected="selected">下單付款</option></c:when>
    		<c:otherwise><option value="下單付款">下單付款</option></c:otherwise>
    		</c:choose>
    		<c:choose>
    		<c:when test="${purchaseCase.progress=='商家出貨'}"><option value="商家出貨" selected="selected">商家出貨</option></c:when>
    		<c:otherwise><option value="商家出貨">商家出貨</option></c:otherwise>
    		</c:choose>
    		<c:choose>
    		<c:when test="${purchaseCase.progress=='缺貨未退款'}"><option value="缺貨未退款" selected="selected">缺貨未退款</option></c:when>
    		<c:otherwise><option value="缺貨未退款">缺貨未退款</option></c:otherwise>
    		</c:choose>
    		<c:choose>
    		<c:when test="${purchaseCase.progress=='缺貨退款'}"><option value="缺貨退款" selected="selected">缺貨退款</option></c:when>
    		<c:otherwise><option value="缺貨退款">缺貨退款</option></c:otherwise>
    		</c:choose>
    		<c:choose>
    		<c:when test="${purchaseCase.progress=='已達代運'}"><option value="已達代運" selected="selected">已達代運</option></c:when>
    		<c:otherwise><option value="已達代運">已達代運</option></c:otherwise>
    		</c:choose>
    		<c:choose>
    		<c:when test="${purchaseCase.progress=='代運寄回'}"><option value="代運寄回" selected="selected">代運寄回</option></c:when>
    		<c:otherwise><option value="代運寄回">代運寄回</option></c:otherwise>
    		</c:choose>
    		<c:choose>
    		<c:when test="${purchaseCase.progress=='進貨完成'}"><option value="進貨完成" selected="selected">進貨完成</option></c:when>
    		<c:otherwise><option value="進貨完成">進貨完成</option></c:otherwise>
    		</c:choose>
    	</select>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">託運公司：</label>
    	<div class="col-sm-10">
    	<select name="shippingCompany">
    		<c:forEach items="${applicationScope.shippingCompany}" var="vo">
    			<c:choose>
    			<c:when test="${vo.storeId==purchaseCase.shippingCompany.storeId}"><option value="${vo.storeId}" selected="selected">${vo.name}</option></c:when>
				<c:otherwise><option value="${vo.storeId}">${vo.name}</option></c:otherwise>	
    			</c:choose>
    		</c:forEach>
    	</select>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Tracking number：</label>
    	<div class="col-sm-10">
    	<input type="text" name="trackingNumber" value='<c:out value="${purchaseCase.trackingNumber}"/>'>
    	</div>
    </div>

    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Tracking number link：</label>
    	<div class="col-sm-10">
    	<input type="text" name="trackingNumberLink" value='<c:out value="${purchaseCase.trackingNumberLink}"/>'>
    	</div>
    </div>
        
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">代運人：</label>
    	<div class="col-sm-10">
    	<input type="text" name="agent" value='<c:out value="${purchaseCase.agent}"/>'>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">代運Tracking number：</label>
    	<div class="col-sm-10">
    	<input type="text" name="agentTrackingNumber" value='<c:out value="${purchaseCase.agentTrackingNumber}"/>'>
    	</div>
    </div>

    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">代運Tracking number link：</label>
    	<div class="col-sm-10">
    	<input type="text" name="agentTrackingNumberLink" value='<c:out value="${purchaseCase.agentTrackingNumberLink}"/>'>
    	</div>
    </div>
            
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">成本：</label>
    	<div class="col-sm-10">
    	<input type="text" name="cost" value="${purchaseCase.cost}">請輸入數字!
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">國際運費：</label>
    	<div class="col-sm-10">
    	<input type="text" name="agentCost" value="${purchaseCase.agentCost}">請輸入數字!
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">備註：</label>
    	<div class="col-sm-10">
    	<input type="text" name="description" value='<c:out value="${purchaseCase.description}"/>'>
    	</div>
    </div>
    
    <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label>
          <c:choose>
          <c:when test="${purchaseCase.isAbroad==true}">
          <input type="checkbox" name="isAbroad" value="true" checked="checked"> 是否為代購case
          </c:when>
          <c:otherwise>
          <input type="checkbox" name="isAbroad" value="true"> 是否為代購case
          </c:otherwise>
		  </c:choose>
        </label>
      </div>
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