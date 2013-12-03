package com.cend.client.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WalletCurrency {
	public Long id;
	public String name;
	public BigDecimal balance;

	public WalletCurrency(JSONObject object) {
		try {
			this.id = object.getLong("id");
			this.name = object.getString("name");
			this.balance = new BigDecimal(object.getString("balance"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	 // Currency.fromJson(jsonArray);
    public static ArrayList<WalletCurrency> fromJson(JSONArray jsonObjects) {
           ArrayList<WalletCurrency> currencies = new ArrayList<WalletCurrency>();
           for (int i = 0; i < jsonObjects.length(); i++) {
               try {
                  currencies.add(new WalletCurrency(jsonObjects.getJSONObject(i)));
               } catch (JSONException e) {
                  e.printStackTrace();
               }
          }
          return currencies;
    }
}
