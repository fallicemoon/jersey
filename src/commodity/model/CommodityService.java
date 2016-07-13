package commodity.model;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import picture.model.PictureDAO;

public class CommodityService {
	private CommodityDAO dao;

	public CommodityService() {
		this.dao = new CommodityDAO();
	}

	public List<CommodityVO> getAll() {
		return this.dao.getAll();
	}

	public CommodityVO getOne(Integer id) {
		return this.dao.getOne(id);
	}

	public Integer create(CommodityVO vo) {
		return this.dao.create(vo);
	}

	public boolean update(CommodityVO vo) {
		return this.dao.update(vo);
	}

	public boolean delete(Integer[] ids) {
		return this.dao.delete(ids);
	}
	
	public Map<String, Set<String>> getRule (List<CommodityVO> list) {
		Map<String, Set<String>> map = new HashMap<>();
		
		Set<String> itemNames = new LinkedHashSet<String>();
		Set<String> players = new LinkedHashSet<String>();
		Set<String> teams = new LinkedHashSet<String>();
		Set<String> styles = new LinkedHashSet<String>();
		Set<String> brands = new LinkedHashSet<String>();
		Set<String> sizes = new LinkedHashSet<String>();
		Set<String> conditions = new LinkedHashSet<String>();
		Set<String> owners = new LinkedHashSet<String>();
		Set<String> sellPlatforms = new LinkedHashSet<String>();

		for (CommodityVO commodityVO : list) {
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
		
		map.put("itemNames", itemNames);
		map.put("players", players);
		map.put("teams", teams);
		map.put("styles", styles);
		map.put("brands", brands);
		map.put("sizes", sizes);
		map.put("conditions", conditions);
		map.put("owners", owners);
		map.put("sellPlatforms", sellPlatforms);
		return map;
	} 

	public List<CommodityVO> getByRule(Map<String, Object> rule) {
		return this.dao.getByRule(rule);
	}

	public Map<Integer, Long> getCommodityIdPictureCountMap() {
		PictureDAO pictureDAO = new PictureDAO();
		return pictureDAO.getCommodityIdPictureCountMap();
	}

	public Long getCommodityIdPictureCount(Integer commodityId) {
		PictureDAO pictureDAO = new PictureDAO();
		return pictureDAO.getCommodityIdPictureCount(commodityId);
	}
}

