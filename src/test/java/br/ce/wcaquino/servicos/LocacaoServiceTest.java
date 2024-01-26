package br.ce.wcaquino.servicos;



import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoServiceTest {

	private LocacaoService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() { //anotation Before acontece antes de cada metodo, repete a cada metodo
		service = new LocacaoService();
	}
	
	@Test
	public void testeLocacao() throws Exception {

		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0),
				new Filme("Filme 2", 2, 4.5));
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(9.5)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception{
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 2", 0, 4.0),
				new Filme("Filme 1", 1, 5.0));

		//acao
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException{
		//cenario
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 2", 1, 4.0);
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

		//acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}
	

	@Test
	public void testLocacao_FilmesNulo() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		//acao
		service.alugarFilme(usuario, null);
	}

	@Test
	public void testLocacao_FilmesVazio() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = new ArrayList<>();

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		//acao
		service.alugarFilme(usuario, filmes);
	}
}
