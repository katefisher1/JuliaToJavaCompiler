/* CLASS: CS 4308 Section 02
 * TERM: Fall 2021
 * NAME: Kate Fisher
 * INSTRUCTOR: Sharon Perry
 * PROJECT: Deliverable 1 Scanner
 */

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

public class LexicalAnalyzer {
    String file;
    static ArrayList<Tokens> foundTokens = new ArrayList<Tokens>();
    static Hashtable<String, String> juliaTokens = new Hashtable<String, String>();

    public LexicalAnalyzer(String thisFile) {
        file = thisFile;
    }

    public ArrayList<Tokens> scan() throws FileNotFoundException{

        // build dictionary of valid Julia tokens that are present in test files
        juliaTokens.put("function", "FUNCTION");
        juliaTokens.put("a", "IDENTIFIER");
        juliaTokens.put("(", "OPEN_PARENTHESIS");
        juliaTokens.put(")", "CLOSE_PARENTHESIS");
        juliaTokens.put("x", "IDENTIFIER");
        juliaTokens.put("=", "ASSIGNMENT_IDENTIFIER");
        juliaTokens.put("1", "IDENTIFIER");
        juliaTokens.put("2", "IDENTIFIER");
        juliaTokens.put("if", "IF");
        juliaTokens.put("!=", "NE_OPERATOR");
        juliaTokens.put("then", "THEN");
        juliaTokens.put("print", "PRINT");
        juliaTokens.put("0", "IDENTIFIER");
        juliaTokens.put("else", "ELSE");
        juliaTokens.put("end", "END");
        juliaTokens.put("while", "WHILE");
        juliaTokens.put("do", "DO");
        juliaTokens.put("+=", "PLUS_EQ");
        juliaTokens.put("<", "LESS");
        juliaTokens.put("4", "IDENTIFIER");

        //scan test file into string
        String content = new Scanner(new File(file)).useDelimiter("\\Z").next();

        //print to verify
        System.out.println("Contents of the test file: ");
        System.out.println(content);

        System.out.println("-------------------------------------------------");

        // String array that holds file contents with tabs removed and separated by line
        String[] rawLines = content.replace("\t", "").replace("\r", "").split(System.lineSeparator());


        // remove comments
        for (int i = 0; i < rawLines.length; i ++) {

            if (rawLines[i].startsWith("//")) {
                rawLines[i] = "";
            }
        }

        // String array converted to Array List
        List<String> lines = Arrays.asList(rawLines);

        // remove empty strings
        lines = lines.stream().filter(x-> !(x.isEmpty())).collect(Collectors.toList());

        //multidimensional array to break strings by spaces. Each element in tokensByLine array contains
        //array of possible tokens separated by spaces
        String[][] tokensByLine = new String[lines.size()][];

        //foreach separate by space
        for (int i = 0; i < lines.size(); i ++) {
            tokensByLine[i] = lines.get(i).split(" ");
        }

        //for each element check to see if string is key in juliaToken hash, if it is build token and add to foundToken arraylist
        //otherwise pass string into findLexeme function to further parse to detect lexemes.
        for (int i = 0; i < tokensByLine.length; i ++) {
            for (int j = 0; j < tokensByLine[i].length;  j++) {
                if (juliaTokens.containsKey(tokensByLine[i][j])) {
                    Tokens token = new Tokens((tokensByLine[i][j]), juliaTokens.get(tokensByLine[i][j]));
                    foundTokens.add(token);
                } else {
                    findLexeme(tokensByLine[i][j]);
                }
            }
        }
        return foundTokens;
    }

    static void findLexeme(String s) {

        String temp = "";

        //while the string is not empty
        while (s.length() > 0) {
            int leadingCursor = 1;
            int trailingCursor = 0;

            //initialize temp substring
            if (s.length() > 1) {
                temp = s.substring(trailingCursor, leadingCursor);

            } else {//string only has one character
                temp = s;
            }

            //check if substring is valid Julia token.. if it is add token to foundTokens list,
            //otherwise build temp out one more character and check
            while (!juliaTokens.containsKey(temp)) {
                leadingCursor++;
                temp = s.substring(trailingCursor, leadingCursor);
            }
            Tokens token = new Tokens(temp, juliaTokens.get(temp));
            foundTokens.add(token);

            s = s.substring(leadingCursor);
        }
    }

    public void printTokens(ArrayList<Tokens> foundTokens) {

        System.out.println("LEXEMES\t\t\t\t\t\tTOKENS");
        System.out.println("-------------------------------------------------");
        foundTokens.forEach(x -> System.out.print(x.toString()));
        System.out.println("-------------------------------------------------");


    }

}
