/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stormsurgefs;
import java.util.Random;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;


/**
 *
 * @author HienNT
 */
public class filterFS {
	Instances iris = null;
	NaiveBayes nb;
	public void loadArff(String arffInput){
		DataSource source = null;
		try {
			source = new DataSource(arffInput);
			iris = source.getDataSet();
			iris.setClassIndex(iris.numAttributes() - 1);
		} catch (Exception e1) {
		}
	}

	public void selectFeatures(){
		AttributeSelection attSelection = new AttributeSelection();
	    CfsSubsetEval eval = new CfsSubsetEval();
	    BestFirst search = new BestFirst();
	    attSelection.setEvaluator(eval);
	    attSelection.setSearch(search);
	    try {
			attSelection.SelectAttributes(iris);
			int[] attIndex = attSelection.selectedAttributes();
			System.out.println(Utils.arrayToString(attIndex));
		} catch (Exception e) {
		}
	}

	public void selectFeaturesWithFilter(){
		weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();
	    CfsSubsetEval eval = new CfsSubsetEval();
	    BestFirst search = new BestFirst();
	    filter.setEvaluator(eval);
	    filter.setSearch(search);
	    try {
			filter.setInputFormat(iris);
			Instances newData = Filter.useFilter(iris, filter);
			System.out.println(newData);
		} catch (Exception e) {
		}
	}
	
	public void selectFeaturesWithClassifiers(){
		AttributeSelectedClassifier classifier = new AttributeSelectedClassifier();
		CfsSubsetEval eval = new CfsSubsetEval();
		BestFirst search = new BestFirst();
		nb = new NaiveBayes();
		classifier.setClassifier(nb);
		classifier.setEvaluator(eval);
		classifier.setSearch(search);
		Evaluation evaluation;
		try {
			evaluation = new Evaluation(iris);
			evaluation.crossValidateModel(classifier, iris, 10, new Random(1));
			System.out.println(evaluation.toSummaryString());
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args){
		filterFS test = new filterFS();
		test.loadArff("E:\\hiennt\\Working\\DeTaiDuBaoKTTV\\HaiVan\\StormSurgeFs\\data\\train_5h_10.arff");
		test.selectFeatures();
		test.selectFeaturesWithFilter();
		test.selectFeaturesWithClassifiers();
	}
}