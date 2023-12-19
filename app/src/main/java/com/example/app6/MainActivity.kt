package com.example.app6

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity()
{
  companion object
  {
    private var instance : MainActivity? = null
    public fun getInstance() : MainActivity
    {
      return instance!!
    }
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    instance = this
    setContentView(R.layout.activity_main)
  }
}