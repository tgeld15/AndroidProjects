package ge.bog.mbank.ui.login;

import ge.bog.mbank.R;
import ge.bog.mbank.exceptions.ValidationException;
import ge.bog.mbank.network.RequestInterceptor;

/**
 * created by tamuna
 */
public class LoginPresenterImpl implements LoginContract.LoginPresenter {
    private LoginContract.LoginView loginView;
    private LoginContract.LoginInteractor loginInteractor;


    public LoginPresenterImpl(LoginContract.LoginView loginView, LoginContract.LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void tryLogin(String username, String password) {
        loginInteractor.authenticate(new OnFinishListenerImpl(), username, password);
    }

    @Override
    public void validateInputs(String username, String password, LoginActivity context) throws ValidationException {
        loginInteractor.validateLogin(username, password, context);
    }


    public class OnFinishListenerImpl implements LoginContract.LoginInteractor.OnFinishListener {
        @Override
        public void onFinished(String sessionId) {
            if (sessionId != null) {
                RequestInterceptor.setSessionId(sessionId);
                loginView.redirectToMain();
            } else {
                loginView.unsuccessfulLogin();
            }
        }

        @Override
        public void onFailure(Throwable t) {

        }
    }
}
