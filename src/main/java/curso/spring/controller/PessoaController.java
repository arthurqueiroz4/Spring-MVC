package curso.spring.controller;

import curso.spring.model.Pessoa;
import curso.spring.repository.PessoaRepository;
import javax.validation.Valid;

import curso.spring.repository.TelefoneRepository;
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

    @RequestMapping(method = RequestMethod.GET, value = "**/cadastropessoa")
    public ModelAndView inicio(){
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        andView.addObject("pessoas", pessoaRepository.findAll());
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/**/salvarpessoa")
    public ModelAndView salvar (@Valid Pessoa pessoa, BindingResult bindingResult){

        pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getId()));

        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
            modelAndView.addObject("pessoas", pessoasIt);
            modelAndView.addObject("pessoaobj", pessoa);
            List<String> msg =new ArrayList<String>();
            for (ObjectError erro : bindingResult.getAllErrors()) {
                msg.add(erro.getDefaultMessage());
            }
            modelAndView.addObject("msg", msg);
            return modelAndView;
        }

        pessoaRepository.save(pessoa);

        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoasIt);
        andView.addObject("pessoaobj", new Pessoa());

        return andView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
    public ModelAndView pessoas(){
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoasIt);
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    }
    @GetMapping("/editarpessoa/{idpessoa}")
    public ModelAndView editar(@PathVariable("idpessoa") Long id){
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        andView.addObject("pessoaobj", pessoa.get());
        return andView;
    }

    @GetMapping("removerpessoa/{idpessoa}")
    public ModelAndView excluir(@PathVariable("idpessoa") Long id){
        pessoaRepository.deleteById(id);
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        andView.addObject("pessoas", pessoaRepository.findAll());
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    }
    @PostMapping("**/pesquisapessoa")
    public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa){
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        andView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    }
}