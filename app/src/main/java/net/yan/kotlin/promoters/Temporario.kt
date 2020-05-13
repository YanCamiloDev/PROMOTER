package net.yan.kotlin.promoters

/*



        val lista = initializeListPopupMenu(binding.clientes)

    @SuppressLint("ResourceType")
    private fun initializeListPopupMenu(v: View): ListPopupWindow? {
        val listPopupWindow = context?.let {
            ListPopupWindow(
                it,
                null,
                R.attr.listPopupWindowStyle
            )
        }
        val adapter = context?.let {
            ArrayAdapter(
                it,
                R.layout.chip,
                resources.getStringArray(R.array.promotores)
            )
        }
        listPopupWindow?.setAdapter(adapter)
        listPopupWindow?.anchorView = v
        listPopupWindow?.setOnItemClickListener { parent, view, position, id ->

            listPopupWindow.dismiss()
        }
        return listPopupWindow
    }


         val chip = Chip(binding.scrollGroup.context)
            chip.text = text
            chip.isCheckable = true
            chip.isClickable = true

            binding.scrollGroup.addView(chip)

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