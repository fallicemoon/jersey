package sellCase.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import purchaseCase.model.PurchaseCaseVO;
import sellCase.model.SellCaseService;
import sellCase.model.SellCaseVO;

public class SellCaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String forwardUrl = "/WEB-INF/pages/sellCase";
	private final String forwardListUrl = forwardUrl + "/list.jsp";
	private final String forwardAddUrl = forwardUrl + "/add.jsp";
	private final String forwardUpdateUrl = forwardUrl + "/update.jsp";
	private final String forwardAddPurchaseCaseUrl = forwardUrl + "/addPurchaseCase.jsp";


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		SellCaseService service = new SellCaseService();
		Set<String> errors = new LinkedHashSet<String>();

		if ("getAll".equals(action)) {
			request.setAttribute("sellCaseList", service.getAll());
		} else if ("getUncollectedNotZero".equals(action)) {
			request.setAttribute("sellCaseList", service.getUncollectedNotZero());
		} else if ("getIsClosed".equals(action)) {
			request.setAttribute("sellCaseList", service.getIsClosed());
		} else if ("getNotClosed".equals(action)) {
			request.setAttribute("sellCaseList", service.getNotClosed());
		} else if ("getOne".equals(action)) {
			Integer sellCaseId = Integer.valueOf(request.getParameter("sellCaseId"));
			SellCaseVO sellCaseVO = service.getOne(sellCaseId);

			Integer collected = sellCaseVO.getCollected();
			Integer income = sellCaseVO.getIncome();
			Integer transportCost = sellCaseVO.getTransportCost();
			Integer costs = Integer.valueOf(0);
			Integer agentCosts = Integer.valueOf(0);

			Set<PurchaseCaseVO> purchaseCases = sellCaseVO.getPurchaseCases();
			for (PurchaseCaseVO purchaseCaseVO : purchaseCases) {
				costs = Integer.valueOf(costs.intValue() + purchaseCaseVO.getCost().intValue());
				agentCosts = Integer.valueOf(agentCosts.intValue() + purchaseCaseVO.getAgentCost().intValue());
			}

			Integer benefit = Integer.valueOf(
					collected.intValue() - transportCost.intValue() - costs.intValue() - agentCosts.intValue());
			Integer estimateBenefit = Integer
					.valueOf(income.intValue() - transportCost.intValue() - costs.intValue() - agentCosts.intValue());

			request.setAttribute("sellCase", service.getOne(sellCaseId));
			request.setAttribute("benefit", benefit);
			request.setAttribute("estimateBenefit", estimateBenefit);
			request.setAttribute("costs", costs);
			request.setAttribute("agentCosts", agentCosts);
		} else if ("getPurchaseCaseListBySellCaseId".equals(action)) {
			Integer purchaseCaseId = Integer.valueOf(request.getParameter("sellCaseId"));
			request.setAttribute("purchaseCaseList1", service.getPurchaseCasesBySellCaseId(purchaseCaseId));
		} else if ("getPurchaseCaseListBySellCaseIdIsNull".equals(action)) {
			List<PurchaseCaseVO> list = service.getPurchaseCasesBySellCaseIdIsNull();
			request.setAttribute("purchaseCaseList2", list);
		} else if ("create".equals(action)) {
			SellCaseVO sellCaseVO = new SellCaseVO();

			sellCaseVO.setAddressee(request.getParameter("addressee").trim());
			sellCaseVO.setPhone(request.getParameter("phone").trim());
			sellCaseVO.setAddress(request.getParameter("address").trim());
			sellCaseVO.setDescription(request.getParameter("description").trim());
			sellCaseVO.setTrackingNumber(request.getParameter("trackingNumber").trim());
			sellCaseVO.setTransportMethod(request.getParameter("transportMethod").trim());
			sellCaseVO.setIsShipping(Boolean.valueOf(request.getParameter("isShipping")));
			try {
				sellCaseVO.setIncome(Integer.valueOf(request.getParameter("income").trim()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				errors.add("總價需為數字!");
			}
			try {
				sellCaseVO.setTransportCost(Integer.valueOf(request.getParameter("transportCost").trim()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				errors.add("運費需為數字!");
			}
			try {
				sellCaseVO.setCollected(Integer.valueOf(request.getParameter("collected").trim()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				errors.add("已收額須為數字");
			}
			sellCaseVO.setIsChecked(Boolean.valueOf(request.getParameter("isChecked")));

			if (!errors.isEmpty()) {
				HttpSession session = request.getSession();

				session.setAttribute("errors", errors);
				response.sendRedirect("/jersey/sellCase/add.jsp");
				return;
			}

			Integer uncollected = Integer
					.valueOf(sellCaseVO.getIncome().intValue() - sellCaseVO.getCollected().intValue());
			sellCaseVO.setUncollected(uncollected);

			if (sellCaseVO.getIsShipping().booleanValue()) {
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sellCaseVO.setShippingTime(sdf.format(timestamp));
			}

			if ((uncollected.intValue() == 0) && (sellCaseVO.getIsChecked().booleanValue())) {
//				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//TODO 測試
				sellCaseVO.setCloseTime(new Date());
			}

			Integer sellCaseId = service.create(sellCaseVO);
			HttpSession session = request.getSession();
			Integer[] purchaseCaseIds = (Integer[]) session.getAttribute("purchaseCaseIds");
			if (purchaseCaseIds != null)
				service.addSellCaseIdToPurchaseCases(sellCaseId, purchaseCaseIds);
			session.removeAttribute("purchaseCaseIds");
		} else {
			if ("update".equals(action)) {
				Integer sellCaseId = Integer.valueOf(request.getParameter("sellCaseId"));
				SellCaseVO sellCaseVO = service.getOne(sellCaseId);

				sellCaseVO.setAddressee(request.getParameter("addressee").trim());
				sellCaseVO.setPhone(request.getParameter("phone").trim());
				sellCaseVO.setAddress(request.getParameter("address").trim());
				sellCaseVO.setDescription(request.getParameter("description").trim());
				sellCaseVO.setTrackingNumber(request.getParameter("trackingNumber").trim());
				sellCaseVO.setTransportMethod(request.getParameter("transportMethod").trim());
				sellCaseVO.setIsShipping(Boolean.valueOf(request.getParameter("isShipping")));
				try {
					sellCaseVO.setIncome(Integer.valueOf(request.getParameter("income").trim()));
				} catch (NumberFormatException e) {
					e.printStackTrace();
					errors.add("總價需為數字!");
				}
				try {
					sellCaseVO.setTransportCost(Integer.valueOf(request.getParameter("transportCost").trim()));
				} catch (NumberFormatException e) {
					e.printStackTrace();
					errors.add("運費需為數字!");
				}
				try {
					sellCaseVO.setCollected(Integer.valueOf(request.getParameter("collected").trim()));
				} catch (NumberFormatException e) {
					e.printStackTrace();
					errors.add("已收額須為數字");
				}
				sellCaseVO.setIsChecked(Boolean.valueOf(request.getParameter("isChecked")));

				if (!errors.isEmpty()) {
					HttpSession session = request.getSession();
					session.setAttribute("errors", errors);
					response.sendRedirect("/jersey/sellCase/update.jsp?sellCaseId=" + sellCaseId);
					return;
				}

				Integer uncollected = Integer
						.valueOf(sellCaseVO.getIncome().intValue() - sellCaseVO.getCollected().intValue());
				sellCaseVO.setUncollected(uncollected);

				if (sellCaseVO.getIsShipping().booleanValue()) {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					sellCaseVO.setShippingTime(sdf.format(timestamp));
				} else {
					sellCaseVO.setShippingTime(null);
				}

				if ((uncollected.intValue() == 0) && (sellCaseVO.getIsChecked().booleanValue())) {
//					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					//TODO 測試
					sellCaseVO.setCloseTime(new Date());
				} else {
					sellCaseVO.setCloseTime(null);
				}

				service.update(sellCaseVO);

				if (Boolean.valueOf(request.getParameter("listOne")).booleanValue()) {
					String id = request.getParameter("sellCaseId");
					response.sendRedirect("/jersey/OtherServlet?action=sellCase&sellCaseId=" + id);
					return;
				}

				String page = request.getParameter("page");
				response.sendRedirect("/jersey/sellCase/list.jsp?action=getAll&page=" + page);
				return;
			}
			if ("delete".equals(action)) {
				String[] sellCaseIds = request.getParameterValues("sellCaseIds");
				String page = request.getParameter("page");
				if (sellCaseIds == null) {
					response.sendRedirect("/jersey/sellCase/list.jsp?action=getAll&page=" + page);
					return;
				}
				Integer[] ids = new Integer[sellCaseIds.length];
				for (int i = 0; i < sellCaseIds.length; i++) {
					ids[i] = Integer.valueOf(sellCaseIds[i]);
				}
				service.delete(ids);
			} else {
				if ("addSellCaseId".equals(action)) {
					Integer sellCaseId = Integer.valueOf(request.getParameter("sellCaseId"));
					String[] purchaseCaseIds = request.getParameterValues("purchaseCaseIds");
					if (purchaseCaseIds == null) {
						response.sendRedirect("/jersey/sellCase/addPurchaseCase.jsp?sellCaseId=" + sellCaseId);
						return;
					}
					Integer[] ids = new Integer[purchaseCaseIds.length];
					for (int i = 0; i < purchaseCaseIds.length; i++) {
						ids[i] = Integer.valueOf(purchaseCaseIds[i]);
					}

					if (sellCaseId.intValue() == 0) {
						HttpSession session = request.getSession();
						session.setAttribute("purchaseCaseIds", ids);
						response.sendRedirect("/jersey/sellCase/add.jsp");
					} else {
						service.addSellCaseIdToPurchaseCases(sellCaseId, ids);
						response.sendRedirect("/jersey/sellCase/addPurchaseCase.jsp?sellCaseId=" + sellCaseId);
					}
					return;
				}
				if ("deleteSellCaseId".equals(action)) {
					String sellCaseId = request.getParameter("sellCaseId");
					String[] purchaseCaseIds = request.getParameterValues("purchaseCaseIds");
					if (purchaseCaseIds == null) {
						response.sendRedirect("/jersey/sellCase/addPurchaseCase.jsp?sellCaseId=" + sellCaseId);
						return;
					}
					Integer[] ids = new Integer[purchaseCaseIds.length];
					for (int i = 0; i < purchaseCaseIds.length; i++) {
						ids[i] = Integer.valueOf(purchaseCaseIds[i]);
					}

					service.deleteSellCaseIdFromPurchaseCases(ids);
					response.sendRedirect("/jersey/sellCase/addPurchaseCase.jsp?sellCaseId=" + sellCaseId);
					return;
				}
			}
		}
		response.sendRedirect("/jersey/sellCase/list.jsp?action=getAll&page=1");
	}
}
