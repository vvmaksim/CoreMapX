package model.pathfinding

import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.concrete.UndirectedWeightedGraph
import model.graph.contracts.Graph
import model.graph.entities.UnweightedEdge
import model.graph.entities.Vertex
import model.graph.entities.WeightedEdge
import model.graph.pathfinding.BFSStrategy
import model.result.Result
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class BFSStrategyTests {
    lateinit var strategy: BFSStrategy<Long, Long>
    lateinit var graph: Graph<Long, Long>

    val vertex1 = Vertex(1L, "1")
    val vertex2 = Vertex(2L, "2")
    val vertex3 = Vertex(3L, "3")
    val vertex4 = Vertex(4L, "4")
    val vertex5 = Vertex(5L, "5")
    val vertex6 = Vertex(6L, "6")
    val unweightedEdge1To2 = UnweightedEdge(0L, vertex1, vertex2)
    val unweightedEdge1To3 = UnweightedEdge(1L, vertex1, vertex3)
    val unweightedEdge2To3 = UnweightedEdge(2L, vertex2, vertex3)
    val weightedEdge1To2 = WeightedEdge(0L, vertex1, vertex2, 1)
    val weightedEdge1To4 = WeightedEdge(1L, vertex1, vertex4, 1)
    val weightedEdge1To3 = WeightedEdge(2L, vertex1, vertex3, 1)
    val weightedEdge2To4 = WeightedEdge(3L, vertex2, vertex4, 1)
    val weightedEdge3To4 = WeightedEdge(4L, vertex3, vertex4, 1)
    val weightedEdge4To5 = WeightedEdge(5L, vertex4, vertex5, 1)
    val weightedEdge4To6 = WeightedEdge(6L, vertex4, vertex6, 1)
    val weightedEdge5To6 = WeightedEdge(7L, vertex5, vertex6, 1)

    @BeforeEach
    fun setup() {
        strategy = BFSStrategy()
        graph = UndirectedUnweightedGraph()
    }

    @Test
    fun `find simply path in UndirectedUnweightedGraph`() {
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(unweightedEdge1To2)
        val pathfindingResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 2L,
            )
        assertIs<Result.Success<List<List<Long>>>>(pathfindingResult)
        assertEquals(listOf(listOf(0L)), pathfindingResult.data)
    }

    @Test
    fun `find path in UndirectedUnweightedGraph with same start and end`() {
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(unweightedEdge1To2)
        val pathfindingResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 1L,
            )
        assertIs<Result.Success<List<List<Long>>>>(pathfindingResult)
        assertEquals(listOf(emptyList()), pathfindingResult.data)
    }

    @Test
    fun `find path in UndirectedUnweightedGraph with several paths`() {
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(unweightedEdge1To2)
        graph.addEdge(unweightedEdge1To3)
        graph.addEdge(unweightedEdge2To3)
        val pathfindingResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 3L,
                maxPaths = 2,
            )
        assertIs<Result.Success<List<List<Long>>>>(pathfindingResult)
        assertEquals(listOf(listOf(1L), listOf(0L, 2L)), pathfindingResult.data)
    }

    @Test
    fun `find path in DirectedUnweightedGraph with several paths`() {
        graph = DirectedUnweightedGraph()
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(unweightedEdge1To2)
        val pathfindingResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 2L,
            )
        assertIs<Result.Success<List<List<Long>>>>(pathfindingResult)
        assertEquals(listOf(listOf(0L)), pathfindingResult.data)
    }

    @Test
    fun `find path in UndirectedWeightedGraph with several paths`() {
        graph = UndirectedWeightedGraph()
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addVertex(vertex4)
        graph.addVertex(vertex5)
        graph.addVertex(vertex6)
        graph.addEdge(weightedEdge1To2)
        graph.addEdge(weightedEdge1To3)
        graph.addEdge(weightedEdge2To4)
        graph.addEdge(weightedEdge3To4)
        graph.addEdge(weightedEdge1To4)
        graph.addEdge(weightedEdge4To5)
        graph.addEdge(weightedEdge4To6)
        graph.addEdge(weightedEdge5To6)
        val pathfindingResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 6L,
                maxPaths = 3,
            )
        assertIs<Result.Success<List<List<Long>>>>(pathfindingResult)
        assertTrue(
            actual = (
                pathfindingResult.data == listOf(listOf(1L, 6L), listOf(0L, 3L, 6L), listOf(2L, 4L, 6L)) ||
                    pathfindingResult.data == listOf(listOf(1L, 6L), listOf(0L, 3L, 6L), listOf(1L, 5L, 7L)) ||
                    pathfindingResult.data == listOf(listOf(1L, 6L), listOf(2L, 4L, 6L), listOf(0L, 3L, 6L)) ||
                    pathfindingResult.data == listOf(listOf(1L, 6L), listOf(2L, 4L, 6L), listOf(1L, 5L, 7L)) ||
                    pathfindingResult.data == listOf(listOf(1L, 6L), listOf(1L, 5L, 7L), listOf(0L, 3L, 6L)) ||
                    pathfindingResult.data == listOf(listOf(1L, 6L), listOf(1L, 5L, 7L), listOf(2L, 4L, 6L))
            ),
        )
    }

    @Test
    fun `not path found in UndirectedUnweightedGraph`() {
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(unweightedEdge1To3)
        val pathfindingResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 2L,
            )
        assertIs<Result.Error>(pathfindingResult)
        assertEquals("NoPathFound", pathfindingResult.error.type)
    }
}
