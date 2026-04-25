package com.example.tasktraker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.navigation3.runtime.NavKey
import com.example.tasktraker.models.Task
import com.example.tasktraker.models.TaskCategory
import com.example.tasktraker.navigation.LocalNavBackStack
import com.example.tasktraker.navigation.Route
import com.example.tasktraker.ui.theme.BluePrimary
import com.example.tasktraker.ui.theme.TextPrimary
import com.example.tasktraker.ui.theme.TextSecondary
import com.example.tasktraker.viewModels.TaskUIState
import com.example.tasktraker.viewModels.TaskViewModel
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale


enum class Destination(
    val route: NavKey,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    HOME(Route.TaskList, "Home", Icons.Default.Add, "Home"),
    ALERTS(Route.TaskDetail(-1L), "Alerts", Icons.Default.AddAlert, "Alerts"),
}

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = koinViewModel(),
    onTaskItemClicked: (Long) -> Unit,
    onAddTaskClicked: () -> Unit
) {
    val taskUIState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val taskCategorySelected by viewModel.selectedCategory.collectAsState()
    val backStack = LocalNavBackStack.current

    Scaffold(bottomBar = {
        NavigationBar(
            containerColor = Color.White,
            windowInsets = NavigationBarDefaults.windowInsets
        ) {
            val currentKey = backStack.last()
            Destination.entries.forEach { destination ->
                NavigationBarItem(
                    selected = currentKey == destination.route,
                    onClick = {
                        if (currentKey != destination.route) {
                            backStack.add(destination.route)
                        }
                    },
                    icon = {
                        Icon(
                            destination.icon,
                            contentDescription = destination.contentDescription
                        )
                    },
                    label = { Text(destination.label) },
                )
            }
        }
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = onAddTaskClicked,
            contentColor = Color.White,
            shape = CircleShape,
            containerColor = Color(0xFF4A80F0),
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 18.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        SimpleDateFormat(
                            "MMMM dd, yyyy",
                            Locale.getDefault()
                        ).format(System.currentTimeMillis()),
                        style = MaterialTheme.typography.labelMedium.copy(color = TextSecondary)
                    )
                    Text(
                        "My Tasks",
                        style = MaterialTheme.typography.headlineLarge.copy(color = TextPrimary)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFFE3F2FD), shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        tint = Color(0xFF1E88E5),
                        contentDescription = "Notification"
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = searchQuery,
                placeholder = { Text("Search your tasks") },
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    ChipButton("All", taskCategorySelected == null, onCategoryClicked = {
                        viewModel.onTaskCategoryChanged(null)
                    })
                }
                items(TaskCategory.entries) { taskCategory ->
                    ChipButton(
                        taskCategory.category,
                        isSelected = taskCategorySelected == taskCategory,
                        { viewModel.onTaskCategoryChanged(taskCategory) })
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            when (val state = taskUIState) {
                is TaskUIState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is TaskUIState.Empty -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Tasks Present. Please click + to add")
                    }
                }

                is TaskUIState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Something went wrong. Please try again")
                    }
                }

                is TaskUIState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.tasks) { task ->
                            println("Task Id's are ${task.id}")
                            TaskCard(
                                task = task,
                                onDelete = { viewModel.deleteTask(task) },
                                onTaskItemClicked = { onTaskItemClicked(task.id) },
                                onTaskCheckboxClicked = { viewModel.toggleTaskCompletion(task) }
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ChipButton(
    title: String,
    isSelected: Boolean,
    onCategoryClicked: () -> Unit
) {
    Surface(
        color = if (isSelected) BluePrimary else Color.White,
        shape = RoundedCornerShape(12.dp),
        border = if (isSelected) null else ButtonDefaults.outlinedButtonBorder,
        modifier = Modifier.height(40.dp),
        onClick = onCategoryClicked
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

@Composable
fun TaskCard(
    task: Task,
    onDelete: () -> Unit,
    onTaskItemClicked: () -> Unit,
    onTaskCheckboxClicked: () -> Unit
) {

    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        SwipeToDismissBoxValue.Settled,
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                false
            }
        },
        SwipeToDismissBoxDefaults.positionalThreshold
    )
    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = Modifier.fillMaxWidth(),
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove item",
                            modifier = Modifier.padding(16.dp),
                            tint = Color.White
                        )
                    }
                }

                else -> {}
            }
        },
    ) {
        TaskItem(
            task = task,
            onTaskItemClick = onTaskItemClicked,
            onTaskCheckClick = onTaskCheckboxClicked
        )
    }
}