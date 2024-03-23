package com.ipurvey.gdstransformerservice.amadeus.soap.handlers;

import javax.xml.ws.Holder;
import org.apache.cxf.endpoint.Client;

import com.amadeus.xml._2010._06.session_v3.Session;

/**
 * This class builds the session elements in the SOAP header, and allows to manage sessions 
 */
public class SessionHandler extends HeaderHandler {

	private Holder<Session> mSession;
	private SecurityHandler mSecurity;

	public class TransactionStatusCode {
		public static final int NONE = 0;
		public static final int START = 1;
		public static final int CONTINUE = 2;
		public static final int END = 3;
	}

	public SessionHandler(Client client, SecurityHandler security) {
		super(client);
		mSession = new Holder<Session>();
		mSecurity = security;
	}

	public void handleSessionStatus(int transactionStatusCode) {
		switch (transactionStatusCode) {
		case TransactionStatusCode.START:
			startSession();
			break;
		case TransactionStatusCode.CONTINUE:
			continueSession();
			break;
		case TransactionStatusCode.END:
			endSession();
			break;
		case TransactionStatusCode.NONE:
			mSecurity.fill();
			resetSession();
			break;
		default:
			break;
		}
	} 

	private void startSession() {
		mSecurity.fill();
		mSession.value = new Session();
		mSession.value.setTransactionStatusCode("Start");
	}

	private void continueSession() {
		mSecurity.reset();
		Integer sequenceNumber = Integer.parseInt(mSession.value
				.getSequenceNumber());
		sequenceNumber++;
		mSession.value.setSequenceNumber(sequenceNumber.toString());
	}

	private void endSession() {
		continueSession();
		mSession.value.setTransactionStatusCode("End");
	}
	
	private void resetSession() {
		mSession.value = null;
	}

	public Holder<Session> getSession() {
		return mSession;
	}

}
