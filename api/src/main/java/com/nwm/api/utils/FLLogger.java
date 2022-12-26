package com.nwm.api.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class FLLogger {
	private String _fileName;
	public static final Hashtable<String, FLLogger> _hashLogger=new Hashtable<String, FLLogger>();
	private static Logger _parentLog = null;
	private Logger _currenLog = null;;
	private static String _defaultfileName = "";
	private static FlexRollingFileAppender _temp_appender = null;
	private FlexRollingFileAppender newAppender=null;
	public enum LogLevel {
		DEBUG, INFO, WARN, ERROR, FATAL

	}

	public FLLogger(String fileName) {
		if (_parentLog == null) {
			_parentLog = Logger.getRootLogger();
			_temp_appender = (FlexRollingFileAppender) _parentLog.getAppender("tempLog");
			_parentLog.removeAppender(_temp_appender);
		}
		this._fileName = fileName;
		_currenLog=Logger.getLogger(this._fileName);
		
		
		intializeLogger();
	}

	private void intializeLogger() {
		//_tempLog = Logger.getLogger("tempLog");
		
		if (Lib.isBlank(_defaultfileName))
			_defaultfileName = _temp_appender.getFile();
		_currenLog.removeAllAppenders();
		newAppender= new FlexRollingFileAppender();
		newAppender.setLayout(_temp_appender.getLayout());
		newAppender.setMaxBackupIndex(_temp_appender.getMaxBackupIndex());
		newAppender.setMaximumFileSize(_temp_appender.getMaximumFileSize());
		
		newAppender.setEncoding("UTF-8");
		newAppender.setAppend(true);
		String fileName = "";

		fileName = _defaultfileName.replace("{LogFileName}", this._fileName);
		newAppender.setFile(fileName);
		newAppender.activateOptions();
		
		_currenLog.addAppender(newAppender)
		;
	
	}

	/**
	 * write to log
	 * 
	 * @param level
	 * @param msg
	 */
	private void write(LogLevel level, Object msg, Throwable exception) {

		switch (level) {
		case DEBUG:
			_currenLog.debug(msg, exception);
			break;

		case INFO:
			_currenLog.info(msg, exception);
			break;

		case WARN:
			_currenLog.warn(msg, exception);
			break;

		case ERROR:
			_currenLog.error(msg, exception);
			break;

		case FATAL:
			_currenLog.fatal(msg, exception);
			break;
		}
	}

	/**
	 * write to log with choosed file name
	 * 
	 * @param level
	 * @param msg
	 * @param logFileName
	 * @param suffixesFileName
	 */
	private synchronized void logMsg(String suffixesFileName, boolean is_dateFolder, LogLevel level, Object msg,
			Throwable exception) {
		try {
			String fileName = "";
			String dateFolder = "";
			if (is_dateFolder) {
//				Calendar cal = Calendar.getInstance();
				Calendar cal = Calendar.getInstance(TimeZone.getDefault());
				Date date = cal.getTime();
				dateFolder = new SimpleDateFormat("yyyyMMdd").format(date);
			}
			if (!Lib.isBlank(suffixesFileName)) {
				fileName = this._fileName + "_" + suffixesFileName;
			} else {
				fileName = this._fileName;
			}
			if (Lib.isBlank(dateFolder)) {
				fileName = _defaultfileName.replace("{LogFileName}", fileName);
			} else {
				File file = new File(fileName);
				String path = file.getParent();
				String nameFile = file.getName();
				fileName = _defaultfileName.replace("{LogFileName}", Lib.combine(path, dateFolder, nameFile));
			}
			if(!fileName.equals(newAppender.getFile())){
				newAppender.setFile(fileName);
				newAppender.activateOptions();
			}
			write(level, msg, exception);
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
	}

	// ###################Start
	// debug#######################################################################
	public void debug(Object msg) {
		logMsg("", false, LogLevel.DEBUG, msg, null);
	}

	public void debug(Object msg, Throwable exception) {
		logMsg("", false, LogLevel.DEBUG, msg, exception);
	}

	public void debug(Object msg, boolean is_date_folder) {
		logMsg("", is_date_folder, LogLevel.DEBUG, msg, null);
	}

	public void debug(Object msg, boolean is_date_folder, Throwable exception) {
		logMsg("", is_date_folder, LogLevel.DEBUG, msg, exception);
	}

	public void debugSuffixes(String suffixesFileName, Object msg) {
		logMsg(suffixesFileName, false, LogLevel.DEBUG, msg, null);
	}

	public void debugSuffixes(String suffixesFileName, Object msg, Throwable exception) {
		logMsg(suffixesFileName, false, LogLevel.DEBUG, msg, exception);
	}

	public void debugSuffixes(String suffixesFileName, Object msg, boolean is_date_folder, Throwable exception) {
		logMsg(suffixesFileName, is_date_folder, LogLevel.DEBUG, msg, exception);
	}

	public void debugSuffixes(String suffixesFileName, Object msg, boolean is_date_folder) {
		logMsg(suffixesFileName, is_date_folder, LogLevel.DEBUG, msg, null);
	}
	// ####################End
	// debug########################################################################

	// ###################Start
	// info#######################################################################

	public void info(Object msg) {
		logMsg("", false, LogLevel.INFO, msg, null);
	}

	public void info(Object msg, Throwable exception) {
		logMsg("", false, LogLevel.INFO, msg, exception);
	}

	public void info(Object msg, boolean is_date_folder) {
		logMsg("", is_date_folder, LogLevel.INFO, msg, null);
	}

	public void info(Object msg, boolean is_date_folder, Throwable exception) {
		logMsg("", is_date_folder, LogLevel.INFO, msg, exception);
	}

	public void infoSuffixes(String suffixesFileName, Object msg) {
		logMsg(suffixesFileName, false, LogLevel.INFO, msg, null);
	}

	public void infoSuffixes(String suffixesFileName, Object msg, Throwable exception) {
		logMsg(suffixesFileName, false, LogLevel.INFO, msg, exception);
	}

	public void infoSuffixes(String suffixesFileName, Object msg, boolean is_date_folder, Throwable exception) {
		logMsg(suffixesFileName, is_date_folder, LogLevel.INFO, msg, exception);
	}

	public void infoSuffixes(String suffixesFileName, Object msg, boolean is_date_folder) {
		logMsg(suffixesFileName, is_date_folder, LogLevel.INFO, msg, null);
	}
	// ####################End
	// info########################################################################

	// ###################Start
	// error#######################################################################
	public void error(Object msg) {
		logMsg("", false, LogLevel.ERROR, msg, null);
	}

	public void error(Object msg, Throwable exception) {
		logMsg("", false, LogLevel.ERROR, msg, exception);
	}

	public void error(Object msg, boolean is_date_folder) {
		logMsg("", is_date_folder, LogLevel.ERROR, msg, null);
	}

	public void error(Object msg, boolean is_date_folder, Throwable exception) {
		logMsg("", is_date_folder, LogLevel.ERROR, msg, exception);
	}

	public void errorSuffixes(String suffixesFileName, Object msg) {
		logMsg(suffixesFileName, false, LogLevel.ERROR, msg, null);
	}

	public void errorSuffixes(String suffixesFileName, Object msg, Throwable exception) {
		logMsg(suffixesFileName, false, LogLevel.ERROR, msg, exception);
	}

	public void errorSuffixes(String suffixesFileName, Object msg, boolean is_date_folder, Throwable exception) {
		logMsg(suffixesFileName, is_date_folder, LogLevel.ERROR, msg, exception);
	}

	public void errorSuffixes(String suffixesFileName, Object msg, boolean is_date_folder) {
		logMsg(suffixesFileName, is_date_folder, LogLevel.ERROR, msg, null);
	}
	// ####################End
	// error########################################################################

	// ###################Start
	// Warn#######################################################################
	public void warn(String msg) {
		logMsg("", false, LogLevel.WARN, msg, null);
	}

	public void warn(Object msg, Throwable exception) {
		logMsg("", false, LogLevel.WARN, msg, exception);
	}

	public void warn(Object msg, boolean is_date_folder) {
		logMsg("", is_date_folder, LogLevel.WARN, msg, null);
	}

	public void warn(Object msg, boolean is_date_folder, Throwable exception) {
		logMsg("", is_date_folder, LogLevel.WARN, msg, exception);
	}

	public void warnSuffixes(String suffixesFileName, Object msg) {
		logMsg(suffixesFileName, false, LogLevel.WARN, msg, null);
	}

	public void warnSuffixes(String suffixesFileName, Object msg, Throwable exception) {
		logMsg(suffixesFileName, false, LogLevel.WARN, msg, exception);
	}

	public void warnSuffixes(String suffixesFileName, Object msg, boolean is_date_folder, Throwable exception) {
		logMsg(suffixesFileName, is_date_folder, LogLevel.WARN, msg, exception);
	}

	public void warnSuffixes(String suffixesFileName, Object msg, boolean is_date_folder) {
		logMsg(suffixesFileName, is_date_folder, LogLevel.WARN, msg, null);
	}
	// ####################End
	// warn########################################################################

	// ###################Start
	// fatal#######################################################################
	public void fatal(Object msg) {
		logMsg("", false, LogLevel.FATAL, msg, null);
	}

	public void fatal(Object msg, Throwable exception) {
		logMsg("", false, LogLevel.FATAL, msg, exception);
	}

	public void fatal(Object msg, boolean is_date_folder) {
		logMsg("", is_date_folder, LogLevel.FATAL, msg, null);
	}

	public void fatal(Object msg, boolean is_date_folder, Throwable exception) {
		logMsg("", is_date_folder, LogLevel.FATAL, msg, exception);
	}

	public void fatalSuffixes(String suffixesFileName, Object msg) {
		logMsg(suffixesFileName, false, LogLevel.FATAL, msg, null);
	}

	public void fatalSuffixes(String suffixesFileName, Object msg, Throwable exception) {
		logMsg(suffixesFileName, false, LogLevel.FATAL, msg, exception);
	}

	public void fatalSuffixes(String suffixesFileName, Object msg, boolean is_date_folder, Throwable exception) {
		logMsg(suffixesFileName, is_date_folder, LogLevel.FATAL, msg, exception);
	}

	public void fatalSuffixes(String suffixesFileName, Object msg, boolean is_date_folder) {
		logMsg(suffixesFileName, is_date_folder, LogLevel.FATAL, msg, null);
	}
	// ####################End
	// info########################################################################

	/// <summary>
	/// get logger
	/// </summary>
	/// <param name="logFileName"></param>
	/// <returns></returns>
	public static FLLogger getLogger(String logFileName) {
		if(_hashLogger.containsKey(logFileName)){
			return _hashLogger.get(logFileName);
		}
		FLLogger log = new FLLogger(logFileName);
		_hashLogger.put(logFileName, log);
		return log;
	}

}
