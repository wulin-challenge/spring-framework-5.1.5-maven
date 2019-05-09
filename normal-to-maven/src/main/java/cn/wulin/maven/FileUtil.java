package cn.wulin.maven;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件工具类
 * @author wubo
 *
 */
public class FileUtil {
	
	/**
	 * longId生成锁
	 */
	private static Object idLock = new Object();
	
	/**
	 * 替换反斜杠,解决在windows,linux下的路径问题
	 * @param path
	 * @return
	 */
	public static String replaceSprit(String path){
		if(StringUtils.isEmpty(path)){
			return "";
		}
		path = path.replace("\\\\", "/"); //// Java中4个反斜杠表示一个反斜杠
		path = path.replace("\\", "/"); 
		return path;
	}
	
	/**
	 * 替换反斜杠,解决在windows,linux下的路径问题,并且以"/"结束
	 * @param path
	 * @return
	 */
	public static String replaceSpritAndEnd(String path){
		if(StringUtils.isEmpty(path)){
			return "";
		}
		path = path.replace("\\\\", "/"); //// Java中4个反斜杠表示一个反斜杠
		path = path.replace("\\", "/"); 
		
		if(!path.endsWith("/")){
			path+="/";
		}
		return path;
	}
	
	/**
	 * 替换反斜杠,解决在windows,linux下的路径问题,并且不能以"/"结束
	 * @param path
	 * @return
	 */
	public static String replaceSpritAndNotEnd(String path){
		if(StringUtils.isEmpty(path)){
			return "";
		}
		path = path.replace("\\\\", "/"); //// Java中4个反斜杠表示一个反斜杠
		path = path.replace("\\", "/"); 
		
		if(path.endsWith("/")){
			path = path.substring(0, path.length()-1);
		}
		return path;
	}
	
	/**
	 * 得到uuid
	 * @return
	 */
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		return uuid;
	}
	
	/**
	 * long型id
	 * @return
	 */
	public static long getLongId(){
		synchronized (idLock) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String format = sdf.format(new Date());
			return Long.parseLong(format);
		}
	}
	
	/**
	 * 删除某个文件夹及该文件夹下的所有文件及文件夹
	 * @param delpath
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteFile(String delpath) throws Exception{
		try {
			delpath = delpath.replace("\\\\", "/"); //// Java中4个反斜杠表示一个反斜杠
			delpath = delpath.replace("\\", "/"); 
			File file = new File(delpath);
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "/" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
						System.out.println(delfile.getAbsolutePath() + "删除文件成功");
					} else if (delfile.isDirectory()) {
						deleteFile(delpath + "/" + filelist[i]);
					}
				}
				System.out.println(file.getAbsolutePath() + "删除成功");
				file.delete();
			}

		} catch (FileNotFoundException e) {
			System.out.println("deletefile() Exception:" + e.getMessage());
		}
		return true;
	}
	
	/**
	 * 得到 .classpath 文件
	 * @return
	 */
	public static List<File> getClassPathFiles(String root_path){
		List<File> returnFiles = new ArrayList<File>();
		File rootFile = new File(FileUtil.replaceSpritAndNotEnd(root_path));
		
		if(!rootFile.exists() || !rootFile.isDirectory()){
			System.out.println("指定的根目录不存在,退出转换!");
			System.exit(0);
		}
		
		getClassPathFiles(returnFiles,rootFile);
		return returnFiles;
	}
	
	/**
	 * 递归得到 得到 .classpath 文件
	 * @param returnFiles
	 * @param parentFile
	 */
	private static void getClassPathFiles(List<File> returnFiles,File parentFile){
		if(parentFile.isFile()){
			returnFiles.add(parentFile);
		}else{
			File[] listFiles = parentFile.listFiles(new FilenameFilter(){
				@Override
				public boolean accept(File dir, String name) {
					if(".classpath".equals(name)){
						return true;
					}
					
					File tempFile = new File(dir,name);
					if(tempFile.isDirectory()){
						return true;
					}
					return false;
				}
			});
			for (File subParentFile : listFiles) {
				getClassPathFiles(returnFiles,subParentFile);
			}
		}
	}
	
}
