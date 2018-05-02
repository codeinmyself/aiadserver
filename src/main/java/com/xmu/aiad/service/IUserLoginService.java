package com.xmu.aiad.service;

import com.xmu.aiad.model.User;

import java.math.BigInteger;
import java.sql.Timestamp;

public interface IUserLoginService {

     User login(long telephone, String password);

     boolean logout(String userId, Timestamp logoutTime);


}
