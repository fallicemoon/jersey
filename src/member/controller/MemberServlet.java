package member.controller;

import java.io.IOException;
import java.util.LinkedHashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.MemberService;

public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String forwardUrl = "/WEB-INF/pages";
	private final String forwardLoginUrl = forwardUrl + "/login.jsp";
	private final String sendRedirectUrl = "/jersey/MemberServlet";
	private final String sendRedirectIndexUrl = "/jersey/index.jsp";
	private final MemberService memberService = new MemberService();
	
	private final String login = "login";
	private final String logout = "logout";
	private final String ok = "ok";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		LinkedHashSet<String> errors = new LinkedHashSet<>();
		if (!ok.equals(session.getAttribute("login"))) {
			request.getRequestDispatcher(forwardLoginUrl).forward(request, response);
			return;
		}
		
		if (login.equals(action)) {
			if (processLogin(request)) {
				response.sendRedirect(sendRedirectIndexUrl);
			} else {
				errors.add("廢物, 帳號密碼錯了");
				request.setAttribute("errors", errors);
				request.getRequestDispatcher(forwardLoginUrl).forward(request, response);
			}
		} else if (logout.equals(request.getParameter("action"))) {
			logout(session);
			request.getRequestDispatcher(forwardLoginUrl).forward(request, response);
		}
	}

	private boolean processLogin(HttpServletRequest request)
			throws ServletException, IOException {
		boolean login = false;
		HttpSession session = request.getSession();
		String _user = getServletContext().getInitParameter("user");
		String _password = getServletContext().getInitParameter("password");
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		if (user.equals(_user) && password.equals(_password)) {
			session.setAttribute("login", ok);
			login = true;
		}
		return login;
	}

	private void logout(HttpSession session) {
		session.invalidate();
	}
}
