package com.example.tasktraker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.tasktraker.screens.AddTaskScreen
import com.example.tasktraker.screens.TaskScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavigationRoot(modifier: Modifier) {

    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(baseClass = NavKey::class) {
                    subclass(Route.TaskList::class, Route.TaskList.serializer())
                    subclass(Route.TaskDetail::class, Route.TaskDetail.serializer())

                }
            }
        },
        Route.TaskList
    )
    NavDisplay(
        backStack = backStack,
        entryProvider = { key ->
            when (key) {
                is Route.TaskList -> {
                    NavEntry(key) {
                        TaskScreen(
                            onTaskItemClicked = { taskId ->
                                backStack.add(Route.TaskDetail(taskId))
                            },
                            onAddTaskClicked = {
                                backStack.add(Route.TaskDetail(-1L))
                            }
                        )
                    }
                }

                is Route.TaskDetail -> {
                    NavEntry(key) {
                        AddTaskScreen(
                            navigateToTaskScreen = {
                                backStack.removeAt(backStack.lastIndex)
                            }
                        )
                    }
                }

                else -> error("Unknown Navkey")
            }
        }
    )
}