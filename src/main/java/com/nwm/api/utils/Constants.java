package com.nwm.api.utils;

/**
 * <p>Title: contants of all avis system </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: flex</p>
 *
 * @author chuong.ma
 * @version 1.0
 */
public class Constants {
	public static final String FTPServer="ftp.server";
	public static final String FTPUser="ftp.user";
	public static final String FTPPass="ftp.pass";
	public static final String uploadFilePathConfigKeyFTP="uploadpath.ftp";
	
	public static final String weatherAPIURL = "https://api.openweathermap.org/data/2.5/weather";
	public static final String weatherAPIKEY = "868e4c309abc11cdfd41b07b84c4cd7f";
	
	public static final String OPEN_WEATHER_URL = "https://api.solcast.com.au/data/forecast/radiation_and_weather";
	public static final String OPEN_WEATHER_KEY = "Aec4Vj7qmIrIXf39aSAbKferD6V_LSxU";
	
	public static final String logoURL = "http://dev.vinarealtor.com.vn/assets/images/logo.png";
	
	public static final String dataBaseConfigFile = "database";
	public static final String appDev = "application-dev";
	public static final String appTest = "application-test";
	public static final String appProd = "application-prod";
	
	public static final String sqlMapconfigXml = "mybatis/SQLMapconfig.xml";
	public static final String appConfigFileName="application";
	public static final String tokenSignKey="avis";
	public static final String uploadRootPathConfigKey="uploadpath.root";
	public static final String uploadFilePathConfigKey="uploadpath.logo";
	public static final String uploadFilePathConfigKeyAvatar="uploadpath.avatar";
	public static final String uploadFilePathConfigKeyGallery="uploadpath.galleries";
	public static final String uploadFilePathConfigKeyIcons="uploadpath.icons";
	public static final String uploadFilePathConfigKeySupport="uploadpath.supports";
	public static final String uploadFilePathConfigKeyLogoReport="uploadpath.reports";
	public static final String uploadFilePathReportFiles="uploadpath.files";
	
	
	
	public static final String freeShopOrderLimit="free_shop_order_limit";
//	start mail config key
	public static final String mailConfigFileName="mailcontent";	
	public static final String mailProtocol = "mailProtocol";
	public static final String mailSSL = "mailSSL";
	public static final String mailTLS = "mailTLS"; 
	public static final String mailSmtpServer = "mailSmtpServer";
	public static final String mailUserName = "mailUserName";
	public static final String mailPassword = "mailPassword";
	public static final String mailFrom = "mailFrom";
	public static final String mailFromContact = "mailFromContact";
	public static final String mailTo = "mailTo";
	public static final String mailPort = "mailPort";
	public static final String mailAuth = "mailAuth";
	public static final String mailDebug = "mailDebug";
	public static final String mailReview = "mailReview";
	public static final String mailResetPassword = "mailResetPassword";
	public static final String mailSetPassword = "mailSetPassword";
	public static final String mailSentPassword = "mailSentPassword";
	public static final String mailModify = "mailModify";
	public static final String mailEnable = "mailEnable";
	public static final String mailAlert = "mailAlert";
	public static final String mail_bcc = "mail_bcc";
	

//	public static final String mailStateSubject_02 = "mailStateSubject_02";
	public static final String mailStateSubject_02_shop = "mailStateSubject_02_shop";
	public static final String mailStateSubject_02_product = "mailStateSubject_02_product";
	public static final String mailStateSubject_03 = "mailStateSubject_03";
	public static final String mailStateSubject_04 = "mailStateSubject_04";
	public static final String mailStateSubject_05 = "mailStateSubject_05";
	public static final String mailStateSubject_06 = "mailStateSubject_06";
	public static final String mailStateSubject_07 = "mailStateSubject_07";
	public static final String mailStateSubject_08 = "mailStateSubject_08";
	public static final String mailStateSubject_09 = "mailStateSubject_09";
	public static final String mailStateSubject_12 = "mailStateSubject_12";
	public static final String mailInscriptionToCustomerSubject = "mailInscriptionToCustomerSubject";
	public static final String mailInscriptionToAvisSubject = "mailInscriptionToAvisSubject";
	
