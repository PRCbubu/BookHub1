package com.padmanavo.bookhub.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.padmanavo.bookhub.R

class LoginSignupActivity : AppCompatActivity()
{
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var googleButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)

        googleButton = findViewById(R.id.GoogleButton)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        if(acct!=null)
        {
            navigateToMainActivity()
        }

        googleButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn()
    {
        val signInIntent: Intent = gsc.signInIntent
        startActivityForResult(signInIntent, 1000)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000)
        {
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try
            {
                //task.getResult(ApiException::class.java)
                task.result
                navigateToMainActivity()
            }
            catch (e: ApiException)
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun navigateToMainActivity()
    {
        finish()
        startActivity(Intent(this,MainActivity::class.java))
    }
}