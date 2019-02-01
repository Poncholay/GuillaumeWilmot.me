package me.guillaumewilmot.powerlifting.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*
import me.guillaumewilmot.powerlifting.R
import me.guillaumewilmot.powerlifting.utils.errorSnackbar
import me.guillaumewilmot.powerlifting.utils.successSnackbar

class LoginActivity : ParentActivity() {
    companion object {
        private const val GOOGLE_SIGN_IN = 500
        const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnForgotPassword.transformationMethod = null
        btnLogin.transformationMethod = null
        btnRegister.transformationMethod = null

        initGoogle()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            completedTask.getResult(ApiException::class.java)?.let { account ->
                return loginCallback(account)
            }
            errorSnackbar(root, "Error during login")
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            errorSnackbar(root, e.message ?: "Error during login")
        }
    }

    private fun loginCallback(account: GoogleSignInAccount) {
        successSnackbar(root)
    }

    private fun initGoogle() {
        googleSignInButton.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            startActivityForResult(GoogleSignIn.getClient(this, gso).signInIntent, GOOGLE_SIGN_IN)
        }
    }
}
