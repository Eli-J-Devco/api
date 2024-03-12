package com.nwm.api.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author tritich
 * @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
public final class

Lib {
	
	public static String getDomain() {
		ResourceBundle resourceAppBundle = ResourceBundle.getBundle(Constants.appConfigFileName);
		String env = readProperty(resourceAppBundle, "spring.profiles.active", "dev");
		String domain = "https://nextwavemonitoring.com";
		switch (env) {
		case "test":
			ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.appTest);
			domain = readProperty(resourceBundle, "domain", "");
			break;
		case "prod":
			ResourceBundle resourceBundleProd = ResourceBundle.getBundle(Constants.appProd);
			domain = readProperty(resourceBundleProd, "domain", "");
			break;
		case "staging":
			ResourceBundle resourceBundleStaging = ResourceBundle.getBundle(Constants.appStaging);
			domain = readProperty(resourceBundleStaging, "domain", "");
			break;
		default:
			ResourceBundle resourceBundleDev = ResourceBundle.getBundle(Constants.appDev);
			domain = readProperty(resourceBundleDev, "domain", "");
			break;
		}
		return domain;
	};
	
	private static char[] SPECIAL_CHARACTERS = { '!', '"', '#', '$', '%', '*', '+', ',', ':', '<', '=', '>', '?', '@',
			'[', '\\', ']', '^', '`', '|', '~', 'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù',
			'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ',
			'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ',
			'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề',
			'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ',
			'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ',
			'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự', 'Ỳ', 'ỳ' };

	private static char[] REPLACEMENTS = { ' ', ' ', ' ', ' ', ' ', ' ', '_', ' ', '_', ' ', ' ', ' ', ' ', ' ', ' ',
			'_', ' ', ' ', ' ', ' ', ' ', 'A', 'A', 'A', 'A', 'E', 'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U',
			'Y', 'a', 'a', 'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A', 'a', 'D', 'd',
			'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
			'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e',
			'E', 'e', 'E', 'e', 'E', 'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
			'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
			'U', 'u', 'U', 'u', 'U', 'u', 'Y', 'y' };

	private static String[][] htmlEscape = { { "&lt;", "<" }, { "&gt;", ">" }, { "&amp;", "&" }, { "&quot;", "\"" },
			{ "&agrave;", "à" }, { "&Agrave;", "À" }, { "&acirc;", "â" }, { "&auml;", "ä" }, { "&Auml;", "Ä" },
			{ "&Acirc;", "Â" }, { "&aring;", "å" }, { "&Aring;", "Å" }, { "&aelig;", "æ" }, { "&AElig;", "Æ" },
			{ "&ccedil;", "ç" }, { "&Ccedil;", "Ç" }, { "&eacute;", "é" }, { "&Eacute;", "É" }, { "&egrave;", "è" },
			{ "&Egrave;", "È" }, { "&ecirc;", "ê" }, { "&Ecirc;", "Ê" }, { "&euml;", "ë" }, { "&Euml;", "Ë" },
			{ "&iuml;", "ï" }, { "&Iuml;", "Ï" }, { "&ocirc;", "ô" }, { "&Ocirc;", "Ô" }, { "&ouml;", "ö" },
			{ "&Ouml;", "Ö" }, { "&oslash;", "ø" }, { "&Oslash;", "Ø" }, { "&szlig;", "ß" }, { "&ugrave;", "ù" },
			{ "&Ugrave;", "Ù" }, { "&ucirc;", "û" }, { "&Ucirc;", "Û" }, { "&uuml;", "ü" }, { "&Uuml;", "Ü" },
			{ "&nbsp;", " " }, { "&copy;", "\u00a9" }, { "&reg;", "\u00ae" }, { "&euro;", "\u20a0" } };

	public static final String unescapeHTML(String s, int start) {
		int i, j, k;

		i = s.indexOf("&", start);
		start = i + 1;
		if (i > -1) {
			j = s.indexOf(";", i);
			/*
			 * we don't want to start from the beginning the next time, to handle the case
			 * of the & thanks to Pieter Hertogh for the bug fix!
			 */
			if (j > i) {
				// ok this is not most optimized way to
				// do it, a StringBuffer would be better,
				// this is left as an exercise to the reader!
				String temp = s.substring(i, j + 1);
				// search in htmlEscape[][] if temp is there
				k = 0;
				while (k < htmlEscape.length) {
					if (htmlEscape[k][0].equals(temp))
						break;
					else
						k++;
				}
				if (k < htmlEscape.length) {
					s = s.substring(0, i) + htmlEscape[k][1] + s.substring(j + 1);
					return unescapeHTML(s, i); // recursive call
				}
			}
		}
		return s;
	}

	public static String escapeHtmlFull(String s) {
		StringBuilder b = new StringBuilder(s.length());
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9') {
				// safe
				b.append(ch);
			} else if (Character.isWhitespace(ch)) {
				// paranoid version: whitespaces are unsafe - escape
				// conversion of (int)ch is naive
				b.append("&#").append((int) ch).append(";");
			} else if (Character.isISOControl(ch)) {
				// paranoid version:isISOControl which are not isWhitespace
				// removed !
				// do nothing do not include in output !
			} else if (Character.isHighSurrogate(ch)) {
				int codePoint;
				if (i + 1 < s.length() && Character.isSurrogatePair(ch, s.charAt(i + 1))
						&& Character.isDefined(codePoint = (Character.toCodePoint(ch, s.charAt(i + 1))))) {
					b.append("&#").append(codePoint).append(";");
				} else {
				}
				i++; // in both ways move forward
			} else if (Character.isLowSurrogate(ch)) {
				i++; // move forward,do nothing do not include in output !
			} else {
				if (Character.isDefined(ch)) {
					// paranoid version
					// the rest is unsafe, including <127 control chars
					b.append("&#").append((int) ch).append(";");
				}
				// do nothing do not include undefined in output!
			}
		}
		return b.toString();
	}

	/**
	 * <p>
	 * Unescapes any JavaScript literals found in the <code>String</code>.
	 * </p>
	 * <p>
	 * For example, it will turn a sequence of <code>'\'</code> and <code>'n'</code>
	 * into a newline character, unless the <code>'\'</code> is preceded by another
	 * <code>'\'</code>.
	 * </p>
	 * 
	 * @param str the <code>String</code> to unescape, may be null
	 * @return A new unescaped <code>String</code>, <code>null</code> if null string
	 *         input
	 */
	public static String unescapeJavaScript(String str) {
		if (str == null) {
			return null;
		}

		StringBuffer writer = new StringBuffer(str.length());
		int sz = str.length();
		StringBuffer unicode = new StringBuffer(4);
		boolean hadSlash = false;
		boolean inUnicode = false;

		for (int i = 0; i < sz; i++) {
			char ch = str.charAt(i);
			if (inUnicode) {
				// if in unicode, then we're reading unicode
				// values in somehow
				unicode.append(ch);
				if (unicode.length() == 4) {
					// unicode now contains the four hex digits
					// which represents our unicode character
					try {
						int value = Integer.parseInt(unicode.toString(), 16);
						writer.append((char) value);
						unicode.setLength(0);
						inUnicode = false;
						hadSlash = false;
					} catch (NumberFormatException nfe) {
						throw new IllegalArgumentException(
								"Unable to parse unicode value: " + unicode + " cause: " + nfe);
					}
				}
				continue;
			}

			if (hadSlash) {
				// handle an escaped value
				hadSlash = false;
				switch (ch) {
				case '\\':
					writer.append('\\');
					break;
				case '\'':
					writer.append('\'');
					break;
				case '\"':
					writer.append('"');
					break;
				case 'r':
					writer.append('\r');
					break;
				case 'f':
					writer.append('\f');
					break;
				case 't':
					writer.append('\t');
					break;
				case 'n':
					writer.append('\n');
					break;
				case 'b':
					writer.append('\b');
					break;
				case 'u':
					// uh-oh, we're in unicode country....
					inUnicode = true;
					break;
				default:
					writer.append(ch);
					break;
				}
				continue;
			} else if (ch == '\\') {
				hadSlash = true;
				continue;
			}
			writer.append(ch);
		}

		if (hadSlash) {
			// then we're in the weird case of a \ at the end of the
			// string, let's output it anyway.
			writer.append('\\');
		}

		return writer.toString();
	}

	/**
	 * null save to string conversion
	 * 
	 * @param aO
	 * @return
	 */
	public static String str(Object aO) {
		if (aO == null)
			return "";
		else
			return aO.toString();
	}

	/**
	 * opposite of str(). Converts s to null if it contains ""
	 * 
	 * @param aS
	 * @return
	 */
	public static String rst(String aS) {
		if (aS != null && aS.trim().length() == 0)
			return null;
		return aS;
	}

	/**
	 * use this function will not appear error Safe for trim
	 * 
	 * @param aS
	 * @return
	 */
	public static String safeTrim(String aS) {
		if (aS == null)
			return "";
		else
			return aS.trim();
	}

	/**
	 * use this function will not appear error Safe for trim
	 * 
	 * @param aS
	 * @return
	 */
	public static String safeTrim(Object aS) {
		try {
			if (aS == null)
				return "";
			else
				return ((String) aS).trim();
		} catch (Exception ex) {
			return "";
		}

	}

	/**
	 * Get Stack Trace
	 * 
	 * @param aT
	 * @return
	 */
	public static String getStackTrace(Throwable aT) {
		StringWriter sw = new StringWriter();
		aT.printStackTrace(new PrintWriter(sw));
		return sw.getBuffer().toString();
	}

	/**
	 * Check object is not null if object is null, the function will throw exception
	 * when program you will catch this error
	 * 
	 * @param aObj
	 * @param aObjName
	 * @throws java.lang.Exception
	 */
	public static void assertNotNull(Object aObj, String aObjName) throws Exception {
		if (aObj == null)
			throw new Exception(aObjName + " must not be null");
	}

	/**
	 * Check object is not blank if object is blank, the function will throw
	 * exception when program you will catch this error
	 * 
	 * @param aS
	 * @param varName
	 * @throws java.lang.Exception
	 */
	public static void assertNotBlank(String aS, String varName) throws Exception {
		if (isBlank(aS))
			throw new Exception(varName + " must not be blank");
	}

	/**
	 * Check codition is true
	 * 
	 * @param aCondition
	 * @param aMsg
	 * @throws java.lang.Exception
	 */
	public static void assert1(boolean aCondition, String aMsg) throws Exception {
		if (!aCondition)
			throw new Exception(aMsg);
	}

	/**
	 * check String is blank or not
	 * 
	 * @param aS
	 * @return true if String is null
	 */
	public static boolean isBlank(String aS) {
		return (aS == null || "".equals(safeTrim(aS))) ? true : aS.trim().length() == 0;
	}

	/**
	 * check String is blank or not
	 * 
	 * @param aS
	 * @return true if String is null
	 */
	public static boolean isBlank(Object aS) {
		return (aS == null || "".equals(safeTrim(aS))) ? true : safeTrim(aS).length() == 0;
	}

	/**
	 * check aS is null or not
	 * 
	 * @param aS
	 * @param aDefault
	 * @return aS if s is not null else return sDefault
	 */
	public static String nvl(String aS, String aDefault) {
		return (aS != null && !safeTrim(aS).equals("")) ? aS : aDefault;
	}

	/**
	 * Tries it's best to generically deep-copy an object. Strings and Numbers are
	 * copied by reference. For all other types an attempt is made to serialize the
	 * object. If the object is not serializable, the original object reference is
	 * returned.
	 */
	public static Object copyObject(Object aO) {
		Object retval = null;

		if (aO instanceof String || aO instanceof Number)
			retval = aO;
		else {
			try { // try to stream it out
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos);
				oos.writeObject(aO);
				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
				retval = ois.readObject();
			} // if not serializable use pointer asignment
			catch (NotSerializableException ex) {
				retval = aO;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return retval;
	}

	/**
	 * Creates an object of the specified class by calling the default constructor.
	 * 
	 * @param aClassName
	 * @return object has just created
	 */
	public static Object createObject(String aClassName) {
		return createObject(aClassName, new Class[] {}, new Object[] {});
	}

	/**
	 * Creates an object of the specified class by calling the constructor
	 * identified by the specified parameter types.
	 * 
	 * @param aClassName
	 * @param aParamTypes
	 * @param aParameters
	 * @return object has just been created
	 */
	public static Object createObject(String aClassName, Class[] aParamTypes, Object[] aParameters) {
		try {
			Class cls = Class.forName(aClassName);
			Constructor constr = cls.getConstructor(aParamTypes);
			return constr.newInstance(aParameters);
		} catch (Exception ex) {
			// ex.printStackTrace();
			StringBuffer buf = new StringBuffer(128);
			buf.append("Couldn't create ").append(aClassName).append("(");
			if (aParamTypes != null)
				for (int i = 0; i < aParamTypes.length; i++) {
					if (i > 0)
						buf.append(", ");
					buf.append(aParamTypes[i].getName());
					buf.append(" = ");
					if (aParameters != null && i < aParameters.length)
						buf.append(str(aParameters[i]));
				}
			buf.append(")\n").append("Reason: ");
			buf.append(ex.getClass().getName()).append(": ").append(getStackTrace(ex));
			throw new RuntimeException(buf.toString());
		}
	}

	/**
	 * replace all string
	 * 
	 * @param aSource
	 * @param aFind
	 * @param aReplaceBy
	 * @return string has just been repalced
	 */
	public static String replace(String aSource, String aFind, String aReplaceBy) {
		if (aSource == null || aFind == null)
			return aSource;
		if (aSource.equals("") || aFind.equals(""))
			return aSource;
		if (aReplaceBy == null)
			aReplaceBy = "";
		StringBuffer result = new StringBuffer();
		int len = aFind.length();

		int oldPos = 0;
		int pos = aSource.indexOf(aFind);
		while (pos > -1) {
			result.append(aSource.substring(oldPos, pos));
			result.append(aReplaceBy);
			oldPos = pos + len;
			pos = aSource.indexOf(aFind, oldPos);
		}
		result.append(aSource.substring(oldPos));

		return result.toString();
	}

	/**
	 * encode string to md5
	 * 
	 * @param aClear
	 * @return string was encoded
	 * @throws java.lang.Exception
	 */
	public static String toMd5String(String aClear) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b;
			b = md.digest(aClear.getBytes());
			StringBuffer md5;
			md5 = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				int u = b[i] & 0xFF; // unsigned conversion
				if (u < 0x10)
					md5.append("0");
				md5.append(Integer.toHexString(u));
			}
			return md5.toString();
		} catch (Exception ex) {
			return aClear;
		}
	}

	/**
	 * get date at client
	 * 
	 * @return
	 */
	public static String GetDateNow() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return PadLeft(String.valueOf(calendar.get(Calendar.YEAR)), 4) + "/"
				+ PadLeft(String.valueOf((calendar.get(Calendar.MONTH) + 1)), 2) + "/"
				+ PadLeft(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2) + " "
				+ PadLeft(String.valueOf(calendar.get(Calendar.HOUR)), 2) + ":"
				+ PadLeft(String.valueOf(calendar.get(Calendar.MINUTE)), 2) + ":"
				+ PadLeft(String.valueOf(calendar.get(Calendar.SECOND)), 2);
	}

	/**
	 * get time string yyyymmddhhmmdd
	 * 
	 * @return
	 */
	public static String getTimeString() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return PadLeft(String.valueOf(calendar.get(Calendar.YEAR)), 4)
				+ PadLeft(String.valueOf((calendar.get(Calendar.MONTH) + 1)), 2)
				+ PadLeft(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2)
				+ PadLeft(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2)
				+ PadLeft(String.valueOf(calendar.get(Calendar.MINUTE)), 2)
				+ PadLeft(String.valueOf(calendar.get(Calendar.SECOND)), 2);

	}

	/**
	 * This method is to get string dd/mm/yyyy hh:mm
	 * 
	 * @author Trinh Nguyen
	 * @date 16-02-2009
	 * 
	 * @return
	 */
	public static String getCurrentDate() {

		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return Lib.PadLeft(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2) + "/"
				+ Lib.PadLeft(String.valueOf((calendar.get(Calendar.MONTH) + 1)), 2) + "/"
				+ Lib.PadLeft(String.valueOf(calendar.get(Calendar.YEAR)), 4) + " "
				+ Lib.PadLeft(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2) + ":"
				+ Lib.PadLeft(String.valueOf(calendar.get(Calendar.MINUTE)), 2);
	}

	/**
	 * This method is to get string ddmmyyyy
	 * 
	 * @author Trinh Nguyen
	 * @date 28-03-2009
	 * 
	 * @return
	 */
	public static String getCurrentDayStr() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		String year = cal.get(Calendar.YEAR) + "";
		String yearS = year.substring(2, 4);
		return Lib.PadLeft(String.valueOf(day), 2) + Lib.PadLeft(String.valueOf(month), 2) + yearS;
	}

	/**
	 * This method is to get string ddmmyy
	 * 
	 * @return
	 */
	public static String getCurrentDayStrDDMMYY() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		String year = cal.get(Calendar.YEAR) + "";
		String yearS = Lib.PadLeft(year, 2);
		return Lib.PadLeft(String.valueOf(day), 2) + Lib.PadLeft(String.valueOf(month), 2) + yearS;
	}

	/**
	 * This method is to get current day dd/mm/yyyy
	 * 
	 * @author Trinh Nguyen
	 * @date 12-03-2009
	 * 
	 * @return
	 */
	public static String getCurrentDay() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return Lib.PadLeft(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2) + "/"
				+ Lib.PadLeft(String.valueOf((calendar.get(Calendar.MONTH) + 1)), 2) + "/"
				+ Lib.PadLeft(String.valueOf(calendar.get(Calendar.YEAR)), 4);
	}

	public static String getCurrentDay(String patern) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return Lib.PadLeft(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2) + "/"
				+ Lib.PadLeft(String.valueOf((calendar.get(Calendar.MONTH) + 1)), 2) + "/"
				+ Lib.PadLeft(String.valueOf(calendar.get(Calendar.YEAR)), 4);
	}

	/**
	 * This method is to get current day yyyy/MM/dd
	 * 
	 * @author Tich Nguyen
	 * @date 09-04-2010
	 * 
	 * @return
	 */
	public static String getCurrentJPDate() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return Lib.PadLeft(String.valueOf(calendar.get(Calendar.YEAR)), 4) + "/"
				+ Lib.PadLeft(String.valueOf((calendar.get(Calendar.MONTH) + 1)), 2) + "/"
				+ Lib.PadLeft(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2);
	}

	public static String getCurrentDayByWord() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return "Ngày " + Lib.PadLeft(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2) + " Tháng "
				+ Lib.PadLeft(String.valueOf((calendar.get(Calendar.MONTH) + 1)), 2) + " Năm "
				+ Lib.PadLeft(String.valueOf(calendar.get(Calendar.YEAR)), 4);
	}

	/**
	 * convert date to word dd/MM/yyyy to Ngay ... Thang ... Nam
	 * 
	 * @param strDate
	 * @return
	 */
	public static String date2Word(String strDate) {
		strDate = strDateConvertForView(strDate);
		if (isBlank(strDate))
			return "";
		String[] arrDate = strDate.split("/");
		return "Ngày " + PadLeft(safeTrim(arrDate[0]), 2) + " tháng " + PadLeft(safeTrim(arrDate[1]), 2) + " năm "
				+ arrDate[2];
	}

	public static String date2WordCarePlus(String strDate) {
		strDate = strDateConvertForView(strDate);
		if (isBlank(strDate))
			return "";
		String[] arrDate = strDate.split("/");
		return "Ngày<span class='title-hoadon-en'>(Day)</span> " + PadLeft(safeTrim(arrDate[0]), 2)
				+ " Tháng<span class='title-hoadon-en'>(Month)</span> " + PadLeft(safeTrim(arrDate[1]), 2)
				+ " Năm<span class='title-hoadon-en'>(Year)</span> " + arrDate[2];

	}

	/**
	 * convert date to word Date to ngay .. thang .. nam ....
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToWord(Date date) {
		if (date == null)
			return "";
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(date);
		return "Ngày " + Lib.PadLeft(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2) + " Tháng "
				+ Lib.PadLeft(String.valueOf((cal.get(Calendar.MONTH) + 1)), 2) + " Năm "
				+ Lib.PadLeft(String.valueOf(cal.get(Calendar.YEAR)), 4);
	}

	public static String dateToWordEn(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
		return formatter.format(date);
	}

	@SuppressWarnings("deprecation")
	public static String dateToENWord(Date date) {
		try {
			if (date == null)
				return "";
			String strDate = DateToStringDDMMYYYY(date);
			date = StringToDate(strDate);
			String str = date.toLocaleString();
			str = str.replace(" 12:00:00 AM", "");
			return str;
		} catch (Exception ex) {
			return "";
		}
	}

	public static String strToUnicode(String aStr) {
		String r = "";
		char[] ch = aStr.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			r += "\\u" + Integer.toHexString(ch[i]);
		}
		return r;
	}

	/**
	 * get Day at client
	 * 
	 * @return
	 */
	public static int GetCurrentDay() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * get Month at client
	 * 
	 * @return
	 */
	public static int GetCurrentMonth() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * get Month at client
	 * 
	 * @return
	 */
	public static int GetCurrentHour() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * get Minute at client
	 * 
	 * @return
	 */
	public static int GetCurrentMinute() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * get Second at client
	 * 
	 * @return
	 */
	public static int GetCurrentSecond() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * get Year at client
	 * 
	 * @return
	 */
	public static int GetCurrentYear() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 
	 * @return String
	 */
	public static String GetYMDHMSMS() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return PadLeft(String.valueOf(calendar.get(Calendar.DATE)), 4) + "/"
				+ PadLeft(String.valueOf((calendar.get(Calendar.MONTH) + 1)), 2) + "/"
				+ PadLeft(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2) + " "
				+ PadLeft(String.valueOf(calendar.get(Calendar.HOUR)), 2) + ":"
				+ PadLeft(String.valueOf(calendar.get(Calendar.MINUTE)), 2) + ":"
				+ PadLeft(String.valueOf(calendar.get(Calendar.SECOND)), 2) + calendar.get(Calendar.MILLISECOND)
				+ calendar.get(Calendar.AM_PM);
	}

	/**
	 * format date in "yyyy/MM/dd hh:mm:ss" type
	 * 
	 * @param aD
	 * @return
	 */
	public static String DateToString(java.util.Date aD) {

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return df.format(aD);
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * format date in "yyyy/MM/dd hh:mm:ss" type
	 * 
	 * @param aD
	 * @return
	 */
	public static String DateToString(java.util.Date aD, String format) {

		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(aD);
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * format date in "yyyy/MM/dd" type
	 * 
	 * @param aD
	 * @return
	 */
	public static String DateToStringYYYYMMDD(java.util.Date aD) {

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			return df.format(aD);
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * format date in "dd/MM/yyyy" stype
	 * 
	 * @param aD
	 * @return
	 */
	public static String DateToStringDDMMYYYY(java.util.Date aD) {

		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			return df.format(aD);
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * format date in "dd/MM/yyyy" stype
	 * 
	 * @param aS
	 * @return
	 */
	public static java.util.Date StringToDate(String aS) {
		if (isBlank(aS))
			return null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date todate;
		try {
			todate = df.parse(aS);
		} catch (Exception e) {
			return null;
		}
		return todate;

	}

	public static java.util.Date StringToDate(String aS, String format) {
		if (isBlank(aS))
			return null;
		DateFormat df = new SimpleDateFormat(format);
		Date todate;
		try {
			todate = df.parse(aS);
		} catch (Exception e) {
			return null;
		}
		return todate;

	}

	/**
	 * convert from date dd/mm/yyyy to yyyy/mm/dd hoac nguojc lai
	 * 
	 * @param strDate
	 * @return
	 */
	public static String strDateConvert(String strDate) {
		if (isBlank(strDate))
			return null;
		try {
			strDate = strDate.replace("-", "/");
			String[] strDateInfo = strDate.split("/");
			return strDateInfo[2] + "/" + strDateInfo[1] + "/" + strDateInfo[0];
		} catch (Exception ex) {
			return null;
		}
	}

	public static String strDateConvertDDMMYYYY(String strDate) {
		if (isBlank(strDate))
			return null;
		try {
			String[] strDateInfo = strDate.split("/");
			return strDateInfo[2] + strDateInfo[1] + strDateInfo[0];
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * convert date string to dd/MM/yyyy
	 * 
	 * @param strDate
	 * @return
	 */
	public static String strDateConvertForView(String strDate) {
		if (isBlank(strDate))
			return null;
		try {
			if (strDate.length() < 10)
				return strDate;

			String[] strDateInfo;
			strDate = strDate.substring(0, 10);
			if (strDate.indexOf("-") > 0) {
				strDateInfo = strDate.split("-");
			} else if (strDate.indexOf("/") > 0) {
				strDateInfo = strDate.split("/");
			} else
				return strDate;
			if (strDateInfo[0].length() == 2)
				return strDateInfo[0] + "/" + strDateInfo[1] + "/" + strDateInfo[2];
			else
				return strDateInfo[2] + "/" + strDateInfo[1] + "/" + strDateInfo[0];
		} catch (Exception ex) {
			return "";
		}
	}

	public static String strDateConvertForDB(String strDate) {
		if (isBlank(strDate))
			return null;
		try {
			String[] strDateInfo = strDate.split("/");
			if (strDateInfo[0].length() == 4)
				return strDate;

			return strDateInfo[2] + "/" + strDateInfo[1] + "/" + strDateInfo[0];
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * convert from date dd/mm/yyyy hh:mm to yyyy/mm/dd hh:mm hoac nguoc lai
	 * 
	 * @param strDate
	 * @return
	 */
	public static String strDateTimeConvert(String strDateTime) {
		if (isBlank(strDateTime))
			return null;
		try {
			strDateTime = strDateTime.replaceAll("-", "/");
			String[] strDateInfo = strDateTime.split("/");
			String[] strYearTime = safeTrim(strDateInfo[2]).split(" ");
			String str = strYearTime[0] + "/" + strDateInfo[1] + "/" + strDateInfo[0];
			if (strYearTime.length > 1) {

				if (!Lib.isBlank(strYearTime[1])) {
					if (strYearTime[1].length() == 4)
						strYearTime[1] += "0";
					if (strYearTime[1].length() >= 5)
						str += " " + strYearTime[1].substring(0, 5);
				}

			}
			return str;
		} catch (Exception ex) {

			return null;
		}
	}

	/**
	 * format date in "yyyy/MM/dd HH:mm" stype
	 * 
	 * @param aS
	 * @return
	 */
	public static java.sql.Date StringToSQLDate(String aS) {
		if (isBlank(aS))
			return null;
		try {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			java.util.Date parsedDate = formater.parse(aS);
			java.sql.Date result = new java.sql.Date(parsedDate.getTime());
			return result;
		} catch (ParseException ex) {
			try {
				SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				java.util.Date parsedDate = formater.parse(aS);
				java.sql.Date result = new java.sql.Date(parsedDate.getTime());
				return result;
			} catch (ParseException ex1) {
				try {
					SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date parsedDate = formater.parse(aS);
					java.sql.Date result = new java.sql.Date(parsedDate.getTime());
					return result;
				} catch (ParseException ex2) {
					try {
						SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						java.util.Date parsedDate = formater.parse(aS);
						java.sql.Date result = new java.sql.Date(parsedDate.getTime());
						return result;
					} catch (ParseException ex3) {
						return null;
					}
				}
			}

		}

	}

	/**
	 * format double to string with any pattern
	 * 
	 * @param aS
	 * @param aPattern
	 * @return
	 */
	public static String format(double aS, String aPattern) {
		try {
			Locale.setDefault(Locale.US);
			DecimalFormat df = new DecimalFormat(aPattern);
			return df.format(aS);
		} catch (Exception ex) {
			return String.valueOf(aS);
		}

	}

	/**
	 * all double can't contain comma, it is just contain dot
	 * 
	 * @param aNumber               double
	 * @param aRoundUp_Down         int, it has 3 values, 0 , 1,2 if aRoundUp_Down=0
	 *                              is normal .1 Up, 2 is Down
	 * @param aNumberDigiAfterComma int
	 * @return double
	 */
	public static double Round(double aNumber, int aRoundUp_Down, int aNumberDigiAfterComma) {
		String sNumber;
		String sNumberBeforeDot;
		String sNumberAfterDot;
		try {
			int PosOfDot = 0;
			if (aNumber == 0)
				return 0;
			// convert double to String to split dot
			sNumber = String.format("%.2f", aNumber);
			PosOfDot = sNumber.lastIndexOf(".");
			sNumberBeforeDot = sNumber.substring(0, PosOfDot);
			sNumberAfterDot = sNumber.substring(PosOfDot + 1, sNumber.length());
			if (sNumberAfterDot.equals(""))
				return Double.parseDouble(sNumberBeforeDot);
			if (Double.parseDouble(sNumberAfterDot) <= 0)
				return Double.parseDouble(sNumberBeforeDot);
			switch (aRoundUp_Down) {
			case 2: {
				if (aNumberDigiAfterComma == 0)
					return Double.parseDouble(sNumberBeforeDot);
				if (sNumberAfterDot.equals("") || sNumberAfterDot.length() <= aNumberDigiAfterComma)
					return Double.parseDouble(sNumberBeforeDot + "." + sNumberAfterDot);
				else {
					if (sNumberAfterDot.length() <= aNumberDigiAfterComma) {
						sNumber = sNumberBeforeDot + "." + sNumberAfterDot;
						return Double.parseDouble(sNumber);
					} else {
						sNumber = sNumberBeforeDot + "." + sNumberAfterDot.substring(0, aNumberDigiAfterComma);
					}
					return Double.parseDouble(sNumber);
				}
			}
			case 1: {
				if (sNumberAfterDot.length() <= aNumberDigiAfterComma) {
					if (!sNumberAfterDot.equals(""))
						sNumber = sNumberBeforeDot + "." + sNumberAfterDot;
					else
						sNumber = sNumberBeforeDot;
					return Double.parseDouble(sNumber);
				}
				String s = "";
				s = sNumberAfterDot.substring(0, aNumberDigiAfterComma);
				if (!s.equals("")) {
					sNumber = sNumberBeforeDot + "." + (Long.parseLong(s) + 1);
					return Double.parseDouble(sNumber);
				} else
					return (Double.parseDouble(sNumberBeforeDot) + 1);
			}
			case 0: {
				if (sNumberAfterDot.length() <= aNumberDigiAfterComma) {
					if (!sNumberAfterDot.equals(""))
						sNumber = sNumberBeforeDot + "." + sNumberAfterDot;
					else
						sNumber = sNumberBeforeDot;
					return Double.parseDouble(sNumber);
				}
				if (aNumberDigiAfterComma == 0)
					return java.lang.Math.round(aNumber);
				String s = sNumberAfterDot.substring(0, aNumberDigiAfterComma);
				String s1 = sNumberAfterDot.substring(aNumberDigiAfterComma, aNumberDigiAfterComma + 1);
				if (Integer.parseInt(s1) < 5) {
					sNumber = sNumberBeforeDot + "." + s;
				} else
					sNumber = sNumberBeforeDot + "." + (Long.parseLong(s) + 1);
				return Double.parseDouble(sNumber);
			}
			}
		} catch (Exception ex) {
			return 0;
		}
		return 0;
	}

	/**
	 * add zero at left of String
	 * 
	 * @param aS
	 * @param aLen
	 * @return
	 */
	public static String PadLeft(String aS, int aLen) {
		if (aS == null || aS.equals(""))
			return "";
		String sZero = "";
		try {
			for (int i = 0; i < aLen; i++)
				sZero += "0";

			aS = sZero + aS;
			aS = aS.substring(aS.length() - aLen, aS.length());
			return aS;
		} catch (Exception ex) {
			return aS;
		}
	}

	/**
	 * add zero at right of String
	 * 
	 * @param aS
	 * @param aLen
	 * @return
	 */
	public static String PadRight(String aS, int aLen) {
		String sZero = "";
		try {
			for (int i = 0; i < aLen; i++)
				sZero += "0";
			aS = aS + sZero;
			aS = aS.substring(0, aLen);
			return aS;
		} catch (Exception ex) {
			return aS;
		}
	}

	/**
	 * use this function to break page html
	 * 
	 * @param aSource  is Vector contain a table
	 * @param aRecFrom is from pos
	 * @param aRecTo   is to pos
	 * @return Vector
	 */
	public static Vector BreakPage(Vector aSource, int aRecFrom, int aRecTo) {
		Vector PageBreak = new Vector();
		for (int i = 0; i < aSource.size(); i++) {
			if (i >= aRecFrom && i <= aRecTo) {
				PageBreak.add(aSource.elementAt(i));
			}
		}
		return PageBreak;
	}

	/**
	 * Calling a page jsp from servlet
	 * 
	 * @param request
	 * @param response
	 * @param servletPath
	 */
	public static void call(HttpServletRequest request, HttpServletResponse response, String servletPath,
			ServletContext sc) {
		try {
			// String ctxRoot="";
			// ctxRoot = request.getContextPath();
			String retPath = "/" + servletPath;
			// ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(retPath);
			rd.forward(request, response);
		} catch (Exception ex) {
			ex.getMessage();
		}
	}

	/**
	 * convert String to Shift_JIS endcode
	 * 
	 * @param aString
	 * @return
	 */
	public static String strToShift_JIS(String aString) {
		if (aString == null)
			return "";
		try {
			String sString = new String(aString.getBytes("ISO-8859-1"), "Shift_JIS");
			return sString;
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * convert String to Shift_JIS endcode
	 * 
	 * @param aString
	 * @return
	 */
	public static String strUTF8ToShift_JIS(String aString) {
		if (aString == null)
			return "";
		try {
			String sString = new String(aString.getBytes("UTF-8"), "Shift_JIS");
			return sString;
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * convert String to Shift_JIS endcode
	 * 
	 * @param aString
	 * @return
	 */
	public static String strShift_JISToUTF8(String aString) {
		if (aString == null)
			return "";
		try {
			String sString = new String(aString.getBytes("Shift_JIS"), "UTF-8");
			return sString;
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * convert String to UTF-8 endcode
	 * 
	 * @param aString
	 * @return
	 */
	public static String strToUTF_8(String aString) {
		if (aString == null)
			return "";
		try {
			String sString = new String(aString.getBytes("Cp437"), "UTF-8");

			return sString;
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * convert String to UTF-8 endcode
	 * 
	 * @param aString
	 * @return
	 */
	public static String strTo8859_1(String aString) {
		if (aString == null)
			return "";
		try {
			String sString = new String(aString.getBytes("UTF-8"), "ISO-8859-1");
			return sString;
		} catch (Exception ex) {
			return "";
		}
	}

	public static String strISOToUTF8(String aString) {
		if (aString == null)
			return "";
		try {
			String sString = new String(aString.getBytes("ISO-8859-1"), "UTF-8");
			return sString;
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * out html tags to webpage
	 * 
	 * @param aString
	 * @return
	 */
	public static String ToHtml(String aString) {
		if (aString == null || safeTrim(aString).equals(""))
			return "";
		String sString = replace(aString, "[\\x00-\\x1f]", " ");
		sString = replace(sString, "&", "&amp;");
		sString = replace(sString, "<", "&lt;");
		sString = replace(sString, ">", "&gt;");
		sString = replace(sString, "\"", "&quot;");

		return sString;
	}

	/**
	 * out html tags to webpage
	 * 
	 * @param aString
	 * @return
	 */
	public static String HtmlToTag(String aString) {
		if (aString == null || safeTrim(aString).equals(""))
			return "";
		// String sString=replace(aString," ","[\\x00-\\x1f]");
		String sString = replace(aString, "&amp;", "&");
		sString = replace(sString, "&lt;", "<");
		sString = replace(sString, "&gt;", ">");
		sString = replace(sString, "&quot;", "\"");

		return sString;
	}

	/**
	 * get param
	 * 
	 * @param request
	 * @param aName
	 * @return
	 */
	public static String GetParam(HttpServletRequest request, String aName) {
		return request.getParameter(aName) == null ? "" : strToUTF_8(request.getParameter(aName));
	}

	public static String insertSpace(String aS, int aIndex) {

		StringBuffer s = new StringBuffer(aS);
		int i = 1;
		while (true) {
			if (aIndex > aS.length())
				break;
			s = s.insert(aIndex, " ");
			aIndex += aIndex + i++;

		}
		return s.toString();
	}

	/**
	 * ������o�C�g?���J�E���g����?B
	 * 
	 * @param str return
	 */
	public static int countBytes(String str) {
		byte[] ba = str.getBytes();

		return ba.length;
	}

	public static String getRealPath(ServletContext aContext, String aName) {
		return aContext.getRealPath(aName);
	}

	/**
	 * get total page Number
	 * 
	 * @param aNumRecPerPage
	 * @param aTotalRecNum
	 * @return
	 */
	public static int getTotalPageNum(int aNumRecPerPage, int aTotalRecNum) {
		int numPage = 1;
		if (aTotalRecNum <= 0)
			return 0;
		int delta = aTotalRecNum - aNumRecPerPage;
		if (delta <= 0)
			return 1;
		else
			numPage += getTotalPageNum(aNumRecPerPage, delta);
		return numPage;
	}

	/**
	 * split String
	 * 
	 * @param str
	 * @param spltr
	 * @return
	 */
	public static String[] split(String str, String spltr) {

		Vector v = new Vector();
		boolean ok = true;

		int indx1 = 0;
		int indx2 = 0;

		str = str.trim();

		while (ok) {
			indx2 = str.indexOf(spltr, indx1);
			if (indx2 != -1) {
				v.addElement(str.substring(indx1, indx2));
				indx1 = indx2 + 1;
			} else {
				ok = false;
			}
		}

		v.addElement(str.substring(indx1, str.length()));
		String[] sResult = new String[v.size()];
		for (int i = 0; i < v.size(); i++) {
			sResult[i] = (String) v.get(i);
		}

		return sResult;
	}

	/**
	 * get cookies from client
	 * 
	 * @param cookies
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(Cookie[] cookies, String cookieName) {
		try {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookieName.equals(cookie.getName()))
					return (cookie.getValue());
			}
		} catch (Exception ex) {
			return "";
		}
		return "";
	}

	/**
	 * create folder
	 * 
	 * @param aFolderNamePath
	 * @return
	 */
	public static boolean createFolder(String aFolderNamePath) {
		try {
			File f = new File(aFolderNamePath);
			return f.mkdirs();
		} catch (Exception iox) {
			return false;
		}

	}

	/**
	 * create folder
	 * 
	 * @param aFolderNamePath
	 * @return
	 */
	public static boolean deleteFolder(String aFolderNamePath) {
		try {
			File f = new File(aFolderNamePath);
			String files[] = f.list();

			for (int i = 0; i < files.length; i++) {
				File file = new File(aFolderNamePath + "/" + files[i]);
				file.delete();
			}
			return f.delete();
		} catch (Exception iox) {
			return false;
		}

	}

	/**
	 * create folder
	 * 
	 * @param aFileNamePath
	 * @return
	 */
	public static boolean deleteFile(String aFileNamePath) {
		try {
			File f = new File(aFileNamePath);
			return f.delete();
		} catch (Exception iox) {
			return false;
		}

	}

	public static String toScript(String str) {
		if (Lib.isBlank(str))
			return "";
		str = replace(str, "\\", "\\\\");
		str = str.replace("'", "\\\'");
		str = str.replace("\"", "\\\"");
		str = str.replace("\r", "\\r");
		str = str.replace("\n", "\\n");
		return str;

	}

	public static String strToJavascript(String str) {
		if (Lib.isBlank(str))
			return "";
		str = replace(str, "\\", "\\\\");
		str = str.replace("'", "\\\'");
		str = str.replace("\"", "\\\"");
		str = str.replace("\r", "\\r");
		str = str.replace("\n", "\\n");
		return str;
	}

	/** Prevent instantiation. */
	private Lib() {
	}

	public static long strToLong(String str) {
		if (isBlank(str))
			return 0;
		try {
			str = safeTrim(str);
			str = replace(str, ",", "");
			// return Long.parseLong(str);
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			Number number = 0;
			number = format.parse(str);
			return number.longValue();
		} catch (Exception e) {
			return 0;
		}
	}

	public static float strToFloat(String str) {
		if (isBlank(str))
			return 0;
		try {
			str = safeTrim(str);
			str = replace(str, ",", "");
			// return Long.parseLong(str);
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			Number number = 0;
			number = format.parse(str);
			return number.floatValue();
		} catch (Exception e) {
			return 0;
		}
	}

	public static int strToInteger(String str) {
		if (isBlank(str))
			return 0;
		try {
			str = safeTrim(str);
			str = replace(str, ",", "");
			// return Integer.parseInt(str);
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			Number number = 0;
			number = format.parse(str);
			return number.intValue();
		} catch (Exception e) {
			return 0;
		}
	}

	public static double strToDouble(String str) {
		if (isBlank(str))
			return 0;
		try {
			String dbl = safeTrim(str);
			dbl = replace(dbl, ",", "");
			// return Double.parseDouble(dbl);
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			Number number = 0;
			number = format.parse(str);
			return number.doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * This method to cut left spaces of string
	 * 
	 * @param str
	 * @return string
	 */

	public static String leftTrim(String str) {
		Pattern p = Pattern.compile("[ \t]*(.*)");
		Matcher m = p.matcher(str);
		m.find();
		String result = m.group(1);
		return result;
	}

	/**
	 * This method to cut right spaces of string
	 * 
	 * @param str
	 * @return string
	 */

	public static String rightTrim(String str) {
		Pattern p = Pattern.compile("(.*[^ \t])[ \t]*");
		Matcher m = p.matcher(str);
		m.find();
		String result = m.group(1);
		return result;
	}

	/**
	 * This method is to check existed file
	 * 
	 * @author Trinh Nguyen
	 * @date 19-12-2008
	 * 
	 * @param file_name
	 * @return
	 */
	public static boolean checkExistFile(String file_name) {
		File file = new File(file_name);
		boolean exists = file.exists();
		if (!exists) {
			return false;
		}
		return true;
	}

	/**
	 * This method is to copy file
	 * 
	 * @author Trinh Nguyen
	 * @date 19-12-2008
	 * 
	 * @param srFile
	 * @param dtFile
	 */
	public static void copyFile(String srFile, String dtFile) {
		try {
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
			InputStream in = new FileInputStream(f1);

			// For Append the file.
			// OutputStream out = new FileOutputStream(f2,true);

			// For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException ex) {
			System.exit(0);
		} catch (IOException e) {
		}
	}

	/**
	 * get extention of file
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtFile(String fileName) {
		if (isBlank(fileName))
			return "";
		String ext = "";
		try {
			ext = fileName.substring(fileName.lastIndexOf("."));
		} catch (Exception ex) {
			return "";
		}
		return ext;
	}

	public static String convertToBase64Binary(Object jsObject) {
		try {
			// sBase64 base64 = new Base64();
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			ObjectOutputStream oStream = new ObjectOutputStream(bStream);
			oStream.writeObject(jsObject);
			byte[] byteVal = bStream.toByteArray();
			return Base64.encode(byteVal).replace("\r", "").replace("\n", "");

		} catch (Exception e) {
			return "";
		}
	}

	public static String decode64(String input) {
		String output = "";
		int chr1, chr2, chr3;
		int enc1, enc2, enc3, enc4;
		int i = 0;
		String keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		// remove all characters that are not A-Z, a-z, 0-9, +, /, or =
		input = input.replaceAll("/[^A-Za-z0-9\\+\\/\\=]/g", "");

		do {
			enc1 = keyStr.indexOf(input.charAt(i++));
			enc2 = keyStr.indexOf(input.charAt(i++));
			enc3 = keyStr.indexOf(input.charAt(i++));
			enc4 = keyStr.indexOf(input.charAt(i++));

			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;

			output = output + (char) chr1;

			if (enc3 != 64) {
				output = output + (char) chr2;
			}
			if (enc4 != 64) {
				output = output + (char) chr3;
			}
		} while (i < input.length());

		return output;
	}

	public static Object convertBase64ToObject(String base64Str) {
		try {

			byte[] byteVal = Base64.decode(base64Str);
			return toObject(byteVal);

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Converts an array of bytes back to its constituent object. The input array is
	 * assumed to have been created from the original object. Uses the Logging
	 * utilities in j2sdk1.4 for reporting exceptions.
	 * 
	 * @param bytes the byte array to convert.
	 * @return the associated object.
	 */
	public static Object toObject(byte[] bytes) {
		Object object = null;
		try {
			object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
		} catch (java.io.IOException ioe) {
		} catch (java.lang.ClassNotFoundException cnfe) {
		}
		return object;
	}

//	public static File base64ToImageFile(String img64, String folder, String filename) {
//		try {
//			byte[] decodedBytes = DatatypeConverter.parseBase64Binary(img64);
//			BufferedImage bfi;
//
//			bfi = ImageIO.read(new ByteArrayInputStream(decodedBytes));
//			Lib.createFolder(folder);
//			File outputfile = new File(folder, filename);
//
//			ImageIO.write(bfi, "png", outputfile);
//			bfi.flush();
//			return outputfile;
//		} catch (IOException e) {
//			return null;
//		}
//	}

	/**
	 * Tis method is to get cal's month by month
	 * 
	 * @author Trinh Nguyen
	 * @date 19-08-2009
	 * 
	 * @param month
	 * @return
	 */
	public static int getMonth(int month) {
		int cal_month = 0;
		if (month == 0) {
			cal_month = Calendar.JANUARY;
		} else if (month == 1) {
			cal_month = Calendar.FEBRUARY;
		} else if (month == 2) {
			cal_month = Calendar.MARCH;
		} else if (month == 3) {
			cal_month = Calendar.APRIL;
		} else if (month == 4) {
			cal_month = Calendar.MAY;
		} else if (month == 5) {
			cal_month = Calendar.JUNE;
		} else if (month == 6) {
			cal_month = Calendar.JULY;
		} else if (month == 7) {
			cal_month = Calendar.AUGUST;
		} else if (month == 8) {
			cal_month = Calendar.SEPTEMBER;
		} else if (month == 9) {
			cal_month = Calendar.OCTOBER;
		} else if (month == 10) {
			cal_month = Calendar.NOVEMBER;
		} else if (month == 11) {
			cal_month = Calendar.DECEMBER;
		}
		return cal_month;
	}

	/**
	 * This method is to get cal's day by day
	 * 
	 * @author Trinh Nguyen
	 * @date 19-08-2009
	 * 
	 * @param day
	 * @return
	 */
	public static String getDay(int day) {
		String cal_day = "";
		if (day == 1) {
			cal_day = "SUNDAY";
		} else if (day == 2) {
			cal_day = "MONDAY";
		} else if (day == 3) {
			cal_day = "TUESDAY";
		} else if (day == 4) {
			cal_day = "WEDNESDAY";
		} else if (day == 5) {
			cal_day = "THURSDAY";
		} else if (day == 6) {
			cal_day = "FRIDAY";
		} else if (day == 7) {
			cal_day = "SATURDAY";
		}
		return cal_day;
	}

	/**
	 * lay so gio gia 2 ngay
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int getHourBetween2Date(Date fromDate, Date toDate) {
		try {
			return (int) Math.ceil(
					Math.abs((double) ((double) (fromDate.getTime() - toDate.getTime()) / (double) (1000 * 60 * 60))));
		} catch (Exception ex) {
			return 0;

		}
	}

	public static int daysBetween2Dates(Date fromDate, Date toDate) {
		try {
			return (int) Math.ceil(Math
					.abs((double) ((double) (fromDate.getTime() - toDate.getTime()) / (double) (24 * 1000 * 60 * 60))));
		} catch (Exception ex) {
			return 0;

		}
	}

	/**
	 * cach tinh tien benh vien, 1 ngay chia ra làm 6 block, 1 block 6h 1 ngày sẽ có
	 * 4 mức: 0.25 nếu số giờ nhỏ hơn hoặc bằng 6h 0.5 nếu số giờ >6h và <=12h 0.75
	 * nếu số giờ >12h và <= 18h 1 nếu số giờ >18h và <=24h
	 * 
	 * @param hour
	 * @return
	 */
	public static double getDaysOfBedNoiTru(int hour) {
		double numRealDays = 0.0;
		int numDays = (int) Math.floor((double) hour / 24.0);// tổng số ngày
		// lấy giờ lẽ
		int digit = hour - numDays * 24;
		if (digit <= 6)
			numRealDays = numDays + 0.25;
		else if (digit > 6 && digit <= 12)
			numRealDays = numDays + 0.5;
		else if (digit > 12 && digit <= 18)
			numRealDays = numDays + 0.75;
		else if (digit > 18 && digit <= 24)
			numRealDays = numDays + 1.0;
		return numRealDays;
	}

	/**
	 * This method is to check drink_time < current_time
	 * 
	 * @author Trinh Nguyen
	 * @date 19-05-2009
	 * 
	 * @param drink_hour
	 * @param drink_time
	 * @return
	 */
	public static boolean checkBeforeCurrentTime(String drink_hour, String drink_time) {
		boolean flag;
//		Calendar cal1 = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance(TimeZone.getDefault());
//		Calendar cal2 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance(TimeZone.getDefault());
		cal1.set(2009, 5, 19, Lib.strToInteger(drink_hour), Lib.strToInteger(drink_time), 0);
		cal2.set(2009, 5, 19, cal2.get(Calendar.HOUR_OF_DAY), cal2.get(Calendar.MINUTE), 0);
		flag = cal1.getTime().before(cal2.getTime());
		return flag;
	}

	public static boolean checkBeforeCurrentTime1(Date drink_date) {
		Date now = new Date();
		return drink_date.before(now);
	}

	/**
	 * This method is to subtract time
	 * 
	 * @author Trinh Nguyen
	 * @date 04-08-2009
	 * 
	 * @param start_time
	 * @param end_time
	 * @return
	 */
	public static double getPeriod(int start_hour, int start_minute, int end_hour, int end_minute) {
		double result = 0;
		GregorianCalendar scal = new GregorianCalendar(2009, 8, 4, start_hour, start_minute);
		GregorianCalendar escal = new GregorianCalendar(2009, 8, 4, end_hour, end_minute);
		Date sdate = scal.getTime();
		Date edate = escal.getTime();
		result = (edate.getTime() - sdate.getTime()) / 60000;
		result /= 60;
		return result;
	}

	/* Tinh khoang cach ngay giua 2 ngay */
	public static int subtractDays(Date date1, Date date2) {
		Calendar gc1 = Calendar.getInstance();
		gc1.setTime(date1);
		Calendar gc2 = Calendar.getInstance();
		gc2.setTime(date2);

		int days1 = 0;
		int days2 = 0;
		int maxYear = Math.max(gc1.get(Calendar.YEAR), gc2.get(Calendar.YEAR));

		Calendar gctmp = (Calendar) gc1.clone();
		for (int f = gctmp.get(Calendar.YEAR); f < maxYear; f++) {
			days1 += gctmp.getActualMaximum(Calendar.DAY_OF_YEAR);
			gctmp.add(Calendar.YEAR, 1);
		}

		gctmp = (Calendar) gc2.clone();
		for (int f = gctmp.get(Calendar.YEAR); f < maxYear; f++) {
			days2 += gctmp.getActualMaximum(Calendar.DAY_OF_YEAR);
			gctmp.add(Calendar.YEAR, 1);
		}

		days1 += gc1.get(Calendar.DAY_OF_YEAR) - 1;
		days2 += gc2.get(Calendar.DAY_OF_YEAR) - 1;

		return (days1 - days2);
	}

	/**
	 * This method is to get field_value by field_key
	 * 
	 * @author Trinh Nguyen
	 * @date 29-09-2009
	 * 
	 * @param field_key
	 * @return
	 */
	public static String getFieldValue(String field_key) {
		return !Lib.isBlank(field_key) ? field_key : "";
	}

	/**
	 * get hour and minute string
	 * 
	 * @return hh:mm
	 */
	public static String getHourMinute() {
		return PadLeft(GetCurrentHour() + "", 2) + ":" + PadLeft(GetCurrentMinute() + "", 2);
	}

	/**
	 * get last day of month
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		int date = 1;
		calendar.set(year, month - 1, date);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDay;
	}

	/**
	 * get date after added number day base on current day lay ngay sau khi da cong
	 * them bao nhieu ngay vd: hom nay: 09/04/2010 goi ham getDateAddedDays(10) se
	 * tra ve ngay 19/04/2010
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateAddedDays(int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());// current date
		c.add(Calendar.DATE, days); // number of days to add
		return c.getTime();
	}

	public static String getIP(HttpServletRequest servletRequest) {
		try {
			String ip = servletRequest.getRemoteAddr();
			return ip;
		} catch (Exception ex) {
			return "";
		}
	}

	public static String getIP1(HttpServletRequest request) {
		String ip = "";
		if (request != null) {
			if (request.getHeader("HTTP_X_FORWARDED_FOR") != null) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			} else if (request.getHeader("HTTP_CLIENT_IP") != null) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			} else {
				ip = request.getHeader("REMOTE_ADDR");
			}
		}
		return ip;
	}

	/**
	 * Ham kiem tra 2 ngay truyen vao co bang nhau khong(chi kiem tra phan ngay
	 * khong kiem tra phan gio)
	 * 
	 * @author MinhPham date 25-07-2011
	 * @return
	 */
	public static boolean compareDate(Date date1, Date date2) {
		String str_date1 = DateToString(date1, "yyyyMMdd");
		String str_date2 = DateToString(date2, "yyyyMMdd");
		if (str_date1.equals(str_date2))
			return true;
		return false;
	}

	public static String getCurdateWorld(String patern) {
		try {
			SimpleDateFormat gmtDateFormat = new SimpleDateFormat(patern);
			gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			return gmtDateFormat.format(new Date());
		} catch (Exception ex) {
			return "";
		}

	}

	public static long daysBetween2Dates(int year, int month, int day) {
		long days = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance(TimeZone.getDefault());
		c1.set(year, month, day);
		days = ((c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000));
		return days;
	}

	public static String getContentResult(HttpServletRequest request, URL url) throws IOException {

		String content = "";
		URLConnection con = (URLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);
		con.setAllowUserInteraction(true);
		con.setRequestProperty("Cookie", "JSESSIONID=" + request.getSession().getId());
		con.connect();
		InputStreamReader in = new InputStreamReader((InputStream) con.getContent());
		BufferedReader buff = new BufferedReader(in);

		String line;
		do {
			line = buff.readLine();
			content += line;
		} while (line != null);
		return content;
	}

	/**
	 * This method is to get re_examine_date after 7 days
	 * 
	 * @author Trinh Nguyen
	 * @date 17-02-2009
	 * 
	 * @return
	 */
	public static String getReExamineDate(int date_number) {
		String re_examine_date = "";
		Calendar date = new GregorianCalendar();
		Calendar cal = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH),
				date.get(Calendar.DATE) + date_number, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
		re_examine_date = Lib.PadLeft(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2) + "/"
				+ Lib.PadLeft(String.valueOf((cal.get(Calendar.MONTH) + 1)), 2) + "/"
				+ Lib.PadLeft(String.valueOf(cal.get(Calendar.YEAR)), 4) + " "
				+ Lib.PadLeft(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)), 2) + ":"
				+ Lib.PadLeft(String.valueOf(cal.get(Calendar.MINUTE)), 2);
		return re_examine_date;
	}

	public static String MemmoryInfo() {
		// final RuntimeMXBean memMXBean = ManagementFactory.getRuntimeMXBean();
		// final String prefix = "-XX:MaxPermSize=";
		// final List<String> jvmArgs = memMXBean.getInputArguments();
		// String maxPermSize = null;
		// for (final String jvmArg : jvmArgs) {
		// if (jvmArg.startsWith(prefix)) {
		// maxPermSize = jvmArg.substring(prefix.length());
		// break;
		// }
		// }
		List<MemoryPoolMXBean> beans = ManagementFactory.getMemoryPoolMXBeans();
		MemoryPoolMXBean permgenBean = null;
		for (MemoryPoolMXBean bean : beans) {
			if (bean.getName().toLowerCase().indexOf("perm gen") >= 0) {
				permgenBean = bean;
				break;
			}
		}
		MemoryUsage currentUsage = permgenBean.getUsage();
		int percentageUsed = (int) ((currentUsage.getUsed() * 100) / currentUsage.getMax());
		String memInfo = GetDateNow() + ": Permgen " + currentUsage.getUsed() + " of " + currentUsage.getMax() + " ("
				+ percentageUsed + "%)";
		return memInfo;

	}

	public static java.util.Calendar StringToCalendar(String aS) {
		if (isBlank(aS))
			return null;
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdf.parse(aS));// all done
		} catch (Exception e) {
			return null;
		}
		return cal;

	}

	public static java.util.Calendar StringToCalendar(Date aS) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		try {
			cal.setTime(aS);// all done
		} catch (Exception e) {
			return null;
		}
		return cal;

	}

	/**
	 * convert string json to object
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T convertJson2Object(String jsonStr, Class<T> clazz) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(jsonStr, clazz);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Convert tiếng việt có dấu thành tiếng việt không dấu
	 * 
	 * @param org
	 * @return
	 */
	public static String convertToVnNoSign(String org) {
		// convert to VNese no sign. @haidh 2008
		char arrChar[] = org.toCharArray();
		char result[] = new char[arrChar.length];
		for (int i = 0; i < arrChar.length; i++) {
			switch (arrChar[i]) {
			case '\u00E1':
			case '\u00E0':
			case '\u1EA3':
			case '\u00E3':
			case '\u1EA1':
			case '\u0103':
			case '\u1EAF':
			case '\u1EB1':
			case '\u1EB3':
			case '\u1EB5':
			case '\u1EB7':
			case '\u00E2':
			case '\u1EA5':
			case '\u1EA7':
			case '\u1EA9':
			case '\u1EAB':
			case '\u1EAD':
			case '\u0203':
			case '\u01CE': {
				result[i] = 'a';
				break;
			}
			case '\u00E9':
			case '\u00E8':
			case '\u1EBB':
			case '\u1EBD':
			case '\u1EB9':
			case '\u00EA':
			case '\u1EBF':
			case '\u1EC1':
			case '\u1EC3':
			case '\u1EC5':
			case '\u1EC7':
			case '\u0207': {
				result[i] = 'e';
				break;
			}
			case '\u00ED':
			case '\u00EC':
			case '\u1EC9':
			case '\u0129':
			case '\u1ECB': {
				result[i] = 'i';
				break;
			}
			case '\u00F3':
			case '\u00F2':
			case '\u1ECF':
			case '\u00F5':
			case '\u1ECD':
			case '\u00F4':
			case '\u1ED1':
			case '\u1ED3':
			case '\u1ED5':
			case '\u1ED7':
			case '\u1ED9':
			case '\u01A1':
			case '\u1EDB':
			case '\u1EDD':
			case '\u1EDF':
			case '\u1EE1':
			case '\u1EE3':
			case '\u020F': {
				result[i] = 'o';
				break;
			}
			case '\u00FA':
			case '\u00F9':
			case '\u1EE7':
			case '\u0169':
			case '\u1EE5':
			case '\u01B0':
			case '\u1EE9':
			case '\u1EEB':
			case '\u1EED':
			case '\u1EEF':
			case '\u1EF1': {
				result[i] = 'u';
				break;
			}
			case '\u00FD':
			case '\u1EF3':
			case '\u1EF7':
			case '\u1EF9':
			case '\u1EF5': {
				result[i] = 'y';
				break;
			}
			case '\u0111': {
				result[i] = 'd';
				break;
			}
			case '\u00C1':
			case '\u00C0':
			case '\u1EA2':
			case '\u00C3':
			case '\u1EA0':
			case '\u0102':
			case '\u1EAE':
			case '\u1EB0':
			case '\u1EB2':
			case '\u1EB4':
			case '\u1EB6':
			case '\u00C2':
			case '\u1EA4':
			case '\u1EA6':
			case '\u1EA8':
			case '\u1EAA':
			case '\u1EAC':
			case '\u0202':
			case '\u01CD': {
				result[i] = 'A';
				break;
			}
			case '\u00C9':
			case '\u00C8':
			case '\u1EBA':
			case '\u1EBC':
			case '\u1EB8':
			case '\u00CA':
			case '\u1EBE':
			case '\u1EC0':
			case '\u1EC2':
			case '\u1EC4':
			case '\u1EC6':
			case '\u0206': {
				result[i] = 'E';
				break;
			}
			case '\u00CD':
			case '\u00CC':
			case '\u1EC8':
			case '\u0128':
			case '\u1ECA': {
				result[i] = 'I';
				break;
			}
			case '\u00D3':
			case '\u00D2':
			case '\u1ECE':
			case '\u00D5':
			case '\u1ECC':
			case '\u00D4':
			case '\u1ED0':
			case '\u1ED2':
			case '\u1ED4':
			case '\u1ED6':
			case '\u1ED8':
			case '\u01A0':
			case '\u1EDA':
			case '\u1EDC':
			case '\u1EDE':
			case '\u1EE0':
			case '\u1EE2':
			case '\u020E': {
				result[i] = 'O';
				break;
			}
			case '\u00DA':
			case '\u00D9':
			case '\u1EE6':
			case '\u0168':
			case '\u1EE4':
			case '\u01AF':
			case '\u1EE8':
			case '\u1EEA':
			case '\u1EEC':
			case '\u1EEE':
			case '\u1EF0': {
				result[i] = 'U';
				break;
			}

			case '\u00DD':
			case '\u1EF2':
			case '\u1EF6':
			case '\u1EF8':
			case '\u1EF4': {
				result[i] = 'Y';
				break;
			}
			case '\u0110':
			case '\u00D0':
			case '\u0089': {
				result[i] = 'D';
				break;
			}
			default:
				result[i] = arrChar[i];
			}
		}
		return new String(result);
	}

	public static String correctPhoneNumberForSendSMS(String phone) {
		if (isBlank(phone))
			return "";
		phone = phone.replace("+", "");
		if (phone.startsWith("84", 0)) {
			return phone;
		} else {
			if (phone.startsWith("0", 0)) {
				phone = phone.substring(1);
			}
			phone = "84" + phone;
		}
		return phone;
	}

	/**
	 * convert object to json string
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> String convertObj2JsonString(T obj) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(obj);
		return jsonStr;
	}

	public static String getAge(int day, int month, int year, int curDay, int curMonth, int curYear) {

		if (day <= 0)
			day = 1;
		if (month <= 0)
			month = 1;
		if (year <= 0)
			return "0";
		int age_year = 0;
		if (curMonth == month && curDay < day) {
			age_year = 1;
		}
		String dateStop = curYear + "/" + curMonth + "/" + curDay;
		String dateStart = year + "/" + month + "/" + day;

		// Custom date format
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long diff = d2.getTime() - d1.getTime();

		int days = (int) (diff / (24 * 60 * 60 * 1000));

		double avgMonth = 365.0 / 12;
		double tt_mm = days / avgMonth;
		// int tt_day = days % 30;
		double tt_nam = tt_mm / 12 - age_year;
		double tt_mm_cl = (tt_mm % 12);

		int ageP = 0;
		ageP = (int) Lib.Round(tt_nam, 2, 0);
		if (tt_mm_cl >= 11)
			ageP++;
		return ageP + "";
	}

	public static boolean checkBitOnOff(int nByte, int bitIndex) {
		int result = nByte & Lib.strToInteger(Math.pow(2, bitIndex) + "");
		return result != 0 ? true : false;
	}

	public static void main(String[] args) throws Exception {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.add(Calendar.DAY_OF_MONTH, 2);
		int day = cal.get(Calendar.DAY_OF_WEEK);
//		SecretCards cet = new SecretCards();
//		String k = cet.encrypt("1,419");
//		String value = cet.decrypt(k);
		// DOB: 18/10/2013 - 16/10/2018
//		String a = getAge(18, 10, 2013, 16, 10, 2018);
//		a = getAge(18, 10, 2013, 1, 10, 2018);
//		a = getAge(30, 3, 2017, 17, 10, 2018);
//		a = getAge(18, 10, 2013, 1, 1, 2018);
//		a = getAge(18, 10, 2013, 18, 10, 2018);
//		a = getAge(26, 7, 2014, 6, 6, 2018);
//		a = getAge(13, 6, 2016, 6, 6, 2018);
	}

	public static int getQuarterOfYear(int month) {
		int quarter = 0;
		if (month >= 1 && month <= 3)
			quarter = 1;
		else if (month >= 4 && month <= 6)
			quarter = 2;
		else if (month >= 7 && month <= 9)
			quarter = 3;
		else if (month >= 10 && month <= 12)
			quarter = 4;
		else
			quarter = 0;
		return quarter;
	}

	public static String removeAccent(String s) {
		String result = "";
		char[] charData = s.toCharArray();
		for (char ch : charData) {
			result += removeAccent(ch);
		}
		return result.trim().replaceAll(" +", " ");
	}

	public static char removeAccent(char ch) {
		int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
		if (index >= 0) {
			ch = REPLACEMENTS[index];
		}
		return ch;
	}

	public static Date addMinuteDate(Date date, int minutes) {
		if (date == null)
			return new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);// current date
		c.add(Calendar.MINUTE, minutes); // number of minutes to add
		return c.getTime();
	}

	public static Date addDate(Date date, int field, int interval) {
		if (date == null)
			return new Date();
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.setTime(date);// current date
		c.add(field, interval); // number of minutes to add
		return c.getTime();
	}

	public static Date setTime(Date date, int field, int interval) {
		if (date == null)
			return new Date();
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.setTime(date);// current date
		c.set(field, interval); // number of minutes to add
		return c.getTime();
	}

	/**
	 * combine paths
	 * 
	 * @param paths
	 * @return
	 */
	public static String combine(String... paths) {
		File file = new File(paths[0]);

		for (int i = 1; i < paths.length; i++) {
			file = new File(file, paths[i]);
		}

		return file.getPath();
	}

	/**
	 * LinkMap to object
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public static <T> T cashLinkMap2Object(Object map, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		T object = mapper.convertValue(map, clazz);
		return object;
	}

	public static String readProperty(ResourceBundle resourceBundle, String key, String defaultValue) {
		String value = defaultValue;
		try {
			value = resourceBundle.getString(key);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return value;
	}

	@SuppressWarnings("resource")
	public static String uploadFromBase64(String strUploadData, String fileName, String saveDir) {
		try {
			String[] data = strUploadData.split(",");
			String imgData = data[1];
			String extension;
			byte[] imageByte = org.apache.commons.codec.binary.Base64.decodeBase64(imgData);
			switch (data[0]) {// check image's extension
			case "data:image/jpeg;base64":
				extension = "jpeg";
				break;
			case "data:image/png;base64":
				extension = "png";
				break;

			case "data:text/csv;base64":
				extension = "csv";
				break;
			
			case "data:application/pdf;base64":
				extension = "pdf";
				break;
			case "data:application/msword;base64":
				extension = "doc";
				break;
			case "data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64":
				extension = "docx";
				break;
				
			case "data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64":
				extension = "xlsx";
				break;
				
			case "data:application/vnd.ms-excel;base64":
				extension = "xls";
				break;
			default:// should write cases for more images types
				extension = "jpg";
				break;
			}
			
			Files.createDirectories(Paths.get(saveDir));
			String directory = saveDir + "/" + fileName + "." + extension;

			new FileOutputStream(directory).write(imageByte);
			return fileName + "." + extension;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static String getReourcePropValue(String resourceFileName, String resourceKey) {
		ResourceBundle.Control utf8Control = new Utf8ResourceBundleControl();
		ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceFileName, utf8Control);
		return Lib.readProperty(resourceBundle, resourceKey, "/tmp");
	}

	public static String subStr(String text, int startIndex, int endIdex) {
		if (Lib.isBlank(text)) {
			return text;
		}
		if (endIdex >= text.length()) {
			endIdex = text.length() - 1;
		}
		return text.substring(startIndex, endIdex);
	}

	/**
	 * build hash from string sha - 256
	 * 
	 * @param s
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String hashString(String s) throws NoSuchAlgorithmException {
		byte[] hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			hash = md.digest(s.getBytes());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hash.length; ++i) {
			String hex = Integer.toHexString(hash[i]);
			if (hex.length() == 1) {
				sb.append(0);
				sb.append(hex.charAt(hex.length() - 1));
			} else {
				sb.append(hex.substring(hex.length() - 2));
			}
		}
		return sb.toString();
	}

	// Function return true if given element
	// found in array
	public static boolean checkElementInArr(int[] arr, int toCheckValue) {
		// check if the specified element
		// is present in the array or not
		// using Linear Search method
		boolean state = false;
		for (int element : arr) {
			if (element == toCheckValue) {
				state = true;
				break;
			}
		}

		return state;
	}

	public static String randomString(int len) {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~!@#$%^&*()_-";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
	
	public static String[] removeAllEmpty(String[] arr) {
	    if (arr == null)
	        return arr;

	    String[] result = new String[arr.length];
	    int amountOfValidStrings = 0;

	    for (int i = 0; i < arr.length; i++) {
	        if (!arr[i].equals(""))
	            result[amountOfValidStrings++] = arr[i];
	    }
	    result = Arrays.copyOf(result, amountOfValidStrings);

	    return result;
	}
	
	
	
	
	/**
	 * This method is validate date
	 * 
	 * @author Long.Pham
	 * @date 2023-05-31
	 * 
	 * @return
	 */
	
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static boolean isDateValid(String date) {
		try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
	}
	
	public static List<String> datetimeToCronExp(int periodicity, String timeSchedule, String daysWeek, String offset_timezone) {
		try {
			List<String> cronsList = new ArrayList<String>();
			if (timeSchedule.isEmpty()) return new ArrayList<String>();
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(timeSchedule, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of(offset_timezone)));
			ZonedDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
			
			switch (periodicity) {
				case 1: { // daily
					String cron = String.format("0 %d %d * * *", localDateTime.getMinute(), localDateTime.getHour());
					cronsList.add(cron);
					break;
				}
				case 2: { // weekly
					if (daysWeek.isEmpty()) return new ArrayList<String>();
					List<String> days = new ArrayList<>();
					for (int i = 0; i < daysWeek.length(); i++) {
						if (Character.compare((char) daysWeek.charAt(i), '0') == 0) continue;
						days.add(String.valueOf(i + 1));
					}
					String cron = String.format("0 %d %d * * %s", localDateTime.getMinute(), localDateTime.getHour(), days.stream().collect(Collectors.joining(",")));
					cronsList.add(cron);
					break;
				}
				case 3: { // monthly
					// last day of month
					if (localDateTime.getDayOfMonth() == localDateTime.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
						for (int i = 1; i <= 12; i++) {
							String cron = String.format("0 %d %d %d %d *", localDateTime.getMinute(), localDateTime.getHour(), localDateTime.withMonth(i).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth(), i);
							cronsList.add(cron);
						}
					} else {
						// day out of range in February
						if (localDateTime.getDayOfMonth() > 28) {
							String cron = String.format("0 %d %d %d 1,3-12 *", localDateTime.getMinute(), localDateTime.getHour(), localDateTime.getDayOfMonth());
							cronsList.add(cron);
							
							cron = String.format("0 %d %d 28 2 *", localDateTime.getMinute(), localDateTime.getHour());
							cronsList.add(cron);
						} else {
							String cron = String.format("0 %d %d %d * *", localDateTime.getMinute(), localDateTime.getHour(), localDateTime.getDayOfMonth());
							cronsList.add(cron);
						}
					}
					break;
				}
				case 4: { // quarterly
					// last day of month
					if (localDateTime.getDayOfMonth() == localDateTime.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
						for (int i = 0; i < 4; i++) {
							int month = (localDateTime.getMonthValue() + 3 * i) > 12 ? localDateTime.getMonthValue() + 3 * i - 12 : localDateTime.getMonthValue() + 3 * i;
							String cron = String.format("0 %d %d %d %d *", localDateTime.getMinute(), localDateTime.getHour(), localDateTime.withMonth(month).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth(), month);
							cronsList.add(cron);
						}
					} else {
						for (int i = 0; i < 4; i++) {
							int month = (localDateTime.getMonthValue() + 3 * i) > 12 ? localDateTime.getMonthValue() + 3 * i - 12 : localDateTime.getMonthValue() + 3 * i;
							String cron = String.format("0 %d %d %d %d *", localDateTime.getMinute(), localDateTime.getHour(), localDateTime.getDayOfMonth() > 28 && month == 2 ? 28 : localDateTime.getDayOfMonth(), month);
							cronsList.add(cron);
						}
					}
					break;
				}
				case 5: { // annually
					String cron = String.format("0 %d %d %d %d *", localDateTime.getMinute(), localDateTime.getHour(), localDateTime.getDayOfMonth(), localDateTime.getMonthValue());
					cronsList.add(cron);
					break;
				}
				default:
					break;
			}
			return cronsList;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

}
