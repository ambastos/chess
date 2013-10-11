package meu.chess.builder

import static meu.chess.utils.ConvertUtil.*
import static meu.chess.factory.BoardFactory.*
import static meu.chess.Board.*
import meu.chess.Board
import java.util.List
import meu.chess.SquareEnum

class BoardBuilder {

	def static buildTheLinesToPrint(board) {
		def squares = board.squares
		def cursorCordinate = board.cursorCordinate
		String strLines = ""
		for (line in MAX_NUMBER_OF_LINES..1) {

			def possibleColumns = new LinkedList(BOARD_COLUMNS)
			def strCompleteLine = ""
			def numberOfTheLineThatBeingPrinted = 0

			while (numberOfTheLineThatBeingPrinted  < LINE_HEIGHT) {
				def strLine = ""

				switch(numberOfTheLineThatBeingPrinted) {
					case 0:
						strLine = buildTheFirstPartOfTheChessColumn(line, strLine)
						break;
					case 1:
						strLine = buildTheSecondPartOfTheChessColumn(squares, possibleColumns, line, strLine)
						break;
					case 2:
						strLine = buildTheThirdPartOfTheChessColumn(board, strLine, line)

						if (possibleColumns.size() == 0 && line != 1) {
							strLine+="\n"
						}
						break;
				}
				strCompleteLine+=strLine

				numberOfTheLineThatBeingPrinted+=1
			}

			strLines+=strCompleteLine

		}
		return strLines
	}

	private static String buildTheFirstPartOfTheChessColumn(def line, String strLine) {
		if (line == MAX_NUMBER_OF_LINES) {
			for (position in MAX_NUMBER_OF_LINES..1) {
				strLine +=" ___"
			}
			strLine+=" \n"
		}
		return strLine
	}
	
	private static String buildTheSecondPartOfTheChessColumn(def squares, List posssibleColumns, int line, String strLine) {
		 
		for (position in MAX_NUMBER_OF_LINES..1) {
			def strColumn = ""
			for (def i = 0; i < posssibleColumns.size();i++) {

				def column = posssibleColumns.get(i);
				def cordinate = column+line
				def square = squares.get(cordinate)
				def content =  square.content;
				def strColor = square.color == BLACK ? "*" : " "
				
				strColumn = content !=null ? content.toString() : SquareEnum.EMPTY_SQUARE.toString()

				if (posssibleColumns.contains(column)) {
					posssibleColumns.remove(column)
					if (position == MAX_NUMBER_OF_LINES) {
						strLine +="|"+strColumn+strColor+"|"
					}else {
						strLine +=strColumn+strColor+"|"
					}
					break;
				}
			}

		}
		strLine+="\n"
		return strLine
	}

	private static String buildTheThirdPartOfTheChessColumn(board, strLine, line) {
		for (column in 1..MAX_NUMBER_OF_COLUMNS) {
			def currentCordinate = getColumnSimbolFromNumber(column)+line
			if (column == 1) {
				if (board.application && board.cursorCordinate == currentCordinate) {
					if (board.selectPieceOnCursor) {
						strLine+= "|[X]|"
					}else {
						strLine+= "|[_]|"
					}
					
				}else {
					strLine+= "|___|"
				}	
			}else {
				if (board.application && board.cursorCordinate == currentCordinate) {
					strLine +="[_]|"
					if (board.selectPieceOnCursor) {
						strLine+= "[X]|"
					}	
				}else {
					strLine +="___|"
				}				
			}
			
		}
		return strLine
	}

}
