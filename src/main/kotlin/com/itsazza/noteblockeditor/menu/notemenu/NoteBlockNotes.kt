package com.itsazza.noteblockeditor.menu.notemenu

import com.itsazza.noteblockeditor.NoteBlockEditorPlugin

class NoteBlockNote(val name: String, val value: Int)
val secondOctave = NoteBlockEditorPlugin.instance.getLangString("second-octave")

val listOfNotes = listOf(
    NoteBlockNote("F♯/G♭", 1),
    NoteBlockNote("G", 2),
    NoteBlockNote("G♯/A♭", 3),
    NoteBlockNote("A", 4),
    NoteBlockNote("A♯/B♭", 5),
    NoteBlockNote("B", 6),
    NoteBlockNote("C", 7),
    NoteBlockNote("C♯/D♭", 8),
    NoteBlockNote("D", 9),
    NoteBlockNote("D♯/E♭", 10),
    NoteBlockNote("E", 11),
    NoteBlockNote("F", 12),
    NoteBlockNote("F♯/G♭ $secondOctave", 13),
    NoteBlockNote("G $secondOctave", 14),
    NoteBlockNote("G♯/A♭ $secondOctave", 15),
    NoteBlockNote("A $secondOctave", 16),
    NoteBlockNote("A♯/B♭ $secondOctave", 17),
    NoteBlockNote("B $secondOctave", 18),
    NoteBlockNote("C $secondOctave", 19),
    NoteBlockNote("C♯/D♭ $secondOctave", 20),
    NoteBlockNote("D $secondOctave", 21),
    NoteBlockNote("D♯/E♭ $secondOctave", 22),
    NoteBlockNote("E $secondOctave", 23),
    NoteBlockNote("F $secondOctave", 24),
    NoteBlockNote("F♯/G♭ $secondOctave", 25)
)