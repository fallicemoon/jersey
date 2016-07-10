<%@page import="purchaseCase.model.PurchaseCaseVO"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.catalina.connector.Request"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>關聯條目</title>
</head>
<body>
  <c:import url="/WEB-INF/pages/header.jsp"/>
  <br>
  <h1>${requestScope.title}</h1>
<!-- 商品 -->
	<h3>商品</h3>
	<form action="/jersey/CommodityServlet" method="POST">
  	<table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th></th>
      <th></th>
      <th>圖片</th>
      <th>商品編號/商品名稱</th>
      <th>player</th>
      <th>number</th>
      <th>season</th>
      <th>team</th>
      <th>color</th>
      <th>brand</th>
      <th>size</th>
      <th>level</th>
      <th>condition</th>
      <th>tag</th>
      <th>Patch/Certificate</th>
      <th>serial</th>
      <th>owner</th>
      <td>販售平台</td>
      <td>是否仍在庫</td>
    </tr>
    </thead>
      	
  	<c:forEach items="${requestScope.commodityList}" var="vo">
  	<tr>
  	  <td>
		<input type="checkbox" name="commodityIds" value="${vo.commodityId}">
  	  </td>
  	  <td>
		<a href="/jersey/commodity/update.jsp?commodityId=${vo.commodityId}&listOne=true"><button type="button" class="btn btn-warning">修改</button></a>
  	  </td>
  	  
<%--   	  <jsp:include page="/CommodityServlet"> --%>
<%--   	  	<jsp:param value="getOne" name="action"/> --%>
<%--   	  	<jsp:param value="${vo.commodityId}" name="commodityId"/> --%>
<%--   	  </jsp:include> --%>
  	  <c:if test="${requestScope.commodityIdPictureCount != 0}"><td><a href="/jersey/picture/uploadPicture.jsp?commodityId=${vo.commodityId}"><button type="button" class="btn btn-success" data-toggle="modal">${commodityIdPictureCount}</button></a></td></c:if> 
  	  <c:if test="${requestScope.commodityIdPictureCount == 0}"><td><a href="/jersey/picture/uploadPicture.jsp?commodityId=${vo.commodityId}"><button type="button" class="btn btn-danger" data-toggle="modal">0</button></a></td></c:if>
  	  
  	  <td>${vo.commodityId} - <c:out value="${vo.itemName}" />
  	  		<c:if test="${!empty vo.link}"><a href="${vo.link}" target="_blank"> 連結</a></c:if>
  	  		<c:if test="${empty vo.link}"></c:if></td>
  	  <td><c:out value="${vo.player}" /></td>
  	  <td><c:out value="${vo.number}" /></td>
  	  <td><c:out value="${vo.season}" /></td>
  	  <td><c:out value="${vo.team}" /></td>
  	  <td><c:out value="${vo.color}" /></td>
  	  <td><c:out value="${vo.brand}" /></td>
  	  <td><c:out value="${vo.size}" /></td>
  	  <td><c:out value="${vo.level}" /></td>
  	  <td><c:out value="${vo.condition}" /></td>
  	  <td><c:out value="${vo.tag}" /></td>
  	  <td><c:out value="${vo.patchAndCertificate}" /></td>
  	  <td><c:out value="${vo.serial}" /></td>
  	  <td><c:out value="${vo.owner}" /></td>
  	  <td><c:out value="${vo.sellPlatform}" /></td>
  	  <c:if test="${vo.isStored}"><td>是</td></c:if>
  	  <c:if test="${!vo.isStored}"><td>否</td></c:if>
  	</tr>
  	</c:forEach>
  </table>
  	<button type="submit" name="action" value="delete" class="btn btn-danger" data-toggle="modal" onclick="return confirm('確認刪除?')">刪除</button>
  </form>

<h3>進貨</h3>
<!-- 進貨 -->
  <form action="/jersey/PurchaseCaseServlet" method="POST">
  <table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th></th>
      <th></th>
      <th>進貨編號/商家名稱</th>
	  <th>商品編號/商品名稱</th>
      <th>進度</th>
      <th>託運公司</th>
      <th>Tracking number</th>
      <th>代運人</th>
      <th>代運Tracking number</th>
      <th>是否為代購</th>
      <th>成本</th>
      <th>國際運費</th>
      <th>備註</th>
      <th>進貨時間</th>
    </tr>
    </thead>
      	
  	<c:forEach items="${requestScope.purchaseCaseList}" var="vo">
  	<tr>
	  <td>
  	    <input type="checkbox" name="purchaseCaseIds" value="${vo.purchaseCaseId}">
  	  </td>
  	  <td>
		<a href="/jersey/purchaseCase/update.jsp?purchaseCaseId=${vo.purchaseCaseId}&listOne=true"><button type="button" class="btn btn-warning">修改</button></a>
  	  </td>
  	  <jsp:include page="/StoreServlet">
  	  	<jsp:param value="getOne" name="action"/>
  	  	<jsp:param value="${vo.store}" name="storeId"/>
  	  </jsp:include>
  	  <td>${vo.purchaseCaseId} - <c:out value="${store.name}" /></td>
  	  <td><c:forEach items="${vo.commoditys}" var="commodity">${commodity.commodityId}-${commodity.itemName}<br></c:forEach></td>
  	  <td>${vo.progress}</td>
  	  <jsp:include page="/StoreServlet">
  	  	<jsp:param value="getOne" name="action"/>
  	  	<jsp:param value="${vo.shippingCompany}" name="storeId"/>
  	  </jsp:include>
  	  <td><c:out value="${store.name}" /></td>
  	  <td><c:out value="${vo.trackingNumber}" /><c:if test="${!empty vo.trackingNumberLink}"><a href="${vo.trackingNumberLink}" target="_blank"> 連結</a></c:if>
  	  		<c:if test="${empty vo.trackingNumberLink}"></c:if></td>
  	  <td><c:out value="${vo.agent}" /></td>
  	  <td><c:out value="${vo.agentTrackingNumber}" /><c:if test="${!empty vo.agentTrackingNumberLink}"><a href="${vo.agentTrackingNumberLink}" target="_blank"> 連結</a></c:if>
  	  	  <c:if test="${empty vo.agentTrackingNumberLink}"></c:if></td>
  	  <c:if test="${vo.isAbroad}"><td>是</td></c:if>
  	  <c:if test="${!vo.isAbroad}"><td>否</td></c:if>
  	  <td>${vo.cost}</td>
  	  <td>${vo.agentCost}</td>
  	  <td><c:out value="${vo.description}" /></td>
  	  <td>${vo.time}</td>
  	</tr>
  	</c:forEach>
  </table>
  	<button type="submit" name="action" value="delete" class="btn btn-danger" data-toggle="modal" onclick="return confirm('確認刪除?')">刪除</button>
  </form>	

