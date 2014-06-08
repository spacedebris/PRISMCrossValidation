/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prismcrossvalidation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import static prismcrossvalidation.Classifier.fold;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.Prism;
import weka.core.Instances;
import weka.filters.supervised.attribute.Discretize;

/**
 * Klasa implementujaca klasyfikator PRISM
 * @author si
 */
public class Classifier {
    public static int fold = 10;
    
    static public String crossValidationPRISM_DISCRET()
    throws FileNotFoundException, IOException, Exception
    {
        String prismResult = "";
        String source = MainWindow.pathChooseField.getText();
        Instances data = DataLoad.loadData(source.replace("\\", "/"));
        
        data.setClassIndex(data.numAttributes() - 1);

        Discretize filter = new Discretize();
        Prism rules = new Prism();
        
        FilteredClassifier fClassifier = new FilteredClassifier();
        fClassifier.setFilter(filter); //Ustawienie aktualnego filtra
        fClassifier.setClassifier(rules); //Ustawienie aktualnego klasyfikatora

        Evaluation eval = new MyEvaluation(data);
	eval.crossValidateModel(fClassifier, data, fold, new Random(1)); //CV dla 10 foldow
        
        System.out.println("amount of folds: "+fold);
        
        System.out.println(eval.toSummaryString("Wyniki:", false));
        MainWindow.logArea.append(eval.toSummaryString("Wyniki:", false));
        
        return prismResult = eval.toSummaryString("Wyniki:", false);
    }
}
