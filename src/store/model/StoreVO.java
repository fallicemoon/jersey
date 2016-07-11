package store.model;

import java.io.Serializable;

import tools.JerseyEnum.StoreType;

public class StoreVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4628934097511568808L;
	private Integer storeId;
	private StoreType type;
	private String name;

	public Integer getStoreId() {
		return this.storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public StoreType getType() {
		return this.type;
	}

	public void setType(StoreType type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
