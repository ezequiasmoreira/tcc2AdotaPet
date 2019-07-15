package com.adotaPet.api.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.adotaPet.api.domain.Acompanhamento;
import com.adotaPet.api.domain.Adocao;
import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.domain.Cidade;
import com.adotaPet.api.domain.Doenca;
import com.adotaPet.api.domain.Estado;
import com.adotaPet.api.domain.Ong;
import com.adotaPet.api.domain.Pessoa;
import com.adotaPet.api.domain.Raca;
import com.adotaPet.api.domain.enums.AcompanhamentoSituacao;
import com.adotaPet.api.domain.enums.AcompanhamentoStatus;
import com.adotaPet.api.domain.enums.AdocaoStatus;
import com.adotaPet.api.domain.enums.AnimalGenero;
import com.adotaPet.api.domain.enums.AnimalStatus;
import com.adotaPet.api.domain.enums.Perfil;
import com.adotaPet.api.domain.enums.Porte;
import com.adotaPet.api.domain.enums.Sexo;
import com.adotaPet.api.repository.AcompanhamentoRepository;
import com.adotaPet.api.repository.AdocaoRepository;
import com.adotaPet.api.repository.AnimalRepository;
import com.adotaPet.api.repository.CidadeRepository;
import com.adotaPet.api.repository.DoencaRepository;
import com.adotaPet.api.repository.EstadoRepository;
import com.adotaPet.api.repository.OngRepository;
import com.adotaPet.api.repository.PessoaRepository;
import com.adotaPet.api.repository.RacaRepository;

