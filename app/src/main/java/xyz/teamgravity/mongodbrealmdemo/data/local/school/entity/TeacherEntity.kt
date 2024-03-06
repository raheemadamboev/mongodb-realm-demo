package xyz.teamgravity.mongodbrealmdemo.data.local.school.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class TeacherEntity : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var address: AddressEntity? = null
    var courses: RealmList<CourseEntity> = realmListOf()
}