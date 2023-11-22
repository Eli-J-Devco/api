/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelAE1000NXClass9644Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double LastRestart;
	private double Uptime;
	private double Year;
	private double Month;
	private double Day;
	private double Hour;
	private double Minutes;
	private double Seconds;
	private double CurrentTime;
	private double ACPower;
	private double ACFrequency;
	private double PVVoltage;
	private double PVCurrent;
	private double CommonMode;
	private double AmbientTemperature;
	private double CoolantTemperature;
	private double ReactorTemperature;
	private double CabinetTemperature;
	private double BusVoltage;
	private double GroundCurrent;
	private double ReactivePower;
	private double ACCurrent;
	private double TodaykWh;
	private double YTDkWhTotal;
	private double LifekWhTotal;
	private double YTDkWh;
	private double LifekWh;
	private double Last15MinkWh;
	private double TimeStamp15Minutes;
	private double State;
	private double Limits;
	private double MasterFault;
	private double MasterWarning;
	private double ArrayFault;
	private double ArrayWarning;
	private double GridFault;
	private double GridWarning;
	private double SystemFault;
	private double SystemWarning;
	private double DriveFault;
	private double DriveWarning;
	private double TemperatureFault;
	private double TemperatureWarning;
	private double CoolingSystemFault;
	private double CoolingSystemWarning;
	private double ElectricInterlockFault;
	private double ElectricInterlockWarning;
	private double PowerSupplyFault;
	private double PowerSupplyWarning;
	private double RequestSetACPowerLimit;
	private double LowerRangeOfACPowerLimit;
	private double UpperRangeOfACPowerLimit;
	private double RequestSetInstantaneousReactivePowerSetPoint;
	private double LowerRangeOfInstantaneousReactivePowerSetPoint;
	private double UpperRangeOfInstantaneousReactivePowerSetPoint;
	private double EnableDisableTheUnit;
	private double SetReadReactivePowerMode;
	private double SetReadPACLimit;
	private double SetReadInstantaneousReactivePowerSetPoint;
	private double SetReadPowerFactorSetPoint;
	private double ACPowerRampRate;
	private double ReactivePowerRampRate;
	private double PowerFactorRampRate;
	private double ReactivePowerSignConvention;
	private double DCS_N;
	private double DCS_StCtcr;
	private double DCS_StCpt;
	private double DCS_EvtFlt;
	private double DCS_EvtWrn;
	private double DCS_StVnd;
	private double DCS_DCA;
	private double DCS_DCADif;
	private double DCS_DCV;
	private double DCS_Tmp;
	private double DCS_InId01;
	private double DCS_InFlt01;
	private double DCS_InWrn01;
	private double DCS_InPDCA01;
	private double DCS_InNDCA01;
	private double DCS_InId02;
	private double DCS_InFlt02;
	private double DCS_InWrn02;
	private double DCS_InPDCA02;
	private double DCS_InNDCA02;
	private double DCS_InId03;
	private double DCS_InFlt03;
	private double DCS_InWrn03;
	private double DCS_InPDCA03;
	private double DCS_InNDCA03;
	private double DCS_InId04;
	private double DCS_InFlt04;
	private double DCS_InWrn04;
	private double DCS_InPDCA04;
	private double DCS_InNDCA04;
	private double DCS_InId05;
	private double DCS_InFlt05;
	private double DCS_InWrn05;
	private double DCS_InPDCA05;
	private double DCS_InNDCA05;
	private double DCS_InId06;
	private double DCS_InFlt06;
	private double DCS_InWrn06;
	private double DCS_InPDCA06;
	private double DCS_InNDCA06;
	private double DCS_InId07;
	private double DCS_InFlt07;
	private double DCS_InWrn07;
	private double DCS_InPDCA07;
	private double DCS_InNDCA07;
	private double DCS_InId08;
	private double DCS_InFlt08;
	private double DCS_InWrn08;
	private double DCS_InPDCA08;
	private double DCS_InNDCA08;
	private double DCS_InId09;
	private double DCS_InFlt09;
	private double DCS_InWrn09;
	private double DCS_InPDCA09;
	private double DCS_InNDCA09;
	private double DCS_InId10;
	private double DCS_InFlt10;
	private double DCS_InWrn10;
	private double DCS_InPDCA10;
	private double DCS_InNDCA10;
	
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	
	
	
	
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
	
	
	
	
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
	}
	
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
	public double getLastRestart() {
		return LastRestart;
	}
	public void setLastRestart(double lastRestart) {
		LastRestart = lastRestart;
	}
	public double getUptime() {
		return Uptime;
	}
	public void setUptime(double uptime) {
		Uptime = uptime;
	}
	public double getYear() {
		return Year;
	}
	public void setYear(double year) {
		Year = year;
	}
	public double getMonth() {
		return Month;
	}
	public void setMonth(double month) {
		Month = month;
	}
	public double getDay() {
		return Day;
	}
	public void setDay(double day) {
		Day = day;
	}
	public double getHour() {
		return Hour;
	}
	public void setHour(double hour) {
		Hour = hour;
	}
	public double getMinutes() {
		return Minutes;
	}
	public void setMinutes(double minutes) {
		Minutes = minutes;
	}
	public double getSeconds() {
		return Seconds;
	}
	public void setSeconds(double seconds) {
		Seconds = seconds;
	}
	public double getCurrentTime() {
		return CurrentTime;
	}
	public void setCurrentTime(double currentTime) {
		CurrentTime = currentTime;
	}
	public double getACPower() {
		return ACPower;
	}
	public void setACPower(double aCPower) {
		ACPower = aCPower;
	}
	public double getACFrequency() {
		return ACFrequency;
	}
	public void setACFrequency(double aCFrequency) {
		ACFrequency = aCFrequency;
	}
	public double getPVVoltage() {
		return PVVoltage;
	}
	public void setPVVoltage(double pVVoltage) {
		PVVoltage = pVVoltage;
	}
	public double getPVCurrent() {
		return PVCurrent;
	}
	public void setPVCurrent(double pVCurrent) {
		PVCurrent = pVCurrent;
	}
	public double getCommonMode() {
		return CommonMode;
	}
	public void setCommonMode(double commonMode) {
		CommonMode = commonMode;
	}
	public double getAmbientTemperature() {
		return AmbientTemperature;
	}
	public void setAmbientTemperature(double ambientTemperature) {
		AmbientTemperature = ambientTemperature;
	}
	public double getCoolantTemperature() {
		return CoolantTemperature;
	}
	public void setCoolantTemperature(double coolantTemperature) {
		CoolantTemperature = coolantTemperature;
	}
	public double getReactorTemperature() {
		return ReactorTemperature;
	}
	public void setReactorTemperature(double reactorTemperature) {
		ReactorTemperature = reactorTemperature;
	}
	public double getCabinetTemperature() {
		return CabinetTemperature;
	}
	public void setCabinetTemperature(double cabinetTemperature) {
		CabinetTemperature = cabinetTemperature;
	}
	public double getBusVoltage() {
		return BusVoltage;
	}
	public void setBusVoltage(double busVoltage) {
		BusVoltage = busVoltage;
	}
	public double getGroundCurrent() {
		return GroundCurrent;
	}
	public void setGroundCurrent(double groundCurrent) {
		GroundCurrent = groundCurrent;
	}
	public double getReactivePower() {
		return ReactivePower;
	}
	public void setReactivePower(double reactivePower) {
		ReactivePower = reactivePower;
	}
	public double getACCurrent() {
		return ACCurrent;
	}
	public void setACCurrent(double aCCurrent) {
		ACCurrent = aCCurrent;
	}
	public double getTodaykWh() {
		return TodaykWh;
	}
	public void setTodaykWh(double todaykWh) {
		TodaykWh = todaykWh;
	}
	public double getYTDkWhTotal() {
		return YTDkWhTotal;
	}
	public void setYTDkWhTotal(double yTDkWhTotal) {
		YTDkWhTotal = yTDkWhTotal;
	}
	public double getLifekWhTotal() {
		return LifekWhTotal;
	}
	public void setLifekWhTotal(double lifekWhTotal) {
		LifekWhTotal = lifekWhTotal;
	}
	public double getYTDkWh() {
		return YTDkWh;
	}
	public void setYTDkWh(double yTDkWh) {
		YTDkWh = yTDkWh;
	}
	public double getLifekWh() {
		return LifekWh;
	}
	public void setLifekWh(double lifekWh) {
		LifekWh = lifekWh;
	}
	public double getLast15MinkWh() {
		return Last15MinkWh;
	}
	public void setLast15MinkWh(double last15MinkWh) {
		Last15MinkWh = last15MinkWh;
	}
	public double getTimeStamp15Minutes() {
		return TimeStamp15Minutes;
	}
	public void setTimeStamp15Minutes(double timeStamp15Minutes) {
		TimeStamp15Minutes = timeStamp15Minutes;
	}
	public double getState() {
		return State;
	}
	public void setState(double state) {
		State = state;
	}
	public double getLimits() {
		return Limits;
	}
	public void setLimits(double limits) {
		Limits = limits;
	}
	public double getMasterFault() {
		return MasterFault;
	}
	public void setMasterFault(double masterFault) {
		MasterFault = masterFault;
	}
	public double getMasterWarning() {
		return MasterWarning;
	}
	public void setMasterWarning(double masterWarning) {
		MasterWarning = masterWarning;
	}
	public double getArrayFault() {
		return ArrayFault;
	}
	public void setArrayFault(double arrayFault) {
		ArrayFault = arrayFault;
	}
	public double getArrayWarning() {
		return ArrayWarning;
	}
	public void setArrayWarning(double arrayWarning) {
		ArrayWarning = arrayWarning;
	}
	public double getGridFault() {
		return GridFault;
	}
	public void setGridFault(double gridFault) {
		GridFault = gridFault;
	}
	public double getGridWarning() {
		return GridWarning;
	}
	public void setGridWarning(double gridWarning) {
		GridWarning = gridWarning;
	}
	public double getSystemFault() {
		return SystemFault;
	}
	public void setSystemFault(double systemFault) {
		SystemFault = systemFault;
	}
	public double getSystemWarning() {
		return SystemWarning;
	}
	public void setSystemWarning(double systemWarning) {
		SystemWarning = systemWarning;
	}
	public double getDriveFault() {
		return DriveFault;
	}
	public void setDriveFault(double driveFault) {
		DriveFault = driveFault;
	}
	public double getDriveWarning() {
		return DriveWarning;
	}
	public void setDriveWarning(double driveWarning) {
		DriveWarning = driveWarning;
	}
	public double getTemperatureFault() {
		return TemperatureFault;
	}
	public void setTemperatureFault(double temperatureFault) {
		TemperatureFault = temperatureFault;
	}
	public double getTemperatureWarning() {
		return TemperatureWarning;
	}
	public void setTemperatureWarning(double temperatureWarning) {
		TemperatureWarning = temperatureWarning;
	}
	public double getCoolingSystemFault() {
		return CoolingSystemFault;
	}
	public void setCoolingSystemFault(double coolingSystemFault) {
		CoolingSystemFault = coolingSystemFault;
	}
	public double getCoolingSystemWarning() {
		return CoolingSystemWarning;
	}
	public void setCoolingSystemWarning(double coolingSystemWarning) {
		CoolingSystemWarning = coolingSystemWarning;
	}
	public double getElectricInterlockFault() {
		return ElectricInterlockFault;
	}
	public void setElectricInterlockFault(double electricInterlockFault) {
		ElectricInterlockFault = electricInterlockFault;
	}
	public double getElectricInterlockWarning() {
		return ElectricInterlockWarning;
	}
	public void setElectricInterlockWarning(double electricInterlockWarning) {
		ElectricInterlockWarning = electricInterlockWarning;
	}
	public double getPowerSupplyFault() {
		return PowerSupplyFault;
	}
	public void setPowerSupplyFault(double powerSupplyFault) {
		PowerSupplyFault = powerSupplyFault;
	}
	public double getPowerSupplyWarning() {
		return PowerSupplyWarning;
	}
	public void setPowerSupplyWarning(double powerSupplyWarning) {
		PowerSupplyWarning = powerSupplyWarning;
	}
	public double getRequestSetACPowerLimit() {
		return RequestSetACPowerLimit;
	}
	public void setRequestSetACPowerLimit(double requestSetACPowerLimit) {
		RequestSetACPowerLimit = requestSetACPowerLimit;
	}
	public double getLowerRangeOfACPowerLimit() {
		return LowerRangeOfACPowerLimit;
	}
	public void setLowerRangeOfACPowerLimit(double lowerRangeOfACPowerLimit) {
		LowerRangeOfACPowerLimit = lowerRangeOfACPowerLimit;
	}
	public double getUpperRangeOfACPowerLimit() {
		return UpperRangeOfACPowerLimit;
	}
	public void setUpperRangeOfACPowerLimit(double upperRangeOfACPowerLimit) {
		UpperRangeOfACPowerLimit = upperRangeOfACPowerLimit;
	}
	public double getRequestSetInstantaneousReactivePowerSetPoint() {
		return RequestSetInstantaneousReactivePowerSetPoint;
	}
	public void setRequestSetInstantaneousReactivePowerSetPoint(double requestSetInstantaneousReactivePowerSetPoint) {
		RequestSetInstantaneousReactivePowerSetPoint = requestSetInstantaneousReactivePowerSetPoint;
	}
	public double getLowerRangeOfInstantaneousReactivePowerSetPoint() {
		return LowerRangeOfInstantaneousReactivePowerSetPoint;
	}
	public void setLowerRangeOfInstantaneousReactivePowerSetPoint(double lowerRangeOfInstantaneousReactivePowerSetPoint) {
		LowerRangeOfInstantaneousReactivePowerSetPoint = lowerRangeOfInstantaneousReactivePowerSetPoint;
	}
	public double getUpperRangeOfInstantaneousReactivePowerSetPoint() {
		return UpperRangeOfInstantaneousReactivePowerSetPoint;
	}
	public void setUpperRangeOfInstantaneousReactivePowerSetPoint(double upperRangeOfInstantaneousReactivePowerSetPoint) {
		UpperRangeOfInstantaneousReactivePowerSetPoint = upperRangeOfInstantaneousReactivePowerSetPoint;
	}
	public double getEnableDisableTheUnit() {
		return EnableDisableTheUnit;
	}
	public void setEnableDisableTheUnit(double enableDisableTheUnit) {
		EnableDisableTheUnit = enableDisableTheUnit;
	}
	public double getSetReadReactivePowerMode() {
		return SetReadReactivePowerMode;
	}
	public void setSetReadReactivePowerMode(double setReadReactivePowerMode) {
		SetReadReactivePowerMode = setReadReactivePowerMode;
	}
	public double getSetReadPACLimit() {
		return SetReadPACLimit;
	}
	public void setSetReadPACLimit(double setReadPACLimit) {
		SetReadPACLimit = setReadPACLimit;
	}
	public double getSetReadInstantaneousReactivePowerSetPoint() {
		return SetReadInstantaneousReactivePowerSetPoint;
	}
	public void setSetReadInstantaneousReactivePowerSetPoint(double setReadInstantaneousReactivePowerSetPoint) {
		SetReadInstantaneousReactivePowerSetPoint = setReadInstantaneousReactivePowerSetPoint;
	}
	public double getSetReadPowerFactorSetPoint() {
		return SetReadPowerFactorSetPoint;
	}
	public void setSetReadPowerFactorSetPoint(double setReadPowerFactorSetPoint) {
		SetReadPowerFactorSetPoint = setReadPowerFactorSetPoint;
	}
	public double getACPowerRampRate() {
		return ACPowerRampRate;
	}
	public void setACPowerRampRate(double aCPowerRampRate) {
		ACPowerRampRate = aCPowerRampRate;
	}
	public double getReactivePowerRampRate() {
		return ReactivePowerRampRate;
	}
	public void setReactivePowerRampRate(double reactivePowerRampRate) {
		ReactivePowerRampRate = reactivePowerRampRate;
	}
	public double getPowerFactorRampRate() {
		return PowerFactorRampRate;
	}
	public void setPowerFactorRampRate(double powerFactorRampRate) {
		PowerFactorRampRate = powerFactorRampRate;
	}
	public double getReactivePowerSignConvention() {
		return ReactivePowerSignConvention;
	}
	public void setReactivePowerSignConvention(double reactivePowerSignConvention) {
		ReactivePowerSignConvention = reactivePowerSignConvention;
	}
	public double getDCS_N() {
		return DCS_N;
	}
	public void setDCS_N(double dCS_N) {
		DCS_N = dCS_N;
	}
	public double getDCS_StCtcr() {
		return DCS_StCtcr;
	}
	public void setDCS_StCtcr(double dCS_StCtcr) {
		DCS_StCtcr = dCS_StCtcr;
	}
	public double getDCS_StCpt() {
		return DCS_StCpt;
	}
	public void setDCS_StCpt(double dCS_StCpt) {
		DCS_StCpt = dCS_StCpt;
	}
	public double getDCS_EvtFlt() {
		return DCS_EvtFlt;
	}
	public void setDCS_EvtFlt(double dCS_EvtFlt) {
		DCS_EvtFlt = dCS_EvtFlt;
	}
	public double getDCS_EvtWrn() {
		return DCS_EvtWrn;
	}
	public void setDCS_EvtWrn(double dCS_EvtWrn) {
		DCS_EvtWrn = dCS_EvtWrn;
	}
	public double getDCS_StVnd() {
		return DCS_StVnd;
	}
	public void setDCS_StVnd(double dCS_StVnd) {
		DCS_StVnd = dCS_StVnd;
	}
	public double getDCS_DCA() {
		return DCS_DCA;
	}
	public void setDCS_DCA(double dCS_DCA) {
		DCS_DCA = dCS_DCA;
	}
	public double getDCS_DCADif() {
		return DCS_DCADif;
	}
	public void setDCS_DCADif(double dCS_DCADif) {
		DCS_DCADif = dCS_DCADif;
	}
	public double getDCS_DCV() {
		return DCS_DCV;
	}
	public void setDCS_DCV(double dCS_DCV) {
		DCS_DCV = dCS_DCV;
	}
	public double getDCS_Tmp() {
		return DCS_Tmp;
	}
	public void setDCS_Tmp(double dCS_Tmp) {
		DCS_Tmp = dCS_Tmp;
	}
	public double getDCS_InId01() {
		return DCS_InId01;
	}
	public void setDCS_InId01(double dCS_InId01) {
		DCS_InId01 = dCS_InId01;
	}
	public double getDCS_InFlt01() {
		return DCS_InFlt01;
	}
	public void setDCS_InFlt01(double dCS_InFlt01) {
		DCS_InFlt01 = dCS_InFlt01;
	}
	public double getDCS_InWrn01() {
		return DCS_InWrn01;
	}
	public void setDCS_InWrn01(double dCS_InWrn01) {
		DCS_InWrn01 = dCS_InWrn01;
	}
	public double getDCS_InPDCA01() {
		return DCS_InPDCA01;
	}
	public void setDCS_InPDCA01(double dCS_InPDCA01) {
		DCS_InPDCA01 = dCS_InPDCA01;
	}
	public double getDCS_InNDCA01() {
		return DCS_InNDCA01;
	}
	public void setDCS_InNDCA01(double dCS_InNDCA01) {
		DCS_InNDCA01 = dCS_InNDCA01;
	}
	public double getDCS_InId02() {
		return DCS_InId02;
	}
	public void setDCS_InId02(double dCS_InId02) {
		DCS_InId02 = dCS_InId02;
	}
	public double getDCS_InFlt02() {
		return DCS_InFlt02;
	}
	public void setDCS_InFlt02(double dCS_InFlt02) {
		DCS_InFlt02 = dCS_InFlt02;
	}
	public double getDCS_InWrn02() {
		return DCS_InWrn02;
	}
	public void setDCS_InWrn02(double dCS_InWrn02) {
		DCS_InWrn02 = dCS_InWrn02;
	}
	public double getDCS_InPDCA02() {
		return DCS_InPDCA02;
	}
	public void setDCS_InPDCA02(double dCS_InPDCA02) {
		DCS_InPDCA02 = dCS_InPDCA02;
	}
	public double getDCS_InNDCA02() {
		return DCS_InNDCA02;
	}
	public void setDCS_InNDCA02(double dCS_InNDCA02) {
		DCS_InNDCA02 = dCS_InNDCA02;
	}
	public double getDCS_InId03() {
		return DCS_InId03;
	}
	public void setDCS_InId03(double dCS_InId03) {
		DCS_InId03 = dCS_InId03;
	}
	public double getDCS_InFlt03() {
		return DCS_InFlt03;
	}
	public void setDCS_InFlt03(double dCS_InFlt03) {
		DCS_InFlt03 = dCS_InFlt03;
	}
	public double getDCS_InWrn03() {
		return DCS_InWrn03;
	}
	public void setDCS_InWrn03(double dCS_InWrn03) {
		DCS_InWrn03 = dCS_InWrn03;
	}
	public double getDCS_InPDCA03() {
		return DCS_InPDCA03;
	}
	public void setDCS_InPDCA03(double dCS_InPDCA03) {
		DCS_InPDCA03 = dCS_InPDCA03;
	}
	public double getDCS_InNDCA03() {
		return DCS_InNDCA03;
	}
	public void setDCS_InNDCA03(double dCS_InNDCA03) {
		DCS_InNDCA03 = dCS_InNDCA03;
	}
	public double getDCS_InId04() {
		return DCS_InId04;
	}
	public void setDCS_InId04(double dCS_InId04) {
		DCS_InId04 = dCS_InId04;
	}
	public double getDCS_InFlt04() {
		return DCS_InFlt04;
	}
	public void setDCS_InFlt04(double dCS_InFlt04) {
		DCS_InFlt04 = dCS_InFlt04;
	}
	public double getDCS_InWrn04() {
		return DCS_InWrn04;
	}
	public void setDCS_InWrn04(double dCS_InWrn04) {
		DCS_InWrn04 = dCS_InWrn04;
	}
	public double getDCS_InPDCA04() {
		return DCS_InPDCA04;
	}
	public void setDCS_InPDCA04(double dCS_InPDCA04) {
		DCS_InPDCA04 = dCS_InPDCA04;
	}
	public double getDCS_InNDCA04() {
		return DCS_InNDCA04;
	}
	public void setDCS_InNDCA04(double dCS_InNDCA04) {
		DCS_InNDCA04 = dCS_InNDCA04;
	}
	public double getDCS_InId05() {
		return DCS_InId05;
	}
	public void setDCS_InId05(double dCS_InId05) {
		DCS_InId05 = dCS_InId05;
	}
	public double getDCS_InFlt05() {
		return DCS_InFlt05;
	}
	public void setDCS_InFlt05(double dCS_InFlt05) {
		DCS_InFlt05 = dCS_InFlt05;
	}
	public double getDCS_InWrn05() {
		return DCS_InWrn05;
	}
	public void setDCS_InWrn05(double dCS_InWrn05) {
		DCS_InWrn05 = dCS_InWrn05;
	}
	public double getDCS_InPDCA05() {
		return DCS_InPDCA05;
	}
	public void setDCS_InPDCA05(double dCS_InPDCA05) {
		DCS_InPDCA05 = dCS_InPDCA05;
	}
	public double getDCS_InNDCA05() {
		return DCS_InNDCA05;
	}
	public void setDCS_InNDCA05(double dCS_InNDCA05) {
		DCS_InNDCA05 = dCS_InNDCA05;
	}
	public double getDCS_InId06() {
		return DCS_InId06;
	}
	public void setDCS_InId06(double dCS_InId06) {
		DCS_InId06 = dCS_InId06;
	}
	public double getDCS_InFlt06() {
		return DCS_InFlt06;
	}
	public void setDCS_InFlt06(double dCS_InFlt06) {
		DCS_InFlt06 = dCS_InFlt06;
	}
	public double getDCS_InWrn06() {
		return DCS_InWrn06;
	}
	public void setDCS_InWrn06(double dCS_InWrn06) {
		DCS_InWrn06 = dCS_InWrn06;
	}
	public double getDCS_InPDCA06() {
		return DCS_InPDCA06;
	}
	public void setDCS_InPDCA06(double dCS_InPDCA06) {
		DCS_InPDCA06 = dCS_InPDCA06;
	}
	public double getDCS_InNDCA06() {
		return DCS_InNDCA06;
	}
	public void setDCS_InNDCA06(double dCS_InNDCA06) {
		DCS_InNDCA06 = dCS_InNDCA06;
	}
	public double getDCS_InId07() {
		return DCS_InId07;
	}
	public void setDCS_InId07(double dCS_InId07) {
		DCS_InId07 = dCS_InId07;
	}
	public double getDCS_InFlt07() {
		return DCS_InFlt07;
	}
	public void setDCS_InFlt07(double dCS_InFlt07) {
		DCS_InFlt07 = dCS_InFlt07;
	}
	public double getDCS_InWrn07() {
		return DCS_InWrn07;
	}
	public void setDCS_InWrn07(double dCS_InWrn07) {
		DCS_InWrn07 = dCS_InWrn07;
	}
	public double getDCS_InPDCA07() {
		return DCS_InPDCA07;
	}
	public void setDCS_InPDCA07(double dCS_InPDCA07) {
		DCS_InPDCA07 = dCS_InPDCA07;
	}
	public double getDCS_InNDCA07() {
		return DCS_InNDCA07;
	}
	public void setDCS_InNDCA07(double dCS_InNDCA07) {
		DCS_InNDCA07 = dCS_InNDCA07;
	}
	public double getDCS_InId08() {
		return DCS_InId08;
	}
	public void setDCS_InId08(double dCS_InId08) {
		DCS_InId08 = dCS_InId08;
	}
	public double getDCS_InFlt08() {
		return DCS_InFlt08;
	}
	public void setDCS_InFlt08(double dCS_InFlt08) {
		DCS_InFlt08 = dCS_InFlt08;
	}
	public double getDCS_InWrn08() {
		return DCS_InWrn08;
	}
	public void setDCS_InWrn08(double dCS_InWrn08) {
		DCS_InWrn08 = dCS_InWrn08;
	}
	public double getDCS_InPDCA08() {
		return DCS_InPDCA08;
	}
	public void setDCS_InPDCA08(double dCS_InPDCA08) {
		DCS_InPDCA08 = dCS_InPDCA08;
	}
	public double getDCS_InNDCA08() {
		return DCS_InNDCA08;
	}
	public void setDCS_InNDCA08(double dCS_InNDCA08) {
		DCS_InNDCA08 = dCS_InNDCA08;
	}
	public double getDCS_InId09() {
		return DCS_InId09;
	}
	public void setDCS_InId09(double dCS_InId09) {
		DCS_InId09 = dCS_InId09;
	}
	public double getDCS_InFlt09() {
		return DCS_InFlt09;
	}
	public void setDCS_InFlt09(double dCS_InFlt09) {
		DCS_InFlt09 = dCS_InFlt09;
	}
	public double getDCS_InWrn09() {
		return DCS_InWrn09;
	}
	public void setDCS_InWrn09(double dCS_InWrn09) {
		DCS_InWrn09 = dCS_InWrn09;
	}
	public double getDCS_InPDCA09() {
		return DCS_InPDCA09;
	}
	public void setDCS_InPDCA09(double dCS_InPDCA09) {
		DCS_InPDCA09 = dCS_InPDCA09;
	}
	public double getDCS_InNDCA09() {
		return DCS_InNDCA09;
	}
	public void setDCS_InNDCA09(double dCS_InNDCA09) {
		DCS_InNDCA09 = dCS_InNDCA09;
	}
	public double getDCS_InId10() {
		return DCS_InId10;
	}
	public void setDCS_InId10(double dCS_InId10) {
		DCS_InId10 = dCS_InId10;
	}
	public double getDCS_InFlt10() {
		return DCS_InFlt10;
	}
	public void setDCS_InFlt10(double dCS_InFlt10) {
		DCS_InFlt10 = dCS_InFlt10;
	}
	public double getDCS_InWrn10() {
		return DCS_InWrn10;
	}
	public void setDCS_InWrn10(double dCS_InWrn10) {
		DCS_InWrn10 = dCS_InWrn10;
	}
	public double getDCS_InPDCA10() {
		return DCS_InPDCA10;
	}
	public void setDCS_InPDCA10(double dCS_InPDCA10) {
		DCS_InPDCA10 = dCS_InPDCA10;
	}
	public double getDCS_InNDCA10() {
		return DCS_InNDCA10;
	}
	public void setDCS_InNDCA10(double dCS_InNDCA10) {
		DCS_InNDCA10 = dCS_InNDCA10;
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
	
}
