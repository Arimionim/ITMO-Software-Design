package graph

import visualizer.DrawingApi
import visualizer.getSize
import visualizer.getVertex
import java.io.File
import kotlin.math.max
import kotlin.math.min

class EdgeListGraph(drawingApi: DrawingApi) : Graph(drawingApi) {
    private fun isConnected(a: Int, b: Int): Boolean {
        for (edge in edges) {
            if ((a == edge.u && b == edge.v) || (a == edge.v && b == edge.u)) {
                return true
            }
        }

        return false
    }

    private fun listOfVertices(): List<Int> {
        val set: MutableSet<Int> = mutableSetOf()
        for (edge in edges) {
            set.add(edge.u)
            set.add(edge.v)
        }

        return set.toList()
    }

    private val edges = ArrayList<Edge>()

    override fun drawGraph(inputFileName: String) {
        File(inputFileName).bufferedReader().use {
            val line = it.readLine().split(" ").map(String::toInt)
            check(line.size == 2) { "First line of input must contain vertex count and edge count." }
            val (n, m) = line

            drawingApi.setSize(getSize(n))

            for (i in 0 until n) {
                drawingApi.drawCircle(
                    getVertex(i, n),
                    5
                )
            }

            for (i in 0 until m) {
                val (x, y) = it.readLine().split(" ").map(String::toInt)

                val edge = Edge(min(x, y) - 1, max(x, y) - 1)

                edges.add(edge)
                drawingApi.drawLine(
                    getVertex(edge.u, n),
                    getVertex(edge.v, n)
                )
            }
        }
    }
}