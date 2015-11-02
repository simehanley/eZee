package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EZEE_USER")
public class EzeeUser extends EzeeDatabaseEntity implements EzeeHasName {

	private static final long serialVersionUID = -6217125456081902517L;

	@Column(name = "FIRST_NAME")
	private String firstname;

	@Column(name = "LAST_NAME")
	private String lastname;

	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	private String email;

	public EzeeUser() {
		super();
	}

	public EzeeUser(final String firstname, final String lastname, final String username, final String password,
			final String email, final Date created, final Date updated) {
		this(NULL_ID, firstname, lastname, username, password, email, created, updated);
	}

	public EzeeUser(final Long id, final String firstname, final String lastname, final String username,
			final String password, final String email, final Date created, final Date updated) {
		super(id, created, updated);
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	@Override
	public final String getName() {
		return getUsername();
	}

	public final String getFirstname() {
		return firstname;
	}

	public final String getLastname() {
		return lastname;
	}

	public final String getUsername() {
		return username;
	}

	public final String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public final String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "EzeeUser [username=" + username + "]";
	}
}