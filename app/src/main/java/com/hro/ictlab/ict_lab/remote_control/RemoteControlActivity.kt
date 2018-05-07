package com.hro.ictlab.ict_lab.remote_control

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import kotlinx.android.synthetic.main.activity_remote_control.*
import nl.boydroid.BarcodeCaptureView

class RemoteControlActivity : BaseActivity(), BarcodeCaptureView.OnResultHandler {

    private var currentLevel = Level.DAY

    enum class Level {
        DAY,
        WEEK,
        MONTH,
        YEAR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_control)
        setActionBar(R.string.remote_control_title, true)

        connected_with.text = String.format(connected_with.text.toString(), "WD.01.003")

        updateUI()

        start_scan_button.setOnClickListener { scanner_explanation_layout.visibility = View.GONE
            scanner_view.start()
            onCodeScanned("Nice")
        }

        connect_again_button.setOnClickListener { remote_control_view.visibility = View.GONE
            scanner_view.start() }

        control_left.setOnClickListener { sendRemoteAction("Previous %s") }
        control_right.setOnClickListener { sendRemoteAction("Next %s") }

        control_reset.setOnClickListener { currentLevel = Level.DAY
            updateUI()
            sendRemoteAction("Back to current day") }

        control_up.setOnClickListener { levelUp()
            sendRemoteAction("Change to %s overview") }

        control_down.setOnClickListener { levelDown()
            sendRemoteAction("Change to %s overview") }

        scanner_view.resultHandler = this
    }

    override fun onCodeScanned(code: String) {
        runOnUiThread { remote_control_view.visibility = View.VISIBLE }
    }

    private fun sendRemoteAction(action: String) {
        val level = when(currentLevel) {
            Level.DAY -> "day"
            Level.WEEK -> "week"
            Level.MONTH -> "month"
            else -> "year"
        }

        Toast.makeText(this, String.format(action, level), Toast.LENGTH_SHORT).show()
    }

    private fun levelUp() {
        currentLevel = when(currentLevel) {
            Level.DAY -> Level.WEEK
            Level.WEEK -> Level.MONTH
            else -> Level.YEAR
        }
        updateUI()
    }

    private fun levelDown() {
        currentLevel = when(currentLevel) {
            Level.YEAR -> Level.MONTH
            Level.MONTH -> Level.WEEK
            else -> Level.DAY
        }
        updateUI()
    }

    private fun updateUI() {
        when(currentLevel) {
            Level.DAY -> {
                left_text.text = "Vorige dag"
                right_text.text = "Volgende dag"
                up_text.text = "Week overzicht"
                down_text.text = ""
                control_down.visibility = View.GONE
                control_up.visibility = View.VISIBLE
            }
            Level.WEEK -> {
                left_text.text = "Vorige week"
                right_text.text = "Volgende week"
                up_text.text = "Maand overzicht"
                down_text.text = "Dag overzicht"
                control_down.visibility = View.VISIBLE
            }
            Level.MONTH -> {
                left_text.text = "Vorige maand"
                right_text.text = "Volgende maand"
                up_text.text = "Jaar overzicht"
                down_text.text = "Week overzicht"
                control_up.visibility = View.VISIBLE
            }
            Level.YEAR -> {
                left_text.text = "Vorig jaar"
                right_text.text = "Volgend jaar"
                up_text.text = ""
                down_text.text = "Maand overzicht"
                control_up.visibility = View.GONE
            }
        }
    }
}
