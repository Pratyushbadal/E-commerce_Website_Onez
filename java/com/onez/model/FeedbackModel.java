package com.onez.model;

public class FeedbackModel{
	
	private int feedbackId;
	private String feedbackDetails;
	private int rating;
	
	public FeedbackModel(){
		
	}
	
	public FeedbackModel(int feedbackId, String feedbackDetails, int rating) {
		super();
		this.feedbackId = feedbackId;
		this.feedbackDetails = feedbackDetails;
		this.rating = rating;
	}

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getFeedbackDetails() {
		return feedbackDetails;
	}

	public void setFeedbackDetails(String feedbackDetails) {
		this.feedbackDetails = feedbackDetails;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	
}
