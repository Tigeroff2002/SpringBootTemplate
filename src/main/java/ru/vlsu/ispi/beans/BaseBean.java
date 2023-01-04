package ru.vlsu.ispi.beans;

import javax.validation.constraints.NotNull;

public class BaseBean {
    @NotNull(message="Id should not be null")
    private Long id;
    public Long getId(){
        return id;
    }
    public void setId(Long _id){
        id = _id;
    }
}
