package cn.wmc.dormitory.web.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.wmc.dormitory.dao.StudentDao;
import cn.wmc.dormitory.web.struts.service.ResponseBean;
import cn.wmc.dormitory.web.struts.service.RoomBean;

/**
 * Servlet implementation class AddTest
 */
@WebServlet("/AddTestServlet")
public class AddTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTestServlet() {
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
		ResponseBean resb = null;
		RoomBean rb = null;
		
		//请求参数
		String stu_name = request.getParameter("stu_name");
		String stu_number = request.getParameter("stu_number");
		int institute_id = Integer.parseInt(request.getParameter("institute_id"))+1;
		int profession_rank = Integer.parseInt(request.getParameter("profession_rank"))+1;
		int profession_id = sd.getProfessionId(institute_id,profession_rank);
		int building = Integer.parseInt(request.getParameter("building"))+1; 
		int room_name = Integer.parseInt(request.getParameter("room_name")); 
		rb = sd.getRoomInfo(building, room_name);
		
		//开始判断
		if(sd.isStuNumberRepeat(stu_number)) {
			status = 1;
			statusMsg = "学号重复";
		}else if(rb==null) {
			status = 2;
			statusMsg = "不存在该宿舍";
		}else if(rb.getUsed_people()==rb.getMax_people()) {
			status = 3;
			statusMsg = "不存在该宿舍";
		}else {
			status = sd.addStuInfo(stu_name, stu_number, profession_id+"",rb.getRoom_id(),rb.getUsed_people());
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
