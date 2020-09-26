package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.app.AlertDialog;
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

data class Cell(val row: Int, val col: Int);

class GameOverPopUp(val title: String, val message: String, val restart: () -> Unit): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            fragmentActivity ->
            val builder = AlertDialog.Builder(fragmentActivity)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton("Restart", DialogInterface.OnClickListener { _, _ -> restart()})
            return builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

class MainActivity : AppCompatActivity() {
    private val player1Symbol = "X"
    private val player2Symbol = "O"
    private var currentSymbol = player1Symbol
    private var board = TicTacToe()
//    private val btn_ids = listOf<Int>(R.id.btn_0_0, R.id.btn_0_1, R.id.btn_0_2, R.id.btn_1_0, R.id.btn_1_1, R.id.btn_1_2, R.id.btn_2_0, R.id.btn_2_1, R.id.btn_2_2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
    // Write a message to the database
//        val database = Firebase.database
//        val myRef = database.getReference("game1")
//        myRef.setValue("Hello, World!")
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue<String>()
//                Log.d("something", "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("something went wrong", "Failed to read value.", error.toException())
//            }
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun getRowCol(id: Int): Cell {
        return when (id) {
            R.id.btn_0_0 -> Cell(0, 0)
            R.id.btn_0_1 -> Cell(0, 1)
            R.id.btn_0_2 -> Cell(0, 2)
            R.id.btn_1_0 -> Cell(1, 0)
            R.id.btn_1_1 -> Cell(1, 1)
            R.id.btn_1_2 -> Cell(1, 2)
            R.id.btn_2_0 -> Cell(2, 0)
            R.id.btn_2_1 -> Cell(2, 1)
            R.id.btn_2_2 -> Cell(2, 2)
            else -> throw Error("Invalid id")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.create_btn -> {true}
            R.id.reset_btn -> {
                restart()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun move(btn: View) {
        val cell = getRowCol(btn.id)
        if (btn is Button && btn.text == "") {
            btn.text = currentSymbol
            board.move(cell.row, cell.col, currentSymbol)
            when (board.getWinningPerson()) {
                -1 -> showDialog("Game Over", "O wins")
                1 -> showDialog("Game Over", "X wins")
                0 -> {}
                2 -> showDialog("Game Over", "Match Draw")
                else -> throw Error("invalid person")
            }
            toggle()
        }
    }

    private fun restart() {
        for (row in 0..2) {
            for (col in 0..2) {
                findViewById<Button>(resources.getIdentifier("btn_${row}_${col}", "id", packageName)).text = ""
            }
        }
        board = TicTacToe()
        currentSymbol = player1Symbol
    }

    private fun showDialog(title: String, message: String) {
        GameOverPopUp(title, message, {restart()}).show(supportFragmentManager, "gameover")
    }

    private fun toggle() {
        currentSymbol = if (currentSymbol == player1Symbol) {
            player2Symbol
        } else {
            player1Symbol
        }
    }
}