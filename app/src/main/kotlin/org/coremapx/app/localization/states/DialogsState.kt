package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.DialogsKeys

class DialogsState : LocalizationState {
    // New Graph
    val newGraphTitle = mutableStateOf("")
    val newGraphTextFieldPlaceholder = mutableStateOf("")
    val newGraphTextFieldError = mutableStateOf("")
    val newGraphIsDirected = mutableStateOf("")
    val newGraphIsWeighted = mutableStateOf("")
    val newGraphCreateButton = mutableStateOf("")

    // Save Graph
    val saveGraphAsTitle = mutableStateOf("")
    val saveGraphAsSubtitle = mutableStateOf("")
    val saveGraphAsFileNameHint = mutableStateOf("")
    val saveGraphAsFileNamePlaceholder = mutableStateOf("")
    val saveGraphAsSaveDirectoryHint = mutableStateOf("")
    val saveGraphAsFileFormatHint = mutableStateOf("")
    val saveGraphAsFileExistsWarning = mutableStateOf("")
    val saveGraphAsSaveButton = mutableStateOf("")

    // User Notification
    val userNotificationSaveError = mutableStateOf("")
    val userNotificationSaveSuccess = mutableStateOf("")
    val userNotificationSaveSuccessMessage = mutableStateOf("")
    val userNotificationOkButton = mutableStateOf("")

    // Settings
    val settingsTitle = mutableStateOf("")
    val settingsSubTitle = mutableStateOf("")
    val settingsGeneralBlockName = mutableStateOf("")
    val settingsColorsBlockName = mutableStateOf("")
    val settingsMainScreenBlockName = mutableStateOf("")
    val settingsTitleBarBlockName = mutableStateOf("")
    val settingsCommandFieldBlockName = mutableStateOf("")
    val settingsWorkAreaBlockName = mutableStateOf("")
    val settingsPerformanceBlockName = mutableStateOf("")

    // // General Block
    val generalLanguage = mutableStateOf("")
    val generalTheme = mutableStateOf("")
    val generalSystemDialogTheme = mutableStateOf("")
    val generalExpanded = mutableStateOf("")

    // // Colors Block
    val colorsPrimary = mutableStateOf("")
    val colorsPrimaryVariant = mutableStateOf("")
    val colorsSecondary = mutableStateOf("")
    val colorsSecondaryVariant = mutableStateOf("")
    val colorsBackground = mutableStateOf("")
    val colorsSurface = mutableStateOf("")
    val colorsError = mutableStateOf("")
    val colorsOnPrimary = mutableStateOf("")
    val colorsOnSecondary = mutableStateOf("")
    val colorsOnBackground = mutableStateOf("")
    val colorsOnSurface = mutableStateOf("")
    val colorsOnError = mutableStateOf("")
    val colorsBorderColor = mutableStateOf("")
    val colorsSuccessColor = mutableStateOf("")
    val colorsWarningColor = mutableStateOf("")
    val colorsVertexMainColor = mutableStateOf("")
    val colorsHoveredBorderColor = mutableStateOf("")
    val colorsEdgeMainColor = mutableStateOf("")
    val colorsShortestPathColor = mutableStateOf("")
    val colorsOtherPathsColor = mutableStateOf("")
    val colorsCanvasBackgroundColor = mutableStateOf("")
    val colorsCommandLineBlockBackgroundColor = mutableStateOf("")

    // // Main Screen Block
    val mainScreenStartHeight = mutableStateOf("")
    val mainScreenStartWidth = mutableStateOf("")
    val mainScreenPlacement = mutableStateOf("")

    // // Title Bar Block
    val titleBarHeight = mutableStateOf("")
    val titleBarIconSize = mutableStateOf("")

    // // Command Field Block
    val commandFieldMessageOutputHeight = mutableStateOf("")
    val commandFieldMaxCountMessages = mutableStateOf("")
    val commandFieldMaxCountUserCommands = mutableStateOf("")
    val commandFieldWidth = mutableStateOf("")
    val commandFieldIsTransparent = mutableStateOf("")

