// Name:PrintVisitor.java
// Author: David Sinclair      Date: 29 Aug 2012
//
// Visitor for type checking an abstract syntax tree in the ExprLang language
//

import java.util.*;

public class SemanticCheckVisitor implements ParserVisitor
{


  private class SemanticErrorChecker {
    String errorList = "";
    SymbolTable inputSymbolTable;



    public Boolean isDeclaredInScope(String var, String scope) {
      return ((Hashtable)inputSymbolTable.ST.get(scope)).containsKey(var) || ((Hashtable)inputSymbolTable.ST.get("global")).containsKey(var);
    }


    // String ID -> int. If any are > 1, its been declared multiple times.
    Hashtable declTable;

    public void declareID(String id, String scope) {
      String scopedID = scope+"."+id;

      if(declTable.containsKey(scopedID)) {
        this.addError(id+" declared multiple times in "+scope+".\n");
      } else {
        declTable.put(scopedID, true);
      }
    }

    public void checkAssignTypeWithID(String id, String secondID) {
      String neededType = this.getTypeFromID(id);
      String gotType = this.getTypeFromID(secondID);

      if(neededType != gotType) {
        errorList += "Needed type " + neededType + " for ID " + id + " but got type " + gotType + " from ID " + secondID + ".\n";
      }
    }

    public String getTypeFromNode(SimpleNode n) {
      String nType = n.getClass().getName();

      switch(nType) {
        case "ASTID": 
          ASTID IDNode = (ASTID) n;
          String id = IDNode.value.toString();
          return this.getTypeFromID(id);

        case "ASTMethodCall": 
          ASTMethodCall methodCallNode = (ASTMethodCall) n;
          ASTID MethodIDNode = (ASTID) methodCallNode.jjtGetChild(0);
          String methodName = MethodIDNode.value.toString();
          switch(this.getTypeFromID(methodName)) {
            case "integer": return "IntegerType";
            case "boolean": return "BooleanType";
            case "void": return "VoidType";
          }

        case "ASTNum": return "IntegerType";
        case "ASTPlus": return "IntegerType";
        case "ASTMinus": return "IntegerType";
        case "ASTNegativeID": return "IntegerType";

        case "ASTTrue": return "BooleanType";
        case "ASTFalse": return "BooleanType";
        case "ASTEqual": return "BooleanType";
        case "ASTGreaterThan": return "BooleanType";
        case "ASTGreaterThanEqual": return "BooleanType";
        case "ASTLessThan": return "BooleanType";
        case "ASTLessThanEqual": return "BooleanType";
        case "ASTNotEqual": return "BooleanType";
        case "ASTBooleanOr": return "BooleanType";
        case "ASTBooleanAnd": return "BooleanType";

        default: return "";
      }
    }

    public void checkAssignTypes(SimpleNode t1, SimpleNode t2) {
      String neededType = this.getTypeFromNode(t1);
      String gottenType = this.getTypeFromNode(t2);

      if(neededType != gottenType) {
        errorList += "Needed type " + neededType + " but got type " + gottenType + ".\n";
      }
    }

    public String getTypeFromID(String id) {
      try {
        STC res = (STC) ((Hashtable) inputSymbolTable.ST.get(scope.peek())).get(id);
        return res.value;
      } catch (Exception e) {
        try {
          STC res = (STC) ((Hashtable) inputSymbolTable.ST.get("global")).get(id);
          return res.value;
        } catch (Exception f) {
          return "";
        }
      }
    }


    public SemanticErrorChecker(SymbolTable st) {
      inputSymbolTable = st;
      declTable = new Hashtable();
    }

    public void printErrors() {
      System.out.println(errorList);
    }

    public void addError(String error) {
      errorList += error;
    }

    public void dump() {
      System.out.println("Analysis nd stuff...");
    }
  }

  SemanticErrorChecker sec;

  public void printErrors() {
    sec.printErrors();
  }

  void dump() {
    sec.dump();
  }

  Stack<String> scope = new Stack();

  private void acceptAllChildren(Node n, Object d) {
    for(int i=0; i<n.jjtGetNumChildren(); i++) {
      n.jjtGetChild(i).jjtAccept(this, d);
    }
  }







  public Object visit(SimpleNode node, Object data)
  {
    throw new RuntimeException("Visit SimpleNode");
  }

  public Object visit(ASTArguments node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTAssign node, Object data) {
    acceptAllChildren(node, data);

    ASTID lhs = (ASTID) node.jjtGetChild(0);
    SimpleNode rhs = (SimpleNode) node.jjtGetChild(1);

    sec.checkAssignTypes(lhs, rhs);

    return data;
  }

