package store.controller;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.model.StoreDAO;
import store.model.StoreVO;

public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String action;
	private String error;
	private Connection connection;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		this.action = request.getParameter("action");
		this.connection = ((Connection) session.getAttribute("connection"));
		StoreDAO storeDAO = new StoreDAO();

		if ("getAll".equals(this.action)) {
			request.setAttribute("storeList", storeDAO.getAll());
		} else if ("getOne".equals(this.action)) {
			Integer storeId = Integer.valueOf(request.getParameter("storeId"));
			request.setAttribute("store", storeDAO.getOne(storeId));
		} else if ("create".equals(this.action)) {
			StoreVO storeVO = (StoreVO) request.getAttribute("store");
			storeDAO.create(storeVO);
		} else if ("update".equals(this.action)) {
			StoreVO storeVO = (StoreVO) request.getAttribute("store");
			storeDAO.update(storeVO);
		} else if ("delete".equals(this.action)) {
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
		} else if ("getStores".equals(this.action)) {
			request.setAttribute("stores", storeDAO.getStoreListByType("store"));
		} else if ("getShippingCompanys".equals(this.action)) {
			request.setAttribute("shippingCompanys",
					storeDAO.getStoreListByType("shippingCompany"));
		}

		response.sendRedirect("/jersey/store/list.jsp");
	}
}

