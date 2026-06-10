/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSmaShp7510Entity extends ModelBaseEntity {
	
	private double ACcurrentA;
	private double CurrentlineconductorL1;
	private double CurrentlineconductorL2;
	private double CurrentlineconductorL3;
	private double Scalefactorcurrent;
	private double VoltagelineconductorL1toL2;
	private double VoltagelineconductorL2toL3;
	private double VoltagelineconductorL3toL1;
	private double VoltagelineconductorL1toN;
	private double VoltagelineconductorL2toN;
	private double VoltagelineconductorL3toN;
	private double Scalefactorvoltage;
	private double Activepower;
	private double Scalefactoractivepower;
	private double Powerfrequency;
	private double Scalefactorpowerfrequency;
	private double Apparentpower;
	private double Scalefactorapparentpower;
	private double Reactivepower;
	private double Scalefactorreactivepower;
	private double Displacementpowerfactorcos;
	private double Scalefactordisplacementpowerfactor;
	private double Totalyield;
	private double calefactortotalyield;
	private double DCcurrent;
	private double ScalefactorDCcurrent;
	private double DCvoltage;
	private double ScalefactorDCvoltage;
	private double DCpower;
	private double ScalefactorDCpower;
	private double Internaltemperature;
	private double Heatsinktemperature;
	private double Transformer;
	private double Othertemperature;
	private double Scalefactortemperature;
	private double Operatingstatus;
	private double EventnumberEvt1;
	private double EventnumberEvt2;
	private double ManufacturerspecificeventcodeEvtVnd1;
	private double ManufacturerspecificeventcodeEvtVnd2;
	private double ManufacturerspecificeventcodeEvtVnd3;
	private double ManufacturerspecificeventcodeEvtVnd4;
	private double Manufacturerspecificstatuscode;
	private int totalFault1;
	private int totalFault2;
	private int totalFault3;
	private int totalFault4;
	private int totalFault5;
	
	
	
	public int getTotalFault2() {
		return totalFault2;
	}
	public void setTotalFault2(int totalFault2) {
		this.totalFault2 = totalFault2;
	}
	public int getTotalFault3() {
		return totalFault3;
	}
	public void setTotalFault3(int totalFault3) {
		this.totalFault3 = totalFault3;
	}
	public int getTotalFault4() {
		return totalFault4;
	}
	public void setTotalFault4(int totalFault4) {
		this.totalFault4 = totalFault4;
	}
	public int getTotalFault5() {
		return totalFault5;
	}
	public void setTotalFault5(int totalFault5) {
		this.totalFault5 = totalFault5;
	}
	public int getTotalFault1() {
		return totalFault1;
	}
	public void setTotalFault1(int totalFault1) {
		this.totalFault1 = totalFault1;
	}
	public double getManufacturerspecificstatuscode() {
		return Manufacturerspecificstatuscode;
	}
	public void setManufacturerspecificstatuscode(double manufacturerspecificstatuscode) {
		Manufacturerspecificstatuscode = manufacturerspecificstatuscode;
	}
	public double getACcurrentA() {
		return ACcurrentA;
	}
	public void setACcurrentA(double aCcurrentA) {
		ACcurrentA = aCcurrentA;
	}
	public double getCurrentlineconductorL1() {
		return CurrentlineconductorL1;
	}
	public void setCurrentlineconductorL1(double currentlineconductorL1) {
		CurrentlineconductorL1 = currentlineconductorL1;
	}
	public double getCurrentlineconductorL2() {
		return CurrentlineconductorL2;
	}
	public void setCurrentlineconductorL2(double currentlineconductorL2) {
		CurrentlineconductorL2 = currentlineconductorL2;
	}
	public double getCurrentlineconductorL3() {
		return CurrentlineconductorL3;
	}
	public void setCurrentlineconductorL3(double currentlineconductorL3) {
		CurrentlineconductorL3 = currentlineconductorL3;
	}
	public double getScalefactorcurrent() {
		return Scalefactorcurrent;
	}
	public void setScalefactorcurrent(double scalefactorcurrent) {
		Scalefactorcurrent = scalefactorcurrent;
	}
	public double getVoltagelineconductorL1toL2() {
		return VoltagelineconductorL1toL2;
	}
	public void setVoltagelineconductorL1toL2(double voltagelineconductorL1toL2) {
		VoltagelineconductorL1toL2 = voltagelineconductorL1toL2;
	}
	public double getVoltagelineconductorL2toL3() {
		return VoltagelineconductorL2toL3;
	}
	public void setVoltagelineconductorL2toL3(double voltagelineconductorL2toL3) {
		VoltagelineconductorL2toL3 = voltagelineconductorL2toL3;
	}
	public double getVoltagelineconductorL3toL1() {
		return VoltagelineconductorL3toL1;
	}
	public void setVoltagelineconductorL3toL1(double voltagelineconductorL3toL1) {
		VoltagelineconductorL3toL1 = voltagelineconductorL3toL1;
	}
	public double getVoltagelineconductorL1toN() {
		return VoltagelineconductorL1toN;
	}
	public void setVoltagelineconductorL1toN(double voltagelineconductorL1toN) {
		VoltagelineconductorL1toN = voltagelineconductorL1toN;
	}
	public double getVoltagelineconductorL2toN() {
		return VoltagelineconductorL2toN;
	}
	public void setVoltagelineconductorL2toN(double voltagelineconductorL2toN) {
		VoltagelineconductorL2toN = voltagelineconductorL2toN;
	}
	public double getVoltagelineconductorL3toN() {
		return VoltagelineconductorL3toN;
	}
	public void setVoltagelineconductorL3toN(double voltagelineconductorL3toN) {
		VoltagelineconductorL3toN = voltagelineconductorL3toN;
	}
	public double getScalefactorvoltage() {
		return Scalefactorvoltage;
	}
	public void setScalefactorvoltage(double scalefactorvoltage) {
		Scalefactorvoltage = scalefactorvoltage;
	}
	public double getActivepower() {
		return Activepower;
	}
	public void setActivepower(double activepower) {
		Activepower = activepower;
	}
	public double getScalefactoractivepower() {
		return Scalefactoractivepower;
	}
	public void setScalefactoractivepower(double scalefactoractivepower) {
		Scalefactoractivepower = scalefactoractivepower;
	}
	public double getPowerfrequency() {
		return Powerfrequency;
	}
	public void setPowerfrequency(double powerfrequency) {
		Powerfrequency = powerfrequency;
	}
	public double getScalefactorpowerfrequency() {
		return Scalefactorpowerfrequency;
	}
	public void setScalefactorpowerfrequency(double scalefactorpowerfrequency) {
		Scalefactorpowerfrequency = scalefactorpowerfrequency;
	}
	public double getApparentpower() {
		return Apparentpower;
	}
	public void setApparentpower(double apparentpower) {
		Apparentpower = apparentpower;
	}
	public double getScalefactorapparentpower() {
		return Scalefactorapparentpower;
	}
	public void setScalefactorapparentpower(double scalefactorapparentpower) {
		Scalefactorapparentpower = scalefactorapparentpower;
	}
	public double getReactivepower() {
		return Reactivepower;
	}
	public void setReactivepower(double reactivepower) {
		Reactivepower = reactivepower;
	}
	public double getScalefactorreactivepower() {
		return Scalefactorreactivepower;
	}
	public void setScalefactorreactivepower(double scalefactorreactivepower) {
		Scalefactorreactivepower = scalefactorreactivepower;
	}
	public double getDisplacementpowerfactorcos() {
		return Displacementpowerfactorcos;
	}
	public void setDisplacementpowerfactorcos(double displacementpowerfactorcos) {
		Displacementpowerfactorcos = displacementpowerfactorcos;
	}
	public double getScalefactordisplacementpowerfactor() {
		return Scalefactordisplacementpowerfactor;
	}
	public void setScalefactordisplacementpowerfactor(double scalefactordisplacementpowerfactor) {
		Scalefactordisplacementpowerfactor = scalefactordisplacementpowerfactor;
	}
	public double getTotalyield() {
		return Totalyield;
	}
	public void setTotalyield(double totalyield) {
		Totalyield = totalyield;
	}
	public double getCalefactortotalyield() {
		return calefactortotalyield;
	}
	public void setCalefactortotalyield(double calefactortotalyield) {
		this.calefactortotalyield = calefactortotalyield;
	}
	public double getDCcurrent() {
		return DCcurrent;
	}
	public void setDCcurrent(double dCcurrent) {
		DCcurrent = dCcurrent;
	}
	public double getScalefactorDCcurrent() {
		return ScalefactorDCcurrent;
	}
	public void setScalefactorDCcurrent(double scalefactorDCcurrent) {
		ScalefactorDCcurrent = scalefactorDCcurrent;
	}
	public double getDCvoltage() {
		return DCvoltage;
	}
	public void setDCvoltage(double dCvoltage) {
		DCvoltage = dCvoltage;
	}
	public double getScalefactorDCvoltage() {
		return ScalefactorDCvoltage;
	}
	public void setScalefactorDCvoltage(double scalefactorDCvoltage) {
		ScalefactorDCvoltage = scalefactorDCvoltage;
	}
	public double getDCpower() {
		return DCpower;
	}
	public void setDCpower(double dCpower) {
		DCpower = dCpower;
	}
	public double getScalefactorDCpower() {
		return ScalefactorDCpower;
	}
	public void setScalefactorDCpower(double scalefactorDCpower) {
		ScalefactorDCpower = scalefactorDCpower;
	}
	public double getInternaltemperature() {
		return Internaltemperature;
	}
	public void setInternaltemperature(double internaltemperature) {
		Internaltemperature = internaltemperature;
	}
	public double getHeatsinktemperature() {
		return Heatsinktemperature;
	}
	public void setHeatsinktemperature(double heatsinktemperature) {
		Heatsinktemperature = heatsinktemperature;
	}
	public double getTransformer() {
		return Transformer;
	}
	public void setTransformer(double transformer) {
		Transformer = transformer;
	}
	public double getOthertemperature() {
		return Othertemperature;
	}
	public void setOthertemperature(double othertemperature) {
		Othertemperature = othertemperature;
	}
	public double getScalefactortemperature() {
		return Scalefactortemperature;
	}
	public void setScalefactortemperature(double scalefactortemperature) {
		Scalefactortemperature = scalefactortemperature;
	}
	public double getOperatingstatus() {
		return Operatingstatus;
	}
	public void setOperatingstatus(double operatingstatus) {
		Operatingstatus = operatingstatus;
	}
	public double getEventnumberEvt1() {
		return EventnumberEvt1;
	}
	public void setEventnumberEvt1(double eventnumberEvt1) {
		EventnumberEvt1 = eventnumberEvt1;
	}
	public double getEventnumberEvt2() {
		return EventnumberEvt2;
	}
	public void setEventnumberEvt2(double eventnumberEvt2) {
		EventnumberEvt2 = eventnumberEvt2;
	}
	public double getManufacturerspecificeventcodeEvtVnd1() {
		return ManufacturerspecificeventcodeEvtVnd1;
	}
	public void setManufacturerspecificeventcodeEvtVnd1(double manufacturerspecificeventcodeEvtVnd1) {
		ManufacturerspecificeventcodeEvtVnd1 = manufacturerspecificeventcodeEvtVnd1;
	}
	public double getManufacturerspecificeventcodeEvtVnd2() {
		return ManufacturerspecificeventcodeEvtVnd2;
	}
	public void setManufacturerspecificeventcodeEvtVnd2(double manufacturerspecificeventcodeEvtVnd2) {
		ManufacturerspecificeventcodeEvtVnd2 = manufacturerspecificeventcodeEvtVnd2;
	}
	public double getManufacturerspecificeventcodeEvtVnd3() {
		return ManufacturerspecificeventcodeEvtVnd3;
	}
	public void setManufacturerspecificeventcodeEvtVnd3(double manufacturerspecificeventcodeEvtVnd3) {
		ManufacturerspecificeventcodeEvtVnd3 = manufacturerspecificeventcodeEvtVnd3;
	}
	public double getManufacturerspecificeventcodeEvtVnd4() {
		return ManufacturerspecificeventcodeEvtVnd4;
	}
	public void setManufacturerspecificeventcodeEvtVnd4(double manufacturerspecificeventcodeEvtVnd4) {
		ManufacturerspecificeventcodeEvtVnd4 = manufacturerspecificeventcodeEvtVnd4;
	}
	
	
	
	
}
