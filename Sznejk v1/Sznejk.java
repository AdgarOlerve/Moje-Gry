import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

class Sznejk implements KeyListener
{
    static char kierunek = 'G';//zmienna przechowujaca kierunek poruszania sie snejka
    static JFrame frame=new JFrame();//tworzenie ramki okna gry
    static SnakeBody apple = new SnakeBody(9,9);
    static ArrayList<SnakeBody> snek = new ArrayList<SnakeBody>();//tworzy ArrayListę przechowujaca cialo snejka
    static Color cialo=new Color(16,205,60);
    static Color zdobycz = new Color(88,88,88);
    static Color tlo = new Color(203,136,19);
    static boolean restart = false;
    static int speed = 1_000_000_000;
    static double speed_multiplier=0.5;//mnoznik podstawowej szybkosci snejka
    static double acceleration_rate=0.008;//przyspieszenie po zjedzeniu zdobyczy
    static int score=0;
    Sznejk() //konstruktor
    {
        frame.addKeyListener(this); //dodanie KeyListener(ktory pobiera to co nacisnal gracz na klawiaturze i wykonuje odpowiednie akcje)
        System.out.println("KeyListener: OK");
    }
    public static void main(String[] args)//funkcja main
    {
        System.out.println("defaultowe zmienne: OK");
        //main_menu();
        while(true)
        {
            if(restart==true)
            {
                var_restarter();
                restart = false;
            }
            Sznejk ramka = new Sznejk(); //stworzenie obiektu typu Sznejk co wywoluje konstruktor ktory dodaje KeyListenera
            int mapaWiersze=18;//ilosc wierszy mapy
            int mapaKolumny=19;//ilosc kolumn mapy
            int panelWidth=20;//szerokosc jednego panela
            int panelHeight=20;//wysokosc jednego panela
            int gap=5;//przerwa miedzy panelami

            JPanel[][] mapa = new JPanel[mapaWiersze][mapaKolumny];//tworzenie macierzy paneli czyli mapy na ktorej sie gra
            System.out.println("Mapa stworzona: OK");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//po nacisnieciu 'x' na ramce zamknie sie okno gry
            frame.setSize(500,500); //ustawienie rozmiaru ramki
            frame.setLayout(null); //ustawienie Layoutu komponentow ramki
            frame.setResizable(false);//ustawienie czy mozna zmieniac rozmiar ramki
            frame.setLocationRelativeTo(null);//ramka pojawia sie na srodku ekranu
            Mapa(mapaWiersze,mapaKolumny,panelHeight,panelWidth,gap,frame,mapa);//wywolanie metody Mapa i przekazanie jej potrzebnych zmiennych
            System.out.println("Mapa wypelniona: OK");
            frame.setVisible(true);//pokazanie ramki na ekranie
            System.out.println("Okno gry: OK");
            game(mapa);//wywolanie funkcji odpowiedzialnej za gameplay
        }
    }
    public static void main_menu()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel(new BorderLayout(0, 120));

        JButton new_game = new JButton("NOWA GRA");
        new_game.setPreferredSize(new Dimension(300, 70));
        buttonPanel.add(new_game, BorderLayout.PAGE_START);

        JButton options = new JButton("OPCJE");
        options.setPreferredSize(new Dimension(300, 70));
        buttonPanel.add(options, BorderLayout.CENTER);

        JButton exit = new JButton("WYJSCIE");
        exit.setPreferredSize(new Dimension(300, 70));
        buttonPanel.add(exit, BorderLayout.PAGE_END);

