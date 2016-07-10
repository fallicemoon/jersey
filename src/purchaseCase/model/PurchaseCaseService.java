package purchaseCase.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import commodity.model.CommodityDAO;
import commodity.model.CommodityVO;

public class PurchaseCaseService {
	private PurchaseCaseDAO dao;

	public PurchaseCaseService() {
		this.dao = new PurchaseCaseDAO();
	}

	public List<PurchaseCaseWithStoreNameVO> getAll() {
		return dao.getAllWithStoreName();
	}

	public List<PurchaseCaseVO> getAllOfNotComplete() {
		return this.dao.getAllOfNotProgress("進貨完成");
	}

	public PurchaseCaseVO getOne(Integer id) {
		return this.dao.getOne(id);
	}

	public Integer create(PurchaseCaseVO vo) {
		return this.dao.create(vo);
	}

	public boolean update(PurchaseCaseVO vo) {
		return this.dao.update(vo);
	}

	public boolean delete(Integer[] ids) {
		return this.dao.delete(ids);
	}

	public Set<CommodityVO> getCommoditysByPurchaseCaseId(Integer id) {
		PurchaseCaseVO vo = this.dao.getOne(id);
		if (vo != null)
			return vo.getCommoditys();
		return new HashSet();
	}

	public List<CommodityVO> getCommoditysByPurchaseCaseIdIsNull() {
		CommodityDAO commodityDAO = new CommodityDAO();
		return commodityDAO.getByPurchaseCaseIdIsNull();
	}

	public void addPurchaseCaseIdToCommoditys(Integer purchaseCaseId, Integer[] commodityIds) {
		CommodityDAO commodityDAO = new CommodityDAO();
		PurchaseCaseVO purchaseCaseVO = getOne(purchaseCaseId);
		commodityDAO.updatePurchaseCaseId(purchaseCaseVO, commodityIds);
	}

	public void deletePurchasCaseIdFromCommoditys(Integer[] commodityIds) {
		CommodityDAO commodityDAO = new CommodityDAO();
		commodityDAO.deletePurchaseCaseId(commodityIds);
	}
}

/*
 * Location: C:\Users\fallicemoon\Desktop\jersey\WEB-INF\classes\ Qualified
 * Name: purchaseCase.model.PurchaseCaseService JD-Core Version: 0.6.2
 */