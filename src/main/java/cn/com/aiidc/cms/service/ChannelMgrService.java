package cn.com.aiidc.cms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.ChannelMgrDao;
import cn.com.aiidc.cms.entity.TChannel;


@Service
public class ChannelMgrService {

	@Resource
	private ChannelMgrDao channelMgrDao;

	public ChannelMgrDao getChannelMgrDao() {
		return channelMgrDao;
	}

	public void setChannelMgrDao(ChannelMgrDao channelMgrDao) {
		this.channelMgrDao = channelMgrDao;
	}
	
	/**
	 * 校验频道是否存在
	 * @param siteId
	 * @param channelName
	 * @param channelId
	 * @return
	 */
	public List<TChannel> checkChannelByChanelName(Integer siteId,String channelName,Integer channelId){
		return channelMgrDao.findBySiteIdAndChannelName(siteId, channelName, channelId);
	}
	
	/**
	 * 添加频道
	 * @param channel
	 * @return
	 */
	public Integer addChannel(TChannel channel){
		return  (Integer) channelMgrDao.save(channel);
	}
	
	/**
	 * 更新频道
	 * @param channel
	 */
	public void UpdateChannel(TChannel channel){
		channelMgrDao.saveOrUpdate(channel);;
	}
	
	/**
	 * 根据id查询频道的数据
	 * @param channelId
	 * @return
	 */
	public TChannel findByChannelId(Integer channelId){
		return channelMgrDao.findById(channelId, TChannel.class);
		 
	}
	

	/**
	 * 伪删除：审核通过后的频道，删除时可以暂时保留一段时间
	 * @param channel
	 */
	public void updateIsDisplayForChannel(TChannel channel){
		channelMgrDao.saveOrUpdate(channel);
	}
	
	/**
	 * 删除频道
	 * @param channel
	 */
	public void deleteChannel(TChannel channel){
		channelMgrDao.delete(channel);
	}
	
	/**
	 * 分页查询：满足条件
	 * @param map
	 * @return
	 */
	public List<TChannel> findAllChannelBypage(Map<String,Object> map)
	{	
		return channelMgrDao.findAllChannelBypage(map);
	}
	
	/**
	 * 获取满足条件的记录总数
	 * @param map
	 * @return
	 */
	public Integer gettotalForPage(Map<String,Object> map){
		return channelMgrDao.gettotalForPage(map);
	}
	
	/**
	 * 根据id查询class
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T findClassById(Serializable id,Class<T> clazz){
		return channelMgrDao.findById(id, clazz);
	}
	
	/**
	 * 获取通过某站点下，通过审核的频道
	 * 注：当频道id为null时，返回所有通过审核的频道
	 * @param siteId
	 * @return
	 */
	public List<TChannel> getChannelByApprPass(Integer siteId){
		return channelMgrDao.getChannelByApprPass(siteId);
	}
}
