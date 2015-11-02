package com.ezee.dao;

import org.jasypt.util.password.PasswordEncryptor;

import com.ezee.model.entity.EzeeUser;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeUserDao extends EzeeBaseDao<EzeeUser> {

	PasswordEncryptor geEncryptor();

	EzeeUser get(String username, String email);
}