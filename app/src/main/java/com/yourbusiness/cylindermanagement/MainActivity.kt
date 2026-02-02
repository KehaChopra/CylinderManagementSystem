package com.yourbusiness.cylindermanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.yourbusiness.cylindermanagement.ui.screens.AddCylinderScreen
import com.yourbusiness.cylindermanagement.ui.theme.CylinderManagementTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CylinderManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        // Home Screen
                        composable("home") {
                            HomeScreen1(
                                onMenuClick = {
                                    navController.navigate("menu")
                                }
                            )
                        }

                        // Menu Screen
                        composable("menu") {
                            MenuScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                },
                                onMenuItemClick = { route ->
                                    when (route) {
                                        "all_cylinders" -> {
                                            navController.navigate("all_cylinders")
                                        }
                                        "customers" -> {
                                            navController.popBackStack()
                                            // navController.navigate("customers")
                                        }
                                        "add_cylinder" -> {
                                            navController.navigate("add_cylinder")
                                        }
                                        "vendors" -> {
                                            navController.popBackStack()
                                            // navController.navigate("vendors")
                                        }
                                        "add_customer" -> {
                                            navController.popBackStack()
                                            // navController.navigate("add_customer")
                                        }
                                        "credit_list" -> {
                                            navController.popBackStack()
                                            // navController.navigate("credit_list")
                                        }
                                        "issued_list" -> {
                                            navController.popBackStack()
                                            // navController.navigate("issued_list")
                                        }
                                        "daily_book" -> {
                                            navController.popBackStack()
                                            // navController.navigate("daily_book")
                                        }
                                        "add_inventory" -> {
                                            navController.popBackStack()
                                            // navController.navigate("add_inventory")
                                        }
                                        "inventory_vendors" -> {
                                            navController.popBackStack()
                                            // navController.navigate("inventory_vendors")
                                        }
                                        else -> {
                                            navController.popBackStack()
                                        }
                                    }
                                }
                            )
                        }

                        // All Cylinders Screen
                        composable("all_cylinders") {
                            AllCylindersScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onCylinderClick = { serialNumber ->
                                    // Navigate to cylinder details with serial number
                                    navController.navigate("cylinder_details/$serialNumber")
                                }
                            )
                        }

                        // Cylinder Details Screen
                        composable("cylinder_details/{serialNumber}") { backStackEntry ->
                            val serialNumber = backStackEntry.arguments?.getString("serialNumber") ?: ""
                            CylinderDetailsScreen(
                                serialNumber = serialNumber,
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        // Add Cylinder Screen
                        composable("add_cylinder") {
                            AddCylinderScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onAddCylinder = { gasType, volumeType, serialNumber, batchNumber, status, remarks ->
                                    // Handle adding cylinder
                                    // Save to database or perform action
                                    navController.popBackStack()
                                }
                            )
                        }

                        // Add other screens here as you create them
                        // composable("customers") { CustomersScreen(onBackClick = { navController.popBackStack() }) }
                        // composable("vendors") { VendorsScreen(onBackClick = { navController.popBackStack() }) }
                        // etc...
                    }
                }
            }
        }
    }
}