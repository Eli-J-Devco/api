/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;
import com.maxmind.geoip.LookupService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import java.io.IOException;
import java.net.InetAddress;

public class GeoIPv4Service{
	private static LookupService lookUp;
	
	static {
        try {
        	String filePath = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey) + "/GeoLiteCity.dat" ;
            lookUp = new LookupService(
            		GeoIPv4Service.class.getResource(filePath).getFile(),
                    LookupService.GEOIP_MEMORY_CACHE);

            System.out.println("GeoIP Database loaded: " + lookUp.getDatabaseInfo());
        } catch (IOException e) {
            System.out.println("Could not load geo ip database: " + e.getMessage());
        }
    }

    public static GeoLocation getLocation(String ipAddress) {
        return GeoLocation.map(lookUp.getLocation(ipAddress));
    }

    public static GeoLocation getLocation(long ipAddress){
        return GeoLocation.map(lookUp.getLocation(ipAddress));
    }

    public static GeoLocation getLocation(InetAddress ipAddress){
        return GeoLocation.map(lookUp.getLocation(ipAddress));
    }
	
	
}
