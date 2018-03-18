package com.hysm.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class FileUpAndWr {
	/**
	 * @author 陈思成
	 * @param path：物理路径
	 * @param image：图片file
	 * @param imageContentType：图片类型
	 * @return
	 * @throws IOException
	 */
	//将图片写入指定文件夹,返回路径
			public static String uploadImage(String path,File image,String imageContentType) throws IOException {
				String paths=path+"/"+DateUtil.getLocalByDate()+"/";
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
		        ServletContext servletContext = webApplicationContext.getServletContext();
				String realpath = servletContext.getRealPath(paths);
				
				String randamName=String.valueOf(System.currentTimeMillis());
				String[] types=imageContentType.split("/");
				String type=types[types.length-1];
				System.out.println(type);
				String imageFileName=randamName+"."+type;
				if (image != null) {
					
					File savefile = new File(new File(realpath),imageFileName);
					if (!savefile.getParentFile().exists())
					{	//判断父文件夹是否存在，不存在就建一个父文件夹
						savefile.getParentFile().mkdirs();
					}
					FileUtils.copyFile(image, savefile);
					//ActionContext.getContext().put("message", "图片上传成功");
				}else {
					
					return null;
				}
				
				
				return (paths + imageFileName);
			}
			
			/**
			 * 写html文件，并返回路径
			 */
			
			public static String createHtml(String path,String data) throws IOException {
				String paths=path+"/"+DateUtil.getLocalByDate()+"/";
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
		        ServletContext servletContext = webApplicationContext.getServletContext();
				String realpath = servletContext.getRealPath(paths);
				
				
				String randamName=String.valueOf(System.currentTimeMillis()/1000);
				
				
				String imageFileName=randamName+".html";
				if (data != null) {
					
					File savefile = new File(new File(realpath),imageFileName);
					if (!savefile.getParentFile().exists())
					{	//判断父文件夹是否存在，不存在就建一个父文件夹
						savefile.getParentFile().mkdirs();
					}
					FileUtils.writeStringToFile(savefile, data, "utf-8");
					
					//ActionContext.getContext().put("message", "图片上传成功");
				}else {
					
					return null;
				}
				
				
				return (paths + imageFileName);
			}

			public static String createHtml2(String realPath, String str) {
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
		        ServletContext servletContext = webApplicationContext.getServletContext();
				
				String r = servletContext.getRealPath(realPath);
				
				
				if (str != null) {
					
					File savefile = new File(r);
					
					try {
						FileUtils.writeStringToFile(savefile, str, "utf-8");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//ActionContext.getContext().put("message", "图片上传成功");
				}else {
					
					return null;
				}
				
				
				return realPath;
			}
			public static String createJson(String realPath, String str) {
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
		        ServletContext servletContext = webApplicationContext.getServletContext();
				
				String r =servletContext.getRealPath(realPath);
				
				
				if (str != null) {
					
					File savefile = new File(r);
					
					try {
						FileUtils.writeStringToFile(savefile, str, "utf-8");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//ActionContext.getContext().put("message", "图片上传成功");
				}else {
					
					return null;
				}
				
				
				return realPath;
			}
			/**
			 * 将图片写入指定文件夹,并压缩,返回路径
			 * @author 陈思成
			 * @param path：物理路径
			 * @param image：图片file
			 * @param imageContentType：图片类型
			 * @return
			 * @throws IOException
			 */
			
					
			
			 /**
		     * 按倍率缩小图片
		     * 
		     * @param srcImagePath
		     *            读取图片路径
		     * @param toImagePath
		     *            写入图片路径
		     * @param widthRatio
		     *            宽度
		     * @param heightRatio
		     *            高度
		     * @throws IOException
		     */
		    public void reduceImageByRatio(String srcImagePath, String toImagePath,
		            int widthRatio, int heightRatio) throws IOException
		    {
		        FileOutputStream out = null;
		        try
		        {
		            // 读入文件
		            File file = new File(srcImagePath);
		            // 构造Image对象
		            BufferedImage src = javax.imageio.ImageIO.read(file);
		            int width = src.getWidth();
		            int height = src.getHeight();
		            // 缩小边长
		            BufferedImage tag = new BufferedImage(widthRatio, heightRatio,
		                    BufferedImage.TYPE_INT_RGB);
		            // 绘制 缩小 后的图片
		            tag.getGraphics().drawImage(src, 0, 0, widthRatio, heightRatio,
		                    null);
		            out = new FileOutputStream(toImagePath);
		            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		            encoder.encode(tag);
		        }
		        catch (Exception e)
		        {
		            e.printStackTrace();
		        }
		        finally
		        {
		            if (out != null)
		            {
		                out.close();
		            }
		        }
		    }
}
