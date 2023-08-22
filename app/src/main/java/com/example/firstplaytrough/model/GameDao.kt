package com.example.firstplaytrough.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Relation

@Entity(tableName = "game",
    foreignKeys = [ForeignKey(entity = Account::class, parentColumns = ["id"],
        childColumns = ["accountId"],
        onDelete = ForeignKey.CASCADE)])
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val accountId: Long, // Foreign key referencing Account table
    val playerAName: String,
    val playerBName: String,
    val date: String,
    val time: String,
    val goalsPlayerA: Int,
    val goalsPlayerB: Int
)

data class AccountWithGames(
    @Embedded val account: Account,
    @Relation(
        parentColumn = "id",
        entityColumn = "accountId"
    )
    val games: List<Game>
)

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM game WHERE accountId = :accountId")
    suspend fun getAllGamesByAccountId(accountId: Long): List<Game>

    @Query("SELECT * FROM game WHERE accountId = :accountId AND playerAName = :playerA AND playerBName = :playerB")
    suspend fun getAllGamesByAccountIdPlayerAPlayerB(accountId: Long, playerA: String, playerB: String): List<Game>

}