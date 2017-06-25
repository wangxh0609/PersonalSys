package com.hust.docMgr.role.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hust.docMgr.role.dao.RoleDao;
import com.hust.docMgr.role.entity.Role;
import com.hust.docMgr.role.service.RoleService;
import com.hust.newcore.service.impl.BaseServiceImpl;


@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	
	private RoleDao roleDao;
	@Resource
	public void setRoleDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
		this.roleDao = roleDao;
	}

	@Override
	public void update(Role role) {		
		roleDao.deleteRolePrivilegeByRoleId(role.getRoleId());
		roleDao.update(role);
	}

}
