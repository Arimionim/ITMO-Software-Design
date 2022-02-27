package graph

import visualizer.DrawingApi
import visualizer.getSize
import visualizer.getVertex
import java.io.File

class AdjacencyMatrixGraph(drawingApi: DrawingApi) : Graph(drawingApi) {
    val matrix = ArrayList<ArrayList<Int>>()
    override fun drawGraph(fileName: String) {
        File(fileName).bufferedReader().use {
            val n = it.readLine().toInt()

            drawingApi.setSize(getSize(n))

            for (i in 0 until n) {
                drawingApi.drawCircle(
                    getVertex(i, n),
                    5
                )
            }

            for (i in 0 until n) {
                val line = it.readLine().split(" ").map(String::toInt)

                for (j in (i + 1) until n) {
                    when (line[j]) {
                        0 -> Unit
                        1 -> drawingApi.drawLine(getVertex(i, n), getVertex(j, n))
                    }
                }
            }
        }
    }
}