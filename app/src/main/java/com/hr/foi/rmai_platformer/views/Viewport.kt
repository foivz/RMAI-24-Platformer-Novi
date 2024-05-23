package com.hr.foi.rmai_platformer.views

import android.graphics.Rect
import com.hr.foi.rmai_platformer.entities.WorldLocation

class Viewport(screenWidth: Int, screenHeight: Int) {
    private var screenCenterX: Int
    private var screenCenterY: Int
    public var pixelsPerMeterX: Int
    public var pixelsPerMeterY: Int
    private var metersToShowX: Int
    private var metersToShowY: Int

    private var currentWorldCenter: WorldLocation
    public var numClipped: Int = 0

    init {
        screenCenterX = screenWidth / 2
        screenCenterY = screenHeight / 2

        pixelsPerMeterX = screenWidth / 20
        pixelsPerMeterY = screenHeight / 12

        metersToShowX = 115
        metersToShowY = 100

        currentWorldCenter = WorldLocation(0f, 0f, 0)
    }

    fun worldToScreen(objectX: Float, objectY: Float, objectWidth: Int, objectHeight: Int): Rect {
        val positionRect = Rect()

        var left = screenCenterX - ((currentWorldCenter.x - objectX) * pixelsPerMeterX)
        var top = screenCenterY - ((currentWorldCenter.y - objectY) * pixelsPerMeterY)
        var right = left + objectWidth * pixelsPerMeterX
        var bottom = top + objectHeight * pixelsPerMeterY

        positionRect.set(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())

        return positionRect
    }

    fun clipObjects(objectX: Float, objectY: Float, objectWidth: Float, objectHeight: Float): Boolean {
        var clipped = true;

        if (objectX - objectWidth < currentWorldCenter.x + (metersToShowX / 2)) {
           if (objectX + objectWidth > currentWorldCenter.x - (metersToShowX / 2)) {
               if (objectY - objectHeight < currentWorldCenter.y + (metersToShowY / 2)) {
                   if (objectY + objectHeight > currentWorldCenter.y - (metersToShowY / 2)) {
                       clipped = false;
                   }
               }
           }
        }

        if (clipped) {
            numClipped++
        }

        return clipped;
    }

    fun resetNumClipped() {
        numClipped = 0
    }

    fun setWorldCenter(x: Float, y: Float) {
        currentWorldCenter.x = x
        currentWorldCenter.y = y
    }
}