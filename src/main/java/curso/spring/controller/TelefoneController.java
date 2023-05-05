package curso.spring.controller;

import curso.spring.model.Pessoa;
import curso.spring.model.Telefone;
import curso.spring.repository.PessoaRepository;
import curso.spring.repository.TelefoneRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TelefoneController{
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TelefoneRepository telefoneRepository;
    @GetMapping("/telefones/{idpessoa}")
    public ModelAndView telefones(@PathVariable("idpessoa") Long id){
        ModelAndView andView = new ModelAndView("cadastro/telefones");
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        andView.addObject("telefones", telefoneRepository.getTelefones(id));
        andView.addObject("pessoaobj", pessoa.get());
        andView.addObject("telefoneobj", new Telefone());
        return andView;
    }
    @PostMapping("**/addfonePessoa/{pessoaid}")
    public ModelAndView addFonePessoa(@Valid Telefone telefone,
                                      BindingResult bindingResult,
                                      @PathVariable("pessoaid") Long pessoaId){

        Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaId);

        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
            List<Telefone> telefones = telefoneRepository.findAll();
            modelAndView.addObject("telefones", telefones);
            modelAndView.addObject("telefoneobj", telefone);
            modelAndView.addObject("pessoaobj", pessoa.get());

            List<String> msg =new ArrayList<String>();
            for (ObjectError erro : bindingResult.getAllErrors()) {
                msg.add(erro.getDefaultMessage());
            }
            modelAndView.addObject("msg", msg);
            return modelAndView;
        }


        telefone.setPessoa(pessoa.get());
        telefoneRepository.save(telefone);
        ModelAndView andView = new ModelAndView("cadastro/telefones");
        andView.addObject("pessoaobj", pessoa.get());
        andView.addObject("telefoneobj", telefone);
        andView.addObject("telefones", telefoneRepository.getTelefones(pessoaId));
        return andView;
    }

    @GetMapping("**/removertelefone/{idtelefone}")
    public ModelAndView deleteTelefone(@PathVariable("idtelefone") Long idtelefone){
        Telefone telefone = telefoneRepository.findById(idtelefone).orElse(null);
        Pessoa pessoa = telefone.getPessoa();
        telefoneRepository.deleteById(idtelefone);
        ModelAndView andView = new ModelAndView("cadastro/telefones");
        andView.addObject(andView.addObject("pessoaobj", pessoa));
        andView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
        return andView;
    }

    //@GetMapping("**/listar")
}
