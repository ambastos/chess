package meu.chess

import static meu.chess.pieces.Piece.*
import static meu.chess.utils.ConvertUtil.*
import meu.chess.pieces.King
import meu.chess.pieces.NullPiece
import meu.chess.pieces.Piece

class Square{
						 
	Piece content
	private futureContent
	def cordinate = "  "
	def color
	def board 	  
	
	Square(def cordinate, def content) {
		update(cordinate, content)
	}
	
	Square(def cordinate){
		update(cordinate, null)
	}
	
	def void updateTemporally(content) {
		futureContent = content
	}
	
	def void update(cordinate, content) {
		
		this.cordinate = cordinate
		this.color = getColorFromSquareCordinate(cordinate)
		
		if (content !=null) {
			this.content = content
		}else {
			this.content = NULL_PIECE
		}
		this.futureContent = null
		this.content.currentSquare = this
	}
	
	def getColumn() {
		getColumnFromCordinate(cordinate)
	}

	def getLine() {
		getLineFromCordinate(cordinate)
	}
	
	public getColumnNumber() {
		getNumberOfColumn(getColumnFromCordinate(cordinate))
	}
	
	/**
	 * Retorna o conteúdo que é um objeto do tipo Piece	
	 * @return content (Um objeto do tipo Piece)
	 */
	def getContent() {
		return content
	}

	def hasPieceOfSameColor(def content) {
		!(this.content instanceof NullPiece) &&  (this.content.color == content?.color) 
	}
	
	def hasPieceOfOpponent(def content) {
		!(this.content instanceof NullPiece) &&  (this.content.color != content?.color)
	}
	
	def isAKing() {
		(this.content instanceof King)
	}
	
	@Override
	public String toString() {
		return this.cordinate;
	}	

	@Override
	public boolean equals(square) {
		return this.cordinate == square.cordinate;
	}
	@Override
	public int hashCode() {
		def strNumberCordinate = String.valueOf(getNumberOfColumnFromCordinate(cordinate))+ String.valueOf(getLineFromCordinate(cordinate))
		return Integer.valueOf(strNumberCordinate)
	}
	
	def isChanged() {
		futureContent !=null
	}
	
	def distanceBetweenThisCordinate(cordinate) {
		def difColumn =  getNumberOfColumnFromCordinate(cordinate) - getColumnNumber()
		def difLine = getLineFromCordinate(cordinate) - getLine()
		
		def ret = [:]
		ret.put(difColumn, difLine)
		ret 
	}
	
	def isFilledWithPiece() {
		this.content !=null && !(this.content instanceof NullPiece)
	}
	
	def isEmpty() {
		this.content ==null || (this.content instanceof NullPiece)
	}
	
	def getCurrentContent() {
		if (futureContent !=null ) 
			futureContent
		else 
			content
	}
	
	def cancelUpdate() {
		if (futureContent !=null)
			futureContent = null
	}
	
	def isFirstLineFromPieces() {
		getLine() ==1 || getLine() == Board.MAX_NUMBER_OF_LINES	
	}
}
