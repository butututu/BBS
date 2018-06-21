package com.lzy.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lzy.db.*;
import java.sql.ResultSet;  
import java.sql.SQLException;
import java.text.SimpleDateFormat; 
public class IndexAction {
	private String url = "./";
	public JsonArray login(String un,String pw){
		String sql=null;
		DBHelper db1 = null;
		ResultSet ret = null;
		int count=0;
		JsonArray user = new JsonArray();
		sql="select * from t_user where username='"+un+"' and password='"+pw+"' and role=3";
		db1=new DBHelper(sql);
		JsonObject ob = new JsonObject();
		try{
            ret = db1.pst.executeQuery();
            while(ret.next()){
            	count++;
            	int id=ret.getInt("id");
            	String username=ret.getString("username");
            	String truename=ret.getString("truename");
            	
            	ob.addProperty("id", Integer.toString(id));
            	ob.addProperty("nickname", truename);
            	ob.addProperty("userid", username);
            	ob.addProperty("tip", "1");
            	ob.addProperty("tips", "login success!");
            	
            }
       
            ret.close();  
            db1.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        } 
		
		 if(count==1){
			 user.add(ob);
			 return user;
         }else {
        	 ob.addProperty("id", " ");
         	ob.addProperty("nickname"," ");
         	ob.addProperty("userid", " ");
         	ob.addProperty("tip", "0");
         	ob.addProperty("tips", "login error!");
         	return user;
         }
	}
	public int register(String rename,String repwd,String rephone,String renikoname) {
		String sql=null;
		DBHelper db = null;
		ResultSet ret = null;
		int count=0;
		
		
		sql="select * from t_user where truename='"+renikoname+"' and deletestatus=0";
		db=new DBHelper(sql);
		try{
            ret = db.pst.executeQuery();  
            while(ret.next()){
            	count+=1;
            }
            ret.close();
            db.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
		sql=null;
		db=null;
		ret=null;
		
		
		sql="select * from t_user where username='"+rename+"' and deletestatus=0";
		db=new DBHelper(sql);
		try{
            ret = db.pst.executeQuery();
            while(ret.next()){
            	count+=2;
            }
            ret.close();
            db.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
		sql=null;
		db=null;
		ret=null;
		

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String nowtime = df.format(new Date());// new Date()为获取当前系统时间
		
		if(count>=1) {
			return count;
		}else {
			int n = 0;
			sql="insert into t_user (createtime,lianxifangshi,password,truename,username,deletestatus,fatieshu,huifushu,role) values ('"+nowtime+"','"+rephone+"','"+repwd+"','"+renikoname+"','"+rename+"',0,0,0,3)";
			db=new DBHelper(sql);
		
			try{
	              
	            n = db.pst.executeUpdate();
	            
	            db.close();
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }
			
			
			if(n==1) {
				return -1;
			}else {
				return 0;
			}
		}
	}
	public JsonArray gettuijiantiezi() {
		String sql=null;
		DBHelper db = null;
		ResultSet ret = null;
		
		int id=0;
		String info=null;
		String title=null;
		JsonArray tiezi = new JsonArray();
		sql="select * from t_tiezi where tuijian='1' and deletestatus=0";
		db=new DBHelper(sql);
		try{
            ret = db.pst.executeQuery();//鎵ц璇彞锛屽緱鍒扮粨鏋滈泦  
            while(ret.next()){
            	id=ret.getInt("id");
            	info=ret.getString("content");
            	title=ret.getString("title");
            	JsonObject ob = new JsonObject();
            	ob.addProperty("id", Integer.toString(id));
            	ob.addProperty("info", info);
            	ob.addProperty("title", title);
            	tiezi.add(ob);
            }
         
            ret.close();
            db.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
	
		return tiezi;
	}
	public JsonArray getbankuai() {
		String sql=null;
		DBHelper db = null;
		ResultSet ret = null;
		
		int id=0;
		int guanliid=0;
		String info=null;
		String bankuaiming=null;
		String bankuaiimagepath=null;
		JsonArray bankuai = new JsonArray();
		sql="select * from t_bankuai where deletestatus=0";
		db=new DBHelper(sql);
		try{
            ret = db.pst.executeQuery();  
            while(ret.next()){
            	id=ret.getInt("id");
            	info=ret.getString("info");
            	bankuaiming=ret.getString("bankuaiming");
            	bankuaiimagepath=ret.getString("bankuaiimagepath");
            	guanliid=ret.getInt("guanliid");
            	JsonObject ob = new JsonObject();
            	ob.addProperty("id", Integer.toString(id));
            	ob.addProperty("info", info);
            	ob.addProperty("title", bankuaiming);
            	//ob.addProperty("bankuaiimagepath", bankuaiimagepath);
            	//ob.addProperty("guanliid", Integer.toString(guanliid));
            	bankuai.add(ob);
            }
         
            ret.close();
            db.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
	
		return bankuai;
	}
	

	
	public JsonArray gettiezineirong (String tieziid) {
		String sql=null;
		DBHelper db = null;
		ResultSet ret = null;
		int tiezi_id=Integer.parseInt(tieziid);
		JsonArray tiezineirong = new JsonArray();
		sql="select title,content,truename from t_tiezi,t_user where t_tiezi.userid=t_user.id and t_tiezi.id="+tiezi_id;
		db=new DBHelper(sql);
		try{
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            while(ret.next()){
            	String title=ret.getString("title");
            	String info=ret.getString("content");
            	String truename=ret.getString("truename");
            	String weizhi="0";
            	JsonObject ob = new JsonObject();
            	ob.addProperty("title", title);
            	ob.addProperty("info", info);
            	ob.addProperty("nickname", truename);
            	ob.addProperty("louceng", weizhi);
            	//ob.addProperty("bankuaiimagepath", bankuaiimagepath);
            	//ob.addProperty("guanliid", Integer.toString(guanliid));
            	tiezineirong.add(ob);
            }
         
            ret.close();
            db.close();//关闭连接
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
		
		sql="select title,t_huifu.content content,truename,weizhi from t_tiezi,t_user,t_huifu where t_huifu.userid=t_user.id and t_huifu.tieziid=t_tiezi.id and  t_tiezi.id="+tiezi_id;
		db=new DBHelper(sql);
		try{
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            while(ret.next()){
            	String title=ret.getString("title");
            	String info=ret.getString("content");
            	String truename=ret.getString("truename");
            	String weizhi=ret.getString("weizhi");;
            	JsonObject ob = new JsonObject();
            	ob.addProperty("title", title);
            	ob.addProperty("info", info);
            	ob.addProperty("nickname", truename);
            	ob.addProperty("louceng", weizhi);
            	//ob.addProperty("bankuaiimagepath", bankuaiimagepath);
            	//ob.addProperty("guanliid", Integer.toString(guanliid));
            	tiezineirong.add(ob);
            }
         
            ret.close();
            db.close();//关闭连接
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
		
		return tiezineirong;
	}


	
	public JsonArray gettiezi(String bankuaiid) {
		String sql=null;
		DBHelper db = null;
		ResultSet ret = null;
		
	
		JsonArray tiezi = new JsonArray();
		sql="select title,truename,t_tiezi.huifushu,t_tiezi.id,bankuaiming from t_tiezi,t_user,t_bankuai where t_tiezi.bankuaiid="+bankuaiid+" and t_tiezi.deletestatus=0 and t_tiezi.userid=t_user.id and t_tiezi.bankuaiid=t_bankuai.id";
		db=new DBHelper(sql);
		try{
            ret = db.pst.executeQuery();
            while(ret.next()){
            	int id=ret.getInt("id");
            	String content=ret.getString("huifushu");
            	String title=ret.getString("title");
            	String truename=ret.getString("truename");
            	String bankuaiming=ret.getString("bankuaiming");
            	
            	JsonObject ob = new JsonObject();
            	ob.addProperty("id", Integer.toString(id));
            	ob.addProperty("contentNum", content);
            	ob.addProperty("title", title);
            	ob.addProperty("userid", truename);
            	ob.addProperty("bankuaiming",bankuaiming );
            	
            	tiezi.add(ob);
            }
         
            ret.close();
            db.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
		return tiezi;
	}
	
	
	public int huifuadd(String content, String tieziid, String userid) {
		String sql=null;
		DBHelper db = null;
		ResultSet ret = null;
		String weizhi=null;
		int reweizhi=0;
		int max=0;
		
		sql="select weizhi from t_huifu where tieziid='"+tieziid+"' and deletestatus=0";
		db=new DBHelper(sql);
		try{
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            while(ret.next()){
            	
            	reweizhi = Integer.parseInt(ret.getString("weizhi"));
            	
            	if(max < reweizhi) {
            		max = reweizhi;
            	}
            	
            	
            	
            	
            }
         
            ret.close();
            db.close();//关闭连接
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String nowtime = df.format(new Date());// new Date()为获取当前系统时间
		
		max+=1;
		int n = 0;
		sql="insert into t_huifu (createtime,content,weizhi,tieziid,userid,deletestatus)values('"+nowtime+"','"+content+"','"+max+"','"+tieziid+"','"+userid+"',0)";
		db=new DBHelper(sql);
		try{
            n = db.pst.executeUpdate();//执行语句，得到结果集  
            db.close();//关闭连接
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
		
		sql="update t_tiezi set huifutime='"+nowtime+"' where id = '"+tieziid+"'";
		db=new DBHelper(sql);
		try{
            n = db.pst.executeUpdate();//执行语句，得到结果集
            db.close();//关闭连接
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
		
		
		if(n==1)
			return 1;
		else
			return 0;
	}
	
	
public int tieziadd(String content, String title, String userid, String bankuaiid) {
		
		String sql=null;
		DBHelper db = null;
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String nowtime = df.format(new Date());// new Date()为获取当前系统时间
		
		int n = 0;
		sql="insert into t_tiezi (createtime,content,title,userid,bankuaiid,deletestatus,dianjishu,huifushu,tuijian) values ('"+nowtime+"','"+content+"','"+title+"','"+userid+"','"+bankuaiid+"',0,0,0,0)";
		db=new DBHelper(sql);
	
		try{
            n = db.pst.executeUpdate();
            db.close();//关闭连接
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
		
		
		if(n==1) {
			return 1;//插入成功
		}else {
			return 0;//插入失败
		}
	}


	public JsonArray getmytiezi(String user_id) {
		String sql=null;
		DBHelper db = null;
		ResultSet ret = null;
		
	
		JsonArray tiezi = new JsonArray();
		sql="select title,truename,t_tiezi.huifushu,t_tiezi.id,bankuaiming from t_tiezi,t_user,t_bankuai where t_tiezi.userid="+user_id+" and t_tiezi.deletestatus=0 and t_tiezi.userid=t_user.id and t_tiezi.bankuaiid=t_bankuai.id";
		db=new DBHelper(sql);
		try{
	        ret = db.pst.executeQuery();//执行语句，得到结果集  
	        while(ret.next()){
	        	int id=ret.getInt("id");
	        	String content=ret.getString("huifushu");
	        	String title=ret.getString("title");
	        	String truename=ret.getString("truename");
	        	String bankuaiming=ret.getString("bankuaiming");
	        	
	        	JsonObject ob = new JsonObject();
	        	ob.addProperty("id", Integer.toString(id));
	        	ob.addProperty("contentNum", content);
	        	ob.addProperty("title", title);
	        	ob.addProperty("userid", truename);
	        	ob.addProperty("bankuaiming",bankuaiming );
	        	
	        	tiezi.add(ob);
	        }
	     
	        ret.close();
	        db.close();//关闭连接
	    } catch (SQLException e) {  
	        e.printStackTrace();  
	    }
		return tiezi;
	}
	
	public JsonArray getmyhuifu(String user_id) {
		String sql=null;
		DBHelper db = null;
		ResultSet ret = null;
		
	
		JsonArray tiezi = new JsonArray();
		sql="select t_tiezi.title title,t_tiezi.id id,t_huifu.content content,t_huifu.id i_id from t_tiezi,t_huifu where t_huifu.userid="+user_id+" and t_tiezi.deletestatus=0 and t_huifu.deletestatus=0 and t_huifu.tieziid=t_tiezi.id";
		db=new DBHelper(sql);
		try{
	        ret = db.pst.executeQuery();//执行语句，得到结果集  
	        while(ret.next()){
	        	int id=ret.getInt("id");
	        	String content=ret.getString("content");
	        	String title=ret.getString("title");
	        	int i_id=ret.getInt("i_id");
	        	
	        	
	        	JsonObject ob = new JsonObject();
	        	ob.addProperty("tiezi_id", Integer.toString(id));
	        	ob.addProperty("content", content);
	        	ob.addProperty("title", title);
	        	ob.addProperty("id", i_id);
	
	        	
	        	tiezi.add(ob);
	        }
	     
	        ret.close();
	        db.close();//关闭连接
	    } catch (SQLException e) {  
	        e.printStackTrace();  
	    }
		return tiezi;
	}


	
}
