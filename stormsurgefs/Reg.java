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
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.*;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

/**
 *
 * @author truongtran
 */
public class Reg 
{
    //Linear Regression
    public static double[] LR(Instances train, Instances test, String name) throws Exception
    {
        //Train
        LinearRegression lr=new LinearRegression();
        lr.buildClassifier(train);
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(lr, test);
        double[] results = new double[2];
        results[0] = eval.correlationCoefficient();
        results[1] = eval.rootRelativeSquaredError();
        //Write features' name
        File file=new File("lr_features_"+name+".txt");
        FileWriter fileWriter=new FileWriter(file.getAbsoluteFile());
        BufferedWriter buff=new BufferedWriter(fileWriter);
        buff.write("\nNumber of features: ");
        buff.write(Integer.toString(train.numAttributes()-1));
        for(int i=0;i<train.numAttributes()-1;i++)
            buff.write("\n"+train.attribute(i).name());
        buff.close();
        //Write results of each testing instance
        FileOutputStream fos=new FileOutputStream("lr_results_"+name+".xlsx");
        Workbook workBook =new XSSFWorkbook();
        Sheet sheet;
        sheet=workBook.createSheet("results");
        int rowIndex=0;
        for(int i=0; i<test.numInstances();i++)
        {
            Row row=sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(test.instance(i).classValue());
            row.createCell(1).setCellValue(eval.evaluateModelOnce(lr, test.instance(i)));
        }
        workBook.write(fos);
        workBook.close();
        return results;
    }
    //REPTree
    public static double[] REPTree(Instances train, Instances test, String name) throws Exception
    {
        //Train
        REPTree repTree=new REPTree();
        repTree.buildClassifier(train);
        repTree.buildClassifier(train);
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(repTree, test);
        double[] results = new double[2];
        results[0] = eval.correlationCoefficient();
        results[1] = eval.rootRelativeSquaredError();
        //Write features' name
        File file=new File("rep_features_"+name+".txt");
        FileWriter fileWriter=new FileWriter(file.getAbsoluteFile());
        BufferedWriter buff=new BufferedWriter(fileWriter);
        buff.write("\nNumber of features: ");
        buff.write(Integer.toString(train.numAttributes()-1));
        for(int i=0;i<train.numAttributes()-1;i++)
            buff.write("\n"+train.attribute(i).name());
        buff.close();
        //Write results of each testing instance
        FileOutputStream fos=new FileOutputStream("rep_results_"+name+".xlsx");
        Workbook workBook =new XSSFWorkbook();
        Sheet sheet;
        sheet=workBook.createSheet("results");
        int rowIndex=0;
        for(int i=0; i<test.numInstances();i++)
        {
            Row row=sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(test.instance(i).classValue());
            row.createCell(1).setCellValue(eval.evaluateModelOnce(repTree, test.instance(i)));
        }
        workBook.write(fos);
        workBook.close();
        return results;
    }
    
    //Random forest
    public static double[] RandomForest(Instances train, Instances test, String name) throws Exception
    {
        //Train
        RandomForest ranForest=new RandomForest();
        
        ranForest.setNumIterations(200);//.setNumTrees(200);
        ranForest.buildClassifier(train);
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(ranForest, test);
        double[] results = new double[2];
        results[0] = eval.correlationCoefficient();
        results[1] = eval.rootRelativeSquaredError();
        //Write features' name
        File file=new File("ran_features_"+name+".txt");
        FileWriter fileWriter=new FileWriter(file.getAbsoluteFile());
        BufferedWriter buff=new BufferedWriter(fileWriter);
        buff.write("\nNumber of features: ");
        buff.write(Integer.toString(train.numAttributes()-1));
        for(int i=0;i<train.numAttributes()-1;i++)
            buff.write("\n"+train.attribute(i).name());
        buff.close();
        //Write results of each testing instance
        FileOutputStream fos=new FileOutputStream("ran_results_"+name+".xlsx");
        Workbook workBook =new XSSFWorkbook();
        Sheet sheet;
        sheet=workBook.createSheet("results");
        int rowIndex=0;
        for(int i=0; i<test.numInstances();i++)
        {
            Row row=sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(test.instance(i).classValue());
            row.createCell(1).setCellValue(eval.evaluateModelOnce(ranForest, test.instance(i)));
        }
        workBook.write(fos);
        workBook.close();
        return results;
    }
    
