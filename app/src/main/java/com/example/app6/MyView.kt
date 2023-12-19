package com.example.app6


import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible


class MyView : View
{
  private var start = false
  private var imageView : ImageView? = null
  private var lion : ImageView? = null
  private var cobra : ImageView? = null
  private var rabbit : ImageView? = null
  private var phoneLion : ImageView? = null
  private var phoneRabbit : ImageView? = null
  private var phoneCobra : ImageView? = null
  private var frame : ConstraintLayout? = null
  private var introComplete = false
  private var introComplete2 = false
  private var playComplete = false
  private var origtouchedLion = Rect(0,0,0,0)
  private var origtouchedCobra = Rect(0,0,0,0)
  private var origtouchedRabbit = Rect(0,0,0,0)
  private var origtouchedPhone = Rect(0,0,0,0)
  private var phoneMoveCompleted = false
  private var homeMoveCompleted = false
  private var phoneTotal : Int = 0
  private var playerTotal : Int = 0
  //var paint = Paint()

  //new stuff
  private var x1 : Float = 100.0f
  private var y1 : Float = 100.0f
  private var playerCoords : Rect = Rect(0,0,0,0)
  private var lion_touched = false
  private var startLionPoint : Point = Point(0,0)
  private var cobra_touched = false
  private var startCobraPoint : Point = Point(0,0)
  private var rabbit_touched = false
  private var startRabbitPoint : Point = Point(0,0)
  private var offsetRabbit : Point = Point(0,0)
  private var offsetLion : Point = Point(0,0)
  private var offsetCobra : Point = Point(0,0)
  private var playerChoice : String = ""
  private var phoneChoice : String = ""
  val choices = listOf("rabbit","cobra","lion")
  private var playerReady = false
  private var phoneReady = false
  private var returnTrip = false
  private var attack = false
  //private val paint = Paint(Paint.ANTI_ALIAS_FLAG)  //One per widget

  companion object
  {
    private var instance : MyView? = null
    public fun getInstance() : MyView
    {
      return instance!!
    }
  }
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
  {
    this.setWillNotDraw(false)
    origtouchedLion = Rect(832,443,1080, 691)
    origtouchedCobra = Rect(832,993,1080, 1241)
    origtouchedRabbit = Rect(832,1532,1080, 1776)
    //lion?.setLeftTopRightBottom(832,443,1080,691)
    instance = this

  }