    // // Work Area Block
    val workAreaGraphLayoutHeight = mutableStateOf("")
    val workAreaGraphLayoutWidth = mutableStateOf("")
    val workAreaVertexRadius = mutableStateOf("")
    val workAreaVertexLabelSize = mutableStateOf("")
    val workAreaEdgeLabelSize = mutableStateOf("")
    val workAreaEdgeArrowSize = mutableStateOf("")
    val workAreaEdgeWidth = mutableStateOf("")
    val workAreaCanvasDragRatio = mutableStateOf("")
    val workAreaCanvasLimit = mutableStateOf("")

    // // Performance Block
    val performanceAnimationDuration = mutableStateOf("")
    val performanceCommandFieldScrollDelay = mutableStateOf("")

    // Analytics
    val analyticsTitle = mutableStateOf("")
    val analyticsLayoutStrategyHint = mutableStateOf("")

    // Generate Random Graph
    val generateRandomGraphTitle = mutableStateOf("")
    val generateRandomGraphVertexCountHint = mutableStateOf("")
    val generateRandomGraphVertexCountIconDescription = mutableStateOf("")
    val generateRandomGraphVertexCountErrorMessage = mutableStateOf("")
    val generateRandomGraphEdgeCountHint = mutableStateOf("")
    val generateRandomGraphEdgeCountIconDescription = mutableStateOf("")
    val generateRandomGraphEdgeCountErrorMessage = mutableStateOf("")
    val generateRandomGraphIsDirectedGraph = mutableStateOf("")
    val generateRandomGraphIsWeightedGraph = mutableStateOf("")
    val generateRandomGraphGeneratingProgress = mutableStateOf("")
    val generateRandomGraphVisualizingIconDescription = mutableStateOf("")
    val generateRandomGraphVisualizingMessage = mutableStateOf("")
    val generateRandomGraphNotification = mutableStateOf("")
    val generateRandomGraphGenerateButton = mutableStateOf("")

    // Dialog Header
    val dialogHeaderCloseIconDescription = mutableStateOf("")

    // OpenGraphErrors
    val openGraphErrorsWarningIconDescription = mutableStateOf("")
    val openGraphErrorsNotification = mutableStateOf("")
    val openGraphErrorsErrorIconDescription = mutableStateOf("")
    val openGraphErrorsOkButton = mutableStateOf("")

    // Open Repository
    val openRepositoryTitle = mutableStateOf("")
    val openRepositoryErrorMessage = mutableStateOf("")
    val openRepositoryIsDirected = mutableStateOf("")
    val openRepositoryIsUndirected = mutableStateOf("")
    val openRepositoryIsWeighted = mutableStateOf("")
    val openRepositoryIsUnweighted = mutableStateOf("")
    val openRepositoryVerticesCount = mutableStateOf("")
    val openRepositoryEdgesCount = mutableStateOf("")
    val openRepositoryOpenButton = mutableStateOf("")

    // Find Path
    val findPathTitle = mutableStateOf("")
    val findPathSubtitle = mutableStateOf("")
    val findPathStartVertexId = mutableStateOf("")
    val findPathEndVertexId = mutableStateOf("")
    val findPathVertexIdPlaceholder = mutableStateOf("")
    val findPathMaxPathsCount = mutableStateOf("")
    val findPathMaxPathsPlaceholder = mutableStateOf("")
    val findPathFindStrategy = mutableStateOf("")
    val findPathFindButton = mutableStateOf("")

    // Add Vertex
    val addVertexTitle = mutableStateOf("")
    val addVertexVertexIdFieldLabel = mutableStateOf("")
    val addVertexVertexLabelFieldLabel = mutableStateOf("")
    val addVertexVertexIdAndVertexLabelCannotBeEmpty = mutableStateOf("")
    val addVertexVertexIdCannotBeEmpty = mutableStateOf("")
    val addVertexVertexLabelCannotBeEmpty = mutableStateOf("")
    val addVertexVertexIdMustBeLong = mutableStateOf("")
    val addVertexButton = mutableStateOf("")

