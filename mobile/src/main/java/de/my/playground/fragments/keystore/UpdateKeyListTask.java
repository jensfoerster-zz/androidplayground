package de.my.playground.fragments.keystore;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by dep01181 on 12/15/2015.
 */
public class UpdateKeyListTask extends AsyncTask<Void, Void, Enumeration<String>> {
    private static final String TAG = "UpdateKeyListTask";

    private KeyStoreUsageFragment keyStoreUsageFragment;

    public UpdateKeyListTask(KeyStoreUsageFragment keyStoreUsageFragment) {
        this.keyStoreUsageFragment = keyStoreUsageFragment;
    }

    @Override
    protected Enumeration<String> doInBackground(Void... params) {
        try {
            /*
             * Load the Android KeyStore instance using the the
             * "AndroidKeyStore" provider to list out what entries are
             * currently stored.
             */
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            Enumeration<String> aliases = ks.aliases();
            return aliases;
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            Log.w(TAG, "Could not list keys", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Enumeration<String> result) {
        List<String> aliases = new ArrayList<String>();
        while (result.hasMoreElements()) {
            aliases.add(result.nextElement());
        }
        keyStoreUsageFragment.mAdapter.setAliases(aliases);
    }
}
