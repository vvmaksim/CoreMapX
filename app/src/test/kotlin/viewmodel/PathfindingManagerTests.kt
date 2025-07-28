package viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import model.dto.VisibleStates
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.contracts.Graph
import model.graph.entities.UnweightedEdge
import model.graph.entities.Vertex
import model.graph.pathfinding.BFSStrategy
import model.graph.pathfinding.BellmanFordStrategy
import model.graph.pathfinding.DijkstraStrategy
import model.graph.pathfinding.PathfindingStrategiesNames
import model.result.Result
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import viewmodel.graph.GraphViewModel
import viewmodel.managers.PathfindingManager
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PathfindingManagerTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_pathfinding_manager_test_" + System.currentTimeMillis(),
            )
        }
    }

    val defaultPathfindingStrategy: BFSStrategy<Long, Long> = BFSStrategy()
    val unknownStrategyName = "unknownStrategy"
    val allStrategies =
        listOf(
            PathfindingStrategiesNames.BFS,
            PathfindingStrategiesNames.DIJKSTRA,
            PathfindingStrategiesNames.BELLMAN_FORD,
        )

    lateinit var graph: State<Graph<Long, Long>>
    lateinit var visibleStates: State<VisibleStates>
    lateinit var pathfindingManager: PathfindingManager<Long, Long>

    @BeforeEach
    fun setup() {
        graph = mutableStateOf<Graph<Long, Long>>(UndirectedUnweightedGraph())
        visibleStates =
            mutableStateOf(
                value =
                    VisibleStates(
                        verticesIds = mutableStateOf(true),
                        verticesLabels = mutableStateOf(true),
                        edgesWeights = mutableStateOf(true),
                        edgesIds = mutableStateOf(true),
                    ),
            )
        pathfindingManager =
            PathfindingManager(
                graphViewModel =
                    mutableStateOf(
                        value =
                            GraphViewModel(
                                graph = graph.value,
                                visibleStates = visibleStates,
                            ),
                    ),
                graph = graph,
            )
    }

    @Test
    fun `check default pathfinding strategy`() {
        assertEquals(defaultPathfindingStrategy::class, pathfindingManager.pathfindingStrategy.value::class)
    }

    @Test
    fun `get all strategies`() {
        assertEquals(allStrategies, pathfindingManager.getAllStrategiesAsList())
    }

    @Test
    fun `set strategy on BFS`() {
        pathfindingManager.setStrategyByStringName(PathfindingStrategiesNames.BFS)
        assertTrue(pathfindingManager.pathfindingStrategy.value is BFSStrategy)
    }

    @Test
    fun `set strategy on Dijkstra`() {
        pathfindingManager.setStrategyByStringName(PathfindingStrategiesNames.DIJKSTRA)
        assertTrue(pathfindingManager.pathfindingStrategy.value is DijkstraStrategy)
    }

    @Test
    fun `set strategy on Bellman Ford`() {
        pathfindingManager.setStrategyByStringName(PathfindingStrategiesNames.BELLMAN_FORD)
        assertTrue(pathfindingManager.pathfindingStrategy.value is BellmanFordStrategy)
    }

    @Test
    fun `set strategy on unknown strategy`() {
        pathfindingManager.setStrategyByStringName(unknownStrategyName)
        assertTrue(pathfindingManager.pathfindingStrategy.value is BFSStrategy)
    }

    @Test
    fun `get current BFS strategy as strings`() {
        pathfindingManager.setStrategyByStringName(PathfindingStrategiesNames.BFS)
        assertEquals(PathfindingStrategiesNames.BFS, pathfindingManager.getCurrentStrategyAsString())
    }

    @Test
    fun `get current Dijkstra strategy as strings`() {
        pathfindingManager.setStrategyByStringName(PathfindingStrategiesNames.DIJKSTRA)
        assertEquals(PathfindingStrategiesNames.DIJKSTRA, pathfindingManager.getCurrentStrategyAsString())
    }

    @Test
    fun `get current Bellman Ford strategy as strings`() {
        pathfindingManager.setStrategyByStringName(PathfindingStrategiesNames.BELLMAN_FORD)
        assertEquals(PathfindingStrategiesNames.BELLMAN_FORD, pathfindingManager.getCurrentStrategyAsString())
    }

    @Test
    fun `find one path in graph`() {
        graph = mutableStateOf<Graph<Long, Long>>(UndirectedUnweightedGraph())
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.value.addVertex(vertex1)
        graph.value.addVertex(vertex2)
        graph.value.addEdge(UnweightedEdge(1L, vertex1, vertex2))
        pathfindingManager =
            PathfindingManager(
                graphViewModel =
                    mutableStateOf(
                        value =
                            GraphViewModel(
                                graph = graph.value,
                                visibleStates = visibleStates,
                            ),
                    ),
                graph = graph,
            )
        val findResult = pathfindingManager.findPath(start = 1L, end = 2L, maxPaths = 1)
        assertTrue(findResult is Result.Success)
    }

    @Test
    fun `find paths in graph`() {
        graph = mutableStateOf<Graph<Long, Long>>(UndirectedUnweightedGraph())
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        val vertex3 = Vertex(3L, "3")
        graph.value.addVertex(vertex1)
        graph.value.addVertex(vertex2)
        graph.value.addVertex(vertex3)
        graph.value.addEdge(UnweightedEdge(1L, vertex1, vertex2))
        graph.value.addEdge(UnweightedEdge(2L, vertex1, vertex3))
        graph.value.addEdge(UnweightedEdge(3L, vertex2, vertex3))
        pathfindingManager =
            PathfindingManager(
                graphViewModel =
                    mutableStateOf(
                        value =
                            GraphViewModel(
                                graph = graph.value,
                                visibleStates = visibleStates,
                            ),
                    ),
                graph = graph,
            )
        val findResult = pathfindingManager.findPath(start = 1L, end = 3L, maxPaths = 10)
        assertTrue(findResult is Result.Success)
    }

    @Test
    fun `find path in empty graph`() {
        val findResult = pathfindingManager.findPath(start = 1L, end = 2L)
        assertTrue(findResult is Result.Error)
    }

    @Test
    fun `check null graph with null graphViewModel`() {
        pathfindingManager =
            PathfindingManager(
                graphViewModel = mutableStateOf(null),
                graph = mutableStateOf(null),
            )
        assertEquals(null, pathfindingManager.graphViewModel.value)
        assertEquals(null, pathfindingManager.graph.value)
    }
}
