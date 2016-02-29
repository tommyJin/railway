package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 2016/2/28.
 */
public class Journey {
    String id;
    int state;//0->waiting   1->running
    String source;
    String dest;
    String current;//current route
    String currentBlock;
    List<Route> all = new ArrayList<>();
    List<Route> visited = new ArrayList<>();

    public Journey(String id,String source, String dest) {
        this.id = id;
        this.source = source;
        this.dest = dest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public List<Route> getAll() {
        return all;
    }

    public void setAll(List<Route> all) {
        this.all = all;
    }

    public List<Route> getVisited() {
        return visited;
    }

    public void setVisited(List<Route> visited) {
        this.visited = visited;
    }
}
