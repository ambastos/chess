package meu.chess.factory

import static meu.chess.Board.*
import static meu.chess.utils.ConvertUtil.*
import meu.chess.Board
import meu.chess.MovimentoInvalidoException
import meu.chess.Square
import meu.chess.builder.MoveLineReader;
import meu.chess.pieces.King
import meu.chess.pieces.Knight;
import meu.chess.pieces.NullPiece
import meu.chess.pieces.ValidPiece
import meu.chess.pieces.Pawn;
import meu.chess.pieces.Piece;
import meus.chess.Castle

public class BoardFactory {
	
	
	def static final BOARD_COLUMNS = ["A","B","C","D","E", "F", "G","H"]
	Board board
	
	BoardFactory(board) {
		this.board = board
	}
	public void movePiece(Piece piece, def initialPosition, def finalPosition) {
		def t = new Factory(this, piece, initialPosition, finalPosition)
		t.move()
	}

	private atualizaVezDoJogador(piece) {
		if (piece.getColor() == WHITE)
			board.color = BLACK
		else
			board.color = WHITE
	}
	
	private verifyIfKingIsInCheck(Piece piece,verifyIfPieceIsNotTheOwnKing ,initialPosition, finalPosition, undo) {
		def sameColor = piece.color 
		def description = piece.description
		
		/**Muda a descrição pois rei não está na casa atual(devido a verificação de cheque, mas sim de @initialPosition*/ 
		if (verifyIfPieceIsNotTheOwnKing == false)
			description = "rei"
		 
		def isKingInCheck = board.isThereKingInCheck(sameColor)
		
		//qual peca ataca o rei?
		
		if (isKingInCheck) {
			if (verifyIfPieceIsNotTheOwnKing) {
				if (piece.isAKing()) {
					return
				}	
			}		 
			def king = board.getKing(sameColor)
			def kingCordinate = king.currentSquare.cordinate
			
			/**Muda a descrição pois rei não está na casa atual(devido a verificação de cheque, mas sim de @initialPosition*/ 
			if (verifyIfPieceIsNotTheOwnKing == false)
				kingCordinate = initialPosition 
			
			//Desfaz as alterações
			if (undo)
				undoUpdateSquaresContent(finalPosition, initialPosition, null)
			
			throw new MovimentoInvalidoException("Não é possível mover a peça $description de $initialPosition para $finalPosition pois o rei ficará em xeque.")
		}
	}	
		
	def undoUpdateSquaresContent(finalPosition, initialPosition, castle) {
		
		if (!castle) { 
			Square initialSquare = board.squares.get(finalPosition)
			initialSquare.update(finalPosition, initialSquare.previousContent)
	
			Square finalSquare = board.squares.get(initialPosition)
			finalSquare.update(initialPosition, finalSquare.previousContent)
		}	
		//Desfaz o castle
		if (castle) {
			
			def curreKingSquare = castle.king.currentSquare
			curreKingSquare.update(curreKingSquare.cordinate, new NullPiece()) 
			board.squares.put(curreKingSquare.cordinate, curreKingSquare)
			
			Square newKingSquare = castle.kingInitialSquare
			newKingSquare.update(newKingSquare.cordinate, castle.king)
			board.squares.put(newKingSquare.cordinate, newKingSquare)
			
			
			def currenRockSquare = castle.rock.currentSquare
			currenRockSquare.update(currenRockSquare.cordinate, new NullPiece())
			board.squares.put(currenRockSquare.cordinate, currenRockSquare)
						
			Square newRockSquare = castle.rockInitialSquare
			newRockSquare.update(newRockSquare.cordinate, castle.rock)
			board.squares.put(newRockSquare.cordinate, newRockSquare)
		}
		
	}
		
	private verifyIfMovementIsValid(Piece piece, initialPosition, finalPosition, square) {
		validMovement(piece, initialPosition, finalPosition, square)
		piece.validMovement(square, finalPosition)
	}
	
