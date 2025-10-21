package com.example.uinavegacion.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.uinavegacion.ui.theme.Dimens
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetWhite

@Composable
fun MoviPetButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.cornerRadius)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MoviPetOrange,
            contentColor = MoviPetWhite
        )
    ) {
        Text(text)
    }
}



//boton:
/*

 MoviPetButton(
    text ="",
    onClick = { /* tu acción */ },
    modifier = Modifier.fillMaxWidth()
)
*
* */