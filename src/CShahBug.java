package BattleBugs;
import java.util.ArrayList;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import info.gridworld.actor.Actor;
public class CShahBug extends BattleBug2012
{
    public CShahBug(int str, int def, int spd, String name, Color col)
    {
        super(str, def, spd, name, col);
    }
    public void act()
    {
        int numActs=getNumAct();
        Location myLoc=getLocation();
        int myDir=getDirection();
        ArrayList<Location> puLocs=getPowerUpLocs();
        int mod=numActs%40;
        if(mod==38||mod==39)
        {
            Location regulatorLoc=new Location(13,13);
            if(myLoc.getDirectionToward(regulatorLoc)==myDir)
                move();
            else
                turnTo(myLoc.getDirectionToward(regulatorLoc));
        }
        else if(getBugLocs().size()>0)
        {
            if(bugsThatCanKillMe().size()>0)
            {
                Location min=puLocs.get(0);
            double a=distance(myLoc.getRow(),min.getRow(),myLoc.getCol(),min.getCol());
            for(int i=1;i<puLocs.size();i++) 
            {
                double dist=distance(myLoc.getRow(),puLocs.get(i).getRow(),myLoc.getCol(),puLocs.get(i).getCol());
                if(dist<a)
                {
                    a=dist;
                    min=puLocs.get(i);
                }    
            }    
            Location goTo=min;
            if(myLoc.getDirectionToward(goTo)==myDir)
                move();
            else
                turnTo(myLoc.getDirectionToward(goTo));
            }
            if(bugsIcanKill().size()>0)
            {
                getNearestBug().attack();
            }
            
        }
        else if(puLocs.size()>0)
        {
            Location min=puLocs.get(0);
            double a=distance(myLoc.getRow(),min.getRow(),myLoc.getCol(),min.getCol());
            for(int i=1;i<puLocs.size();i++)
            {
                double dist=distance(myLoc.getRow(),puLocs.get(i).getRow(),myLoc.getCol(),puLocs.get(i).getCol());
                if(dist<a)
                {
                    a=dist;
                    min=puLocs.get(i);
                }   
            }   
            Location goTo=min;
            if(myLoc.getDirectionToward(goTo)==myDir)
                move();
            else
                turnTo(myLoc.getDirectionToward(goTo));
        }   
        else
            move();
    }  
    public double distance(int x1,int x2,int y1,int y2)
    {
        return Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));
    }  
    public ArrayList<BattleBug2012> getAllBugs()
    {
        ArrayList<Actor> allActors=getActors();
        ArrayList<BattleBug2012> allBugs=new ArrayList<BattleBug2012>();
        for(int i=0;i<allActors.size();i++)
        {
            if(allActors.get(i) instanceof BattleBug2012)
                allBugs.add((BattleBug2012)allActors.get(i));
        }   
        return allBugs;
    }      
    public ArrayList<BattleBug2012> bugsIcanKill()
    {
        ArrayList<BattleBug2012> allActors=getAllBugs();
        ArrayList<BattleBug2012> bugsICanKill=new ArrayList<BattleBug2012>();
        for(int i=0;i<allActors.size();i++)
        {
            if(allActors.get(i).getDefense()+3>=this.getStrength())
                bugsICanKill.add((BattleBug2012)allActors.get(i));
        }   
        return bugsICanKill;
    }
    public ArrayList<BattleBug2012> bugsThatCanKillMe()
    {
        ArrayList<BattleBug2012> allActors=getAllBugs();
        ArrayList<BattleBug2012> bugsThatCanKillMe=new ArrayList<BattleBug2012>();
        for(int i=0;i<allActors.size();i++)
        {
            if(!(allActors.get(i).getDefense()+3>=this.getStrength()))
                bugsThatCanKillMe.add((BattleBug2012)allActors.get(i));
        }   
        return bugsThatCanKillMe;
    }       
    public ArrayList<Location> getBugLocs()
    {
        ArrayList<Location> bugLocs=new ArrayList<Location>();
        ArrayList<BattleBug2012> allBugs=getAllBugs();
        for(int i=0;i<allBugs.size();i++)
            bugLocs.add(allBugs.get(i).getLocation());
        return bugLocs;   
    }       
     public BattleBug getNearestBug()
    {
        getAllBugs();
        if(getAllBugs().isEmpty())
            return null;
        else
        {
            Location myLoc=getLocation();
            ArrayList<Location> bugLocs=getBugLocs();
            BattleBug nearestBug=getAllBugs().get(0);
            double a=distance(myLoc.getRow(),getBugLocs().get(0).getRow(),myLoc.getCol(),getBugLocs().get(0).getCol());
            for(int i=0;i<getBugLocs().size();i++)
            {   
                double dist=distance(myLoc.getRow(),getBugLocs().get(i).getRow(),myLoc.getCol(),getBugLocs().get(i).getCol());
                if(dist<a)
                {
                    a=dist;
                    nearestBug=getAllBugs().get(i);
                }   
            }   
            return nearestBug;
        }   
    }      
}