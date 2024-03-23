package com.ipurvey.gdstransformerservice.amadeus.soap.handlers;

import org.apache.cxf.endpoint.Client;

public abstract class HeaderHandler {

	protected Client mClient;
	
	public HeaderHandler(Client client) {
		mClient = client;
	}
}
