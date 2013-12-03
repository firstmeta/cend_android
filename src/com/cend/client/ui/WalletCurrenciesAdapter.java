package com.cend.client.ui;

import com.cend.client.R;
import com.cend.client.service.WalletCurrency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WalletCurrenciesAdapter extends ArrayAdapter<WalletCurrency> {

	private static class ViewHolder {
		TextView name;
		TextView balance;
	}

	public WalletCurrenciesAdapter(Context context, int resource) {
		super(context, R.layout.currencylistcurrency);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		WalletCurrency currency = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.currencylistcurrency, null);
			viewHolder.name = (TextView) convertView.findViewById(R.id.currencyName);
			viewHolder.balance = (TextView) convertView.findViewById(R.id.currencyBalance);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Populate the data into the template view using the data object
		viewHolder.name.setText(currency.name);
		viewHolder.balance.setText(currency.balance.toString());
		// Return the completed view to render on screen
		return convertView;
	}

}
