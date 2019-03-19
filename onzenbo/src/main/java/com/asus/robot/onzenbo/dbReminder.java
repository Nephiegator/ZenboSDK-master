package com.asus.robot.onzenbo;

        import com.google.firebase.firestore.Exclude;
        import com.google.firebase.firestore.IgnoreExtraProperties;

        import java.io.Serializable;

@IgnoreExtraProperties
public class dbReminder implements Serializable {

    @Exclude
    private String id;

    private String title;
    private String desc;
    private String inLocation;
    private String ObjPerson;
    private String time;
    private boolean isSelected;

    public dbReminder(String title, String desc, String inLocation, String objPerson, String time) {
        this.title = title;
        this.desc = desc;
        this.inLocation = inLocation;
        this.ObjPerson = objPerson;
        this.time = time;
    }

    public dbReminder() {
        //empty constructor needed

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInLocation() {
        return inLocation;
    }

    public void setInLocation(String inLocation) {
        this.inLocation = inLocation;
    }

    public String getObjPerson() {
        return ObjPerson;
    }

    public void setObjPerson(String objPerson) {
        ObjPerson = objPerson;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

