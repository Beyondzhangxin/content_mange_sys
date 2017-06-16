package cn.com.aiidc.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉子转换成拼音
 * 需要jar包：pinyin4j.jar
 * @author gkr
 *
 */
public class Chinese2PinYin {
	
	public static void main(String[] args) {
		String cnStr = "中华人民共和国";
		getPinYin(cnStr);
	}
	
	/**
	 * 获取汉子字符串的全拼
	 * @param src
	 * @return
	 */
	public static String getPinYin(String src){
		char[] str1 = src.toCharArray();//将字符串转换成字符数组
		String[] str2 = new String[str1.length];
		//创建格式化对象：HanyuPinyinOutputFormat
		HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
		//设置拼音大小写格式：
		//		 HanyuPinyinCaseType.LOWERCASE 转换后以全小写方式输出
		//		 HanyuPinyinCaseType.UPPERCASE 转换后以全大写方式输出
		hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		//设置声调格式：
		//		 HanyuPinyinToneType.WITH_TONE_NUMBER 用数字表示声调，例如：zhao4
		//		 HanyuPinyinToneType.WITHOUT_TONE 无声调表示，例如：zhao
		//		 HanyuPinyinToneType.WITH_TONE_MARK 用声调符号表示，例如：zhao
		hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
		// 设置特殊拼音ü的显示格式：
		//		 HanyuPinyinVCharType.WITH_U_AND_COLON 	以U和一个冒号表示该拼音， 
		//		 HanyuPinyinVCharType.WITH_V 							以V表示该字符， 
		//		 HanyuPinyinVCharType.WITH_U_UNICODE  
		hanyuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		String str3 = "";
		try {
			//判断是否为汉子字符
			for(int i=0;i<str1.length;i++){
				if(java.lang.Character.toString(str1[i]).matches("[\\u4E00-\\u9FA5]+")){
					str2 = PinyinHelper.toHanyuPinyinStringArray(str1[i], hanyuPinyinOutputFormat);
					str3+=str2[0];
				}else{
					str3+=java.lang.Character.toString(str1[i]);
				}	
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return str3;
	}
	

	/**
	 * 获取汉字的首字母
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str){
		String convert="";
		for(int j = 0 ; j<str.length();j++){
			char word = str.charAt(j);
			String[]  pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if(pinyinArray != null){
				convert += pinyinArray[0].charAt(0);
			}else{
				convert +=word;
			}
		}
		return convert;
	}
	
	/**
	 * 获取中文的unicode码
	 * @param chineseStr
	 * @return
	 */
	public static String getChineseASCII(String chineseStr){
		StringBuffer strBuf = new StringBuffer();
		byte[]  bytes = chineseStr.getBytes();
		for(int i =0;i<bytes.length;i++){
			strBuf.append(Integer.toHexString(bytes[i]&0xff));
		}
		return strBuf.toString();
	}
	
}






























