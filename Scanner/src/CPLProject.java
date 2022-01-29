/* CLASS: CS 4308 Section 02
 * TERM: Fall 2021
 * NAME: Kate Fisher
 * INSTRUCTOR: Sharon Perry
 * PROJECT: Concepts of Programming Language Project
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CPLProject {

    public static void main(String[] args) throws FileNotFoundException {

        // test files
        String test1 = "/Users/katefisher/IdeaProjects/Scanner/Test1.jl";
        String test2 = "/Users/katefisher/IdeaProjects/Scanner/Test2.jl";
        String test3 = "/Users/katefisher/IdeaProjects/Scanner/Test3.jl";

        //DELIVERABLE 1: LEXICAL ANALYZER

        //create LexicalAnalyzer object with desired test file
        LexicalAnalyzer la = new LexicalAnalyzer(test2);
        ArrayList<Tokens> foundTokens = la.scan();
        la.printTokens(foundTokens);

        //DELIVERABLE 2: SYNTAX ANALYZER

        //create SyntaxAnalyzer object with ArrayList of foundTokens
        SyntaxAnalyzer sa = new SyntaxAnalyzer(foundTokens);
        ArrayList<String> parseTree = sa.parse();


        //DELIVERABLE 3: INTERPRETER

        Interpreter interpret = new Interpreter(parseTree);
        interpret.interpret();

    }
}
