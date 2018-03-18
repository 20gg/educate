package com.hysm.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

public class FileUtil {
	/**
	 * 上传文件保存到当前工程根目录的upload文件夹下
	 * 
	 * @param request
	 * @param filedata
	 * @param filenameext
	 * @param newfilename
	 * @return
	 */
	public static String uploadfilenew_local(HttpServletRequest request,
			File filedata, String filenameext, String newfilename) {
		String filepath = request.getSession().getServletContext().getRealPath(
				"");
		String newname = "";
		InputStream stream = null;
		OutputStream bos = null;
		try {
			stream = new BufferedInputStream(new FileInputStream(filedata),
					16 * 1024);
			newname = newfilename + "." + filenameext;
			bos = new FileOutputStream(filepath + "/apic/" + newname);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.close();
			stream.close();
		} catch (Exception e) {
			newname = "error";
		} finally {
			try {
				bos.close();
				stream.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return newname;
	}

}
