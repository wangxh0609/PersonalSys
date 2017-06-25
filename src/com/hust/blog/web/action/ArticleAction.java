package com.hust.blog.web.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.hust.AccountSys.blog.dao.ArticleDao;
import com.hust.AccountSys.blog.dao.ArticleTypeDao;
import com.hust.AccountSys.blog.dao.ReviewDao;
import com.hust.core.action.BaseAction;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.docMgr.blog.domain.ArticleBean;
import com.hust.docMgr.blog.domain.ArticleTypeBean;
import com.hust.docMgr.blog.domain.ReviewBean;
import com.hust.toolsbean.MyTools;
import com.opensymphony.xwork2.ActionContext;

public class ArticleAction extends BaseAction {

	private ArticleBean articleBean;
	private ArticleTypeBean typeBean;

	public ArticleTypeBean getTypeBean() {
		return typeBean;
	}

	public void setTypeBean(ArticleTypeBean typeBean) {
		this.typeBean = typeBean;
	}

	public ArticleBean getArticleBean() {
		return articleBean;
	}

	public void setArticleBean(ArticleBean articleBean) {
		this.articleBean = articleBean;
	}

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;

	public String articleTypeAdd() {
		if(isHaveLogin()){
		    return "articleTypeAdd";
		}else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}

	public String articleSingle() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		try {
			return this.readArticle(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block

		}

		return "fail";
	}

	/**
	 * @ 功能 后台,验证是否登录
	 * 
	 * @return
	 */
	private Boolean isHaveLogin() {
		Object obj = ActionContext.getContext().getSession().get("boZhuStaff");
		if (obj != null) {
			if (obj instanceof CrmStaff) {
				CrmStaff staff = (CrmStaff) obj;
				if (!staff.getLoginName().equals("匿名用户")) {
					return true;
				}
			}
		}
		return false;
	}
	//前台
	private Boolean isHaveLoginqt() {
		Object obj = ActionContext.getContext().getSession().get("loginStaff");
		if (obj != null) {
			if (obj instanceof CrmStaff) {
				CrmStaff staff = (CrmStaff) obj;
				if (!staff.getLoginName().equals("匿名用户")) {
					return true;
				}
			}
		}
		return false;
	}

	public String addReview() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		if (!isHaveLoginqt()) {
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
		try {
			return this.validateFollow(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "fail";
	}

	public String articleIndex() {
		return "articleIndex";
	}

	public String articleList() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		try {
			return this.selectArticle(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "articleList";
	}

	/**
	 * @功能 验证评论信息
	 */
	public String validateFollow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean mark = true;
		String messages = "";
		request.setCharacterEncoding("utf-8");
		String content = request.getParameter("reContent");
		if (content == null || content.equals("")) {
			mark = false;
			messages = "<li>请输入 <b>评论内容！</b></li>";
		}
		if (mark) {

			if (content.length() > 1000) {
				mark = false;
				messages = "<li><b>评论内容</b> 最多允许输入1000个字符！</li>";
			}
		}
		if (!mark) {
			request.setAttribute("messages", messages);
			return "fail";
		} else {
			return followAdd(request, response);
		}
	}

	/**
	 * @功能 添加文章评论
	 */
	public String followAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int id = Integer.parseInt(request.getParameter("articleId"));
		String author = request.getParameter("reAuthor");
		String content = request.getParameter("reContent");
		String today = MyTools.changeTime(new Date());
		if (author == null || author.equals(""))
			author = "匿名好友";

		ReviewBean reviewBean = new ReviewBean();
		reviewBean.setArticleId(id);
		reviewBean.setReAuthor(author);
		reviewBean.setReContent(content);
		reviewBean.setReSdTime(today);

		ReviewDao reviewDao = new ReviewDao();
		String messages = "";
		boolean mark = reviewDao.operationReview("add", reviewBean);
		if (mark) {
			// forward="/front/article/success.jsp";
			messages = "<li>发表评论成功！</li>";
			request.setAttribute("messages", messages);
			return "success";
		} else {
			// forward="/front/article/error.jsp";
			messages = "<li>发表评论失败！</li>";
			request.setAttribute("messages", messages);
			return "fail";
		}
	}

	/**
	 * @功能 阅读文章
	 * @实现 1.增加文章阅读次数<br>
	 * 	2.获取指定文章信息<br>
	 * 	3.获取对该文章的所有评论
	 */
	public String readArticle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		ArticleBean articleBean = new ArticleBean();
		ArticleDao articleDao = new ArticleDao();
		ReviewDao reviewDao = new ReviewDao();
		request.setCharacterEncoding("utf-8");
		String strId = request.getParameter("id");
		int id = Integer.parseInt(strId);
		articleBean.setId(id);

		articleDao.operationArticle("readTimes", articleBean); // 累加阅读次数
		articleBean = articleDao.queryArticleSingle(id); // 查询指定文章的详细内容
		session.setAttribute("readSingle", articleBean); // 保存articleBean到request对象中

		List reviewlist = reviewDao.queryReview(id);
		session.setAttribute("reviewlist", reviewlist);
		return "articleSingle";
		// RequestDispatcher rd =
		// request.getRequestDispatcher("/front/article/ArticleSingle.jsp");
		// rd.forward(request, response);
	}

