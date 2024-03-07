package xyz.teamgravity.mongodbrealmdemo.presentation.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.ext.realmListOf
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.teamgravity.mongodbrealmdemo.data.local.school.database.SchoolDatabase
import xyz.teamgravity.mongodbrealmdemo.data.local.school.entity.AddressEntity
import xyz.teamgravity.mongodbrealmdemo.data.local.school.entity.CourseEntity
import xyz.teamgravity.mongodbrealmdemo.data.local.school.entity.StudentEntity
import xyz.teamgravity.mongodbrealmdemo.data.local.school.entity.TeacherEntity
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val database: SchoolDatabase
) : ViewModel() {

    var courses: ImmutableList<CourseEntity> by mutableStateOf(persistentListOf())
        private set

    var currentCourse: CourseEntity? by mutableStateOf(null)
        private set

    init {
        observe()
    }

    private fun observe() {
        observeCourses()
    }

    private fun observeCourses() {
        viewModelScope.launch {
            database.getCourses().collectLatest { courses ->
                this@MainViewModel.courses = courses.toImmutableList()
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onPopulate() {
        viewModelScope.launch {
            val teacher1 = TeacherEntity().apply {
                address = AddressEntity()
                address?.fullName = "Raheem Adam"
                address?.street = "Long Beach"
                address?.houseNumber = 777
                address?.zip = 777777
                address?.city = "LA"
            }
            val teacher2 = TeacherEntity().apply {
                address = AddressEntity()
                address?.fullName = "Nargeeza Adam"
                address?.street = "Short Beach"
                address?.houseNumber = 666
                address?.zip = 6666666
                address?.city = "NY"
            }

            val course1 = CourseEntity().apply {
                name = "Yappology"
                teacher = teacher1
            }
            val course2 = CourseEntity().apply {
                name = "Moneylogy"
                teacher = teacher1
            }
            val course3 = CourseEntity().apply {
                name = "Healthlogy"
                teacher = teacher2
            }

            teacher1.courses = realmListOf(course1, course2)
            teacher2.courses = realmListOf(course3)

            val student1 = StudentEntity().apply {
                name = "Alligator"
            }
            val student2 = StudentEntity().apply {
                name = "Elephant"
            }

            course1.enrolledStudents = realmListOf(student1)
            course2.enrolledStudents = realmListOf(student2)
            course3.enrolledStudents = realmListOf(student1, student2)

            database.insertTeachers(listOf(teacher1, teacher2))
            database.insertCourses(listOf(course1, course2, course3))
            database.insertStudents(listOf(student1, student2))
        }
    }

    fun onCourseShow(course: CourseEntity) {
        currentCourse = course
    }

    fun onCourseHide() {
        currentCourse = null
    }

    fun onDelete() {
        val course = currentCourse ?: return
        viewModelScope.launch {
            database.deleteCourse(course)
            currentCourse = null
        }
    }
}