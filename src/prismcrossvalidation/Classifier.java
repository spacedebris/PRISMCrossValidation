/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prismcrossvalidation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import weka.associations.Apriori;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.Prism;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.supervised.attribute.Discretize;

/**
 *
 * @author si
 */
public class Classifier {
    static public void crossValidationPRISM_DISCRET()
    throws FileNotFoundException, IOException, Exception
    {
        Instances data = DataLoad.loadData("./src/data/serce.arff");
        data.setClassIndex(data.numAttributes() - 1);

        Discretize filter = new Discretize();
        Prism rules = new Prism();
        
        FilteredClassifier fClassifier = new FilteredClassifier();
        fClassifier.setFilter(filter); //Ustawienie aktualnego filtra
        fClassifier.setClassifier(rules); //Ustawienie aktualnego klasyfikatora

        Evaluation eval = new MyEvaluation(data);
	eval.crossValidateModel(fClassifier, data, 10, new Random(1)); //CV dla 10 foldow
        System.out.println(eval.toSummaryString("Wyniki:", false));
    }
    
    //Generowanie regul asocjacyjnych
    public static void regulyAsocjacyjne()
    throws Exception
    {
        Instances data = DataLoad.loadData("./src/data/osmolski.arff");
        data.setClassIndex(data.numAttributes() - 1);
        
        //-N ->Liczba regul do policzenia (standardowo: 10)
        //-C ->Minmalna ufnosc reguly (standardowo: 0.9).
        
        String[] options = Utils.splitOptions("-N 20 -C 0.6");
        Apriori apriori = new Apriori();
        apriori.setOptions(options);
        apriori.buildAssociations(data); //Generowanie regul asocjacyjnych

        System.out.println("Liczba regul=" + apriori.getNumRules());

        System.out.println(apriori.toString()); //Wypisanie informacji o regulach
    }
    
    
    
}