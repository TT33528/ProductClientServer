package com.edu.service;

import com.edu.beans.User;

public interface IUserService {
	void saveUser(User user);
	boolean login(User user);
}