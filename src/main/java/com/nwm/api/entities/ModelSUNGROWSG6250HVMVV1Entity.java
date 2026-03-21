/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSUNGROWSG6250HVMVV1Entity extends ModelBaseEntity {
	private double AC_Active_Power;
	private double AC_Current_L1;
	private double AC_Current_L2;
	private double AC_Current_L3;
	private double AC_Apparent_Power;
	private double CO2_Emission;
	private double Day_Yield;
	private double DC_Current_Input_1;
	private double DC_Current_Input_2;
	private double DC_Power_Input;
	private double DC_Voltage_Input_1;
	private double DC_Voltage_Input_2;
	private double Emergency_Stop;
	private double AC_Frequency;
	private double Insulation_Neg_Mod_1;
	private double Insulation_Neg_Mod_2;
	private double Insulation_Pos_Mod_1;
	private double Insulation_Pos_Mod_2;
	private double Local_Remote_Mode;
	private double Operating_Time;
	private double Operating_Stat;
	private double P_Ctrl_Percentage;
	private double Power_Factor;
	private double AC_Reactive_Power;
	private double START_STOP;
	private double Total_Yield;
	private double AC_Voltage_AB;
	private double AC_Voltage_BC;
	private double AC_Voltage_CA;
	private double M1ASAC_Switch_Abnormal;
	private double M1ASBypass_Board_Communication_Abnormal;
	private double M1ASContactor_Contact_Abnormal;
	private double M1ASCT_Unbalance;
	private double M1ASDC_Bypass_Forward_Overcurrent_Alarm;
	private double M1ASDC_Sensor_Abnormal;
	private double M1ASDC_Switch_Abnormal;
	private double M1ASElectricity_Meter_Communication_Abnormal;
	private double M1ASExternal_Power_Supply_Abnormal;
	private double M1ASFan_2_Abnormal;
	private double M1ASFan_Abnormal;
	private double M1ASGFRT_Operation;
	private double M1ASGround_Fuse_Abnormal;
	private double M1ASLow_Insulation_Resistance;
	private double M1ASTemperature_Abnormal_Alarm;
	private double M1FS1AC_Leakage_Current_Protection;
	private double M1FS1AC_Overcurrent;
	private double M1FS1AC_Overvoltage;
	private double M1FS1AC_Undervoltage;
	private double M1FS1Contactor_Fault;
	private double M1FS1DC_Fuse_Fault;
	private double M1FS1DC_Leakage_Current_Protection;
	private double M1FS1DC_Overcurrent;
	private double M1FS1DC_Overvoltage;
	private double M1FS1DC_Undervoltage;
	private double M1FS1Detection_Fuse_Fault;
	private double M1FS1Fan_Fault;
	private double M1FS1Frequency_Abnormal;
	private double M1FS1GFDI_Protection;
	private double M1FS1Hardware_Fault;
	private double M1FS1Heat_Sik_Over_Temperature;
	private double M1FS1Islanding_Protection;
	private double M1FS1Module_Overtemperature;
	private double M1FS1Overfrequency;
	private double M1FS1Overload_Protection;
	private double M1FS1PDP_Protection;
	private double M1FS1Reactor_Overtemperature;
	private double M1FS1Sensor_Failure;
	private double M1FS1Temperature_Abnormal;
	private double M1FS1Transformer_Overtemperature;
	private double M1FS1Underfrequency;
	private double M1FS2AC_Current_Unbalance;
	private double M1FS2AC_SPD_Fault;
	private double M1FS2DC_SPD_Fault;
	private double M1FS2PV_Polarity_Reversed;
	private double M1FS2AC_Fuse_Fault;
	private double M1FS2AC_Switch_Disconnection;
	private double M1FS2AC_Switch_Fault;
	private double M1FS2Backup_Power_Supply_Abnormal;
	private double M1FS2Buffer_Contactor_Fault;
	private double M1FS2Bypass_Reverse_Overcurrent;
	private double M1FS2Control_Cabinet_Temperature_Abnormal;
	private double M1FS2Control_Power_Supply_Abnormal;
	private double M1FS2Current_Unbalance_2;
	private double M1FS2Current_Unbalance_3;
	private double M1FS2DC_Cabinet_Over_Temperature;
	private double M1FS2DC_Fuse_Grounding_Fault;
	private double M1FS2DC_Injection_Fault;
	private double M1FS2DC_Switch_Abnormal;
	private double M1FS2DC_Switch_Fault;
	private double M1FS2DC_Voltage_Sampling_Fault;
	private double M1FS2Drive_Board_Fault;
	private double M1FS2Fan_2_Fault;
	private double M1FS2Grid_DC_Injection_Fault;
	private double M1FS2Grid_Voltage_Unbalance;
	private double M1FS2Insulation_Impedance;
	private double M1FS2Reverse_Charging_Power_Supply_Abnormal;
	private double M1FS2Sampling_Fault;
	private double M1FS2Soft_Start_Fault;
	private double M1FS3Carrier_Sync_Fault;
	private double M1FS3Device_Code_Repeat_Fault;
	private double M1FS3Neutral_Point_Potential_Shift;
	private double M1FS3Parallel_Operation_Communication_Fault;
	private double M1NS1AC_Circuit_Breaker_State;
	private double M1NS1AC_Main_Contactor_State;
	private double M1NS1ACB_Fault_State;
	private double M1NS1DC_Switch_State_1;
	private double M1NS1DC_Switch_State_2;
	private double M1NS1DC_Switch_State_3;
	private double M1NS1DC_Switch_State_4;
	private double M1NS1External_Power_Supply_State;
	private double M1WSAlarm_Running;
	private double M1WSDerating_Running;
	private double M1WSEmergency_Stop;
	private double M1WSFault_Stop;
	private double M1WSInitial_Standby;
	private double M1WSIO_DSP_Communication_Abnormal;
	private double M1WSIO_MDC_Communication_Abnormal;
	private double M1WSKey_Stop;
	private double M1WSRunning;
	private double M1WSSleeping;
	private double M1WSStandby;
	private double M1WSStarting;
	private double M1WSStopped;
	private double M1WSStopping;
	private double M1WSTotal_Signal_Bit_Of_Running_State;
	private double M1WSTotal_Stop_Bit;
	private double M2ASAC_Switch_Abnormal;
	private double M2ASBypass_Board_Communication_Abnormal;
	private double M2ASContactor_Contact_Abnormal;
	private double M2ASCT_Unbalance;
	private double M2ASDC_Bypass_Forward_Overcurrent_Alarm;
	private double M2ASDC_Sensor_Abnormal;
	private double M2ASDC_Switch_Abnormal;
	private double M2ASElectricity_Meter_Communication_Abnormal;
	private double M2ASExternal_Power_Supply_Abnormal;
	private double M2ASFan_2_Abnormal;
	private double M2ASFan_Abnormal;
	private double M2ASGFRT_Operation;
	private double M2ASGround_Fuse_Abnormal;
	private double M2ASLow_Insulation_Resistance;
	private double M2ASTemperature_Abnormal_Alarm;
	private double M2FS1AC_Leakage_Current_Protection;
	private double M2FS1AC_Overcurrent;
	private double M2FS1AC_Overvoltage;
	private double M2FS1AC_Undervoltage;
	private double M2FS1Contactor_Fault;
	private double M2FS1DC_Fuse_Fault;
	private double M2FS1DC_Leakage_Current_Protection;
	private double M2FS1DC_Overcurrent;
	private double M2FS1DC_Overvoltage;
	private double M2FS1DC_Undervoltage;
	private double M2FS1Detection_Fuse_Fault;
	private double M2FS1Fan_Fault;
	private double M2FS1Frequency_Abnormal;
	private double M2FS1GFDI_Protection;
	private double M2FS1Hardware_Fault;
	private double M2FS1Heat_Sik_Over_Temperature;
	private double M2FS1Islanding_Protection;
	private double M2FS1Module_Overtemperature;
	private double M2FS1Overfrequency;
	private double M2FS1Overload_Protection;
	private double M2FS1PDP_Protection;
	private double M2FS1Reactor_Overtemperature;
	private double M2FS1Sensor_Failure;
	private double M2FS1Temperature_Abnormal;
	private double M2FS1Transformer_Overtemperature;
	private double M2FS1Underfrequency;
	private double M2FS2AC_Current_Unbalance;
	private double M2FS2AC_SPD_Fault;
	private double M2FS2DC_SPD_Fault;
	private double M2FS2PV_Polarity_Reversed;
	private double M2FS2AC_Fuse_Fault;
	private double M2FS2AC_Switch_Disconnection;
	private double M2FS2AC_Switch_Fault;
	private double M2FS2Backup_Power_Supply_Abnormal;
	private double M2FS2Buffer_Contactor_Fault;
	private double M2FS2Bypass_Reverse_Overcurrent;
	private double M2FS2Control_Cabinet_Temperature_Abnormal;
	private double M2FS2Control_Power_Supply_Abnormal;
	private double M2FS2Current_Unbalance_2;
	private double M2FS2Current_Unbalance_3;
	private double M2FS2DC_Cabinet_Over_Temperature;
	private double M2FS2DC_Fuse_Grounding_Fault;
	private double M2FS2DC_Injection_Fault;
	private double M2FS2DC_Switch_Abnormal;
	private double M2FS2DC_Switch_Fault;
	private double M2FS2DC_Voltage_Sampling_Fault;
	private double M2FS2Drive_Board_Fault;
	private double M2FS2Fan_2_Fault;
	private double M2FS2Grid_DC_Injection_Fault;
	private double M2FS2Grid_Voltage_Unbalance;
	private double M2FS2Insulation_Impedance;
	private double M2FS2Reverse_Charging_Power_Supply_Abnormal;
	private double M2FS2Sampling_Fault;
	private double M2FS2Soft_Start_Fault;
	private double M2FS3Carrier_Sync_Fault;
	private double M2FS3Device_Code_Repeat_Fault;
	private double M2FS3Neutral_Point_Potential_Shift;
	private double M2FS3Parallel_Operation_Communication_Fault;
	private double M2NS1AC_Circuit_Breaker_State;
	private double M2NS1AC_Main_Contactor_State;
	private double M2NS1ACB_Fault_State;
	private double M2NS1DC_Switch_State_1;
	private double M2NS1DC_Switch_State_2;
	private double M2NS1DC_Switch_State_3;
	private double M2NS1DC_Switch_State_4;
	private double M2NS1External_Power_Supply_State;
	private double M2WSAlarm_Running;
	private double M2WSAnti_PID_Running;
	private double M2WSDerating_Running;
	private double M2WSEmergency_Stop;
	private double M2WSFault_Stop;
	private double M2WSInitial_Standby;
	private double M2WSIO_DSP_Communication_Abnormal;
	private double M2WSIO_MDC_Communication_Abnormal;
	private double M2WSKey_Stop;
	private double M2WSRunning;
	private double M2WSSleeping;
	private double M2WSSleeping1;
	private double M2WSStandby;
	private double M2WSStarting;
	private double M2WSStopped;
	private double M2WSStopping;
	private double M2WSTotal_Signal_Bit_Of_Running_State;
	private double M2WSTotal_Stop_Bit;
	private double MV_NODE_STATE_Coil_Temp_Alarm;
	private double MV_NODE_STATE_Coil_Temp_Trip;
	private double MV_NODE_STATE_Gas_Relay_Alarm;
	private double MV_NODE_STATE_Gas_Relay_Trip;
	private double MV_NODE_STATE_Low_Oil_Level_Alarm;
	private double MV_NODE_STATE_Low_Oil_Level_Trip;
	private double MV_NODE_STATE_MV_DS;
	private double MV_NODE_STATE_MV_Load_Switch_1;
	private double MV_NODE_STATE_MV_Load_Switch_2;
	private double MV_NODE_STATE_MV_VCB;
	private double MV_NODE_STATE_Oil_Temp_Alarm;
	private double MV_NODE_STATE_Oil_Temp_Trip;
	private double MV_NODE_STATE_Overcurrent_Protection;
	private double MV_NODE_STATE_Pressure_Relief_Alarm;
	private double MV_NODE_STATE_Pressure_Relief_Trip;
	private double WORK_STATE_DOOR_OPEN_PROT;
	private double WORK_STATE_EXTERNAL_EMER_STOP;
	private double WORK_STATE_LOCAL_EMER_STOP;
	private double WORK_STATE_MV_FAULT;
	private double WORK_STATE_REM_EMER_STOP;
	private double WORK_STATE_RUNNING;
	private double WORK_STATE_SMOKE_PROT;
	private double WORK_STATE_STOPPED;
	public double getAC_Active_Power() {
		return AC_Active_Power;
	}
	public void setAC_Active_Power(double aC_Active_Power) {
		AC_Active_Power = aC_Active_Power;
	}
	public double getAC_Current_L1() {
		return AC_Current_L1;
	}
	public void setAC_Current_L1(double aC_Current_L1) {
		AC_Current_L1 = aC_Current_L1;
	}
	public double getAC_Current_L2() {
		return AC_Current_L2;
	}
	public void setAC_Current_L2(double aC_Current_L2) {
		AC_Current_L2 = aC_Current_L2;
	}
	public double getAC_Current_L3() {
		return AC_Current_L3;
	}
	public void setAC_Current_L3(double aC_Current_L3) {
		AC_Current_L3 = aC_Current_L3;
	}
	public double getAC_Apparent_Power() {
		return AC_Apparent_Power;
	}
	public void setAC_Apparent_Power(double aC_Apparent_Power) {
		AC_Apparent_Power = aC_Apparent_Power;
	}
	public double getCO2_Emission() {
		return CO2_Emission;
	}
	public void setCO2_Emission(double cO2_Emission) {
		CO2_Emission = cO2_Emission;
	}
	public double getDay_Yield() {
		return Day_Yield;
	}
	public void setDay_Yield(double day_Yield) {
		Day_Yield = day_Yield;
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
	public double getDC_Power_Input() {
		return DC_Power_Input;
	}
	public void setDC_Power_Input(double dC_Power_Input) {
		DC_Power_Input = dC_Power_Input;
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
	public double getEmergency_Stop() {
		return Emergency_Stop;
	}
	public void setEmergency_Stop(double emergency_Stop) {
		Emergency_Stop = emergency_Stop;
	}
	public double getAC_Frequency() {
		return AC_Frequency;
	}
	public void setAC_Frequency(double aC_Frequency) {
		AC_Frequency = aC_Frequency;
	}
	public double getInsulation_Neg_Mod_1() {
		return Insulation_Neg_Mod_1;
	}
	public void setInsulation_Neg_Mod_1(double insulation_Neg_Mod_1) {
		Insulation_Neg_Mod_1 = insulation_Neg_Mod_1;
	}
	public double getInsulation_Neg_Mod_2() {
		return Insulation_Neg_Mod_2;
	}
	public void setInsulation_Neg_Mod_2(double insulation_Neg_Mod_2) {
		Insulation_Neg_Mod_2 = insulation_Neg_Mod_2;
	}
	public double getInsulation_Pos_Mod_1() {
		return Insulation_Pos_Mod_1;
	}
	public void setInsulation_Pos_Mod_1(double insulation_Pos_Mod_1) {
		Insulation_Pos_Mod_1 = insulation_Pos_Mod_1;
	}
	public double getInsulation_Pos_Mod_2() {
		return Insulation_Pos_Mod_2;
	}
	public void setInsulation_Pos_Mod_2(double insulation_Pos_Mod_2) {
		Insulation_Pos_Mod_2 = insulation_Pos_Mod_2;
	}
	public double getLocal_Remote_Mode() {
		return Local_Remote_Mode;
	}
	public void setLocal_Remote_Mode(double local_Remote_Mode) {
		Local_Remote_Mode = local_Remote_Mode;
	}
	public double getOperating_Time() {
		return Operating_Time;
	}
	public void setOperating_Time(double operating_Time) {
		Operating_Time = operating_Time;
	}
	public double getOperating_Stat() {
		return Operating_Stat;
	}
	public void setOperating_Stat(double operating_Stat) {
		Operating_Stat = operating_Stat;
	}
	public double getP_Ctrl_Percentage() {
		return P_Ctrl_Percentage;
	}
	public void setP_Ctrl_Percentage(double p_Ctrl_Percentage) {
		P_Ctrl_Percentage = p_Ctrl_Percentage;
	}
	public double getPower_Factor() {
		return Power_Factor;
	}
	public void setPower_Factor(double power_Factor) {
		Power_Factor = power_Factor;
	}
	public double getAC_Reactive_Power() {
		return AC_Reactive_Power;
	}
	public void setAC_Reactive_Power(double aC_Reactive_Power) {
		AC_Reactive_Power = aC_Reactive_Power;
	}
	public double getSTART_STOP() {
		return START_STOP;
	}
	public void setSTART_STOP(double sTART_STOP) {
		START_STOP = sTART_STOP;
	}
	public double getTotal_Yield() {
		return Total_Yield;
	}
	public void setTotal_Yield(double total_Yield) {
		Total_Yield = total_Yield;
	}
	public double getAC_Voltage_AB() {
		return AC_Voltage_AB;
	}
	public void setAC_Voltage_AB(double aC_Voltage_AB) {
		AC_Voltage_AB = aC_Voltage_AB;
	}
	public double getAC_Voltage_BC() {
		return AC_Voltage_BC;
	}
	public void setAC_Voltage_BC(double aC_Voltage_BC) {
		AC_Voltage_BC = aC_Voltage_BC;
	}
	public double getAC_Voltage_CA() {
		return AC_Voltage_CA;
	}
	public void setAC_Voltage_CA(double aC_Voltage_CA) {
		AC_Voltage_CA = aC_Voltage_CA;
	}
	public double getM1ASAC_Switch_Abnormal() {
		return M1ASAC_Switch_Abnormal;
	}
	public void setM1ASAC_Switch_Abnormal(double m1asac_Switch_Abnormal) {
		M1ASAC_Switch_Abnormal = m1asac_Switch_Abnormal;
	}
	public double getM1ASBypass_Board_Communication_Abnormal() {
		return M1ASBypass_Board_Communication_Abnormal;
	}
	public void setM1ASBypass_Board_Communication_Abnormal(double m1asBypass_Board_Communication_Abnormal) {
		M1ASBypass_Board_Communication_Abnormal = m1asBypass_Board_Communication_Abnormal;
	}
	public double getM1ASContactor_Contact_Abnormal() {
		return M1ASContactor_Contact_Abnormal;
	}
	public void setM1ASContactor_Contact_Abnormal(double m1asContactor_Contact_Abnormal) {
		M1ASContactor_Contact_Abnormal = m1asContactor_Contact_Abnormal;
	}
	public double getM1ASCT_Unbalance() {
		return M1ASCT_Unbalance;
	}
	public void setM1ASCT_Unbalance(double m1asct_Unbalance) {
		M1ASCT_Unbalance = m1asct_Unbalance;
	}
	public double getM1ASDC_Bypass_Forward_Overcurrent_Alarm() {
		return M1ASDC_Bypass_Forward_Overcurrent_Alarm;
	}
	public void setM1ASDC_Bypass_Forward_Overcurrent_Alarm(double m1asdc_Bypass_Forward_Overcurrent_Alarm) {
		M1ASDC_Bypass_Forward_Overcurrent_Alarm = m1asdc_Bypass_Forward_Overcurrent_Alarm;
	}
	public double getM1ASDC_Sensor_Abnormal() {
		return M1ASDC_Sensor_Abnormal;
	}
	public void setM1ASDC_Sensor_Abnormal(double m1asdc_Sensor_Abnormal) {
		M1ASDC_Sensor_Abnormal = m1asdc_Sensor_Abnormal;
	}
	public double getM1ASDC_Switch_Abnormal() {
		return M1ASDC_Switch_Abnormal;
	}
	public void setM1ASDC_Switch_Abnormal(double m1asdc_Switch_Abnormal) {
		M1ASDC_Switch_Abnormal = m1asdc_Switch_Abnormal;
	}
	public double getM1ASElectricity_Meter_Communication_Abnormal() {
		return M1ASElectricity_Meter_Communication_Abnormal;
	}
	public void setM1ASElectricity_Meter_Communication_Abnormal(double m1asElectricity_Meter_Communication_Abnormal) {
		M1ASElectricity_Meter_Communication_Abnormal = m1asElectricity_Meter_Communication_Abnormal;
	}
	public double getM1ASExternal_Power_Supply_Abnormal() {
		return M1ASExternal_Power_Supply_Abnormal;
	}
	public void setM1ASExternal_Power_Supply_Abnormal(double m1asExternal_Power_Supply_Abnormal) {
		M1ASExternal_Power_Supply_Abnormal = m1asExternal_Power_Supply_Abnormal;
	}
	public double getM1ASFan_2_Abnormal() {
		return M1ASFan_2_Abnormal;
	}
	public void setM1ASFan_2_Abnormal(double m1asFan_2_Abnormal) {
		M1ASFan_2_Abnormal = m1asFan_2_Abnormal;
	}
	public double getM1ASFan_Abnormal() {
		return M1ASFan_Abnormal;
	}
	public void setM1ASFan_Abnormal(double m1asFan_Abnormal) {
		M1ASFan_Abnormal = m1asFan_Abnormal;
	}
	public double getM1ASGFRT_Operation() {
		return M1ASGFRT_Operation;
	}
	public void setM1ASGFRT_Operation(double m1asgfrt_Operation) {
		M1ASGFRT_Operation = m1asgfrt_Operation;
	}
	public double getM1ASGround_Fuse_Abnormal() {
		return M1ASGround_Fuse_Abnormal;
	}
	public void setM1ASGround_Fuse_Abnormal(double m1asGround_Fuse_Abnormal) {
		M1ASGround_Fuse_Abnormal = m1asGround_Fuse_Abnormal;
	}
	public double getM1ASLow_Insulation_Resistance() {
		return M1ASLow_Insulation_Resistance;
	}
	public void setM1ASLow_Insulation_Resistance(double m1asLow_Insulation_Resistance) {
		M1ASLow_Insulation_Resistance = m1asLow_Insulation_Resistance;
	}
	public double getM1ASTemperature_Abnormal_Alarm() {
		return M1ASTemperature_Abnormal_Alarm;
	}
	public void setM1ASTemperature_Abnormal_Alarm(double m1asTemperature_Abnormal_Alarm) {
		M1ASTemperature_Abnormal_Alarm = m1asTemperature_Abnormal_Alarm;
	}
	public double getM1FS1AC_Leakage_Current_Protection() {
		return M1FS1AC_Leakage_Current_Protection;
	}
	public void setM1FS1AC_Leakage_Current_Protection(double m1fs1ac_Leakage_Current_Protection) {
		M1FS1AC_Leakage_Current_Protection = m1fs1ac_Leakage_Current_Protection;
	}
	public double getM1FS1AC_Overcurrent() {
		return M1FS1AC_Overcurrent;
	}
	public void setM1FS1AC_Overcurrent(double m1fs1ac_Overcurrent) {
		M1FS1AC_Overcurrent = m1fs1ac_Overcurrent;
	}
	public double getM1FS1AC_Overvoltage() {
		return M1FS1AC_Overvoltage;
	}
	public void setM1FS1AC_Overvoltage(double m1fs1ac_Overvoltage) {
		M1FS1AC_Overvoltage = m1fs1ac_Overvoltage;
	}
	public double getM1FS1AC_Undervoltage() {
		return M1FS1AC_Undervoltage;
	}
	public void setM1FS1AC_Undervoltage(double m1fs1ac_Undervoltage) {
		M1FS1AC_Undervoltage = m1fs1ac_Undervoltage;
	}
	public double getM1FS1Contactor_Fault() {
		return M1FS1Contactor_Fault;
	}
	public void setM1FS1Contactor_Fault(double m1fs1Contactor_Fault) {
		M1FS1Contactor_Fault = m1fs1Contactor_Fault;
	}
	public double getM1FS1DC_Fuse_Fault() {
		return M1FS1DC_Fuse_Fault;
	}
	public void setM1FS1DC_Fuse_Fault(double m1fs1dc_Fuse_Fault) {
		M1FS1DC_Fuse_Fault = m1fs1dc_Fuse_Fault;
	}
	public double getM1FS1DC_Leakage_Current_Protection() {
		return M1FS1DC_Leakage_Current_Protection;
	}
	public void setM1FS1DC_Leakage_Current_Protection(double m1fs1dc_Leakage_Current_Protection) {
		M1FS1DC_Leakage_Current_Protection = m1fs1dc_Leakage_Current_Protection;
	}
	public double getM1FS1DC_Overcurrent() {
		return M1FS1DC_Overcurrent;
	}
	public void setM1FS1DC_Overcurrent(double m1fs1dc_Overcurrent) {
		M1FS1DC_Overcurrent = m1fs1dc_Overcurrent;
	}
	public double getM1FS1DC_Overvoltage() {
		return M1FS1DC_Overvoltage;
	}
	public void setM1FS1DC_Overvoltage(double m1fs1dc_Overvoltage) {
		M1FS1DC_Overvoltage = m1fs1dc_Overvoltage;
	}
	public double getM1FS1DC_Undervoltage() {
		return M1FS1DC_Undervoltage;
	}
	public void setM1FS1DC_Undervoltage(double m1fs1dc_Undervoltage) {
		M1FS1DC_Undervoltage = m1fs1dc_Undervoltage;
	}
	public double getM1FS1Detection_Fuse_Fault() {
		return M1FS1Detection_Fuse_Fault;
	}
	public void setM1FS1Detection_Fuse_Fault(double m1fs1Detection_Fuse_Fault) {
		M1FS1Detection_Fuse_Fault = m1fs1Detection_Fuse_Fault;
	}
	public double getM1FS1Fan_Fault() {
		return M1FS1Fan_Fault;
	}
	public void setM1FS1Fan_Fault(double m1fs1Fan_Fault) {
		M1FS1Fan_Fault = m1fs1Fan_Fault;
	}
	public double getM1FS1Frequency_Abnormal() {
		return M1FS1Frequency_Abnormal;
	}
	public void setM1FS1Frequency_Abnormal(double m1fs1Frequency_Abnormal) {
		M1FS1Frequency_Abnormal = m1fs1Frequency_Abnormal;
	}
	public double getM1FS1GFDI_Protection() {
		return M1FS1GFDI_Protection;
	}
	public void setM1FS1GFDI_Protection(double m1fs1gfdi_Protection) {
		M1FS1GFDI_Protection = m1fs1gfdi_Protection;
	}
	public double getM1FS1Hardware_Fault() {
		return M1FS1Hardware_Fault;
	}
	public void setM1FS1Hardware_Fault(double m1fs1Hardware_Fault) {
		M1FS1Hardware_Fault = m1fs1Hardware_Fault;
	}
	public double getM1FS1Heat_Sik_Over_Temperature() {
		return M1FS1Heat_Sik_Over_Temperature;
	}
	public void setM1FS1Heat_Sik_Over_Temperature(double m1fs1Heat_Sik_Over_Temperature) {
		M1FS1Heat_Sik_Over_Temperature = m1fs1Heat_Sik_Over_Temperature;
	}
	public double getM1FS1Islanding_Protection() {
		return M1FS1Islanding_Protection;
	}
	public void setM1FS1Islanding_Protection(double m1fs1Islanding_Protection) {
		M1FS1Islanding_Protection = m1fs1Islanding_Protection;
	}
	public double getM1FS1Module_Overtemperature() {
		return M1FS1Module_Overtemperature;
	}
	public void setM1FS1Module_Overtemperature(double m1fs1Module_Overtemperature) {
		M1FS1Module_Overtemperature = m1fs1Module_Overtemperature;
	}
	public double getM1FS1Overfrequency() {
		return M1FS1Overfrequency;
	}
	public void setM1FS1Overfrequency(double m1fs1Overfrequency) {
		M1FS1Overfrequency = m1fs1Overfrequency;
	}
	public double getM1FS1Overload_Protection() {
		return M1FS1Overload_Protection;
	}
	public void setM1FS1Overload_Protection(double m1fs1Overload_Protection) {
		M1FS1Overload_Protection = m1fs1Overload_Protection;
	}
	public double getM1FS1PDP_Protection() {
		return M1FS1PDP_Protection;
	}
	public void setM1FS1PDP_Protection(double m1fs1pdp_Protection) {
		M1FS1PDP_Protection = m1fs1pdp_Protection;
	}
	public double getM1FS1Reactor_Overtemperature() {
		return M1FS1Reactor_Overtemperature;
	}
	public void setM1FS1Reactor_Overtemperature(double m1fs1Reactor_Overtemperature) {
		M1FS1Reactor_Overtemperature = m1fs1Reactor_Overtemperature;
	}
	public double getM1FS1Sensor_Failure() {
		return M1FS1Sensor_Failure;
	}
	public void setM1FS1Sensor_Failure(double m1fs1Sensor_Failure) {
		M1FS1Sensor_Failure = m1fs1Sensor_Failure;
	}
	public double getM1FS1Temperature_Abnormal() {
		return M1FS1Temperature_Abnormal;
	}
	public void setM1FS1Temperature_Abnormal(double m1fs1Temperature_Abnormal) {
		M1FS1Temperature_Abnormal = m1fs1Temperature_Abnormal;
	}
	public double getM1FS1Transformer_Overtemperature() {
		return M1FS1Transformer_Overtemperature;
	}
	public void setM1FS1Transformer_Overtemperature(double m1fs1Transformer_Overtemperature) {
		M1FS1Transformer_Overtemperature = m1fs1Transformer_Overtemperature;
	}
	public double getM1FS1Underfrequency() {
		return M1FS1Underfrequency;
	}
	public void setM1FS1Underfrequency(double m1fs1Underfrequency) {
		M1FS1Underfrequency = m1fs1Underfrequency;
	}
	public double getM1FS2AC_Current_Unbalance() {
		return M1FS2AC_Current_Unbalance;
	}
	public void setM1FS2AC_Current_Unbalance(double m1fs2ac_Current_Unbalance) {
		M1FS2AC_Current_Unbalance = m1fs2ac_Current_Unbalance;
	}
	public double getM1FS2AC_SPD_Fault() {
		return M1FS2AC_SPD_Fault;
	}
	public void setM1FS2AC_SPD_Fault(double m1fs2ac_SPD_Fault) {
		M1FS2AC_SPD_Fault = m1fs2ac_SPD_Fault;
	}
	public double getM1FS2DC_SPD_Fault() {
		return M1FS2DC_SPD_Fault;
	}
	public void setM1FS2DC_SPD_Fault(double m1fs2dc_SPD_Fault) {
		M1FS2DC_SPD_Fault = m1fs2dc_SPD_Fault;
	}
	public double getM1FS2PV_Polarity_Reversed() {
		return M1FS2PV_Polarity_Reversed;
	}
	public void setM1FS2PV_Polarity_Reversed(double m1fs2pv_Polarity_Reversed) {
		M1FS2PV_Polarity_Reversed = m1fs2pv_Polarity_Reversed;
	}
	public double getM1FS2AC_Fuse_Fault() {
		return M1FS2AC_Fuse_Fault;
	}
	public void setM1FS2AC_Fuse_Fault(double m1fs2ac_Fuse_Fault) {
		M1FS2AC_Fuse_Fault = m1fs2ac_Fuse_Fault;
	}
	public double getM1FS2AC_Switch_Disconnection() {
		return M1FS2AC_Switch_Disconnection;
	}
	public void setM1FS2AC_Switch_Disconnection(double m1fs2ac_Switch_Disconnection) {
		M1FS2AC_Switch_Disconnection = m1fs2ac_Switch_Disconnection;
	}
	public double getM1FS2AC_Switch_Fault() {
		return M1FS2AC_Switch_Fault;
	}
	public void setM1FS2AC_Switch_Fault(double m1fs2ac_Switch_Fault) {
		M1FS2AC_Switch_Fault = m1fs2ac_Switch_Fault;
	}
	public double getM1FS2Backup_Power_Supply_Abnormal() {
		return M1FS2Backup_Power_Supply_Abnormal;
	}
	public void setM1FS2Backup_Power_Supply_Abnormal(double m1fs2Backup_Power_Supply_Abnormal) {
		M1FS2Backup_Power_Supply_Abnormal = m1fs2Backup_Power_Supply_Abnormal;
	}
	public double getM1FS2Buffer_Contactor_Fault() {
		return M1FS2Buffer_Contactor_Fault;
	}
	public void setM1FS2Buffer_Contactor_Fault(double m1fs2Buffer_Contactor_Fault) {
		M1FS2Buffer_Contactor_Fault = m1fs2Buffer_Contactor_Fault;
	}
	public double getM1FS2Bypass_Reverse_Overcurrent() {
		return M1FS2Bypass_Reverse_Overcurrent;
	}
	public void setM1FS2Bypass_Reverse_Overcurrent(double m1fs2Bypass_Reverse_Overcurrent) {
		M1FS2Bypass_Reverse_Overcurrent = m1fs2Bypass_Reverse_Overcurrent;
	}
	public double getM1FS2Control_Cabinet_Temperature_Abnormal() {
		return M1FS2Control_Cabinet_Temperature_Abnormal;
	}
	public void setM1FS2Control_Cabinet_Temperature_Abnormal(double m1fs2Control_Cabinet_Temperature_Abnormal) {
		M1FS2Control_Cabinet_Temperature_Abnormal = m1fs2Control_Cabinet_Temperature_Abnormal;
	}
	public double getM1FS2Control_Power_Supply_Abnormal() {
		return M1FS2Control_Power_Supply_Abnormal;
	}
	public void setM1FS2Control_Power_Supply_Abnormal(double m1fs2Control_Power_Supply_Abnormal) {
		M1FS2Control_Power_Supply_Abnormal = m1fs2Control_Power_Supply_Abnormal;
	}
	public double getM1FS2Current_Unbalance_2() {
		return M1FS2Current_Unbalance_2;
	}
	public void setM1FS2Current_Unbalance_2(double m1fs2Current_Unbalance_2) {
		M1FS2Current_Unbalance_2 = m1fs2Current_Unbalance_2;
	}
	public double getM1FS2Current_Unbalance_3() {
		return M1FS2Current_Unbalance_3;
	}
	public void setM1FS2Current_Unbalance_3(double m1fs2Current_Unbalance_3) {
		M1FS2Current_Unbalance_3 = m1fs2Current_Unbalance_3;
	}
	public double getM1FS2DC_Cabinet_Over_Temperature() {
		return M1FS2DC_Cabinet_Over_Temperature;
	}
	public void setM1FS2DC_Cabinet_Over_Temperature(double m1fs2dc_Cabinet_Over_Temperature) {
		M1FS2DC_Cabinet_Over_Temperature = m1fs2dc_Cabinet_Over_Temperature;
	}
	public double getM1FS2DC_Fuse_Grounding_Fault() {
		return M1FS2DC_Fuse_Grounding_Fault;
	}
	public void setM1FS2DC_Fuse_Grounding_Fault(double m1fs2dc_Fuse_Grounding_Fault) {
		M1FS2DC_Fuse_Grounding_Fault = m1fs2dc_Fuse_Grounding_Fault;
	}
	public double getM1FS2DC_Injection_Fault() {
		return M1FS2DC_Injection_Fault;
	}
	public void setM1FS2DC_Injection_Fault(double m1fs2dc_Injection_Fault) {
		M1FS2DC_Injection_Fault = m1fs2dc_Injection_Fault;
	}
	public double getM1FS2DC_Switch_Abnormal() {
		return M1FS2DC_Switch_Abnormal;
	}
	public void setM1FS2DC_Switch_Abnormal(double m1fs2dc_Switch_Abnormal) {
		M1FS2DC_Switch_Abnormal = m1fs2dc_Switch_Abnormal;
	}
	public double getM1FS2DC_Switch_Fault() {
		return M1FS2DC_Switch_Fault;
	}
	public void setM1FS2DC_Switch_Fault(double m1fs2dc_Switch_Fault) {
		M1FS2DC_Switch_Fault = m1fs2dc_Switch_Fault;
	}
	public double getM1FS2DC_Voltage_Sampling_Fault() {
		return M1FS2DC_Voltage_Sampling_Fault;
	}
	public void setM1FS2DC_Voltage_Sampling_Fault(double m1fs2dc_Voltage_Sampling_Fault) {
		M1FS2DC_Voltage_Sampling_Fault = m1fs2dc_Voltage_Sampling_Fault;
	}
	public double getM1FS2Drive_Board_Fault() {
		return M1FS2Drive_Board_Fault;
	}
	public void setM1FS2Drive_Board_Fault(double m1fs2Drive_Board_Fault) {
		M1FS2Drive_Board_Fault = m1fs2Drive_Board_Fault;
	}
	public double getM1FS2Fan_2_Fault() {
		return M1FS2Fan_2_Fault;
	}
	public void setM1FS2Fan_2_Fault(double m1fs2Fan_2_Fault) {
		M1FS2Fan_2_Fault = m1fs2Fan_2_Fault;
	}
	public double getM1FS2Grid_DC_Injection_Fault() {
		return M1FS2Grid_DC_Injection_Fault;
	}
	public void setM1FS2Grid_DC_Injection_Fault(double m1fs2Grid_DC_Injection_Fault) {
		M1FS2Grid_DC_Injection_Fault = m1fs2Grid_DC_Injection_Fault;
	}
	public double getM1FS2Grid_Voltage_Unbalance() {
		return M1FS2Grid_Voltage_Unbalance;
	}
	public void setM1FS2Grid_Voltage_Unbalance(double m1fs2Grid_Voltage_Unbalance) {
		M1FS2Grid_Voltage_Unbalance = m1fs2Grid_Voltage_Unbalance;
	}
	public double getM1FS2Insulation_Impedance() {
		return M1FS2Insulation_Impedance;
	}
	public void setM1FS2Insulation_Impedance(double m1fs2Insulation_Impedance) {
		M1FS2Insulation_Impedance = m1fs2Insulation_Impedance;
	}
	public double getM1FS2Reverse_Charging_Power_Supply_Abnormal() {
		return M1FS2Reverse_Charging_Power_Supply_Abnormal;
	}
	public void setM1FS2Reverse_Charging_Power_Supply_Abnormal(double m1fs2Reverse_Charging_Power_Supply_Abnormal) {
		M1FS2Reverse_Charging_Power_Supply_Abnormal = m1fs2Reverse_Charging_Power_Supply_Abnormal;
	}
	public double getM1FS2Sampling_Fault() {
		return M1FS2Sampling_Fault;
	}
	public void setM1FS2Sampling_Fault(double m1fs2Sampling_Fault) {
		M1FS2Sampling_Fault = m1fs2Sampling_Fault;
	}
	public double getM1FS2Soft_Start_Fault() {
		return M1FS2Soft_Start_Fault;
	}
	public void setM1FS2Soft_Start_Fault(double m1fs2Soft_Start_Fault) {
		M1FS2Soft_Start_Fault = m1fs2Soft_Start_Fault;
	}
	public double getM1FS3Carrier_Sync_Fault() {
		return M1FS3Carrier_Sync_Fault;
	}
	public void setM1FS3Carrier_Sync_Fault(double m1fs3Carrier_Sync_Fault) {
		M1FS3Carrier_Sync_Fault = m1fs3Carrier_Sync_Fault;
	}
	public double getM1FS3Device_Code_Repeat_Fault() {
		return M1FS3Device_Code_Repeat_Fault;
	}
	public void setM1FS3Device_Code_Repeat_Fault(double m1fs3Device_Code_Repeat_Fault) {
		M1FS3Device_Code_Repeat_Fault = m1fs3Device_Code_Repeat_Fault;
	}
	public double getM1FS3Neutral_Point_Potential_Shift() {
		return M1FS3Neutral_Point_Potential_Shift;
	}
	public void setM1FS3Neutral_Point_Potential_Shift(double m1fs3Neutral_Point_Potential_Shift) {
		M1FS3Neutral_Point_Potential_Shift = m1fs3Neutral_Point_Potential_Shift;
	}
	public double getM1FS3Parallel_Operation_Communication_Fault() {
		return M1FS3Parallel_Operation_Communication_Fault;
	}
	public void setM1FS3Parallel_Operation_Communication_Fault(double m1fs3Parallel_Operation_Communication_Fault) {
		M1FS3Parallel_Operation_Communication_Fault = m1fs3Parallel_Operation_Communication_Fault;
	}
	public double getM1NS1AC_Circuit_Breaker_State() {
		return M1NS1AC_Circuit_Breaker_State;
	}
	public void setM1NS1AC_Circuit_Breaker_State(double m1ns1ac_Circuit_Breaker_State) {
		M1NS1AC_Circuit_Breaker_State = m1ns1ac_Circuit_Breaker_State;
	}
	public double getM1NS1AC_Main_Contactor_State() {
		return M1NS1AC_Main_Contactor_State;
	}
	public void setM1NS1AC_Main_Contactor_State(double m1ns1ac_Main_Contactor_State) {
		M1NS1AC_Main_Contactor_State = m1ns1ac_Main_Contactor_State;
	}
	public double getM1NS1ACB_Fault_State() {
		return M1NS1ACB_Fault_State;
	}
	public void setM1NS1ACB_Fault_State(double m1ns1acb_Fault_State) {
		M1NS1ACB_Fault_State = m1ns1acb_Fault_State;
	}
	public double getM1NS1DC_Switch_State_1() {
		return M1NS1DC_Switch_State_1;
	}
	public void setM1NS1DC_Switch_State_1(double m1ns1dc_Switch_State_1) {
		M1NS1DC_Switch_State_1 = m1ns1dc_Switch_State_1;
	}
	public double getM1NS1DC_Switch_State_2() {
		return M1NS1DC_Switch_State_2;
	}
	public void setM1NS1DC_Switch_State_2(double m1ns1dc_Switch_State_2) {
		M1NS1DC_Switch_State_2 = m1ns1dc_Switch_State_2;
	}
	public double getM1NS1DC_Switch_State_3() {
		return M1NS1DC_Switch_State_3;
	}
	public void setM1NS1DC_Switch_State_3(double m1ns1dc_Switch_State_3) {
		M1NS1DC_Switch_State_3 = m1ns1dc_Switch_State_3;
	}
	public double getM1NS1DC_Switch_State_4() {
		return M1NS1DC_Switch_State_4;
	}
	public void setM1NS1DC_Switch_State_4(double m1ns1dc_Switch_State_4) {
		M1NS1DC_Switch_State_4 = m1ns1dc_Switch_State_4;
	}
	public double getM1NS1External_Power_Supply_State() {
		return M1NS1External_Power_Supply_State;
	}
	public void setM1NS1External_Power_Supply_State(double m1ns1External_Power_Supply_State) {
		M1NS1External_Power_Supply_State = m1ns1External_Power_Supply_State;
	}
	public double getM1WSAlarm_Running() {
		return M1WSAlarm_Running;
	}
	public void setM1WSAlarm_Running(double m1wsAlarm_Running) {
		M1WSAlarm_Running = m1wsAlarm_Running;
	}
	public double getM1WSDerating_Running() {
		return M1WSDerating_Running;
	}
	public void setM1WSDerating_Running(double m1wsDerating_Running) {
		M1WSDerating_Running = m1wsDerating_Running;
	}
	public double getM1WSEmergency_Stop() {
		return M1WSEmergency_Stop;
	}
	public void setM1WSEmergency_Stop(double m1wsEmergency_Stop) {
		M1WSEmergency_Stop = m1wsEmergency_Stop;
	}
	public double getM1WSFault_Stop() {
		return M1WSFault_Stop;
	}
	public void setM1WSFault_Stop(double m1wsFault_Stop) {
		M1WSFault_Stop = m1wsFault_Stop;
	}
	public double getM1WSInitial_Standby() {
		return M1WSInitial_Standby;
	}
	public void setM1WSInitial_Standby(double m1wsInitial_Standby) {
		M1WSInitial_Standby = m1wsInitial_Standby;
	}
	public double getM1WSIO_DSP_Communication_Abnormal() {
		return M1WSIO_DSP_Communication_Abnormal;
	}
	public void setM1WSIO_DSP_Communication_Abnormal(double m1wsio_DSP_Communication_Abnormal) {
		M1WSIO_DSP_Communication_Abnormal = m1wsio_DSP_Communication_Abnormal;
	}
	public double getM1WSIO_MDC_Communication_Abnormal() {
		return M1WSIO_MDC_Communication_Abnormal;
	}
	public void setM1WSIO_MDC_Communication_Abnormal(double m1wsio_MDC_Communication_Abnormal) {
		M1WSIO_MDC_Communication_Abnormal = m1wsio_MDC_Communication_Abnormal;
	}
	public double getM1WSKey_Stop() {
		return M1WSKey_Stop;
	}
	public void setM1WSKey_Stop(double m1wsKey_Stop) {
		M1WSKey_Stop = m1wsKey_Stop;
	}
	public double getM1WSRunning() {
		return M1WSRunning;
	}
	public void setM1WSRunning(double m1wsRunning) {
		M1WSRunning = m1wsRunning;
	}
	public double getM1WSSleeping() {
		return M1WSSleeping;
	}
	public void setM1WSSleeping(double m1wsSleeping) {
		M1WSSleeping = m1wsSleeping;
	}
	public double getM1WSStandby() {
		return M1WSStandby;
	}
	public void setM1WSStandby(double m1wsStandby) {
		M1WSStandby = m1wsStandby;
	}
	public double getM1WSStarting() {
		return M1WSStarting;
	}
	public void setM1WSStarting(double m1wsStarting) {
		M1WSStarting = m1wsStarting;
	}
	public double getM1WSStopped() {
		return M1WSStopped;
	}
	public void setM1WSStopped(double m1wsStopped) {
		M1WSStopped = m1wsStopped;
	}
	public double getM1WSStopping() {
		return M1WSStopping;
	}
	public void setM1WSStopping(double m1wsStopping) {
		M1WSStopping = m1wsStopping;
	}
	public double getM1WSTotal_Signal_Bit_Of_Running_State() {
		return M1WSTotal_Signal_Bit_Of_Running_State;
	}
	public void setM1WSTotal_Signal_Bit_Of_Running_State(double m1wsTotal_Signal_Bit_Of_Running_State) {
		M1WSTotal_Signal_Bit_Of_Running_State = m1wsTotal_Signal_Bit_Of_Running_State;
	}
	public double getM1WSTotal_Stop_Bit() {
		return M1WSTotal_Stop_Bit;
	}
	public void setM1WSTotal_Stop_Bit(double m1wsTotal_Stop_Bit) {
		M1WSTotal_Stop_Bit = m1wsTotal_Stop_Bit;
	}
	public double getM2ASAC_Switch_Abnormal() {
		return M2ASAC_Switch_Abnormal;
	}
	public void setM2ASAC_Switch_Abnormal(double m2asac_Switch_Abnormal) {
		M2ASAC_Switch_Abnormal = m2asac_Switch_Abnormal;
	}
	public double getM2ASBypass_Board_Communication_Abnormal() {
		return M2ASBypass_Board_Communication_Abnormal;
	}
	public void setM2ASBypass_Board_Communication_Abnormal(double m2asBypass_Board_Communication_Abnormal) {
		M2ASBypass_Board_Communication_Abnormal = m2asBypass_Board_Communication_Abnormal;
	}
	public double getM2ASContactor_Contact_Abnormal() {
		return M2ASContactor_Contact_Abnormal;
	}
	public void setM2ASContactor_Contact_Abnormal(double m2asContactor_Contact_Abnormal) {
		M2ASContactor_Contact_Abnormal = m2asContactor_Contact_Abnormal;
	}
	public double getM2ASCT_Unbalance() {
		return M2ASCT_Unbalance;
	}
	public void setM2ASCT_Unbalance(double m2asct_Unbalance) {
		M2ASCT_Unbalance = m2asct_Unbalance;
	}
	public double getM2ASDC_Bypass_Forward_Overcurrent_Alarm() {
		return M2ASDC_Bypass_Forward_Overcurrent_Alarm;
	}
	public void setM2ASDC_Bypass_Forward_Overcurrent_Alarm(double m2asdc_Bypass_Forward_Overcurrent_Alarm) {
		M2ASDC_Bypass_Forward_Overcurrent_Alarm = m2asdc_Bypass_Forward_Overcurrent_Alarm;
	}
	public double getM2ASDC_Sensor_Abnormal() {
		return M2ASDC_Sensor_Abnormal;
	}
	public void setM2ASDC_Sensor_Abnormal(double m2asdc_Sensor_Abnormal) {
		M2ASDC_Sensor_Abnormal = m2asdc_Sensor_Abnormal;
	}
	public double getM2ASDC_Switch_Abnormal() {
		return M2ASDC_Switch_Abnormal;
	}
	public void setM2ASDC_Switch_Abnormal(double m2asdc_Switch_Abnormal) {
		M2ASDC_Switch_Abnormal = m2asdc_Switch_Abnormal;
	}
	public double getM2ASElectricity_Meter_Communication_Abnormal() {
		return M2ASElectricity_Meter_Communication_Abnormal;
	}
	public void setM2ASElectricity_Meter_Communication_Abnormal(double m2asElectricity_Meter_Communication_Abnormal) {
		M2ASElectricity_Meter_Communication_Abnormal = m2asElectricity_Meter_Communication_Abnormal;
	}
	public double getM2ASExternal_Power_Supply_Abnormal() {
		return M2ASExternal_Power_Supply_Abnormal;
	}
	public void setM2ASExternal_Power_Supply_Abnormal(double m2asExternal_Power_Supply_Abnormal) {
		M2ASExternal_Power_Supply_Abnormal = m2asExternal_Power_Supply_Abnormal;
	}
	public double getM2ASFan_2_Abnormal() {
		return M2ASFan_2_Abnormal;
	}
	public void setM2ASFan_2_Abnormal(double m2asFan_2_Abnormal) {
		M2ASFan_2_Abnormal = m2asFan_2_Abnormal;
	}
	public double getM2ASFan_Abnormal() {
		return M2ASFan_Abnormal;
	}
	public void setM2ASFan_Abnormal(double m2asFan_Abnormal) {
		M2ASFan_Abnormal = m2asFan_Abnormal;
	}
	public double getM2ASGFRT_Operation() {
		return M2ASGFRT_Operation;
	}
	public void setM2ASGFRT_Operation(double m2asgfrt_Operation) {
		M2ASGFRT_Operation = m2asgfrt_Operation;
	}
	public double getM2ASGround_Fuse_Abnormal() {
		return M2ASGround_Fuse_Abnormal;
	}
	public void setM2ASGround_Fuse_Abnormal(double m2asGround_Fuse_Abnormal) {
		M2ASGround_Fuse_Abnormal = m2asGround_Fuse_Abnormal;
	}
	public double getM2ASLow_Insulation_Resistance() {
		return M2ASLow_Insulation_Resistance;
	}
	public void setM2ASLow_Insulation_Resistance(double m2asLow_Insulation_Resistance) {
		M2ASLow_Insulation_Resistance = m2asLow_Insulation_Resistance;
	}
	public double getM2ASTemperature_Abnormal_Alarm() {
		return M2ASTemperature_Abnormal_Alarm;
	}
	public void setM2ASTemperature_Abnormal_Alarm(double m2asTemperature_Abnormal_Alarm) {
		M2ASTemperature_Abnormal_Alarm = m2asTemperature_Abnormal_Alarm;
	}
	public double getM2FS1AC_Leakage_Current_Protection() {
		return M2FS1AC_Leakage_Current_Protection;
	}
	public void setM2FS1AC_Leakage_Current_Protection(double m2fs1ac_Leakage_Current_Protection) {
		M2FS1AC_Leakage_Current_Protection = m2fs1ac_Leakage_Current_Protection;
	}
	public double getM2FS1AC_Overcurrent() {
		return M2FS1AC_Overcurrent;
	}
	public void setM2FS1AC_Overcurrent(double m2fs1ac_Overcurrent) {
		M2FS1AC_Overcurrent = m2fs1ac_Overcurrent;
	}
	public double getM2FS1AC_Overvoltage() {
		return M2FS1AC_Overvoltage;
	}
	public void setM2FS1AC_Overvoltage(double m2fs1ac_Overvoltage) {
		M2FS1AC_Overvoltage = m2fs1ac_Overvoltage;
	}
	public double getM2FS1AC_Undervoltage() {
		return M2FS1AC_Undervoltage;
	}
	public void setM2FS1AC_Undervoltage(double m2fs1ac_Undervoltage) {
		M2FS1AC_Undervoltage = m2fs1ac_Undervoltage;
	}
	public double getM2FS1Contactor_Fault() {
		return M2FS1Contactor_Fault;
	}
	public void setM2FS1Contactor_Fault(double m2fs1Contactor_Fault) {
		M2FS1Contactor_Fault = m2fs1Contactor_Fault;
	}
	public double getM2FS1DC_Fuse_Fault() {
		return M2FS1DC_Fuse_Fault;
	}
	public void setM2FS1DC_Fuse_Fault(double m2fs1dc_Fuse_Fault) {
		M2FS1DC_Fuse_Fault = m2fs1dc_Fuse_Fault;
	}
	public double getM2FS1DC_Leakage_Current_Protection() {
		return M2FS1DC_Leakage_Current_Protection;
	}
	public void setM2FS1DC_Leakage_Current_Protection(double m2fs1dc_Leakage_Current_Protection) {
		M2FS1DC_Leakage_Current_Protection = m2fs1dc_Leakage_Current_Protection;
	}
	public double getM2FS1DC_Overcurrent() {
		return M2FS1DC_Overcurrent;
	}
	public void setM2FS1DC_Overcurrent(double m2fs1dc_Overcurrent) {
		M2FS1DC_Overcurrent = m2fs1dc_Overcurrent;
	}
	public double getM2FS1DC_Overvoltage() {
		return M2FS1DC_Overvoltage;
	}
	public void setM2FS1DC_Overvoltage(double m2fs1dc_Overvoltage) {
		M2FS1DC_Overvoltage = m2fs1dc_Overvoltage;
	}
	public double getM2FS1DC_Undervoltage() {
		return M2FS1DC_Undervoltage;
	}
	public void setM2FS1DC_Undervoltage(double m2fs1dc_Undervoltage) {
		M2FS1DC_Undervoltage = m2fs1dc_Undervoltage;
	}
	public double getM2FS1Detection_Fuse_Fault() {
		return M2FS1Detection_Fuse_Fault;
	}
	public void setM2FS1Detection_Fuse_Fault(double m2fs1Detection_Fuse_Fault) {
		M2FS1Detection_Fuse_Fault = m2fs1Detection_Fuse_Fault;
	}
	public double getM2FS1Fan_Fault() {
		return M2FS1Fan_Fault;
	}
	public void setM2FS1Fan_Fault(double m2fs1Fan_Fault) {
		M2FS1Fan_Fault = m2fs1Fan_Fault;
	}
	public double getM2FS1Frequency_Abnormal() {
		return M2FS1Frequency_Abnormal;
	}
	public void setM2FS1Frequency_Abnormal(double m2fs1Frequency_Abnormal) {
		M2FS1Frequency_Abnormal = m2fs1Frequency_Abnormal;
	}
	public double getM2FS1GFDI_Protection() {
		return M2FS1GFDI_Protection;
	}
	public void setM2FS1GFDI_Protection(double m2fs1gfdi_Protection) {
		M2FS1GFDI_Protection = m2fs1gfdi_Protection;
	}
	public double getM2FS1Hardware_Fault() {
		return M2FS1Hardware_Fault;
	}
	public void setM2FS1Hardware_Fault(double m2fs1Hardware_Fault) {
		M2FS1Hardware_Fault = m2fs1Hardware_Fault;
	}
	public double getM2FS1Heat_Sik_Over_Temperature() {
		return M2FS1Heat_Sik_Over_Temperature;
	}
	public void setM2FS1Heat_Sik_Over_Temperature(double m2fs1Heat_Sik_Over_Temperature) {
		M2FS1Heat_Sik_Over_Temperature = m2fs1Heat_Sik_Over_Temperature;
	}
	public double getM2FS1Islanding_Protection() {
		return M2FS1Islanding_Protection;
	}
	public void setM2FS1Islanding_Protection(double m2fs1Islanding_Protection) {
		M2FS1Islanding_Protection = m2fs1Islanding_Protection;
	}
	public double getM2FS1Module_Overtemperature() {
		return M2FS1Module_Overtemperature;
	}
	public void setM2FS1Module_Overtemperature(double m2fs1Module_Overtemperature) {
		M2FS1Module_Overtemperature = m2fs1Module_Overtemperature;
	}
	public double getM2FS1Overfrequency() {
		return M2FS1Overfrequency;
	}
	public void setM2FS1Overfrequency(double m2fs1Overfrequency) {
		M2FS1Overfrequency = m2fs1Overfrequency;
	}
	public double getM2FS1Overload_Protection() {
		return M2FS1Overload_Protection;
	}
	public void setM2FS1Overload_Protection(double m2fs1Overload_Protection) {
		M2FS1Overload_Protection = m2fs1Overload_Protection;
	}
	public double getM2FS1PDP_Protection() {
		return M2FS1PDP_Protection;
	}
	public void setM2FS1PDP_Protection(double m2fs1pdp_Protection) {
		M2FS1PDP_Protection = m2fs1pdp_Protection;
	}
	public double getM2FS1Reactor_Overtemperature() {
		return M2FS1Reactor_Overtemperature;
	}
	public void setM2FS1Reactor_Overtemperature(double m2fs1Reactor_Overtemperature) {
		M2FS1Reactor_Overtemperature = m2fs1Reactor_Overtemperature;
	}
	public double getM2FS1Sensor_Failure() {
		return M2FS1Sensor_Failure;
	}
	public void setM2FS1Sensor_Failure(double m2fs1Sensor_Failure) {
		M2FS1Sensor_Failure = m2fs1Sensor_Failure;
	}
	public double getM2FS1Temperature_Abnormal() {
		return M2FS1Temperature_Abnormal;
	}
	public void setM2FS1Temperature_Abnormal(double m2fs1Temperature_Abnormal) {
		M2FS1Temperature_Abnormal = m2fs1Temperature_Abnormal;
	}
	public double getM2FS1Transformer_Overtemperature() {
		return M2FS1Transformer_Overtemperature;
	}
	public void setM2FS1Transformer_Overtemperature(double m2fs1Transformer_Overtemperature) {
		M2FS1Transformer_Overtemperature = m2fs1Transformer_Overtemperature;
	}
	public double getM2FS1Underfrequency() {
		return M2FS1Underfrequency;
	}
	public void setM2FS1Underfrequency(double m2fs1Underfrequency) {
		M2FS1Underfrequency = m2fs1Underfrequency;
	}
	public double getM2FS2AC_Current_Unbalance() {
		return M2FS2AC_Current_Unbalance;
	}
	public void setM2FS2AC_Current_Unbalance(double m2fs2ac_Current_Unbalance) {
		M2FS2AC_Current_Unbalance = m2fs2ac_Current_Unbalance;
	}
	public double getM2FS2AC_SPD_Fault() {
		return M2FS2AC_SPD_Fault;
	}
	public void setM2FS2AC_SPD_Fault(double m2fs2ac_SPD_Fault) {
		M2FS2AC_SPD_Fault = m2fs2ac_SPD_Fault;
	}
	public double getM2FS2DC_SPD_Fault() {
		return M2FS2DC_SPD_Fault;
	}
	public void setM2FS2DC_SPD_Fault(double m2fs2dc_SPD_Fault) {
		M2FS2DC_SPD_Fault = m2fs2dc_SPD_Fault;
	}
	public double getM2FS2PV_Polarity_Reversed() {
		return M2FS2PV_Polarity_Reversed;
	}
	public void setM2FS2PV_Polarity_Reversed(double m2fs2pv_Polarity_Reversed) {
		M2FS2PV_Polarity_Reversed = m2fs2pv_Polarity_Reversed;
	}
	public double getM2FS2AC_Fuse_Fault() {
		return M2FS2AC_Fuse_Fault;
	}
	public void setM2FS2AC_Fuse_Fault(double m2fs2ac_Fuse_Fault) {
		M2FS2AC_Fuse_Fault = m2fs2ac_Fuse_Fault;
	}
	public double getM2FS2AC_Switch_Disconnection() {
		return M2FS2AC_Switch_Disconnection;
	}
	public void setM2FS2AC_Switch_Disconnection(double m2fs2ac_Switch_Disconnection) {
		M2FS2AC_Switch_Disconnection = m2fs2ac_Switch_Disconnection;
	}
	public double getM2FS2AC_Switch_Fault() {
		return M2FS2AC_Switch_Fault;
	}
	public void setM2FS2AC_Switch_Fault(double m2fs2ac_Switch_Fault) {
		M2FS2AC_Switch_Fault = m2fs2ac_Switch_Fault;
	}
	public double getM2FS2Backup_Power_Supply_Abnormal() {
		return M2FS2Backup_Power_Supply_Abnormal;
	}
	public void setM2FS2Backup_Power_Supply_Abnormal(double m2fs2Backup_Power_Supply_Abnormal) {
		M2FS2Backup_Power_Supply_Abnormal = m2fs2Backup_Power_Supply_Abnormal;
	}
	public double getM2FS2Buffer_Contactor_Fault() {
		return M2FS2Buffer_Contactor_Fault;
	}
	public void setM2FS2Buffer_Contactor_Fault(double m2fs2Buffer_Contactor_Fault) {
		M2FS2Buffer_Contactor_Fault = m2fs2Buffer_Contactor_Fault;
	}
	public double getM2FS2Bypass_Reverse_Overcurrent() {
		return M2FS2Bypass_Reverse_Overcurrent;
	}
	public void setM2FS2Bypass_Reverse_Overcurrent(double m2fs2Bypass_Reverse_Overcurrent) {
		M2FS2Bypass_Reverse_Overcurrent = m2fs2Bypass_Reverse_Overcurrent;
	}
	public double getM2FS2Control_Cabinet_Temperature_Abnormal() {
		return M2FS2Control_Cabinet_Temperature_Abnormal;
	}
	public void setM2FS2Control_Cabinet_Temperature_Abnormal(double m2fs2Control_Cabinet_Temperature_Abnormal) {
		M2FS2Control_Cabinet_Temperature_Abnormal = m2fs2Control_Cabinet_Temperature_Abnormal;
	}
	public double getM2FS2Control_Power_Supply_Abnormal() {
		return M2FS2Control_Power_Supply_Abnormal;
	}
	public void setM2FS2Control_Power_Supply_Abnormal(double m2fs2Control_Power_Supply_Abnormal) {
		M2FS2Control_Power_Supply_Abnormal = m2fs2Control_Power_Supply_Abnormal;
	}
	public double getM2FS2Current_Unbalance_2() {
		return M2FS2Current_Unbalance_2;
	}
	public void setM2FS2Current_Unbalance_2(double m2fs2Current_Unbalance_2) {
		M2FS2Current_Unbalance_2 = m2fs2Current_Unbalance_2;
	}
	public double getM2FS2Current_Unbalance_3() {
		return M2FS2Current_Unbalance_3;
	}
	public void setM2FS2Current_Unbalance_3(double m2fs2Current_Unbalance_3) {
		M2FS2Current_Unbalance_3 = m2fs2Current_Unbalance_3;
	}
	public double getM2FS2DC_Cabinet_Over_Temperature() {
		return M2FS2DC_Cabinet_Over_Temperature;
	}
	public void setM2FS2DC_Cabinet_Over_Temperature(double m2fs2dc_Cabinet_Over_Temperature) {
		M2FS2DC_Cabinet_Over_Temperature = m2fs2dc_Cabinet_Over_Temperature;
	}
	public double getM2FS2DC_Fuse_Grounding_Fault() {
		return M2FS2DC_Fuse_Grounding_Fault;
	}
	public void setM2FS2DC_Fuse_Grounding_Fault(double m2fs2dc_Fuse_Grounding_Fault) {
		M2FS2DC_Fuse_Grounding_Fault = m2fs2dc_Fuse_Grounding_Fault;
	}
	public double getM2FS2DC_Injection_Fault() {
		return M2FS2DC_Injection_Fault;
	}
	public void setM2FS2DC_Injection_Fault(double m2fs2dc_Injection_Fault) {
		M2FS2DC_Injection_Fault = m2fs2dc_Injection_Fault;
	}
	public double getM2FS2DC_Switch_Abnormal() {
		return M2FS2DC_Switch_Abnormal;
	}
	public void setM2FS2DC_Switch_Abnormal(double m2fs2dc_Switch_Abnormal) {
		M2FS2DC_Switch_Abnormal = m2fs2dc_Switch_Abnormal;
	}
	public double getM2FS2DC_Switch_Fault() {
		return M2FS2DC_Switch_Fault;
	}
	public void setM2FS2DC_Switch_Fault(double m2fs2dc_Switch_Fault) {
		M2FS2DC_Switch_Fault = m2fs2dc_Switch_Fault;
	}
	public double getM2FS2DC_Voltage_Sampling_Fault() {
		return M2FS2DC_Voltage_Sampling_Fault;
	}
	public void setM2FS2DC_Voltage_Sampling_Fault(double m2fs2dc_Voltage_Sampling_Fault) {
		M2FS2DC_Voltage_Sampling_Fault = m2fs2dc_Voltage_Sampling_Fault;
	}
	public double getM2FS2Drive_Board_Fault() {
		return M2FS2Drive_Board_Fault;
	}
	public void setM2FS2Drive_Board_Fault(double m2fs2Drive_Board_Fault) {
		M2FS2Drive_Board_Fault = m2fs2Drive_Board_Fault;
	}
	public double getM2FS2Fan_2_Fault() {
		return M2FS2Fan_2_Fault;
	}
	public void setM2FS2Fan_2_Fault(double m2fs2Fan_2_Fault) {
		M2FS2Fan_2_Fault = m2fs2Fan_2_Fault;
	}
	public double getM2FS2Grid_DC_Injection_Fault() {
		return M2FS2Grid_DC_Injection_Fault;
	}
	public void setM2FS2Grid_DC_Injection_Fault(double m2fs2Grid_DC_Injection_Fault) {
		M2FS2Grid_DC_Injection_Fault = m2fs2Grid_DC_Injection_Fault;
	}
	public double getM2FS2Grid_Voltage_Unbalance() {
		return M2FS2Grid_Voltage_Unbalance;
	}
	public void setM2FS2Grid_Voltage_Unbalance(double m2fs2Grid_Voltage_Unbalance) {
		M2FS2Grid_Voltage_Unbalance = m2fs2Grid_Voltage_Unbalance;
	}
	public double getM2FS2Insulation_Impedance() {
		return M2FS2Insulation_Impedance;
	}
	public void setM2FS2Insulation_Impedance(double m2fs2Insulation_Impedance) {
		M2FS2Insulation_Impedance = m2fs2Insulation_Impedance;
	}
	public double getM2FS2Reverse_Charging_Power_Supply_Abnormal() {
		return M2FS2Reverse_Charging_Power_Supply_Abnormal;
	}
	public void setM2FS2Reverse_Charging_Power_Supply_Abnormal(double m2fs2Reverse_Charging_Power_Supply_Abnormal) {
		M2FS2Reverse_Charging_Power_Supply_Abnormal = m2fs2Reverse_Charging_Power_Supply_Abnormal;
	}
	public double getM2FS2Sampling_Fault() {
		return M2FS2Sampling_Fault;
	}
	public void setM2FS2Sampling_Fault(double m2fs2Sampling_Fault) {
		M2FS2Sampling_Fault = m2fs2Sampling_Fault;
	}
	public double getM2FS2Soft_Start_Fault() {
		return M2FS2Soft_Start_Fault;
	}
	public void setM2FS2Soft_Start_Fault(double m2fs2Soft_Start_Fault) {
		M2FS2Soft_Start_Fault = m2fs2Soft_Start_Fault;
	}
	public double getM2FS3Carrier_Sync_Fault() {
		return M2FS3Carrier_Sync_Fault;
	}
	public void setM2FS3Carrier_Sync_Fault(double m2fs3Carrier_Sync_Fault) {
		M2FS3Carrier_Sync_Fault = m2fs3Carrier_Sync_Fault;
	}
	public double getM2FS3Device_Code_Repeat_Fault() {
		return M2FS3Device_Code_Repeat_Fault;
	}
	public void setM2FS3Device_Code_Repeat_Fault(double m2fs3Device_Code_Repeat_Fault) {
		M2FS3Device_Code_Repeat_Fault = m2fs3Device_Code_Repeat_Fault;
	}
	public double getM2FS3Neutral_Point_Potential_Shift() {
		return M2FS3Neutral_Point_Potential_Shift;
	}
	public void setM2FS3Neutral_Point_Potential_Shift(double m2fs3Neutral_Point_Potential_Shift) {
		M2FS3Neutral_Point_Potential_Shift = m2fs3Neutral_Point_Potential_Shift;
	}
	public double getM2FS3Parallel_Operation_Communication_Fault() {
		return M2FS3Parallel_Operation_Communication_Fault;
	}
	public void setM2FS3Parallel_Operation_Communication_Fault(double m2fs3Parallel_Operation_Communication_Fault) {
		M2FS3Parallel_Operation_Communication_Fault = m2fs3Parallel_Operation_Communication_Fault;
	}
	public double getM2NS1AC_Circuit_Breaker_State() {
		return M2NS1AC_Circuit_Breaker_State;
	}
	public void setM2NS1AC_Circuit_Breaker_State(double m2ns1ac_Circuit_Breaker_State) {
		M2NS1AC_Circuit_Breaker_State = m2ns1ac_Circuit_Breaker_State;
	}
	public double getM2NS1AC_Main_Contactor_State() {
		return M2NS1AC_Main_Contactor_State;
	}
	public void setM2NS1AC_Main_Contactor_State(double m2ns1ac_Main_Contactor_State) {
		M2NS1AC_Main_Contactor_State = m2ns1ac_Main_Contactor_State;
	}
	public double getM2NS1ACB_Fault_State() {
		return M2NS1ACB_Fault_State;
	}
	public void setM2NS1ACB_Fault_State(double m2ns1acb_Fault_State) {
		M2NS1ACB_Fault_State = m2ns1acb_Fault_State;
	}
	public double getM2NS1DC_Switch_State_1() {
		return M2NS1DC_Switch_State_1;
	}
	public void setM2NS1DC_Switch_State_1(double m2ns1dc_Switch_State_1) {
		M2NS1DC_Switch_State_1 = m2ns1dc_Switch_State_1;
	}
	public double getM2NS1DC_Switch_State_2() {
		return M2NS1DC_Switch_State_2;
	}
	public void setM2NS1DC_Switch_State_2(double m2ns1dc_Switch_State_2) {
		M2NS1DC_Switch_State_2 = m2ns1dc_Switch_State_2;
	}
	public double getM2NS1DC_Switch_State_3() {
		return M2NS1DC_Switch_State_3;
	}
	public void setM2NS1DC_Switch_State_3(double m2ns1dc_Switch_State_3) {
		M2NS1DC_Switch_State_3 = m2ns1dc_Switch_State_3;
	}
	public double getM2NS1DC_Switch_State_4() {
		return M2NS1DC_Switch_State_4;
	}
	public void setM2NS1DC_Switch_State_4(double m2ns1dc_Switch_State_4) {
		M2NS1DC_Switch_State_4 = m2ns1dc_Switch_State_4;
	}
	public double getM2NS1External_Power_Supply_State() {
		return M2NS1External_Power_Supply_State;
	}
	public void setM2NS1External_Power_Supply_State(double m2ns1External_Power_Supply_State) {
		M2NS1External_Power_Supply_State = m2ns1External_Power_Supply_State;
	}
	public double getM2WSAlarm_Running() {
		return M2WSAlarm_Running;
	}
	public void setM2WSAlarm_Running(double m2wsAlarm_Running) {
		M2WSAlarm_Running = m2wsAlarm_Running;
	}
	public double getM2WSAnti_PID_Running() {
		return M2WSAnti_PID_Running;
	}
	public void setM2WSAnti_PID_Running(double m2wsAnti_PID_Running) {
		M2WSAnti_PID_Running = m2wsAnti_PID_Running;
	}
	public double getM2WSDerating_Running() {
		return M2WSDerating_Running;
	}
	public void setM2WSDerating_Running(double m2wsDerating_Running) {
		M2WSDerating_Running = m2wsDerating_Running;
	}
	public double getM2WSEmergency_Stop() {
		return M2WSEmergency_Stop;
	}
	public void setM2WSEmergency_Stop(double m2wsEmergency_Stop) {
		M2WSEmergency_Stop = m2wsEmergency_Stop;
	}
	public double getM2WSFault_Stop() {
		return M2WSFault_Stop;
	}
	public void setM2WSFault_Stop(double m2wsFault_Stop) {
		M2WSFault_Stop = m2wsFault_Stop;
	}
	public double getM2WSInitial_Standby() {
		return M2WSInitial_Standby;
	}
	public void setM2WSInitial_Standby(double m2wsInitial_Standby) {
		M2WSInitial_Standby = m2wsInitial_Standby;
	}
	public double getM2WSIO_DSP_Communication_Abnormal() {
		return M2WSIO_DSP_Communication_Abnormal;
	}
	public void setM2WSIO_DSP_Communication_Abnormal(double m2wsio_DSP_Communication_Abnormal) {
		M2WSIO_DSP_Communication_Abnormal = m2wsio_DSP_Communication_Abnormal;
	}
	public double getM2WSIO_MDC_Communication_Abnormal() {
		return M2WSIO_MDC_Communication_Abnormal;
	}
	public void setM2WSIO_MDC_Communication_Abnormal(double m2wsio_MDC_Communication_Abnormal) {
		M2WSIO_MDC_Communication_Abnormal = m2wsio_MDC_Communication_Abnormal;
	}
	public double getM2WSKey_Stop() {
		return M2WSKey_Stop;
	}
	public void setM2WSKey_Stop(double m2wsKey_Stop) {
		M2WSKey_Stop = m2wsKey_Stop;
	}
	public double getM2WSRunning() {
		return M2WSRunning;
	}
	public void setM2WSRunning(double m2wsRunning) {
		M2WSRunning = m2wsRunning;
	}
	public double getM2WSSleeping() {
		return M2WSSleeping;
	}
	public void setM2WSSleeping(double m2wsSleeping) {
		M2WSSleeping = m2wsSleeping;
	}
	public double getM2WSSleeping1() {
		return M2WSSleeping1;
	}
	public void setM2WSSleeping1(double m2wsSleeping1) {
		M2WSSleeping1 = m2wsSleeping1;
	}
	public double getM2WSStandby() {
		return M2WSStandby;
	}
	public void setM2WSStandby(double m2wsStandby) {
		M2WSStandby = m2wsStandby;
	}
	public double getM2WSStarting() {
		return M2WSStarting;
	}
	public void setM2WSStarting(double m2wsStarting) {
		M2WSStarting = m2wsStarting;
	}
	public double getM2WSStopped() {
		return M2WSStopped;
	}
	public void setM2WSStopped(double m2wsStopped) {
		M2WSStopped = m2wsStopped;
	}
	public double getM2WSStopping() {
		return M2WSStopping;
	}
	public void setM2WSStopping(double m2wsStopping) {
		M2WSStopping = m2wsStopping;
	}
	public double getM2WSTotal_Signal_Bit_Of_Running_State() {
		return M2WSTotal_Signal_Bit_Of_Running_State;
	}
	public void setM2WSTotal_Signal_Bit_Of_Running_State(double m2wsTotal_Signal_Bit_Of_Running_State) {
		M2WSTotal_Signal_Bit_Of_Running_State = m2wsTotal_Signal_Bit_Of_Running_State;
	}
	public double getM2WSTotal_Stop_Bit() {
		return M2WSTotal_Stop_Bit;
	}
	public void setM2WSTotal_Stop_Bit(double m2wsTotal_Stop_Bit) {
		M2WSTotal_Stop_Bit = m2wsTotal_Stop_Bit;
	}
	public double getMV_NODE_STATE_Coil_Temp_Alarm() {
		return MV_NODE_STATE_Coil_Temp_Alarm;
	}
	public void setMV_NODE_STATE_Coil_Temp_Alarm(double mV_NODE_STATE_Coil_Temp_Alarm) {
		MV_NODE_STATE_Coil_Temp_Alarm = mV_NODE_STATE_Coil_Temp_Alarm;
	}
	public double getMV_NODE_STATE_Coil_Temp_Trip() {
		return MV_NODE_STATE_Coil_Temp_Trip;
	}
	public void setMV_NODE_STATE_Coil_Temp_Trip(double mV_NODE_STATE_Coil_Temp_Trip) {
		MV_NODE_STATE_Coil_Temp_Trip = mV_NODE_STATE_Coil_Temp_Trip;
	}
	public double getMV_NODE_STATE_Gas_Relay_Alarm() {
		return MV_NODE_STATE_Gas_Relay_Alarm;
	}
	public void setMV_NODE_STATE_Gas_Relay_Alarm(double mV_NODE_STATE_Gas_Relay_Alarm) {
		MV_NODE_STATE_Gas_Relay_Alarm = mV_NODE_STATE_Gas_Relay_Alarm;
	}
	public double getMV_NODE_STATE_Gas_Relay_Trip() {
		return MV_NODE_STATE_Gas_Relay_Trip;
	}
	public void setMV_NODE_STATE_Gas_Relay_Trip(double mV_NODE_STATE_Gas_Relay_Trip) {
		MV_NODE_STATE_Gas_Relay_Trip = mV_NODE_STATE_Gas_Relay_Trip;
	}
	public double getMV_NODE_STATE_Low_Oil_Level_Alarm() {
		return MV_NODE_STATE_Low_Oil_Level_Alarm;
	}
	public void setMV_NODE_STATE_Low_Oil_Level_Alarm(double mV_NODE_STATE_Low_Oil_Level_Alarm) {
		MV_NODE_STATE_Low_Oil_Level_Alarm = mV_NODE_STATE_Low_Oil_Level_Alarm;
	}
	public double getMV_NODE_STATE_Low_Oil_Level_Trip() {
		return MV_NODE_STATE_Low_Oil_Level_Trip;
	}
	public void setMV_NODE_STATE_Low_Oil_Level_Trip(double mV_NODE_STATE_Low_Oil_Level_Trip) {
		MV_NODE_STATE_Low_Oil_Level_Trip = mV_NODE_STATE_Low_Oil_Level_Trip;
	}
	public double getMV_NODE_STATE_MV_DS() {
		return MV_NODE_STATE_MV_DS;
	}
	public void setMV_NODE_STATE_MV_DS(double mV_NODE_STATE_MV_DS) {
		MV_NODE_STATE_MV_DS = mV_NODE_STATE_MV_DS;
	}
	public double getMV_NODE_STATE_MV_Load_Switch_1() {
		return MV_NODE_STATE_MV_Load_Switch_1;
	}
	public void setMV_NODE_STATE_MV_Load_Switch_1(double mV_NODE_STATE_MV_Load_Switch_1) {
		MV_NODE_STATE_MV_Load_Switch_1 = mV_NODE_STATE_MV_Load_Switch_1;
	}
	public double getMV_NODE_STATE_MV_Load_Switch_2() {
		return MV_NODE_STATE_MV_Load_Switch_2;
	}
	public void setMV_NODE_STATE_MV_Load_Switch_2(double mV_NODE_STATE_MV_Load_Switch_2) {
		MV_NODE_STATE_MV_Load_Switch_2 = mV_NODE_STATE_MV_Load_Switch_2;
	}
	public double getMV_NODE_STATE_MV_VCB() {
		return MV_NODE_STATE_MV_VCB;
	}
	public void setMV_NODE_STATE_MV_VCB(double mV_NODE_STATE_MV_VCB) {
		MV_NODE_STATE_MV_VCB = mV_NODE_STATE_MV_VCB;
	}
	public double getMV_NODE_STATE_Oil_Temp_Alarm() {
		return MV_NODE_STATE_Oil_Temp_Alarm;
	}
	public void setMV_NODE_STATE_Oil_Temp_Alarm(double mV_NODE_STATE_Oil_Temp_Alarm) {
		MV_NODE_STATE_Oil_Temp_Alarm = mV_NODE_STATE_Oil_Temp_Alarm;
	}
	public double getMV_NODE_STATE_Oil_Temp_Trip() {
		return MV_NODE_STATE_Oil_Temp_Trip;
	}
	public void setMV_NODE_STATE_Oil_Temp_Trip(double mV_NODE_STATE_Oil_Temp_Trip) {
		MV_NODE_STATE_Oil_Temp_Trip = mV_NODE_STATE_Oil_Temp_Trip;
	}
	public double getMV_NODE_STATE_Overcurrent_Protection() {
		return MV_NODE_STATE_Overcurrent_Protection;
	}
	public void setMV_NODE_STATE_Overcurrent_Protection(double mV_NODE_STATE_Overcurrent_Protection) {
		MV_NODE_STATE_Overcurrent_Protection = mV_NODE_STATE_Overcurrent_Protection;
	}
	public double getMV_NODE_STATE_Pressure_Relief_Alarm() {
		return MV_NODE_STATE_Pressure_Relief_Alarm;
	}
	public void setMV_NODE_STATE_Pressure_Relief_Alarm(double mV_NODE_STATE_Pressure_Relief_Alarm) {
		MV_NODE_STATE_Pressure_Relief_Alarm = mV_NODE_STATE_Pressure_Relief_Alarm;
	}
	public double getMV_NODE_STATE_Pressure_Relief_Trip() {
		return MV_NODE_STATE_Pressure_Relief_Trip;
	}
	public void setMV_NODE_STATE_Pressure_Relief_Trip(double mV_NODE_STATE_Pressure_Relief_Trip) {
		MV_NODE_STATE_Pressure_Relief_Trip = mV_NODE_STATE_Pressure_Relief_Trip;
	}
	public double getWORK_STATE_DOOR_OPEN_PROT() {
		return WORK_STATE_DOOR_OPEN_PROT;
	}
	public void setWORK_STATE_DOOR_OPEN_PROT(double wORK_STATE_DOOR_OPEN_PROT) {
		WORK_STATE_DOOR_OPEN_PROT = wORK_STATE_DOOR_OPEN_PROT;
	}
	public double getWORK_STATE_EXTERNAL_EMER_STOP() {
		return WORK_STATE_EXTERNAL_EMER_STOP;
	}
	public void setWORK_STATE_EXTERNAL_EMER_STOP(double wORK_STATE_EXTERNAL_EMER_STOP) {
		WORK_STATE_EXTERNAL_EMER_STOP = wORK_STATE_EXTERNAL_EMER_STOP;
	}
	public double getWORK_STATE_LOCAL_EMER_STOP() {
		return WORK_STATE_LOCAL_EMER_STOP;
	}
	public void setWORK_STATE_LOCAL_EMER_STOP(double wORK_STATE_LOCAL_EMER_STOP) {
		WORK_STATE_LOCAL_EMER_STOP = wORK_STATE_LOCAL_EMER_STOP;
	}
	public double getWORK_STATE_MV_FAULT() {
		return WORK_STATE_MV_FAULT;
	}
	public void setWORK_STATE_MV_FAULT(double wORK_STATE_MV_FAULT) {
		WORK_STATE_MV_FAULT = wORK_STATE_MV_FAULT;
	}
	public double getWORK_STATE_REM_EMER_STOP() {
		return WORK_STATE_REM_EMER_STOP;
	}
	public void setWORK_STATE_REM_EMER_STOP(double wORK_STATE_REM_EMER_STOP) {
		WORK_STATE_REM_EMER_STOP = wORK_STATE_REM_EMER_STOP;
	}
	public double getWORK_STATE_RUNNING() {
		return WORK_STATE_RUNNING;
	}
	public void setWORK_STATE_RUNNING(double wORK_STATE_RUNNING) {
		WORK_STATE_RUNNING = wORK_STATE_RUNNING;
	}
	public double getWORK_STATE_SMOKE_PROT() {
		return WORK_STATE_SMOKE_PROT;
	}
	public void setWORK_STATE_SMOKE_PROT(double wORK_STATE_SMOKE_PROT) {
		WORK_STATE_SMOKE_PROT = wORK_STATE_SMOKE_PROT;
	}
	public double getWORK_STATE_STOPPED() {
		return WORK_STATE_STOPPED;
	}
	public void setWORK_STATE_STOPPED(double wORK_STATE_STOPPED) {
		WORK_STATE_STOPPED = wORK_STATE_STOPPED;
	}
	
	
}
