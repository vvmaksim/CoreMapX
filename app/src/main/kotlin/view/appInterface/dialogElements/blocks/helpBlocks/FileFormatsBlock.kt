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
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun FileFormatsBlock() {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(
            text = "Общие сведения",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "В этом разделе описаны особенности поддерживаемых форматов хранения графа: " +
                    "GRAPH (.graph), JSON (.json) и SQLite DB (.db).\n\n" +
                    "Основной формат — GRAPH. При открытии файла в любом формате он конвертируется в " +
                    "GRAPH, что упрощает преобразование графа между форматами.",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Формат GRAPH (.graph)",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Формат GRAPH — текстовый файл с двумя блоками: `Info:` и `Graph:`.\n\n" +
                    "Блок `Info:` содержит обязательные параметры `isDirected` и `isWeighted`. " +
                    "Рекомендуется указывать все параметры: `name`, `author`, `isDirected`, `isWeighted`.\n\n" +
                    "Блок `Graph:` включает команды добавления (только с типом `add`). " +
                    "Рекомендуется сначала добавлять все вершины, затем рёбра, " +
                    "так как команды обрабатываются последовательно сверху вниз.",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Пример 2.1\n" +
                    "Взвешенный направленный граф в формате GRAPH:",
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "Info:\n" +
                    "name=Graph Name\n" +
                    "author=Patrick Bateman\n" +
                    "isDirected=true\n" +
                    "isWeighted=true\n" +
                    "\n" +
                    "Graph:\n" +
                    "add vertex 0 0\n" +
                    "add vertex 1 1\n" +
                    "add edge 0 1 52",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Формат JSON (.json)",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Формат JSON — текстовый файл с двумя блоками: `info` и `graph`.\n\n" +
                    "Блок `info` содержит обязательные параметры `isDirected` и `isWeighted`. " +
                    "Рекомендуется указывать все параметры: `name`, `author`, `isDirected`, `isWeighted`.\n\n" +
                    "Блок `graph` включает два списка: `vertices` (вершины) и `edges` (рёбра).",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Пример 2.2\n" +
                    "Взвешенный направленный граф в формате JSON:",
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "{\n" +
                    "    \"info\": {\n" +
                    "        \"name\": \"Graph Name\",\n" +
                    "        \"author\": \"Patrick Bateman\",\n" +
                    "        \"isDirected\": true,\n" +
                    "        \"isWeighted\": true\n" +
                    "    },\n" +
                    "    \"graph\": {\n" +
                    "        \"vertices\": [\n" +
                    "            {\n" +
                    "                \"id\": 0,\n" +
                    "                \"label\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"id\": 1,\n" +
                    "                \"label\": \"1\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"edges\": [\n" +
                    "            {\n" +
                    "                \"from\": 0,\n" +
                    "                \"to\": 1,\n" +
                    "                \"weight\": 52\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Формат SQL DB (.db)",
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                "Формат SQLite DB — база данных с тремя таблицами: `graphs`, `vertices` и `edges`. " +
                    "Такой подход позволяет хранить несколько графов в одном файле (репозитории). " +
                    "Файлы .db открываются отдельной кнопкой в главном меню.\n\n" +
                    "Ниже приведены структуры таблиц на языке SQL.",
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "CREATE TABLE \"graphs\" (\n" +
                    "   \"graph_id\"    INTEGER,\n" +
                    "   \"name\"    TEXT,\n" +
                    "   \"author\"  TEXT,\n" +
                    "   \"isDirected\"  INTEGER NOT NULL,\n" +
                    "   \"isWeighted\"  INTEGER NOT NULL,\n" +
                    "   PRIMARY KEY(\"graph_id\" AUTOINCREMENT)\n" +
                    ");",
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "CREATE TABLE \"vertices\" (\n" +
                    "   \"id\"  INTEGER,\n" +
                    "   \"graph_id\"    INTEGER NOT NULL,\n" +
                    "   \"vertex_id\"   INTEGER NOT NULL,\n" +
                    "   \"label\"   TEXT NOT NULL,\n" +
                    "   UNIQUE(\"graph_id\",\"vertex_id\"),\n" +
                    "   PRIMARY KEY(\"id\" AUTOINCREMENT),\n" +
                    "   FOREIGN KEY(\"graph_id\") REFERENCES \"graphs\"(\"graph_id\") ON DELETE CASCADE\n" +
                    ");",
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "CREATE TABLE \"edges\" (\n" +
                    "   \"id\"  INTEGER,\n" +
                    "   \"graph_id\"    INTEGER NOT NULL,\n" +
                    "   \"from_vertex\" INTEGER NOT NULL,\n" +
                    "   \"to_vertex\"   INTEGER NOT NULL,\n" +
                    "   \"weight\"  INTEGER,\n" +
                    "   PRIMARY KEY(\"id\" AUTOINCREMENT),\n" +
                    "   FOREIGN KEY(\"from_vertex\") REFERENCES \"vertices\"(\"vertex_id\"),\n" +
                    "   FOREIGN KEY(\"graph_id\") REFERENCES \"graphs\"(\"graph_id\") ON DELETE CASCADE,\n" +
                    "   FOREIGN KEY(\"to_vertex\") REFERENCES \"vertices\"(\"vertex_id\")\n" +
                    ");",
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewGeneralBlock() {
    AppTheme {
        PreviewSurface(content = { FileFormatsBlock() })
    }
}
