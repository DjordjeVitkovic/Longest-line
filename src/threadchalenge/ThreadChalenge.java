package threadchalenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadChalenge {

    /*Zadatak je da iscitavam tekstualni fajl, i da u njemu pronadjem najduzu liniju
     I da mi drugi thread, bude progess bar
     */
    public synchronized static int brojKoraka() {
        broj++;
        return broj;

    }

    static int broj = 0;
    static String line;
    static int total = 0;
    static boolean radim = true;
    static int current = 0;

    public static void gotov() {
        radim = false;
    }

    public static void odradioLiniju() {

        current++;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, Exception {

        FileReader fr = new FileReader(new File("Text.txt"));
        BufferedReader br = new BufferedReader(fr);

        while ((line = br.readLine()) != null) {

            total = brojKoraka();

        }

        System.out.println(total);

        br.close();
        fr.close();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                FileReader fr2 = null;
                BufferedReader br2 = null;
                try {
                    fr2 = new FileReader(new File("Text.txt"));
                    br2 = new BufferedReader(fr2);
                    String a = "";
                    String b = "";
                    try {
                        while ((line = br2.readLine()) != null) {

                            a = line;
                            if (a.length() > b.length()) {
                                b = a;

                            }
                            odradioLiniju();
                            Thread.sleep(10);
                        }
                        System.out.println(b);
                        gotov();

                    } catch (IOException ex) {
                        Logger.getLogger(ThreadChalenge.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(ThreadChalenge.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ThreadChalenge.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fr2.close();
                        br2.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadChalenge.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        //Drugi thread
        Thread nit = new Thread(task);

        nit.start();

        while (radim) {

            System.out.println("Odradio: " + current + "/" + total + "(" + ((float)current/total*100) + "%)");
            
            Thread.sleep(200);

        }

    }

}
