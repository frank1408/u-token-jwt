
package com.rodriguez.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class Mipasswordencoder implements PasswordEncoder {
	
	/*
	 * creado para NO codificar/cifrar
	 * es su unico proposito !
	 */

	@Override
	public String encode(CharSequence arg0) {
		return arg0.toString();
	}

	@Override
	public boolean matches(CharSequence arg0, String arg1) {
		return true;
	}

}
