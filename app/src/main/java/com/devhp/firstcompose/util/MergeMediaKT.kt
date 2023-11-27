import android.util.Log
import java.io.File

//package com.devhp.firstcompose.util
//
//import android.app.Activity
//import android.content.Intent
//import android.net.Uri
//import java.io.File
//import java.io.FileOutputStream
//import java.io.IOException
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//class MergeMediaKT {
//
//    private val PICK_VIDEO_REQUEST = 1
//    private val MAX_SELECTED_VIDEOS = 2
//    private val MIN_SELECTED_VIDEOS = 2
//
//    private fun openVideoPicker() {
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        intent.type = "video/*"
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//
////        startActivityForResult(intent, PICK_VIDEO_REQUEST)
//    }
//
////    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////        super.onActivityResult(requestCode, resultCode, data)
////
////        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK) {
////            data?.let { handleSelectedVideos(it) }
////        }
////    }
//
////    private fun getPathFromUri(uri: Uri): String? {
////
////        val contentResolver = applicationContext.contentResolver
////        var path: String? = null
////        contentResolver.openInputStream(uri)?.use { inputStream ->
////            val data = inputStream.readBytes()
////            // Create a new file in the app's cache directory
////
////            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
////            val fileName = "myFile_$timeStamp.mp4"
////            val file = File(applicationContext.cacheDir, fileName)
////            FileOutputStream(file).use { outputStream ->
////                // Write the data to the file
////                outputStream.write(data)
////            }
////            // Get the path of the file
////            path = file.absolutePath
////        }
////        return path
////    }
////    private fun handleSelectedVideos(intent: Intent) {
////        val selectedVideos: ArrayList<String> = ArrayList()
////
////
////        if (intent.clipData != null) {
////            val clipData = intent.clipData
////            for (i in 0 until clipData!!.itemCount) {
////                val uri = clipData.getItemAt(i).uri
////                val videoPath = getPathFromUri(uri)
////                videoPath?.let { selectedVideos.add(it) }
////            }
////        } else if (intent.data != null) {
////            val uri = intent.data
////            val videoPath = uri?.let { getPathFromUri(it) }
////            videoPath?.let { selectedVideos.add(it) }
////        }
////
////        if (selectedVideos.size in MIN_SELECTED_VIDEOS..MAX_SELECTED_VIDEOS) {
////            MergeMedia.merge(selectedVideos)
////        }
////    }
////
////    private fun saveVideosToCache(selectedVideos: ArrayList<Uri>) {
////        val cacheDir = File(cacheDir, "video_cache")
////        if (!cacheDir.exists()) {
////            cacheDir.mkdirs()
////        }
////
////        for (i in 0 until selectedVideos.size) {
////            val selectedVideo = selectedVideos[i]
////            val destFile = File(cacheDir, "video_$i.mp4")
////
////            try {
////                contentResolver.openInputStream(selectedVideo)?.use { inputStream ->
////                    destFile.outputStream().use { outputStream ->
////                        inputStream.copyTo(outputStream)
////                    }
////                }
////            } catch (e: IOException) {
////                e.printStackTrace()
////
////            }
////        }
////
////    }

//
//val dir = File(cacheDir.absolutePath)
//if (dir.exists()) {
//    for (f in dir.listFiles()!!) {
//        Log.i("MyTag", "${f.name}  - ${f.absolutePath}")
//    }
//}
//}