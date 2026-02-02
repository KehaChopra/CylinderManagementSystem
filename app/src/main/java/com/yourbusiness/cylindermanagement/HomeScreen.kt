package com.yourbusiness.cylindermanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbusiness.cylindermanagement.ui.theme.AmmoniaAccent
import com.yourbusiness.cylindermanagement.ui.theme.BackgroundBlue
import com.yourbusiness.cylindermanagement.ui.theme.CO2Accent
import com.yourbusiness.cylindermanagement.ui.theme.CardWhite
import com.yourbusiness.cylindermanagement.ui.theme.DarkText
import com.yourbusiness.cylindermanagement.ui.theme.LPGAccent
import com.yourbusiness.cylindermanagement.ui.theme.MediumGray
import com.yourbusiness.cylindermanagement.ui.theme.NitrogenAccent
import com.yourbusiness.cylindermanagement.ui.theme.OxygenAccent
import com.yourbusiness.cylindermanagement.ui.theme.SelectedBlue

data class CylinderData(
    val full: Int,
    val empty: Int,
    val issued: Int,
    val repair: Int,
    val plant: Int
)

data class GasType(
    val name: String,
    val formula: String,
    val color: Color,
    val data: CylinderData
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen1(
    onMenuClick: () -> Unit = {}  // Added callback for menu click
) {
    var selectedFilter by remember { mutableStateOf("Full") }
    var selectedNavItem by remember { mutableStateOf("Bill") }

    val gasTypes = listOf(
        GasType("Oxygen", "O₂", OxygenAccent, CylinderData(7, 62, 218, 6, 0)),
        GasType("LPG", "LPG", LPGAccent, CylinderData(19, 54, 12, 2, 5)),
        GasType("Ammonia", "NH₃", AmmoniaAccent, CylinderData(1, 0, 1, 0, 0)),
        GasType("Nitrogen", "N₂", NitrogenAccent, CylinderData(51, 15, 89, 2, 10)),
        GasType("Carbon Dioxide", "CO₂", CO2Accent, CylinderData(33, 10, 45, 4, 5))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Cylinder Management",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {  // Updated to call onMenuClick
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = DarkText)
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { /* TODO*/ }) {
                            Icon(Icons.Default.Notifications, contentDescription = "Reminders", tint = DarkText)
                        }
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .offset(x = 24.dp, y = 8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50))
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundBlue
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedNavItem,
                onItemSelected = { selectedNavItem = it }
            )
        },
        containerColor = BackgroundBlue
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filter Buttons
            FilterSection(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Gas Cylinders List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(gasTypes) { gas ->
                    GasCard(gas = gas, onClick = { /* TODO*/ })
                }
            }
        }
    }
}

@Composable
fun FilterSection(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    val filters = listOf("Full", "Empty", "Issued", "Repair", "At Plant")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        filters.forEach { filter ->
            FilterButton(
                text = filter,
                isSelected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) SelectedBlue else CardWhite,
            contentColor = if (isSelected) Color.White else DarkText
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp
        ),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
fun GasCard(gas: GasType, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick), //whole card becomes clickable
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Colored side bar with gradient
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(110.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf( //darker in center lighter at edges
                                gas.color.copy(alpha = 0.3f),
                                gas.color,
                                gas.color.copy(alpha = 0.3f)
                            )
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Gas Icon Circle - Double circle effect
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(gas.color.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(gas.color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = gas.formula,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Gas Information
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = gas.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkText
                        )
                        // Arrow indicator
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Open details",
                            tint = MediumGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Statistics in one row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatItem("${gas.data.full}", "Full", DarkText)
                        Text("|", color = Color.LightGray, fontSize = 14.sp)
                        StatItem("${gas.data.empty}", "Empty", DarkText)
                        Text("|", color = Color.LightGray, fontSize = 14.sp)
                        StatItem("${gas.data.issued}", "Issued", DarkText)
                        Text("|", color = Color.LightGray, fontSize = 14.sp)
                        StatItem("${gas.data.repair}", "Repair", DarkText)
                        Text("|", color = Color.LightGray, fontSize = 14.sp)
                        StatItem("${gas.data.plant}", "At Plant", DarkText)
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(value: String, label: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(45.dp)
    ) {
        Text(
            text = value,
            fontSize = 13.sp,
            color = color,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            fontSize = 9.sp,
            color = DarkText,
            fontWeight = FontWeight.Normal,
            maxLines = 1
        )
    }
}

@Composable
fun BottomNavigationBar(
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Bill",
                    tint = if (selectedItem == "Bill") SelectedBlue else MediumGray
                )
            },
            label = {
                Text(
                    "Bill",
                    color = if (selectedItem == "Bill") SelectedBlue else MediumGray
                )
            },
            selected = selectedItem == "Bill",
            onClick = { onItemSelected("Bill") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SelectedBlue,
                selectedTextColor = SelectedBlue,
                unselectedIconColor = MediumGray,
                unselectedTextColor = MediumGray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Refilling",
                    tint = if (selectedItem == "Refilling") SelectedBlue else MediumGray
                )
            },
            label = {
                Text(
                    "Refilling",
                    color = if (selectedItem == "Refilling") SelectedBlue else MediumGray
                )
            },
            selected = selectedItem == "Refilling",
            onClick = { onItemSelected("Refilling") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SelectedBlue,
                selectedTextColor = SelectedBlue,
                unselectedIconColor = MediumGray,
                unselectedTextColor = MediumGray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.List,
                    contentDescription = "Inventory",
                    tint = if (selectedItem == "Inventory") SelectedBlue else MediumGray
                )
            },
            label = {
                Text(
                    "Inventory",
                    color = if (selectedItem == "Inventory") SelectedBlue else MediumGray
                )
            },
            selected = selectedItem == "Inventory",
            onClick = { onItemSelected("Inventory") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SelectedBlue,
                selectedTextColor = SelectedBlue,
                unselectedIconColor = MediumGray,
                unselectedTextColor = MediumGray,
                indicatorColor = Color.Transparent
            )
        )
    }
}