package com.ipurvey.gdstransformerservice.amadeus.soap.handlers;

import java.util.Map;


import com.ipurvey.gdstransformerservice.amadeus.soap.Constants;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.JAXWSAConstants;

/**
 * This class builds the addressing element in the SOAP header
 */
public class AddressingHandler extends HeaderHandler {
	
	private Map<String, Object> mContext;
	private String mWSAP;
	private String mAddress;
	
	public AddressingHandler(Client client, String WSAP) {
		super(client);
		mContext = mClient.getRequestContext();
		mWSAP = WSAP;
		mAddress = Constants.WSAP_hostAddress;
		if(mAddress == null || mAddress.length() == 0) {
			mAddress =  mClient.getConduit().getTarget().getAddress().getValue();
			// remove the default wsap from address
			mAddress = mAddress.substring(0, mAddress.lastIndexOf('/'));
		}
	}

	public void setWSAP(String WSAP) {
		mWSAP = WSAP;
	}
	
	public String getWSAP() {
		return mWSAP;
	}

	public void update() {
		setWSAP();		
	}

	private void setWSAP() {
		// create empty request properties to reset all properties
		// this is necessary to reset the values from the previous message
		// otherwise, CXF will send the information stored from the last call
		AddressingProperties requestProperties = new AddressingProperties();
		// set wsa:To
		AttributedURIType to = new AttributedURIType();
		to.setValue(mAddress + "/" + mWSAP);
		// put wsa:To in request context and reset all other properties
		requestProperties.setTo(to);
		mContext.put(JAXWSAConstants.CLIENT_ADDRESSING_PROPERTIES, requestProperties);
	}

}
