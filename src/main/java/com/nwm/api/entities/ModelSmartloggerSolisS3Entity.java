/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSmartloggerSolisS3Entity extends ModelBaseEntity {
	
	private double ActivePowerModeFeedback;
	private double ReactivePowerModeFeedback;
	private double ActivePowerPercentageFeedback;
	private double ReactivePowerPercentageFeedback;
	private double PowerFactorSettingFeedback;
	private double ActiveGainConstant;
	private double ActiveGainCoefficient;
	private double PositiveReactiveGainConstant;
	private double PositiveReactiveGainCoefficient;
	private double NegativeReactiveGainConstant;
	private double NegativeReactiveGainCoefficient;
	private double AdjustableActiveUpperLimit;
	private double AdjustableReactiveUpperLimit;
	private double MeasuredTotalActivePoweratTargetPoint;
	private double MeasuredTotalReactivePoweratTargetPoint;
	private double MeasuredPowerFactoratTargetPoint;
	private double TotalActivePowerofINVs;
	private double TotalReactivePowerofINVs;
	private double OfflineINVCount;
	private double OnlineINVCount;
	private double TotalConnectedDevices;
	private double RatedActivePowerofArray;
	private double RatedReactivePowerofArray;
	private double DailyGenerationofINVs;
	private double MonthlyGenerationofINVs;
	private double AnnualGenerationofINVs;
	private double TotalGenerationofINVs;
	private double MaxDailyOperatingTimeofINVs;
	private double INVStatusXinjiang;
	private double INVStatusNingxia;
	private double INVStatusShaanxi;
	private double AdjustableActiveLowerLimit;
	private double AdjustableReactiveLowerLimit;
	private double ActiveRegulationDeadband;
	private double ReactiveRegulationDeadband;
	private double ActivePowerModeFeedbackPanorama;
	private double ReactivePowerModeFeedbackPanorama;
	private double ActivePowerPercentageFeedbackPanorama;
	private double ReactivePowerPercentageFeedbackPanorama;
	private double PowerFactorSettingFeedbackPanorama;
	private double ReceivedPowerControlTimeoutseconds;
	private double SpareTelemetry41;
	private double SpareTelemetry42;
	private double SpareTelemetry43;
	private double SpareTelemetry44;
	private double SpareTelemetry45;
	private double SpareTelemetry46;
	private double SpareTelemetry47;
	private double SpareTelemetry48;
	private double SpareTelemetry49;
	private double Com11INVActivePowerModeFeedback;
	private double Com11INVReactivePowerModeFeedback;
	private double Com11INVActivePowerPercentageFeedback;
	private double Com11INVReactivePowerPercentageFeedback;
	private double Com11INVPowerFactorSettingFeedback;
	private double Com12INVActivePowerModeFeedback;
	private double Com12INVReactivePowerModeFeedback;
	private double Com12INVActivePowerPercentageFeedback;
	private double Com12INVReactivePowerPercentageFeedback;
	private double Com12INVPowerFactorSettingFeedback;
	private double Com13INVActivePowerModeFeedback;
	private double Com13INVReactivePowerModeFeedback;
	private double Com13INVActivePowerPercentageFeedback;
	private double Com13INVReactivePowerPercentageFeedback;
	private double Com13INVPowerFactorSettingFeedback;
	private double Com14INVActivePowerModeFeedback;
	private double Com14INVReactivePowerModeFeedback;
	private double Com14INVActivePowerPercentageFeedback;
	private double Com14INVReactivePowerPercentageFeedback;
	private double Com14INVPowerFactorSettingFeedback;
	private double Com15INVActivePowerModeFeedback;
	private double Com15INVReactivePowerModeFeedback;
	private double Com15INVActivePowerPercentageFeedback;
	private double Com15INVReactivePowerPercentageFeedback;
	private double Com15INVPowerFactorSettingFeedback;
	private double Com16INVActivePowerModeFeedback;
	private double Com16INVReactivePowerModeFeedback;
	private double Com16INVActivePowerPercentageFeedback;
	private double Com16INVReactivePowerPercentageFeedback;
	private double Com16INVPowerFactorSettingFeedback;
	private double Com17INVActivePowerModeFeedback;
	private double Com17INVReactivePowerModeFeedback;
	private double Com17INVActivePowerPercentageFeedback;
	private double Com17INVReactivePowerPercentageFeedback;
	private double Com17INVPowerFactorSettingFeedback;
	private double Com18INVActivePowerModeFeedback;
	private double Com18INVReactivePowerModeFeedback;
	private double Com18INVActivePowerPercentageFeedback;
	private double Com18INVReactivePowerPercentageFeedback;
	private double Com18INVPowerFactorSettingFeedback;
	private double Com19INVActivePowerModeFeedback;
	private double Com19INVReactivePowerModeFeedback;
	private double Com19INVActivePowerPercentageFeedback;
	private double Com19INVReactivePowerPercentageFeedback;
	private double Com19INVPowerFactorSettingFeedback;
	private double Com110INVActivePowerModeFeedback;
	private double Com110INVReactivePowerModeFeedback;
	private double Com110INVActivePowerPercentageFeedback;
	private double Com110INVReactivePowerPercentageFeedback;
	private double Com110INVPowerFactorSettingFeedback;
	private double Com111INVActivePowerModeFeedback;
	private double Com111INVReactivePowerModeFeedback;
	private double Com111INVActivePowerPercentageFeedback;
	private double Com111INVReactivePowerPercentageFeedback;
	private double Com111INVPowerFactorSettingFeedback;
	private double Com112INVActivePowerModeFeedback;
	private double Com112INVReactivePowerModeFeedback;
	private double Com112INVActivePowerPercentageFeedback;
	private double Com112INVReactivePowerPercentageFeedback;
	private double Com112INVPowerFactorSettingFeedback;
	private double Com113INVActivePowerModeFeedback;
	private double Com113INVReactivePowerModeFeedback;
	private double Com113INVActivePowerPercentageFeedback;
	private double Com113INVReactivePowerPercentageFeedback;
	private double Com113INVPowerFactorSettingFeedback;
	private double Com114INVActivePowerModeFeedback;
	private double Com114INVReactivePowerModeFeedback;
	private double Com114INVActivePowerPercentageFeedback;
	private double Com114INVReactivePowerPercentageFeedback;
	private double Com114INVPowerFactorSettingFeedback;
	private double Com115INVActivePowerModeFeedback;
	private double Com115INVReactivePowerModeFeedback;
	private double Com115INVActivePowerPercentageFeedback;
	private double Com115INVReactivePowerPercentageFeedback;
	private double Com115INVPowerFactorSettingFeedback;
	private double Com21INVActivePowerModeFeedback;
	private double Com21INVReactivePowerModeFeedback;
	private double Com21INVActivePowerPercentageFeedback;
	private double Com21INVReactivePowerPercentageFeedback;
	private double Com21INVPowerFactorSettingFeedback;
	private double Com22INVActivePowerModeFeedback;
	private double Com22INVReactivePowerModeFeedback;
	private double Com22INVActivePowerPercentageFeedback;
	private double Com22INVReactivePowerPercentageFeedback;
	private double Com22INVPowerFactorSettingFeedback;
	private double Com23INVActivePowerModeFeedback;
	private double Com23INVReactivePowerModeFeedback;
	private double Com23INVActivePowerPercentageFeedback;
	private double Com23INVReactivePowerPercentageFeedback;
	private double Com23INVPowerFactorSettingFeedback;
	private double Com24INVActivePowerModeFeedback;
	private double Com24INVReactivePowerModeFeedback;
	private double Com24INVActivePowerPercentageFeedback;
	private double Com24INVReactivePowerPercentageFeedback;
	private double Com24INVPowerFactorSettingFeedback;
	private double Com25INVActivePowerModeFeedback;
	private double Com25INVReactivePowerModeFeedback;
	private double Com25INVActivePowerPercentageFeedback;
	private double Com25INVReactivePowerPercentageFeedback;
	private double Com25INVPowerFactorSettingFeedback;
	private double Com26INVActivePowerModeFeedback;
	private double Com26INVReactivePowerModeFeedback;
	private double Com26INVActivePowerPercentageFeedback;
	private double Com26INVReactivePowerPercentageFeedback;
	private double Com26INVPowerFactorSettingFeedback;
	private double Com27INVActivePowerModeFeedback;
	private double Com27INVReactivePowerModeFeedback;
	private double Com27INVActivePowerPercentageFeedback;
	private double Com27INVReactivePowerPercentageFeedback;
	private double Com27INVPowerFactorSettingFeedback;
	private double Com28INVActivePowerModeFeedback;
	private double Com28INVReactivePowerModeFeedback;
	private double Com28INVActivePowerPercentageFeedback;
	private double Com28INVReactivePowerPercentageFeedback;
	private double Com28INVPowerFactorSettingFeedback;
	private double Com29INVActivePowerModeFeedback;
	private double Com29INVReactivePowerModeFeedback;
	private double Com29INVActivePowerPercentageFeedback;
	private double Com29INVReactivePowerPercentageFeedback;
	private double Com29INVPowerFactorSettingFeedback;
	private double Com210INVActivePowerModeFeedback;
	private double Com210INVReactivePowerModeFeedback;
	private double Com210INVActivePowerPercentageFeedback;
	private double Com210INVReactivePowerPercentageFeedback;
	private double Com210INVPowerFactorSettingFeedback;
	private double Com211INVActivePowerModeFeedback;
	private double Com211INVReactivePowerModeFeedback;
	private double Com211INVActivePowerPercentageFeedback;
	private double Com211INVReactivePowerPercentageFeedback;
	private double Com211INVPowerFactorSettingFeedback;
	private double Com212INVActivePowerModeFeedback;
	private double Com212INVReactivePowerModeFeedback;
	private double Com212INVActivePowerPercentageFeedback;
	private double Com212INVReactivePowerPercentageFeedback;
	private double Com212INVPowerFactorSettingFeedback;
	private double Com213INVActivePowerModeFeedback;
	private double Com213INVReactivePowerModeFeedback;
	private double Com213INVActivePowerPercentageFeedback;
	private double Com213INVReactivePowerPercentageFeedback;
	private double Com213INVPowerFactorSettingFeedback;
	private double Com214INVActivePowerModeFeedback;
	private double Com214INVReactivePowerModeFeedback;
	private double Com214INVActivePowerPercentageFeedback;
	private double Com214INVReactivePowerPercentageFeedback;
	private double Com214INVPowerFactorSettingFeedback;
	private double Com215INVActivePowerModeFeedback;
	private double Com215INVReactivePowerModeFeedback;
	private double Com215INVActivePowerPercentageFeedback;
	private double Com215INVReactivePowerPercentageFeedback;
	private double Com215INVPowerFactorSettingFeedback;
	public double getActivePowerModeFeedback() {
		return ActivePowerModeFeedback;
	}
	public void setActivePowerModeFeedback(double activePowerModeFeedback) {
		ActivePowerModeFeedback = activePowerModeFeedback;
	}
	public double getReactivePowerModeFeedback() {
		return ReactivePowerModeFeedback;
	}
	public void setReactivePowerModeFeedback(double reactivePowerModeFeedback) {
		ReactivePowerModeFeedback = reactivePowerModeFeedback;
	}
	public double getActivePowerPercentageFeedback() {
		return ActivePowerPercentageFeedback;
	}
	public void setActivePowerPercentageFeedback(double activePowerPercentageFeedback) {
		ActivePowerPercentageFeedback = activePowerPercentageFeedback;
	}
	public double getReactivePowerPercentageFeedback() {
		return ReactivePowerPercentageFeedback;
	}
	public void setReactivePowerPercentageFeedback(double reactivePowerPercentageFeedback) {
		ReactivePowerPercentageFeedback = reactivePowerPercentageFeedback;
	}
	public double getPowerFactorSettingFeedback() {
		return PowerFactorSettingFeedback;
	}
	public void setPowerFactorSettingFeedback(double powerFactorSettingFeedback) {
		PowerFactorSettingFeedback = powerFactorSettingFeedback;
	}
	public double getActiveGainConstant() {
		return ActiveGainConstant;
	}
	public void setActiveGainConstant(double activeGainConstant) {
		ActiveGainConstant = activeGainConstant;
	}
	public double getActiveGainCoefficient() {
		return ActiveGainCoefficient;
	}
	public void setActiveGainCoefficient(double activeGainCoefficient) {
		ActiveGainCoefficient = activeGainCoefficient;
	}
	public double getPositiveReactiveGainConstant() {
		return PositiveReactiveGainConstant;
	}
	public void setPositiveReactiveGainConstant(double positiveReactiveGainConstant) {
		PositiveReactiveGainConstant = positiveReactiveGainConstant;
	}
	public double getPositiveReactiveGainCoefficient() {
		return PositiveReactiveGainCoefficient;
	}
	public void setPositiveReactiveGainCoefficient(double positiveReactiveGainCoefficient) {
		PositiveReactiveGainCoefficient = positiveReactiveGainCoefficient;
	}
	public double getNegativeReactiveGainConstant() {
		return NegativeReactiveGainConstant;
	}
	public void setNegativeReactiveGainConstant(double negativeReactiveGainConstant) {
		NegativeReactiveGainConstant = negativeReactiveGainConstant;
	}
	public double getNegativeReactiveGainCoefficient() {
		return NegativeReactiveGainCoefficient;
	}
	public void setNegativeReactiveGainCoefficient(double negativeReactiveGainCoefficient) {
		NegativeReactiveGainCoefficient = negativeReactiveGainCoefficient;
	}
	public double getAdjustableActiveUpperLimit() {
		return AdjustableActiveUpperLimit;
	}
	public void setAdjustableActiveUpperLimit(double adjustableActiveUpperLimit) {
		AdjustableActiveUpperLimit = adjustableActiveUpperLimit;
	}
	public double getAdjustableReactiveUpperLimit() {
		return AdjustableReactiveUpperLimit;
	}
	public void setAdjustableReactiveUpperLimit(double adjustableReactiveUpperLimit) {
		AdjustableReactiveUpperLimit = adjustableReactiveUpperLimit;
	}
	public double getMeasuredTotalActivePoweratTargetPoint() {
		return MeasuredTotalActivePoweratTargetPoint;
	}
	public void setMeasuredTotalActivePoweratTargetPoint(double measuredTotalActivePoweratTargetPoint) {
		MeasuredTotalActivePoweratTargetPoint = measuredTotalActivePoweratTargetPoint;
	}
	public double getMeasuredTotalReactivePoweratTargetPoint() {
		return MeasuredTotalReactivePoweratTargetPoint;
	}
	public void setMeasuredTotalReactivePoweratTargetPoint(double measuredTotalReactivePoweratTargetPoint) {
		MeasuredTotalReactivePoweratTargetPoint = measuredTotalReactivePoweratTargetPoint;
	}
	public double getMeasuredPowerFactoratTargetPoint() {
		return MeasuredPowerFactoratTargetPoint;
	}
	public void setMeasuredPowerFactoratTargetPoint(double measuredPowerFactoratTargetPoint) {
		MeasuredPowerFactoratTargetPoint = measuredPowerFactoratTargetPoint;
	}
	public double getTotalActivePowerofINVs() {
		return TotalActivePowerofINVs;
	}
	public void setTotalActivePowerofINVs(double totalActivePowerofINVs) {
		TotalActivePowerofINVs = totalActivePowerofINVs;
	}
	public double getTotalReactivePowerofINVs() {
		return TotalReactivePowerofINVs;
	}
	public void setTotalReactivePowerofINVs(double totalReactivePowerofINVs) {
		TotalReactivePowerofINVs = totalReactivePowerofINVs;
	}
	public double getOfflineINVCount() {
		return OfflineINVCount;
	}
	public void setOfflineINVCount(double offlineINVCount) {
		OfflineINVCount = offlineINVCount;
	}
	public double getOnlineINVCount() {
		return OnlineINVCount;
	}
	public void setOnlineINVCount(double onlineINVCount) {
		OnlineINVCount = onlineINVCount;
	}
	public double getTotalConnectedDevices() {
		return TotalConnectedDevices;
	}
	public void setTotalConnectedDevices(double totalConnectedDevices) {
		TotalConnectedDevices = totalConnectedDevices;
	}
	public double getRatedActivePowerofArray() {
		return RatedActivePowerofArray;
	}
	public void setRatedActivePowerofArray(double ratedActivePowerofArray) {
		RatedActivePowerofArray = ratedActivePowerofArray;
	}
	public double getRatedReactivePowerofArray() {
		return RatedReactivePowerofArray;
	}
	public void setRatedReactivePowerofArray(double ratedReactivePowerofArray) {
		RatedReactivePowerofArray = ratedReactivePowerofArray;
	}
	public double getDailyGenerationofINVs() {
		return DailyGenerationofINVs;
	}
	public void setDailyGenerationofINVs(double dailyGenerationofINVs) {
		DailyGenerationofINVs = dailyGenerationofINVs;
	}
	public double getMonthlyGenerationofINVs() {
		return MonthlyGenerationofINVs;
	}
	public void setMonthlyGenerationofINVs(double monthlyGenerationofINVs) {
		MonthlyGenerationofINVs = monthlyGenerationofINVs;
	}
	public double getAnnualGenerationofINVs() {
		return AnnualGenerationofINVs;
	}
	public void setAnnualGenerationofINVs(double annualGenerationofINVs) {
		AnnualGenerationofINVs = annualGenerationofINVs;
	}
	public double getTotalGenerationofINVs() {
		return TotalGenerationofINVs;
	}
	public void setTotalGenerationofINVs(double totalGenerationofINVs) {
		TotalGenerationofINVs = totalGenerationofINVs;
	}
	public double getMaxDailyOperatingTimeofINVs() {
		return MaxDailyOperatingTimeofINVs;
	}
	public void setMaxDailyOperatingTimeofINVs(double maxDailyOperatingTimeofINVs) {
		MaxDailyOperatingTimeofINVs = maxDailyOperatingTimeofINVs;
	}
	public double getINVStatusXinjiang() {
		return INVStatusXinjiang;
	}
	public void setINVStatusXinjiang(double iNVStatusXinjiang) {
		INVStatusXinjiang = iNVStatusXinjiang;
	}
	public double getINVStatusNingxia() {
		return INVStatusNingxia;
	}
	public void setINVStatusNingxia(double iNVStatusNingxia) {
		INVStatusNingxia = iNVStatusNingxia;
	}
	public double getINVStatusShaanxi() {
		return INVStatusShaanxi;
	}
	public void setINVStatusShaanxi(double iNVStatusShaanxi) {
		INVStatusShaanxi = iNVStatusShaanxi;
	}
	public double getAdjustableActiveLowerLimit() {
		return AdjustableActiveLowerLimit;
	}
	public void setAdjustableActiveLowerLimit(double adjustableActiveLowerLimit) {
		AdjustableActiveLowerLimit = adjustableActiveLowerLimit;
	}
	public double getAdjustableReactiveLowerLimit() {
		return AdjustableReactiveLowerLimit;
	}
	public void setAdjustableReactiveLowerLimit(double adjustableReactiveLowerLimit) {
		AdjustableReactiveLowerLimit = adjustableReactiveLowerLimit;
	}
	public double getActiveRegulationDeadband() {
		return ActiveRegulationDeadband;
	}
	public void setActiveRegulationDeadband(double activeRegulationDeadband) {
		ActiveRegulationDeadband = activeRegulationDeadband;
	}
	public double getReactiveRegulationDeadband() {
		return ReactiveRegulationDeadband;
	}
	public void setReactiveRegulationDeadband(double reactiveRegulationDeadband) {
		ReactiveRegulationDeadband = reactiveRegulationDeadband;
	}
	public double getActivePowerModeFeedbackPanorama() {
		return ActivePowerModeFeedbackPanorama;
	}
	public void setActivePowerModeFeedbackPanorama(double activePowerModeFeedbackPanorama) {
		ActivePowerModeFeedbackPanorama = activePowerModeFeedbackPanorama;
	}
	public double getReactivePowerModeFeedbackPanorama() {
		return ReactivePowerModeFeedbackPanorama;
	}
	public void setReactivePowerModeFeedbackPanorama(double reactivePowerModeFeedbackPanorama) {
		ReactivePowerModeFeedbackPanorama = reactivePowerModeFeedbackPanorama;
	}
	public double getActivePowerPercentageFeedbackPanorama() {
		return ActivePowerPercentageFeedbackPanorama;
	}
	public void setActivePowerPercentageFeedbackPanorama(double activePowerPercentageFeedbackPanorama) {
		ActivePowerPercentageFeedbackPanorama = activePowerPercentageFeedbackPanorama;
	}
	public double getReactivePowerPercentageFeedbackPanorama() {
		return ReactivePowerPercentageFeedbackPanorama;
	}
	public void setReactivePowerPercentageFeedbackPanorama(double reactivePowerPercentageFeedbackPanorama) {
		ReactivePowerPercentageFeedbackPanorama = reactivePowerPercentageFeedbackPanorama;
	}
	public double getPowerFactorSettingFeedbackPanorama() {
		return PowerFactorSettingFeedbackPanorama;
	}
	public void setPowerFactorSettingFeedbackPanorama(double powerFactorSettingFeedbackPanorama) {
		PowerFactorSettingFeedbackPanorama = powerFactorSettingFeedbackPanorama;
	}
	public double getReceivedPowerControlTimeoutseconds() {
		return ReceivedPowerControlTimeoutseconds;
	}
	public void setReceivedPowerControlTimeoutseconds(double receivedPowerControlTimeoutseconds) {
		ReceivedPowerControlTimeoutseconds = receivedPowerControlTimeoutseconds;
	}
	public double getSpareTelemetry41() {
		return SpareTelemetry41;
	}
	public void setSpareTelemetry41(double spareTelemetry41) {
		SpareTelemetry41 = spareTelemetry41;
	}
	public double getSpareTelemetry42() {
		return SpareTelemetry42;
	}
	public void setSpareTelemetry42(double spareTelemetry42) {
		SpareTelemetry42 = spareTelemetry42;
	}
	public double getSpareTelemetry43() {
		return SpareTelemetry43;
	}
	public void setSpareTelemetry43(double spareTelemetry43) {
		SpareTelemetry43 = spareTelemetry43;
	}
	public double getSpareTelemetry44() {
		return SpareTelemetry44;
	}
	public void setSpareTelemetry44(double spareTelemetry44) {
		SpareTelemetry44 = spareTelemetry44;
	}
	public double getSpareTelemetry45() {
		return SpareTelemetry45;
	}
	public void setSpareTelemetry45(double spareTelemetry45) {
		SpareTelemetry45 = spareTelemetry45;
	}
	public double getSpareTelemetry46() {
		return SpareTelemetry46;
	}
	public void setSpareTelemetry46(double spareTelemetry46) {
		SpareTelemetry46 = spareTelemetry46;
	}
	public double getSpareTelemetry47() {
		return SpareTelemetry47;
	}
	public void setSpareTelemetry47(double spareTelemetry47) {
		SpareTelemetry47 = spareTelemetry47;
	}
	public double getSpareTelemetry48() {
		return SpareTelemetry48;
	}
	public void setSpareTelemetry48(double spareTelemetry48) {
		SpareTelemetry48 = spareTelemetry48;
	}
	public double getSpareTelemetry49() {
		return SpareTelemetry49;
	}
	public void setSpareTelemetry49(double spareTelemetry49) {
		SpareTelemetry49 = spareTelemetry49;
	}
	public double getCom11INVActivePowerModeFeedback() {
		return Com11INVActivePowerModeFeedback;
	}
	public void setCom11INVActivePowerModeFeedback(double com11invActivePowerModeFeedback) {
		Com11INVActivePowerModeFeedback = com11invActivePowerModeFeedback;
	}
	public double getCom11INVReactivePowerModeFeedback() {
		return Com11INVReactivePowerModeFeedback;
	}
	public void setCom11INVReactivePowerModeFeedback(double com11invReactivePowerModeFeedback) {
		Com11INVReactivePowerModeFeedback = com11invReactivePowerModeFeedback;
	}
	public double getCom11INVActivePowerPercentageFeedback() {
		return Com11INVActivePowerPercentageFeedback;
	}
	public void setCom11INVActivePowerPercentageFeedback(double com11invActivePowerPercentageFeedback) {
		Com11INVActivePowerPercentageFeedback = com11invActivePowerPercentageFeedback;
	}
	public double getCom11INVReactivePowerPercentageFeedback() {
		return Com11INVReactivePowerPercentageFeedback;
	}
	public void setCom11INVReactivePowerPercentageFeedback(double com11invReactivePowerPercentageFeedback) {
		Com11INVReactivePowerPercentageFeedback = com11invReactivePowerPercentageFeedback;
	}
	public double getCom11INVPowerFactorSettingFeedback() {
		return Com11INVPowerFactorSettingFeedback;
	}
	public void setCom11INVPowerFactorSettingFeedback(double com11invPowerFactorSettingFeedback) {
		Com11INVPowerFactorSettingFeedback = com11invPowerFactorSettingFeedback;
	}
	public double getCom12INVActivePowerModeFeedback() {
		return Com12INVActivePowerModeFeedback;
	}
	public void setCom12INVActivePowerModeFeedback(double com12invActivePowerModeFeedback) {
		Com12INVActivePowerModeFeedback = com12invActivePowerModeFeedback;
	}
	public double getCom12INVReactivePowerModeFeedback() {
		return Com12INVReactivePowerModeFeedback;
	}
	public void setCom12INVReactivePowerModeFeedback(double com12invReactivePowerModeFeedback) {
		Com12INVReactivePowerModeFeedback = com12invReactivePowerModeFeedback;
	}
	public double getCom12INVActivePowerPercentageFeedback() {
		return Com12INVActivePowerPercentageFeedback;
	}
	public void setCom12INVActivePowerPercentageFeedback(double com12invActivePowerPercentageFeedback) {
		Com12INVActivePowerPercentageFeedback = com12invActivePowerPercentageFeedback;
	}
	public double getCom12INVReactivePowerPercentageFeedback() {
		return Com12INVReactivePowerPercentageFeedback;
	}
	public void setCom12INVReactivePowerPercentageFeedback(double com12invReactivePowerPercentageFeedback) {
		Com12INVReactivePowerPercentageFeedback = com12invReactivePowerPercentageFeedback;
	}
	public double getCom12INVPowerFactorSettingFeedback() {
		return Com12INVPowerFactorSettingFeedback;
	}
	public void setCom12INVPowerFactorSettingFeedback(double com12invPowerFactorSettingFeedback) {
		Com12INVPowerFactorSettingFeedback = com12invPowerFactorSettingFeedback;
	}
	public double getCom13INVActivePowerModeFeedback() {
		return Com13INVActivePowerModeFeedback;
	}
	public void setCom13INVActivePowerModeFeedback(double com13invActivePowerModeFeedback) {
		Com13INVActivePowerModeFeedback = com13invActivePowerModeFeedback;
	}
	public double getCom13INVReactivePowerModeFeedback() {
		return Com13INVReactivePowerModeFeedback;
	}
	public void setCom13INVReactivePowerModeFeedback(double com13invReactivePowerModeFeedback) {
		Com13INVReactivePowerModeFeedback = com13invReactivePowerModeFeedback;
	}
	public double getCom13INVActivePowerPercentageFeedback() {
		return Com13INVActivePowerPercentageFeedback;
	}
	public void setCom13INVActivePowerPercentageFeedback(double com13invActivePowerPercentageFeedback) {
		Com13INVActivePowerPercentageFeedback = com13invActivePowerPercentageFeedback;
	}
	public double getCom13INVReactivePowerPercentageFeedback() {
		return Com13INVReactivePowerPercentageFeedback;
	}
	public void setCom13INVReactivePowerPercentageFeedback(double com13invReactivePowerPercentageFeedback) {
		Com13INVReactivePowerPercentageFeedback = com13invReactivePowerPercentageFeedback;
	}
	public double getCom13INVPowerFactorSettingFeedback() {
		return Com13INVPowerFactorSettingFeedback;
	}
	public void setCom13INVPowerFactorSettingFeedback(double com13invPowerFactorSettingFeedback) {
		Com13INVPowerFactorSettingFeedback = com13invPowerFactorSettingFeedback;
	}
	public double getCom14INVActivePowerModeFeedback() {
		return Com14INVActivePowerModeFeedback;
	}
	public void setCom14INVActivePowerModeFeedback(double com14invActivePowerModeFeedback) {
		Com14INVActivePowerModeFeedback = com14invActivePowerModeFeedback;
	}
	public double getCom14INVReactivePowerModeFeedback() {
		return Com14INVReactivePowerModeFeedback;
	}
	public void setCom14INVReactivePowerModeFeedback(double com14invReactivePowerModeFeedback) {
		Com14INVReactivePowerModeFeedback = com14invReactivePowerModeFeedback;
	}
	public double getCom14INVActivePowerPercentageFeedback() {
		return Com14INVActivePowerPercentageFeedback;
	}
	public void setCom14INVActivePowerPercentageFeedback(double com14invActivePowerPercentageFeedback) {
		Com14INVActivePowerPercentageFeedback = com14invActivePowerPercentageFeedback;
	}
	public double getCom14INVReactivePowerPercentageFeedback() {
		return Com14INVReactivePowerPercentageFeedback;
	}
	public void setCom14INVReactivePowerPercentageFeedback(double com14invReactivePowerPercentageFeedback) {
		Com14INVReactivePowerPercentageFeedback = com14invReactivePowerPercentageFeedback;
	}
	public double getCom14INVPowerFactorSettingFeedback() {
		return Com14INVPowerFactorSettingFeedback;
	}
	public void setCom14INVPowerFactorSettingFeedback(double com14invPowerFactorSettingFeedback) {
		Com14INVPowerFactorSettingFeedback = com14invPowerFactorSettingFeedback;
	}
	public double getCom15INVActivePowerModeFeedback() {
		return Com15INVActivePowerModeFeedback;
	}
	public void setCom15INVActivePowerModeFeedback(double com15invActivePowerModeFeedback) {
		Com15INVActivePowerModeFeedback = com15invActivePowerModeFeedback;
	}
	public double getCom15INVReactivePowerModeFeedback() {
		return Com15INVReactivePowerModeFeedback;
	}
	public void setCom15INVReactivePowerModeFeedback(double com15invReactivePowerModeFeedback) {
		Com15INVReactivePowerModeFeedback = com15invReactivePowerModeFeedback;
	}
	public double getCom15INVActivePowerPercentageFeedback() {
		return Com15INVActivePowerPercentageFeedback;
	}
	public void setCom15INVActivePowerPercentageFeedback(double com15invActivePowerPercentageFeedback) {
		Com15INVActivePowerPercentageFeedback = com15invActivePowerPercentageFeedback;
	}
	public double getCom15INVReactivePowerPercentageFeedback() {
		return Com15INVReactivePowerPercentageFeedback;
	}
	public void setCom15INVReactivePowerPercentageFeedback(double com15invReactivePowerPercentageFeedback) {
		Com15INVReactivePowerPercentageFeedback = com15invReactivePowerPercentageFeedback;
	}
	public double getCom15INVPowerFactorSettingFeedback() {
		return Com15INVPowerFactorSettingFeedback;
	}
	public void setCom15INVPowerFactorSettingFeedback(double com15invPowerFactorSettingFeedback) {
		Com15INVPowerFactorSettingFeedback = com15invPowerFactorSettingFeedback;
	}
	public double getCom16INVActivePowerModeFeedback() {
		return Com16INVActivePowerModeFeedback;
	}
	public void setCom16INVActivePowerModeFeedback(double com16invActivePowerModeFeedback) {
		Com16INVActivePowerModeFeedback = com16invActivePowerModeFeedback;
	}
	public double getCom16INVReactivePowerModeFeedback() {
		return Com16INVReactivePowerModeFeedback;
	}
	public void setCom16INVReactivePowerModeFeedback(double com16invReactivePowerModeFeedback) {
		Com16INVReactivePowerModeFeedback = com16invReactivePowerModeFeedback;
	}
	public double getCom16INVActivePowerPercentageFeedback() {
		return Com16INVActivePowerPercentageFeedback;
	}
	public void setCom16INVActivePowerPercentageFeedback(double com16invActivePowerPercentageFeedback) {
		Com16INVActivePowerPercentageFeedback = com16invActivePowerPercentageFeedback;
	}
	public double getCom16INVReactivePowerPercentageFeedback() {
		return Com16INVReactivePowerPercentageFeedback;
	}
	public void setCom16INVReactivePowerPercentageFeedback(double com16invReactivePowerPercentageFeedback) {
		Com16INVReactivePowerPercentageFeedback = com16invReactivePowerPercentageFeedback;
	}
	public double getCom16INVPowerFactorSettingFeedback() {
		return Com16INVPowerFactorSettingFeedback;
	}
	public void setCom16INVPowerFactorSettingFeedback(double com16invPowerFactorSettingFeedback) {
		Com16INVPowerFactorSettingFeedback = com16invPowerFactorSettingFeedback;
	}
	public double getCom17INVActivePowerModeFeedback() {
		return Com17INVActivePowerModeFeedback;
	}
	public void setCom17INVActivePowerModeFeedback(double com17invActivePowerModeFeedback) {
		Com17INVActivePowerModeFeedback = com17invActivePowerModeFeedback;
	}
	public double getCom17INVReactivePowerModeFeedback() {
		return Com17INVReactivePowerModeFeedback;
	}
	public void setCom17INVReactivePowerModeFeedback(double com17invReactivePowerModeFeedback) {
		Com17INVReactivePowerModeFeedback = com17invReactivePowerModeFeedback;
	}
	public double getCom17INVActivePowerPercentageFeedback() {
		return Com17INVActivePowerPercentageFeedback;
	}
	public void setCom17INVActivePowerPercentageFeedback(double com17invActivePowerPercentageFeedback) {
		Com17INVActivePowerPercentageFeedback = com17invActivePowerPercentageFeedback;
	}
	public double getCom17INVReactivePowerPercentageFeedback() {
		return Com17INVReactivePowerPercentageFeedback;
	}
	public void setCom17INVReactivePowerPercentageFeedback(double com17invReactivePowerPercentageFeedback) {
		Com17INVReactivePowerPercentageFeedback = com17invReactivePowerPercentageFeedback;
	}
	public double getCom17INVPowerFactorSettingFeedback() {
		return Com17INVPowerFactorSettingFeedback;
	}
	public void setCom17INVPowerFactorSettingFeedback(double com17invPowerFactorSettingFeedback) {
		Com17INVPowerFactorSettingFeedback = com17invPowerFactorSettingFeedback;
	}
	public double getCom18INVActivePowerModeFeedback() {
		return Com18INVActivePowerModeFeedback;
	}
	public void setCom18INVActivePowerModeFeedback(double com18invActivePowerModeFeedback) {
		Com18INVActivePowerModeFeedback = com18invActivePowerModeFeedback;
	}
	public double getCom18INVReactivePowerModeFeedback() {
		return Com18INVReactivePowerModeFeedback;
	}
	public void setCom18INVReactivePowerModeFeedback(double com18invReactivePowerModeFeedback) {
		Com18INVReactivePowerModeFeedback = com18invReactivePowerModeFeedback;
	}
	public double getCom18INVActivePowerPercentageFeedback() {
		return Com18INVActivePowerPercentageFeedback;
	}
	public void setCom18INVActivePowerPercentageFeedback(double com18invActivePowerPercentageFeedback) {
		Com18INVActivePowerPercentageFeedback = com18invActivePowerPercentageFeedback;
	}
	public double getCom18INVReactivePowerPercentageFeedback() {
		return Com18INVReactivePowerPercentageFeedback;
	}
	public void setCom18INVReactivePowerPercentageFeedback(double com18invReactivePowerPercentageFeedback) {
		Com18INVReactivePowerPercentageFeedback = com18invReactivePowerPercentageFeedback;
	}
	public double getCom18INVPowerFactorSettingFeedback() {
		return Com18INVPowerFactorSettingFeedback;
	}
	public void setCom18INVPowerFactorSettingFeedback(double com18invPowerFactorSettingFeedback) {
		Com18INVPowerFactorSettingFeedback = com18invPowerFactorSettingFeedback;
	}
	public double getCom19INVActivePowerModeFeedback() {
		return Com19INVActivePowerModeFeedback;
	}
	public void setCom19INVActivePowerModeFeedback(double com19invActivePowerModeFeedback) {
		Com19INVActivePowerModeFeedback = com19invActivePowerModeFeedback;
	}
	public double getCom19INVReactivePowerModeFeedback() {
		return Com19INVReactivePowerModeFeedback;
	}
	public void setCom19INVReactivePowerModeFeedback(double com19invReactivePowerModeFeedback) {
		Com19INVReactivePowerModeFeedback = com19invReactivePowerModeFeedback;
	}
	public double getCom19INVActivePowerPercentageFeedback() {
		return Com19INVActivePowerPercentageFeedback;
	}
	public void setCom19INVActivePowerPercentageFeedback(double com19invActivePowerPercentageFeedback) {
		Com19INVActivePowerPercentageFeedback = com19invActivePowerPercentageFeedback;
	}
	public double getCom19INVReactivePowerPercentageFeedback() {
		return Com19INVReactivePowerPercentageFeedback;
	}
	public void setCom19INVReactivePowerPercentageFeedback(double com19invReactivePowerPercentageFeedback) {
		Com19INVReactivePowerPercentageFeedback = com19invReactivePowerPercentageFeedback;
	}
	public double getCom19INVPowerFactorSettingFeedback() {
		return Com19INVPowerFactorSettingFeedback;
	}
	public void setCom19INVPowerFactorSettingFeedback(double com19invPowerFactorSettingFeedback) {
		Com19INVPowerFactorSettingFeedback = com19invPowerFactorSettingFeedback;
	}
	public double getCom110INVActivePowerModeFeedback() {
		return Com110INVActivePowerModeFeedback;
	}
	public void setCom110INVActivePowerModeFeedback(double com110invActivePowerModeFeedback) {
		Com110INVActivePowerModeFeedback = com110invActivePowerModeFeedback;
	}
	public double getCom110INVReactivePowerModeFeedback() {
		return Com110INVReactivePowerModeFeedback;
	}
	public void setCom110INVReactivePowerModeFeedback(double com110invReactivePowerModeFeedback) {
		Com110INVReactivePowerModeFeedback = com110invReactivePowerModeFeedback;
	}
	public double getCom110INVActivePowerPercentageFeedback() {
		return Com110INVActivePowerPercentageFeedback;
	}
	public void setCom110INVActivePowerPercentageFeedback(double com110invActivePowerPercentageFeedback) {
		Com110INVActivePowerPercentageFeedback = com110invActivePowerPercentageFeedback;
	}
	public double getCom110INVReactivePowerPercentageFeedback() {
		return Com110INVReactivePowerPercentageFeedback;
	}
	public void setCom110INVReactivePowerPercentageFeedback(double com110invReactivePowerPercentageFeedback) {
		Com110INVReactivePowerPercentageFeedback = com110invReactivePowerPercentageFeedback;
	}
	public double getCom110INVPowerFactorSettingFeedback() {
		return Com110INVPowerFactorSettingFeedback;
	}
	public void setCom110INVPowerFactorSettingFeedback(double com110invPowerFactorSettingFeedback) {
		Com110INVPowerFactorSettingFeedback = com110invPowerFactorSettingFeedback;
	}
	public double getCom111INVActivePowerModeFeedback() {
		return Com111INVActivePowerModeFeedback;
	}
	public void setCom111INVActivePowerModeFeedback(double com111invActivePowerModeFeedback) {
		Com111INVActivePowerModeFeedback = com111invActivePowerModeFeedback;
	}
	public double getCom111INVReactivePowerModeFeedback() {
		return Com111INVReactivePowerModeFeedback;
	}
	public void setCom111INVReactivePowerModeFeedback(double com111invReactivePowerModeFeedback) {
		Com111INVReactivePowerModeFeedback = com111invReactivePowerModeFeedback;
	}
	public double getCom111INVActivePowerPercentageFeedback() {
		return Com111INVActivePowerPercentageFeedback;
	}
	public void setCom111INVActivePowerPercentageFeedback(double com111invActivePowerPercentageFeedback) {
		Com111INVActivePowerPercentageFeedback = com111invActivePowerPercentageFeedback;
	}
	public double getCom111INVReactivePowerPercentageFeedback() {
		return Com111INVReactivePowerPercentageFeedback;
	}
	public void setCom111INVReactivePowerPercentageFeedback(double com111invReactivePowerPercentageFeedback) {
		Com111INVReactivePowerPercentageFeedback = com111invReactivePowerPercentageFeedback;
	}
	public double getCom111INVPowerFactorSettingFeedback() {
		return Com111INVPowerFactorSettingFeedback;
	}
	public void setCom111INVPowerFactorSettingFeedback(double com111invPowerFactorSettingFeedback) {
		Com111INVPowerFactorSettingFeedback = com111invPowerFactorSettingFeedback;
	}
	public double getCom112INVActivePowerModeFeedback() {
		return Com112INVActivePowerModeFeedback;
	}
	public void setCom112INVActivePowerModeFeedback(double com112invActivePowerModeFeedback) {
		Com112INVActivePowerModeFeedback = com112invActivePowerModeFeedback;
	}
	public double getCom112INVReactivePowerModeFeedback() {
		return Com112INVReactivePowerModeFeedback;
	}
	public void setCom112INVReactivePowerModeFeedback(double com112invReactivePowerModeFeedback) {
		Com112INVReactivePowerModeFeedback = com112invReactivePowerModeFeedback;
	}
	public double getCom112INVActivePowerPercentageFeedback() {
		return Com112INVActivePowerPercentageFeedback;
	}
	public void setCom112INVActivePowerPercentageFeedback(double com112invActivePowerPercentageFeedback) {
		Com112INVActivePowerPercentageFeedback = com112invActivePowerPercentageFeedback;
	}
	public double getCom112INVReactivePowerPercentageFeedback() {
		return Com112INVReactivePowerPercentageFeedback;
	}
	public void setCom112INVReactivePowerPercentageFeedback(double com112invReactivePowerPercentageFeedback) {
		Com112INVReactivePowerPercentageFeedback = com112invReactivePowerPercentageFeedback;
	}
	public double getCom112INVPowerFactorSettingFeedback() {
		return Com112INVPowerFactorSettingFeedback;
	}
	public void setCom112INVPowerFactorSettingFeedback(double com112invPowerFactorSettingFeedback) {
		Com112INVPowerFactorSettingFeedback = com112invPowerFactorSettingFeedback;
	}
	public double getCom113INVActivePowerModeFeedback() {
		return Com113INVActivePowerModeFeedback;
	}
	public void setCom113INVActivePowerModeFeedback(double com113invActivePowerModeFeedback) {
		Com113INVActivePowerModeFeedback = com113invActivePowerModeFeedback;
	}
	public double getCom113INVReactivePowerModeFeedback() {
		return Com113INVReactivePowerModeFeedback;
	}
	public void setCom113INVReactivePowerModeFeedback(double com113invReactivePowerModeFeedback) {
		Com113INVReactivePowerModeFeedback = com113invReactivePowerModeFeedback;
	}
	public double getCom113INVActivePowerPercentageFeedback() {
		return Com113INVActivePowerPercentageFeedback;
	}
	public void setCom113INVActivePowerPercentageFeedback(double com113invActivePowerPercentageFeedback) {
		Com113INVActivePowerPercentageFeedback = com113invActivePowerPercentageFeedback;
	}
	public double getCom113INVReactivePowerPercentageFeedback() {
		return Com113INVReactivePowerPercentageFeedback;
	}
	public void setCom113INVReactivePowerPercentageFeedback(double com113invReactivePowerPercentageFeedback) {
		Com113INVReactivePowerPercentageFeedback = com113invReactivePowerPercentageFeedback;
	}
	public double getCom113INVPowerFactorSettingFeedback() {
		return Com113INVPowerFactorSettingFeedback;
	}
	public void setCom113INVPowerFactorSettingFeedback(double com113invPowerFactorSettingFeedback) {
		Com113INVPowerFactorSettingFeedback = com113invPowerFactorSettingFeedback;
	}
	public double getCom114INVActivePowerModeFeedback() {
		return Com114INVActivePowerModeFeedback;
	}
	public void setCom114INVActivePowerModeFeedback(double com114invActivePowerModeFeedback) {
		Com114INVActivePowerModeFeedback = com114invActivePowerModeFeedback;
	}
	public double getCom114INVReactivePowerModeFeedback() {
		return Com114INVReactivePowerModeFeedback;
	}
	public void setCom114INVReactivePowerModeFeedback(double com114invReactivePowerModeFeedback) {
		Com114INVReactivePowerModeFeedback = com114invReactivePowerModeFeedback;
	}
	public double getCom114INVActivePowerPercentageFeedback() {
		return Com114INVActivePowerPercentageFeedback;
	}
	public void setCom114INVActivePowerPercentageFeedback(double com114invActivePowerPercentageFeedback) {
		Com114INVActivePowerPercentageFeedback = com114invActivePowerPercentageFeedback;
	}
	public double getCom114INVReactivePowerPercentageFeedback() {
		return Com114INVReactivePowerPercentageFeedback;
	}
	public void setCom114INVReactivePowerPercentageFeedback(double com114invReactivePowerPercentageFeedback) {
		Com114INVReactivePowerPercentageFeedback = com114invReactivePowerPercentageFeedback;
	}
	public double getCom114INVPowerFactorSettingFeedback() {
		return Com114INVPowerFactorSettingFeedback;
	}
	public void setCom114INVPowerFactorSettingFeedback(double com114invPowerFactorSettingFeedback) {
		Com114INVPowerFactorSettingFeedback = com114invPowerFactorSettingFeedback;
	}
	public double getCom115INVActivePowerModeFeedback() {
		return Com115INVActivePowerModeFeedback;
	}
	public void setCom115INVActivePowerModeFeedback(double com115invActivePowerModeFeedback) {
		Com115INVActivePowerModeFeedback = com115invActivePowerModeFeedback;
	}
	public double getCom115INVReactivePowerModeFeedback() {
		return Com115INVReactivePowerModeFeedback;
	}
	public void setCom115INVReactivePowerModeFeedback(double com115invReactivePowerModeFeedback) {
		Com115INVReactivePowerModeFeedback = com115invReactivePowerModeFeedback;
	}
	public double getCom115INVActivePowerPercentageFeedback() {
		return Com115INVActivePowerPercentageFeedback;
	}
	public void setCom115INVActivePowerPercentageFeedback(double com115invActivePowerPercentageFeedback) {
		Com115INVActivePowerPercentageFeedback = com115invActivePowerPercentageFeedback;
	}
	public double getCom115INVReactivePowerPercentageFeedback() {
		return Com115INVReactivePowerPercentageFeedback;
	}
	public void setCom115INVReactivePowerPercentageFeedback(double com115invReactivePowerPercentageFeedback) {
		Com115INVReactivePowerPercentageFeedback = com115invReactivePowerPercentageFeedback;
	}
	public double getCom115INVPowerFactorSettingFeedback() {
		return Com115INVPowerFactorSettingFeedback;
	}
	public void setCom115INVPowerFactorSettingFeedback(double com115invPowerFactorSettingFeedback) {
		Com115INVPowerFactorSettingFeedback = com115invPowerFactorSettingFeedback;
	}
	public double getCom21INVActivePowerModeFeedback() {
		return Com21INVActivePowerModeFeedback;
	}
	public void setCom21INVActivePowerModeFeedback(double com21invActivePowerModeFeedback) {
		Com21INVActivePowerModeFeedback = com21invActivePowerModeFeedback;
	}
	public double getCom21INVReactivePowerModeFeedback() {
		return Com21INVReactivePowerModeFeedback;
	}
	public void setCom21INVReactivePowerModeFeedback(double com21invReactivePowerModeFeedback) {
		Com21INVReactivePowerModeFeedback = com21invReactivePowerModeFeedback;
	}
	public double getCom21INVActivePowerPercentageFeedback() {
		return Com21INVActivePowerPercentageFeedback;
	}
	public void setCom21INVActivePowerPercentageFeedback(double com21invActivePowerPercentageFeedback) {
		Com21INVActivePowerPercentageFeedback = com21invActivePowerPercentageFeedback;
	}
	public double getCom21INVReactivePowerPercentageFeedback() {
		return Com21INVReactivePowerPercentageFeedback;
	}
	public void setCom21INVReactivePowerPercentageFeedback(double com21invReactivePowerPercentageFeedback) {
		Com21INVReactivePowerPercentageFeedback = com21invReactivePowerPercentageFeedback;
	}
	public double getCom21INVPowerFactorSettingFeedback() {
		return Com21INVPowerFactorSettingFeedback;
	}
	public void setCom21INVPowerFactorSettingFeedback(double com21invPowerFactorSettingFeedback) {
		Com21INVPowerFactorSettingFeedback = com21invPowerFactorSettingFeedback;
	}
	public double getCom22INVActivePowerModeFeedback() {
		return Com22INVActivePowerModeFeedback;
	}
	public void setCom22INVActivePowerModeFeedback(double com22invActivePowerModeFeedback) {
		Com22INVActivePowerModeFeedback = com22invActivePowerModeFeedback;
	}
	public double getCom22INVReactivePowerModeFeedback() {
		return Com22INVReactivePowerModeFeedback;
	}
	public void setCom22INVReactivePowerModeFeedback(double com22invReactivePowerModeFeedback) {
		Com22INVReactivePowerModeFeedback = com22invReactivePowerModeFeedback;
	}
	public double getCom22INVActivePowerPercentageFeedback() {
		return Com22INVActivePowerPercentageFeedback;
	}
	public void setCom22INVActivePowerPercentageFeedback(double com22invActivePowerPercentageFeedback) {
		Com22INVActivePowerPercentageFeedback = com22invActivePowerPercentageFeedback;
	}
	public double getCom22INVReactivePowerPercentageFeedback() {
		return Com22INVReactivePowerPercentageFeedback;
	}
	public void setCom22INVReactivePowerPercentageFeedback(double com22invReactivePowerPercentageFeedback) {
		Com22INVReactivePowerPercentageFeedback = com22invReactivePowerPercentageFeedback;
	}
	public double getCom22INVPowerFactorSettingFeedback() {
		return Com22INVPowerFactorSettingFeedback;
	}
	public void setCom22INVPowerFactorSettingFeedback(double com22invPowerFactorSettingFeedback) {
		Com22INVPowerFactorSettingFeedback = com22invPowerFactorSettingFeedback;
	}
	public double getCom23INVActivePowerModeFeedback() {
		return Com23INVActivePowerModeFeedback;
	}
	public void setCom23INVActivePowerModeFeedback(double com23invActivePowerModeFeedback) {
		Com23INVActivePowerModeFeedback = com23invActivePowerModeFeedback;
	}
	public double getCom23INVReactivePowerModeFeedback() {
		return Com23INVReactivePowerModeFeedback;
	}
	public void setCom23INVReactivePowerModeFeedback(double com23invReactivePowerModeFeedback) {
		Com23INVReactivePowerModeFeedback = com23invReactivePowerModeFeedback;
	}
	public double getCom23INVActivePowerPercentageFeedback() {
		return Com23INVActivePowerPercentageFeedback;
	}
	public void setCom23INVActivePowerPercentageFeedback(double com23invActivePowerPercentageFeedback) {
		Com23INVActivePowerPercentageFeedback = com23invActivePowerPercentageFeedback;
	}
	public double getCom23INVReactivePowerPercentageFeedback() {
		return Com23INVReactivePowerPercentageFeedback;
	}
	public void setCom23INVReactivePowerPercentageFeedback(double com23invReactivePowerPercentageFeedback) {
		Com23INVReactivePowerPercentageFeedback = com23invReactivePowerPercentageFeedback;
	}
	public double getCom23INVPowerFactorSettingFeedback() {
		return Com23INVPowerFactorSettingFeedback;
	}
	public void setCom23INVPowerFactorSettingFeedback(double com23invPowerFactorSettingFeedback) {
		Com23INVPowerFactorSettingFeedback = com23invPowerFactorSettingFeedback;
	}
	public double getCom24INVActivePowerModeFeedback() {
		return Com24INVActivePowerModeFeedback;
	}
	public void setCom24INVActivePowerModeFeedback(double com24invActivePowerModeFeedback) {
		Com24INVActivePowerModeFeedback = com24invActivePowerModeFeedback;
	}
	public double getCom24INVReactivePowerModeFeedback() {
		return Com24INVReactivePowerModeFeedback;
	}
	public void setCom24INVReactivePowerModeFeedback(double com24invReactivePowerModeFeedback) {
		Com24INVReactivePowerModeFeedback = com24invReactivePowerModeFeedback;
	}
	public double getCom24INVActivePowerPercentageFeedback() {
		return Com24INVActivePowerPercentageFeedback;
	}
	public void setCom24INVActivePowerPercentageFeedback(double com24invActivePowerPercentageFeedback) {
		Com24INVActivePowerPercentageFeedback = com24invActivePowerPercentageFeedback;
	}
	public double getCom24INVReactivePowerPercentageFeedback() {
		return Com24INVReactivePowerPercentageFeedback;
	}
	public void setCom24INVReactivePowerPercentageFeedback(double com24invReactivePowerPercentageFeedback) {
		Com24INVReactivePowerPercentageFeedback = com24invReactivePowerPercentageFeedback;
	}
	public double getCom24INVPowerFactorSettingFeedback() {
		return Com24INVPowerFactorSettingFeedback;
	}
	public void setCom24INVPowerFactorSettingFeedback(double com24invPowerFactorSettingFeedback) {
		Com24INVPowerFactorSettingFeedback = com24invPowerFactorSettingFeedback;
	}
	public double getCom25INVActivePowerModeFeedback() {
		return Com25INVActivePowerModeFeedback;
	}
	public void setCom25INVActivePowerModeFeedback(double com25invActivePowerModeFeedback) {
		Com25INVActivePowerModeFeedback = com25invActivePowerModeFeedback;
	}
	public double getCom25INVReactivePowerModeFeedback() {
		return Com25INVReactivePowerModeFeedback;
	}
	public void setCom25INVReactivePowerModeFeedback(double com25invReactivePowerModeFeedback) {
		Com25INVReactivePowerModeFeedback = com25invReactivePowerModeFeedback;
	}
	public double getCom25INVActivePowerPercentageFeedback() {
		return Com25INVActivePowerPercentageFeedback;
	}
	public void setCom25INVActivePowerPercentageFeedback(double com25invActivePowerPercentageFeedback) {
		Com25INVActivePowerPercentageFeedback = com25invActivePowerPercentageFeedback;
	}
	public double getCom25INVReactivePowerPercentageFeedback() {
		return Com25INVReactivePowerPercentageFeedback;
	}
	public void setCom25INVReactivePowerPercentageFeedback(double com25invReactivePowerPercentageFeedback) {
		Com25INVReactivePowerPercentageFeedback = com25invReactivePowerPercentageFeedback;
	}
	public double getCom25INVPowerFactorSettingFeedback() {
		return Com25INVPowerFactorSettingFeedback;
	}
	public void setCom25INVPowerFactorSettingFeedback(double com25invPowerFactorSettingFeedback) {
		Com25INVPowerFactorSettingFeedback = com25invPowerFactorSettingFeedback;
	}
	public double getCom26INVActivePowerModeFeedback() {
		return Com26INVActivePowerModeFeedback;
	}
	public void setCom26INVActivePowerModeFeedback(double com26invActivePowerModeFeedback) {
		Com26INVActivePowerModeFeedback = com26invActivePowerModeFeedback;
	}
	public double getCom26INVReactivePowerModeFeedback() {
		return Com26INVReactivePowerModeFeedback;
	}
	public void setCom26INVReactivePowerModeFeedback(double com26invReactivePowerModeFeedback) {
		Com26INVReactivePowerModeFeedback = com26invReactivePowerModeFeedback;
	}
	public double getCom26INVActivePowerPercentageFeedback() {
		return Com26INVActivePowerPercentageFeedback;
	}
	public void setCom26INVActivePowerPercentageFeedback(double com26invActivePowerPercentageFeedback) {
		Com26INVActivePowerPercentageFeedback = com26invActivePowerPercentageFeedback;
	}
	public double getCom26INVReactivePowerPercentageFeedback() {
		return Com26INVReactivePowerPercentageFeedback;
	}
	public void setCom26INVReactivePowerPercentageFeedback(double com26invReactivePowerPercentageFeedback) {
		Com26INVReactivePowerPercentageFeedback = com26invReactivePowerPercentageFeedback;
	}
	public double getCom26INVPowerFactorSettingFeedback() {
		return Com26INVPowerFactorSettingFeedback;
	}
	public void setCom26INVPowerFactorSettingFeedback(double com26invPowerFactorSettingFeedback) {
		Com26INVPowerFactorSettingFeedback = com26invPowerFactorSettingFeedback;
	}
	public double getCom27INVActivePowerModeFeedback() {
		return Com27INVActivePowerModeFeedback;
	}
	public void setCom27INVActivePowerModeFeedback(double com27invActivePowerModeFeedback) {
		Com27INVActivePowerModeFeedback = com27invActivePowerModeFeedback;
	}
	public double getCom27INVReactivePowerModeFeedback() {
		return Com27INVReactivePowerModeFeedback;
	}
	public void setCom27INVReactivePowerModeFeedback(double com27invReactivePowerModeFeedback) {
		Com27INVReactivePowerModeFeedback = com27invReactivePowerModeFeedback;
	}
	public double getCom27INVActivePowerPercentageFeedback() {
		return Com27INVActivePowerPercentageFeedback;
	}
	public void setCom27INVActivePowerPercentageFeedback(double com27invActivePowerPercentageFeedback) {
		Com27INVActivePowerPercentageFeedback = com27invActivePowerPercentageFeedback;
	}
	public double getCom27INVReactivePowerPercentageFeedback() {
		return Com27INVReactivePowerPercentageFeedback;
	}
	public void setCom27INVReactivePowerPercentageFeedback(double com27invReactivePowerPercentageFeedback) {
		Com27INVReactivePowerPercentageFeedback = com27invReactivePowerPercentageFeedback;
	}
	public double getCom27INVPowerFactorSettingFeedback() {
		return Com27INVPowerFactorSettingFeedback;
	}
	public void setCom27INVPowerFactorSettingFeedback(double com27invPowerFactorSettingFeedback) {
		Com27INVPowerFactorSettingFeedback = com27invPowerFactorSettingFeedback;
	}
	public double getCom28INVActivePowerModeFeedback() {
		return Com28INVActivePowerModeFeedback;
	}
	public void setCom28INVActivePowerModeFeedback(double com28invActivePowerModeFeedback) {
		Com28INVActivePowerModeFeedback = com28invActivePowerModeFeedback;
	}
	public double getCom28INVReactivePowerModeFeedback() {
		return Com28INVReactivePowerModeFeedback;
	}
	public void setCom28INVReactivePowerModeFeedback(double com28invReactivePowerModeFeedback) {
		Com28INVReactivePowerModeFeedback = com28invReactivePowerModeFeedback;
	}
	public double getCom28INVActivePowerPercentageFeedback() {
		return Com28INVActivePowerPercentageFeedback;
	}
	public void setCom28INVActivePowerPercentageFeedback(double com28invActivePowerPercentageFeedback) {
		Com28INVActivePowerPercentageFeedback = com28invActivePowerPercentageFeedback;
	}
	public double getCom28INVReactivePowerPercentageFeedback() {
		return Com28INVReactivePowerPercentageFeedback;
	}
	public void setCom28INVReactivePowerPercentageFeedback(double com28invReactivePowerPercentageFeedback) {
		Com28INVReactivePowerPercentageFeedback = com28invReactivePowerPercentageFeedback;
	}
	public double getCom28INVPowerFactorSettingFeedback() {
		return Com28INVPowerFactorSettingFeedback;
	}
	public void setCom28INVPowerFactorSettingFeedback(double com28invPowerFactorSettingFeedback) {
		Com28INVPowerFactorSettingFeedback = com28invPowerFactorSettingFeedback;
	}
	public double getCom29INVActivePowerModeFeedback() {
		return Com29INVActivePowerModeFeedback;
	}
	public void setCom29INVActivePowerModeFeedback(double com29invActivePowerModeFeedback) {
		Com29INVActivePowerModeFeedback = com29invActivePowerModeFeedback;
	}
	public double getCom29INVReactivePowerModeFeedback() {
		return Com29INVReactivePowerModeFeedback;
	}
	public void setCom29INVReactivePowerModeFeedback(double com29invReactivePowerModeFeedback) {
		Com29INVReactivePowerModeFeedback = com29invReactivePowerModeFeedback;
	}
	public double getCom29INVActivePowerPercentageFeedback() {
		return Com29INVActivePowerPercentageFeedback;
	}
	public void setCom29INVActivePowerPercentageFeedback(double com29invActivePowerPercentageFeedback) {
		Com29INVActivePowerPercentageFeedback = com29invActivePowerPercentageFeedback;
	}
	public double getCom29INVReactivePowerPercentageFeedback() {
		return Com29INVReactivePowerPercentageFeedback;
	}
	public void setCom29INVReactivePowerPercentageFeedback(double com29invReactivePowerPercentageFeedback) {
		Com29INVReactivePowerPercentageFeedback = com29invReactivePowerPercentageFeedback;
	}
	public double getCom29INVPowerFactorSettingFeedback() {
		return Com29INVPowerFactorSettingFeedback;
	}
	public void setCom29INVPowerFactorSettingFeedback(double com29invPowerFactorSettingFeedback) {
		Com29INVPowerFactorSettingFeedback = com29invPowerFactorSettingFeedback;
	}
	public double getCom210INVActivePowerModeFeedback() {
		return Com210INVActivePowerModeFeedback;
	}
	public void setCom210INVActivePowerModeFeedback(double com210invActivePowerModeFeedback) {
		Com210INVActivePowerModeFeedback = com210invActivePowerModeFeedback;
	}
	public double getCom210INVReactivePowerModeFeedback() {
		return Com210INVReactivePowerModeFeedback;
	}
	public void setCom210INVReactivePowerModeFeedback(double com210invReactivePowerModeFeedback) {
		Com210INVReactivePowerModeFeedback = com210invReactivePowerModeFeedback;
	}
	public double getCom210INVActivePowerPercentageFeedback() {
		return Com210INVActivePowerPercentageFeedback;
	}
	public void setCom210INVActivePowerPercentageFeedback(double com210invActivePowerPercentageFeedback) {
		Com210INVActivePowerPercentageFeedback = com210invActivePowerPercentageFeedback;
	}
	public double getCom210INVReactivePowerPercentageFeedback() {
		return Com210INVReactivePowerPercentageFeedback;
	}
	public void setCom210INVReactivePowerPercentageFeedback(double com210invReactivePowerPercentageFeedback) {
		Com210INVReactivePowerPercentageFeedback = com210invReactivePowerPercentageFeedback;
	}
	public double getCom210INVPowerFactorSettingFeedback() {
		return Com210INVPowerFactorSettingFeedback;
	}
	public void setCom210INVPowerFactorSettingFeedback(double com210invPowerFactorSettingFeedback) {
		Com210INVPowerFactorSettingFeedback = com210invPowerFactorSettingFeedback;
	}
	public double getCom211INVActivePowerModeFeedback() {
		return Com211INVActivePowerModeFeedback;
	}
	public void setCom211INVActivePowerModeFeedback(double com211invActivePowerModeFeedback) {
		Com211INVActivePowerModeFeedback = com211invActivePowerModeFeedback;
	}
	public double getCom211INVReactivePowerModeFeedback() {
		return Com211INVReactivePowerModeFeedback;
	}
	public void setCom211INVReactivePowerModeFeedback(double com211invReactivePowerModeFeedback) {
		Com211INVReactivePowerModeFeedback = com211invReactivePowerModeFeedback;
	}
	public double getCom211INVActivePowerPercentageFeedback() {
		return Com211INVActivePowerPercentageFeedback;
	}
	public void setCom211INVActivePowerPercentageFeedback(double com211invActivePowerPercentageFeedback) {
		Com211INVActivePowerPercentageFeedback = com211invActivePowerPercentageFeedback;
	}
	public double getCom211INVReactivePowerPercentageFeedback() {
		return Com211INVReactivePowerPercentageFeedback;
	}
	public void setCom211INVReactivePowerPercentageFeedback(double com211invReactivePowerPercentageFeedback) {
		Com211INVReactivePowerPercentageFeedback = com211invReactivePowerPercentageFeedback;
	}
	public double getCom211INVPowerFactorSettingFeedback() {
		return Com211INVPowerFactorSettingFeedback;
	}
	public void setCom211INVPowerFactorSettingFeedback(double com211invPowerFactorSettingFeedback) {
		Com211INVPowerFactorSettingFeedback = com211invPowerFactorSettingFeedback;
	}
	public double getCom212INVActivePowerModeFeedback() {
		return Com212INVActivePowerModeFeedback;
	}
	public void setCom212INVActivePowerModeFeedback(double com212invActivePowerModeFeedback) {
		Com212INVActivePowerModeFeedback = com212invActivePowerModeFeedback;
	}
	public double getCom212INVReactivePowerModeFeedback() {
		return Com212INVReactivePowerModeFeedback;
	}
	public void setCom212INVReactivePowerModeFeedback(double com212invReactivePowerModeFeedback) {
		Com212INVReactivePowerModeFeedback = com212invReactivePowerModeFeedback;
	}
	public double getCom212INVActivePowerPercentageFeedback() {
		return Com212INVActivePowerPercentageFeedback;
	}
	public void setCom212INVActivePowerPercentageFeedback(double com212invActivePowerPercentageFeedback) {
		Com212INVActivePowerPercentageFeedback = com212invActivePowerPercentageFeedback;
	}
	public double getCom212INVReactivePowerPercentageFeedback() {
		return Com212INVReactivePowerPercentageFeedback;
	}
	public void setCom212INVReactivePowerPercentageFeedback(double com212invReactivePowerPercentageFeedback) {
		Com212INVReactivePowerPercentageFeedback = com212invReactivePowerPercentageFeedback;
	}
	public double getCom212INVPowerFactorSettingFeedback() {
		return Com212INVPowerFactorSettingFeedback;
	}
	public void setCom212INVPowerFactorSettingFeedback(double com212invPowerFactorSettingFeedback) {
		Com212INVPowerFactorSettingFeedback = com212invPowerFactorSettingFeedback;
	}
	public double getCom213INVActivePowerModeFeedback() {
		return Com213INVActivePowerModeFeedback;
	}
	public void setCom213INVActivePowerModeFeedback(double com213invActivePowerModeFeedback) {
		Com213INVActivePowerModeFeedback = com213invActivePowerModeFeedback;
	}
	public double getCom213INVReactivePowerModeFeedback() {
		return Com213INVReactivePowerModeFeedback;
	}
	public void setCom213INVReactivePowerModeFeedback(double com213invReactivePowerModeFeedback) {
		Com213INVReactivePowerModeFeedback = com213invReactivePowerModeFeedback;
	}
	public double getCom213INVActivePowerPercentageFeedback() {
		return Com213INVActivePowerPercentageFeedback;
	}
	public void setCom213INVActivePowerPercentageFeedback(double com213invActivePowerPercentageFeedback) {
		Com213INVActivePowerPercentageFeedback = com213invActivePowerPercentageFeedback;
	}
	public double getCom213INVReactivePowerPercentageFeedback() {
		return Com213INVReactivePowerPercentageFeedback;
	}
	public void setCom213INVReactivePowerPercentageFeedback(double com213invReactivePowerPercentageFeedback) {
		Com213INVReactivePowerPercentageFeedback = com213invReactivePowerPercentageFeedback;
	}
	public double getCom213INVPowerFactorSettingFeedback() {
		return Com213INVPowerFactorSettingFeedback;
	}
	public void setCom213INVPowerFactorSettingFeedback(double com213invPowerFactorSettingFeedback) {
		Com213INVPowerFactorSettingFeedback = com213invPowerFactorSettingFeedback;
	}
	public double getCom214INVActivePowerModeFeedback() {
		return Com214INVActivePowerModeFeedback;
	}
	public void setCom214INVActivePowerModeFeedback(double com214invActivePowerModeFeedback) {
		Com214INVActivePowerModeFeedback = com214invActivePowerModeFeedback;
	}
	public double getCom214INVReactivePowerModeFeedback() {
		return Com214INVReactivePowerModeFeedback;
	}
	public void setCom214INVReactivePowerModeFeedback(double com214invReactivePowerModeFeedback) {
		Com214INVReactivePowerModeFeedback = com214invReactivePowerModeFeedback;
	}
	public double getCom214INVActivePowerPercentageFeedback() {
		return Com214INVActivePowerPercentageFeedback;
	}
	public void setCom214INVActivePowerPercentageFeedback(double com214invActivePowerPercentageFeedback) {
		Com214INVActivePowerPercentageFeedback = com214invActivePowerPercentageFeedback;
	}
	public double getCom214INVReactivePowerPercentageFeedback() {
		return Com214INVReactivePowerPercentageFeedback;
	}
	public void setCom214INVReactivePowerPercentageFeedback(double com214invReactivePowerPercentageFeedback) {
		Com214INVReactivePowerPercentageFeedback = com214invReactivePowerPercentageFeedback;
	}
	public double getCom214INVPowerFactorSettingFeedback() {
		return Com214INVPowerFactorSettingFeedback;
	}
	public void setCom214INVPowerFactorSettingFeedback(double com214invPowerFactorSettingFeedback) {
		Com214INVPowerFactorSettingFeedback = com214invPowerFactorSettingFeedback;
	}
	public double getCom215INVActivePowerModeFeedback() {
		return Com215INVActivePowerModeFeedback;
	}
	public void setCom215INVActivePowerModeFeedback(double com215invActivePowerModeFeedback) {
		Com215INVActivePowerModeFeedback = com215invActivePowerModeFeedback;
	}
	public double getCom215INVReactivePowerModeFeedback() {
		return Com215INVReactivePowerModeFeedback;
	}
	public void setCom215INVReactivePowerModeFeedback(double com215invReactivePowerModeFeedback) {
		Com215INVReactivePowerModeFeedback = com215invReactivePowerModeFeedback;
	}
	public double getCom215INVActivePowerPercentageFeedback() {
		return Com215INVActivePowerPercentageFeedback;
	}
	public void setCom215INVActivePowerPercentageFeedback(double com215invActivePowerPercentageFeedback) {
		Com215INVActivePowerPercentageFeedback = com215invActivePowerPercentageFeedback;
	}
	public double getCom215INVReactivePowerPercentageFeedback() {
		return Com215INVReactivePowerPercentageFeedback;
	}
	public void setCom215INVReactivePowerPercentageFeedback(double com215invReactivePowerPercentageFeedback) {
		Com215INVReactivePowerPercentageFeedback = com215invReactivePowerPercentageFeedback;
	}
	public double getCom215INVPowerFactorSettingFeedback() {
		return Com215INVPowerFactorSettingFeedback;
	}
	public void setCom215INVPowerFactorSettingFeedback(double com215invPowerFactorSettingFeedback) {
		Com215INVPowerFactorSettingFeedback = com215invPowerFactorSettingFeedback;
	}
	
	
	

}
