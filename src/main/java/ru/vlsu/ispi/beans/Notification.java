package ru.vlsu.ispi.beans;

import javax.validation.constraints.NotEmpty;
import java.time.format.DateTimeFormatter;

public class Notification extends BaseBean{
    private String NotifyText;
    public String getNotifyText(){
        return NotifyText;
    }
    public void setNotifyText(String notifyText){
        NotifyText = notifyText;
    }

    private DateTimeFormatter Time;
    public DateTimeFormatter getTime(){
        return Time;
    }
    public void setTime(DateTimeFormatter time){
        Time = time;
    }

    private Long ExecutorId;
    public Long getExecutorId(){
        return ExecutorId;
    }
    public void setExecutorId(Long executorId){
        ExecutorId = executorId;
    }
}