	/**
	 * @功能 修改文章
	 */
	public String articleModify() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		if (isHaveLogin()) {
			ArticleDao articleDao = new ArticleDao();
			String type = request.getParameter("type");
			if (type == null)
				type = "";
			if (!type.equals("doModify")) {
				int id = Integer.parseInt(request.getParameter("id"));
				ArticleBean articleBean = articleDao.queryArticleSingle(id);
				request.setAttribute("modifySingle", articleBean);
				return "articleModify";
				// rd=request.getRequestDispatcher("/admin/article/ArticleModify.jsp");
				// rd.forward(request,response);
			} else {
				String messages = "";
				String href = "";
				boolean flag = validateArticle();
				if (flag) {
					// ArticleBean articleBean = new ArticleBean();
					if (articleBean != null) {
						articleBean.setId(Integer.parseInt(request.getParameter("id")));
					}
					boolean mark = articleDao.operationArticle("modify", articleBean);
					if (mark) {
						messages = "<li>修改成功！</li>";
						href = "<a href='" + request.getContextPath() + "/sys/article_admin_adminSelectList?typeId="
								+ session.getAttribute("showTypeId") + "'>[继续修改其他文章]</a>";
						// forward="/admin/success.jsp";
						request.setAttribute("messages", messages);
						request.setAttribute("href", href);
						return "successAdmin";
					} else {
						messages = "<li>修改失败！</li>";
						href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
						// forward="/admin/error.jsp";
					}
					request.setAttribute("messages", messages);
					request.setAttribute("href", href);
				} else {
					href = "<a href='javascript:window.history.go(-1)>[返回]</a>";
					// forward="/admin/error.jsp";
					request.setAttribute("href", href);
				}
			}
			return "failAdmin";
		} else {
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}

