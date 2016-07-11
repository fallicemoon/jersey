package accounting.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sellCase.model.SellCaseService;
import sellCase.model.SellCaseVO;


public class AccountingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String forwardUrl = "/WEB-INF/pages/accounting";
	
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		LinkedHashSet<String> errors = new LinkedHashSet<>();
		
		if ("accounting".equals(action)) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			String startString = request.getParameter("start");
			String endString = request.getParameter("end");
			errors = new LinkedHashSet<String>();
			if (startString == null)
				errors.add("請選擇開始時間");
			if (endString == null)
				errors.add("請選擇結束時間");

			Date start = new Date();
			Date end = new Date();
			try {
				start = sdf.parse(request.getParameter("start").replaceAll("AM", "上午").replaceAll("PM", "下午"));
			} catch (ParseException e) {
				errors.add("開始時間請用下拉式選單選擇，勿自行輸入");
			}
			try {
				end = sdf.parse(request.getParameter("end").replaceAll("AM", "上午").replaceAll("PM", "下午"));
			} catch (ParseException e) {
				errors.add("結束時間請用下拉式選單選擇，勿自行輸入");
			}

			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				response.sendRedirect("/jersey/accounting/datePicker.jsp");
				return;
			}

			SellCaseService sellCaseService = new SellCaseService();
			List<SellCaseVO> list = sellCaseService.getBetweenCloseTime(start, end);
			request.setAttribute("start", sdf.format(start));
			request.setAttribute("end", sdf.format(end));
			request.setAttribute("sellCaseList", list);

			request.getRequestDispatcher("/accounting/accounting.jsp").forward(request, response);
		}
	}

}
