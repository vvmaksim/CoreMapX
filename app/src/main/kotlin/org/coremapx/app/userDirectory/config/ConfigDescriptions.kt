package org.coremapx.app.userDirectory.config

object ConfigDescriptions {
    // General
    val LANGUAGE =
        """
        You can choose one of the suggested languages for the interface or use a custom localization file.
        """.trimIndent()
    val THEME =
        """
        You can change the interface colors in the color settings. Changing any of the colors will switch the theme to the `Custom` status.
        """.trimIndent()
    val SYSTEM_DIALOG_THEME =
        """
        To select a graph file to open, a window with the appropriate interface appears.
        You can change the theme of this window to one that would better suit your theme.
        When switching between ready-made themes, this parameter switches automatically.
        """.trimIndent()
    val IS_EXPANDED_SETTINGS =
        """
        If this option is active, the next time you open the settings menu, all settings blocks will be expanded and all descriptions in the settings will be shown.
        
        If the parameter is disabled, then the next time you open the settings menu, all settings blocks and all descriptions are hidden.
        """.trimIndent()

    // Colors
    val PRIMARY =
        """
        The main color of the interface.
        It is used for key controls, buttons, highlighting active elements, etc.
        """.trimIndent()
    val PRIMARY_VARIANT =
        """
        A variant of the main color, usually slightly darker or lighter.
        It is used for shades, shadows, highlights, or minor accents related to the main color.
        """.trimIndent()
    val SECONDARY =
        """
        The secondary color of the interface.
        It is used to highlight less important elements, minor buttons, icons, links, etc.
        """.trimIndent()
    val SECONDARY_VARIANT =
        """
        A secondary color option, similar to primaryVariant, for shades, shadows, and additional accents associated with secondary.
        """.trimIndent()
    val BACKGROUND =
        """
        The background color of the entire application or large areas.
        """.trimIndent()
    val SURFACE =
        """
        Surface color: cards, panels, pop-ups, dialogs, and other elements that "lie" on the background.
        """.trimIndent()
    val ERROR =
        """
        The color for displaying errors: error text, frames, icons, indicators, etc.
        """.trimIndent()
    val ON_PRIMARY =
        """
        The color of the content (text, icons) that is placed on top of the primary. Usually contrasting.
        """.trimIndent()
    val ON_SECONDARY =
        """
        The color of the content that is placed on top of secondary.
        """.trimIndent()
    val ON_BACKGROUND =
        """
        The color of the content that is placed on top of background.
        """.trimIndent()
    val ON_SURFACE =
        """
        The color of the content that is placed on top of surface.
        """.trimIndent()
    val ON_ERROR =
        """
        The color of the content that is placed on top of error.
        """.trimIndent()

    val BORDER_COLOR =
        """
        The color of the frames for various interface elements: text fields, buttons, cards, etc.
        """.trimIndent()
    val SUCCESS_COLOR =
        """
        A color to indicate successful actions: confirmations, successful notifications, etc.
        """.trimIndent()
    val WARNING_COLOR =
        """
        Color for warnings: yellow icons, frames, notifications of potential problems.
        """.trimIndent()
    val VERTEX_MAIN_COLOR =
        """
        The primary color for displaying vertices.
        """.trimIndent()
    val HOVERED_BORDER_COLOR =
        """
        The color of the border when hovering over the element.
        """.trimIndent()
    val EDGE_MAIN_COLOR =
        """
        The primary color for displaying edges between vertices on the graph.
        """.trimIndent()
    val CANVAS_BACKGROUND_COLOR =
        """
        The background color for graph visualization.
        """.trimIndent()
    val COMMAND_LINE_BACKGROUND_COLOR =
        """
        The background color for the command line.

        This color is applied if the `isTransparentCommandLine` parameter is disabled.
        """.trimIndent()

