package smasini.it.traxer.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Simone Masini on 16/04/2016.
 */
public class AuthenticatorService extends Service {

    private Authenticator mAuthenticator;
    @Override
    public void onCreate() {
        mAuthenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
