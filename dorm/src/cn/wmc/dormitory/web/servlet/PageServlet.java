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

/**
 * Servlet implementation class PageServlet
 */
@WebServlet("/PageServlet")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StudentDao studentdao = new StudentDao();
		ResponseBean rb = null;
		String mode = request.getParameter("mode");
		int room=0;
		if(request.getParameter("room")=="") room=0;
		else room= Integer.parseInt(request.getParameter("room"));
		int[] allid = {
				Integer.parseInt(request.getParameter("institute")),
				Integer.parseInt(request.getParameter("profession")),
				Integer.parseInt(request.getParameter("building")),
				room};
		
		
		
		if(mode.equals("condition")) rb = new ResponseBean(studentdao.getTotalPage(
				studentdao.conditionQuerySql(allid, true)),1,"获取数据成功");
		else if(mode.equals("fuzzyName")) rb = new ResponseBean(studentdao.getTotalPage(
				studentdao.fuzzyQuerySql(true,request.getParameter("fuzzyName"), true)),1,"获取数据成功");
		else if(mode.equals("fuzzyNumber")) rb = new ResponseBean(studentdao.getTotalPage(
				studentdao.fuzzyQuerySql(false,request.getParameter("fuzzyNumber"), true)),1,"获取数据成功");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(JSON.toJSONString(rb));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
