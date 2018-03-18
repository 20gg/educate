package com.hysm.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hysm.bean.MessageType;
import com.hysm.controller.BaseController;
import com.hysm.tools.PageBean;
import com.hysm.tools.StringUtil;

@Controller
@RequestMapping("/img")
public class ImgController extends BaseController {

	private final String imgpath = "/img/";

	@RequestMapping("queryImg")
	@ResponseBody
	public PageBean<JSONObject> queryImgs(HttpServletRequest req) {
		Map<String, Object> m = (Map<String, Object>) getSession()
				.getAttribute(MessageType.sessionManager);

		if (m == null) {

			return null;
		}
		String ipath = imgpath;
		
		String imgp = req.getParameter("imgpath");
		if (StringUtil.bIsNotNull(imgp)) {
			ipath = imgp;
		}

		String likename = req.getParameter("likename");
		String page = req.getParameter("page");

		int ps = 10;
		int pn = StringUtil.toNum(page);
		if (pn < 1) {
			pn = 1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.bIsNotNull(likename)) {
			map.put("likename", likename);
		}
		map.put("pn", pn);
		map.put("ps", ps);

		return this.queryImgsByPath(ipath, map);

	}

	/**
	 * 根据路径获取路径下文件夹和图片 注意分页
	 * 
	 * @param map
	 * 
	 * @param imgpath2
	 * @return
	 */
	private PageBean<JSONObject> queryImgsByPath(String path,
			Map<String, Object> map) {

		PageBean<JSONObject> pb = new PageBean<JSONObject>();
		pb.setPageNum((Integer) map.get("pn"));
		pb.setPageSize((Integer) map.get("ps"));

		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();

		String realpath = servletContext.getRealPath(path);
		File file = new File(realpath);

		if (!file.exists() || !file.isDirectory()) {
			// 判断文件夹是否存在，不存在就建一个文件夹
			file.mkdirs();
			pb.setRowCount(0);
			return pb;
		}

		File[] tempList = file.listFiles();

		if (tempList != null && tempList.length > 0) {

			int k = (pb.getPageNum() - 1) * pb.getPageSize();
			int s = pb.getPageSize();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for (int i = 0; i < tempList.length; i++) {
				File onef = tempList[i];
				String fileName = onef.getName();

				JSONObject one = new JSONObject();
				
				
				one.put("name", path + fileName);
				if (onef.isDirectory()) {
					// 文件夹
					one.put("type", "dir");

				} else if (onef.isFile()) {
					// 文件
					one.put("type", "file");
					one.put("fileName", fileName);
					/*String ss=daxiao(onef);
					if(ss!=null){
						one.put("size", ss);
					}else{
						one.put("size", "未知尺寸");
					}*/

				}
				if (i >= k && list.size() < s) {
					list.add(one);
				}

			}

			pb.setRowCount(tempList.length);

			if (list != null && list.size() > 0) {
				pb.setList(list);
			}

		}

		return pb;

	}
	
	public String daxiao(File picture){
		
		 BufferedImage sourceImg;
		try {
			sourceImg = ImageIO.read(new FileInputStream(picture));
			
			  //System.out.println(String.format("%.1f",picture.length()/1024.0));  
	        //System.out.println(sourceImg.getWidth());  
	        //System.out.println(sourceImg.getHeight()); 
			return sourceImg.getWidth()+"×"+sourceImg.getHeight();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	      return null;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/uploadImg" }, method = { RequestMethod.POST })
	public void up_img(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {

		String imgpath = request.getParameter("imgpath");
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();

		String file_code = String.valueOf(System.currentTimeMillis());

		int res = 0;
		int is_img = 0;
		String path = request.getRealPath("");
		String p_path = "";
		p_path += path;

		if (StringUtil.bIsNotNull(imgpath)) {
			p_path += imgpath;
		}

		String ext = null, file_path = null;
		String fileName = "";

		while (iter.hasNext()) {
			MultipartFile file = multiRequest.getFile((String) iter.next());

			// 有上传文件的话，容量是大于0的。
			if (file.getSize() > 0) {

				// size = size + file.getSize();
				fileName = file.getOriginalFilename();// 文件全名
				ext = fileName.substring(fileName.lastIndexOf("."));// 后缀

				File dir = new File(p_path);
				// 如果文件夹不存在则创建
				if (!dir.exists() && !dir.isDirectory()) {
					dir.mkdir();
				}

				if (ext.equals(".jpg")) {
					is_img = 1;
				} else if (ext.equals(".png")) {
					is_img = 1;
				} else if (ext.equals(".gif")) {
					is_img = 1;
				} else if (ext.equals(".jpeg")) {
					is_img = 1;
				}

				if (is_img == 1) {

					File localFile = new File(p_path, file_code + ext);// 指定文件名称后缀名
					file_path = p_path + file_code + ext;

					try {
						file.transferTo(localFile); // 保存图片到指定文件夹目录中
					} catch (IllegalStateException e) {

						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					res = 1;

				} else {

					res = -1;
				}

			}
		}

		if (is_img == 0) {
			sendMassage("not_img", response);
		} else {
			if (res == 1) {
				sendMassage(file_code + ext, response);
			} else {
				sendMassage("error", response);
			}
		}

	}

	/**
	 * 删除图片/文件
	 * 
	 */
	@RequestMapping("delimg")
	public void delimg(String arr, HttpServletRequest request,
			HttpServletResponse response) {
		JSONArray ja = JSONArray.fromObject(arr);
		for (int i = 0; i < ja.size(); i++) {
			String oneimg = ja.getString(i);
			String path = request.getRealPath(oneimg);

			File file = new File(path);
			if (!file.exists()) {
				System.out.println("删除文件失败:" + path + "不存在！");

			} else {
				if (file.isFile()) {
					deleteFile(path);
				}

			}
		}
		
		sendMassage("suc", response);

	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}


	  /**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = ImgController.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = ImgController.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }
    
	
}
