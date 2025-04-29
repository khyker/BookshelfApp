package com.example.bookshelfapp.ui


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelfapp.R
import com.example.bookshelfapp.ui.screens.BookViewModel
import com.example.bookshelfapp.ui.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val bookshelfViewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar( title = { Text(text = stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) },
        bottomBar = { AppSearchBar(getBookInformation = {_: String -> bookshelfViewModel.getBooks()})}
    ) {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ){
            HomeScreen(
                retryAction = { bookshelfViewModel.getBooks() },
                bookshelfUiState = bookshelfViewModel.bookshelfUiState,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun AppSearchBar(getBookInformation: (String) -> Unit, modifier: Modifier = Modifier) {
    var topic: String by remember { mutableStateOf("")}
    BottomAppBar(modifier = modifier) {
        Row(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium),
            top = dimensionResource(id = R.dimen.padding_small))) {
            TextField(
                value = topic,
                placeholder = {Text(text = stringResource(R.string.searchbar)) },
                onValueChange = { newTopic -> topic = newTopic},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "searchIcon"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.padding_medium))
                    .weight(2.5f)
            )
            Button(
                onClick = {
                    val query = convertStringToQuery(topic)
                    if (!query.isNullOrBlank()) {
                        getBookInformation(query)
                    }
                },
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_small),
                        end = dimensionResource(id = R.dimen.padding_small)
                    )
                    .weight(1f),
            ) {
                Text(text = stringResource(R.string.search))
            }
        }
    }
}

fun convertStringToQuery(text: String?): String? {
    return text?.split(" ")?.joinToString { "+" }
}

fun parseUrl(httpUrl: String?) : String? = httpUrl?.replace("http://", "https://")