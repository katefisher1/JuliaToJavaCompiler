
/* CLASS: CS 4308 Section 02
 * TERM: Fall 2021
 * NAME: Kate Fisher
 * INSTRUCTOR: Sharon Perry
 * PROJECT: Deliverable 2 Parser
 */

import java.lang.annotation.Target;
import java.util.ArrayList;

public class SyntaxAnalyzer {

    ArrayList<Tokens> foundTokens;
    ArrayList<String> parseTree = new ArrayList<String>();
    int cursor = 0;

    //constructor
    public SyntaxAnalyzer(ArrayList<Tokens> myFoundTokens) {
        foundTokens = myFoundTokens;
    }

    // function that checks for conformance to Julia grammar and builds parse tree, prints leftmost derivation
    public ArrayList<String> parse() {

        System.out.println("************************** PARSING **************************");
        parseTree.add(" <program> ");
        parseTree.add(" -> ");

        // does the file/associated tokens contain a function
        if (isFunction(foundTokens.get(cursor).token)) {
            parseTree.add(" function ");
            parseTree.add(" id ");
            parseTree.add(" ( ");
            parseTree.add(" ) ");
            parseTree.add(" <block> ");
            parseTree.add(" end ");

            printArrayList(parseTree);

            //while there are still tokens
            while (cursor < foundTokens.size() - 2  ) {
                if (foundTokens.get(cursor).token.equals("FUNCTION")) {
                    parseTree.set(parseTree.indexOf(" id "), foundTokens.get(cursor + 1).lexeme);
                    printArrayList(parseTree);
                    cursor += 4;
                    continue;
                }
                if (isElse(foundTokens.get(cursor).token)) {
                    cursor++;
                }
                if (cursor > 8) {
                    if (isWhile(foundTokens.get(cursor - 8).token)) {
                        cursor++;
                    }
                }

                    switch (whichStatement(foundTokens, cursor)){

                        // ifStatement
                        case 1:
                            if  (foundTokens.size() - (cursor + 15) > 1){
                                parseTree.set(parseTree.indexOf(" <block> "), " <statement> ");
                                parseTree.add(parseTree.indexOf(" <statement> ") + 1, " <block> ");
                            } else {
                                parseTree.set(parseTree.indexOf(" <block> "), " <statement> ");
                            }
                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <statement> "), " <if_statement> ");
                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <if_statement> "), " if ");
                            parseTree.add(parseTree.indexOf(" if ") + 1, " <boolean_expression> ");
                            parseTree.add(parseTree.indexOf(" <boolean_expression> ") + 1, " then ");
                            parseTree.add(parseTree.indexOf(" then ") + 1, " <block> ");
                            parseTree.add(parseTree.indexOf(" <block> ") + 1, " else ");
                            parseTree.add(parseTree.indexOf(" else ") + 1, " <block> ");
                            parseTree.add(parseTree.lastIndexOf(" <block> ") + 1, " end ");
                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <boolean_expression> "), " <arithmetic_expression> ");
                            parseTree.add(parseTree.indexOf(" <arithmetic_expression> ") + 1, " <relative_op> ");
                            parseTree.add(parseTree.indexOf(" <relative_op> ") + 1, " <arithmetic_expression> ");
                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <arithmetic_expression> "), (" " + foundTokens.get(cursor + 1).lexeme + " "));
                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <relative_op> "), (" " + foundTokens.get(cursor + 2).lexeme + " "));
                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <arithmetic_expression> "), (" " + foundTokens.get(cursor + 3).lexeme + " "));
                            printArrayList(parseTree);

                            cursor += 5;
                            break;

                        // assignment Statement
                        case 2:
                            if (cursor > 9) {
                                if (isWhile(foundTokens.get(cursor - 5).token)) {
                                    parseTree.set(parseTree.indexOf(" <block> "), " <statement> ");
                                }

                            } else if  (foundTokens.size() - (cursor + 6) > 1) {
                                parseTree.set(parseTree.indexOf(" <block> "), " <statement> ");
                                parseTree.add(parseTree.indexOf(" <statement> ") + 1, " <block> ");

                            } else {
                                parseTree.set(parseTree.indexOf(" <block> "), " <statement> ");
                            }

                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <statement> "), " <assignment_statement> ");

                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <assignment_statement> "), " id ");
                            parseTree.add(parseTree.indexOf(" id ") + 1, " <assignment_operator> ");
                            parseTree.add(parseTree.indexOf(" <assignment_operator> ") + 1, " <arithmetic_expression> ");

                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" id "), (" " + foundTokens.get(cursor).lexeme) + " ") ;
                            cursor++;

                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <assignment_operator> "), (" " + foundTokens.get(cursor).lexeme) + " ") ;
                            cursor++;

                            printArrayList((parseTree));

                            parseTree.set(parseTree.indexOf(" <arithmetic_expression> "), (" " + foundTokens.get(cursor).lexeme) + " ") ;
                            cursor++;

                            printArrayList(parseTree);

                            break;

                        // while statement
                        case 3:
                            if  (foundTokens.size() - (cursor + 10) > 1){
                                parseTree.set(parseTree.indexOf(" <block> "), " <statement> ");
                                parseTree.add(parseTree.indexOf(" <statement> ") + 1, " <block> ");

                            } else {
                                parseTree.set(parseTree.indexOf(" <block> "), " <statement> ");
                            }

                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <statement> "), " <while_statement> ");

                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <while_statement> "), " while ");
                            parseTree.add(parseTree.indexOf(" while ") + 1, " <boolean_expression> ");
                            parseTree.add(parseTree.indexOf(" <boolean_expression> ") + 1, " do ");
                            parseTree.add(parseTree.indexOf(" do ") + 1, " <block> ");
                            parseTree.add(parseTree.indexOf(" <block> ") + 1, " end ");

                            printArrayList(parseTree);

                            parseTree.set(parseTree.lastIndexOf(" <boolean_expression> "), " <relative_op> ");
                            parseTree.add(parseTree.lastIndexOf(" <relative_op> ") + 1, " <arithmetic_expression> ");
                            parseTree.add(parseTree.lastIndexOf(" <arithmetic_expression> ") + 1, " <arithmetic_expression> ");

                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <relative_op> "), (" " +foundTokens.get(cursor + 1).lexeme) + " ");

                            printArrayList((parseTree));

                            parseTree.set(parseTree.indexOf(" <arithmetic_expression> "), (" " +foundTokens.get(cursor + 2).lexeme) + " ");

                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <arithmetic_expression> "), (" " +foundTokens.get(cursor + 3).lexeme) + " ");

                            printArrayList(parseTree);

                            cursor += 5;

                            if (isEnd(foundTokens.get(cursor).token)) {
                                cursor++;
                            }

                            break;

                        // print statement
                        case 4:
                            parseTree.set(parseTree.indexOf(" <block> "), " <statement> ");

                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <statement> "), " <print_statement> ");

                            printArrayList(parseTree);

                            parseTree.set(parseTree.indexOf(" <print_statement> "), " print ");
                            parseTree.add(parseTree.lastIndexOf(" print ") + 1 , " ( ");
                            parseTree.add(parseTree.lastIndexOf(" ( ") + 1 , " <arithmetic_expression> ");
                            parseTree.add(parseTree.indexOf(" <arithmetic_expression> ") + 1 , " ) ");

                            printArrayList(parseTree);


                            parseTree.set(parseTree.indexOf(" <arithmetic_expression> "), (" " + foundTokens.get(cursor +2).lexeme + " "));

                            cursor += 4;

                            printArrayList(parseTree);

                            break;

                        default :
                            printError();
                            cursor = 100;


                    }
                }
            }
        return parseTree;
        }

    public void printArrayList(ArrayList<String> parseTree){
        for(int i = 0; i < parseTree.size(); i ++) {
            System.out.print(parseTree.get(i));
            }
        System.out.println("");
        }


    // FUNCTIONS FOR CHECKING FOR VALID JULIA GRAMMAR
    public boolean isBlock(ArrayList<Tokens> tokens, int i){
        if (isStatement(tokens, i)) {
            return true;
        }
        return false;
    }

    int whichStatement(ArrayList<Tokens> tokens, int cursor){
        if (isIfStatement(tokens, cursor)){
            return 1;
        } else if (isAssignmentStatement(tokens, cursor)) {
            return 2;
        } else if (isWhileStatement(tokens, cursor)) {
            return 3;
        } else if (isPrintStatement(tokens, cursor)) {
            return 4;
        } else {
            return -1;
        }
    }

    public boolean isStatement(ArrayList<Tokens> tokens, int i){
        if (isIfStatement(tokens, i) ||
                isAssignmentStatement(tokens, i) ||
                isWhileStatement(tokens, i) ||
                isPrintStatement(tokens, i)) {
            return true;
        }
        return false;
    }

    public boolean isIfStatement(ArrayList<Tokens> tokens, int i) {
        if (isIf(tokens.get(i).token) &&
                isBoolExpr(tokens, i+1) &&
                isThen(tokens.get(i+4).token) &&
                isBlock(tokens,i+5) &&
                isElse(tokens.get(i+9).token) &&
                isBlock(tokens, i + 10) &&
                isEnd(tokens.get(i+14).token) &&
                isEnd(tokens.get(i+15).token)){
            return true;
        }
        return false;
    }

    public boolean isAssignmentStatement(ArrayList<Tokens> tokens, int i) {
        if (isIdentifier(tokens.get(i).token) &&
                isAssignmentOp(tokens.get(i+1).token) &&
                isArithmeticExpr(tokens.get(i+2).token)) {
            return true;
        }
        return false;
    }

    public boolean isWhileStatement(ArrayList<Tokens> tokens, int i) {
        if (isWhile(tokens.get(i).token) &&
                isBoolExpr(tokens, i+1) &&
                isDo(tokens.get(i+4).token) &&
                isBlock(tokens, i+5) &&
                isEnd(tokens.get(i+8).token)) {
            return true;
        }
        return false;
    }

    public boolean isPrintStatement(ArrayList<Tokens> tokens, int i) {
        if (tokens.get(i).token.equals("PRINT") &&
                isOpenParen(tokens.get(i + 1).token) &&
                isIdentifier(tokens.get(i+2).token) &&
                isCloseParen(tokens.get(i + 3).token)) {
            return true;
        }
        return false;
    }

    public boolean isArithmeticExpr(String token) {
        if (token.equals("IDENTIFIER") || (token.equals("INTEGER")) || token.equals("PLUS_EQ")) {
            return true;
        }
        return false;
    }

    public boolean isBoolExpr (ArrayList<Tokens> tokens, int i) {
        if (isArithmeticExpr(tokens.get(i).token) &&
                isRelativeOp(tokens.get(i+1).token) &&
                isArithmeticExpr(tokens.get(i+2).token)){
            return true;
        }
        return false;
    }

    public boolean isRelativeOp(String token) {
        if (token.equals("LESS") || token.equals("NE_OPERATOR")) {
            return true;
        }
            return false;
    }

    public boolean  isAssignmentOp(String token) {
        if (token.equals("ASSIGNMENT_IDENTIFIER") || token.equals("PLUS_EQ")) {
            return true;
        }
        return false;
    }

    public boolean isIdentifier(String token) {
        if (token.equals("IDENTIFIER")) {
            return true;
        }
        return false;
    }

    public boolean isEnd(String token) {
        if (token.equals("END")) {
            return true;
        }
        return false;
    }

    public boolean isDo(String token) {
        if (token.equals("DO")) {
            return true;
        }
        return false;
    }

    public boolean isWhile(String token) {
        if (token.equals("WHILE")) {
            return true;
        }
        return false;
    }

    public boolean isOpenParen(String token) {
        if (token.equals("OPEN_PARENTHESIS")) {
            return true;
        }
        return false;
    }

    public boolean isCloseParen(String token) {
        if (token.equals("CLOSE_PARENTHESIS")) {
            return true;
        }
        return false;
    }

    public boolean isFunction(String token) {
        if (token.equals("FUNCTION")) {
            return true;
        }
        return false;
    }

    public boolean isIf(String token) {
        if (token.equals("IF")) {
            return true;
        }
        return false;
    }

    public boolean isElse(String token) {
        if (token.equals("ELSE")) {
            return true;
        }
        return false;
    }

    public boolean isThen(String token) {
        if (token.equals("THEN")) {
            return true;
        }
        return false;
    }

    public void printError() {

        System.out.println("PARSE ERROR DETECTED");
    }
}



