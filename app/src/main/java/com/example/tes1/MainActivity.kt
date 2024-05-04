package com.example.tes1

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.tes1.R



class MainActivity : ComponentActivity() {

    lateinit var redBar: SeekBar
    lateinit var greenBar: SeekBar
    lateinit var blueBar: SeekBar

    lateinit var redValue: TextView
    lateinit var greenValue: TextView
    lateinit var blueValue: TextView

    lateinit var redSwitch: Switch
    lateinit var greenSwitch: Switch
    lateinit var blueSwitch: Switch

    private lateinit var colorView: View
    private lateinit var resetButton: Button
    private lateinit var saveButton: Button

    private val myViewModel: MyViewModel by lazy {
        MyPreferencesRepository.initialize(this)
        MyViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectViews()
        setuplisteners()
        this.myViewModel.LoadInputs(this)
        updateColor()
    }

    private fun connectViews() {

        //initalizing every var with findviewbyid
        redBar = findViewById(R.id.redBar)
        redValue = findViewById(R.id.redValue)
        redSwitch = findViewById(R.id.red_switch)

        greenBar = findViewById(R.id.greenBar)
        greenValue = findViewById(R.id.greenValue)
        greenSwitch = findViewById(R.id.green_switch)

        blueBar = findViewById(R.id.blueBar)
        blueValue = findViewById(R.id.blueValue)
        blueSwitch = findViewById(R.id.blue_switch)

        colorView = findViewById(R.id.color_view)
        resetButton = findViewById(R.id.reset)
        saveButton = findViewById(R.id.save_button)


        //switch listeners
        redSwitch.setOnClickListener {
            toggleSeekBar(redBar)
            redValue.text = "0"
            redBar.progress = 0
        }
        greenSwitch.setOnClickListener {
            toggleSeekBar(greenBar)
            greenValue.text = "0"
            greenBar.progress = 0
        }
        blueSwitch.setOnClickListener {
            toggleSeekBar(blueBar)
            blueValue.text = "0"
            blueBar.progress = 0
        }

        //reset button to 0
        resetButton.setOnClickListener{
            redValue.text = "0"
            redBar.progress = 0
            greenValue.text = "0"
            greenBar.progress = 0
            blueValue.text = "0"
            blueBar.progress = 0
        }

        //Seekbar color listeners
        redBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                redValue.text = progress.toString()
                updateColor()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        greenBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                greenValue.text = progress.toString()
                updateColor()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        blueBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                blueValue.text = progress.toString()
                updateColor()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        // https://developer.android.com/reference/android/text/TextWatcher
        //parameters (s: charsequence, start: int, count: int, after: int)
        // s has to be editable (and maybe nullable)
        redValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.let { text ->
                    //had to switch out toInt for toIntOrNull b/c logcat expection
                    //java.lang.NumberFormatException:For input string: ""
                    val progress = text.toIntOrNull() ?: 0
                    redBar.progress = progress.coerceIn(0, 255)
                    updateColor()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        greenValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.let { text ->
                    val progress = text.toIntOrNull() ?: 0
                    greenBar.progress = progress.coerceIn(0, 255)
                    updateColor()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        blueValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.let { text ->
                    val progress = text.toIntOrNull() ?: 0
                    blueBar.progress = progress.coerceIn(0, 255)
                    updateColor()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }



    //toggle on or off seekbar
    private fun toggleSeekBar(seekBar: SeekBar?) {
        if (seekBar != null) {
            if(seekBar.visibility == View.VISIBLE){
                seekBar.visibility = View.GONE
            } else {
                seekBar.visibility = View.VISIBLE
            }
        }

    }

    //function to update color as seekbar is used
    private fun updateColor() {
        val red = redBar.progress
        val green = greenBar.progress
        val blue = blueBar.progress
        val color = Color.rgb(red, green, blue)
        colorView.setBackgroundColor(color)
    }

    private fun setuplisteners() {
        this.saveButton.setOnClickListener{
            this.myViewModel.saveInputBool(this.redSwitch.isChecked, 1)
            this.myViewModel.saveInputBool(this.greenSwitch.isChecked,2 )
            this.myViewModel.saveInputBool(this.blueSwitch.isChecked, 3)

            this.myViewModel.saveInputString(this.redValue.text.toString(), 1)
            this.myViewModel.saveInputString(this.greenValue.text.toString(), 2)
            this.myViewModel.saveInputString(this.blueValue.text.toString(), 3)

            this.myViewModel.saveInputInt(this.redBar.progress, 1)
            this.myViewModel.saveInputInt(this.greenBar.progress,2)
            this.myViewModel.saveInputInt(this.blueBar.progress, 3)

        }
    }
}

