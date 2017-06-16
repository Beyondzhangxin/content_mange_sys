package cn.com.aiidc.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class FileUtil {
	
	
	/**
	 * 产生全局唯一文件名
	 * 利用base64编码UUID
	 * @return
	 */
	public static String getFileName(){
		return Base64.encodeBase64String(UUID.randomUUID().toString().getBytes());
	}
	
	/**
	 * 根据文件的路径，读取文件的内容
	 * @param savePath 文件的保存路径:xxx/xxx/xxx/xxx.html
	 * @return
	 */
	public static String ReadFileByBufferedReader(String savePath){
		StringBuffer sb = new StringBuffer();
		FileInputStream fis;
		try {
			fis = new FileInputStream(savePath);
			//GBK、GB2312中文不乱码；utf-8中文乱码
			BufferedReader br = new BufferedReader(new InputStreamReader(fis,"utf-8"));
			String temp = null;
			while((temp= br.readLine()) != null){
				sb.append(temp);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 向指定的文件中写入指定的字符串
	 * @param text 需要写入的字符串
	 * @param filePath 被写入的文件:xxx/xxx/xxx/xxx.html
	 */
	public static void WriteFileByBufferedWriter(String text,String filePath){
		File file = new File(filePath);
		try {
			file.createNewFile();
			//向文件中写入字符串
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(text);
			writer.close();
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除文件
	 * @param path 文件的保存路径:xxx/xxx/xxx/xxx.html
	 * @return
	 */
	public static boolean deleteFile(String path){
		boolean ok =false;
		File deleteFile = new File(path);
		if(deleteFile.exists()){
			ok = deleteFile.delete();
		}
		return ok;
	}
	
	/**
	 * 将指定的文件复制到指定的目录下
	 * @param filepath 指定的文件：xxxx/xxx/xxx/xxx.html
	 * @param savePath 指定的目录： xxxx/xxx/年/月/日
	 * @return 返回文件复制后的保存路径
	 */
	public static String copyFile(String filepath ,String savePath){
		File file = new File(filepath);
		String filename = file.getName();
		if(file.exists()){//判断文件是否存在
			try {
				@SuppressWarnings("resource")
				FileInputStream input  = new FileInputStream(filepath);
				@SuppressWarnings("resource")
				FileOutputStream output = new FileOutputStream(savePath+File.separator+filename);
				int in = input.read();
		        while(in!=-1){  
		            output.write(in);  
		            in = input.read();  
		        }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return savePath+File.separator+filename;
	}
	
	
	/**
	 * 将指定文件夹下文件复制到指定的目录下
	 * @param targetFilePath 目标目录： xxxx/xxx/年/月/日
	 * @param savePath 复制目录： xxxx/xxx/年/月/日
	 */
	public static void copyFiles(String targetFilePath,String savePath){
		File targetFile = new File(targetFilePath);
		if(targetFile.isDirectory()){
			//获取目录下的所有文件
			File[] files = targetFile.listFiles();
			for(File file : files){
				//获取文件名
				String filename = file.getName();
				String filepath = targetFilePath+File.separator+filename;
				copyFile(filepath, savePath);
			}
		}
	}
}




























