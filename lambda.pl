v(X):-
        member(X,[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]).

p(X,Y) :- v(X),v(Y).

isLeft(p(AX,AY),p(BX,BY),p(CX,CY)) :-v(AX),v(AY),v(BX),v(BY),v(CX),v(CY), 0< AX*BY+BX*CY+CX*AY-CX*BY-BX*AY-AX*CY.


