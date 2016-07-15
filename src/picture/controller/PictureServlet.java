package picture.controller;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;

import commodity.model.CommodityService;
import picture.model.PictureService;

public class PictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String sendRedirectUrl = "/jersey/PictureServlet";
	private final String forwardUrl = "/WEB-INF/pages/picture";
	private final String forwardUploadPictureUrl = forwardUrl + "/uploadPicture.jsp";
	private final PictureService service = new PictureService();
	private final CommodityService commodityService = new CommodityService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		//取得圖片網址, 不需要forward
		if ("getPicture".equals(action)) {
			Integer pictureId = Integer.valueOf(request.getParameter("pictureId"));
			response.setContentType("image/*");
			service.getPicrture(pictureId, response.getOutputStream());
			return;
		}
		
		Integer commodityId;
		try {
			commodityId = Integer.valueOf(request.getParameter("commodityId"));
		} catch (NumberFormatException e) {
			request.getRequestDispatcher(forwardUploadPictureUrl).forward(request, response);
			return;
		}
		Set<String> errors = new LinkedHashSet<String>();

		if (StringUtils.isEmpty(action)) {
			Set<Integer> pictureIds = service.getPictureIds(commodityId);
			request.setAttribute("pictureIds", pictureIds);
			request.setAttribute("commodity", commodityService.getOne(commodityId));
			request.getRequestDispatcher(forwardUploadPictureUrl).forward(request, response);
			return;
		} else if ("upload".equals(action)) {
			try {
				service.uploadPicture(request);
			} catch (SizeLimitExceededException e) {
				e.printStackTrace();
				errors.add("上傳檔案需小於30MB!");
				request.setAttribute("errors", errors);
			} catch (FileUploadException e) {
				e.printStackTrace();
				if ("extensionName".equals(e.getMessage())) {
					errors.add("副檔名須為jpg, gif, png 三者其中之一");
					request.setAttribute("errors", errors);
				}
			}

			request.getRequestDispatcher(forwardUploadPictureUrl).forward(request, response);
			return;
		}
//		else if ("getPicturesBase64".equals(action)) {
//			Map<Integer, String> pictureBase64Map = service.getPictureBase64(commodityId);
//			request.setAttribute("pictureBase64Map", pictureBase64Map);
//			return;
//		} 
		else if ("delete".equals(action)) {
			String[] pictureIds = request.getParameterValues("pictureId");
			if (pictureIds != null) {
				service.deletePictures(pictureIds);
			}			
			response.sendRedirect(sendRedirectUrl);
			return;
		} else if ("download".equals(action)) {
			String[] pictureIds = request.getParameterValues("pictureId");
			if (pictureIds != null) {
				response.setHeader("Content-disposition", "attachment; filename=" + commodityId + ".zip");
				service.getPicturesZip(pictureIds, response.getOutputStream());
			}
//			response.sendRedirect(sendRedirectUrl);
//			return;
		} else if ("downloadAll".equals(action)) {
			response.setHeader("Content-disposition", "attachment; filename=" + commodityId + "All.zip");
			service.getPicturesZip(commodityId, response.getOutputStream());
		}
//			response.sendRedirect(sendRedirectUrl);
//			return;
	}
}
