package com.hust.docMgr.role.daoImpl;

import org.hibernate.Query;

import com.hust.docMgr.role.dao.RoleDao;
import com.hust.docMgr.role.entity.Role;
import com.hust.newcore.dao.impl.BaseDaoImpl;



public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	@Override
	public void deleteRolePrivilegeByRoleId(String roleId) {
		Query query = getSession().createQuery("DELETE FROM RolePrivilege WHERE id.role.roleId=?");
		query.setParameter(0, roleId);
		query.executeUpdate();
	}

}
