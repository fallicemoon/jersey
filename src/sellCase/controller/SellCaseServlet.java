package sellCase.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import purchaseCase.model.PurchaseCaseVO;
import sellCase.model.SellCaseService;
import sellCase.model.SellCaseVO;
import sellCase.model.SellCaseWithBenefitVO;

public class SellCaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String sendRedirectUrl = "/jersey/SellCaseServlet";
	private final String forwardUrl = "/WEB-INF/pages/sellCase";
	private final String forwardListUrl = forwardUrl + "/list.jsp";
	private final String forwardAddUrl = forwardUrl + "/add.jsp";
	private final String forwardUpdateUrl = forwardUrl + "/update.jsp";
	private final String forwardAddPurchaseCaseUrl = forwardUrl + "/addPurchaseCase.jsp";
	private SellCaseService service = new SellCaseService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		//判斷第一頁還第二頁
		String sellCasePage = (String)session.getAttribute("sellCasePage");
		String changePage = request.getParameter("changePage");
		if (sellCasePage==null) {
			session.setAttribute("sellCasePage", "before");
		}
		//怒換頁
		if ("true".equals(changePage)) {
			sellCasePage = sellCasePage.equals("after")?"before":"after";
			session.setAttribute("sellCasePage", sellCasePage);
		}
		
		String action = request.getParameter("action");		
		Set<String> errors = new LinkedHashSet<String>();		

		if (StringUtils.isEmpty(action)) {
			request.setAttribute("sellCaseList", service.getAll());
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("getUncollectedNotZero".equals(action)) {
			request.setAttribute("sellCaseList", service.getUncollectedNotZero());
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("getIsClosed".equals(action)) {
			request.setAttribute("sellCaseList", service.getIsClosed());
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("getNotClosed".equals(action)) {
			request.setAttribute("sellCaseList", service.getNotClosed());
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("getOne".equals(action)) {
			try {
				// update
				Integer sellCaseId = Integer.valueOf(request.getParameter("sellCaseId"));
				request.setAttribute("sellCase", service.getOne(sellCaseId));
				request.getRequestDispatcher(forwardUpdateUrl).forward(request, response);
				return;
			} catch (NumberFormatException e) {
				// create
				request.getRequestDispatcher(forwardAddUrl).forward(request, response);
				return;
			}
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
				request.setAttribute("errors", errors);
				request.setAttribute("sellCase", sellCaseVO);
				request.getRequestDispatcher(forwardAddUrl).forward(request, response);
				return;
			}

			Integer sellCaseId = service.create(sellCaseVO);


			Integer[] purchaseCaseIds = (Integer[]) session.getAttribute("purchaseCaseIds");
			if (purchaseCaseIds != null)
				service.addSellCaseIdToPurchaseCases(sellCaseId, purchaseCaseIds);
			session.removeAttribute("purchaseCaseIds");

			List<SellCaseWithBenefitVO> sellCaseList = new ArrayList<>();
			sellCaseList.add(service.getSellCaseWithBenefitVo(sellCaseVO));
			request.setAttribute("sellCaseList", sellCaseList);
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("update".equals(action)) {
			Integer sellCaseId = Integer.valueOf(request.getParameter("sellCaseId"));
			SellCaseVO sellCaseVO = new SellCaseVO();

			sellCaseVO.setSellCaseId(sellCaseId);
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
				request.setAttribute("errors", errors);
				request.setAttribute("sellCase", sellCaseVO);
				request.getRequestDispatcher(forwardUpdateUrl).forward(request, response);
				return;
			}

			service.update(sellCaseVO);

			// if
			// (Boolean.valueOf(request.getParameter("listOne")).booleanValue())
			// {
			// String id = request.getParameter("sellCaseId");
			// response.sendRedirect("/jersey/OtherServlet?action=sellCase&sellCaseId="
			// + id);
			// return;
			// }

			// String page = request.getParameter("page");
			// response.sendRedirect("/jersey/sellCase/list.jsp?action=getAll&page="
			// + page);
			List<SellCaseWithBenefitVO> sellCaseList = new ArrayList<>();
			sellCaseList.add(service.getSellCaseWithBenefitVo(sellCaseVO));
			request.setAttribute("sellCaseList", sellCaseList);
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("delete".equals(action)) {
			String[] sellCaseIds = request.getParameterValues("sellCaseIds");
			if (sellCaseIds != null) {
				Integer[] ids = new Integer[sellCaseIds.length];
				for (int i = 0; i < sellCaseIds.length; i++) {
					ids[i] = Integer.valueOf(sellCaseIds[i]);
				}
				service.delete(ids);
			}
			response.sendRedirect(sendRedirectUrl);
			return;			
		} else if ("addSellCaseId".equals(action)) {
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
				session.setAttribute("purchaseCaseIds", ids);
				response.sendRedirect("/jersey/sellCase/add.jsp");
			} else {
				service.addSellCaseIdToPurchaseCases(sellCaseId, ids);
				response.sendRedirect("/jersey/sellCase/addPurchaseCase.jsp?sellCaseId=" + sellCaseId);
			}
			return;
		} else if ("deleteSellCaseId".equals(action)) {
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
