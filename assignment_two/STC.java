// Name:STC.java
// Author: David Sinclair      Date: 29 Aug 2012
//
// Very basic Symbol Table implementation
//

import java.util.*;

public class STC extends Object
{
  String type;
  String value;
  String extras;

  public STC(String itype, String ivalue)
  {
    this(itype, ivalue, "");
  }

  public STC(String itype, String ivalue, String extrass) {
    type = itype;
    value = ivalue;
    extras = extrass;
  }
    
  public void dump() {
    System.out.println("\t\treturn type: " + type);
    System.out.println("\t\tvalue: " + value);
    System.out.println("\t\textras: " + extras);
  }
}
