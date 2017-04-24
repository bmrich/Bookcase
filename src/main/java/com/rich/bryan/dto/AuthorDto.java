package com.rich.bryan.dto;

public class AuthorDto {
    private String name;
    private String fName;
    private String lName;

    public AuthorDto(String name) {
        this.name = name;
        breakName(name);
    }

    private void breakName(String name){
        try {
            this.lName = name.substring(name.lastIndexOf(" ")+1);
            this.fName = name.substring(0, name.lastIndexOf(" "));
        } catch (StringIndexOutOfBoundsException e) {
            this.fName = null;
            this.lName = null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "name='" + name + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                '}';
    }
}
