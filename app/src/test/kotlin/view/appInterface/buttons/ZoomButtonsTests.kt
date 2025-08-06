package view.appInterface.buttons

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import io.mockk.mockk
import io.mockk.verify
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import view.appInterface.button.ZoomButtons

class ZoomButtonsTests {
    companion object {
        @JvmStatic
        @BeforeClass
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_ui_zoom_buttons_test_" + System.currentTimeMillis(),
            )
        }
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `simply ZoomButtons test`() {
        val zoomFactor = 0.5f
        val onZoom: (Float) -> Unit = mockk(relaxed = true)

        composeTestRule.setContent {
            AppTheme {
                ZoomButtons(
                    onZoom = onZoom,
                    zoomFactor = zoomFactor,
                )
            }
        }

        composeTestRule.onNodeWithTag("ZoomInButton").performClick()
        composeTestRule.onNodeWithTag("ZoomOutButton").performClick()
        verify { onZoom(zoomFactor) }
        verify { onZoom(-zoomFactor) }
        composeTestRule.onNodeWithTag("ZoomButtonsContainer").assertExists()
        composeTestRule.onNodeWithTag("ZoomInButton").assertExists()
        composeTestRule.onNodeWithTag("ZoomOutButton").assertExists()
    }
}
