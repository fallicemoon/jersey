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
<title>修改出貨項目</title>
</head>
<c:import url="/header.jsp"/>
<body>
	
	<c:if test="${param.sellCaseId == null}">
		<c:redirect context="/jersey" url="/sellCase/list.jsp?action=getAll&page=1" />
	</c:if>
	<br/><br/>
	
	<c:if test="${param.sellCaseId != null}">
		<jsp:include page="/SellCaseServlet">
			<jsp:param name="action" value="getOne" />
			<jsp:param name="sellCaseId" value="${param.sellCaseId}" />
		</jsp:include>
	</c:if>
	
	<c:forEach items="${sessionScope.errors}" var="error">
		<p style="color: red">${error}</p>
	</c:forEach>
	<c:remove var="errors" scope="session"/>

	<form action="/jersey/SellCaseServlet?listOne=${param.listOne}" method="post" class="form-horizontal">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="page" value="${param.page}">
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">出貨編號：</label>
    	<div class="col-sm-10">
    	<input type="hidden" name="sellCaseId" value="${sellCase.sellCaseId}">${sellCase.sellCaseId}
    	</div>
    </div>
    
    <div class="form-group">
	<label for="inputEmail3" class="col-sm-2 control-label">進貨編號/商家名稱：</label>
	    	<div class="col-sm-10">
	    	<a href="/jersey/sellCase/addPurchaseCase.jsp?sellCaseId=${sellCase.sellCaseId}"><button type="button" class="btn btn-warning">匯入</button></a>
	    	</div>
	</div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">運送方式：</label>
    	<div class="col-sm-10">
    	<select name="transportMethod">
    		<c:choose>
    		<c:when test="${sellCase.transportMethod=='面交'}"><option value="面交" selected="selected">面交</option></c:when>
    		<c:otherwise><option value="面交">面交</option></c:otherwise>
    		</c:choose>
    		<c:choose>
    		<c:when test="${sellCase.transportMethod=='郵局'}"><option value="郵局" selected="selected">郵局</option></c:when>
    		<c:otherwise><option value="郵局">郵局</option></c:otherwise>
    		</c:choose>
    		<c:choose>
    		<c:when test="${sellCase.transportMethod=='ezShip'}"><option value="ezShip" selected="selected">ezShip</option></c:when>
    		<c:otherwise><option value="ezShip">ezShip</option></c:otherwise>
    		</c:choose>
    		<c:choose>
    		<c:when test="${sellCase.transportMethod=='7-11'}"><option value="7-11">7-11</option></c:when>
    		<c:otherwise><option value="7-11">7-11</option></c:otherwise>
    		</c:choose>
    	</select>
    	</div>
    </div>

    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">收件人：</label>
    	<div class="col-sm-10">
    	<input type="text" name="addressee" value="${sellCase.addressee}">
    	</div>
    </div>

    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">手機</label>
    	<div class="col-sm-10">
    	<input type="text" name="phone" value="${sellCase.phone}">
    	</div>
    </div>
            
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">地址/店名：</label>
    	<div class="col-sm-10">
    	<input type="text" name="address" value="${sellCase.address}">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Tracking number：</label>
    	<div class="col-sm-10">
    	<input type="text" name="trackingNumber" value="${sellCase.trackingNumber}">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">運費：</label>
    	<div class="col-sm-10">
    	<input type="text" name="transportCost" value="${sellCase.transportCost}">請輸入數字!
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">總價：</label>
    	<div class="col-sm-10">
    	<input type="text" name="income" value="${sellCase.income}">請輸入數字!
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">已收額：</label>
    	<div class="col-sm-10">
    	<input type="text" name="collected"  value="${sellCase.collected}">請輸入數字!
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">備註：</label>
    	<div class="col-sm-10">
    	<input type="text" name="description" value="${sellCase.description}">
    	</div>
    </div>
    
    <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label>
          <c:choose>
          <c:when test="${sellCase.isShipping==true}">
          <input type="checkbox" name="isShipping" value="true" checked="checked"> 是否已出貨
          </c:when>
          <c:otherwise>
          <input type="checkbox" name="isShipping" value="true"> 是否已出貨
          </c:otherwise>
		  </c:choose>
        </label>
      </div>
    </div>
  	</div>

    <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label>
          <c:choose>
          <c:when test="${sellCase.isChecked==true}">
          <input type="checkbox" name="isChecked" value="true" checked="checked"> 是否已查收
          </c:when>
          <c:otherwise>
          <input type="checkbox" name="isChecked" value="true"> 是否已查收
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

<c:import url="/footer.jsp"></c:import>
</body>
</html>