  /////////////////////////////////////////////HERE'S onDraw///////////////////////////////////////
  override fun onDraw(canvas: Canvas)
  {
    super.onDraw(canvas)
    var width = getWidth()
    var height = getHeight()

    //new stuff--------------------
    //need to suppress the intro
    if (!introComplete)
     {
       introComplete = true
       var myZoom = AnimationUtils.loadAnimation(MainActivity.getInstance(),R.anim.zoom_in)
       var zoomHandler = ZoomHandler()
       myZoom.setAnimationListener(zoomHandler)
       var intro = MainActivity.getInstance().findViewById<ImageView>(R.id.intro)
       intro.startAnimation(myZoom)

     }

    /*if (returnTrip)
    {
      rabbit!!.setLeftTopRightBottom(origtouchedRabbit.left,origtouchedRabbit.top,
        origtouchedRabbit.right,origtouchedRabbit.bottom)
      returnTrip = false
    }*/

  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
  {
    super.onSizeChanged(w, h, oldw, oldh)
    var width = this.getWidth()
    var height = this.getHeight()
    //Run only once per class

    lion = MainActivity.getInstance().findViewById<ImageView>(R.id.lion)
    cobra = MainActivity.getInstance().findViewById<ImageView>(R.id.cobra)
    rabbit = MainActivity.getInstance().findViewById<ImageView>(R.id.rabbit)
    phoneLion = MainActivity.getInstance().findViewById<ImageView>(R.id.phoneLion)
    phoneCobra = MainActivity.getInstance().findViewById<ImageView>(R.id.phoneCobra)
    phoneRabbit = MainActivity.getInstance().findViewById<ImageView>(R.id.phoneRabbit)
    frame = MainActivity.getInstance().findViewById<ConstraintLayout>(R.id.frame)

    lion?.left = origtouchedLion.left
    lion?.top = origtouchedLion.top
    lion?.bottom = origtouchedLion.bottom
    lion?.right = origtouchedLion.right
    rabbit!!.setLeftTopRightBottom(origtouchedRabbit.left,origtouchedRabbit.top,origtouchedRabbit.right,origtouchedRabbit.bottom)
    cobra!!.setLeftTopRightBottom(origtouchedCobra.left,origtouchedCobra.top,origtouchedCobra.right,origtouchedCobra.bottom)



  }

  /*********************FadeOutHandler**********************************/
  inner class FadeOutHandler : Animation.AnimationListener
  {
    override fun onAnimationRepeat(animation: Animation?)
    {
      println("repeat")
    }
    override fun onAnimationEnd(animation: Animation?)
    {
      if (!introComplete2)
      {
        println("Fade out finshied")
        introComplete2 = true
        var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
        var animHandler1 = FadeInHandler()
        myFadeIn.setAnimationListener(animHandler1)
        var playerScore = MainActivity.getInstance().findViewById<TextView>(R.id.playerScore)
        var phoneScore = MainActivity.getInstance().findViewById<TextView>(R.id.phoneScore)
        var playerLabel = MainActivity.getInstance().findViewById<TextView>(R.id.playerLabel)
        var phoneLabel = MainActivity.getInstance().findViewById<TextView>(R.id.phoneLabel)
        playerScore.startAnimation(myFadeIn)
        phoneScore.startAnimation(myFadeIn)
        playerLabel.startAnimation(myFadeIn)
        phoneLabel.startAnimation(myFadeIn)
      }



      //if  (playComplete)
      //{
      //  rabbit!!.setLeftTopRightBottom(frame!!.right,frame!!.getHeight()-120, frame!!.right+241, frame!!.getHeight()+120)
      //}

      /*if (playerReady && phoneReady)
      {
        origtouchedLion = Rect(832,443,1080, 691)
        origtouchedCobra = Rect(832,993,1080, 1241)
        origtouchedRabbit = Rect(832,1532,1080, 1776)
        phoneReady = false
        playerReady = false
      }*/


    }
    override fun onAnimationStart(animation: Animation?)
    {
      println("Fade out start")

    }
  }
  /****************************************************************/

  /**************************************************************************FadeInHandler************/
  inner class FadeInHandler : Animation.AnimationListener
  {
    override fun onAnimationRepeat(animation: Animation?)
    {
      println("repeat")
    }
    override fun onAnimationEnd(animation: Animation?)
    {
      println("Fade in finshied")
      if (playComplete)
      {
        phoneReady = false
        playerReady = false
        playComplete = false
        var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
        var animHandler6 = FadeInHandler()
        myFadeOut.setAnimationListener(animHandler6)
        myFadeOut.duration = 2000
        var status = MainActivity.getInstance().findViewById<TextView>(R.id.status)
        //status.setText("")
        if (playerChoice == "rabbit")
        {
          rabbit!!.scaleX= 1.0f
        }
        status.startAnimation(myFadeOut)

      }
      else if (playerReady && phoneReady)
      {
        //phoneReady = false
        //playerReady = false
        playComplete = true
        //do some stuff
        //var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
        //var animHandler5 = FadeInHandler()
        //myFadeIn.setAnimationListener(animHandler5)
        //var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
        //var animHandler6 = FadeOutHandler()
        //myFadeOut.setAnimationListener(animHandler6)
        if (phoneChoice == "lion" && playerChoice == "rabbit")
        {
          println("phone won")
          var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
          var animHandler5 = FadeInHandler()
          myFadeIn.setAnimationListener(animHandler5)
          var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
          var animHandler6 = FadeOutHandler()
          myFadeOut.setAnimationListener(animHandler6)
          phoneTotal++
          var phoneScore = MainActivity.getInstance().findViewById<TextView>(R.id.phoneScore)
          phoneScore.setText(phoneTotal.toString())
          var status = MainActivity.getInstance().findViewById<TextView>(R.id.status)
          status.setText("Lion Defeats Rabbit - Phone Wins!")
          status.startAnimation(myFadeIn)
          var phoneLion = MainActivity.getInstance().findViewById<ImageView>(R.id.phoneLion)
          var frame = MainActivity.getInstance().findViewById<ConstraintLayout>(R.id.frame)
          var rabbit = MainActivity.getInstance().findViewById<ImageView>(R.id.rabbit)
          //phoneLion.startAnimation(myFadeOut)

          attack = true
          var delta_x = ((frame.right -120) - phoneLion.right).toFloat()
          var delta_y = ((frame.top - 120)- phoneLion.top).toFloat()
          var moveAnim = TranslateAnimation(0f, delta_x, 0f, delta_y)
          moveAnim.duration = 1000
          moveAnim.fillAfter = false
          var handler = MoveHandler()
          moveAnim.setAnimationListener(handler)
          phoneLion.startAnimation(moveAnim)
        }
        else if (phoneChoice == "lion" && playerChoice == "cobra")
        {
          println("player won")
          var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
          var animHandler5 = FadeInHandler()
          myFadeIn.setAnimationListener(animHandler5)
          var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
          var animHandler6 = FadeOutHandler()
          myFadeOut.setAnimationListener(animHandler6)
          playerTotal++
          var playerScore = MainActivity.getInstance().findViewById<TextView>(R.id.playerScore)
          playerScore.setText(playerTotal.toString())
          var status = MainActivity.getInstance().findViewById<TextView>(R.id.status)
          status.setText("Cobra Defeats Lion - Player Wins!")
          status.startAnimation(myFadeIn)
          var phoneLion= MainActivity.getInstance().findViewById<ImageView>(R.id.phoneLion)
          var frame = MainActivity.getInstance().findViewById<ConstraintLayout>(R.id.frame)
          var cobra = MainActivity.getInstance().findViewById<ImageView>(R.id.cobra)
          //phoneLion.startAnimation(myFadeOut)

          attack = true
          var delta_x = ((phoneLion.left +120) - cobra.right ).toFloat()
          var delta_y = (phoneLion.top -400).toFloat()
          var moveAnim = TranslateAnimation(0f, delta_x, 0f, delta_y)
          //var moveAnim = TranslateAnimation(delta_x,0f, delta_y,  0f)
          moveAnim.duration = 1000
          moveAnim.fillAfter = false
          var handler = MoveHandler()
          moveAnim.setAnimationListener(handler)
          cobra.startAnimation(moveAnim)
        }
        else if (phoneChoice == "cobra" && playerChoice == "lion")
        {
          println("phone won")
          var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
          var animHandler5 = FadeInHandler()
          myFadeIn.setAnimationListener(animHandler5)
          var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
          var animHandler6 = FadeOutHandler()
          myFadeOut.setAnimationListener(animHandler6)
          phoneTotal++
          var phoneScore = MainActivity.getInstance().findViewById<TextView>(R.id.phoneScore)
          phoneScore.setText(phoneTotal.toString())
          var status = MainActivity.getInstance().findViewById<TextView>(R.id.status)
          status.setText("Cobra Defeats Lion - Phone Wins!")
          status.startAnimation(myFadeIn)
          var phoneCobra= MainActivity.getInstance().findViewById<ImageView>(R.id.phoneCobra)
          var frame = MainActivity.getInstance().findViewById<ConstraintLayout>(R.id.frame)
          var lion = MainActivity.getInstance().findViewById<ImageView>(R.id.cobra)
          //phoneCobra.startAnimation(myFadeOut)

          attack = true
          var delta_x = ((frame.right -120) - phoneCobra.right).toFloat()
          var delta_y = ((frame.top - 120)- phoneCobra.top).toFloat()
          var moveAnim = TranslateAnimation(0f, delta_x, 0f, delta_y)
          moveAnim.duration = 1000
          moveAnim.fillAfter = false
          var handler = MoveHandler()
          moveAnim.setAnimationListener(handler)
          phoneCobra.startAnimation(moveAnim)
        }
        else if (phoneChoice == "cobra" && playerChoice == "rabbit")
        {
          println("player won")
          var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
          var animHandler5 = FadeInHandler()
          myFadeIn.setAnimationListener(animHandler5)
          var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
          var animHandler6 = FadeOutHandler()
          myFadeOut.setAnimationListener(animHandler6)
          playerTotal++
          var playerScore = MainActivity.getInstance().findViewById<TextView>(R.id.playerScore)
          playerScore.setText(playerTotal.toString())
          var status = MainActivity.getInstance().findViewById<TextView>(R.id.status)
          status.setText("Rabbit Defeats Cobra - Player Wins!")
          status.startAnimation(myFadeIn)
          var phoneCobra= MainActivity.getInstance().findViewById<ImageView>(R.id.phoneCobra)
          var frame = MainActivity.getInstance().findViewById<ConstraintLayout>(R.id.frame)
          var rabbit = MainActivity.getInstance().findViewById<ImageView>(R.id.rabbit)
          //phoneCobra.startAnimation(myFadeOut)

          attack = true
          var delta_x = ((phoneCobra.left +120) - rabbit.right ).toFloat()
          var delta_y = (phoneCobra.top -800).toFloat()
          var moveAnim = TranslateAnimation(0f, delta_x, 0f, delta_y)
          //var moveAnim = TranslateAnimation(delta_x,0f, delta_y,  0f)
          moveAnim.duration = 1000
          moveAnim.fillAfter = false
          var handler = MoveHandler()
          moveAnim.setAnimationListener(handler)
          rabbit.startAnimation(moveAnim)

        }
        else if (phoneChoice == "rabbit" && playerChoice == "lion")
        {
          println("player won")
          var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
          var animHandler5 = FadeInHandler()
          myFadeIn.setAnimationListener(animHandler5)
          var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
          var animHandler6 = FadeOutHandler()
          myFadeOut.setAnimationListener(animHandler6)
          playerTotal++
          var playerScore = MainActivity.getInstance().findViewById<TextView>(R.id.playerScore)
          playerScore.setText(playerTotal.toString())
          var status = MainActivity.getInstance().findViewById<TextView>(R.id.status)
          status.setText("Lion Defeats Rabbit - Player Wins!")
          status.startAnimation(myFadeIn)
          var phoneRabbit= MainActivity.getInstance().findViewById<ImageView>(R.id.phoneRabbit)
          var frame = MainActivity.getInstance().findViewById<ConstraintLayout>(R.id.frame)
          var lion = MainActivity.getInstance().findViewById<ImageView>(R.id.lion)
          //phoneRabbit.startAnimation(myFadeOut)

          attack = true
          var delta_x = ((phoneRabbit.left +120) - lion.right ).toFloat()
          var delta_y = (phoneRabbit.top ).toFloat()
          var moveAnim = TranslateAnimation(0f, delta_x, 0f, delta_y)
          //var moveAnim = TranslateAnimation(delta_x,0f, delta_y,  0f)
          moveAnim.duration = 1000
          moveAnim.fillAfter = false
          var handler = MoveHandler()
          moveAnim.setAnimationListener(handler)
          lion.startAnimation(moveAnim)
        }
        else if (phoneChoice == "rabbit" && playerChoice == "cobra")
        {
          println("phone won")
          var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
          var animHandler5 = FadeInHandler()
          myFadeIn.setAnimationListener(animHandler5)
          var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
          var animHandler6 = FadeOutHandler()
          myFadeOut.setAnimationListener(animHandler6)
          phoneTotal++
          var phoneScore = MainActivity.getInstance().findViewById<TextView>(R.id.phoneScore)
          phoneScore.setText(phoneTotal.toString())
          var status = MainActivity.getInstance().findViewById<TextView>(R.id.status)
          status.setText("Rabbit Defeats Cobra - Phone Wins!")
          status.startAnimation(myFadeIn)
          var phoneRabbit= MainActivity.getInstance().findViewById<ImageView>(R.id.phoneRabbit)
          var cobra= MainActivity.getInstance().findViewById<ImageView>(R.id.cobra)
          var frame = MainActivity.getInstance().findViewById<ConstraintLayout>(R.id.frame)
          //phoneRabbit.startAnimation(myFadeOut)

          attack = true
          var delta_x = ((frame.right -120) - phoneRabbit.right).toFloat()
          var delta_y = ((frame.top - 120)- phoneRabbit.top).toFloat()
          var moveAnim = TranslateAnimation(0f, delta_x, 0f, delta_y)
          moveAnim.duration = 1000
          moveAnim.fillAfter = false
          var handler = MoveHandler()
          moveAnim.setAnimationListener(handler)
          phoneRabbit.startAnimation(moveAnim)
        }
        else
        {
          println("tie")
          //phoneReady = false
          //playerReady = false
          //var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
          //var animHandler4 = FadeOutHandler()
          //myFadeOut.setAnimationListener(animHandler4)
          if (phoneChoice == "lion")
          {
            var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
            var animHandler4 = FadeOutHandler()
            myFadeOut.setAnimationListener(animHandler4)
            var phoneLion= MainActivity.getInstance().findViewById<ImageView>(R.id.phoneLion)
            phoneLion.startAnimation(myFadeOut)
          }
          else if (phoneChoice == "cobra")
          {
            var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
            var animHandler4 = FadeOutHandler()
            myFadeOut.setAnimationListener(animHandler4)
            var phoneCobra= MainActivity.getInstance().findViewById<ImageView>(R.id.phoneCobra)
            phoneCobra.startAnimation(myFadeOut)
          }
          else if (phoneChoice == "rabbit")
          {
            var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
            var animHandler4 = FadeOutHandler()
            myFadeOut.setAnimationListener(animHandler4)
            var phoneRabbit= MainActivity.getInstance().findViewById<ImageView>(R.id.phoneRabbit)
            phoneRabbit.startAnimation(myFadeOut)
          }
          if (playerChoice == "lion")
          {
            var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
            var animHandler4 = FadeOutHandler()
            myFadeOut.setAnimationListener(animHandler4)
            var lion= MainActivity.getInstance().findViewById<ImageView>(R.id.lion)
            //myFadeOut.fillAfter = false
            //lion.startAnimation(myFadeOut)
          }
          else if (playerChoice == "cobra")
          {
            var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
            var animHandler4 = FadeOutHandler()
            myFadeOut.setAnimationListener(animHandler4)
            var cobra= MainActivity.getInstance().findViewById<ImageView>(R.id.cobra)
            //myFadeOut.fillAfter = false
            //cobra.startAnimation(myFadeOut)
          }
          else if (playerChoice == "rabbit")
          {
            var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
            var animHandler4 = FadeOutHandler()
            myFadeOut.setAnimationListener(animHandler4)
            var rabbit= MainActivity.getInstance().findViewById<ImageView>(R.id.rabbit)
            //myFadeOut.fillAfter = false
            //rabbit.startAnimation(myFadeHome)
          }
          var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
          var animHandler5 = FadeInHandler()
          myFadeIn.setAnimationListener(animHandler5)
          var status = MainActivity.getInstance().findViewById<TextView>(R.id.status)
          status.setText("TIE!")
          status.startAnimation(myFadeIn)

        }

      }


    }
    override fun onAnimationStart(animation: Animation?)
    {

      println("Fade in start")

    }
  }
  /**********************************************************************************/

  /***********************************Dissolve Handler**********************************/
  inner class DissolveHandler : Animation.AnimationListener
  {
    override fun onAnimationRepeat(animation: Animation?)
    {
      println("repeat")
    }
    override fun onAnimationEnd(animation: Animation?)
    {
      println("dissolve finshied")

      //var untouched = findViewById<ImageView>(R.id.lion)
      //untouched.setAlpha(1.0f)
      imageView?.setAlpha(0.0f)
    }
    override fun onAnimationStart(animation: Animation?)
    {
      println("dissolve start")
      imageView?.setImageResource(R.drawable.rabbit)
      var untouched = findViewById<ImageView>(R.id.lion)
      imageView?.layoutParams = untouched.layoutParams
      //Be sure to add only once!
      if (!start)
      {
        var cl = findViewById<ConstraintLayout>(R.id.cl)
        cl.addView(imageView)
        start = true
      }
      var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in )
      var animHandler2 = FadeInHandler()
      myFadeIn.setAnimationListener(animHandler2)
      imageView?.startAnimation(myFadeIn)
    }
  }
  /*************************************************************************************************/

