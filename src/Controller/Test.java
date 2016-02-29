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
        railway.addJourney("j1", source, dest, passby);
        railway.addJourney("j2", "s1", "s7", "s1;s6;s7");
        railway.addJourney("j3", "s8", "s2", "s8;s3;s2");
        railway.addJourney("j4", "s8", "s2", "s8;s5;s2");

        while (true){

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            railway.checkWaitingList();

            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                railway.runFreely();
            }

        }

    }
}
