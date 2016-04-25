package de.my.playground.keystore;

import android.graphics.Color;
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
 * Created by dep01181 on 12/15/2015.
 */
public class VerifyTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "KeyStore.SignTask";

    private KeyStoreUsageFragment keyStoreUsageFragment;

    public VerifyTask(KeyStoreUsageFragment keyStoreUsageFragment) {
        this.keyStoreUsageFragment = keyStoreUsageFragment;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        final String alias = params[0];
        final String dataString = params[1];
        final String signatureString = params[2];
        try {
            byte[] data = dataString.getBytes();
            byte[] signature;
            try {
                signature = Base64.decode(signatureString, Base64.DEFAULT);
            } catch (IllegalArgumentException e) {
                signature = new byte[0];
            }
            /*
             * Verify a signature previously made by a PrivateKey in our
             * KeyStore. This uses the X.509 certificate attached to our
             * private key in the KeyStore to validate a previously
             * generated signature.
             */
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(alias, null);
            if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                Log.w(TAG, "Not an instance of a PrivateKeyEntry");
                return false;
            }
            Signature s = Signature.getInstance("SHA256withECDSA");
            s.initVerify(((KeyStore.PrivateKeyEntry) entry).getCertificate());
            s.update(data);
            return s.verify(signature);
        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException
                 | IOException | UnrecoverableEntryException | InvalidKeyException | SignatureException e) {
            Log.w(TAG, "Could not generate key", e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            keyStoreUsageFragment.mCipherText.setTextColor(Color.GREEN);
        } else {
            keyStoreUsageFragment.mCipherText.setTextColor(Color.RED);
        }
        keyStoreUsageFragment.setKeyActionButtonsEnabled(true);
    }

    @Override
    protected void onCancelled() {
        keyStoreUsageFragment.mCipherText.setText("error!");
        keyStoreUsageFragment.setKeyActionButtonsEnabled(true);
        keyStoreUsageFragment.mCipherText.setTextColor(keyStoreUsageFragment.getResources().getColor(android.R.color.primary_text_dark));
    }
}
