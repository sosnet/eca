/*
    This file is part of JLog.

    Created by Glendon Holst for Alan Mackworth and the 
    "Computational Intelligence: A Logical Approach" text.
    
    Copyright 1998, 2000, 2002 by University of British Columbia and 
    Alan Mackworth.
    
    This notice must remain in all files which belong to, or are derived 
    from JLog.
    
    Check <http://jlogic.sourceforge.net/> or 
    <http://sourceforge.net/projects/jlogic> for further information
    about JLog, or to contact the authors.
     
    JLog is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    JLog is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with JLog; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    URLs: <http://www.fsf.org> or <http://www.gnu.org>
*/
//#########################################################################
//	pParens
//#########################################################################

package ubc.cs.JLog.Parser;

import java.util.*;
import java.lang.*;
import ubc.cs.JLog.Terms.*;

class pParens extends pPacket
{ 
 protected pPacket 		inside;
 
 public 	pParens(pStartParen pt,pPacket i)
 {
  super(pt);
  inside = i;
 };
 
 public 	pParens(pStartBrace pt,pPacket i)
 {
  super(pt);
  inside = i;
 };
 
 public jTerm 		getTerm(pVariableRegistry vars,pTermToPacketHashtable phash)
 {
  return getInside().getTerm(vars,phash);
 };

 public pPacket 	getInside()
 {
  if (inside == null)
   throw new SyntaxErrorException("Term required after '(' at ",
                                   token.getPosition(),token.getLine(),token.getCharPos()); 
  return inside;
 };

 public void 		setGeneric(boolean genericpred)
 {
  if (inside != null)
   inside.setGeneric(genericpred);
 };
};

