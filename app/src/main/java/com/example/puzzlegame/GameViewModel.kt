package com.example.puzzlegame

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val _puzzle = mutableStateListOf<Int>()
    val puzzle: List<Int> get() = _puzzle
    
    private val _moves = mutableStateOf(0)
    val moves: Int get() = _moves.value
    
    private val _isSolved = mutableStateOf(false)
    val isSolved: Boolean get() = _isSolved.value
    
    init {
        resetGame()
    }
    
    fun resetGame() {
        _puzzle.clear()
        // Başlangıç durumu: 1-8 sayıları ve 9 (boşluk)
        val numbers = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        
        // Rastgele karıştır (geçerli bir çözülebilir durum için)
        do {
            numbers.shuffle()
        } while (!isSolvable(numbers))
        
        _puzzle.addAll(numbers)
        _moves.value = 0
        _isSolved.value = false
    }
    
    private fun isSolvable(list: List<Int>): Boolean {
        // Boşluk (9) hariç inversiyon sayısını hesapla
        var inversions = 0
        for (i in list.indices) {
            if (list[i] == 9) continue
            for (j in i + 1 until list.size) {
                if (list[j] == 9) continue
                if (list[i] > list[j]) inversions++
            }
        }
        // 3x3 puzzle için inversiyon sayısı çift olmalı
        return inversions % 2 == 0
    }
    
    fun moveTile(index: Int) {
        if (_isSolved.value) return
        
        val emptyIndex = _puzzle.indexOf(9)
        val row = index / 3
        val col = index % 3
        val emptyRow = emptyIndex / 3
        val emptyCol = emptyIndex % 3
        
        // Sadece yatay veya dikey komşuysa hareket ettir
        val isAdjacent = (row == emptyRow && Math.abs(col - emptyCol) == 1) ||
                (col == emptyCol && Math.abs(row - emptyRow) == 1)
        
        if (isAdjacent) {
            _puzzle[emptyIndex] = _puzzle[index]
            _puzzle[index] = 9
            _moves.value++
            checkSolved()
        }
    }
    
    private fun checkSolved() {
        // 1-8 sıralı ve son eleman 9 (boşluk) ise çözülmüştür
        for (i in 0..7) {
            if (_puzzle[i] != i + 1) {
                _isSolved.value = false
                return
            }
        }
        if (_puzzle[8] == 9) {
            _isSolved.value = true
        }
    }
}