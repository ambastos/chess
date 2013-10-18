package meu.chess

import static meu.chess.Board.*
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

   class AtackObject {
	   def square
	   def color
	   boolean atacked = false
	   AtackObject(square, color) {
		   this.square =square
		   this.color = color
	   }
	   
	   AtackObject OR (method) {
		    if (method(square, color) ) 
				atacked = true
		   
		   return this
	   } 
		   
	   boolean isTrue() {
		   atacked
	   }
   }	
   
   def isSquareAtacked(square, color) {

	   def atack = new AtackObject(square, color)
	   
	   def atacked = atack
	   		.OR(this.&isVertical)
	   		.OR(this.&isHorizontal)
			.OR(this.&isDiagonal)
			.OR(this.&isLShaped)
			.OR(this.&isByPawn).isTrue()
	   
		if  (atacked) 
			return true
	   else 
	   		return false
   }
   
   private isByPawn(square, color) {
	   Piece content =  square.content
	   def cordinates
	   def colorFromEnemyPiece
	   if (content.isWhite()) {
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
   /**
    * Reformar o trecho futureContent
    * @param square
    * @param color
    * @return
    */
   private isDiagonal(square,color) {
	   def check = false
	   
	   def diagonals = board.getDiagonals(square.cordinate)
	   for (objDiagonal in diagonals) {
		   for (cordinatesFromSource in objDiagonal.value.cordinatesFromSource()) {
			   for (currentSquare in cordinatesFromSource.value) {
				   
				   if (isThereAPieceOfSameColorInThisSquare(currentSquare, square, color))
					   break
				   if (isThisSquareAttackedByQueenOrBishop(currentSquare, color) )
					   return true
			   }
		   }
	   }
	   
	   return false
   }
   
   private boolean isThisSquareAttackedByQueenOrBishop(currentSquare, colorOfOwnPiece) {
	   def pieceOnSquare = currentSquare?.currentContent
	   pieceOnSquare.isValid() && pieceOnSquare?.color != colorOfOwnPiece && ( pieceOnSquare instanceof Queen || pieceOnSquare instanceof Bishop)
   }
   
   private boolean isEnemyPieceInThisSquareQueenOrRock(currentSquare, color) {
	   def pieceOnSquare = currentSquare?.currentContent
	   if (pieceOnSquare?.color != color) {
		   if (pieceOnSquare instanceof Queen || pieceOnSquare instanceof Rock) {
			   return true
		   }
	   }
	   return false
   }

   private isLShaped(square, color) {
	   
	   def LShape = board.getLShape(square.cordinate)
	   for (currentSquare in LShape) {
		   if (isThereAPieceOfSameColorInThisSquare(currentSquare, square, color))
			   break
		   
		   if (isByKnight(currentSquare, color))
			   return true
	   }
	   return false
   }
   
   private def isByKnight(currentSquare, color) {
	   def pieceOnSquare = currentSquare?.currentContent
	   if (pieceOnSquare?.color != color) {
		   if (pieceOnSquare instanceof Knight) {
			   return true
		   }
	   }
	   return false
   }
   
   private isHorizontal(square, color) {
	   
	   def columnNumber = getNumberOfColumnFromCordinate(square.cordinate)
	   def line = getLineFromCordinate(square.cordinate)

	   def finalSquareCordinates = []
	   finalSquareCordinates.add(getCordinateFromColumNumberAndLine(1,line))
	   finalSquareCordinates.add(getCordinateFromColumNumberAndLine(MAX_NUMBER_OF_COLUMNS,line))

	   for (finalSquareCordinate in finalSquareCordinates) {
		   def horizontal = board.getHorizontal(square.cordinate, finalSquareCordinate)
		   for (currentSquare in horizontal) {
			   if (square == currentSquare)
			   		continue
					   
			   if (isThereAPieceOfSameColorInThisSquare(currentSquare, square, color))
				   break
			   
			   if (isEnemyPieceInThisSquareQueenOrRock(currentSquare, color)) {
				   return true
			   }else if (isThereAPieceOfAnotherColor(currentSquare, square, color)) {
				   return false
			   }
		   }
	   }
	   return false
   }

   private boolean isVertical(square, color) {
	   
	   def columnNumber = getNumberOfColumnFromCordinate(square.cordinate)
	   def line = getLineFromCordinate(square.cordinate)
	   
	   def finalSquareCordinates = []
	   finalSquareCordinates.add(getCordinateFromColumNumberAndLine(columnNumber,1))
	   finalSquareCordinates.add(getCordinateFromColumNumberAndLine(columnNumber,MAX_NUMBER_OF_LINES))

	   for (finalSquareCordinate in finalSquareCordinates) {
		   def vertical = board.getVertical(square.cordinate, finalSquareCordinate)
		   for (currentSquare in vertical) {
			   
			   if (isThereAPieceOfSameColorInThisSquare(currentSquare, square, color))
				   return false
				   
			   if (isEnemyPieceInThisSquareQueenOrRock(currentSquare, color)) {
				   return true
			   }else if (isThereAPieceOfAnotherColor(currentSquare, square, color)) {
				   return false
			   }
		   }
	   }
	   return false
   }
   
   private isThereAPieceOfSameColorInThisSquare(currentSquare, square, colorOfEmemyPieces) {
	   def pieceOnSquare = currentSquare?.currentContent
	   pieceOnSquare.isValid() && pieceOnSquare != square.content && pieceOnSquare?.color == colorOfEmemyPieces
   }
   
   private isThereAPieceOfAnotherColor(currentSquare, square, colorOfEmemyPieces) {
	   def pieceOnSquare = currentSquare?.currentContent
	   pieceOnSquare.isValid() && pieceOnSquare != square.content && pieceOnSquare?.color != colorOfEmemyPieces
   }
}
