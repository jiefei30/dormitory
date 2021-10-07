package cn.wmc.dormitory.web.struts.service;

public class InstituteBean {
	private int institute_id;
	private String institue_name;
	public InstituteBean(String institue_name) {
		super();
		this.institue_name = institue_name;
	}
	public InstituteBean() {}
	public int getInstitute_id() {
		return institute_id;
	}
	public String getInstitue_name() {
		return institue_name;
	}
	public void setInstitute_id(int institute_id) {
		this.institute_id = institute_id;
	}
	public void setInstitue_name(String institue_name) {
		this.institue_name = institue_name;
	}
	
}
