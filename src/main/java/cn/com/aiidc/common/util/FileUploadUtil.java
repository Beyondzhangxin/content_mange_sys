package cn.com.aiidc.common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public class FileUploadUtil {
	
	//定义缩略图的宽度
		public static final int WIDTH =100;
		//定义缩略图的高度
		public static final int HEIGHT =100;
		
		/**
		 * 通过输入输出流上传图片
		 * @param file
		 * @param uploadPath
		 * @param realUploadPath
		 * @return 返回图片保存的绝对路径
		 */
		public static String uploadImage(CommonsMultipartFile file ,String uploadPath,String realUploadPath){
			InputStream is = null;
			OutputStream os = null;
			try {
				is = file.getInputStream();//获取上传文件的输入流
				//输出流的目标路径
				String des = realUploadPath+"/"+file.getOriginalFilename();
				//获取输出流
				os = new FileOutputStream(des);
				byte[] buffer = new byte[1024];
				int len = 0;
				while((len=is.read(buffer))>0){
					os.write(buffer);//通过输出流将读取的字节写出
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(is != null){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(os !=null){
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return realUploadPath+"/"+file.getOriginalFilename();
		}
		
		
		/**
		 * 	推荐
		 *  通过Thumbnailator类生成缩略图
		 *  注意：需要thumbnailator-0.4.8.jar
		 * @param file 上传文件
		 * @param realUploadPath 上传文件保存的绝对路径
		 * @return 上传文件保存的绝对路径
		 */
		public  static String thumbnail(CommonsMultipartFile file,String realUploadPath){
			try {
				//缩略图在服务器上保存的绝对路径
				String des = realUploadPath+"/thum_"+file.getOriginalFilename();
				Thumbnails.of(file.getInputStream()).size(WIDTH,HEIGHT).toFile(des);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return  realUploadPath+"/thum_"+file.getOriginalFilename();
		}
		
		/**
		 * 通过awt生成缩略图
		 * @param file
		 * @param uploadPath
		 * @param realUploadPath
		 * @return
		 */
		public static String thumbnailByAwt(CommonsMultipartFile file ,String realUploadPath)
		{
			OutputStream os = null;
			try {
				String des = realUploadPath+"/thum_"+file.getOriginalFilename();
				//获取输出流
				os = new FileOutputStream(des);
				//读取上传的图片信息
				Image image = ImageIO.read(file.getInputStream());
				int width = image.getWidth(null);//获取原图宽度
				int heigth = image.getHeight(null);//获取原图高度
				//计算宽度、高度缩略比列
				int rate1 = width/WIDTH;
				int rate2 = heigth/HEIGHT;
				int rate =0;
				if(rate1 > rate2){//获取较大的缩略比例
					rate = rate1;
				}else{
					rate =rate2;
				}
				//计算缩略图最终的宽度和高度
				int newWidth = width/rate;
				int newHeight = heigth/rate;
				//创建图像缓冲对象
				BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
				//绘制缩略图
				bufferedImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, image.SCALE_SMOOTH),0,0,null);
				//*image/jpeg
				String imageType = file.getContentType().substring(file.getContentType().indexOf("/")+1);
				ImageIO.write(bufferedImage, imageType, os);//通过输入流输入到目标对象中
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(os !=null ){
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return realUploadPath+"/thum_"+file.getOriginalFilename();
		}
}
