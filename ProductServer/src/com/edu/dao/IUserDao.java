package com.edu.dao;

import com.edu.beans.User;

public interface IUserDao {
	void saveUser(User user);
	boolean login(User user);
}