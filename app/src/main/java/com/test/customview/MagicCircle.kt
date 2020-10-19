package com.test.customview

import android.graphics.Color
import android.graphics.Paint

data class MagicCircle(val maxX: Int, val maxY: Int){
    var cx:Float = 50F
    var cy:Float = 50F
    var rad:Float = 80F
    //Sens de déplacement
    var dx = CustomView.DELTA
    var dy = CustomView.DELTA
    //Couleur
    var paint:Paint = Paint(Color.BLACK)
    //bool qui renvoie s'il est attrapé ou non
    var grapped:Boolean = false

    fun move(){
        when{
            cx.toInt() !in 0..maxX -> dx = -dx
            cy.toInt() !in 0..maxY -> dy = -dy
        }
        if(!grapped) {
            cx += dx
            cy += dy
        }
    }

    fun setDelta(delta: Int){
        dx = delta
        dy = delta
    }

    fun setColor(newPaint: Paint){
        this.paint = newPaint
    }

    fun setCoord(cx:Float,cy:Float){
        this.cx = cx;
        this.cy = cy;
    }

    fun setCoord(cx:Int,cy:Int){
        this.cx = cx.toFloat();
        this.cy = cy.toFloat();
    }

    fun grap(){
        grapped = true
    }

    fun ungrap(){
        grapped = false
    }
}

