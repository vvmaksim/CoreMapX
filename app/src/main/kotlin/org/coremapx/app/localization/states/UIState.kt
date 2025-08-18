package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.UIKeys

class UIState : LocalizationState {
    // Main Menu Buttons
    val mainMenuButtonNewGraph = mutableStateOf("")
    val mainMenuNewGraphIconDescription = mutableStateOf("")
    val mainMenuButtonSave = mutableStateOf("")
    val mainMenuSaveIconDescription = mutableStateOf("")
    val mainMenuButtonSaveAs = mutableStateOf("")
    val mainMenuSaveAsIconDescription = mutableStateOf("")
    val mainMenuButtonOpenGraph = mutableStateOf("")
    val mainMenuOpenGraphIconDescription = mutableStateOf("")
    val mainMenuButtonOpenRepository = mutableStateOf("")
    val mainMenuOpenRepositoryIconDescription = mutableStateOf("")
    val mainMenuButtonAnalytics = mutableStateOf("")
    val mainMenuAnalyticsIconDescription = mutableStateOf("")
    val mainMenuButtonSettings = mutableStateOf("")
    val mainMenuSettingsIconDescription = mutableStateOf("")
    val mainMenuButtonHelp = mutableStateOf("")
    val mainMenuHelpIconDescription = mutableStateOf("")

    // Title Bar
    var titleBarMenuIconDescription = mutableStateOf("")
    var titleBarFileButton = mutableStateOf("")
    var titleBarFileNewIconDescription = mutableStateOf("")
    var titleBarFileNewButton = mutableStateOf("")
    var titleBarFileOpenIconDescription = mutableStateOf("")
    var titleBarFileOpenButton = mutableStateOf("")
    var titleBarFileOpenFileIconDescription = mutableStateOf("")
    var titleBarFileOpenFileButton = mutableStateOf("")
    var titleBarFileOpenRepositoryIconDescription = mutableStateOf("")
    var titleBarFileOpenRepositoryButton = mutableStateOf("")
    var titleBarFileSaveIconDescription = mutableStateOf("")
    var titleBarFileSaveButton = mutableStateOf("")
    var titleBarFileSaveAsIconDescription = mutableStateOf("")
    var titleBarFileSaveAsButton = mutableStateOf("")
    var titleBarSettingsButton = mutableStateOf("")
    var titleBarHelpButton = mutableStateOf("")
    var titleBarMinimizeIconDescription = mutableStateOf("")
    var titleBarRecoverIconDescription = mutableStateOf("")
    var titleBarMaximizeIconDescription = mutableStateOf("")
    var titleBarCloseIconDescription = mutableStateOf("")

    // Top Menu
    var topMenuActions = mutableStateOf("")
    var topMenuDrawGraphAgain = mutableStateOf("")
    var topMenuResetDefaultCanvasState = mutableStateOf("")
    var topMenuGenerateRandomGraph = mutableStateOf("")
    var topMenuFindPath = mutableStateOf("")
    var topMenuVertices = mutableStateOf("")
    var topMenuEdges = mutableStateOf("")
    var topMenuHideVerticesLabels = mutableStateOf("")
    var topMenuShowVerticesLabels = mutableStateOf("")
    var topMenuHideVerticesIds = mutableStateOf("")
    var topMenuShowVerticesIds = mutableStateOf("")
    var topMenuHideEdgesWeights = mutableStateOf("")
    var topMenuShowEdgesWeights = mutableStateOf("")
    var topMenuHideEdgesIds = mutableStateOf("")
    var topMenuShowEdgesIds = mutableStateOf("")

    // Force-Directed Animation Menu
    var forceDirectedMenuTitle = mutableStateOf("")
    var forceDirectedMenuApply = mutableStateOf("")
    var forceDirectedMenuStart = mutableStateOf("")
    var forceDirectedMenuStop = mutableStateOf("")
    var forceDirectedMenuIterations = mutableStateOf("")
    var forceDirectedMenuArea = mutableStateOf("")
    var forceDirectedMenuGravity = mutableStateOf("")
    var forceDirectedMenuSpeed = mutableStateOf("")