	/**
	 * @功能 后台-删除文章
	 */
	public String deleteArticle() {

		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		if (isHaveLogin()) {
			ArticleDao articleDao = new ArticleDao();
			ArticleBean articleBean = new ArticleBean();

			String messages = "";
			String href = "";
			articleBean.setId(Integer.parseInt(request.getParameter("id")));
			boolean mark = articleDao.operationArticle("delete", articleBean);
			if (mark) {
				String typeId = String.valueOf(request.getSession().getAttribute("showTypeId"));
				messages += "<li>删除文章成功！</li>";
				href = "<a href='" + request.getContextPath() + "/sys/article_admin_adminSelectList?typeId=" + typeId
						+ "'>[继续删除其他文章]</a>";
				request.setAttribute("messages", messages);
				request.setAttribute("href", href);
				return "successAdmin";
			} else {
				messages += "<li>删除文章失败！</li>";
				href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
				// forward="/admin/error.jsp";
			}
			request.setAttribute("messages", messages);
			request.setAttribute("href", href);
			return "failAdmin";
		} else {
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}

	/**
	 * @功能 验证发表文章信息
	 */
	public boolean validateArticle() {
		boolean mark = true;
		String messages = "";

		String typeId = String.valueOf(articleBean.getTypeId());
		String title = articleBean.getTitle();
		String create = articleBean.getCreate();
		String info = articleBean.getInfo();
		String content = articleBean.getContent();

		if (typeId == null || typeId.equals("")) {
			mark = false;
			messages += "<li>请选择 <b>文章类别！</b></li>";
		}
		if (title == null || title.equals("")) {
			mark = false;
			messages += "<li>请输入 <b>文章标题！</b></li>";
		}
		if (create == null || create.equals("")) {
			mark = false;
			messages += "<li>请选择 <b>文章来源！</b></li>";
		}
		if (info == null || info.equals("")) {
			mark = false;
			messages += "<li>请输入 <b>文章描述！</b></li>";
		}
		if (content == null || content.equals("")) {
			mark = false;
			messages += "<li>请输入 <b>文章内容！</b></li>";
		}
		if (mark) {
			if (title.length() > 20) {
				mark = false;
				messages += "<li><b>文章标题</b> 最多允许输入20个字符！</li>";
			}
			if (content.length() > 65535) {
				mark = false;
				messages += "<li><b>文章内容</b> 最多允许输入65535个字符！</li>";
			}
		}
		request.setAttribute("messages", messages);
		return mark;
	}

	public String articleAdd() {
		if (isHaveLogin()) {
			return "articleAdd";
		} else {
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}

	/**
	 * @功能 后台-增加文章
	 */
	public String addArticle() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		if (isHaveLogin()) {
			String messages = "";
			String href = "";
			// String forward="";

			boolean flag = false;
			try {
				flag = validateArticle();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flag) {
				if (articleBean != null) {
					articleBean.setSdTime(MyTools.changeTime(new Date()));
					articleBean.setCount(0);
					articleBean.setReview(0);
				}
				ArticleDao articleDao = new ArticleDao();
				boolean mark = articleDao.operationArticle("add", articleBean);
				if (mark) {
					messages = "<li>发表文章成功！</li>";
					href = "<a href='" + request.getContextPath() + "/sys/article_admin_articleAdd'>[继续发表]</a>";
					request.setAttribute("messages", messages);
					request.setAttribute("href", href);
					// forward="/admin/success.jsp";
					return "successAdmin";
				} else {
					messages = "<li>发表文章失败！</li>";
					href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
					request.setAttribute("messages", messages);
					request.setAttribute("href", href);
				}

			} else {
				href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
				request.setAttribute("href", href);
			}
			return "failAdmin";
		} else {
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}

	/**
	 * @功能 实现后台文章管理中的文章浏览功能
	 */
	public String adminSelectList() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		if(isHaveLogin()){
			HttpSession session = request.getSession();
			ArticleDao articleDao = new ArticleDao();
			String strId = request.getParameter("typeId");
			if (strId == null || strId.equals("") || strId.equals("null"))
				strId = "-1";
			int typeId = Integer.parseInt(strId);
			session.setAttribute("showTypeId", typeId);
			List articleList = articleDao.queryArticle(typeId, "all");
			request.setAttribute("articleList", articleList);
			return "articleListAdmin";
		}else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
		// RequestDispatcher
		// rd=request.getRequestDispatcher("/admin/article/ArticleList.jsp");
		// rd.forward(request,response);
	}

	public String articleSingleAdmin() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		int id = MyTools.strToint(request.getParameter("id"));
		ArticleDao articleDao = new ArticleDao();

		ArticleBean articleBean = articleDao.queryArticleSingle(id); // 查询指定文章的详细内容
		request.setAttribute("articleSingle", articleBean);
		return "adminarticleSingle";
		// RequestDispatcher
		// rd=request.getRequestDispatcher("/admin/article/ArticleSingle.jsp");
		// rd.forward(request,response);
	}

	/**
	 * @功能 从数据表中获取某类别下的所有文章
	 */
	public String selectArticle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArticleDao articleDao = new ArticleDao();
		String strId = request.getParameter("typeId");
		if (strId == null || strId.equals(""))
			strId = "-1";
		int typeId = Integer.parseInt(strId);
		List articleList = articleDao.queryArticle(typeId, "all");

