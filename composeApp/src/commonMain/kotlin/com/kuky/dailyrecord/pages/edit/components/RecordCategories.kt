package com.kuky.dailyrecord.pages.edit.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuky.dailyrecord.model.DBMainCategoryEntity
import com.kuky.dailyrecord.model.DBSubCategoryEntity

@Composable
fun RecordCategories(
    mainCategories: List<DBMainCategoryEntity>,
    subCategories: List<DBSubCategoryEntity>,
    selMainIndex: Int,
    selSubIndex: Int,
    onMainClick: ((Int) -> Unit)? = null,
    onSubClick: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {

    Column(modifier) {
        LazyRow(Modifier.padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            items(mainCategories.size, key = { mainCategories[it].id!! }) {
                val category = mainCategories[it]
                Text(
                    category.categoryName,
                    fontSize = if (selMainIndex == it) 16.sp else 14.sp,
                    color = if (selMainIndex == it) MaterialTheme.colors.primary else Color.Unspecified,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        .clickable(interactionSource = MutableInteractionSource(), indication = null, onClick = { onMainClick?.invoke(it) })
                )
            }

            item {
                IconButton({}) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null, tint = MaterialTheme.colors.onBackground,
                    )
                }
            }
        }

        LazyVerticalGrid(GridCells.Adaptive(160.dp)) {
            items(subCategories.size, key = { subCategories[it].id!! }) {
                val category = subCategories[it]
                Box(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        .border(
                            1.dp,
                            if (selSubIndex == it) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,
                            RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        category.subCategoryName,
                        fontSize = 14.sp,
                        color = if (selSubIndex == it) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                            .clickable(interactionSource = MutableInteractionSource(), indication = null, onClick = { onSubClick?.invoke(it) }),
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colors.onBackground,
                            RoundedCornerShape(8.dp)
                        ).clickable(interactionSource = MutableInteractionSource(), indication = null, onClick = { }),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null, tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}