  /***********************************Zoom Handler**********************************/
  inner class ZoomHandler : Animation.AnimationListener
  {
    override fun onAnimationRepeat(animation: Animation?)
    {
      println("repeat")
    }
    override fun onAnimationEnd(animation: Animation?)
    {

      println("End animation")
      var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
      var animHandler1 = FadeOutHandler()
      myFadeOut.setAnimationListener(animHandler1)
      var intro = MainActivity.getInstance().findViewById<ImageView>(R.id.intro)
      intro.startAnimation(myFadeOut)
    }
    override fun onAnimationStart(animation: Animation?)
    {
      println("zoom start")

    }
  }
  /*************************************************************************************************/

  /*********************Move Handler**********************************/
  inner class MoveHandler : Animation.AnimationListener
  {
    override fun onAnimationRepeat(animation: Animation?)
    {
      println("repeat")
    }
    override fun onAnimationEnd(animation: Animation?)
    {
      println("move finshied")
      if (attack)
      {

        var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
        var animHandler6 = FadeOutHandler()
        myFadeOut.setAnimationListener(animHandler6)
        attack = false
        if (phoneChoice == "lion" && playerChoice == "cobra")
        {
          var cobra = MainActivity.getInstance().findViewById<ImageView>(R.id.cobra)
          var phoneLion = MainActivity.getInstance().findViewById<ImageView>(R.id.phoneLion)
          //cobra.setLeftTopRightBottom(phoneLion.left,phoneLion.top, phoneLion.right, phoneLion.bottom)
          phoneLion.startAnimation(myFadeOut)
        }
        else if (phoneChoice == "lion" && playerChoice == "rabbit")
        {
          var phoneLion = MainActivity.getInstance().findViewById<ImageView>(R.id.phoneLion)
          phoneLion.startAnimation(myFadeOut)
        }
        else if (phoneChoice == "cobra" && playerChoice == "lion")
        {
          var phoneCobra = MainActivity.getInstance().findViewById<ImageView>(R.id.phoneCobra)
          phoneCobra.startAnimation(myFadeOut)
        }
        else if (phoneChoice == "cobra" && playerChoice == "rabbit")
        {
          var phoneCobra = MainActivity.getInstance().findViewById<ImageView>(R.id.phoneCobra)
          phoneCobra.startAnimation(myFadeOut)
        }
        else if (phoneChoice == "rabbit" && playerChoice == "lion")
        {
          var phoneRabbit = MainActivity.getInstance().findViewById<ImageView>(R.id.phoneRabbit)
          phoneRabbit.startAnimation(myFadeOut)
        }
        else if (phoneChoice == "rabbit" && playerChoice == "cobra")
        {
          var phoneRabbit = MainActivity.getInstance().findViewById<ImageView>(R.id.phoneRabbit)
          phoneRabbit.startAnimation(myFadeOut)
        }


      }
      else
      {
        lion?.left = origtouchedLion.left
        lion?.top = origtouchedLion.top
        lion?.bottom = origtouchedLion.bottom
        lion?.right = origtouchedLion.right
        //lion?.setLeftTopRightBottom(832,443,1080, 691)

        cobra!!.setLeftTopRightBottom(origtouchedCobra.left,origtouchedCobra.top,origtouchedCobra.right,origtouchedCobra.bottom)
        rabbit!!.setLeftTopRightBottom(origtouchedRabbit.left,origtouchedRabbit.top,origtouchedRabbit.right,origtouchedRabbit.bottom)
      }

    }
    override fun onAnimationStart(animation: Animation?)
    {
      println("move start")
    }
  }
  /****************************************************************/

