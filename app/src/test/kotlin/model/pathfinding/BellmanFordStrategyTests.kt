package model.pathfinding

import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.concrete.UndirectedWeightedGraph
import model.graph.contracts.Graph
import model.graph.entities.UnweightedEdge
import model.graph.entities.Vertex
import model.graph.entities.WeightedEdge
import model.graph.pathfinding.BellmanFordStrategy
import model.result.Result
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class BellmanFordStrategyTests {
    lateinit var strategy: BellmanFordStrategy<Long, Long>
    lateinit var graph: Graph<Long, Long>

    val vertex1 = Vertex(1L, "1")
    val vertex2 = Vertex(2L, "2")
    val vertex3 = Vertex(3L, "3")
    val vertex4 = Vertex(4L, "4")
    val vertex5 = Vertex(5L, "5")
    val unweightedEdge1To2 = UnweightedEdge(0L, vertex1, vertex2)
    val weightedEdge1To2 = WeightedEdge(0L, vertex1, vertex2, 4)
    val weightedEdge1To3 = WeightedEdge(1L, vertex1, vertex3, 2)
    val weightedEdge2To3 = WeightedEdge(2L, vertex2, vertex3, 3)
    val weightedEdge2To4 = WeightedEdge(3L, vertex2, vertex4, 2)
    val weightedEdge2To5 = WeightedEdge(4L, vertex2, vertex5, 3)
    val weightedEdge3To2 = WeightedEdge(5L, vertex3, vertex2, 1)
    val weightedEdge3To4 = WeightedEdge(6L, vertex3, vertex4, 4)
    val weightedEdge4To5 = WeightedEdge(7L, vertex4, vertex5, -1)
    val weightedEdge5To4 = WeightedEdge(8L, vertex5, vertex4, 3)

    val weightedEdge4To5v2 = WeightedEdge(7L, vertex4, vertex5, -2)
    val weightedEdge5To4v2 = WeightedEdge(8L, vertex5, vertex4, -2)

    @BeforeEach
    fun setup() {
        strategy = BellmanFordStrategy()
        graph = UndirectedWeightedGraph()
    }

    @Test
    fun `find path in UndirectedWeightedGraph with same startId and endId`() {
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(weightedEdge1To2)
        val findResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 1L,
            )
        assertIs<Result.Success<List<List<Long>>>>(findResult)
        assertEquals(listOf(emptyList()), findResult.data)
    }

    @Test
    fun `find simply path in UndirectedWeightedGraph`() {
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(weightedEdge1To2)
        graph.addEdge(weightedEdge1To3)
        graph.addEdge(weightedEdge2To3)
        graph.addEdge(weightedEdge3To2)
        val findResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 2L,
            )
        assertIs<Result.Success<List<List<Long>>>>(findResult)
        assertEquals(listOf(listOf(1L, 5L)), findResult.data)
    }

    @Test
    fun `find path in DirectedWeightedGraph`() {
        graph = DirectedWeightedGraph()
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addVertex(vertex4)
        graph.addVertex(vertex5)
        graph.addEdge(weightedEdge1To2)
        graph.addEdge(weightedEdge1To3)
        graph.addEdge(weightedEdge2To3)
        graph.addEdge(weightedEdge2To4)
        graph.addEdge(weightedEdge2To5)
        graph.addEdge(weightedEdge3To2)
        graph.addEdge(weightedEdge3To4)
        graph.addEdge(weightedEdge4To5)
        graph.addEdge(weightedEdge5To4)
        val findResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 5L,
            )
        assertIs<Result.Success<List<List<Long>>>>(findResult)
        assertEquals(listOf(listOf(1L, 5L, 3L, 7L)), findResult.data)
    }

    @Test
    fun `find path in UndirectedUnweightedGraph`() {
        graph = UndirectedUnweightedGraph()
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(unweightedEdge1To2)
        val findResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 2L,
            )
        assertIs<Result.Success<List<List<Long>>>>(findResult)
        assertEquals(listOf(listOf(0L)), findResult.data)
    }

    @Test
    fun `find path in DirectedUnweightedGraph`() {
        graph = DirectedUnweightedGraph()
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(unweightedEdge1To2)
        val findResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 2L,
            )
        assertIs<Result.Success<List<List<Long>>>>(findResult)
        assertEquals(listOf(listOf(0L)), findResult.data)
    }

    @Test
    fun `no path found in UndirectedWeightedGraph`() {
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(weightedEdge1To2)
        val findResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 3L,
            )
        assertIs<Result.Error>(findResult)
        assertEquals("NoPathFound", findResult.error.type)
    }

    @Test
    fun `find path in UndirectedWeightedGraph with invalid graph`() {
        val findResult =
            strategy.findPath(
                graph = null,
                start = 1L,
                end = 2L,
            )
        assertIs<Result.Error>(findResult)
        assertEquals("EmptyGraph", findResult.error.type)
    }

    @Test
    fun `find path in DirectedWeightedGraph with negative cycle`() {
        graph = DirectedWeightedGraph()
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addVertex(vertex4)
        graph.addVertex(vertex5)
        graph.addEdge(weightedEdge1To2)
        graph.addEdge(weightedEdge1To3)
        graph.addEdge(weightedEdge2To3)
        graph.addEdge(weightedEdge2To4)
        graph.addEdge(weightedEdge2To5)
        graph.addEdge(weightedEdge3To2)
        graph.addEdge(weightedEdge3To4)
        graph.addEdge(weightedEdge4To5v2)
        graph.addEdge(weightedEdge5To4v2)
        val findResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 5L,
            )
        assertIs<Result.Error>(findResult)
        assertEquals("NegativeCycle", findResult.error.type)
    }

    @Test
    fun `find path in UndirectedWeightedGraph with negative cycle`() {
        graph = UndirectedWeightedGraph()
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addVertex(vertex4)
        graph.addVertex(vertex5)
        graph.addEdge(weightedEdge1To2)
        graph.addEdge(weightedEdge1To3)
        graph.addEdge(weightedEdge2To3)
        graph.addEdge(weightedEdge2To4)
        graph.addEdge(weightedEdge2To5)
        graph.addEdge(weightedEdge3To2)
        graph.addEdge(weightedEdge3To4)
        graph.addEdge(weightedEdge4To5v2)
        graph.addEdge(weightedEdge5To4v2)
        val findResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 5L,
            )
        assertIs<Result.Error>(findResult)
        assertEquals("NegativeCycle", findResult.error.type)
    }
}
