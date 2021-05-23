public class Player
{
    private int hp; //punkty zycia
    private int mana;//dmg zadawany przez czary
    private int dmg;//dmg zadawany wręcz
    private int luck;//szczęście w rng
    private int armor;//zdolność odbijania fizycznych uderzeń
    private int level = 1;//poziom postaci
    private String name;
    private playerClass chosenClass;

    public Player()
    {
        this.hp=0;
        this.mana=0;
        this.dmg=0;
        this.luck=0;
        this.armor=0;
        this.name="Player";
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public playerClass getChosenClass()
    {
        return chosenClass;
    }

    public void setClass(playerClass cls)
    {
        if(cls == playerClass.WARRIOR)
        {
            this.hp=150;
            this.mana=50;
            this.dmg=150;
            this.luck=10;
            this.armor=150;

        }
        else if(cls == playerClass.MAGE)
        {
            this.hp=100;
            this.mana=150;
            this.dmg=70;
            this.luck=15;
            this.armor=70;
        }
        else if(cls == playerClass.ROGUE)
        {
            this.hp=90;
            this.mana=50;
            this.dmg=100;
            this.luck=25;
            this.armor=100;
        }
        this.chosenClass = cls;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getHp()
    {
        return hp;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
    }

    public int getMana()
    {
        return mana;
    }

    public void setMana(int mana)
    {
        this.mana = mana;
    }

    public int getDmg()
    {
        return dmg;
    }

    public void setDmg(int dmg)
    {
        this.dmg = dmg;
    }

    public int getLuck()
    {
        return luck;
    }

    public void setLuck(int luck)
    {
        this.luck = luck;
    }

    public int getArmor()
    {
        return armor;
    }

    public void setArmor(int armor)
    {
        this.armor = armor;
    }
}
enum playerClass
{
    WARRIOR,
    MAGE,
    ROGUE
}
