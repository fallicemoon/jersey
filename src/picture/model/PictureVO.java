package picture.model;

import java.io.InputStream;

public class PictureVO {
	private Integer pictureId;
	private Integer commodityId;
	private Integer sequenceId;
	private InputStream picture;
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

	public Integer getCommodityId() {
		return this.commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getSequenceId() {
		return this.sequenceId;
	}

	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	public InputStream getPicture() {
		return this.picture;
	}

	public void setPicture(InputStream picture) {
		this.picture = picture;
	}
}

/*
 * Location: C:\Users\fallicemoon\Desktop\jersey\WEB-INF\classes\ Qualified
 * Name: picture.model.PictureVO JD-Core Version: 0.6.2
 */