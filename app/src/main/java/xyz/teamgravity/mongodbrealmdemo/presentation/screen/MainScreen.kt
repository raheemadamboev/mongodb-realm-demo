package xyz.teamgravity.mongodbrealmdemo.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.teamgravity.mongodbrealmdemo.R

@Composable
fun MainScreen(
    viewmodel: MainViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewmodel::onPopulate
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = viewmodel.courses,
                    key = { it.id.toHexString() }
                ) { course ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewmodel.onCourseShow(course)
                            }
                            .padding(16.dp)
                    ) {
                        Text(
                            text = course.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = stringResource(id = R.string.held_by, course.teacher?.address?.fullName ?: ""),
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.enrolled_students, course.enrolledStudents.joinToString { it.name }),
                            fontSize = 10.sp
                        )
                    }
                }
            }
            viewmodel.currentCourse?.let { course ->
                Dialog(
                    onDismissRequest = viewmodel::onCourseHide
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(
                                min = 200.dp,
                                max = 300.dp
                            )
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        course.teacher?.address?.let { address ->
                            Text(
                                text = address.fullName
                            )
                            Text(
                                text = "${address.street} ${address.houseNumber}"
                            )
                            Text(
                                text = "${address.zip} ${address.city}"
                            )
                        }
                        Button(
                            onClick = viewmodel::onDelete,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.delete)
                            )
                        }
                    }
                }
            }
        }
    }
}