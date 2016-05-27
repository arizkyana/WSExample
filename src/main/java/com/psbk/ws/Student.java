package com.psbk.ws;

public class Student {

	private String id;
	private String name;
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", address=" + address + ", getId()=" + getId() + ", getName()="
				+ getName() + ", getAddress()=" + getAddress() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return "Student : "+id+" - "+name;
//	}

}
