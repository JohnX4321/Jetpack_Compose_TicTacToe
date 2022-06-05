package com.thingsenz.tictactoe

object Utils {

    const val X="X"
    const val O="O"

    fun isFull(game: MutableList<String>): Boolean {
        for (i in game)
            if (i!= X && i!= O)
                return false
        return true
    }

    fun copyGame(game: MutableList<String>): ArrayList<String> {
        val n = arrayListOf("","","","","","","","","")
        for (i in game.indices)
            n[i] = game[i]
        return n
    }

    fun randomMove(game: MutableList<String>, moves: ArrayList<Int>): Int {
        val possible = arrayListOf<Int>()
        for (i in moves)
            if (game[i]=="")
                possible.add(i)
        return if (possible.isEmpty()) -1 else {
            possible[java.util.Random().nextInt(possible.size)]
        }
    }

    fun autoMove(game: MutableList<String>): Int {
        //computer win
        for (i in game.indices) {
            val c = copyGame(game = game)
            if (c[i]=="")
                c[i] = O
            if (isWon(c, O))
                return i
        }

        //player win next
        for (i in game.indices) {
            val c= copyGame(game = game)
            if (c[i]=="")
                c[i] = X
            if (isWon(c, X))
                return i
        }

        //strategy corners
        val m= randomMove(game = game, arrayListOf(0,2,6,8))
        if (m!=-1)
            return m
        //center
        if (game[4]=="")
            return 4
        return randomMove(game = game, arrayListOf(1,3,5,7))
    }

    fun isWon(game: MutableList<String>, playerType: String) = if ((game[0]==playerType && game[1]==playerType && game[2]==playerType) || (game[3]==playerType && game[4]==playerType && game[5]==playerType) || (game[6]==playerType && game[7]==playerType && game[8]==playerType)) true
    else if ((game[0]==playerType && game[3]==playerType && game[6]==playerType) || (game[1]==playerType && game[4]==playerType && game[7]==playerType) || (game[2]==playerType && game[5]==playerType && game[8]==playerType)) true
    else (game[0]==playerType && game[4]==playerType && game[8]==playerType) || (game[2]==playerType && game[4]==playerType && game[6]==playerType)

    fun result(game: MutableList<String>, singleMode: Boolean) = when {
        isWon(game = game, X) -> "${if (singleMode) "YOU" else "X"} WON"
        isWon(game = game, O) -> "${if (singleMode) "COMPUTER" else "O"} WON"
        isFull(game = game) -> "Tied"
        else -> "Tie"
    }

}