/**
 * Klasa pomocnicza z zapisem stanu gry
 */

class GameStatus{

    public static int score;  //liczba zgromadzonych punktow za dobre odpowiedzi
    public static int false_score; //liczba zgromadzonych punktow za zle odpowiedzi
    public int level; //poziom gry


    public void reset(){
        score=0;
        false_score=0;
        level=1;

    }

    public void resetPoints(){
        score=0;
    }   // zerowanie punktow
    public void resetFalse_score(){
       false_score=0;
    } // zerowanie zlych odpowiedzi


}
