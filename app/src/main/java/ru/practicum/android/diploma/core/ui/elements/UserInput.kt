package ru.practicum.android.diploma.core.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.YpBlack
import ru.practicum.android.diploma.core.ui.theme.YpLightGray
import ru.practicum.android.diploma.core.ui.theme.YsDispalyFamily

@Composable
fun UserInput(
    onValueChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChanged.invoke(it)
        },
        label = { if (text.isBlank()) Text(text = stringResource(id = R.string.search)) },
        maxLines = 1,
        textStyle = TextStyle(
            fontFamily = YsDispalyFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = YpLightGray,
            unfocusedContainerColor = YpLightGray,
            disabledContainerColor = YpLightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = YpBlack,
            unfocusedTextColor = YpBlack
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(id = if (text.isNotBlank()) R.drawable.ic_clear else R.drawable.ic_search),
                contentDescription = "clear text icon",
                modifier = Modifier
                    .clickable {
                        text = ""
                        onValueChanged("")
                    }
            )
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .onFocusChanged { focusState ->
                if (focusState.hasFocus && text.isBlank()) {
                    onValueChanged.invoke(text)
                }
            }
    )
}

