This project is a sudoku game developed in Java using the swing framework for the graphical user interface (GUI). It follows the model-view-controller (MVC) design pattern to separate the logic, presentation, and user interaction of the game. The game has two modes: design mode and play mode. 

In design mode, the user can create their own sudoku board configuration from scratch or modify an existing one. The board size can be 4x4, 9x9, or 16x16, with corresponding subgrids of 2x2, 3x3, or 4x4. The user can save their configuration and load it later for playing.

In play mode, the user can choose a difficulty level (easy, medium, or hard) and play a sudoku board that is partially filled with numbers. The user has to fill in the remaining cells with numbers that follow the sudoku rules: no repeated numbers in any row, column, or subgrid.

The project also implements a client-server architecture using threads. A server can be launched at a specific port and it can communicate with multiple clients (players) simultaneously. A client can request a sudoku board configuration from the server and play it. When the client finishes the game, 
the details of their performance (such as time, score, and errors) 
are sent back to the server and recorded.
