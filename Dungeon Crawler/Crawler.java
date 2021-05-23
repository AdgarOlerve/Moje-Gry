import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class Crawler extends JFrame implements ActionListener
{
    //scieżki do plików:
    File menuBackgroundJPG = new File("src/resources/backgrounds/menuBackground.jpg");
    File cityBackgroundJPG = new File("src/resources/backgrounds/cityBackground.jpg");
    File newGameBackgroundJPG = new File("src/resources/backgrounds/newGameBackground.png");
    File blackBackgroundJPG = new File("src/resources/backgrounds/blackBackground.jpg");
    File redBackgroundJPG = new File("src/resources/backgrounds/redBackground.jpg");
    File newGameButtonJPG = new File("src/resources/buttons/newGameButton.png");
    File loadGameButtonJPG = new File("src/resources/buttons/loadGameButton.png");
    File optionsButonJPG = new File("src/resources/buttons/optionsButton.png");
    File exitGameButtonJPG = new File("src/resources/buttons/exitGameButton.png");
    File menuButtonJPG = new File("src/resources/buttons/menuButton.png");

    File alchemikButtonJPG = new File("src/resources/buttons/alchemikButton.png");
    File kowalButtonJPG = new File("src/resources/buttons/kowalButton.png");
    File targButtonJPG = new File("src/resources/buttons/targButton.png");
    File zamtuzButtonJPG = new File("src/resources/buttons/zamtuzButton.png");
    File tawernaButtonJPG = new File("src/resources/buttons/tawernaButton.png");
    File dungeonButtonJPG = new File("src/resources/buttons/dungeonButton.png");

    File warriorClassIcon = new File("src/resources/icons/classIcon/warrior.png");
    File mageClassIcon = new File("src/resources/icons/classIcon/mage.png");
    File rogueClassIcon = new File("src/resources/icons/classIcon/rogue.png");
    //zmienne globalne
    FrameType nextFrameType = FrameType.MAINMENUSCREEN;
    volatile boolean frameRepaintRequest = true;
    volatile String chosenClass = "";
    Player player = new Player();

    public static void main(String[] args)
    {

        Crawler gra = new Crawler();
    }

    public Crawler()
    {
        JFrame frame = new JFrame();
        while(true)
        {
            if(frameRepaintRequest)
            {
                FramePainter(frame,nextFrameType);
                frameRepaintRequest=false;
            }

        }
    }

    public void FramePainter(JFrame f, FrameType type)
    {
        f.setTitle("Dungeon Crawler");
        ClearFrame(f);
        if (type == FrameType.MAINMENUSCREEN) //rysuje menu glowne
        {
            f.setSize(600, 700);
            f.setResizable(false);

            //tworzy "tło" i ustawia jego layoutMngra na BoxLayoutManager
            JLabel label = new JLabel(new ImageIcon(menuBackgroundJPG.getPath()));
            label.setLayout(new BoxLayout(label,BoxLayout.Y_AXIS));

            //dostosowanie i dodawanie przyciskow na ekran menu glownego
            JButton newGameButton = new JButton();
            newGameButton.setIcon(new ImageIcon(newGameButtonJPG.getPath()));
            newGameButton.setActionCommand("newGameButton");
            newGameButton.addActionListener(this);
            newGameButton.setContentAreaFilled(false);
            newGameButton.setFocusPainted(false);
            newGameButton.setBorderPainted(false);

            label.add(Box.createRigidArea(new Dimension(220,60)));//dodanie przerwy miedzy przyciskami
            label.add(newGameButton,CENTER_ALIGNMENT);

            JButton loadGameButton = new JButton();
            loadGameButton.setIcon(new ImageIcon(loadGameButtonJPG.getPath()));
            loadGameButton.setActionCommand("loadGameButton");
            loadGameButton.addActionListener(this);
            loadGameButton.setContentAreaFilled(false);
            loadGameButton.setFocusPainted(false);
            loadGameButton.setBorderPainted(false);
            label.add(Box.createRigidArea(new Dimension(220,60)));
            label.add(loadGameButton,CENTER_ALIGNMENT);

            JButton optionsButton = new JButton();
            optionsButton.setIcon(new ImageIcon(optionsButonJPG.getPath()));
            optionsButton.setActionCommand("optionsButton");
            optionsButton.addActionListener(this);
            optionsButton.setContentAreaFilled(false);
            optionsButton.setFocusPainted(false);
            optionsButton.setBorderPainted(false);
            label.add(Box.createRigidArea(new Dimension(200,60)));
            label.add(optionsButton,CENTER_ALIGNMENT);

            JButton exitGameButton = new JButton();
            exitGameButton.setIcon(new ImageIcon(exitGameButtonJPG.getPath()));
            exitGameButton.setActionCommand("exitGameButton");
            exitGameButton.addActionListener(this);
            exitGameButton.setContentAreaFilled(false);
            exitGameButton.setFocusPainted(false);
            exitGameButton.setBorderPainted(false);
            label.add(Box.createRigidArea(new Dimension(200,60)));
            label.add(exitGameButton,CENTER_ALIGNMENT);

            f.add(label);//dodanie "tła" wraz z przyciskami do ramki
            System.out.println("menu narysowane");
        }

        else if(type == FrameType.NEWGAMESCREEN)
        {
            f.setSize(700,500);
            f.setResizable(false);
            String playerName = "x";

            JLabel label = new JLabel(new ImageIcon(newGameBackgroundJPG.getPath()));
            JLabel upper = new JLabel();
            JLabel lower = new JLabel();
            label.setLayout(new GridLayout(2,1));

            //Daje napis i pole tekstowe w górnej części
            upper.setLayout(new GridLayout(2,1));

            JLabel upperUp = new JLabel();
            upperUp.setLayout(new FlowLayout(FlowLayout.CENTER,0,50));

            JLabel upperUpLeft = new JLabel("Podaj swoje imię: ");
            upperUpLeft.setFont(new Font("Arial",Font.BOLD,30));

            JTextField txt = new JTextField(15);
            txt.setFont(new Font("Arial",Font.BOLD,25));
            txt.setActionCommand("playerName");
            txt.addActionListener(this);
            upperUp.add(upperUpLeft);
            upperUp.add(txt);

            //Dodaje napis na środku
            JLabel upperDown = new JLabel();
            upperDown.setLayout(new FlowLayout());
            JLabel chooseYourPlayerClassLabel = new JLabel("Wybierz klasę postaci",JLabel.CENTER);
            chooseYourPlayerClassLabel.setFont(new Font("Arial",Font.BOLD,50));
            chooseYourPlayerClassLabel.setForeground(new Color(250, 172, 3, 255));
            upperDown.add(chooseYourPlayerClassLabel);

            upper.add(upperUp);
            upper.add(upperDown);
            label.add(upper);

            //Tworzenie 3 przycisków do wyboru klasy
            JButton warrior = new JButton(new ImageIcon(warriorClassIcon.getPath()));
            warrior.setFocusPainted(false);
            warrior.setBorderPainted(false);
            warrior.setContentAreaFilled(false);
            warrior.setActionCommand("warriorClass");
            warrior.addActionListener(this);

            JButton mage = new JButton(new ImageIcon(mageClassIcon.getPath()));
            mage.setFocusPainted(false);
            mage.setBorderPainted(false);
            mage.setContentAreaFilled(false);
            mage.setActionCommand("mageClass");
            mage.addActionListener(this);

            JButton rogue = new JButton(new ImageIcon(rogueClassIcon.getPath()));
            rogue.setFocusPainted(false);
            rogue.setBorderPainted(false);
            rogue.setContentAreaFilled(false);
            rogue.setActionCommand("rogueClass");
            rogue.addActionListener(this);

            JLabel lowerUp = new JLabel();
            JLabel lowerDown = new JLabel();

            lower.setLayout(new GridLayout(2,1));

            lowerUp.setLayout(new GridLayout(1,3));

            //Dodaje przyciski z odpowiednimi ikonami na dolnośrodkową część
            lowerUp.add(warrior);
            lowerUp.add(mage);
            lowerUp.add(rogue);

            //Dodaje odpowiednie opisy pod przyciskami na dolną część
            lowerDown.setLayout(new GridLayout(1,3));
            JLabel lowerDownLeft = new JLabel("Wojownik",JLabel.CENTER);
            lowerDownLeft.setFont(new Font("Arial",Font.BOLD,30));

            JLabel lowerDownCenter = new JLabel("Mag",JLabel.CENTER);
            lowerDownCenter.setFont(new Font("Arial",Font.BOLD,30));

            JLabel lowerDownRight = new JLabel("Łotr",JLabel.CENTER);
            lowerDownRight.setFont(new Font("Arial",Font.BOLD,30));

            lowerDown.add(lowerDownLeft);
            lowerDown.add(lowerDownCenter);
            lowerDown.add(lowerDownRight);

            lower.add(lowerUp);
            lower.add(lowerDown);

            label.add(lower);

            f.add(label);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            while(player.getChosenClass() == null)
            {
                System.out.println("pętla");
                if(chosenClass == "warriorClass")
                {
                    player.setClass(playerClass.WARRIOR);
                    System.out.println(player.getChosenClass());
                }
                else if(chosenClass == "mageClass")
                {
                    player.setClass(playerClass.MAGE);
                    System.out.println(player.getChosenClass());
                }
                else if(chosenClass == "rogueClass")
                {
                    player.setClass(playerClass.ROGUE);
                    System.out.println(player.getChosenClass());
                }
                Wait(100);
            }
            //Po wybraniu klasy postaci i imienia
            ClearFrame(f);
            label=new JLabel(new ImageIcon(newGameBackgroundJPG.getPath()));
            f.add(label);
        }

        else if(type == FrameType.CITYMAPSCREEN)//rysuje miasto
        {
            f.setSize(400, 600);

            JLabel label = new JLabel(new ImageIcon(cityBackgroundJPG.getPath()));
            label.setLayout(new GridLayout(4,2,20,10));
            cityMapButtons(label);

            f.add(label);
            f.setVisible(true);
        }



        //zasady dot. wszystkich typów ramek
        f.setLocationRelativeTo(null);
        f.setVisible(true);


    }
    //zajmuje sie tworzeniem, pozycjonowaniem i przypisywaniem funkcji przyciskom na mapie miasta
    public void cityMapButtons(JLabel label)
    {
        JButton[] buttons = new JButton[8];
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JButton();
            buttons[i].setBorderPainted(false);
            buttons[i].setFocusPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].addActionListener(this);
            label.add(buttons[i]);
        }
        buttons[0].setIcon(new ImageIcon(alchemikButtonJPG.getPath()));
        buttons[0].setActionCommand("alchemikButton");

        buttons[1].setIcon(new ImageIcon(kowalButtonJPG.getPath()));
        buttons[1].setActionCommand("kowalButton");

        buttons[2].setIcon(new ImageIcon(targButtonJPG.getPath()));
        buttons[2].setActionCommand("targButton");

        buttons[3].setIcon(new ImageIcon(tawernaButtonJPG.getPath()));
        buttons[3].setActionCommand("tawernaButton");

        buttons[4].setIcon(new ImageIcon(zamtuzButtonJPG.getPath()));
        buttons[4].setActionCommand("zamtuzButton");

        buttons[5].setIcon(new ImageIcon(dungeonButtonJPG.getPath()));
        buttons[5].setActionCommand("dungeonButton");

        buttons[6].setIcon(new ImageIcon(menuButtonJPG.getPath()));
        buttons[6].setActionCommand("menuButton");

        buttons[7].setIcon(new ImageIcon(exitGameButtonJPG.getPath()));
        buttons[7].setActionCommand("exitGameButton");
    }

    public void ClearFrame(JFrame f)
    {
        f.setLayout(new FlowLayout());
        f.setVisible(false);
        f.getContentPane().removeAll();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void Wait(int time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String x = e.getActionCommand();
        System.out.println(x);


        if(x == "newGameButton")
        {
            nextFrameType = FrameType.NEWGAMESCREEN;
            frameRepaintRequest = true;
        }
        else if(x == "menuButton")
        {
            nextFrameType = FrameType.MAINMENUSCREEN;
            frameRepaintRequest = true;
        }
        else if(x == "exitGameButton")
        {
            System.exit(666);
        }
        else if(x == "warriorClass" || x == "mageClass" || x == "rogueClass")
        {
            chosenClass = e.getActionCommand();
        }
        else if(e.getSource() instanceof JTextField && e.getActionCommand().equals("playerName"))
        {
            JTextField temp = (JTextField)e.getSource();
            player.setName(temp.getText());
        }
    }

    enum FrameType
    {
        NEWGAMESCREEN,
        MAINMENUSCREEN,
        CITYMAPSCREEN,
        DUNGEONMAPSCREEN
    }
}
