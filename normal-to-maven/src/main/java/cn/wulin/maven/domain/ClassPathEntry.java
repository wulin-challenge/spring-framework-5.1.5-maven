package cn.wulin.maven.domain;

public class ClassPathEntry {
	
	/**
	 * 排序字段
	 */
	private int order = 0;
	private String kind;
	private String path;
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
