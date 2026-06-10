/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPextronUrp6000Entity extends ModelBaseEntity {	
	private double PhaseACurrent;
	private double PhaseBCurrent;
	private double PhaseCCurrent;
	private double GroundCurrent;
	private double NeutralCurrent;
	private double PhaseAVoltage;
	private double PhaseBVoltage;
	private double PhaseCVoltage;
	private double ResidualVoltage;
	private double NegativeSequenceCurrent;
	private double LineFrequency;
	private double PhaseAPowerFactor;
	private double PhaseBPowerFactor;
	private double PhaseCPowerFactor;
	private double AuxillaryPowerSupply;
	private double ActivePowerPhaseA;
	private double ActivePowerPhaseB;
	private double ActivePowerPhaseC;
	private double InternalRelayTemperature;
	private double PhaseAi2t;
	private double PhaseBi2t;
	private double PhaseCi2t;
	private double NumberofCircuitBreakerOpenings;
	private double PowerFactor;
	private double InductiveEnergyStorage;
	private double CapacitiveEnergyStorage;
	private double InductiveReactiveEnergyStorage;
	private double CapactiveReactiveEnergyStorage;
	private double OutputRelay1;
	private double OutputRelay2;
	private double OutputRelay3;
	private double OutputRelay4;
	private double OutputRelay5;
	private double HotLineTag;
	private double EnableRemoteReset;
	private double TotalActivePower;
	private double TotalReactivePower;
	private double PhaseandNeutralCurrentTransformerRatio;
	private double InputCurrentTransformerRatio;
	private double PotentialTransformerRatio;
	private double PhaseAActivePowerUncalculated;
	private double PhaseBActivePowerUncalculated;
	private double PhaseCActivePowerUncalculated;
	private double TotalActivePowerUncalculated;
	private double TotalReactivePowerUncalculated;
	private double PowerFactorUncalculated;
	private double PhaseAPowerFactorUncalculated;
	private double PhaseBPowerFactorUncalculated;
	private double PhaseCPowerFactorUncalculated;
	private double PhaseAi2tUncalculated;
	private double PhaseBi2tUncalculated;
	private double PhaseCi2tUncalculated;
	private double ProtectionStateFlags;
	private double ProtectionStateFlags2;
	private double ProtectionStateFlagsOutputRelays;
	
	public double getPhaseACurrent() {
		return PhaseACurrent;
	}
	public void setPhaseACurrent(double phaseACurrent) {
		PhaseACurrent = phaseACurrent;
	}
	public double getPhaseBCurrent() {
		return PhaseBCurrent;
	}
	public void setPhaseBCurrent(double phaseBCurrent) {
		PhaseBCurrent = phaseBCurrent;
	}
	public double getPhaseCCurrent() {
		return PhaseCCurrent;
	}
	public void setPhaseCCurrent(double phaseCCurrent) {
		PhaseCCurrent = phaseCCurrent;
	}
	public double getGroundCurrent() {
		return GroundCurrent;
	}
	public void setGroundCurrent(double groundCurrent) {
		GroundCurrent = groundCurrent;
	}
	public double getNeutralCurrent() {
		return NeutralCurrent;
	}
	public void setNeutralCurrent(double neutralCurrent) {
		NeutralCurrent = neutralCurrent;
	}
	public double getPhaseAVoltage() {
		return PhaseAVoltage;
	}
	public void setPhaseAVoltage(double phaseAVoltage) {
		PhaseAVoltage = phaseAVoltage;
	}
	public double getPhaseBVoltage() {
		return PhaseBVoltage;
	}
	public void setPhaseBVoltage(double phaseBVoltage) {
		PhaseBVoltage = phaseBVoltage;
	}
	public double getPhaseCVoltage() {
		return PhaseCVoltage;
	}
	public void setPhaseCVoltage(double phaseCVoltage) {
		PhaseCVoltage = phaseCVoltage;
	}
	public double getResidualVoltage() {
		return ResidualVoltage;
	}
	public void setResidualVoltage(double residualVoltage) {
		ResidualVoltage = residualVoltage;
	}
	public double getNegativeSequenceCurrent() {
		return NegativeSequenceCurrent;
	}
	public void setNegativeSequenceCurrent(double negativeSequenceCurrent) {
		NegativeSequenceCurrent = negativeSequenceCurrent;
	}
	public double getLineFrequency() {
		return LineFrequency;
	}
	public void setLineFrequency(double lineFrequency) {
		LineFrequency = lineFrequency;
	}
	public double getPhaseAPowerFactor() {
		return PhaseAPowerFactor;
	}
	public void setPhaseAPowerFactor(double phaseAPowerFactor) {
		PhaseAPowerFactor = phaseAPowerFactor;
	}
	public double getPhaseBPowerFactor() {
		return PhaseBPowerFactor;
	}
	public void setPhaseBPowerFactor(double phaseBPowerFactor) {
		PhaseBPowerFactor = phaseBPowerFactor;
	}
	public double getPhaseCPowerFactor() {
		return PhaseCPowerFactor;
	}
	public void setPhaseCPowerFactor(double phaseCPowerFactor) {
		PhaseCPowerFactor = phaseCPowerFactor;
	}
	public double getAuxillaryPowerSupply() {
		return AuxillaryPowerSupply;
	}
	public void setAuxillaryPowerSupply(double auxillaryPowerSupply) {
		AuxillaryPowerSupply = auxillaryPowerSupply;
	}
	public double getActivePowerPhaseA() {
		return ActivePowerPhaseA;
	}
	public void setActivePowerPhaseA(double activePowerPhaseA) {
		ActivePowerPhaseA = activePowerPhaseA;
	}
	public double getActivePowerPhaseB() {
		return ActivePowerPhaseB;
	}
	public void setActivePowerPhaseB(double activePowerPhaseB) {
		ActivePowerPhaseB = activePowerPhaseB;
	}
	public double getActivePowerPhaseC() {
		return ActivePowerPhaseC;
	}
	public void setActivePowerPhaseC(double activePowerPhaseC) {
		ActivePowerPhaseC = activePowerPhaseC;
	}
	public double getInternalRelayTemperature() {
		return InternalRelayTemperature;
	}
	public void setInternalRelayTemperature(double internalRelayTemperature) {
		InternalRelayTemperature = internalRelayTemperature;
	}
	public double getPhaseAi2t() {
		return PhaseAi2t;
	}
	public void setPhaseAi2t(double phaseAi2t) {
		PhaseAi2t = phaseAi2t;
	}
	public double getPhaseBi2t() {
		return PhaseBi2t;
	}
	public void setPhaseBi2t(double phaseBi2t) {
		PhaseBi2t = phaseBi2t;
	}
	public double getPhaseCi2t() {
		return PhaseCi2t;
	}
	public void setPhaseCi2t(double phaseCi2t) {
		PhaseCi2t = phaseCi2t;
	}
	public double getNumberofCircuitBreakerOpenings() {
		return NumberofCircuitBreakerOpenings;
	}
	public void setNumberofCircuitBreakerOpenings(double numberofCircuitBreakerOpenings) {
		NumberofCircuitBreakerOpenings = numberofCircuitBreakerOpenings;
	}
	public double getPowerFactor() {
		return PowerFactor;
	}
	public void setPowerFactor(double powerFactor) {
		PowerFactor = powerFactor;
	}
	public double getInductiveEnergyStorage() {
		return InductiveEnergyStorage;
	}
	public void setInductiveEnergyStorage(double inductiveEnergyStorage) {
		InductiveEnergyStorage = inductiveEnergyStorage;
	}
	public double getCapacitiveEnergyStorage() {
		return CapacitiveEnergyStorage;
	}
	public void setCapacitiveEnergyStorage(double capacitiveEnergyStorage) {
		CapacitiveEnergyStorage = capacitiveEnergyStorage;
	}
	public double getInductiveReactiveEnergyStorage() {
		return InductiveReactiveEnergyStorage;
	}
	public void setInductiveReactiveEnergyStorage(double inductiveReactiveEnergyStorage) {
		InductiveReactiveEnergyStorage = inductiveReactiveEnergyStorage;
	}
	public double getCapactiveReactiveEnergyStorage() {
		return CapactiveReactiveEnergyStorage;
	}
	public void setCapactiveReactiveEnergyStorage(double capactiveReactiveEnergyStorage) {
		CapactiveReactiveEnergyStorage = capactiveReactiveEnergyStorage;
	}
	public double getOutputRelay1() {
		return OutputRelay1;
	}
	public void setOutputRelay1(double outputRelay1) {
		OutputRelay1 = outputRelay1;
	}
	public double getOutputRelay2() {
		return OutputRelay2;
	}
	public void setOutputRelay2(double outputRelay2) {
		OutputRelay2 = outputRelay2;
	}
	public double getOutputRelay3() {
		return OutputRelay3;
	}
	public void setOutputRelay3(double outputRelay3) {
		OutputRelay3 = outputRelay3;
	}
	public double getOutputRelay4() {
		return OutputRelay4;
	}
	public void setOutputRelay4(double outputRelay4) {
		OutputRelay4 = outputRelay4;
	}
	public double getOutputRelay5() {
		return OutputRelay5;
	}
	public void setOutputRelay5(double outputRelay5) {
		OutputRelay5 = outputRelay5;
	}
	public double getHotLineTag() {
		return HotLineTag;
	}
	public void setHotLineTag(double hotLineTag) {
		HotLineTag = hotLineTag;
	}
	public double getEnableRemoteReset() {
		return EnableRemoteReset;
	}
	public void setEnableRemoteReset(double enableRemoteReset) {
		EnableRemoteReset = enableRemoteReset;
	}
	public double getTotalActivePower() {
		return TotalActivePower;
	}
	public void setTotalActivePower(double totalActivePower) {
		TotalActivePower = totalActivePower;
	}
	public double getTotalReactivePower() {
		return TotalReactivePower;
	}
	public void setTotalReactivePower(double totalReactivePower) {
		TotalReactivePower = totalReactivePower;
	}
	public double getPhaseandNeutralCurrentTransformerRatio() {
		return PhaseandNeutralCurrentTransformerRatio;
	}
	public void setPhaseandNeutralCurrentTransformerRatio(double phaseandNeutralCurrentTransformerRatio) {
		PhaseandNeutralCurrentTransformerRatio = phaseandNeutralCurrentTransformerRatio;
	}
	public double getInputCurrentTransformerRatio() {
		return InputCurrentTransformerRatio;
	}
	public void setInputCurrentTransformerRatio(double inputCurrentTransformerRatio) {
		InputCurrentTransformerRatio = inputCurrentTransformerRatio;
	}
	public double getPotentialTransformerRatio() {
		return PotentialTransformerRatio;
	}
	public void setPotentialTransformerRatio(double potentialTransformerRatio) {
		PotentialTransformerRatio = potentialTransformerRatio;
	}
	public double getPhaseAActivePowerUncalculated() {
		return PhaseAActivePowerUncalculated;
	}
	public void setPhaseAActivePowerUncalculated(double phaseAActivePowerUncalculated) {
		PhaseAActivePowerUncalculated = phaseAActivePowerUncalculated;
	}
	public double getPhaseBActivePowerUncalculated() {
		return PhaseBActivePowerUncalculated;
	}
	public void setPhaseBActivePowerUncalculated(double phaseBActivePowerUncalculated) {
		PhaseBActivePowerUncalculated = phaseBActivePowerUncalculated;
	}
	public double getPhaseCActivePowerUncalculated() {
		return PhaseCActivePowerUncalculated;
	}
	public void setPhaseCActivePowerUncalculated(double phaseCActivePowerUncalculated) {
		PhaseCActivePowerUncalculated = phaseCActivePowerUncalculated;
	}
	public double getTotalActivePowerUncalculated() {
		return TotalActivePowerUncalculated;
	}
	public void setTotalActivePowerUncalculated(double totalActivePowerUncalculated) {
		TotalActivePowerUncalculated = totalActivePowerUncalculated;
	}
	public double getTotalReactivePowerUncalculated() {
		return TotalReactivePowerUncalculated;
	}
	public void setTotalReactivePowerUncalculated(double totalReactivePowerUncalculated) {
		TotalReactivePowerUncalculated = totalReactivePowerUncalculated;
	}
	public double getPowerFactorUncalculated() {
		return PowerFactorUncalculated;
	}
	public void setPowerFactorUncalculated(double powerFactorUncalculated) {
		PowerFactorUncalculated = powerFactorUncalculated;
	}
	public double getPhaseAPowerFactorUncalculated() {
		return PhaseAPowerFactorUncalculated;
	}
	public void setPhaseAPowerFactorUncalculated(double phaseAPowerFactorUncalculated) {
		PhaseAPowerFactorUncalculated = phaseAPowerFactorUncalculated;
	}
	public double getPhaseBPowerFactorUncalculated() {
		return PhaseBPowerFactorUncalculated;
	}
	public void setPhaseBPowerFactorUncalculated(double phaseBPowerFactorUncalculated) {
		PhaseBPowerFactorUncalculated = phaseBPowerFactorUncalculated;
	}
	public double getPhaseCPowerFactorUncalculated() {
		return PhaseCPowerFactorUncalculated;
	}
	public void setPhaseCPowerFactorUncalculated(double phaseCPowerFactorUncalculated) {
		PhaseCPowerFactorUncalculated = phaseCPowerFactorUncalculated;
	}
	public double getPhaseAi2tUncalculated() {
		return PhaseAi2tUncalculated;
	}
	public void setPhaseAi2tUncalculated(double phaseAi2tUncalculated) {
		PhaseAi2tUncalculated = phaseAi2tUncalculated;
	}
	public double getPhaseBi2tUncalculated() {
		return PhaseBi2tUncalculated;
	}
	public void setPhaseBi2tUncalculated(double phaseBi2tUncalculated) {
		PhaseBi2tUncalculated = phaseBi2tUncalculated;
	}
	public double getPhaseCi2tUncalculated() {
		return PhaseCi2tUncalculated;
	}
	public void setPhaseCi2tUncalculated(double phaseCi2tUncalculated) {
		PhaseCi2tUncalculated = phaseCi2tUncalculated;
	}
	public double getProtectionStateFlags() {
		return ProtectionStateFlags;
	}
	public void setProtectionStateFlags(double protectionStateFlags) {
		ProtectionStateFlags = protectionStateFlags;
	}
	public double getProtectionStateFlags2() {
		return ProtectionStateFlags2;
	}
	public void setProtectionStateFlags2(double protectionStateFlags2) {
		ProtectionStateFlags2 = protectionStateFlags2;
	}
	public double getProtectionStateFlagsOutputRelays() {
		return ProtectionStateFlagsOutputRelays;
	}
	public void setProtectionStateFlagsOutputRelays(double protectionStateFlagsOutputRelays) {
		ProtectionStateFlagsOutputRelays = protectionStateFlagsOutputRelays;
	}
}
