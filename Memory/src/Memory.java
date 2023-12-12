/*
 *
 * Prosta gra polegająca na zapamiętaniu coraz dłuższej sekwencji i powtórzeniu jej
 * Powstała dnia 06.03.2021 w pokoju hotelowym w Dubaju
 * Piotr Jędrzejczak-Rakowski
 *
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Memory extends JFrame implements ActionListener {
    final int gridXY = 3;
    ArrayList < Integer > playerSequence = new ArrayList < Integer > ();
    boolean brk = false;
    JLabel wynik = new JLabel();

    public static void main(String[] args) {
        Memory gra = new Memory();
    }
    public Memory() {
        //Wtępne ustawienia ramki i etykiet

        JButton[][] grid = new JButton[gridXY][gridXY];

        JFrame frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel();
        label.setLayout(new GridLayout(gridXY, gridXY));
        label.setPreferredSize(new Dimension(500, 450));

        wynik.setFont(new Font("Arial", Font.PLAIN, 40));
        wynik.setPreferredSize(new Dimension(500, 50));

        gridFiller(grid, label); // wypełnia macierz 'grid' przyciskami i wrzuca to na label

        frame.add(label, BorderLayout.CENTER);
        frame.add(wynik, BorderLayout.SOUTH);
        frame.setVisible(true);

        while (true) { //zaczyna nową grę i resetuje gre po przegranej
            int[] randomSequence = new int[100];
            sequenceGenerator(randomSequence);
            int licznik = 0;
            playerSequence.clear();
            System.out.println("restart");
            brk = false;
            Wait(200);
            while (licznik < randomSequence.length && !brk) //pętla w której odbywa się gra do skuchy
            {
                Wait(300);
                wynik.setText("Twoj wynik to: " + String.valueOf(licznik));

                //zaciemnia i rozjaśnia odpowiednie buttony w odpowiedniej kolejności
                animator(grid, licznik, randomSequence);

                //'Zatrzymuje' grę dopóki gracz nie wcisnie swojej sekwencji przyciskow
                playerInputWaiter(grid, licznik, playerSequence);

                //Sprawdza czy wcisnieta przez gracza sekwencja jest prawidlowa
                playerInputCheck(randomSequence, grid);
                licznik++;
            }
        }
    }
    public void playerInputCheck(int[] randomSequence, JButton[][] grid) {
        boolean git = true;
        while (true) { //sprawdza czy podana przez gracza sekwencja zgadza sie z wygenerowaną przez komputer
            for (int i = 0; i <= playerSequence.size() - 1; i++) {
                if (randomSequence[i] != playerSequence.get(i)) {
                    git = false;
                }
            }
            if (git) //jak tak to gra idzie dalej
            {
                break;
            } else // jak nie to przyciski blinkają na czerwono i program zaczyna sie od początku
            {
                brk = true;
                colorBlink(Color.RED, grid);
                break;
            }
        }
        playerSequence.clear(); // 'zeruje' sekwencję wprowadzoną przez użytkownika
    }
    //czeka az użytkownik wprowadzi swoja sekwencję
    public void playerInputWaiter(JButton[][] grid, int licznik, ArrayList < Integer > playerSequance) {
        //przyciski blinkaja na zielono dając znać, że czekają na input
        colorBlink(Color.GREEN, grid);

        //program siedzi w nieskończonej pętli dopóki użytkownik nie wprowadzi swojej sekwencji
        boolean stop = false;
        while (!stop) {
            if (playerSequance.size() == licznik + 1) {
                stop = true;
            }
            Wait(0); //jakby tego nie bylo to musiałbym dać boolean stop do zmiennych globalnych
        }

    }
    //liczy koordynaty przycisków i blinka nimi na czarno zgodnie z sekwencją wygenerowaną na początku rozgrywki
    public void animator(JButton[][] grid, int licznik, int[] randomsequence) {
        for (int i = 0; i <= licznik; i++) {
            int x = animatorObliczX(randomsequence[i]);
            int y = animatorObliczY(randomsequence[i]);
            grid[x][y].setBackground(Color.BLACK);
            Wait(250);
            grid[x][y].setBackground(Color.WHITE);
            Wait(250);
        }
    }

    public int animatorObliczX(int input) {
        int x = 0;
        if (input < 3) {
            x = 0;
        } else if (input >= 3 && input < 6) {
            x = 1;
        } else if (input >= 6) {
            x = 2;
        }
        return x;
    }

    public int animatorObliczY(int input) {
        int y = 0;
        if (input % 3 == 0) {
            y = 0;
        } else if (input % 3 == 1) {
            y = 1;
        } else if (input % 3 == 2) {
            y = 2;
        }
        return y;
    }
    public void sequenceGenerator(int[] seq) //generuje losową sekwencję liczb 0-9
    {
        Random rand = new Random();
        for (int i = 0; i < seq.length; i++) {
            seq[i] = rand.nextInt(9);
            Wait(20); //zapobiega zbyt częstemu powtarzaniu się liczb zaraz po sobie kilka razy
        }
    }

    //wstrzymuje wątek na zadany czas
    public static void Wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //blinka wszystkimi przyciskami naraz w danym kolorze
    public void colorBlink(Color color, JButton[][] grid) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setBackground(color);
            }
        }
        Wait(170);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setBackground(Color.WHITE);
            }
        }
    }

    //wypełnia macierz 'grid' przyciskami i wrzuca je na label
    public void gridFiller(JButton[][] grid, JLabel label) {
        int licznik = 0;
        for (int i = 0; i < gridXY; i++) {
            for (int j = 0; j < gridXY; j++) {
                grid[i][j] = new JButton(String.valueOf(licznik));
                grid[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                grid[i][j].setBackground(Color.WHITE);
                grid[i][j].setActionCommand(String.valueOf(licznik)); //przyznaje kazdemu buttonowi numer ID
                grid[i][j].setBorderPainted(false);
                grid[i][j].setFocusPainted(false);
                licznik++;
                grid[i][j].addActionListener(this);
                label.add(grid[i][j]);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        playerSequence.add(Integer.parseInt(e.getActionCommand()));
    }
}