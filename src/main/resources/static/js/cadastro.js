function verificar(){
    var erros = [];
    var numeroErros = 0;
	var nome = $("#nome").val();
    var email = $("#email").val();
    var idade = $("#idade").val();
    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    
    if (nome == ""){
		erros.push("<br><br>- Name is a required field");
        numeroErros++;
	}

    if(idade < 0 || idade >= 110 || idade == ""){
        erros.push("<br><br>- This age ist not allowed");
        numeroErros++;
        $("#idade").val(" ")
    }
	
	if (!regexEmail.test(email)) {
        erros.push("<br><br>- Enter a valid email.");
        numeroErros++;
        $("#email").val(" ");
    }
    /*
    if(email.substring(email.length - 10) != "@gmail.com"){
        erros.push("<br><br>- É necessário que seu email possua o prefixo '@gmail.com'");
        numeroErros++;
        $("#email").val(" ")
    }
    */

    if(numeroErros > 0) gerarMessageBox(2, "Erro(s):"+erros, "Try again");
    else salvar();
}

function salvar(){
    $.ajax({
        method: "POST",
        url: "/pessoa",
        data: JSON.stringify(
        {
            codigo: $('#codigo').val(),
            nome: $("#nome").val(),
            idade: $("#idade").val(),
            email: $("#email").val()
        }),
        contentType: "application/json; charset-utf8",
        success: function (dados){
            gerarMessageBox(1, dados, "Proceed");
        }
    }).fail(function(err){
        tratarErro(err);
    });
}