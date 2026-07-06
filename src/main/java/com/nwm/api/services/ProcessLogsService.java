package com.nwm.api.services;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ProcessLogsEntity;

public class ProcessLogsService extends DB{
	
	/**
	 * @description insert logs
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertProcessLogs(ProcessLogsEntity obj) {
		try {
			Object insertId = insert("ProcessLogs.insert", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
}
