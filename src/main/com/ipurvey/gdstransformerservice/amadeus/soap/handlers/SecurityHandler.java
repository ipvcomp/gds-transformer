package com.ipurvey.gdstransformerservice.amadeus.soap.handlers;

import java.util.HashMap;
import java.util.Map;

import com.ipurvey.gdstransformerservice.amadeus.soap.Constants;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wss4j.common.WSS4JConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.iata.iata._2007._00.iata2010.CompanyNameType;
import org.iata.iata._2007._00.iata2010.UniqueIDType;

import com.amadeus.xml._2010._06.security_v1.AMASecurityHostedUser;
import com.amadeus.xml._2010._06.security_v1.AMASecurityHostedUser.UserID;



/**
 * This class builds the OASIS security and Amadeus hostedUser elements in the SOAP header.
 * These elements are used for authentication and officeID / pseudoCityCode identification.
 */
public class SecurityHandler extends HeaderHandler {

	private WSS4JOutInterceptor wssOut;
	private AMASecurityHostedUser mHostedUser;

	private static final Logger logger = LogManager.getLogger(SecurityHandler.class);

	public SecurityHandler(Client client) {
		super(client);
	}

	/**
	 * Fills the security headers
	 * <p>
	 * Adds the WS-Security header according to the policy and sets the
	 * <code>AMA_SecurityHostedUser</code> with the relevant values.
	 */
	public void fill() {
		// Reset the security handler first to remove all previously added
		// headers
		reset();
		addWSSecurity();
		setHostedUser();
	}

	/**
	 * Removes all the security headers
	 */
	public void reset() {
		mHostedUser = null;
		removeWSSecurity();
	}

	private void removeWSSecurity() {
		logger.debug("Remove WS-Security");
		if (wssOut != null) {
			org.apache.cxf.endpoint.Endpoint endpoint = mClient.getEndpoint();
			endpoint.getOutInterceptors().remove(wssOut);
		}
	}

	private void addWSSecurity() {
		logger.debug("Add WS-Security");
		
		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION,	WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, Constants.WSAP_username);
		outProps.put(WSHandlerConstants.PASSWORD_TYPE,		WSS4JConstants.PW_DIGEST);
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,	ClientPasswordCallbackHandler.class.getName());
		outProps.put(WSHandlerConstants.USE_ENCODED_PASSWORDS,	"true");
		wssOut = new WSS4JOutInterceptor(outProps);
		wssOut.setId(Constants.WSAP_username);
		mClient.getEndpoint().getOutInterceptors().add(wssOut);
	}
	
	/**
	 * Adds the <code>AMA_SecurityHostedUser</code> header to the outgoing
	 * message.
	 * <p>
	 * Sets the following values:
	 * <ul>
	 * <li><b>Office ID</b> in the <code>PseudoCityCode</code> attribute</li>
	 * <li><b>Duty code</b> in the <code>AgentDutyCode</code> attribute</li>
	 * <li><b>RequestorType</b> to "U" as the LSS user is used</li>
	 * <li><b>Organization</b> in the <code>CompanyName</code> element</li>
	 * </ul>
	 */
	private void setHostedUser() {
		logger.debug("Set Security hosted user");
		
		com.amadeus.xml._2010._06.security_v1.ObjectFactory securityFactory = new com.amadeus.xml._2010._06.security_v1.ObjectFactory();
		mHostedUser = securityFactory.createAMASecurityHostedUser();
		UserID userId = securityFactory.createAMASecurityHostedUserUserID();
		userId.setAgentDutyCode(Constants.WSAP_agentDutyCode);
		userId.setRequestorType(Constants.WSAP_requestorType);
		userId.setPOSType(Constants.WSAP_POSType);
		userId.setPseudoCityCode(Constants.WSAP_officeID);
		org.iata.iata._2007._00.iata2010.ObjectFactory iataFactory = new org.iata.iata._2007._00.iata2010.ObjectFactory();
		UniqueIDType uniqueId = iataFactory.createUniqueIDType();
		CompanyNameType organization = iataFactory.createCompanyNameType();
		organization.setValue(Constants.WSAP_organization);
		uniqueId.setCompanyName(organization);
		userId.setRequestorID(uniqueId);
		mHostedUser.setUserID(userId);
	}

	/**
	 * Returns the container for <code>AMA_SecurityHostedUser</code> header
	 */
	public AMASecurityHostedUser getHostedUser() {
		return mHostedUser;
	}

}
