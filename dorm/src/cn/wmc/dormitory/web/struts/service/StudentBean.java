package cn.wmc.dormitory.web.struts.service;

public class StudentBean {
	private int stu_id;
	private String stu_name;
	private long stu_number;
	private int profession_id;
	public StudentBean() {
		
	}
	public StudentBean(String stu_name, long stu_number,int profession_id) {
		super();
		this.stu_name = stu_name;
		this.stu_number = stu_number;
		this.profession_id = profession_id;
	}
	public int getStu_id() {
		return stu_id;
	}
	public String getStu_name() {
		return stu_name;
	}
	public long getStu_number() {
		return stu_number;
	}
	public int getProfession_id() {
		return profession_id;
	}
	public void setStu_id(int stu_id) {
		this.stu_id = stu_id;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public void setStu_number(long stu_number) {
		this.stu_number = stu_number;
	}
	public void setProfession_id(int profession_id) {
		this.profession_id = profession_id;
	}
}
