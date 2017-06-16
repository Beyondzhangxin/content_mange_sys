package cn.com.aiidc.cms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.ChannelApprDao;
import cn.com.aiidc.cms.entity.TChannel;

@Service
public class ChannelApprService {
	
	@Resource
	private ChannelApprDao channelApprDao;

	public ChannelApprDao getChannelApprDao() {
		return channelApprDao;
	}

	public void setChannelApprDao(ChannelApprDao channelApprDao) {
		this.channelApprDao = channelApprDao;
	}
	
	/**
	 * 获取需要审核的频道数据
	 * @param map
	 * @return
	 */
	public List<TChannel> getDataForChannelAppr(Map<String, Object> map){
		return channelApprDao.getdataForChannelAppr(map);
	}
	
	/**
	 * 通过id查找对应的bean
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T findById(Serializable id,Class<T> clazz){
		return channelApprDao.findById(id, clazz);
	}
	
	/**
	 * 获取满足添加的记录数
	 * @param map
	 * @return
	 */
	public Integer getTotalForAppr(Map<String, Object> map){
		return channelApprDao.getTotalForChannelAppr(map);
	}
	
	/**
	 * 更新频道信息
	 * @param channel
	 */
	public void updateChannel(TChannel channel){
		channelApprDao.saveOrUpdate(channel);
	}
}
