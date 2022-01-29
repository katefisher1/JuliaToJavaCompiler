/* CLASS: CS 4308 Section 02
 * TERM: Fall 2021
 * NAME: Kate Fisher
 * INSTRUCTOR: Sharon Perry
 * PROJECT: Deliverable 1 Scanner
 */


//Token class for the found lexemes and tokens

 class Tokens {

    String lexeme;
    String token;

    //constructor
    public Tokens (String myLexeme, String myToken) {
        lexeme = myLexeme;
        token = myToken;
    }



    //toString for printing in table format
    @Override
    public String toString() {
        int distance = 28;
        int whiteSpaces = distance - lexeme.length();

        String builder = "";

        builder += lexeme;

        for (int i = 0; i < whiteSpaces; i++) {
            builder += " ";
        }

        builder += token;
        builder += "\n";

        return builder;
    }

    //getters
     public String getLexeme() {
         return lexeme;
     }

     public String getToken() {
         return token;
     }
 }
