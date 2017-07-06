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
	 * @ ���� ��̨,��֤�Ƿ��¼
	 * @return
	 */
	private Boolean isHaveLogin(){		
		Object obj=ActionContext.getContext().getSession().get("boZhuStaff");//
		if(obj!=null){
			if(obj instanceof CrmStaff){
				CrmStaff staff=(CrmStaff)obj;
				if(!staff.getLoginName().equals("�����û�")){
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
	 * @���� ��̨-�޸ĺ�����Ϣ
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
						messages="<li>�޸ĳɹ���</li>";
						href="<a href='"+request.getContextPath()+"/sys/friend_admin_adminListFriend'>[�����޸���������]</a>";
						request.setAttribute("messages",messages);
						request.setAttribute("href",href);
						return "successAdmin";				
					} else {
						messages="<li>�޸�ʧ�ܣ�</li>";
						href="<a href='javascript:window.history.go(-1)>[����]</a>";
						
					}
					request.setAttribute("messages",messages);
					request.setAttribute("href",href);
				}
				else{
					href="<a href='javascript:window.history.go(-1)>[����]</a>";
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
	 * @���� ǰ̨-��ѯĳ��������Ϣ
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
	 * @���� ��̨-��ѯĳ��������Ϣ
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
	 * @���� ��̨-ɾ��������Ϣ
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
			messages+="<li>ɾ�����ѳɹ���</li>";
			href="<a href='"+request.getContextPath()+"/sys/friend_admin_adminListFriend'>[����ɾ����������]</a>";
			request.setAttribute("messages",messages);
			request.setAttribute("href",href);
			return "successAdmin";
		
		} else {
			messages+="<li>ɾ������ʧ�ܣ�</li>";
			href="<a href='javascript:window.history.go(-1)'>[����]</a>";			
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
	 * @���� ��֤������
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
			messages+="<li>������ <b>����������</b></li>";
		}
		if(sex==null||sex.equals("")){
			mark=false;
			messages+="<li>��ѡ�� <b>�����Ա�</b></li>";
		}
		if(oicq==null||oicq.equals("")){
			mark=false;
			messages+="<li>������ <b>QQ ���룡</b></li>";
		}
		if(blog==null||blog.equals("")){
			mark=false;
			messages+="<li>������ <b>BLOG��ַ��</b></li>";
		}
		request.setAttribute("messages",messages);
		return mark;
	}
	/**
	 * @���� ��̨-��Ӻ�����Ϣ
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
				//friendDao.closeConnection();
				if(mark){
					messages = "<li>��Ӻ��ѳɹ���</li>";
					href="<a href='"+request.getContextPath()+"/sys/friend_admin_friendAdd'>[�������]</a>";
					request.setAttribute("messages",messages);
					request.setAttribute("href",href);	
					return "successAdmin";
				}
				else{
					messages = "<li>��Ӻ���ʧ�ܣ�</li>";
					href="<a href='javascript:window.history.go(-1)'>[����]</a>";				
				}
				request.setAttribute("messages",messages);
			}
			else{
				href="<a href='javascript:window.history.go(-1)'>[����]</a>";			
			}
			request.setAttribute("href",href);						
			return "failAdmin";
		}else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}
	/**
	 * @���� ǰ̨-��ѯ���к���
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
	 * @���� ��̨-��ѯ���к���
	 */
	public String adminListFriend() {
		request=ServletActionContext.getRequest();
		FriendDao friendDao=new FriendDao();
		List friendList=friendDao.queryFriend("all");
		request.setAttribute("friendList", friendList);
		return "adminListFriend";				
	}	

}