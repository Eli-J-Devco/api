package com.nwm.api.utils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.RollingFileAppender;

public class FlexRollingFileAppender extends RollingFileAppender {

    @Override
    public synchronized void setFile(String fileName, boolean append,
		boolean bufferedIO, int bufferSize) throws IOException {
        //Your logic goes here
    	File processed = new File(fileName);
    	String path= processed.getParent();
    	File dir = new File(path);
		if (!dir.exists() )
			dir.mkdirs();
        super.setFile(fileName, append, bufferedIO, bufferSize);
    }

}
