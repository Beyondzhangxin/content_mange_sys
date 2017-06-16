package cn.com.aiidc.cms.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.ColumnApprDao;
import cn.com.aiidc.cms.entity.TColumn;

@Service
public class ColumnApprService {

	@Resource
	private ColumnApprDao columnApprDao;

	public ColumnApprDao getColumnApprDao() {
		return columnApprDao;
	}

	public void setColumnApprDao(ColumnApprDao columnApprDao) {
		this.columnApprDao = columnApprDao;
	}
	
	/**
	 * 获取栏目审核的分页数据
	 * @param map
	 * @return
	 */
	public List<TColumn>  getPageDataForColumnAppr(Map<String, Object> map){
		return columnApprDao.getPageDataForColumnAppr(map);
	}
	
	/**
	 * 获取满足添加的未审核的总记录数
	 * @param map
	 * @return
	 */
	public Integer  getTotalForColumnAppr(Map<String, Object> map){
		return columnApprDao.getTotalForColumnAppr(map);
	}
	
	/**
	 *	根据id查询clazz
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T findById(Integer id, Class<T> clazz){
		  return columnApprDao.findById(id, clazz);
	}
	
	/**
	 * 更新column
	 * @param column
	 */
	public void updateColumn(TColumn column){
		columnApprDao.saveOrUpdate(column);
	}
	
}
