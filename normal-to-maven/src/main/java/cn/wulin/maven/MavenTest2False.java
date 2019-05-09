package cn.wulin.maven;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.wulin.maven.domain.ClassPath;

/**
 * 将导入eclipse的maven的src/test/java等目录变为
 * 
 * @author ThinkPad
 *
 */
@SuppressWarnings("unchecked")
public class MavenTest2False {

	/**
	 * 根路径
	 */
	private static final String root_path = "D:\\software\\helpProgramming\\git_client\\git\\workspace_directory\\self_directory\\spring_source_maven_workspace\\spring-framework-parent";

	public static void main(String[] args) {
		MavenTest2False mavenTest2False = new MavenTest2False();
		List<File> classPathFiles = FileUtil.getClassPathFiles(root_path);
		for (File classPathXml : classPathFiles) {
			Document document = mavenTest2False.parseClassPathXml(classPathXml);
			try {
				mavenTest2False.writer(document, classPathXml);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println();
	}

	/**
	 * 把document对象写入新的文件
	 * 
	 * @param document
	 * @throws Exception
	 */
	public void writer(Document document, File writeFile) throws Exception {
		// 紧凑的格式
		// OutputFormat format = OutputFormat.createCompactFormat();
		// 排版缩进的格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 设置编码
		format.setEncoding("UTF-8");
		// 创建XMLWriter对象,指定了写出文件及编码格式
		// XMLWriter writer = new XMLWriter(new FileWriter(new
		// File("src//a.xml")),format);
		XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(writeFile), "UTF-8"), format);
		// 写入
		writer.write(document);
		// 立即写入
		writer.flush();
		// 关闭操作
		writer.close();
	}

	public Document parseClassPathXml(File classPathXml) {
		ClassPath classPath = new ClassPath();

		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(classPathXml);
			Element root = document.getRootElement();

			parseRootElement(classPath, root);
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void parseRootElement(ClassPath classPath, Element parent) {
		List<Element> elements = parent.elements("classpathentry");
		for (Element element : elements) {

			parseClasspathentryElement(classPath, element);
		}
	}

	private void parseClasspathentryElement(ClassPath classPath, Element parent) {

		Element attributes = parent.element("attributes");
		if (attributes == null) {
			return;
		}
		List<Element> attributeList = attributes.elements("attribute");

		if (attributeList == null || attributeList.size() == 0) {
			return;
		}
		for (Element element : attributeList) {
			parseAttributeElement(element);
		}
	}

	private void parseAttributeElement(Element parent) {
		Attribute attribute = parent.attribute("name");
		if (attribute != null && attribute.getText() != null && attribute.getText().trim().equals("test")) {
			Element addAttribute = parent.addAttribute("value", "false");
			System.out.println(addAttribute);
		}
	}

}
