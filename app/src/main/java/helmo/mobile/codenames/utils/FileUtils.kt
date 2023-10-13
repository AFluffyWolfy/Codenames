package helmo.mobile.codenames.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.*

class FileUtils {

    companion object {

        fun savePhoto(file: File, resolver: ContentResolver): String? {
            var result: String? = null
            if(!file.isFile || file.absolutePath.startsWith(("/storage/"))) return file.absolutePath
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.WIDTH, bitmap.width)
                    put(MediaStore.Images.Media.HEIGHT, bitmap.height)
                }

                var uri: Uri? = null
                var fos: OutputStream? = null
                try {
                    val imagesCollection = getImgPath()
                    uri = resolver.insert(imagesCollection, contentValues)
                    uri?.let {
                        fos = resolver.openOutputStream(it)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        fos?.flush()
                        fos?.close()
                        result = getRealPathFromUri(it, resolver)
                    }
                } catch (e: IOException) {
                    throw FileNotSaveException("An error occured while the file was saved")
                }
            } else {
                // Assuming you have already saved your image to the app's temporary directory

                // Determine where you want to save the file (e.g., internal or external storage)
                val imageFile = File(getImgPath().path, file.name)

                try {
                    // Open an input stream to read from the temporary file
                    val fis = FileInputStream(file)

                    // Open an output stream to write to the new file
                    val fos = FileOutputStream(imageFile)

                    // Use a byte buffer to read and write data in chunks
                    val buffer = ByteArray(1024)
                    var bytesRead: Int
                    while (fis.read(buffer).also { bytesRead = it } != -1) {
                        fos.write(buffer, 0, bytesRead)
                    }

                    // Close the input and output streams
                    fis.close()
                    fos.close()

                    // Optionally, delete the temporary file
                    file.delete()
                    result = imageFile.absolutePath
                } catch (e: IOException) {
                    throw FileNotSaveException("An error occured while the file was saved")
                }
            }
            return result
        }

        private fun getImgPath(): Uri {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            } else {
                // For Android versions prior to Android 10:
                val externalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                val path = externalStorage.absolutePath + "/Camera/"
                return Uri.parse(path)
            }
        }



        @Throws(IOException::class)
        fun createTempImage(fileName: String, fromContext: Context): File {
            // Create an image file name
            val storageDir: File? = fromContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                fileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            )
        }

        fun getRealPathFromUri(uri: Uri, resolver: ContentResolver): String? {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor = resolver.query(uri, projection, null, null, null)!!
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath: String = cursor.getString(column_index)
            cursor.close()
            return filePath
        }

    }

}