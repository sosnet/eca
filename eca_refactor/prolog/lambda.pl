v(X):- X#>=0, X#=<10.
p(X,Y) :- v(X),v(Y).

isLeft(p(AX,AY),p(BX,BY),p(CX,CY)) :-v(AX),v(AY),v(BX),v(BY),v(CX),v(CY), 0#< AX*BY+BX*CY+CX*AY-CX*BY-BX*AY-AX*CY, labeling([ff],[AX,AY,BX,BY,CX,CY]).
isColl(p(AX,AY),p(BX,BY),p(CX,CY)) :-v(AX),v(AY),v(BX),v(BY),v(CX),v(CY), 0#= AX*BY+BX*CY+CX*AY-CX*BY-BX*AY-AX*CY, labeling([ff],[AX,AY,BX,BY,CX,CY]).





