package com.cend.client.smack.transaction;

import org.jivesoftware.smack.packet.PacketExtension;

public class TransactionExtension implements PacketExtension {
	
	public static final String NAMESPACE = "urn:xmpp:transaction";
	
	public TransactionExtension(String transactionId){
		this.transactionId = transactionId;
	}
	
	private String transactionId;
	public String getTransactionId() { return transactionId; }

	@Override
	public String getElementName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}

	@Override
	public String toXML() {
		StringBuilder builder = new StringBuilder("<transaction xmlns=\"");
		builder.append(getNamespace()).append("\">");
		builder.append(transactionId);
		builder.append("</transaction>");
		return builder.toString();
	}

}