	public static final String mailStateBody_02_shop = "mailStateBody_02_shop";
	public static final String mailStateBody_02_product = "mailStateBody_02_product";
	public static final String mailStateBody_03 = "mailStateBody_03";
	public static final String mailStateBody_04 = "mailStateBody_04";
//	public static final String mailStateBody_05 = "mailStateBody_05";
	public static final String mailStateBody_06 = "mailStateBody_06";
	public static final String mailStateBody_07 = "mailStateBody_07";
	public static final String mailStateBody_08 = "mailStateBody_08";
	public static final String mailStateBody_09 = "mailStateBody_09";
	public static final String mailStateBody_12 = "mailStateBody_12";
	
	public static final String mailREC = "mailREC";
	public static final String mailRECSubject = "mailRECSubject";
	public static final String mailRECBody = "mailRECBody";
	
	public static final String mailMonthly = "mailMonthly";
	public static final String mailMonthlySubject = "mailMonthlySubject";
	public static final String mailMonthlyBody = "mailMonthlyBody";
	
	
	
	public static final String mailInscriptionToCustomerBody = "mailInscriptionToCustomerBody";
	public static final String mailInscriptionToAvisBody = "mailInscriptionToAvisBody";
	public static final String mailToAvisInscription = "mailToAvisInscription";
	
	public static final String mailResetPasswordBody = "mailResetPasswordBody";
	public static final String alertModerationLatenessBody = "alertModerationLatenessBody";
	
	public static final String mailRunCronJobBody = "mailRunCronJobBody";
	public static final String mailSetPasswordBody = "mailSetPasswordBody";
	public static final String mailSetPasswordSubject = "mailSetPasswordSubject";
	
	public static final String mailSentPasswordBody = "mailSentPasswordBody";
	public static final String mailSentPasswordSubject = "mailSentPasswordSubject";
	
	public static final String mailSentResetPasswordBody = "mailSentResetPasswordBody";
	public static final String mailSentResetPasswordSubject = "mailSentResetPasswordSubject";

	public static final String mailCronJobBody = "mailCronJobBody";
	public static final String mailCronJobSubject = "mailCronJobSubject";
	
	public static final String mailResetPasswordSubject = "mailResetPasswordSubject";
	public static final String alertModerationLatenessSubject = "alertModerationLatenessSubject";
	
	
	
	public static final String unsubscribe_link = "unsubscribe_link";
//	end mail config key
	
	
	public static final String ONLINE_USER_GUIDE = "http://avisbackofficedev.flex.com.vn/api-server/swagger-ui.html";
	public static final String AVIS_CLIENT = "avis-clients";
	
    public static final String AES_KEY = "P_Rh@95dv1Zx#=OS";
    /**
     * Use for DB class to return result automatically without returnID
     */
    public static final String DEFAULT_RETURN_KEY = "return";
    /**
     * Using for break page of flex
     */
    public static final int STARTRECORD = 0;
    public static final int MAXRECORD = 20;
    public static final int ADD_MINUTE = 15;
    public static final int SUB_TRACT_MINUTE = -15;
    public static final int NO_COMMUNICATION  = 1001; // "nvm_1001";
    public static final int NO_PRODUCTION =  1000; // "nvm_1000";
    
    public static final int MAXRECORD_DISPLAY_DEFAULT = 5;
    public static final int MAXRECORD_NO_MINIT = 200;
    public static final int ADJUST_DELAY_MIN_DEFAULT = 3;
    public static final int ADJUST_TIME_DEFAULT = 43200;
    public static final int PRODUCT_SENDING_DELAY = 7;
    public static final String RESETPASSW_EXPIRED_TIME_KEY = "resetpass_expired_time";
    
    public static final int LANGUEAGE_ID_DEFAULT = 1; // French
    public static String SAVE_SUCCESS_MSG = Translator.toLocale("SAVE.SUCCESS");
    public static String USING_SUCCESS_MSG = Translator.toLocale("SAVE.USING");
    
    
    public static String SAVE_ERROR_MSG = Translator.toLocale("SAVE.ERROR");
    public static String INSERT_SUCCESS_MSG = Translator.toLocale("INSERT.SUCCESS");
    public static String INSERT_ERROR_MSG = Translator.toLocale("INSERT.ERROR");
    public static String UPDATE_SUCCESS_MSG = Translator.toLocale("UPDATE.SUCCESS");
    public static String UPDATE_ERROR_MSG = Translator.toLocale("UPDATE.ERROR");
    public static String DELETE_SUCCESS_MSG = Translator.toLocale("DELETE.SUCCESS");
    public static String RESTORE_SUCCESS_MSG = Translator.toLocale("RESTORE.SUCCESS");
    public static String DELETE_ERROR_MSG = Translator.toLocale("DELETE.ERROR");
    public static String GET_SUCCESS_MSG = Translator.toLocale("GET.SUCCESS");
    public static String SENT_EMAIL_SUCCESS = Translator.toLocale("EMAIL.SUCCESS");
    public static String SENT_EMAIL_ERROR = Translator.toLocale("EMAIL.ERROR");
    
