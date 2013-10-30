package meu.chess.factory

import static meu.chess.Board.*

import meu.chess.Board
import meu.chess.pieces.Bishop
import meu.chess.pieces.King
import meu.chess.pieces.Knight
import meu.chess.pieces.Pawn
import meu.chess.pieces.Piece
import meu.chess.pieces.Queen
import meu.chess.pieces.Rock

class PieceFactory {

	def static createInitialPieces() {
		def pieces = ["A8":new Rock(BLACK),
			"B8":new Knight(BLACK),
			"C8":new Bishop(BLACK),
			"D8":new Queen(BLACK),
			"E8":new King(BLACK),
			"F8":new Bishop(BLACK),
			"G8":new Knight(BLACK),
			"H8":new Rock(BLACK),
			"A7":new Pawn(BLACK),
			"B7":new Pawn(BLACK),
			"C7":new Pawn(BLACK),
			"D7":new Pawn(BLACK),
			"E7":new Pawn(BLACK),
			"F7":new Pawn(BLACK),
			"G7":new Pawn(BLACK),
			"H7":new Pawn(BLACK),
			"A2":new Pawn(WHITE),
			"B2":new Pawn(WHITE),
			"C2":new Pawn(WHITE),
			"D2":new Pawn(WHITE),
			"E2":new Pawn(WHITE),
			"F2":new Pawn(WHITE),
			"G2":new Pawn(WHITE),
			"H2":new Pawn(WHITE),
			"A1":new Rock(WHITE),
			"B1":new Knight(WHITE),
			"C1":new Bishop(WHITE),
			"D1":new Queen(WHITE),
			"E1":new King(WHITE),
			"F1":new Bishop(WHITE),
			"G1":new Knight(WHITE),
			"H1":new Rock(WHITE),
		   ]
		
		pieces
	}
	
}
