import model.database.sqlite.createDatabase
import model.database.sqlite.repository.EdgeRepository
import model.database.sqlite.repository.GraphRepository
import model.database.sqlite.repository.VertexRepository
import model.fileHandler.ConvertModes
import model.fileHandler.FileExtensions
import model.fileHandler.converter.Converter
import model.result.Result
import org.coremapx.app.userDirectory.UserDirectory
import org.coremapx.graph.GraphDatabase
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import orgcoremapxapp.Edges
import orgcoremapxapp.Vertices
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ConverterTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_converter_test_" + System.currentTimeMillis(),
            )
        }
    }

    @TempDir
    lateinit var tempDir: File

    @Test
    fun `convert graph to graph correct`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            isDirected=false
            isWeighted=false
            Graph:
            add vertex 1 1
            add vertex 2 2
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.GRAPH, ConvertModes.LOAD, null)
        assertTrue(result is Result.Success)
        assertEquals(file, result.data)
    }

    @Test
    fun `convert graph to graph validate error`() {
        val file = File(tempDir, "test.graph")
        file.writeText("")
        val result = Converter.convert(file, FileExtensions.GRAPH, ConvertModes.LOAD, null)
        assertTrue(result is Result.Error)
        assertEquals("NotFoundInfoMarker", result.error.type)
    }

    @Test
    fun `convert json to is weighted graph correct`() {
        val file = File(tempDir, "test.json")
        file.writeText(
            """
            {
              "info": {
                "name": "Template",
                "author": "User",
                "isDirected": true,
                "isWeighted": true
              },
              "graph": {
                "vertices": [
                  {"id": 1, "label": "1"},
                  {"id": 2, "label": "2"}
                ],
                "edges": [
                  {"from": 1, "to": 2, "weight": 52}
                ]
              }
            }
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.GRAPH, ConvertModes.LOAD, null)
        assertTrue(result is Result.Success)
        val expectedFile = File(tempDir, "test.graph")
        expectedFile.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            isWeighted=true
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2 52
            """.trimIndent(),
        )
        assertEquals(expectedFile.name, result.data.name)
        assertEquals(expectedFile.readLines(), result.data.readLines())
    }

    @Test
    fun `convert json to is not weighted graph correct`() {
        val file = File(tempDir, "test.json")
        file.writeText(
            """
            {
              "info": {
                "name": "Template",
                "author": "User",
                "isDirected": true,
                "isWeighted": false
              },
              "graph": {
                "vertices": [
                  {"id": 1, "label": "1"},
                  {"id": 2, "label": "2"}
                ],
                "edges": [
                  {"from": 1, "to": 2}
                ]
              }
            }
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.GRAPH, ConvertModes.LOAD, null)
        assertTrue(result is Result.Success)
        val expectedFile = File(tempDir, "test.graph")
        expectedFile.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            isWeighted=false
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2
            """.trimIndent(),
        )
        assertEquals(expectedFile.name, result.data.name)
        assertEquals(expectedFile.readLines(), result.data.readLines())
    }

    @Test
    fun `convert and save json to is not weighted graph correct`() {
        val file = File(tempDir, "test.json")
        file.writeText(
            """
            {
              "info": {
                "name": "Template",
                "author": "User",
                "isDirected": true,
                "isWeighted": false
              },
              "graph": {
                "vertices": [
                  {"id": 1, "label": "1"},
                  {"id": 2, "label": "2"}
                ],
                "edges": [
                  {"from": 1, "to": 2}
                ]
              }
            }
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.GRAPH, ConvertModes.SAVE, null)
        assertTrue(result is Result.Success)
        val expectedFile = File(tempDir, "test.graph")
        expectedFile.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            isWeighted=false
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2
            """.trimIndent(),
        )
        assertEquals(expectedFile.name, result.data.name)
        assertEquals(expectedFile.readLines(), result.data.readLines())
    }

    @Test
    fun `convert IR to JSON weighted correct`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            isWeighted=true
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2 52
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.JSON, ConvertModes.LOAD, null)
        assertTrue(result is Result.Success)
        val expectedFile = File(tempDir, "test.json")
        expectedFile.writeText(
            """
            {
                "info": {
                    "name": "Template",
                    "author": "User",
                    "isDirected": true,
                    "isWeighted": true
                },
                "graph": {
                    "vertices": [
                        {
                            "id": 1,
                            "label": "1"
                        },
                        {
                            "id": 2,
                            "label": "2"
                        }
                    ],
                    "edges": [
                        {
                            "from": 1,
                            "to": 2,
                            "weight": 52
                        }
                    ]
                }
            }
            """.trimIndent(),
        )
        assertEquals(expectedFile.name, result.data.name)
        assertEquals(expectedFile.readLines(), result.data.readLines())
    }

    @Test
    fun `convert IR to JSON unweighted correct`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            isWeighted=false
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.JSON, ConvertModes.LOAD, null)
        assertTrue(result is Result.Success)
        val expectedFile = File(tempDir, "test.json")
        expectedFile.writeText(
            """
            {
                "info": {
                    "name": "Template",
                    "author": "User",
                    "isDirected": true,
                    "isWeighted": false
                },
                "graph": {
                    "vertices": [
                        {
                            "id": 1,
                            "label": "1"
                        },
                        {
                            "id": 2,
                            "label": "2"
                        }
                    ],
                    "edges": [
                        {
                            "from": 1,
                            "to": 2
                        }
                    ]
                }
            }
            """.trimIndent(),
        )
        assertEquals(expectedFile.name, result.data.name)
        assertEquals(expectedFile.readLines(), result.data.readLines())
    }

    @Test
    fun `convert and save IR to JSON unweighted correct`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            isWeighted=false
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.JSON, ConvertModes.SAVE, null)
        assertTrue(result is Result.Success)
        val expectedFile = File(tempDir, "test.json")
        expectedFile.writeText(
            """
            {
                "info": {
                    "name": "Template",
                    "author": "User",
                    "isDirected": true,
                    "isWeighted": false
                },
                "graph": {
                    "vertices": [
                        {
                            "id": 1,
                            "label": "1"
                        },
                        {
                            "id": 2,
                            "label": "2"
                        }
                    ],
                    "edges": [
                        {
                            "from": 1,
                            "to": 2
                        }
                    ]
                }
            }
            """.trimIndent(),
        )
        assertEquals(expectedFile.name, result.data.name)
        assertEquals(expectedFile.readLines(), result.data.readLines())
    }

    @Test
    fun `convert IR to SQL correct`() {
        val file = File(tempDir, "testSQL.graph")
        file.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            isWeighted=true
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2 52
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.SQL, ConvertModes.LOAD, null)
        assertTrue(result is Result.Success)
        val dbFile = result.data
        assertEquals("testSQL.db", dbFile.name)
        val database: GraphDatabase = createDatabase(dbFile.absolutePath)
        val info = GraphRepository(database).getGraphById(1L)
        assertNotNull(info)
        assertEquals("Template", info.name)
        assertEquals("User", info.author)
        assertEquals(1L, info.isDirected)
        assertEquals(1L, info.isWeighted)
        val vertices = VertexRepository(database).getVerticesByGraph(1L)
        assertEquals(2, vertices.size)
        assertEquals(Vertices(1L, 1L, 1L, "1"), vertices[0])
        assertEquals(Vertices(2L, 1L, 2L, "2"), vertices[1])
        val edges = EdgeRepository(database).getEdgesByGraph(1L)
        assertEquals(1, edges.size)
        assertEquals(Edges(1L, 1L, 1L, 2L, 52), edges[0])
    }

    @Test
    fun `convert IR to SQL unweighted correct`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            isWeighted=false
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.SQL, ConvertModes.LOAD, null)
        assertTrue(result is Result.Success)
        val dbFile = result.data
        assertEquals("test.db", dbFile.name)
        val database: GraphDatabase = createDatabase(dbFile.absolutePath)
        val info = GraphRepository(database).getGraphById(1L)
        assertNotNull(info)
        assertEquals("Template", info.name)
        assertEquals("User", info.author)
        assertEquals(1L, info.isDirected)
        assertEquals(0L, info.isWeighted)
        val vertices = VertexRepository(database).getVerticesByGraph(1L)
        assertEquals(2, vertices.size)
        assertEquals(Vertices(1L, 1L, 1L, "1"), vertices[0])
        assertEquals(Vertices(2L, 1L, 2L, "2"), vertices[1])
        val edges = EdgeRepository(database).getEdgesByGraph(1L)
        assertEquals(1, edges.size)
        assertEquals(Edges(1L, 1L, 1L, 2L, null), edges[0])
    }

    @Test
    fun `convert and save IR to SQL correct`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            isWeighted=true
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2 52
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.SQL, ConvertModes.SAVE, null)
        assertTrue(result is Result.Success)
        val dbFile = result.data
        assertEquals("test.db", dbFile.name)
        val database: GraphDatabase = createDatabase(dbFile.absolutePath)
        val info = GraphRepository(database).getGraphById(1L)
        assertNotNull(info)
        assertEquals("Template", info.name)
        assertEquals("User", info.author)
        assertEquals(1L, info.isDirected)
        assertEquals(1L, info.isWeighted)
        val vertices = VertexRepository(database).getVerticesByGraph(1L)
        assertEquals(2, vertices.size)
        assertEquals(Vertices(1L, 1L, 1L, "1"), vertices[0])
        assertEquals(Vertices(2L, 1L, 2L, "2"), vertices[1])
        val edges = EdgeRepository(database).getEdgesByGraph(1L)
        assertEquals(1, edges.size)
        assertEquals(Edges(1L, 1L, 1L, 2L, 52), edges[0])
    }

    @Test
    fun `convert IR with empty graph name to SQL error`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            author=User
            isDirected=true
            isWeighted=true
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2 52
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.SQL, ConvertModes.LOAD, null)
        assertTrue(result is Result.Error)
        assertEquals("ConverterError", result.error.type)
    }

    @Test
    fun `convert IR with empty graph author to SQL error`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=Template
            isDirected=true
            isWeighted=true
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2 52
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.SQL, ConvertModes.LOAD, null)
        assertTrue(result is Result.Error)
        assertEquals("ConverterError", result.error.type)
    }

    @Test
    fun `convert IR with empty isDirected to SQL error`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=Template
            author=User
            isWeighted=true
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2 52
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.SQL, ConvertModes.LOAD, null)
        assertTrue(result is Result.Error)
        assertEquals("ConverterError", result.error.type)
    }

    @Test
    fun `convert IR with empty isWeighted to SQL error`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2 52
            """.trimIndent(),
        )
        val result = Converter.convert(file, FileExtensions.SQL, ConvertModes.LOAD, null)
        assertTrue(result is Result.Error)
        assertEquals("ConverterError", result.error.type)
    }

    @Test
    fun `convert SQL to IR correct`() {
        val dbFile = File(tempDir, "test.db")
        val database = createDatabase(dbFile.absolutePath)
        val graphRepository = GraphRepository(database)
        val vertexRepository = VertexRepository(database)
        val edgeRepository = EdgeRepository(database)
        graphRepository.insertGraph(
            name = "Template",
            author = "User",
            isDirected = true,
            isWeighted = true,
        )
        vertexRepository.insertVertex(
            graphId = 1L,
            vertexId = 1L,
            label = "1",
        )
        vertexRepository.insertVertex(
            graphId = 1L,
            vertexId = 2L,
            label = "2",
        )
        edgeRepository.insertEdge(
            graphId = 1L,
            fromVertex = 1L,
            toVertex = 2L,
            weight = 52,
        )
        val result = Converter.convert(dbFile, FileExtensions.GRAPH, ConvertModes.LOAD, 1L)
        assertTrue(result is Result.Success)
        val expectedFile = File(tempDir, "test.graph")
        expectedFile.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            isWeighted=true
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2 52
            """.trimIndent(),
        )
        assertEquals(expectedFile.name, result.data.name)
        assertEquals(expectedFile.readLines(), result.data.readLines())
    }

    @Test
    fun `convert and save SQL to IR unweighted correct`() {
        val dbFile = File(tempDir, "test.db")
        val database = createDatabase(dbFile.absolutePath)
        val graphRepository = GraphRepository(database)
        val vertexRepository = VertexRepository(database)
        val edgeRepository = EdgeRepository(database)
        graphRepository.insertGraph(
            name = "Template",
            author = "User",
            isDirected = true,
            isWeighted = false,
        )
        vertexRepository.insertVertex(
            graphId = 1L,
            vertexId = 1L,
            label = "1",
        )
        vertexRepository.insertVertex(
            graphId = 1L,
            vertexId = 2L,
            label = "2",
        )
        edgeRepository.insertEdge(
            graphId = 1L,
            fromVertex = 1L,
            toVertex = 2L,
            weight = null,
        )
        val result = Converter.convert(dbFile, FileExtensions.GRAPH, ConvertModes.SAVE, 1L)
        assertTrue(result is Result.Success)
        val expectedFile = File(tempDir, "test.graph")
        expectedFile.writeText(
            """
            Info:
            name=Template
            author=User
            isDirected=true
            isWeighted=false
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2
            """.trimIndent(),
        )
        assertEquals(expectedFile.name, result.data.name)
        assertEquals(expectedFile.readLines(), result.data.readLines())
    }

    @Test
    fun `convert SQL with null graphId to IR error`() {
        val dbFile = File(tempDir, "test.db")
        createDatabase(dbFile.absolutePath)
        val result = Converter.convert(dbFile, FileExtensions.GRAPH, ConvertModes.LOAD, null)
        assertTrue(result is Result.Error)
        assertEquals("ConverterError", result.error.type)
    }

    @Test
    fun `convert SQL with unknown graphId to IR error`() {
        val dbFile = File(tempDir, "test.db")
        createDatabase(dbFile.absolutePath)
        val result = Converter.convert(dbFile, FileExtensions.GRAPH, ConvertModes.LOAD, 52L)
        assertTrue(result is Result.Error)
        assertEquals("ConverterError", result.error.type)
    }
}
