package com.example.tasktraker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tasktraker.models.Task
import com.example.tasktraker.models.TaskCategory
import com.example.tasktraker.models.TaskPriority
import com.example.tasktraker.ui.theme.BackgroundLight
import com.example.tasktraker.ui.theme.BluePrimary
import com.example.tasktraker.ui.theme.TextPrimary
import com.example.tasktraker.ui.theme.TextSecondary


enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    HOME("home", "Home", Icons.Default.Add, "Home"),
    ALERTS("alerts", "Alerts", Icons.Default.AddAlert, "Alerts"),
}

@Composable
fun TaskScreen() {
    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    var searchText by remember { mutableStateOf("") }
    val chips = listOf("All", "Work", "Personal", "Shopping", "Health", "Other")
    Scaffold(bottomBar = {
        NavigationBar(
            containerColor = Color.White,
            windowInsets = NavigationBarDefaults.windowInsets
        ) {
            Destination.entries.forEachIndexed { index, destination ->
                NavigationBarItem(
                    selected = selectedDestination == index,
                    onClick = { },
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
            onClick = {},
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
                        "Wednesday, Mar18",
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
                value = searchText,
                placeholder = { Text("Search your tasks") },
                onValueChange = { searchText = it },
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
                items(chips.size) { index ->
                    ChipButton(chips[index], true)
                }
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    TaskCard {

                    }
                }
            }
        }
    }
}

@Composable
fun ChipButton(
    title: String,
    isSelected: Boolean
) {
    Surface(
        color = if (isSelected) BluePrimary else Color.White,
        shape = RoundedCornerShape(12.dp),
        border = if (isSelected) null else ButtonDefaults.outlinedButtonBorder,
        modifier = Modifier.height(40.dp)
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
fun TaskCard(onDelete: () -> Unit) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                false
            }
        }
    )
    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = Modifier.fillMaxWidth(),
        backgroundContent = {

        },

        ) {
        TaskItem(
            task = com.example.tasktraker.databases.entity.Task(
                id = 0,
                taskName = "Work on Kotlin",
                taskDescription = "I have to do too much work on kotlin and will do it by today",
                category = TaskCategory.WORK,
                dueDate = "28 Mar 2018",
                isRemindMe = 1,
                fileName = "Task",
                fileLocation = "task/hello",
                priority = TaskPriority.MEDIUM
            ),
            onTaskItemClick = {

            },
            onTaskCheckClick = {

            }
        )
    }
}