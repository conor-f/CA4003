import java.util.Hashtable;
import java.util.Enumeration;

public class SymbolTable {
  // This is a hashtable of hashtables.
  Hashtable ST = new Hashtable();


  public void dump() {
    System.out.println();
    System.out.println("SYMBOL TABLE:");

    Enumeration scopes = ST.keys();
    while(scopes.hasMoreElements()) {
      String scope = (String) scopes.nextElement();
      System.out.println("SCOPE: "+scope);
      Hashtable scopedTable = (Hashtable) ST.get(scope);

      Enumeration scoped = scopedTable.keys();
      while(scoped.hasMoreElements()) {
        String k = (String) scoped.nextElement();
        STC temp2 = (STC) scopedTable.get(k);

        System.out.println("\t"+ k + ": ");
        temp2.dump();
        System.out.println();
      }
    }
  }

  public void add(STC elem, String id, String scope) {
    Hashtable scopedTable = (Hashtable) ST.get(scope);

    if(scopedTable == null) {
      scopedTable = new Hashtable();
    }

    scopedTable.put(id, elem);
    ST.put(scope, scopedTable);
  }
}
