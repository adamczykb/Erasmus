package zsp.erasmus.Fragments;

/**
 * Created by julupukki on 11.12.17.
 */

public class Member_datamodel {
    String id_;
    String name;
    String surname;
    String country;
    String team;
    String email;
    String Teach;
    String Desc;
    String Admin;



    public Member_datamodel(String id_, String name, String surname,String country, String email, String Teach, String team, String Desc,String admin) {
        this.name = name;
        this.surname = surname;
        this.team = team;
        this.id_=id_;
        this.country=country;
        this.email=email;
        this.Teach=Teach;
        this.Desc=Desc;
        this.Admin=admin;

    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getTeam() {
        return team;
    }
    public String getId_() {
        return id_;
    }
    public String getCountry() {
        return country;
    }
    public String getEmail() {
        return email;
    }
    public String getTeach() {
        return Teach;
    }
    public String getDesc() {
        return Desc;
    }
    public String getAdmin() {
        return Admin;
    }

    public String[] getData(){
        String[] str = new String[7];
        str[0]=name;
        str[1]=surname;
        str[2]=team;
        str[3]=email;
        str[4]=Desc;
        str[5]=country;
        str[6]=Admin;
        return str;
    }

}
