package model

import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.concrete.UndirectedWeightedGraph
import model.graph.entities.UnweightedEdge
import model.graph.entities.Vertex
import model.graph.entities.WeightedEdge
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GraphTests {
    @Test
    fun `undirected unweighted graph add vertex`() {
        val graph = UndirectedUnweightedGraph<Long>()
        graph.addVertex(Vertex(1, "1"))
        assertEquals(1, graph.vertices[1]?.id)
    }

    @Test
    fun `undirected unweighted graph get label`() {
        val graph = UndirectedUnweightedGraph<Long>()
        graph.addVertex(Vertex(1, "1"))
        assertEquals("1", graph.vertices[1]?.label)
    }

    @Test
    fun `undirected unweighted graph add edge`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        assertEquals(0, graph.addEdge(vertex1, vertex2))
    }

    @Test
    fun `undirected weighted graph add edge`() {
        val graph = UndirectedWeightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        assertEquals(0, graph.addEdge(vertex1, vertex2, 1))
    }

    @Test
    fun `undirected weighted graph get weight`() {
        val graph = UndirectedWeightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex1, vertex2, 1)
        assertEquals(1, (graph.edges[0] as WeightedEdge).weight)
    }

    @Test
    fun `directed weighted graph add edge`() {
        val graph = DirectedWeightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        assertEquals(0, graph.addEdge(vertex1, vertex2, 1))
    }

    @Test
    fun `directed weighted graph getNeighbors correct`() {
        val graph = DirectedWeightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        val vertex3 = Vertex(3L, "3")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(vertex1, vertex2, 1)
        graph.addEdge(vertex1, vertex3, 1)
        assertEquals(listOf(vertex2, vertex3), graph.getNeighbors(vertex1))
    }

    @Test
    fun `directed weighted graph getNeighbors with unknown vertex`() {
        val graph = DirectedWeightedGraph<Long>()
        assertEquals(emptyList(), graph.getNeighbors(Vertex(1, "1")))
    }

    @Test
    fun `directed weighted graph getNeighbors with edge direction is in wrong direction`() {
        val graph = DirectedWeightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex1, vertex2, 1)
        assertEquals(emptyList(), graph.getNeighbors(vertex2))
    }

    @Test
    fun `directed unweighted graph add edge`() {
        val graph = DirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        assertEquals(0, graph.addEdge(vertex1, vertex2))
    }

    @Test
    fun `directed unweighted graph getNeighbors correct`() {
        val graph = DirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        val vertex3 = Vertex(3L, "3")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(vertex1, vertex2)
        graph.addEdge(vertex1, vertex3)
        assertEquals(listOf(vertex2, vertex3), graph.getNeighbors(vertex1))
    }

    @Test
    fun `directed unweighted graph getNeighbors with unknown vertex`() {
        val graph = DirectedUnweightedGraph<Long>()
        assertEquals(emptyList(), graph.getNeighbors(Vertex(1, "1")))
    }

    @Test
    fun `directed unweighted graph getNeighbors with edge direction is in wrong direction`() {
        val graph = DirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex1, vertex2)
        assertEquals(emptyList(), graph.getNeighbors(vertex2))
    }

    @Test
    fun `undirected unweighted graph getVertex`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        graph.addVertex(vertex1)
        assertEquals(vertex1, graph.getVertex(1))
    }

    @Test
    fun `undirected unweighted graph getEdge`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex1, vertex2)
        assertEquals(UnweightedEdge(0L, vertex1, vertex2), graph.getEdge(0))
    }

    @Test
    fun `undirected unweighted graph getAllVertex`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        assertEquals(listOf(vertex1, vertex2), graph.getAllVertices())
    }

    @Test
    fun `undirected unweighted graph getAllEdge`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        val vertex3 = Vertex(3L, "3")
        val vertex4 = Vertex(4L, "4")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addVertex(vertex4)
        graph.addEdge(vertex1, vertex2)
        graph.addEdge(vertex3, vertex4)
        assertEquals(listOf(UnweightedEdge(0L, vertex1, vertex2), UnweightedEdge(1L, vertex3, vertex4)), graph.getAllEdges())
    }

    @Test
    fun `undirected unweighted graph getNeighbors correct version 1`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        val vertex3 = Vertex(3L, "3")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(vertex1, vertex2)
        graph.addEdge(vertex1, vertex3)
        assertEquals(listOf(vertex2, vertex3), graph.getNeighbors(vertex1))
    }

    @Test
    fun `undirected unweighted graph getNeighbors correct version 2`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        val vertex3 = Vertex(3L, "3")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(vertex2, vertex1)
        graph.addEdge(vertex3, vertex1)
        assertEquals(listOf(vertex2, vertex3), graph.getNeighbors(vertex1))
    }

    @Test
    fun `undirected unweighted graph getNeighbors with unknown vertex`() {
        val graph = UndirectedUnweightedGraph<Long>()
        assertEquals(emptyList(), graph.getNeighbors(Vertex(1, "1")))
    }

    @Test
    fun `undirected unweighted graph addEdge with unknown from vertex`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        assertEquals(null, graph.addEdge(vertex2, vertex1))
    }

    @Test
    fun `undirected unweighted graph addEdge with unknown to vertex`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        assertEquals(null, graph.addEdge(vertex1, vertex2))
    }

    @Test
    fun `undirected unweighted graph twice addEdge`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex1, vertex2)
        assertEquals(null, graph.addEdge(UnweightedEdge(0, vertex1, vertex2)))
    }

    @Test
    fun `undirected unweighted graph clear`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        assertEquals(true, graph.vertices.isEmpty())
        graph.addVertex(vertex1)
        assertEquals(false, graph.vertices.isEmpty())
        graph.addVertex(vertex2)
        assertEquals(true, graph.edges.isEmpty())
        graph.addEdge(vertex1, vertex2)
        assertEquals(false, graph.edges.isEmpty())
        graph.clear()
        assertEquals(true, graph.vertices.isEmpty())
        assertEquals(true, graph.edges.isEmpty())
    }

    @Test
    fun `undirected unweighted graph removeVertex correct`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        graph.addVertex(vertex1)
        assertEquals(1, graph.removeVertex(1))
    }

    @Test
    fun `undirected unweighted graph removeVertex with unknown id`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        graph.addVertex(vertex1)
        assertEquals(null, graph.removeVertex(52))
    }

    @Test
    fun `undirected unweighted graph removeVertex with edges version 1`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(3L, "2")
        val vertex3 = Vertex(3L, "3")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(vertex1, vertex2)
        graph.addEdge(vertex3, vertex1)
        assertEquals(1, graph.removeVertex(1))
    }

    @Test
    fun `undirected unweighted graph removeVertex with edges version 2`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(3L, "2")
        val vertex3 = Vertex(3L, "3")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(vertex2, vertex1)
        graph.addEdge(vertex1, vertex3)
        assertEquals(1, graph.removeVertex(1))
    }

    @Test
    fun `undirected unweighted graph removeVertex with edges version 3`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        val vertex3 = Vertex(3L, "3")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(vertex2, vertex3)
        assertEquals(1, graph.removeVertex(1))
    }

    @Test
    fun `undirected unweighted graph removeEdge by id`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex1, vertex2)
        assertEquals(0, graph.removeEdge(0))
    }

    @Test
    fun `undirected unweighted graph removeEdge by unknown id`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex1, vertex2)
        assertEquals(null, graph.removeEdge(1))
    }

    @Test
    fun `undirected unweighted graph removeEdge by from and to`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex1, vertex2)
        assertEquals(0, graph.removeEdge(vertex1.id, vertex2.id))
    }

    @Test
    fun `undirected unweighted graph removeEdge by from and to with wrong direction`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex2, vertex1)
        assertEquals(null, graph.removeEdge(vertex1.id, vertex2.id))
    }

    @Test
    fun `undirected unweighted graph removeEdge by from and to with unknown from`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex1, vertex2)
        assertEquals(null, graph.removeEdge(52, vertex2.id))
    }

    @Test
    fun `undirected unweighted graph removeEdge by from and to with unknown to`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex1, vertex2)
        assertEquals(null, graph.removeEdge(vertex1.id, 52))
    }

    @Test
    fun `undirected unweighted graph removeEdge by unknown from and unknown to`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(vertex1, vertex2)
        assertEquals(null, graph.removeEdge(52, 53))
    }

    @Test
    fun `undirected unweighted graph removeEdge by from and to with wrong to`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        val vertex3 = Vertex(3L, "3")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addEdge(vertex1, vertex3)
        assertEquals(null, graph.removeEdge(vertex1.id, vertex2.id))
    }

    @Test
    fun `directed weighted graph get isDirected and isWeighted values`() {
        val graph = DirectedWeightedGraph<Long>()
        assertEquals(true, graph.isWeighted)
        assertEquals(true, graph.isDirected)
    }
}
