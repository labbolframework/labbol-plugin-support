/**
 * 
 */
package com.labbol.core.rights;

import com.labbol.core.exception.RequestException;

/**
 * 越权访问
 * 
 * @author PengFei
 * @since 1.0.7
 */
public class AccessDenialException extends RequestException {

	private static final long serialVersionUID = 2645319618800119492L;

	public static final int STATUS = 901;

	public AccessDenialException() {
		super(STATUS);
	}

	public AccessDenialException(String message) {
		super(STATUS, message);
	}

}
