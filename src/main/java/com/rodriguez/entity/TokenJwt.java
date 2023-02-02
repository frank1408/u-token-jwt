
package com.rodriguez.entity;

import java.io.Serializable;

public class TokenJwt implements Serializable {

	private static final long serialVersionUID = -1021859095927042844L;
	private final String token;
	
	public TokenJwt(String token) {
		this.token = token;
	}
	public String getToken() {
		return this.token;
	}
}
