    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stormsurgefs;

import java.io.*;
import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Iterator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.util.Random;
//import net.sf.javaml.core.Dataset;
//import net.sf.javaml.core.DefaultDataset;
//import net.sf.javaml.core.DenseInstance;
//import net.sf.javaml.core.Instance;
//import net.sf.javaml.filter.normalize.InstanceNormalizeMidrange;
//import net.sf.javaml.tools.data.FileHandler;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import java.util.Random;
import weka.filters.*;
import weka.attributeSelection.*;
/**
 *
 * @author truongtran
 */
public class DatasetHandle
{
    
    //private int _noFeatures;
    private Instances data;
    //read a dataset from disc 
    public DatasetHandle(String path) throws IOException
    {
        try
        {
            ArffLoader loader = new ArffLoader();
            loader.setSource(new File(path));
            data = loader.getDataSet();
            data.setClassIndex(data.numAttributes() - 1);
        }
        catch (Exception e)
        {//Catch exception if any
            e.printStackTrace();
        }
    }
    
    public static Instances ReadData(String path) throws IOException
    { 
        ArffLoader loader = new ArffLoader();
        loader.setSource(new File(path));
        Instances data = loader.getDataSet();
        data.setClassIndex(data.numAttributes() - 1);
        return data;
    }
    
    public Instances getDataset()
    {
        return data;
    }
    
    public int getNoFeatures()
    {
        return data.numAttributes();
    }

    public int getNoInstances(){
        return data.numInstances();
    }

    public int getNoClasses(){
        return data.numClasses();
    }

        /**
     * Return the mean of data in FeaIdx column
     * @param data
     * @param FeaIdx
     * @return
     */
    public static double Mean(Instances data, int FeaIdx)
    {
        double mean = 0;
        for(int i = 0; i< data.numInstances(); i++){
            mean += data.instance(i).value(FeaIdx);
        }
        return mean/data.numInstances();
    }

    /**
     * Return the standard deviation of data in FeaIdx column
     * @param data
     * @param FeaIdx
     * @return
     */
    public static double Stdv(Instances data, int FeaIdx)
    {

        double mean = Mean(data, FeaIdx);
        double temp = 0;
         for(int i = 0; i< data.numInstances(); i++)
            temp += (data.instance(i).value(FeaIdx) - mean) * (data.instance(i).value(FeaIdx) - mean);

        return Math.sqrt( temp / (data.numInstances() - 1));

    }
    public static Instances Merge(Instances inst1, Instances inst2)
    {
        Instances newInst = new Instances(inst1);
        for(int i=0;i<inst2.numInstances();i++)
            newInst.add(inst2.instance(i));
        return newInst;
    }
      

}
