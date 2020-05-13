package net.yan.kotlin.promoters.model

import com.google.firebase.database.Exclude


class PromPontos{

    var id: String = ""
        @Exclude
        get() = field

    var fk_id_promoter: String = ""
        @Exclude
        get() = field

    var fk_id_pontos: String = ""
        get() = field
    var fk_id_cidade: String = ""
        get() = field
    var data: Long? = null
        get() = field
}