import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun WeatherText(modifier: Modifier = Modifier,
                textAlign: TextAlign = TextAlign.Left,
                content: String = "",
                fontSize: TextUnit = 20.sp,
                fontWeight: FontWeight = FontWeight.Light){
    Text(modifier = modifier,
        textAlign = textAlign,
        text = content,
        fontSize = fontSize,
        fontWeight = fontWeight)
}