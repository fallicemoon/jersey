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
		if (commodityVO.getPurchaseCaseVO()!=null) {
			SellCaseWithBenefitVO vo = scs.getSellCaseWithBenefitVo(commodityVO.getPurchaseCaseVO().getSellCaseVO());
			sellCaseWithBenefitVOList.add(vo);
		}
	}
	
	public void generateTriple(PurchaseCaseVO purchaseCaseVO, Set<CommodityVO> commodityVOSet,
			List<PurchaseCaseVO> purchaseCaseVOList, List<SellCaseWithBenefitVO> sellCaseWithBenefitVOList) {		
		commodityVOSet = purchaseCaseVO.getCommoditys();
		purchaseCaseVOList.add(purchaseCaseVO);
		SellCaseWithBenefitVO vo = scs.getSellCaseWithBenefitVo(purchaseCaseVO.getSellCaseVO());
		sellCaseWithBenefitVOList.add(vo);
	}
	
	public void generateTriple(SellCaseVO sellCaseVO, Set<CommodityVO> commodityVOSet,
			Set<PurchaseCaseVO> purchaseCaseVOSet, List<SellCaseWithBenefitVO> sellCaseWithBenefitVOList) {		
		SellCaseWithBenefitVO sellCaseWithBenefitVO = scs.getSellCaseWithBenefitVo(sellCaseVO);
		sellCaseWithBenefitVOList.add(sellCaseWithBenefitVO);
		purchaseCaseVOSet = sellCaseWithBenefitVO.getPurchaseCases();
		if (purchaseCaseVOSet!=null) {
			for (PurchaseCaseVO purchaseCaseVO : purchaseCaseVOSet) {
				commodityVOSet.addAll(purchaseCaseVO.getCommoditys());
			}
		}
	}
	
	
	
	
	
	

}
