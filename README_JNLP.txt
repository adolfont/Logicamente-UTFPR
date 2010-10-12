Requisitos:
- TomCat
- Java Web Start

1. Exporte a aplicação como um arquivo JAR (utilizando o Eclipse)
	Nome do arquivo: logicamente.jar
	* Deve ser selecionada a opção para incluir todos os recursos requiridos.
	
2. Edite o arquivo logicamenteJNLP.jnlp, colocando o nome ou ip do servidor Tomcat onde a aplicação será publicada.
	<jar href="http://192.168.0.100:8080/logica/logicamente.jar"/>
	<jar href="http://192.168.0.100:8080/logica/lib/java_cup.jar"/>
	<jar href="http://192.168.0.100:8080/logica/lib/JFlex.jar"/>

3. Dentro da pasta webapps do Tomcat, crie a pasta logica.
4. Copie os arquivos logicamente.jar e logicamenteJNLP.jnlp para esta pasta.
5. Dentro da pasta logica criada, crie a pasta lib.
6. Copie os arquivos java_cup.jar e JFlex.jar para esta pasta.
7. Rode o servidor Tomcat.

A aplicação esta pronta para ser acessada :)

Via browser:
Acesse:  http://<seu_servidor>:8080/logica/logicamenteJNLP.jnlp
Se for perguntado a forma de execução, seleciona para executar com o Java Web Start

Via ícone:
- Clique com o botão direito na área de trabalho (ou onde queira criar o icone).
- Selecione Novo/Atalho.
- Insira o link http://<seu_servidor>:8080/logica/logicamenteJNLP.jnlp (com o seu servidor) e clique em avançãr.
- Escolha um nome para o atalho. O atalho foi criado. Dois cliques para executar a aplicação.


