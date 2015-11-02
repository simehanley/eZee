package com.ezee.server;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;

import java.util.Date;

import org.jasypt.util.password.PasswordEncryptor;

import com.ezee.dao.EzeeUserDao;
import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.service.EzeeUserService;
import com.ezee.web.common.ui.login.EzeeLoginResult;
import com.ezee.web.common.ui.register.EzeeRegisterResult;

/**
 * 
 * @author siborg
 *
 */
public class EzeeUserServiceImpl extends AbstractRemoteService implements EzeeUserService {

	private static final long serialVersionUID = -3642962271823671064L;

	@Override
	public EzeeRegisterResult register(final String firstname, final String lastname, final String username,
			final String password, final String email) {
		EzeeUserDao dao = getDao();
		if (dao.get(username, email) != null) {
			return new EzeeRegisterResult(null,
					"User with username = '" + username + "' and/or email = '" + email + "' already exists.");
		}
		EzeeUser user = new EzeeUser(firstname, lastname, username, password, email, new Date(), new Date());
		dao.save(user);
		return new EzeeRegisterResult(user, EMPTY_STRING);
	}

	@Override
	public EzeeLoginResult authenticate(final String username, final String password) {
		EzeeUserDao dao = getDao();
		EzeeUser user = dao.get(username, EMPTY_STRING);
		if (user != null) {
			PasswordEncryptor encryptor = dao.geEncryptor();
			if (encryptor.checkPassword(password, user.getPassword())) {
				return new EzeeLoginResult(user, EMPTY_STRING);
			} else {
				return new EzeeLoginResult(null, "Password is incorrect for user '" + username + "'.");
			}
		}
		return new EzeeLoginResult(null, "Unable to find user with username '" + username + "'.");
	}

	private EzeeUserDao getDao() {
		return getSpringBean(EzeeUserDao.class);
	}
}