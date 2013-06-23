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
//	APIQueens
//#########################################################################

import java.util.*;
import java.io.*;
import ubc.cs.JLog.Foundation.*;

/**
 * This class demonstrates several features of the API in action.
 * This example is automatically built as part of: make build ; make install
 * Run from JLog/JLog directory as: java -jar APIQueens.jar 8
 * Note: Running without a parameter results in a prompt for user input (as specified within the query). 
 *
 * @author       Ulf Dittmer, Glendon Holst
 * @version      %I%, %G%
 */

public class APIQueens 
{
 public static void main (String argv[]) throws FileNotFoundException, IOException
 {iPrologFileServices   fs = new jPrologFileServices();
  PrintWriter			out = new PrintWriter(System.out);
  BufferedReader		in = new BufferedReader(new InputStreamReader(System.in));
  InputStream			is = new FileInputStream(new File("queens.plog"));
  jPrologAPI			api = new jPrologAPI(is,fs,out,in,null);
  Hashtable				bindings = new Hashtable();
  Hashtable				result;
  StringBuffer			qbuff = new StringBuffer();

  System.out.println(api.getRequiredCreditInfo());

  if (argv.length > 0)
   bindings.put("B",new Integer(Integer.parseInt(argv[0])));

  qbuff.append("(var(B), write('enter board size:'), read(B) ; true), ! , queens(B,X).");

  result = api.query(qbuff.toString(),bindings);

  while (result != null)
  {
   System.out.println("The result is: " + result.get("X").toString());
   
   result = api.retry();
  }
  
  System.out.println("That's Every Solution!");
 };
};
