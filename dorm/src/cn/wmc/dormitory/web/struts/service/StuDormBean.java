package cn.wmc.dormitory.web.struts.service;

public class StuDormBean {
	private int studorm_id;
	private long stu_number;
	private int room_id;
	public StuDormBean(long stu_number, int room_id) {
		super();
		this.stu_number = stu_number;
		this.room_id = room_id;
	}
	public StuDormBean() {}
	public int getStudorm_id() {
		return studorm_id;
	}
	public long getStu_number() {
		return stu_number;
	}
	public int getRoom_id() {
		return room_id;
	}
	public void setStudorm_id(int studorm_id) {
		this.studorm_id = studorm_id;
	}
	public void setStu_number(long stu_number) {
		this.stu_number = stu_number;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
}
