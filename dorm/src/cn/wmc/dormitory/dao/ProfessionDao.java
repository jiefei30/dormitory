package cn.wmc.dormitory.dao;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import cn.wmc.dormitory.util.DButil;

public class ProfessionDao {
	public ArrayList<Object[]> getProfessions(int id) {
		ArrayList<Object[]> res=null;
		String condition="";
		if(id!=0)condition=" WHERE institute_id ="+id;
		String sql = "SELECT profession_name FROM profession"+condition;
		res = DButil.read(sql, 1);
		return res;
	}
	public String resToJson(ArrayList<Object[]> object){
		String json ="";
		String[] res = new String[object.size()]; 
		for(int i=0;i<object.size();i++) {
			res[i] = (String)object.get(i)[0];
		}
		json=JSON.toJSONString(res);
		return json;
	}
	
	
	public int getInstituteId(Object profession_id) {
		return (int)DButil.getRestValue("SELECT institute_id FROM profession WHERE profession_id = ?",profession_id.toString());
	}
}
