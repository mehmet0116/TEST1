package com.example.puzzlegame

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PuzzleScreen(viewModel: GameViewModel = viewModel()) {
    val puzzle by viewModel.puzzle.observeAsState(initial = emptyList())
    val moves by viewModel.moves.observeAsState(initial = 0)
    val isSolved by viewModel.isSolved.observeAsState(initial = false)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "8 Puzzle Oyunu",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        Text(
            text = "Hareket: $moves",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (isSolved) {
            Text(
                text = "Tebrikler! Bulmacayı çözdünüz!",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        
        // 3x3 grid
        Column(
            modifier = Modifier
                .size(300.dp)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (row in 0 until 3) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    for (col in 0 until 3) {
                        val index = row * 3 + col
                        val tileValue = puzzle.getOrNull(index) ?: 0
                        
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (tileValue == 9) Color.Transparent
                                    else MaterialTheme.colorScheme.primaryContainer
                                )
                                .border(
                                    2.dp,
                                    if (tileValue == 9) Color.Transparent
                                    else MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable(enabled = !isSolved) {
                                    viewModel.moveTile(index)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (tileValue != 9) {
                                Text(
                                    text = tileValue.toString(),
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }
        
        Button(
            onClick = { viewModel.resetGame() },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 32.dp)
        ) {
            Text(text = "Yeni Oyun", fontSize = 18.sp)
        }
        
        Text(
            text = "Nasıl Oynanır: Boş kutunun yanındaki kutulara tıklayarak sayıları kaydırın. " +
                    "Sayıları 1'den 8'e kadar sıralayın.",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        )
    }
}