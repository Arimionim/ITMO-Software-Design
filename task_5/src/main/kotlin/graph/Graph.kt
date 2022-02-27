package graph

import visualizer.DrawingApi

abstract class Graph(protected val drawingApi: DrawingApi) {
    abstract fun drawGraph(inputFileName: String)

    class Edge(val u: Int, val v: Int)
}