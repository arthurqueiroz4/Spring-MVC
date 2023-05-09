package curso.spring.controller;

import curso.spring.model.Pessoa;
import curso.spring.repository.PessoaRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import curso.spring.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TelefoneRepository telefoneRepository;
    @Autowired
    private ReportUtil reportUtil;

    @RequestMapping(method = RequestMethod.GET, value = "**/cadastropessoa")
    public ModelAndView inicio(){
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        andView.addObject("pessoas",
                pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/**/salvarpessoa",
                    consumes = {"multipart/form-data"})
    public ModelAndView salvar (@Valid Pessoa pessoa, BindingResult bindingResult,
                                final MultipartFile curriculo) throws IOException {

        pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getId()));

        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            modelAndView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
            modelAndView.addObject("pessoaobj", pessoa);
            List<String> msg =new ArrayList<String>();
            for (ObjectError erro : bindingResult.getAllErrors()) {
                msg.add(erro.getDefaultMessage());
            }
            modelAndView.addObject("msg", msg);
            return modelAndView;
        }

        if (curriculo.getSize() > 0){
            pessoa.setFile(curriculo.getBytes());
            pessoa.setTipoFileCurriculo(curriculo.getContentType());
            pessoa.setNomeFileCurriculo(curriculo.getOriginalFilename());
        }else {
            if (pessoa.getId() != null && pessoa.getId()>0){
                Pessoa pessoaTemp = pessoaRepository.findById(pessoa.getId()).get();
                pessoa.setFile(pessoaTemp.getFile());
                pessoa.setTipoFileCurriculo(pessoaTemp.getTipoFileCurriculo());
                pessoa.setNomeFileCurriculo(pessoaTemp.getNomeFileCurriculo());
            }
        }

        pessoaRepository.save(pessoa);

        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        andView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
        andView.addObject("pessoaobj", new Pessoa());

        return andView;
    }
    @GetMapping("/pessoaspag")
    public ModelAndView carregaPessoaPorPaginacao(@PageableDefault(size = 5) Pageable pageable,
                                                  @RequestParam("nomepesquisa") String nomepesquisa,
                                                  ModelAndView model){

        Page<Pessoa> pagePessoa = pessoaRepository.findPessoaByNamePage(nomepesquisa,pageable);
        model.addObject("pessoas", pagePessoa);
        model.addObject("pessoaobj", new Pessoa());
        model.addObject("nomepesquisa", nomepesquisa);
        model.setViewName("cadastro/cadastropessoa");

        return model;

    }

    @RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
    public ModelAndView pessoas(){
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        andView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
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
        andView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    }
    @PostMapping("**/pesquisapessoa")
    public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa
            , @RequestParam("pesquisasexo") String pesquisasexo,
            @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){

        Page<Pessoa> pessoas = null;

        if (!pesquisasexo.contains("null") && !nomepesquisa.equals("")){
            pessoas = pessoaRepository.findPessoaByNomeAndSexoPage(nomepesquisa, pesquisasexo, pageable);
        } else if (pesquisasexo.contains("null")){
            pessoas = pessoaRepository.findPessoaByNamePage(nomepesquisa, pageable);
        }
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        andView.addObject("pessoas", pessoas);
        //andView.addObject("opcoes", new ArrayList<>({"asd"}));
        andView.addObject("sexo", pesquisasexo);
        andView.addObject("pessoaobj", new Pessoa());
        andView.addObject("nomepesquisa", nomepesquisa);
        return andView;
    }

    @GetMapping("**/baixarcurriculo/{idpessoa}")
    public void baixarCurriculo(@PathVariable("idpessoa") Long id, HttpServletResponse response) throws IOException {

        Pessoa pessoa = pessoaRepository.findById(id).get();
        if (pessoa.getFile() != null){
            response.setContentLength(pessoa.getFile().length);
            response.setContentType(pessoa.getTipoFileCurriculo());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", pessoa.getNomeFileCurriculo());
            response.setHeader(headerKey, headerValue);
            response.getOutputStream().write(pessoa.getFile());

        }
    }

    @GetMapping("**/pesquisapessoa")
    public void imprimePdf(@RequestParam("nomepesquisa") String nomepesquisa
            , @RequestParam("pesquisasexo") String pesquisasexo,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception{

        List<Pessoa> pessoas;

        if (pesquisasexo!=null && !pesquisasexo.isEmpty() && !nomepesquisa.isEmpty() && nomepesquisa!=null){
            pessoas = pessoaRepository.findPessoaBySexoAndByName(pesquisasexo, nomepesquisa);
        }else if (!nomepesquisa.contains("null") && !nomepesquisa.isEmpty()){
            pessoas = pessoaRepository.findPessoaByName(nomepesquisa);
        }else if (!pesquisasexo.contains("null") && !pesquisasexo.isEmpty()){
            pessoas = pessoaRepository.findPessoaBySexo(pesquisasexo);
        } else {
            pessoas = new ArrayList<>();
            pessoaRepository.findAll().forEach(pessoa -> {
                pessoas.add(pessoa);
            });
        }

        byte[] pdf = reportUtil.gerarRelatorio(pessoas, "pessoa", request.getServletContext());
        response.setContentLength(pdf.length);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
        response.setHeader(headerKey,headerValue);
        response.getOutputStream().write(pdf);
    }
}