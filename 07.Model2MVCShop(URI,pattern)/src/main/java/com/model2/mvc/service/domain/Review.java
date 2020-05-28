package com.model2.mvc.service.domain;

import java.sql.Date;

public class Review {
	
	private int reviewNo;
	private Purchase reviewTran;
	private Product reviewProd;
	private User reviewBuyer;
	private String reviewDetail;
	private String reviewFileName;
	private Date reviewDate;
	
	public Review() {
	}

	public int getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}

	public Purchase getReviewTran() {
		return reviewTran;
	}

	public void setReviewTran(Purchase reviewTran) {
		this.reviewTran = reviewTran;
	}

	public Product getReviewProd() {
		return reviewProd;
	}

	public void setReviewProd(Product reviewProd) {
		this.reviewProd = reviewProd;
	}

	public User getReviewBuyer() {
		return reviewBuyer;
	}

	public void setReviewBuyer(User reviewBuyer) {
		this.reviewBuyer = reviewBuyer;
	}

	public String getReviewDetail() {
		return reviewDetail;
	}

	public void setReviewDetail(String reviewDetail) {
		this.reviewDetail = reviewDetail;
	}

	public String getReviewFileName() {
		return reviewFileName;
	}

	public void setReviewFileName(String reviewFileName) {
		this.reviewFileName = reviewFileName;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	@Override
	public String toString() {
		return "ReviewVO [reviewNo=" + reviewNo + ", reviewTran=" + reviewTran
				+ ", reviewProd=" + reviewProd +", reviewBuyer="+reviewBuyer+", reviewDetail="+reviewDetail
				+ ", reviewFileName="+ reviewFileName+", reviewDate="+reviewDate+ "]";
	}
	

}
