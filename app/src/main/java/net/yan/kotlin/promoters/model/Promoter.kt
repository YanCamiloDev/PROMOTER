package net.yan.kotlin.promoters.model


import com.google.firebase.database.Exclude

data class Promoter(@Exclude var id: String= "", var email: String="", @Exclude var senha: String = "", var foto: String = "", var nome: String = "" )