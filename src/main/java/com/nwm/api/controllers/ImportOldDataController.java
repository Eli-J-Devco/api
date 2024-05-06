/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import javax.validation.Valid;
import org.apache.commons.lang3.time.StopWatch;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.nwm.api.entities.FileImportDataOldEntity;
import com.nwm.api.entities.ImportOldDataEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.services.ImportOldDataService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/import-old-data")
public class ImportOldDataController extends BaseController {

//	Integer count = 0;
	/**
	 * @description Get all site by id_employee
	 * @author long.pham
	 * @since 2022-12-21
	 * @return data 
	 */
	@PostMapping("/get-list-all-site-by-employee")
	public Object getDropdownList(@RequestBody ImportOldDataEntity obj) {
		try {
			ImportOldDataService service = new ImportOldDataService();
			List data = service.getAllSiteByEmployeeId(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description Get list site by id_customer
	 * @author long.pham
	 * @since 2020-10-09
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-by-site-id")
	public Object getList(@RequestBody ImportOldDataEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			ImportOldDataService service = new ImportOldDataService();
			List data = service.getAllDeviceBySiteId(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	

	
	/**
	 * @description Get list file import
	 * @author long.pham
	 * @since 2023-08-03
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-file-import")
	public Object getListFileImport(@RequestBody FileImportDataOldEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			ImportOldDataService service = new ImportOldDataService();
			List data = service.getListFileImport(obj);
			int totalRecord = service.getTotalRecord(obj);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description save error level
	 * @author long.pham
	 * @since 2021-02-26
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody ImportOldDataEntity obj) {
		try {
			ImportOldDataService service = new ImportOldDataService();
			ImportOldDataEntity data = service.insertImportOldData(obj);
			if (data != null) {
				if (data.getRow() > 0) {
					return this.jsonResult(false, "Error date format at row " + data.getRow(), null, 0);
				}
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description save error level
	 * @author long.pham
	 * @since 2023-05-31
	 * @param screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save-upload")
	public Object saveUpload(@Valid @RequestBody ImportOldDataEntity obj) {
		try {
			ImportOldDataService service = new ImportOldDataService();
			String fileName = "";
			String saveDir = "";

			if (!Lib.isBlank(obj.getFile_upload())) {
				saveDir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName,
						Constants.uploadFilePathConfigKeyOlddata);
				String newFileName = obj.getFilename().toString(); 
				newFileName = newFileName.substring(0, newFileName.indexOf('.'));
				fileName = newFileName + "-"+ randomAlphabetic(16);
				String saveFileName = Lib.uploadFromBase64(obj.getFile_upload(), fileName, saveDir);
				String fileUrl = saveDir + "/" + saveFileName;
				if(fileUrl == null){ return this.jsonResult(true, "Upload file error", null, 1); }
				// Insert file to database
				FileImportDataOldEntity item = new FileImportDataOldEntity();
				item.setId_device(obj.getId_device());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		        // set UTC time zone by using SimpleDateFormat class  
		        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));  
		        item.setTime_upload(sdf.format(new Date()));
		        item.setFilename(saveFileName);
		        item.setId_employee(obj.getId_employee());
		        item.setStatus(1);
		        FileImportDataOldEntity result = service.insertFileImportDataOld(item);
		        
				this.renderDataVirtualDevice(result.getId());
		
				return this.jsonResult(true, "Upload file import old data successfully", null, 1);
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}

		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description run event upload old data asynchronously
	 * @author long.pham
	 * @since 2023-08-03
	 * @return {}
	 */
	public CompletableFuture<Boolean> renderDataVirtualDevice(Integer id) {
		CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
			try {
				if(id == null) return false;
				int idFile = id;
				
				FileImportDataOldEntity itemFile = new FileImportDataOldEntity();
				itemFile.setId(idFile);
				
				ImportOldDataService service = new ImportOldDataService();
				FileImportDataOldEntity dataFile = service.getDetailFileUploadDataOld(itemFile);
				ImportOldDataEntity obj = new ImportOldDataEntity();
				
				HashSet<String> setDate = new HashSet<String> ();
				
				if( dataFile.getId() >= 0 ) {
					obj.setId_site(dataFile.getId_site());
					obj.setTable_name(dataFile.getDatatablename());
					obj.setDevice_group_table(dataFile.getDevice_group_table());
					
					String saveDir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyOlddata);
					String fileUrl = saveDir + "/" + dataFile.getFilename();
					try (InputStream is = new FileInputStream(fileUrl); ReadableWorkbook wb = new ReadableWorkbook(is)) {
						StopWatch watch = new StopWatch();
						watch.start();
		
						wb.getSheets().forEach(sheet -> {
							try (Stream<Row> rows = sheet.openStream()) {
								long rowTotal = sheet.openStream().mapToLong(e -> 1L).sum();
								rows.skip(1).forEach(r -> {
									if(r.getRowNum() == 2) { itemFile.setStart_date(r.getCellText(1).toString()); }
									
									List<Object> result = new ArrayList<Object>();
									HashMap<String, String> rowItem = new HashMap<String, String>();
									if (Lib.isDateValid(r.getCellText(1).toString())) {
										rowItem.put("time", r.getCellText(1).toString());
										rowItem.put("local_time", r.getCellText(1).toString());
										rowItem.put("datatablename", dataFile.getDatatablename());
										rowItem.put("id_device", r.getCellText(2).toString());
										rowItem.put("error", r.getCellText(3).toString());
										rowItem.put("low_alarm", r.getCellText(4).toString());
										rowItem.put("high_alarm", r.getCellText(5).toString());
										String time = r.getCellText(1).toString();
										
										if (time == null || !time.matches("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) ([2][0-3]|[0-1][0-9]|[1-9]):[0-5][0-9]:([0-5][0-9]|[6][0])$")) {
											setDate.clear();
								    	}
	
										String[] parts = time.split(" ");
										String start = parts[0];
										setDate.add(start);
										
										switch (dataFile.getDevice_group_table()) {
										
										case "model_ae_refusol":
											service.setModelAeRefusol(rowItem, r);
											break;
										
										case "model_sunny_central_class9775_inverter":
											service.setModelSunnyCentralClass9775Inverter(rowItem, r);
											break;
											
										case "model_satcon_powergate_225_inverter":
											service.setModelSatconPowergate225Inverter(rowItem, r);
											break;
											
										case "model_meter_ion_8600":
											service.setModelMeterIon8600(rowItem, r);
											break;
										
										case "model_meter_ion_8600v1":
											service.setModelMeterIon8600V1(rowItem, r);
											break;	
										
										case "model_meter_ion_8600v2":
											service.setModelMeterIon8600V2(rowItem, r);
											break;	
											
										case "model_hukseflux_sr30d1_deviceclass_v0":
											service.setModelHukselfluxSr30d1DeviceclassV0(rowItem, r);	
											break;
											
										case "model_imtsolar_tmodul_class8006":
											service.setDataModelIMTSolarTmodulClass8006(rowItem, r);
											break;
											
										case "model_xantrex_gt100_250_500":
											service.setModelXantrexGT100250500(rowItem, r);
											break;
		
										case "model_tti_tracker":
											service.setModelTTiTracker(rowItem, r);	
											break;
											
										case "model_solectria_sgi_226ivt":
											service.setModelSolectriaSGI226IVT(rowItem, r);	
											break;
											
										case "model_pv_powered_35_50_260_500kw_inverter":
											service.setModelPVPowered3550260KWInverter(rowItem, r);
											break;
											
										case "model_lufft_class8020":
											service.setModelLufftClass8020(rowItem, r);	
											break;
											
										case "model_lufft_ws501_umb_weather":
											service.setModelLufftWS501UMBWeather(rowItem, r);										
											break;
											
										case "model_abb_trio_class6210":
											service.setModelAbbTrioClass6210(rowItem, r);
											break;
											
										case "model_elkor_production_meter":
											service.setModelElkorProductionMeter(rowItem, r);
											break;
											
										case "model_w_kipp_zonen_rt1":
											service.setModelWKippZonenRT1(rowItem, r);
											break;
											
										case "model_elkor_wattson_pv_meter":
											service.setModelElkorWattsonPVMeter(rowItem, r);	
											break;
											
										case "model_satcon_pvs357_inverter":
											service.setModelSatconPvs357Inverter(rowItem, r);
											break;
											
										case "model_veris_industries_e51c2_power_meter":
											service.setModelVerisIndustriesE51c2PowerMeter(rowItem, r);
											break;
											
										case "model_chint_solectria_inverter_class9725":
											service.setModelChintSolectriaInverterClass9725(rowItem, r);
											break;
											
										case "model_rt1_class30000":
											service.setModelRT1Class30000(rowItem, r);
											break;
											
										case "model_pyranometer_poa":
											service.setModelPyranometerPOA(rowItem, r);
											break;
											
											
											
										case "model_advanced_energy_solaron":
											service.setModelAdvancedEnergySolaron(rowItem, r);
											break;
											
										case "model_imtsolar_class8000":
											service.setModelIMTSolarClass8000(rowItem, r);
											break;
											
										case "model_pvp_inverter":
											service.setModelPVPInverter(rowItem, r);
											break;
											
										case "model_ivt_solaron_ext":
											service.setModelIVTSolaronEXT(rowItem, r);
											break;
		
										case "model_kippzonen_rt1_class8009":
											service.setModelKippZonenRT1Class8009(rowItem, r);
											break;
											
										case "model_shark100":
											service.setModelShark100(rowItem, r);
											break;
											
										case "model_xantrex_inverter":
											service.setModelXantrexInverter(rowItem, r);
											break;
											
										case "model_poa_temp":
											service.setModelPoaTemp(rowItem, r);
											break;
											
										case "model_eri_weather_icp_class8050":
											service.setModelERIWeatherICPClass8050(rowItem, r);
											break;
											
										case "model_elster_a1700":
											service.setModelElsterA1700(rowItem, r);
											break;
											
										case "model_dts_measurelogic_demand_meter":
											service.setModelDTSMeasurelogicDemandMeter(rowItem, r);
											break;
											
										case "model_janitza_umg604pro":
											service.setModelJanitzaUmg604pro(rowItem, r);
											break;
											
										case "model_acu_rev_production_meter":
											service.setModelAcuRevProductionMeter(rowItem, r);
											break;
										
										case "model_solaredge_inverter":
											service.setModelSolaredgeInverter(rowItem, r);
											break;
											
										case "model_phoenix_contact_quint_ups":
											service.setModelPhoenixContactQuintUPS(rowItem, r);
											break;
										
										case "model_sma_inverter_12_15_20_24_30tlus10":
											service.setModelSmaInverterStp1215202430Tlus10(rowItem, r);
											break;
											
										case "model_meter_ion_6200":
											service.setModelMeterIon6200(rowItem, r);
											break;
										
										case "model_acuvim_IIR":
											service.setModelAcuvimIIR(rowItem, r);
											break;	
											
										}
										
		
										result.add(rowItem);
										obj.setDataList(result);
										ImportOldDataEntity insert = service.insertImportOldData(obj);
										
										// update file data 
										FileImportDataOldEntity updateFileImportRow = new FileImportDataOldEntity();
										updateFileImportRow.setId(itemFile.getId());
										updateFileImportRow.setStatus(2);
										updateFileImportRow.setTotal_row((int) rowTotal);
										updateFileImportRow.setTotal_complete_row(r.getRowNum());
										updateFileImportRow.setTotal_error_row(0);
										service.updateFileReportDataRow(updateFileImportRow);
									}
		
								});
		
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								FileImportDataOldEntity getRowComplete = service.getDetailFileUploadDataOld(itemFile);
								
								obj.setId(dataFile.getId_device());
								obj.setId_device_type(dataFile.getId_device_type());
								
								for (String s : setDate) {	
									String[] year = s.split("-");
									obj.setYear(year[0]);
									obj.setStart_date(s + " 08:00:00");
									obj.setEnd_date(s + " 17:59:59");
									obj.setDatatablename(obj.getTable_name());
									obj.setId(dataFile.getId_device()); 
									
									service.insertSiteDataReport(obj);
									
								}
								// update file data 
								FileImportDataOldEntity updateRow = new FileImportDataOldEntity();
								updateRow.setId(getRowComplete.getId());
								updateRow.setStatus(3);
								updateRow.setTotal_row(getRowComplete.getTotal_row());
								updateRow.setTotal_complete_row(getRowComplete.getTotal_complete_row());
								updateRow.setTotal_error_row( getRowComplete.getTotal_row() - getRowComplete.getTotal_complete_row() );
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));  
								updateRow.setTime_complete(sdf.format(new Date()));
								service.updateFileReportDataRow(updateRow);
							}
							watch.stop();
						});
						
						// Run event update data virtual table
						String domainCronJob = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.domainCronJob);
						DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date currentDate = new Date();
						Date date1 = null;
						Date date2 = null;
						String startDate = itemFile.getStart_date();
						int total_day = 2;
						if (startDate != null) {
							String endDate = simpleDateFormat.format(currentDate);
							date1 = simpleDateFormat.parse(startDate);
							date2 = simpleDateFormat.parse(endDate);
							long getDiff = date2.getTime() - date1.getTime();
							long getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
							total_day = Integer.parseInt(String.valueOf(getDaysDiff));
						}
						
						String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
						String url = domainCronJob + "/api-server/virtual-device/render-data?token=" + privateKey + "&id_site="+ dataFile.getId_site() + "&total_day="+total_day;
						String command = "curl -X GET " + url;
						Runtime.getRuntime().exec(command);
						
						return true;
					} catch (Exception e) {
						return false;
					}
				}
	
				return true;
			} catch (Exception e) {
				log.error(e);
				return false;
			}
		});
		
		return future;
	}
	
	
	
	
	/**
	 * @description delete role
	 * @author long.pham
	 * @since 2020-12-30
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-data-old")
	public Object deleteDataOld(@Valid @RequestBody FileImportDataOldEntity obj) {
		ImportOldDataService service = new ImportOldDataService();
		try {
			int result = service.deleteDataOld(obj);
			if (result == 1) {
				BatchJobService serviceBathJob = new BatchJobService();
				SiteEntity siteItem = new SiteEntity();
				siteItem.setId(obj.getId_site());
				siteItem.setStart_date(obj.getStart_date());
				siteItem.setEnd_date(obj.getEnd_date());
				siteItem.setTime_zone_value(obj.getTimezone_value());
				
				serviceBathJob.insertDataGenerateReport(siteItem);
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	
}
