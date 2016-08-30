import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by wso2123 on 8/29/16.
 */

//Scenario5

public class Process_and_Aggregate_First_n_responses {

    private String details="";

    //payload
    private static List<PayLoad> payLoad= Arrays.asList(
            new PayLoad("ChanakaFernando","TL","ESB"),
            new PayLoad("IsuruUdana","TL","ESB"),
            new PayLoad("ShafreenAnfar","STL","ESB"),
            new PayLoad("KasunIndrasiri","Director","Architecture"),
            new PayLoad("SanjeewaWeerarthna","CEO","Leadership"));

    public static void main(String[] args) {

        Process_and_Aggregate_First_n_responses obj=new Process_and_Aggregate_First_n_responses();
        obj.Scenario5Rx(payLoad,2);
    }

    private void Scenario5Rx(List<PayLoad> payLoad,int numberofResponses)
    {
        Observable.from(payLoad)
                .doOnNext(pl->details=process(pl))//process data
                .observeOn(Schedulers.newThread()) //executing process in parallel
                .take(numberofResponses)//get the first numberofResponses and send it to aggregate
                .map(infoArray->details.split(" "))//mapping data
                .filter(person->person[1].equals("STL"))//filtering data(can done using reduce also)
                .subscribe(person-> {
                    details="";
                    System.out.println(person[0]+" "+person[1]+" "+person[2]+" ");
                });//subscribing and getting the last result
    }

    //process the payload
    private static String process(PayLoad pl) {

        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pl.getName()+" "+pl.getPosition()+" "+pl.getTeam();
    }
}
