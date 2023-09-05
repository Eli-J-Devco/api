/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.UserEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;

@Component
public class CustomUserDetailService extends BaseController implements UserDetailsService {
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
    	
    	if (user.getMax_failed_attempt() > 0) {
    		String message = "";
        	double timeAccountLocked = user.getTime_account_locked() > 0 ? user.getTime_account_locked() : 1;
        	int maxFailedAttempt = user.getMax_failed_attempt() > 0 ? user.getMax_failed_attempt() : 6;
        	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String password = request.getParameter("password");
            
         // Update lock account
        	UserEntity userEn = new UserEntity();
        	userEn.setId(user.getId());
        	
        	
        	Date date = new Date();
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
        	if (user.getId() == 0) {
        		message = "The account does not exist or has been locked. Please contact administrator.";
                throw new InvalidGrantException(message);
            } else if(user.getAccount_locked() == 1 || user.getFailed_attempt() >= maxFailedAttempt) {
            	
            	userEn.setLock_time(format.format(date).toString());
            	userEn.setFailed_attempt( user.getFailed_attempt() >= maxFailedAttempt ? maxFailedAttempt: (user.getFailed_attempt() + 1));
            	if( (user.getFailed_attempt() + 1) >= maxFailedAttempt) { userEn.setAccount_locked(1); }
            	employeeService.updateLockAccount(userEn);
            	
    			if(timeAccountLocked < 1 && timeAccountLocked > 0) {
    				message = "Your account has been locked due to "+maxFailedAttempt+" failed attempts. It will be unlocked after " + (int)(timeAccountLocked * 60) + " minute"+ ( (timeAccountLocked * 60) > 1 ? "s": "") + ".";
    			} else {
    				message = "Your account has been locked due to "+maxFailedAttempt+" failed attempts. It will be unlocked after " + (int)timeAccountLocked + " hour"+ (timeAccountLocked > 1 ? "s": "") + ".";
    			}
    			
    			if (user.getIs_send_email_unblock() == 0 ) {
    				try {
    					// Send email to user to unlock
    					String domain = Lib.getDomain();
//    					String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
    					String ip = InetAddress.getLocalHost().getHostAddress();
    					System.out.println("IP: " + ip);
    				    if (ip.equals("0:0:0:0:0:0:0:1")) ip = "127.0.0.1";
    				    Assert.isTrue(ip.chars().filter($ -> $ == '.').count() == 3, "Illegal IP: " + ip);
    				    System.out.println("IP: " + ip);
    				    
    			        String locationAddress = GeoIPv4Service.getLocation(InetAddress.getByName(ip)).toString();
    			        
    			        System.out.println("IP Adress: " + locationAddress);
    				   
    					StringBuilder bodyHtml = new StringBuilder();
    					bodyHtml.append("<div style=\"max-width: 1000px;\" class=\"main-body\">"
    							+ "<h1 style=\"text-align: left;\">Account Blocked</h1>"
    							+ "<p style=\"text-align: left;\">Next Wave Energy Monitoring system detected suspicious attempts to login to your account from ip: </p>"
    							+ "<strong>"+ ip +" </strong>"
    							+ " location: "
    							+ "<strong>"+ locationAddress +"</strong>"
    							+ "<span>. If this was you <a href=\"" + domain + "/unlock-account/"+user.getHash_id_user()+"\" target=\\\"_blank\\\" >click here</a> to unblock your account. </span>"
    							+ "<div class=\"regards\"><br><p>Regards,</p><p>Next Wave Team</p><p><a href=\"https://nwemon.com\" target=\"_blank\"><img width=\"100px\" src=\"https://nwemon.com/public/uploads/system_setting_images/logo-colored-1642026858.png\"></a></p></div>"
    					+ "                <tbody>\n");
    								
    					String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
    					String subject = "User Account Blocked";
    					String tags = "run_cron_job";
    					String fromName = "NEXT WAVE ENERGY MONITORING INC";
    					String mailTo = user.getUser_name();
    					String mailToCC = "";
    					String mailToBCC = "";
    					
    					
    						if(mailTo != null && !mailTo.trim().isEmpty() ) {
    							boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, mailToCC, mailToBCC, subject, bodyHtml.toString(), tags);
    							
    							if (!flagSent) {
    								throw new Exception(Translator.toLocale(Constants.SEND_MAIL_ERROR_MSG));
    							} else {
    								// update sent email to employee
    								userEn.setIs_send_email_unblock(1);
    								
    								employeeService.updateSendEmailUnblock(userEn);
    							}
    						}
    					} catch (Exception e) {
    						// TODO: handle exception
    						message = "Send email error.";
    						throw new InvalidGrantException(message);
    					}
    			}
    			
    			
    			
    			
        		
        		throw new InvalidGrantException(message);
        		
        	} else if(!password.equals(user.getPassword())) {
        		userEn.setLock_time(format.format(date).toString());
            	userEn.setFailed_attempt( user.getFailed_attempt() >= maxFailedAttempt ? maxFailedAttempt: (user.getFailed_attempt() + 1));
            	if( (user.getFailed_attempt() + 1) >= maxFailedAttempt) { userEn.setAccount_locked(1); }
            	employeeService.updateLockAccount(userEn);
        		message = "Email or Password is incorrect.";
        		throw new InvalidGrantException(message);
        	}
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
