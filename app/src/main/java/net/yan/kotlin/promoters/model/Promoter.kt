package net.yan.kotlin.promoters.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.google.firebase.database.Exclude

data class Promoter(@Exclude var id: String= "", var email: String="", @Exclude var senha: String = "", var foto: String = "", var nome: String = "" )