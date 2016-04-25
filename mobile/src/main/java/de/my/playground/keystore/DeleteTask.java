package de.my.playground.keystore;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Created by dep01181 on 12/15/2015.
 */
public class DeleteTask extends AsyncTask<String, Void, Void> {
    private static final String TAG = "KeyStore.DeleteTask";
    private KeyStoreUsageFragment keyStoreUsageFragment;

    public DeleteTask(KeyStoreUsageFragment keyStoreUsageFragment) {
        this.keyStoreUsageFragment = keyStoreUsageFragment;
    }

    @Override
    protected Void doInBackground(String... params) {
        final String alias = params[0];
        try {
            /*
             * Deletes a previously generated or stored entry in the
             * KeyStore.
             */
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            ks.deleteEntry(alias);
        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException e) {
            Log.w(TAG, "Could not generate key", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        keyStoreUsageFragment.updateKeyList();
    }

    @Override
    protected void onCancelled() {
        keyStoreUsageFragment.updateKeyList();
    }
}
