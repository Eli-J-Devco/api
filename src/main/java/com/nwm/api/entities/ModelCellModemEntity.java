/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelCellModemEntity {
	private String time;
	private int id_device;
	private String serialnumber;
	private String ConnectionName;
	private String ConnectionStatus;
	private String ConnectionType;
	private String ConnectionMethod;
	private String IPAddress;
	private String DefaultGateway;
	private String DNSServers;
	private String MTU;
	private String IMEI;
	private String IMSI;
	private String ICCID;
	private String SIMSLOT;
	private double RSSI4;
	private double SINR4;
	private double RSRP4;
	private double RSRQ4;
	private double Channel4;
	private double RSSI3;
	private double SINR3;
	private double RSRP3;
	private double RSRQ3;
	private double Channel3;
	private double CPULoad;
	
	private String ALLDownload;
	private String ALLUpload;
	private String ALLTotal;
	private String CellularDownload;
	private String CellularUpload;
	private String CellularTotal;
	private String DeviceName;
	private String ProductModel;
	private String HardwareRevision;
	private String FirmwareVersion;
	private String Uptime;
	private String Mode;
	private String SystemMode;
	private double Temperature;
	private String SlotA;
	private String SlotB;
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
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getConnectionName() {
		return ConnectionName;
	}
	public void setConnectionName(String connectionName) {
		ConnectionName = connectionName;
	}
	public String getConnectionStatus() {
		return ConnectionStatus;
	}
	public void setConnectionStatus(String connectionStatus) {
		ConnectionStatus = connectionStatus;
	}
	public String getConnectionType() {
		return ConnectionType;
	}
	public void setConnectionType(String connectionType) {
		ConnectionType = connectionType;
	}
	public String getConnectionMethod() {
		return ConnectionMethod;
	}
	public void setConnectionMethod(String connectionMethod) {
		ConnectionMethod = connectionMethod;
	}
	public String getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	public String getDefaultGateway() {
		return DefaultGateway;
	}
	public void setDefaultGateway(String defaultGateway) {
		DefaultGateway = defaultGateway;
	}
	public String getDNSServers() {
		return DNSServers;
	}
	public void setDNSServers(String dNSServers) {
		DNSServers = dNSServers;
	}
	public String getMTU() {
		return MTU;
	}
	public void setMTU(String mTU) {
		MTU = mTU;
	}
	public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	public String getIMSI() {
		return IMSI;
	}
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public String getSIMSLOT() {
		return SIMSLOT;
	}
	public void setSIMSLOT(String sIMSLOT) {
		SIMSLOT = sIMSLOT;
	}
	public double getRSSI4() {
		return RSSI4;
	}
	public void setRSSI4(double rSSI4) {
		RSSI4 = rSSI4;
	}
	public double getSINR4() {
		return SINR4;
	}
	public void setSINR4(double sINR4) {
		SINR4 = sINR4;
	}
	public double getRSRP4() {
		return RSRP4;
	}
	public void setRSRP4(double rSRP4) {
		RSRP4 = rSRP4;
	}
	public double getRSRQ4() {
		return RSRQ4;
	}
	public void setRSRQ4(double rSRQ4) {
		RSRQ4 = rSRQ4;
	}
	public double getChannel4() {
		return Channel4;
	}
	public void setChannel4(double channel4) {
		Channel4 = channel4;
	}
	public double getRSSI3() {
		return RSSI3;
	}
	public void setRSSI3(double rSSI3) {
		RSSI3 = rSSI3;
	}
	public double getSINR3() {
		return SINR3;
	}
	public void setSINR3(double sINR3) {
		SINR3 = sINR3;
	}
	public double getRSRP3() {
		return RSRP3;
	}
	public void setRSRP3(double rSRP3) {
		RSRP3 = rSRP3;
	}
	public double getRSRQ3() {
		return RSRQ3;
	}
	public void setRSRQ3(double rSRQ3) {
		RSRQ3 = rSRQ3;
	}
	public double getChannel3() {
		return Channel3;
	}
	public void setChannel3(double channel3) {
		Channel3 = channel3;
	}
	public double getCPULoad() {
		return CPULoad;
	}
	public void setCPULoad(double cPULoad) {
		CPULoad = cPULoad;
	}
	public String getALLDownload() {
		return ALLDownload;
	}
	public void setALLDownload(String aLLDownload) {
		ALLDownload = aLLDownload;
	}
	public String getALLUpload() {
		return ALLUpload;
	}
	public void setALLUpload(String aLLUpload) {
		ALLUpload = aLLUpload;
	}
	public String getALLTotal() {
		return ALLTotal;
	}
	public void setALLTotal(String aLLTotal) {
		ALLTotal = aLLTotal;
	}
	public String getCellularDownload() {
		return CellularDownload;
	}
	public void setCellularDownload(String cellularDownload) {
		CellularDownload = cellularDownload;
	}
	public String getCellularUpload() {
		return CellularUpload;
	}
	public void setCellularUpload(String cellularUpload) {
		CellularUpload = cellularUpload;
	}
	public String getCellularTotal() {
		return CellularTotal;
	}
	public void setCellularTotal(String cellularTotal) {
		CellularTotal = cellularTotal;
	}
	public String getDeviceName() {
		return DeviceName;
	}
	public void setDeviceName(String deviceName) {
		DeviceName = deviceName;
	}
	public String getProductModel() {
		return ProductModel;
	}
	public void setProductModel(String productModel) {
		ProductModel = productModel;
	}
	public String getHardwareRevision() {
		return HardwareRevision;
	}
	public void setHardwareRevision(String hardwareRevision) {
		HardwareRevision = hardwareRevision;
	}
	public String getFirmwareVersion() {
		return FirmwareVersion;
	}
	public void setFirmwareVersion(String firmwareVersion) {
		FirmwareVersion = firmwareVersion;
	}
	public String getUptime() {
		return Uptime;
	}
	public void setUptime(String uptime) {
		Uptime = uptime;
	}
	public String getMode() {
		return Mode;
	}
	public void setMode(String mode) {
		Mode = mode;
	}
	public String getSystemMode() {
		return SystemMode;
	}
	public void setSystemMode(String systemMode) {
		SystemMode = systemMode;
	}
	public double getTemperature() {
		return Temperature;
	}
	public void setTemperature(double temperature) {
		Temperature = temperature;
	}
	public String getSlotA() {
		return SlotA;
	}
	public void setSlotA(String slotA) {
		SlotA = slotA;
	}
	public String getSlotB() {
		return SlotB;
	}
	public void setSlotB(String slotB) {
		SlotB = slotB;
	}

	
	
}
