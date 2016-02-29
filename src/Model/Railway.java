package Model;

import Util.JsonFile;

import java.util.*;

/**
 * Created by tommy on 2016/2/18.
 */
public class Railway {
    List<Signal> signals;
    List<Signal> upSignals = new ArrayList<>();
    List<Signal> downSignals = new ArrayList<>();
    List<Block> blocks;
    List<Route> routes;
    List<Route> upRoutes = new ArrayList<>();
    List<Route> downRoutes = new ArrayList<>();
    List<Journey> allJourney = new ArrayList<>();
    List<Journey> runnings = new ArrayList<>();//current running routes
    List<Journey> waitings = new ArrayList<>();//current waiting routes

    List<String> possibles = new ArrayList<>();

    public Railway() {

        JsonFile jf = new JsonFile();
        this.signals = jf.getSignal();
        this.blocks = jf.getBlock();
        this.routes = jf.getRoute();

        for (int i = 0; i < this.routes.size(); i++) {
            Route route = Route.dao.getById(this.routes, this.routes.get(i).getId());
//            System.out.println("Now route is " + route.getId() + " and direction is " + route.getDirection());
            if (route.getDirection() == 1) {//signal direction=0->down  1->up
                this.upRoutes.add(route);
            } else {
                this.downRoutes.add(route);
            }
        }

        for (int i = 0; i < this.signals.size(); i++) {
            Signal signal = Signal.dao.getByName(this.signals, this.signals.get(i).getName());
//            System.out.println("Now signal is " + signal.getName() + " and direction is " + signal.getDirection());
            if (signal.getDirection() == 1) {//signal direction=0->down  1->up
                this.upSignals.add(signal);
            } else {
                this.downSignals.add(signal);
            }
        }
//        System.out.println(this.upSignals.size() + " " + this.downSignals.size());

    }

    public void addJourney(String journeyId,String source,String dest,String passby){
        Journey journey = new Journey(journeyId,source,dest);
        String[] passbys = passby.split(";");
        for (int i = 1; i < passbys.length; i++) {
            System.out.println("Source:"+passbys[i-1]+"  dest:"+passbys[i]);

            Route route = Route.dao.getBySourceAndDest(this.routes,passbys[i-1],passbys[i]);
            journey.getAll().add(route);
            journey.setCurrent(journey.getAll().get(0).getId());
        }

        this.waitings.add(journey);
    }

    public void checkWaitingList(){

        for (int i = 0; i < this.waitings.size(); i++) {
            Journey waiting = this.waitings.get(i);
            boolean flag = lock(waiting,waiting.getCurrent());
            if (flag) {
                waiting.setState(1);
                this.allJourney.add(waiting);
            }
        }


        for (int i = 0; i < this.allJourney.size(); i++) {
            System.out.println("In all running Journey "+this.allJourney.get(i).getSource()+"  "+this.allJourney.get(i).getDest());
        }
    }

    public void runFreely(){
        List<Journey> list = this.allJourney;
        System.out.println("Run one block at one sec");

        for (int i = 0; i < list.size(); i++) {
            Journey j = list.get(i);

            //TODO   how train runs in one second
//            j.getCurrent()

        }

    }

    /**
    * Lock a list of blocks and change signal by a journey and the current route
     * route : a route belongs to a journey
     * name : the current block it is on
    * */
    public boolean lock(Journey journey,String routeId){
        System.out.println("-------------------------lock------------------------------");
        System.out.println("Journey id :"+journey.getId()+" and route is :"+routeId);

        boolean flag = true;
        List<Block> blocks = this.blocks;
        List<Signal> signals = this.signals;
        Route route = Route.dao.getById(this.routes,routeId);
        String path = route.getPath();
        String[] paths = path.split(";");
        for (int i = 0; i < blocks.size(); i++) {
            for (int j = 0; j < paths.length; j++) {
                if (blocks.get(i).getName().equals(paths[j]) ){
                    System.out.println(blocks.get(i).getName()+" is occupied by < "+blocks.get(i).getOccupy()+" > and now journey is "+journey.getId());
                    if (!blocks.get(i).getOccupy().equals("")) {//some on occupy
                            if (!blocks.get(i).getOccupy().equals(journey.getId())) {//the one who wants to occupy is not the one who has occupied
                                flag = false;
                                break;
                            }
                        } else {// no journey occupy
                            blocks.get(i).setOccupy(journey.getId());

                            if (blocks.get(i).getType()==1) {// this is a point
                                String[] point;
                                if (route.getPoints().contains(";")){
                                    point = route.getPoints().split(";");
                                }else {
                                    point = new String[1];
                                    point[0] = route.getPoints();
                                }

                                int position = 0;
                                for (int k = 0; k < point.length; k++) {
                                    String[] p = point[k].split(":");
                                    if (blocks.get(i).getName().equals(p[0])){
                                        position = p[1].equals("p")?0:1;
                                    }
                                }

                                blocks.get(i).setPosition(position);
                            }
                        }
                }
            }
            if (!flag){
                break;
            }
        }

        String[] signal = route.getSignals().split(";");

        for (int i = 0; i < signals.size(); i++) {
            for (int j = 0; j < signal.length; j++) {
                if (signals.get(i).getName().equals(signal[j]) && signals.get(i).getPosition()!=0 ){//signal name is the same and is not set to stop
                    flag = false;
                }
            }

        }

        if (flag){
            this.blocks = blocks;
            System.out.println("Satisfy every condition");
        }

        System.out.println("-------------------------lock  end------------------------------");
        return flag;
    }

    public List<Journey> getAllJourney() {
        return allJourney;
    }

    public void setAllJourney(List<Journey> allJourney) {
        this.allJourney = allJourney;
    }

    public List<Signal> getUpSignals() {
        return upSignals;
    }

    public void setUpSignals(List<Signal> upSignals) {
        this.upSignals = upSignals;
    }

    public List<Signal> getDownSignals() {
        return downSignals;
    }

    public void setDownSignals(List<Signal> downSignals) {
        this.downSignals = downSignals;
    }

    public List<Route> getUpRoutes() {
        return upRoutes;
    }

    public void setUpRoutes(List<Route> upRoutes) {
        this.upRoutes = upRoutes;
    }

    public List<Route> getDownRoutes() {
        return downRoutes;
    }

    public void setDownRoutes(List<Route> downRoutes) {
        this.downRoutes = downRoutes;
    }

    public List<Journey> getRunnings() {
        return runnings;
    }

    public void setRunnings(List<Journey> runnings) {
        this.runnings = runnings;
    }

    public List<Journey> getWaitings() {
        return waitings;
    }

    public void setWaitings(List<Journey> waitings) {
        this.waitings = waitings;
    }

    public List<String> getPossibles() {
        return possibles;
    }

    public void setPossibles(List<String> possibles) {
        this.possibles = possibles;
    }

    public List<Signal> getSignals() {
        return signals;
    }

    public void setSignals(List<Signal> signals) {
        this.signals = signals;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
