package com.example.mvvm.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.viewModel.MainViewModel
import com.example.mvvm.R
import com.example.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener(this)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setObserver()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //binding.textWelcome.text = "Olá"
    }

    override fun onClick(view: View) {
        if(view.id == R.id.buttonLogin){
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            viewModel.doLogin(email, password)
        }
    }

    private fun setObserver(){
        viewModel.getWelcome().observe(this, Observer() {
            binding.textWelcome.text = it
        })

        viewModel.getLogin().observe(this) {
            if (it){
                startActivity(Intent(this, UserActivity::class.java))
                finish()
            } else{
                Toast.makeText(this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}