  public Object visit(ASTBooleanAnd node, Object data) {
    SimpleNode firstTermNode = (SimpleNode) node.jjtGetChild(0);
    String firstTerm = sec.getTypeFromNode(firstTermNode);

    SimpleNode secondTermNode = (SimpleNode) node.jjtGetChild(1);
    String secondTerm = sec.getTypeFromNode(secondTermNode);

    if(firstTerm != "BooleanType") {
      sec.addError("First term of AND op is of type "+firstTerm+".\n");
    }
    if(secondTerm != "BooleanType") {
      sec.addError("Second term of AND op is of type "+secondTerm+".\n");
    }

    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTBooleanOr node, Object data) {
    SimpleNode firstTermNode = (SimpleNode) node.jjtGetChild(0);
    String firstTerm = sec.getTypeFromNode(firstTermNode);

    SimpleNode secondTermNode = (SimpleNode) node.jjtGetChild(1);
    String secondTerm = sec.getTypeFromNode(secondTermNode);

    if(firstTerm != "BooleanType") {
      sec.addError("First term of OR op is not of Boolean type.\n");
    }
    if(secondTerm != "BooleanType") {
      sec.addError("Second term of OR op is not of Boolean type.\n");
    }

    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTBooleanType node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTConstDecl node, Object data) {
    ASTID idNode = (ASTID) node.jjtGetChild(0);
    String id = (String) idNode.value;

    SimpleNode typeNode = (SimpleNode) node.jjtGetChild(1);
    String type = typeNode.toString();

    SimpleNode valueNode = (SimpleNode) node.jjtGetChild(2);
    String value = valueNode.value.toString();

    STC entry = new STC("const", type);

    sec.declareID(id, scope.peek());

    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTDeclList node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTEmptyStatementBlock node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTEqual node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTFalse node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTFunction node, Object data) {
    // Add to symbol table first:
    ASTID idNode = (ASTID) node.jjtGetChild(1);
    String functionName = (String) idNode.value;

    SimpleNode typeNode = (SimpleNode) node.jjtGetChild(0);
    String returnType = typeNode.value.toString();

    ASTParamList paramsNode = (ASTParamList) node.jjtGetChild(2);
    String paramTypes = "";
    for(int i=0; i<paramsNode.jjtGetNumChildren(); i++) {
      ASTParam p = (ASTParam) paramsNode.jjtGetChild(i);
      paramTypes += "|"+p.jjtGetChild(1).toString();
    }

    sec.declareID(functionName, scope.peek());

    STC entry = new STC("function", returnType, "ParamTypes:"+paramTypes);

    // Then change scope:
    scope.push(functionName);

    acceptAllChildren(node, data);

    // Finally back to previous scope:
    scope.pop();

    return data;
  }

  public Object visit(ASTFunctionList node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTGreaterThanEqual node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTGreaterThan node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTID node, Object data) {
    acceptAllChildren(node, data);

    String id = (String) node.value;

    if(!sec.isDeclaredInScope(id, scope.peek())) {
      sec.addError("id " + id + " is not declared in scope " + scope.peek() + ".\n");
    }

    //SimpleNode typeNode = (SimpleNode) node.jjtGetChild(1);
    //String type = typeNode.toString();

    //STC entry = new STC("var", type);

    return data;
  }

  public Object visit(ASTIfStatement node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTIntegerType node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTLessThanEqual node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTLessThan node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTMain node, Object data) {
    // Change scope:
    scope.push("main");

    acceptAllChildren(node, data);

    // Finally back to previous scope:
    scope.pop();

    return data;
  }

  public Object visit(ASTMethodCall node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTMinus node, Object data) {
    SimpleNode firstTermNode = (SimpleNode) node.jjtGetChild(0);
    String firstTerm = sec.getTypeFromNode(firstTermNode);

    SimpleNode secondTermNode = (SimpleNode) node.jjtGetChild(1);
    String secondTerm = sec.getTypeFromNode(secondTermNode);

    if(firstTerm != "IntegerType") {
      sec.addError("First term of minus op is not of integer type.\n");
    }
    if(secondTerm != "IntegerType") {
      sec.addError("Second term of minus op is not of integer type.\n");
    }

    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTNegativeID node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTNegNum node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTNotCondition node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTNotEqual node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTNum node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTParam node, Object data) {
    acceptAllChildren(node, data);

    ASTID idNode = (ASTID) node.jjtGetChild(0);
    String id = (String) idNode.value;

    SimpleNode typeNode = (SimpleNode) node.jjtGetChild(1);
    String type = typeNode.toString();

    STC entry = new STC("var", type);
    sec.declareID(id, scope.peek());
    //symbolTable.add(entry, id, scope.peek());

    return data;
  }

  public Object visit(ASTParamList node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTPlus node, Object data) {
    SimpleNode firstTermNode = (SimpleNode) node.jjtGetChild(0);
    String firstTerm = sec.getTypeFromNode(firstTermNode);

    SimpleNode secondTermNode = (SimpleNode) node.jjtGetChild(1);
    String secondTerm = sec.getTypeFromNode(secondTermNode);

    if(firstTerm != "IntegerType") {
      sec.addError("First term of add op is not of integer type.\n");
    }
    if(secondTerm != "IntegerType") {
      sec.addError("Second term of add op is not of integer type.\n");
    }

    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTProgram node, Object data) {
    sec = new SemanticErrorChecker((SymbolTable) data);
    // Now starting:
    scope.push("global");

    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTReturnStatement node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTScopingBlock node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTSkipStatement node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTStatementBlock node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTTrue node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTVarDecl node, Object data) {
    acceptAllChildren(node, data);

    ASTID idNode = (ASTID) node.jjtGetChild(0);
    String id = (String) idNode.value;

    SimpleNode typeNode = (SimpleNode) node.jjtGetChild(1);
    String type = typeNode.toString();

    sec.declareID(id, scope.peek());

    STC entry = new STC("var", type);

    return data;
  }

  public Object visit(ASTVoidType node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTWhileStatement node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }
}
