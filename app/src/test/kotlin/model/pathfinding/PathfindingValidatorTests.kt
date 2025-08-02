package model.pathfinding

import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.contracts.Graph
import model.graph.entities.UnweightedEdge
import model.graph.entities.Vertex
import model.graph.pathfinding.BFSStrategy
import model.graph.pathfinding.PathfindingValidator
import model.result.Result
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PathfindingValidatorTests {
    lateinit var strategy: BFSStrategy<Long, Long>
    lateinit var graph: Graph<Long, Long>
    var startError by Delegates.notNull<Boolean>()
    var endError by Delegates.notNull<Boolean>()
    var maxPathsError by Delegates.notNull<Boolean>()
    var errorMessage: String? = null

    val vertex1 = Vertex(1L, "1")
    val vertex2 = Vertex(2L, "2")

    val unweightedEdge1To2 = UnweightedEdge(0L, vertex1, vertex2)

    @BeforeEach
    fun setup() {
        strategy = BFSStrategy()
        graph = UndirectedUnweightedGraph()
        startError = false
        endError = false
        maxPathsError = false
        errorMessage = null
    }

    @Test
    fun `find path in empty graph`() {
        val pathfindingResult =
            strategy.findPath(
                graph = null,
                start = 1L,
                end = 2L,
            )
        assertIs<Result.Error>(pathfindingResult)
        assertEquals("EmptyGraph", pathfindingResult.error.type)
    }

    @Test
    fun `find path with empty vertices`() {
        graph.addEdge(unweightedEdge1To2)
        val pathfindingResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 2L,
            )
        assertIs<Result.Error>(pathfindingResult)
        assertEquals("EmptyGraph", pathfindingResult.error.type)
    }

    @Test
    fun `find path with empty edges`() {
        graph.addVertex(vertex1)
        val pathfindingResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 2L,
            )
        assertIs<Result.Error>(pathfindingResult)
        assertEquals("EmptyGraph", pathfindingResult.error.type)
    }

    @Test
    fun `find path with unknown start vertex`() {
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(unweightedEdge1To2)
        val pathfindingResult =
            strategy.findPath(
                graph = graph,
                start = 3L,
                end = 2L,
            )
        assertIs<Result.Error>(pathfindingResult)
        assertEquals("VertexNotFound", pathfindingResult.error.type)
    }

    @Test
    fun `find path with unknown end vertex`() {
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(unweightedEdge1To2)
        val pathfindingResult =
            strategy.findPath(
                graph = graph,
                start = 2L,
                end = 3L,
            )
        assertIs<Result.Error>(pathfindingResult)
        assertEquals("VertexNotFound", pathfindingResult.error.type)
    }

    @Test
    fun `find path correct`() {
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(unweightedEdge1To2)
        val pathfindingResult =
            strategy.findPath(
                graph = graph,
                start = 1L,
                end = 2L,
            )
        assertIs<Result.Success<Boolean>>(pathfindingResult)
    }

    @Test
    fun `build PathfindingValidator`() {
        val result = PathfindingValidator<Long, Long>()
        assertIs<PathfindingValidator<Long, Long>>(result)
    }

    @Test
    fun `check validateInputParameters with null start and null end`() {
        PathfindingValidator.validateInputParameters(
            startId = null,
            endId = null,
            maxPaths = 1,
            isVertexExist =
                { vertexId: Long ->
                    graph.vertices.containsKey(vertexId)
                },
            onStartError = { startError = true },
            onEndError = { endError = true },
            onMaxPathsError = { maxPathsError = true },
            onSetErrorMessage = { newMessage: String ->
                errorMessage = newMessage
            },
        )
        assertTrue(startError)
        assertTrue(endError)
        assertFalse(maxPathsError)
        assertEquals("Vertex id must be Long type", errorMessage)
    }

    @Test
    fun `check validateInputParameters with null end`() {
        PathfindingValidator.validateInputParameters(
            startId = 1L,
            endId = null,
            maxPaths = 1,
            isVertexExist =
                { vertexId: Long ->
                    graph.vertices.containsKey(vertexId)
                },
            onStartError = { startError = true },
            onEndError = { endError = true },
            onMaxPathsError = { maxPathsError = true },
            onSetErrorMessage = { newMessage: String ->
                errorMessage = newMessage
            },
        )
        assertFalse(startError)
        assertTrue(endError)
        assertFalse(maxPathsError)
        assertEquals("Vertex id must be Long type", errorMessage)
    }

    @Test
    fun `check validateInputParameters with null start`() {
        PathfindingValidator.validateInputParameters(
            startId = null,
            endId = 1L,
            maxPaths = 1,
            isVertexExist =
                { vertexId: Long ->
                    graph.vertices.containsKey(vertexId)
                },
            onStartError = { startError = true },
            onEndError = { endError = true },
            onMaxPathsError = { maxPathsError = true },
            onSetErrorMessage = { newMessage: String ->
                errorMessage = newMessage
            },
        )
        assertTrue(startError)
        assertFalse(endError)
        assertFalse(maxPathsError)
        assertEquals("Vertex id must be Long type", errorMessage)
    }

    @Test
    fun `check validateInputParameters with null maxPaths`() {
        PathfindingValidator.validateInputParameters(
            startId = 1L,
            endId = 2L,
            maxPaths = null,
            isVertexExist =
                { vertexId: Long ->
                    graph.vertices.containsKey(vertexId)
                },
            onStartError = { startError = true },
            onEndError = { endError = true },
            onMaxPathsError = { maxPathsError = true },
            onSetErrorMessage = { newMessage: String ->
                errorMessage = newMessage
            },
        )
        assertFalse(startError)
        assertFalse(endError)
        assertTrue(maxPathsError)
        assertEquals("Max paths must be Int type", errorMessage)
    }

    @Test
    fun `check validateInputParameters with incorrect maxPaths`() {
        PathfindingValidator.validateInputParameters(
            startId = 1L,
            endId = 2L,
            maxPaths = -1,
            isVertexExist =
                { vertexId: Long ->
                    graph.vertices.containsKey(vertexId)
                },
            onStartError = { startError = true },
            onEndError = { endError = true },
            onMaxPathsError = { maxPathsError = true },
            onSetErrorMessage = { newMessage: String ->
                errorMessage = newMessage
            },
        )
        assertFalse(startError)
        assertFalse(endError)
        assertTrue(maxPathsError)
        assertEquals("Max paths must be positive integer", errorMessage)
    }

    @Test
    fun `check validateInputParameters with unknown startId`() {
        graph.addVertex(vertex2)
        PathfindingValidator.validateInputParameters(
            startId = 1L,
            endId = 2L,
            maxPaths = 1,
            isVertexExist =
                { vertexId: Long ->
                    graph.vertices.containsKey(vertexId)
                },
            onStartError = { startError = true },
            onEndError = { endError = true },
            onMaxPathsError = { maxPathsError = true },
            onSetErrorMessage = { newMessage: String ->
                errorMessage = newMessage
            },
        )
        assertTrue(startError)
        assertFalse(endError)
        assertFalse(maxPathsError)
        assertEquals("Vertex with 1 id not found in graph", errorMessage)
    }

    @Test
    fun `check validateInputParameters with unknown endId`() {
        graph.addVertex(vertex2)
        PathfindingValidator.validateInputParameters(
            startId = 2L,
            endId = 1L,
            maxPaths = 1,
            isVertexExist =
                { vertexId: Long ->
                    graph.vertices.containsKey(vertexId)
                },
            onStartError = { startError = true },
            onEndError = { endError = true },
            onMaxPathsError = { maxPathsError = true },
            onSetErrorMessage = { newMessage: String ->
                errorMessage = newMessage
            },
        )
        assertFalse(startError)
        assertTrue(endError)
        assertFalse(maxPathsError)
        assertEquals("Vertex with 1 id not found in graph", errorMessage)
    }

    @Test
    fun `check validateInputParameters with unknown startId and endId`() {
        PathfindingValidator.validateInputParameters(
            startId = 1L,
            endId = 2L,
            maxPaths = 1,
            isVertexExist =
                { vertexId: Long ->
                    graph.vertices.containsKey(vertexId)
                },
            onStartError = { startError = true },
            onEndError = { endError = true },
            onMaxPathsError = { maxPathsError = true },
            onSetErrorMessage = { newMessage: String ->
                errorMessage = newMessage
            },
        )
        assertTrue(startError)
        assertTrue(endError)
        assertFalse(maxPathsError)
        assertEquals("Vertices with 1 and 2 ids not found in graph", errorMessage)
    }

    @Test
    fun `check validateInputParameters with null graph`() {
        PathfindingValidator.validateInputParameters(
            startId = 1L,
            endId = 2L,
            maxPaths = 1,
            isVertexExist =
                { vertexId: Long ->
                    null
                },
            onStartError = { startError = true },
            onEndError = { endError = true },
            onMaxPathsError = { maxPathsError = true },
            onSetErrorMessage = { newMessage: String ->
                errorMessage = newMessage
            },
        )
        assertTrue(startError)
        assertTrue(endError)
        assertFalse(maxPathsError)
        assertEquals("Graph not found", errorMessage)
    }

    @Test
    fun `check validateInputParameters correct`() {
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        PathfindingValidator.validateInputParameters(
            startId = 1L,
            endId = 2L,
            maxPaths = 1,
            isVertexExist =
                { vertexId: Long ->
                    graph.vertices.containsKey(vertexId)
                },
            onStartError = { startError = true },
            onEndError = { endError = true },
            onMaxPathsError = { maxPathsError = true },
            onSetErrorMessage = { newMessage: String ->
                errorMessage = newMessage
            },
        )
        assertFalse(startError)
        assertFalse(endError)
        assertFalse(maxPathsError)
        assertEquals(null, errorMessage)
    }
}
