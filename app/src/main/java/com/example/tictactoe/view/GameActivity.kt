package com.example.tictactoe.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.Constants
import com.example.tictactoe.Constants.INTENT_PLAYER1_NAME
import com.example.tictactoe.Constants.INTENT_PLAYER2_NAME
import com.example.tictactoe.GameUtils
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ActivitySecondBinding

class GameActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySecondBinding.inflate(layoutInflater)
    }
    private lateinit var player1Name: String
    private lateinit var player2Name: String
    private lateinit var scoreCarrierIntent: Intent
    private lateinit var gameUtils: GameUtils

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        player1Name = intent.getStringExtra(INTENT_PLAYER1_NAME).toString()
        player2Name = intent.getStringExtra(INTENT_PLAYER2_NAME).toString()
        gameUtils = GameUtils(this, binding, player1Name, player2Name)
        gameUtils.setListenersForCells(gameUtils.btnClickListener())
        binding.backBtn.setOnClickListener {
            backBtnLogic()
        }
        binding.btnRestart.setOnClickListener {
            gameUtils.restartGame()
        }
        Toast.makeText(this, "$player1Name & $player2Name", Toast.LENGTH_SHORT).show()
        binding.hint.text = getString(R.string.it_s_your_turn, player1Name)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            backBtnLogic()
        }
    }


    private fun backBtnLogic() {
        //send scores back to main activity
        scoreCarrierIntent = Intent(this, MainActivity::class.java)
        scoreCarrierIntent.putExtra(Constants.INTENT_PLAYER1_SCORE, gameUtils.player1ScoreGetter)
        scoreCarrierIntent.putExtra(Constants.INTENT_PLAYER2_SCORE, gameUtils.player2ScoreGetter)
        scoreCarrierIntent.putExtra(INTENT_PLAYER1_NAME, player1Name)
        scoreCarrierIntent.putExtra(INTENT_PLAYER2_NAME, player2Name)
        setResult(100, scoreCarrierIntent)
        finish()
    }
}