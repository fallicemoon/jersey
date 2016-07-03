<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.picture {
	width: 100%;
	height: 100%;
}
</style>
<title>上傳圖片</title>
</head>
<body>
	<c:import url="/header.jsp"/>
	<br><br>
	
	<c:forEach items="${sessionScope.errors}" var="error">
		<p style="color: red">${error}</p>
	</c:forEach>
	<c:remove var="errors" scope="session"/>
    <jsp:include page="/CommodityServlet">
  	  <jsp:param name="action" value="getOne"/>
  	  <jsp:param name="commodityId" value="${param.commodityId}"/>
    </jsp:include>
    
  <table border=1 width="1500px" class="table table-hover">
    <thead>
    <tr>
      <th>商品編號/商品名稱</th>
      <th>Qty</th>
      <th>player</th>
      <th>number</th>
      <th>season</th>
      <th>team</th>
      <th>style</th>
      <th>color</th>
      <th>brand</th>
      <th>size</th>
      <th>level</th>
      <th>condition</th>
      <th>tag</th>
      <th>Patch/Certificate</th>
      <th>serial</th>
      <th>owner</th>
      <th>成本</th>
      <th>售價</th>
      <th>販售平台</th>
      <th>是否仍在庫</th>
    </tr>
    </thead>
	<tr>
  	  <td><a href="/jersey/OtherServlet?action=commodity&commodityId=${requestScope.commodity.commodityId}">${requestScope.commodity.commodityId} - <c:out value="${requestScope.commodity.itemName}" /></a>
  	  		<c:if test="${!empty requestScope.commodity.link}"><a href="${requestScope.commodity.link}" target="_blank"> 連結</a></c:if>
  	  		<c:if test="${empty requestScope.commodity.link}"></c:if></td>
  	  <td><c:out value="${requestScope.commodity.qty}" /></td>
  	  <td><c:out value="${requestScope.commodity.player}" /></td>
  	  <td><c:out value="${requestScope.commodity.number}" /></td>
  	  <td><c:out value="${requestScope.commodity.season}" /></td>
  	  <td><c:out value="${requestScope.commodity.team}" /></td>
	  <td><c:out value="${requestScope.commodity.style}" /></td>
  	  <td><c:out value="${requestScope.commodity.color}" /></td>
  	  <td><c:out value="${requestScope.commodity.brand}" /></td>
  	  <td><c:out value="${requestScope.commodity.size}" /></td>
  	  <td><c:out value="${requestScope.commodity.level}" /></td>
  	  <td><c:out value="${requestScope.commodity.condition}" /></td>
  	  <td><c:out value="${requestScope.commodity.tag}" /></td>
  	  <td><c:out value="${requestScope.commodity.patchAndCertificate}" /></td>
  	  <td><c:out value="${requestScope.commodity.serial}" /></td>
  	  <td><c:out value="${requestScope.commodity.owner}" /></td>
  	  <td><c:out value="${requestScope.commodity.cost}" /></td>
  	  <td><c:out value="${requestScope.commodity.sellPrice}" /></td>
  	  <td><c:out value="${requestScope.commodity.sellPlatform}" /></td>
  	  <c:if test="${requestScope.commodity.isStored}"><td>是</td></c:if>
  	  <c:if test="${!requestScope.commodity.isStored}"><td>否</td></c:if>
  	</tr>
  	</table>
	<form action="/jersey/PictureServlet?commodityId=${param.commodityId}" method="POST" enctype="multipart/form-data" class="form-horizontal">
    <input type="hidden" name="commodityId" value="${param.commodityId}">

    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">圖片(須為jpg / gif / png)：</label>
  		<div class="col-lg-6">
    		<div class="input-group">
      		<input type="file" class="form-control" placeholder="請選擇圖片" name="picture" multiple>檔案須小於4MB!
    		</div>
  		</div>
    </div>

	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">
			<button type="submit" class="btn btn-success" name="action" value="upload">新增</button>
		</label>
	</div>
	</form>
	
<%-- 	<jsp:include page="/PictureServlet"> --%>
<%-- 		<jsp:param name="action" value="getPicturesBase64" /> --%>
<%-- 		<jsp:param name="commodityId" value="${param.commodityId}" /> --%>
<%-- 	</jsp:include> --%>
	<jsp:include page="/PictureServlet">
		<jsp:param name="action" value="getPictureIds" />
		<jsp:param name="commodityId" value="${param.commodityId}" />
	</jsp:include>

	<form action="/jersey/PictureServlet" method="POST">
	<input type="hidden" name="commodityId" value="${param.commodityId}">
	<button type="submit" class="btn btn-danger" name="action" value="delete">刪除</button>
	<button type="submit" class="btn btn-normal" name="action" value="download">下載</button>
	<button type="submit" class="btn btn-normal" name="action" value="downloadAll">全部下載</button>
	
	<table border=0 width="1500px" class="table table-hover">
<%-- 		<c:forEach items="${requestScope.pictureBase64Map}" var="picture" varStatus="status"> --%>
<%-- 			<c:if test="${status.index%3 == 0}"><tr></c:if> --%>
<!-- 			<td> -->
<%-- 			<input type="checkbox" name="pictureId" value="${picture.key}" id="${picture.key}" style="margin-left:200px"><br> --%>
<%-- 			<img src="data:image/gif;base64,${picture.value}" style="width: 400px"> --%>
<!-- 			</td> -->
<%-- 			<c:if test="${status.index%3 == 2}"></tr></c:if> --%>
<%-- 		</c:forEach> --%>

		<c:forEach items="${requestScope.pictureIds}" var="pictureId" varStatus="status">
			<c:if test="${status.index%4 == 0}"><tr></c:if>
			<td>
			<input type="checkbox" name="pictureId" value="${pictureId}" id="${pictureId}" style="margin-left:200px"><br>
			<img src="/jersey/PictureServlet?action=getPicture&pictureId=${pictureId}" alt="" class="picture">
			</td>
			<c:if test="${status.index%4 == 3}"></tr></c:if>
		</c:forEach>
	</table>
	</form>
	
</body>
</html>