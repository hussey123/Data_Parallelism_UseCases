import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by wso2123 on 8/29/16.
 */


//Scenario1

public class Process_with_Fixed_Number_of_Threads{


    String details;

    //payload
    private static List<PayLoad> payLoad= Arrays.asList(
            new PayLoad("ChanakaFernando","STL","ESB"),
            new PayLoad("IsuruUdana","TL","ESB"),
            new PayLoad("ShafreenAnfar","STL","ESB"),
            new PayLoad("KasunIndrasiri","Director","Architecture"),
            new PayLoad("SanjeewaWeerarthna","CEO","Leadership"));

    public static void main(String[] args) {


        //initializing object
        Process_with_Fixed_Number_of_Threads obj=new Process_with_Fixed_Number_of_Threads();

        //executing Scenario1 using Rx-Java
        obj.Scenario1Rx(payLoad,1,2);
        obj.Scenario1Rx(payLoad,3,payLoad.size());
    }

    private void Scenario1Rx(List<PayLoad> payLoad,int init,int end)
    {

        Observable.from(payLoad) //setting observable
                .skip(init-1).take(end-init) //determining the fixed thread range
                .doOnNext(pl->details=process(pl))//process data
                .map(infoArray->details.split(" "))//mapping data
                .filter(person->person[1].equals("STL"))//filtering data(can done using reduce also)
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
