package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 2016/2/15.
 */
public class Route {
    public static final Route dao = new Route();

    String id;
    String source;
    String dest;
    String points;
    String signals;
    String path;
    String conflicts;
    int direction;//0->DOWN   1->UP

    public Route(){

    }

    public Route(String id,String source,String dest,String points,String signals,String path,int direction){
        this.id = id;
        this.source = source;
        this.dest = dest;
        this.points = points;
        this.signals = signals;
        this.path = path;
        this.direction = direction;
    }

//    public List<Section> getSection(String source,String dest){
//        List<Section> list = new ArrayList<>();
//        return list;
//    }

    public Route(String source, String dest, String block, String signal, String path, String conflict) {
        this.source = source;
        this.dest = dest;

        String[] blockArray;

        if (block.contains(";")){
            blockArray = block.split(";");
            for (int i=0;i<blockArray.length;i++){
                String[] ps = blockArray[i].split(":");
//                Section p = new Section(ps[0],Boolean.parseBoolean(ps[1]));
//                System.out.println(p.getName()+" "+p.getFlag());
//                sections.add(p);
            }
        }else {
            blockArray = block.split(":");
//            Section p = new Section(blockArray[0],Boolean.parseBoolean(blockArray[1]));
//            System.out.println(p.getName()+" "+p.getFlag());
//            sections.add(p);
        }

        if (signal.contains(";")) {
            String[] signalList = signal.split(";");
            for (int i = 0; i < signalList.length; i++) {
                String[] ss = signalList[i].split(":");
//                Signal s = new Signal(ss[0],Boolean.parseBoolean(ss[1]));
//                System.out.println(s.getName() + " " + s.getFlag());
//                signals.add(s);
            }
        }else {
            String[] ss = signal.split(":");
//            Signal s = new Signal(ss[0],Boolean.parseBoolean(ss[1]));
//            System.out.println(s.getName()+" "+s.getFlag());
//            signals.add(s);
        }
/*

        if (path.contains(";")){
            String[] pathList = path.split(";");
            for (int i = 0; i < pathList.length; i++) {
                Path p = new Path(pathList[i]);
                System.out.println(p.getName());
                paths.add(p);
            }
        }else {
            Path p = new Path(path);
            System.out.println(p.getName());
            paths.add(p);
        }
*/


        this.conflicts = conflict;


//        System.out.println(this.source+" "+this.dest+" "+points+" "+signals+" "+paths+" "+conflicts);
    }

    /**
    * get route by its id
    *
    * */
    public Route getById(List<Route> routes,String id){
        Route r = new Route();
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            if (route.getId().equals(id)){
                r=route;
                break;
            }
        }
        return r;
    }

    public List<Route> getAllRoutes(List<Route> routes,String passby){
        List<Route> routes1 = new ArrayList<>();
        String[] ss = passby.split(";");
        for (int i = 1; i < ss.length; i++) {
            String source = ss[i-1];
            String dest = ss[i];
            Route r = getBySourceAndDest(routes,source,dest);
            routes1.add(r);
        }
        return routes1;
    }

    /**
    * get route By Source And Dest
    * */
    public Route getBySourceAndDest(List<Route> routes,String source,String dest){
        Route r = new Route();
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            if (route.getSource().equals(source) && route.getDest().equals(dest)){
                r = route;
                break;
            }
        }
        return r;
    }


    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSignals() {
        return signals;
    }

    public void setSignals(String signals) {
        this.signals = signals;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getConflicts() {
        return conflicts;
    }

    public void setConflicts(String conflicts) {
        this.conflicts = conflicts;
    }
}
