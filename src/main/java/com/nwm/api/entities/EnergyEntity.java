/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class EnergyEntity {
	private int id;
    private String hashId;
    private String name;
	private Double actual;
	private Double expected;
	private Double loss;
	
	public EnergyEntity() {}
	
	public EnergyEntity(int id, String hashId, String name) {
		this.id = id;
		this.hashId = hashId;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHashId() {
		return hashId;
	}
	public void setHashId(String hashId) {
		this.hashId = hashId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getActual() {
		return actual;
	}
	public void setActual(Double actual) {
		this.actual = actual;
	}
	public Double getExpected() {
		return expected;
	}
	public void setExpected(Double expected) {
		this.expected = expected;
	}
	public Double getLoss() {
		return loss;
	}
	public void setLoss(Double loss) {
		this.loss = loss;
	}

}
