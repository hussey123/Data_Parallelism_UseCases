/**
 * Created by wso2123 on 8/29/16.
 */
public class PayLoad {


    String name,position,team;

    PayLoad()
    {

    }

    PayLoad(String name, String position, String team)
    {
        this.name=name;
        this.position=position;
        this.team=team;
    }

    String getName()
    {
        return name;
    }

    String getPosition()
    {
        return position;
    }

    String getTeam()
    {
        return team;
    }

}
