package com.thingsenz.tictactoe.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.thingsenz.tictactoe.Utils

class TicTacToeModel: ViewModel() {

    var singlePlayer by mutableStateOf(true)
        private set

    var isGameOver by mutableStateOf(false)
        private set

    var winner by mutableStateOf("")
        private set

    var game by mutableStateOf(arrayListOf("","","","","","","","",""))
        private set

    private var curr = Utils.X

    fun play(move: Int) {
        if (isGameOver)
            return
        if (game[move].isEmpty()) {
            if (curr==Utils.X) {
                //needed for updating the value to sync with jetpack ui update
                game = ArrayList(game.toMutableList().also { it[move]=Utils.X })
                curr=Utils.O
                if (singlePlayer) {
                    if (!Utils.isFull(game = game) && !Utils.isWon(game = game,Utils.X)) {
                        val next = Utils.autoMove(game = game)
                        game = ArrayList(game.toMutableList().also {
                            it[next]=Utils.O
                        })
                    }
                    curr = Utils.X
                }
            } else {
                game = ArrayList(game.toMutableList().also { it[move]=Utils.O })
                curr=Utils.X
                if (singlePlayer) {
                    if (!Utils.isFull(game = game) && !Utils.isWon(game = game,Utils.X)) {
                        val next = Utils.autoMove(game = game)
                        game = ArrayList(game.toMutableList().also {
                            it[next]=Utils.X
                        })
                    }
                    curr = Utils.O
                }
            }
        }
        isGameOver = Utils.isWon(game = game,Utils.X) || Utils.isWon(game = game,Utils.O) || Utils.isFull(game = game)
        winner = Utils.result(game = game,singlePlayer)
    }

    fun reset() {
        isGameOver = false
        game = arrayListOf("","","","","","","","","")
    }

    fun updatePlayer(singlePlayer: Boolean) {
        reset()
        this.singlePlayer=singlePlayer
    }


}