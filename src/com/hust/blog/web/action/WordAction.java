package com.hust.blog.web.action;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.hust.AccountSys.blog.dao.WordDao;
import com.hust.core.action.BaseAction;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.docMgr.blog.domain.WordBean;
import com.hust.toolsbean.MyTools;
import com.opensymphony.xwork2.ActionContext;

public class WordAction extends BaseAction {
	
	private int parentId=-1;
	private int firstParentId=-1;
	public int getFirstParentId() {
		return firstParentId;
	}

	public void setFirstParentId(int firstParentId) {
		this.firstParentId = firstParentId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	HttpServletRequest request=null;
	HttpServletResponse response=null;
	public String wordShow(){
		request=ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		try {
			this.selectWord(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "wordShow";
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
	//前台验证是否登陆
	private Boolean isHaveLoginqt(){		
		Object obj=ActionContext.getContext().getSession().get("loginStaff");//
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
	
	public String addWordOther(){
	    request=ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		//留言需要登录
		if(isHaveLoginqt()){
			try {
				return this.validateWord(request, response);
			} catch (ServletException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return "fail";
		}else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}
	public String validateWord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			boolean mark=true;
			String messages="";
			request.setCharacterEncoding("utf-8");
			String author=request.getParameter("wordAuthor");
			String title=request.getParameter("wordTitle");
			String content=request.getParameter("wordContent");
			if(author!=null)
			author=new String(author.getBytes("ISO8859-1"), "UTF-8");
			if(title!=null)
			title=new String(title.getBytes("ISO8859-1"), "UTF-8");
			if(content!=null)
			content=new String(content.getBytes("ISO8859-1"), "UTF-8");
			
			if(author==null||author.equals(""))
				author="匿名好友";
			Object objid=request.getAttribute("parentId");
			if(objid==null){
				if(title==null||title.equals("")){
					mark=false;
					messages+="<li>请输入 <b>留言标题！</b></li>";
				}
			}
			if(content==null||content.equals("")){
				mark=false;
				messages+="<li>请输入 <b>留言内容！</b></li>";
			}
			if(!mark){
				request.setAttribute("messages",messages);
				return "fail";
			}
			else{
				return addWord(request,response);
			}		
		}
		public String addWord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//可能是回复留言 回复留言会有parentId信息 没有就不是  回复的表单?parentId=&wordAuthor=
			if(isHaveLoginqt()){
			Object objid=request.getAttribute("parentId");
			Object firstobjid=request.getAttribute("firstParentId");
			if(objid!=null){
				parentId=(int)(objid);
			}
			if(firstobjid!=null){
				firstParentId=(int)(firstobjid);
			}
			
			WordBean wordSingle=new WordBean();
			request.setCharacterEncoding("utf-8");
			String author=request.getParameter("wordAuthor");//MyTools.toChinese(request.getParameter("wordAuthor"));
			String title=request.getParameter("wordTitle");//MyTools.toChinese(request.getParameter("wordTitle"));
			String content=request.getParameter("wordContent");//MyTools.toChinese(request.getParameter("wordContent"));		
			String sdTime=MyTools.changeTime(new Date());		
			if(author==null||author.equals("")){
				author="匿名用户";
			}
			wordSingle.setFirstParentId(firstParentId);
			Object objlogin=ActionContext.getContext().getSession().get("loginStaff");//访问者			
			if(objlogin!=null){//设置留言人信息
				if(objlogin instanceof CrmStaff){
					CrmStaff login=(CrmStaff)objlogin;
					wordSingle.setWordAuthor(login.getLoginName());
				}
			}else{
				wordSingle.setWordAuthor(author);
			}			
			WordDao wordDao=new WordDao();
			Object objreVisitor=ActionContext.getContext().getSession().get("revisitorStaff");//被访问者
			if(parentId==-1){//不是回复留言,留言			    
				if(objreVisitor!=null){//设置被留言人信息
					if(objreVisitor instanceof CrmStaff){
						CrmStaff revisitor=(CrmStaff)objreVisitor;
						wordSingle.setToName(revisitor.getLoginName());
						wordSingle.setBozName(revisitor.getLoginName());
					}
				}	
				wordSingle.setWordTitle(title);
			}else{
				if(objreVisitor!=null){//设置被博主信息
					if(objreVisitor instanceof CrmStaff){
						CrmStaff revisitor=(CrmStaff)objreVisitor;						
						wordSingle.setBozName(revisitor.getLoginName());
					}
				}	
				wordSingle.setParentId(parentId);//回复留言设置父信息
				//查询从数据库获得wordAuthor被留言人信息
				WordBean wordObj=wordDao.getWordBeanByid(parentId);
				if(wordObj!=null){				    
				    wordSingle.setToName(wordObj.getWordAuthor());
				}
				wordSingle.setWordTitle("回复留言");
			}						
			wordSingle.setWordContent(content);
			wordSingle.setWordTime(sdTime);
			
			String messages="";			
			boolean mark=wordDao.operationWord("add",wordSingle);
			if(mark){
				if(parentId==-1){
					messages="<li>留言成功！</li>";
				}else{
					messages="<li>回复成功！</li>";
				}
				request.setAttribute("messages",messages);
				return "success";
			}
			else{				
				messages="<li>留言失败！</li>";
				request.setAttribute("messages",messages);
				return "fail";
			}	
			}else{
				ActionContext.getContext().getSession().put("loginType", "blog");
				return "login";
			}
		}
		public void selectWord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			WordDao wordDao=new WordDao();
			List wordList=wordDao.queryWord("all");
			if(wordList!=null){
				wordDao.operationWord("update", new WordBean());
			}
			request.setAttribute("wordList",wordList);		
		}
		public String wordList() {
			request=ServletActionContext.getRequest();
			response=ServletActionContext.getResponse();			
			WordDao wordDao=new WordDao();
			List wordList=wordDao.queryWord("adminAll");
			if(wordList!=null){
				wordDao.operationWord("update", new WordBean());
			}
			request.setAttribute("adminwordList",wordList);
			return "wordList";
		}
		
		public String delete() {
			request=ServletActionContext.getRequest();
			response=ServletActionContext.getResponse();
			if(isHaveLogin()){
				WordDao wordDao = new WordDao();
				WordBean wordBean = new WordBean();
	
				String messages="";
				String href="";
				String forward="";
				wordBean.setId(MyTools.strToint(request.getParameter("id")));		
				boolean mark=wordDao.operationWord("delete", wordBean);		
				if (mark) {			
					messages+="<li>删除留言成功！</li>";
					href="<a href='"+request.getContextPath()+"/sys/word_admin_wordList'>[继续删除其他留言]</a>";
					request.setAttribute("messages",messages);
					request.setAttribute("href",href);
					return "successAdmin";			
				} else {
					messages+="<li>删除留言失败！</li>";
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
		 * @功能 验证展开时wordId 的父是否是-1
		 * @return
		 */
		public boolean validateWordExpand(int wordId){
			return true;
		}
		/**
		 * @功能 展开全部
		 */
		public void expandAll(){
			request=ServletActionContext.getRequest();
			response=ServletActionContext.getResponse();
			
			String temObj=request.getParameter("wordId");
			int wordId=-2;
			if(temObj!=null){
				wordId=Integer.parseInt(temObj);
			}
			response.setContentType("text/html;charset=utf-8");			
			PrintWriter out=null;
			try {
				out = response.getWriter();
			} catch (IOException e) {				
			}
			if(validateWordExpand(wordId)){//通过验证
				//从数据库查询
				WordDao wordDao = new WordDao();
				List<WordBean> wordBeanList=wordDao.getAllByParenWordId(wordId);
				
				if(wordBeanList!=null&&wordBeanList.size()>0){
					StringBuilder msg=new StringBuilder("");
					for(int i=0;i<wordBeanList.size();i++){
						WordBean temBean=wordBeanList.get(i);
						msg.append("<tr class='wordclass' id='reply_"+wordId+"_"+temBean.getId()+"'><td style='text-indent:35' colspan='2' valign='top'>"+temBean.getWordAuthor()+"回复"+temBean.getToName()+"："+temBean.getWordContent()+"</td></tr>\n");
					}
					//System.out.println(msg);
					out.print(msg.toString());
				}else{
					out.print("false");
				}
				
			}else{
				out.print("false");
			}
			out.flush();
			out.close();
		}
}
