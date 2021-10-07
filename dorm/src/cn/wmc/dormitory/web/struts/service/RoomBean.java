package cn.wmc.dormitory.web.struts.service;

public class RoomBean {
	private int room_id;
	private int room_name;
	private int building;
	private int used_people;
	private int max_people;
	public RoomBean() {}
	public int getRoom_id() {
		return room_id;
	}
	public RoomBean(int room_id, int room_name, int building, int used_people, int max_people) {
		super();
		this.room_id = room_id;
		this.room_name = room_name;
		this.building = building;
		this.used_people = used_people;
		this.max_people = max_people;
	}
	public int getRoom_name() {
		return room_name;
	}
	public int getBuilding() {
		return building;
	}
	public int getUsed_people() {
		return used_people;
	}
	public int getMax_people() {
		return max_people;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public void setRoom_name(int room_name) {
		this.room_name = room_name;
	}
	public void setBuilding(int building) {
		this.building = building;
	}
	public void setUsed_people(int used_people) {
		this.used_people = used_people;
	}
	public void setMax_people(int max_people) {
		this.max_people = max_people;
	}
	
}
