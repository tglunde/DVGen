package com.tui.dwh;

import com.tui.dwh.model.Cbc;
import com.tui.dwh.model.Nbr;
import com.tui.dwh.model.Subjectarea;

import java.nio.file.Paths;

public class TestJsonParser {
    public static void main(String[] args) throws Exception {
        ELMModelFactory emf = new ELMModelFactory();
        Subjectarea elm = emf.createModel(Paths.get(ClassLoader.getSystemResource("campaign.erdplus").toURI()));
        for(Cbc cbc : elm.getCbcs()) {
            System.out.println(cbc);
        }
        for(Nbr nbr : elm.getNbrs()) {
            System.out.println(nbr);
        }
    }
}
