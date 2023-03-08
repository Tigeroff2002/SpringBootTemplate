package ru.vlsu.ispi.beans;

import ru.vlsu.ispi.enums.EventStatus;

import java.util.Date;

public class Event {
    private Long TaskId;
    public Long getTaskId(){
        return TaskId;
    }
    public void setTaskId(Long taskId){
        TaskId = taskId;
    }

    private int EmployerId;
    public int getEmployerId(){
        return EmployerId;
    }
    public void setEmployerId(int employerId) {
        EmployerId = employerId;
    }

    private EventStatus Status;
    public EventStatus getStatus() {
        return Status;
    }
    public void setStatus(EventStatus status) {
        Status = status;
    }

    private Date EventCompleteData;
    public Date getEventCompleteData() {
        return EventCompleteData;
    }
    public void setEventCompleteData(Date eventCompleteData) {
        EventCompleteData = eventCompleteData;
    }

    private float RealPrice;
    public float getRealPrice(){
        return RealPrice;
    }
    public void setRealPrice(float realPrice){
        RealPrice = realPrice;
    }
}