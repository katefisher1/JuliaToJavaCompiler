
/* CLASS: CS 4308 Section 02
 * TERM: Fall 2021
 * NAME: Kate Fisher
 * INSTRUCTOR: Sharon Perry
 * PROJECT: Deliverable 3 Interpreter
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {

    ArrayList<String> parseTree;
    HashMap<String, Integer> symbolTable = new HashMap<String, Integer>();

    public Interpreter(ArrayList<String> myParseTree) {
        parseTree = myParseTree;
    }

    public void interpret() {

        System.out.println("************************** INTERPRETATION **************************");

        while (!parseTree.isEmpty()) {
            switch (whichStatements()) {
                //program
                case 1:
                    // consume <program> and -> elements of arrayList
                    for(int i = 0; i < 2; i ++) {
                        removeFirstIndex();
                    }
                    break;

                //functions
                case 2:
                    //consumes function, stores function declaration variable, consumes parenthesis, consumes end of function
                    removeFirstIndex();

                    //save function name
                    symbolTable.put(parseTree.get(0), null);

                    //consume function name and parenthesis tokens
                    for(int i = 0; i < 3; i ++) {
                        removeFirstIndex();
                    }
                    //consumes end of function token
                    removeLastIndex();

                    break;

                //assignment statements & variables
                case 3:
                    if (parseTree.get(1).equals(" = ")) {
                        symbolTable.put(parseTree.get(0), Integer.parseInt(parseTree.get(2).replaceAll("\\s", "")));
                    } else if (parseTree.get(1).equals(" += ")) {
                        symbolTable.replace(parseTree.get(0), symbolTable.get(parseTree.get(0)), (symbolTable.get(parseTree.get(0)) + Integer.parseInt(parseTree.get(2).replaceAll("\\s", ""))));
                    }
                    for(int i = 0; i < 3; i ++) {
                        removeFirstIndex();
                    }

                    break;

                //while statement
                case 4:
                    removeFirstIndex();

                    if (parseTree.get(1).equals(" < ")) {

                        while (symbolTable.get(parseTree.get(0)) < Integer.parseInt(parseTree.get(2).replaceAll("\\s", ""))) {

                            if (parseTree.get(5).equals(" += ")) {
                                int temp = symbolTable.get(parseTree.get(4));
                                temp += Integer.parseInt(parseTree.get(6).replaceAll("\\s", ""));

                                symbolTable.replace(parseTree.get(0), symbolTable.get(parseTree.get(0)), temp);
                            }
                        }
                    }
                    //consume all elements associate with while
                    for (int i = 0; i < 8; i++) {
                        removeFirstIndex();
                    }

                    break;

                //if statement
                case 5:
                    removeFirstIndex();

                    if (parseTree.get(1).equals(" != ")) {

                        if (symbolTable.get(parseTree.get(0)) != Integer.parseInt(parseTree.get(2).replaceAll("\\s", ""))) {

                            for (int i = 0; i < 4; i++) {
                                removeFirstIndex();
                            }

                            if (parseTree.get(0).equals(" print ")) {
                                for (int i = 0; i < 2; i++) {
                                    removeFirstIndex();
                                }
                                System.out.println(parseTree.get(0));

                                for (int i = 0; i < 7; i++) {
                                    removeFirstIndex();
                                }
                            }

                        } else {
                            for(int i = 0; i < 9; i ++) {
                                removeFirstIndex();
                            }

                            if (parseTree.get(0).equals(" print ")) {
                                for (int i = 0; i < 2; i++) {
                                    removeFirstIndex();
                                }
                                System.out.println(parseTree.get(0));
                                removeFirstIndex();
                                removeFirstIndex();
                            }
                        }
                        removeFirstIndex();
                    }
                    break;

                //printing
                case 6:
                    System.out.println(symbolTable.get(parseTree.get(2)));
                    for (int i = 0; i < 4; i++) {
                        removeFirstIndex();
                    }

                    break;

                default:
                    printError();
                }
            }
        }

    public int whichStatements() {
        if (parseTree.get(0).equals(" <program> ")){
            return 1;
        } else if (parseTree.get(0).equals(" function ")){
            return 2;
        } else if (parseTree.get(0).matches("[\\s][a-z][\\s]")) {
            return 3;
        } else if (parseTree.get(0).equals(" while ")){
            return 4;
        } else if (parseTree.get(0).equals(" if ")){
            return 5;
        } else if (parseTree.get(0).equals(" print ")){
            return 6;
        } else {
            return -1;
        }
    }

    public void printError() {
        System.out.print("ERROR DETECTED DURING INTERPRETATION");
    }

    public void removeFirstIndex() {
        parseTree.remove(0);
    }

    public void removeLastIndex() {
        parseTree.remove(parseTree.size() - 1);
    }

}
