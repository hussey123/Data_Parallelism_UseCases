import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wso2123 on 8/29/16.
 */
public class Process_with_Dynamic_Number_of_Threads {

    String details="";

    //payload
    static List<PayLoad> payLoad= Arrays.asList(
            new PayLoad("ChanakaFernando","STL","ESB"),
            new PayLoad("IsuruUdana","TL","ESB"),
            new PayLoad("ShafreenAnfar","STL","ESB"),
            new PayLoad("KasunIndrasiri","Director","Architecture"),
            new PayLoad("SanjeewaWeerarthna","CEO","Leadership"));

    public static void main(String[] args) {

        Process_with_Dynamic_Number_of_Threads obj=new Process_with_Dynamic_Number_of_Threads();

        //setting the threshold and execute
        obj.Scenario2Rx(payLoad,10);

    }

    void Scenario2Rx(List<PayLoad> payLoad,int threadPoolSize)
    {
        int size=payLoad.size();//get the size for invoke threads

        if(size<threadPoolSize)  //invoke threads equal to the amount of size(threshold)
        {
            Observable.from(payLoad) //setting observable
                    .doOnNext(new Action1<PayLoad>() {//process data
                        @Override
                        public void call(PayLoad payLoad) {
                            details=process(payLoad);          //whole part can be done using the map function instead a callable
                        }
                    }).map(infoArray->details.split(" "))//mapping data
                    .filter(person->person[1].equals("STL"))//filtering data(can done using reduce also)
                    .observeOn(Schedulers.newThread())//executing the tasks on new thread
                    .subscribe(person-> {
                        details="";
                        System.out.println(person[0]+" "+person[1]+" "+person[2]+" ");
                    });//subscribing and getting the last result
        }
        else //if the threads want to be invoked is greater than the threshold value
        {
            for(int i=1;i<size;i+=threadPoolSize)
            {
                Observable.from(payLoad) //setting observable
                        .skip(i-1).take(i+threadPoolSize-1) //fixed thread range
                        .doOnNext(new Action1<PayLoad>() {//process data
                            @Override
                            public void call(PayLoad payLoad) {
                                details=process(payLoad);          //whole part can be done using the map function instead a callable
                            }
                        }).map(infoArray->details.split(" "))//mapping data
                        .filter(person->person[1].equals("STL"))//filtering data(can done using reduce also)
                        .observeOn(Schedulers.newThread())//executing the tasks on new thread
                        .subscribe(person-> {
                            details="";
                            System.out.println(person[0]+" "+person[1]+" "+person[2]+" "+Thread.currentThread().getName());
                        });//subscribing and getting the last result
            }
        }
    }

    //process the payload
    String process(PayLoad pl)
    {
        return pl.getName()+" "+pl.getPosition()+" "+pl.getTeam();
    }

}
