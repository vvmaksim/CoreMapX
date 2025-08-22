package extensions

import androidx.compose.material.Shapes
import androidx.compose.ui.graphics.Color
import org.coremapx.app.theme.AppShapes
import org.coremapx.app.theme.Theme
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ExtensionsTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_extensions_test_" + System.currentTimeMillis(),
            )
        }
    }

    @Test
    fun `Boolean to Long`() {
        assertEquals(1L, true.toLong())
        assertEquals(0L, false.toLong())
    }

    @Test
    fun `Color to hex String`() {
        assertEquals("#525252", Color(0x00525252).toHexString())
        assertEquals("#525252", Color(0x52525252).toHexString())
        assertEquals("#013252", Color(0x01013252).toHexString())
    }

    @Test
    fun `Colors with extension properties`() {
        assertEquals(Theme.borderColor, Theme.colors.border)
        assertEquals(Theme.successColor, Theme.colors.success)
        assertEquals(Theme.warningColor, Theme.colors.warning)
        assertEquals(Theme.hoveredBorderColor, Theme.colors.hoveredBorder)
        assertEquals(Theme.canvasBackgroundColor, Theme.colors.canvasBackground)
        assertEquals(Theme.consoleBackgroundColor, Theme.colors.consoleBackground)
    }

    @Test
    fun `Long to BooleanOrNull`() {
        assertEquals(true, 1L.toBooleanOrNull())
        assertEquals(false, 0L.toBooleanOrNull())
        assertEquals(null, 52L.toBooleanOrNull())
    }

    @Test
    fun `AppShapes with huge extension`() {
        val shapes =
            Shapes(
                small = AppShapes.small,
                medium = AppShapes.medium,
                large = AppShapes.large,
            )
        assertEquals(AppShapes.huge, shapes.huge)
    }

    @Test
    fun `String to ColorOrNull`() {
        assertEquals(Color(0xFF525252), "#525252".toColorOrNull())
        assertEquals(null, "#GGG".toColorOrNull())
        assertEquals(null, "Some bad string".toColorOrNull())
    }
}
