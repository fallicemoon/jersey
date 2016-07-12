//package tools;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Set;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import commodity.model.CommodityService;
//import commodity.model.CommodityVO;
//import purchaseCase.model.PurchaseCaseService;
//import purchaseCase.model.PurchaseCaseVO;
//import sellCase.model.SellCaseService;
//import sellCase.model.SellCaseVO;
//import store.model.StoreVO;
//
//public class OtherServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private String action;
//	private Set<String> errors;
//	private HttpSession session;
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		doPost(request, response);
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		this.action = request.getParameter("action");
//		if ("commodity".equals(this.action)) {
//			CommodityService cs = new CommodityService();
//
//			List<CommodityVO> commodityList = new ArrayList<CommodityVO>();
//			List<PurchaseCaseVO> purchaseCaseList = new ArrayList<PurchaseCaseVO>();
//			List<SellCaseVO> sellCaseList = new ArrayList<SellCaseVO>();
//
//			SellCaseVO sellCaseVO = null;
//
//			CommodityVO commodityVO = cs.getOne(Integer.valueOf(request.getParameter("commodityId")));
//			commodityList.add(commodityVO);
//			PurchaseCaseVO purchaseCaseVO = commodityVO.getPurchaseCaseVO();
//			if (purchaseCaseVO != null) {
//				purchaseCaseList.add(purchaseCaseVO);
//				sellCaseVO = purchaseCaseVO.getSellCaseVO();
//			}
//			if (sellCaseVO != null) {
//				sellCaseList.add(sellCaseVO);
//			}
//
//			request.setAttribute("title", "商品:" + commodityVO.getCommodityId() + "/" + commodityVO.getItemName());
//			request.setAttribute("commodityList", commodityList);
//			request.setAttribute("purchaseCaseList", purchaseCaseList);
//			request.setAttribute("sellCaseList", sellCaseList);
//
//			request.getRequestDispatcher("/listOne.jsp").forward(request, response);
//		} else {
//			StoreVO store;
//			if ("purchaseCase".equals(this.action)) {
//				PurchaseCaseService pcs = new PurchaseCaseService();
//
//				Set<CommodityVO> commoditys = new LinkedHashSet<CommodityVO>();
//				List<PurchaseCaseVO> purchaseCaseList = new ArrayList<PurchaseCaseVO>();
//				List<SellCaseVO> sellCaseList = new ArrayList<SellCaseVO>();
//
//				PurchaseCaseVO purchaseCaseVO = pcs.getOne(Integer.valueOf(request.getParameter("purchaseCaseId")));
//				commoditys = purchaseCaseVO.getCommoditys();
//				SellCaseVO sellCaseVO = purchaseCaseVO.getSellCaseVO();
//				purchaseCaseList.add(purchaseCaseVO);
//				if (sellCaseVO != null)
//					sellCaseList.add(sellCaseVO);
//
//				request.getRequestDispatcher("/StoreServlet?action=getOne&storeId=" + purchaseCaseVO.getStore())
//						.include(request, response);
//				store = (StoreVO) request.getAttribute("store");
//
//				request.setAttribute("title", "進貨:" + purchaseCaseVO.getPurchaseCaseId() + "/" + store.getName());
//				request.setAttribute("commodityList", commoditys);
//				request.setAttribute("purchaseCaseList", purchaseCaseList);
//				request.setAttribute("sellCaseList", sellCaseList);
//
//				request.getRequestDispatcher("/listOne.jsp").forward(request, response);
//			} else if ("sellCase".equals(this.action)) {
//				SellCaseService scs = new SellCaseService();
//
//				Set<CommodityVO> commoditys = new LinkedHashSet<CommodityVO>();
//
//				List<SellCaseVO> sellCaseList = new ArrayList<SellCaseVO>();
//
//				SellCaseVO sellCaseVO = scs.getOne(Integer.valueOf(request.getParameter("sellCaseId")));
//				Set<PurchaseCaseVO> purchaseCases = sellCaseVO.getPurchaseCases();
//
//				if (purchaseCases != null) {
//					for (PurchaseCaseVO purchaseCaseVO : purchaseCases) {
//						Set<CommodityVO> set = purchaseCaseVO.getCommoditys();
//						if (set != null) {
//							for (CommodityVO commodityVO : set) {
//								commoditys.add(commodityVO);
//							}
//						}
//					}
//				}
//
//				sellCaseList.add(sellCaseVO);
//
//				request.setAttribute("title", "出貨:" + sellCaseVO.getSellCaseId() + "/" + sellCaseVO.getAddressee());
//				request.setAttribute("commodityList", commoditys);
//				request.setAttribute("purchaseCaseList", purchaseCases);
//				request.setAttribute("sellCaseList", sellCaseList);
//
//				request.getRequestDispatcher("/listOne.jsp").forward(request, response);
//			} 
//		}
//	}
//}
