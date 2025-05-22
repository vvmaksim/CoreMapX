import model.fileHandler.Validator
import model.result.Result
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals

class ValidatorTests {
    @TempDir
    lateinit var tempDir: File

    // Incorrect files

    @Test
    fun `validateIR unknown file extension`() {
        val file = File(tempDir, "test.unknown")
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("UnknownFileExtension", (result as Result.Error).error.type)
    }

    @Test
    fun `validateIR not found info marker`() {
        val file = File(tempDir, "test.graph")
        file.writeText("text")
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("NotFoundInfoMarker", (result as Result.Error).error.type)
    }

    @Test
    fun `validateIR not found info marker with spaces`() {
        val file = File(tempDir, "test.graph")
        file.writeText("text  \n\n")
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("NotFoundInfoMarker", (result as Result.Error).error.type)
    }

    @Test
    fun `validateIR not found graph marker`() {
        val file = File(tempDir, "test.graph")
        file.writeText("Info:")
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("NotFoundGraphMarker", (result as Result.Error).error.type)
    }

    @Test
    fun `validateIR broken order markers`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
                |Graph:
                |Info:
                |""".trimMargin())
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("BrokenOrderMarkers", (result as Result.Error).error.type)
    }

    @Test
    fun `validateIR incorrect line`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
                |Info:
                |incorrect string
                |Graph:
                |""".trimMargin())
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("IncorrectLine", (result as Result.Error).error.type)
    }

    @Test
    fun `validateIR unknown property`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
                |Info:
                |unknownProperty=
                |Graph:
                |""".trimMargin())
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("UnknownProperty", (result as Result.Error).error.type)
    }

    @Test
    fun `validateIR incorrect info property`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
                |Info:
                |name=nameWith`=`Symbol
                |Graph:
                |""".trimMargin())
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("IncorrectInfoProperty", (result as Result.Error).error.type)
    }

    @Test
    fun `validateIR missing value`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
                |Info:
                |name=
                |Graph:
                |""".trimMargin())
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("MissingValue", (result as Result.Error).error.type)
    }

    @Test
    fun `validateIR incorrect boolean value`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
                |Info:
                |isDirected=Incorrect
                |Graph:
                |""".trimMargin())
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("IncorrectBooleanValue", (result as Result.Error).error.type)
    }

    @Test
    fun `validateIR incorrect command`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
                |Info:
                |isDirected=false
                |isWeighted=false
                |Graph:
                |add vertex id:incorrect label:label
                |""".trimMargin())
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("IncorrectCommand", (result as Result.Error).error.type)
    }

    @Test
    fun `validateIR error reading file`() {
        val file = File(tempDir, "test.graph")
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("ErrorReadingFile", (result as Result.Error).error.type)
    }

    @Test
    fun `validateJSON converter error`() {
        val file = File(tempDir, "test.json")
        file.writeText("")
        val result = Validator.validate(file)
        assertTrue(result is Result.Error)
        assertEquals("ConverterError", (result as Result.Error).error.type)
    }

    // Correct files

    @Test
    fun `validateIR success`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
                |Info:
                |name=name
                |author=user
                |isDirected=false
                |isWeighted=false
                |Graph:
                |add vertex 1 1
                |""".trimMargin())
        val result = Validator.validate(file)
        assertTrue(result is Result.Success)
        assertEquals("IR is correct", (result as Result.Success).data)
    }

    @Test
    fun `validateJSON success`() {
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
                      {"id": 1, "label": "1"}
                    ],
                    "edges": [
                      {"from": 1, "to": 2, "weight": 52}
                    ]
                  }
                }
            """.trimIndent()
        )
        val result = Validator.validate(file)
        assertTrue(result is Result.Success)
        assertEquals("JSON is correct", (result as Result.Success).data)
    }
}
