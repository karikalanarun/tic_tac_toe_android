package com.example.tictactoe

import android.util.Log
import java.lang.Error


class TicTacToe {
    private val board: List<MutableList<Int>> = listOf(mutableListOf(0, 0, 0), mutableListOf(0, 0, 0), mutableListOf(0, 0, 0)) //[[], [], []]
    fun move(row: Int, col: Int, symbol: String) {
        board[row][col] = when (symbol) {
            "X" -> 1
            "O" -> -1
            else -> throw Error("Invalid symbol")
        }
    }

    private fun checkRowForWin(symbol: Int): Boolean {
        for (row in board) {
            var rowMatched = true
            for (col in row) {
                if (col != symbol) {
                    rowMatched = false
                }
            }
            if (rowMatched) {return true}
        }
        return false
    }

    private fun checkColForWin(symbol: Int): Boolean {
        for (col in 0..2) {
            var colMatched = true
            for (row in 0..2) {
                if (board[row][col] != symbol) {
                    colMatched = false
                    break
                }
            }
            if (colMatched) {return true}
        }
        return false
    }

    private fun  checkNegativeLineForWin(symbol: Int): Boolean {
        var lineMatched = true
        for (row in 0..2) {
            if (board[row][row] != symbol) {
                lineMatched = false
                break;
            }
        }
        return lineMatched
    }

    private fun checkPositiveLineForWin(symbol: Int): Boolean {
        var col = 2;
        for (row in 0..2) {
            if (board[row][col] != symbol) {
                return false
            }
            col--;
        }
        return true
    }

    private fun isWin(symbol: Int): Boolean {
        return checkRowForWin(symbol) || checkColForWin(symbol) || checkNegativeLineForWin(symbol) || checkPositiveLineForWin(symbol)
    }


    private fun allFiled(): Boolean {
        for (row in board) {
            for (col in row) {
                if (col == 0) {return false}
            }
        }
        return true
    }

    private  fun isDraw(): Boolean {
        return allFiled() && !isWin(1) && !isWin(-1)
    }

    fun getWinningPerson(): Int {
        return if (isWin(1)) {
            1
        } else if (isWin(-1)) {
            -1
        } else if (isDraw()){
            2
        }else {
            0
        }
    }
}