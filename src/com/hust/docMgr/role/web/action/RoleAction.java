package com.hust.docMgr.role.web.action;

import com.hust.core.action.BaseAction;
import com.hust.core.page.Constant;
import com.hust.docMgr.role.entity.Role;
import com.hust.docMgr.role.service.RoleService;
import com.opensymphony.xwork2.ActionContext;

public class RoleAction extends BaseAction {
	private RoleService roleService;
	private Role role;
	private String[] privilegeIds;
	private String strName;
	
	
	public String listUI(){
		/*
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		QueryHelper queryHelper = new QueryHelper(Role.class, "r");
		try {
			if(role != null){
				if(StringUtils.isNotBlank(role.getName())){
					role.setName(URLDecoder.decode(role.getName(), "utf-8"));
					queryHelper.addCondition("r.name like ?", "%" + role.getName() + "%");
				}
				
			}
			//pageResult = roleService.getPageResult(queryHelper, getPageNo(), getPageSize());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}*/
		
		
		return "listUI";
	}
	
	public String addUI(){
		
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		return "addUI";
	}

}
