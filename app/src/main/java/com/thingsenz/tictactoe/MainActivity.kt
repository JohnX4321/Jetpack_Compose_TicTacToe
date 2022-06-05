package com.thingsenz.tictactoe

import android.app.GameManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thingsenz.tictactoe.ui.TicTacToeModel
import com.thingsenz.tictactoe.ui.theme.Purple500
import com.thingsenz.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {

    private val gameModel by viewModels<TicTacToeModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(topBar = {
                    MainAppBar(singlePlayer = gameModel.singlePlayer) {
                        gameModel.updatePlayer(it)
                    }
                }) {
                    Surface(color = MaterialTheme.colors.background) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.SpaceAround,modifier = Modifier.fillMaxHeight()) {
                            Grid(game = gameModel.game, onClick = gameModel::play)
                            if (gameModel.isGameOver) {
                                Box {
                                   Text(text = "Game Over: ${gameModel.winner}",fontSize = 20.sp)
                                }
                            }
                            ResetBtn {
                                gameModel.reset()
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TicTacToeTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Grid(game = arrayListOf("X","O","X","O","X","","X","O","X"), onClick = {} )
            ResetBtn { }
        }
    }
}

@Composable
fun MainAppBar(singlePlayer: Boolean,onSwitch: (Boolean)->Unit) {
    val spState = remember {
        mutableStateOf(singlePlayer)
    }
    TopAppBar(title = { Text(text = "TicTacToe",color = Color.White)},
    actions = {
        Row(modifier = Modifier.padding(end = 16.dp)) {
            Text(text = if (spState.value)  "1 vs Comp" else "1 vs 1")
            Spacer(modifier = Modifier.width(16.dp))
            Switch(checked = spState.value, onCheckedChange = {
                spState.value=it
                onSwitch(it)
            })
        }
    })
}

@Composable
fun ResetBtn(onClick: ()->Unit) {
    Button(onClick = onClick,modifier = Modifier
        .padding(16.dp)
        .height(40.dp)) {
        Text(text = "Reset",style = TextStyle(textAlign = TextAlign.Center),modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun GridButton(text: String, onClick: () -> Unit) {
    Box(modifier = Modifier.padding(6.dp)) {
        TextButton(onClick = onClick,
        shape = MaterialTheme.shapes.medium,border = BorderStroke(2.dp, Purple500),
            enabled = text.isBlank(), colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
        ) {
            Text(text = text, style = TextStyle(textAlign = TextAlign.Center, fontSize = 35.sp,color = Color.White),
            modifier = Modifier
                .padding(16.dp)
                .size(42.dp)
                .fillMaxHeight())
        }
    }
}

@Composable
fun Grid(game: ArrayList<String>,onClick: (Int) -> Unit) {
    Column(verticalArrangement = Arrangement.SpaceEvenly) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            GridButton(text = game[0]) {
                onClick(0)
            }
            GridButton(text = game[1]) {
                onClick(1)
            }
            GridButton(text = game[2]) {
                onClick(2)
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            GridButton(text = game[3]) {
                onClick(3)
            }
            GridButton(text = game[4]) {
                onClick(4)
            }
            GridButton(text = game[5]) {
                onClick(5)
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            GridButton(text = game[6]) {
                onClick(6)
            }
            GridButton(text = game[7]) {
                onClick(7)
            }
            GridButton(text = game[8]) {
                onClick(8)
            }
        }
    }
}