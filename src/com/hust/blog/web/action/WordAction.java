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
	//ǰ̨��֤�Ƿ��½
	private Boolean isHaveLoginqt(){		
		Object obj=ActionContext.getContext().getSession().get("loginStaff");//
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
	
	public String addWordOther(){
	    request=ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		//������Ҫ��¼
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
				author="��������";
			Object objid=request.getAttribute("parentId");
			if(objid==null){
				if(title==null||title.equals("")){
					mark=false;
					messages+="<li>������ <b>���Ա��⣡</b></li>";
				}
			}
			if(content==null||content.equals("")){
				mark=false;
				messages+="<li>������ <b>�������ݣ�</b></li>";
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
			//�����ǻظ����� �ظ����Ի���parentId��Ϣ û�оͲ���  �ظ��ı�?parentId=&wordAuthor=
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
				author="�����û�";
			}
			wordSingle.setFirstParentId(firstParentId);
			Object objlogin=ActionContext.getContext().getSession().get("loginStaff");//������			
			if(objlogin!=null){//������������Ϣ
				if(objlogin instanceof CrmStaff){
					CrmStaff login=(CrmStaff)objlogin;
					wordSingle.setWordAuthor(login.getLoginName());
				}
			}else{
				wordSingle.setWordAuthor(author);
			}			
			WordDao wordDao=new WordDao();
			Object objreVisitor=ActionContext.getContext().getSession().get("revisitorStaff");//��������
			if(parentId==-1){//���ǻظ�����,����			    
				if(objreVisitor!=null){//���ñ���������Ϣ
					if(objreVisitor instanceof CrmStaff){
						CrmStaff revisitor=(CrmStaff)objreVisitor;
						wordSingle.setToName(revisitor.getLoginName());
						wordSingle.setBozName(revisitor.getLoginName());
					}
				}	
				wordSingle.setWordTitle(title);
			}else{
				if(objreVisitor!=null){//���ñ�������Ϣ
					if(objreVisitor instanceof CrmStaff){
						CrmStaff revisitor=(CrmStaff)objreVisitor;						
						wordSingle.setBozName(revisitor.getLoginName());
					}
				}	
				wordSingle.setParentId(parentId);//�ظ��������ø���Ϣ
				//��ѯ�����ݿ���wordAuthor����������Ϣ
				WordBean wordObj=wordDao.getWordBeanByid(parentId);
				if(wordObj!=null){				    
				    wordSingle.setToName(wordObj.getWordAuthor());
				}
				wordSingle.setWordTitle("�ظ�����");
			}						
			wordSingle.setWordContent(content);
			wordSingle.setWordTime(sdTime);
			
			String messages="";			
			boolean mark=wordDao.operationWord("add",wordSingle);
			if(mark){
				if(parentId==-1){
					messages="<li>���Գɹ���</li>";
				}else{
					messages="<li>�ظ��ɹ���</li>";
				}
				request.setAttribute("messages",messages);
				return "success";
			}
			else{				
				messages="<li>����ʧ�ܣ�</li>";
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
					messages+="<li>ɾ�����Գɹ���</li>";
					href="<a href='"+request.getContextPath()+"/sys/word_admin_wordList'>[����ɾ����������]</a>";
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
		 * @���� ��֤չ��ʱwordId �ĸ��Ƿ���-1
		 * @return
		 */
		public boolean validateWordExpand(int wordId){
			return true;
		}
		/**
		 * @���� չ��ȫ��
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
			if(validateWordExpand(wordId)){//ͨ����֤
				//�����ݿ��ѯ
				WordDao wordDao = new WordDao();
				List<WordBean> wordBeanList=wordDao.getAllByParenWordId(wordId);
				
				if(wordBeanList!=null&&wordBeanList.size()>0){
					StringBuilder msg=new StringBuilder("");
					for(int i=0;i<wordBeanList.size();i++){
						WordBean temBean=wordBeanList.get(i);
						msg.append("<tr class='wordclass' id='reply_"+wordId+"_"+temBean.getId()+"'><td style='text-indent:35' colspan='2' valign='top'>"+temBean.getWordAuthor()+"�ظ�"+temBean.getToName()+"��"+temBean.getWordContent()+"</td></tr>\n");
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