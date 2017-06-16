package cn.com.aiidc.cms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.ColumnMgrDao;
import cn.com.aiidc.cms.entity.TColumn;

@Service
public class ColumnMgrService {
	@Resource
	private ColumnMgrDao columnMgrDao;

	public ColumnMgrDao getColumnMgrDao() 
	{
		return columnMgrDao;
	}

	public void setColumnMgrDao(ColumnMgrDao columnMgrDao) 
	{
		this.columnMgrDao = columnMgrDao;
	}
	
	/**
	 * 获取频道下，审核通过的栏目
	 * 注意：如果频道id为null,则获取全部通过审核的栏目数据
	 * @param channelId
	 * @return
	 */
	public List<TColumn> getColumnByApprPass(Integer channelId){
		return columnMgrDao.getColumnByApprPass(channelId);
	}
	
	/**
	 * 校验栏目名称是否存在
	 * @param columnId
	 * @param channelId
	 * @param columnName
	 * @return
	 */
	public List<TColumn> checkColumnByColumnName(Integer columnId,Integer channelId,String columnName){
		return columnMgrDao.findByColumnIdChannelIdColumnName(columnId, channelId, columnName);
	}

	/**
	 * 保存栏目
	 * @param column
	 * @return
	 */
	public Integer save(TColumn column){
		Integer columnId =(Integer) columnMgrDao.save(column);
		return columnId;
	}
	
	/**
	 * 编辑更新栏目
	 * @param column
	 */
	public void update(TColumn column){
		 columnMgrDao.saveOrUpdate(column);
	}
	
	
	/**
	 * 获取子栏目的集合
	 * @param pid
	 * @return
	 */
	public List<TColumn> findChildColumnByPid(Serializable pid){
		return columnMgrDao.findChildColumnByPid(pid);
	}
	
	/**
	 * 根据栏目id查找栏目信息
	 * @param columId
	 * @return
	 */
	public TColumn findByColumnId(Integer columnId){
		return columnMgrDao.findById(columnId,TColumn.class);
	}
	
	/**
	 * 删除栏目
	 * @param column
	 */
	public void deleteColumn(TColumn column){
		columnMgrDao.delete(column);
	}
	
	/**
	 * 创建栏目管理的栏目树
	 * @return
	 */
	public List<TColumn> buildColumnTree(Map<String, Object> map){
		return columnMgrDao.getColumnTree(map);
	}
	
	/**
	 * 获取分页管理数据
	 * @param map
	 * @return
	 */
	public List<TColumn>  getPageDataForColumnMgr(Map<String, Object> map){
		return columnMgrDao.getPageDataForColumnMgr(map);
	}
	
	/**
	 * 获取满足条件的总记录数
	 * @param map
	 * @return
	 */
	public Integer  getTotalForColumnMgr(Map<String, Object> map){
		return columnMgrDao.getTotalForColumnMgr(map);
	}
	
	/**
	 * 根据id查询clazz
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T findById(Integer id, Class<T> clazz){
		return columnMgrDao.findById(id, clazz);
	}
	
}

