import model.commands.classes.Command
import model.commands.classes.Result
import model.commands.enums.CommandEntities
import model.commands.enums.CommandTypes
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CommandTests {
    @Test
    fun `add vertex with implicit params`() {
        val result = Command.create("add vertex 1 Bob")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1", "label" to "Bob"), command.parameters)
    }

    @Test
    fun `add vertex with explicit params version 1`() {
        val result = Command.create("add vertex id:1 label:Bob")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1", "label" to "Bob"), command.parameters)
    }

    @Test
    fun `add vertex with explicit params version 2`() {
        val result = Command.create("add vertex label:Bob id:1")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1", "label" to "Bob"), command.parameters)
    }

    @Test
    fun `lots of spaces and different spelling`() {
        val result = Command.create("aDd     vErtEX label:Bob    id:1")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1", "label" to "Bob"), command.parameters)
    }

    @Test
    fun `add edge with implicit params`() {
        val result = Command.create("add edge 1 2")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `add edge with explicit params version 1`() {
        val result = Command.create("add edge from:1 to:2")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `add edge with explicit params version 2`() {
        val result = Command.create("add edge to:2 from:1")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `add weighted edge with implicit params`() {
        val result = Command.create("add edge 1 2 10")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 1`() {
        val result = Command.create("add edge from:1 to:2 weight:10")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 2`() {
        val result = Command.create("add edge from:1 weight:10 to:2")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 3`() {
        val result = Command.create("add edge weight:10 to:2 from:1")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 4`() {
        val result = Command.create("add edge to:2 from:1 weight:10")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 5`() {
        val result = Command.create("add edge weight:10 from:1 to:2")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 6`() {
        val result = Command.create("add edge weight:10 to:2 from:1")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `help is correctly`() {
        val result = Command.create("help")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.HELP, command.type)
        assertEquals(CommandEntities.APP, command.entity)
        assertTrue(command.parameters.isEmpty())
    }

    @Test
    fun `remove vertex with implicit param version 1`() {
        val result = Command.create("rm vertex 1")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1"), command.parameters)
    }

    @Test
    fun `remove vertex with implicit param version 2`() {
        val result = Command.create("remove vertex 1")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1"), command.parameters)
    }

    @Test
    fun `remove vertex with explicit param version 1`() {
        val result = Command.create("rm vertex id:1")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1"), command.parameters)
    }

    @Test
    fun `remove vertex explicit param version 2`() {
        val result = Command.create("remove vertex id:1")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1"), command.parameters)
    }

    @Test
    fun `remove edge with implicit params version 1`() {
        val result = Command.create("rm edge 1 2")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `remove edge with implicit param version 2`() {
        val result = Command.create("remove edge 1 2")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `remove edge with explicit params version 1 and 1`() {
        val result = Command.create("rm edge from:1 to:2")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `remove edge with explicit params version 1 and 2`() {
        val result = Command.create("rm edge to:2 from:1")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `remove edge with explicit param version 2 and 1`() {
        val result = Command.create("remove edge from:1 to:2")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `remove edge with explicit param version 2 and 2`() {
        val result = Command.create("remove edge to:2 from:1")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `clear is correctly`() {
        val result = Command.create("clear")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.CLEAR, command.type)
        assertEquals(CommandEntities.COMMAND_OUTPUT, command.entity)
        assertTrue(command.parameters.isEmpty())
    }

    @Test
    fun `graph_clear is correctly`() {
        val result = Command.create("graph_clear")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.CLEAR, command.type)
        assertEquals(CommandEntities.GRAPH, command.entity)
        assertTrue(command.parameters.isEmpty())
    }
}
