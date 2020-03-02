package com.bn.Datebase;

import java.util.List;

import com.bn.util.StrListChange;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;
import java.util.ArrayList;

public class DBUtil {
	static  ComboPooledDataSource dataSource;
	static
	{
		//���ӳ�����Դ
		dataSource=new ComboPooledDataSource();
		//�����û���
		dataSource.setUser("root");
		//���ݿ�����
		dataSource.setPassword("");
		//���ݿ�����URL
		dataSource.setJdbcUrl("jdbc:mysql://localhost/jjsq?useUnicode=true&characterEncoding=utf8");
		//���ݿ���������
		try{dataSource.setDriverClass("org.gjt.mm.mysql.Driver");}catch(Exception e){e.printStackTrace();}
		//��ʼ����С
		dataSource.setInitialPoolSize(5);
		//��С�ش�С
		dataSource.setMinPoolSize(1);
		//���ش�С
		dataSource.setMaxPoolSize(10);
		
		dataSource.setMaxStatements(50);
		dataSource.setMaxIdleTime(60);
		
	}
	
	//�����ӳ��л�ȡһ������
	public static Connection getConnection() throws Exception
	{
		return dataSource.getConnection();
	}

	//����
	
	//��ȡ��¼��������
	public static String getAdministrator(String ms){
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		String sql=null;
		String ss="";
		try
		{
			con=getConnection();
			st=con.createStatement();
			sql="select * from administrator where administrator_name='"+ms+"'";
			rs=st.executeQuery(sql);
			while(rs.next())
			{
				ss=rs.getString(1)+"<#>"+rs.getString(2);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{rs.close();}catch(Exception e){e.printStackTrace();}
			try{st.close();}catch(Exception e){e.printStackTrace();}
			try{con.close();}catch(Exception e){e.printStackTrace();}
		}
		return ss;
	
	}
	
	public static void insertManager(String[] ms){//���ӹ���Ա
    	Connection con=null;
    	Statement st=null;
    	String sql=null;
    	List<String[]> lstr=new ArrayList<String[]>();
    	try{
    		con=getConnection();
    		st=con.createStatement();
    	    sql="insert into administrator (administrator_name,administrator_password)values('"+ms[0]+"','"+ms[1]+"')";
    		st.execute(sql);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try{st.close();}catch(Exception e){e.printStackTrace();}
    		try{con.close();}catch(Exception e){e.printStackTrace();}
    	}
//    	return lstr;
    }
    //��ȡ���й���Ա
    public static List<String[]> GetAllManager() 
    {
   	     Connection con = null;
		 Statement st = null;
		 ResultSet rs = null;
		 String sql = null;
		 List<String[]> ls=new ArrayList<String[]>();
		 try 
		 {
			 con = getConnection();
			 st=con.createStatement();
			 sql="select administrator_name,administrator_password from administrator ";
			 rs=st.executeQuery(sql);
			 while(rs.next())
			 {
				 String[] str=new String[2];
				 str[0]=rs.getString(1);
				 str[1]=rs.getString(2);		 
				 ls.add(str); 
			 }
		 }catch(Exception e) {
				e.printStackTrace();
		 }
		 finally
		 {
			 try{rs.close();}catch(SQLException e){e.printStackTrace();}
			 try{st.close();}catch(SQLException e){e.printStackTrace();}
			 try{con.close();}catch(SQLException e){e.printStackTrace();}
		 }
   	 return ls;
    }  
   //����Ա�޸İ�ť���
    public static List<String[]> GetManagerByID(String[] ms) 
    {
   	     Connection con = null;
		 Statement st = null;
		 ResultSet rs = null;
		 String sql = null;
		 List<String[]> ls=new ArrayList<String[]>();
		 try 
		 {
			 con = getConnection();
			 st=con.createStatement();
			 sql="select administrator_name,administrator_password from administrator where administrator_name='"+ms[0]+"'";
			 rs=st.executeQuery(sql);
			 while(rs.next())
			 {
				 String[] str=new String[2];
				 str[0]=rs.getString(1);
				 str[1]=rs.getString(2);
				 ls.add(str); 
			 }
		 }catch(Exception e) 
		 {
				e.printStackTrace();
		 }
		 finally{
			 try{rs.close();}catch(SQLException e){e.printStackTrace();}
			 try{st.close();}catch(SQLException e){e.printStackTrace();}
			 try{con.close();}catch(SQLException e){e.printStackTrace();}
		 }
   	 return ls;
    }
    //���¹���Ա����
    public static void UpdateManagerByButton(String[] array)
    {
   	     Connection con = null;
		 Statement st = null;
		 String sql = null;
		 try 
		 {
			 con = getConnection();
			 st=con.createStatement();
			 sql="update administrator set administrator_password='"+array[1]+"' where administrator_name='"+array[0]+"'";
			 st.execute(sql); 
		 }catch(Exception e) 
		 {
				e.printStackTrace();
		 }
		 finally
		 {
			 try{st.close();}catch(Exception e) {e.printStackTrace();}	
			 try{con.close();}catch(Exception e) {e.printStackTrace();}
		 }
    }
	//�����û�id��ȡ�û��ֻ���
	public static String getUserTel(String num){
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		String sql=null;
		String tel="";
		try{
			con=getConnection();
			st=con.createStatement();
			sql="select user_tel from user where user_id='"+num+"';";
			rs=st.executeQuery(sql);
			while(rs.next()){
				tel=rs.getString(1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			try{rs.close();}catch(Exception e){e.printStackTrace();}
			try{st.close();}catch(Exception e){e.printStackTrace();}
			try{con.close();}catch(Exception e){e.printStackTrace();}
		}
		return tel;
	}
	
	
		//�õ����е�������
		public static List<String[]> getdpName(){
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	List<String[]> lstr=new ArrayList<String[]>();
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="select store_name from store ORDER BY CONVERT(store_name USING gbk)";
	    		rs=st.executeQuery(sql);
	    	    while(rs.next()){
	    		 String[] str=new String [1];
	    		 str[0]=rs.getString(1);
	    		 lstr.add(str);
	    	    }
	    		
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	return lstr;
		} 
		
		//�õ����е�����Ϣ
		public static List<String[]> getAlldpXx() {
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	List<String[]> lstr=new ArrayList<String[]>();
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="select store_id,store_name,store_address,store_introduce,store_delete from store";
	    		rs=st.executeQuery(sql);
	    	    while(rs.next()){
	    		 String[] str=new String [5];
	    		 str[0]=rs.getString(1);
	    		 str[1]=rs.getString(2);
	    		 str[2]=rs.getString(3);
	    		 str[3]=rs.getString(4);
	    		 str[4]=rs.getString(5);
	    		 lstr.add(str);
	    		
	    	    }
	    		
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	return lstr;
		}
		
		//�õ����ֵ�����Ϣ
				public static List<String[]> getSectiondpXx(String ms) {
							
					Connection con=null;
					Statement st=null;
					ResultSet rs=null;
					String sql="";
					List<String[]> lstr=new ArrayList<String[]>();
					    try{
					    	con = getConnection();
					    	st=con.createStatement();
					    	sql="select distinct store_id,store_name,store_address,store_introduce,store_delete from store"
					    	+" where store_delete='"+ms+"' "; 
					    	rs=st.executeQuery(sql);
					    	  while(rs.next()){
					    		 String[] str=new String [5];
					    		 str[0]=rs.getString(1);
					    		 str[1]=rs.getString(2);
					    		 str[2]=rs.getString(3);
					    		 str[3]=rs.getString(4);
					    		 str[4]=rs.getString(5);
					    		 lstr.add(str); 
					    	    }
					    		
					    	}catch(Exception e)
					    	{
					    		e.printStackTrace();
					    	}finally{
					    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
					    		try{st.close();}catch(Exception e){e.printStackTrace();}
					    		try{con.close();}catch(Exception e){e.printStackTrace();}
					    	}
//					    	System.out.println("����DBU����");
					    	return lstr;
						}
				
		//���µ��̱�־λ
				public static void updatedpFlag(String ms) {
					Connection con=null;
			    	Statement st=null;
			    	String sql="";
			    	try{
			    		con = getConnection();
			    		st=con.createStatement();
			    		sql="update store set store_delete='0' where store_id='"+ms+"'";
			    		st.executeUpdate(sql);
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}

				}
				
				//���µ��̱�־λΪ1
				public static void updatedp1Flag(String ms) {
					Connection con=null;
			    	Statement st=null;
			    	String sql="";
			    	try{
			    		con = getConnection();
			    		st=con.createStatement();
			    		sql="update store set store_delete='1' where store_id='"+ms+"'";
			    		st.executeUpdate(sql);
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
				}

		
		//�õ��������̵���ϸ��Ϣ
		public static List<String[]> getdpDetialXx(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	List<String[]> lstr=new ArrayList<String[]>();
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="select store_id,store_name,store_address,store_introduce,store_tel,"
	    				+ "store_delete,store_latitude,store_longtitude, store_pic1,store_pic2 from store"
	    		+" where store_id='"+ms+"'" ;
	    		rs=st.executeQuery(sql);
	    	    while(rs.next()){
	    		 String[] str=new String [10];
	    		 for(int i=0;i<str.length;i++){
	    			 if(rs.getString(i+1)!=null){
	    				 str[i]=rs.getString(i+1);
	    			 }
	    			 }
	    		 lstr.add(str);  
	    	    }
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	return lstr;
		}
		//ͨ���������õ���Ϣ
		public static List<String[]> getSectiondpNameXx(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	List<String[]> lstr=new ArrayList<String[]>();
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="select store_id,store_name,store_address,store_introduce,store_delete from store"
	    		+" where store_name='"+ms+"' ;";
	    		rs=st.executeQuery(sql);
	    	    while(rs.next()){
	    		 String[] str=new String [5];
	    		 str[0]=rs.getString(1);
	    		 str[1]=rs.getString(2);
	    		 str[2]=rs.getString(3);
	    		 str[3]=rs.getString(4);
	    		 str[4]=rs.getString(5);
	    		 lstr.add(str); 
	    	    }
	    		
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	} 
	    	return lstr;
		}
		
		//���µ�����Ϣ
		 public static void updatedpxx(String[] ms){
			 Connection con=null;
		    	Statement st=null;
		    	String sql=null;
		    	for(int i=0;i<ms.length;i++){
		    		
		    		if(ms[i].equals("bgp.png")) 
		    		{
		    			ms[i]=null; 
		    		}
		    		System.out.println(ms[i]);
		    	}
		    	try{
		    		con=getConnection();
		    		st=con.createStatement();
		    		//System.out.println(ms[4]+"*******************************");
		    		sql="update store set store_name='"+ms[1]+"',store_address='"+ms[2]+"',store_introduce='"+ms[3]+"',store_latitude='"+ms[4]+"',store_longtitude='"+ms[5]+"',store_tel='"+ms[6]+"',store_pic1='"+ms[7]+"',store_pic2='"+ms[8]+"' where store_id='"+ms[0]+"' "; 
		    		st.executeUpdate(sql);
		    	}catch(Exception e)
		    	{
		    		e.printStackTrace();
		    	}finally{
		    		try{st.close();}catch(Exception e){e.printStackTrace();}
		    		try{con.close();}catch(Exception e){e.printStackTrace();}
		    	}
		    }
		//���������ID
		 public static String getMaxdpId(){//�õ�������ƷID
		    	Connection con=null;
		    	Statement st=null;
		    	ResultSet rs=null;
		    	String sql=null;
		    	String ss="";
		    	try{
		    		con = getConnection();
		    		st=con.createStatement();
		    	    sql="select max(store_id) from store";
		    		rs=st.executeQuery(sql);
		    		rs.next();
		    		ss=rs.getString(1);
		    
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}finally{
		    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
		    		try{st.close();}catch(Exception e){e.printStackTrace();}
		    		try{con.close();}catch(Exception e){e.printStackTrace();}
		    	}
		    	return ss;
		    }
		
		//���������Ϣ
		public static void insertdpXx(String[] ms) {
			Connection con=null;
	    	Statement st=null;
	    	String sql=null;
	    	for(int i=0;i<ms.length;i++){
	    		
	    		if(ms[i].equals("bgp.png")) 
	    		{
	    			ms[i]=null; 
	    		}
	    		//System.out.println(ms[i]);
	    	}
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    	    sql="insert into store (store_id,store_name,store_address,store_introduce,store_latitude,store_longtitude,store_tel,store_delete,store_pic1,store_pic2)values('"+ms[0]+"','"+ms[1]+"','"+ms[2]+"','"+ms[3]+"','"+ms[4]+"','"+ms[5]+"','"+ms[6]+"','"+ms[7]+"','"+ms[8]+"','"+ms[9]+"')";
	    	    st.execute(sql);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	
	   }
		
		//��ȡ��Ʒ������
		public static List<String[]>getSPName(){ 
			Connection conn=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> ls =new ArrayList<String[]>();
			try {
				conn=getConnection();
				st=conn.createStatement();
				sql="select thing_name from thing";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] strings=new String[1];
					strings[0]=rs.getString(1);
					ls.add(strings);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{rs.close();}catch(Exception e){e.printStackTrace();}
				try{st.close();}catch(Exception e){e.printStackTrace();}
				try{conn.close();}catch(Exception e){e.printStackTrace();}
			}
			return ls;
		}
		
		//��ȡ������Ʒ�Ĳ�����Ϣ
		public static List<String[]>getALLSP(){ 
			Connection conn=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> ls =new ArrayList<String[]>();
			try {
				conn=getConnection();
				st=conn.createStatement();
				sql="select thing_id,thing_name,thing_price,thing_information,thing_count,thing_delete from thing";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] strings=new String[6];
					strings[0]=rs.getString(1);
					strings[1]=rs.getString(2);
					strings[2]=rs.getString(3);
					strings[3]=rs.getString(4);
					strings[4]=rs.getString(5);
					strings[5]=rs.getString(6);
					ls.add(strings);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{rs.close();}catch(Exception e){e.printStackTrace();}
				try{st.close();}catch(Exception e){e.printStackTrace();}
				try{conn.close();}catch(Exception e){e.printStackTrace();}
			}
			return ls;
		}
		public static List<String[]> getSectionspXx(String ms) {//ͨ����־λ��ȡ��Ʒ������Ϣ
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select thing_id,thing_name,thing_price,thing_information,thing_count,thing_delete from thing where thing_delete='"+ms+"'";
			    	rs=st.executeQuery(sql);
			    	  while(rs.next()){
			    		 String[] str=new String [6];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 str[5]=rs.getString(6);
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
//			    	System.out.println("����DBU����");
			    	return lstr;
				}
		public static List<String[]> getSectionspNameXx(String ms) {//ͨ�����ƻ�ȡ��Ʒ������Ϣ
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select thing_id,thing_name,thing_price,thing_information,thing_count,thing_delete from thing where thing_name='"+ms+"'";
			    	rs=st.executeQuery(sql);
			    	//System.out.println("2222222222222");
			    	  while(rs.next()){
			    		 String[] str=new String [6];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 str[5]=rs.getString(6);
			    		 //System.out.println(str[2]+"1111111111");
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
		    		
			    	return lstr;
				}
		public static List<String[]> getSectionspNameXxs(String ms) {//ͨ��ģ�����ƻ�ȡ��Ʒ������Ϣ
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select thing_id,thing_name,thing_price,thing_information,thing_count,thing_delete from thing where thing_name like '%"+ms+"%'";
			    	rs=st.executeQuery(sql);
			    	//System.out.println("2222222222222");
			    	  while(rs.next()){
			    		 String[] str=new String [6];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 str[5]=rs.getString(6);
			    		 //System.out.println(str[2]+"1111111111");
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
		    		
			    	return lstr;
				}
		public static List<String[]> getspDetail(String ms) {//ͨ��ID��ȡ��Ʒ��ϸ��Ϣ
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select * from thing where thing_id='"+ms+"'";
			    	rs=st.executeQuery(sql);
			    	//System.out.println("2222222222222");
			    	  while(rs.next()){
			    		 String[] str=new String [10];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 str[5]=rs.getString(6);
			    		 str[6]=rs.getString(7);
			    		 str[7]=rs.getString(8);
			    		 str[8]=rs.getString(9);
			    		 str[9]=rs.getString(10);
			    		 //System.out.println(str[2]+"1111111111");
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
		    		
			    	return lstr;
				}
	    public static void updatespFlag(String[] ms){//������Ʒ��־λ
	    	Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con=getConnection();
	    		st=con.createStatement();
	    		//System.out.println(ms[0]+ms[1]+"*******************************");
	    		sql="update thing set thing_delete='"+ms[0]+"' where thing_id='"+ms[1]+"'";
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    }
	    public static void updatespXx(String[] ms){
	    	Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con=getConnection();
	    		st=con.createStatement();
	    		System.out.println(ms[4]+"*******************************");
	    		sql="update thing set thing_name='"+ms[1]+"',thing_price='"+ms[3]+"',thing_count='"+ms[2]+"',thing_information='"+ms[4]+"',thing_pic1='"+ms[5]+"',thing_pic2='"+ms[6]+"',thing_pic3='"+ms[7]+"' where thing_id='"+ms[0]+"' "; 
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    }
	    public static String getdpIDByName(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	String string=null;
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		System.out.println(ms);
	    		sql="select store_id from store where store_name='"+ms+"'";
	    		rs=st.executeQuery(sql);
	    		rs.next();
	    		string=rs.getString(1); 
	    		
	    	    
	    		
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	} 
	    	return string;
		}
	    public static String GetMaxspId(){//�õ�������ƷID
	    	Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql=null;
	    	String ss="";
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    	    sql="select max(thing_id)from thing";
	    		rs=st.executeQuery(sql);
	    		rs.next();
	    		ss=rs.getString(1);
	    
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	return ss;
	    }
	    public static void insertspXx(String[] ms) {
			Connection con=null;
	    	Statement st=null;
	    	String sql=null;
	    	for(int i=0;i<ms.length;i++){
	    		
	    		if(ms[i].equals("bgp.png")) 
	    		{
	    			ms[i]=null; 
	    		}
	    		//System.out.println(ms[i]);
	    	}
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    	    sql="insert into thing (thing_id,thing_name,thing_price,thing_information,thing_pic1,thing_pic2,thing_pic3,thing_count,thing_delete,store_id)values('"+ms[0]+"','"+ms[1]+"','"+ms[2]+"','"+ms[3]+"','"+ms[4]+"','"+ms[5]+"','"+ms[6]+"','"+ms[7]+"','"+ms[8]+"','"+ms[9]+"')";
	    	    st.execute(sql);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
			
		}
		public static List<String[]> getAlluser() {
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	List<String[]> lstr=new ArrayList<String[]>();
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="select user_id,user_name,user_tel,user_delete from user";
	    		rs=st.executeQuery(sql);
	    	    while(rs.next()){
	    	    	String [] str=new String [4];
	    	    	str[0]=rs.getString(1);
	    	    	str[1]=rs.getString(2);
	    	    	str[2]=rs.getString(3);
	    	    	str[3]=rs.getString(4);
	    	    	lstr.add(str);
	    	    }
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	return lstr;
		}

		public static List<String[]> getUserForjcb(String[] ms) {
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	List<String[]> lstr=new ArrayList<String[]>();
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="select user_id,user_name,user_tel,user_delete from user where user_delete='"+ms[0]+"'";
	    		rs=st.executeQuery(sql);
	    	    while(rs.next()){
	    	    	String [] str=new String [4];
	    	    	str[0]=rs.getString(1);
	    	    	str[1]=rs.getString(2);
	    	    	str[2]=rs.getString(3);
	    	    	str[3]=rs.getString(4);
	    	    	lstr.add(str);
	    	    }
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	return lstr;
		}

		public static List<String[]> getuserForjtf(String content) {
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	List<String[]> lstr=new ArrayList<String[]>();
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="select user_id,user_name,user_tel,user_delete from user where (user_name like '%"+content+"%' or user_id like '%"+content+"%' or user_tel like  '%"+content+"%')";
	    		rs=st.executeQuery(sql);
	    	    while(rs.next()){
	    	    	String [] str=new String [4];
	    	    	str[0]=rs.getString(1);
	    	    	str[1]=rs.getString(2);
	    	    	str[2]=rs.getString(3);
	    	    	str[3]=rs.getString(4);
	    	    	lstr.add(str);
	    	    }
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	return lstr;
		}

		public static List<String[]> getSingleUserXx(String content) {
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	List<String[]> lstr=new ArrayList<String[]>();
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="select user_id,user_name,user_tel,user_delete,user_pic from user where user_id='"+content+"'";
	    		rs=st.executeQuery(sql);
	    	    while(rs.next()){
	    	    	String [] str=new String [5];
	    	    	str[0]=rs.getString(1);
	    	    	str[1]=rs.getString(2);
	    	    	str[2]=rs.getString(3);
	    	    	str[3]=rs.getString(4);
	    	    	str[4]=rs.getString(5);
	    	    	lstr.add(str);
	    	    }
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	return lstr;
		}

		public static void Updateuserflag(String[] array) {
			Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		if(array[1].equals("1"))
	    		{
	    			sql="update user set user_delete='1' where user_id='"+array[0]+"'";
	    		}else{
	    			sql="update user set user_delete='0' where user_id='"+array[0]+"'";
	    		}
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
		}

		public static void updatePassword(String[] array) {
			Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="update user set user_password='"+array[1]+"' where user_id='"+array[0]+"'";
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
		}
		
		
				public static List<String[]> getAllADXx() {
					Connection con=null;
			    	Statement st=null;
			    	ResultSet rs=null;
			    	String sql="";
			    	List<String[]> lstr=new ArrayList<String[]>();
			    	try{
			    		con = getConnection();
			    		st=con.createStatement();
			    		sql="select ad_id,ad_name,ad_text,ad_delete from ad";
			    		rs=st.executeQuery(sql);
			    	    while(rs.next()){
			    		 String[] str=new String [4];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 lstr.add(str);
			    		
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
			    	return lstr;
				}
				
				
						public static List<String[]> getSectionADXx(String ms) {
									
							Connection con=null;
							Statement st=null;
							ResultSet rs=null;
							String sql="";
							List<String[]> lstr=new ArrayList<String[]>();
							    try{
							    	con = getConnection();
							    	st=con.createStatement();
							    	sql="select distinct ad_id,ad_name,ad_text,ad_delete from ad"
							    	+" where ad_delete='"+ms+"' "; 
							    	rs=st.executeQuery(sql);
							    	  while(rs.next()){
							    		 String[] str=new String [4];
							    		 str[0]=rs.getString(1);
							    		 str[1]=rs.getString(2);
							    		 str[2]=rs.getString(3);
							    		 str[3]=rs.getString(4);
							    		 lstr.add(str); 
							    	    }
							    		
							    	}catch(Exception e)
							    	{
							    		e.printStackTrace();
							    	}finally{
							    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
							    		try{st.close();}catch(Exception e){e.printStackTrace();}
							    		try{con.close();}catch(Exception e){e.printStackTrace();}
							    	}
//							    	System.out.println("����DBU����");
							    	return lstr;
								}
						
			
						public static void updateADFlag(String ms) {
							Connection con=null;
					    	Statement st=null;
					    	String sql="";
					    	try{
					    		con = getConnection();
					    		st=con.createStatement();
					    		sql="update ad set ad_delete='0' where ad_id='"+ms+"'";
					    		st.executeUpdate(sql);
					    	}catch(Exception e)
					    	{
					    		e.printStackTrace();
					    	}finally{
					    		try{st.close();}catch(Exception e){e.printStackTrace();}
					    		try{con.close();}catch(Exception e){e.printStackTrace();}
					    	}

						}
						
					
						public static void updateAD1Flag(String ms) {
							Connection con=null;
					    	Statement st=null;
					    	String sql="";
					    	try{
					    		con = getConnection();
					    		st=con.createStatement();
					    		sql="update ad set ad_delete='1' where ad_id='"+ms+"'";
					    		st.executeUpdate(sql);
					    	}catch(Exception e)
					    	{
					    		e.printStackTrace();
					    	}finally{
					    		try{st.close();}catch(Exception e){e.printStackTrace();}
					    		try{con.close();}catch(Exception e){e.printStackTrace();}
					    	}
						}

				
				
				public static List<String[]> getADDetialXx(String ms) {
					Connection con=null;
			    	Statement st=null;
			    	ResultSet rs=null;
			    	String sql="";
			    	List<String[]> lstr=new ArrayList<String[]>();
			    	try{
			    		con = getConnection();
			    		st=con.createStatement();
			    		sql="select ad_id,ad_name,ad_text,"
			    				+ "ad_delete,ad_pic1,ad_pic2 from ad"
			    		+" where ad_id='"+ms+"'" ;
			    		rs=st.executeQuery(sql);
			    	    while(rs.next()){
			    		 String[] str=new String [6];
			    		 for(int i=0;i<str.length;i++){
			    			 if(rs.getString(i+1)!=null){
			    				 str[i]=rs.getString(i+1);
			    			 }
			    			 }
			    		 lstr.add(str);  
			    	    }
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
			    	return lstr;
				}
				
				
				 public static void updateADxx(String[] ms){
					 Connection con=null;
				    	Statement st=null;
				    	String sql=null;
				    	for(int i=0;i<ms.length;i++){
				    		
				    		if(ms[i].equals("bgp.png")) 
				    		{
				    			ms[i]=null; 
				    		}
				    		System.out.println(ms[i]);
				    	}
				    	try{
				    		con=getConnection();
				    		st=con.createStatement();
				    		//System.out.println(ms[4]+"*******************************");
				    		sql="update ad set ad_name='"+ms[1]+"',ad_text='"+ms[2]+"',ad_pic1='"+ms[3]+"',ad_pic2='"+ms[4]+"' where ad_id='"+ms[0]+"' "; 
				    		st.executeUpdate(sql);
				    	}catch(Exception e)
				    	{
				    		e.printStackTrace();
				    	}finally{
				    		try{st.close();}catch(Exception e){e.printStackTrace();}
				    		try{con.close();}catch(Exception e){e.printStackTrace();}
				    	}
				    }
				//���������ID
				 public static String getMaxADId(){
				    	Connection con=null;
				    	Statement st=null;
				    	ResultSet rs=null;
				    	String sql=null;
				    	String ss="";
				    	try{
				    		con = getConnection();
				    		st=con.createStatement();
				    	    sql="select max(ad_id)from ad";
				    		rs=st.executeQuery(sql);
				    		rs.next();
				    		ss=rs.getString(1);
				    
				    	}catch(Exception e){
				    		e.printStackTrace();
				    	}finally{
				    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
				    		try{st.close();}catch(Exception e){e.printStackTrace();}
				    		try{con.close();}catch(Exception e){e.printStackTrace();}
				    	}
				    	return ss;
				    }
				
				//���������Ϣ
				public static void insertADXx(String[] ms) {
					Connection con=null;
			    	Statement st=null;
			    	String sql=null;
			    	for(int i=0;i<ms.length;i++){
			    		
			    		if(ms[i].equals("bgp.png")) 
			    		{
			    			ms[i]=null; 
			    		}
			    		//System.out.println(ms[i]);
			    	}
			    	try{
			    		con = getConnection();
			    		st=con.createStatement();
			    	    sql="insert into AD (ad_id,ad_name,ad_text,ad_delete,ad_pic1,ad_pic2)values('"+ms[0]+"','"+ms[1]+"','"+ms[2]+"','"+ms[3]+"','"+ms[4]+"','"+ms[5]+"')";
			    	    st.execute(sql);
			    	}catch(Exception e){
			    		e.printStackTrace();
			    	}finally{
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
			    	
			   }
				
				public static List<String[]>getADName(){ 
					Connection conn=null;
					Statement st=null;
					ResultSet rs=null;
					String sql="";
					List<String[]> ls =new ArrayList<String[]>();
					try {
						conn=getConnection();
						st=conn.createStatement();
						sql="select ad_name from ad";
						rs=st.executeQuery(sql);
						while(rs.next())
						{
							String[] strings=new String[1];
							strings[0]=rs.getString(1);
							ls.add(strings);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						try{rs.close();}catch(Exception e){e.printStackTrace();}
						try{st.close();}catch(Exception e){e.printStackTrace();}
						try{conn.close();}catch(Exception e){e.printStackTrace();}
					}
					return ls;
				}
				

				public static List<String[]> getSectiontzXx(String ms) {
							
					Connection con=null;
					Statement st=null;
					ResultSet rs=null;
					String sql="";
					List<String[]> lstr=new ArrayList<String[]>();
					    try{
					    	con = getConnection();
					    	st=con.createStatement();
					    	sql="select distinct post_id,post_text,post_date,post_likecount,post_delete from post"
					    	+" where post_delete='"+ms+"' "; 
					    	rs=st.executeQuery(sql);
					    	System.out.println(rs);
					    	  while(rs.next()){
					    		 String[] str=new String [5];
					    		 str[0]=rs.getString(1);
					    		 str[1]=rs.getString(2);
					    		 str[2]=rs.getString(3);
					    		 str[3]=rs.getString(4);
					    		 str[4]=rs.getString(5);
					    		 lstr.add(str); 
					    	    }
					    		
					    	}catch(Exception e)
					    	{
					    		e.printStackTrace();
					    	}finally{
					    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
					    		try{st.close();}catch(Exception e){e.printStackTrace();}
					    		try{con.close();}catch(Exception e){e.printStackTrace();}
					    	}
					    	//System.out.println("����DBU����");
					    	return lstr;
						}
				
	
				public static void updatetzFlag(String ms) {
					Connection con=null;
			    	Statement st=null;
			    	String sql="";
			    	try{
			    		con = getConnection();
			    		st=con.createStatement();
			    		sql="update post set post_delete='0' where post_id='"+ms+"'";
			    		st.executeUpdate(sql);
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}

				}
				
			
				public static void updatetz1Flag(String ms) {
					Connection con=null;
			    	Statement st=null;
			    	String sql="";
			    	try{
			    		con = getConnection();
			    		st=con.createStatement();
			    		sql="update post set post_delete='1' where post_id='"+ms+"'";
			    		st.executeUpdate(sql);
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
				}

		
		
		public static List<String[]> gettzDetailXx(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	List<String[]> lstr=new ArrayList<String[]>();
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="select post_id,user_id,post_text,post_date,post_likecount,"
	    				+ "post_delete,post_pic1 from post"
	    		+" where post_id='"+ms+"'" ;
	    		rs=st.executeQuery(sql);
	    		System.out.println(rs);
	    	    while(rs.next()){
	    		 String[] str=new String [8];
	    		 for(int i=0;i<str.length;i++){
	    			 if(rs.getString(i+1)!=null){
	    				 str[i]=rs.getString(i+1);
	    			 }
	    			 }
	    		 lstr.add(str);  
	    	    }
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	System.out.println("����DBU����");
	    	return lstr;
		}
		
		public static List<String[]> getSectionplXx(String ms) {
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select distinct discuss_num,post_id,user_id,discuss_text,discuss_time,discuss_delete from discuss"
			    	+" where discuss_delete='"+ms+"' "; 
			    	rs=st.executeQuery(sql);
			    	  while(rs.next()){
			    		 String[] str=new String [6];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 str[5]=rs.getString(6);
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
//			    	System.out.println("����DBU����");
			    	return lstr;
				}
		

		public static void updateplFlag(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="update discuss set discuss_delete='0' where discuss_num='"+ms+"'";
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}

		}
		
		public static void updatepl1Flag(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="update discuss set discuss_delete='1' where discuss_num='"+ms+"'";
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
		}
		
       public static List<String[]> getSectionwtXx(String ms) {
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select distinct question_id,question_title,question_text,question_date,question_delete from question"
			    	+" where question_delete='"+ms+"' "; 
			    	rs=st.executeQuery(sql);
			    	  while(rs.next()){
			    		 String[] str=new String [5];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
//			    	System.out.println("����DBU����");
			    	return lstr;
				}
		

		public static void updatewtFlag(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="update question set question_delete='0' where question_id='"+ms+"'";
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}

		}
		
		public static void updatewt1Flag(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="update question set question_delete='1' where question_id='"+ms+"'";
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
		}
        public static List<String[]> getwtDetail(String ms) {//ͨ��ID��ȡ��Ʒ��ϸ��Ϣ
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select * from question where question_id='"+ms+"'";
			    	rs=st.executeQuery(sql);
			    	//System.out.println("2222222222222");
			    	  while(rs.next()){
			    		 String[] str=new String [7];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 str[5]=rs.getString(6);
			    		 str[6]=rs.getString(7);
			    		 //System.out.println(str[2]+"1111111111");
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
		    		
			    	return lstr;
				}
        
       public static List<String[]> getSectionhdXx(String ms) {
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select distinct answer_id,question_id,answer_text,answer_date,answer_delete from answer"
			    	+" where answer_delete='"+ms+"' "; 
			    	rs=st.executeQuery(sql);
			    	  while(rs.next()){
			    		 String[] str=new String [5];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
//			    	System.out.println("����DBU����");
			    	return lstr;
				}
		

		public static void updatehdFlag(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="update answer set answer_delete='0' where answer_id='"+ms+"'";
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}

		}
		
		
		public static void updatehd1Flag(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		sql="update answer set answer_delete='1' where answer_id='"+ms+"'";
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
		}
        public static List<String[]> gethdDetail(String ms) {
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select * from answer where answer_id='"+ms+"'";
			    	rs=st.executeQuery(sql);
			    	//System.out.println("2222222222222");
			    	  while(rs.next()){
			    		 String[] str=new String [7];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 str[5]=rs.getString(6);
			    		 str[6]=rs.getString(7);
			    		 //System.out.println(str[2]+"1111111111");
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
		    		
			    	return lstr;
				}
        
    	//��ȡ��˾������
  		public static List<String[]>getGSName(){ 
  			Connection conn=null;
  			Statement st=null;
  			ResultSet rs=null;
  			String sql="";
  			List<String[]> ls =new ArrayList<String[]>();
  			try {
  				conn=getConnection();
  				st=conn.createStatement();
  				sql="select company_name from company";
  				rs=st.executeQuery(sql);
  				while(rs.next())
  				{
  					String[] strings=new String[1];
  					strings[0]=rs.getString(1);
  					ls.add(strings);
  				}
  			} catch (Exception e) {
  				e.printStackTrace();
  			}finally {
  				try{rs.close();}catch(Exception e){e.printStackTrace();}
  				try{st.close();}catch(Exception e){e.printStackTrace();}
  				try{conn.close();}catch(Exception e){e.printStackTrace();}
  			}
  			return ls;
  		}
  		
  		//��ȡ���й�˾�Ĳ�����Ϣ
  		public static List<String[]>getALLGS(){ 
  			Connection conn=null;
  			Statement st=null;
  			ResultSet rs=null;
  			String sql="";
  			List<String[]> ls =new ArrayList<String[]>();
  			try {
  				conn=getConnection();
  				st=conn.createStatement();
  				sql="select company_id,company_name,company_tel,company_address,company_introduce,company_delete from company";
  				rs=st.executeQuery(sql);
  				while(rs.next())
  				{
  					String[] strings=new String[6];
  					strings[0]=rs.getString(1);
  					strings[1]=rs.getString(2);
  					strings[2]=rs.getString(3);
  					strings[3]=rs.getString(4);
  					strings[4]=rs.getString(5);
  					strings[5]=rs.getString(6);
  					ls.add(strings);
  				}
  			} catch (Exception e) {
  				e.printStackTrace();
  			}finally {
  				try{rs.close();}catch(Exception e){e.printStackTrace();}
  				try{st.close();}catch(Exception e){e.printStackTrace();}
  				try{conn.close();}catch(Exception e){e.printStackTrace();}
  			}
  			return ls;
  		}
  		public static List<String[]> getSectiongsXx(String ms) {//ͨ����־λ��ȡ��˾������Ϣ
  			
  			Connection con=null;
  			Statement st=null;
  			ResultSet rs=null;
  			String sql="";
  			List<String[]> lstr=new ArrayList<String[]>();
  			    try{
  			    	con = getConnection();
  			    	st=con.createStatement();
  			    	sql="select company_id,company_name,company_tel,company_address,company_introduce,company_delete from company where company_delete='"+ms+"'";
  			    	rs=st.executeQuery(sql);
  			    	  while(rs.next()){
  			    		 String[] str=new String [6];
  			    		 str[0]=rs.getString(1);
  			    		 str[1]=rs.getString(2);
  			    		 str[2]=rs.getString(3);
  			    		 str[3]=rs.getString(4);
  			    		 str[4]=rs.getString(5);
  			    		 str[5]=rs.getString(6);
  			    		 lstr.add(str); 
  			    	    }
  			    		
  			    	}catch(Exception e)
  			    	{
  			    		e.printStackTrace();
  			    	}finally{
  			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
  			    		try{st.close();}catch(Exception e){e.printStackTrace();}
  			    		try{con.close();}catch(Exception e){e.printStackTrace();}
  			    	}
//  			    	System.out.println("����DBU����");
  			    	return lstr;
  				}
  		public static List<String[]> getSectiongsNameXx(String ms) {//ͨ�����ƻ�ȡ��˾������Ϣ
  			
  			Connection con=null;
  			Statement st=null;
  			ResultSet rs=null;
  			String sql="";
  			List<String[]> lstr=new ArrayList<String[]>();
  			    try{
  			    	con = getConnection();
  			    	st=con.createStatement();
  			    	sql="select company_id,company_name,company_tel,company_address,company_introduce,company_delete from company where company_name='"+ms+"'";
  			    	rs=st.executeQuery(sql);
  			    	//System.out.println("2222222222222");
  			    	  while(rs.next()){
  			    		 String[] str=new String [6];
  			    		 str[0]=rs.getString(1);
  			    		 str[1]=rs.getString(2);
  			    		 str[2]=rs.getString(3);
  			    		 str[3]=rs.getString(4);
  			    		 str[4]=rs.getString(5);
  			    		 str[5]=rs.getString(6);
  			    		 //System.out.println(str[2]+"1111111111");
  			    		 lstr.add(str); 
  			    	    }
  			    		
  			    	}catch(Exception e)
  			    	{
  			    		e.printStackTrace();
  			    	}finally{
  			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
  			    		try{st.close();}catch(Exception e){e.printStackTrace();}
  			    		try{con.close();}catch(Exception e){e.printStackTrace();}
  			    	}
  		    		
  			    	return lstr;
  				}
  		public static List<String[]> getSectiongsNameXxs(String ms) {//ͨ��ģ�����ƻ�ȡ��˾������Ϣ
  			
  			Connection con=null;
  			Statement st=null;
  			ResultSet rs=null;
  			String sql="";
  			List<String[]> lstr=new ArrayList<String[]>();
  			    try{
  			    	con = getConnection();
  			    	st=con.createStatement();
  			    	sql="select company_id,company_name,company_tel,company_address,company_introduce,company_delete from company where company_name like '%"+ms+"%'";
  			    	rs=st.executeQuery(sql);
  			    	//System.out.println("2222222222222");
  			    	  while(rs.next()){
  			    		 String[] str=new String [6];
  			    		 str[0]=rs.getString(1);
  			    		 str[1]=rs.getString(2);
  			    		 str[2]=rs.getString(3);
  			    		 str[3]=rs.getString(4);
  			    		 str[4]=rs.getString(5);
  			    		 str[5]=rs.getString(6);
  			    		 //System.out.println(str[2]+"1111111111");
  			    		 lstr.add(str); 
  			    	    }
  			    		
  			    	}catch(Exception e)
  			    	{
  			    		e.printStackTrace();
  			    	}finally{
  			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
  			    		try{st.close();}catch(Exception e){e.printStackTrace();}
  			    		try{con.close();}catch(Exception e){e.printStackTrace();}
  			    	}
  		    		
  			    	return lstr;
  				}
  		public static List<String[]> getgsDetail(String ms) {//ͨ��ID��ȡ��˾��ϸ��Ϣ
  			
  			Connection con=null;
  			Statement st=null;
  			ResultSet rs=null;
  			String sql="";
  			List<String[]> lstr=new ArrayList<String[]>();
  			    try{
  			    	con = getConnection();
  			    	st=con.createStatement();
  			    	sql="select * from company where company_id='"+ms+"'";
  			    	rs=st.executeQuery(sql);
  			    	System.out.println("2222222222222");
  			    	  while(rs.next()){
  			    		 String[] str=new String [9];
  			    		 str[0]=rs.getString(1);
  			    		 str[1]=rs.getString(2);
  			    		 str[2]=rs.getString(3);
  			    		 str[3]=rs.getString(4);
  			    		 str[4]=rs.getString(5);
  			    		 str[5]=rs.getString(6);
  			    		 str[6]=rs.getString(7);
  			    		 str[7]=rs.getString(8);
  			    		 str[8]=rs.getString(9);
  			    		 System.out.println(str[2]+"1111111111");
  			    		 lstr.add(str); 
  			    	    }
  			    		
  			    	}catch(Exception e)
  			    	{
  			    		e.printStackTrace();
  			    	}finally{
  			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
  			    		try{st.close();}catch(Exception e){e.printStackTrace();}
  			    		try{con.close();}catch(Exception e){e.printStackTrace();}
  			    	}
  		    		
  			    	return lstr;
  				}
  	    public static void updategsFlag(String[] ms){//���¹�˾��־λ
  	    	Connection con=null;
  	    	Statement st=null;
  	    	String sql="";
  	    	try{
  	    		con=getConnection();
  	    		st=con.createStatement();
  	    		//System.out.println(ms[0]+ms[1]+"*******************************");
  	    		sql="update company set company_delete='"+ms[0]+"' where company_id='"+ms[1]+"'";
  	    		st.executeUpdate(sql);
  	    	}catch(Exception e)
  	    	{
  	    		e.printStackTrace();
  	    	}finally{
  	    		try{st.close();}catch(Exception e){e.printStackTrace();}
  	    		try{con.close();}catch(Exception e){e.printStackTrace();}
  	    	}
  	    }
  	    public static void updategsXx(String[] ms){//���¹�˾��Ϣ
  	    	Connection con=null;
  	    	Statement st=null;
  	    	String sql="";
  	    	try{
  	    		con=getConnection();
  	    		st=con.createStatement();
  	    		System.out.println(ms[4]+"*******************************");
  	    		sql="update company set company_name='"+ms[1]+"',company_tel='"+ms[2]+"',company_address='"+ms[3]+"',company_introduce='"+ms[4]+"',company_pic1='"+ms[5]+"' where company_id='"+ms[0]+"' "; 
  	    		st.executeUpdate(sql);
  	    	}catch(Exception e)
  	    	{
  	    		e.printStackTrace();
  	    	}finally{
  	    		try{st.close();}catch(Exception e){e.printStackTrace();}
  	    		try{con.close();}catch(Exception e){e.printStackTrace();}
  	    	}
  	    }

  	    
  	    public static String GetMaxgsId(){//�õ����Ĺ�˾ID
  	    	Connection con=null;
  	    	Statement st=null;
  	    	ResultSet rs=null;
  	    	String sql=null;
  	    	String ss="";
  	    	try{
  	    		con = getConnection();
  	    		st=con.createStatement();
  	    	    sql="select max(company_id)from company";
  	    		rs=st.executeQuery(sql);
  	    		rs.next();
  	    		ss=rs.getString(1);
  	    
  	    	}catch(Exception e){
  	    		e.printStackTrace();
  	    	}finally{
  	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
  	    		try{st.close();}catch(Exception e){e.printStackTrace();}
  	    		try{con.close();}catch(Exception e){e.printStackTrace();}
  	    	}
  	    	return ss;
  	    }
  	    public static void insertgsXx(String[] ms) {//��ӹ�˾��Ϣ
  			Connection con=null;
  	    	Statement st=null;
  	    	String sql=null;
  	    	for(int i=0;i<ms.length;i++){
  	    		
  	    		if(ms[i].equals("bgp.png")) 
  	    		{
  	    			ms[i]=null; 
  	    		}
  	    		System.out.println(ms[i]);
  	    	}
  	    	try{
  	    		con = getConnection();
  	    		st=con.createStatement();
  	    	    sql="insert into company (company_id,company_name,company_tel,company_address,company_introduce,company_pic1,company_latitude,company_longtitude,company_delete)values('"+ms[0]+"','"+ms[1]+"','"+ms[2]+"','"+ms[3]+"','"+ms[4]+"','"+ms[5]+"','"+ms[6]+"','"+ms[7]+"','"+ms[8]+"')";
  	    	    st.execute(sql);
  	    	}catch(Exception e){
  	    		e.printStackTrace();
  	    	}finally{
  	    		try{st.close();}catch(Exception e){e.printStackTrace();}
  	    		try{con.close();}catch(Exception e){e.printStackTrace();}
  	    	}
  			
  		}
  	    //��ȡ�����ı���
		public static List<String[]>getCaseTtile(){ 
			Connection conn=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> ls =new ArrayList<String[]>();
			try {
				conn=getConnection();
				st=conn.createStatement();
				sql="select example_title from example";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] strings=new String[1];
					strings[0]=rs.getString(1);
					ls.add(strings);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{rs.close();}catch(Exception e){e.printStackTrace();}
				try{st.close();}catch(Exception e){e.printStackTrace();}
				try{conn.close();}catch(Exception e){e.printStackTrace();}
			}
			return ls;
		}
		
		//��ȡ���а����Ĳ�����Ϣ
		public static List<String[]>getALLCase(){ 
			Connection conn=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> ls =new ArrayList<String[]>();
			try {
				conn=getConnection();
				st=conn.createStatement();
				sql="select example_id,example_title,example_introduce,designer_id,example_delete from example";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] strings=new String[5];
					strings[0]=rs.getString(1);
					strings[1]=rs.getString(2);
					strings[2]=rs.getString(3);
					strings[3]=rs.getString(4);
					strings[4]=rs.getString(5);
					ls.add(strings);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{rs.close();}catch(Exception e){e.printStackTrace();}
				try{st.close();}catch(Exception e){e.printStackTrace();}
				try{conn.close();}catch(Exception e){e.printStackTrace();}
			}
			return ls;
		}
		//ͨ��id��ȡ���ʦ��
 		public static String getdesignerNameByID(String ms) {
			Connection con=null;
	    		Statement st=null;
	    		ResultSet rs=null;
	    		String sql="";
	    		String string=null;
	    		try{
	    			con = getConnection();
	    			st=con.createStatement();
	    			System.out.println(ms);
	    			sql="select designer_name from designer where designer_id='"+ms+"'";
	    			rs=st.executeQuery(sql);
	    			rs.next();
	    			string=rs.getString(1); 
	    		 }catch(Exception e){
	    			e.printStackTrace();
	    		}finally{
	    			try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    			try{st.close();}catch(Exception e){e.printStackTrace();}
	    			try{con.close();}catch(Exception e){e.printStackTrace();}
	    		} 
	    		return string;
		}

		public static List<String[]> getSectionCaseXx(String ms) {//ͨ����־λ��ȡ����������Ϣ
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select example_id,example_title,example_introduce,designer_id,example_delete from example where example_delete='"+ms+"'";
			    	rs=st.executeQuery(sql);
			    	  while(rs.next()){
			    		 String[] str=new String [5];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
//			    	System.out.println("����DBU����");
			    	return lstr;
				}
		public static List<String[]> getSectionCaseTitleXx(String ms) {//ͨ�������ȡ����������Ϣ
  			
  			Connection con=null;
  			Statement st=null;
  			ResultSet rs=null;
  			String sql="";
  			List<String[]> lstr=new ArrayList<String[]>();
  			    try{
  			    	con = getConnection();
  			    	st=con.createStatement();
  			    	sql="select example_id,example_title,example_introduce,designer_id,example_delete from example where example_title='"+ms+"'";
  			    	rs=st.executeQuery(sql);
  			    	//System.out.println("2222222222222");
  			    	  while(rs.next()){
  			    		 String[] str=new String [5];
  			    		 str[0]=rs.getString(1);
  			    		 str[1]=rs.getString(2);
  			    		 str[2]=rs.getString(3);
  			    		 str[3]=rs.getString(4);
  			    		 str[4]=rs.getString(5);
  			    		 //System.out.println(str[2]+"1111111111");
  			    		 lstr.add(str); 
  			    	    }
  			    		
  			    	}catch(Exception e)
  			    	{
  			    		e.printStackTrace();
  			    	}finally{
  			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
  			    		try{st.close();}catch(Exception e){e.printStackTrace();}
  			    		try{con.close();}catch(Exception e){e.printStackTrace();}
  			    	}
  		    		
  			    	return lstr;
  				}
		
		public static List<String[]> getSectionCaseTitleXxs(String ms) {//ͨ��ģ�������ȡ����������Ϣ
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select example_id,example_title,example_introduce,designer_id,example_delete from example where example_title like '%"+ms+"%'";
			    	rs=st.executeQuery(sql);
			    	//System.out.println("2222222222222");
			    	  while(rs.next()){
			    		 String[] str=new String [5];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 //System.out.println(str[2]+"1111111111");
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
		    		
			    	return lstr;
				}
		public static List<String[]> getCaseDetail(String ms) {//ͨ��ID��ȡ������ϸ��Ϣ
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select * from example where example_id='"+ms+"'";
			    	rs=st.executeQuery(sql);
			    	System.out.println("2222222222222");
			    	  while(rs.next()){
			    		 String[] str=new String [11];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 str[5]=rs.getString(6);
			    		 str[6]=rs.getString(7);
			    		 str[7]=rs.getString(8);
			    		 str[8]=rs.getString(9);
			    		 str[9]=rs.getString(10);
			    		 str[10]=rs.getString(11);
			    		 System.out.println(str[2]+"1111111111");
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
		    		
			    	return lstr;
				}
	    public static void updateCaseFlag(String[] ms){//���°�����־λ
	    	Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con=getConnection();
	    		st=con.createStatement();
	    		//System.out.println(ms[0]+ms[1]+"*******************************");
	    		sql="update example set example_delete='"+ms[0]+"' where example_id='"+ms[1]+"'";
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    }
	    public static void updateCaseXx(String[] ms){//���°�����Ϣ
	    	Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		con=getConnection();
	    		st=con.createStatement();
	    		System.out.println(ms[4]+"*******************************");
	    		sql="update example set example_title='"+ms[1]+"',example_introduce='"+ms[2]+"',example_pic1='"+ms[3]+"',example_pic2='"+ms[4]+"',example_pic3='"+ms[5]+"',example_pic4='"+ms[6]+"',example_pic5='"+ms[7]+"' where example_id='"+ms[0]+"' "; 
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    }

	    
	    public static String GetMaxCaseId(){//�õ����İ���ID
	    	Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql=null;
	    	String ss="";
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    	    sql="select max(example_id)from example";
	    		rs=st.executeQuery(sql);
	    		rs.next();
	    		ss=rs.getString(1);
	    
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	return ss;
	    }
	    public static void insertCaseXx(String[] ms) {//��Ӱ�����Ϣ
			Connection con=null;
	    		Statement st=null;
	    		String sql=null;
	    		for(int i=0;i<ms.length;i++){
	    		
	    		if(ms[i].equals("bgp.png")) 
	    		{
	    			ms[i]=null; 
	    		}
	    		System.out.println(ms[i]);
	    	}
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    	    sql="insert into example (example_id,example_title,example_introduce,example_date,example_likecount,example_pic1,example_pic2,example_pic3,example_pic4,example_pic5,example_delete,designer_id)values('"+ms[0]+"','"+ms[1]+"','"+ms[2]+"','"+ms[3]+"','"+ms[4]+"','"+ms[5]+"','"+ms[6]+"','"+ms[7]+"','"+ms[8]+"','"+ms[9]+"','"+ms[10]+"','"+ms[11]+"')";
	    	    st.execute(sql);
	    	    System.out.println("�����ɣ���������������������������");
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
			
		}
	    
	    
	    public static  List<String[]>getDesignerName(){//�õ��������ʦ����
	    	Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql=null;
	    	List<String[]> lstr=new ArrayList<String[]>();
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    	    sql="select designer_name from designer";
	    		rs=st.executeQuery(sql);
	    		while(rs.next())
	    		{
	    			String[] ss=new String[1];
	    			ss[0]=rs.getString(1);
	    			lstr.add(ss);
	    		}
	    
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	return lstr;
	    }
	    public static String getDesignerIDByName(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	String string=null;
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		System.out.println(ms);
	    		sql="select designer_id from designer where designer_name='"+ms+"'";
	    		rs=st.executeQuery(sql);
	    		rs.next();
	    		string=rs.getString(1); 
	    		
	    	    
	    		
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	} 
	    	return string;
		}
    
		public static List<String[]>getALLDesigner(){ 
			Connection conn=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> ls =new ArrayList<String[]>();
			try {
				conn=getConnection();
				st=conn.createStatement();
				sql="select designer_id,designer_name,designer_text,company_id,designer_delete from designer";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] strings=new String[5];
					strings[0]=rs.getString(1);
					strings[1]=rs.getString(2);
					strings[2]=rs.getString(3);
					strings[3]=rs.getString(4);
					strings[4]=rs.getString(5);
					ls.add(strings);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{rs.close();}catch(Exception e){e.printStackTrace();}
				try{st.close();}catch(Exception e){e.printStackTrace();}
				try{conn.close();}catch(Exception e){e.printStackTrace();}
			}
			return ls;
		}
		public static List<String[]>getSectionDesignerXxs(String ms){ 
			Connection conn=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> ls =new ArrayList<String[]>();
			try {
				conn=getConnection();
				st=conn.createStatement();
				sql="select designer_id,designer_name,designer_text,company_id,designer_delete from designer where designer_name like '%"+ms+"%'";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] strings=new String[5];
					strings[0]=rs.getString(1);
					strings[1]=rs.getString(2);
					strings[2]=rs.getString(3);
					strings[3]=rs.getString(4);
					strings[4]=rs.getString(5);
					ls.add(strings);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{rs.close();}catch(Exception e){e.printStackTrace();}
				try{st.close();}catch(Exception e){e.printStackTrace();}
				try{conn.close();}catch(Exception e){e.printStackTrace();}
			}
			return ls;
		}
 		public static String getcompanyNameByID(String ms) {
			Connection con=null;
	    		Statement st=null;
	    		ResultSet rs=null;
	    		String sql="";
	    		String string=null;
	    		try{
	    			con = getConnection();
	    			st=con.createStatement();
	    			System.out.println(ms);
	    			sql="select company_name from company where company_id='"+ms+"'";
	    			rs=st.executeQuery(sql);
	    			rs.next();
	    			string=rs.getString(1); 
	    		 }catch(Exception e){
	    			e.printStackTrace();
	    		}finally{
	    			try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    			try{st.close();}catch(Exception e){e.printStackTrace();}
	    			try{con.close();}catch(Exception e){e.printStackTrace();}
	    		} 
	    		return string;
		}
 		public static List<String[]> getDesignerDetail(String ms) {
			
			Connection con=null;
			Statement st=null;
			ResultSet rs=null;
			String sql="";
			List<String[]> lstr=new ArrayList<String[]>();
			    try{
			    	con = getConnection();
			    	st=con.createStatement();
			    	sql="select * from designer where designer_id='"+ms+"'";
			    	rs=st.executeQuery(sql);
			    	  while(rs.next()){
			    		 String[] str=new String [6];
			    		 str[0]=rs.getString(1);
			    		 str[1]=rs.getString(2);
			    		 str[2]=rs.getString(3);
			    		 str[3]=rs.getString(4);
			    		 str[4]=rs.getString(5);
			    		 str[5]=rs.getString(6);
			    		 lstr.add(str); 
			    	    }
			    		
			    	}catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}finally{
			    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
			    		try{st.close();}catch(Exception e){e.printStackTrace();}
			    		try{con.close();}catch(Exception e){e.printStackTrace();}
			    	}
		    		
			    	return lstr;
		}
	    public static void updatedesignerXx(String[] ms){//������Ʒ��־λ
	    	Connection con=null;
	    	Statement st=null;
	    	String sql="";
	    	try{
	    		
	    		con=getConnection();
	    		st=con.createStatement();
	    		sql="update designer set designer_text='"+ms[1]+"',designer_pic1='"+ms[2]+"',company_id='"+ms[3]+"' where designer_id='"+ms[0]+"' "; 
	    		st.executeUpdate(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    }
	    public static String getCompanyIDByName(String ms) {
			Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql="";
	    	String string=null;
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    		System.out.println(ms);
	    		sql="select company_id from company where company_name='"+ms+"'";
	    		rs=st.executeQuery(sql);
	    		rs.next();
	    		string=rs.getString(1); 
	    		
	    	    
	    		
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	} 
	    	return string;
	    }
	    public static String GetMaxdesignerId(){
	    	Connection con=null;
	    	Statement st=null;
	    	ResultSet rs=null;
	    	String sql=null;
	    	String ss="";
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    	    sql="select max(designer_id)from designer";
	    		rs=st.executeQuery(sql);
	    		rs.next();
	    		ss=rs.getString(1);
	    
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
	    	return ss;
	    }
	    public static void insertDesignerXx(String[] ms) {
			Connection con=null;
	    	Statement st=null;
	    	String sql=null;
	    	for(int i=0;i<ms.length;i++){
	    		
	    		if(ms[i].equals("bgp.png")) 
	    		{
	    			ms[i]=null; 
	    		}
	    		System.out.println(ms[i]);
	    	}
	    	try{
	    		con = getConnection();
	    		st=con.createStatement();
	    	    sql="insert into designer (designer_id,designer_name,designer_text,designer_pic1,company_id)values('"+ms[0]+"','"+ms[1]+"','"+ms[2]+"','"+ms[3]+"','"+ms[4]+"')";
	    	    st.execute(sql);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		try{st.close();}catch(Exception e){e.printStackTrace();}
	    		try{con.close();}catch(Exception e){e.printStackTrace();}
	    	}
			
		}
	    
		
		
		 public static void updateDesignerFlag(String[] ms){//������Ʒ��־λ
		    	Connection con=null;
		    	Statement st=null;
		    	String sql="";
		    	try{
		    		con=getConnection();
		    		st=con.createStatement();
		    		sql="update designer set designer_delete='"+ms[0]+"' where designer_id='"+ms[1]+"'";
		    		st.executeUpdate(sql);
		    	}catch(Exception e)
		    	{
		    		e.printStackTrace();
		    	}finally{
		    		try{st.close();}catch(Exception e){e.printStackTrace();}
		    		try{con.close();}catch(Exception e){e.printStackTrace();}
		    	}
		    }
		 
		 
		 
		 
		 
		 
		//��ȡ�û�����
			public static List<String[]> getUserpassword(String content) {
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				List<String[]> lstr=new ArrayList<String[]>();
				
				String sql=null;
				try{
					con=getConnection();
		    		st=con.createStatement();
		    	    sql="select user_id,user_password from user where user_delete='0' and user_tel='"+content+"';";
		    	    rs=st.executeQuery(sql);
		    		while(rs.next())
		    		{
		    			String[] str=new String[2];
		    			
		    			str[0]=rs.getString(1);
		    			str[1]=rs.getString(2);
		    			lstr.add(str);
		    		}
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}finally{
		    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
		    		try{st.close();}catch(Exception e){e.printStackTrace();}
		    		try{con.close();}catch(Exception e){e.printStackTrace();}
		    	}
		    	return lstr;
			}
			
			public static void insertUser(String content) {//�������û�
				Connection con=null;
		    	Statement st=null;
		    	String[] mess=content.split("<#>");
		    	String sql="";
		    	try{
		    		con=getConnection();
		    		st=con.createStatement();
		    		sql="insert into user(user_id,user_tel,user_name,user_password) "
		    				+ "values('"+getNextUserId()+"','"+mess[0]+"','"+mess[1]+"','"+mess[2]+"');";
		    		st.executeUpdate(sql);
		    	}catch(Exception e)
		    	{
		    		e.printStackTrace();
		    	}finally{
		    		try{st.close();}catch(Exception e){e.printStackTrace();}
		    		try{con.close();}catch(Exception e){e.printStackTrace();}
		    	}
			}
			public static String getNextUserId(){
				String maxid="";
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				String sql="";
				String nextid="";
				try{
					con=getConnection();
					st=con.createStatement();
					sql="select max(user_id) from user";
					rs=st.executeQuery(sql);
					while(rs.next()){
						maxid=rs.getString(1);
					}
					if(maxid==null){
						nextid="100000001";
					}else{
						nextid=String.valueOf(Integer.parseInt(maxid.trim())+1);
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try{rs.close();}catch(Exception e){e.printStackTrace();}
					try{st.close();}catch(Exception e){e.printStackTrace();}
					try{con.close();}catch(Exception e){e.printStackTrace();}
				}
				return nextid;
			}
			public static List<String[]> GetPostListXx() {
				Connection con=null;
				Statement st=null;
				ResultSet rs=null;
				List<String[]> lstr=new ArrayList<String[]>();
				String sql=null;
				try{
					con=getConnection();
		    		st=con.createStatement();
		    	    sql="select post_id,post_text,post_pic1,user_id from post";
		    	    rs=st.executeQuery(sql);
		    		while(rs.next())
		    		{
		    			String[] str=new String[4];
		    			str[0]=rs.getString(1);
		    			str[1]=rs.getString(2);
		    			str[2]=rs.getString(3);
		    			str[3]=getUserXx(rs.getString(4));
		    			lstr.add(str);
		    		}
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}finally{
		    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
		    		try{st.close();}catch(Exception e){e.printStackTrace();}
		    		try{con.close();}catch(Exception e){e.printStackTrace();}
		    	}
		    	return lstr;
			}
			public static String getUserXx(String content) {
				Connection con=null;
				//List<String[]> lstr=new ArrayList<String[]>();
				Statement st=null;
				ResultSet rs=null;
				String sql=null;
		    	String ss="";
		    	try{
		    		con=getConnection();
		    		st=con.createStatement();
		    	    sql="select user_name,user_pic from user where user_id='"+content+"'";
		    	    rs=st.executeQuery(sql);
		    		while(rs.next())
		    		{
		    			ss=rs.getString(1)+"<#>"+rs.getString(2);
		    	
		    		}
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}finally{
		    		try{rs.close();}catch(Exception e){e.printStackTrace();} 
		    		try{st.close();}catch(Exception e){e.printStackTrace();}
		    		try{con.close();}catch(Exception e){e.printStackTrace();}
		    	}
		    	return ss;
			}
		
		
		
		
		
		
		
	
	
	
			
}

