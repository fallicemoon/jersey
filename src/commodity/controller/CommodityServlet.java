package commodity.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import commodity.model.CommodityService;
import commodity.model.CommodityVO;

public class CommodityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String forwardUrl = "/WEB-INF/pages/commodity";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		LinkedHashSet<String> errors = new LinkedHashSet<String>();
		HttpSession session = request.getSession();
		CommodityService service = new CommodityService();

		if (StringUtils.isEmpty(action)) {
			List<CommodityVO> commodityList = service.getAll();
			addRule(session, commodityList);
			request.setAttribute("commodityList", commodityList);
			request.setAttribute("commodityIdPictureCountMap", service.getCommodityIdPictureCountMap());
			request.getRequestDispatcher(forwardUrl + "/list.jsp").forward(request, response);
		} else if ("getOne".equals(action)) {
			//用在create和update的時候
			try {
				Integer commodityId = Integer.valueOf(request.getParameter("commodityId"));
				if (commodityId==null) {
					//create
					request.getRequestDispatcher(forwardUrl + "/add.jsp").forward(request, response);
				}
				request.setAttribute("commodity", service.getOne(commodityId));
				request.setAttribute("commodityIdPictureCount", service.getCommodityIdPictureCount(commodityId));
			} catch (NumberFormatException e) {
				errors.add("商品編號須為數字!");
			}
			//update
			request.getRequestDispatcher(forwardUrl + "/update.jsp").forward(request, response);
		} else if ("getByRule".equals(action)) {
			String itemName = request.getParameter("itemName");
			String player = request.getParameter("player");
			String team = request.getParameter("team");
			String style = request.getParameter("style");
			String brand = request.getParameter("brand");
			String size = request.getParameter("size");
			String level = request.getParameter("level");
			String condition = request.getParameter("condition");
			String tag = request.getParameter("tag");
			String owner = request.getParameter("owner");
			String sellPlatform = request.getParameter("sellPlatform");
			String isStored = request.getParameter("isStored");

			Map<String, Object> rule = new HashMap<String, Object>();
			if (!itemName.equals("無"))
				rule.put("itemName", itemName);
			if (!player.equals("無"))
				rule.put("player", player);
			if (!team.equals("無"))
				rule.put("team", team);
			if (!style.equals("無"))
				rule.put("style", style);
			if (!brand.equals("無"))
				rule.put("brand", brand);
			if (!size.equals("無"))
				rule.put("size", size);
			if (!level.equals("無"))
				rule.put("level", level);
			if (!condition.equals("無"))
				rule.put("condition", condition);
			if (!tag.equals("無"))
				rule.put("tag", tag);
			if (!owner.equals("無"))
				rule.put("owner", owner);
			if (!sellPlatform.equals("無"))
				rule.put("sellPlatform", sellPlatform);
			if (!isStored.equals("無"))
				rule.put("isStored", Boolean.valueOf(isStored));

			request.setAttribute("commodityList", service.getByRule(rule));
			request.getRequestDispatcher(forwardUrl+"/list.jsp").forward(request, response);
		} else if ("create".equals(action)) {
			CommodityVO commodityVO = (CommodityVO) request.getAttribute("commodity");
			if (commodityVO == null) {
				commodityVO = new CommodityVO();

				commodityVO.setItemName(request.getParameter("itemName").trim());
				commodityVO.setQty(Integer.valueOf(1));
				commodityVO.setStyle(request.getParameter("style").trim());
				try {
					commodityVO.setCost(Integer.valueOf(request.getParameter("cost").trim()));
				} catch (NumberFormatException e) {
					errors.add("成本需輸入數字!");
				}
				try {
					commodityVO.setSellPrice(Integer.valueOf(request.getParameter("sellPrice").trim()));
				} catch (NumberFormatException e) {
					errors.add("售價需輸入數字!");
				}
				if (!errors.isEmpty()) {
					session.setAttribute("errors", errors);

					response.sendRedirect("/jersey/commodity/add.jsp");
					return;
				}
				commodityVO.setLink(request.getParameter("link").trim());
				commodityVO.setPlayer(request.getParameter("player").trim());
				commodityVO.setNumber(request.getParameter("number").trim());
				commodityVO.setSeason(request.getParameter("season").trim());
				commodityVO.setTeam(request.getParameter("team").trim());
				commodityVO.setColor(request.getParameter("color").trim());
				commodityVO.setBrand(request.getParameter("brand").trim());
				commodityVO.setSize(request.getParameter("size").trim());
				commodityVO.setLevel(request.getParameter("level").trim());
				commodityVO.setCondition(request.getParameter("condition").trim());
				commodityVO.setTag(request.getParameter("tag").trim());
				commodityVO.setPatchAndCertificate(request.getParameter("patchAndCertificate").trim());
				commodityVO.setSerial(request.getParameter("serial").trim());
				commodityVO.setOwner(request.getParameter("owner").trim());
				commodityVO.setSellPlatform(request.getParameter("sellPlatform").trim());
				commodityVO.setIsStored(Boolean.valueOf(true));
			}
			service.create(commodityVO);
			request.getRequestDispatcher(forwardUrl+"/list.jsp").forward(request, response);
		} else if ("update".equals(action)) {
			Integer commodityId = Integer.valueOf(request.getParameter("commodityId").trim());
			CommodityVO commodityVO = service.getOne(commodityId);
			commodityVO.setItemName(request.getParameter("itemName").trim());
			try {
				commodityVO.setQty(Integer.valueOf(request.getParameter("qty").trim()));
			} catch (NumberFormatException e1) {
				errors.add("Qty需輸入數字!");
			}
			commodityVO.setStyle(request.getParameter("style").trim());
			try {
				commodityVO.setCost(Integer.valueOf(request.getParameter("cost").trim()));
			} catch (NumberFormatException e) {
				errors.add("成本需輸入數字!");
			}
			try {
				commodityVO.setSellPrice(Integer.valueOf(request.getParameter("sellPrice").trim()));
			} catch (NumberFormatException e) {
				errors.add("售價需輸入數字!");
			}
			if (!errors.isEmpty()) {
				session.setAttribute("errors", errors);
				response.sendRedirect("/jersey/commodity/update.jsp?commodityId=" + commodityId);
				return;
			}
			commodityVO.setLink(request.getParameter("link").trim());
			commodityVO.setPlayer(request.getParameter("player").trim());
			commodityVO.setNumber(request.getParameter("number").trim());
			commodityVO.setSeason(request.getParameter("season").trim());
			commodityVO.setTeam(request.getParameter("team").trim());
			commodityVO.setColor(request.getParameter("color").trim());
			commodityVO.setBrand(request.getParameter("brand").trim());
			commodityVO.setSize(request.getParameter("size").trim());
			commodityVO.setLevel(request.getParameter("level").trim());
			commodityVO.setCondition(request.getParameter("condition").trim());
			commodityVO.setTag(request.getParameter("tag").trim());
			commodityVO.setPatchAndCertificate(request.getParameter("patchAndCertificate").trim());
			commodityVO.setSerial(request.getParameter("serial").trim());
			commodityVO.setOwner(request.getParameter("owner").trim());
			commodityVO.setSellPlatform(request.getParameter("sellPlatform").trim());
			commodityVO.setIsStored(Boolean.valueOf(request.getParameter("isStored")));

			service.update(commodityVO);
			
			request.getRequestDispatcher(forwardUrl+"/list.jsp");

			//TODO
//			if (Boolean.valueOf(request.getParameter("listOne")).booleanValue()) {
//				String id = request.getParameter("commodityId");
//				response.sendRedirect("/jersey/OtherServlet?action=commodity&commodityId=" + id);
//			}
		} else if ("delete".equals(action)) {
			String[] commodityIds = request.getParameterValues("commodityIds");
			if (commodityIds != null) {
				Integer[] ids = new Integer[commodityIds.length];
				for (int i = 0; i < commodityIds.length; i++) {
					ids[i] = Integer.valueOf(commodityIds[i]);
				}
				service.delete(ids);
			}
			request.getRequestDispatcher(forwardUrl+"/list.jsp");
		} else if ("copy".equals(action)) {
			String[] commodityIds = request.getParameterValues("commodityIds");
			if ((commodityIds != null) && (commodityIds.length == 1)) {
				String commodityId = commodityIds[0];
				CommodityVO commodityVO = service.getOne(Integer.valueOf(commodityId));
				service.create(commodityVO);
			}
			request.getRequestDispatcher(forwardUrl+"/list.jsp").forward(request, response);
		}
	}

	private void addRule(HttpSession session, List<CommodityVO> commodityList) {
		Set<String> itemNames = new LinkedHashSet<String>();
		Set<String> players = new LinkedHashSet<String>();
		Set<String> teams = new LinkedHashSet<String>();
		Set<String> styles = new LinkedHashSet<String>();
		Set<String> brands = new LinkedHashSet<String>();
		Set<String> sizes = new LinkedHashSet<String>();
		Set<String> conditions = new LinkedHashSet<String>();
		Set<String> owners = new LinkedHashSet<String>();
		Set<String> sellPlatforms = new LinkedHashSet<String>();

		for (CommodityVO commodityVO : commodityList) {
			itemNames.add(commodityVO.getItemName());
			players.add(commodityVO.getPlayer());
			teams.add(commodityVO.getTeam());
			styles.add(commodityVO.getStyle());
			brands.add(commodityVO.getBrand());
			sizes.add(commodityVO.getSize());
			conditions.add(commodityVO.getCondition());
			owners.add(commodityVO.getOwner());
			sellPlatforms.add(commodityVO.getSellPlatform());
		}

		session.setAttribute("itemNames", itemNames);
		session.setAttribute("players", players);
		session.setAttribute("teams", teams);
		session.setAttribute("styles", styles);
		session.setAttribute("brands", brands);
		session.setAttribute("sizes", sizes);
		session.setAttribute("conditions", conditions);
		session.setAttribute("owners", owners);
		session.setAttribute("sellPlatforms", sellPlatforms);
	}
}
