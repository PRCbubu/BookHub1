package com.padmanavo.bookhub.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.padmanavo.bookhub.R
import com.padmanavo.bookhub.activity.LoginSignupActivity

class ProfileFragment : Fragment()
{
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(activity as Context, gso)


        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(activity as Context)

        if(acct!=null)
        {
            view.findViewById<TextView>(R.id.txtProfileName).text = acct.displayName
            view.findViewById<TextView>(R.id.txtProfileEmail).text = acct.email
        }

        view.findViewById<Button>(R.id.btnLogout).setOnClickListener(View.OnClickListener {
            signOut()
        })

        return view
    }

    private fun signOut()
    {
        gsc.signOut().addOnCompleteListener(OnCompleteListener {
            requireActivity().finish()
            startActivity(Intent(requireActivity(), LoginSignupActivity::class.java))
        })
    }
}
