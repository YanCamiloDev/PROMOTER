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














/*

<androidx.appcompat.widget.SearchView
                    android:id="@+id/search_prom"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:textCursorDrawable="@null"
                    app:iconifiedByDefault="false"
                    app:queryBackground="@null"/>
                <androidx.recyclerview.widget.RecyclerView
                    android:id="@+id/rec"
                    app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"/>





        selectionTracker = SelectionTracker.Builder<Long>(
            "mySelection",
            binding.rec,
            AdapterVenda.KeyProvider(adapter),
            AdapterVenda.DetailsLookup(binding.rec),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            object : SelectionPredicate<Long>() {
                override fun canSetStateForKey(
                    key: Long,
                    nextState: Boolean
                ): Boolean {
                    return true
                }
                override fun canSetStateAtPosition(
                    position: Int,
                    nextState: Boolean
                ): Boolean {
                    return true
                }
                override fun canSelectMultiple(): Boolean {
                    return false // Set to false to allow single selecting
                }
            }
        ).build()
        adapter.setSelection(selectionTracker)

        selectionTracker!!.addObserver(
            object : SelectionTracker.SelectionObserver<Long?>() {
                override fun onItemStateChanged(key: Long, selected: Boolean) {
                    super.onItemStateChanged(key, selected)
                    if (selected){
                        selectPromotor = lista?.get(key.toInt())
                        binding.promotor = selectPromotor
                        binding.floating.show()
                    }else{
                        binding.promotor = null
                        selectPromotor = null
                        binding.floating.hide()
                    }
                }
                override fun onSelectionChanged() {

                }
            })



 */