    // Error Strings
    val errorBasicString = mutableStateOf("")
    val errorNoDescriptionMessage = mutableStateOf("")

    override fun updateValue(
        key: String,
        value: String,
    ) {
        when (key) {
            // Main Menu Buttons
            UIKeys.MAIN_MENU_BUTTON_NEW_GRAPH -> mainMenuButtonNewGraph.value = value
            UIKeys.MAIN_MENU_NEW_GRAPH_ICON_DESCRIPTION -> mainMenuNewGraphIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_SAVE -> mainMenuButtonSave.value = value
            UIKeys.MAIN_MENU_SAVE_ICON_DESCRIPTION -> mainMenuSaveIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_SAVE_AS -> mainMenuButtonSaveAs.value = value
            UIKeys.MAIN_MENU_SAVE_AS_ICON_DESCRIPTION -> mainMenuSaveAsIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_OPEN_GRAPH -> mainMenuButtonOpenGraph.value = value
            UIKeys.MAIN_MENU_OPEN_GRAPH_ICON_DESCRIPTION -> mainMenuOpenGraphIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_OPEN_REPOSITORY -> mainMenuButtonOpenRepository.value = value
            UIKeys.MAIN_MENU_OPEN_REPOSITORY_ICON_DESCRIPTION -> mainMenuOpenRepositoryIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_ANALYTICS -> mainMenuButtonAnalytics.value = value
            UIKeys.MAIN_MENU_ANALYTICS_ICON_DESCRIPTION -> mainMenuAnalyticsIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_SETTINGS -> mainMenuButtonSettings.value = value
            UIKeys.MAIN_MENU_SETTINGS_ICON_DESCRIPTION -> mainMenuSettingsIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_HELP -> mainMenuButtonHelp.value = value
            UIKeys.MAIN_MENU_HELP_ICON_DESCRIPTION -> mainMenuHelpIconDescription.value = value

            // Title Bar
            UIKeys.TITLE_BAR_MENU_ICON_DESCRIPTION -> titleBarMenuIconDescription.value = value
            UIKeys.TITLE_BAR_FILE_BUTTON -> titleBarFileButton.value = value
            UIKeys.TITLE_BAR_FILE_NEW_ICON_DESCRIPTION -> titleBarFileNewIconDescription.value = value
            UIKeys.TITLE_BAR_FILE_NEW_BUTTON -> titleBarFileNewButton.value = value
            UIKeys.TITLE_BAR_FILE_OPEN_ICON_DESCRIPTION -> titleBarFileOpenIconDescription.value = value
            UIKeys.TITLE_BAR_FILE_OPEN_BUTTON -> titleBarFileOpenButton.value = value
            UIKeys.TITLE_BAR_FILE_OPEN_FILE_ICON_DESCRIPTION -> titleBarFileOpenFileIconDescription.value = value
            UIKeys.TITLE_BAR_FILE_OPEN_FILE_BUTTON -> titleBarFileOpenFileButton.value = value
            UIKeys.TITLE_BAR_FILE_OPEN_REPOSITORY_ICON_DESCRIPTION -> titleBarFileOpenRepositoryIconDescription.value = value
            UIKeys.TITLE_BAR_FILE_OPEN_REPOSITORY_BUTTON -> titleBarFileOpenRepositoryButton.value = value
            UIKeys.TITLE_BAR_FILE_SAVE_ICON_DESCRIPTION -> titleBarFileSaveIconDescription.value = value
            UIKeys.TITLE_BAR_FILE_SAVE_BUTTON -> titleBarFileSaveButton.value = value
            UIKeys.TITLE_BAR_FILE_SAVE_AS_ICON_DESCRIPTION -> titleBarFileSaveAsIconDescription.value = value
            UIKeys.TITLE_BAR_FILE_SAVE_AS_BUTTON -> titleBarFileSaveAsButton.value = value
            UIKeys.TITLE_BAR_SETTINGS_BUTTON -> titleBarSettingsButton.value = value
            UIKeys.TITLE_BAR_HELP_BUTTON -> titleBarHelpButton.value = value
            UIKeys.TITLE_BAR_MINIMIZE_ICON_DESCRIPTION -> titleBarMinimizeIconDescription.value = value
            UIKeys.TITLE_BAR_RECOVER_ICON_DESCRIPTION -> titleBarRecoverIconDescription.value = value
            UIKeys.TITLE_BAR_MAXIMIZE_ICON_DESCRIPTION -> titleBarMaximizeIconDescription.value = value
            UIKeys.TITLE_BAR_CLOSE_ICON_DESCRIPTION -> titleBarCloseIconDescription.value = value

            // Top Menu
            UIKeys.TOP_MENU_ACTIONS -> topMenuActions.value = value
            UIKeys.TOP_MENU_DRAW_GRAPH_AGAIN -> topMenuDrawGraphAgain.value = value
            UIKeys.TOP_MENU_RESET_DEFAULT_CANVAS_STATE -> topMenuResetDefaultCanvasState.value = value
            UIKeys.TOP_MENU_GENERATE_RANDOM_GRAPH -> topMenuGenerateRandomGraph.value = value
            UIKeys.TOP_MENU_FIND_PATH -> topMenuFindPath.value = value
            UIKeys.TOP_MENU_VERTICES -> topMenuVertices.value = value
            UIKeys.TOP_MENU_EDGES -> topMenuEdges.value = value
            UIKeys.TOP_MENU_HIDE_VERTICES_LABELS -> topMenuHideVerticesLabels.value = value
            UIKeys.TOP_MENU_SHOW_VERTICES_LABELS -> topMenuShowVerticesLabels.value = value
            UIKeys.TOP_MENU_HIDE_VERTICES_IDS -> topMenuHideVerticesIds.value = value
            UIKeys.TOP_MENU_SHOW_VERTICES_IDS -> topMenuShowVerticesIds.value = value
            UIKeys.TOP_MENU_HIDE_EDGES_WEIGHTS -> topMenuHideEdgesWeights.value = value
            UIKeys.TOP_MENU_SHOW_EDGES_WEIGHTS -> topMenuShowEdgesWeights.value = value
            UIKeys.TOP_MENU_HIDE_EDGES_IDS -> topMenuHideEdgesIds.value = value
            UIKeys.TOP_MENU_SHOW_EDGES_IDS -> topMenuShowEdgesIds.value = value

            // Force-Directed Animation Menu
            UIKeys.FORCE_DIRECTED_MENU_TITLE -> forceDirectedMenuTitle.value = value
            UIKeys.FORCE_DIRECTED_MENU_APPLY -> forceDirectedMenuApply.value = value
            UIKeys.FORCE_DIRECTED_MENU_START -> forceDirectedMenuStart.value = value
            UIKeys.FORCE_DIRECTED_MENU_STOP -> forceDirectedMenuStop.value = value
            UIKeys.FORCE_DIRECTED_MENU_ITERATIONS -> forceDirectedMenuIterations.value = value
            UIKeys.FORCE_DIRECTED_MENU_AREA -> forceDirectedMenuArea.value = value
            UIKeys.FORCE_DIRECTED_MENU_GRAVITY -> forceDirectedMenuGravity.value = value
            UIKeys.FORCE_DIRECTED_MENU_SPEED -> forceDirectedMenuSpeed.value = value

            // Error Strings
            UIKeys.ERROR_BASIC_STRING -> errorBasicString.value = value
            UIKeys.ERROR_NO_DESCRIPTION_MESSAGE -> errorNoDescriptionMessage.value = value
        }
    }
}
