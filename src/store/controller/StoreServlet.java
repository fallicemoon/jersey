package store.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import store.model.StoreDAO;
import store.model.StoreVO;

public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String forwardUrl = "/WEB-INF/pages/store";
	private final String forwardListUrl = forwardUrl + "/list.jsp";
	private final String forwardAddUrl = forwardUrl + "/add.jsp";
	private final String forwardUpdateUrl = forwardUrl + "/update.jsp";


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		StoreDAO storeDAO = new StoreDAO();

		if (StringUtils.isEmpty(action)) {
			request.setAttribute("storeList", storeDAO.getAll());
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("getOne".equals(action)) {
			String forwardUrl;
			try {
				Integer storeId = Integer.valueOf(request.getParameter("storeId"));
				request.setAttribute("store", storeDAO.getOne(storeId));
				forwardUrl = forwardUpdateUrl;
			} catch (NumberFormatException e) {
				forwardUrl = forwardAddUrl;
			}
			request.getRequestDispatcher(forwardUrl).forward(request, response);
			return;
		} else if ("create".equals(action)) {
			StoreVO storeVO = (StoreVO) request.getAttribute("store");
			storeDAO.create(storeVO);
		} else if ("update".equals(action)) {
			StoreVO storeVO = (StoreVO) request.getAttribute("store");
			storeDAO.update(storeVO);
		} else if ("delete".equals(action)) {
			String[] storeIds = request.getParameterValues("storeIds");
			if (storeIds == null) {
				response.sendRedirect("/jersey/store/list.jsp");
				return;
			}
			Integer[] ids = new Integer[storeIds.length];
			for (int i = 0; i < storeIds.length; i++) {
				ids[i] = Integer.valueOf(storeIds[i]);
			}
			storeDAO.delete(ids);
		} else if ("getStores".equals(action)) {
			request.setAttribute("stores", storeDAO.getStoreListByType("store"));
		} else if ("getShippingCompanys".equals(action)) {
			request.setAttribute("shippingCompanys",
					storeDAO.getStoreListByType("shippingCompany"));
		}

		response.sendRedirect("/jersey/store/list.jsp");
	}
}

