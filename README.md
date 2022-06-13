# CONSULTANDO DADOS DAS "N" LIXEIRAS MAIS CRÍTICAS
URL DE ACESSO: localhost:8080/recyclebin

CORPO DE MENSAGEM: NÚMERO DE LIXEIRAS QUE ESPERA CONSULTAR

# CONSULTANDO DADOS DE UMA LIXEIRA ESPECÍFICA
URL DE ACESSO: localhost:8080/recyclebin/id

CORPO DE MENSAGEM: ID DA LIXEIRA

# COMPILANDO IMAGENS DO DOCKER
1. DIRIJA-SE AO DIRETÓRIO "Docker", ESCOLHA QUAL IMAGEM DESEJA
COMPILAR, ENTRE EM SEU DIRETÓRIO CORRESPONDENTE, E EXECUTE O 
ARQUIVO "build_image.bat" ou "build_image.sh", PORÉM, ANTES DISSO
EXECUTE: echo> host.txt "tcp://127.0.0.1:1883", ALTERE O IP E PORTA
SE NECESSÁRIO.
2. CASO QUEIRA CRIAR MAIS DE UMA IMAGEM ALTERE O ARQUIVO 
"build_image.bat" ou "build_image.sh" E EXECUTE OUTRA VEZ 

# EXECUTANDO IMAGEM DO DOCKER
1. Execute: docker pull eclipse-mosquitto
2. Execute: docker run --name eclipse-mosquitto eclipse-mosquitto:latest
3. DIRIJA-SE AO DIRETÓRIO "Docker", ESCOLHA QUAL IMAGEM DESEJA
EXECUTAR, ENTRE EM SEU DIRETÓRIO CORRESPONDENTE, E EXECUTE O 
ARQUIVO "run_image.bat" ou "run_image.sh"
4. CASO POSSUA MAIS DE UMA IMAGEM, É POSSÍVEL ALTERAR O ARQUIVO
"run_image.bat" ou "run_image.sh"

# AVISO
EXECUTE AS IMAGENS NA SEGUINTE ORDEM
1. Administrator (único)
2. Sector1, Sector2, Sector3, Sector4 (único)
3. RecycleBin (único ou múltiplos)
4. GarbageTruck (único)