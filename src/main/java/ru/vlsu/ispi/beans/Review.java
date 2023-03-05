package ru.vlsu.ispi.beans;

import javax.validation.constraints.NotEmpty;

public class Review extends BaseBean{
    @NotEmpty(message = "Provide a not empty review text")
    private String Text;
    public String getText(){
        return Text;
    }
    public void setText(String text){
        Text = text;
    }

    private Long EventId;
    public Long getEventId(){
        return EventId;
    }
    public void setEventId(Long eventId){
        EventId = eventId;
    }

    private float Rate;
    public float getRate(){
        return Rate;
    }
    public void setRate(float rate){
        Rate = rate;
    }
}
