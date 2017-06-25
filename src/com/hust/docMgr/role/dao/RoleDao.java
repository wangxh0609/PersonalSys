package com.hust.docMgr.role.dao;

import com.hust.docMgr.role.entity.Role;
import com.hust.newcore.dao.BaseDao;

public interface RoleDao extends BaseDao<Role> {


	public void deleteRolePrivilegeByRoleId(String roleId);

}
