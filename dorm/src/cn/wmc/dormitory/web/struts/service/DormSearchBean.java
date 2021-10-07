package cn.wmc.dormitory.web.struts.service;

import com.alibaba.fastjson.annotation.JSONField;

public class DormSearchBean {
	 @JSONField(ordinal = 0)
	private int number;
	 
	 @JSONField(ordinal = 1)
	private String stu_name;
	 
	 @JSONField(ordinal = 2)
	private long stu_number;
	 
	 @JSONField(ordinal = 3)
	private String institute_name;
	 
	 @JSONField(ordinal = 4)
	private String profession_name;
	 
	 @JSONField(ordinal = 5)
	private int building;
	 
	 @JSONField(ordinal = 6)
	private int room_name;
	public DormSearchBean() {
		
	}
	public DormSearchBean(int number, String stu_name, long stu_number, String institute_name, String profession_name,
			int building, int room_name) {
		this.number = number;
		this.stu_name = stu_name;
		this.stu_number = stu_number;
		this.institute_name = institute_name;
		this.profession_name = profession_name;
		this.building = building;
		this.room_name = room_name;
	}



	public int getNumber() {
		return number;
	}
	public String getStu_name() {
		return stu_name;
	}
	public long getStu_number() {
		return stu_number;
	}
	public String getInstitute_name() {
		return institute_name;
	}
	public String getProfession_name() {
		return profession_name;
	}
	public int getBuilding() {
		return building;
	}
	public int getRoom_name() {
		return room_name;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public void setStu_number(long stu_number) {
		this.stu_number = stu_number;
	}
	public void setInstitute_name(String institute_name) {
		this.institute_name = institute_name;
	}
	public void setProfession_name(String profession_name) {
		this.profession_name = profession_name;
	}
	public void setBuilding(int building) {
		this.building = building;
	}
	public void setRoom_name(int room_name) {
		this.room_name = room_name;
	}
	@Override
	public String toString() {
		return "DormSearchBean [number=" + number + ", stu_name=" + stu_name + ", stu_number=" + stu_number
				+ ", institute_name=" + institute_name + ", profession_name=" + profession_name + ", building="
				+ building + ", room_name=" + room_name + "]";
	}
	
}
