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
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupBtn: Button
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login_btn)
        signupBtn = findViewById(R.id.createacc_btn)

        loginBtn.setOnClickListener {view: View ->
            Log.d("MainActivity", "Log In clicked!")

            val inputtedUsername: String = username.text.toString()
            val inputtedPassword: String = password.text.toString()

            firebaseAuth
                .signInWithEmailAndPassword(inputtedUsername, inputtedPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser

                        val intent: Intent = Intent(this, MainMenu::class.java)
                        intent.putExtra("Username", user.toString())
                        startActivity(intent)
                    } else {
                        val exception = task.exception
                        Toast.makeText(this, "Failed to register $exception", Toast.LENGTH_LONG).show()
                    }
                }
        }

        signupBtn.setOnClickListener {view: View ->
            Log.d("Main Activity", "Sign Up clicked!")

            val intent: Intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginBtn.isEnabled = false
        username.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            val inputtedUsername: String = username.text.toString()
            val inputtedPassword: String = password.text.toString()
            val enableButton: Boolean = inputtedUsername.isNotBlank() && inputtedPassword.isNotBlank()

            loginBtn.isEnabled = enableButton
        }

        override fun afterTextChanged(p0: Editable?) {}

    }
}