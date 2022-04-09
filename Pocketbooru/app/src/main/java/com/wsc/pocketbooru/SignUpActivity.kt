package com.wsc.pocketbooru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class SignUpActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var repass: EditText
    private lateinit var signupBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        username = findViewById(R.id.username_sign_up)
        password = findViewById(R.id.password_sign_up)
        repass = findViewById(R.id.password_reenter)
        signupBtn = findViewById(R.id.signup_btn)

        signupBtn.setOnClickListener {view: View ->
            Log.d("SignUpActivity", "Sign Up clicked!")

            val intent: Intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("SEARCH", searchTerm)

            //preferences.edit().putString("SEARCH_TERM", searchTerm).apply()

            startActivity(intent)
        }

        signupBtn.isEnabled = false
        username.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)
        repass.addTextChangedListener(textWatcher)
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            val inputtedUsername: String = username.text.toString()
            val inputtedPassword: String = password.text.toString()
            val inputtedPasswordVerif: String = repass.text.toString()
            var passwordMatch: Boolean = false

            //Check if the two password fields are not blank
            if (inputtedPassword.isNotBlank() && inputtedPasswordVerif.isNotBlank()) {
                if (inputtedPassword == inputtedPasswordVerif) {
                    passwordMatch = true
                }
            }

            val enableButton: Boolean = inputtedUsername.isNotBlank() && passwordMatch == true

            signupBtn.isEnabled = enableButton
        }

        override fun afterTextChanged(p0: Editable?) {}

    }
}