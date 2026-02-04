package com.nwm.api.eventListeners;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelBaseEntity;
import com.nwm.api.entities.TimeValueDTO;
import com.nwm.api.events.LowProductionAlertEvent;
import com.nwm.api.events.NoCommunicationAlertEvent;
import com.nwm.api.events.SolarTrackerNoMotionAlertEvent;
import com.nwm.api.events.WrongEneryAlertEvent;
import com.nwm.api.utils.Constants.ModbusError;
import com.nwm.api.utils.Constants.UploadingDataIntervals;

@Component
@Async
public class AlertEventListener extends DB {
	
	/**
	 * @description Low production alert condition:
	 * - All comparison ratio in latest 4 hours are less than or equal 70%.
	 * - Time difference between latest and oldest in latest 4 hours is larger or equal 4 hours (for case there are not enough data in latest 4 hours).
	 * @author Hung.Bui
	 * @since 2023-08-17
	 */
	@EventListener
    public void lowProductionAlertEventListener(LowProductionAlertEvent event) {
		DeviceEntity device = event.getDevice();
		ModelBaseEntity data = event.getData();
		List<DeviceEntity> devicesBySite = event.getDevicesBySite();
		if (ModbusError.fromValue(data.getError()) == ModbusError.DEVICE_FAILED_TO_RESPOND) return;
		
		SqlSession session = this.beginTransaction();
		
		try {
			int noProduction = session.selectOne("BatchJob.checkNoProductionAlertlExist", device);
			if (noProduction > 0) return;
			
			device.setError_code("1002");
			Integer lowProduction = session.selectOne("Device.getErrorId", device);
			if (lowProduction == null) return;
			
			List<HashMap<String, Object>> latest4HoursComparisonRatioDataList = new ArrayList<HashMap<String, Object>>(); 
			List poaDevicesList = devicesBySite.stream().filter(item -> item.getId_device_type() == 4).collect(Collectors.toList());
			if (!poaDevicesList.isEmpty()) {
				device.setGroupWeather(poaDevicesList);
				latest4HoursComparisonRatioDataList = queryForList("Device.getComparisonRatioHavingPOA", device);
			} else {
				List powerDevicesList = devicesBySite.stream().filter(item -> item.getId_device_type() == device.getId_device_type()).collect(Collectors.toList());
				if (powerDevicesList.size() <= 1) return;
				device.setGroupInverter(powerDevicesList);
				latest4HoursComparisonRatioDataList = queryForList("Device.getComparisonRatioNoPOA", device);
			}
			if (latest4HoursComparisonRatioDataList == null || latest4HoursComparisonRatioDataList.isEmpty()) return;
			
			LocalDateTime startTime = LocalDateTime.parse(latest4HoursComparisonRatioDataList.get(0).get("time_full").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			LocalDateTime endTime = LocalDateTime.parse(latest4HoursComparisonRatioDataList.get(latest4HoursComparisonRatioDataList.size() - 1).get("time_full").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			long hours = ChronoUnit.HOURS.between(startTime, endTime);
			boolean isComparisonRatioLessThanOrEqual70 = latest4HoursComparisonRatioDataList.stream().allMatch(item -> item.get("comparison_ratio") != null ? Double.parseDouble(item.get("comparison_ratio").toString()) <= 70 : false);
			
			AlertEntity alertDeviceItem = new AlertEntity();
			alertDeviceItem.setId_device(device.getId());
			alertDeviceItem.setStart_date(data.getTime());
			alertDeviceItem.setId_error(lowProduction);
			
			if (hours >= 4 && isComparisonRatioLessThanOrEqual70) {
				boolean checkAlertExist = (int) queryForObject("BatchJob.checkAlertlExist", alertDeviceItem) > 0;
				if (!checkAlertExist) {
					insert("BatchJob.insertAlert", alertDeviceItem);
				}
			} else {
				// Close alert
				AlertEntity checkAlertExist = (AlertEntity) queryForObject("BatchJob.getAlertDetail", alertDeviceItem);
				if (checkAlertExist != null && checkAlertExist.getId() > 0) {
					alertDeviceItem.setEnd_date(data.getTime());
					alertDeviceItem.setId(checkAlertExist.getId());
					update("BatchJob.updateCloseAlert", alertDeviceItem);
				}
			}
			
			session.commit();
		} catch (Exception ex) {
			session.rollback();
			log.error("AlertEventListener.lowProductionAlertEventListener", ex);
		} finally {
			session.close();
		}
    }
	
	/**
	 * @description Wrong energy alert is triggered when energy is out of range.
	 * @author Hung.Bui
	 * @since 2024-12-03
	 */
	@EventListener
    public void wrongEnergyAlertEventListener(WrongEneryAlertEvent event) {
		DeviceEntity device = event.getDevice();
		ModelBaseEntity data = event.getData();
		if (ModbusError.fromValue(data.getError()) == ModbusError.DEVICE_FAILED_TO_RESPOND) return;
		
		SqlSession session = this.beginTransaction();
		
		try {
			AlertEntity alert = new AlertEntity();
			alert.setId_device(device.getId());
			alert.setId_device_group(device.getId_device_group());
			alert.setError_code("1003");
			
			Integer errorId = session.selectOne("Device.getErrorId", alert);
			if (errorId == null) return;
			alert.setId_error(errorId);
			
			if (data.getMeasuredProduction() < -10 || data.getMeasuredProduction() > 500) {
				boolean isAlertExist = (int) session.selectOne("BatchJob.checkAlertlExist", alert) > 0;
				if (isAlertExist) return;
				alert.setStart_date(data.getTime());
				session.insert("BatchJob.insertAlert", alert);
			} else {
				// Close alert
				AlertEntity openedAlert = session.selectOne("BatchJob.getAlertDetail", alert);
				if (openedAlert == null || openedAlert.getId() == 0) return;
				
				alert.setEnd_date(data.getTime());
				alert.setId(openedAlert.getId());
				session.update("BatchJob.updateCloseAlert", alert);
			}
			
			session.commit();
		} catch (Exception ex) {
			session.rollback();
			log.error("AlertEventListener.wrongEnergyAlertEventListener", ex);
		} finally {
			session.close();
		}
    }
	
	/**
	 * @description No motion alert is triggered when a tracker detects no motion (shows the same value) for over 24 hours.
	 * The alert is cleared once the tracker has started moving for more than 2 hours.
	 * @author Hung.Bui
	 * @since 2026-01-08
	 */
	@EventListener
    public void solarTrackerNoMotionAlertEventListener(SolarTrackerNoMotionAlertEvent event) {
		DeviceEntity device = event.getDevice();
		ModelBaseEntity data = event.getData();
		if (ModbusError.fromValue(data.getError()) == ModbusError.DEVICE_FAILED_TO_RESPOND) return;
		
		SqlSession session = this.beginTransaction();
		
		try {
			String trackerAngle = device.getField_value_default();
			if (Objects.isNull(trackerAngle)) return;
			
			int dataSendTime = UploadingDataIntervals.fromValue(device.getData_send_time()).getInterval();
			DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime current = LocalDateTime.parse(data.getTime(), inputDateFormat).withSecond(0).minusMinutes(LocalDateTime.parse(data.getTime(), inputDateFormat).getMinute() % dataSendTime);
			LocalDateTime _24HoursBeforeCurrent = current.minusDays(1);
			
			device.setStart_date(_24HoursBeforeCurrent.format(inputDateFormat));
			device.setEnd_date(data.getTime());
			List<TimeValueDTO> _24HourData = session.selectList("Device.getDataFor24Hours", device);
			Optional<TimeValueDTO> comparingData = _24HourData.stream().filter(item -> Objects.nonNull(item.getValue())).findFirst();
			if (!comparingData.isPresent()) return;
			
			Double comparingValue = comparingData.get().getValue();
			boolean isNoMotion = false;
			
			for (int i = 0; i < _24HourData.size(); i++) {
				TimeValueDTO item = _24HourData.get(i);
				if (!_24HoursBeforeCurrent.equals(item.getTime())) break;
				if (Objects.isNull(item.getValue())) break;
				if (!item.getValue().equals(comparingValue)) break;
				_24HoursBeforeCurrent = _24HoursBeforeCurrent.plus(dataSendTime, ChronoUnit.MINUTES);
				if (i < _24HourData.size() - 1) continue;
				isNoMotion = true;
			}
			
			AlertEntity alert = new AlertEntity();
			alert.setId_device(device.getId());
			alert.setId_device_group(device.getId_device_group());
			alert.setError_code("1004");
			
			Integer errorId = session.selectOne("Device.getErrorId", alert);
			if (errorId == null) return;
			alert.setId_error(errorId);
			
			if (isNoMotion) {
				boolean isAlertExist = (int) session.selectOne("BatchJob.checkAlertlExist", alert) > 0;
				if (isAlertExist) return;
				alert.setStart_date(data.getTime());
				session.insert("BatchJob.insertAlert", alert);
			} else {
				// Close alert
				AlertEntity openedAlert = session.selectOne("BatchJob.getAlertDetail", alert);
				if (openedAlert == null || openedAlert.getId() == 0) return;
				
				List<TimeValueDTO> _2HourData = _24HourData.stream().filter(item -> Duration.between(item.getTime().toInstant(ZoneOffset.UTC), current.toInstant(ZoneOffset.UTC)).toMinutes() <= 120).collect(Collectors.toList());
				LocalDateTime _2HoursBeforeCurrent = current.minusHours(2);
				
				for (int i = 0; i < _2HourData.size(); i++) {
					TimeValueDTO item = _2HourData.get(i);
					if (!_2HoursBeforeCurrent.equals(item.getTime())) return;
					if (Objects.isNull(item.getValue())) return;
					if (!item.getValue().equals(_2HourData.get(0).getValue())) break;
					_2HoursBeforeCurrent = _2HoursBeforeCurrent.plus(dataSendTime, ChronoUnit.MINUTES);
					if (i == _2HourData.size() - 1) return;
				}
				
				alert.setEnd_date(data.getTime());
				alert.setId(openedAlert.getId());
				session.update("BatchJob.updateCloseAlert", alert);
			}
			
			session.commit();
		} catch (Exception ex) {
			session.rollback();
			log.error("AlertEventListener.solarTrackerNoMotionAlertEventListener", ex);
		} finally {
			session.close();
		}
    }
	
	/**
	 * @description No communication alert is triggered when device lost connection.
	 * @author Hung.Bui
	 * @since 2026-02-03
	 */
	@EventListener
    public void noCommunicationAlertEventListener(NoCommunicationAlertEvent event) {
		DeviceEntity device = event.getDevice();
		ModelBaseEntity data = event.getData();
		SqlSession session = this.beginTransaction();
		
		try {
			AlertEntity alert = new AlertEntity();
			alert.setId_device(device.getId());
			alert.setId_device_group(device.getId_device_group());
			alert.setError_code("1001");
			
			Integer errorId = session.selectOne("Device.getErrorId", alert);
			if (errorId == null) return;
			alert.setId_error(errorId);
			
			if (ModbusError.fromValue(data.getError()) == ModbusError.DEVICE_FAILED_TO_RESPOND) {
				boolean isAlertExist = (int) session.selectOne("BatchJob.checkAlertlExist", alert) > 0;
				if (isAlertExist) return;
				alert.setStart_date(data.getTime());
				session.insert("BatchJob.insertAlert", alert);
			} else {
				// Close alert
				AlertEntity openedAlert = session.selectOne("BatchJob.getAlertDetail", alert);
				if (openedAlert == null || openedAlert.getId() == 0) return;
				
				alert.setEnd_date(data.getTime());
				alert.setId(openedAlert.getId());
				session.update("BatchJob.updateCloseAlert", alert);
			}
			
			session.commit();
		} catch (Exception ex) {
			session.rollback();
			log.error("AlertEventListener.noCommunicationAlertEventListener", ex);
		} finally {
			session.close();
		}
    }

}
