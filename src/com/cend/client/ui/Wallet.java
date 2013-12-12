package com.cend.client.ui;

import java.util.ArrayList;

import org.jivesoftware.smack.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.cend.client.R;
import com.cend.client.service.Contact;
import com.cend.client.service.Message;
import com.cend.client.service.WalletCurrency;
import com.cend.client.service.aidl.IChat;
import com.cend.client.service.aidl.IChatManager;
import com.cend.client.service.aidl.IMessageListener;
import com.cend.client.service.aidl.IXmppFacade;

public class Wallet extends Activity {
	
	private static final Intent SERVICE_INTENT = new Intent();
	static {
		SERVICE_INTENT.setComponent(new ComponentName("com.cend.client",
				"com.cend.client.BeemService"));
	}
	private IXmppFacade mXmppFacade;
	
	private final IMessageListener mMessageListener = new CendMessageListener();
	private IChatManager mChatManager;
	private IChat mChat;
	private boolean mBinded = false;
	private Handler mHandler = new Handler();
	private final ServiceConnection mServConn = new CendServiceConnection();
	private Contact mContact = new Contact("cend@cendhome.com");
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.wallet);
		getActionBar().setDisplayHomeAsUpEnabled(true);		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if (!mBinded)
			mBinded = bindService(Wallet.SERVICE_INTENT, mServConn, BIND_AUTO_CREATE);
	}
	
	private void retrieveWallet(){
		try {
			mChat = mChatManager.createChat(mContact, mMessageListener);
			mChat.setOpen(true);
			Message msgToSend = new Message("cend@cendhome.com", Message.MSG_TYPE_CHAT);
			msgToSend.setBody("cendwallet");
			mChat.sendMessage(msgToSend);
		}
		catch (RemoteException e) {
			 Log.e("Wallet", e.getMessage()); 
		}
		catch(Exception e){
			 Log.e("Wallet", e.getMessage()); 
		}
	}

	private void updateWalletView(String wallet) {
		try{
			ArrayList<WalletCurrency> currencies = new ArrayList<WalletCurrency>();
			currencies.addAll(WalletCurrency.fromJson(new JSONArray(wallet)));
			WalletCurrenciesAdapter adapter = new WalletCurrenciesAdapter(this, 0);
			adapter.addAll(currencies);
			ListView lv = (ListView) findViewById(R.id.walletListView);
			lv.setAdapter(adapter);
		}
		catch(JSONException ex){
			Log.e("Update wallet view: ", ex.getMessage());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contact_list, menu);
		return true;
	}

	@Override
	public final boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.contact_list_menu_settings:
			startActivity(new Intent(this, Settings.class));
			return true;
		case R.id.contact_list_menu_add_contact:
			startActivity(new Intent(Wallet.this, AddContact.class));
			return true;
		case R.id.menu_change_status:
			startActivity(new Intent(Wallet.this, ChangeStatus.class));
			return true;
		case R.id.menu_friends:
			startActivity(new Intent(Wallet.this, ContactList.class));
			return true;
		case R.id.menu_wallet:
			startActivity(new Intent(Wallet.this, this.getClass()));
			return true;
		case R.id.contact_list_menu_chatlist:
			return false;
		case R.id.menu_disconnect:
			return false;
		default:
			return false;
		}
	}
	
private class CendServiceConnection implements ServiceConnection {
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mXmppFacade = IXmppFacade.Stub.asInterface(service);
			try{
				mChatManager = mXmppFacade.getChatManager();
				retrieveWallet();
			}
			catch (RemoteException e) {
				 Log.e("Wallet", e.getMessage()); 
			}
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mXmppFacade = null;
			mChatManager = null;
		}
	}
	
	private class CendMessageListener extends IMessageListener.Stub {

		/**
		 * Constructor.
		 */
		public CendMessageListener() {
		}

		/**
		 * {@inheritDoc}.
		 */
		@Override
		public void processMessage(IChat chat, final Message msg) throws RemoteException {
			final String fromBareJid = StringUtils.parseBareAddress(msg.getFrom());
			
			//Log.d("Wallet processMessage: ", msg.getBody());
			
			if (mContact.getJID().equals(fromBareJid)) {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						if (msg.getType() == Message.MSG_TYPE_ERROR) {
						} 
						else if (msg.getBody() != null) {
							//Log.d("Wallet Response: ", msg.getBody());
							updateWalletView(msg.getBody());
						}
					}
				});
			}
		}

		/**
		 * {@inheritDoc}.
		 */
		@Override
		public void stateChanged(IChat chat) throws RemoteException {
			final String state = chat.getState();

		}

		@Override
		public void otrStateChanged(final String otrState) throws RemoteException {

		}
	}


}