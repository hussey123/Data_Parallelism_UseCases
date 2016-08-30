import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by wso2123 on 8/29/16.
 */
public class Process_and_Aggregate_in_desierd_Time_Limit {

    private String details="";

    //payload
    private static List<PayLoad> payLoad= Arrays.asList(
            new PayLoad("ChanakaFernando","TL","ESB"),
            new PayLoad("IsuruUdana","TL","ESB"),
            new PayLoad("ShafreenAnfar","STL","ESB"),
            new PayLoad("KasunIndrasiri","Director","Architecture"),
            new PayLoad("SanjeewaWeerarthna","CEO","Leadership"));

    public static void main(String[] args) {

        Process_and_Aggregate_in_desierd_Time_Limit obj=new Process_and_Aggregate_in_desierd_Time_Limit();

        //aggregate responses after desired time limit
        obj.Scenario5Rx(payLoad, 90);

    }

    private void Scenario5Rx(List<PayLoad> payLoad,long waitTime) {

            Observable.from(payLoad)
                    .doOnNext(pl -> details = process(pl))//process data
                    .observeOn(Schedulers.newThread())//executing process in parallel
                    .timeout(waitTime, TimeUnit.MILLISECONDS)//wait for desired limit of time
                    .map(infoArray -> details.split(" "))//mapping data
                    .filter(person -> person[1].equals("STL"))//filtering data(can done using reduce also)
                    .onErrorReturn(error->{
                        System.out.println("Nothing returned;"); //IllegalState Exception error
                        return new String[3];
                    })
                    .subscribe(person -> {
                        details = "";
                        System.out.println(person[0] + " " + person[1] + " " + person[2] + " ");
                    });//subscribing and getting the last result
        }

    //process the payload
    private static String process(PayLoad pl) {

        try {
            Thread.sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pl.getName()+" "+pl.getPosition()+" "+pl.getTeam();
    }

}
