package com.example.tictactoe.view


import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.Constants
import com.example.tictactoe.Constants.COMING_FROM
import com.example.tictactoe.Constants.INTENT_EXTRA_HISTORY
import com.example.tictactoe.Constants.PLAYER_ID
import com.example.tictactoe.R
import com.example.tictactoe.model.Players
import com.example.tictactoe.ui.theme.darkPrimaryColor
import com.example.tictactoe.ui.theme.primaryColor
import com.example.tictactoe.viewmodel.PlayersListViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PlayersListPage(viewModel: PlayersListViewModel) {
    val playersList by viewModel.playersList.observeAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Game History", modifier = Modifier.padding(8.dp), fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.lemon)),
            )
            playersList?.let {
                LazyColumn(
                    content = {
                        itemsIndexed(it) { index: Int, item: Players ->
                            PlayersItem(
                                item = item,
                                onDelete = { viewModel.deletePlayers(item.id) },
                                onContinue = { openGame(item.id, context) },
                            )
                        }
                    }
                )
            } ?: Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "No items yet",
                fontSize = 16.sp
            )
        }
    }
}

fun openGame(id: Int, context: Context) {
    Log.d("TAGTAG", "playersPage | openGame: id: $id")
    val intent = Intent(context, GameActivity::class.java).apply {
        putExtra(PLAYER_ID, id)
        putExtra(COMING_FROM, INTENT_EXTRA_HISTORY)
    }
    context.startActivity(intent)
}

@Composable
fun PlayersItem(item: Players, onDelete: () -> Unit, onContinue: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        primaryColor,
                        darkPrimaryColor
                    )
                )
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            PlayersData(item)
        }
        Column {
            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
            IconButton(onClick = onContinue) {
                Icon(
                    Icons.Default.PlayCircleFilled,
                    contentDescription = "Continue",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

    }
}

@Composable
private fun PlayersData(item: Players) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
    ) {
        Text(
            text = "Last played:", fontSize = 12.sp, color = Color.White,
            modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
        )
        Text(
            text = SimpleDateFormat("HH:mm:aa, dd/mm", Locale.ENGLISH)
                .format(item.lastPlayed),
            fontSize = 12.sp, color = Color.LightGray,
        )

    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
    ) {
        Text(text = item.player1, fontSize = 20.sp, color = Color.White)
        Text(text = " vs ", fontSize = 20.sp, color = Color.White)
        Text(text = item.player2, fontSize = 20.sp, color = Color.White)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
    ) {
        Text(text = "Score: ", fontSize = 12.sp, color = Color.White)
        Text(text = item.player1Score, fontSize = 12.sp, color = Color.Yellow)
        Text(text = " - ", fontSize = 12.sp, color = Color.Yellow)
        Text(text = item.player2Score, fontSize = 12.sp, color = Color.Yellow)
        Text(
            text = "Games played: ", fontSize = 12.sp, color = Color.White,
            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
        )
        Text(text = item.gamesPlayed, fontSize = 12.sp, color = Color.Yellow)
    }
}





