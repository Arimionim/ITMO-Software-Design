package visualizer

interface DrawingApi {
    fun setSize(size: Double)
    fun drawCircle(coords: Pair<Int, Int>, radius: Int)
    fun drawLine(start: Pair<Int, Int>, end: Pair<Int, Int>)
    fun finish()
}