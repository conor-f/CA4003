// Name:PrintVisitor.java
// Author: David Sinclair      Date: 29 Aug 2012
//
// Visitor for "pretty printing" an abstract syntax tree in the ExprLang language
//

public class ParserPrintVisitor implements ParserVisitor
{

  // How many \ts to print before printing the node.
  private int indentLevel = 0;

  private String getTabs() {
    String return_me = "";

    for(int i=0; i<indentLevel; i++)
      return_me += "  ";

    return return_me;
  }

  // Accepts and prints a node and its children.
  private void acceptAndPrintChildren(Node node, Object data, String nodeName) {
    System.out.println(getTabs()+nodeName+" BEGIN");
    indentLevel++;

    for(int i=0; i<node.jjtGetNumChildren(); i++) {
      node.jjtGetChild(i).jjtAccept(this, data);
    }

    indentLevel--;
    System.out.println(getTabs()+nodeName+" END");
  }

  public Object visit(SimpleNode node, Object data)
  {
    throw new RuntimeException("Visit SimpleNode");
  }

  public Object visit(ASTArguments node, Object data) {
    acceptAndPrintChildren(node, data, "Arguments");

    return data;
  }

  public Object visit(ASTAssign node, Object data) {
    acceptAndPrintChildren(node, data, "Assign");

    return data;
  }

  public Object visit(ASTBooleanAnd node, Object data) {
    acceptAndPrintChildren(node, data, "BooleanAnd");

    return data;
  }

  public Object visit(ASTBooleanOr node, Object data) {
    acceptAndPrintChildren(node, data, "BooleanOr");

    return data;
  }

  public Object visit(ASTBooleanType node, Object data) {
    acceptAndPrintChildren(node, data, "BooleanType");

    return data;
  }

  public Object visit(ASTConstDecl node, Object data) {
    //acceptAndPrintChildren(node, data, "ConstDecl");
    System.out.println(getTabs()+"ConstDecl BEGIN");
    indentLevel++;

    for(int i=0; i<node.jjtGetNumChildren(); i++) {
      node.jjtGetChild(i).jjtAccept(this, data);
    }

    indentLevel--;
    System.out.println(getTabs()+"ConstDecl END");

    return data;
  }

  public Object visit(ASTDeclList node, Object data) {
    acceptAndPrintChildren(node, data, "DeclList");

    return data;
  }

  public Object visit(ASTEmptyStatementBlock node, Object data) {
    acceptAndPrintChildren(node, data, "EmptyStatementBlock");

    return data;
  }

  public Object visit(ASTEqual node, Object data) {
    acceptAndPrintChildren(node, data, "Equal");

    return data;
  }

  public Object visit(ASTFalse node, Object data) {
    acceptAndPrintChildren(node, data, "False");

    return data;
  }

  public Object visit(ASTFunction node, Object data) {
    acceptAndPrintChildren(node, data, "Function");

    return data;
  }

  public Object visit(ASTFunctionList node, Object data) {
    acceptAndPrintChildren(node, data, "FunctionList");

    return data;
  }

  public Object visit(ASTGreaterThanEqual node, Object data) {
    acceptAndPrintChildren(node, data, "GreaterThanEqual");

    return data;
  }

  public Object visit(ASTGreaterThan node, Object data) {
    acceptAndPrintChildren(node, data, "GreaterThan");

    return data;
  }

  public Object visit(ASTID node, Object data) {
    System.out.println(getTabs()+"ID BEGIN");
    indentLevel++;

    System.out.println(getTabs()+node.value);

    indentLevel--;
    System.out.println(getTabs()+"ID END");

    return data;
  }

  public Object visit(ASTIfStatement node, Object data) {
    acceptAndPrintChildren(node, data, "IfStatement");

    return data;
  }

  public Object visit(ASTIntegerType node, Object data) {
    acceptAndPrintChildren(node, data, "IntegerType");

    return data;
  }

  public Object visit(ASTLessThanEqual node, Object data) {
    acceptAndPrintChildren(node, data, "LessThanEqual");

    return data;
  }

  public Object visit(ASTLessThan node, Object data) {
    acceptAndPrintChildren(node, data, "LessThan");

    return data;
  }

  public Object visit(ASTMain node, Object data) {
    acceptAndPrintChildren(node, data, "Main");
    return data;
  }

  public Object visit(ASTMethodCall node, Object data) {
    acceptAndPrintChildren(node, data, "MethodCall");

    return data;
  }

  public Object visit(ASTMinus node, Object data) {
    acceptAndPrintChildren(node, data, "Minus");

    return data;
  }

  public Object visit(ASTNegativeID node, Object data) {
    acceptAndPrintChildren(node, data, "NegativeID");

    return data;
  }

  public Object visit(ASTNegNum node, Object data) {
    acceptAndPrintChildren(node, data, "NegNum");

    return data;
  }

  public Object visit(ASTNotCondition node, Object data) {
    acceptAndPrintChildren(node, data, "NotCondition");

    return data;
  }

  public Object visit(ASTNotEqual node, Object data) {
    acceptAndPrintChildren(node, data, "NotEqual");

    return data;
  }

  public Object visit(ASTNum node, Object data) {
    System.out.println(getTabs()+"Num BEGIN");
    indentLevel++;

    System.out.println(getTabs()+node.value);

    indentLevel--;
    System.out.println(getTabs()+"Num END");

    return data;
  }

  public Object visit(ASTParam node, Object data) {
    acceptAndPrintChildren(node, data, "Param");

    return data;
  }

  public Object visit(ASTParamList node, Object data) {
    acceptAndPrintChildren(node, data, "ParamList");

    return data;
  }

  public Object visit(ASTPlus node, Object data) {
    acceptAndPrintChildren(node, data, "Plus");

    return data;
  }

  public Object visit(ASTProgram node, Object data) {
    acceptAndPrintChildren(node, data, "Program");

    return data;
  }

  public Object visit(ASTReturnStatement node, Object data) {
    acceptAndPrintChildren(node, data, "ReturnStatement");

    return data;
  }

  public Object visit(ASTScopingBlock node, Object data) {
    acceptAndPrintChildren(node, data, "ScopingBlock");

    return data;
  }

  public Object visit(ASTSkipStatement node, Object data) {
    acceptAndPrintChildren(node, data, "SkipStatement");

    return data;
  }

  public Object visit(ASTStatementBlock node, Object data) {
    acceptAndPrintChildren(node, data, "StatementBlock");

    return data;

  }

  public Object visit(ASTTrue node, Object data) {
    acceptAndPrintChildren(node, data, "True");

    return data;
  }

  public Object visit(ASTVarDecl node, Object data) {
    acceptAndPrintChildren(node, data, "VarDecl");

    return data;
  }

  public Object visit(ASTVoidType node, Object data) {
    acceptAndPrintChildren(node, data, "VoidType");

    return data;
  }

  public Object visit(ASTWhileStatement node, Object data) {
    acceptAndPrintChildren(node, data, "WhileStatement");

    return data;
  }
}
