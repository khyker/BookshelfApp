package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.BookList
import com.example.bookshelfapp.ui.parseUrl
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

@Composable
fun HomeScreen(
    retryAction: () -> Unit,
    bookshelfUiState: BookshelfUiState,
    modifier: Modifier = Modifier,
) {
    when (bookshelfUiState) {
        is BookshelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BookshelfUiState.Success -> BookshelfScreen(books = bookshelfUiState.books)
        is BookshelfUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

 @Composable
 fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
 }

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.failed_to_load), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun BookshelfScreen(
    books: List<Book>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding
    ) {
        items(items = books) { book ->
            BookCard(book = book)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(book: Book, modifier: Modifier = Modifier) {
    Card(modifier = modifier,
        shape = RoundedCornerShape(0.dp)

    ) {
        Box( modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(parseUrl(book.imageLinks?.thumbnail ?: ""))
                    .crossfade(true)
                    .build(), contentDescription = null, contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.no_cover),
                placeholder = painterResource(id = R.drawable.no_cover),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = book.description,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    BookshelfAppTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview(){
    BookshelfAppTheme {
        ErrorScreen({})
    }
}



