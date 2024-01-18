package com.nwm.api.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.nwm.api.entities.UserEntity;
/**
 * custom token info, additionnal use for clients
 * @author Long.Pham
 *
 */
public class CustomTokenEnhancer 
implements TokenEnhancer {
	@Autowired
	ServletRequest req;
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("organization", authentication.getName());// + randomAlphabetic(4));
        try {
        	UserEntity user = (UserEntity)authentication.getUserAuthentication().getPrincipal();
        	if(user != null) {
        		additionalInfo.put("last_name", user.getLast_name());
        		additionalInfo.put("first_name", user.getFirst_name());
        		additionalInfo.put("id_user", user.getId());
        		additionalInfo.put("logo", user.getLogo());
        		additionalInfo.put("phone", user.getPhone());
        		additionalInfo.put("permissions", user.getPermissions());
        		additionalInfo.put("id_sites", user.getId_sites());
        		additionalInfo.put("is_technical", user.getIs_technical());
        		additionalInfo.put("id_company", user.getId_company());
        	}
        	
        }catch (Exception e) {
		}
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
