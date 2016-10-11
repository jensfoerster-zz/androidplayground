package de.my.playground.fragments.keystore;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

/**
 * Created by jensfoerster on 12/15/2015.
 */
public class SignTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "KeyStore.SignTask";
    private KeyStoreUsageFragment keyStoreUsageFragment;

    public SignTask(KeyStoreUsageFragment keyStoreUsageFragment) {
        this.keyStoreUsageFragment = keyStoreUsageFragment;
    }

    @Override
    protected String doInBackground(String... params) {
        final String alias = params[0];
        final String dataString = params[1];
        try {
            byte[] data = dataString.getBytes();
            /*
             * Use a PrivateKey in the KeyStore to create a signature over
             * some data.
             */
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(alias, null);
            if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                Log.w(TAG, "Not an instance of a PrivateKeyEntry");
                return null;
            }
            Signature s = Signature.getInstance("SHA256withECDSA");
            s.initSign(((KeyStore.PrivateKeyEntry) entry).getPrivateKey());
            s.update(data);
            byte[] signature = s.sign();
            return Base64.encodeToString(signature, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException
                 | IOException | UnrecoverableEntryException | InvalidKeyException | SignatureException e) {
            Log.w(TAG, "Could not generate key", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        keyStoreUsageFragment.mCipherText.setText(result);
        keyStoreUsageFragment.setKeyActionButtonsEnabled(true);
    }

    @Override
    protected void onCancelled() {
        keyStoreUsageFragment.mCipherText.setText("error!");
        keyStoreUsageFragment.setKeyActionButtonsEnabled(true);
    }
}
