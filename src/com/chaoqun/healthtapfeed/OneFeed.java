package com.chaoqun.healthtapfeed;

public class OneFeed {
	private String last_name;
	private String question;
	private String imagePath;
	
	private String answer;
	private String name;
	private String snapshot;
	
	public OneFeed(String last_n, String q, String imageP) {
		this.last_name = last_n;
		this.question = q;
		this.imagePath = imageP;
	}
	
	public String getName(){
		return this.last_name;
	}
	public String getQuestion() {
		return this.question;
	}
	public String getImagePath() {
		return this.imagePath;
	}

}
