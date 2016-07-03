package commodity.model;

import java.util.List;
import java.util.Map;
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

/*
 * Location: C:\Users\fallicemoon\Desktop\jersey\WEB-INF\classes\ Qualified
 * Name: commodity.model.CommodityService JD-Core Version: 0.6.2
 */