package me.guillaumewilmot.powerlifting.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import me.guillaumewilmot.powerlifting.R
import me.guillaumewilmot.powerlifting.services.Api
import me.guillaumewilmot.powerlifting.services.errMsg
import me.guillaumewilmot.powerlifting.utils.errorSnackbar
import me.guillaumewilmot.powerlifting.utils.successSnackbar

class LoginActivity : ParentActivity() {
    companion object {
        private const val GOOGLE_SIGN_IN = 500
        const val TAG = "LoginActivity"
    }

    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        queue = Volley.newRequestQueue(this)

        btnForgotPassword.transformationMethod = null
        btnForgotPassword.setOnClickListener {
            errorSnackbar(root, getString(R.string.feature_not_supported))
        }
        btnLogin.transformationMethod = null
        btnLogin.setOnClickListener {
            errorSnackbar(root, getString(R.string.feature_not_supported))
        }
        btnRegister.transformationMethod = null
        btnRegister.setOnClickListener {
            errorSnackbar(root, getString(R.string.feature_not_supported))
        }

        evPassword.setOnEditorActionListener { _: TextView, actionId: Int, _: KeyEvent? ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    //TODO : regular login
                    errorSnackbar(root, getString(R.string.feature_not_supported))
                    true
                }
                else -> false
            }
        }

        initGoogle()
    }

    override fun onStop() {
        super.onStop()
        queue.cancelAll(TAG)
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
                return googleLoginCallback(account)
            }
            errorSnackbar(root, resources.getQuantityString(R.plurals.error_google_login, 1))
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            errorSnackbar(root, resources.getQuantityString(R.plurals.error_google_login, 2, e.statusCode))
        }
    }

    private fun googleLoginCallback(account: GoogleSignInAccount) {
        val visibility = loading.visibility
        loading.visibility = View.VISIBLE
        queue.add(
            Api.Auth.googleSignin(
            JsonObject().apply { addProperty("email", account.email) }
                .apply { addProperty("firstname", account.givenName) }
                .apply { addProperty("lastname", account.familyName) }
                .apply { addProperty("idToken", account.idToken) },
            Response.Listener { response ->
                loading.visibility = visibility
                successSnackbar(root, response.message)
            },
            Response.ErrorListener { error ->
                loading.visibility = visibility
                errorSnackbar(root, error.errMsg(this, getString(R.string.api_parse_error)))
            }
        ).apply { tag = TAG })
    }

    private fun initGoogle() {
        googleSignInButton.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("294753139295-m7bsqrdj8q8gomitc54knag0rnguopf3.apps.googleusercontent.com")
                .requestEmail()
                .build()
            startActivityForResult(GoogleSignIn.getClient(this, gso).signInIntent, GOOGLE_SIGN_IN)
        }
    }
}
