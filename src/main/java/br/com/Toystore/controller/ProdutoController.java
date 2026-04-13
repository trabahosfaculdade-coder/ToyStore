package br.com.Toystore.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.Toystore.model.Produto;
import br.com.Toystore.model.Usuario;
import br.com.Toystore.repository.UsuarioRepository;
import br.com.Toystore.service.ProdutoService;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // =========================
    // USUÁRIO LOGADO DISPONÍVEL EM TODAS AS TELAS
    // =========================
    @ModelAttribute
    public void adicionarUsuarioLogado(Model model, HttpSession session) {
        model.addAttribute("usuarioLogado", session.getAttribute("usuarioLogado"));
    }

    // =========================
    // HOME
    // =========================
    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("produtos", service.listarTodos());
        model.addAttribute("destaque", service.getProdutoDestaque());
        model.addAttribute("produtosDestaque", service.listarTodos().stream().limit(5).toList());
        return "index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("produtos", service.listarTodos());
        model.addAttribute("destaque", service.getProdutoDestaque());
        model.addAttribute("produtosDestaque", service.listarTodos().stream().limit(5).toList());
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("produtos", service.listarTodos());
        model.addAttribute("destaque", service.getProdutoDestaque());
        model.addAttribute("produtosDestaque", service.listarTodos().stream().limit(5).toList());
        return "index";
    }

    // =========================
    // LOJA (LISTA DE PRODUTOS PARA CLIENTE)
    // =========================
    @GetMapping("/produto")
    public String loja(Model model) {
        model.addAttribute("produtos", service.listarTodos());
        return "list_produtos";
    }

    // =========================
    // DETALHE DO PRODUTO
    // =========================
    @GetMapping("/produto/{id}")
    public String verProduto(@PathVariable Long id, Model model) {
        Produto produto = service.buscarPorId(id);

        if (produto == null) {
            return "redirect:/produto";
        }

        model.addAttribute("produto", produto);
        return "produto";
    }

    // =========================
    // ADMIN - LISTA PRODUTOS
    // =========================
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("produtos", service.listarTodos());
        return "adm_list";
    }

    // =========================
    // CRIAR PRODUTO
    // =========================
    @GetMapping("/criar")
    public String criar(Model model) {
        model.addAttribute("produto", new Produto());
        return "criar_p";
    }

    // =========================
    // SALVAR PRODUTO (CRIAR + EDITAR)
    // =========================
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto,
                         @RequestParam(value = "arquivoImagem", required = false) MultipartFile arquivoImagem) {

        Produto existente = null;

        if (produto.getId() != null) {
            existente = service.buscarPorId(produto.getId());
        }

        if (arquivoImagem != null && !arquivoImagem.isEmpty()) {
            try {
                String pasta = System.getProperty("user.dir") + "/uploads/";
                File dir = new File(pasta);

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String nomeArquivo = UUID.randomUUID() + "_" + arquivoImagem.getOriginalFilename();
                File destino = new File(pasta + nomeArquivo);

                arquivoImagem.transferTo(destino);
                produto.setImagem(nomeArquivo);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (existente != null) {
            produto.setImagem(existente.getImagem());
        }

        service.salvar(produto);
        return "redirect:/admin";
    }

    // =========================
    // EDITAR PRODUTO
    // =========================
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("produto", service.buscarPorId(id));
        return "editar_p";
    }

    // =========================
    // EXCLUIR PRODUTO
    // =========================
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/admin";
    }

    // =========================
    // LOGIN USUÁRIO
    // =========================
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestParam String usuario,
                             @RequestParam String senha,
                             Model model,
                             HttpSession session) {

        Usuario user = usuarioRepository.findByUsuario(usuario);

        if (user != null && user.getSenha().equals(senha)) {
            session.setAttribute("usuarioLogado", user.getUsuario());
            return "redirect:/index";
        } else {
            model.addAttribute("erro", "Login inválido");
            return "login";
        }
    }

    // =========================
    // LOGOUT
    // =========================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    // =========================
    // CADASTRO
    // =========================
    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@RequestParam String nome,
                            @RequestParam String email,
                            @RequestParam String usuario,
                            @RequestParam String senha,
                            @RequestParam String confirmarSenha,
                            Model model) {

        if (!senha.equals(confirmarSenha)) {
            model.addAttribute("erro", "As senhas não coincidem");
            return "cadastro";
        }

        Usuario existente = usuarioRepository.findByUsuario(usuario);

        if (existente != null) {
            model.addAttribute("erro", "Usuário já existe");
            return "cadastro";
        }

        Usuario novo = new Usuario();
        novo.setNome(nome);
        novo.setEmail(email);
        novo.setUsuario(usuario);
        novo.setSenha(senha);

        usuarioRepository.save(novo);

        return "redirect:/login";
    }

    // =========================
    // LOGIN ADMIN
    // =========================
    @GetMapping("/adm_l")
    public String loginAdmin() {
        return "adm_l";
    }

    @PostMapping("/login-admin")
    public String fazerLoginAdmin(@RequestParam String usuario,
                                  @RequestParam String senha,
                                  Model model) {

        if (usuario.equals("admin") && senha.equals("1234")) {
            model.addAttribute("produtos", service.listarTodos());
            return "adm_list";
        } else {
            model.addAttribute("erro", "Login admin inválido");
            return "adm_l";
        }
    }

    // =========================
    // EQUIPE
    // =========================
    @GetMapping("/equipe")
    public String equipe() {
        return "equipe";
    }
    
}