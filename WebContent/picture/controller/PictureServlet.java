package picture.controller;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;

import picture.model.PictureService;

public class PictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String forwardUrl = "/WEB-INF/pages/picture";
	private final String forwardUploadPictureUrl = forwardUrl + "/uploadPicture.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		Set<String> errors = new LinkedHashSet<String>();
		HttpSession session = request.getSession();
		
		PictureService service = new PictureService();
		
		if (StringUtils.isEmpty(action)) {
			try {
				service.uploadPicture(request);
			} catch (SizeLimitExceededException e) {
				e.printStackTrace();
				errors.add("上傳檔案需小於20MB!");				
				session.setAttribute("errors", errors);
			} catch (FileUploadException e) {
				e.printStackTrace();

				if ("extensionName".equals(e.getMessage())) {
					errors.add("副檔名須為jpg, gif, png 三者其中之一");
					session = request.getSession();
					session.setAttribute("errors", errors);
				}
			}

			response.sendRedirect(
					"/jersey/picture/uploadPicture.jsp?commodityId=" + request.getParameter("commodityId"));
		} else if ("getPicturesBase64".equals(action)) {
			Integer commodityId = Integer.valueOf(request.getParameter("commodityId"));
			Map<Integer, String> pictureBase64Map = service.getPictureBase64(commodityId);
			request.setAttribute("pictureBase64Map", pictureBase64Map);
			return;
		} else if ("getPictureIds".equals(action)) {
			Integer commodityId = Integer.valueOf(request.getParameter("commodityId"));
			Set<Integer> pictureIds = service.getPictureIds(commodityId);
			request.setAttribute("pictureIds", pictureIds);
			return;
		} else if ("getPicture".equals(action)) {
			Integer pictureId = Integer.valueOf(request.getParameter("pictureId"));
			response.setContentType("image/*");
			service.getPicrture(pictureId, response.getOutputStream());
			return;
		} else if ("delete".equals(action)) {
			String[] pictureIds = request.getParameterValues("pictureId");
			String commodityId = request.getParameter("commodityId");
			if (pictureIds == null) {
				response.sendRedirect(
						request.getContextPath() + "/picture/uploadPicture.jsp?commodityId=" + commodityId);
				return;
			}
			service.deletePictures(pictureIds);
			response.sendRedirect(request.getContextPath() + "/picture/uploadPicture.jsp?commodityId=" + commodityId);
		} else if ("download".equals(action)) {
			String[] pictureIds = request.getParameterValues("pictureId");
			String commodityId = request.getParameter("commodityId");
			if (pictureIds == null) {
				response.sendRedirect(
						request.getContextPath() + "/picture/uploadPicture.jsp?commodityId=" + commodityId);
				return;
			}
			response.setHeader("Content-disposition", "attachment; filename=" + commodityId + ".zip");
			service.getPicturesZip(pictureIds, response.getOutputStream());
		} else if ("downloadAll".equals(action)) {
			String commodityId = request.getParameter("commodityId");
			response.setHeader("Content-disposition", "attachment; filename=" + commodityId + "All.zip");
			service.getPicturesZip(commodityId, response.getOutputStream());
		}
	}
}
