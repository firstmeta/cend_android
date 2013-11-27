/*
    BEEM is a videoconference application on the Android Platform.

    Copyright (C) 2009 by Frederic-Charles Barthelery,
                          Jean-Manuel Da Silva,
                          Nikita Kozlov,
                          Philippe Lago,
                          Jean Baptiste Vergely,
                          Vincent Veronis.

    This file is part of BEEM.

    BEEM is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    BEEM is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with BEEM.  If not, see <http://www.gnu.org/licenses/>.

    Please send bug reports with examples or suggestions to
    contact@beem-project.com or http://dev.beem-project.com/

    Epitech, hereby disclaims all copyright interest in the program "Beem"
    written by Frederic-Charles Barthelery,
               Jean-Manuel Da Silva,
               Nikita Kozlov,
               Philippe Lago,
               Jean Baptiste Vergely,
               Vincent Veronis.

    Nicolas Sadirac, November 26, 2009
    President of Epitech.

    Flavien Astraud, November 26, 2009
    Head of the EIP Laboratory.

 */
package com.cend.client.ui.wizard;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.cend.client.R;

/**
 * The first activity of an user friendly wizard to configure a XMPP account.
 */
public class Account extends FragmentActivity {

	private static final String TAG = Account.class.getSimpleName();

	private FragmentManager fragmentMgr;

	/**
	 * Constructor.
	 */
	public Account() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentMgr = getSupportFragmentManager();
		if (savedInstanceState == null) {
			FragmentTransaction t = fragmentMgr.beginTransaction();
			t.add(android.R.id.content, MainFragment.newInstance(), "Main");
			t.commit();
		}
	}

	/**
	 * Callback called when the create account option is selected.
	 * 
	 */
	public void onCreateAccountSelected() {
		Fragment f = CreateAccountFragment.newInstance();
		FragmentTransaction transaction = fragmentMgr.beginTransaction();

		transaction.replace(android.R.id.content, f, "createAccount");
		transaction.addToBackStack(null);
		transaction.commit();
	}

	/**
	 * Callback called when the configure account option is selected.
	 * 
	 */
	public void onConfigureAccountSelected() {
		Fragment f = AccountConfigureFragment.newInstance();
		FragmentTransaction transaction = fragmentMgr.beginTransaction();

		transaction.replace(android.R.id.content, f, "configureAccount");
		transaction.addToBackStack(null);
		transaction.commit();
	}

	/**
	 * Main fragment of the wizard account activity.
	 */
	public static class MainFragment extends Fragment implements
			OnClickListener, RadioGroup.OnCheckedChangeListener {
		private RadioGroup mConfigureGroup;
		private Button mNextButton;
		private Button mSigninButton;
		private Button mSignupButton;

		private Account activity;

		/**
		 * Create a new MainFragment.
		 * 
		 * @return a MainFragment
		 */
		static MainFragment newInstance() {
			return new MainFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.wizard_account_main_fragment,
					container, false);
			mNextButton = (Button) v.findViewById(R.id.next);
			mNextButton.setOnClickListener(this);
			mSigninButton = (Button) v.findViewById(R.id.buttonSignin);
			mSignupButton = (Button) v.findViewById(R.id.buttonSignup);
			mSigninButton.setOnClickListener(this);
			mSignupButton.setOnClickListener(this);
			mConfigureGroup = (RadioGroup) v.findViewById(R.id.configure_group);
			mConfigureGroup.setOnCheckedChangeListener(this);
			activity.getActionBar().hide();
			return v;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			this.activity = (Account) activity;
		}

		@Override
		public void onClick(View v) {
			ActionBar actionbar = activity.getActionBar();
			if (v == mSigninButton) {
				activity.onConfigureAccountSelected();
				actionbar.setTitle("Sign In");
				actionbar.show();
			} else if (v == mSignupButton) {
				activity.onCreateAccountSelected();
				actionbar.setTitle("Sign Up");
				actionbar.show();
			}
			if (v == mNextButton) {
				/*
				 * int selectedid = mConfigureGroup.getCheckedRadioButtonId();
				 * if (selectedid == R.id.configure_account) {
				 * activity.onConfigureAccountSelected(); } else if (selectedid
				 * == R.id.create_account) { activity.onCreateAccountSelected();
				 * }
				 */
			}
		}

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == -1)
				mNextButton.setEnabled(false);
			else
				mNextButton.setEnabled(true);
		}

	}
}