    public static String GET_ERROR_MSG = Translator.toLocale("GET.ERROR");
    public static String COMPANY_EMAIL_EXIST = Translator.toLocale("COMPANY.EXIST");
    public static String SHOP_URL_EXIST = Translator.toLocale("SHOP.EXIST");
    public static String LOGIN_EMAIL_EXIST = Translator.toLocale("LOGIN_EMAIL.EXIST");
    public static String USER_EXIST = Translator.toLocale("USER.EXIST");
    public static String PASSWORD_OLD_ERROR_MSG = Translator.toLocale("USER.PASSWORD_OLD_ERROR_MSG");
    public static String ADMIN_RESET_PASSWORD = Translator.toLocale("EMPLOYEE.ADMIN_RESET_PASSWORD");
  
    

    public static String IMPORT_ORDER_SUCCESS_MSG = Translator.toLocale("SAVE_ORDER.SUCCESS");
    public static String IMPORT_ORDER_ERROR_MSG = Translator.toLocale("SAVE_ORDER.ERROR");
    public static String IMPORT_REVIEW_SUCCESS_MSG = Translator.toLocale("SAVE_REVIEW.SUCCESS");
    public static String IMPORT_REVIEW_ERROR_MSG = Translator.toLocale("SAVE_REVIEW.ERROR");
    public static String REVIEWS_URL_EXITS = Translator.toLocale("REVIEWS_URL_EXITS");
    
    public static String voir_les_avis = Translator.toLocale("voir_les_avis");
    public static String WEBSITE_EXITS = Translator.toLocale("WEBSITE_EXITS");
    
    public static String NOT_SUCH_SHOP_ERROR_MSG = "SHOP.NOT_SUCH";
    public static String NOT_SUCH_CUSTOMER_ERROR_MSG = "CUSTOMER.NOT_SUCH";
    public static String NO_DATA_ERROR_MSG = "DATA.NO_DATA";
    public static String SHOP_EXPIRED = "ERROR.EXPIRED";
    
    public static String VALIDATE_ERROR_MSG = "VALIDATE.ERROR";
    public static String SEND_MAIL_ERROR_MSG = "SENDMAIL.ERROR";
    
    public static String EMAIL_NOT_EXIST = "SENDMAIL.EMAIL_NOT_EXIST";
    public static String LINK_EXPIRED_ERROR_MSG = Translator.toLocale("LINK.EXPIRED");
    public static String CHANGE_PASSWORD_SUCCESS_MSG = Translator.toLocale("LINK.CHANGE_PASSWORD_SUCCESS_MSG");
    
    // widget
    public static String DOMAIN_GET_JS_WIDGET = "domain_get_js_widget";
    
    public static final String paymentConfigFileName="payment";
    public static final String CMCIC_CLE="CMCIC_CLE";
    public static final String CMCIC_TPE="CMCIC_TPE";
    public static final String CMCIC_VERSION="CMCIC_VERSION";
    public static final String CMCIC_CODESOCIETE="CMCIC_CODESOCIETE";
    public static final String URL_RETOUR="URL_RETOUR";
    public static final String CMCIC_URLOK="CMCIC_URLOK";
    public static final String CMCIC_URLKO="CMCIC_URLKO";
    public static final String CMCIC_SERVEUR="CMCIC_SERVEUR";
    public static final String VAT = "VAT";
    
    public static final String url_offres = "url_offres";
    public static final String url_conditions = "url_conditions";
    
    
    
