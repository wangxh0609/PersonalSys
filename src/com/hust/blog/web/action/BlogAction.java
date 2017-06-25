package com.hust.blog.web.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.hust.AccountSys.blog.dao.ArticleDao;
import com.hust.AccountSys.blog.dao.ArticleTypeDao;
import com.hust.AccountSys.blog.dao.FriendDao;
import com.hust.AccountSys.blog.dao.PhotoDao;
import com.hust.AccountSys.blog.dao.WordDao;
import com.hust.AccountSys.blog.serviceImpl.VisitorLogService;
import com.hust.core.action.BaseAction;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.crm.staff.service.StaffService;
import com.hust.crm.staff.service.Impl.StaffServiceImpl;
import com.hust.docMgr.blog.domain.MasterBean;
import com.hust.docMgr.blog.domain.VisitorLogBean;
import com.opensymphony.xwork2.ActionContext;

public class BlogAction extends BaseAction {
	private static MasterBean masterBean;
	static {
		// LogonDao logonDao=new LogonDao();
		// masterBean=logonDao.getMaster();
	}
	HttpServletRequest request=null;
	private StaffService staffService;
	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
	}
	
	private Boolean isHaveLogin(){		
		Object obj=ActionContext.getContext().getSession().get("loginStaff");
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
	
	public String blogfrontIndex() {
		request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		ArticleDao articleDao = new ArticleDao();
		ArticleTypeDao artTypeDao = new ArticleTypeDao();
		PhotoDao photoDao = new PhotoDao();
		WordDao wordDao = new WordDao();
		CrmStaff staff = (CrmStaff) ActionContext.getContext().getSession().get("loginStaff");
		// 记录登陆信息
		VisitorLogService visitorService = new VisitorLogService();
		VisitorLogBean visitorBean = new VisitorLogBean();
		String visitorip=(Object)request.getAttribute("visitorip")==null?"":(String)request.getAttribute("visitorip");
		visitorBean.setVisitorIp(visitorip);
		if (staff != null) {
			visitorBean.setVisitorName(staff.getLoginName());
			visitorBean.setVisitorInfo(staff.getLoginName() + "访问了博客！");
		} else {
			visitorBean.setVisitorName("匿名用戶");
			visitorBean.setVisitorInfo("匿名用戶访问了博客！");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		String datestr = sdf.format(new Date());
		visitorBean.setVisitorTime(datestr);
		visitorService.operationArticle("add", visitorBean);

		
		handleBefEnter();
		FriendDao friendDao = new FriendDao();

		/********** 获取在主页面的内容显示区中显示的内容 *********/
		// 从tb_article数据表中获取前3篇文章
		List articleList = articleDao.queryArticle(-1, null);
		request.setAttribute("articleList", articleList);
		// 从tb_photo数据表中获取前8张照片
		List photoList = photoDao.queryPhoto("sub");
		request.setAttribute("photoList", photoList);

		/********** 获取在页面侧栏中显示的内容 *********/
		/* 从tb_word数据表中获取前5条留言 */
		List wordList = wordDao.queryWord("sub");
		session.setAttribute("wordList", wordList);
		/* 从tb_article数据表中获取前5章推荐文章 */
		articleDao.connect();
		List artTJList = articleDao.queryArticle(4, "sub");
		session.setAttribute("artTJList", artTJList);
		/* 从tb_friend数据表中获取前5位好友信息 */
		List friendList = friendDao.queryFriend("sub");
		session.setAttribute("friendList", friendList);

		/********** 获取文章类别 *******************/
		/* 从tb_articleType数据表中获取文章类别 */
		List artTypeList = artTypeDao.queryTypeList();
		session.setAttribute("artTypeList", artTypeList);

		/*********** 保存博主信息 *****************/
		// session.setAttribute("master",masterBean);
		return "frontSucc";
	}

	// 判断是从哪里访问的，通过判断session是否有loginStaff且（revisitorStaff不存在或者revisitorStaff存在且revisitorStaff.isNeedEncry()=false）是从系统访问的
	// 从系统访问 1.访问自己的  不会有访问人的id信息 将loginStaff 设置成revisitorStaff 让两者相等
	//2.从系统访问别人的 根据请求传入的id 得到被访问人的信息赋给revisitorStaff
	// 如果是通过地址访问的就把数据库中匿名用户
	// 赋值给loginStaff 访问的就我的博客 
	//如果一直刷新会存在问题 第一次刷新会认为是从系统中登录的去访问自己的 通过staff的是否加密变量判断是刷新是前是从哪里来的
	//如果为false说明是从系统来的  从网页上访问博主登陆后需要设置revisitorStaff isNeedEncry=false
	public void handleBefEnter() {
		CrmStaff staff =(CrmStaff) ActionContext.getContext().getSession().get("loginStaff");
		CrmStaff revisitorStaff=null;
		Object teobj=ActionContext.getContext().getSession().get("revisitorStaff");
		if(teobj!=null){
			revisitorStaff=(CrmStaff)teobj;
		}
		//不是登录访问 staff为null或者staff不为null且revisitorStaff.isNeedEncry()=ture
		if (staff == null || staff.getLoginName().equals("")||(staff!=null&&revisitorStaff!=null&&revisitorStaff.isNeedEncry()==true)) {
			staff = new CrmStaff();
			staff.setLoginName("匿名用户");
			staff.setGender("未知");
			ActionContext.getContext().getSession().put("loginStaff", staff);
			if(staffService!=null){
				revisitorStaff=staffService.findByLoginName("wangxh");
				
				if(revisitorStaff!=null){
					revisitorStaff.setLoginPwd("");
				}
			}
			ActionContext.getContext().getSession().put("revisitorStaff", revisitorStaff);
		}else{
			//从系统访问
			request = ServletActionContext.getRequest();
			Object obj=request.getAttribute("staffId");
			if(obj==null){//访问自己的博客
				if(revisitorStaff==null){//不是null是留言或评论登陆的
				    CrmStaff temStaff=new CrmStaff();
				    temStaff=staff;
				    temStaff.setNeedEncry(false);//是从系统访问自己的
					ActionContext.getContext().getSession().put("revisitorStaff", temStaff);
				}
			}else{//访问别人的博客
				String staffId=(String)obj;
				if(staffService!=null){
					revisitorStaff=staffService.findById(staffId);				
				}
				ActionContext.getContext().getSession().put("revisitorStaff", revisitorStaff);
			}
		}
	}
}
