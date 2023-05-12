/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stormsurgefs;
import javax.swing.*;

/**
 *
 * @author Tran Cao Truong
 */
public class StormSurgeFs 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception 
    {
        int pro_idx = Integer.parseInt(JOptionPane.showInputDialog("Choice of machine learning techniques to do: O for MLP, 1 for SVM, 2 for kNN, 3 for RF"));
        int data_idx = Integer.parseInt(JOptionPane.showInputDialog("Choice of dataset to do"));
        System.out.println(pro_idx+ "  "+ data_idx);
        switch(pro_idx)
        {
            case 0: nn_functions.mainFunction(data_idx);
                break;
            case 1:svm_functions.mainFunction(data_idx);
                break;
            case 2:knn_functions.mainFunction(data_idx);
                break;
            case 3:ran_functions.mainFunction(data_idx);
                break;
            
        }
    }
    
}
