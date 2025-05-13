/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class EnergyEntity {
	private Double actual;
	private Double expected;
	private Double loss;
	
	public EnergyEntity() {}
	
	public EnergyEntity(Double actual, Double expected, Double loss) {
		this.actual = actual;
		this.expected = expected;
		this.loss = loss;
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
