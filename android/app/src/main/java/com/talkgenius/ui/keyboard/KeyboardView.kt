package com.talkgenius.ui.keyboard

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkgenius.data.model.ToneStyle

/**
 * Main keyboard view component implementing the LoveAssist design.
 *
 * Features:
 * - Paste button for clipboard access
 * - 9 tone style buttons
 * - Clear and Send buttons
 * - Pink/coral color scheme
 */
@Composable
fun KeyboardView(
    onPasteClick: () -> Unit,
    onToneSelected: (ToneStyle) -> Unit,
    onClearClick: () -> Unit,
    onSendClick: () -> Unit,
    inputText: String,
    onInputChange: (String) -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    // Color scheme matching LoveAssist design
    val primaryPink = Color(0xFFFF6B9D)
    val lightPink = Color(0xFFFFF0F5)
    val textGray = Color(0xFF666666)
    val backgroundColor = Color(0xFFFFFBFE)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(12.dp)
    ) {
        // Header with branding
        HeaderSection()

        Spacer(modifier = Modifier.height(16.dp))

        // Input field with paste button
        InputSection(
            text = inputText,
            onTextChange = onInputChange,
            onPasteClick = onPasteClick,
            primaryPink = primaryPink,
            lightPink = lightPink
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tone style buttons grid
        ToneStyleGrid(
            onToneSelected = onToneSelected,
            primaryPink = primaryPink
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Action buttons (Clear & Send)
        ActionButtons(
            onClearClick = onClearClick,
            onSendClick = onSendClick,
            isLoading = isLoading,
            primaryPink = primaryPink
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Bottom toolbar
        BottomToolbar(primaryPink = primaryPink)
    }
}

@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "LoveAssist",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF6B9D)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Love Icon",
            tint = Color(0xFFFF6B9D),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "30%",
            fontSize = 14.sp,
            color = Color(0xFFFF6B9D)
        )
    }
}

@Composable
private fun InputSection(
    text: String,
    onTextChange: (String) -> Unit,
    onPasteClick: () -> Unit,
    primaryPink: Color,
    lightPink: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(lightPink)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    text = "點擊此處粘貼 TA 的對話",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = onPasteClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryPink
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = "粘貼",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun ToneStyleGrid(
    onToneSelected: (ToneStyle) -> Unit,
    primaryPink: Color
) {
    val toneStyles = listOf(
        ToneStyleButton("溫柔", "💕", ToneStyle.Gentle),
        ToneStyleButton("高情商", "🌟", ToneStyle.High_EQ),
        ToneStyleButton("幽默", "😄", ToneStyle.Humorous),
        ToneStyleButton("可愛", "🥰", ToneStyle.Cute),
        ToneStyleButton("撤嬌", "😘", ToneStyle.Romantic),
        ToneStyleButton("智能回復", "🤖", ToneStyle.Professional),
        ToneStyleButton("曖味拉扯", "💋", ToneStyle.Flirty),
        ToneStyleButton("林黛玉式", "🌸", ToneStyle.Gentle),
        ToneStyleButton("甄嬛文學", "👑", ToneStyle.Professional)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.height(160.dp)
    ) {
        items(toneStyles) { style ->
            ToneButton(
                label = style.label,
                emoji = style.emoji,
                onClick = { onToneSelected(style.toneStyle) },
                primaryPink = primaryPink
            )
        }
    }
}

@Composable
private fun ToneButton(
    label: String,
    emoji: String,
    onClick: () -> Unit,
    primaryPink: Color
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = emoji,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color(0xFF666666),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
private fun ActionButtons(
    onClearClick: () -> Unit,
    onSendClick: () -> Unit,
    isLoading: Boolean,
    primaryPink: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Clear button
        OutlinedButton(
            onClick = onClearClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = primaryPink
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, primaryPink)
        ) {
            Text(
                text = "清空",
                fontSize = 14.sp
            )
        }

        // Send button
        Button(
            onClick = onSendClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryPink
            ),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "發送",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun BottomToolbar(primaryPink: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { /* Language selector */ }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "中文",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Language",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        IconButton(
            onClick = { /* Voice input */ }
        ) {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Voice input",
                tint = primaryPink
            )
        }
    }
}

/**
 * Data class for tone style button configuration
 */
private data class ToneStyleButton(
    val label: String,
    val emoji: String,
    val toneStyle: ToneStyle
)
