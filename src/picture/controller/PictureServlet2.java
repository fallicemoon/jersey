package picture.controller;

import java.io.IOException;
import java.io.OutputStream;
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

import picture.model.PictureService;

public class PictureServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String action;
	private Set<String> errors;
	private HttpSession session;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.action = request.getParameter("action");
		PictureService service = new PictureService();
		errors = new LinkedHashSet<String>();
		if (this.action == null) {
			try {
				service.uploadPicture(request);
			} catch (SizeLimitExceededException e) {
				e.printStackTrace();

				this.errors.add("上傳檔案需小於20MB!");
				this.session = request.getSession();
				this.session.setAttribute("errors", this.errors);
			} catch (FileUploadException e) {
				e.printStackTrace();

				if ("extensionName".equals(e.getMessage())) {
					this.errors.add("副檔名須為jpg, gif, png 三者其中之一");
					this.session = request.getSession();
					this.session.setAttribute("errors", this.errors);
				}
			}

			response.sendRedirect(
					"/jersey/picture/uploadPicture.jsp?commodityId=" + request.getParameter("commodityId"));
		} else if ("getPicturesBase64".equals(this.action)) {
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
		} else if ("delete".equals(this.action)) {
			String[] pictureIds = request.getParameterValues("pictureId");
			String commodityId = request.getParameter("commodityId");
			if (pictureIds == null) {
				response.sendRedirect(
						request.getContextPath() + "/picture/uploadPicture.jsp?commodityId=" + commodityId);
				return;
			}
			service.deletePictures(pictureIds);
			response.sendRedirect(request.getContextPath() + "/picture/uploadPicture.jsp?commodityId=" + commodityId);
		} else if ("download".equals(this.action)) {
			String[] pictureIds = request.getParameterValues("pictureId");
			String commodityId = request.getParameter("commodityId");
			if (pictureIds == null) {
				response.sendRedirect(
						request.getContextPath() + "/picture/uploadPicture.jsp?commodityId=" + commodityId);
				return;
			}
			response.setHeader("Content-disposition", "attachment; filename=" + commodityId + ".zip");
			service.getPicturesZip(pictureIds, response.getOutputStream());
		} else if ("downloadAll".equals(this.action)) {
			String commodityId = request.getParameter("commodityId");
			response.setHeader("Content-disposition", "attachment; filename=" + commodityId + "All.zip");
			service.getPicturesZip(commodityId, response.getOutputStream());
		}
	}
}
