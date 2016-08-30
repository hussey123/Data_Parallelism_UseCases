import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wso2123 on 8/29/16.
 */

//Scenario 3

public class Process_with_an_additional_condition {

    private String details;

    //payload
    private static List<PayLoad> payLoad= Arrays.asList(
            new PayLoad("ChanakaFernando","STL","ESB"),
            new PayLoad("IsuruUdana","TL","ESB"),
            new PayLoad("ShafreenAnfar","STL","ESB"),
            new PayLoad("KasunIndrasiri","Director","Architecture"),
            new PayLoad("SanjeewaWeerarthna","CEO","Leadership"));

    public static void main(String[] args) {

        Process_with_an_additional_condition obj=new Process_with_an_additional_condition();

        //executing scenario3
        obj.Scenario3Rx(payLoad);

    }

    private void Scenario3Rx(List<PayLoad> payLoad)
    {

        Observable.from(payLoad) //setting observable
                .filter(pl->pl.getTeam().equals("ESB"))//filtering data with a condition
                .doOnNext(pl->details=process(pl))//process data
                .map(infoArray->details.split(" "))//mapping data
                .filter(person->person[1].equals("TL"))//filtering data(can done using reduce also)
                .observeOn(Schedulers.newThread())//executing the tasks on new thread
                .subscribe(person-> {
                    details="";
                    System.out.println(person[0]+" "+person[1]+" "+person[2]+" ");
                });//subscribing and getting the final result
    }

    //process the payload
    private String process(PayLoad pl)
    {
        return pl.getName()+" "+pl.getPosition()+" "+pl.getTeam();
    }
}
