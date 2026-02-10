package com.yourbusiness.cylindermanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbusiness.cylindermanagement.ui.theme.OxygenAccent
import com.yourbusiness.cylindermanagement.ui.theme.BackgroundBlue
import com.yourbusiness.cylindermanagement.ui.theme.CardWhite
import com.yourbusiness.cylindermanagement.ui.theme.LightGray
import com.yourbusiness.cylindermanagement.ui.theme.MediumGray
import com.yourbusiness.cylindermanagement.ui.theme.TextGray
import com.yourbusiness.cylindermanagement.ui.theme.DarkText
import com.yourbusiness.cylindermanagement.ui.theme.BlueText

data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    onNavigateBack: () -> Unit,
    onMenuItemClick: (String) -> Unit
) {
    val menuItems = listOf(
        MenuItem("All Cylinder Details", Icons.Default.Info, "all_cylinders"),
        MenuItem("Customer Details", Icons.Default.Person, "customers"),
        MenuItem("Add Cylinder", Icons.Default.Add, "add_cylinder"),
        MenuItem("Vendor Details", Icons.Default.Person, "vendors"),
        MenuItem("Add New Customer", Icons.Default.Add, "add_customer"),
        MenuItem("Credit List", Icons.Default.List, "credit_list"),
        MenuItem("Issued List", Icons.Default.List, "issued_list"),
        MenuItem("Daily Book", Icons.Default.Email, "daily_book"),
        MenuItem("Add Inventory", Icons.Default.Add, "add_inventory"),
        MenuItem("Inventory Vendors", Icons.Default.AccountCircle, "inventory_vendors")
    )

    Scaffold(
        topBar = {
            MenuTopBar(onNavigateBack = onNavigateBack)
        },
        bottomBar = {
            DevelopedByFooter()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundBlue) // was Color(0xFFF5F5F5)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(CardWhite) // was Color.White
            ) {
                items(menuItems) { menuItem ->
                    MenuItemRow(
                        menuItem = menuItem,
                        onClick = { onMenuItemClick(menuItem.route) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopBar(onNavigateBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Cylinder Management",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = OxygenAccent // Color(0xFF5E8BC7) — unchanged, already correct
        )
    )
}

@Composable
fun MenuItemRow(
    menuItem: MenuItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(CardWhite)
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = menuItem.icon,
            contentDescription = menuItem.title,
            modifier = Modifier.size(24.dp),
            tint = BlueText // was Color(0xFF404040)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = menuItem.title,
            fontSize = 16.sp,
            color = DarkText, // was Color(0xFF404040)
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = MediumGray
        )
    }

    HorizontalDivider(
        color = LightGray, // was Color(0xFFE5E5E5)
        thickness = 1.dp
    )
}

@Composable
fun DevelopedByFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BackgroundBlue,
                        LightGray
                    )
                )
            )
            .padding(vertical = 20.dp, horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Developed By",
                fontSize = 15.sp,
                color = Color(0xFF666666),
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Humble",
                fontSize = 28.sp,
                color = Color(0xFFFDB827), // Golden yellow
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "CODERS",
                fontSize = 26.sp,
                color = Color(0xFF5B9BD5), // original brand color
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}