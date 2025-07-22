/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSmaStp2550us50Entity extends ModelBaseEntity {
	private double Grid_Frequency;
	private double Apparent_Power;
	private double Reactive_Power;
	private double Active_Power;
	private double Power_Factor;
	private double Grid_Current;
	private double Current_Phase_1;
	private double Current_Phase_2;
	private double Current_Phase_3;
	private double Active_Power_Phase_1;
	private double Active_Power_Phase_2;
	private double Active_Power_Phase_3;
	private double Reactive_Power_Phase_1;
	private double Reactive_Power_Phase_2;
	private double Reactive_Power_Phase_3;
	private double Apparent_Power_Phase_1;
	private double Apparent_Power_Phase_2;
	private double Apparent_Power_Phase_3;
	private double Voltage_Phase_1;
	private double Voltage_Phase_2;
	private double Voltage_Phase_3;
	private double Voltage_Phase_1_2;
	private double Voltage_Phase_2_3;
	private double Voltage_Phase_3_1;
	private double Residual_DC_Current;
	private double Insulation_Resistance;
	private double Feed_In_Time;
	private double Operating_Time;
	private double Total_Yield;
	private double Internal_Temperature;
	private double DC_Current_Input_1;
	private double DC_Current_Input_2;
	private double DC_Current_Input_3;
	private double DC_Current_Input_4;
	private double DC_Voltage_Input_1;
	private double DC_Voltage_Input_2;
	private double DC_Voltage_Input_3;
	private double DC_Voltage_Input_4;
	private double DC_Power_Input_1;
	private double DC_Power_Input_2;
	private double DC_Power_Input_3;
	private double DC_Power_Input_4;
	private double EventMessage;
	private double HealthCondition;
	private double FaultCorrectionMeasure;
	private double BlockStatus;
	private double ReasonforDerating;
	private int totalFaultCode1;
	private int totalFaultCode2;
	private int totalFaultCode3;
	
	
	
	public int getTotalFaultCode1() {
		return totalFaultCode1;
	}
	public void setTotalFaultCode1(int totalFaultCode1) {
		this.totalFaultCode1 = totalFaultCode1;
	}
	public int getTotalFaultCode2() {
		return totalFaultCode2;
	}
	public void setTotalFaultCode2(int totalFaultCode2) {
		this.totalFaultCode2 = totalFaultCode2;
	}
	public int getTotalFaultCode3() {
		return totalFaultCode3;
	}
	public void setTotalFaultCode3(int totalFaultCode3) {
		this.totalFaultCode3 = totalFaultCode3;
	}
	public double getEventMessage() {
		return EventMessage;
	}
	public void setEventMessage(double eventMessage) {
		EventMessage = eventMessage;
	}
	public double getHealthCondition() {
		return HealthCondition;
	}
	public void setHealthCondition(double healthCondition) {
		HealthCondition = healthCondition;
	}
	public double getFaultCorrectionMeasure() {
		return FaultCorrectionMeasure;
	}
	public void setFaultCorrectionMeasure(double faultCorrectionMeasure) {
		FaultCorrectionMeasure = faultCorrectionMeasure;
	}
	public double getBlockStatus() {
		return BlockStatus;
	}
	public void setBlockStatus(double blockStatus) {
		BlockStatus = blockStatus;
	}
	public double getReasonforDerating() {
		return ReasonforDerating;
	}
	public void setReasonforDerating(double reasonforDerating) {
		ReasonforDerating = reasonforDerating;
	}
	public double getDC_Voltage_Input_1() {
		return DC_Voltage_Input_1;
	}
	public void setDC_Voltage_Input_1(double dC_Voltage_Input_1) {
		DC_Voltage_Input_1 = dC_Voltage_Input_1;
	}
	public double getDC_Voltage_Input_2() {
		return DC_Voltage_Input_2;
	}
	public void setDC_Voltage_Input_2(double dC_Voltage_Input_2) {
		DC_Voltage_Input_2 = dC_Voltage_Input_2;
	}
	public double getDC_Voltage_Input_3() {
		return DC_Voltage_Input_3;
	}
	public void setDC_Voltage_Input_3(double dC_Voltage_Input_3) {
		DC_Voltage_Input_3 = dC_Voltage_Input_3;
	}
	public double getDC_Voltage_Input_4() {
		return DC_Voltage_Input_4;
	}
	public void setDC_Voltage_Input_4(double dC_Voltage_Input_4) {
		DC_Voltage_Input_4 = dC_Voltage_Input_4;
	}
	public double getDC_Power_Input_1() {
		return DC_Power_Input_1;
	}
	public void setDC_Power_Input_1(double dC_Power_Input_1) {
		DC_Power_Input_1 = dC_Power_Input_1;
	}
	public double getDC_Power_Input_2() {
		return DC_Power_Input_2;
	}
	public void setDC_Power_Input_2(double dC_Power_Input_2) {
		DC_Power_Input_2 = dC_Power_Input_2;
	}
	public double getDC_Power_Input_3() {
		return DC_Power_Input_3;
	}
	public void setDC_Power_Input_3(double dC_Power_Input_3) {
		DC_Power_Input_3 = dC_Power_Input_3;
	}
	public double getDC_Power_Input_4() {
		return DC_Power_Input_4;
	}
	public void setDC_Power_Input_4(double dC_Power_Input_4) {
		DC_Power_Input_4 = dC_Power_Input_4;
	}
	public double getGrid_Frequency() {
		return Grid_Frequency;
	}
	public void setGrid_Frequency(double grid_Frequency) {
		Grid_Frequency = grid_Frequency;
	}
	public double getApparent_Power() {
		return Apparent_Power;
	}
	public void setApparent_Power(double apparent_Power) {
		Apparent_Power = apparent_Power;
	}
	public double getReactive_Power() {
		return Reactive_Power;
	}
	public void setReactive_Power(double reactive_Power) {
		Reactive_Power = reactive_Power;
	}
	public double getActive_Power() {
		return Active_Power;
	}
	public void setActive_Power(double active_Power) {
		Active_Power = active_Power;
	}
	public double getPower_Factor() {
		return Power_Factor;
	}
	public void setPower_Factor(double power_Factor) {
		Power_Factor = power_Factor;
	}
	public double getGrid_Current() {
		return Grid_Current;
	}
	public void setGrid_Current(double grid_Current) {
		Grid_Current = grid_Current;
	}
	public double getCurrent_Phase_1() {
		return Current_Phase_1;
	}
	public void setCurrent_Phase_1(double current_Phase_1) {
		Current_Phase_1 = current_Phase_1;
	}
	public double getCurrent_Phase_2() {
		return Current_Phase_2;
	}
	public void setCurrent_Phase_2(double current_Phase_2) {
		Current_Phase_2 = current_Phase_2;
	}
	public double getCurrent_Phase_3() {
		return Current_Phase_3;
	}
	public void setCurrent_Phase_3(double current_Phase_3) {
		Current_Phase_3 = current_Phase_3;
	}
	public double getActive_Power_Phase_1() {
		return Active_Power_Phase_1;
	}
	public void setActive_Power_Phase_1(double active_Power_Phase_1) {
		Active_Power_Phase_1 = active_Power_Phase_1;
	}
	public double getActive_Power_Phase_2() {
		return Active_Power_Phase_2;
	}
	public void setActive_Power_Phase_2(double active_Power_Phase_2) {
		Active_Power_Phase_2 = active_Power_Phase_2;
	}
	public double getActive_Power_Phase_3() {
		return Active_Power_Phase_3;
	}
	public void setActive_Power_Phase_3(double active_Power_Phase_3) {
		Active_Power_Phase_3 = active_Power_Phase_3;
	}
	public double getReactive_Power_Phase_1() {
		return Reactive_Power_Phase_1;
	}
	public void setReactive_Power_Phase_1(double reactive_Power_Phase_1) {
		Reactive_Power_Phase_1 = reactive_Power_Phase_1;
	}
	public double getReactive_Power_Phase_2() {
		return Reactive_Power_Phase_2;
	}
	public void setReactive_Power_Phase_2(double reactive_Power_Phase_2) {
		Reactive_Power_Phase_2 = reactive_Power_Phase_2;
	}
	public double getReactive_Power_Phase_3() {
		return Reactive_Power_Phase_3;
	}
	public void setReactive_Power_Phase_3(double reactive_Power_Phase_3) {
		Reactive_Power_Phase_3 = reactive_Power_Phase_3;
	}
	public double getApparent_Power_Phase_1() {
		return Apparent_Power_Phase_1;
	}
	public void setApparent_Power_Phase_1(double apparent_Power_Phase_1) {
		Apparent_Power_Phase_1 = apparent_Power_Phase_1;
	}
	public double getApparent_Power_Phase_2() {
		return Apparent_Power_Phase_2;
	}
	public void setApparent_Power_Phase_2(double apparent_Power_Phase_2) {
		Apparent_Power_Phase_2 = apparent_Power_Phase_2;
	}
	public double getApparent_Power_Phase_3() {
		return Apparent_Power_Phase_3;
	}
	public void setApparent_Power_Phase_3(double apparent_Power_Phase_3) {
		Apparent_Power_Phase_3 = apparent_Power_Phase_3;
	}
	public double getVoltage_Phase_1() {
		return Voltage_Phase_1;
	}
	public void setVoltage_Phase_1(double voltage_Phase_1) {
		Voltage_Phase_1 = voltage_Phase_1;
	}
	public double getVoltage_Phase_2() {
		return Voltage_Phase_2;
	}
	public void setVoltage_Phase_2(double voltage_Phase_2) {
		Voltage_Phase_2 = voltage_Phase_2;
	}
	public double getVoltage_Phase_3() {
		return Voltage_Phase_3;
	}
	public void setVoltage_Phase_3(double voltage_Phase_3) {
		Voltage_Phase_3 = voltage_Phase_3;
	}
	public double getVoltage_Phase_1_2() {
		return Voltage_Phase_1_2;
	}
	public void setVoltage_Phase_1_2(double voltage_Phase_1_2) {
		Voltage_Phase_1_2 = voltage_Phase_1_2;
	}
	public double getVoltage_Phase_2_3() {
		return Voltage_Phase_2_3;
	}
	public void setVoltage_Phase_2_3(double voltage_Phase_2_3) {
		Voltage_Phase_2_3 = voltage_Phase_2_3;
	}
	public double getVoltage_Phase_3_1() {
		return Voltage_Phase_3_1;
	}
	public void setVoltage_Phase_3_1(double voltage_Phase_3_1) {
		Voltage_Phase_3_1 = voltage_Phase_3_1;
	}
	public double getResidual_DC_Current() {
		return Residual_DC_Current;
	}
	public void setResidual_DC_Current(double residual_DC_Current) {
		Residual_DC_Current = residual_DC_Current;
	}
	public double getInsulation_Resistance() {
		return Insulation_Resistance;
	}
	public void setInsulation_Resistance(double insulation_Resistance) {
		Insulation_Resistance = insulation_Resistance;
	}
	public double getFeed_In_Time() {
		return Feed_In_Time;
	}
	public void setFeed_In_Time(double feed_In_Time) {
		Feed_In_Time = feed_In_Time;
	}
	public double getOperating_Time() {
		return Operating_Time;
	}
	public void setOperating_Time(double operating_Time) {
		Operating_Time = operating_Time;
	}
	public double getTotal_Yield() {
		return Total_Yield;
	}
	public void setTotal_Yield(double total_Yield) {
		Total_Yield = total_Yield;
	}
	public double getInternal_Temperature() {
		return Internal_Temperature;
	}
	public void setInternal_Temperature(double internal_Temperature) {
		Internal_Temperature = internal_Temperature;
	}
	public double getDC_Current_Input_1() {
		return DC_Current_Input_1;
	}
	public void setDC_Current_Input_1(double dC_Current_Input_1) {
		DC_Current_Input_1 = dC_Current_Input_1;
	}
	public double getDC_Current_Input_2() {
		return DC_Current_Input_2;
	}
	public void setDC_Current_Input_2(double dC_Current_Input_2) {
		DC_Current_Input_2 = dC_Current_Input_2;
	}
	public double getDC_Current_Input_3() {
		return DC_Current_Input_3;
	}
	public void setDC_Current_Input_3(double dC_Current_Input_3) {
		DC_Current_Input_3 = dC_Current_Input_3;
	}
	public double getDC_Current_Input_4() {
		return DC_Current_Input_4;
	}
	public void setDC_Current_Input_4(double dC_Current_Input_4) {
		DC_Current_Input_4 = dC_Current_Input_4;
	}
}
