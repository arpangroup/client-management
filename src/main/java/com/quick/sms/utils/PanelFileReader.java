/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.sms.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author leela
 */
public interface PanelFileReader extends java.io.Serializable {

    public void openFile(File file, boolean firstLineHeader) throws FileNotFoundException, IOException, Exception;

    public void closeFile();

    public ArrayList<Object> getNextLine() throws Exception;

    public int getLineNumber();

    public Object getNextLine(int columnName) throws Exception;
}
