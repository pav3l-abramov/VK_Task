# VK_Task
 
This is a test task for an internship at VK.
On the home screen you can see detailed information about the application and about me.
To create this application I used Kotlin, Jetpack Compose, Hilt, Retrofit2, Okhttp3, Coil for loading images and kotlinx.coroutines for working with coroutines.

Here are examples of how the application works.

A custom loader was programmed. The loader is launched when a request is sent to the server.

<img src="https://github.com/pav3l-abramov/VK_Task/blob/main/ScreenShots/1.jpg" width="250" />

The application receives a list of 20 products to the server; the names in the fields can be scrolled.

<img src="https://github.com/pav3l-abramov/VK_Task/blob/main/ScreenShots/2.jpg" width="250" />

You can go to different pages, on the first page you cannot go to the previous one, and on the last page (when an empty list arrives) you cannot go to the next one.

<img src="https://github.com/pav3l-abramov/VK_Task/blob/main/ScreenShots/3.jpg" width="250" />

You can select categories, we receive the list from the server, so the mobile application will always have up-to-date information.

<p float="left">
  <img src="https://github.com/pav3l-abramov/VK_Task/blob/main/ScreenShots/4.jpg" width="200" />
  <img src="https://github.com/pav3l-abramov/VK_Task/blob/main/ScreenShots/5.jpg" width="200" /> 
</p>

We receive data through the backend in the search bar, each product card is also clickable.

<img src="https://github.com/pav3l-abramov/VK_Task/blob/main/ScreenShots/6.jpg" width="250" />

You can go to a detailed description of each product. You can scroll pictures using HorizontalPager.

<img src="https://github.com/pav3l-abramov/VK_Task/blob/main/ScreenShots/7.jpg" width="250" />

The program is written in Jetpack Compose, so it was not very difficult to implement a dynamic dark theme.

<img src="https://github.com/pav3l-abramov/VK_Task/blob/main/ScreenShots/8.jpg" width="250" />