    // Main Screen
    val MAIN_SCREEN_START_HEIGHT =
        """
        The starting height of the application window.
        
        It is used only when the `startWindowPlacement` is in the `Floating` state.
        
        Min=720
        """.trimIndent()
    val MAIN_SCREEN_START_WIDTH =
        """
        The starting width of the application window.
        
        It is used only when the `startWindowPlacement` is in the `Floating` state.
        
        Min=1280
        """.trimIndent()
    val START_WINDOW_PLACEMENT =
        """
        Maximized - The application is open to the full window, but not to the full screen. (Recommended)
        
        Floating - The application is open in the window. The minimum window size is 1280x720.
        You can set the initial dimensions with the `mainScreenStartWidth` and `mainScreenStartHeight` parameters, but their values cannot be less than the minimum.
        
        FullScreen - The application is open to full screen and occupies its entire area. (Not recommended)
        """.trimIndent()

    // Title Bar
    val TITLE_BAR_HEIGHT =
        """
        Height of the title bar.
        
        Min=35
        Recommended=40 
        """.trimIndent()
    val TITLE_BAR_ICON_SIZE =
        """
        Size of the title bar icons.
        
        Min=16
        Recommended=20
        """.trimIndent()

    // Command Field
    val MESSAGE_OUTPUT_HEIGHT =
        """
        Maximum lifting height for command line output messages.
        
        Min=150
        Recommended=200
        """.trimIndent()
    val MAX_COUNT_MESSAGES =
        """
        The maximum number of output messages that are remembered.
        
        Min=1
        Recommended=100
        Max=10000
        """.trimIndent()
    val MAX_COUNT_USER_COMMANDS =
        """
        The maximum number of history user commands that are remembered.
        
        Min=1
        Recommended=200
        Max=1000
        """.trimIndent()
    val COMMAND_FIELD_WIDTH =
        """
        The height of the command line.
        
        Min=400
        Recommended=700
        Max=900
        """.trimIndent()
    val IS_TRANSPARENT_COMMAND_LINE =
        """
        This parameter is responsible for the transparency of the command line background.

        If it is enabled, the background of the text field will be transparent.
        If it is turned off, the background of the text field will take on the color specified in the corresponding parameter in the color settings.
        """.trimIndent()

    // Work Area
    val GRAPH_LAYOUT_HEIGHT =
        """
        The graph is drawn on a certain plane, you can change the height of this plane.
        
        Min=2000
        Recommended=7000
        """.trimIndent()
    val GRAPH_LAYOUT_WIDTH =
        """
        The graph is drawn on a certain plane, you can change the width of this plane.
        
        Min=1000
        Recommended=7000
        """.trimIndent()
    val VERTEX_RADIUS =
        """
        The standard radius for a vertex.
        
        Min=1
        Recommended=15
        """.trimIndent()
    val VERTEX_LABEL_SIZE =
        """
        Vertex label size.
        
        Min=6
        Recommended=14
        """.trimIndent()
    val EDGE_LABEL_SIZE =
        """
        Edge label size.
        
        Min=6
        Recommended=14
        """.trimIndent()
    val EDGE_ARROW_SIZE =
        """
        Edge arrow size.
        
        Min=1
        Recommended=10
        Max=100
        """.trimIndent()
    val EDGE_WIDTH =
        """
        Width for an edge.
        
        Min=1
        Recommended=2
        Max=15
        """.trimIndent()
    val CANVAS_DRAG_RATIO =
        """
        The graph movement speed is calculated using a certain `canvasDragRatio` multiplier.
        
        Min=0.1
        Recommended=1
        Max=10
        """.trimIndent()
    val CANVAS_LIMIT =
        """
        The area where the graph is drawn is finite. To limit movement on this plane, you can change this parameter.
        
        Min=2000
        Recommended=8000
        """.trimIndent()

    // Performance
    val ANIMATION_DURATION =
        """
        This parameter is used to specify the speed of animations, for example, the speed of minimizing the side menu, etc.
        
        Min=100
        Recommended=300
        Max=1500
        """.trimIndent()
    val COMMAND_FIELD_SCROLL_DELAY =
        """
        A certain amount of time passes when sending a command on the command line.
        
        Min=10
        Recommended=50
        Max=300
        """.trimIndent()
}
