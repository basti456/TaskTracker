package com.example.tasktraker.screens

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tasktraker.models.Task
import com.example.tasktraker.models.TaskCategory
import com.example.tasktraker.models.TaskPriority
import com.example.tasktraker.ui.theme.BackgroundLight
import com.example.tasktraker.ui.theme.BluePrimary
import com.example.tasktraker.ui.theme.TextPrimary
import com.example.tasktraker.ui.theme.TextSecondary
import com.example.tasktraker.viewModels.AddEditTaskViewModel
import com.example.tasktraker.viewModels.SaveDeleteTaskUIEvent
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    taskId: Long = -1L,
    viewModel: AddEditTaskViewModel = koinViewModel(),
    navigateToTaskScreen: () -> Unit
) {
    println("AddTaskScreen $taskId")
    val isTaskEdit = taskId != -1L
    val addEditUIState by viewModel.addEditTaskUIState.collectAsState()
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = addEditUIState.dueDate)

    var showDatePickerDialog by remember { mutableStateOf(false) }

    val formattedDate = remember(addEditUIState.dueDate) {
        SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(addEditUIState.dueDate)
    }
    val context = LocalContext.current

    val filePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.onFileUriUpdate(it.toString())
                viewModel.onFileNameUpdate(getFileName(context, it))
            }

        }
    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    LaunchedEffect(Unit) {
        viewModel.saveDeleteTaskEvent.collect { event ->
            when (event) {
                is SaveDeleteTaskUIEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }

                is SaveDeleteTaskUIEvent.NavigateBack -> {
                    navigateToTaskScreen()
                }
            }
        }
    }

    if (showDatePickerDialog) {
        DatePickerDialog(onDismissRequest = {
            showDatePickerDialog = false
        }, confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { viewModel.onDueDateUpdate(it) }
                showDatePickerDialog = false
            }) {
                Text("OK")
            }
        }, dismissButton = {
            TextButton(onClick = { showDatePickerDialog = false }) {
                Text("Cancel")
            }
        }) {
            DatePicker(state = datePickerState)
        }
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
                TextButton(
                    onClick = {
                        navigateToTaskScreen()
                    },
                    modifier = Modifier.align(Alignment.CenterStart),
                ) {
                    Text(
                        "Cancel",
                        style = MaterialTheme.typography.labelLarge.copy(color = TextPrimary)
                    )
                }

                Text(
                    if (isTaskEdit) "Edit Task" else "New Task",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.headlineMedium.copy(color = TextPrimary)
                )
                if (isTaskEdit) {
                    TextButton(
                        onClick = {
                            viewModel.saveTask()
                        },
                        modifier = Modifier.align(Alignment.CenterEnd),
                    ) {
                        Text(
                            "Save",
                            style = MaterialTheme.typography.labelLarge.copy(color = TextPrimary)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Task Name",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = TextSecondary.copy(alpha = 0.5f), fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = addEditUIState.taskName,
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("What needs to be done?") },
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { viewModel.onTaskNameUpdate(it) },
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
                    "Task Description",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = TextSecondary.copy(alpha = 0.5f), fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = addEditUIState.taskDescription,
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("Add details,subtasks, or links...") },
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { viewModel.onTaskDescriptionUpdate(it) },
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
                    TaskCategory.entries.forEachIndexed { index, category ->
                        val isSelected = addEditUIState.category == category
                        TaskCategoryChip(
                            category.category,
                            isSelected,
                            onClick = { viewModel.onTaskCategoryUpdate(category) })
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
                DetailsItem(
                    Icons.Default.CalendarMonth,
                    "Due Date",
                    formattedDate,
                    BluePrimary,
                    onClick = {
                        showDatePickerDialog = true
                    }
                )
                DetailsItem(
                    Icons.AutoMirrored.Filled.FormatListBulleted,
                    "List",
                    addEditUIState.category.category, onClick = {})
                DetailsRow(Icons.Outlined.Notifications, "Remind Me") {
                    Switch(
                        checked = addEditUIState.isRemindMe,
                        onCheckedChange = {
                            viewModel.isRemindMeUpdate()
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
                        TaskPriority.entries.forEachIndexed { index, priority ->
                            val isSelected = addEditUIState.priority == priority
                            PriorityButton(priority.priority, isSelected, Modifier.weight(1f)) {
                                viewModel.onPriorityUpdate(priority)
                            }
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(
                    onClick = {
                        filePickerLauncher.launch(arrayOf("*/*"))
                    }
                )) {
                Icon(
                    Icons.Outlined.AttachFile,
                    tint = TextPrimary.copy(alpha = 0.5f),
                    contentDescription = "Attachment"
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    if (addEditUIState.fileName == null) "Add Attachment" else addEditUIState.fileName.toString(),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = TextPrimary.copy(alpha = 0.5f), fontWeight = FontWeight.Bold
                    )
                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(60.dp),
                color = Color.White,
                shape = RoundedCornerShape(8.dp),
                border = if (isTaskEdit) BorderStroke(2.dp, Color.Red) else BorderStroke(
                    2.dp,
                    TextSecondary
                )
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        if (isTaskEdit) TextButton(
                            onClick = {
                                val task = Task(
                                    id = addEditUIState.id!!,
                                    taskName = addEditUIState.taskName,
                                    taskDescription = addEditUIState.taskDescription,
                                    category = addEditUIState.category,
                                    dueDate = addEditUIState.dueDate,
                                    isRemindMe = addEditUIState.isRemindMe,
                                    priority = addEditUIState.priority,
                                    isCompleted = addEditUIState.isCompleted,
                                    fileName = addEditUIState.fileName?:"",
                                    fileLocation = addEditUIState.fileLocation?:""
                                )
                                viewModel.deleteTask(task)
                            }
                        ) {
                            Text(
                                "Delete Task", style = MaterialTheme.typography.labelLarge.copy(
                                    color = Color.Red, fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        else TextButton(onClick = {
                            viewModel.saveTask()
                        }) {
                            Text(
                                "Save Task", style = MaterialTheme.typography.labelLarge.copy(
                                    color = TextSecondary, fontWeight = FontWeight.Bold
                                )

                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "")
                    }
                }
            }
        }
    }

}

@Composable
fun TaskCategoryChip(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.height(40.dp),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) BluePrimary.copy(alpha = 0.4f) else Color.White,
        border = if (isSelected) null else ButtonDefaults.outlinedButtonBorder(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Outlined.FormatListBulleted,
                contentDescription = "",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                title,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = if (isSelected) BluePrimary else TextSecondary,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                ),
            )
        }
    }
}

@Composable
fun DetailsItem(
    icon: ImageVector,
    detailText: String,
    detailValue: String,
    valueColor: Color = TextSecondary,
    onClick: () -> Unit
) {
    DetailsRow(icon = icon, detailText = detailText, onClick = onClick) {
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
                Icons.AutoMirrored.Filled.ArrowForward,
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
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit),

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
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
    modifier: Modifier,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) BluePrimary else Color.White,
        shape = RoundedCornerShape(12.dp),
        border = if (isSelected) null else ButtonDefaults.outlinedButtonBorder(),
        modifier = modifier
            .height(40.dp)
            .padding(horizontal = 4.dp),
        onClick = onClick
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