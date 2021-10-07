package cn.wmc.dormitory.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DButil {
	//获取连接池中的链接
	public static Connection getConn() {
		Connection con =null;
		Context ctx;
		try {
			ctx = new InitialContext();  //获取context.xml中的内容
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/dormitoryDBPC");  //获取数据源
			try {
				con = ds.getConnection();  //得到连接
				con.setAutoCommit(false);
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("获取连接错误");
			}
		} catch (NamingException e2) {
			e2.printStackTrace();
			System.out.println("获取数据源错误");
		}
		return con;
	}
	//释放连接
	public static int closeConn(Connection con) {
		int flag = -1;
		try {
			if(con!=null)con.close();
			flag=0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("释放连接错误");
		}
		
		return flag;
	}
	//获取 PreparedStatement
	public static PreparedStatement getPrestmt(Connection con,String sql,Object... params) {
		PreparedStatement stmt=null;
		try {
			stmt = con.prepareStatement(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("得到prepareStatement错误");
		}
		if(params.length!=0)
			for(int i=0;i<params.length;i++) {
			try {
				stmt.setObject(i+1, params[i]);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("给prepareStatement错误");
			}
		}
		return stmt;
	}
	//关闭 PreparedStatement
	public static int closePrestmt(PreparedStatement stmt) {
		int flag = -1;
		try {
			if(stmt!=null)stmt.close();
			flag=0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("关闭PreparedStatement错误");
		}
		
		return flag;
	}
	//获得结果集
	public static ResultSet getRest(PreparedStatement stmt) {
		ResultSet rest = null;
		try {
			rest = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("得到ResultSet错误");
		}
		return rest;
	}
	//关闭结果集
	public static int closeRest(ResultSet rest) {
		int flag = -1;
		try {
			if(rest!=null)rest.close();
			flag=0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("关闭PreparedStatement错误");
		}
		
		return flag;
	}
	//获得结果集的一个值
	public static Object getRestValue(String sql, Object... params) {
		PreparedStatement stmt=null;
		Connection con =null;
		ResultSet rest=null;
		con = getConn();
		stmt = getPrestmt(con, sql, params);
		try {
			rest=stmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Object value = null;
		try {
			rest.next();
			value = rest.getObject(1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("获取结果集值错误");
		}
		closeAll(con, stmt, rest);
		return value;
	}
	//关闭所有
	public static void closeAll(Connection con,PreparedStatement stmt,ResultSet rest) {
		closeRest(rest);
		closePrestmt(stmt);
		closeConn(con);	
	}
	
	//写操作
	public static int write(String sql, Object... params) {
		int flag = 0;
		PreparedStatement stmt=null;
		Connection con =null;
		con = getConn();
		stmt = getPrestmt(con, sql, params);
		try {
			stmt.execute();
			con.commit();    //提交
		} catch (SQLException e) {
			try {
				con.rollback();   //出现错误回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("con回滚发生错误");
				flag =1;
			}
			flag =2;
			e.printStackTrace();
			System.out.println("stmt执行发生错误");
		}finally{
			closePrestmt(stmt);
			closeConn(con);
		}
		return flag;
	}
	//写操作
		public static int write(String[] sql, Object[][] params) {
			int flag = 0;
			PreparedStatement[] stmt=new PreparedStatement[sql.length];
			Connection con =null;
			con = getConn();		
			try {
				for(int i=0;i<sql.length;i++) {
					stmt[i] = getPrestmt(con, sql[i], params[i]);	
					stmt[i].execute();
				}
				con.commit();    //提交
			} catch (SQLException e) {
				try {
					con.rollback();   //出现错误回滚
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("con回滚发生错误");
					flag =1;
				}
				flag =2;
				e.printStackTrace();
				System.out.println("stmt执行发生错误");
			}finally{
				for(int i=0;i<stmt.length;i++) {
					closePrestmt(stmt[i]);			
				}
				closeConn(con);
			}
			return flag;
		}
	//读操作
		public static ArrayList<Object[]> read(String sql,int col,Object... params) {
			PreparedStatement stmt=null;
			Connection con =null;
			ResultSet rest=null;
			con = getConn();
			stmt = getPrestmt(con, sql, params);
			
			try {
				rest=stmt.executeQuery();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ArrayList<Object[]> res = new ArrayList<Object[]>();
			try {
				while(rest.next()) {
					Object[] temp = new Object[col];
					for(int i =0;i<col;i++) {
						temp[i] = rest.getObject(i+1);
					}
					res.add(temp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll(con, stmt, rest);
			return res;
		}
		
}
