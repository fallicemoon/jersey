package purchaseCase.model;

public class PurchaseCaseWithStoreNameVO extends PurchaseCaseVO {
	
	private String storeName;
	private String shippingCompanyName;
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getShippingCompanyName() {
		return shippingCompanyName;
	}
	public void setShippingCompanyName(String shippingCompanyName) {
		this.shippingCompanyName = shippingCompanyName;
	}
	@Override
	public String toString() {
		return "PurchaseCaseVOWithStoreName [storeName=" + storeName + ", shippingCompanyName=" + shippingCompanyName
				+ "]";
	}
	
	

}
