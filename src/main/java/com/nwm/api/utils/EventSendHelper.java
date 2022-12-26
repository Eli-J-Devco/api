package com.nwm.api.utils;
//import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import org.bouncycastle.jcajce.provider.symmetric.Serpent.TKeyGen;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Scope;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.config.LogEvent;
import com.nwm.api.entities.ActionLogEntity;

@Component
public class EventSendHelper implements ApplicationContextAware {
	@Autowired 
	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}
	@SuppressWarnings("unchecked")
	public static void sendLogEvent(HttpServletRequest req, int action_type, int count) {
		try {
			String token = req.getHeader("Authorization");
			if(Lib.isBlank(token)) {
				return;
			}
			token = token.replace("bearer ", "").replace("Bearer ", "");
	        Map<String, Object> map = decodeJwtToken(token);
	        String userName = Lib.convertObj2JsonString(map.get("user_name"));
	        Object obj = map.get("user_id");
	        if(null == obj) {
	        	return;
	        }
	        int userId = (int)obj;
	        if(Lib.isBlank(userName)|| userId <=0) {
	        	return;
	        }
			ActionLogEntity entity = buildLogEntity(action_type, userId, count);
			if(entity!=null) {
				LogEvent le = new LogEvent(entity);
				applicationContext.publishEvent(le);
			}
		}catch (Exception e) {
			System.out.print(e);
		}
		
	}
	
	public static void sendLogEventWidgetRequest(int id_user, int id_shop) {
		try {
			if(id_user <= 0) {
				return;
			}
			
			ActionLogEntity entity = buildLogWidgetEntity(Constants.ACTION_TYPE.WidgetRequest.getValue(), id_user, 0, id_shop);
			if(entity!=null) {
				LogEvent le = new LogEvent(entity);
				applicationContext.publishEvent(le);
			}
		}catch (Exception e) {
			System.out.print(e);
		}
		
	}
	
	private static ActionLogEntity buildLogWidgetEntity(int action_type, int id_user,int count, int id_shop) {
		try {
			ActionLogEntity entity = new ActionLogEntity();
			entity.setAction_type(action_type);
			entity.setAction_name(Constants.ACTION_TYPE.getMessageByValue(action_type));
			entity.setId_user(id_user);
			entity.setCount(count);
			entity.setId_shop(id_shop);
			return entity;
		}catch (Exception e) {
			return null;
		}
	}
	

	public static void requestLogEvent(HttpServletRequest req) {
    	try {
	        sendLogEvent(req, Constants.ACTION_TYPE.Request.getValue(),0);
    	}catch (Exception e) {
			System.out.print(e);
		}
    }
	private static ActionLogEntity buildLogEntity(int action_type, int id_user,int count) {
		try {
			ActionLogEntity entity = new ActionLogEntity();
			entity.setAction_type(action_type);
			entity.setAction_name(Constants.ACTION_TYPE.getMessageByValue(action_type));
			entity.setId_user(id_user);
			entity.setCount(count);
			return entity;
		}catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Map decodeJwtToken(String token){
		try {
			MacSigner signer = new MacSigner(Constants.tokenSignKey);
			Jwt jwtToken = JwtHelper.decodeAndVerify(token,signer);
	        ObjectMapper jsonMapper = new ObjectMapper(); 
	        return jsonMapper.readValue(jwtToken.getClaims(), Map.class);
		}catch (Exception e) {
			return null;
		}
	}
}
