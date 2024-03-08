package com.example.vktask.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.vktask.common.composable.BasicButton
import com.example.vktask.common.ext.basicButton
import com.example.vktask.common.ext.fieldModifier

@Composable
fun HomeScreen(context: Context){
Column(
    modifier = Modifier.fieldModifier()
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
Text(text = "Привет! Меня зовут Павел, я студент мехмата ЮФУ 2 курса магистратуры и специализируюсь на разработке мобильных приложений. Одним из проектов, над которым я работал, является приложение, которое было тестовым заданием для собеседования на стажировку.\n" +
        "\n" +
        "Это приложение позволяет просматривать карточки товаров, данные для которых загружаются с сервера. Реализован поиск через бэкенд, сортировка товаров по категориям, а также возможность прокручивать изображения с помощью HorizontalPager. Для создания этого приложения я использовал Kotlin, Jetpack Compose, Hilt, Retrofit2, Okhttp3, Coil для загрузки изображений и kotlinx.coroutines для работы с корутинами. Приложение стабильно работает и обрабатывает ошибки, которые могут возникнуть при общении с сервером.\n" +
        "\n" +
        "Если ты хочешь посмотреть мой код или узнать больше о моих проектах, можешь посетить мой GitHub. Буду рад любому фидбеку и готов обсудить любые детали моего приложения. Спасибо за внимание!")
    BasicButton("Мой GitHub", Modifier.basicButton()) {
        val url = "https://github.com/pav3l-abramov"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }
}
}
