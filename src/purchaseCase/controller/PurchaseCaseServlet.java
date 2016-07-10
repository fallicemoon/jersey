package purchaseCase.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import commodity.model.CommodityVO;
import purchaseCase.model.PurchaseCaseService;
import purchaseCase.model.PurchaseCaseVO;
import store.model.StoreService;
import store.model.StoreVO;
import tools.JerseyEnum.StoreType;

public class PurchaseCaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String sendRedirectUrl = "/jersey/PurchaseCaseServlet";
	private final String forwardUrl = "/WEB-INF/pages/purchaseCase";
	private final String forwardListUrl = forwardUrl + "/list.jsp";
	private final String forwardAddUrl = forwardUrl + "/add.jsp";
	private final String forwardUpdateUrl = forwardUrl + "/update.jsp";
	private final String forwardAddCommodityUrl = forwardUrl + "/addCommodity.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		PurchaseCaseService service = new PurchaseCaseService();

		if (StringUtils.isEmpty(action)) {
			request.setAttribute("purchaseCaseList", service.getAll());
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("getProgressNotComplete".equals(action)) {
			request.setAttribute("purchaseCaseList", service.getAllOfNotComplete());
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("getOne".equals(action)) {
			StoreService storeService = new StoreService();
			request.setAttribute("stores", storeService.getStoreListByType(StoreType.store));
			request.setAttribute("shippingCompanys", storeService.getStoreListByType(StoreType.shippingCompany));
			try {
				// update
				Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
				request.setAttribute("purchaseCase", service.getOne(purchaseCaseId));
				request.getRequestDispatcher(forwardUpdateUrl).forward(request, response);
				return;
			} catch (NumberFormatException e) {
				// create
				request.getRequestDispatcher(forwardAddUrl).forward(request, response);
				return;
			}
		} else if ("getCommodityList".equals(action)) {
			// 取得已經在進貨單中的商品清單			
			try {
				Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
				Set<CommodityVO> commodityListInPurchaseCase = service.getCommoditysByPurchaseCaseId(purchaseCaseId);
				request.setAttribute("commodityListInPurchaseCase", commodityListInPurchaseCase);
			} catch (NumberFormatException e) {
				//沒有purchaseCaseId, 代表是新增進貨
				request.setAttribute("commodityListInPurchaseCase", new HashSet<CommodityVO>());
			}

			// 取得可以新增在進貨單中的商品清單
			List<CommodityVO> commodityListNotInPurchaseCase = service.getCommoditysByPurchaseCaseIdIsNull();
			request.setAttribute("commodityListNotInPurchaseCase", commodityListNotInPurchaseCase);

			request.getRequestDispatcher(forwardAddCommodityUrl).forward(request, response);
			return;
		}
		// else if ("getCommodityListByPurchaseCaseId".equals(action)) {
		// Integer purchaseCaseId =
		// Integer.valueOf(request.getParameter("purchaseCaseId"));
		// Set<CommodityVO> commodityList1 =
		// service.getCommoditysByPurchaseCaseId(purchaseCaseId);
		// request.setAttribute("commodityList1", commodityList1);
		// request.getRequestDispatcher(forwardListUrl).forward(request,
		// response);
		// } else if ("getCommodityListByPurchaseCaseIdIsNull".equals(action)) {
		// List<CommodityVO> list =
		// service.getCommoditysByPurchaseCaseIdIsNull();
		// request.setAttribute("commodityList2", list);
		// request.getRequestDispatcher(forwardListUrl).forward(request,
		// response);
		// }
		else if ("create".equals(action)) {
			PurchaseCaseVO purchaseCaseVO = new PurchaseCaseVO();
			LinkedHashSet<String> errors = new LinkedHashSet<String>();

			String store = request.getParameter("store").trim();
			String progress = request.getParameter("progress").trim();
			String shippingCompany = request.getParameter("shippingCompany").trim();
			String trackingNumber = request.getParameter("trackingNumber").trim();
			String trackingNumberLink = request.getParameter("trackingNumberLink").trim();
			String agent = request.getParameter("agent").trim();
			String agentTrackingNumber = request.getParameter("agentTrackingNumber").trim();
			String agentTrackingNumberLink = request.getParameter("agentTrackingNumberLink").trim();
			String description = request.getParameter("description").trim();
			Boolean isAbroad = Boolean.valueOf(request.getParameter("isAbroad"));
			try {
				purchaseCaseVO.setCost(Integer.valueOf(request.getParameter("cost")));
			} catch (NumberFormatException e) {
				errors.add("成本需為數字!");
			}
			try {
				purchaseCaseVO.setAgentCost(Integer.valueOf(request.getParameter("agentCost")));
			} catch (NumberFormatException e) {
				errors.add("國際運費需為數字!");
			}

			StoreVO storeVO = new StoreVO();
			storeVO.setName(store);
			purchaseCaseVO.setStore(storeVO);
			StoreVO shippingCompanyVO = new StoreVO();
			shippingCompanyVO.setName(shippingCompany);
			purchaseCaseVO.setShippingCompany(shippingCompanyVO);
			
			purchaseCaseVO.setProgress(progress);
			purchaseCaseVO.setTrackingNumber(trackingNumber);
			purchaseCaseVO.setTrackingNumberLink(trackingNumberLink);
			purchaseCaseVO.setAgent(agent);
			purchaseCaseVO.setAgentTrackingNumber(agentTrackingNumber);
			purchaseCaseVO.setAgentTrackingNumberLink(agentTrackingNumberLink);
			purchaseCaseVO.setDescription(description);
			purchaseCaseVO.setIsAbroad(isAbroad);

			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				request.setAttribute("purchaseCase", purchaseCaseVO);
				request.getRequestDispatcher(forwardAddUrl).forward(request, response);
				return;
			}

			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(System.currentTimeMillis()));
			purchaseCaseVO.setTime(time);

			Integer purchaseCaseId = service.create(purchaseCaseVO);
			HttpSession session = request.getSession();
			Integer[] commodityIds = (Integer[]) session.getAttribute("commodityIds");
			if (commodityIds != null)
				service.addPurchaseCaseIdToCommoditys(purchaseCaseId, commodityIds);
			session.removeAttribute("commodityIds");
			
			List<PurchaseCaseVO> purchaseCaseList = new ArrayList<>();
			purchaseCaseList.add(purchaseCaseVO);
			request.setAttribute("purchaseCaseList", purchaseCaseList);
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("update".equals(action)) {
			Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
			PurchaseCaseVO purchaseCaseVO = service.getOne(purchaseCaseId);
			LinkedHashSet<String> errors = new LinkedHashSet<String>();

			String store = request.getParameter("store").trim();
			String progress = request.getParameter("progress").trim();
			String shippingCompany = request.getParameter("shippingCompany").trim();
			String trackingNumber = request.getParameter("trackingNumber").trim();
			String trackingNumberLink = request.getParameter("trackingNumberLink").trim();
			String agent = request.getParameter("agent").trim();
			String agentTrackingNumber = request.getParameter("agentTrackingNumber").trim();
			String agentTrackingNumberLink = request.getParameter("agentTrackingNumberLink").trim();
			String description = request.getParameter("description").trim();
			Boolean isAbroad = Boolean.valueOf(request.getParameter("isAbroad"));
			Integer cost = Integer.valueOf(0);
			try {
				cost = Integer.valueOf(request.getParameter("cost"));
			} catch (NumberFormatException e) {
				errors.add("成本需為數字");
			}
			Integer agentCost = Integer.valueOf(0);
			try {
				agentCost = Integer.valueOf(request.getParameter("agentCost"));
			} catch (NumberFormatException e) {
				errors.add("國際運費需為數字!");
			}

			StoreVO storeVO = new StoreVO();
			storeVO.setName(store);
			purchaseCaseVO.setStore(storeVO);
			StoreVO shippingCompanyVO = new StoreVO();
			shippingCompanyVO.setName(shippingCompany);
			purchaseCaseVO.setShippingCompany(shippingCompanyVO);
			
			purchaseCaseVO.setProgress(progress);
			purchaseCaseVO.setTrackingNumber(trackingNumber);
			purchaseCaseVO.setTrackingNumberLink(trackingNumberLink);
			purchaseCaseVO.setAgent(agent);
			purchaseCaseVO.setAgentTrackingNumber(agentTrackingNumber);
			purchaseCaseVO.setAgentTrackingNumberLink(agentTrackingNumberLink);
			purchaseCaseVO.setDescription(description);
			purchaseCaseVO.setIsAbroad(isAbroad);
			purchaseCaseVO.setCost(cost);
			purchaseCaseVO.setAgentCost(agentCost);

			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				request.getRequestDispatcher(forwardUpdateUrl).forward(request, response);
				return;
			}

			service.update(purchaseCaseVO);
			
			List<PurchaseCaseVO> purchaseCaseList = new ArrayList<>();
			purchaseCaseList.add(purchaseCaseVO);
			request.setAttribute("purchaseCaseList", purchaseCaseList);
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;

//			if (Boolean.valueOf(request.getParameter("listOne")).booleanValue()) {
//				String id = request.getParameter("purchaseCaseId");
//				response.sendRedirect("/jersey/OtherServlet?action=purchaseCase&purchaseCaseId=" + id);
//			}
		} else if ("delete".equals(action)) {
			String[] purchaseCaseIds = request.getParameterValues("purchaseCaseIds");
			if (purchaseCaseIds != null) {
				Integer[] ids = new Integer[purchaseCaseIds.length];
				for (int i = 0; i < purchaseCaseIds.length; i++) {
					ids[i] = Integer.valueOf(purchaseCaseIds[i]);
				}
				service.delete(ids);
			}
			response.sendRedirect(sendRedirectUrl);
			return;
		} else if ("addPurchaseCaseId".equals(action)) {
			Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
			String[] commodityIds = request.getParameterValues("commodityIds");
			if (commodityIds == null) {
				// 沒有勾任何商品
				request.getRequestDispatcher(forwardAddCommodityUrl).forward(request, response);
				return;
			}

			Integer[] ids = new Integer[commodityIds.length];
			for (int i = 0; i < commodityIds.length; i++) {
				ids[i] = Integer.valueOf(commodityIds[i]);
			}

			service.addPurchaseCaseIdToCommoditys(purchaseCaseId, ids);
			List<Integer> idList = Arrays.asList(ids);
			// 直接改requestScope的值, 不要再跟DB讀一次
			List<CommodityVO> commodityListNotInPurchaseCase = (List<CommodityVO>) request
					.getAttribute("commodityListNotInPurchaseCase");
			List<CommodityVO> commodityListInPurchaseCase = (List<CommodityVO>) request
					.getAttribute("commodityListInPurchaseCase");
			List<CommodityVO> temp = new ArrayList<>();
			for (CommodityVO commodityVO : commodityListNotInPurchaseCase) {
				if (idList.contains(commodityVO.getCommodityId())) {
					temp.add(commodityVO);
				}
			}
			commodityListNotInPurchaseCase.removeAll(temp);
			commodityListInPurchaseCase.addAll(temp);

			request.getRequestDispatcher(forwardAddCommodityUrl).forward(request, response);
			return;
		} else if ("deletePurchaseCaseId".equals(action)) {
			String[] commodityIds = request.getParameterValues("commodityIds");
			if (commodityIds != null) {
				//有勾商品
				Integer[] ids = new Integer[commodityIds.length];
				for (int i = 0; i < commodityIds.length; i++) {
					ids[i] = Integer.valueOf(commodityIds[i]);
				}
				service.deletePurchasCaseIdFromCommoditys(ids);

				List<Integer> idList = Arrays.asList(ids);
				// 直接改requestScope的值, 不要再跟DB讀一次
				List<CommodityVO> commodityListNotInPurchaseCase = (List<CommodityVO>) request
						.getAttribute("commodityListNotInPurchaseCase");
				List<CommodityVO> commodityListInPurchaseCase = (List<CommodityVO>) request
						.getAttribute("commodityListInPurchaseCase");
				List<CommodityVO> temp = new ArrayList<>();
				for (CommodityVO commodityVO : commodityListInPurchaseCase) {
					if (idList.contains(commodityVO.getCommodityId())) {
						temp.add(commodityVO);
					}
				}
				commodityListInPurchaseCase.removeAll(temp);
				commodityListNotInPurchaseCase.addAll(temp);
			}

			request.getRequestDispatcher(forwardAddCommodityUrl).forward(request, response);
			return;
		}

	}
}
