package com.example.mvvm.model

class PersonRepository {
    fun login(email: String, password: String): Boolean{
        return (email == "contato@email.com" && password == "123456789")
    }
}