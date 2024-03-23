package com.ipurvey.gdstransformerservice.amadeus.soap.handlers;



import com.amadeus.xml.fmptbr_23_2_1a.FareMasterPricerTravelBoardSearchReply;
import com.amadeus.xml.qdqlrr_11_1_1a.QueueListReply;
import com.ipurvey.gdstransformerservice.amadeus.soap.Constants;
import com.ipurvey.gdstransformerservice.amadeus.soap.MessageFactory;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.amadeus.xml.AmadeusWebServices;
import com.amadeus.xml.AmadeusWebServicesPT;

import javax.xml.ws.soap.AddressingFeature;
import javax.xml.ws.soap.SOAPFaultException;


public class ServiceHandler {
	private static final Logger logger = LogManager.getLogger(ServiceHandler.class);

	private AmadeusWebServicesPT mPortType;
	private SessionHandler mSession;
	private TransactionFlowLinkHandler mLink;
	private SecurityHandler mSecurity;
	private AddressingHandler mAddressing;

	public ServiceHandler(String WSAP) {
		AmadeusWebServices service = new AmadeusWebServices();
		mPortType = service.getAmadeusWebServicesPort(new AddressingFeature());
		Client client = ClientProxy.getClient(mPortType);
		LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
		loggingInInterceptor.setPrettyLogging(true);
		client.getInInterceptors().add(loggingInInterceptor);
		client.getOutInterceptors().add(loggingInInterceptor);
		// Snippet of code to inject your own endpoint if you wish to target different endpoints
		// depending on the amadeus config property file. The cleanest solution is to not define
		// a custom endpoint address or WSAP and let the WSDL define these.
		String address = Constants.WSAP_hostAddress;
		if(address != null && address.length() > 0) {
			client.getRequestContext().put(Message.ENDPOINT_ADDRESS, address + "/" + WSAP);
		}

		mSecurity = new SecurityHandler(client);
		mSession = new SessionHandler(client, mSecurity);
		mLink = new TransactionFlowLinkHandler(client);
		mAddressing = new AddressingHandler(client, WSAP);
	}

	public FareMasterPricerTravelBoardSearchReply fareMasterPricerTravelBoardSearchReply(int transactionStatusCode, int linkStatusCode) throws Exception {
		mSession.handleSessionStatus(transactionStatusCode);
		mLink.handleLinkAction(linkStatusCode);
		mAddressing.update();

		FareMasterPricerTravelBoardSearchReply boardSearchReply = null;
		try {

	   boardSearchReply = mPortType.fareMasterPricerTravelBoardSearch(MessageFactory.buildFlightRequest(), mSession.getSession(), mLink.getLink(), mSecurity.getHostedUser());

		} catch (SOAPFaultException soapFault) {
			logger.error(soapFault.toString());
			throw new Exception("FareMasterPricerTravelBoardSearch call failed due to SOAP fault.");
		} catch (Throwable t) {
			t.printStackTrace();
			logger.error(t.toString());
			throw new Exception("FareMasterPricerTravelBoardSearch call failed due to unknown error.");
		}

		return boardSearchReply;
	}


	public QueueListReply listPnrs(int transactionStatusCode, int linkStatusCode){
		mSession.handleSessionStatus(transactionStatusCode);
		mLink.handleLinkAction(linkStatusCode);
		mAddressing.update();

		QueueListReply pnrListReply = null;
		try {
		pnrListReply = mPortType.queueList(MessageFactory.buildPnrQueueListRequest(), mSession.getSession(), mLink.getLink(), mSecurity.getHostedUser());

		} catch (SOAPFaultException soapFault) {
			logger.error(soapFault.toString());
			throw new RuntimeException("FareMasterPricerTravelBoardSearch call failed due to SOAP fault.");
		} catch (Throwable t) {
			logger.error(t.toString());
			throw new RuntimeException("FareMasterPricerTravelBoardSearch call failed due to unknown error.");
		}

		return pnrListReply;

	}
//	public AirSellFromRecommendationReply airSellFromRecommendationReply(int transactionStatusCode, int linkStatusCode) throws Exception {
//		mSession.handleSessionStatus(transactionStatusCode);
//		mLink.handleLinkAction(linkStatusCode);
//		mAddressing.update();
//
//		AirSellFromRecommendationReply fromRecommendationReply = null;
//		try {
//			fromRecommendationReply = mPortType.airSellFromRecommendation(MessageFactory.airSellFromRecommendation(), mSession.getSession(), mLink.getLink(), mSecurity.getHostedUser());
//
//		} catch (SOAPFaultException soapFault) {
//			logger.error(soapFault.toString());
//			throw new Exception("AirSellFromRecommendation call failed due to SOAP fault.");
//		} catch (Throwable t) {
//			logger.error(t.toString());
//			throw new Exception("AirSellFromRecommendation call failed due to unknown error.");
//		}
//
//		return fromRecommendationReply;
//	}


	public void setWSAP(String WSAP) {
		mAddressing.setWSAP(WSAP);
	}

	public String getWSAP() {
		return mAddressing.getWSAP();
	}

}
