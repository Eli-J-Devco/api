/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelAcuvimIIREntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double Frequency;
	private double PhasevoltageV1;
	private double PhasevoltageV2;
	private double PhasevoltageV3;
	private double AveragevoltageVavg;
	private double LinevoltageV12;
	private double LinevoltageV23;
	private double LinevoltageV31;
	private double AveragelinevoltageVLavg;
	private double PhaselinecurrentI1;	
	private double PhaselinecurrentI2;
	private double PhaselinecurrentI3;
	private double AveragecurrentIavg;
	private double NeutrallinecurrentIn;
	private double PhaseApowerPa;
	private double PhaseBpowerPb;
	private double PhaseCpowerPc;
	private double SystempowerPsum;
	private double PhaseAreactivepowerQa;
	private double PhaseBreactivepowerQb;
	private double PhaseCreactivepowerQc;	
	private double SystemreactivepowerQsum;
	private double PhaseAapparentpowerSa;
	private double PhaseBapparentpowerSb;
	private double PhaseCapparentpowerSc;
	private double SystemapparentpowerSsum;
	private double PhaseApowerfactorPFa;
	private double PhaseBpowerfactorPFb;
	private double PhaseCpowerfactorPFc;
	private double SystempowerfactorPFsum;
	private double Voltageunbalancefactor;	
	private double Currentunbalancefactor;
	private double LoadcharacteristicLCR;
	private double Powerdemand;
	private double Reactivepowerdemand;
	private double Apparentpowerdemand;
	private double V1Maximum;
	private double V2Maximum;
	private double V3Maximum;
	private double V12Maximum;
	private double V23Maximum;	
	private double V31Maximum;
	private double I1Maximum;
	private double I2Maximum;
	private double I3Maximum;
	private double SystemPowerMaximum;
	private double SystemReactivePowerMaximum;
	private double SystemApparentPowerMaximum;
	private double PowerFactorMaximum;
	private double FrequencyMaximum;
	private double PowerDemandMaximum;	
	private double ReactivePowerDemandMaximum;
	private double ApparentPowerDemandMaximum;
	private double VoltageUnbalanceFactorMaximum;
	private double CurrentUnbalanceFactormaximum;
	private double V1V12THDMaximum;
	private double V2V31THDMaximum;
	private double V3V23THDMaximum;
	private double I1THDMaximum;
	private double I2THDMaximum;
	private double I3THDMaximum;
	private double PhaseAngleV2toV1;
	private double PhaseAngleV3toV1;
	private double PhaseAngleI1toV1;
	private double PhaseAngleI2toV1;
	private double PhaseAngleI3toV1;
	private double PhaseAngleV23toV12;
	private double PhaseAngleofI1toV12;
	private double PhaseAngleofI2toV12;
	private double PhaseAngleofI3toV12;
	private double THDV1ofV1V12;	
	private double THDV1ofV2V31;
	private double THDV1ofV3V23;
	private double AverageTHDofV;
	private double THDI1;
	private double THDI2;
	private double THDI3;
	private double AverageTHDofI;
	private double EnergyIMP;
	private double EnergyEXP;
	private double ReactiveEnergyIMP;	
	private double ReactiveEnergyEXP;
	private double EnergyTotal;
	private double EnergyNet;
	private double ReactiveEnergyTotal;
	private double ReactiveEnergyNet;
	private double ApparentEnergy;
	
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	private int enable_alert;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getLow_alarm() {
		return low_alarm;
	}
	public void setLow_alarm(int low_alarm) {
		this.low_alarm = low_alarm;
	}
	public int getHigh_alarm() {
		return high_alarm;
	}
	public void setHigh_alarm(int high_alarm) {
		this.high_alarm = high_alarm;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getPhasevoltageV1() {
		return PhasevoltageV1;
	}
	public void setPhasevoltageV1(double phasevoltageV1) {
		PhasevoltageV1 = phasevoltageV1;
	}
	public double getPhasevoltageV2() {
		return PhasevoltageV2;
	}
	public void setPhasevoltageV2(double phasevoltageV2) {
		PhasevoltageV2 = phasevoltageV2;
	}
	public double getPhasevoltageV3() {
		return PhasevoltageV3;
	}
	public void setPhasevoltageV3(double phasevoltageV3) {
		PhasevoltageV3 = phasevoltageV3;
	}
	public double getAveragevoltageVavg() {
		return AveragevoltageVavg;
	}
	public void setAveragevoltageVavg(double averagevoltageVavg) {
		AveragevoltageVavg = averagevoltageVavg;
	}
	public double getLinevoltageV12() {
		return LinevoltageV12;
	}
	public void setLinevoltageV12(double linevoltageV12) {
		LinevoltageV12 = linevoltageV12;
	}
	public double getLinevoltageV23() {
		return LinevoltageV23;
	}
	public void setLinevoltageV23(double linevoltageV23) {
		LinevoltageV23 = linevoltageV23;
	}
	public double getLinevoltageV31() {
		return LinevoltageV31;
	}
	public void setLinevoltageV31(double linevoltageV31) {
		LinevoltageV31 = linevoltageV31;
	}
	public double getAveragelinevoltageVLavg() {
		return AveragelinevoltageVLavg;
	}
	public void setAveragelinevoltageVLavg(double averagelinevoltageVLavg) {
		AveragelinevoltageVLavg = averagelinevoltageVLavg;
	}
	public double getPhaselinecurrentI1() {
		return PhaselinecurrentI1;
	}
	public void setPhaselinecurrentI1(double phaselinecurrentI1) {
		PhaselinecurrentI1 = phaselinecurrentI1;
	}
	public double getPhaselinecurrentI2() {
		return PhaselinecurrentI2;
	}
	public void setPhaselinecurrentI2(double phaselinecurrentI2) {
		PhaselinecurrentI2 = phaselinecurrentI2;
	}
	public double getPhaselinecurrentI3() {
		return PhaselinecurrentI3;
	}
	public void setPhaselinecurrentI3(double phaselinecurrentI3) {
		PhaselinecurrentI3 = phaselinecurrentI3;
	}
	public double getAveragecurrentIavg() {
		return AveragecurrentIavg;
	}
	public void setAveragecurrentIavg(double averagecurrentIavg) {
		AveragecurrentIavg = averagecurrentIavg;
	}
	public double getNeutrallinecurrentIn() {
		return NeutrallinecurrentIn;
	}
	public void setNeutrallinecurrentIn(double neutrallinecurrentIn) {
		NeutrallinecurrentIn = neutrallinecurrentIn;
	}
	public double getPhaseApowerPa() {
		return PhaseApowerPa;
	}
	public void setPhaseApowerPa(double phaseApowerPa) {
		PhaseApowerPa = phaseApowerPa;
	}
	public double getPhaseBpowerPb() {
		return PhaseBpowerPb;
	}
	public void setPhaseBpowerPb(double phaseBpowerPb) {
		PhaseBpowerPb = phaseBpowerPb;
	}
	public double getPhaseCpowerPc() {
		return PhaseCpowerPc;
	}
	public void setPhaseCpowerPc(double phaseCpowerPc) {
		PhaseCpowerPc = phaseCpowerPc;
	}
	public double getSystempowerPsum() {
		return SystempowerPsum;
	}
	public void setSystempowerPsum(double systempowerPsum) {
		SystempowerPsum = systempowerPsum;
	}
	public double getPhaseAreactivepowerQa() {
		return PhaseAreactivepowerQa;
	}
	public void setPhaseAreactivepowerQa(double phaseAreactivepowerQa) {
		PhaseAreactivepowerQa = phaseAreactivepowerQa;
	}
	public double getPhaseBreactivepowerQb() {
		return PhaseBreactivepowerQb;
	}
	public void setPhaseBreactivepowerQb(double phaseBreactivepowerQb) {
		PhaseBreactivepowerQb = phaseBreactivepowerQb;
	}
	public double getPhaseCreactivepowerQc() {
		return PhaseCreactivepowerQc;
	}
	public void setPhaseCreactivepowerQc(double phaseCreactivepowerQc) {
		PhaseCreactivepowerQc = phaseCreactivepowerQc;
	}
	public double getSystemreactivepowerQsum() {
		return SystemreactivepowerQsum;
	}
	public void setSystemreactivepowerQsum(double systemreactivepowerQsum) {
		SystemreactivepowerQsum = systemreactivepowerQsum;
	}
	public double getPhaseAapparentpowerSa() {
		return PhaseAapparentpowerSa;
	}
	public void setPhaseAapparentpowerSa(double phaseAapparentpowerSa) {
		PhaseAapparentpowerSa = phaseAapparentpowerSa;
	}
	public double getPhaseBapparentpowerSb() {
		return PhaseBapparentpowerSb;
	}
	public void setPhaseBapparentpowerSb(double phaseBapparentpowerSb) {
		PhaseBapparentpowerSb = phaseBapparentpowerSb;
	}
	public double getPhaseCapparentpowerSc() {
		return PhaseCapparentpowerSc;
	}
	public void setPhaseCapparentpowerSc(double phaseCapparentpowerSc) {
		PhaseCapparentpowerSc = phaseCapparentpowerSc;
	}
	public double getSystemapparentpowerSsum() {
		return SystemapparentpowerSsum;
	}
	public void setSystemapparentpowerSsum(double systemapparentpowerSsum) {
		SystemapparentpowerSsum = systemapparentpowerSsum;
	}
	public double getPhaseApowerfactorPFa() {
		return PhaseApowerfactorPFa;
	}
	public void setPhaseApowerfactorPFa(double phaseApowerfactorPFa) {
		PhaseApowerfactorPFa = phaseApowerfactorPFa;
	}
	public double getPhaseBpowerfactorPFb() {
		return PhaseBpowerfactorPFb;
	}
	public void setPhaseBpowerfactorPFb(double phaseBpowerfactorPFb) {
		PhaseBpowerfactorPFb = phaseBpowerfactorPFb;
	}
	public double getPhaseCpowerfactorPFc() {
		return PhaseCpowerfactorPFc;
	}
	public void setPhaseCpowerfactorPFc(double phaseCpowerfactorPFc) {
		PhaseCpowerfactorPFc = phaseCpowerfactorPFc;
	}
	public double getSystempowerfactorPFsum() {
		return SystempowerfactorPFsum;
	}
	public void setSystempowerfactorPFsum(double systempowerfactorPFsum) {
		SystempowerfactorPFsum = systempowerfactorPFsum;
	}
	public double getVoltageunbalancefactor() {
		return Voltageunbalancefactor;
	}
	public void setVoltageunbalancefactor(double voltageunbalancefactor) {
		Voltageunbalancefactor = voltageunbalancefactor;
	}
	public double getCurrentunbalancefactor() {
		return Currentunbalancefactor;
	}
	public void setCurrentunbalancefactor(double currentunbalancefactor) {
		Currentunbalancefactor = currentunbalancefactor;
	}
	public double getLoadcharacteristicLCR() {
		return LoadcharacteristicLCR;
	}
	public void setLoadcharacteristicLCR(double loadcharacteristicLCR) {
		LoadcharacteristicLCR = loadcharacteristicLCR;
	}
	public double getPowerdemand() {
		return Powerdemand;
	}
	public void setPowerdemand(double powerdemand) {
		Powerdemand = powerdemand;
	}
	public double getReactivepowerdemand() {
		return Reactivepowerdemand;
	}
	public void setReactivepowerdemand(double reactivepowerdemand) {
		Reactivepowerdemand = reactivepowerdemand;
	}
	public double getApparentpowerdemand() {
		return Apparentpowerdemand;
	}
	public void setApparentpowerdemand(double apparentpowerdemand) {
		Apparentpowerdemand = apparentpowerdemand;
	}
	public double getV1Maximum() {
		return V1Maximum;
	}
	public void setV1Maximum(double v1Maximum) {
		V1Maximum = v1Maximum;
	}
	public double getV2Maximum() {
		return V2Maximum;
	}
	public void setV2Maximum(double v2Maximum) {
		V2Maximum = v2Maximum;
	}
	public double getV3Maximum() {
		return V3Maximum;
	}
	public void setV3Maximum(double v3Maximum) {
		V3Maximum = v3Maximum;
	}
	public double getV12Maximum() {
		return V12Maximum;
	}
	public void setV12Maximum(double v12Maximum) {
		V12Maximum = v12Maximum;
	}
	public double getV23Maximum() {
		return V23Maximum;
	}
	public void setV23Maximum(double v23Maximum) {
		V23Maximum = v23Maximum;
	}
	public double getV31Maximum() {
		return V31Maximum;
	}
	public void setV31Maximum(double v31Maximum) {
		V31Maximum = v31Maximum;
	}
	public double getI1Maximum() {
		return I1Maximum;
	}
	public void setI1Maximum(double i1Maximum) {
		I1Maximum = i1Maximum;
	}
	public double getI2Maximum() {
		return I2Maximum;
	}
	public void setI2Maximum(double i2Maximum) {
		I2Maximum = i2Maximum;
	}
	public double getI3Maximum() {
		return I3Maximum;
	}
	public void setI3Maximum(double i3Maximum) {
		I3Maximum = i3Maximum;
	}
	public double getSystemPowerMaximum() {
		return SystemPowerMaximum;
	}
	public void setSystemPowerMaximum(double systemPowerMaximum) {
		SystemPowerMaximum = systemPowerMaximum;
	}
	public double getSystemReactivePowerMaximum() {
		return SystemReactivePowerMaximum;
	}
	public void setSystemReactivePowerMaximum(double systemReactivePowerMaximum) {
		SystemReactivePowerMaximum = systemReactivePowerMaximum;
	}
	public double getSystemApparentPowerMaximum() {
		return SystemApparentPowerMaximum;
	}
	public void setSystemApparentPowerMaximum(double systemApparentPowerMaximum) {
		SystemApparentPowerMaximum = systemApparentPowerMaximum;
	}
	public double getPowerFactorMaximum() {
		return PowerFactorMaximum;
	}
	public void setPowerFactorMaximum(double powerFactorMaximum) {
		PowerFactorMaximum = powerFactorMaximum;
	}
	public double getFrequencyMaximum() {
		return FrequencyMaximum;
	}
	public void setFrequencyMaximum(double frequencyMaximum) {
		FrequencyMaximum = frequencyMaximum;
	}
	public double getPowerDemandMaximum() {
		return PowerDemandMaximum;
	}
	public void setPowerDemandMaximum(double powerDemandMaximum) {
		PowerDemandMaximum = powerDemandMaximum;
	}
	public double getReactivePowerDemandMaximum() {
		return ReactivePowerDemandMaximum;
	}
	public void setReactivePowerDemandMaximum(double reactivePowerDemandMaximum) {
		ReactivePowerDemandMaximum = reactivePowerDemandMaximum;
	}
	public double getApparentPowerDemandMaximum() {
		return ApparentPowerDemandMaximum;
	}
	public void setApparentPowerDemandMaximum(double apparentPowerDemandMaximum) {
		ApparentPowerDemandMaximum = apparentPowerDemandMaximum;
	}
	public double getVoltageUnbalanceFactorMaximum() {
		return VoltageUnbalanceFactorMaximum;
	}
	public void setVoltageUnbalanceFactorMaximum(double voltageUnbalanceFactorMaximum) {
		VoltageUnbalanceFactorMaximum = voltageUnbalanceFactorMaximum;
	}
	public double getCurrentUnbalanceFactormaximum() {
		return CurrentUnbalanceFactormaximum;
	}
	public void setCurrentUnbalanceFactormaximum(double currentUnbalanceFactormaximum) {
		CurrentUnbalanceFactormaximum = currentUnbalanceFactormaximum;
	}
	public double getV1V12THDMaximum() {
		return V1V12THDMaximum;
	}
	public void setV1V12THDMaximum(double v1v12thdMaximum) {
		V1V12THDMaximum = v1v12thdMaximum;
	}
	public double getV2V31THDMaximum() {
		return V2V31THDMaximum;
	}
	public void setV2V31THDMaximum(double v2v31thdMaximum) {
		V2V31THDMaximum = v2v31thdMaximum;
	}
	public double getV3V23THDMaximum() {
		return V3V23THDMaximum;
	}
	public void setV3V23THDMaximum(double v3v23thdMaximum) {
		V3V23THDMaximum = v3v23thdMaximum;
	}
	public double getI1THDMaximum() {
		return I1THDMaximum;
	}
	public void setI1THDMaximum(double i1thdMaximum) {
		I1THDMaximum = i1thdMaximum;
	}
	public double getI2THDMaximum() {
		return I2THDMaximum;
	}
	public void setI2THDMaximum(double i2thdMaximum) {
		I2THDMaximum = i2thdMaximum;
	}
	public double getI3THDMaximum() {
		return I3THDMaximum;
	}
	public void setI3THDMaximum(double i3thdMaximum) {
		I3THDMaximum = i3thdMaximum;
	}
	public double getPhaseAngleV2toV1() {
		return PhaseAngleV2toV1;
	}
	public void setPhaseAngleV2toV1(double phaseAngleV2toV1) {
		PhaseAngleV2toV1 = phaseAngleV2toV1;
	}
	public double getPhaseAngleV3toV1() {
		return PhaseAngleV3toV1;
	}
	public void setPhaseAngleV3toV1(double phaseAngleV3toV1) {
		PhaseAngleV3toV1 = phaseAngleV3toV1;
	}
	public double getPhaseAngleI1toV1() {
		return PhaseAngleI1toV1;
	}
	public void setPhaseAngleI1toV1(double phaseAngleI1toV1) {
		PhaseAngleI1toV1 = phaseAngleI1toV1;
	}
	public double getPhaseAngleI2toV1() {
		return PhaseAngleI2toV1;
	}
	public void setPhaseAngleI2toV1(double phaseAngleI2toV1) {
		PhaseAngleI2toV1 = phaseAngleI2toV1;
	}
	public double getPhaseAngleI3toV1() {
		return PhaseAngleI3toV1;
	}
	public void setPhaseAngleI3toV1(double phaseAngleI3toV1) {
		PhaseAngleI3toV1 = phaseAngleI3toV1;
	}
	public double getPhaseAngleV23toV12() {
		return PhaseAngleV23toV12;
	}
	public void setPhaseAngleV23toV12(double phaseAngleV23toV12) {
		PhaseAngleV23toV12 = phaseAngleV23toV12;
	}
	public double getPhaseAngleofI1toV12() {
		return PhaseAngleofI1toV12;
	}
	public void setPhaseAngleofI1toV12(double phaseAngleofI1toV12) {
		PhaseAngleofI1toV12 = phaseAngleofI1toV12;
	}
	public double getPhaseAngleofI2toV12() {
		return PhaseAngleofI2toV12;
	}
	public void setPhaseAngleofI2toV12(double phaseAngleofI2toV12) {
		PhaseAngleofI2toV12 = phaseAngleofI2toV12;
	}
	public double getPhaseAngleofI3toV12() {
		return PhaseAngleofI3toV12;
	}
	public void setPhaseAngleofI3toV12(double phaseAngleofI3toV12) {
		PhaseAngleofI3toV12 = phaseAngleofI3toV12;
	}
	public double getTHDV1ofV1V12() {
		return THDV1ofV1V12;
	}
	public void setTHDV1ofV1V12(double tHDV1ofV1V12) {
		THDV1ofV1V12 = tHDV1ofV1V12;
	}
	public double getTHDV1ofV2V31() {
		return THDV1ofV2V31;
	}
	public void setTHDV1ofV2V31(double tHDV1ofV2V31) {
		THDV1ofV2V31 = tHDV1ofV2V31;
	}
	public double getTHDV1ofV3V23() {
		return THDV1ofV3V23;
	}
	public void setTHDV1ofV3V23(double tHDV1ofV3V23) {
		THDV1ofV3V23 = tHDV1ofV3V23;
	}
	public double getAverageTHDofV() {
		return AverageTHDofV;
	}
	public void setAverageTHDofV(double averageTHDofV) {
		AverageTHDofV = averageTHDofV;
	}
	public double getTHDI1() {
		return THDI1;
	}
	public void setTHDI1(double tHDI1) {
		THDI1 = tHDI1;
	}
	public double getTHDI2() {
		return THDI2;
	}
	public void setTHDI2(double tHDI2) {
		THDI2 = tHDI2;
	}
	public double getTHDI3() {
		return THDI3;
	}
	public void setTHDI3(double tHDI3) {
		THDI3 = tHDI3;
	}
	public double getAverageTHDofI() {
		return AverageTHDofI;
	}
	public void setAverageTHDofI(double averageTHDofI) {
		AverageTHDofI = averageTHDofI;
	}
	public double getEnergyIMP() {
		return EnergyIMP;
	}
	public void setEnergyIMP(double energyIMP) {
		EnergyIMP = energyIMP;
	}
	public double getEnergyEXP() {
		return EnergyEXP;
	}
	public void setEnergyEXP(double energyEXP) {
		EnergyEXP = energyEXP;
	}
	public double getReactiveEnergyIMP() {
		return ReactiveEnergyIMP;
	}
	public void setReactiveEnergyIMP(double reactiveEnergyIMP) {
		ReactiveEnergyIMP = reactiveEnergyIMP;
	}
	public double getReactiveEnergyEXP() {
		return ReactiveEnergyEXP;
	}
	public void setReactiveEnergyEXP(double reactiveEnergyEXP) {
		ReactiveEnergyEXP = reactiveEnergyEXP;
	}
	public double getEnergyTotal() {
		return EnergyTotal;
	}
	public void setEnergyTotal(double energyTotal) {
		EnergyTotal = energyTotal;
	}
	public double getEnergyNet() {
		return EnergyNet;
	}
	public void setEnergyNet(double energyNet) {
		EnergyNet = energyNet;
	}
	public double getReactiveEnergyTotal() {
		return ReactiveEnergyTotal;
	}
	public void setReactiveEnergyTotal(double reactiveEnergyTotal) {
		ReactiveEnergyTotal = reactiveEnergyTotal;
	}
	public double getReactiveEnergyNet() {
		return ReactiveEnergyNet;
	}
	public void setReactiveEnergyNet(double reactiveEnergyNet) {
		ReactiveEnergyNet = reactiveEnergyNet;
	}
	public double getApparentEnergy() {
		return ApparentEnergy;
	}
	public void setApparentEnergy(double apparentEnergy) {
		ApparentEnergy = apparentEnergy;
	}
	public double getNvmActivePower() {
		return nvmActivePower;
	}
	public void setNvmActivePower(double nvmActivePower) {
		this.nvmActivePower = nvmActivePower;
	}
	public double getNvmActiveEnergy() {
		return nvmActiveEnergy;
	}
	public void setNvmActiveEnergy(double nvmActiveEnergy) {
		this.nvmActiveEnergy = nvmActiveEnergy;
	}
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getView_tablename() {
		return view_tablename;
	}
	public void setView_tablename(String view_tablename) {
		this.view_tablename = view_tablename;
	}
	public String getJob_tablename() {
		return job_tablename;
	}
	public void setJob_tablename(String job_tablename) {
		this.job_tablename = job_tablename;
	}
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
	}
}
