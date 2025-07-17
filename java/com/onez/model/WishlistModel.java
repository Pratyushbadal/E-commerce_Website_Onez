package com.onez.model;

public class WishlistModel{
	
	private int wishlistId;
	private int wishlistName;
	
	public WishlistModel() {
		
	}
	
	public WishlistModel(int wishlistId, int wishlistName) {
		super();
		this.wishlistId = wishlistId;
		this.wishlistName = wishlistName;
	}

	public int getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(int wishlistId) {
		this.wishlistId = wishlistId;
	}

	public int getWishlistName() {
		return wishlistName;
	}

	public void setWishlistName(int wishlistName) {
		this.wishlistName = wishlistName;
	}
	
	
	
}
