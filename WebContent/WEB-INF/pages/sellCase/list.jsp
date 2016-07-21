<%@page import="purchaseCase.model.PurchaseCaseVO"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.catalina.connector.Request"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>出貨</title>
</head>
<body>

	<form action="/jersey/SellCaseServlet" method="POST">
		<c:import url="/WEB-INF/pages/header.jsp" />
		<span style="display: inline-block; width: 100px"></span> <a
			href="/jersey/SellCaseServlet?action=getOne"><button
				type="button" class="btn btn-success" data-toggle="modal">新增</button></a>
		<button type="submit" name="action" value="delete"
			class="btn btn-danger" data-toggle="modal"
			onclick="return confirm('確認刪除?')">刪除</button>
		<c:if test="${param.action != 'getUncollectedNotZero'}">
			<a href="/jersey/SellCaseServlet?action=getUncollectedNotZero"
				class="btn btn-default btn-lg">尚有未收額</a>
		</c:if>
		<c:if test="${param.action == 'getUncollectedNotZero'}">
			<a href="/jersey/SellCaseServlet?action=getUncollectedNotZero"
				class="btn btn-default btn-lg disabled" style="color: red;">尚有未收額</a>
		</c:if>
		<c:if test="${param.action != 'getIsClosed'}">
			<a href="/jersey/SellCaseServlet?action=getIsClosed"
				class="btn btn-default btn-lg">已結案</a>
		</c:if>
		<c:if test="${param.action == 'getIsClosed'}">
			<a href="/jersey/SellCaseServlet?action=getIsClosed"
				class="btn btn-default btn-lg disabled" style="color: red;">已結案</a>
		</c:if>
		<c:if test="${param.action != 'getNotClosed'}">
			<a href="/jersey/SellCaseServlet?action=getNotClosed"
				class="btn btn-default btn-lg">未結案</a>
		</c:if>
		<c:if test="${param.action == 'getNotClosed'}">
			<a href="/jersey/SellCaseServlet?action=getNotClosed"
				class="btn btn-default btn-lg disabled" style="color: red;">未結案</a>
		</c:if>
		<c:if test="${param.action != 'getAll'}">
			<a href="/jersey/SellCaseServlet" class="btn btn-default btn-lg">列出全部</a>
		</c:if>
		<c:if test="${param.action == 'getAll'}">
			<a href="/jersey/SellCaseServlet"
				class="btn btn-default btn-lg disabled" style="color: red;">列出全部</a>
		</c:if>
		
		
		<c:if test="${sessionScope.sellCasePage == 'before'}">			
				<button type="submit" class="btn btn-normal" name="changePage" value="true">第二頁</button>
			<table border=1 width="1500px" class="table table-hover">
				<thead>
					<tr>
						<th></th>
						<th></th>
						<th></th>
						<th>出貨編號/收件人</th>
						<th>總價</th>
						<th>是否已出貨</th>
						<th>是否以查收</th>
						<th>未收額</th>
						<th>是否已結案</th>
						<th>實際利潤 / 預期利潤</th>
					</tr>
				</thead>
				<c:forEach items="${sellCaseList}" var="vo">
					<tr>
						<td><input type="checkbox" name="sellCaseIds"
							value="${vo.sellCaseId}"></td>
						<td><a
							href="/jersey/SellCaseServlet?action=getOne&sellCaseId=${vo.sellCaseId}"><button
									type="button" class="btn btn-warning">修改</button></a></td>
						<td><a href="/jersey/SellCaseServlet?action=getPurchaseCaseList&sellCaseId=${vo.sellCaseId}"><button type="button" class="btn btn-success">匯入進貨</button></a></td>
						<td><a
							href="/jersey/TripleServlet?action=sellCase&sellCaseId=${vo.sellCaseId}">${vo.sellCaseId}
								- <c:out value="${vo.addressee}" />
						</a></td>
						<td>${vo.income}</td>
						<c:if test="${vo.isShipping}">
							<td>是</td>
						</c:if>
						<c:if test="${!vo.isShipping}">
							<td>否</td>
						</c:if>
						<c:if test="${vo.isChecked}">
							<td>是</td>
						</c:if>
						<c:if test="${!vo.isChecked}">
							<td>否</td>
						</c:if>
						<td>${vo.uncollected}</td>
						<td><c:if test="${vo.uncollected==0 && vo.isChecked}">是</c:if>
							<c:if test="${!vo.isChecked || vo.uncollected!=0}">否</c:if></td>
						<%--   	  <jsp:include page="/SellCaseServlet"> --%>
						<%--   	  	<jsp:param value="getOne" name="action"/> --%>
						<%--   	  	<jsp:param value="${vo.sellCaseId}" name="sellCaseId"/> --%>
						<%--   	  </jsp:include> --%>
						<td>已收額${vo.collected} (總價${vo.income}) - 成本${vo.costs} -
							國際運費${vo.agentCosts} - 國內運費${vo.transportCost} = <c:if
								test="${vo.benefit < 0}">
								<span style="color: red">${vo.benefit} / </span>
							</c:if> <c:if test="${vo.benefit >= 0}">
								<span style="color: blue">${vo.benefit} / </span>
							</c:if> <c:if test="${vo.estimateBenefit < 0}">
								<span style="color: red">${vo.estimateBenefit}</span>
							</c:if> <c:if test="${vo.estimateBenefit >= 0}">
								<span style="color: blue">${vo.estimateBenefit}</span>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

		<c:if test="${sessionScope.sellCasePage == 'after'}">
			<button type="submit" class="btn btn-normal" name="changePage" value="true">第一頁</button>
			<table border=1 width="1500px" class="table table-hover">
				<thead>
					<tr>
						<th></th>
						<th></th>
						<th></th>
						<th>出貨編號/收件人</th>
						<th>進貨編號/商家名稱</th>
						<th>運送方式</th>
						<th>手機</th>
						<th>地址/店名</th>
						<th>Tracking number</th>
						<th>運費</th>
						<th>時間</th>
						<th>備註</th>
						<th>已收額</th>
						<th>結案時間</th>
					</tr>
				</thead>

				<c:forEach items="${requestScope.sellCaseList}" var="vo">
					<tr>
						<td><input type="checkbox" name="sellCaseIds"
							value="${vo.sellCaseId}"></td>
						<td><a
							href="/jersey/SellCaseServlet?action=getOne&sellCaseId=${vo.sellCaseId}"><button
									type="button" class="btn btn-warning">修改</button></a></td>
						<td><a href="/jersey/SellCaseServlet?action=getPurchaseCaseList&sellCaseId=${vo.sellCaseId}"><button type="button" class="btn btn-success">匯入進貨</button></a></td>
						<td><a
							href="/jersey/TripleServlet?action=sellCase&sellCaseId=${vo.sellCaseId}">${vo.sellCaseId}
								- <c:out value="${vo.addressee}" />
						</a></td>
						<td><c:forEach items="${vo.purchaseCases}" var="purchaseCase">
								<%--   	   	<jsp:include page="/StoreServlet"> --%>
								<%--   	  		<jsp:param value="getOne" name="action"/> --%>
								<%--   	  		<jsp:param value="${purchaseCase.store}" name="storeId"/> --%>
								<%--   	  	</jsp:include> --%>
  	  	${purchaseCase.purchaseCaseId}-${purchaseCase.store.name}<br>
							</c:forEach></td>
						<td>${vo.transportMethod}</td>
						<td><c:out value="${vo.phone}" /></td>
						<td><c:out value="${vo.address}" /></td>
						<td><c:out value="${vo.trackingNumber}" /></td>
						<td>${vo.transportCost}</td>
						<td><fmt:formatDate value="${vo.shippingTime}"
								pattern="yyyy/MM/dd hh:mm:ss" /></td>
						<td><c:out value="${vo.description}" /></td>
						<td>${vo.collected}</td>
						<td><fmt:formatDate value="${vo.closeTime}"
								pattern="yyyy/MM/dd hh:mm:ss" /></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

	</form>

	<c:import url="/WEB-INF/pages/footer.jsp"></c:import>


</body>
</html>