import model.fileHandler.ConvertModes
import model.fileHandler.FileExtensions
import model.fileHandler.converter.Converter
import model.result.Result
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ConverterTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            UserDirectory.init()
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
}