    //Knn-based Regression
    public static double[] KnnReg(Instances train, Instances test, String name) throws Exception
    {
        //Train
        IBk knnReg=new IBk(3);
        knnReg.buildClassifier(train);
        knnReg.buildClassifier(train);
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(knnReg, test);
        double[] results = new double[2];
        //results[0] = eval.correlationCoefficient();
        results[1] = eval.rootRelativeSquaredError();
        //Write features' name
        File file=new File("knn_features_"+name+".txt");
        FileWriter fileWriter=new FileWriter(file.getAbsoluteFile());
        BufferedWriter buff=new BufferedWriter(fileWriter);
        buff.write("\nNumber of features: ");
        buff.write(Integer.toString(train.numAttributes()-1));
        for(int i=0;i<train.numAttributes()-1;i++)
            buff.write("\n"+train.attribute(i).name());
        buff.close();
        //Write results of each testing instance
        FileOutputStream fos=new FileOutputStream("knn_results_"+name+".xlsx");
        Workbook workBook =new XSSFWorkbook();
        Sheet sheet;
        sheet=workBook.createSheet("results");
        int rowIndex=0;
        for(int i=0; i<test.numInstances();i++)
        {
            Row row=sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(test.instance(i).classValue());
            row.createCell(1).setCellValue(eval.evaluateModelOnce(knnReg, test.instance(i)));
        }
        workBook.write(fos);
        workBook.close();
        return results;
    }
    
    //SVM-based Regression
    public static double[] SvmReg(Instances train, Instances test, String name) throws Exception
    {
        //Train
        SMOreg smoReg=new SMOreg();
        smoReg.buildClassifier(train);
        smoReg.buildClassifier(train);
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(smoReg, test);
        double[] results = new double[2];
        results[0] = eval.correlationCoefficient();
        results[1] = eval.rootRelativeSquaredError();
        //Write features' name
        File file=new File("svm_features_"+name+".txt");
        FileWriter fileWriter=new FileWriter(file.getAbsoluteFile());
        BufferedWriter buff=new BufferedWriter(fileWriter);
        buff.write("\nNumber of features: ");
        buff.write(Integer.toString(train.numAttributes()-1));
        for(int i=0;i<train.numAttributes()-1;i++)
            buff.write("\n"+train.attribute(i).name());
        buff.close();
        //Write results of each testing instance
        FileOutputStream fos=new FileOutputStream("svm_results_"+name+".xlsx");
        Workbook workBook =new XSSFWorkbook();
        Sheet sheet;
        sheet=workBook.createSheet("results");
        int rowIndex=0;
        for(int i=0; i<test.numInstances();i++)
        {
            Row row=sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(test.instance(i).classValue());
            row.createCell(1).setCellValue(eval.evaluateModelOnce(smoReg, test.instance(i)));
        }
        workBook.write(fos);
        workBook.close();
        return results;
    }
    //Neural Network based regression
    public static double[] MNPReg(Instances train, Instances test, String name) throws Exception
    {
        //Train
        MultilayerPerceptron mnp=new MultilayerPerceptron();
        mnp.setTrainingTime(10000);
        mnp.buildClassifier(train);
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(mnp, test);
        double[] results = new double[2];
        //results[0] = eval.correlationCoefficient();
        results[1] = eval.rootRelativeSquaredError();
        //Write features' name
        File file=new File("nn_features_"+name+".txt");
        FileWriter fileWriter=new FileWriter(file.getAbsoluteFile());
        BufferedWriter buff=new BufferedWriter(fileWriter);
        buff.write("\nNumber of features: ");
        buff.write(Integer.toString(train.numAttributes()-1));
        for(int i=0;i<train.numAttributes()-1;i++)
            buff.write("\n"+train.attribute(i).name());
        buff.close();
        //Write results of each testing instance
        FileOutputStream fos=new FileOutputStream("nn_results_"+name+".xlsx");
        Workbook workBook =new XSSFWorkbook();
        Sheet sheet;
        sheet=workBook.createSheet("results");
        int rowIndex=0;
        for(int i=0; i<test.numInstances();i++)
        {
            Row row=sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(test.instance(i).classValue());
            row.createCell(1).setCellValue(eval.evaluateModelOnce(mnp, test.instance(i)));
        }
        workBook.write(fos);
        workBook.close();
        return results;
    }
}
