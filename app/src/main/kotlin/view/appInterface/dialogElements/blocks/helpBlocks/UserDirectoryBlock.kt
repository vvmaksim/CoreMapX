package view.appInterface.dialogElements.blocks.helpBlocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun UserDirectoryBlock() {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(
            text = "Общая информация",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Пользовательская директория используется для хранения конфигурационных файлов, локализации, " +
                    "временных файлов, шрифтов и логов приложения. По умолчанию графы сохраняются и открываются из " +
                    "папки `.coremapx/data/graphs`.\n\n" +
                    "Директория создаётся в домашней папке вашей системы. Путь зависит от операционной системы:\n" +
                    "   UNIX-подобные системы: `/home/UserName/.coremapx`\n" +
                    "   Windows: `C:\\Users\\UserName\\.coremapx`",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "ПРИМЕЧАНИЕ 3.1\n" +
                    "Не изменяйте конфигурационный файл во время работы приложения, это может привести к ошибкам. " +
                    "Используйте настройки приложения для изменения конфигурации.",
            color = config.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "ПРИМЕЧАНИЕ 3.2\n" +
                    "При изменении параметров в текстовом поле нажмите клавишу `Enter` для сохранения. " +
                    "После этого появится уведомление о результате операции.",
            color = config.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Настройка языка интерфейса",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Приложение поддерживает перевод интерфейса на любой язык с кодировкой UTF-8. " +
                    "Для этого в директории `.coremapx/config/` находится файл `custom_language.lang`, " +
                    "в котором текст задаётся по ключам, используемым в приложении. " +
                    "Чтобы применить перевод, в настройках выберите язык `Custom`.",
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewUserDirectoryBlock() {
    AppTheme {
        PreviewSurface(content = { UserDirectoryBlock() })
    }
}
