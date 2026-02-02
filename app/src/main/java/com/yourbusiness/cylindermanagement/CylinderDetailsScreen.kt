package com.yourbusiness.cylindermanagement



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbusiness.cylindermanagement.ui.theme.BackgroundBlue
import com.yourbusiness.cylindermanagement.ui.theme.CardWhite
import com.yourbusiness.cylindermanagement.ui.theme.DarkText
import com.yourbusiness.cylindermanagement.ui.theme.MediumGray
import com.yourbusiness.cylindermanagement.ui.theme.OxygenAccent
import com.yourbusiness.cylindermanagement.ui.theme.TextGray




data class CylinderDetails(
    val serialNo: String,
    val type: String,
    val batch: String,
    val status: String,
    val price: String
)

data class CurrentIssue(
    val customerName: String,
    val issueDate: String,
    val returnDate: String,
    val daysHeld: Int,
    val sellingPrice: String
)

data class HistoryItem(
    val customerName: String,
    val issueDate: String,
    val returnDate: String,
    val sellingPrice: String,
    val daysHeld: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CylinderDetailsScreen(
    serialNumber: String = "0021",  // Added parameter
    onBackClick: () -> Unit = {}     // Added callback
) {
    var searchQuery by remember { mutableStateOf("") }

    // In a real app, you would fetch cylinder details based on serialNumber
    // For now, we'll use mock data
    val cylinderDetails = CylinderDetails(
        serialNo = serialNumber,
        type = "Oxygen Cylinder",
        batch = "41435",
        status = "Issued",
        price = "₹ 600"
    )

    val currentIssue = CurrentIssue(
        customerName = "Akaal Hospital",
        issueDate = "24 Jan 2026",
        returnDate = "31 Jan 2026",
        daysHeld = 6,
        sellingPrice = "₹ 500"
    )

    val historyList = listOf(
        HistoryItem(
            customerName = "Ganeshgarhia Construction Pvt Ltd",
            issueDate = "12 Oct 2024",
            returnDate = "18 Oct 2024",
            sellingPrice = "₹ 450",
            daysHeld = 6
        ),
        HistoryItem(
            customerName = "Neeraj Duggal",
            issueDate = "15 Sep 2024",
            returnDate = "21 Sep 2024",
            sellingPrice = "₹ 500",
            daysHeld = 6
        ),
        HistoryItem(
            customerName = "Mehtab Enterprises",
            issueDate = "05 Aug 2024",
            returnDate = "12 Aug 2024",
            sellingPrice = "₹ 475",
            daysHeld = 7
        ),
        HistoryItem(
            customerName = "City Hospital",
            issueDate = "20 Jul 2024",
            returnDate = "27 Jul 2024",
            sellingPrice = "₹ 500",
            daysHeld = 7
        )
    )

    val filteredHistory = remember(searchQuery, historyList) {
        if (searchQuery.isEmpty()) {
            historyList
        } else {
            historyList.filter {
                it.customerName.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cylinder Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {  // Updated to use onBackClick
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OxygenAccent
                )
            )
        },
        containerColor = BackgroundBlue
    ) { paddingValues ->
        // ... rest of the code remains the same
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Cylinder Info Card
            CylinderInfoCard(cylinderDetails)

            Spacer(modifier = Modifier.height(16.dp))

            // Currently Issued To Card
            CurrentlyIssuedCard(currentIssue)

            Spacer(modifier = Modifier.height(24.dp))

            // Cylinder History Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Previous Customers",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Text(
                    text = "${filteredHistory.size} records",
                    fontSize = 14.sp,
                    color = MediumGray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            //Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Search customers...",
                        color = MediumGray,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MediumGray
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = MediumGray
                            )
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = CardWhite,
                    focusedContainerColor = CardWhite,
                    unfocusedBorderColor = MediumGray.copy(alpha = 0.3f),
                    focusedBorderColor = OxygenAccent
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // History Cards - Vertical LazyColumn
            if (filteredHistory.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No customers found",
                        color = MediumGray,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 600.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    userScrollEnabled = false
                ) {
                    items(filteredHistory) { historyItem ->
                        HistoryCard(historyItem)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CylinderInfoCard(details: CylinderDetails) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Top Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // O2 Icon - Circular
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
                            text = "O₂",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Cylinder Info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Serial No - ${details.serialNo}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = details.type,
                        fontSize = 14.sp,
                        color = DarkText.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Diameter: 7 cm",
                        fontSize = 12.sp,
                        color = TextGray
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = OxygenAccent,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MediumGray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Bottom Section with 4 columns
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Batch Box
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(BackgroundBlue, RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Batch",
                            fontSize = 11.sp,
                            color = TextGray
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = details.batch,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkText
                        )
                    }
                }

                // Status Box
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(BackgroundBlue, RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Status",
                            fontSize = 11.sp,
                            color = TextGray
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = details.status,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkText
                        )
                    }
                }

                // Price Box
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(BackgroundBlue, RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Price",
                            fontSize = 11.sp,
                            color = TextGray
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = details.price,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkText
                        )
                    }
                }

                // Remarks Box
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(BackgroundBlue, RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Remarks",
                            fontSize = 11.sp,
                            color = TextGray
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Full",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkText
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentlyIssuedCard(issue: CurrentIssue) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Currently Issued To",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = OxygenAccent
            )

            Spacer(modifier = Modifier.height(14.dp))

            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Name:",
                        fontSize = 14.sp,
                        color = DarkText.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.width(100.dp)
                    )
                    Text(
                        text = issue.customerName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Issue Date:",
                        fontSize = 14.sp,
                        color = DarkText.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.width(100.dp)
                    )
                    Text(
                        text = issue.issueDate,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Days Held:",
                        fontSize = 14.sp,
                        color = DarkText.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.width(100.dp)
                    )
                    Text(
                        text = "${issue.daysHeld} days",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Return Date:",
                        fontSize = 14.sp,
                        color = DarkText.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.width(100.dp)
                    )
                    Text(
                        text = issue.returnDate,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Selling Price:",
                        fontSize = 14.sp,
                        color = DarkText.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.width(100.dp)
                    )
                    Text(
                        text = issue.sellingPrice,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryCard(item: HistoryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = item.customerName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Row 1
                Row(modifier = Modifier.fillMaxWidth()) {

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Issue: ${item.issueDate}",
                            fontSize = 13.sp,
                            color = TextGray
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Days Held: ${item.daysHeld}",
                            fontSize = 13.sp,
                            color = TextGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Row 2
                Row(modifier = Modifier.fillMaxWidth()) {

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Return: ${item.returnDate}",
                            fontSize = 13.sp,
                            color = TextGray
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Selling Price: ${item.sellingPrice}",
                            fontSize = 13.sp,
                            color = TextGray
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "View Details",
                tint = MediumGray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}