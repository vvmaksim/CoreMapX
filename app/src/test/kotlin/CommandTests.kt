import model.command.concrete.Command
import model.command.enums.CommandEntities
import model.command.enums.CommandTypes
import model.result.Result
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CommandTests {
    // Correct commands

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

    @Test
    fun `remove edge by implicit id`() {
        val result = Command.create("rm edge 0")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
    }

    @Test
    fun `remove edge by explicit id`() {
        val result = Command.create("rm edge id:0")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
    }

    @Test
    fun `set strategy on random with explicit method`() {
        val result = Command.create("set strategy strategy:Random")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.SET, command.type)
        assertEquals(CommandEntities.LAYOUT_STRATEGY, command.entity)
    }

    @Test
    fun `set strategy on circular with implicit method`() {
        val result = Command.create("set strategy Circular")
        assertIs<Result.Success<Command>>(result)
        val command = result.data
        assertEquals(CommandTypes.SET, command.type)
        assertEquals(CommandEntities.LAYOUT_STRATEGY, command.entity)
    }

    // Incorrect commands

    @Test
    fun `set strategy with bad strategy name in explicit method`() {
        val result = Command.create("set strategy strategy:Ran:dom")
        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Set layout strategy command must specify 'strategy'", result.error.description)
    }

    @Test
    fun `set strategy with unknown key name in explicit method`() {
        val result = Command.create("set strategy unknown_key:Random")
        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Set layout strategy command must specify 'strategy'", result.error.description)
    }

    @Test
    fun `set unknown entity in explicit method`() {
        val result = Command.create("set unknown_entity some_key:Random")
        assertIs<Result.Error>(result)
        assertEquals("UnknownEntity", result.error.type)
        assertEquals("Unknown entity for command: unknown_entity", result.error.description)
    }

    @Test
    fun `empty command`() {
        val result = Command.create("")
        assertIs<Result.Error>(result)
        assertEquals("EmptyCommand", result.error.type)
        assertEquals("Command cannot be empty", result.error.description)
    }

    @Test
    fun `unknown command type`() {
        val result = Command.create("example vertex 1 Bob")
        assertIs<Result.Error>(result)
        assertEquals("UnknownType", result.error.type)
        assertEquals("Unknown type for command: example", result.error.description)
    }

    @Test
    fun `add vertex with missing id`() {
        val result = Command.create("add vertex label:Bob")
        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Add vertex command must specify 'id' and 'label'", result.error.description)
    }

    @Test
    fun `add vertex with invalid id`() {
        val result = Command.create("add vertex id:invalid label:Bob")
        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter id must be Long", result.error.description)
    }

    @Test
    fun `add edge with missing from`() {
        val result = Command.create("add edge to:2")
        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Add edge command must specify 'from' and 'to'", result.error.description)
    }

    @Test
    fun `add edge with missing to`() {
        val result = Command.create("add edge from:2")
        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Add edge command must specify 'from' and 'to'", result.error.description)
    }

    @Test
    fun `add edge with invalid from`() {
        val result = Command.create("add edge from:invalid to:2")
        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter from must be Long", result.error.description)
    }

    @Test
    fun `add edge with invalid to`() {
        val result = Command.create("add edge from:2 to:invalid")
        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter to must be Long", result.error.description)
    }

    @Test
    fun `add weighted edge with invalid weight`() {
        val result = Command.create("add edge from:1 to:2 weight:invalid")
        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter weight must be Long", result.error.description)
    }

    @Test
    fun `remove vertex with missing id`() {
        val result = Command.create("rm vertex")
        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Remove vertex command must specify 'id'", result.error.description)
    }

    @Test
    fun `remove edge with missing parameters`() {
        val result = Command.create("rm edge")
        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Remove edge command must specify 'from' and 'to' or 'id'", result.error.description)
    }

    @Test
    fun `invalid parameter format for add vertex version 1`() {
        val result = Command.create("add vertex id:1:label:Bob")
        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Add vertex command must specify 'id' and 'label'", result.error.description)
    }

    @Test
    fun `invalid parameter format for add vertex version 2`() {
        val result = Command.create("add vertex id:1 label:Bob:)")
        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Add vertex command must specify 'id' and 'label'", result.error.description)
    }

    @Test
    fun `invalid parameter format for rm vertex`() {
        val result = Command.create("rm vertex id::1")
        assertIs<Result.Error>(result)
        assertEquals("MissingParameters", result.error.type)
        assertEquals("Missing required parameters: Remove vertex command must specify 'id'", result.error.description)
    }

    @Test
    fun `unknown entity`() {
        val result = Command.create("add unknown_entity")
        assertIs<Result.Error>(result)
        assertEquals("UnknownEntity", result.error.type)
        assertEquals("Unknown entity for command: unknown_entity", result.error.description)
    }

    @Test
    fun `invalid id type for add vertex`() {
        val result = Command.create("add vertex id:invalid label:correct")
        assertIs<Result.Error>(result)
        assertEquals("InvalidParameterType", result.error.type)
        assertEquals("Parameter id must be Long", result.error.description)
    }
}
