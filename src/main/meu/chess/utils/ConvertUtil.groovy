package meu.chess.utils

import static BoardFactory.BOARD_COLUMNS 
import static meu.chess.Board.*;
import meu.chess.Board;
import meu.chess.factory.BoardFactory;

class ConvertUtil {

	def static getColumnFromCordinate(def cordinate) {
		cordinate.substring(0,1)
	}
	
	def static getLineFromCordinate(def cordinate) {
		cordinate.substring(1).toInteger()
	}
	
	def static getNumberOfColumn(def column) {
		BOARD_COLUMNS.indexOf(column)+1 
	}
	
	def static getNumberOfColumnFromCordinate(def cordinate) {
		getNumberOfColumn(getColumnFromCordinate(cordinate))
	}
	
	def static getColumnSimbolFromNumber(def column) {
		if (column < 1 || column > Board.MAX_NUMBER_OF_COLUMNS) {
			null
		}else {
			BOARD_COLUMNS.get(column - 1)
		}	
	}
	/**
	 * Define a new cordinate based on oldCordinate plus number of columns and number of lines
	 * @param oldCordinate
	 * @param numberOfColumns
	 * @param numberOfLines
	 * @return
	 */
	def static getNewCordinateFrom(oldCordinate, numberOfColumns, numberOfLines) {
		
		def oldColumnNumber = getNumberOfColumnFromCordinate(oldCordinate)
		def oldLine = getLineFromCordinate(oldCordinate)
		
		def newColumnNumber = oldColumnNumber+numberOfColumns
		def newLine = oldLine+numberOfLines 
		
		def newCordinate = getColumnSimbolFromNumber(newColumnNumber >=1 ? newColumnNumber: 1)+newLine
		newCordinate
	}
	
	def static getCordinateFromColumNumberAndLine(columnNumber, line) {
		BOARD_COLUMNS.get(columnNumber - 1) + line
	}
	
	def static getColorFromSquareCordinate(def cordinate) {
		def columnNumber = getNumberOfColumnFromCordinate(cordinate)
		def line = getLineFromCordinate(cordinate)
		
		def color
		if (isOdd(columnNumber) && isEven(line)) {
			color = WHITE
		}else if (isOdd(columnNumber) && isOdd(line)) {
			color = BLACK
		}else if (isEven(columnNumber) && isEven(line)) {
			color = BLACK
		}else if (isEven(columnNumber) && isOdd(line)) {
			color = WHITE
		}
		
		color		
	}
	
	def static isAValidColumn(def columnNumber) {
		
		def column = getColumnSimbolFromNumber(columnNumber)
		if (column) {
			BOARD_COLUMNS.contains(column)
		}else {
			false
		}	
	}
	
	private def static isOdd (def number) {
		number % 2 != 0
	}
	
	private def static isEven (def number) {
		number % 2 == 0
	}
	
	
}
