package com.psbk.ws.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @created by : Endang Hidayat
 * @supported by : Spring JdbcTemplate
 */
public class JdbcTemplate extends org.springframework.jdbc.core.JdbcTemplate {
	protected Logger log = Logger.getLogger(this.getClass());
	private List<MyMap> listSql = new ArrayList<MyMap>();
	private Connection conn;

	public JdbcTemplate(Connection conn, DataSource ds) {
		super(new SingleConnectionDataSource(conn, false));
		this.conn = conn;
	}

	@Override
	public Object query(String sql, Object[] args, int[] argTypes, ResultSetExtractor rse) throws DataAccessException {
		log.info(sql);
		return super.query(sql, args, argTypes, rse);
	}

	@Override
	public void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch) throws DataAccessException {
		log.info(sql);
		super.query(sql, args, argTypes, rch);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List query(String sql, Object[] args, int[] argTypes, RowMapper rowMapper) throws DataAccessException {
		log.info(sql);
		return super.query(sql, args, argTypes, rowMapper);
	}

	@SuppressWarnings("rawtypes")
	public List queryList(String sql, Object[] args, int[] argTypes, RowMapper rowMapper) throws DataAccessException {
		try {
			log.info(sql);
			return super.query(sql, args, argTypes, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Object query(String sql, PreparedStatementSetter pss, ResultSetExtractor rse) throws DataAccessException {
		log.info(sql);
		return super.query(sql, pss, rse);
	}

	@Override
	public void query(String sql, PreparedStatementSetter pss, RowCallbackHandler rch) throws DataAccessException {
		log.info(sql);
		super.query(sql, pss, rch);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List query(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws DataAccessException {
		log.info(sql);
		return super.query(sql, pss, rowMapper);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List query(String sql, RowMapper rowMapper) throws DataAccessException {
		log.info(sql);
		return super.query(sql, rowMapper);
	}

	@Override
	public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		Integer retVal = null;
		try {
			log.info(sql);
			retVal = super.update(sql, args, argTypes);
			commit();
			return retVal;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	@Override
	public int update(String sql, Object[] args) throws DataAccessException {
		Integer retVal = null;
		try {
			log.info(sql);
			retVal = super.update(sql, args);
			commit();
			return retVal;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	@Override
	public int update(String sql) throws DataAccessException {
		Integer retVal = null;
		try {
			log.info(sql);
			retVal = super.update(sql);
			commit();
			return retVal;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	public int update(String sql, Object[] args, int[] argTypes, Boolean isCommit)
			throws DataAccessException, SQLException {
		log.info(sql);
		Integer retVal = super.update(sql, args, argTypes);
		if (isCommit)
			commit();
		return retVal;
	}

	public int update(String sql, Object[] args, Boolean isCommit) throws DataAccessException, SQLException {
		log.info(sql);
		Integer retVal = super.update(sql, args);
		if (isCommit)
			commit();
		return retVal;
	}

	public int update(String sql, Boolean isCommit) throws DataAccessException, SQLException {
		log.info(sql);
		Integer retVal = super.update(sql);
		if (isCommit)
			commit();
		return retVal;
	}

	public int insert(String tableName, MyMap dtoMap) {
		StringBuffer sql = new StringBuffer("INSERT INTO " + tableName + " ( ");
		Set<String> keySet = dtoMap.map.keySet();
		int count = 0;
		for (String string : keySet) {
			System.out.println("string dto, " + string);
			sql.append(string);
			if (count < keySet.size() - 1)
				sql.append(", ");
			count++;
		}
		sql.append(") VALUES ( ");
		Collection<Object> values = dtoMap.map.values();
		count = 0;
		Object[] a = new Object[values.size()];
		for (Object object : values) {
			sql.append("?");
			a[count] = object;
			if (count < keySet.size() - 1)
				sql.append(", ");
			count++;
		}
		sql.append(")");
		
		System.out.println("SQL, " + sql);

		Integer retVal = update(sql.toString(), a);
		try {
			commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return retVal;
	}

	public void insert(String tableName, MyMap dtoMap, Boolean isCommit) {
		StringBuffer sql = new StringBuffer("INSERT INTO " + tableName + " ( ");
		StringBuffer param = new StringBuffer(" ");

		Set<Entry<String, Object>> entrySet = dtoMap.map.entrySet();
		List<Object> l = new ArrayList<Object>();
		int count = 0;
		for (Entry<String, Object> entry : entrySet) {
			sql.append(entry.getKey());
			param.append("?");
			l.add(entry.getValue());
			if (count < entrySet.size() - 1) {
				sql.append(", ");
				param.append(", ");
			}
			count++;
		}
		sql.append(") VALUES ( " + param.toString() + " )");
		try {
			update(sql.toString(), l.toArray(new Object[] {}), isCommit);
		} catch (DataAccessException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(String tableName, MyMap data, MyMap primaryKey, Boolean isCommit) {
		StringBuffer sql = new StringBuffer("UPDATE " + tableName + " SET ");

		Set<Entry<String, Object>> entrySet = data.map.entrySet();
		List<Object> l = new ArrayList<Object>();
		int count = 0;
		for (Entry<String, Object> entry : entrySet) {
			sql.append(entry.getKey() + " = ? ");
			l.add(entry.getValue());
			if (count < entrySet.size() - 1) {
				sql.append(", ");
			}
			count++;
		}

		if (primaryKey != null) {
			entrySet = primaryKey.map.entrySet();
			count = 0;
			sql.append(" WHERE ");
			for (Entry<String, Object> entry : entrySet) {
				sql.append(entry.getKey() + " = ? ");
				l.add(entry.getValue());
				if (count < entrySet.size() - 1) {
					sql.append(" AND ");
				}
				count++;
			}
		}

		try {
			update(sql.toString(), l.toArray(new Object[] {}), isCommit);
		} catch (DataAccessException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int updateData(MyMap dtoMap, String tblName) {
		List<String> whereCol = Arrays.asList(dtoMap.getString("PK").split(","));
		dtoMap.map.remove("PK");
		List<Object> param = new ArrayList<Object>();
		String sql = "Update " + tblName + " Set ";
		for (String key : dtoMap.map.keySet()) {
			if (!whereCol.contains(key)) {
				sql += key + "=?,";
				param.add(dtoMap.get(key));
			}
		}
		sql = sql.substring(0, sql.length() - 1) + " Where ";

		for (String wc : whereCol) {
			sql += wc + "=? and ";
			param.add(dtoMap.get(wc));
		}
		sql = sql.substring(0, sql.length() - 4);
		Object[] paramO = param.toArray(new Object[param.size()]);
		return this.update(sql, paramO);
	}

	public void commit() throws SQLException {
		conn.commit();
	}

	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Object queryObject(String sql, Object[] args, RowMapper rowMapper) throws DataAccessException {
		try {
			log.info(sql);
			return super.queryForObject(sql, args, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Object queryObject(String sql, RowMapper rowMapper) throws DataAccessException {
		try {
			return super.queryForObject(sql, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public Object queryObject(String sql, Object[] args, Class clazz) throws DataAccessException {
		try {
			return super.queryForObject(sql, args, clazz);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public Object queryObject(String sql, Class clazz) throws DataAccessException {
		try {
			return super.queryForObject(sql, clazz);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Integer queryInt(String sql, Object[] args) throws DataAccessException {
		try {
			return super.queryForInt(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List queryList(String sql, Object[] args, RowMapper rowMapper) throws DataAccessException {
		try {
			return super.query(sql, args, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List queryList(String sql, RowMapper rowMapper) throws DataAccessException {
		try {
			return query(sql, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List queryList(String sql, Class clazz) throws DataAccessException {
		try {
			return super.queryForList(sql, clazz);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List queryList(String sql, Object[] args, Class clazz) throws DataAccessException {
		try {
			return super.queryForList(sql, args, clazz);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public MyMap addQuery(String sql, Object[] param) {
		MyMap map = new MyMap();
		map.put("QUERY", sql);
		map.put("PARAM", param);
		return map;
	}

	public void addSql(MyMap query) {
		this.listSql.add(query);
	}

	public void addSql(String sql, Object[] param) {
		MyMap map = new MyMap();
		map.put("QUERY", sql);
		map.put("PARAM", param);
		this.listSql.add(map);
	}

	public void resetListSql() {
		listSql.clear();
	}

	public void executes() {
		executes(listSql);
	}

	public void executes(List<MyMap> listSql) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(this.getDataSource());
		TransactionStatus status = transactionManager.getTransaction(def);

		try {
			for (MyMap sql : listSql) {
				String query = sql.getString("QUERY");
				Object[] param = (Object[]) sql.get("PARAM");
				if (param != null)
					this.update(query, param);
				else
					this.update(query);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		transactionManager.commit(status);
	}
}
