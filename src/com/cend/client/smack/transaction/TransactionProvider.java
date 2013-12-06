package com.cend.client.smack.transaction;

import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider;;

public class TransactionProvider extends EmbeddedExtensionProvider {

	@Override
	protected PacketExtension createReturnExtension(String currentElement, String currentNamespace,
			Map<String, String> attributeMap, List<? extends PacketExtension> content) {
		
		return new TransactionExtension(attributeMap.get("transactionId"));
	}

}
