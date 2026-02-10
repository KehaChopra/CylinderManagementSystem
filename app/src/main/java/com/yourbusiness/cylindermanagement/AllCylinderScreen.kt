package com.yourbusiness.cylindermanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbusiness.cylindermanagement.ui.theme.BackgroundBlue
import com.yourbusiness.cylindermanagement.ui.theme.BlueText
import com.yourbusiness.cylindermanagement.ui.theme.CardWhite
import com.yourbusiness.cylindermanagement.ui.theme.DarkText
import com.yourbusiness.cylindermanagement.ui.theme.MediumGray
import com.yourbusiness.cylindermanagement.ui.theme.OxygenAccent
import com.yourbusiness.cylindermanagement.ui.theme.SelectedBlue
import com.yourbusiness.cylindermanagement.ui.theme.TextGray

// Data class
data class CylinderDetailsModern(
    val serialNumber: String,
    val batchNumber: String,
    val gasType: String,
    val gasSymbol: String,
    val volumeType: String,
    val status: String,
    val remarks: String,
    val gasColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllCylindersScreen(
    onBackClick: () -> Unit = {},
    onCylinderClick: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val cylinderList = listOf(
        CylinderDetailsModern(
            serialNumber = "0021",
            batchNumber = "41435",
            gasType = "Oxygen",
            gasSymbol = "O₂",
            volumeType = "7 cm",
            status = "Issued",
            remarks = "full",
            gasColor = OxygenAccent
        ),
        CylinderDetailsModern(
            serialNumber = "0024",
            batchNumber = "41378",
            gasType = "Oxygen",
            gasSymbol = "O₂",
            volumeType = "7 cm",
            status = "Empty",
            remarks = "",
            gasColor = OxygenAccent
        ),
        CylinderDetailsModern(
            serialNumber = "0003",
            batchNumber = "41396",
            gasType = "Oxygen",
            gasSymbol = "O₂",
            volumeType = "7 cm",
            status = "Issued",
            remarks = "",
            gasColor = OxygenAccent
        )
    )

    val filteredCylinders = cylinderList.filter {
        (selectedFilter == "All" || it.status == selectedFilter) &&
                (it.serialNumber.contains(searchQuery, ignoreCase = true) ||
                        it.batchNumber.contains(searchQuery, ignoreCase = true))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "All Cylinder Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OxygenAccent,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundBlue)
                .padding(paddingValues)
        ) {
            // Search bar
            AllCylindersSearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it }
            )

            // Filter chips
            FilterChipRow(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Cylinder list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredCylinders) { cylinder ->
                    CylinderCard(
                        cylinder = cylinder,
                        onClick = { onCylinderClick(cylinder.serialNumber) }
                    )
                }
            }
        }
    }
}

@Composable
fun AllCylindersSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        placeholder = {
            Text(
                text = "Search by Serial Number",
                color = TextGray
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = SelectedBlue
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = MediumGray
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CardWhite,
            unfocusedContainerColor = CardWhite,
            focusedBorderColor = SelectedBlue,
            unfocusedBorderColor = MediumGray,
            cursorColor = SelectedBlue
        ),
        shape = RoundedCornerShape(16.dp),
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipRow(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    val filters = listOf("All", "Issued", "Empty", "Full")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = { Text(filter) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SelectedBlue,
                    selectedLabelColor = Color.White,
                    containerColor = CardWhite,
                    labelColor = TextGray
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selectedFilter == filter,
                    borderColor = if (selectedFilter == filter) SelectedBlue else MediumGray,
                    selectedBorderColor = SelectedBlue
                )
            )
        }
    }
}

/*@Composable
fun SummaryCards(cylinderList: List<CylinderDetailsModern>) {
    val issuedCount = cylinderList.count { it.status == "Issued" }
    val emptyCount = cylinderList.count { it.status == "Empty" }
    val fullCount = cylinderList.count { it.status == "Full" }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MiniStatCard(
            title = "Issued",
            count = issuedCount,
            color = BlueText,
            modifier = Modifier.weight(1f)
        )
        MiniStatCard(
            title = "Empty",
            count = emptyCount,
            color = BlueText,
            modifier = Modifier.weight(1f)
        )
        MiniStatCard(
            title = "Full",
            count = fullCount,
            color = BlueText,
            modifier = Modifier.weight(1f)
        )
    }
}*/

/*@Composable
fun MiniStatCard(
    title: String,
    count: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = CardWhite
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                fontSize = 11.sp,
                color = TextGray
            )
        }
    }
}*/

@Composable
fun CylinderCard(
    cylinder: CylinderDetailsModern,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardWhite
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with gas icon and status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Gas icon circle
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(OxygenAccent.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(OxygenAccent),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cylinder.gasSymbol,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = cylinder.gasType,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )
                        Text(
                            text = "Serial: ${cylinder.serialNumber}",
                            fontSize = 14.sp,
                            color = TextGray
                        )
                    }
                }

                StatusChip(status = cylinder.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Divider
            HorizontalDivider(
                thickness = 1.dp,
                color = MediumGray.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Details grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailColumn(
                    label = "Batch",
                    value = cylinder.batchNumber,
                    modifier = Modifier.weight(1f)
                )
                DetailColumn(
                    label = "Volume",
                    value = cylinder.volumeType,
                    modifier = Modifier.weight(1f)
                )
            }

            if (cylinder.remarks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Remarks: ${cylinder.remarks}",
                    fontSize = 13.sp,
                    color = TextGray,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun DetailColumn(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextGray,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = BlueText,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun StatusChip(status: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = CardWhite,
        shadowElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, MediumGray.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = status,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
        }
    }
}