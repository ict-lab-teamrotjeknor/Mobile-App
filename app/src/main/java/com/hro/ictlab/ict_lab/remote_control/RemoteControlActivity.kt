package com.hro.ictlab.ict_lab.remote_control

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.base.BaseActivity
import kotlinx.android.synthetic.main.activity_remote_control.*
import nl.boydroid.BarcodeCaptureView
import java.util.*
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import com.greysonparrelli.permiso.PermisoActivity
import nl.boydroid.grantMe


class RemoteControlActivity : PermisoActivity(), BarcodeCaptureView.OnResultHandler {

    private var currentLevel = Level.DAY
    private val bluetoothAdapter: BluetoothAdapter by lazy { BluetoothAdapter.getDefaultAdapter() }
    private var bluetoothSocket: BluetoothSocket? = null
    private var scannedCode = ""

    private val bluetoothReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                if (device.address == scannedCode) {
                    bluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.randomUUID())
                    bluetoothSocket!!.connect()
                }
                bluetoothAdapter.cancelDiscovery()
            }
        }
    }

    enum class Level {
        DAY,
        WEEK,
        MONTH,
        YEAR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_control)
        val supportActionBar = supportActionBar ?: return
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        supportActionBar.title = getString(R.string.remote_control_title)

        connected_with.text = String.format(connected_with.text.toString(), "WD.01.003")

        val filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        registerReceiver(bluetoothReceiver, filter)

        updateUI()

        start_scan_button.setOnClickListener {
            grantMe(Manifest.permission.CAMERA, permissionGranted = { granted ->
                if(granted) {
                    scanner_explanation_layout.visibility = View.GONE
                    scanner_view.start()
                }
            })
        }

        connect_again_button.setOnClickListener {
            remote_control_view.visibility = View.GONE
            scanner_view.start()
        }

        control_left.setOnClickListener { sendRemoteAction() }
        control_right.setOnClickListener { sendRemoteAction() }

        control_reset.setOnClickListener {
            currentLevel = Level.DAY
            updateUI()
            sendRemoteAction()
        }

        control_up.setOnClickListener {
            levelUp()
            sendRemoteAction()
        }

        control_down.setOnClickListener {
            levelDown()
            sendRemoteAction()
        }

        scanner_view.resultHandler = this
        bluetoothAdapter.startDiscovery()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothReceiver)
        if (bluetoothSocket != null) {
            bluetoothSocket!!.close()
        }
    }

    override fun onCodeScanned(code: String) {
        runOnUiThread { remote_control_view.visibility = View.VISIBLE }
        scannedCode = code
        bluetoothAdapter.startDiscovery()
    }

    private fun sendRemoteAction() {
        if (!bluetoothAdapter.isEnabled) {
            Toast.makeText(this, String.format("Bluetooth is niet ingeschakeld op dit apparaat"), Toast.LENGTH_SHORT).show()
            return
        }

        val level = when (currentLevel) {
            Level.DAY -> "day"
            Level.WEEK -> "week"
            Level.MONTH -> "month"
            else -> "year"
        }

        try {
            bluetoothSocket!!.outputStream.write(level.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun levelUp() {
        currentLevel = when (currentLevel) {
            Level.DAY -> Level.WEEK
            Level.WEEK -> Level.MONTH
            else -> Level.YEAR
        }
        updateUI()
    }

    private fun levelDown() {
        currentLevel = when (currentLevel) {
            Level.YEAR -> Level.MONTH
            Level.MONTH -> Level.WEEK
            else -> Level.DAY
        }
        updateUI()
    }

    private fun updateUI() {
        when (currentLevel) {
            Level.DAY -> {
                left_text.text = getString(R.string.previous_day)
                right_text.text = getString(R.string.next_day)
                up_text.text = getString(R.string.week_overview)
                down_text.text = ""
                control_down.visibility = View.GONE
                control_up.visibility = View.VISIBLE
            }
            Level.WEEK -> {
                left_text.text = getString(R.string.last_week)
                right_text.text = getString(R.string.next_week)
                up_text.text = getString(R.string.month_overview)
                down_text.text = getString(R.string.day_overview)
                control_down.visibility = View.VISIBLE
            }
            Level.MONTH -> {
                left_text.text = getString(R.string.last_month)
                right_text.text = getString(R.string.next_month)
                up_text.text = getString(R.string.year_overview)
                down_text.text = getString(R.string.week_overview)
                control_up.visibility = View.VISIBLE
            }
            Level.YEAR -> {
                left_text.text = getString(R.string.last_year)
                right_text.text = getString(R.string.next_year)
                up_text.text = ""
                down_text.text = getString(R.string.month_overview)
                control_up.visibility = View.GONE
            }
        }
    }
}
