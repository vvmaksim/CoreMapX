package viewmodel.managers

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.graph.contracts.Graph
import model.graph.pathfinding.BFSStrategy
import model.graph.pathfinding.BellmanFordStrategy
import model.graph.pathfinding.DijkstraStrategy
import model.graph.pathfinding.PathfindingStrategiesNames
import model.graph.pathfinding.PathfindingStrategy
import model.result.Result
import org.coremapx.app.AppLogger.logDebug
import org.coremapx.app.config
import viewmodel.graph.GraphViewModel

class PathfindingManager<E : Comparable<E>, V : Comparable<V>>(
    val graphViewModel: State<GraphViewModel<E, V>?>,
    val graph: State<Graph<E, V>?>,
) {
    private val _pathfindingStrategy = mutableStateOf<PathfindingStrategy<E, V>>(BFSStrategy<E, V>())
    val pathfindingStrategy: State<PathfindingStrategy<E, V>>
        get() = _pathfindingStrategy

    fun getCurrentStrategyAsString(): String =
        when (_pathfindingStrategy.value) {
            is BFSStrategy<E, V> -> PathfindingStrategiesNames.BFS
            is DijkstraStrategy<E, V> -> PathfindingStrategiesNames.DIJKSTRA
            is BellmanFordStrategy<E, V> -> PathfindingStrategiesNames.BELLMAN_FORD
            else -> PathfindingStrategiesNames.BFS
        }

    fun setStrategyByStringName(strategyName: String) {
        when (strategyName) {
            PathfindingStrategiesNames.BFS -> updatePathfindingStrategy(BFSStrategy())
            PathfindingStrategiesNames.DIJKSTRA -> updatePathfindingStrategy(DijkstraStrategy())
            PathfindingStrategiesNames.BELLMAN_FORD -> updatePathfindingStrategy(BellmanFordStrategy())
            else -> updatePathfindingStrategy(BFSStrategy())
        }
    }

    fun getAllStrategiesAsList(): List<String> =
        listOf(
            PathfindingStrategiesNames.BFS,
            PathfindingStrategiesNames.DIJKSTRA,
            PathfindingStrategiesNames.BELLMAN_FORD,
        )

    fun findPath(
        start: V,
        end: V,
        maxPaths: Int = 1,
    ): Result<Boolean> {
        logDebug("Launched findPath with stratId:$start, endId:$end, maxPaths:$maxPaths")
        val resultFindPaths =
            pathfindingStrategy.value.findPath(
                graph.value,
                start,
                end,
                maxPaths,
            )
        when (resultFindPaths) {
            is Result.Error -> return resultFindPaths
            is Result.Success -> highlightPathsWithColors(resultFindPaths.data)
        }
        return Result.Success(true)
    }

    private fun highlightEdges(
        edgeIds: List<E>,
        highlightColor: Color,
    ) {
        graphViewModel.value?.edges?.forEach { edgeViewModel ->
            if (edgeIds.contains(edgeViewModel.edgeId)) {
                edgeViewModel.color = highlightColor
            }
        }
    }

    private fun highlightPathsWithColors(paths: List<List<E>>) {
        resetEdgeColors()

        paths.drop(1).forEach { path ->
            highlightEdges(path, config.states.otherPathsColor.value)
        }
        if (paths.isNotEmpty()) {
            highlightEdges(paths[0], config.states.shortestPathColor.value)
        }
    }

    private fun resetEdgeColors() {
        val defaultColor = config.states.edgeMainColor.value
        graphViewModel.value?.edges?.forEach { edgeViewModel ->
            edgeViewModel.color = defaultColor
        }
    }

    private fun updatePathfindingStrategy(newStrategy: PathfindingStrategy<E, V>) {
        _pathfindingStrategy.value = newStrategy
        logDebug("Set pathfindingStrategy on ${newStrategy::class.simpleName}")
    }
}
