package com.onez.model;

public class AddressModel {

	private int addressId;
	private String name;

	public AddressModel() {
	}

	public AddressModel(String name) {
		super();
		this.name = name;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}