    /**
     * deifne review state
     * @author jincheng
     *
     */
    public enum STATE {
    	Pending(1), // Before Sending Email
    	Sent(2), // After Sending Email
    	Submitted(3), // After Customer Form Submit (old name Waiting for Moderation)
    	Published(4), //Moderation OK
    	PublishedWithResponse(5), // Moderation OK
    	Incomprehensible(6), // Moderation Not OK
    	Inconsistent(7), // Moderation Not OK
    	Vulgar(8), // Moderation Not OK
    	Rejected(9), // Moderation Not OK, Timeout
    	Expired(10), // Timeout
    	Reported(11), // After Report Form Submit
    	Depublished(12), // Rejected by Moderation after a report
    	Blocked(13), // Moderation block email because of delivery delay
    	Cancelled(14), // Moderation chose to not send email
    	Unanswered(15), // Customer not answer this review product
    	ReSubmitted(16); // Customer update review after moderation request
		private int value;
		
		private STATE(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
    }

    /** 
     * get action get by state
     * @param state
     * @return
     */
    public static String getActionTextByState(int state) {
    	switch (state) {
    	case 6:
			return Constants.ACTION_TYPE.AnswerIncoherent.getMessage();
    	case 7:
			return Constants.ACTION_TYPE.AnswerIncomprehensible.getMessage();	

    	case 8:
			return Constants.ACTION_TYPE.AnswerInappropriate.getMessage();	

		default:
			return "";
    	}
    }
    /**
     * get state review by action type
     * @param actionType
     * @return
     */
    public static int getState(int actionType) {
		switch (actionType) {
		case 1:
			return Constants.STATE.Published.getValue();
		case 2:
			return Constants.STATE.PublishedWithResponse.getValue();	
		case 3:		
			return Constants.STATE.Incomprehensible.getValue();
		case 4:
			return Constants.STATE.Inconsistent.getValue();	
		case 5:
			return Constants.STATE.Vulgar.getValue();	
		case 6:
			return Constants.STATE.Rejected.getValue();	
		case 7:
			return Constants.STATE.Blocked.getValue();	
		case 8:
			return Constants.STATE.Cancelled.getValue();
		case 9:
			return Constants.STATE.Pending.getValue();
		case 11:
			return Constants.STATE.Depublished.getValue();

		default:
			return 0;
		}
	}

    /**
     * get comment type by action type
     * @param actionType
     * @return
     */
    public static int getCommentType(int actionType) {
		if(actionType<2) {
			return 1;
		}
		return 2;
	}
    public enum GENDER{
    	MALE("M"),
    	FEMALE("W");
    	private String value;
    	private GENDER(String value) {
    		this.value = value;
    	}

		public String getValue() {
			return value;
		}
    	
    }
    /**
     * defined action
     * @author jincheng
     *
     */
	public enum ACTION_TYPE {
		Publish(1, "Publish"), PublishAndAnswer(2, "Publish and answer"),
		AnswerIncomprehensible(3, "Incoherent"), AnswerIncoherent(4, "Incomprehensible"),
		AnswerInappropriate(5, "Inappropriate"), Reject(6, "Reject"),
		Blocked(7, "Blocked"),
		Cancelled(8, "Cancelled"),
		Pending(9, "Pending"),
		Report(10, "Customer Report"),
		Depublish(11, "Moderation Depublish"),
		
		Request(12, "Last request"),
		UpdateMainSetting(13, "Update main setting"),
		UpdateAdjustEmailDelay(14, "Update adjiust email delay"),
		CreateUser(15, "Add new user"),
		UpdateWidget(16, "Update widget"),
		ExportDatas(17, "Export datas"),
		ModerationReject(18, "Moderation Reject"),
		WidgetRequest(19, "Widget request")
		;
		private int value;
		private String message;
		private ACTION_TYPE(int value, String message) {
			this.value = value;
			this.message = message;
			
		}

		public int getValue() {
			return value;
		}
		public String getMessage() {
			return message;
		}
		public static String getMessageByValue(int val) {
	        for (ACTION_TYPE e : ACTION_TYPE.values()) {
	            if (e.value == val) {
	                return e.message;
	            }
	        }
	        return null;
	    }
	}
    /**
     * deined comment type
     */
    public enum COMMENTTYPE {
    	InternComment(1), //Intern Comment 
    	ModerationResponse(2), //Moderation Response 
    	CustomerResponse(3), // Customer Response
    	ReportComment(4); //Report Comment 
    	private int value;
		
