package de.my.playground.fragments.keystore;

import android.os.AsyncTask;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by dep01181 on 12/15/2015.
 */
public class GenerateTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "KeyStore.GenerateTask";

    private KeyStoreUsageFragment keyStoreUsageFragment;

    public GenerateTask(KeyStoreUsageFragment keyStoreUsageFragment) {
        this.keyStoreUsageFragment = keyStoreUsageFragment;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        final String alias = params[0];
        try {
            /*
             * Generate a new EC key pair entry in the Android Keystore by
             * using the KeyPairGenerator API. The private key can only be
             * used for signing or verification and only with SHA-256 or
             * SHA-512 as the message digest.
             */
            KeyPairGenerator kpg =
                    KeyPairGenerator.getInstance(
                            KeyProperties.KEY_ALGORITHM_RSA,
                            "AndroidKeyStore");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                kpg.initialize(
                        new KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                                .build());
                KeyPair kp = kpg.generateKeyPair();
                return true;
            } else {
                //kpg.initialize();
                KeyPair kp = kpg.generateKeyPair();
                return true;
            }
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException e) {
            Log.w(TAG, "Could not generate key", e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        keyStoreUsageFragment.updateKeyList();
        keyStoreUsageFragment.mGenerateButton.setEnabled(true);
    }

    @Override
    protected void onCancelled() {
        keyStoreUsageFragment.mGenerateButton.setEnabled(true);
    }
}
