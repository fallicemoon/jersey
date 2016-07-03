package picture.model;

import java.sql.Blob;

import commodity.model.CommodityVO;

public class PictureVO {
	private Integer pictureId;
	private CommodityVO commodityVO;
	private Integer sequenceId;
	private Blob picture;
	private String fileName;

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getPictureId() {
		return this.pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public CommodityVO getCommodityVO() {
		return this.commodityVO;
	}

	public void setCommodityVO(CommodityVO commodityVO) {
		this.commodityVO = commodityVO;
	}

	public Integer getSequenceId() {
		return this.sequenceId;
	}

	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Blob getPicture() {
		return this.picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}
}
