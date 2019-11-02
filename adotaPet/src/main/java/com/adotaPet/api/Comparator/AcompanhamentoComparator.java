package com.adotaPet.api.Comparator;

import java.util.Comparator;

import com.adotaPet.api.domain.Acompanhamento;

public class AcompanhamentoComparator implements Comparator<Acompanhamento> {
	
    public int compare(Acompanhamento acompanhamento, Acompanhamento outroAcompanhamento) {
        return acompanhamento.getId().
                compareTo(outroAcompanhamento.getId());
    }	
}
