/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelCellModemEntity;
import com.nwm.api.entities.ModelDataloggerEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.entities.TablePreferenceEntity;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ModelCellModemService;
import com.nwm.api.services.ModelDataloggerService;
import com.nwm.api.services.SitesDevicesService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/sites-devices")
public class SitesDevicesController extends BaseController {
	
	/**
	 * @description Get detail site 
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/detail")
	public Object getDetailSite(@RequestBody SitesDevicesEntity obj) {
		try {			
			SitesDevicesService service = new SitesDevicesService();
			SitesDevicesEntity getDetail = service.getDetail(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-by-id-site")
	public Object getListDeviceByIdSite(@RequestBody SitesDevicesEntity obj) {
		try {
			SitesDevicesService service = new SitesDevicesService();
			List data = service.getListDeviceByIdSite(obj);
			TablePreferenceEntity preference = service.getPreference(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size(), preference);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0, null);
		}
	}
	
	
	/**
	 * @description Get device detail by id 
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/device-detail")
	public Object getDeviceDetail(@RequestBody DeviceEntity obj) {
		try {
			
			SitesDevicesService service = new SitesDevicesService();
			DeviceEntity getDetail = service.getDeviceDetail(obj);
			
			
			if (getDetail.getId() > 0 ) {
				
				final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			    final String utcTime = sdf.format(new Date());
				
				if(getDetail.getId_device_type() == 5 && obj.getReload_ssh() == 1 && getDetail.getSsh_host() != null && getDetail.getSsh_user() != null && getDetail.getSsh_pass() != null && getDetail.getSsh_port() != null) {
					// datalogger
					ModelDataloggerService dataloggerModem = new ModelDataloggerService();
					ModelDataloggerEntity dataloggerEntity = new ModelDataloggerEntity();
					dataloggerEntity.setTime(utcTime);
					
					DeviceEntity deviceUpdateE = new DeviceEntity();
					DeviceService serviceD = new DeviceService();
					
					 // Reading SSH info
				    Session session = null;
					ChannelExec channel = null;
					String command = "cat /proc/meminfo";
					
					try {
						session = new JSch().getSession(getDetail.getSsh_user(), getDetail.getSsh_host(), Integer.parseInt(getDetail.getSsh_port()) );
						session.setPassword(getDetail.getSsh_pass());
						session.setConfig("StrictHostKeyChecking", "no");
						session.connect();

						channel = (ChannelExec) session.openChannel("exec");
						channel.setCommand(command);
						ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
						channel.setOutputStream(responseStream);
						InputStream is = channel.getInputStream();
						channel.connect();
						while (channel.isConnected()) { Thread.sleep(100); }
						
						String MemTotal = null;
						String MemFree = null;

						try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
							for (String line = br.readLine(); line != null; line = br.readLine()) {
								
								if (line.contains("MemTotal") && !line.contains("awk ")) {
									MemTotal = line.split(":")[1];
								}
								
								if (line.contains("MemFree") && !line.contains("awk ")) {
									MemFree = line.split(":")[1];
								}

							}
							String regex = "[^0-9]";
							MemTotal = MemTotal.replaceAll(regex, "");
							MemFree = MemFree.replaceAll(regex, "");  
							
							dataloggerEntity.setMemTotal(MemTotal != null ? Double.parseDouble(MemTotal.trim()): null);
							dataloggerEntity.setMemFree(MemFree != null ? Double.parseDouble(MemFree.trim()): null);
							dataloggerEntity.setId_device(getDetail.getId());	
							dataloggerModem.insertModelDatalogger(dataloggerEntity);
							
							// CPU load
							if(MemFree != null) {
								deviceUpdateE.setLast_updated(dataloggerEntity.getTime());
								deviceUpdateE.setLast_value(Double.parseDouble(MemFree));
								deviceUpdateE.setField_value1(Double.parseDouble(MemFree));
							} else {
								deviceUpdateE.setLast_updated(null);
								deviceUpdateE.setLast_value(null);
								deviceUpdateE.setField_value1(null);
							}
						}
						
						
						
						// value 3
						deviceUpdateE.setField_value2(null);
						deviceUpdateE.setField_value3(null);
						
						deviceUpdateE.setId(dataloggerEntity.getId_device());
						serviceD.updateLastUpdated(deviceUpdateE);	
						
						// converting date format for US
						Date date = new Date();
				        SimpleDateFormat sdfAmerica = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss a");
				        TimeZone tzInAmerica = TimeZone.getTimeZone(getDetail.getTimezone());
				        sdfAmerica.setTimeZone(tzInAmerica);
						
						getDetail.setLast_attempt(sdfAmerica.format(date) + " " + getDetail.getAbbreviation_std());
						List listParameters = service.getListParameters(obj);
						getDetail.setList_parameters(listParameters);
						return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);

					} finally {
						if (session != null) {
							session.disconnect();
						}
						if (channel != null) {
							channel.disconnect();
						}
					}
					
				
					
				} else if(getDetail.getId_device_type() == 10 && obj.getReload_ssh() == 1 && getDetail.getSsh_host() != null && getDetail.getSsh_user() != null && getDetail.getSsh_pass() != null && getDetail.getSsh_port() != null) {
					
					// Save device cell modem 
					ModelCellModemService serviceCellModem = new ModelCellModemService();
					ModelCellModemEntity celModemEntity = new ModelCellModemEntity();
					
					DeviceEntity deviceUpdateE = new DeviceEntity();
					DeviceService serviceD = new DeviceService();
					
					
				    celModemEntity.setTime(utcTime);
				    
				    // Reading SSH info
				    Session session = null;
					ChannelExec channel = null;
					String command = "get wan 2";
					
					ChannelExec channelcpu = null;
					String commandcpu = "get cpuload";
					
					ChannelExec channelbt = null;
					String commandbt = "get bandwidth transferred";
					
					ChannelExec channelsystem = null;
					String commandsystem = "get system";
					
					ChannelExec channeluptime = null;
					String commanduptime = "get uptime";
					
					ChannelExec channelscm = null;
					String commandscm = "support cellular-mdstatus 1";
					
					ChannelExec channelscs = null;
					String commandscs = "support cellular-simstatus 1";
					
					try {
						session = new JSch().getSession(getDetail.getSsh_user(), getDetail.getSsh_host(), Integer.parseInt(getDetail.getSsh_port()) );
						session.setPassword(getDetail.getSsh_pass());
						session.setConfig("StrictHostKeyChecking", "no");
						session.connect();

						channel = (ChannelExec) session.openChannel("exec");
						channel.setCommand(command);
						ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
						channel.setOutputStream(responseStream);
						InputStream is = channel.getInputStream();
						channel.connect();
						while (channel.isConnected()) { Thread.sleep(100); }
						
						
						channelcpu = (ChannelExec) session.openChannel("exec");
						channelcpu.setCommand(commandcpu);
						ByteArrayOutputStream responseStreamcpu = new ByteArrayOutputStream();
						channelcpu.setOutputStream(responseStreamcpu);
						InputStream iscpu = channelcpu.getInputStream();
						channelcpu.connect();
						while (channelcpu.isConnected()) { Thread.sleep(100); }
						
						channelbt = (ChannelExec) session.openChannel("exec");
						channelbt.setCommand(commandbt);
						ByteArrayOutputStream responseStreambt = new ByteArrayOutputStream();
						channelbt.setOutputStream(responseStreambt);
						InputStream isbt = channelbt.getInputStream();
						channelbt.connect();
						while (channelbt.isConnected()) { Thread.sleep(100); }
						
						
						channelsystem = (ChannelExec) session.openChannel("exec");
						channelsystem.setCommand(commandsystem);
						ByteArrayOutputStream responseStreamsystem = new ByteArrayOutputStream();
						channelsystem.setOutputStream(responseStreamsystem);
						InputStream issystem = channelsystem.getInputStream();
						channelsystem.connect();
						while (channelsystem.isConnected()) { Thread.sleep(100); }
						
						
						channeluptime = (ChannelExec) session.openChannel("exec");
						channeluptime.setCommand(commanduptime);
						ByteArrayOutputStream responseStreamuptime = new ByteArrayOutputStream();
						channeluptime.setOutputStream(responseStreamuptime);
						InputStream isuptime = channeluptime.getInputStream();
						channeluptime.connect();
						while (channeluptime.isConnected()) { Thread.sleep(100); }
						
						
						channelscm = (ChannelExec) session.openChannel("exec");
						channelscm.setCommand(commandscm);
						ByteArrayOutputStream responseStreamscm = new ByteArrayOutputStream();
						channelscm.setOutputStream(responseStreamscm);
						InputStream isscm = channelscm.getInputStream();
						channelscm.connect();
						while (channelscm.isConnected()) { Thread.sleep(100); }
						
						channelscs = (ChannelExec) session.openChannel("exec");
						channelscs.setCommand(commandscs);
						ByteArrayOutputStream responseStreamscs = new ByteArrayOutputStream();
						channelscs.setOutputStream(responseStreamscs);
						InputStream isscs = channelscs.getInputStream();
						channelscs.connect();
						while (channelscs.isConnected()) { Thread.sleep(100); }
						
						
						
						String CPULoad = null;
						String WANConnection = null;
						String ConnectionName = null;
						String ConnectionStatus = null;
						String ConnectionType = null;
						String ConnectionMethod = null;
						String IPAddress = null;
						String DefaultGateway = null;
						String DNSServers = null;
						String MTU = null;
						String IMEI = null;
						String IMSI = null;
						String ICCID = null;
						String SIMSLOT = null;
						String Band = null;
						String RSSI4 = null;
						String SINR4 = null;
						String RSRP4 = null;
						String RSRQ4 = null;
						String Channel4 = null;

						String RSSI3 = null;
						String SINR3 = null;
						String RSRP3 = null;
						String RSRQ3 = null;
						String Channel3 = null;
						
						String ALLDownload = null;
						String ALLUpload = null;
						String ALLTotal = null;
						String CellularDownload = null;
						String CellularUpload = null;
						String CellularTotal = null;
						String DeviceName = null;
						String ProductModel = null;
						String HardwareRevision = null;
						String SerialNumber = null;
						String FirmwareVersion = null;
						String uptime = null;
						String mode = null;
						String SystemMode = null;
						String Temperature = null;
						String SlotA = null;
						String SlotB = null;

						try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
							for (String line = br.readLine(); line != null; line = br.readLine()) {
								
								if (line.contains("WAN Connection [2]") && !line.contains("awk ")) {
									WANConnection = line.trim();
								}
								

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("MTU")
										&& !line.contains("awk ")) {
									MTU = line.split(":")[1];
								}

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
										&& line.contains("IP Address") && !line.contains("awk ")) {
									IPAddress = line.split(":")[1];
								}

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
										&& line.contains("Default Gateway") && !line.contains("awk ")) {
									DefaultGateway = line.split(":")[1];
								}

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
										&& line.contains("DNS Servers") && !line.contains("awk ")) {
									DNSServers = line.split(":")[1];
								}

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("IMEI")
										&& !line.contains("awk ")) {
									IMEI = line.split(":")[1];
								}


								if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("IMSI")
										&& !line.contains("awk ")) {
									IMSI = line.split(":")[1];
								}

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("ICCID")
										&& !line.contains("awk ")) {
									ICCID = line.split(":")[1];
								}

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
										&& line.contains("SIM SLOT") && !line.contains("awk ")) {
									SIMSLOT = line.split(":")[1];
								}

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
										&& line.contains("Connection Name") && !line.contains("awk ")) {
									ConnectionName = line.split(":")[1];
								}

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
										&& line.contains("Connection Status") && !line.contains("awk ")) {
									ConnectionStatus = line.split(":")[1];
								}

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
										&& line.contains("Connection Type") && !line.contains("awk ")) {
									ConnectionType = line.split(":")[1];
								}

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
										&& line.contains("Connection Method") && !line.contains("awk ")) {
									ConnectionMethod = line.split(":")[1];
								}

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("Band")
										&& !line.contains("awk ")) {
									Band = line.split(":")[1];
									Band = Band.trim();
								}

								if (Band != null && Band.equals("LTE Band 4 (AWS 1700/2100 MHz)")) {
									if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
											&& line.contains("RSSI") && !line.contains("awk ")) {
										RSSI4 = line.split(":")[1];
									}

									if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
											&& line.contains("SINR") && !line.contains("awk ")) {
										SINR4 = line.split(":")[1];
									}

									if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
											&& line.contains("RSRP") && !line.contains("awk ")) {
										RSRP4 = line.split(":")[1];
									}

									if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
											&& line.contains("RSRQ") && !line.contains("awk ")) {
										RSRQ4 = line.split(":")[1];
									}

									if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
											&& line.contains("Channel") && !line.contains("awk ")) {
										Channel4 = line.split(":")[1];
									}

								} else if (Band != null && Band.equals("LTE Band 13 (700 MHz)")) {
									if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
											&& line.contains("RSSI") && !line.contains("awk ")) {
										RSSI3 = line.split(":")[1];
									}

									if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
											&& line.contains("SINR") && !line.contains("awk ")) {
										SINR3 = line.split(":")[1];
									}

									if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
											&& line.contains("RSRP") && !line.contains("awk ")) {
										RSRP3 = line.split(":")[1];
									}

									if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
											&& line.contains("RSRQ") && !line.contains("awk ")) {
										RSRQ3 = line.split(":")[1];
									}

									if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
											&& line.contains("Channel") && !line.contains("awk ")) {
										Channel3 = line.split(":")[1];
									}
								}
							}

							celModemEntity.setIPAddress( IPAddress != null ? IPAddress.trim(): null);
							celModemEntity.setDefaultGateway(DefaultGateway !=null ? DefaultGateway.trim(): null);
							celModemEntity.setDNSServers(DNSServers != null ? DNSServers.trim(): null);
							celModemEntity.setMTU(MTU != null ? MTU.trim(): null);
							celModemEntity.setIMEI(IMEI != null ? IMEI.trim(): null);
							celModemEntity.setIMSI(IMSI != null ? IMSI.trim(): null);
							celModemEntity.setICCID(ICCID != null ? ICCID.trim(): null);
							celModemEntity.setSIMSLOT(SIMSLOT != null ? SIMSLOT.trim(): null);
							celModemEntity.setConnectionName(ConnectionName != null ? ConnectionName.trim(): null);
							celModemEntity.setConnectionStatus(ConnectionStatus != null ? ConnectionStatus.trim(): null);
							celModemEntity.setConnectionType(ConnectionType != null ? ConnectionType.trim(): null);
							celModemEntity.setConnectionMethod(ConnectionMethod != null ? ConnectionMethod.trim(): null);
//							
							RSSI3 = RSSI3.replaceAll("dBm", "");
							SINR3 = SINR3.replaceAll("dB", "");
							RSRP3 = RSRP3.replaceAll("dBm", "");
							RSRQ3 = RSRQ3.replaceAll("dB", "");
							celModemEntity.setRSSI3(RSSI3 != null ? Double.parseDouble(RSSI3.trim()): null);
							celModemEntity.setSINR3(SINR3 != null ? Double.parseDouble(SINR3.trim()): null);
							celModemEntity.setRSRP3(RSRP3 != null ? Double.parseDouble(RSRP3.trim()): null);
							celModemEntity.setRSRQ3(RSRQ3 != null ? Double.parseDouble(RSRQ3.trim()): null);
							celModemEntity.setChannel3(Channel3 != null ? Double.parseDouble(Channel3.trim()): null);
							RSSI4 = RSSI4.replaceAll("dBm", "");
							SINR4 = SINR4.replaceAll("dB", "");
							RSRP4 = RSRP4.replaceAll("dBm", "");
							RSRQ4 = RSRQ4.replaceAll("dB", "");
							celModemEntity.setRSSI4(RSSI4 != null ? Double.parseDouble(RSSI4.trim()): null);
							celModemEntity.setSINR4(SINR4 != null ? Double.parseDouble(SINR4.trim()): null);
							celModemEntity.setRSRP4(RSRP4 != null ? Double.parseDouble(RSRP4.trim()): null);
							celModemEntity.setRSRQ4(RSRQ4 != null ? Double.parseDouble(RSRQ4.trim()): null);
							celModemEntity.setChannel4(Channel4 != null ? Double.parseDouble(Channel4.trim()): null);
							celModemEntity.setCPULoad(0);
							
							celModemEntity.setId_device(getDetail.getId());	
							serviceCellModem.insertModelCellModem(celModemEntity);
						}
						
						
						try (BufferedReader br = new BufferedReader(new InputStreamReader(iscpu))) {
							for (String line = br.readLine(); line != null; line = br.readLine()) {
								
								if (line.contains("get wan 2 WAN Connection [2]") && !line.contains("awk ")) {
									WANConnection = line.trim();
								}
								

								if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("cpuload CPU Load")
										&& !line.contains("awk ")) {
									CPULoad = line.split(":")[1];
									CPULoad = CPULoad.replace("%", "");
								}
							}
							
							celModemEntity.setCPULoad( CPULoad != null ? Double.parseDouble(CPULoad): 0);
							serviceCellModem.insertModelCellModem(celModemEntity);
							
							// CPU load
							if(CPULoad != null) {
								deviceUpdateE.setLast_updated(celModemEntity.getTime());
								deviceUpdateE.setLast_value(Double.parseDouble(CPULoad));
								deviceUpdateE.setField_value1(Double.parseDouble(CPULoad));
							} else {
								deviceUpdateE.setLast_updated(null);
								deviceUpdateE.setLast_value(null);
								deviceUpdateE.setField_value1(null);
							}
							
						}
						
						
						
						try (BufferedReader br = new BufferedReader(new InputStreamReader(isbt))) {
							for (String line = br.readLine(); line != null; line = br.readLine()) {
								
								if (line != null && line.contains("ALL WAN Connections") && !line.contains("awk ")) {
									String lineText = line.split("ALL WAN Connections")[1];
									
									String[] ary = lineText.split(" ");
									ary = Arrays.stream(ary).filter(x -> !StringUtils.isBlank(x)).toArray(String[]::new);
									ALLDownload = ary[0] + " " + ary[1];
									ALLUpload = ary[2] + " " + ary[3];
									ALLTotal = ary[4] + " " + ary[5];
								}
								
								if (line != null && line.contains("Cellular") && !line.contains("awk ")) {
									String lineText = line.split("Cellular")[1];
									
									String[] ary = lineText.split(" ");
									ary = Arrays.stream(ary).filter(x -> !StringUtils.isBlank(x)).toArray(String[]::new);
									CellularDownload = ary[0] + " " + ary[1];
									CellularUpload = ary[2] + " " + ary[3];
									CellularTotal = ary[4] + " " + ary[5];
								}
							}
							
							celModemEntity.setALLDownload(ALLDownload != null ? ALLDownload : null);
							celModemEntity.setALLUpload(ALLUpload != null ? ALLUpload : null);
							celModemEntity.setALLTotal(ALLTotal != null ? ALLTotal : null);
							celModemEntity.setCellularDownload(CellularDownload != null ? CellularDownload : null);
							celModemEntity.setCellularUpload(CellularUpload != null ? CellularUpload : null);
							celModemEntity.setCellularTotal(CellularTotal != null ? CellularTotal : null);
							
							
							serviceCellModem.insertModelCellModem(celModemEntity);
						}
						
						
						
						try (BufferedReader br = new BufferedReader(new InputStreamReader(issystem))) {
							for (String line = br.readLine(); line != null; line = br.readLine()) {
								
								if (line != null && line.contains("Device Name") && !line.contains("awk ")) {
									DeviceName = line.split(":")[1];
								}
								
								if (line != null && line.contains("Product Model") && !line.contains("awk ")) {
									ProductModel = line.split(":")[1];
								}
								
								if (line != null && line.contains("Hardware Revision") && !line.contains("awk ")) {
									HardwareRevision = line.split(":")[1];
								}
								
								if (line != null && line.contains("Serial Number") && !line.contains("awk ")) {
									SerialNumber = line.split(":")[1];
								}
								
								if (line != null && line.contains("Firmware Version") && !line.contains("awk ")) {
									FirmwareVersion = line.split(":")[1];
								}
								
							}
							
							celModemEntity.setDeviceName(DeviceName != null ? DeviceName : null);
							celModemEntity.setProductModel(ProductModel != null ? ProductModel : null);
							celModemEntity.setHardwareRevision(HardwareRevision != null ? HardwareRevision : null);
							celModemEntity.setSerialnumber(SerialNumber != null ? SerialNumber : null);
							celModemEntity.setFirmwareVersion(FirmwareVersion != null ? FirmwareVersion : null);
							serviceCellModem.insertModelCellModem(celModemEntity);
						}
						
						
						try (BufferedReader br = new BufferedReader(new InputStreamReader(isuptime))) {
							for (String line = br.readLine(); line != null; line = br.readLine()) {
								
								if (line != null && line.contains("uptime Uptime") && !line.contains("awk ")) {
									uptime = line.split(":")[1];
								}
							}
							
							celModemEntity.setUptime(uptime != null ? uptime : null);
							serviceCellModem.insertModelCellModem(celModemEntity);
						}
						
						
						
						try (BufferedReader br = new BufferedReader(new InputStreamReader(isscm))) {
							for (String line = br.readLine(); line != null; line = br.readLine()) {
								
								if (line != null && line.contains("Mode:") && !line.contains("awk ")) {
									mode = line.split("Mode:")[1];
								}
								
								if (line != null && line.contains("Temperature:") && !line.contains("awk ")) {
									Temperature = line.split("Temperature:")[1];
								}
								
								if (line != null && line.contains("System mode:") && !line.contains("awk ")) {
									String lineText = line.split("System mode:")[1];
									lineText = lineText.trim();
									SystemMode = lineText.split("     ")[0];
								}
								
								// getTemperature
								if(Temperature != null) {
									deviceUpdateE.setField_value2(Double.parseDouble(Temperature));
								} else {
									deviceUpdateE.setField_value2(null);
								}
							}
							
							celModemEntity.setMode(mode != null ? mode : null);
							celModemEntity.setSystemMode(SystemMode != null ? SystemMode : null);

							celModemEntity.setTemperature( Temperature != null ? Double.parseDouble(Temperature): 0);
							serviceCellModem.insertModelCellModem(celModemEntity);
						}
						
						
						
						try (BufferedReader br = new BufferedReader(new InputStreamReader(isscs))) {
							for (String line = br.readLine(); line != null; line = br.readLine()) {
								
								if (line != null && line.contains("Slot A -") && !line.contains("awk ")) {
									SlotA = line.split("Slot A -")[1];
								}
								
								if (line != null && line.contains("Slot B -") && !line.contains("awk ")) {
									SlotB = line.split("Slot B -")[1];
								}
							}
							
							celModemEntity.setSlotA(SlotA != null ? SlotA : null);
							celModemEntity.setSlotB(SlotB != null ? SlotB : null);
							serviceCellModem.insertModelCellModem(celModemEntity);
						}
						
						// value 3
						deviceUpdateE.setField_value3(null);
						
						deviceUpdateE.setId(celModemEntity.getId_device());
						serviceD.updateLastUpdated(deviceUpdateE);
						
						
						// converting date format for US
						Date date = new Date();
				        SimpleDateFormat sdfAmerica = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss a");
				        TimeZone tzInAmerica = TimeZone.getTimeZone(getDetail.getTimezone());
				        sdfAmerica.setTimeZone(tzInAmerica);
						
						getDetail.setLast_attempt(sdfAmerica.format(date) + " " + getDetail.getAbbreviation_std());
						List listParameters = service.getListParameters(obj);
						getDetail.setList_parameters(listParameters);
						return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);

					} finally {
						if (session != null) {
							session.disconnect();
						}
						if (channel != null) {
							channel.disconnect();
						}
						
						if (channelcpu != null) {
							channelcpu.disconnect();
						}

						if (channelbt != null) {
							channelbt.disconnect();
						}

						if (channelsystem != null) {
							channelsystem.disconnect();
						}
						
						if (channeluptime != null) {
							channeluptime.disconnect();
						}

						if (channelscm != null) {
							channelscm.disconnect();
						}

						if (channelscs != null) {
							channelscs.disconnect();
						}
					}
				    
				} else {
					// converting date format for US
					Date date = new Date();
			        SimpleDateFormat sdfAmerica = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss a");
			        TimeZone tzInAmerica = TimeZone.getTimeZone(getDetail.getTimezone());
			        sdfAmerica.setTimeZone(tzInAmerica);
					
					getDetail.setLast_attempt(sdfAmerica.format(date) + " " + getDetail.getAbbreviation_std());
					List listParameters = service.getListParameters(obj);
					getDetail.setList_parameters(listParameters);
					return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
				}
				
			    
			    
			    
				
				
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
}
