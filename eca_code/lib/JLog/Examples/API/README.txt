APIQueens Example 

This example demonstrates the use of the PrologAPI to directly access the JLog Prolog Engine.  It consults the queens.plog KB, and performs a query to find all solutions.

Ex. (in JLog install directory) to find all solutions on a regular chess board (8x8): 

java -jar APIQueens.jar 8

NOTE: The query APIQueens source asks the user (from within Prolog) for the board size if it is not provided.  Enter a valid term, terminated by a period (.), and press the return key.