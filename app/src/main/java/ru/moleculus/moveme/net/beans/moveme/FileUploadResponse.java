package ru.moleculus.moveme.net.beans.moveme;

/**
 * Created by Oleg on 10.03.2016.
 */
public class FileUploadResponse extends BaseMoveMeResponse {

    private String uploaded_pic;

    public String getUploadedPath(){
        return uploaded_pic;
    }
}
