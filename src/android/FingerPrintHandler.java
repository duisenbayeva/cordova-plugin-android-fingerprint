package com.cordova.plugin.android.fingerprintauth;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;

/**
 * Created by bduisenbayeva on 11/23/17.
 */

public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback{

    static final long ERROR_TIMEOUT_MILLIS = 1600;
    static final long SUCCESS_DELAY_MILLIS = 1300;

    private final Context mContext;

    private CancellationSignal mCancellationSignal;
//    private final Callback mCallback;
//    private final FingerprintManager mFingerprintManager;
    private int mAttempts = 0;
    private static FingerprintManager.AuthenticationResult fingerprintResult;

    boolean mSelfCancelled;

    FingerPrintHandler(Context context) {
        this.mContext = context;

    }


    public void startAuthentication(FingerprintManager mFingerPrintManager, FingerprintManager.CryptoObject mCryptoObject) {

        mCancellationSignal = new CancellationSignal();

        mFingerPrintManager.authenticate(mCryptoObject, mCancellationSignal, 0 /* flags */, this, null);

    }

    public void stopListening() {
        if (mCancellationSignal != null) {
            mSelfCancelled = true;
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
//        super.onAuthenticationSucceeded(result);
        FingerprintAuth.onAuthenticated(true /* withFingerprint */, result);
//        dismissAllowingStateLoss();
    }

    @Override
    public void onAuthenticationFailed() {
//        super.onAuthenticationFailed();
        FingerprintAuth.onError();
    }

}
