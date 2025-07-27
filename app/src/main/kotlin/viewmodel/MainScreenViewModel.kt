package viewmodel

import viewmodel.managers.CanvasManager
import viewmodel.managers.GraphManager
import viewmodel.managers.PathfindingManager

class MainScreenViewModel<E : Comparable<E>, V : Comparable<V>> {
    val graphManager = GraphManager<E, V>()

    val canvasManager = CanvasManager()

    val pathfindingManager =
        PathfindingManager<E, V>(
            graphViewModel = graphManager.graphViewModel,
            graph = graphManager.graph,
        )
}