    // Help
    val helpTitle = mutableStateOf("")
    val helpSubTitle = mutableStateOf("")
    val helpCommandBlockName = mutableStateOf("")
    val helpGraphFormatsBlockName = mutableStateOf("")
    val helpUserDirectoryBlockName = mutableStateOf("")

    // // Command Block
    val commandGeneralInformationTitle = mutableStateOf("")
    val commandGeneralInformationText1 = mutableStateOf("")
    val commandAttention1Point1 = mutableStateOf("")
    val commandExample1Point1 = mutableStateOf("")
    val commandAttention1Point2 = mutableStateOf("")
    val commandAddTitle = mutableStateOf("")
    val commandAddText1 = mutableStateOf("")
    val commandRmTitle = mutableStateOf("")
    val commandAttention1Point3 = mutableStateOf("")
    val commandRmText1 = mutableStateOf("")
    val commandAttention1Point4 = mutableStateOf("")
    val commandSetTitle = mutableStateOf("")
    val commandSetText1 = mutableStateOf("")
    val commandSetText2 = mutableStateOf("")
    val commandSetText3 = mutableStateOf("")
    val commandSetText4 = mutableStateOf("")
    val commandClearTitle = mutableStateOf("")
    val commandClearText1 = mutableStateOf("")
    val commandGraphClearTitle = mutableStateOf("")
    val commandGraphClearText1 = mutableStateOf("")
    val commandHelpTitle = mutableStateOf("")
    val commandHelpText1 = mutableStateOf("")

    // // Graph Formats Block
    val graphFormatsGeneralInformationTitle = mutableStateOf("")
    val graphFormatsGeneralInformationText1 = mutableStateOf("")
    val graphFormatsGraphFormatTitle = mutableStateOf("")
    val graphFormatsGraphFormatText1 = mutableStateOf("")
    val graphFormatsExample2Point1 = mutableStateOf("")
    val graphFormatsJsonFormatTitle = mutableStateOf("")
    val graphFormatsJsonFormatText1 = mutableStateOf("")
    val graphFormatsExample2Point2 = mutableStateOf("")
    val graphFormatsSqliteDbFormatTitle = mutableStateOf("")
    val graphFormatsSqliteDbFormatText1 = mutableStateOf("")

    // // User Directory Block
    val userDirectoryGeneralInformationTitle = mutableStateOf("")
    val userDirectoryGeneralInformationText1 = mutableStateOf("")
    val userDirectoryAttention3Point1 = mutableStateOf("")
    val userDirectoryAttention3Point2 = mutableStateOf("")
    val userDirectoryAttention3Point3 = mutableStateOf("")
    val userDirectoryInterfaceLanguageConfigurationTitle = mutableStateOf("")
    val userDirectoryInterfaceLanguageConfigurationText1 = mutableStateOf("")

