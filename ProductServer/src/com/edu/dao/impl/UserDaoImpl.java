package com.edu.dao.impl;

import com.edu.beans.User;
import com.edu.dao.IUserDao;
import com.edu.utils.DbHelper;

public class UserDaoImpl implements IUserDao{

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		String sql = "insert into users(name,pwd) values(?,?)";
		DbHelper.update(sql, user.getName(), user.getPwd());
	}

	@Override
	public boolean login(User user) {
		// TODO Auto-generated method stub
		String sql = "select * from users where name=? and pwd=?";
		User u = DbHelper.getBean(User.class, sql, user.getName(), user.getPwd());
		if(u == null) {//֤����½ʧ��
			return false;
		}else //��½�ɹ�
			return true;
	}
	
}