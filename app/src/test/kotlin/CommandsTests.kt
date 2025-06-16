import io.mockk.*
import model.command.`class`.Command
import model.command.`class`.Commands
import model.command.enum.CommandEntities
import model.command.enum.CommandTypes
import model.graph.classes.DirectedUnweightedGraph
import model.graph.classes.DirectedWeightedGraph
import model.graph.classes.UndirectedUnweightedGraph
import model.graph.classes.UndirectedWeightedGraph
import model.graph.dataClasses.Vertex
import model.graph.interfaces.Graph
import model.result.Result
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CommandsTests {
    lateinit var command: Command
    lateinit var graph: Graph<Long, Long>
    lateinit var outputMessages: MutableList<String>
    lateinit var commands: Commands<Long, Long>

    @BeforeEach
    fun setup() {
        command = mockk()
        graph = mockk()
        outputMessages = mutableListOf()
        commands = Commands(command, graph, outputMessages)
    }

    // Correct commands

    @Test
    fun `add vertex`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.VERTEX
        every { command.parameters } returns mapOf("id" to "1", "label" to "1")
        every { graph.addVertex(Vertex(1, "1")) } returns 1

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Vertex added: id:1, label:1", result.data)
        verify { graph.addVertex(Vertex(1, "1")) }
    }

    @Test
    fun `add edge for undirected and unweighted graph`() {
        graph = mockk<UndirectedUnweightedGraph<Long>>()
        commands = Commands(command, graph as UndirectedUnweightedGraph, outputMessages)
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { graph.getVertex(1) } returns vertex1
        every { graph.getVertex(2) } returns vertex2
        every { (graph as UndirectedUnweightedGraph).addEdge(vertex1, vertex2) } returns 0

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Edge added: from=1, to=2", result.data)
        verify { (graph as UndirectedUnweightedGraph).addEdge(vertex1, vertex2) }
    }

    @Test
    fun `add edge for undirected and weighted graph`() {
        graph = mockk<UndirectedWeightedGraph<Long>>()
        commands = Commands(command, graph as UndirectedWeightedGraph, outputMessages)
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2", "weight" to "52")
        every { graph.getVertex(1) } returns vertex1
        every { graph.getVertex(2) } returns vertex2
        every { (graph as UndirectedWeightedGraph).addEdge(vertex1, vertex2, 52) } returns 0

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Edge added: from=1, to=2, weight=52", result.data)
        verify { (graph as UndirectedWeightedGraph).addEdge(vertex1, vertex2, 52) }
    }

    @Test
    fun `add edge for directed and unweighted graph`() {
        graph = mockk<DirectedUnweightedGraph<Long>>()
        commands = Commands(command, graph as DirectedUnweightedGraph, outputMessages)
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { graph.getVertex(1) } returns vertex1
        every { graph.getVertex(2) } returns vertex2
        every { (graph as DirectedUnweightedGraph).addEdge(vertex1, vertex2) } returns 0

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Edge added: from=1, to=2", result.data)
        verify { (graph as DirectedUnweightedGraph).addEdge(vertex1, vertex2) }
    }

    @Test
    fun `add edge for directed and weighted graph`() {
        graph = mockk<DirectedWeightedGraph<Long>>()
        commands = Commands(command, graph as DirectedWeightedGraph, outputMessages)
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2", "weight" to "52")
        every { graph.getVertex(1) } returns vertex1
        every { graph.getVertex(2) } returns vertex2
        every { (graph as DirectedWeightedGraph).addEdge(vertex1, vertex2, 52) } returns 0

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Edge added: from=1, to=2, weight=52", result.data)
        verify { (graph as DirectedWeightedGraph).addEdge(vertex1, vertex2, 52) }
    }

    @Test
    fun `rm vertex`() {
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.VERTEX
        every { command.parameters } returns mapOf("id" to "1")
        every { graph.removeVertex(1) } returns 1

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Vertex with id:1 removed", result.data)
        verify { graph.removeVertex(1) }
    }

    @Test
    fun `rm edge for undirected and unweighted graph by id`() {
        graph = mockk<UndirectedUnweightedGraph<Long>>()
        commands = Commands(command, graph as UndirectedUnweightedGraph, outputMessages)
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("id" to "1")
        every { (graph as UndirectedUnweightedGraph).removeEdge(1) } returns 1

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Edge with id:1 removed", result.data)
        verify { (graph as UndirectedUnweightedGraph).removeEdge(1) }
    }

    @Test
    fun `rm edge for undirected and unweighted graph by from and to`() {
        graph = mockk<UndirectedUnweightedGraph<Long>>()
        commands = Commands(command, graph as UndirectedUnweightedGraph, outputMessages)
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { (graph as UndirectedUnweightedGraph).removeEdge(1, 2) } returns 0
        every { (graph as UndirectedUnweightedGraph).removeEdge(2, 1) } returns 0

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Edge with from:1 and to:2 removed", result.data)
        verify { (graph as UndirectedUnweightedGraph).removeEdge(1, 2) }
        verify { (graph as UndirectedUnweightedGraph).removeEdge(2, 1) }
    }

    @Test
    fun `rm edge for undirected and weighted graph by from and to`() {
        graph = mockk<UndirectedWeightedGraph<Long>>()
        commands = Commands(command, graph as UndirectedWeightedGraph, outputMessages)
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { (graph as UndirectedWeightedGraph).removeEdge(1, 2) } returns 0
        every { (graph as UndirectedWeightedGraph).removeEdge(2, 1) } returns 0

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Edge with from:1 and to:2 removed", result.data)
        verify { (graph as UndirectedWeightedGraph).removeEdge(1, 2) }
        verify { (graph as UndirectedWeightedGraph).removeEdge(2, 1) }
    }

    @Test
    fun `rm edge for directed and unweighted graph by from and to`() {
        graph = mockk<DirectedUnweightedGraph<Long>>()
        commands = Commands(command, graph as DirectedUnweightedGraph, outputMessages)
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { (graph as DirectedUnweightedGraph).removeEdge(1, 2) } returns 0

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Edge with from:1 and to:2 removed", result.data)
        verify { (graph as DirectedUnweightedGraph).removeEdge(1, 2) }
    }

    @Test
    fun `rm edge for directed and weighted graph by from and to`() {
        graph = mockk<DirectedWeightedGraph<Long>>()
        commands = Commands(command, graph as DirectedWeightedGraph, outputMessages)
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { (graph as DirectedWeightedGraph).removeEdge(1, 2) } returns 0

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Edge with from:1 and to:2 removed", result.data)
        verify { (graph as DirectedWeightedGraph).removeEdge(1, 2) }
    }

    @Test
    fun `clear graph`() {
        every { command.type } returns CommandTypes.CLEAR
        every { command.entity } returns CommandEntities.GRAPH
        every { graph.clear() } just Runs

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Graph cleared", result.data)
        verify { graph.clear() }
    }

    @Test
    fun `clear output messages`() {
        every { command.type } returns CommandTypes.CLEAR
        every { command.entity } returns CommandEntities.COMMAND_OUTPUT

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Command line cleared", result.data)
    }

    @Test
    fun `just help`() {
        every { command.type } returns CommandTypes.HELP
        every { command.entity } returns CommandEntities.APP

        val result = commands.execute()

        assertIs<Result.Success<String>>(result)
        assertEquals("Help information", result.data)
    }

    // Incorrect commands

    @Test
    fun `unknown entity in add command`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.APP
        val result = commands.execute()
        assertIs<Result.Error>(result)
        assertEquals("UnknownEntity", result.error.type)
        assertEquals("Unknown entity for command: Unsupported entity for add command", result.error.description)
    }

    @Test
    fun `add edge for undirected and unweighted graph with unknown edgeId`() {
        graph = mockk<UndirectedUnweightedGraph<Long>>()
        commands = Commands(command, graph as UndirectedUnweightedGraph, outputMessages)
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { graph.getVertex(1) } returns vertex1
        every { graph.getVertex(2) } returns vertex2
        every { (graph as UndirectedUnweightedGraph).addEdge(vertex1, vertex2) } returns null

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("EdgeAdditionFailed", result.error.type)
        assertEquals("Failed to add edge: from=1, to=2 ", result.error.description)
        verify { (graph as UndirectedUnweightedGraph).addEdge(vertex1, vertex2) }
    }

    @Test
    fun `add edge for undirected and weighted graph with unknown edgeId`() {
        graph = mockk<UndirectedWeightedGraph<Long>>()
        commands = Commands(command, graph as UndirectedWeightedGraph, outputMessages)
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2", "weight" to "52")
        every { graph.getVertex(1) } returns vertex1
        every { graph.getVertex(2) } returns vertex2
        every { (graph as UndirectedWeightedGraph).addEdge(vertex1, vertex2, 52) } returns null

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("EdgeAdditionFailed", result.error.type)
        assertEquals("Failed to add edge: from=1, to=2 , weight=52", result.error.description)
        verify { (graph as UndirectedWeightedGraph).addEdge(vertex1, vertex2, 52) }
    }

    @Test
    fun `add edge for directed and unweighted graph with unknown edgeId`() {
        graph = mockk<DirectedUnweightedGraph<Long>>()
        commands = Commands(command, graph as DirectedUnweightedGraph, outputMessages)
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { graph.getVertex(1) } returns vertex1
        every { graph.getVertex(2) } returns vertex2
        every { (graph as DirectedUnweightedGraph).addEdge(vertex1, vertex2) } returns null

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("EdgeAdditionFailed", result.error.type)
        assertEquals("Failed to add edge: from=1, to=2 ", result.error.description)
        verify { (graph as DirectedUnweightedGraph).addEdge(vertex1, vertex2) }
    }

    @Test
    fun `add edge for directed and weighted graph with unknown edgeId`() {
        graph = mockk<DirectedWeightedGraph<Long>>()
        commands = Commands(command, graph as DirectedWeightedGraph, outputMessages)
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2", "weight" to "52")
        every { graph.getVertex(1) } returns vertex1
        every { graph.getVertex(2) } returns vertex2
        every { (graph as DirectedWeightedGraph).addEdge(vertex1, vertex2, 52) } returns null

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("EdgeAdditionFailed", result.error.type)
        assertEquals("Failed to add edge: from=1, to=2 , weight=52", result.error.description)
        verify { (graph as DirectedWeightedGraph).addEdge(vertex1, vertex2, 52) }
    }

    @Test
    fun `add edge for unknown graph`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { graph.getVertex(1) } returns Vertex(1, "1")
        every { graph.getVertex(2) } returns Vertex(2, "2")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("UnknownGraphType", result.error.type)
        assertEquals("Unknown graph type: Graph\$Subclass0", result.error.description)
    }

    @Test
    fun `add edge for undirected and weighted graph without weight`() {
        graph = mockk<UndirectedWeightedGraph<Long>>()
        commands = Commands(command, graph as UndirectedWeightedGraph, outputMessages)
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { graph.getVertex(1) } returns Vertex(1, "1")
        every { graph.getVertex(2) } returns Vertex(2, "2")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter weight must be Long", result.error.description)
    }

    @Test
    fun `add edge for undirected and weighted graph with incorrect weight`() {
        graph = mockk<UndirectedWeightedGraph<Long>>()
        commands = Commands(command, graph as UndirectedWeightedGraph, outputMessages)
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2", "weight" to "incorrect")
        every { graph.getVertex(1) } returns Vertex(1, "1")
        every { graph.getVertex(2) } returns Vertex(2, "2")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter weight must be Long", result.error.description)
    }

    @Test
    fun `add edge for directed and weighted graph without weight`() {
        graph = mockk<DirectedWeightedGraph<Long>>()
        commands = Commands(command, graph as DirectedWeightedGraph, outputMessages)
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { graph.getVertex(1) } returns Vertex(1, "1")
        every { graph.getVertex(2) } returns Vertex(2, "2")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter weight must be Long", result.error.description)
    }

    @Test
    fun `add edge for directed and weighted graph with incorrect weight`() {
        graph = mockk<DirectedWeightedGraph<Long>>()
        commands = Commands(command, graph as DirectedWeightedGraph, outputMessages)
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2", "weight" to "incorrect")
        every { graph.getVertex(1) } returns Vertex(1, "1")
        every { graph.getVertex(2) } returns Vertex(2, "2")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter weight must be Long", result.error.description)
    }

    @Test
    fun `add edge without from`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("to" to "2")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: from is required", result.error.description)
    }

    @Test
    fun `add edge without to`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: to is required", result.error.description)
    }

    @Test
    fun `add edge with incorrect form`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "incorrect", "to" to "2")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter from must be Long", result.error.description)
    }

    @Test
    fun `add edge with incorrect to`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("to" to "incorrect", "from" to "1")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter to must be Long", result.error.description)
    }

    @Test
    fun `add edge with unknown from vertex`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { graph.getVertex(1) } returns null

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("VertexNotFound", result.error.type)
        assertEquals("Vertex not found: id=1", result.error.description)
    }

    @Test
    fun `add edge with unknown to vertex`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "2")
        every { graph.getVertex(1) } returns Vertex(1, "1")
        every { graph.getVertex(2) } returns null

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("VertexNotFound", result.error.type)
        assertEquals("Vertex not found: id=2", result.error.description)
    }

    @Test
    fun `add vertex without id`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.VERTEX
        every { command.parameters } returns mapOf("label" to "label")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: id is required", result.error.description)
    }

    @Test
    fun `add vertex without label`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.VERTEX
        every { command.parameters } returns mapOf("id" to "1")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: label is required", result.error.description)
    }

    @Test
    fun `add vertex with incorrect id`() {
        every { command.type } returns CommandTypes.ADD
        every { command.entity } returns CommandEntities.VERTEX
        every { command.parameters } returns mapOf("id" to "incorrect", "label" to "label")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter id must be Long", result.error.description)
    }

    @Test
    fun `rm edge without correct parameters`() {
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("unknown" to "1")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Remove edge command must specify 'from' and 'to' or 'id'", result.error.description)
    }

    @Test
    fun `rm vertex without id`() {
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.VERTEX
        every { command.parameters } returns emptyMap()

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: id is required", result.error.description)
    }

    @Test
    fun `rm vertex with incorrect id`() {
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.VERTEX
        every { command.parameters } returns mapOf("id" to "incorrect")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter id must be Long", result.error.description)
    }

    @Test
    fun `rm edge with incorrect from`() {
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "incorrect", "to" to "2")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter from must be Long", result.error.description)
    }

    @Test
    fun `rm edge with incorrect to`() {
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1", "to" to "incorrect")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter to must be Long", result.error.description)
    }

    @Test
    fun `rm edge with from and without to`() {
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("from" to "1")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Remove edge command must specify 'from' and 'to' or 'id'", result.error.description)
    }

    @Test
    fun `rm edge with incorrect id`() {
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.EDGE
        every { command.parameters } returns mapOf("id" to "incorrect")

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter id must be Long", result.error.description)
    }

    @Test
    fun `rm unknown entity`() {
        every { command.type } returns CommandTypes.RM
        every { command.entity } returns CommandEntities.APP

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("UnknownEntity", result.error.type)
        assertEquals("Unknown entity for command: Unsupported entity for rm/remove command", result.error.description)
    }

    @Test
    fun `clear unknown entity`() {
        every { command.type } returns CommandTypes.CLEAR
        every { command.entity } returns CommandEntities.APP

        val result = commands.execute()

        assertIs<Result.Error>(result)
        assertEquals("UnknownEntity", result.error.type)
        assertEquals("Unknown entity for command: Unsupported entity for clear command", result.error.description)
    }
}
