package net.yan.kotlin.promoters.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Cidade(
             var id: String="",
             val local: String="") {
}