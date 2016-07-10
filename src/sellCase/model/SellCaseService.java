package sellCase.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import purchaseCase.model.PurchaseCaseDAO;
import purchaseCase.model.PurchaseCaseVO;
import tools.Tools;

public class SellCaseService {
	private SellCaseDAO dao;

	public SellCaseService() {
		this.dao = new SellCaseDAO();
	}

	public List<SellCaseWithBenefitVo> getAll() {
		List<SellCaseVO> oldList = this.dao.getAll();
		List<SellCaseWithBenefitVo> newList = new ArrayList<>();
		SellCaseWithBenefitVo sellCaseWithBenefitVo;
		for (SellCaseVO sellCaseVO : oldList) {
			sellCaseWithBenefitVo = getSellCaseWithBenefitVo(sellCaseVO);
			newList.add(sellCaseWithBenefitVo);
		}
		return newList;
	}

	public SellCaseVO getOne(Integer id) {
		return dao.getOne(id);
	}

	public Integer create(SellCaseVO vo) {
		vo.setUncollected(vo.getIncome() - vo.getCollected());
		
		if (vo.getIsShipping()) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			vo.setShippingTime(sdf.format(timestamp));
		}
		
		if ((vo.getUncollected() == 0) && (vo.getIsChecked())) {
			// TODO 測試
			vo.setCloseTime(new Date());
		}
		
		return this.dao.create(vo);
	}

	public boolean update(SellCaseVO vo) {
		vo.setUncollected(vo.getIncome() - vo.getCollected());
		
		if (vo.getIsShipping()) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			vo.setShippingTime(sdf.format(timestamp));
		}
		
		if ((vo.getUncollected() == 0) && (vo.getIsChecked())) {
			// TODO 測試
			vo.setCloseTime(new Date());
		}
		return this.dao.update(vo);
	}

	public boolean delete(Integer[] ids) {
		return this.dao.delete(ids);
	}

	public Set<PurchaseCaseVO> getPurchaseCasesBySellCaseId(Integer id) {
		SellCaseVO sellCaseVO = this.dao.getOne(id);
		if (sellCaseVO != null)
			return sellCaseVO.getPurchaseCases();
		return new HashSet<PurchaseCaseVO>();
	}

	public List<PurchaseCaseVO> getPurchaseCasesBySellCaseIdIsNull() {
		PurchaseCaseDAO purchaseCaseDAO = new PurchaseCaseDAO();
		return purchaseCaseDAO.getPurchaseCasesBySellCaseIdIsNull();
	}

	public void addSellCaseIdToPurchaseCases(Integer sellCaseId, Integer[] purchaseCaseIds) {
		PurchaseCaseDAO purchaseCaseDAO = new PurchaseCaseDAO();
		purchaseCaseDAO.updateSellCaseId(sellCaseId, purchaseCaseIds);
	}

	public void deleteSellCaseIdFromPurchaseCases(Integer[] purchaseCaseIds) {
		PurchaseCaseDAO purchaseCaseDAO = new PurchaseCaseDAO();
		purchaseCaseDAO.deleteSellCaseId(purchaseCaseIds);
	}

	public List<SellCaseVO> getUncollectedNotZero() {
		return this.dao.getUncollectedNotZero();
	}

	public List<SellCaseVO> getIsClosed() {
		return this.dao.getIsClosed();
	}

	public List<SellCaseVO> getNotClosed() {
		return this.dao.getNotClosed();
	}

	public List<SellCaseVO> getBetweenCloseTime(Date start, Date end) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<SellCaseVO> sellCaseList = dao.getBetweenCloseTime(start, end);
//		List<SellCaseVO> list = new ArrayList<SellCaseVO>();
//
//		for (Object[] object : sellCaseList) {
//			SellCaseVO sellCaseVO = new SellCaseVO();
//			sellCaseVO.setSellCaseId((Integer) object[0]);
//			sellCaseVO.setAddressee((String) object[1]);
//			sellCaseVO.setPhone((String) object[2]);
//			sellCaseVO.setAddress((String) object[3]);
//			sellCaseVO.setDescription((String) object[4]);
//			sellCaseVO.setTrackingNumber((String) object[5]);
//			sellCaseVO.setTransportMethod((String) object[6]);
//			if (((Byte) object[7]).byteValue() == 1)
//				sellCaseVO.setIsShipping(Boolean.valueOf(true));
//			else
//				sellCaseVO.setIsShipping(Boolean.valueOf(false));
//			sellCaseVO.setIncome((Integer) object[8]);
//			sellCaseVO.setTransportCost((Integer) object[9]);
//			sellCaseVO.setCollected((Integer) object[10]);
//			sellCaseVO.setUncollected((Integer) object[11]);
//			sellCaseVO.setShippingTime((String) object[12]);
//			sellCaseVO.setCloseTime((String) object[13]);
//			if (((Byte) object[14]).byteValue() == 1)
//				sellCaseVO.setIsChecked(Boolean.valueOf(true));
//			else
//				sellCaseVO.setIsChecked(Boolean.valueOf(false));
//			list.add(sellCaseVO);
//		}

		return sellCaseList;
	}
	
	public SellCaseWithBenefitVo getSellCaseWithBenefitVo (SellCaseVO sellCaseVO) {
		SellCaseWithBenefitVo sellCaseWithBenefitVo = new SellCaseWithBenefitVo();
		Tools.copyBeanProperties(sellCaseVO, sellCaseWithBenefitVo);
		
		Integer costs = 0;
		Integer agentCosts = 0;
		Set<PurchaseCaseVO> purchaseCases = sellCaseVO.getPurchaseCases();
		if (purchaseCases!=null) {
			for (PurchaseCaseVO purchaseCaseVO : purchaseCases) {
				costs = costs + purchaseCaseVO.getCost();
				agentCosts = agentCosts + purchaseCaseVO.getAgentCost();
			}
		}
		sellCaseWithBenefitVo.setBenefit(sellCaseVO.getCollected() - sellCaseVO.getTransportCost() - costs - agentCosts);
		sellCaseWithBenefitVo.setEstimateBenefit(sellCaseVO.getIncome() - sellCaseVO.getTransportCost() - costs - agentCosts);
		sellCaseWithBenefitVo.setCosts(costs);
		sellCaseWithBenefitVo.setAgentCosts(agentCosts);
		return sellCaseWithBenefitVo;
	}
	
	
	
	
	
	
	
}
