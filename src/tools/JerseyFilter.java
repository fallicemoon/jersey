package tools;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class JerseyFilter implements Filter {
	private String ENCODING;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		System.out.println(new Date() + " from " + httpServletRequest.getRemoteAddr());
		httpServletRequest = new EncodingWrapper(httpServletRequest, this.ENCODING);

		HttpSession session = httpServletRequest.getSession();

		if (!"OK".equals(session.getAttribute("login"))) {
			httpServletResponse.sendRedirect("/jersey/login.jsp");
			return;
		}

		chain.doFilter(httpServletRequest, httpServletResponse);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.ENCODING = fConfig.getInitParameter("encoding");
	}
}