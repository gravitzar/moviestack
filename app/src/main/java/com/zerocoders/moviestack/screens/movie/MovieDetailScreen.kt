package com.zerocoders.moviestack.screens.movie

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.android.exoplayer2.MediaItem
import com.zerocoders.moviestack.R
import com.zerocoders.moviestack.ui.theme.Montserrat
import com.zerocoders.moviestack.ui.theme.robotoMediumTextStyle
import com.zerocoders.moviestack.ui.theme.robotoNormalTextStyle
import com.zerocoders.moviestack.utils.ASPECT_RATIO_16_9
import com.zerocoders.moviestack.utils.applyGradient
import com.zerocoders.moviestack.utils.roundToSingleFraction
import com.zerocoders.moviestack.utils.toRuntimeString
import com.zerocoders.moviestack.widgets.common.BoxWithElevation
import com.zerocoders.moviestack.widgets.common.ExpandableText
import com.zerocoders.moviestack.widgets.common.TitleText
import com.zerocoders.moviestack.widgets.common.VideoThumbnail
import com.zerocoders.moviestack.widgets.common.animatedHorizontalListItemsWithTitle
import com.zerocoders.showdiary.tmdb.Genre
import com.zerocoders.showdiary.tmdb.Movie
import kotlinx.datetime.LocalDate
import java.util.Locale
import kotlin.math.min

@Composable
internal fun MovieDetailScreenRoute(
    id: Int = -1,
    onTrailerVideoClicked: (videoId: String) -> Unit = {},
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        viewModel.getMovieDetail(id)
    }
    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    if (uiState.isError) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    if (uiState.isSuccess) {
        MovieDetailScreen(
            uiState = uiState,
            onBookMarkClicked = { viewModel.updateBookMark() },
            onFavouriteClicked = { viewModel.updateFavourite() },
            onTrailerVideoClicked = { onTrailerVideoClicked(it) }
        )
    }
}

@Composable
fun MovieBackDrop(uiState: MovieDetailScreenUiState) {
    when (uiState.trailerVideoState) {
        is TrailerState.Success -> {
            com.zerocoders.moviestack.widgets.video2.VideoPlayer(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ASPECT_RATIO_16_9),
                mediaItemToPlay = MediaItem.fromUri(uiState.trailerVideoLink.orEmpty())
            )
        }

        else -> {
            BoxWithElevation(
                color = Color.LightGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ASPECT_RATIO_16_9)
                    .applyGradient(),
            ) {
                val painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original${uiState.movie?.backdropPath}")
                val state = painter.state

                val transition by animateFloatAsState(
                    targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f, label = ""
                )
                Image(
                    painter = painter,
                    contentDescription = uiState.movie?.title.orEmpty(),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .alpha(min(1f, transition / .2f)),
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(transition) })
                )
            }
        }
    }
}

