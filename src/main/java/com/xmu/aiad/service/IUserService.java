package com.xmu.aiad.service;

import com.xmu.aiad.model.User;

import java.math.BigInteger;

public interface IUserService {

    public User getUserById(int id);

    int updateUser(User user);

    boolean addUser(long telephone, String password);
}
