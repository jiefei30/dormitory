package cn.wmc.dormitory.dao;
import cn.wmc.dormitory.web.struts.service.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSONArray;
import cn.wmc.dormitory.util.DButil;
public class StudentDao {
	//基本查询语句
	String basicSql1="SELECT "
			+ "si.stu_name,si.stu_number,i.institute_name,p.profession_name,room.building,room.room_name "
			+ "FROM stuinfo AS si "
			+ "INNER JOIN "
			+ "profession AS p ON si.profession_id =p.profession_id "
			+ "INNER JOIN "
			+ "institute AS i ON p.institute_id =i.institute_id "
			+ "INNER JOIN "
			+ "studorm AS sd ON si.stu_number=sd.stu_number "
			+ "INNER JOIN "
			+ "room ON sd.room_id=room.room_id";
	
	String basicSql2="SELECT count(*) "
			+ "FROM stuinfo AS si "
			+ "INNER JOIN "
			+ "profession AS p ON si.profession_id =p.profession_id "
			+ "INNER JOIN "
			+ "institute AS i ON p.institute_id =i.institute_id "
			+ "INNER JOIN "
			+ "studorm AS sd ON si.stu_number=sd.stu_number "
			+ "INNER JOIN "
			+ "room ON sd.room_id=room.room_id";
	//加上4个条件查询
	public String conditionQuerySql(int[] allid,boolean isPage){
		String sql = null;
		if(isPage)sql = basicSql2;
		else sql = basicSql1;
		for(int i=0,flag=0;i<4;i++) {
			  if(allid[i]!=0) {
				  if(flag == 0) {
					  sql+=" WHERE ";
					  flag=1;
				  }else {
					  sql+=" AND ";
				  }
				  switch(i) {
				  case 0:sql+="i.institute_id="+allid[0];break;
				  case 1:sql+="p.profession_rank="+allid[1];break;
				  case 2:sql+="room.building="+allid[2];break;
				  case 3:sql+="room.room_name="+allid[3];break;
				  }
			  }
		  }
		return sql;
	}
	//加上两个模糊查询
	public String fuzzyQuerySql(boolean name,String msg,boolean isPage) {
		String sql = null;
		if(isPage)sql = basicSql2;
		else sql = basicSql1;
		String con = "si.stu_name";
		if(!name) con = "si.stu_number";
		sql+=" Where "+con+" like '%"+msg+"%'";
		return sql;
	}
	//查询总页数
	public long getTotalPage(String sql) {
		return  (long)DButil.getRestValue(sql);
	}
	
	//分页查询结果
	public List<DormSearchBean> getQueryList(String sql,int page){ 
		sql+=" LIMIT "+page*5+",5";
		ArrayList<Object[]> res = DButil.read(sql,6);
		List<DormSearchBean> list = new ArrayList<DormSearchBean>();
		for(int i=0;i<res.size();i++) {
			DormSearchBean dsb = new DormSearchBean();
			dsb.setNumber(i+1);
			dsb.setStu_name((String)res.get(i)[0]);
			dsb.setStu_number(((BigInteger)res.get(i)[1]).longValue());
			dsb.setInstitute_name((String)res.get(i)[2]);
			dsb.setProfession_name((String)res.get(i)[3]);
			dsb.setRoom_name((int)res.get(i)[4]);
			dsb.setBuilding((int)res.get(i)[5]);
			list.add(dsb);			
		}
		return list;
	}
	//获得专业的ID
	public int getProfessionId(int institute_id,int profession_rank) {
		return (int)DButil.getRestValue("SELECT profession_id FROM profession WHERE institute_id = ? AND profession_rank = ?",institute_id,profession_rank);
	}
	//获得学生的专业id
	public int getProfessionId(String stu_number) {
		return (int)DButil.getRestValue("SELECT profession_id FROM stuinfo WHERE stu_number = ?",stu_number);
	}
	//获得宿舍信息实体类
	public RoomBean getRoomInfo(int building,int room_name) {
		RoomBean rb = null;
		ArrayList<Object[]> res = DButil.read("SELECT room_id,used_people,max_people FROM room WHERE building = ? AND room_name = ?",3,building,room_name);
		if(res.size()!=0) rb = new RoomBean((int)res.get(0)[0],room_name,building,(int)res.get(0)[1],(int)res.get(0)[2]);
		return rb;
	}
	//获得宿舍信息实体类
		public RoomBean getRoomInfo(int room_id) {
			RoomBean rb = null;
			ArrayList<Object[]> res = DButil.read("SELECT * FROM room WHERE room_id = ?",5,room_id);
			if(res.size()!=0) rb = new RoomBean((int)res.get(0)[0],(int)res.get(0)[1],(int)res.get(0)[2],(int)res.get(0)[3],(int)res.get(0)[4]);
			return rb;
		}
	
