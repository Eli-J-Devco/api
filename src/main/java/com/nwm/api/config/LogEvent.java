package com.nwm.api.config;

import org.springframework.context.ApplicationEvent;

import com.nwm.api.entities.ActionLogEntity;

/**
 * event log
 * @author Long.Pham
 */
public class LogEvent extends ApplicationEvent {
	private ActionLogEntity sourceEntity;
	private static final long serialVersionUID = 1L;
	public LogEvent(String source) {
		super(source);
	}
	public LogEvent(ActionLogEntity sourceEntity){
		super(sourceEntity.getAction_name());
		this.setSourceEntity(sourceEntity);
	}
	public ActionLogEntity getSourceEntity() {
		return sourceEntity;
	}
	public void setSourceEntity(ActionLogEntity sourceEntity) {
		this.sourceEntity = sourceEntity;
	}
	
}
