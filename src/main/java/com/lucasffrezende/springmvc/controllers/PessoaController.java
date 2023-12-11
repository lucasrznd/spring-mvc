package com.lucasffrezende.springmvc.controllers;

import com.lucasffrezende.springmvc.models.Pessoa;
import com.lucasffrezende.springmvc.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
    public ModelAndView inicio() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");

        Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
        modelAndView.addObject("pessoas", pessoasIt);
        modelAndView.addObject("pessoaObj", new Pessoa());

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/salvarpessoa")
    public ModelAndView salvar(Pessoa pessoa) {
        pessoaRepository.save(pessoa);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
        modelAndView.addObject("pessoas", pessoasIt);
        modelAndView.addObject("pessoaObj", new Pessoa());

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
    public ModelAndView listarTodos() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");

        Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
        modelAndView.addObject("pessoas", pessoasIt);
        modelAndView.addObject("pessoaObj", new Pessoa());

        return modelAndView;
    }

    @GetMapping("/editarpessoa/{idpessoa}")
    public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaObj", pessoa.get());
        return modelAndView;
    }

    @GetMapping("/removerpessoa/{idpessoa}")
    public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {
        pessoaRepository.deleteById(idpessoa);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findAll());
        modelAndView.addObject("pessoaObj", new Pessoa());

        return modelAndView;
    }

    @PostMapping("/pesquisarpessoa")
    public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nome) {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findPessoaByNome(nome));
        modelAndView.addObject("pessoaObj", new Pessoa());

        return modelAndView;
    }

    @GetMapping("/telefones/{idpessoa}")
    public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaObj", pessoa.get());
        return modelAndView;
    }

}
