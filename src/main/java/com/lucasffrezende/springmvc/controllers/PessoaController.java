package com.lucasffrezende.springmvc.controllers;

import com.lucasffrezende.springmvc.models.Pessoa;
import com.lucasffrezende.springmvc.models.Telefone;
import com.lucasffrezende.springmvc.repositories.PessoaRepository;
import com.lucasffrezende.springmvc.repositories.TelefoneRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
    public ModelAndView inicio() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");

        Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
        modelAndView.addObject("pessoas", pessoasIt);
        modelAndView.addObject("pessoaObj", new Pessoa());

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/salvarpessoa")
    public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
            modelAndView.addObject("pessoas", pessoasIt);
            modelAndView.addObject("pessoaObj", pessoa);

            List<String> msg = new ArrayList<>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                msg.add(objectError.getDefaultMessage());
            }

            modelAndView.addObject("msg", msg);
            return modelAndView;
        }

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
    public ModelAndView telefones(@PathVariable("idpessoa") Long idPessoa) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(idPessoa);

        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaObj", pessoa.get());
        modelAndView.addObject("fones", telefoneRepository.getTelefonesByPessoa(idPessoa));
        return modelAndView;
    }

    @PostMapping("/addfonePessoa/{pessoaid}")
    public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("pessoaid") Long idPessoa) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa).get();
        telefone.setPessoa(pessoa);

        telefoneRepository.save(telefone);

        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("fones", telefoneRepository.getTelefonesByPessoa(idPessoa));
        modelAndView.addObject("pessoaObj", new Pessoa());

        return modelAndView;
    }

    @GetMapping("/removertelefone/{idtelefone}")
    public ModelAndView removerTelefone(@PathVariable("idtelefone") Long idTelefone) {
        Pessoa pessoa = telefoneRepository.findById(idTelefone).get().getPessoa();

        telefoneRepository.deleteById(idTelefone);

        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("fones", telefoneRepository.getTelefonesByPessoa(pessoa.getId()));
        modelAndView.addObject("pessoaObj", pessoa);

        return modelAndView;
    }

}
