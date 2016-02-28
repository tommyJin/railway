package Model;

import java.util.List;

/**
 * Created by tommy on 2016/2/28.
 */
public class Journey {
    int state;//0->waiting   1->running
    List<Route> all;
    List<Route> visit;
}
