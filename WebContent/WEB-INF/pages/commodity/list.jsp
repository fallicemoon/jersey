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

<title>商品</title>
</head>
<body>
	<%--   <jsp:include page="/CommodityServlet"> --%>
	<%--   	<jsp:param name="action" value="${param.action}"/> --%>
	<%--   </jsp:include> --%>
	<form action="/jersey/CommodityServlet" method="POST">
		<c:import url="/WEB-INF/pages/header.jsp" />
		<span style="display: inline-block; width: 100px"></span> <a
			href="/jersey/CommodityServlet?action=getOne"><button
				type="button" class="btn btn-success" data-toggle="modal">新增</button></a>
		<button type="submit" name="action" value="delete"
			class="btn btn-danger" data-toggle="modal"
			onclick="return confirm('確認刪除?')">刪除</button>
		<button type="submit" name="action" value="getByRule"
			class="btn btn-warning" data-toggle="modal">篩選</button>
		<button type="submit" name="action" value="copy"
			class="btn btn-warning" data-toggle="modal">複製</button>
		<table border=1 width="1500px" class="table table-hover">
			<thead>
				<tr>
					<c:if test="${!requestScope.showRule}">
						<th></th>
						<th></th>
						<th>圖片</th>
						<th>商品編號/商品名稱
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
					</c:if>
					<c:if test="${requestScope.showRule}">
						<th></th>
						<th></th>
						<th>圖片</th>
						<th>商品編號/商品名稱 <select name="itemName">
								<option selected="selected">無</option>
								<c:forEach items="${requestScope.itemNames}" var="itemName">
									<option value="${itemName}">${itemName}</option>
								</c:forEach>
						</select>
						</th>
						<th>Qty</th>
						<th>player <select name="player">
								<option selected="selected">無</option>
								<c:forEach items="${requestScope.players}" var="player">
									<option value="${player}">${player}</option>
								</c:forEach>
						</select>
						</th>
						<th>number</th>
						<th>season</th>
						<th>team <select name="team">
								<option selected="selected">無</option>
								<c:forEach items="${requestScope.teams}" var="team">
									<option value="${team}">${team}</option>
								</c:forEach>
						</select>
						</th>
						<th>style <select name="style">
								<option selected="selected">無</option>
								<c:forEach items="${requestScope.styles}" var="style">
									<option value="${style}">${style}</option>
								</c:forEach>
						</select>
						</th>
						<th>color</th>
						<th>brand <select name="brand">
								<option selected="selected">無</option>
								<c:forEach items="${requestScope.brands}" var="brand">
									<option value="${brand}">${brand}</option>
								</c:forEach>
						</select>
						</th>
						<th>size <select name="size">
								<option selected="selected">無</option>
								<c:forEach items="${requestScope.sizes}" var="size">
									<option value="${size}">${size}</option>
								</c:forEach>
						</select>
						</th>
						<th>level <select name="level">
								<option selected="selected">無</option>
								<option></option>
								<option>Replica</option>
								<option>Swingman</option>
								<option>Authentic</option>
								<option>Team Authentic</option>
								<option>Pro Cut</option>
								<option>Team Issued</option>
								<option>Game Issued</option>
								<option>Game Used</option>
						</select>
						</th>
						<th>condition <select name="condition">
								<option selected="selected">無</option>
								<c:forEach items="${requestScope.conditions}" var="condition">
									<option value="${condition}">${condition}</option>
								</c:forEach>
						</select>
						</th>
						<th>tag <select name="tag">
								<option>無</option>
								<option>--</option>
								<option>Yes</option>
								<option>No</option>
						</select>
						</th>
						<th>Patch/Certificate</th>
						<th>serial</th>
						<th>owner <select name="owner">
								<option selected="selected">無</option>
								<c:forEach items="${requestScope.owners}" var="owner">
									<option value="${owner}">${owner}</option>
								</c:forEach>
						</select>
						</th>
						<th>成本</th>
						<th>售價</th>
						<th>販售平台 <select name="sellPlatform">
								<option selected="selected">無</option>
								<c:forEach items="${requestScope.sellPlatforms}"
									var="sellPlatform">
									<option value="${sellPlatform}">${sellPlatform}</option>
								</c:forEach>
						</select>
						</th>
						<th>是否仍在庫 <select name="isStored">
								<option selected="selected">無</option>
								<option value="true">是</option>
								<option value="false">否</option>
						</select>
						</th>
					</c:if>
				</tr>
			</thead>


			<c:forEach items="${commodityList}" var="vo">
				<tr>
					<td><input type="checkbox" name="commodityIds"
						value="${vo.commodityId}"></td>
					<td><a
						href="/jersey/CommodityServlet?action=getOne&commodityId=${vo.commodityId}"><button
								type="button" class="btn btn-warning">修改</button></a></td>
					<%--   	  <c:if test="${not empty commodityIdPictureCountMap[vo.commodityId]}"><td><a href="/jersey/picture/uploadPicture.jsp?commodityId=${vo.commodityId}"><button type="button" class="btn btn-success" data-toggle="modal">${commodityIdPictureCountMap[vo.commodityId]}</button></a></td></c:if>  --%>
					<%--   	  <c:if test="${empty commodityIdPictureCountMap[vo.commodityId]}"><td><a href="/jersey/picture/uploadPicture.jsp?commodityId=${vo.commodityId}"><button type="button" class="btn btn-danger" data-toggle="modal">0</button></a></td></c:if> --%>
					<c:if test="${vo.pictureCount!=0}">
						<td><a
							href="/jersey/PictureServlet?commodityId=${vo.commodityId}"><button
									type="button" class="btn btn-success" data-toggle="modal">${vo.pictureCount}</button></a></td>
					</c:if>
					<c:if test="${vo.pictureCount==0}">
						<td><a
							href="/jersey/PictureServlet?commodityId=${vo.commodityId}"><button
									type="button" class="btn btn-danger" data-toggle="modal">0</button></a></td>
					</c:if>

					<td><a
						href="/jersey/TripleServlet?action=commodity&commodityId=${vo.commodityId}">${vo.commodityId}
							- <c:out value="${vo.itemName}" />
					</a> <c:if test="${!empty vo.link}">
							<a href="${vo.link}" target="_blank"> 連結</a>
						</c:if> <c:if test="${empty vo.link}"></c:if></td>
					<td><c:out value="${vo.qty}" /></td>
					<td><c:out value="${vo.player}" /></td>
					<td><c:out value="${vo.number}" /></td>
					<td><c:out value="${vo.season}" /></td>
					<td><c:out value="${vo.team}" /></td>
					<td><c:out value="${vo.style}" /></td>
					<td><c:out value="${vo.color}" /></td>
					<td><c:out value="${vo.brand}" /></td>
					<td><c:out value="${vo.size}" /></td>
					<td><c:out value="${vo.level}" /></td>
					<td><c:out value="${vo.condition}" /></td>
					<td><c:out value="${vo.tag}" /></td>
					<td><c:out value="${vo.patchAndCertificate}" /></td>
					<td><c:out value="${vo.serial}" /></td>
					<td><c:out value="${vo.owner}" /></td>
					<td><c:out value="${vo.cost}" /></td>
					<td><c:out value="${vo.sellPrice}" /></td>
					<td><c:out value="${vo.sellPlatform}" /></td>
					<c:if test="${vo.isStored}">
						<td>是</td>
					</c:if>
					<c:if test="${!vo.isStored}">
						<td>否</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</form>


	<c:import url="/WEB-INF/pages/footer.jsp"></c:import>


</body>
</html>