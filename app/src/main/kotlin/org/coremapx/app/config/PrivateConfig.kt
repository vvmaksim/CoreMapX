package org.coremapx.app.config

object PrivateConfig {
    object MainMenu {
        const val LOGO_SIZE = 52
    }

    object UserDirectory {
        val HOME_DIR_PATH = "${System.getProperty("user.home")}"
        val DIR_PATH = "$HOME_DIR_PATH/.coremapx"
        val LOGS_DIR_PATH = "$DIR_PATH/logs"
        val CONFIG_DIR_PATH = "$DIR_PATH/config"
        val FONTS_DIR_PATH = "$DIR_PATH/config/fonts"
        val DATA_DIR_PATH = "$DIR_PATH/data"
        val TEMP_DIR_PATH = "$DIR_PATH/data/temp"
        val GRAPHS_DIR_PATH = "$DIR_PATH/data/graphs"
        val DIRECTORIES =
            listOf(
                LOGS_DIR_PATH,
                CONFIG_DIR_PATH,
                FONTS_DIR_PATH,
                DATA_DIR_PATH,
                TEMP_DIR_PATH,
                GRAPHS_DIR_PATH,
            )
        val CONFIG_FILE_PATH = "$DIR_PATH/config/config.cfg"
    }

    object AppResources {
        const val DEFAULT_CONFIG_PATH = "app/src/main/resources/config/DefaultConfig.cfg"
        const val DEFAULT_FONTS_DIRECTORY_PATH = "app/src/main/resources/fonts"
        const val EN_LANGUAGE_PATH = "app/src/main/resources/languages/en.lang"
        const val RU_LANGUAGE_PATH = "app/src/main/resources/languages/ru.lang"
    }

    object View {
        const val DISABLED_ALPHA = 0.38F
        const val DESCRIPTION_ALPHA = 0.6F
    }
}
