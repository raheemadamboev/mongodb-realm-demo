package xyz.teamgravity.mongodbrealmdemo.data.local.school.entity

import io.realm.kotlin.types.EmbeddedRealmObject

class AddressEntity : EmbeddedRealmObject {
    var fullName: String = ""
    var street: String = ""
    var houseNumber: Int = 0
    var zip: Int = 0
    var city: String = ""
    var teacher: TeacherEntity? = null
}