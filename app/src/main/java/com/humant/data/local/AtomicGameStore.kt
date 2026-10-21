package com.humant.data.local

import android.util.AtomicFile
import com.humant.domain.model.GameState
import java.io.File

/** Atomic replacement keeps the preceding complete save if writing fails. */
class AtomicGameStore(directory: File) {
    private val file = AtomicFile(File(directory, "game.save"))
    fun load(): GameState = try {
        file.openRead().bufferedReader().use { SaveCodec.decode(it.readText()) }
    } catch (error: java.io.FileNotFoundException) {
        if (file.baseFile.exists()) throw error
        GameState()
    }
    fun save(state: GameState) {
        val output = file.startWrite()
        try {
            output.write(SaveCodec.encode(state).toByteArray(Charsets.UTF_8))
            file.finishWrite(output)
        } catch (error: Exception) {
            file.failWrite(output)
            throw error
        }
    }
}
