package com.nwm.api.eventListeners;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.TimeValueDTO;
import com.nwm.api.events.SolarTrackerNoMotionAlertEvent;

@Component
public class AlertEventListener extends DB {
	
	/**
	 * @description No motion alert is triggered when a tracker detects no motion (shows the same value) for over 24 hours.
	 * The alert is cleared once the tracker has started moving for more than 2 hours.
	 * @author Hung.Bui
	 * @since 2026-01-08
	 */
	@Async
	@EventListener
    public void solarTrackerNoMotionAlertEventListener(SolarTrackerNoMotionAlertEvent event) {
		SqlSession session = this.beginTransaction();
		
		try {
			DeviceEntity device = event.getEvent();
			String trackerAngle = device.getField_value_default();
			if (Objects.isNull(trackerAngle)) return;
			
			List<TimeValueDTO> _24HourData = session.selectList("Device.getDataFor24Hours", device);
			List<TimeValueDTO> _24HourDataWithoutNull = _24HourData.stream().filter(item -> Objects.nonNull(item.getValue())).collect(Collectors.toList());
			if (_24HourDataWithoutNull.size() < 2) return;
			
			AlertEntity alert = new AlertEntity();
			alert.setId_device(device.getId());
			alert.setId_device_group(device.getId_device_group());
			alert.setError_code("1004");
			
			Integer errorId = (Integer) session.selectOne("Device.getErrorId", alert);
			if (errorId == null) return;
			alert.setId_error(errorId);
			
			if (_24HourDataWithoutNull.stream().allMatch(item -> item.getValue().equals(_24HourDataWithoutNull.get(0).getValue()))) {
				boolean isAlertExist = (int) session.selectOne("BatchJob.checkAlertlExist", alert) > 0;
				if (isAlertExist) return;
				alert.setStart_date(device.getLast_updated());
				session.insert("BatchJob.insertAlert", alert);
			} else {
				// Close alert
				AlertEntity openedAlert = (AlertEntity) session.selectOne("BatchJob.getAlertDetail", alert);
				if (openedAlert == null || openedAlert.getId() == 0) return;
				
				List<TimeValueDTO> _2HourDataWithoutNull = _24HourDataWithoutNull.stream().filter(item -> Duration.between(item.getTime().toInstant(ZoneOffset.UTC), Instant.now()).toMinutes() <= 120).collect(Collectors.toList());
				if (_2HourDataWithoutNull.size() < 2) return;
				if (_2HourDataWithoutNull.stream().allMatch(item -> item.getValue().equals(_2HourDataWithoutNull.get(0).getValue()))) return;
				
				alert.setEnd_date(device.getLast_updated());
				alert.setId(openedAlert.getId());
				session.update("BatchJob.updateCloseAlert", alert);
			}
			
			session.commit();
		} catch (Exception ex) {
			session.rollback();
			log.error("SolarTrackerAlertEventListener.noMotionAlertEventListener", ex);
		} finally {
			session.close();
		}
    }

}
