package com.kenchen.bullseye

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kenchen.bullseye.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var sliderValue = 0
    private var targetValue = newTargetValue()
    private var totalScore = 0
    private var numRound = 1

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // create splash screen
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // set content view after binding
        val view = binding.root
        setContentView(view)

        startNewGame()

        binding.hitButton.setOnClickListener {
            showResult()
            totalScore += pointsForCurrentRound()
            binding.gameScoreTextView?.text = totalScore.toString()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        // start over button hit
        binding.startOverButton?.setOnClickListener {
            startNewGame()
        }

        // info button hit
        binding.infoButton?.setOnClickListener {
            navigateToAboutPage()
        }
    }

    private fun navigateToAboutPage() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun differenceAmount() = abs(targetValue - sliderValue)

    private fun newTargetValue() = Random.nextInt(1, 100)

    private fun pointsForCurrentRound(): Int {
        val maxScore = 100
        var bonus = 0

        if (differenceAmount() == 0) {
            bonus = 100
        } else if (differenceAmount() == 1) {
            bonus = 50
        }

        return maxScore - differenceAmount() + bonus
    }

    private fun startNewGame() {
        totalScore = 0
        numRound = 1
        sliderValue = 0
        targetValue = newTargetValue()

        binding.gameScoreTextView?.text = totalScore.toString()
        binding.gameRoundTextView?.text = numRound.toString()
        binding.targetTextView?.text = targetValue.toString()
        binding.seekBar.progress = sliderValue

    }

    private fun showResult() {
        val dialogTitle = alertTitle()
        val dialogMessage =
            getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())
//        val dialogMessage = "The slider value is $sliderValue"

        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(R.string.result_dialog_button_text) { dialog, _ ->
            dialog.dismiss()

            // increment round
            numRound += 1
            // show number of round
            binding.gameRoundTextView?.text = numRound.toString()

            // generate new target value
            targetValue = newTargetValue()

            // show new target value
            binding.targetTextView.text = targetValue.toString()
        }

        builder.create().show()
    }

    private fun alertTitle(): String {

        val difference = differenceAmount()

        val title: String = if (difference == 0) {
            getString(R.string.alert_title_1)
        } else if (difference < 5) {
            getString(R.string.alert_title_2)
        } else if (difference <= 10) {
            getString(R.string.alert_title_3)
        } else {
            getString(R.string.alert_title_4)
        }

        return title
    }
}