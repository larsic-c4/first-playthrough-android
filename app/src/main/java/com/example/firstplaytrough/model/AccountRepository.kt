package com.example.firstplaytrough.model

import java.security.MessageDigest
import java.nio.charset.StandardCharsets

class AccountRepository(private val accountDao: AccountDao) {

    suspend fun findAccountByUsername(username: String): Account? {
        return accountDao.getAccountByUsername(username)
    }

    suspend fun insertAccount(account: Account): Long {
        return accountDao.insertAccount(account)
    }

    suspend fun findAccountById(id: Long): Account {
        return accountDao.getAccountById(id)!!
    }

    suspend fun authenticateAccount(username: String, password: String): Boolean {
        val account = accountDao.getAccountByUsername(username)
        val hashedPassword = calculateSHA256Hash(password)
        return account != null && account.passwordHash == hashedPassword
    }

    fun calculateSHA256Hash(input: String): String {
        val bytes = input.toByteArray(StandardCharsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }
}
