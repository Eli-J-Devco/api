/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Component;
import com.nwm.api.entities.UserEntity;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private UserService userService;
    private EmployeeService employeeService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	userService = new UserService();
    	employeeService = new EmployeeService();
    	String[] split = username.split(":");
    	if(split.length < 2)
    	{
    		throw new UsernameNotFoundException("Must specify both username and corporate customer type");
    	}

    	String usernameLogin = split[0];
    	String customerType = split[1];

    	UserEntity user = new UserEntity();
    	if(customerType.equals("1d607a2011ba58ed52cc32db71ffd37d")) {
    		user = employeeService.getAdminByUserName(usernameLogin);
    	} else {
    		user = userService.getUserByUserName(usernameLogin);
    	}
    	String message = "";
    	double timeAccountLocked = user.getTime_account_locked() > 0 ? user.getTime_account_locked() : 1;
    	int maxFailedAttempt = user.getMax_failed_attempt() > 0 ? user.getMax_failed_attempt() : 6;
    	
    	if (user.getId() == 0) {
    		message = "The account does not exist or has been locked. Please contact administrator.";
            throw new InvalidGrantException(message);
        } else if(user.getAccount_locked() == 1 || user.getFailed_attempt() >= maxFailedAttempt) {
        	// Update lock account
        	UserEntity userEn = new UserEntity();
        	userEn.setId(user.getId());
        	
        	
        	Date date = new Date();
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		userEn.setLock_time(format.format(date).toString());
        	
        	userEn.setFailed_attempt( user.getFailed_attempt() >= maxFailedAttempt ? maxFailedAttempt: (user.getFailed_attempt() + 1));
        	if( (user.getFailed_attempt() + 1) >= maxFailedAttempt) { userEn.setAccount_locked(1); }
        	employeeService.updateLockAccount(userEn);
        	
			if(timeAccountLocked < 1 && timeAccountLocked > 0) {
				message = "Your account has been locked due to "+maxFailedAttempt+" failed attempts. It will be unlocked after " + (int)(timeAccountLocked * 60) + " minute"+ ( (timeAccountLocked * 60) > 1 ? "s": "") + ".";
			} else {
				message = "Your account has been locked due to "+maxFailedAttempt+" failed attempts. It will be unlocked after " + (int)timeAccountLocked + " hour"+ (timeAccountLocked > 1 ? "s": "") + ".";
			}
    		
    		throw new InvalidGrantException(message);
    	}
    	
        String passwd = "";
        passwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwd);
        user.setFirst_name(user.getFirst_name());
        user.setId(user.getId());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRoles()));
		user.setAuthorities(authorities);

		return user;
    }
    
}
