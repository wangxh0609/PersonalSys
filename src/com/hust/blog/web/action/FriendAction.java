package com.hust.blog.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.hust.AccountSys.blog.dao.FriendDao;
import com.hust.core.action.BaseAction;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.docMgr.blog.domain.FriendBean;
import com.hust.toolsbean.MyTools;
import com.opensymphony.xwork2.ActionContext;

public class FriendAction extends BaseAction {
	private HttpServletRequest request=null;
	private HttpServletResponse response=null;
	private FriendBean friendBean=null;
	public FriendBean getFriendBean() {
		return friendBean;
	}
	public void setFriendBean(FriendBean friendBean) {
		this.friendBean = friendBean;
	}	
	
	/**
	 * @ 功能 后台,验证是否登录
	 * @return
	 */
	private Boolean isHaveLogin(){		
		Object obj=ActionContext.getContext().getSession().get("boZhuStaff");//
		if(obj!=null){
			if(obj instanceof CrmStaff){
				CrmStaff staff=(CrmStaff)obj;
				if(!staff.getLoginName().equals("匿名用户")){
					return true;
				}
			}
		}
		return false;
	}
	
	public String friendAdd(){
		if(isHaveLogin()){
			return "friendAdd";
		}
		else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}
	public String friendList(){
		request=ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		try {
			return this.listFriend(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "friendList";
	}
	/**
	 * @功能 后台-修改好友信息
	 */
	public String friendModify() {
		request=ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		if(isHaveLogin()){
			FriendDao friendDao=new FriendDao();		
			String type=request.getParameter("type");
			if(type==null)
				type="";
			if(!type.equals("doModify")){			
				int id=Integer.parseInt(request.getParameter("id"));
				FriendBean friendBean=friendDao.queryFriendSingle(id);			
				request.setAttribute("modifySingle",friendBean);
				return "friendModify";
			}
			else{
				String messages="";
				String href="";
				//String forward="";
				boolean flag=validateFriend();
				boolean mark=false;
				if(flag){
					if(friendBean!=null){
						mark=friendDao.operationFriend("modify",friendBean);
					}
					if (mark) {
						messages="<li>修改成功！</li>";
						href="<a href='"+request.getContextPath()+"/sys/friend_admin_adminListFriend'>[继续修改其他好友]</a>";
						request.setAttribute("messages",messages);
						request.setAttribute("href",href);
						return "successAdmin";				
					} else {
						messages="<li>修改失败！</li>";
						href="<a href='javascript:window.history.go(-1)>[返回]</a>";
						
					}
					request.setAttribute("messages",messages);
					request.setAttribute("href",href);
				}
				else{
					href="<a href='javascript:window.history.go(-1)>[返回]</a>";
					request.setAttribute("href",href);
				}			
				return "failAdmin";
			}
		}else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}
	/**
	 * @功能 前台-查询某个好友信息
	 */
	public String friendSingle() {
		String strId=request.getParameter("id");
		int id=Integer.parseInt(strId);
		FriendDao friendDao = new FriendDao();
		FriendBean friendSingle=friendDao.queryFriendSingle(id);		
		request.setAttribute("friendSingle",friendSingle);
		return "friendSingle";		
	}
	/**
	 * @功能 后台-查询某个好友信息
	 */
	public String adminSingleFriend() {
		request=ServletActionContext.getRequest();
		if(isHaveLogin()){
		String strId=request.getParameter("id");
		int id=Integer.parseInt(strId);
		FriendDao friendDao = new FriendDao();
		FriendBean friendSingle=friendDao.queryFriendSingle(id);		
		request.setAttribute("friendSingle",friendSingle);
		return "adminSingleFriend";
		}else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}		
	}
	/**
	 * @功能 后台-删除好友信息
	 */
	public String deleteFriend(){
		request=ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		if(isHaveLogin()){
		FriendDao friendDao = new FriendDao();
		FriendBean friendBean = new FriendBean();
		String messages="";
		String href="";
		friendBean.setId(Integer.parseInt((request.getParameter("id"))));		
		boolean mark=friendDao.operationFriend("delete",friendBean);		
		if (mark) {			
			messages+="<li>删除好友成功！</li>";
			href="<a href='"+request.getContextPath()+"/sys/friend_admin_adminListFriend'>[继续删除其他好友]</a>";
			request.setAttribute("messages",messages);
			request.setAttribute("href",href);
			return "successAdmin";
		
		} else {
			messages+="<li>删除好友失败！</li>";
			href="<a href='javascript:window.history.go(-1)'>[返回]</a>";			
		}
		request.setAttribute("messages",messages);
		request.setAttribute("href",href);		
		return "failAdmin";
		}else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}
	/**
	 * @功能 验证表单数据
	 */
	public boolean validateFriend() {
		boolean mark=true;
		String messages="";
		String name=null;
		String sex=null;
		String oicq=null;
		String blog=null;
		if(friendBean!=null){
			name=friendBean.getName();
			sex=friendBean.getSex();
			oicq=friendBean.getOicq();
			blog=friendBean.getBlog();
		}
				
		
		if(name==null||name.equals("")){
			mark=false;
			messages+="<li>请输入 <b>好友姓名！</b></li>";
		}
		if(sex==null||sex.equals("")){
			mark=false;
			messages+="<li>请选择 <b>好友性别！</b></li>";
		}
		if(oicq==null||oicq.equals("")){
			mark=false;
			messages+="<li>请输入 <b>QQ 号码！</b></li>";
		}
		if(blog==null||blog.equals("")){
			mark=false;
			messages+="<li>请输入 <b>BLOG地址！</b></li>";
		}
		request.setAttribute("messages",messages);
		return mark;
	}
	/**
	 * @功能 后台-添加好友信息
	 */
	public String addFriend() {
		request=ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		if(isHaveLogin()){
			String messages = "";
			String href="";
			boolean flag = validateFriend();
			
			if(flag){
				FriendDao friendDao = new FriendDao();						
				boolean mark=friendDao.operationFriend("add",friendBean);
				friendDao.closeConnection();
				if(mark){
					messages = "<li>添加好友成功！</li>";
					href="<a href='"+request.getContextPath()+"/sys/friend_admin_friendAdd'>[继续添加]</a>";
					request.setAttribute("messages",messages);
					request.setAttribute("href",href);	
					return "successAdmin";
				}
				else{
					messages = "<li>添加好友失败！</li>";
					href="<a href='javascript:window.history.go(-1)'>[返回]</a>";				
				}
				request.setAttribute("messages",messages);
			}
			else{
				href="<a href='javascript:window.history.go(-1)'>[返回]</a>";			
			}
			request.setAttribute("href",href);						
			return "failAdmin";
		}else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}
	/**
	 * @功能 前台-查询所有好友
	 */
	public String listFriend(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		FriendDao friendDao=new FriendDao();
		List friendList=friendDao.queryFriend("all");
		request.setAttribute("friendList", friendList);
		return "friendList";
		//RequestDispatcher rd = request.getRequestDispatcher("/front/friend/FriendList.jsp");
		//rd.forward(request, response);		
	}
	/**
	 * @功能 后台-查询所有好友
	 */
	public String adminListFriend() {
		request=ServletActionContext.getRequest();
		FriendDao friendDao=new FriendDao();
		List friendList=friendDao.queryFriend("all");
		request.setAttribute("friendList", friendList);
		return "adminListFriend";				
	}	

}
