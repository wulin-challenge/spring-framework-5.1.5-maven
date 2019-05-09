package cn.wulin.maven;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.wulin.maven.domain.ClassPath;
import cn.wulin.maven.domain.ClassPathEntry;

@SuppressWarnings("unchecked")
public class Normal2Maven {
	
	/**
	 * 根路径
	 */
	private static final String root_path = "D:/software/helpProgramming/git_client/git/workspace_directory/self_directory/spring_source_5.1.5_normal_workspace/spring-framework-5.1.6.RELEASE/spring-context";
	
	public static void main(String[] args) {
		Normal2Maven normal2Maven = new Normal2Maven();
		List<File> classPathFiles = FileUtil.getClassPathFiles(root_path);
		for (File classPathXml : classPathFiles) {
//			System.out.println(classPathXml.getAbsolutePath());
			ClassPath classPath = normal2Maven.parseClassPathXml(classPathXml);
			normal2Maven.dependencyPrint(classPath);
		}
	}
	
	private void dependencyPrint(ClassPath classPath){
		List<ClassPathEntry> classPathEntryList = classPath.getClassPathEntryList();
		if(classPathEntryList == null || classPathEntryList.size() == 0){
			return;
		}
		
		classPathEntryList.sort((o1,o2)->{return o1.getOrder()-o2.getOrder();});
		
		for (ClassPathEntry classPathEntry : classPathEntryList) {
			String buildDependency = buildDependency(classPathEntry);
			System.out.println(buildDependency);
		}
		
	}
	
	/**
	 * 构建依赖
	 * @param path
	 * @return
	 */
	private String buildDependency(ClassPathEntry classPathEntry){
		if(classPathEntry.getOrder() != 0){
			return classPathEntry.getPath();
		}
		StringBuilder dependencyPrint = new StringBuilder();
		String[] dependency = classPathEntry.getPath().split("/");
		if(dependency.length == 12){
			String groupId = dependency[7];
			String artifactId = dependency[8];
			String version = dependency[9];
			dependencyPrint.append("<dependency>");
			dependencyPrint.append("\n\t <groupId>"+groupId+"</groupId>");
			dependencyPrint.append("\n\t <artifactId>"+artifactId+"</artifactId>");
			dependencyPrint.append("\n\t <version>"+version+"</version>");
			dependencyPrint.append("\n</dependency>");
		}
		return dependencyPrint.toString();
	}
	
	public ClassPath parseClassPathXml(File classPathXml){
		ClassPath classPath = new ClassPath();
		
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(classPathXml);
			Element root = document.getRootElement();
			
			parseRootElement(classPath, root);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return classPath;
	}
	
	private void parseRootElement(ClassPath classPath,Element parent){
		List<Element> elements = parent.elements("classpathentry");
		for (Element element : elements) {
			parseClasspathentryElement(classPath, element);
		}
	}
	
	private void parseClasspathentryElement(ClassPath classPath,Element parent){
		String kind = parent.attribute("kind").getText();
		String path = FileUtil.replaceSpritAndNotEnd(parent.attribute("path").getText());
		ClassPathEntry classPathEntry = new ClassPathEntry();
		String[] dependency = path.split("/");
		if(kind.equals("lib") && dependency.length == 12){
			classPathEntry.setOrder(0);
		}else{
			classPathEntry.setOrder(1);
		}
		
		classPathEntry.setKind(kind);
		classPathEntry.setPath(path);
		classPath.getClassPathEntryList().add(classPathEntry);
	}
}
