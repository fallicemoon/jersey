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

<title>新增商品</title>
</head>
<body>
<c:import url="/header.jsp"/>
	<br/><br/>
	<c:forEach items="${sessionScope.errors}" var="error">
		<p style="color: red">${error}</p>
	</c:forEach>
	<c:remove var="errors" scope="session"/>
		
	<form action="/jersey/CommodityServlet" method="post" class="form-horizontal">
	<input type="hidden" name="action" value="create">
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">商品名稱：</label>
    	<div class="col-sm-10">
    	<input type="text" name="itemName">
    	</div>
    </div>

    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">link：</label>
    	<div class="col-sm-10">
    	<input type="text" name="link">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">player：</label>
    	<div class="col-sm-10">
    	<input type="text" name="player">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">number：</label>
    	<div class="col-sm-10">
    	<input type="text" name="number">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">season：</label>
    	<div class="col-sm-10">
    	<input type="text" name="season">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">team：</label>
    	<div class="col-sm-10">
    	<input type="text" name="team">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">style：</label>
    	<div class="col-sm-10">
    	<input type="text" name="style">
    	</div>
    </div>    
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">color：</label>
    	<div class="col-sm-10">
    	<input type="text" name="color">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">brand：</label>
    	<div class="col-sm-10">
    	<input type="text" name="brand">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">size：</label>
    	<div class="col-sm-10">
    	<input type="text" name="size">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">level：</label>
    	<div class="col-sm-10">
    	<select name="level">
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
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">condition：</label>
    	<div class="col-sm-10">
    	<input type="text" name="condition">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">tag：</label>
    	<div class="col-sm-10">
    	<select name="tag">
    		<option>--</option>
    		<option>Yes</option>
    		<option>No</option>
    	</select>
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Patch/Certificate：</label>
    	<div class="col-sm-10">
    	<input type="text" name="patchAndCertificate">
    	</div>                   
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">serial：</label>
    	<div class="col-sm-10">
    	<input type="text" name="serial">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">owner：</label>
    	<div class="col-sm-10">
    	<input type="text" name="owner">
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">成本：</label>
    	<div class="col-sm-10">
    	<input type="text" name="cost" value=0> 請輸入數字!
    	</div>
    </div>
    
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">售價：</label>
    	<div class="col-sm-10">
    	<input type="text" name="sellPrice" value=0> 請輸入數字!
    	</div>
    </div>
                
    <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">販售平台：</label>
    	<div class="col-sm-10">
    	<input type="text" name="sellPlatform">
    	</div>
    </div>
	
	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">
			<button type="submit" class="btn btn-success" >新增</button>
		</label>
	</div>
	</form>

<c:import url="/footer.jsp"></c:import>
</body>
</html>