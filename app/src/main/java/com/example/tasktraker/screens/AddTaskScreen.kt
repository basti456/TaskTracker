package com.example.tasktraker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tasktraker.ui.theme.BackgroundLight
import com.example.tasktraker.ui.theme.BluePrimary
import com.example.tasktraker.ui.theme.TextPrimary
import com.example.tasktraker.ui.theme.TextSecondary
import com.example.tasktraker.viewModels.AddEditTaskViewModel
import org.koin.compose.viewmodel.koinViewModel

enum class Categories(val title: String) {
    WORK("Work"),
    PERSONAL("Personal"),
    SHOPPING("Shopping"),
    HEALTH("Health"),
    OTHER("Other")
}

@Composable
fun AddTaskScreen(
    taskId: Long = -1L,
    viewModel: AddEditTaskViewModel = koinViewModel()
) {
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var isRemindMeChecked by remember {
        mutableStateOf(false)
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Cancel",
                    modifier = Modifier.align(Alignment.CenterStart),
                    style = MaterialTheme.typography.labelLarge.copy(color = TextPrimary)
                )
                Text(
                    "New Task",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.headlineMedium.copy(color = TextPrimary)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "TaskName",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = TextSecondary.copy(alpha = 0.5f), fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = taskName,
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("What needs to be done?") },
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { taskName = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = BluePrimary.copy(0.1f),
                        unfocusedContainerColor = BluePrimary.copy(0.05f),
                        focusedBorderColor = BackgroundLight,
                        unfocusedBorderColor = BackgroundLight
                    )
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Description",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = TextSecondary.copy(alpha = 0.5f), fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = taskDescription,
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("Add details,subtasks, or links...") },
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { taskDescription = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = BluePrimary.copy(0.1f),
                        unfocusedContainerColor = BluePrimary.copy(0.05f),
                        focusedBorderColor = BackgroundLight,
                        unfocusedBorderColor = BackgroundLight
                    ),
                    minLines = 4
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Category",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = TextSecondary.copy(alpha = 0.5f), fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Categories.entries.forEachIndexed { index, categories ->
                        TaskCategoryChip(categories.title, true)
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    "Details", style = MaterialTheme.typography.labelMedium.copy(
                        color = TextSecondary.copy(alpha = 0.5f), fontWeight = FontWeight.Bold
                    )
                )
                DetailsItem(Icons.Default.CalendarMonth, "Due Date", "Mar 23, 2026", BluePrimary)
                DetailsItem(Icons.Default.FormatListBulleted, "List", "Other")
                DetailsRow(Icons.Outlined.Notifications, "Remind Me") {
                    Switch(
                        checked = isRemindMeChecked,
                        onCheckedChange = {
                            isRemindMeChecked = it
                        }
                    )

                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    "Priority",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = TextSecondary.copy(alpha = 0.5f), fontWeight = FontWeight.Bold
                    )
                )
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(60.dp),
                    color = BackgroundLight,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PriorityButton("Low", true, modifier = Modifier.weight(1f))
                        PriorityButton("Medium", false, modifier = Modifier.weight(1f))
                        PriorityButton("High", false, modifier = Modifier.weight(1f))
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.AttachFile,
                    tint = TextPrimary.copy(alpha = 0.5f),
                    contentDescription = "Attachment"
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Add Attachment",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = TextPrimary.copy(alpha = 0.5f), fontWeight = FontWeight.Bold
                    )
                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(60.dp),
                color = TextSecondary,
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Save Task")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = "")
                    }
                }
            }
        }
    }

}

@Composable
fun TaskCategoryChip(title: String, isSelected: Boolean) {
    Surface(
        modifier = Modifier.height(40.dp),
        shape = RoundedCornerShape(12.dp),
        color = BackgroundLight,
        border = if (isSelected) null else ButtonDefaults.outlinedButtonBorder
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.FormatListBulleted,
                contentDescription = "",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                title,
                style = MaterialTheme.typography.labelMedium.copy(color = TextPrimary),
            )
        }
    }
}

@Composable
fun DetailsItem(
    icon: ImageVector,
    detailText: String,
    detailValue: String,
    valueColor: Color = TextSecondary
) {
    DetailsRow(icon = icon, detailText = detailText) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = if (valueColor == BluePrimary) BluePrimary.copy(alpha = 0.5f) else Color.Transparent,
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    detailValue,
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary),
                    modifier = Modifier.padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Arrow",
                tint = TextSecondary.copy(alpha = 0.5f),
            )
        }
    }
}

@Composable
fun DetailsRow(
    icon: ImageVector,
    detailText: String,
    trailing: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = BackgroundLight,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = detailText,
                    tint = if (detailText != "Remind Me") BluePrimary else Color(0xFFFBC02D)
                )

            }

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                detailText,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        trailing()
    }

}

@Composable
fun PriorityButton(
    title: String,
    isSelected: Boolean,
    modifier: Modifier
) {
    Surface(
        color = if (isSelected) BluePrimary else Color.White,
        shape = RoundedCornerShape(12.dp),
        border = if (isSelected) null else ButtonDefaults.outlinedButtonBorder,
        modifier = modifier
            .height(40.dp)
            .padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = if (isSelected) Color.White else TextSecondary,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            )
        }
    }
}