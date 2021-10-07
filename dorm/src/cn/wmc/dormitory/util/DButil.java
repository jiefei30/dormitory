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
	//��ȡ���ӳ��е�����
	public static Connection getConn() {
		Connection con =null;
		Context ctx;
		try {
			ctx = new InitialContext();  //��ȡcontext.xml�е�����
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/dormitoryDBPC");  //��ȡ����Դ
			try {
				con = ds.getConnection();  //�õ�����
				con.setAutoCommit(false);
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("��ȡ���Ӵ���");
			}
		} catch (NamingException e2) {
			e2.printStackTrace();
			System.out.println("��ȡ����Դ����");
		}
		return con;
	}
	//�ͷ�����
	public static int closeConn(Connection con) {
		int flag = -1;
		try {
			if(con!=null)con.close();
			flag=0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�ͷ����Ӵ���");
		}
		
		return flag;
	}
	//��ȡ PreparedStatement
	public static PreparedStatement getPrestmt(Connection con,String sql,Object... params) {
		PreparedStatement stmt=null;
		try {
			stmt = con.prepareStatement(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("�õ�prepareStatement����");
		}
		if(params.length!=0)
			for(int i=0;i<params.length;i++) {
			try {
				stmt.setObject(i+1, params[i]);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("��prepareStatement����");
			}
		}
		return stmt;
	}
	//�ر� PreparedStatement
	public static int closePrestmt(PreparedStatement stmt) {
		int flag = -1;
		try {
			if(stmt!=null)stmt.close();
			flag=0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�ر�PreparedStatement����");
		}
		
		return flag;
	}
	//��ý����
	public static ResultSet getRest(PreparedStatement stmt) {
		ResultSet rest = null;
		try {
			rest = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�õ�ResultSet����");
		}
		return rest;
	}
	//�رս����
	public static int closeRest(ResultSet rest) {
		int flag = -1;
		try {
			if(rest!=null)rest.close();
			flag=0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�ر�PreparedStatement����");
		}
		
		return flag;
	}
	//��ý������һ��ֵ
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
			System.out.println("��ȡ�����ֵ����");
		}
		closeAll(con, stmt, rest);
		return value;
	}
	//�ر�����
	public static void closeAll(Connection con,PreparedStatement stmt,ResultSet rest) {
		closeRest(rest);
		closePrestmt(stmt);
		closeConn(con);	
	}
	
	//д����
	public static int write(String sql, Object... params) {
		int flag = 0;
		PreparedStatement stmt=null;
		Connection con =null;
		con = getConn();
		stmt = getPrestmt(con, sql, params);
		try {
			stmt.execute();
			con.commit();    //�ύ
		} catch (SQLException e) {
			try {
				con.rollback();   //���ִ���ع�
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("con�ع���������");
				flag =1;
			}
			flag =2;
			e.printStackTrace();
			System.out.println("stmtִ�з�������");
		}finally{
			closePrestmt(stmt);
			closeConn(con);
		}
		return flag;
	}
	//д����
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
				con.commit();    //�ύ
			} catch (SQLException e) {
				try {
					con.rollback();   //���ִ���ع�
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("con�ع���������");
					flag =1;
				}
				flag =2;
				e.printStackTrace();
				System.out.println("stmtִ�з�������");
			}finally{
				for(int i=0;i<stmt.length;i++) {
					closePrestmt(stmt[i]);			
				}
				closeConn(con);
			}
			return flag;
		}
	//������
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
