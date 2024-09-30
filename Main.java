public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        try {
            game.loadGame("jumpin77.txt");
            game.play();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
