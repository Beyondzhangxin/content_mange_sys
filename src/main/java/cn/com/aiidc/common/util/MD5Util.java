package cn.com.aiidc.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * md5生存工具
 * @author chenll
 *
 */
public class MD5Util {
	
	
	/**
	 * 得到md5加密字符串
	 * @param source
	 * @return
	 */
	public static String getMD5(String source){
		String result="";
		try {
			//获取MD5摘要算法的 MessageDigest 对象
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			
			// 使用指定byte[]更新摘要
			messageDigest.update(source.getBytes("UTF8"));//转化成按utf-8编码的byte数组
			// 完成计算，返回结果数组
			result = toHexString(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将产生的消息字节组数转成16进制字符串
	 * @param byteArray
	 * @return
	 */
	public static String toHexString(byte[] byteArray){
		StringBuffer md5StrBuff = new StringBuffer();  
		  
        for (int i = 0; i < byteArray.length; i++) {  
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  //每个byte位与之后用2位16进制表示
                md5StrBuff.append("0").append(  
                        Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return md5StrBuff.toString();  
	}
	
	/**
	 * 
	 * <p>Description: md5加密，false 表示：生成32位的Hex版,  
	 *    默认配置 true  表示：生成24位的Base64版</p>
	 * @author joyu
	 */
	 public static String md5(String password,boolean base64) {       
	        Md5PasswordEncoder md5 = new Md5PasswordEncoder();       
	        md5.setEncodeHashAsBase64(base64);       
	        String pwd = md5.encodePassword(password, null);      
	        return pwd;
   
	    }   
	 	/**
	 	 * 
	 	 * <p>Description: 用哈希算法 256进行秘密加密,此加密算法加密结果同MD5</p>
	 	 * @author joyu
	 	 * @throws NoSuchAlgorithmException
	 	 */
	    public static String sha_256(String password,boolean base64) throws NoSuchAlgorithmException {         
	        ShaPasswordEncoder sha = new ShaPasswordEncoder(256);       
	        sha.setEncodeHashAsBase64(base64);       
	        String pwd = sha.encodePassword(password, null);       
	        return pwd;
	    }       
	           
	    /**
	     *       
	     * <p>Description: sha算法加密</p>
	     * @author joyu
	     */
	    public static String sha(String password,boolean base64) {       
	        ShaPasswordEncoder sha = new ShaPasswordEncoder();       
	        sha.setEncodeHashAsBase64(base64);       
	        String pwd = sha.encodePassword(password, null);    
	        return pwd;
  
	    }       
	           
	    /**
	     *       
	     * <p>Description:md5加盐加密</p>
	     * @author joyu
	     */
	    public static String  md5_SystemWideSaltSource (String salt,String password) {       
	        Md5PasswordEncoder md5 = new Md5PasswordEncoder();       
	        md5.setEncodeHashAsBase64(false);       
	               
	        String pwd = md5.encodePassword(password, salt);    
	        return pwd;
	    }       
}
