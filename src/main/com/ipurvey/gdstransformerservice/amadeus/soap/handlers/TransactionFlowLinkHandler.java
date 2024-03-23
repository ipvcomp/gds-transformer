package com.ipurvey.gdstransformerservice.amadeus.soap.handlers;

import java.util.UUID;

import javax.xml.ws.Holder;

import org.apache.cxf.endpoint.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amadeus.wsdl._2010._06.ws.link_v1.ConsumerType;
import com.amadeus.wsdl._2010._06.ws.link_v1.TransactionFlowLinkType;

/**
 * Manage Transaction Flow Link
 */
public class TransactionFlowLinkHandler extends HeaderHandler {

	private Holder<TransactionFlowLinkType> mLink;

	private static final Logger logger = LogManager.getLogger(TransactionFlowLinkHandler.class);

	public class TransactionFlowLinkAction {
		public static final int NONE = 0;
		public static final int NEW = 1;
		public static final int FOLLOW_UP = 2;
		public static final int RESET = 3;
	}

	public TransactionFlowLinkHandler(Client client) {
		super(client);
		mLink = new Holder<>();
	}

	public void handleLinkAction(int linkAction) {
		switch (linkAction) {
		case TransactionFlowLinkAction.NEW:
			newLink();
			break;
		case TransactionFlowLinkAction.FOLLOW_UP:
			followUpLink();
			break;
		case TransactionFlowLinkAction.RESET:
			resetLink();
			break;
		case TransactionFlowLinkAction.NONE:
			noLink();
			break;
		default:
			break;
		}

	}

	private void newLink() {
		logger.debug("New link");
		mLink.value = new TransactionFlowLinkType();
		ConsumerType consumer = new ConsumerType();
		consumer.setUniqueID(UUID.randomUUID().toString());
		mLink.value.setConsumer(consumer);
	}

	private void followUpLink() {
		logger.debug("Follow-up link");
		// Nothing to do
	}

	private void resetLink() {
		logger.debug("Reset link");
		mLink.value.setReceiver(null);
	}
	
	private void noLink() {
		logger.debug("No link");
		mLink.value = null;
	}

	public Holder<TransactionFlowLinkType> getLink() {
		return mLink;
	}

}
