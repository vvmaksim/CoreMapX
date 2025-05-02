import model.commands.classes.Command
import model.commands.enums.CommandEntities
import model.commands.enums.CommandTypes
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CommandTests {
    @Test
    fun `add vertex with implicit params`() {
        val command = Command("add vertex 1 Bob")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1", "label" to "Bob"), command.parameters)
    }

    @Test
    fun `add vertex with explicit params version 1`() {
        val command = Command("add vertex id:1 label:Bob")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1", "label" to "Bob"), command.parameters)
    }

    @Test
    fun `add vertex with explicit params version 2`() {
        val command = Command("add vertex label:Bob id:1")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1", "label" to "Bob"), command.parameters)
    }

    @Test
    fun `lots of spaces and different spelling`() {
        val command = Command("aDd     vErtEX label:Bob    id:1")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1", "label" to "Bob"), command.parameters)
    }

    @Test
    fun `add edge with implicit params`() {
        val command = Command("add edge 1 2")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `add edge with explicit params version 1`() {
        val command = Command("add edge from:1 to:2")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `add edge with explicit params version 2`() {
        val command = Command("add edge to:2 from:1")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `add weighted edge with implicit params`() {
        val command = Command("add edge 1 2 10")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 1`() {
        val command = Command("add edge from:1 to:2 weight:10")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 2`() {
        val command = Command("add edge from:1 weight:10 to:2")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 3`() {
        val command = Command("add edge weight:10 to:2 from:1")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 4`() {
        val command = Command("add edge to:2 from:1 weight:10")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 5`() {
        val command = Command("add edge weight:10 from:1 to:2")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `add weighted edge with explicit params version 6`() {
        val command = Command("add edge weight:10 to:2 from:1")
        assertEquals(CommandTypes.ADD, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2", "weight" to "10"), command.parameters)
    }

    @Test
    fun `help is correctly`() {
        val command = Command("help")
        assertEquals(CommandTypes.HELP, command.type)
        assertEquals(CommandEntities.APP, command.entity)
        assertTrue(command.parameters.isEmpty())
    }

    @Test
    fun `remove vertex with implicit param version 1`() {
        val command = Command("rm vertex 1")
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1"), command.parameters)
    }

    @Test
    fun `remove vertex with implicit param version 2`() {
        val command = Command("remove vertex 1")
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1"), command.parameters)
    }

    @Test
    fun `remove vertex with explicit param version 1`() {
        val command = Command("rm vertex id:1")
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1"), command.parameters)
    }

    @Test
    fun `remove vertex explicit param version 2`() {
        val command = Command("remove vertex id:1")
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.VERTEX, command.entity)
        assertEquals(mapOf("id" to "1"), command.parameters)
    }

    @Test
    fun `remove edge with implicit params version 1`() {
        val command = Command("rm edge 1 2")
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `remove edge with implicit param version 2`() {
        val command = Command("remove edge 1 2")
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `remove edge with explicit params version 1 and 1`() {
        val command = Command("rm edge from:1 to:2")
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `remove edge with explicit params version 1 and 2`() {
        val command = Command("rm edge to:2 from:1")
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `remove edge with explicit param version 2 and 1`() {
        val command = Command("remove edge from:1 to:2")
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `remove edge with explicit param version 2 and 2`() {
        val command = Command("remove edge to:2 from:1")
        assertEquals(CommandTypes.RM, command.type)
        assertEquals(CommandEntities.EDGE, command.entity)
        assertEquals(mapOf("from" to "1", "to" to "2"), command.parameters)
    }

    @Test
    fun `clear is correctly`() {
        val command = Command("clear")
        assertEquals(CommandTypes.CLEAR, command.type)
        assertEquals(CommandEntities.COMMAND_OUTPUT, command.entity)
        assertTrue(command.parameters.isEmpty())
    }

    @Test
    fun `graph_clear is correctly`() {
        val command = Command("graph_clear")
        assertEquals(CommandTypes.CLEAR, command.type)
        assertEquals(CommandEntities.GRAPH, command.entity)
        assertTrue(command.parameters.isEmpty())
    }
}
