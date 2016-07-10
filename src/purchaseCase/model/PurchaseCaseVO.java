package purchaseCase.model;

import java.util.LinkedHashSet;
import java.util.Set;

import commodity.model.CommodityVO;
import sellCase.model.SellCaseVO;
import store.model.StoreVO;

public class PurchaseCaseVO {
	private Integer purchaseCaseId;
	private SellCaseVO sellCaseVO;
	private StoreVO store;
	private String progress;
	private StoreVO shippingCompany;
	private String trackingNumber;
	private String trackingNumberLink;
	private String agent;
	private String agentTrackingNumber;
	private String agentTrackingNumberLink;
	private Boolean isAbroad;
	private Integer cost;
	private Integer agentCost;
	private String description;
	private String time;
	private Set<CommodityVO> commoditys = new LinkedHashSet<CommodityVO>();

	public Set<CommodityVO> getCommoditys() {
		return this.commoditys;
	}

	public void setCommoditys(Set<CommodityVO> commoditys) {
		this.commoditys = commoditys;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAgentTrackingNumberLink() {
		return this.agentTrackingNumberLink;
	}

	public void setAgentTrackingNumberLink(String agentTrackingNumberLink) {
		this.agentTrackingNumberLink = agentTrackingNumberLink;
	}

	public Integer getPurchaseCaseId() {
		return this.purchaseCaseId;
	}

	public void setPurchaseCaseId(Integer purchaseCaseId) {
		this.purchaseCaseId = purchaseCaseId;
	}

	public StoreVO getStore() {
		return this.store;
	}

	public void setStore(StoreVO store) {
		this.store = store;
	}

	public String getProgress() {
		return this.progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public StoreVO getShippingCompany() {
		return this.shippingCompany;
	}

	public void setShippingCompany(StoreVO shippingCompany) {
		this.shippingCompany = shippingCompany;
	}

	public String getTrackingNumber() {
		return this.trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getTrackingNumberLink() {
		return this.trackingNumberLink;
	}

	public void setTrackingNumberLink(String trackingNumberLink) {
		this.trackingNumberLink = trackingNumberLink;
	}

	public String getAgent() {
		return this.agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getAgentTrackingNumber() {
		return this.agentTrackingNumber;
	}

	public void setAgentTrackingNumber(String agentTrackingNumber) {
		this.agentTrackingNumber = agentTrackingNumber;
	}

	public Boolean getIsAbroad() {
		return this.isAbroad;
	}

	public void setIsAbroad(Boolean isAbroad) {
		this.isAbroad = isAbroad;
	}

	public Integer getCost() {
		return this.cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getAgentCost() {
		return this.agentCost;
	}

	public void setAgentCost(Integer agentCost) {
		this.agentCost = agentCost;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SellCaseVO getSellCaseVO() {
		return this.sellCaseVO;
	}

	public void setSellCaseVO(SellCaseVO sellCaseVO) {
		this.sellCaseVO = sellCaseVO;
	}

	@Override
	public String toString() {
		return "PurchaseCaseVO [purchaseCaseId=" + purchaseCaseId + ", sellCaseVO=" + sellCaseVO + ", store=" + store
				+ ", progress=" + progress + ", shippingCompany=" + shippingCompany + ", trackingNumber="
				+ trackingNumber + ", trackingNumberLink=" + trackingNumberLink + ", agent=" + agent
				+ ", agentTrackingNumber=" + agentTrackingNumber + ", agentTrackingNumberLink="
				+ agentTrackingNumberLink + ", isAbroad=" + isAbroad + ", cost=" + cost + ", agentCost=" + agentCost
				+ ", description=" + description + ", time=" + time + "]";
	}

	
	
	
	
}