		request.setAttribute("articleList", articleList);
		return "articleList";

	}

	public boolean validateType() {
		boolean mark = true;
		String messages = "";
		String typeName = null;
		String typeInfo = null;
		if (typeBean != null) {
			typeName = typeBean.getTypeName();
			typeInfo = typeBean.getTypeInfo();
		}
		if (typeName == null || typeName.equals("")) {
			mark = false;
			messages += "<li>请输入 <b>类别名称！</b></li>";
		}
		if (typeInfo == null || typeInfo.equals("")) {
			mark = false;
			messages += "<li>请输入 <b>类别介绍！</b></li>";
		}
		request.setAttribute("messages", messages);
		return mark;
	}

	/**
	 * @功能 后台-增加文章类别
	 */
	public String addArticleType() {

		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		if (isHaveLogin()) {
			String messages = "";
			String href = "";
			boolean flag = validateType();

			if (flag) {
				ArticleTypeDao articleTypeDao = new ArticleTypeDao();
				boolean mark = articleTypeDao.operationArticleType("add", typeBean);
				if (mark) {
					messages += "<li>添加文章类别成功！</li>";
					href = "<a href='" + request.getContextPath() + "/sys/article_admin_articleTypeAdd'>[继续添加文章类别]</a>";
					// forward="/admin/success.jsp";
					request.setAttribute("messages", messages);
					request.setAttribute("href", href);
					return "successAdmin";
				} else {
					messages += "<li>添加文章类别失败！</li>";
					href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
				}
				request.setAttribute("messages", messages);
				request.setAttribute("href", href);
			} else {
				href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
				request.setAttribute("href", href);
			}
			return "failAdmin";
		} else {
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}

	public String articleTypeList() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		ArticleTypeDao typeDao = new ArticleTypeDao();
		List typelist = typeDao.queryTypeList();
		request.setAttribute("typelist", typelist);
		return "articleTypeList";
	}

	/**
	 * @功能 后台-修改文章类别
	 */
	public String articleTypeModify() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		if (isHaveLogin()) {
			ArticleTypeDao typeDao = new ArticleTypeDao();

			String type = request.getParameter("type");
			if (type == null)
				type = "";
			if (!type.equals("doModify")) {
				int typeId = Integer.parseInt(request.getParameter("typeId"));
				typeBean = typeDao.queryTypeSingle(typeId);
				request.setAttribute("typeSingle", typeBean);
				return "articleTypeModify";
			} else {
				String messages = "";
				String href = "";
				boolean flag = validateType();
				if (flag) {
					typeBean.setId(Integer.parseInt(request.getParameter("typeId")));
					boolean mark = typeDao.operationArticleType("modify", typeBean);
					if (mark) {
						messages = "<li>修改类别成功！</li>";
						href = "<a href='" + request.getContextPath()
								+ "/sys/article_admin_articleTypeList'>[继续修改其他类别]</a>";

						request.setAttribute("messages", messages);
						request.setAttribute("href", href);
						return "successAdmin";
					} else {
						messages = "<li>修改失败！</li>";
						href = "<a href='javascript:window.history.go(-1)>[返回]</a>";
					}
					request.setAttribute("messages", messages);
					request.setAttribute("href", href);
				} else {
					href = "<a href='javascript:window.history.go(-1)>[返回]</a>";
					request.setAttribute("href", href);
				}
				return "failAdmin";
			}
		} else {
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}

	/**
	 * @功能 后台-删除文章类别
	 */
	public String deleteArticleType() {

		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		if (isHaveLogin()) {
			ArticleTypeDao typeDao = new ArticleTypeDao();
			ArticleTypeBean typeBean = new ArticleTypeBean();

			String messages = "";
			String href = "";

			typeBean.setId(Integer.parseInt(request.getParameter("typeId")));
			boolean mark = typeDao.operationArticleType("delete", typeBean);
			if (mark) {
				messages += "<li>删除类别成功！</li>";
				href = "<a href='" + request.getContextPath() + "/sys/article_admin_articleTypeList'>[继续删除其他类别]</a>";
				request.setAttribute("messages", messages);
				request.setAttribute("href", href);
				return "successAdmin";

			} else {
				messages += "<li>删除类别失败！</li>";
				href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
			}
			request.setAttribute("messages", messages);
			request.setAttribute("href", href);
			return "failAdmin";
		} else {
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}
}
