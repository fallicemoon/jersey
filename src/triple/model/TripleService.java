package triple.model;

import java.util.List;
import java.util.Set;

import commodity.model.CommodityVO;
import purchaseCase.model.PurchaseCaseVO;
import sellCase.model.SellCaseService;
import sellCase.model.SellCaseVO;
import sellCase.model.SellCaseWithBenefitVO;

public class TripleService {

	private SellCaseService scs = new SellCaseService();

	public void generateTriple(CommodityVO commodityVO, List<CommodityVO> commodityVOList,
			List<PurchaseCaseVO> purchaseCaseVOList, List<SellCaseWithBenefitVO> sellCaseWithBenefitVOList) {
		commodityVOList.add(commodityVO);
		purchaseCaseVOList.add(commodityVO.getPurchaseCaseVO());
		if (commodityVO.getPurchaseCaseVO() != null && commodityVO.getPurchaseCaseVO().getSellCaseVO() != null) {
			sellCaseWithBenefitVOList
					.add(scs.getSellCaseWithBenefitVo(commodityVO.getPurchaseCaseVO().getSellCaseVO()));
		}
	}

	public void generateTriple(PurchaseCaseVO purchaseCaseVO, Set<CommodityVO> commodityVOSet,
			List<PurchaseCaseVO> purchaseCaseVOList, List<SellCaseWithBenefitVO> sellCaseWithBenefitVOList) {
		commodityVOSet.addAll(purchaseCaseVO.getCommoditys());
		purchaseCaseVOList.add(purchaseCaseVO);
		if (purchaseCaseVO.getSellCaseVO() != null) {
			sellCaseWithBenefitVOList.add(scs.getSellCaseWithBenefitVo(purchaseCaseVO.getSellCaseVO()));
		}
	}

	public void generateTriple(SellCaseVO sellCaseVO, Set<CommodityVO> commodityVOSet,
			Set<PurchaseCaseVO> purchaseCaseVOSet, List<SellCaseWithBenefitVO> sellCaseWithBenefitVOList) {
		SellCaseWithBenefitVO sellCaseWithBenefitVO = scs.getSellCaseWithBenefitVo(sellCaseVO);
		sellCaseWithBenefitVOList.add(sellCaseWithBenefitVO);
		purchaseCaseVOSet.addAll(sellCaseWithBenefitVO.getPurchaseCases());
		if (purchaseCaseVOSet != null) {
			for (PurchaseCaseVO purchaseCaseVO : purchaseCaseVOSet) {
				commodityVOSet.addAll(purchaseCaseVO.getCommoditys());
			}
		}
	}

	
	
}
