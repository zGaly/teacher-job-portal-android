package ipca.aulas.gappacademy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(
    searchTextState: State<String>,
    onSearchTextChange: (String) -> Unit,
    placeholder: String = "Pesquisar..."
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF3C6E58), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = searchTextState.value,
            onValueChange = onSearchTextChange,
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            decorationBox = { innerTextField ->
                Box {
                    if (searchTextState.value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TextStyle(color = Color.Gray, fontSize = 16.sp)
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}