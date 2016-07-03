package tools;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("login".equals(action)) {
			processLogin(request, response);
			response.sendRedirect("/jersey/index.jsp");
			return;
		}
		if ("logout".equals(request.getParameter("action"))) {
			logout(request);
			response.sendRedirect("/jersey/login.jsp");
			return;
		}
	}

	private void processLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String _user = getServletContext().getInitParameter("user");
		String _password = getServletContext().getInitParameter("password");
		String user = request.getParameter("user");
		String password = request.getParameter("password");

		if (!user.equals(_user))
			session.setAttribute("errorMessage", "wrong user name");
		else if (!password.equals(_password))
			session.setAttribute("errorMessage", "wrong password");
		else
			session.setAttribute("login", "OK");
	}

	private void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
}
