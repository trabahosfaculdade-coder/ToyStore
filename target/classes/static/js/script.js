document.addEventListener("DOMContentLoaded", function () {
    configurarPreviewImagem();
    configurarConfirmacaoExclusao();
    configurarMascaraValor();
    configurarBuscaTabela();
    configurarAutoCloseAlert();
});

function configurarPreviewImagem() {
    const inputImagem = document.getElementById("arquivoImagem");
    const preview = document.getElementById("preview-imagem");

    if (!inputImagem || !preview) return;

    inputImagem.addEventListener("change", function () {
        const arquivo = this.files && this.files[0];

        if (!arquivo) {
            preview.src = "";
            preview.style.display = "none";
            return;
        }

        if (!arquivo.type.startsWith("image/")) {
            alert("Selecione um arquivo de imagem válido.");
            inputImagem.value = "";
            preview.src = "";
            preview.style.display = "none";
            return;
        }

        const reader = new FileReader();
        reader.onload = function (e) {
            preview.src = e.target.result;
            preview.style.display = "block";
        };
        reader.readAsDataURL(arquivo);
    });
}

function configurarConfirmacaoExclusao() {
    const linksExcluir = document.querySelectorAll(".btn-excluir");

    if (!linksExcluir.length) return;

    linksExcluir.forEach(function (link) {
        link.addEventListener("click", function (event) {
            const nome = this.getAttribute("data-nome") || "este produto";
            const confirmar = confirm(`Deseja realmente excluir ${nome}?`);

            if (!confirmar) {
                event.preventDefault();
            }
        });
    });
}

function configurarMascaraValor() {
    const campoValor = document.getElementById("valor");

    if (!campoValor) return;

    campoValor.addEventListener("input", function () {
        let valor = this.value.replace(",", ".");
        this.value = valor;
    });
}

function configurarBuscaTabela() {
    const campoBusca = document.getElementById("busca-produto");
    const linhas = document.querySelectorAll(".linha-produto");

    if (!campoBusca || !linhas.length) return;

    campoBusca.addEventListener("input", function () {
        const texto = this.value.toLowerCase().trim();

        linhas.forEach(function (linha) {
            const conteudo = linha.textContent.toLowerCase();
            linha.style.display = conteudo.includes(texto) ? "" : "none";
        });
    });
}

function configurarAutoCloseAlert() {
    const alerta = document.getElementById("mensagem-sucesso");

    if (!alerta) return;

    setTimeout(function () {
        alerta.style.opacity = "0";
        alerta.style.transition = "0.4s";

        setTimeout(function () {
            alerta.style.display = "none";
        }, 400);
    }, 3000);
}