import java.util.*;

public class SymbolTableVisitor implements ParserVisitor
{

  SymbolTable symbolTable = new SymbolTable();

  public SymbolTable getSymbolTable() {
    return symbolTable;
  }

  // Initial scope is global.
  // scope only changes at the start of functions so will be set to the
  // function name then.
  Stack<String> scope = new Stack();

  void dump() {
    symbolTable.dump();
  }

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

    return data;
  }

  public Object visit(ASTBooleanAnd node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTBooleanOr node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTBooleanType node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTConstDecl node, Object data) {
    acceptAllChildren(node, data);

    ASTID idNode = (ASTID) node.jjtGetChild(0);
    String id = (String) idNode.value;

    SimpleNode typeNode = (SimpleNode) node.jjtGetChild(1);
    String type = typeNode.toString();

    SimpleNode valueNode = (SimpleNode) node.jjtGetChild(2);
    String value = valueNode.value.toString();

    STC entry = new STC("const", type);
    symbolTable.add(entry, id, scope.peek());

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
    // Add function ID to symbol table first:
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

    STC entry = new STC("function", returnType, "ParamTypes:"+paramTypes);
    symbolTable.add(entry, functionName, scope.peek());

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
    symbolTable.add(entry, id, scope.peek());

    return data;
  }

  public Object visit(ASTParamList node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTPlus node, Object data) {
    acceptAllChildren(node, data);

    return data;
  }

  public Object visit(ASTProgram node, Object data) {
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

    STC entry = new STC("var", type);
    symbolTable.add(entry, id, scope.peek());

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
