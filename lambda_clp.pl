v(X):- X#>=0, X#=<20. 
p(X,Y) :- v(X),v(Y).

isLeft(AX,AY,BX,BY,CX,CY) :-v(AX),v(AY),v(BX),v(BY),v(CX),v(CY), 0#< AX*BY+BX*CY+CX*AY-CX*BY-BX*AY-AX*CY.





