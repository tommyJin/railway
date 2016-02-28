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
    List<List<Route>> runnings = new ArrayList<>();//current running routes
    List<List<Route>> waitings = new ArrayList<>();//current waiting routes

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
            System.out.println("Now signal is " + signal.getName() + " and direction is " + signal.getDirection());
            if (signal.getDirection() == 1) {//signal direction=0->down  1->up
                this.upSignals.add(signal);
            } else {
                this.downSignals.add(signal);
            }
        }
        System.out.println(this.upSignals.size() + " " + this.downSignals.size());

       /* for (int i = 0; i < signals.size(); i++) {
            Signal s = signals.get(i);
            String flag = s.getPosition() == 0 ? "STOP" : "GO";
            String direction = s.getDirection() == 0 ? "DOWN" : "UP";
//            System.out.println("Signal: "+s.getName()+"("+direction+") controls block: "+s.getBlockName()+" current flag: "+flag);
        }

        for (int i = 0; i < this.blocks.size(); i++) {
            Block b = this.blocks.get(i);
            String type = b.getType() == 0 ? "track" : "point";
//            System.out.println("Block: "+b.getName()+" is a "+type+ " and position is "+b.getPosition());
        }*/
    }

    public void run() {
        System.out.println("#################################");
        if (this.runnings.size() == 0) {
            System.out.println("There is no route running in the railway!");
        } else {
            for (int i = 0; i < this.runnings.size(); i++) {
//                Route route = runnings.get(i);
//                System.out.println("Route " + route.getId() + " from " + route.getSource() + " to " + route.getDest() + " is running");
            }
        }

        //add a random route into the railway
        Random random = new Random();
        int ran = random.nextInt(this.routes.size());
        Route added = this.routes.get(ran);
//        this.waitings.add(added);

        //calculate which routes could be added into the railway
//        calculate();

        System.out.println("Now there are " + this.runnings.size() + " running");
        System.out.println("#################################\n");
    }

    public void testRun1(String source, String dest,String passby) {
        List<Route> passbys = Route.dao.getAllRoutes(this.routes,passby);
        if (this.runnings.size()==0){
            System.out.println("There is no train running!");
            this.runnings.add(passbys);
            Route route = this.runnings.get(0).get(0);

        }

    }


    public void testRun(String source, String dest) {


        List<Route> possible = new ArrayList<>();
        List<String> results = new ArrayList<>();
        String result = source;
//        results.add(source);//add the source signal as the first one
        Signal signal = Signal.dao.getByName(this.signals, source);
        boolean isLoop = false;
        if (signal.getDirection() == 0) {
            possible.addAll(downRoutes);
        } else {
            possible.addAll(upRoutes);
        }
//        System.out.println("Init possible are "+possible.size());
        int level = 0;
//        chooseRoute(source, dest, source, possible, results, result, level);
        System.out.println("-----------------------------------");
        generateRoutes(source, dest, source, possible, results, result, level);
    }

    public void generateRoutes(String source, String dest, String current, List<Route> possible, List<String> results, String result, int level) {
        for (int i = 0; i < this.signals.size(); i++) {
            Signal signal = this.signals.get(i);
            String s = signal.getNext();
            String[] ss;
            if (s.contains(";")) {// more than one signal
                ss = s.split(";");
            } else if (!s.equals("")) {// one signal
                ss = new String[1];
                ss[0] = s;
            } else {//no signal
                ss = new String[1];
                ss[0] = "";
            }
//            for (int j  = 0; j < ss.length; j++) {
//                System.out.println(ss[j]);
//            }
            this.signals.get(i).setNextArray(ss);
        }

        System.out.println("Start num" + this.upSignals.size() + " " + this.downSignals.size());
        int num = 1;//number of possibility
        for (int i = 0; i < this.upSignals.size(); i++) {
            Signal signal = this.upSignals.get(i);
            System.out.println(this.signals.get(i).getName() + ":");
            for (int j = 0; j < signal.getNextArray().length; j++) {
                String[] s = signal.getNextArray();
                System.out.println(s[j]);
            }
            num *= signal.getNextArray().length;
            System.out.println(signal.getName() + " has " + signal.getNextArray().length + " next signals ");

            System.out.println("\n");
        }
        System.out.println("num is " + num);

        List<Route> routeList = possible;

//        System.out.println("Do something to get the routes");

        Map<String,Object> map = new HashMap<>();
        map.put("source",source);
        map.put("dest",dest);
        map.put("name",source);
        map.put("rs",source);
        map.put("restart","0");//0->dont restart   1->restart
        loopArray(map);
        if (level == 0) {
            for (int i = 0; i < num; i++) {
                System.out.println(possibles.get(i));
            }
        }
    }

    public Map<String,Object> loopArray(Map<String,Object> map){
        System.out.println("Loop---------------------");
        System.out.println("Choose which route to run from :" + map.get("source") + " to :" +  map.get("dest") + " and now is at :" +  map.get("name"));
        Signal signal = Signal.dao.getByName(this.signals, map.get("name").toString());
        String source =  map.get("source").toString();
        String dest =  map.get("dest").toString();
        String rs =  map.get("rs").toString();
        for (int i = 0; i < signal.getNextArray().length; i++) {
            System.out.println("Into the loop in loop");
            String[] s = signal.getNextArray();
            String next = s[i];
            if (next.equals("")){
                this.possibles.add(rs);
                map.put("restart","1");
                break;
            }else {
                System.out.println("rs = " +rs);
                map.put("restart","0");
                rs += ";" + next;
                System.out.println("rs = " +rs);
                map.put("name",next);
                map.put("rs",rs);
                System.out.println("1map = " +map);
                map = loopArray(map);
                System.out.println("2map = " +map);

            }
        }
        if (map.get("restart").toString().equals("1")){
            map.put("rs",source);
        }
        System.out.println(rs);
        System.out.println("Loop end++++++++++++++++++++++");
        System.out.println("3map = " +map);
        return map;
    }

    /**
     * possible : all routes that in the same direction at this time
     * results : fixed lines which could be used to make a journey
     * result : one line which contains all signals like  s1,s6,s7
     */
    public String chooseRoute(String source, String dest, String current, List<Route> possible, List<String> results, String result, int level) {
        System.out.println("#####################################################################");
        System.out.println("Choose which route to run from :" + source + " to :" + dest + " and now is at :" + current);
        //if current is not the dest
//        if (!dest.equals(current)) {
//        if (possible.size()!=0){
        List<Route> routeList = possible;

//        System.out.println("Do something to get the routes");

        for (int i = 0; i < routeList.size(); i++) {
            Route route = routeList.get(i);

            String next = route.getDest();//get the dest of this route
            //add one signal to the journey line
            result += ";" + next;//add this route dest to the tail like   s1;s6
            System.out.println("Results add one signal : " + next);
            possible.remove(i);
            System.out.println("Start chooseRoute again!");
            level++;
            System.out.println("Level is " + level + "  " + result);
            result = chooseRoute(source, dest, next, possible, results, result, level);

            if (result.contains(dest)) {
                this.possibles.add(result);
                System.out.println("Find all the signals and add it to the results list " + results.size() + " and break ");
                level--;
                System.out.println("Level is " + level);
                break;
            }
        }
//        }else {

//            System.out.println("!!!!!This journey has get all answer!!! Signals are:"+result);

//        }
        System.out.println("#####################################################################\n\r");
        if (level == 0) {
            for (int i = 0; i < possibles.size(); i++) {
                System.out.println(possibles.get(i));
            }
        }
        return result;
    }


    /**
     * get the next route
     */
    public void getNextRoute() {
        System.out.println("Get the next route ");
    }

    /**
     * compare the runnings and the waitings
     * to see if the waitings could be add into the railway
     */
    /*public void calculate() {
        if (this.runnings.size() == 0) {
            Route route = this.waitings.get(0);
            System.out.println("Railway now is empty.First route " + route.getId() + " is added");
            System.out.println("1 Runnings :" + this.runnings.size() + "      waitings :" + this.waitings.size());
            String path = route.getPath();
            String[] paths;
            if (path.contains(";")) {
                paths = path.split(";");
            } else {
                paths = new String[1];
                paths[0] = path;
            }
            System.out.println("paths are");
            for (int i = 0; i < paths.length; i++) {
                System.out.println(paths[i]);
            }
            List<Signal> signals = route.getSignals();

            System.out.println("signals are");
            for (int i = 0; i < signals.size(); i++) {
                System.out.println(signals.get(i).getName());
            }

            for (int i = 0; i < this.signals.size(); i++) {
                Signal s = this.signals.get(i);//
                for (int j = 0; j < signals.size(); j++) {
                    if (s.getName().equals(signals.get(j).getName())) {
                        this.signals.get(i).setPosition(1);//set to stop
                        System.out.println("Signal " + this.getSignals().get(i).getName() + " is changed to stop by " + route.getId());
                    }
                }
            }


            for (int i = 0; i < paths.length; i++) {
                for (int j = 0; j < this.blocks.size(); j++) {
                    Block section = this.blocks.get(j);
                    if (section.getName().equals(paths[i])) {
                        this.blocks.get(j).setOccupy(route.getId());//set occupy to this section
                        System.out.println("Block " + this.getBlocks().get(j).getName() + " is occupied by " + route.getId());
                    }
                }
            }

            this.runnings.add(route);//add the route to the running routes
            this.waitings.clear();//clear the waiting routes

        } else {
            Iterator<Route> waiting = this.waitings.iterator();
            while (waiting.hasNext()) {
                Route route = waiting.next();
                System.out.println("Route " + route.getId() + " in the waitings is added");
                boolean flag = true;//true -> not occupied    false -> occupied
                for (int i = 0; i < this.blocks.size(); i++) {
                    String currentSecction = this.blocks.get(i).getName();
                    String path = route.getPath();
                    String[] paths;
                    if (path.contains(";")) {
                        paths = path.split(";");
                    } else {
                        paths = new String[1];
                        paths[0] = path;
                    }


                    for (int j = 0; j < paths.length; j++) {
                        System.out.println("current section name is " + currentSecction + " and this path name is " + paths[j]);
                        if (currentSecction != null && currentSecction.equals(paths[j])) {
                            System.out.println("Block " + paths[j] + " is occupied by " + this.blocks.get(i).getOccupy());
                            flag = false;
                            break;
                        }
                    }

                    if (!flag) {
                        break;
                    } else {
                        System.out.println("This route " + route.getId() + " path " + route.getPath() + " is clear and could go now!");
                    }
                }

                if (flag) {
                    System.out.println("Add this route into runnings");
                    this.runnings.add(route);//add the route to the running routes
                    waiting.remove();
                } else {
                    System.out.println("Add this route into waitings");
                }
            }
        }
        System.out.println("2 Runnings :" + this.runnings.size() + "      waitings :" + this.waitings.size());

        for (int i = 0; i < this.runnings.size(); i++) {
            Route route = this.runnings.get(i);
            String path = route.getPath();
            String[] paths;
            if (path.contains(";")) {
                paths = path.split(";");
            } else {
                paths = new String[1];
                paths[0] = path;
            }


        }

    }*/

    /**
     * update the railway's
     * signals
     * blocks
     * runnings
     * waitings
     */
    public void update(Route route) {

        System.out.println("Railway now is updating by route " + route.getId());
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

    public List<List<Route>> getRunnings() {
        return runnings;
    }

    public void setRunnings(List<List<Route>> runnings) {
        this.runnings = runnings;
    }

    public List<List<Route>> getWaitings() {
        return waitings;
    }

    public void setWaitings(List<List<Route>> waitings) {
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
