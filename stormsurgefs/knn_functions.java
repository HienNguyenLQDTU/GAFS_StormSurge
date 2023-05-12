/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stormsurgefs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.lazy.IBk;
import weka.attributeSelection.PSOSearch;
import weka.core.Instances;

/**
 *
 * @author truongtran
 */
public class knn_functions 
{
     
    //Remove features not in list
    private static Instances RemoveFS(Instances inst, int[] list)
    {
        Instances newInst=new Instances(inst);
        boolean[] check=new boolean[inst.numAttributes()];
        for(int i=0;i<list.length;i++)
            check[list[i]]=true;
        for(int i=inst.numAttributes()-1;i>=0;i--)
            if(!check[i])
                newInst.deleteAttributeAt(i);
        return newInst;
        
    }
    //PSO_Cfs feature selection
    private static Instances[] PSOCfs(Instances trainData, Instances testData) throws Exception
    {
        Instances[] results=new Instances[2];
        AttributeSelection attsel = new AttributeSelection(); 
        CfsSubsetEval eval= new CfsSubsetEval();
        attsel.setEvaluator(eval);
        PSOSearch search=new PSOSearch();
        search.setPopulationSize(50);
        search.setIterations(100);
        attsel.setSearch(search);
        attsel.SelectAttributes(trainData);
        int[] list=attsel.selectedAttributes();
        results[0] = new Instances(RemoveFS(trainData, list));
        results[1] = new Instances(RemoveFS(testData, list));
        return results;
    }
    //PSO_Wrap feature selection
    private static Instances[] PSOWrap(Instances trainData, Instances testData) throws Exception
    {
        Instances[] results=new Instances[2];
        AttributeSelection attsel = new AttributeSelection(); 
        WrapperSubsetEval eval= new WrapperSubsetEval();
        IBk knn =new IBk(3);
        eval.setClassifier(knn);
        attsel.setEvaluator(eval);
        PSOSearch search=new PSOSearch();
        search.setPopulationSize(50);
        search.setIterations(100);
        attsel.setSearch(search);
        attsel.SelectAttributes(trainData);
        int[] list=attsel.selectedAttributes();
        results[0] = new Instances(RemoveFS(trainData, list));
        results[1] = new Instances(RemoveFS(testData, list));
        return results;
    }
   
    public static void mainFunction(int data_idx) throws IOException, Exception
    {
        
        //int data_idx = 0;
        String[] fileTrainName={"train_193_weka"};
        String[] fileTestName={"test_193_weka"};
        //read input file
        String workingDir = System.getProperty("user.dir");
        String pathTrain = workingDir +"/data/"+fileTrainName[data_idx]+".arff";
        Instances trainData = DatasetHandle.ReadData(pathTrain);
        String pathTest = workingDir +"/data/"+fileTestName[data_idx]+".arff";
        Instances testData = DatasetHandle.ReadData(pathTest);
        //accuracy
        double ccAll;
        double ccPSOCfs;
        double ccPSOWrap;
        double nrmseAll;
        double nrmsePSOCfs;
        double nrmsePSOWrap;
        double[] results = new double[2];
        //all features
        results = Reg.KnnReg(trainData, testData, "all_"+fileTrainName[data_idx]);
        ccAll = results[0];
        nrmseAll = results[1];
        //PSOCfs
        Instances[] psoCfsInst =new Instances[2];
        psoCfsInst = PSOCfs(trainData, testData);
        results = Reg.KnnReg(psoCfsInst[0], psoCfsInst[1], "psoCfs_"+fileTrainName[data_idx]);
        ccPSOCfs = results[0];
        nrmsePSOCfs = results[1];
        //PSOWrap
        Instances[] psoWrapInst =new Instances[2];
        psoWrapInst = PSOWrap(trainData, testData);
        results = Reg.KnnReg(psoWrapInst[0], psoWrapInst[1], "psoWrap_"+fileTrainName[data_idx]);
        ccPSOWrap = results[0];
        nrmsePSOWrap = results[1];
        //Write results to a file
        File file=new File("knn_cc_nrmse_"+fileTrainName[data_idx]+".txt");
        FileWriter fileWriter=new FileWriter(file.getAbsoluteFile());
        BufferedWriter buff=new BufferedWriter(fileWriter);
        buff.write("all:");
        buff.write(Double.toString(ccAll));
        buff.write("|");
        buff.write(Double.toString(nrmseAll));
        
        buff.write("\npsoCfs:");
        buff.write(Double.toString(ccPSOCfs));
        buff.write("|");
        buff.write(Double.toString(nrmsePSOCfs));
        
        buff.write("\npsoWrap:");
        buff.write(Double.toString(ccPSOWrap));
        buff.write("|");
        buff.write(Double.toString(nrmsePSOWrap));

        buff.close();
        System.out.println("Finish_knn_"+fileTrainName[data_idx]);
    }
    
}
