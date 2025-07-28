package org.coremapx.app.config

import org.coremapx.app.config.PrivateConfig.AppResources.CUSTOM_LANGUAGE_PATH
import org.coremapx.app.config.PrivateConfig.UserDirectory.DIR_PATH

@Suppress("ktlint:standard:property-naming")
object PrivateConfig {
    object MainMenu {
        const val LOGO_SIZE = 52
    }

    object UserDirectory {
        @Suppress("ktlint:standard:property-naming")
        var HOME_DIR_PATH: String = System.getProperty("user.home")
            set(value) {
                field = value

                DIR_PATH = "$HOME_DIR_PATH/.coremapx"
                LOGS_DIR_PATH = "$DIR_PATH/logs"
                CONFIG_DIR_PATH = "$DIR_PATH/config"
                FONTS_DIR_PATH = "$DIR_PATH/config/fonts"
                REGULAR_FONT_PATH = "$FONTS_DIR_PATH/Font-Regular.ttf"
                BOLD_FONT_PATH = "$FONTS_DIR_PATH/Font-Bold.ttf"
                MEDIUM_FONT_PATH = "$FONTS_DIR_PATH/Font-Medium.ttf"
                LIGHT_FONT_PATH = "$FONTS_DIR_PATH/Font-Light.ttf"
                DATA_DIR_PATH = "$DIR_PATH/data"
                TEMP_DIR_PATH = "$DIR_PATH/data/temp"
                GRAPHS_DIR_PATH = "$DIR_PATH/data/graphs"
                DIRECTORIES =
                    listOf(
                        LOGS_DIR_PATH,
                        CONFIG_DIR_PATH,
                        FONTS_DIR_PATH,
                        DATA_DIR_PATH,
                        TEMP_DIR_PATH,
                        GRAPHS_DIR_PATH,
                    )
                CONFIG_FILE_PATH = "$DIR_PATH/config/config.cfg"
                CUSTOM_LANGUAGE_PATH = "$DIR_PATH/config/custom_language.lang"
            }

        var DIR_PATH = "$HOME_DIR_PATH/.coremapx"
            private set

        var LOGS_DIR_PATH = "$DIR_PATH/logs"
            private set

        var CONFIG_DIR_PATH = "$DIR_PATH/config"
            private set

        var FONTS_DIR_PATH = "$DIR_PATH/config/fonts"
            private set

        var REGULAR_FONT_PATH = "$FONTS_DIR_PATH/Font-Regular.ttf"
            private set

        var BOLD_FONT_PATH = "$FONTS_DIR_PATH/Font-Bold.ttf"
            private set

        var MEDIUM_FONT_PATH = "$FONTS_DIR_PATH/Font-Medium.ttf"
            private set

        var LIGHT_FONT_PATH = "$FONTS_DIR_PATH/Font-Light.ttf"
            private set

        var DATA_DIR_PATH = "$DIR_PATH/data"
            private set

        var TEMP_DIR_PATH = "$DIR_PATH/data/temp"
            private set

        var GRAPHS_DIR_PATH = "$DIR_PATH/data/graphs"
            private set

        var DIRECTORIES =
            listOf(
                LOGS_DIR_PATH,
                CONFIG_DIR_PATH,
                FONTS_DIR_PATH,
                DATA_DIR_PATH,
                TEMP_DIR_PATH,
                GRAPHS_DIR_PATH,
            )
            private set

        var CONFIG_FILE_PATH = "$DIR_PATH/config/config.cfg"
            private set
    }

    object AppResources {
        const val DEFAULT_CONFIG_PATH = "config/DefaultConfig.cfg"
        const val DEFAULT_FONTS_DIRECTORY_PATH = "fonts"

        var EN_LANGUAGE_PATH = "app/src/main/resources/languages/en.lang"
            private set
        var RU_LANGUAGE_PATH = "app/src/main/resources/languages/ru.lang"
            private set

        var CUSTOM_LANGUAGE_PATH = "$DIR_PATH/config/custom_language.lang"
            internal set

        fun setLanguageResourcesPathsForTests() {
            EN_LANGUAGE_PATH = "languages/en.lang"
            RU_LANGUAGE_PATH = "languages/ru.lang"
        }
    }

    object View {
        const val DISABLED_ALPHA = 0.38F
        const val DESCRIPTION_ALPHA = 0.6F
    }
}
