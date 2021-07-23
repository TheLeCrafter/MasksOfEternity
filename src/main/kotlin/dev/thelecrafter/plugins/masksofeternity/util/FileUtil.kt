package dev.thelecrafter.plugins.masksofeternity.util

import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object FileUtil {

    fun zipFiles(zipFile: File, vararg filesToZip: File) {
        if (filesToZip.isNotEmpty()) {
            val out = ZipOutputStream(FileOutputStream(zipFile))
            for (file in filesToZip) {
                val zipEntry = ZipEntry(file.name)
                out.putNextEntry(zipEntry)
                out.closeEntry()
            }
            out.close()
        } else throw IllegalArgumentException("No files provided!")
    }

}