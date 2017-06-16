package cn.com.aiidc.common.util;

/**
 * 加载配置表工具类
 * @author chenll
 *
 */
public class ConfigUtil {
	/**
	 * 文件下载服务器IP code
	 */
	public static final String HOST_IP_CODE = "com.midou.file.ip";
	/**
	 * 文件下载服务器IP key
	 */
	public static final String HOST_IP_KEY  = "fileIp";
	
	/**
	 * 默认头像code
	 */
	public static final String DEFAULT_HEAD_CODE = "com.midou.default.head.code";
	/**
	 * 默认头像key
	 */
	public static final String DEFAULT_HEA_KEY = "defaultHead";

	/**
	 * 文件保存路径code
	 */
	public static final String PATHUPLOAD_CODE = "com.midou.path.upload";
	
	/**
	 * 文件保存路径 key D:/XXX/XXX
	 */
	public static final String PATHUPLOAD_BASEPATH_KEY = "basePath";	
	/**
	 * 图片保存路径 key
	 */
	public static final String PATHUPLOAD_IMAGE_KEY = "pathImage";	
	/**
	 * 资讯文件保存路径 key
	 */
	public static final String PATHUPLOAD_INFO_KEY = "pathInfo";	
	/**
	 * 声音保存路径 key
	 */
	public static final String PATHUPLOAD_SOUND_KEY = "pathVoice";
	/**
	 * apk路径 key
	 */
	public static final String PATHUPLOAD_APK_KEY = "apkPath";
	/**
	 * 获得积分规则 code
	 */
	public static final String GETSCORE_RULE_CODE = "getScoreRule";
	/**
	 * 上传获得积分 key
	 */
	public static final String GETSCORE_BYUPLOAD_KEY = "upload";
	/**
	 * 得到暂获得积分 key
	 */
	public static final String GETSCORE_BYGETOK_KEY = "getOk";
	/**
	 * 点暂获得积分 key
	 */
	public static final String GETSCORE_BYGIVEOK_KEY = "giveOk";
	/**
	 * 奖励积分 code 
	 */
	public static final String AWARD_SCORE_CODE = "awardScore";
	/**
	 * 得到100个赞奖励积分 key 
	 */
	public static final String AWARD_100_KEY = "100";
	/**
	 * 得到1000个赞奖励积分 key 
	 */
	public static final String AWARD_1000_KEY = "1000";
	/**
	 * 得到10000个赞奖励积分 key 
	 */
	public static final String AWARD_10000_KEY = "10000";
	/**
	 * 每天的点赞次数限制 code 
	 */
	public static final String DAY_PRAISE_TIME_CODE = "dayPraiseTime";
	/**
	 * 一般用户每天的点赞次数限制 key 
	 */
	public static final String USER_PRAISE_TIME_KEY = "userPraiseTime";
	/**
	 * vip每天的点赞次数限制 key 
	 */
	public static final String VIP_PRAISE_TIME_KEY = "vipPraiseTime";
	/**
	 * 得到配置表中的模块对应的key的value值
	 * @param modelCode (模块编码)
	 * @param key (键值对的key)
	 * @return
	 */
	public static String getModelValue(String modelCode,String key){
		String result="";
/*		TConfigService tConfigService = (TConfigService) BeanHolder.getBean("tConfigService");
		List<TConfig> list = tConfigService.getConfigByCodeKey(modelCode, key);
		if (list!=null&&!list.isEmpty()){//list中只有一个唯一值
			result = list.get(0).getValue();
		}*/
		return result;
	}
	
	/**
	 * 得到配置表中的模块对应的key的value值
	 * 使用hibernate的二级缓存
	 * @param modelCode (模块编码)
	 * @param key (键值对的key)
	 * @return
	 */
	public static String getModelValueUseCache(String modelCode,String key){
		String result="";
		
/*		TConfigService tConfigService = (TConfigService) BeanHolder.getBean("tConfigService");
		List<TConfig> list = tConfigService.getAllConfig();
		
		if (list!=null&&!list.isEmpty()){
			for (TConfig tConfig : list) {
				if (tConfig.getModelCode().equals(modelCode)&&tConfig.getKey().equals(key)){
					result =  tConfig.getValue();
				}
			}
		}*/
		return result;
	}
}