		private COMMENTTYPE(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
    }

    /**
     * defined review type
     * @author jincheng
     *
     */
    public enum REVIEW_TYPE {
    	shop(1), 
    	product(2), 
    	survey(3); 
    	private int value;
		
		private REVIEW_TYPE(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
    }
    /**
     * defined api result error message
     * @author jincheng
     *
     */
    public enum API_RESULT_ERR_TYPE {
    	email(1, "err_type.email"), // error email
    	api_key(2, "err_type.api_key"), // api key not exist
    	order(3, "err_type.order"), // error order info
    	product(4, "err_type.product"), // error product info
    	customer(5, "err_type.customer"), // error customer info
    	review(6, "err_type.review"), // error product info
    	order_exist(7, "err_type.order_exist"), // error order info
    	unknow(8, "err_type.unknow");
    	private int value;
    	private String message;
		
		private API_RESULT_ERR_TYPE(int value, String message) {
			this.value = value;
			this.message = message;
		}
		
		public int getValue() {
			return value;
		}

		public String getMessage() {
			return Translator.toLocale(message);
		}
    }
    /**
     * Get Templete mail by state
     * @param state
     * @return
     */
    public static String getMailTempleteByState(int state, boolean... isShop) {
    	switch (state) {
		case 2:
			if(isShop!=null && isShop.length>0 && isShop[0]) {
				return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateBody_02_shop);
			}else {
				return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateBody_02_product);
			}
		case 3:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateBody_03);
		case 4:
		case 5:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateBody_04);
//			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateBody_05);
		case 6:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateBody_06);
		case 7:
			// Java cron job
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailCronJobBody);
		case 8:
			// Sent account info reset password administrator 
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSentResetPasswordBody);
		case 9:
			// Sent account password administrator 
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSentPasswordBody);
		case 10:
			// set new password when create new customer 
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSetPasswordBody);
		case 11:
			// CronJob get alert
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailRunCronJobBody);
		case 12:
			// Reset password
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailResetPasswordBody);
		case 13:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.alertModerationLatenessBody );
			
			
		case 15:
			// Renewable Energy Credits
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailRECBody);
		case 16:
			// monthly report
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailMonthlyBody);
		default:
			return null;
		}
	}
    /**
     * get Mail Subject by State
     * @param state
     * @return
     */
    public static String getMailSubjectByState(int state,  boolean... isShop) {
    	switch (state) {
		case 2:
			if(isShop!=null && isShop.length>0 && isShop[0]) {
				return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateSubject_02_shop);
			}else {
				return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateSubject_02_product);
			}
		case 3:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateSubject_03);
		case 4:
		case 5:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateSubject_04);
//			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateSubject_05);
		case 6:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailStateSubject_06);
		case 7:
			// Java cron job
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailCronJobSubject);
		case 8:
			// Sent account info reset password administrator
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSentResetPasswordSubject);
		case 9:
			// Sent account password administrator
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSentPasswordSubject);
		case 10:
			// set new password when create new customer 
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSetPasswordSubject);
		case 11:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailInscriptionToAvisSubject);
		case 12:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailResetPasswordSubject);
		case 13:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.alertModerationLatenessSubject);
		case 15:
			// Renewable Energy Credits
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailRECSubject);
		case 16:
			// Monthly report
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailMonthlySubject);
		default:
			return null;
		}
	}
    /**
     * swidth sender email by state
     * @param state
     * @return
     */
    public static String getMailFromByState(int state) {
    	switch (state) {
		case 2:
		case 6:
		case 7:
		case 8:
		case 9:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
		case 10:
		case 11:
		case 4:
		case 5:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFrom);
		default:
			return Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFrom);
		}
	}
    /**
     * defined payment status
     * @author jincheng
     *
     */
    public enum PAYMENT_STATUS {
    	in_progress("in progress"), 
    	rejected("rejected"), 
    	successful("successful");
    	
    	private String value;
		
		private PAYMENT_STATUS(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}

    }

    /**
     * defined payment mode
     * @author jincheng
     *
     */
    public enum PAYMENT_MODE {
    	bank_card("bank card"), 
    	rejected("paypal");
    	
    	private String value;
		
		private PAYMENT_MODE(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}

    }
}
