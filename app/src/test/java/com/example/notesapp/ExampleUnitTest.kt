package com.example.notesapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.notesapp.db.Dao
import com.example.notesapp.db.Note
import com.example.notesapp.db.NoteDatabase
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NotesTest {

    private lateinit var db: NoteDatabase
    private lateinit var dao: Dao


    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db= Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).build()
        dao = db.getNoteDao()
    }


    @After
    fun tearDown(){
        db.close()
    }

    @Test
    fun add_notes_db()= runBlocking(Dispatchers.IO) {
        val testNote = Note(101,"Testing Note 1","This is description of the test note 1")
        dao.insert(testNote)
        val testNotes=dao.getAllNotesList()
        TestCase.assertTrue(testNotes.contains(testNote))
    }

    @Test
    fun delete_notes_db()= runBlocking(Dispatchers.IO) {
        val testNote = Note(101,"Testing Note 1","This is description of the test note 1")
        dao.insert(testNote)
        val testNotes=dao.getAllNotesList()
        TestCase.assertTrue(!testNotes.contains(testNote))
    }
}