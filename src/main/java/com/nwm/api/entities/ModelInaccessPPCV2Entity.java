/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelInaccessPPCV2Entity extends ModelBaseEntity {
	private double AI_Active_Power_Feedback_pct;
	private double AI_Active_Power_Ramp_Down_Feedback;
	private double AI_Active_Power_Ramp_Up_Feedback;
	private double AI_Auto_Volt_Ctrl_Deadband_Feedback;
	private double AI_Auto_Volt_Ctrl_Feedback;
	private double AI_Frequency_Ctrl_Feedback;
	private double AI_Over_Frequency_Droop_Feedback;
	private double AI_Power_Factor_Feedback;
	private double AI_Reactive_Power_Feedback;
	private double AI_Under_Frequency_Droop_Feedback;
	private double AO_Active_Power_Ramp_Down_Setpoint;
	private double AO_Active_Power_Ramp_Up_Setpoint;
	private double AO_Active_Power_Setpoint;
	private double AO_Auto_Volt_Ctrl_Setpoint;
	private double AO_Over_Frequency_Droop_Setpoint;
	private double AO_Power_Factor_Setpoint;
	private double AO_Reactive_Power_Setpoint;
	private double AO_Under_Frequency_Droop_Setpoint;
	private double DI_Auto_Volt_Reg_Status;
	private double DI_Freq_Ctrl_Event_Status;
	private double DI_Freq_Ctrl_Status;
	private double DI_Local_Remote_Status;
	private double DI_P_Ctrl_Ramp_Rate_Status;
	private double DI_P_Ctrl_Status;
	private double DI_PF_Ctrl_Status;
	private double DI_Q_Ctrl_Status;
	private double DO_All_Inverters_On_Off;
	private double DO_Auto_Volt_Reg_Set;
	private double DO_Freq_Ctrl_Set;
	private double DO_Local_Remote_Set;
	private double DO_P_Ctrl_Ramp_Rate_Set;
	private double DO_P_Ctrl_Set;
	private double DO_PF_Ctrl_Set;
	private double DO_Q_Ctrl_Set;
	public double getAI_Active_Power_Feedback_pct() {
		return AI_Active_Power_Feedback_pct;
	}
	public void setAI_Active_Power_Feedback_pct(double aI_Active_Power_Feedback_pct) {
		AI_Active_Power_Feedback_pct = aI_Active_Power_Feedback_pct;
	}
	public double getAI_Active_Power_Ramp_Down_Feedback() {
		return AI_Active_Power_Ramp_Down_Feedback;
	}
	public void setAI_Active_Power_Ramp_Down_Feedback(double aI_Active_Power_Ramp_Down_Feedback) {
		AI_Active_Power_Ramp_Down_Feedback = aI_Active_Power_Ramp_Down_Feedback;
	}
	public double getAI_Active_Power_Ramp_Up_Feedback() {
		return AI_Active_Power_Ramp_Up_Feedback;
	}
	public void setAI_Active_Power_Ramp_Up_Feedback(double aI_Active_Power_Ramp_Up_Feedback) {
		AI_Active_Power_Ramp_Up_Feedback = aI_Active_Power_Ramp_Up_Feedback;
	}
	public double getAI_Auto_Volt_Ctrl_Deadband_Feedback() {
		return AI_Auto_Volt_Ctrl_Deadband_Feedback;
	}
	public void setAI_Auto_Volt_Ctrl_Deadband_Feedback(double aI_Auto_Volt_Ctrl_Deadband_Feedback) {
		AI_Auto_Volt_Ctrl_Deadband_Feedback = aI_Auto_Volt_Ctrl_Deadband_Feedback;
	}
	public double getAI_Auto_Volt_Ctrl_Feedback() {
		return AI_Auto_Volt_Ctrl_Feedback;
	}
	public void setAI_Auto_Volt_Ctrl_Feedback(double aI_Auto_Volt_Ctrl_Feedback) {
		AI_Auto_Volt_Ctrl_Feedback = aI_Auto_Volt_Ctrl_Feedback;
	}
	public double getAI_Frequency_Ctrl_Feedback() {
		return AI_Frequency_Ctrl_Feedback;
	}
	public void setAI_Frequency_Ctrl_Feedback(double aI_Frequency_Ctrl_Feedback) {
		AI_Frequency_Ctrl_Feedback = aI_Frequency_Ctrl_Feedback;
	}
	public double getAI_Over_Frequency_Droop_Feedback() {
		return AI_Over_Frequency_Droop_Feedback;
	}
	public void setAI_Over_Frequency_Droop_Feedback(double aI_Over_Frequency_Droop_Feedback) {
		AI_Over_Frequency_Droop_Feedback = aI_Over_Frequency_Droop_Feedback;
	}
	public double getAI_Power_Factor_Feedback() {
		return AI_Power_Factor_Feedback;
	}
	public void setAI_Power_Factor_Feedback(double aI_Power_Factor_Feedback) {
		AI_Power_Factor_Feedback = aI_Power_Factor_Feedback;
	}
	public double getAI_Reactive_Power_Feedback() {
		return AI_Reactive_Power_Feedback;
	}
	public void setAI_Reactive_Power_Feedback(double aI_Reactive_Power_Feedback) {
		AI_Reactive_Power_Feedback = aI_Reactive_Power_Feedback;
	}
	public double getAI_Under_Frequency_Droop_Feedback() {
		return AI_Under_Frequency_Droop_Feedback;
	}
	public void setAI_Under_Frequency_Droop_Feedback(double aI_Under_Frequency_Droop_Feedback) {
		AI_Under_Frequency_Droop_Feedback = aI_Under_Frequency_Droop_Feedback;
	}
	public double getAO_Active_Power_Ramp_Down_Setpoint() {
		return AO_Active_Power_Ramp_Down_Setpoint;
	}
	public void setAO_Active_Power_Ramp_Down_Setpoint(double aO_Active_Power_Ramp_Down_Setpoint) {
		AO_Active_Power_Ramp_Down_Setpoint = aO_Active_Power_Ramp_Down_Setpoint;
	}
	public double getAO_Active_Power_Ramp_Up_Setpoint() {
		return AO_Active_Power_Ramp_Up_Setpoint;
	}
	public void setAO_Active_Power_Ramp_Up_Setpoint(double aO_Active_Power_Ramp_Up_Setpoint) {
		AO_Active_Power_Ramp_Up_Setpoint = aO_Active_Power_Ramp_Up_Setpoint;
	}
	public double getAO_Active_Power_Setpoint() {
		return AO_Active_Power_Setpoint;
	}
	public void setAO_Active_Power_Setpoint(double aO_Active_Power_Setpoint) {
		AO_Active_Power_Setpoint = aO_Active_Power_Setpoint;
	}
	public double getAO_Auto_Volt_Ctrl_Setpoint() {
		return AO_Auto_Volt_Ctrl_Setpoint;
	}
	public void setAO_Auto_Volt_Ctrl_Setpoint(double aO_Auto_Volt_Ctrl_Setpoint) {
		AO_Auto_Volt_Ctrl_Setpoint = aO_Auto_Volt_Ctrl_Setpoint;
	}
	public double getAO_Over_Frequency_Droop_Setpoint() {
		return AO_Over_Frequency_Droop_Setpoint;
	}
	public void setAO_Over_Frequency_Droop_Setpoint(double aO_Over_Frequency_Droop_Setpoint) {
		AO_Over_Frequency_Droop_Setpoint = aO_Over_Frequency_Droop_Setpoint;
	}
	public double getAO_Power_Factor_Setpoint() {
		return AO_Power_Factor_Setpoint;
	}
	public void setAO_Power_Factor_Setpoint(double aO_Power_Factor_Setpoint) {
		AO_Power_Factor_Setpoint = aO_Power_Factor_Setpoint;
	}
	public double getAO_Reactive_Power_Setpoint() {
		return AO_Reactive_Power_Setpoint;
	}
	public void setAO_Reactive_Power_Setpoint(double aO_Reactive_Power_Setpoint) {
		AO_Reactive_Power_Setpoint = aO_Reactive_Power_Setpoint;
	}
	public double getAO_Under_Frequency_Droop_Setpoint() {
		return AO_Under_Frequency_Droop_Setpoint;
	}
	public void setAO_Under_Frequency_Droop_Setpoint(double aO_Under_Frequency_Droop_Setpoint) {
		AO_Under_Frequency_Droop_Setpoint = aO_Under_Frequency_Droop_Setpoint;
	}
	public double getDI_Auto_Volt_Reg_Status() {
		return DI_Auto_Volt_Reg_Status;
	}
	public void setDI_Auto_Volt_Reg_Status(double dI_Auto_Volt_Reg_Status) {
		DI_Auto_Volt_Reg_Status = dI_Auto_Volt_Reg_Status;
	}
	public double getDI_Freq_Ctrl_Event_Status() {
		return DI_Freq_Ctrl_Event_Status;
	}
	public void setDI_Freq_Ctrl_Event_Status(double dI_Freq_Ctrl_Event_Status) {
		DI_Freq_Ctrl_Event_Status = dI_Freq_Ctrl_Event_Status;
	}
	public double getDI_Freq_Ctrl_Status() {
		return DI_Freq_Ctrl_Status;
	}
	public void setDI_Freq_Ctrl_Status(double dI_Freq_Ctrl_Status) {
		DI_Freq_Ctrl_Status = dI_Freq_Ctrl_Status;
	}
	public double getDI_Local_Remote_Status() {
		return DI_Local_Remote_Status;
	}
	public void setDI_Local_Remote_Status(double dI_Local_Remote_Status) {
		DI_Local_Remote_Status = dI_Local_Remote_Status;
	}
	public double getDI_P_Ctrl_Ramp_Rate_Status() {
		return DI_P_Ctrl_Ramp_Rate_Status;
	}
	public void setDI_P_Ctrl_Ramp_Rate_Status(double dI_P_Ctrl_Ramp_Rate_Status) {
		DI_P_Ctrl_Ramp_Rate_Status = dI_P_Ctrl_Ramp_Rate_Status;
	}
	public double getDI_P_Ctrl_Status() {
		return DI_P_Ctrl_Status;
	}
	public void setDI_P_Ctrl_Status(double dI_P_Ctrl_Status) {
		DI_P_Ctrl_Status = dI_P_Ctrl_Status;
	}
	public double getDI_PF_Ctrl_Status() {
		return DI_PF_Ctrl_Status;
	}
	public void setDI_PF_Ctrl_Status(double dI_PF_Ctrl_Status) {
		DI_PF_Ctrl_Status = dI_PF_Ctrl_Status;
	}
	public double getDI_Q_Ctrl_Status() {
		return DI_Q_Ctrl_Status;
	}
	public void setDI_Q_Ctrl_Status(double dI_Q_Ctrl_Status) {
		DI_Q_Ctrl_Status = dI_Q_Ctrl_Status;
	}
	public double getDO_All_Inverters_On_Off() {
		return DO_All_Inverters_On_Off;
	}
	public void setDO_All_Inverters_On_Off(double dO_All_Inverters_On_Off) {
		DO_All_Inverters_On_Off = dO_All_Inverters_On_Off;
	}
	public double getDO_Auto_Volt_Reg_Set() {
		return DO_Auto_Volt_Reg_Set;
	}
	public void setDO_Auto_Volt_Reg_Set(double dO_Auto_Volt_Reg_Set) {
		DO_Auto_Volt_Reg_Set = dO_Auto_Volt_Reg_Set;
	}
	public double getDO_Freq_Ctrl_Set() {
		return DO_Freq_Ctrl_Set;
	}
	public void setDO_Freq_Ctrl_Set(double dO_Freq_Ctrl_Set) {
		DO_Freq_Ctrl_Set = dO_Freq_Ctrl_Set;
	}
	public double getDO_Local_Remote_Set() {
		return DO_Local_Remote_Set;
	}
	public void setDO_Local_Remote_Set(double dO_Local_Remote_Set) {
		DO_Local_Remote_Set = dO_Local_Remote_Set;
	}
	public double getDO_P_Ctrl_Ramp_Rate_Set() {
		return DO_P_Ctrl_Ramp_Rate_Set;
	}
	public void setDO_P_Ctrl_Ramp_Rate_Set(double dO_P_Ctrl_Ramp_Rate_Set) {
		DO_P_Ctrl_Ramp_Rate_Set = dO_P_Ctrl_Ramp_Rate_Set;
	}
	public double getDO_P_Ctrl_Set() {
		return DO_P_Ctrl_Set;
	}
	public void setDO_P_Ctrl_Set(double dO_P_Ctrl_Set) {
		DO_P_Ctrl_Set = dO_P_Ctrl_Set;
	}
	public double getDO_PF_Ctrl_Set() {
		return DO_PF_Ctrl_Set;
	}
	public void setDO_PF_Ctrl_Set(double dO_PF_Ctrl_Set) {
		DO_PF_Ctrl_Set = dO_PF_Ctrl_Set;
	}
	public double getDO_Q_Ctrl_Set() {
		return DO_Q_Ctrl_Set;
	}
	public void setDO_Q_Ctrl_Set(double dO_Q_Ctrl_Set) {
		DO_Q_Ctrl_Set = dO_Q_Ctrl_Set;
	}
	
	
}
