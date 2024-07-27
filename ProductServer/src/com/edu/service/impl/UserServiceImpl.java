package com.edu.service.impl;

import com.edu.beans.User;
import com.edu.dao.IUserDao;
import com.edu.dao.impl.UserDaoImpl;
import com.edu.service.IUserService;

public class UserServiceImpl implements IUserService{

	private IUserDao userDao = new UserDaoImpl();
	
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		userDao.saveUser(user);
	}

	@Override
	public boolean login(User user) {
		// TODO Auto-generated method stub
		return userDao.login(user);
	}

}