/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    	if (user.getId() == 0) {
            throw new UsernameNotFoundException(usernameLogin);
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
