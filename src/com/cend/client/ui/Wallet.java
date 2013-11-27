package com.cend.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cend.client.R;

public class Wallet extends Activity {
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.wallet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contact_list, menu);
		return true;
	}

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

}
