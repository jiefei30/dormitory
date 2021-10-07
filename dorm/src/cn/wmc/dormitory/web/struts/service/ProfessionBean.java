package cn.wmc.dormitory.web.struts.service;

public class ProfessionBean {
	private int profession_id;
	private String profession_name;
	private int institute_id;
	public ProfessionBean(String profession_name, int institute_id) {
		super();
		this.profession_name = profession_name;
		this.institute_id = institute_id;
	}
	public ProfessionBean() {}
	public int getProfession_id() {
		return profession_id;
	}
	public String getProfession_name() {
		return profession_name;
	}
	public int getInstitute_id() {
		return institute_id;
	}
	public void setProfession_id(int profession_id) {
		this.profession_id = profession_id;
	}
	public void setProfession_name(String profession_name) {
		this.profession_name = profession_name;
	}
	public void setInstitute_id(int institute_id) {
		this.institute_id = institute_id;
	}
	
}
