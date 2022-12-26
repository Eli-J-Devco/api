package com.nwm.api.DBManagers;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.nwm.api.utils.Constants;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.SecretCards;

@SuppressWarnings("rawtypes")
public class DB {
	public static SqlSessionFactory sqlMap;
    protected static final FLLogger dbLog = FLLogger.getLogger("db/mysqlLog");
    protected final FLLogger log = FLLogger.getLogger("service/"+this.getClass().getSimpleName());
	private static String environment = "development";

	// ~--- static initializers ------------------------------------------------

	/* Create a singleton SqlMap object */
	static {
		try {
			Reader configReader = null;
			try {
				configReader = Resources.getResourceAsReader(Constants.sqlMapconfigXml);
				dbLog.info("read sqlmapconfig.xml is ok");
			} catch (Exception ex) {
				dbLog.info("read sqlmapconfig.xml is not ok");
			}
			try {
				Properties props = initProperties();
				sqlMap = new SqlSessionFactoryBuilder().build(configReader, environment, props);
				dbLog.info("connect DB Successful");
			} catch (Exception ex1) {
				sqlMap = null;
				dbLog.error(ex1);
				System.out.println(ex1);
			}
		} catch (Throwable ex) {
			sqlMap = null;
			System.out.println(ex);
			dbLog.error(ex);
			dbLog.error("connect DB unSuccessful");
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) {

	}
	private static String readProperty(ResourceBundle resourceBundle, String key, String defaultValue) {
		String value = defaultValue;
		try {
			value = resourceBundle.getString(key);
		}catch (Exception e) {
			// TODO: handle exception
			dbLog.error(e);
		}
		return value;
	}
	private static Properties initProperties() {
		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.dataBaseConfigFile);
			Properties props = new Properties();
			String driver = readProperty(resourceBundle,"driver","com.mysql.jdbc.Driver");
			String url = readProperty(resourceBundle,"url","");
			String encryptedUsername = readProperty(resourceBundle,"username","");
			String encryptedPassword = readProperty(resourceBundle,"password","");
			String isEnscrypt = readProperty(resourceBundle,"encrypt","");
			String mysql_maximum_active_connections = readProperty(resourceBundle,"mysql_maximum_active_connections","50");
			if (!"0".equals(mysql_maximum_active_connections)) {
				props.setProperty("poolMaximumActiveConnections", mysql_maximum_active_connections);
			}
			/* Put the plain text for driver and url */
			props.setProperty("database.driver", driver);
			props.setProperty("database.url", url);
			SecretCards secretCards = new SecretCards();
			if ("false".equals(isEnscrypt)) {
				props.setProperty("database.username", encryptedUsername);
				props.setProperty("database.password", encryptedPassword);
			} else {
				/* Decrypt the username and password, then put in map */
				String username = secretCards.decrypt(encryptedUsername);
				String pass = secretCards.decrypt(encryptedPassword);
				props.setProperty("database.username", username);
				props.setProperty("database.password", pass);
			}
			return props;
		} catch (Exception e) {
			dbLog.error(e);
			return null;
		}
	}
	// ~--- fields -------------------------------------------------------------

	/* Auto transaction management mode flag */
	// private boolean pAutoTransactionMode;

	/* Use transaction flag */
	// private boolean pIsTransaction;

	// ~--- constructors -------------------------------------------------------

	/**
	 * DB class' Constructor
	 */
	public DB() {

		// Default is not using Transaction
		// this.pIsTransaction = false; // This means AutoCommit also

		// Let SqlMap to manage transaction in default
		// this.pAutoTransactionMode = true;
		if (sqlMap == null) {
			try {
				Reader configReader = null;
				try {
					configReader = Resources.getResourceAsReader(Constants.sqlMapconfigXml);
					dbLog.info("read sqlmapconfig.xml is ok");
				} catch (Exception ex) {
					dbLog.info("read sqlmapconfig.xml is not ok");
				}
				try {
					Properties props = initProperties();
					sqlMap = new SqlSessionFactoryBuilder().build(configReader, environment, props);
					dbLog.error("connect DB Successful");
				} catch (Exception ex1) {
					sqlMap = null;
					dbLog.error(ex1);
				}
			} catch (Throwable ex) {
				sqlMap = null;
				dbLog.error(ex);
				dbLog.error("connect DB error");
				throw new ExceptionInInitializerError(ex);
			}
		}
		// sqlMap.getUserConnection().setAutoCommit(true);
	}

	// ~--- methods ------------------------------------------------------------

	/**
	 * 
	 * Commit changes to Oracle DB
	 * 
	 */
	public void commit() throws SQLException {
		SqlSession session = sqlMap.openSession();
		session.commit();
	}

	/**
	 * 
	 * Make an Delete call to DB Method Name: delete
	 *
	 * @param aSqlID    a mapped Delete SQL orgID
	 * @param aParamObj parameter class
	 * @return int number of rows effected
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected int delete(String aSqlID, Object aParamObj) throws SQLException {
		// Number of rows effected
		int iRet = 0;
		SqlSession session = sqlMap.openSession(true);
		try {
			// Ask SqlMap to do delete task
			iRet = session.delete(aSqlID, aParamObj);

		} catch (Exception ex) {
			dbLog.error(ex);
		} finally {
			session.close();
		}

		// return number of effected rows
		return iRet;
	}
	/**
	 * open the transaction
	 * @return
	 */
	public SqlSession beginTransaction() {
		try {
			return sqlMap.openSession(false);
		}catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	/**
	 * 
	 * An alias of queryForList function
	 *
	 * @param aSqlID a mapped query orgID
	 * @return List A List of result objects
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected List executeFuncForList(String aSqlID) throws SQLException {

		// HashMap for returns
		HashMap mapParams = new HashMap();

		// Query for list and result will be put into the HashMap
		return this.executeFuncForList(aSqlID, mapParams, Constants.DEFAULT_RETURN_KEY);
	}

	/**
	 * 
	 * An alias of queryForList function
	 *
	 * @param aSqlID    a mapped query orgID
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @return List A List of result objects
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected List executeFuncForList(String aSqlID, Object aParamObj) throws SQLException {

		// Query for list and result will be put into the HashMap
		return this.executeFuncForList(aSqlID, aParamObj, Constants.DEFAULT_RETURN_KEY);
	}

	/**
	 * 
	 * An alias of queryForList function
	 *
	 * @param aSqlID    a mapped query orgID
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @return List A List of result objects
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected List executeFuncForList(String aSqlID, Object aParamObj, String returnID) throws SQLException {
		List ret = this.queryForList(aSqlID, aParamObj);

		if ((ret == null) || ((ret != null) && ret.isEmpty())) {
			try {
				HashMap map = (HashMap) aParamObj;

				ret = (List) map.get(returnID);
			} catch (ClassCastException ex) {
				ret = new ArrayList();
			}
		}

		return ret;
	}

	/**
	 * 
	 * An alias of executeFuncForList function without return ID parameter. This ID
	 * will be take from the class Constants
	 *
	 * @param aSqlID    a mapped query orgID
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @param skip      The number of results to ignore.
	 * @param max       The maximum number of results to return.
	 * @return List A List of result objects
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected List executeFuncForList(String aSqlID, Object aParamObj, int skip, int max) throws SQLException {
		return this.executeFuncForList(aSqlID, aParamObj, skip, max, Constants.DEFAULT_RETURN_KEY);
	}

	/**
	 * 
	 * An alias of queryForList function.
	 *
	 * @param aSqlID    a mapped query orgID
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @param skip      The number of results to ignore.
	 * @param max       The maximum number of results to return.
	 * @return List A List of result objects
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected List executeFuncForList(String aSqlID, Object aParamObj, int skip, int max, String returnID)
			throws SQLException {
		List ret = this.queryForList(aSqlID, aParamObj, skip, max);

		if ((ret == null) || ((ret != null) && ret.isEmpty())) {
			try {
				HashMap map = (HashMap) aParamObj;

				ret = (List) map.get(returnID);
			} catch (ClassCastException ex) {
				ret = new ArrayList();
			}
		}

		return ret;
	}

	/**
	 * 
	 * An alias of queryForMap function.
	 *
	 * @param aSqlID    a mapped query orgID
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @param aKeyProp  The property to be used as the key in the Map.
	 * @return Map A Map keyed by aKeyProp with values being the result object
	 *         instance
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected Map executeFuncForMap(String aSqlID, Object aParamObj, String aKeyProp) throws SQLException {
		return queryForMap(aSqlID, aParamObj, aKeyProp);
	}

	/**
	 * 
	 * An alias of queryForMap function.
	 *
	 * @param aSqlID     a mapped query orgID
	 * @param aParamObj  The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @param aKeyProp   The property to be used as the key in the Map.
	 * @param aValueProp The property to be used as the value in the Map.
	 * @return Map A Map keyed by aKeyProp with values being the result object
	 *         instance
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected Map executeFuncForMap(String aSqlID, Object aParamObj, String aKeyProp, String aValueProp)
			throws SQLException {
		return this.queryForMap(aSqlID, aParamObj, aKeyProp, aValueProp);
	}

	/**
	 * 
	 * An alias of queryForObject function
	 *
	 * @param aSqlID    The orgName of the statement to execute
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @return Object The single result object populated with the result set data,
	 *         or null if no result was found
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected Object executeFuncForObject(String aSqlID) throws SQLException {

		// HashMap for returns
		HashMap mapParams = new HashMap();

		// Return one object specified by returnID
		return executeFuncForObject(aSqlID, mapParams, Constants.DEFAULT_RETURN_KEY);
	}

	/**
	 * 
	 * An alias of queryForObject function
	 *
	 * @param aSqlID    The orgName of the statement to execute
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @return Object The single result object populated with the result set data,
	 *         or null if no result was found
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected Object executeFuncForObject(String aSqlID, Object aParamObj) throws SQLException {
		return executeFuncForObject(aSqlID, aParamObj, Constants.DEFAULT_RETURN_KEY);
	}

	/**
	 * 
	 * An alias of queryForObject function
	 *
	 * @param aSqlID        The orgName of the statement to execute
	 * @param aParamObj     The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @param aResultObject The result object instance that should be populated with
	 *                      result data.
	 * @return Object The single result object populated with the result set data,
	 *         or null if no result was found
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected Object executeFuncForObject(String aSqlID, Object aParamObj, Object aResultObject) throws SQLException {
		return queryForObject(aSqlID, aParamObj, aResultObject);
	}

	/**
	 * 
	 * An alias of queryForObject function
	 *
	 * @param aSqlID    The orgName of the statement to execute
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @return Object The single result object populated with the result set data,
	 *         or null if no result was found
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected Object executeFuncForObject(String aSqlID, Object aParamObj, String returnID) throws SQLException {
		Object ret = this.queryForObject(aSqlID, aParamObj);

		try {
			if (ret == null) {
				HashMap map = (HashMap) aParamObj;
				try {
					List lst = (List) map.get(returnID);
					if (lst == null || lst.size() == 0)
						return null;
					ret = lst.get(0);
				} catch (ClassCastException ex) {
					return map.get(returnID);
				}
			} else {
				List lst = (List) ret;

				if (lst.isEmpty()) {
					HashMap map = (HashMap) aParamObj;

					ret = map.get(returnID);
				}
			}
		} catch (ClassCastException ex) {
			dbLog.error(ex);
			// Do nothing
		}

		return ret;
	}

	/**
	 * using for break page
	 * 
	 * @param aSqlID
	 * @param aParamObj
	 * @param curPage   should be >0, start=1...
	 * @param rsPerPage
	 * @return
	 * @throws SQLException
	 */
	protected List executeFuncForPaginatedList(String aSqlID, Object aParamObj, int curPage, int rsPerPage)
			throws SQLException {
		int skip = (curPage - 1) * rsPerPage;
		int max = Constants.MAXRECORD;
		return this.executeFuncForList(aSqlID, aParamObj, skip, max);
	}

	/**
	 * 
	 * An alias of queryForObject function
	 *
	 * @param aSqlID    The orgName of the statement to execute
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @return Object The single result object populated with the result set data,
	 *         or null if no result was found
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected void executeFuncForUpdate(String aSqlID, Object aParamObj) throws SQLException {
		queryForObject(aSqlID, aParamObj);

	}

	/**
	 * 
	 * Make an Insert call to DB Method Name: insert
	 *
	 * @param aSqlID an Insert call orgID
	 * @param aObj   parameter class
	 * @return Object primaryKey object generated by RDBMS
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected Object insert(String aSqlID, Object aObj) throws SQLException {
		SqlSession session = sqlMap.openSession(true);
		// Primary key return by Insert command
		Object retPrk = null;
		try {
			// Ask SqlMap to do insert task
			retPrk = session.insert(aSqlID, aObj);
		} catch (Exception ex) {
			dbLog.error(ex);
			throw ex;
		} finally {
			session.close();
		}

		// Return the primary key
		return retPrk;
	}

	/**
	 * 
	 * Make an query to DB for a list of objects
	 *
	 * @param aSqlID    a mapped query orgID
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @return List A List of result objects
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected List queryForList(String aSqlID, Object aParamObj) throws SQLException {

		// A List of result objects
		List lsRet = null;
		SqlSession session = sqlMap.openSession(true);
		try {
			// Ask SqlMap to do queryForList task
			lsRet = session.selectList(aSqlID, aParamObj);
//            lsRet = session.queryForList(aSqlID, aParamObj);
		} catch (Exception ex) {
			dbLog.error(ex);
			throw ex;
		} finally {
			session.close();
		}

		// return a list of result objects
		return lsRet;
	}

	/**
	 * 
	 * Executes a mapped SQL SELECT statement that returns data to populate a number
	 * of result objects within a certain range.
	 *
	 * @param aSqlID    a mapped query orgID
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @param skip      The number of results to ignore.
	 * @param max       The maximum number of results to return.
	 * @return List A List of result objects
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected List queryForList(String aSqlID, Object aParamObj, int skip, int max) throws SQLException {

		// A List of result objects
		List lsRet = null;
		SqlSession session = sqlMap.openSession(true);
		try {
			// Ask SqlMap to do queryForList task
			RowBounds bound = new RowBounds(skip, max);
			lsRet = session.selectList(aSqlID, aParamObj, bound);
		} catch (Exception ex) {
			dbLog.error(ex);
			throw ex;
		} finally {
			session.close();
		}
		// return a list of result objects
		return lsRet;
	}

	/**
	 * 
	 * Executes a mapped SQL SELECT statement that returns data to populate a number
	 * of result objects that will be keyed into a Map.
	 *
	 * @param aSqlID    a mapped query orgID
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @param aKeyProp  The property to be used as the key in the Map.
	 * @return Map A Map keyed by aKeyProp with values being the result object
	 *         instance
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected Map queryForMap(String aSqlID, Object aParamObj, String aKeyProp) throws SQLException {

		// A Map keyed by aKeyProp
		Map mapRet = null;
		SqlSession session = sqlMap.openSession(true);
		try {
			// Ask SqlMap to do queryForMap task
			mapRet = session.selectMap(aSqlID, aParamObj, aKeyProp);
//            mapRet = session.queryForMap(aSqlID, aParamObj, aKeyProp);
		} catch (Exception ex) {
			dbLog.error(ex);
			throw ex;
		} finally {
			session.close();
		}
		// return a map keyed by aKeyProp
		return mapRet;
	}

	/**
	 * 
	 * Executes a mapped SQL SELECT statement that returns data to populate a number
	 * of result objects from which one property will be keyed into a Map.
	 *
	 * @param aSqlID     a mapped query orgID
	 * @param aParamObj  The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @param aKeyProp   The property to be used as the key in the Map.
	 * @param aValueProp The property to be used as the value in the Map.
	 * @return Map A Map keyed by aKeyProp with values being the result object
	 *         instance
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected Map queryForMap(String aSqlID, Object aParamObj, String aKeyProp, String aValueProp) throws SQLException {

		// A Map keyed by aKeyProp
		Map mapRet;
		// Ask SqlMap to do queryForMap task
		SqlSession session = sqlMap.openSession(true);
		try {
//        	RowBounds bound = new RowBounds(skip, max);
			mapRet = session.selectMap(aSqlID, aKeyProp, aValueProp);
//        	mapRet = session.selectList(aSqlID, aParamObj, aKeyProp,
//                    aValueProp);
//            mapRet = sqlMap.queryForMap(aSqlID, aParamObj, aKeyProp,
//                                             aValueProp);
		} catch (Exception e) {
			dbLog.error(e);
			throw e;
		} finally {
			session.close();
		}
		// return a map keyed by aKeyProp
		return mapRet;
	}

	/**
	 * 
	 * Make an query to DB for a single object instance populated with the result
	 * set data.
	 *
	 * @param aSqlID    The orgName of the statement to execute
	 * @param aParamObj The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @return Object The single result object populated with the result set data,
	 *         or null if no result was found
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected Object queryForObject(String aSqlID, Object aParamObj) throws SQLException {

		// The result object instance
		Object objRet = null;
		SqlSession session = sqlMap.openSession(true);
		try {
			// Ask SqlMap to do queryForObject task
			objRet = session.selectOne(aSqlID, aParamObj);
		} catch (Exception ex) {
			dbLog.error(ex);
			throw ex;
		} finally {
			session.close();
		}
		// return the result object instance
		return objRet;
	}

	/**
	 * 
	 * Executes a mapped SQL SELECT statement that returns data to populate the
	 * supplied result object.
	 *
	 * @param aSqlID        The orgName of the statement to execute
	 * @param aParamObj     The parameter object (e.g. JavaBean, Map, XML etc.)
	 * @param aResultObject The result object instance that should be populated with
	 *                      result data.
	 * @return Object The single result object populated with the result set data,
	 *         or null if no result was found
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected Object queryForObject(String aSqlID, Object aParamObj, Object aResultObject) throws SQLException {

		// The result object instance
		Object objRet = null;
		SqlSession session = sqlMap.openSession(true);
		try {
			// Ask SqlMap to do queryForObject task
			objRet = session.selectOne(aSqlID, aParamObj);
//            objRet = sqlMap.queryForObject(aSqlID, aParamObj,
//                    aResultObject);
		} catch (Exception e) {
			dbLog.error(e);
			throw e;
		} finally {
			session.close();
		}
		// return the result object instance
		return objRet;
	}

	/**
	 * 
	 * Make an Update call to DB Method Name: update
	 *
	 * @param aSqlID    a mapped Update SQL call orgID
	 * @param aParamObj parameter class
	 * @return int number of rows effected
	 * @throws java.sql.SQLException - If an error occurs
	 * 
	 */
	protected int update(String aSqlID, Object aParamObj) {
		// Ask SqlMap to do update task
		SqlSession session = sqlMap.openSession();
		// Number of rows effected
		int iRet = 0;
		try {
			iRet = session.update(aSqlID, aParamObj);
			session.commit();
		} catch (Exception e) {
			dbLog.error(e);
			throw e;
		} finally {
			session.close();
		}
		// return number of effected rows
		return iRet;
	}

	/**
	 * 
	 * Return true if transaction mode is enabled
	 *
	 * @return boolean Is using transaction flag
	 * 
	 */
	protected boolean isTransaction() {

		// Return transaction flag value
		return false;// this.pIsTransaction;
	}
}