	//检测是否学号重复
	public boolean isStuNumberRepeat(String stuNumber) {
		boolean flag = false;
		long res = (long)DButil.getRestValue("SELECT count(*) FROM stuinfo WHERE stu_number = ?",stuNumber);
		if(res!=0)flag = true;
		return flag;
	}
	
	//增加学生信息
	public int addStuInfo(String stu_name,String stu_number,String profession_id,int room_id,int used_people) {	
		String[] sql = {"INSERT INTO stuinfo(stu_name,stu_number,profession_id) VALUES(?,?,?)",
				"INSERT INTO studorm(stu_number,room_id) VALUES(?,?)",
				"UPDATE room SET used_people = ? WHERE room_id = ?"};
		Object[][] params = {
				{stu_name,stu_number,profession_id},
				{stu_number,room_id},
				{used_people+1,room_id}
		};
		
		int flag =	DButil.write(sql, params);
		return flag;	
	}
	
	//修改学生信息（宿舍变动）
		public int mdfStuInfo(String stu_name,String stu_number,String profession_id,int room_id,
				int used_people,int room_id_old,int used_people_old) {	
			String[] sql = {"UPDATE stuinfo SET stu_name = ?,profession_id=? WHERE stu_number = ?",
					"UPDATE studorm SET room_id =? WHERE stu_number = ?",
					"UPDATE room SET used_people = ? WHERE room_id = ?",
					"UPDATE room SET used_people = ? WHERE room_id = ?"};
			Object[][] params = {
					{stu_name,profession_id,stu_number},
					{room_id,stu_number},
					{used_people+1,room_id},
					{used_people_old-1,room_id_old}
			};
			
			int flag =	DButil.write(sql, params);
			return flag;	
		}
//		//修改学生信息（宿舍未变动）
//				public int mdfStuInfo(String stu_name,String stu_number,String profession_id) {	
//					String[] sql = {"UPDATE stuinfo SET stu_name = ?,profession_id=? WHERE stu_number = ?",
//							"UPDATE studorm SET room_id =? WHERE stu_number = ?",};
//					Object[][] params = {
//							{stu_name,profession_id,stu_number},
//							{room_id,stu_number},
//							{used_people+1,room_id},
//							{used_people_old-1,room_id_old}
//					};
//					
//					int flag =	DButil.write(sql, params);
//					return flag;	
//				}
	
	//获得学生分配的宿舍ID
	public int getStuRoom(String stu_number) {
		return (int)DButil.getRestValue("SELECT room_id FROM studorm WHERE stu_number = ?", stu_number);
	}
	
	//删除学生信息
	public int deleteStuinfo(String numbers) {
		int flag = 0;
		JSONArray stu_numbers = JSONArray.parseArray(numbers);
		String[] sql = {
				"DELETE FROM stuinfo WHERE stu_number = ?",
				"DELETE FROM studorm WHERE stu_number = ?",
				"UPDATE room SET used_people = ? WHERE room_id = ?"
		};
		for(int i=0;i<stu_numbers.size();i++) {
			String stu_number = stu_numbers.get(0).toString();
			int room_id = getStuRoom(stu_number);
			int used_people = (int)DButil.getRestValue("SELECT used_people FROM room WHERE room_id = ?", room_id);
			Object[][] params = {{stu_numbers.get(0)},{stu_numbers.get(0)},{used_people-1,room_id}};
			DButil.write(sql, params);
		}
		return flag;
	}
	//获得学生姓名
	public String getStuName(String stu_number) {
		return (String)DButil.getRestValue("SELECT stu_name FROM stuinfo WHERE stu_number = ?", stu_number);
	}
	
}