	private validMovement(piece, currentPosition, finalPosition, square) {
		if (isThisSquareEmpty(square))
			throw new MovimentoInvalidoException("Nao há peça nessa casa ($square.cordinate)")

		if (board.application) {
			def color = Board.COLOR_NAMES[piece.color]
			if (piece.color != board.color) {
				throw new MovimentoInvalidoException("Não é a vez das $color(s).")
			}
		}
		def finalSquare = board.squares.get(finalPosition)
		
		if (!board.hasThisSquare(currentPosition)) 
			throw new MovimentoInvalidoException("A casa de origem $currentPosition é inválida.")
			
		if (!board.hasThisSquare(finalPosition))
			throw new MovimentoInvalidoException("A casa de destino $finalPosition é inválida.")
		
		if (!piece.isTheSameColorAndType(square.content)) {
			throw new MovimentoInvalidoException("Não há a peça ($piece) desse tipo na casa informada.")
		}
			
		if (finalSquare == null)
			throw new MovimentoInvalidoException("A casa de destino $finalPosition da peça $square.content.description é inválida.")

		if (currentPosition == finalPosition) {
			def d = square.content.description
			throw new MovimentoInvalidoException("A própria peça($d) não pode se mover para a mesma casa.")
		}

		def pieceInSquare = square.content
		if (finalSquare.hasPieceOfSameColor(pieceInSquare) )
			throw new MovimentoInvalidoException("Movimento da peça $square.content.description não é valido devido a presença de $finalSquare.content.description na casa $finalPosition.")
		
		if (piece.isAKing()) {
			def horizontal = board.getHorizontal(currentPosition, finalPosition)
			piece.validMovementsOnSquares(square, horizontal)
			
			if (piece.isCastle(square, finalPosition)) {
				def columns = piece.getNumberOfMovedColumns(square, finalPosition)
				def lineSquares = board.getLine(square.line)
				def rockSquare
				if (columns <= 2 ) 
					rockSquare = lineSquares.get(0)
				else
					rockSquare = lineSquares.get(lineSquares.size()-1)
				
				def squareOfCastleRock = board.getSquareBy(rockSquare.cordinate)
				def description = piece.color == WHITE ? "brancas" : "negras" 
				if (squareOfCastleRock.isEmpty()) {
					throw new MovimentoInvalidoException("Não é possível realizar o O-O das $description pois a torre de $rockSquare.cordinate não está presente.")
				}else {
					def content = squareOfCastleRock.content
					if (board.isThisPieceMovedBefore(content)) {
						throw new MovimentoInvalidoException("Não é possível realizar o O-O das $description pois a torre de $rockSquare.cordinate já realizou um movimento.")
					}
				}
			}
		}			
	}

	private isThisSquareEmpty(square) {
		def pieceInSquare = square.content
		def isAValidPiece = pieceInSquare instanceof ValidPiece
		(pieceInSquare == null || !isAValidPiece)
	}

	private Castle updateSquaresContent(initialPosition, finalPosition, Piece piece) {
		
		def castle
		def initialSquare = board.squares.get(initialPosition)
		if (piece.isAKing() 
			 && piece.isCastle(initialSquare, finalPosition)) {
				castle = new Castle()
				castle.doIt(board, piece, initialPosition, finalPosition)
				
		}else {
			
			initialSquare.update(initialPosition, new NullPiece())		
			board.squares.put(initialPosition, initialSquare)
			
			def finalSquare = board.squares.get(finalPosition)
			finalSquare.update(finalPosition, piece)
			board.squares.put(finalPosition, finalSquare)
		
		}	
		castle
	}

	
	class Factory {
		
		BoardFactory factory
		def piece, initialPosition, finalPosition
		Factory(BoardFactory factory, piece, initialPosition, finalPosition) {
			this.factory = factory
			this.piece = piece 
			this.initialPosition = initialPosition 
			this.finalPosition = finalPosition
		}
		
		void move() {
			def square = board.squares.get(initialPosition)
			factory.verifyIfMovementIsValid(piece, initialPosition, finalPosition, square)
			 
			//Atualiza a square(apenas para verificar se esta em cheque
			square = board.squares.get(finalPosition)
			
			def castle = factory.updateSquaresContent(initialPosition, finalPosition, piece)
			try {
				def content =castle !=null ? castle.king : square.content
				factory.verifyIfKingIsInCheck(content, true, initialPosition, finalPosition, false)
			}finally {
				//DESFAZ as alteracoes
				factory.undoUpdateSquaresContent(finalPosition, initialPosition, castle)
			}
			
			square = board.squares.get(initialPosition)
			factory.verifyIfMovementIsValid(piece, initialPosition, finalPosition, square)
			
			factory.updateSquaresContent(initialPosition, finalPosition, piece)
			
			factory.verifyIfKingIsInCheck(piece, false, initialPosition, finalPosition, true)
			
			factory.atualizaVezDoJogador(piece)
			
			if (board.application)
				board.registerMoveLine(piece, initialPosition, finalPosition, castle)
		}
	}
}
