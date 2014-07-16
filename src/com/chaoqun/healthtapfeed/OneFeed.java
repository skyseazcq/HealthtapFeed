package com.chaoqun.healthtapfeed;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OneFeed implements Serializable{
	private String last_name;
	private String question;
	private String imagePath;
	
	private String answer;
	private String full_name;
	private String snapshot;
	private String avatarPath;
	
	public OneFeed(String last_n, String q, String imageP) {
		this.last_name = last_n;
		this.question = q;
		this.imagePath = imageP;
	}
	
	public String getName(){
		return this.last_name;
	}
	public String getFullName(){
		return this.full_name;
	}
	public void setFullName(String fullname){
		this.full_name = fullname;
	}
	public String getQuestion() {
		return this.question;
	}
	public String getImagePath() {
		return this.imagePath;
	}

	public void setAnswer(String ans) {
		this.answer = ans;
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}
	
	public String getSnapshot() {
		return this.snapshot;
	}
	
	public void setAvatar(String avatar) {
		this.avatarPath = avatar;
	}
	
	public String getAvatar() {
		return this.avatarPath;
	}
}
