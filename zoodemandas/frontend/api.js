// Endereço da API. Se mudar a porta ou o host, altere só aqui.
const API_BASE = "http://localhost:8080";

// Envia um JSON por POST. Devolve o texto da resposta se der certo
// e lança um Error com uma mensagem legível se algo falhar.
async function postJson(caminho, dados) {
  let resposta;
  try {
    resposta = await fetch(API_BASE + caminho, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(dados)
    });
  } catch (e) {
    throw new Error("Não foi possível conectar à API. Verifique se ela está rodando.");
  }

  const texto = await resposta.text();

  if (!resposta.ok) {
    // Erros tratados no Controller chegam como texto simples.
    // Se vier um JSON (erro 500 padrão do Spring), mostramos só o status.
    const ehJson = texto.trim().startsWith("{");
    throw new Error(!texto || ehJson
      ? "A API respondeu com status " + resposta.status + "em " + API_BASE + caminho
      : texto);
  }
  return texto;
}
