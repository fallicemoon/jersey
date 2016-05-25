package purchaseCase.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import commodity.model.CommodityVO;
import purchaseCase.model.PurchaseCaseService;
import purchaseCase.model.PurchaseCaseVO;

public class PurchaseCaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String action;
	private LinkedHashSet<String> errors;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.action = request.getParameter("action");
		PurchaseCaseService service = new PurchaseCaseService();

		errors = new LinkedHashSet<String>();

		if (this.action.isEmpty()) {
			request.setAttribute("purchaseCaseList", service.getAll());
		} else if ("getProgressNotComplete".equals(this.action)) {
			request.setAttribute("purchaseCaseList", service.getAllOfNotComplete());
		} else if ("getOne".equals(this.action)) {
			try {
				Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
				request.setAttribute("purchaseCase", service.getOne(purchaseCaseId));
			} catch (NumberFormatException e) {
				this.errors.add("進貨編號須為數字!");
			}
		} else if ("getCommodityListByPurchaseCaseId".equals(this.action)) {
			Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
			Set<CommodityVO> commodityList1 = service.getCommoditysByPurchaseCaseId(purchaseCaseId);
			request.setAttribute("commodityList1", commodityList1);
		} else if ("getCommodityListByPurchaseCaseIdIsNull".equals(this.action)) {
			List<CommodityVO> list = service.getCommoditysByPurchaseCaseIdIsNull();
			request.setAttribute("commodityList2", list);
		} else if ("create".equals(this.action)) {
			PurchaseCaseVO purchaseCaseVO = new PurchaseCaseVO();

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
				this.errors.add("成本需為數字!");
			}
			Integer agentCost = Integer.valueOf(0);
			try {
				agentCost = Integer.valueOf(request.getParameter("agentCost"));
			} catch (NumberFormatException e) {
				this.errors.add("國際運費需為數字!");
			}

			purchaseCaseVO.setStore(store);
			purchaseCaseVO.setProgress(progress);
			purchaseCaseVO.setShippingCompany(shippingCompany);
			purchaseCaseVO.setTrackingNumber(trackingNumber);
			purchaseCaseVO.setTrackingNumberLink(trackingNumberLink);
			purchaseCaseVO.setAgent(agent);
			purchaseCaseVO.setAgentTrackingNumber(agentTrackingNumber);
			purchaseCaseVO.setAgentTrackingNumberLink(agentTrackingNumberLink);
			purchaseCaseVO.setDescription(description);
			purchaseCaseVO.setIsAbroad(isAbroad);
			purchaseCaseVO.setCost(cost);
			purchaseCaseVO.setAgentCost(agentCost);

			if (!this.errors.isEmpty()) {
				HttpSession session = request.getSession();

				session.setAttribute("errors", this.errors);
				response.sendRedirect("/jersey/purchaseCase/add.jsp");
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
		} else if ("update".equals(this.action)) {
			Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
			PurchaseCaseVO purchaseCaseVO = service.getOne(purchaseCaseId);

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
				this.errors.add("成本需為數字");
			}
			Integer agentCost = Integer.valueOf(0);
			try {
				agentCost = Integer.valueOf(request.getParameter("agentCost"));
			} catch (NumberFormatException e) {
				this.errors.add("國際運費需為數字!");
			}

			purchaseCaseVO.setStore(store);
			purchaseCaseVO.setProgress(progress);
			purchaseCaseVO.setShippingCompany(shippingCompany);
			purchaseCaseVO.setTrackingNumber(trackingNumber);
			purchaseCaseVO.setTrackingNumberLink(trackingNumberLink);
			purchaseCaseVO.setAgent(agent);
			purchaseCaseVO.setAgentTrackingNumber(agentTrackingNumber);
			purchaseCaseVO.setAgentTrackingNumberLink(agentTrackingNumberLink);
			purchaseCaseVO.setDescription(description);
			purchaseCaseVO.setIsAbroad(isAbroad);
			purchaseCaseVO.setCost(cost);
			purchaseCaseVO.setAgentCost(agentCost);

			if (!this.errors.isEmpty()) {
				HttpSession session = request.getSession();
				session.setAttribute("errors", this.errors);
				response.sendRedirect("/jersey/purchaseCase/update.jsp?purchaseCaseId=" + purchaseCaseId);
				return;
			}

			service.update(purchaseCaseVO);

			if (Boolean.valueOf(request.getParameter("listOne")).booleanValue()) {
				String id = request.getParameter("purchaseCaseId");
				response.sendRedirect("/jersey/OtherServlet?action=purchaseCase&purchaseCaseId=" + id);
			}
		} else if ("delete".equals(this.action)) {
			String[] purchaseCaseIds = request.getParameterValues("purchaseCaseIds");
			if (purchaseCaseIds == null) {
				response.sendRedirect("/jersey/purchaseCase/list.jsp");
				return;
			}
			Integer[] ids = new Integer[purchaseCaseIds.length];
			for (int i = 0; i < purchaseCaseIds.length; i++) {
				ids[i] = Integer.valueOf(purchaseCaseIds[i]);
			}
			service.delete(ids);
		} else {
			if ("addPurchaseCaseId".equals(this.action)) {
				Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
				String[] commodityIds = request.getParameterValues("commodityIds");
				if (commodityIds == null) {
					response.sendRedirect("/jersey/purchaseCase/addCommodity.jsp?purchaseCaseId=" + purchaseCaseId);
					return;
				}
				Integer[] ids = new Integer[commodityIds.length];
				for (int i = 0; i < commodityIds.length; i++) {
					ids[i] = Integer.valueOf(commodityIds[i]);
				}

				if (purchaseCaseId.intValue() == 0) {
					HttpSession session = request.getSession();
					session.setAttribute("commodityIds", ids);
					response.sendRedirect("/jersey/purchaseCase/add.jsp");
				} else {
					service.addPurchaseCaseIdToCommoditys(purchaseCaseId, ids);
					response.sendRedirect("/jersey/purchaseCase/addCommodity.jsp?purchaseCaseId=" + purchaseCaseId);
				}
				return;
			}
			if ("deletePurchaseCaseId".equals(this.action)) {
				String purchaseCaseId = request.getParameter("purchaseCaseId");
				String[] commodityIds = request.getParameterValues("commodityIds");
				if (commodityIds == null) {
					response.sendRedirect("/jersey/purchaseCase/addCommodity.jsp?purchaseCaseId=" + purchaseCaseId);
					return;
				}
				Integer[] ids = new Integer[commodityIds.length];
				for (int i = 0; i < commodityIds.length; i++) {
					ids[i] = Integer.valueOf(commodityIds[i]);
				}
				service.deletePurchasCaseIdFromCommoditys(ids);
				response.sendRedirect("/jersey/purchaseCase/addCommodity.jsp?purchaseCaseId=" + purchaseCaseId);
				return;
			}
		}
		response.sendRedirect("/jersey/purchaseCase/list.jsp");
	}
}