  /****************************drag the pics*********************************/
  override fun onTouchEvent(event: MotionEvent): Boolean
  {

    var x = (event.getX())
    var y = (event.getY())
    var currentTime = System.currentTimeMillis()
    var index = event.getActionIndex()
    println("Finger Index#: $index")


    if (event.getAction() == MotionEvent.ACTION_DOWN )
    {
      println("touched down initially - $x, $y")

      //Verify if touch inside the ball
      if ( (x > lion?.left!!) && (x < lion?.right!!) &&
        (y > lion?.top!!) && (y < lion?.bottom!!))
      {
        println("lionTouched")
        lion_touched = true
        origtouchedLion = Rect(lion!!.left,lion!!.top,lion!!.right, lion!!.bottom)/////////////////////newnewnew
        startLionPoint.set(x.toInt(),y.toInt())
        offsetLion.set((x - lion!!.left).toInt(), (y - lion!!.top).toInt())
      }
      else if ( (x > cobra?.left!!) && (x < cobra?.right!!) &&
        (y > cobra?.top!!) && (y < cobra?.bottom!!))
      {
        println("cobraTouched")
        cobra_touched = true
        origtouchedCobra = Rect(cobra!!.left,cobra!!.top,cobra!!.right, cobra!!.bottom)
        startCobraPoint.set(x.toInt(),y.toInt())
        offsetCobra.set((x - cobra!!.left).toInt(), (y - cobra!!.top).toInt())
      }
      else if ( (x > rabbit?.left!!) && (x < rabbit?.right!!) &&
        (y > rabbit?.top!!) && (y < rabbit?.bottom!!))
      {
        println("rabbitTouched")
        rabbit_touched = true
        origtouchedRabbit = Rect(rabbit!!.left,rabbit!!.top,rabbit!!.right, rabbit!!.bottom)
        startRabbitPoint.set(x.toInt(),y.toInt())
        offsetRabbit.set((x - rabbit!!.left).toInt(), (y - rabbit!!.top).toInt())
      }
    }
    else if (event.getAction() ==  MotionEvent.ACTION_MOVE)
    {
      //player is set, set the phone

      println("moving $x, $y")
      var pic1 = false
      var pic2 = false

      println("moving findger 1 $x, $y")
      for (i in 0..event.getPointerCount()-1)
      {
        var xp = event.getX(event.getPointerId(i))
        var yp = event.getY(event.getPointerId(i))
        println("Moving  finger $i $xp $yp ")
        if (lion_touched)
        {

        }

      }
      if (lion_touched)
      {
        lion!!.setImageResource(R.drawable.lion2)
        //lion!!.setImageResource(R.drawable.lion1)
        lion?.left = x.toInt() - offsetLion.x
        lion?.top = y.toInt() - offsetLion.y
        //lion?.right = (x + lion?.getWidth()!! - offsetLion.x).toInt()
        //lion?.bottom = (y + lion?.getHeight()!! - offsetLion.y).toInt()
        lion?.right = (x + 241- offsetLion.x).toInt()
        lion?.bottom = (y + 241 - offsetLion.y).toInt()

        this.invalidate()
      }
      else if (cobra_touched)
      {
        cobra?.left = x.toInt() - offsetCobra.x
        cobra?.top = y.toInt() - offsetCobra.y
        //cobra?.right = (x + cobra?.getWidth()!! - offsetCobra.x).toInt()
        //cobra?.bottom = (y + cobra?.getHeight()!! - offsetCobra.y).toInt()
        cobra?.right = (x + 241 - offsetCobra.x).toInt()
        cobra?.bottom = (y + 241 - offsetCobra.y).toInt()

        this.invalidate()
      }
      else if (rabbit_touched)
      {
        rabbit?.left = x.toInt() - offsetRabbit.x
        rabbit?.top = y.toInt() - offsetRabbit.y
        //rabbit?.right = (x + rabbit?.getWidth()!!- offsetRabbit.x).toInt()
        //rabbit?.bottom = (y + rabbit?.getHeight()!! - offsetRabbit.y).toInt()
        rabbit?.right = (x + 241- offsetRabbit.x).toInt()
        rabbit?.bottom = (y + 241 - offsetRabbit.y).toInt()

        this.invalidate()
      }

    }
    else if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN)
    {
      var xp  = event.getX(event.getPointerId(index))
      var yp = event.getY(event.getPointerId(index))
      println("Pointer down $xp $yp ")
    }
    else if (event.getAction() == MotionEvent.ACTION_UP)
    {

      println("touched up - $x, $y")

      //new stuff
      if ( (lion?.left!! > frame!!.left) && (lion?.top!! > frame!!.top) &&
        (lion?.left!! < frame!!.right) && (lion?.top!! < frame!!.bottom))
      {
        playerChoice = "lion"
        lion!!.setLeftTopRightBottom(frame!!.right-241,frame!!.getHeight()-120, frame!!.right, frame!!.getHeight()+120)
        playerReady = true
      }
      else if ( (cobra?.left!! > frame!!.left) && (cobra?.top!! > frame!!.top) &&
        (cobra?.left!! < frame!!.right) && (cobra?.top!! < frame!!.bottom))
      {
        playerChoice = "cobra"
        cobra!!.setLeftTopRightBottom(frame!!.right-241,frame!!.getHeight()-120, frame!!.right, frame!!.getHeight()+120)
        playerReady = true
      }
      else if ( (rabbit?.left!! > frame!!.left) && (rabbit?.top!! > frame!!.top) &&
        (rabbit?.left!! < frame!!.right) && (rabbit?.top!! < frame!!.bottom))
      {
        playerChoice = "rabbit"
        rabbit!!.setLeftTopRightBottom(frame!!.right-241,frame!!.getHeight()-120, frame!!.right, frame!!.getHeight()+120)
        //rabbit!!.getRotationY()
        //rabbit!!.setRotationY(180.0f)
        rabbit!!.scaleX = -1.0f
        playerReady = true

      }
      else
      {
        if (lion_touched)
        {
          returnTrip = true
          var delta_x = (origtouchedLion.left - lion!!.left).toFloat()
          var delta_y = (origtouchedLion.top - lion!!.top).toFloat()
          var moveAnim = TranslateAnimation(0f, delta_x, 0f, delta_y)
          moveAnim.duration = 1000
          moveAnim.fillAfter = false
          var handler = MoveHandler()
          moveAnim.setAnimationListener(handler)
          lion!!.startAnimation(moveAnim)
          //this.invalidate()
        }
        else if (cobra_touched)
        {
          returnTrip = true
          var delta_x = (origtouchedCobra.left - cobra!!.left).toFloat()
          var delta_y = (origtouchedCobra.top - cobra!!.top).toFloat()
          var moveAnim = TranslateAnimation(0f, delta_x, 0f, delta_y)
          moveAnim.duration = 1000
          moveAnim.fillAfter = false
          var handler = MoveHandler()
          moveAnim.setAnimationListener(handler)
          cobra!!.startAnimation(moveAnim)
          //this.invalidate()
        }
        else if (rabbit_touched)
        {
          returnTrip = true
          var delta_x = (origtouchedRabbit.left - rabbit!!.left).toFloat()
          var delta_y = (origtouchedRabbit.top - rabbit!!.top).toFloat()
          var moveAnim = TranslateAnimation(0f, delta_x, 0f, delta_y)
          moveAnim.duration = 1000
          moveAnim.fillAfter = false
          var handler = MoveHandler()
          moveAnim.setAnimationListener(handler)
          rabbit!!.startAnimation(moveAnim)
          //this.invalidate()
        }
      }

      lion_touched = false
      cobra_touched = false
      rabbit_touched = false

      ///////////////////////////////////////////////////////////////////////////////////////////////////////////////change pics///
      if (playerReady)
      {
        phoneChoice = choices.random()
        println(phoneChoice)
        phoneReady = true
        if (phoneChoice == "lion")
        {
          var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
          var animHandler3 = FadeInHandler()
          //phoneLion?.setImageResource(R.drawable.title)
          myFadeIn.setAnimationListener(animHandler3)
          phoneLion!!.startAnimation(myFadeIn)
        }
        else if (phoneChoice == "cobra")
        {
          var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
          var animHandler3 = FadeInHandler()
          myFadeIn.setAnimationListener(animHandler3)
          phoneCobra!!.startAnimation(myFadeIn)
        }
        else if (phoneChoice == "rabbit")
        {
          var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
          var animHandler3 = FadeInHandler()
          myFadeIn.setAnimationListener(animHandler3)
          phoneRabbit!!.startAnimation(myFadeIn)
        }

      }

    }
    return true  //Use up this event prevents the bubbling to a parent of this view
  }

}