@Service
public class DBService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private OngRepository ongRepository;
	@Autowired
	private RacaRepository racaRepository;
	@Autowired
	private DoencaRepository doencaRepository;
	@Autowired
	private AnimalRepository animalRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private AdocaoRepository adocaoRepository;
	@Autowired
	private AcompanhamentoRepository acompanhamentoRepository;
	@Autowired
	private OngService ongService;
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public void instanciateTestDataBase() throws ParseException {
		
		Estado est1 = new Estado(null,"Minas Gerais",31);
		Estado est2 = new Estado(null,"Santa Catarina",42);
		
		Cidade c1 = new Cidade(null,"Mantena",est1);
		Cidade c2 = new Cidade(null,"Criciuma",est2);
		Cidade c3 = new Cidade(null,"Florianipolis",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
				
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Ong ong1 = new Ong(null, 10,"Avenida Sebastião Toledo dos santos","785","Proximo a moradas da colina","Maria ceu","88806-620",
				c2,"Ong LTDA","Ong do bem","62.769.648/0001-84");
		Ong ong2 = new Ong(null, 11,"Libano jose gomes","45","Proximo ao pisofer","Santa luzia","88806-000",
				c3,"Ong LTDA","Cao amigo","98.013.581/0001-09");
				
		ongRepository.saveAll(Arrays.asList(ong1,ong2));
		
		Ong ong10 =  ongService.find(1);
		Ong ong11 =  ongService.find(2);
		Raca raca1 = new Raca(null,1,"Pastor Alemão");
		Raca raca2 = new Raca(null,4,"Buldogue");
		Raca raca3 = new Raca(null,12,"Chihuahua");
		Raca raca4 = new Raca(null,20,"Dalmata");
		Raca raca5 = new Raca(null,22,"Pinscher");
		
		racaRepository.saveAll(Arrays.asList(raca1,raca2,raca3,raca4,raca5));
		
		Pessoa p1 = new Pessoa(null, 5, "Avenida sete de setembro", "45", "Proximo ao ebenezer", "Treze de junho", "35290-000", c1, "Pedro", Sexo.MASCULINO, Perfil.USUARIO, "108.963.205-96", "895632145", "pedro@hotmail.com", pe.encode("123"), "98564712",null);
		p1.addPerfil(Perfil.USUARIO);
		
		Pessoa p2 = new Pessoa(null, 51, "Santo amaro", "80", "Apt 87", "Centro", "35290-200", c1, "Sandra", Sexo.FEMININO, Perfil.VOLUNTARIO, "108.963.205-50", "8957895145", "sandra@hotmail.com", pe.encode("147"), "98564877712",ong1);
		p2.addPerfil(Perfil.VOLUNTARIO);
		p2.addPerfil(Perfil.USUARIO);
		
		Pessoa p3 = new Pessoa(null, 59, "Lacerda campos", "7850", null, "Bela vista", "88697-896", c3, "Camilo", Sexo.MASCULINO, Perfil.ADMIN, "108.963.205-89", "895788745", "camilo@hotmail.com", pe.encode("caca"), "9856482",ong1);
		p3.addPerfil(Perfil.USUARIO);
		p3.addPerfil(Perfil.VOLUNTARIO);
		p3.addPerfil(Perfil.ADMIN);
		
		Pessoa p4 = new Pessoa(null, 100, "Libano jose gomes", "785", "casa 45", "Santa luzia", "88806-620", c2, "Ezequias", Sexo.MASCULINO, Perfil.MASTER, "107.458.987-87", "789456123", "ezequiasmoreira@hotmail.com", pe.encode("0123"), "98410553",null);
		p4.addPerfil(Perfil.MASTER);
		p4.addPerfil(Perfil.USUARIO);
		p4.addPerfil(Perfil.VOLUNTARIO);
		p4.addPerfil(Perfil.ADMIN);
		
		pessoaRepository.saveAll(Arrays.asList(p1,p2,p3,p4));
		
		Doenca doenca1 = new Doenca(null,1,"Alergia alimentar",ong10);
		Doenca doenca2 = new Doenca(null,2,"Depressão",ong10);
		Doenca doenca3 = new Doenca(null,3,"Erlichiose",ong10);
		Doenca doenca4 = new Doenca(null,4,"Insuficiência renal",ong11);
		Doenca doenca5 = new Doenca(null,5,"Obesidade",ong11);
		Doenca doenca6 = new Doenca(null,6,"Otite",ong11);
		
		doencaRepository.saveAll(Arrays.asList(doenca1,doenca2,doenca3,doenca4,doenca5,doenca6));
		
		Animal animal1 = new Animal(null, 5,"Belinha", AnimalGenero.FEMEA, Porte.PEQUENO, true, false, true, ong1, AnimalStatus.DISPONIVEL, raca1);
		animal1.getDoencas().addAll(Arrays.asList(doenca1,doenca4));
		Animal animal2 = new Animal(null, 8,"Charopinho", AnimalGenero.MACHO, Porte.PEQUENO, true, true, false, ong1, AnimalStatus.PROCESSAMENTO, raca1);
		Animal animal3 = new Animal(null, 9,"Chulinho", AnimalGenero.MACHO, Porte.MEDIO, false, false, true, ong2, AnimalStatus.ADOTADO, raca2);
		animal3.getDoencas().addAll(Arrays.asList(doenca2,doenca3,doenca5,doenca6));
		Animal animal4 = new Animal(null, 10,"Lindinha", AnimalGenero.FEMEA, Porte.MEDIO, false, true, false, ong2, AnimalStatus.ADOTADO, raca4);
		animal4.getDoencas().addAll(Arrays.asList(doenca1,doenca2,doenca3,doenca4,doenca5));
		Animal animal5 = new Animal(null, 15,"Chulinho", AnimalGenero.FEMEA, Porte.GRANDE, false, true, true, ong2, AnimalStatus.DISPONIVEL, raca5);
		animal5.getDoencas().addAll(Arrays.asList(doenca6));
		animalRepository.saveAll(Arrays.asList(animal1,animal2,animal3,animal4,animal5));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Adocao adocao1 = new Adocao(null, 2, sdf.parse("30/06/2019 11:32"), null, AdocaoStatus.AGUARDANDO, p1, null, "Agurdando reposta da ong", ong1, animal1);
		Adocao adocao2 = new Adocao(null, 3, sdf.parse("20/05/2019 08:32"), sdf.parse("22/05/2019 10:00"), AdocaoStatus.APROVADO, p1, p2, "Adocao concluida", ong2, animal5);
		Adocao adocao3 = new Adocao(null, 4, sdf.parse("25/05/2019 18:00"), sdf.parse("25/05/2019 18:03"), AdocaoStatus.REJEITADO, p2, null, "Perfil indisponvel para adocao", ong1, animal1);
		Adocao adocao4 = new Adocao(null, 5, sdf.parse("28/06/2019 14:00"), null, AdocaoStatus.ANALISE, p1, p3, null, ong1, animal1);
		adocaoRepository.saveAll(Arrays.asList(adocao1,adocao2,adocao3,adocao4));
		
		Acompanhamento acom1 =new Acompanhamento(null, 1, "Acompamento após 1 mês de adoção", AcompanhamentoStatus.ABERTO,AcompanhamentoSituacao.ANALISE, null, sdf.parse("02/07/2019 10:00"), null, adocao1);
		Acompanhamento acom2 =new Acompanhamento(null, 2, "Acompanhamento do chulinho", AcompanhamentoStatus.AGENDADO, AcompanhamentoSituacao.ANALISE, "Tentando contato", sdf.parse("12/07/2019 10:20"), null, adocao2);
		Acompanhamento acom3 =new Acompanhamento(null, 3, "Acompanhamento do chulinho segunda tentativa", AcompanhamentoStatus.FINALIZADO, AcompanhamentoSituacao.OTIMO, null,  sdf.parse("13/07/2019 11:20"), sdf.parse("13/07/2019 11:30"), adocao2);	
		
		acompanhamentoRepository.saveAll(Arrays.asList(acom1,acom2,acom3));
		
				
	}

}
