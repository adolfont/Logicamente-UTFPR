Requisitos:
- TomCat
- Java Web Start

1. Exporte a aplica��o como um arquivo JAR (utilizando o Eclipse)
	Nome do arquivo: logicamente.jar
	* Deve ser selecionada a op��o para incluir todos os recursos requiridos.
	
2. Edite o arquivo logicamenteJNLP.jnlp, colocando o nome ou ip do servidor Tomcat onde a aplica��o ser� publicada.
	<jar href="http://192.168.0.100:8080/logica/logicamente.jar"/>
	<jar href="http://192.168.0.100:8080/logica/lib/java_cup.jar"/>
	<jar href="http://192.168.0.100:8080/logica/lib/JFlex.jar"/>

3. Dentro da pasta webapps do Tomcat, crie a pasta logica.
4. Copie os arquivos logicamente.jar e logicamenteJNLP.jnlp para esta pasta.
5. Dentro da pasta logica criada, crie a pasta lib.
6. Copie os arquivos java_cup.jar e JFlex.jar para esta pasta.
7. Rode o servidor Tomcat.

A aplica��o esta pronta para ser acessada :)

Via browser:
Acesse:  http://<seu_servidor>:8080/logica/logicamenteJNLP.jnlp
Se for perguntado a forma de execu��o, seleciona para executar com o Java Web Start

Via �cone:
- Clique com o bot�o direito na �rea de trabalho (ou onde queira criar o icone).
- Selecione Novo/Atalho.
- Insira o link http://<seu_servidor>:8080/logica/logicamenteJNLP.jnlp (com o seu servidor) e clique em avan��r.
- Escolha um nome para o atalho. O atalho foi criado. Dois cliques para executar a aplica��o.


