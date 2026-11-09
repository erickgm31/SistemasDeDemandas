// Utiliza o mesmo endereço do front-end.
const API_BASE = "";


// Busca o token CSRF no back-end.
async function obterTokenCsrf() {

  const resposta = await fetch("/usuario/csrf", {
    method: "GET",
    credentials: "same-origin"
  });

  if (!resposta.ok) {
    throw new Error("Não foi possível obter o token de segurança.");
  }

  return await resposta.json();
}


// Envia os dados para a API utilizando a sessão e o token CSRF.
async function postJson(caminho, dados) {

  let csrf;

  try {
    csrf = await obterTokenCsrf();
  } catch (erro) {
    throw new Error(erro.message);
  }

  const headers = {
    "Content-Type": "application/json"
  };

  // Adiciona o token CSRF ao cabeçalho da requisição.
  headers[csrf.headerName] = csrf.token;

  let resposta;

  try {
    resposta = await fetch(API_BASE + caminho, {
      method: "POST",
      credentials: "same-origin",
      headers: headers,
      body: JSON.stringify(dados)
    });

  } catch (erro) {
    throw new Error(
      "Não foi possível conectar à API. Verifique se ela está rodando."
    );
  }

  const texto = await resposta.text();

  if (!resposta.ok) {

    // Se a sessão expirou, encaminha o funcionário para o login.
    if (resposta.status === 401 && caminho !== "/usuario/login") {
      window.location.replace("/login.html");
      throw new Error("Sua sessão expirou. Faça login novamente.");
    }

    const ehJson = texto.trim().startsWith("{");

    throw new Error(!texto || ehJson
      ? "A API respondeu com status " + resposta.status
      : texto);
  }

  return texto;
}