package com.cend.client.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.cend.client.R;
import com.cend.client.service.WalletCurrency;

public class Wallet extends Activity {
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.wallet);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		JSONArray currArray = new JSONArray();
		JSONObject imvu = new JSONObject();
		JSONObject lindens = new JSONObject();
		JSONObject usd = new JSONObject();
		JSONObject euro = new JSONObject();
		try {
			imvu.put("id", 112);
			imvu.put("name", "IMVU CREDITS");
			imvu.put("balance", 22000);
			lindens.put("id", 122);
			lindens.put("name", "LINDENS");
			lindens.put("balance", 12000);
			usd.put("id", 231);
			usd.put("name", "USD");
			usd.put("balance", 22);
			euro.put("id", 22121);
			euro.put("name", "EURO");
			euro.put("balance", 20);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currArray.put(imvu);
		currArray.put(lindens);
		currArray.put(usd);
		currArray.put(euro);
		ArrayList<WalletCurrency> currencies = new ArrayList<WalletCurrency>();
		currencies.addAll(WalletCurrency.fromJson(currArray));
		WalletCurrenciesAdapter adapter = new WalletCurrenciesAdapter(this, 0);
		adapter.addAll(currencies);
		ListView lv = (ListView) findViewById(R.id.walletListView);
		lv.setAdapter(adapter);
		
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