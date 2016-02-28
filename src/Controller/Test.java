package Controller;

import Model.Railway;

/**
 * Created by tommy on 2016/2/28.
 */
public class Test {
    public static void main(String[] args){

        Railway railway = new Railway();

        String source = "s1";
        String dest = "s7";
        String passby = "s1;s4;s7";
        railway.testRun(source,dest,passby);


    }
}
