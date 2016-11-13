package meu.chess


import meu.chess.pieces.King;
import meu.chess.pieces.NullPiece
import meu.chess.pieces.Piece;
import static meu.chess.utils.ConvertUtil.*;

class Square{
						 
	def content
	def previousContent = new NullPiece()
	def cordinate = "  "
	def color
	def board 	  
	
	Square(def cordinate, def piece) {
		update(cordinate, piece)
	}
	
	Square(def cordinate){
		update(cordinate, null)
	}
	
	public void setContent(piece) {
		new Exception("Inválido, use updade(cordinate,piece)")
	}
	def void update(cordinate, piece) {
		
		this.cordinate = cordinate
		this.color = getColorFromSquareCordinate(cordinate)
		
		this.previousContent = this.content
		if (piece !=null) {
			this.content = piece
		}else {
			this.content = new NullPiece()
		}
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
		if (this.content instanceof NullPiece)
			return false
		return (this.content.color == piece?.color) 
	}
	
	def hasPieceOfOpponent(def piece) {
		if (this.content instanceof NullPiece)
			return false
		return  (this.content.color != piece?.color)
	}
	
	def isAKing() {
		(this.content instanceof King)
	}
	
	def isEmpty() {
		this.content == null || (this.content instanceof NullPiece) 
	}
	
	@Override
	public String toString() {
		return this.cordinate;
	}	
}
