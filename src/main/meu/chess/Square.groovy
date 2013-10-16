package meu.chess


import meu.chess.pieces.King;
import meu.chess.pieces.NullPiece
import meu.chess.pieces.Piece;
import static meu.chess.utils.ConvertUtil.*;

class Square{
						 
	Piece content
	private futureContent
	def cordinate = "  "
	def color
	def board 	  
	
	Square(def cordinate, def piece) {
		update(cordinate, piece)
	}
	
	Square(def cordinate){
		update(cordinate, null)
	}
	
	def void updateTemporally(piece) {
		futureContent = piece
	}
	
	def void update(cordinate, piece) {
		
		this.cordinate = cordinate
		this.color = getColorFromSquareCordinate(cordinate)
		
		if (piece !=null) {
			this.content = piece
		}else {
			this.content = new NullPiece()
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

	def hasPieceOfSameColor(def piece) {
		!(this.content instanceof NullPiece) &&  (this.content.color == piece?.color) 
	}
	
	def hasPieceOfOpponent(def piece) {
		!(this.content instanceof NullPiece) &&  (this.content.color != piece?.color)
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
}
