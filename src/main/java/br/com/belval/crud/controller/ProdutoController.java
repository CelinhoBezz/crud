package br.com.belval.crud.controller;

//amework.beans.factory.annotati

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.belval.crud.model.Produto;
import br.com.belval.crud.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
//	private static List<Produto> lista = new ArrayList<>();
//	private static int proxId = 1;
	@Autowired
	private ProdutoRepository repository;
	
	@GetMapping("/produto/novo")
	public String novo(Model model) {
		model.addAttribute("produto", new Produto());
		return "produto";
	}
	
	@GetMapping("/produto/{id}/edit")
	public String editar(@PathVariable int id, Model model) {
		
		Produto produto = repository.findById(id);
		
		if (produto == null) {
			return "produto-nao-encontrado";
		}
		
		model.addAttribute("produto", produto);
		
		return "produto";
	}
	
	@PostMapping("/produto/novo")
	public ModelAndView novo(Produto produto, RedirectAttributes redirectAtributtes){
		ModelAndView modelAndView = new ModelAndView("redirect:/produto/list");
		
		if (produto.getId() == 0) {
//			insert(produto);
			redirectAtributtes.addFlashAttribute("msg", "Novo produto criado!");
		} else {
//			update(produto);
			redirectAtributtes.addFlashAttribute("msg", "Produto atualizado!");
		}
		repository.save(produto);
		
		return modelAndView;
	}

//	private void insert(Produto produto) {
////		produto.setId(proxId++);
////		lista.add(produto);
//		repository.save(produto);
//	}

//	private void update(Produto produto) {
//		repository.save(produto);
////		ListIterator<Produto> it = lista.listIterator();
////		while(it.hasNext()) {
////			Produto encontrado = it.next();
////			if (encontrado.getId() == produto.getId()) {
////				it.remove();
////				it.add(produto);
////			}
////		}
//	}

	@GetMapping("/produto/list")
	public String list(Model model) {
//		model.addAttribute("produtos", lista);
		model.addAttribute("produtos", repository.findAll());
		return "lista-produtos";
	}
	
	@GetMapping("/produto/{id}")
	public String detalhe(@PathVariable int id, Model model) {
		Produto produto = repository.findById(id);//buscarPorId.
		
		if (produto != null) {
			model.addAttribute("novoProduto", produto);
			return "novo-produto-criado";
		}
		
		return "produto-nao-encontrado";
	}
//
//	private Produto buscarPorId(int id) {
//		Produto encontrou = null;
//		for(Produto p : lista) {
//			if (p.getId() == id) {
//				//encontrou o produto solicitado
//				encontrou = p;
//				break;
//			}
//		}
//		return encontrou;
//	}
	
	@GetMapping("/produto/{id}/delete")
	public ModelAndView deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView("redirect:/produto/list");
//		ListIterator<Produto> it = lista.listIterator();
//		while(it.hasNext()) {
//			Produto encontrado = it.next();
//			if (encontrado.getId() == id) {
//				it.remove();
//				break;
//			}
//		}
		repository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("msg","Produto excluído!");
		return view;
	}

	
}
