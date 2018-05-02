package com.xmu.aiad.util;

import com.xmu.aiad.query.PageSelect;
import com.xmu.aiad.query.QueryResult;
import com.xmu.aiad.query.RecordCountSelect;
import com.xmu.aiad.query.Select;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseDao {

    private JdbcTemplate hikariJdbcTemplate;


    /**
     * queryForBeanList
     * @param sql
     * @param params
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public List queryForBeanList(String sql, Object[] params, Class beanClass){
        List list = hikariJdbcTemplate.query(sql, params, new BeanPropertyRowMapper(beanClass));
        if(list!=null && list.size()>0){
            return list;
        }
        return null;
    }

    /**
     * queryForMap
     * @param sql
     * @param params
     * @return
     */
    public Map queryForMap(String sql, Object[] params){
        try {
            return hikariJdbcTemplate.queryForMap(sql, params);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    /**
     * queryForMapList
     * @param sql
     * @param params
     * @return
     */
    public List queryForMapList(String sql, Object[] params){ return hikariJdbcTemplate.queryForList(sql,params);}

    /**
     * queryForBeanList
     * @param sql
     * @param beanClass
     * @return
     */
    public List queryForBeanList(String sql, Class beanClass){
        return this.queryForBeanList(sql, null, beanClass);
    }

    /**
     * queryForBean
     * @param sql
     * @param params
     * @param beanClass
     * @return
     */
    public Object queryForBean(String sql, Object[] params, Class beanClass){
        List list = this.queryForBeanList(sql, params, beanClass);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * queryForBean
     * @param sql
     * @param beanClass
     * @return
     */
    public Object queryForBean(String sql, Class beanClass){
        return this.queryForBean(sql, null, beanClass);
    }

    /**
     * insert update delete
     * @param sql
     * @param params
     * @return
     */
    public int executeUpdate(String sql, Object[] params){
        return hikariJdbcTemplate.update(sql,params);
    }

    /**
     * batch insert update delete
     * @param sql
     * @param batchArgs
     * @return
     */
    public int[] executeBatchUpdate(String sql, List<Object[]> batchArgs){
        return hikariJdbcTemplate.batchUpdate(sql,batchArgs);
    }

    /**
     * executePaginate
     * @param select
     * @param pojoClass
     * @param <T>
     * @return
     */
    public <T> QueryResult<T> executePaginate(Select select, Class<T> pojoClass){

        List<T> list = new ArrayList<T>();

        if(select!=null && !"".equals(StringClass.getString(select.getSql()))){

            list = this.queryForBeanList(select.getSql(),select.getParameters(),pojoClass);
            if(select instanceof PageSelect){
                int total = getRecordCount(((PageSelect) select).getWrappedSelect());
                return new QueryResult<T>(list, total);
            }
        }

        return new QueryResult<T>(list, list.size());
    }

    private int getRecordCount(Select select){

        RecordCountSelect recordCountSelect = new RecordCountSelect(select);

        Map map = this.queryForMap(recordCountSelect.getSql(),recordCountSelect.getParameters());

        return Integer.parseInt(StringClass.getString(map.get(RecordCountSelect.RECORD_COUNT)));
    }

    public String getSysdate(){
        Map map = this.queryForMap("select date_format(now(),'%Y-%m-%d %H:%i:%s') sysdate from dual",null);
        if(map != null){
            return StringClass.getString(map.get("sysdate"));
        }
        return "";
    }
}
