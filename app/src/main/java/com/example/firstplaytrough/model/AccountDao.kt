package com.example.firstplaytrough.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val username: String,
    val passwordHash: String
)

@Dao
interface AccountDao {
    @Insert
    suspend fun insertAccount(account: Account): Long

    @Transaction
    @Query("SELECT * FROM account WHERE id = :accountId")
    suspend fun getAccountById(accountId: Long): Account?

    @Transaction
    @Query("SELECT * FROM account WHERE username = :username")
    suspend fun getAccountByUsername(username: String): Account?
}