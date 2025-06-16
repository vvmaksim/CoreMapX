import model.commands.`class`.Command
import model.fileHandler.Parser
import model.result.Result
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ParseTests {
    @TempDir
    lateinit var tempDir: File

    @Test
    fun `parse with error not found info marker`() {
        val file = File(tempDir, "test.graph")
        file.writeText("")
        val result = Parser.parse(file, null)
        assertTrue(result is Result.Error)
        assertEquals("NotFoundInfoMarker", result.error.type)
    }

    @Test
    fun `parse correct`() {
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
            """.trimIndent(),
        )
        val result = Parser.parse(file, null)
        assertTrue(result is Result.Success)
        assertEquals("Template", result.data.name)
        assertEquals("User", result.data.author)
        assertTrue(result.data.isDirected)
        assertTrue(result.data.isWeighted)
        assertEquals(emptyList(), result.data.warnings)
        val expectedCommand = (Command.create("add vertex 1 1") as Result.Success).data
        val resultCommand = (result.data.commands[0] as Result.Success).data
        assertEquals(expectedCommand.type, resultCommand.type)
        assertEquals(expectedCommand.entity, resultCommand.entity)
        assertEquals(expectedCommand.parameters, resultCommand.parameters)
    }

    @Test
    fun `parse without optional parameters`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            isDirected=true
            isWeighted=true
            Graph:
            add vertex 1 1
            """.trimIndent(),
        )
        val result = Parser.parse(file, null)
        assertTrue(result is Result.Success)
        assertTrue(result.data.isDirected)
        assertTrue(result.data.isWeighted)
        assertEquals(listOf("Optional field `name` was missed", "Optional field `author` was missed"), result.data.warnings)
        val expectedCommand = (Command.create("add vertex 1 1") as Result.Success).data
        val resultCommand = (result.data.commands[0] as Result.Success).data
        assertEquals(expectedCommand.type, resultCommand.type)
        assertEquals(expectedCommand.entity, resultCommand.entity)
        assertEquals(expectedCommand.parameters, resultCommand.parameters)
    }
}
