package visualizer

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun getSize(n: Int): Double = 200 + getRadius(n)

fun getRadius(n: Int): Double = 80 * n / PI

fun polarToDecart(fraction: Double, radius: Double, size: Double) =
    (size / 2 + cos(PI * 2 * fraction) * radius).toInt() to
            (size / 2 + sin(PI * 2 * fraction) * radius).toInt()

fun getVertex(i: Int, n: Int) =
    polarToDecart(i.toDouble() / n, getRadius(n), getSize(n))

//
//fun polarToDecart(fraction: Double, bigR: Int, smallR: Int) =
//    bigR + (cos(PI * 2 * fraction) * smallR).toInt() to
//            bigR + (sin(PI * 2 * fraction) * smallR).toInt()
//
//fun getVertex(i: Int, n: Int) =
//    polarToDecart(i.toDouble() / n, getBigRadius(n), getSmallRadius(n))
//
//fun getSmallRadius(n: Int) =
//    60 + 15 * n
//
//fun getBigRadius(n: Int) =
//    getSmallRadius(n) + 50
//
//fun getSize(n: Int) =
//    getBigRadius(n) * 2
