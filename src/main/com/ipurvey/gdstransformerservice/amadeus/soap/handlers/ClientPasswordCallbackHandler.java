package com.ipurvey.gdstransformerservice.amadeus.soap.handlers;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;


import com.ipurvey.gdstransformerservice.amadeus.soap.Constants;
import org.apache.wss4j.common.ext.WSPasswordCallback;

/**
 * Password callback handler used to set the password
 */
public class ClientPasswordCallbackHandler implements CallbackHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.security.auth.callback.CallbackHandler#handle(javax.security.auth
	 * .callback.Callback[])
	 */
	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		if (callbacks.length > 0) {
			WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
			try {
				/*
				* As per the Amadeus SOAP 4.0 implementation guide, the digest generation algorithm is as follows:
				*		Base64(SHA1($NONCE + $TIMESTAMP + SHA1($CLEARPASSWORD)))
				*
				* However, CXF expects the password to come in the following format:
				* 		Base64(SHA1($NONCE + $TIMESTAMP + BASE64(SHA1($CLEARPASSWORD))))
				*
				* So we perform an extra base64 encoding so CXF can do the digest without additional input.
				*/
				byte[] passwordSha1 = MessageDigest.getInstance("SHA-1").digest(Constants.WSAP_password.getBytes());
				pc.setPassword(Base64.getEncoder().encodeToString(passwordSha1));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
	}

}
