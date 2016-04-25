/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package de.my.playground.keystore;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.security.KeyStore;

import de.my.playground.R;

public class KeyStoreUsageFragment extends Fragment {
    private static final String TAG = "AndroidKeyStoreUsage";

    KeyStore mKeyStore;

    AliasAdapter mAdapter;
    /**
     * Button in the UI that causes a new keypair to be generated in the
     * {@code KeyStore}.
     */
    Button mGenerateButton;
    /**
     * Button in the UI that causes data to be signed by a key we selected from
     * the list available in the {@code KeyStore}.
     */
    Button mSignButton;
    /**
     * Button in the UI that causes data to be signed by a key we selected from
     * the list available in the {@code KeyStore}.
     */
    Button mVerifyButton;
    /**
     * Button in the UI that causes a key entry to be deleted from the
     * {@code KeyStore}.
     */
    Button mDeleteButton;
    /**
     * Text field in the UI that holds plaintext.
     */
    EditText mPlainText;
    /**
     * Text field in the UI that holds the signature.
     */
    EditText mCipherText;
    /**
     * The alias of the selected entry in the KeyStore.
     */
    private String mSelectedAlias;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_keystore_usage, container, false);

        ListView lv = (ListView) v.findViewById(R.id.entries_list);
        mAdapter = new AliasAdapter(getActivity());
        lv.setAdapter(mAdapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedAlias = mAdapter.getItem(position);
                setKeyActionButtonsEnabled(true);
            }
        });
        // This is alias the user wants for a generated key.
        final EditText aliasInput = (EditText) v.findViewById(R.id.entry_name);
        mGenerateButton = (Button) v.findViewById(R.id.generate_button);
        mGenerateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * When the user presses the "Generate" button, we'll
                 * check the alias isn't blank here.
                 */
                final String alias = aliasInput.getText().toString();
                if (alias.length() == 0) {
                    aliasInput.setError(getResources().getText(R.string.keystore_no_alias_error));
                } else {
                    /*
                     * It's not blank, so disable the generate button while
                     * the generation of the key is happening. It will be
                     * enabled by the {@code AsyncTask} later after its
                     * work is done.
                     */
                    aliasInput.setError(null);
                    mGenerateButton.setEnabled(false);
                    new GenerateTask(KeyStoreUsageFragment.this).execute(alias);
                }
            }
        });
        mSignButton = (Button) v.findViewById(R.id.sign_button);
        mSignButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String alias = mSelectedAlias;
                final String data = mPlainText.getText().toString();
                if (alias != null) {
                    setKeyActionButtonsEnabled(false);
                    new SignTask(KeyStoreUsageFragment.this).execute(alias, data);
                }
            }
        });
        mVerifyButton = (Button) v.findViewById(R.id.verify_button);
        mVerifyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String alias = mSelectedAlias;
                final String data = mPlainText.getText().toString();
                final String signature = mCipherText.getText().toString();
                if (alias != null) {
                    setKeyActionButtonsEnabled(false);
                    new VerifyTask(KeyStoreUsageFragment.this).execute(alias, data, signature);
                }
            }
        });
        mDeleteButton = (Button) v.findViewById(R.id.delete_button);
        mDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String alias = mSelectedAlias;
                if (alias != null) {
                    setKeyActionButtonsEnabled(false);
                    new DeleteTask(KeyStoreUsageFragment.this).execute(alias);
                }
            }
        });
        mPlainText = (EditText) v.findViewById(R.id.plaintext);
        mPlainText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mPlainText.setTextColor(getResources().getColor(android.R.color.primary_text_dark));
            }
        });
        mCipherText = (EditText) v.findViewById(R.id.ciphertext);
        mCipherText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mCipherText
                        .setTextColor(getResources().getColor(android.R.color.primary_text_dark));
            }
        });
        updateKeyList();

        return v;
    }

    void updateKeyList() {
        setKeyActionButtonsEnabled(false);
        new UpdateKeyListTask(this).execute();
    }
    /**
     * Sets all the buttons related to actions that act on an existing key to
     * enabled or disabled.
     */
    void setKeyActionButtonsEnabled(boolean enabled) {
        mPlainText.setEnabled(enabled);
        mCipherText.setEnabled(enabled);
        mSignButton.setEnabled(enabled);
        mVerifyButton.setEnabled(enabled);
        mDeleteButton.setEnabled(enabled);
    }

}