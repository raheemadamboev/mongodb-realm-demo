package xyz.teamgravity.mongodbrealmdemo.data.local.school.database

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import xyz.teamgravity.mongodbrealmdemo.data.local.school.entity.AddressEntity
import xyz.teamgravity.mongodbrealmdemo.data.local.school.entity.CourseEntity
import xyz.teamgravity.mongodbrealmdemo.data.local.school.entity.StudentEntity
import xyz.teamgravity.mongodbrealmdemo.data.local.school.entity.TeacherEntity

class SchoolDatabase {

    private var database: Realm? = null

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun initialize() {
        database = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    AddressEntity::class,
                    TeacherEntity::class,
                    CourseEntity::class,
                    StudentEntity::class
                )
            )
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    suspend fun insertTeachers(teachers: List<TeacherEntity>) {
        withContext(Dispatchers.IO) {
            database?.write {
                teachers.forEach { teacher ->
                    copyToRealm(
                        instance = teacher,
                        updatePolicy = UpdatePolicy.ALL
                    )
                }
            }
        }
    }

    suspend fun insertCourses(courses: List<CourseEntity>) {
        withContext(Dispatchers.IO) {
            database?.write {
                courses.forEach { course ->
                    copyToRealm(
                        instance = course,
                        updatePolicy = UpdatePolicy.ALL
                    )
                }
            }
        }
    }

    suspend fun insertStudents(students: List<StudentEntity>) {
        withContext(Dispatchers.IO) {
            database?.write {
                students.forEach { student ->
                    copyToRealm(
                        instance = student,
                        updatePolicy = UpdatePolicy.ALL
                    )
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteCourse(course: CourseEntity) {
        withContext(Dispatchers.IO) {
            database?.write {
                val latestCourse = findLatest(course) ?: return@write
                delete(latestCourse)
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////

    fun getCourses(): Flow<List<CourseEntity>> {
        return database?.query<CourseEntity>()
            ?.asFlow()
            ?.map { it.list } ?: emptyFlow()
    }
}