@Composable
fun MovieDetailScreen(
    uiState: MovieDetailScreenUiState,
    onBookMarkClicked: () -> Unit = {},
    onFavouriteClicked: () -> Unit = {},
    onTrailerVideoClicked: (videoId: String) -> Unit = {}
) {
    LazyColumn(
        state = rememberLazyListState()
    ) {
        item {
            MovieBackDrop(uiState = uiState)
        }
        item {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                val (rating, favourite, share, bookmark) = createRefs()
                Row(
                    modifier = Modifier
                        .constrainAs(rating) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = "star",
                        tint = Color(0xfffabc2b)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${uiState.movie?.voteAverage?.roundToSingleFraction()}",
                        style = robotoNormalTextStyle.copy(fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "|",
                        style = robotoNormalTextStyle.copy(fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${uiState.movie?.voteCount}",
                        style = robotoNormalTextStyle.copy(fontSize = 14.sp)
                    )
                }
                IconButton(modifier = Modifier
                    .constrainAs(favourite) {
                        end.linkTo(share.start)
                        top.linkTo(parent.top)
                        start.linkTo(share.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(end = 16.dp)
                    .size(24.dp),
                    onClick = { onFavouriteClicked() }) {
                    Icon(
                        imageVector = if (uiState.isFavourite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                        contentDescription = "Favorite",
                        tint = if (uiState.isFavourite) Color.Red else MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(modifier = Modifier
                    .constrainAs(share) {
                        end.linkTo(bookmark.start)
                        top.linkTo(parent.top)
                        start.linkTo(favourite.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(end = 16.dp)
                    .size(24.dp), onClick = { /*TODO*/ }) {
                    Icon(

                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(modifier = Modifier
                    .constrainAs(bookmark) {
                        start.linkTo(share.end)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(end = 16.dp)
                    .size(24.dp),
                    onClick = { onBookMarkClicked() }) {
                    Icon(
                        imageVector = if (uiState.isBookMarked) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,
                        contentDescription = "Bookmark",
                        tint = if (uiState.isBookMarked) Color(0xfffabc2b) else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        item {
            Text(
                text = uiState.movie?.title.orEmpty(),
                style = robotoNormalTextStyle.copy(fontFamily = Montserrat, fontSize = 24.sp),
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
        }
        item {
            Text(
                text = "${uiState.movie?.runtime?.toRuntimeString()} • ${uiState.movie?.genres?.joinToString { it.name }} • ${uiState.movie?.releaseDate?.year}",
                style = robotoNormalTextStyle.copy(fontSize = 14.sp),
                modifier = Modifier.padding(top = 2.dp, start = 8.dp, end = 8.dp)
            )
        }
        item {
            ExpandableText(
                modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp),
                text = uiState.movie?.overview.orEmpty(),
                style = robotoNormalTextStyle.copy(fontSize = 14.sp)
            )
        }
        animatedHorizontalListItemsWithTitle(
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top,
            listItems = uiState.movieCasts,
            title = {
                TitleText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    title = "Casts"
                )
            }
        ) { cast ->
            Column(
                modifier = Modifier
                    .width(84.dp)
                    .padding(bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w185${cast.profilePath}")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_profile_placeholder),
                    error = painterResource(R.drawable.ic_profile_placeholder),
                    contentDescription = cast.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = cast.name,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(IntrinsicSize.Max),
                    style = robotoMediumTextStyle
                )
                Text(
                    text = cast.character,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(IntrinsicSize.Max),
                    style = robotoNormalTextStyle.copy(fontSize = 10.sp)
                )
            }
        }
        uiState.movieVideos.groupBy { it.type }.forEach { (type, videoList) ->
            animatedHorizontalListItemsWithTitle(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top,
                listItems = videoList,
                title = {
                    TitleText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                        title = type?.name?.replace("_", " ")?.lowercase()?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }.orEmpty()
                    )
                }
            ) { video ->
                VideoThumbnail(video = video, onVideoClicked = { onTrailerVideoClicked(video.key) })
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun MovieDetailScreenPreview() {
    MovieDetailScreen(
        MovieDetailScreenUiState(
            movie = Movie(
                adult = false,
                backdropPath = "/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg",
                genres = listOf(
                    Genre(id = 28, name = "Action"),
                    Genre(id = 12, name = "Adventure"),
                    Genre(id = 878, name = "Science Fiction"),
                    Genre(id = 14, name = "Fantasy"),
                ),
                id = 436270,
                originalLanguage = "en",
                originalTitle = "Black Adam",
                overview = "Nearly 5,000 years after he was bestowed with the almighty powers of the Egyptian gods—and imprisoned just as quickly—Black Adam is freed from his earthly tomb, ready to unleash his unique form of justice on the modern world.",
                popularity = 1085.853f,
                posterPath = "/pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg",
                releaseDate = LocalDate.parse("2022-10-19"),
                title = "Black Adam",
                video = false,
                voteAverage = 7.113f,
                voteCount = 4651,
                runtime = 234
            ),
            isLoading = false,
            isSuccess = true,
            trailerVideoState = TrailerState.Loading
        )
    )
}