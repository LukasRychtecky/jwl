package com.jwl.business.article;

import java.util.List;

public class TopicTO {

	private int id;
	private String title;
	private boolean closed;
	private List<PostTO> posts;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public List<PostTO> getPosts() {
		return posts;
	}

	public void setPosts(List<PostTO> posts) {
		this.posts = posts;
	}
	
	public PostTO getOpeningPost(){
		return posts.get(0);
	}
	
	public boolean hasReplies(){
		if(posts.size()>1){
			return true;
		}
		return false;
	}
	
	public PostTO getLastReply(){
		return posts.get(posts.size()-1);
	}

}
