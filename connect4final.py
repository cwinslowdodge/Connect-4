#!/usr/bin/env python

import getopt
import sys
import os
import pickle

try:
    opts, args = getopt.getopt(sys.argv[1:], '-w:-h:-s:-c:-l',
                               ['-width=', '-height=', '-square=', '-connect=', '-load='])
except getopt.GetoptError:
    sys.exit(2)


class Player:
    def __init__(self, k, n):
        self.key = k
        self.name = n


class Game:
    def __init__(self, load=False, width=7, height=7, connect=4, square=0):

        self.square = int(square)
        self.connect = int(connect)
        self.player1 = Player(1, 'Player 1')
        self.player2 = Player(2, 'Player 2')
        self.current_player = self.player1
        self.winner = False

        if load:

            self.gameboard = pickle.load(open("gameboard.p", "rb"))
            self.width = int(len(self.gameboard[0]))
            self.height = int(len(self.gameboard))
            self.create_board(self.width, self.height, False, self.connect)
        else:

            if square > 0:
                self.width = self.square
                self.height = self.square
                self.gameboard = []
                self.create_board(self.width, self.height, True, self.connect)
            else:
                self.width = int(width)
                self.height = int(height)
                self.gameboard = []
                self.create_board(self.width, self.height, True, self.connect)

        self.max_moves = self.width * self.height

        m = 0
        while m < self.max_moves and not self.winner:
            self.take_turn()
            m = m + 1

    def print_board(self):
        for e in self.gameboard:
            print e

    def create_board(self, width, height, new_board, connect):
        i = 0
        self.connect = connect
        if new_board:
            while i < height:
                self.gameboard.append([])
                j = 0
                while j < width:
                    self.gameboard[i].append(0)
                    j = j + 1

                i = i + 1

        for e in self.gameboard:
            print e

    def toggle_player(self):
        if self.current_player.key == 1:
            self.current_player = self.player2
        else:
            self.current_player = self.player1

    def take_turn(self):
        # prompt user for input
        move = raw_input(self.current_player.name + ' - input column between 1 and ' + str(self.width) + ':')
        if move.isdigit():
            move = int(move) - 1

        else:
            print "Invalid column selection.  Please try again."
            self.take_turn()

        if move >= 0 and move < self.width:
            self.drop_piece(move)
            self.toggle_player()

        else:
            print "Invalid column selection.  Please try again."
            self.take_turn()

    def horizontal_win(self, row):
        sequence = 0
        column = 0

        while column < self.width:

            if self.gameboard[row][column] == self.current_player.key:
                sequence = sequence + 1

                if sequence == self.connect:
                    self.winner = True
                    self.game_winner()
            else:
                sequence = 0
            column = column + 1

    def vertical_win(self, column):
        row = self.height - 1
        sequence = 0

        while row >= 0:
            if self.gameboard[row][column] == self.current_player.key:
                sequence = sequence + 1

                if sequence == self.connect:
                    self.winner = True
                    self.game_winner()
            else:
                sequence = 0

            row = row - 1

    def diagonal_win(self, row, column):
        max_row = row
        max_col = column
        sequence = 0

        while max_row > 0 and max_col < len(self.gameboard[0]) - 1:
            max_row = max_row - 1
            max_col = max_col + 1

        while max_row < len(self.gameboard) - 1 and max_col > 0:
            max_row = max_row + 1
            max_col = max_col - 1

            if self.gameboard[max_row][max_col] == self.current_player.key:
                sequence = sequence + 1

            else:
                sequence = 0

            if sequence == self.connect:
                self.winner = True
                self.game_winner()
                break

        max_row = row
        min_col = column
        sequence = 0

        # print "max row: " + str(max_row)
        # print 'min column: ' + str(min_col)
        # print self.gameboard[max_row]
        # print self.gameboard[max_row][min_col]


        while max_row > 0 and min_col > 0:
            min_col = min_col - 1
            max_row = max_row - 1

        while max_row < len(self.gameboard) - 1 and min_col < len(self.gameboard[0]) - 1:

            max_row = max_row + 1
            min_col = min_col + 1

            if self.gameboard[max_row][min_col] == self.current_player.key:
                sequence = sequence + 1
            else:
                sequence = 0

            if sequence == self.connect:
                self.winner = True
                self.game_winner()
                # print 'R-L winner: ' + str(self.winner)
                break

    '''
    @TODO: handle case where column is full
    '''

    def drop_piece(self, column):
        row = len(self.gameboard) - 1

        while (row >= 0):
            if self.gameboard[row][column] == 0:
                self.gameboard[row][column] = self.current_player.key
                break

            row = row - 1

        os.system('cls' if os.name == 'nt' else 'clear')

        for slot in self.gameboard:
            print slot

        pickle.dump(self.gameboard, open("./gameboard.p", "wb"))

        self.horizontal_win(row)
        self.vertical_win(column)
        self.diagonal_win(row, column)

    def game_winner(self):
        print str(self.current_player.name) + ' is the winner!'
        restart = raw_input("Would you like to play again? (y/n)")
        if restart == "y":
            self.game = Game(load, width, height, connect)
        else:
            print "Thank you for playing."


load = False
width = 7
height = 7
connect = 4
square = 0

for opt, arg in opts:
    if opt in ('-w', '--width='):
        print('arg: ', arg)
        width = arg
    elif opt in ('-h', '--height='):
        print('arg: ', arg)
        height = arg
    elif opt in ('-s', '--square='):
        square = arg
    elif opt in ('-c', '--connect='):
        print('arg: ', arg)
        connect = arg
    elif opt in ('-l', '--load='):
        load = True

    else:
        sys.exit(2)

game = Game(load, width, height, connect, square)


# todo  connect from save, use lambda expression
# todo   check all win scenarios