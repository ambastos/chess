package meu.chess

import static Board.*
import static meu.chess.utils.ConvertUtil.*
import meu.chess.pieces.Bishop
import meu.chess.pieces.Knight
import meu.chess.pieces.Piece
import meu.chess.pieces.Queen
import meu.chess.pieces.Rock

protected class Atack {
	
	Board board;
	Atack(board) {
		this.board = board
	}

	def isThereKingInCheck(color) {
		def king =  board.getKingFromColor(color)
		def square = king.currentSquare
	   
		isSquareAtacked(square, color)
   }

   def isSquareAtacked(square, color) {

	   if  (isSquareVerticallyAttacked(square, color)) {
		   return true
	   }
		   
	   if (isSquareHorizontallyAtacked(square, color)) {
		   return true
	   }
	   
	   if (isSquareDiagonallyAtacked(square, color)) {
		   return true
	   }
	   
	   if (isLShapedAtacked(square, color)) {
		   return true
	   }
	   
	   if (isAtackedByPawn(square,color)) {
		   return true
	   }
	   return false
   }
   
   private isAtackedByPawn(square, color) {
	   Piece piece =  square.content
	   def cordinates
	   def colorFromEnemyPiece
	   if (piece.isWhite()) {
		   cordinates = [getNewCordinateFrom(square.cordinate, 1, 1),getNewCordinateFrom(square.cordinate, -1, 1)]
		   colorFromEnemyPiece = BLACK
	   }else {
		   cordinates = [getNewCordinateFrom(square.cordinate, 1, -1),getNewCordinateFrom(square.cordinate, -1, -1)]
		   colorFromEnemyPiece = WHITE
	   }
	   
	   def squaresByCordinates = board.getSquaresByCordinates(cordinates)
	   for (squareByCordinate in squaresByCordinates)
		   if (squareByCordinate.content.isPawn(colorFromEnemyPiece))
			   return true
			   
	   return false
   }
   
   private isSquareDiagonallyAtacked(square,color) {
	   def check = false
	   
	   def diagonals = board.getDiagonals(square.cordinate)
	   for (objDiagonal in diagonals) {
		   for (cordinatesFromSource in objDiagonal.value.cordinatesFromSource()) {
			   for (currentSquare in cordinatesFromSource.value) {
				   def pieceOnSquare = currentSquare.content
				   if (isThereAPieceOfSameColor(pieceOnSquare, square, color))
					   break
				   if (isThisSquareAttackedByQueenOrBishop(pieceOnSquare, color) )
					   return true
			   }
		   }
	   }
	   
	   return false
   }
   
   private boolean isThisSquareAttackedByQueenOrBishop(pieceOnSquare, colorOfOwnPiece) {
	   pieceOnSquare.isValid() && pieceOnSquare?.color != colorOfOwnPiece && ( pieceOnSquare instanceof Queen || pieceOnSquare instanceof Bishop)
   }
   
   private boolean isEnemyPieceQueenOrRock(pieceOnSquare, color) {
	   if (pieceOnSquare?.color != color) {
		   if (pieceOnSquare instanceof Queen || pieceOnSquare instanceof Rock) {
			   return true
		   }
	   }
	   return false
   }

   private isLShapedAtacked(square, color) {
	   
	   def LShape = board.getLShape(square.cordinate)
	   for (currentSquare in LShape) {
		   def pieceOnSquare = currentSquare?.content
		   if (isThereAPieceOfSameColor(pieceOnSquare, square, color))
			   break
		   
		   if (isSquareAttackeByKnight(pieceOnSquare, color))
			   return true
	   }
	   return false
   }
   
   private def isSquareAttackeByKnight(pieceOnSquare, color) {
	   if (pieceOnSquare?.color != color) {
		   if (pieceOnSquare instanceof Knight) {
			   return true
		   }
	   }
	   return false
   }
   
   private isSquareHorizontallyAtacked(square, color) {
	   
	   def columnNumber = getNumberOfColumnFromCordinate(square.cordinate)
	   def line = getLineFromCordinate(square.cordinate)

	   def finalSquareCordinates = []
	   finalSquareCordinates.add(getCordinateFromColumNumberAndLine(1,line))
	   finalSquareCordinates.add(getCordinateFromColumNumberAndLine(MAX_NUMBER_OF_COLUMNS,line))

	   for (finalSquareCordinate in finalSquareCordinates) {
		   def horizontal = board.getHorizontal(square.cordinate, finalSquareCordinate)
		   for (currentSquare in horizontal) {
			   def pieceOnSquare = currentSquare?.content
			   if (isThereAPieceOfSameColor(pieceOnSquare, square, color))
				   break
			   
			   if (isEnemyPieceQueenOrRock(pieceOnSquare, color))
				   return true
		   }
	   }
	   return false
   }

   private boolean isSquareVerticallyAttacked(square, color) {
	   
	   def columnNumber = getNumberOfColumnFromCordinate(square.cordinate)
	   def line = getLineFromCordinate(square.cordinate)
	   
	   def finalSquareCordinates = []
	   finalSquareCordinates.add(getCordinateFromColumNumberAndLine(columnNumber,1))
	   finalSquareCordinates.add(getCordinateFromColumNumberAndLine(columnNumber,MAX_NUMBER_OF_LINES))

	   for (finalSquareCordinate in finalSquareCordinates) {
		   def vertical = board.getVertical(square.cordinate, finalSquareCordinate)
		   for (currentSquare in vertical) {
			   def pieceOnSquare = currentSquare?.content
			   if (isThereAPieceOfSameColor(pieceOnSquare, square, color))
				   return false
				   
			   if (isEnemyPieceQueenOrRock(pieceOnSquare, color)) {
				   return true
			   }else if (isThereAPieceOfAnotherColor(pieceOnSquare, square, color)) {
				   return false
			   }
		   }
	   }
	   return false
   }
   
   private isThereAPieceOfSameColor(pieceOnSquare, square, colorOfEmemyPieces) {
	   pieceOnSquare.isValid() && pieceOnSquare != square.content && pieceOnSquare?.color == colorOfEmemyPieces
   }
   
   private isThereAPieceOfAnotherColor(pieceOnSquare, square, colorOfEmemyPieces) {
	   pieceOnSquare.isValid() && pieceOnSquare != square.content && pieceOnSquare?.color != colorOfEmemyPieces
   }
}
