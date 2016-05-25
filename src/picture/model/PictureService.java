package picture.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class PictureService {
	public void uploadPicture(HttpServletRequest request)
			throws SizeLimitExceededException, IOException, FileUploadException {
		if (!ServletFileUpload.isMultipartContent(request))
			return;

		DiskFileItemFactory factory = new DiskFileItemFactory();

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(31457280L);
		upload.setHeaderEncoding("UTF-8");

		List<FileItem> items = upload.parseRequest(request);
		List<PictureVO> pictureVOList = parseFormToPictureVO(items);
		PictureDAO pictureDAO = new PictureDAO();
		for (PictureVO pictureVO : pictureVOList)
			pictureDAO.create(pictureVO);
	}
	
	public Set<Integer> getPictureIds(Integer commodityId) {
		PictureDAO pictureDAO = new PictureDAO();
		return pictureDAO.getPictureIds(commodityId);
	}
	
	public void getPicrture(Integer pictureId, OutputStream os) {
		PictureDAO pictureDAO = new PictureDAO();
		InputStream is = pictureDAO.getOne(pictureId).getPicture();
		

		try {
			byte[] b = new byte[4096];
			int length;
			while((length=is.read(b)) != -1){
				os.write(b, 0, length);
			}
			os.flush();
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	public Map<Integer, String> getPictureBase64(Integer commodityId) throws IOException {
		PictureDAO pictureDAO = new PictureDAO();
		List<PictureVO> list = pictureDAO.getPicturesByCommodityId(commodityId);
		Map<Integer, String> pictures = new LinkedHashMap<Integer, String>();

		for (PictureVO vo : list) {
			String pictureBase64 = parseInputStreamToBase64(vo.getPicture());
			pictures.put(vo.getPictureId(), pictureBase64);
		}
		return pictures;
	}

	public void getPicturesZip(String[] pictureIds, OutputStream os) throws IOException {
		PictureDAO pictureDAO = new PictureDAO();
		ZipOutputStream zos = new ZipOutputStream(os);

		for (String pictureId : pictureIds) {
			PictureVO vo = pictureDAO.getOne(Integer.valueOf(pictureId));
			InputStream is = vo.getPicture();
			zos.putNextEntry(new ZipEntry(vo.getFileName()));

			byte[] b = new byte[1024];
			int length;
			while ((length = is.read(b)) != -1) {
				zos.write(b, 0, length);
			}
		}
		zos.close();
		os.close();
	}

	public void getPicturesZip(String commodityId, OutputStream os) throws IOException {
		PictureDAO pictureDAO = new PictureDAO();
		List<PictureVO> list = pictureDAO.getPicturesByCommodityId(Integer.valueOf(commodityId));
		ZipOutputStream zos = new ZipOutputStream(os);
		InputStream is;

		for (PictureVO vo : list) {
			is = vo.getPicture();
			zos.putNextEntry(new ZipEntry(vo.getFileName()));

			int length;
			byte[] b = new byte[1024];
			while ((length = is.read(b)) != -1) {
				zos.write(b, 0, length);

			}

		}

		zos.close();
		os.close();
	}

	public void deletePictures(String[] pictureIds) {
		PictureDAO pictureDAO = new PictureDAO();
		Integer[] ids = new Integer[pictureIds.length];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = Integer.valueOf(pictureIds[i]);
		}
		pictureDAO.delete(ids);
	}

	private List<PictureVO> parseFormToPictureVO(List<FileItem> fileItems) throws FileUploadException, IOException {
		Integer commodityId = Integer.valueOf(((FileItem) fileItems.get(0)).getString());
		List<PictureVO> list = new ArrayList<PictureVO>();

		for (FileItem fileItem : fileItems)
			if (fileItem.getFieldName().equals("picture")) {
				PictureVO pictureVO = new PictureVO();
				pictureVO.setCommodityId(commodityId);
				String str1;
				switch ((str1 = fileItem.getFieldName()).hashCode()) {
				case -577741570:
					if (str1.equals("picture")) {
						String fileName = fileItem.getName();
						String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
						if ((!extensionName.equals("jpg")) && (!extensionName.equals("gif"))
								&& (!extensionName.equals("png")))
							throw new FileUploadException("extensionName");
						pictureVO.setPicture(fileItem.getInputStream());
						pictureVO.setFileName(fileName);
					}
					break;
				}

				list.add(pictureVO);
			}
		return list;
	}

	private String parseInputStreamToBase64(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int length = -1;
		while ((length = is.read(b)) != -1) {
			baos.write(b, 0, length);
		}

		byte[] pictureByteArray = baos.toByteArray();
		Base64 base64 = new Base64();
		String picture = base64.encodeToString(pictureByteArray);
		return picture;
	}
}
