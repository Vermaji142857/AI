male(shantanu). male(chitrangad). male(vichitravirya). male(devavrat). male(pandu). male(dhrutrashtra). male(vidur). male(pandav). male(kaurav). female(dushila).

parent(shantanu,chitrangad). parent(shantanu,vichitravirya). parent(shantanu,devavrat). parent(vichitravirya,pandu). parent(vichitravirya,dhrutrashtra). parent(vichitravirya,vidur). parent(pandu,pandav). parent(dhrutrashtra,kaurav). parent(dhrutrashtra,dushila).

son(X,Y):-male(X) ,parent(Y,X).
daughter(X, Y):- female(X) ,parent(Y ,X).
sibling(X, Y):- parent(Z, X) ,parent(Z,Y).
