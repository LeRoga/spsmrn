package greatsoup;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Game extends JFrame {
 private static Game game_game;
 private static long last_frame_time;
 private static Image fon;
 private static Image spsmrn;
 private static Image spsmrn1;
 private static Image spsmrn2;

 private static Image plan;
 private static Image sop;
 private static Image expl;
 private static float drop_left = 200;
 private static float drop_top = -100;
 private static float drop_v = 200;
 private static int score = 0;
 public static void main(String[] args) throws IOException {
 fon= ImageIO.read(Game.class.getResourceAsStream("fon.jpg"));
 spsmrn = ImageIO.read(Game.class.getResourceAsStream("spsmrn.jpg"));
 spsmrn1 = ImageIO.read(Game.class.getResourceAsStream("spsmrn1.jpg"));
 spsmrn2 = ImageIO.read(Game.class.getResourceAsStream("spsmrn2.jpg"));
 plan = ImageIO.read(Game.class.getResourceAsStream("plan.jpg"));
 sop = ImageIO.read(Game.class.getResourceAsStream("sop.png"));
 expl = ImageIO.read(Game.class.getResourceAsStream("expl.png"));
 game_game = new Game();
 game_game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
 game_game.setLocation(200, 50);
 game_game.setSize(1200, 800);
 game_game.setResizable(false);
 last_frame_time = System.nanoTime();
 GameField game_field = new GameField();
 game_field.addMouseListener(new MouseAdapter() {
 @Override
 public void mousePressed(MouseEvent e) {
 int x = e.getX();
 int y = e.getY();

 float drop_right = drop_left + expl.getWidth(null);
 float drop_bottom = drop_top + expl.getHeight(null);
 boolean is_drop = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;

 if (is_drop) {
 drop_top = -100;
 drop_left = (int) (Math.random() * (game_field.getWidth() - expl.getWidth(null)));
 drop_v = drop_v + 10;
 score++;
 game_game.setTitle("Score: " + score);
 }
 }
 });
 game_game.add(game_field);
 game_game.setVisible(true);
 }

 private static void onRepaint(Graphics g){
 long current_time = System.nanoTime();
 float delt_time = (current_time - last_frame_time) * 0.000000001f;
 last_frame_time = current_time;
 drop_top = drop_top + drop_v * delt_time;
 g.drawImage(fon, 0, 0, null);
 g.drawImage(spsmrn, 0, 0, null);
 g.drawImage(spsmrn1, 400, 0, null);
 g.drawImage(spsmrn2, 800, 0, null);
 g.drawImage(plan, 500, 500, null);
 g.drawImage(sop, (int) drop_left, (int) drop_top, null);
 if(drop_top > game_game.getHeight()) g.drawImage(expl, 200, 150, null);
 }

 private static class GameField extends JPanel{
 @Override
 protected void paintComponent(Graphics g){

 super.paintComponent(g);
 onRepaint(g);
 repaint();
 }
}
}
