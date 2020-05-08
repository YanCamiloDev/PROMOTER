package net.yan.kotlin.promoters

/*





    fun gravarFoto(bitmap: Bitmap, id: String): String {
        var uri = ""
        uiScope.launch {
            try{
                val storageRef = dataSource.storage?.child("imagens")?.child("perfil")?.child(
                    "$id.jpeg"
                )
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
                val data = baos.toByteArray()

                var uploadTask = storageRef?.putBytes(data)
                uploadTask?.addOnFailureListener {
                    Log.i("URI", it.message)
                }?.addOnSuccessListener {
                    uri = it.metadata!!.toString()
                }
            }catch (e: FirebaseException){
                Log.i("Exception", "UPLOAD FOTO "+ e.message)
            }
        }
        Log.i("URI","KKKKKKKKKKKKKKK "+ uri)
        return uri
    }

 */