    override fun updateValue(
        key: String,
        value: String,
    ) {
        when (key) {
            // New Graph
            DialogsKeys.NEW_GRAPH_TITLE -> newGraphTitle.value = value
            DialogsKeys.NEW_GRAPH_TEXT_FIELD_PLACEHOLDER -> newGraphTextFieldPlaceholder.value = value
            DialogsKeys.NEW_GRAPH_TEXT_FIELD_ERROR -> newGraphTextFieldError.value = value
            DialogsKeys.NEW_GRAPH_IS_DIRECTED -> newGraphIsDirected.value = value
            DialogsKeys.NEW_GRAPH_IS_WEIGHTED -> newGraphIsWeighted.value = value
            DialogsKeys.NEW_GRAPH_CREATE_BUTTON -> newGraphCreateButton.value = value

            // Save Graph
            DialogsKeys.SAVE_GRAPH_AS_TITLE -> saveGraphAsTitle.value = value
            DialogsKeys.SAVE_GRAPH_AS_SUBTITLE -> saveGraphAsSubtitle.value = value
            DialogsKeys.SAVE_GRAPH_AS_FILE_NAME_HINT -> saveGraphAsFileNameHint.value = value
            DialogsKeys.SAVE_GRAPH_AS_FILE_NAME_PLACEHOLDER -> saveGraphAsFileNamePlaceholder.value = value
            DialogsKeys.SAVE_GRAPH_AS_SAVE_DIRECTORY_HINT -> saveGraphAsSaveDirectoryHint.value = value
            DialogsKeys.SAVE_GRAPH_AS_FILE_FORMAT_HINT -> saveGraphAsFileFormatHint.value = value
            DialogsKeys.SAVE_GRAPH_AS_FILE_EXISTS_WARNING -> saveGraphAsFileExistsWarning.value = value
            DialogsKeys.SAVE_GRAPH_AS_SAVE_BUTTON -> saveGraphAsSaveButton.value = value

            // User Notification
            DialogsKeys.USER_NOTIFICATION_SAVE_ERROR -> userNotificationSaveError.value = value
            DialogsKeys.USER_NOTIFICATION_SAVE_SUCCESS -> userNotificationSaveSuccess.value = value
            DialogsKeys.USER_NOTIFICATION_SAVE_SUCCESS_MESSAGE -> userNotificationSaveSuccessMessage.value = value
            DialogsKeys.USER_NOTIFICATION_OK_BUTTON -> userNotificationOkButton.value = value

            // Settings
            DialogsKeys.SETTINGS_TITLE -> settingsTitle.value = value
            DialogsKeys.SETTINGS_SUB_TITLE -> settingsSubTitle.value = value
            DialogsKeys.SETTINGS_GENERAL_BLOCK_NAME -> settingsGeneralBlockName.value = value
            DialogsKeys.SETTINGS_COLORS_BLOCK_NAME -> settingsColorsBlockName.value = value
            DialogsKeys.SETTINGS_MAIN_SCREEN_BLOCK_NAME -> settingsMainScreenBlockName.value = value
            DialogsKeys.SETTINGS_TITLE_BAR_BLOCK_NAME -> settingsTitleBarBlockName.value = value
            DialogsKeys.SETTINGS_COMMAND_FIELD_BLOCK_NAME -> settingsCommandFieldBlockName.value = value
            DialogsKeys.SETTINGS_WORK_AREA_BLOCK_NAME -> settingsWorkAreaBlockName.value = value
            DialogsKeys.SETTINGS_PERFORMANCE_BLOCK_NAME -> settingsPerformanceBlockName.value = value
            // // General Block
            DialogsKeys.GENERAL_LANGUAGE -> generalLanguage.value = value
            DialogsKeys.GENERAL_THEME -> generalTheme.value = value
            DialogsKeys.GENERAL_SYSTEM_DIALOG_THEME -> generalSystemDialogTheme.value = value
            DialogsKeys.GENERAL_EXPANDED -> generalExpanded.value = value
            // // Colors Block
            DialogsKeys.COLORS_PRIMARY -> colorsPrimary.value = value
            DialogsKeys.COLORS_PRIMARY_VARIANT -> colorsPrimaryVariant.value = value
            DialogsKeys.COLORS_SECONDARY -> colorsSecondary.value = value
            DialogsKeys.COLORS_SECONDARY_VARIANT -> colorsSecondaryVariant.value = value
            DialogsKeys.COLORS_BACKGROUND -> colorsBackground.value = value
            DialogsKeys.COLORS_SURFACE -> colorsSurface.value = value
            DialogsKeys.COLORS_ERROR -> colorsError.value = value
            DialogsKeys.COLORS_ON_PRIMARY -> colorsOnPrimary.value = value
            DialogsKeys.COLORS_ON_SECONDARY -> colorsOnSecondary.value = value
            DialogsKeys.COLORS_ON_BACKGROUND -> colorsOnBackground.value = value
            DialogsKeys.COLORS_ON_SURFACE -> colorsOnSurface.value = value
            DialogsKeys.COLORS_ON_ERROR -> colorsOnError.value = value
            DialogsKeys.COLORS_BORDER_COLOR -> colorsBorderColor.value = value
            DialogsKeys.COLORS_SUCCESS_COLOR -> colorsSuccessColor.value = value
            DialogsKeys.COLORS_WARNING_COLOR -> colorsWarningColor.value = value
            DialogsKeys.COLORS_VERTEX_MAIN_COLOR -> colorsVertexMainColor.value = value
            DialogsKeys.COLORS_HOVERED_BORDER_COLOR -> colorsHoveredBorderColor.value = value
            DialogsKeys.COLORS_EDGE_MAIN_COLOR -> colorsEdgeMainColor.value = value
            DialogsKeys.COLORS_SHORTEST_PATH_COLOR -> colorsShortestPathColor.value = value
            DialogsKeys.COLORS_OTHER_PATHS_COLOR -> colorsOtherPathsColor.value = value
            DialogsKeys.COLORS_CANVAS_BACKGROUND_COLOR -> colorsCanvasBackgroundColor.value = value
            DialogsKeys.COLORS_COMMAND_LINE_BLOCK_BACKGROUND_COLOR -> colorsCommandLineBlockBackgroundColor.value = value
            // // Main Screen Block
            DialogsKeys.MAIN_SCREEN_START_HEIGHT -> mainScreenStartHeight.value = value
            DialogsKeys.MAIN_SCREEN_START_WIDTH -> mainScreenStartWidth.value = value
            DialogsKeys.MAIN_SCREEN_PLACEMENT -> mainScreenPlacement.value = value
            // // Title Bar Block
            DialogsKeys.TITLE_BAR_HEIGHT -> titleBarHeight.value = value
            DialogsKeys.TITLE_BAR_ICON_SIZE -> titleBarIconSize.value = value
            // // Command Field Block
            DialogsKeys.COMMAND_FIELD_MESSAGE_OUTPUT_HEIGHT -> commandFieldMessageOutputHeight.value = value
            DialogsKeys.COMMAND_FIELD_MAX_COUNT_MESSAGES -> commandFieldMaxCountMessages.value = value
            DialogsKeys.COMMAND_FIELD_MAX_COUNT_USER_COMMANDS -> commandFieldMaxCountUserCommands.value = value
            DialogsKeys.COMMAND_FIELD_WIDTH -> commandFieldWidth.value = value
            DialogsKeys.COMMAND_FIELD_IS_TRANSPARENT -> commandFieldIsTransparent.value = value
            // // Command Field Block
            DialogsKeys.WORK_AREA_GRAPH_LAYOUT_HEIGHT -> workAreaGraphLayoutHeight.value = value
            DialogsKeys.WORK_AREA_GRAPH_LAYOUT_WIDTH -> workAreaGraphLayoutWidth.value = value
            DialogsKeys.WORK_AREA_VERTEX_RADIUS -> workAreaVertexRadius.value = value
            DialogsKeys.WORK_AREA_VERTEX_LABEL_SIZE -> workAreaVertexLabelSize.value = value
            DialogsKeys.WORK_AREA_EDGE_LABEL_SIZE -> workAreaEdgeLabelSize.value = value
            DialogsKeys.WORK_AREA_EDGE_ARROW_SIZE -> workAreaEdgeArrowSize.value = value
            DialogsKeys.WORK_AREA_EDGE_WIDTH -> workAreaEdgeWidth.value = value
            DialogsKeys.WORK_AREA_CANVAS_DRAG_RATIO -> workAreaCanvasDragRatio.value = value
            DialogsKeys.WORK_AREA_CANVAS_LIMIT -> workAreaCanvasLimit.value = value
            // // Title Bar Block
            DialogsKeys.PERFORMANCE_ANIMATION_DURATION -> performanceAnimationDuration.value = value
            DialogsKeys.PERFORMANCE_COMMAND_FIELD_SCROLL_DELAY -> performanceCommandFieldScrollDelay.value = value

            // Analytics
            DialogsKeys.ANALYTICS_TITLE -> analyticsTitle.value = value
            DialogsKeys.ANALYTICS_LAYOUT_STRATEGY_HINT -> analyticsLayoutStrategyHint.value = value

            // Generate Random Graph
            DialogsKeys.GENERATE_RANDOM_GRAPH_TITLE -> generateRandomGraphTitle.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_VERTEX_COUNT_HINT -> generateRandomGraphVertexCountHint.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_VERTEX_COUNT_ICON_DESCRIPTION -> generateRandomGraphVertexCountIconDescription.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_VERTEX_COUNT_ERROR_MESSAGE -> generateRandomGraphVertexCountErrorMessage.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_EDGE_COUNT_HINT -> generateRandomGraphEdgeCountHint.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_EDGE_COUNT_ICON_DESCRIPTION -> generateRandomGraphEdgeCountIconDescription.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_EDGE_COUNT_ERROR_MESSAGE -> generateRandomGraphEdgeCountErrorMessage.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_IS_DIRECTED_GRAPH -> generateRandomGraphIsDirectedGraph.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_IS_WEIGHTED_GRAPH -> generateRandomGraphIsWeightedGraph.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_GENERATING_PROGRESS -> generateRandomGraphGeneratingProgress.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_VISUALIZING_ICON_DESCRIPTION -> generateRandomGraphVisualizingIconDescription.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_VISUALIZING_MESSAGE -> generateRandomGraphVisualizingMessage.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_VISUALIZING_NOTIFICATION -> generateRandomGraphNotification.value = value
            DialogsKeys.GENERATE_RANDOM_GRAPH_GENERATE_BUTTON -> generateRandomGraphGenerateButton.value = value

            // Dialog Header
            DialogsKeys.DIALOG_HEADER_CLOSE_ICON_DESCRIPTION -> dialogHeaderCloseIconDescription.value = value

            // OpenGraphErrors
            DialogsKeys.OPEN_GRAPH_ERRORS_WARNING_ICON_DESCRIPTION -> openGraphErrorsWarningIconDescription.value = value
            DialogsKeys.OPEN_GRAPH_ERRORS_NOTIFICATION -> openGraphErrorsNotification.value = value
            DialogsKeys.OPEN_GRAPH_ERRORS_ERROR_ICON_DESCRIPTION -> openGraphErrorsErrorIconDescription.value = value
            DialogsKeys.OPEN_GRAPH_ERRORS_OK_BUTTON -> openGraphErrorsOkButton.value = value

            // Open Repository
            DialogsKeys.OPEN_REPOSITORY_TITLE -> openRepositoryTitle.value = value
            DialogsKeys.OPEN_REPOSITORY_ERROR_MESSAGE -> openRepositoryErrorMessage.value = value
            DialogsKeys.OPEN_REPOSITORY_IS_DIRECTED -> openRepositoryIsDirected.value = value
            DialogsKeys.OPEN_REPOSITORY_IS_UNDIRECTED -> openRepositoryIsUndirected.value = value
            DialogsKeys.OPEN_REPOSITORY_IS_WEIGHTED -> openRepositoryIsWeighted.value = value
            DialogsKeys.OPEN_REPOSITORY_IS_UNWEIGHTED -> openRepositoryIsUnweighted.value = value
            DialogsKeys.OPEN_REPOSITORY_VERTICES_COUNT -> openRepositoryVerticesCount.value = value
            DialogsKeys.OPEN_REPOSITORY_EDGES_COUNT -> openRepositoryEdgesCount.value = value
            DialogsKeys.OPEN_REPOSITORY_OPEN_BUTTON -> openRepositoryOpenButton.value = value

            // Find Path
            DialogsKeys.FIND_PATH_TITLE -> findPathTitle.value = value
            DialogsKeys.FIND_PATH_SUBTITLE -> findPathSubtitle.value = value
            DialogsKeys.FIND_PATH_START_VERTEX_ID -> findPathStartVertexId.value = value
            DialogsKeys.FIND_PATH_END_VERTEX_ID -> findPathEndVertexId.value = value
            DialogsKeys.FIND_PATH_VERTEX_ID_PLACEHOLDER -> findPathVertexIdPlaceholder.value = value
            DialogsKeys.FIND_PATH_MAX_PATHS_COUNT -> findPathMaxPathsCount.value = value
            DialogsKeys.FIND_PATH_MAX_PATHS_PLACEHOLDER -> findPathMaxPathsPlaceholder.value = value
            DialogsKeys.FIND_PATH_FIND_STRATEGY -> findPathFindStrategy.value = value
            DialogsKeys.FIND_PATH_FIND_BUTTON -> findPathFindButton.value = value

            // Add Vertex
            DialogsKeys.ADD_VERTEX_TITLE -> addVertexTitle.value = value
            DialogsKeys.ADD_VERTEX_VERTEX_ID_FIELD_LABEL -> addVertexVertexIdFieldLabel.value = value
            DialogsKeys.ADD_VERTEX_VERTEX_LABEL_FIELD_LABEL -> addVertexVertexLabelFieldLabel.value = value
            DialogsKeys.ADD_VERTEX_VERTEX_ID_AND_VERTEX_LABEL_CANNOT_BE_EMPTY -> addVertexVertexIdAndVertexLabelCannotBeEmpty.value = value
            DialogsKeys.ADD_VERTEX_VERTEX_ID_CANNOT_BE_EMPTY -> addVertexVertexIdCannotBeEmpty.value = value
            DialogsKeys.ADD_VERTEX_VERTEX_LABEL_CANNOT_BE_EMPTY -> addVertexVertexLabelCannotBeEmpty.value = value
            DialogsKeys.ADD_VERTEX_VERTEX_ID_MUST_BE_LONG -> addVertexVertexIdMustBeLong.value = value
            DialogsKeys.ADD_VERTEX_BUTTON -> addVertexButton.value = value

            // Help
            DialogsKeys.HELP_TITLE -> helpTitle.value = value
            DialogsKeys.HELP_SUB_TITLE -> helpSubTitle.value = value
            DialogsKeys.HELP_COMMAND_BLOCK_NAME -> helpCommandBlockName.value = value
            DialogsKeys.HELP_GRAPH_FORMATS_BLOCK_NAME -> helpGraphFormatsBlockName.value = value
            DialogsKeys.HELP_USER_DIRECTORY_BLOCK_NAME -> helpUserDirectoryBlockName.value = value
            // // Command Block
            DialogsKeys.COMMAND_GENERAL_INFORMATION_TITLE -> commandGeneralInformationTitle.value = value
            DialogsKeys.COMMAND_GENERAL_INFORMATION_TEXT1 -> commandGeneralInformationText1.value = value
            DialogsKeys.COMMAND_ATTENTION_1_POINT_1 -> commandAttention1Point1.value = value
            DialogsKeys.COMMAND_EXAMPLE_1_POINT_1 -> commandExample1Point1.value = value
            DialogsKeys.COMMAND_ATTENTION_1_POINT_2 -> commandAttention1Point2.value = value
            DialogsKeys.COMMAND_ADD_TITLE -> commandAddTitle.value = value
            DialogsKeys.COMMAND_ADD_TEXT1 -> commandAddText1.value = value
            DialogsKeys.COMMAND_RM_TITLE -> commandRmTitle.value = value
            DialogsKeys.COMMAND_ATTENTION_1_POINT_3 -> commandAttention1Point3.value = value
            DialogsKeys.COMMAND_RM_TEXT1 -> commandRmText1.value = value
            DialogsKeys.COMMAND_ATTENTION_1_POINT_4 -> commandAttention1Point4.value = value
            DialogsKeys.COMMAND_SET_TITLE -> commandSetTitle.value = value
            DialogsKeys.COMMAND_SET_TEXT1 -> commandSetText1.value = value
            DialogsKeys.COMMAND_SET_TEXT2 -> commandSetText2.value = value
            DialogsKeys.COMMAND_SET_TEXT3 -> commandSetText3.value = value
            DialogsKeys.COMMAND_SET_TEXT4 -> commandSetText4.value = value
            DialogsKeys.COMMAND_CLEAR_TITLE -> commandClearTitle.value = value
            DialogsKeys.COMMAND_CLEAR_TEXT1 -> commandClearText1.value = value
            DialogsKeys.COMMAND_GRAPH_CLEAR_TITLE -> commandGraphClearTitle.value = value
            DialogsKeys.COMMAND_GRAPH_CLEAR_TEXT1 -> commandGraphClearText1.value = value
            DialogsKeys.COMMAND_HELP_TITLE -> commandHelpTitle.value = value
            DialogsKeys.COMMAND_HELP_TEXT1 -> commandHelpText1.value = value
            // // Graph Formats Block
            DialogsKeys.GRAPH_FORMATS_GENERAL_INFORMATION_TITLE -> graphFormatsGeneralInformationTitle.value = value
            DialogsKeys.GRAPH_FORMATS_GENERAL_INFORMATION_TEXT1 -> graphFormatsGeneralInformationText1.value = value
            DialogsKeys.GRAPH_FORMATS_GRAPH_FORMAT_TITLE -> graphFormatsGraphFormatTitle.value = value
            DialogsKeys.GRAPH_FORMATS_GRAPH_FORMAT_TEXT1 -> graphFormatsGraphFormatText1.value = value
            DialogsKeys.GRAPH_FORMATS_EXAMPLE_2_POINT_1 -> graphFormatsExample2Point1.value = value
            DialogsKeys.GRAPH_FORMATS_JSON_FORMAT_TITLE -> graphFormatsJsonFormatTitle.value = value
            DialogsKeys.GRAPH_FORMATS_JSON_FORMAT_TEXT1 -> graphFormatsJsonFormatText1.value = value
            DialogsKeys.GRAPH_FORMATS_EXAMPLE_2_POINT_2 -> graphFormatsExample2Point2.value = value
            DialogsKeys.GRAPH_FORMATS_SQLITE_DB_FORMAT_TITLE -> graphFormatsSqliteDbFormatTitle.value = value
            DialogsKeys.GRAPH_FORMATS_SQLITE_DB_FORMAT_TEXT1 -> graphFormatsSqliteDbFormatText1.value = value
            // // User Directory Block
            DialogsKeys.USER_DIRECTORY_GENERAL_INFORMATION_TITLE -> userDirectoryGeneralInformationTitle.value = value
            DialogsKeys.USER_DIRECTORY_GENERAL_INFORMATION_TEXT1 -> userDirectoryGeneralInformationText1.value = value
            DialogsKeys.USER_DIRECTORY_ATTENTION_3_POINT_1 -> userDirectoryAttention3Point1.value = value
            DialogsKeys.USER_DIRECTORY_ATTENTION_3_POINT_2 -> userDirectoryAttention3Point2.value = value
            DialogsKeys.USER_DIRECTORY_ATTENTION_3_POINT_3 -> userDirectoryAttention3Point3.value = value
            DialogsKeys.USER_DIRECTORY_INTERFACE_LANGUAGE_CONFIGURATION_TITLE ->
                userDirectoryInterfaceLanguageConfigurationTitle.value =
                    value
            DialogsKeys.USER_DIRECTORY_INTERFACE_LANGUAGE_CONFIGURATION_TEXT1 ->
                userDirectoryInterfaceLanguageConfigurationText1.value =
                    value
        }
    }
}
