package com.xh.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.lob.LobHandler;


@SuppressWarnings({"unchecked"})
public abstract class BaseJdbcDAO extends JdbcDaoSupport {

	protected static final Log logger = LogFactory.getLog(BaseJdbcDAO.class);
	
	/**
	 * LOB处理器
	 */
	private LobHandler lobHandler ;
	
	/**
	 * 注入dataSource
	 * @param dataSource
	 */
	@Resource(name="dataSource")
	public void setSuperDataSource(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	/**
	 * 普通DML操作,如insert,update,delete
	 * @param sql
	 * @return
	 */
	public int update(String sql) {
		return this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 普通DML操作,如insert,update,delete
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql, Object[] params) {
		return this.getJdbcTemplate().update(sql, params);
	}
	
	/**
	 * 批量普通DML操作,如insert,update,delete
	 * 由于需要预编译多条sql语句,效率低于batchUpdate(final String sql, final List<List<Object>> paramsList)、batchUpdate(String sql, BatchPreparedStatementSetter pss)
	 * @param sql
	 * @return
	 */
	public int[] batchUpdate(String[] sql) {
		return this.getJdbcTemplate().batchUpdate(sql);
	}
	
	/**
	 * 批量普通DML操作,如insert,update,delete
	 * 由于仅预编译1条sql语句执行效率较高
	 * @param sql
	 * @param paramsList
	 * @return
	 */
	public int[] batchUpdate(final String sql, final List<Object[]> batchArgs) {
		Assert.notEmpty(batchArgs, "can not do batchUpdate operation, batchArgs is empty!");
		return this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter(){
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				Object[] params = batchArgs.get(i);
				if(params != null && params.length > 0){
					for(int k = 0; k < params.length; k++){
						Object arg = params[k];
						if (arg instanceof SqlParameterValue) {
							SqlParameterValue paramValue = (SqlParameterValue) arg;
							StatementCreatorUtils.setParameterValue(ps, k + 1, paramValue, paramValue.getValue());
						} else {
							StatementCreatorUtils.setParameterValue(ps, k + 1, SqlTypeValue.TYPE_UNKNOWN, arg);
						}
					}
				}
			}
			public int getBatchSize() {
				return batchArgs.size();
			}
		});
	}
	
	/**
	 * 批量普通DML操作,如insert,update,delete
	 * 由于仅预编译1条sql语句执行效率较高
	 * @param sql	- 
	 * @param pss
	 * @return
	 */
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) {
		return this.getJdbcTemplate().batchUpdate(sql, pss);
	}
	
	/**
	 * 单行结果查询
	 * (注：返回的结果行数大于1时将会抛出异常)
	 * @param sql
	 * @return
	 */
	public Map<String,Object> queryForMap(String sql) {
		return this.getJdbcTemplate().queryForMap(sql);
	}
	
	/**
	 * 单行结果查询
	 * (注：返回的结果行数大于1时将会抛出异常)
	 * @param sql
	 * @param params
	 * @return
	 */
	public Map<String,Object> queryForMap(String sql, Object[] params) {
		return this.getJdbcTemplate().queryForMap(sql, params);
	}
	
	/**
	 * 针对单行中某个列的值的查询
	 * (注：返回的结果行数大于1时将会抛出异常)
	 * @param <T>
	 * @param sql
	 * @param requiredType
	 * @return
	 */
	public <T> T queryForObject(String sql, Class<T> requiredType) {
		return (T) this.getJdbcTemplate().queryForObject(sql, requiredType);
	}
	
	/**
	 * 针对单行中某个列的值的查询
	 * (注：返回的结果行数大于1时将会抛出异常)
	 * @param <T>
	 * @param sql
	 * @param params
	 * @param requiredType
	 * @return
	 */
	public <T> T queryForObject(String sql, Object[] params, Class<T> requiredType) {
		return (T) this.getJdbcTemplate().queryForObject(sql, params, requiredType);
	}
	
	/**
	 * 单行结果查询
	 * 返回的javabean类型由rowMapper决定
	 * (注：返回的结果行数大于1时将会抛出异常)
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return
	 */
	public <T> T queryForObject(String sql, RowMapper rowMapper) {
		return (T) this.getJdbcTemplate().queryForObject(sql, rowMapper);
	}
	
	/**
	 * 单行结果查询
	 * 返回的javabean类型由rowMapper决定
	 * (注：返回的结果行数大于1时将会抛出异常)
	 * @see e.g. <code>AutoMatchingRowMapper</code>
	 * @param <T>
	 * @param sql
	 * @param params
	 * @param rowMapper
	 * @return
	 */
	public <T> T queryForObject(String sql, Object[] params, RowMapper rowMapper) {
		return (T) this.getJdbcTemplate().queryForObject(sql, params, rowMapper);
	}
	/**
	 * count,sum,max等查询
	 * @param sql
	 * @return
	 */
	public int queryForInt(String sql){
		return this.getJdbcTemplate().queryForInt(sql);
	}
	
	/**
	 * count,sum,max等查询
	 * @param sql
	 * @param params
	 * @return
	 */
	public int queryForInt(String sql, Object[] params){
		return this.getJdbcTemplate().queryForInt(sql, params);
	}
	
	/**
	 * count,sum,max等查询
	 * @param sql
	 * @param params
	 * @return
	 */
	public long queryForLong(String sql, Object[] params){
		return this.getJdbcTemplate().queryForLong(sql, params);
	}
	
	/**
	 * count,sum,max等查询
	 * @param sql
	 * @return
	 */
	public long queryForLong(String sql){
		return this.getJdbcTemplate().queryForLong(sql);
	}
	
	/**
	 * 查询下个sequence值
	 * @param sequenceName	- sequence名称
	 * @return
	 */
	public long querySequenceNextValue(String sequenceName){
		return this.getJdbcTemplate().queryForLong(String.format("select %s.nextval from dual", sequenceName));
	}
	
	/**
	 * 批量获取sequence值
	 * @param sequenceName	- sequence名称
	 * @param size			- 一次获取多少个?
	 * @return
	 */
	public List<Long> querySequenceNextValues(String sequenceName, final int size){
		return this.getJdbcTemplate().query(String.format("select %s.nextval from dual connect by rownum <= ?", sequenceName), new Object[]{size}, new RowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong(1);
			}
		});
	} 
	
	/**
	 * Map类型列表查询
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> queryForList(String sql) {
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * Map类型列表查询
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryForList(String sql, Object[] params) {
		return this.getJdbcTemplate().queryForList(sql, params);
	}
	
	/**
	 * Map类型列表查询(带分页)
	 * @param sql
	 * @param params
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> queryForList(String sql, Object[] params, Page page) {
		if(page != null){
			int count = this.queryForInt(getCountSql(sql), params);
			page.setTotalRow(count);
			return this.queryForList(getLimitSql(sql, page.getCurrentPage(), page.getRowsPerPage()), params);
		}
		return this.queryForList(sql, params);
	}
	
	/**
	 * Map类型列表查询(带分页)
	 * @param selectSql		- 结果查询语句
	 * @param countSql		- 总记录数查询语句(countSql为空则通过selectSql获取countSql)
	 * @param params
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> queryForList(String selectSql, String countSql, Object[] params, Page page) {
		if(page != null){
			int count = this.queryForInt((countSql == null || "".equals(countSql.trim()) ? getCountSql(selectSql) : countSql), params);
			page.setTotalRow(count);
			return this.queryForList(getLimitSql(selectSql, page.getCurrentPage(), page.getRowsPerPage()), params);
		}
		return this.queryForList(selectSql, params);
	}
	
	/**
	 * javabean列表查询
	 * javabean的类型由rowMapper决定
	 * @param sql
	 * @param params
	 * @param rowMapper
	 * @return
	 */
	public <T> List<T> queryForList(String sql, Object[] params, RowMapper rowMapper) {
		return this.getJdbcTemplate().query(sql, params, rowMapper);
	}
	
	/**
	 * javabean列表查询(带分页)
	 * javabean的类型由rowMapper决定
	 * @see e.g. <code>AutoMatchingRowMapper</code>
	 * @param <T>
	 * @param sql			- 结果查询语句
	 * @param params
	 * @param rowMapper
	 * @param page
	 * @return
	 */
	public <T> List<T> queryForList(String sql, Object[] params, RowMapper rowMapper, Page page) {
		if(page != null){
			int count = this.queryForInt(getCountSql(sql), params);
			page.setTotalRow(count);
			return this.queryForList(getLimitSql(sql, page.getCurrentPage(), page.getRowsPerPage()), params, rowMapper);
		}
		return this.queryForList(sql, params, rowMapper);
	}
	
	/**
	 * javabean列表查询(带分页)
	 * javabean的类型由rowMapper决定
	 * @see e.g. <code>AutoMatchingRowMapper</code>
	 * @param <T>
	 * @param selectSql		- 结果查询语句
	 * @param countSql		- 总记录数查询语句(countSql为空则通过selectSql获取countSql)
	 * @param params
	 * @param rowMapper
	 * @param page
	 * @return
	 */
	public <T> List<T> queryForList(String selectSql, String countSql, Object[] params, RowMapper rowMapper, Page page) {
		if(page != null){
			int count = this.queryForInt((countSql == null || "".equals(countSql.trim()) ? getCountSql(selectSql) : countSql), params);
			page.setTotalRow(count);
			return this.queryForList(getLimitSql(selectSql, page.getCurrentPage(), page.getRowsPerPage()), params, rowMapper);
		}
		return this.queryForList(selectSql, params, rowMapper);
	}
	
	/**
	 * 执行DDL语句(alter,create,drop ...)
	 * @param sql
	 */
	public void execute(String sql){
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * @param sql
	 * @param action
	 */
	public Object execute(String sql, PreparedStatementCallback action) {
		return this.getJdbcTemplate().execute(sql, action);
	}
	
	
	/**
	 * 获取分页sql语句
	 * @param sql
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public String getLimitSql(String sql, int currentPage, int pageSize) {
		Assert.isTrue(currentPage > 0, "'currentPage' 不能为负数!");
		Assert.isTrue(pageSize > 0, "'pageSize' 不能为负数!");
		String s = "select *"
				  + " from (select rownum rn_, inner_tab.*"
				  		  + " from (" + sql + ") inner_tab"
				  		 + " where rownum <= " + (currentPage * pageSize) + ") outer_tab"
			      + " where outer_tab.rn_ > " + ((currentPage - 1) * pageSize);
		return s;
	}
	
	/**
	 * 获取总记录数sql语句
	 * @param sql
	 * @return
	 */
	public String getCountSql(String sql) {
		return String.format("select count(1) from (%s)", sql);
	}
	
	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}
	
}
