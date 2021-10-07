package cn.wmc.dormitory.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.wmc.dormitory.dao.ProfessionDao;
import cn.wmc.dormitory.dao.StudentDao;
import cn.wmc.dormitory.util.DButil;
import cn.wmc.dormitory.web.struts.service.ResponseBean;
import cn.wmc.dormitory.web.struts.service.RoomBean;

/**
 * Servlet implementation class ModifyServlet
 */
@WebServlet("/ModifyServlet")
public class ModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//状态参数部分
		int status = 0;
		String statusMsg = "数据正常";
		
		//所需类
		StudentDao sd = new StudentDao();
		RoomBean rb = null;
		RoomBean rb_old = null;
		ResponseBean resb = null;
		
		//请求参数
		String stu_name = request.getParameter("stu_name");
		String stu_number = request.getParameter("stu_number");
		int institute_id = Integer.parseInt(request.getParameter("institute_id"))+1;
		int profession_rank = Integer.parseInt(request.getParameter("profession_rank"))+1;
		int profession_id = sd.getProfessionId(institute_id,profession_rank);
		int building = Integer.parseInt(request.getParameter("building"))+1; 
		int room_name = Integer.parseInt(request.getParameter("room_name")); 
		rb = sd.getRoomInfo(building, room_name);
		
		//数据库原数据
		String stu_name_old = sd.getStuName(stu_number);
		int profession_id_old = sd.getProfessionId(stu_number);
		int institute_id_old = new ProfessionDao().getInstituteId(profession_id_old);
		int room_id_old = sd.getStuRoom(stu_number);
		rb_old = sd.getRoomInfo(room_id_old);
		
		System.out.println(stu_name+" "+stu_name_old +" "+profession_id+" "+profession_id_old+" "+institute_id+" "+
				institute_id_old+" "+building+" "+rb_old.getBuilding()+" "+room_name+" "+rb_old.getRoom_name());
		//开始判断
		if(rb==null) {
			status = 1;
			statusMsg = "宿舍不存在";
		}else if(stu_name_old.equals(stu_name) && institute_id == institute_id_old && profession_id == profession_id_old
				&& building == rb_old.getBuilding() && room_name == rb_old.getRoom_name()) {
			status = 2;
			statusMsg = "请做出修改";
		}else if(rb.getMax_people()==rb.getUsed_people()) {
			status = 3;
			statusMsg = "该房间人数已满";
		}else {
			if(building == rb_old.getBuilding() && room_name == rb_old.getRoom_name()) {
				DButil.write("UPDATE stuinfo SET stu_name = ?,profession_id=? WHERE stu_number = ?",stu_name,profession_id,stu_number);
			}else {
				status = sd.mdfStuInfo(stu_name, stu_number, profession_id+"",rb.getRoom_id(),rb.getUsed_people(),rb_old.getRoom_id(),rb_old.getUsed_people());
			}
		}
		
		//响应结果
				resb = new ResponseBean(status,statusMsg);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().append(JSON.toJSONString(resb));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
