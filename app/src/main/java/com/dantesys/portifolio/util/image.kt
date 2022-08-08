package com.dantesys.portifolio.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dantesys.portifolio.R
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class Image {
    companion object {
        fun share(context: Context,view: View,texto: String){
            val bm = getBitmapFromView(view)
            bm?.let {
                saveStorage(context,it,texto)
            }
        }

        private fun saveStorage(context: Context,bitmap: Bitmap,texto:String) {
            val fotonome = "${System.currentTimeMillis()}.png"
            var fos:FileOutputStream?=null
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
                context.contentResolver?.also {resolver->
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME,fotonome)
                        put(MediaStore.MediaColumns.MIME_TYPE,"image/png")
                        put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES)
                    }
                    val imgUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)
                    fos = imgUri?.let {
                        shareIntent(context,imgUri,texto)
                        resolver.openOutputStream(it)
                    } as FileOutputStream?
                }
            }else{
                val fotosDIR = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val foto = File(fotosDIR,fotonome)
                shareIntent(context,Uri.fromFile(foto),texto)
                fos = FileOutputStream(foto)
            }
            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.PNG,100,it)
                Toast.makeText(context,"Imagem gerada com sucesso",Toast.LENGTH_SHORT)
            }
        }
        private fun shareIntent(context: Context,imgUri: Uri,texto:String){
            val shareIntent:Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM,imgUri)
                putExtra(Intent.EXTRA_TEXT, texto)
                type = "image/png"
            }
            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    context.resources.getText(R.string.send_to)
                )
            )
        }
        private fun getBitmapFromView(view: View): Bitmap? {
            var returnedBitmap:Bitmap? = null
            try{
                returnedBitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(returnedBitmap)
                view.draw(canvas)

            }catch (e:Exception){
                Log.e("IMAGE","FALHA AO TIRAR PRINT\n"+e.message)
            }
            return returnedBitmap
        }
    }
}