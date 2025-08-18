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
fun CommandBlock() {
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
                "Большинство команд имеют формат: `<Тип> <Объект> <Параметры>`.\n\n" +
                    "Для выполнения нескольких команд используйте разделитель `;`.\n\n" +
                    "В справке приведены примеры команд, которые можно опробовать, создав новый граф.\n\n" +
                    "Параметры можно задавать явно через символ двоеточия (с указанием имени, например, `id:1`) " +
                    "или неявно (без имени, в строгом порядке).",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "ПРИМЕЧАНИЕ 1.1\n" +
                    "Смешивание явной и неявной форм записи запрещено. Например, команда `add vertex 1 label:label1` недопустима.",
            color = config.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Пример 1.1\n" +
                    "Взвешенный граф с вершинами и рёбрами, заданными явно и неявно:",
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "add vertex 1 A;\n" +
                    "add vertex id:2 label:B;\n" +
                    "add vertex label:C id:3;\n" +
                    "add edge 1 2 52;\n" +
                    "add edge from:2 to:3 weight:5;\n" +
                    "add edge weight:25 to:1 from:3",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "ПРИМЕЧАНИЕ 1.2\n" +
                    "В невзвешенных графах параметр `weight` не требуется и игнорируется.",
            color = config.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Команда add",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Для `add vertex` доступны параметры (в порядке неявной записи):\n" +
                    "   `id` — число типа Long (8 байт)\n" +
                    "   `label` — строка без пробелов и символов `:` двоеточия\n\n" +
                    "Для `add edge` доступны параметры (в порядке неявной записи):\n" +
                    "   `from` — id вершины, от которой идёт ребро\n" +
                    "   `to` — id вершины, в которую идёт ребро\n" +
                    "   `weight` — число типа Long (8 байт), указывается для взвешенных графов",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Команда rm/remove",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "ПРИМЕЧАНИЕ 1.3\n" +
                    "Команды `rm` и `remove` эквивалентны, можно использовать любую форму.",
            color = config.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Для `rm vertex` используется параметр:\n" +
                    "   `id` — число типа Long (8 байт)\n\n" +
                    "Для `rm edge` используются параметры (в порядке неявной записи):\n" +
                    "   `from` — id вершины, от которой удаляется ребро\n" +
                    "   `to` — id вершины, до которой удаляется ребро\n\n" +
                    "Также можно удалить ребро по его уникальному `id`, автоматически присваиваемому каждому ребру.",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "ПРИМЕЧАНИЕ 1.4\n" +
                    "Пусть существует граф с вершинами 1 и 2, а так же ребром (1, 2). " +
                    "Выполнение команды `rm edge from:2 to:1` удалит ребро (1, 2), " +
                    "так как в ненаправленном графе (1, 2) и (2, 1) — одно и то же ребро. " +
                    "В направленном графе ребро (1, 2) останется.",
            color = config.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "add vertex id:1 label:1;\n" +
                    "add vertex id:2 label:2;\n" +
                    "add edge from:1 to:2",
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock("rm edge from:2 to:1")
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Команда set",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Для `set strategy` используется параметр:\n" +
                    "   `strategy` — строка с названием стратегии расположения вершин графа. " +
                    "Допустимые значения: Random, Circular, Force-Directed.",
        )
        Spacer(Modifier.height(8.dp))
        Text("Смена стратегии на Random (Случайное расположение вершин):")
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "set strategy Random",
        )
        Spacer(Modifier.height(8.dp))
        Text("Смена стратегии на Circular (Вершины располагаются по окружности):")
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "set strategy Circular",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Смена стратегии на Force-Directed " +
                    "(Вершины динамически перемещаются определённое количество итераций по алгоритму):",
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "set strategy Force-Directed",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Команда clear",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text("Для `clear` параметры не задаются. Она нужна для отчистки сообщений в консоли.")
        Spacer(Modifier.height(8.dp))
        CodeBlock("clear")
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Команда graph_clear",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Для `graph_clear` параметры не задаются. Она нужна для полной отчистки графа " +
                    "(Будут удалены все вершины и рёбра). Будьте с ней осторожны.",
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock("graph_clear")
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Команда help",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text("Для `help` параметры не задаются. Она нужна для получения краткой справки по самым популярным командам.")
        Spacer(Modifier.height(8.dp))
        CodeBlock("help")
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewGeneralBlock() {
    AppTheme {
        PreviewSurface(content = { CommandBlock() })
    }
}