        frame.add(buttonPanel);
        frame.setVisible(true);
        int choice = 0;
        while (true)
        {
            if (choice == 1)
            {
                break;
            }
            else if (choice == 2)
            {
                //options();
                break;
            }
            else if (choice == 3)
            {
                System.exit(0);
            }
        }
    }
    public static void var_restarter()//przywraca zmienne globalne do stanu domyslnego po restarcie gry
    {
        kierunek = 'G';
        frame=new JFrame();
        apple = new SnakeBody(9,9);
        snek = new ArrayList<SnakeBody>();
        score=0;
        System.out.println("Restart zakonczony pomyslnie");
    }
    public static void Mapa(int wiersze,int kolumny,int panelH,int panelW,int gap,JFrame frame,JPanel[][] mapa)
    {
        int panelX=0;
        int panelY=0;
        for(int i=0;i<wiersze;i++)//dwie petle for tworzace kolejne panele,pozycjonujace je w odpowiednich miejscach
        {							//
            for(int j=0;j<kolumny;j++)//kolorujace a nastepnie dodajace panele na ramke
            {
                mapa[i][j]=new JPanel();
                mapa[i][j].setBounds(panelX+gap,panelY+gap,panelH,panelW);
                mapa[i][j].setBackground(tlo);
                panelX=panelX+panelW+gap;
                frame.add(mapa[i][j]);


            }
            panelX=0;
            panelY=panelY+panelH+gap;
        }
        apple=Randomizer(18,19);
        mapa[apple.getX()][apple.getY()].setBackground(zdobycz);
    }

    public static void game(JPanel[][] mapa)//metoda odpowiedzialna za gameplay
    {
        System.out.println("Main loop: OK");
        SnakeBody head = new SnakeBody(0,0);
        head=Randomizer(18,19);
        snek.add(head);//dodaje glowe na dole na srodku planszy
        SnakeBody bufor = snek.get(0);
        mapa[bufor.getX()][bufor.getY()].setBackground(cialo);//ustawia kolor
        long start=0;//zmienne do obslugi czasu
        long end=0;
        long TimePassed=0;
        while(restart==false)
        {
            start=System.nanoTime();

            while(TimePassed<1)//"zatrzymuje" program na jakiś czas,od tego zalezy szybkosc snejka
            {
                end=System.nanoTime();
                TimePassed=(end-start)/(long)(speed*speed_multiplier);
            }

            TimePassed=0;
            mover(mapa);//bierze aktualna mape,ArrayListe z polozeniem czesci snejka i kierunek wybrany przez gracza
            //a nastepnie rysuje nastepny "krok" snejka
        }

    }
    public static void mover(JPanel[][] mapa)
    {
        boolean apple_ate=false;//czy jablko jest zjedzone
        SnakeBody head=snek.get(0);//przechowuje koordy glowy
        SnakeBody bufor=new SnakeBody(0,0);
        SnakeBody nextPos=new SnakeBody(0,0);//przechowuje miejsce,w ktorym wyladuje glowa po kolejnym kroku
        //ustawienie nowych koordynatow nextPos,w zaleznosci od wybranego kierunku
        if(kierunek=='G')//gora
        {
            nextPos.setX(head.getX()-1);
            nextPos.setY(head.getY());
        }
        else if(kierunek=='D')//dol
        {
            nextPos.setX(head.getX()+1);
            nextPos.setY(head.getY());
        }
        else if(kierunek=='L')//lewo
        {
            nextPos.setX(head.getX());
            nextPos.setY(head.getY()-1);
        }
        else if(kierunek=='P')//prawo
        {
            nextPos.setX(head.getX());
            nextPos.setY(head.getY()+1);
        }
        for(int i=0;i<snek.size();i++) //sprawdza czy w nastepnym ruchu nie wchodzisz sam na siebie
        {
            bufor=snek.get(i);
            if(nextPos.equals(bufor))
            {
                gameOver();
            }
        }
        if(nextPos.equals(apple))//sprawdza czy w nastepnym ruchu jablko zostanie zjedzone
        {
            apple_ate=true;
        }
        if(nextPos.getX()<0 || nextPos.getX()>17 || nextPos.getY()<0 || nextPos.getY()>18)//sprawdza czy wchodzisz na sciane,jesli tak to gameOver();
        {
            gameOver();
        }
        else
        {
            if(snek.size()>1) //gdy jest dluzszy niz sama glowa
            {
                if(apple_ate==true)
                {
                    apple_eaten(snek);
                    apple_ate=false;
                }

                for(int i = snek.size()-1;i>0;i--)
                {
                    if((i-1)>=0)
                    {
                        bufor=snek.get(i-1);
                        snek.set(i,bufor);
                    }
                }
                snek.set(0,nextPos); //przesuwa glowe o krok
                score+=10;//dodaje 10 punktow po kazdym kroku
            }
            else //gdy jest tylko glowa
            {
                if(apple_ate==true)
                {
                    apple_eaten(snek);
                    apple_ate=false;
                }
                snek.set(0,nextPos); //przesuwa glowe o krok
                score+=10;//dodaje 10 punktow po kazdym kroku

            }
            Austriacki_akwarelista(mapa,snek);//maluje mape
        }
    }
    public static void Austriacki_akwarelista(JPanel[][] mapa,ArrayList<SnakeBody> snek)//maluje mape po kazdym kroku
    {
        int mapaWiersze=18;
        int mapaKolumny=19;
        SnakeBody bufor=new SnakeBody(0,0);
        for(int i = 0;i<mapaWiersze;i++)//rysuje mape i zdobycz
        {
            for(int j = 0;j<mapaKolumny;j++)
            {
                mapa[i][j].setBackground(tlo);

                if(apple.getX()==i && apple.getY()==j)
                {
                    mapa[i][j].setBackground(zdobycz);
                }
            }
        }
        for(int i = 0;i<mapaWiersze;i++)//rysuje na mapie cialo weza
        {
            for(int j = 0;j<mapaKolumny;j++)
            {
                for(int k=0;k<snek.size();k++)
                {
                    bufor.setX(snek.get(k).getX());
                    bufor.setY(snek.get(k).getY());
                    if((bufor.getX() == i) && (bufor.getY() == j))
                    {
                        mapa[i][j].setBackground(cialo);
                    }
                }
            }
        }

    }
    public static ArrayList<SnakeBody> ArrayCopy(ArrayList<SnakeBody>snek)
    {
        ArrayList<SnakeBody> copy = new ArrayList<SnakeBody>();
        SnakeBody bufor=new SnakeBody(0,0);
        for(int i=0;i<snek.size();i++)
        {
            bufor=snek.get(i);
            copy.add(bufor);
        }
        return copy;
    }
    public static SnakeBody Randomizer(int x,int y)//zwraca obiekt typu SnakeBody z losowymi koordynatami z podanego przedziału
    {
        Random generator = new Random();
        int newX=0;
        int newY=0;
        boolean overlaps=false;
        SnakeBody random=new SnakeBody(0,0);
        do//ta pętla zapewnia,ze nie beda sie generowaly koordynaty na ciele snejka
        {
            overlaps=false;
            newX=generator.nextInt(x-1);
            newY=generator.nextInt(y-1);
            random.setX(newX);
            random.setY(newY);
            SnakeBody bufor;
            for(int i=0;i<snek.size();i++)
            {
                bufor=snek.get(i);
                if((newX==bufor.getX())&&(newY==bufor.getY()))
                {
                    overlaps=true;
                }
            }
        }
        while(overlaps==true);
        return random;
    }
    public static void apple_eaten(ArrayList<SnakeBody> snek)//steruje tym co sie stanie po zjedzeniu zdobyczy
    {
        SnakeBody ostatni = new SnakeBody(0,0);
        SnakeBody przedostatni = new SnakeBody(0,0);
        SnakeBody wynik = new SnakeBody(0,0);

        wynik.setX(ostatni.getX()-przedostatni.getX());//ten kawalek kodu oblicza na jakich koordach powinien sie znalezc nowy kawalek ogona
        wynik.setY(ostatni.getY()-przedostatni.getY());
        wynik.setX(ostatni.getX()+wynik.getX());
        wynik.setY(ostatni.getY()+wynik.getY());
        SnakeBody res = new SnakeBody(wynik.getX(),wynik.getY());
        snek.add(res);//i umieszcza go na koncu snejka

        apple=Randomizer(18,19);//losuje koordy dla nowej zdobyczy

        speed_multiplier=speed_multiplier-(speed_multiplier*acceleration_rate);//przyspiesza snejka po kazdej zjedzonej zdobyczy
        score+=100;//dodaje 100 punktow za zjedzenie zdobyczy
    }
    public static void gameOver()
    {
        System.out.println("Game over");
        System.out.println("Wynik: "+score);
        frame.dispose();
        frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//po nacisnieciu 'x' na ramce zamknie sie okno gry
        frame.setSize(500,500); //ustawienie rozmiaru ramki
        frame.setLayout(null); //ustawienie Layoutu komponentow ramki
        frame.setResizable(false);//ustawienie czy mozna zmieniac rozmiar ramki
        frame.setLocationRelativeTo(null);//ramka pojawia sie na srodku ekranu
        JLabel label = new JLabel("Twoj wynik to: "+score);
        label.setBounds(150,150,200,200);
        frame.add(label);
        frame.setVisible(true);
        while(true)
        {

        }
    }
    //KeyListener
    @Override
    public void keyTyped(KeyEvent event)
    {}
    public void keyReleased(KeyEvent event)
    {}
    public void keyPressed(KeyEvent event)
    {
        char c = event.getKeyChar();
        if(c==119)
        {
            kierunek='G';
        }
        else if(c==115)
        {
            kierunek='D';
        }
        else if(c==97)
        {
            kierunek='L';
        }
        else if(c==100)
        {
            kierunek='P';
        }
        else if(c==114) //przycisk 'r' restartuje gre
        {
            restart=true;
            frame.dispose();
        }
    }
}
