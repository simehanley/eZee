package com.ezee.server;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;

import org.jasypt.util.password.PasswordEncryptor;

import com.ezee.dao.EzeeUserDao;
import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.service.EzeeUserService;
import com.ezee.web.common.ui.EzeeUserResult;

/**
 * 
 * @author siborg
 *
 */
public class EzeeUserServiceImpl extends AbstractRemoteService implements EzeeUserService {

	private static final long serialVersionUID = -3642962271823671064L;

	@Override
	public EzeeUserResult register(final EzeeUser created) {
		EzeeUserDao dao = getDao();
		if (dao.get(created.getUsername(), created.getEmail()) != null) {
			return new EzeeUserResult(null, "User with username = '" + created.getUsername() + "' and/or email = '"
					+ created.getEmail() + "' already exists.");
		}
		dao.save(created);
		return new EzeeUserResult(created, EMPTY_STRING);
	}

	@Override
	public EzeeUserResult authenticate(final String username, final String password) {
		EzeeUserDao dao = getDao();
		EzeeUser user = dao.get(username);
		if (user != null) {
			PasswordEncryptor encryptor = dao.geEncryptor();
			if (encryptor.checkPassword(password, user.getPassword())) {
				return new EzeeUserResult(user, EMPTY_STRING);
			} else {
				return new EzeeUserResult(null, "Password is incorrect for user '" + username + "'.");
			}
		}
		return new EzeeUserResult(null, "Unable to find user with username '" + username + "'.");
	}

	@Override
	public EzeeUserResult retrieve(final String username) {
		EzeeUserDao dao = getDao();
		EzeeUser user = dao.get(username);
		if (user != null) {
			return new EzeeUserResult(user, EMPTY_STRING);
		}
		return new EzeeUserResult(null, "Unable to find user with username '" + username + "'.");
	}

	@Override
	public EzeeUserResult edit(final EzeeUser existing, final EzeeUser edited, final String passwordCheck) {
		EzeeUserDao dao = getDao();
		PasswordEncryptor encryptor = dao.geEncryptor();
		if (encryptor.checkPassword(passwordCheck, existing.getPassword())) {
			dao.save(edited);
			return new EzeeUserResult(edited, EMPTY_STRING);
		} else {
			return new EzeeUserResult(null, "Existing user password is incorrect.");
		}
	}

	private EzeeUserDao getDao() {
		return getSpringBean(EzeeUserDao.class);
	}
}