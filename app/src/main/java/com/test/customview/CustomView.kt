package com.test.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlin.random.Random
import org.jetbrains.anko.*

class CustomView : View, AnkoLogger {
    // VARIABLE
    private var arrayMC :ArrayList<MagicCircle> = ArrayList()
    private var grappedMC : MagicCircle? = null
    var screenWidth : Int = 0
    var screenHeight : Int = 0
    companion object {
        val DELTA = 8
    }

    //CONSTRUCTEUR
    constructor(context: Context?, attributes: AttributeSet?) : super(context,attributes){
        init()
    }
    constructor(context: Context?) : this(context,null)

    //FONCTIONS
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mooveAll(arrayMC,canvas)
    }

    private fun mooveAll(Mcircles : ArrayList<MagicCircle>, canvas: Canvas?){
        for (circle : MagicCircle in Mcircles){
            circle.move()
            canvas?.drawCircle(circle.cx, circle.cy , circle.rad, circle.paint)
            invalidate()
        }
    }

    private fun init(){

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        var ux :Float = event.x;
        var uy :Float = event.y;

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                var notTouch = true
                for (mcircle : MagicCircle in arrayMC) {
                    if(IsTouched(mcircle,ux,uy))
                    {
                        mcircle.grap()
                        grappedMC = mcircle
                        notTouch = false
                        break
                    }
                }
                if(notTouch)
                    createCircle(ux,uy)
            }

            MotionEvent.ACTION_UP -> {
                grappedMC?.ungrap()
                if(grappedMC != null)
                    grappedMC = null
            }

            MotionEvent.ACTION_MOVE -> {
                if(grappedMC != null){
                    grappedMC?.setCoord(ux,uy)
                }
            }
        }
        return true
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        screenWidth = width
        screenHeight = height

        super.onLayout(changed, left, top, right, bottom)
        //mCircle = MagicCircle(width,height)
        val nbCircle = Random.nextInt(3,10)
        for (i in 0..nbCircle){
            arrayMC.add(MagicCircle(width,height))
        }
        for (circle : MagicCircle in arrayMC){
            randomizeCircle(circle)
        }
    }

    private fun randomizeCircle(circle : MagicCircle){
        val cxRand:Int = Random.nextInt(0,screenWidth.toInt())
        val cyRand:Int = Random.nextInt(0,screenHeight.toInt())
        val dRand:Int = Random.nextInt(5,15)
        val radRand:Int = Random.nextInt(50,200)
        circle.setCoord(cxRand,cyRand)
        circle.setDelta(dRand)
        circle.rad = radRand.toFloat()
        circle.paint.setARGB(
            Random.nextInt(100,255),
            Random.nextInt(0,255),
            Random.nextInt(0,255),
            Random.nextInt(0,255))
    }

    private fun createCircle(ux : Float, uy: Float){
        var circle : MagicCircle = MagicCircle(screenWidth,screenHeight)
        randomizeCircle(circle)
        circle.cx = ux
        circle.cy = uy
        arrayMC.add(circle)
    }

    private fun IsTouched(mcircle : MagicCircle, userX : Float, userY : Float) : Boolean{
        val size : Float = mcircle.rad / 2
        val xmin = mcircle.cx - size
        val xmax = mcircle.cx + size
        val ymin = mcircle.cy - size
        val ymax = mcircle.cy + size
        if((xmin < userX && userX < xmax) && (ymin < userY && userY <ymax)) {
            return true
        }
        return false
    }

}