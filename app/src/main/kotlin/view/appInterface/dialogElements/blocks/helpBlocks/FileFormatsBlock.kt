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
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun FileFormatsBlock() {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(
            text = LocalizationManager.states.dialogs.graphFormatsGeneralInformationTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.graphFormatsGeneralInformationText1.value,
                ),
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = LocalizationManager.states.dialogs.graphFormatsGraphFormatTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.graphFormatsGraphFormatText1.value,
                ),
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.graphFormatsExample2Point1.value,
                ),
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
            text = LocalizationManager.states.dialogs.graphFormatsJsonFormatTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.graphFormatsJsonFormatText1.value,
                ),
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.graphFormatsExample2Point2.value,
                ),
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
            text = LocalizationManager.states.dialogs.graphFormatsSqliteDbFormatTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.graphFormatsSqliteDbFormatText1.value,
                ),
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