<!-- 出貨 -->
  <h3>出貨</h3>
  <form action="/jersey/SellCaseServlet" method="POST">
  <c:if test="${empty param.page}">
  <table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th></th>
      <th></th>
      <th>出貨編號/收件人</th>
      <th>總價</th>
      <th>是否已出貨</th>
      <th>是否以查收</th>
      <th>未收額</th>
      <th>是否已結案</th>
      <th>實際利潤/預期利潤</th>
      <th>進貨編號/商家名稱</th>
      <th>運送方式</th>
      <th>手機</th>
      <th>地址/店名</th>
      <th>Tracking number</th>
      <th>運費</th>
      <th>出貨時間</th>
      <th>備註</th>
      <th>已收額</th>
      <th>結案時間</th>
    </tr>
    </thead>
  	<c:forEach items="${sellCaseList}" var="vo">
  	<tr>
  	  <td>
  	  	<input type="checkbox" name="sellCaseIds" value="${vo.sellCaseId}">
  	  </td>
  	  <td>
  	  	<a href="/jersey/sellCase/update.jsp?sellCaseId=${vo.sellCaseId}&listOne=true"><button type="button" class="btn btn-warning">修改</button></a>
  	  </td>
  	  <td>${vo.sellCaseId} - <c:out value="${vo.addressee}" /></td>
  	  <td>${vo.income}</td>
  	  <c:if test="${vo.isShipping}"><td>是</td></c:if>
  	  <c:if test="${!vo.isShipping}"><td>否</td></c:if>
  	  <c:if test="${vo.isChecked}"><td>是</td></c:if>
  	  <c:if test="${!vo.isChecked}"><td>否</td></c:if>
  	  <td>${vo.uncollected}</td>
  	  <td><c:if test="${vo.uncollected==0 && vo.isChecked}">是</c:if>
  	      <c:if test="${!vo.isChecked || vo.uncollected!=0}">否</c:if></td>
  	  <jsp:include page="/SellCaseServlet">
  	  	<jsp:param value="getOne" name="action"/>
  	  	<jsp:param value="${vo.sellCaseId}" name="sellCaseId"/>
  	  </jsp:include>
  	  <td>已收額${vo.collected} (總價${vo.income}) - 成本${requestScope.costs} - 國際運費${requestScope.agentCosts} - 國內運費${vo.transportCost} =   	  
  	  	  <c:if test="${requestScope.benefit < 0}"><span style="color: red">${requestScope.benefit} / </span></c:if>
		  <c:if test="${requestScope.benefit >= 0}"><span style="color: blue">${requestScope.benefit} / </span></c:if>
  	  	  <c:if test="${requestScope.estimateBenefit < 0}"><span style="color: red">${requestScope.estimateBenefit}</span></c:if>
		  <c:if test="${requestScope.estimateBenefit >= 0}"><span style="color: blue">${requestScope.estimateBenefit}</span></c:if>
	  </td>
  	  <td><c:forEach items="${vo.purchaseCases}" var="purchaseCase">
  	  <jsp:include page="/StoreServlet">
  	  	<jsp:param value="getOne" name="action"/>
  	  	<jsp:param value="${purchaseCase.store}" name="storeId"/>
  	  </jsp:include>
  	  	${purchaseCase.purchaseCaseId}-${store.name}<br>
  	  </c:forEach></td>
	  <td>${vo.transportMethod}</td>
	  <td><c:out value="${vo.phone}" /></td>
  	  <td><c:out value="${vo.address}" /></td>
  	  <td><c:out value="${vo.trackingNumber}" /></td>
  	  <td>${vo.transportCost}</td>
  	  <td>${vo.shippingTime}</td>
  	  <td><c:out value="${vo.description}" /></td>
  	  <td>${vo.collected}</td>
  	  <td>${vo.closeTime}</td>
  	</tr>
  	</c:forEach>
  </table>
  </c:if>

  	<button type="submit" name="action" value="delete" class="btn btn-danger" data-toggle="modal" onclick="return confirm('確認刪除?')">刪除</button>
  </form>

<c:import url="/WEB-INF/pages/footer.jsp"></c:import>


</body>
</html>