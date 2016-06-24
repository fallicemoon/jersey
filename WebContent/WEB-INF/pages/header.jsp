<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="/jersey/WEB-INF/pages/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/jersey/WEB-INF/pages/lib/bootstrap/bootstrap-datetimepicker.min.css" rel="stylesheet">

<script src="/jersey/WEB-INF/pages/lib/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/jersey/WEB-INF/pages/lib/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/jersey/WEB-INF/pages/lib/moment-with-locales.min.js"></script>
<script src="/jersey/WEB-INF/pages/lib/bootstrap/bootstrap-datetimepicker.min.js"></script>

</head>
<body>

<div class="btn-group">
  <a href="/jersey/CommodityServlet">
  <button type="button" class="btn btn-info dropdown-toggle">
    商品
  </button>
  </a>
</div>

<div class="btn-group">
  <a href="/jersey/PurchaseServlet">
  <button type="button" class="btn btn-primary dropdown-toggle">
    進貨 
  </button>
  </a>
</div>

<div class="btn-group">
  <a href="/jersey/sellCase/SellCaseServlet">
  <button type="button" class="btn btn-success dropdown-toggle">
    出貨
  </button>
  </a>
</div>

<div class="btn-group">
  <a href="/jersey/StoreServlet">
  <button type="button" class="btn btn-danger dropdown-toggle">
    商家
  </button>
  </a>
</div>

<div class="btn-group">
  <a href="/jersey/SellCaseServlet">
  <button type="button" class="btn btn-warning dropdown-toggle">
    會計
  </button>
  </a>
</div>

<a href="/jersey/LoginServlet?action=logout">登出</a>

</body>
</html>