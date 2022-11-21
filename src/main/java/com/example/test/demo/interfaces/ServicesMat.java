package com.example.test.demo.interfaces;

import com.example.test.demo.model.Material;

import java.util.concurrent.ExecutionException;

public interface ServicesMat {
    public String saveMat(Material mat) throws InterruptedException, ExecutionException;
    public void deleteMat();
    public void updateMat();
    public void getMat();

}
