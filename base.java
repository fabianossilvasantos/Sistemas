import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

public class base {
    static int MAXIMO_TEMPO_EXECUCAO = 65535;
    static int n_processos = 3;
    public static void main(String[] args) {
        // Posicao 0: Id |
        // Posicao 1: Tempo de Execucao |
        // Posicao 2: Tempo de Chegada |
        // Posicao 3: Prioridade |
        // Posicao 4: Tempo Restante |
        // Posicao 5: Tempo Espera |

        System.out.println("Quantos processos você deseja criar?");
        Scanner teclado = new Scanner (System.in);

        n_processos = teclado.nextInt();
        int[][] processos = new int [n_processos][6]; //criando a matriz de n processos com 6 colunas

        popular_processos(processos); //populando linhas e colunas
        imprime_processos(processos); //imprimindo a matriz

        int[][] processosOriginal = copiaProcessos(processos); //copiando a matriz para o processo original

        //Escolher algoritmo
        int alg;

        while(true) {
            System.out.print("Escolha o argoritmo:\n1=FCFS\n2=SJF Preemptivo\n3=SJF Não Preemptivo\n4=Prioridade Preemptivo\n5=Prioridade Não Preemptivo\n6=Round_Robin\n7=Imprime lista de processos\n8=Popular processos novamente\n9=Sair: \n");
            alg =  teclado.nextInt();


            if (alg == 1) { //FCFS
                restauraProcessos(processos, processosOriginal);
                FCFS(processos);
            }
            else if (alg == 2) { //SJF PREEMPTIVO
                restauraProcessos(processos, processosOriginal);
                SJFPreemptivo(processos);
            }
            else if (alg == 3) { //SJF NÃO PREEMPTIVO
                restauraProcessos(processos, processosOriginal);
                SJF(processos);

            }
            else if (alg == 4) { //PRIORIDADE PREEMPTIVO
                restauraProcessos(processos, processosOriginal);
                prioridadeNaoPreemptivo(processos);
            }
            else if (alg == 5) { //PRIORIDADE NÃO PREEMPTIVO
                restauraProcessos(processos, processosOriginal);
                prioridadeNaoPreemptivo(processos);

            }
            else if (alg == 6) { //Round_Robin
                //Round_Robin(tempo_execucao, tempo_espera, tempo_restante);

            }
            else if (alg == 7) { //IMPRIME CONTEÚDO INICIAL DOS PROCESSOS
                imprime_processos(processos);
            }
            else if (alg == 8) { //REATRIBUI VALORES INICIAIS
                popular_processos(processos);
                imprime_processos(processos);
                processosOriginal = copiaProcessos(processos);
            }
            else if (alg == 9) {
                break;

            }
        }

    }


    public static void popular_processos(int[][] processos){
        Random random = new Random();
        Scanner teclado = new Scanner (System.in);
        int aleatorio;

        System.out.print("Será aleatório?:  ");
        aleatorio =  teclado.nextInt();

        for (int linhas = 0; linhas < n_processos; linhas++) {//criando matriz
            //Popular Processos Aleatorio
            if(aleatorio==1){
                processos[linhas][0]=linhas+1; //rever linhas 14 a 19 para entender pq é id e outros
                processos[linhas][1]= random.nextInt(10)+1; //tempo_execucao
                processos[linhas][2]= random.nextInt(10)+1; //tempo_chegadA
                processos[linhas][3]= random.nextInt(15)+1; //PRIORIDADE
                processos[linhas][4]=processos[linhas][1]; //tempo_restante
                processos[linhas][5]=0; //tempo_espera

            }
            //Popular Processos Manual
            else {
                processos[linhas][0]=linhas+1;
                System.out.print("Digite o tempo de execução do processo["+ processos[linhas][0]+ "]:  ");
                processos[linhas][1] = teclado.nextInt();
                System.out.print("Digite o tempo de chegada do processo["+ processos[linhas][0]+ "]:  ");
                processos[linhas][2] = teclado.nextInt();
                System.out.print("Digite a prioridade do processo["+ processos[linhas][0]+ "]:  ");
                processos[linhas][3]= teclado.nextInt();
                processos[linhas][4]=processos[linhas][1]; //tempo_restante
                processos[linhas][5]=0; //tempo_espera
            }
        }
    }



    public static void imprime_processos(int[][]processos){
        //Imprime lista de processos
        for (int linhas = 0; linhas < n_processos; linhas++) {//controle é o numero de linhas (var linhas)
            System.out.println("Processo[" +processos[linhas][0]+"]: tempo_execucao=" + processos[linhas][1] + " tempo_restante=" +processos[linhas][4] + " tempo_chegada=" + processos[linhas][2] + " prioridade =" + processos[linhas][3]);
        }
    }

    public static void FCFS(int[][]processos){
        int[][] processos_fifo;

        int processoEmExecucao = 0;

        for(int  contador=1; contador < 100; contador++ ){
            processos[processoEmExecucao][4]--;
            System.out.println("Tempo "+contador+": processo "+processos[processoEmExecucao][0]+" Restante: "+processos[processoEmExecucao][4]);
            esperaFcfs(processos, processos[processoEmExecucao][0]);
            delay();
            //se o processo atual executou todos os tempos dele, entao incrementa processo em execucao
            if (processos[processoEmExecucao][4]  == 0) {  //Pegando o processo pela posição processos[posição = linhas][atributo = colunas]
                //se todos os processos executaram, termina o programa
                if(processoEmExecucao==(n_processos-1)){
                    break;
                } //se o processo em execução for igual ao numero de processos -1
                else {
                    processoEmExecucao++;
                }
            }
        }
        imprimeEspera(processos);
    }

    public static void SJF(int [][]processos){
        int [][] processosOrganizados = calculaSjfNaoPreemptivo(processos);
        int processoEmExecucao =0;


        //implementar código do SJF preemptivo e não preemptivo
        //...
        //
        for(int  contador=1; contador < 100; contador++ ){ //contador é o tempo
            if(processosOrganizados[processoEmExecucao][2] <= contador){//verificando se algum processos chegou, se nenhum processo for menor que o contador significa que nenhum chegou ainda
                processosOrganizados[processoEmExecucao][4]--;
                System.out.println("Tempo "+contador+": processo "+processosOrganizados[processoEmExecucao][0]+" Restante: "+processosOrganizados[processoEmExecucao][4]);
                espera(processosOrganizados, processosOrganizados[processoEmExecucao][0], contador);
                delay();
                //se o processo atual executou todos os tempos dele, entao incrementa processo em execucao
                if (processosOrganizados[processoEmExecucao][4]  == 0) {  //Pegando o processo pela posição processos[posição = linhas][atributo = colunas]
                    //se todos os processos executaram, termina o programa
                    if(processoEmExecucao==(n_processos-1)){
                        break;
                    } //se o processo em execução for igual ao numero de processos -1
                    else {
                        processoEmExecucao++;
                    }
                }
            }else{
                System.out.println("Tempo "+contador+ " nenhum processo está pronto");
            }

        }
        imprimeEspera(processos);



    }
    public static void SJFPreemptivo(int[][]processos){
        int countFinal = 0;
        Arrays.sort(processos, Comparator.comparingInt(row -> row[4])); //organizar/ordenando com base de no tempo restante/execução (coluna 4) // organizando do mais curto para o mais longo
        Arrays.sort(processos, Comparator.comparingInt(row -> row[2]));
        int processoEmExecucao =calculaSjfPreemptivo(processos,0,1);

        for(int  contador=1; contador < 100; contador++ ){ //contador é o tempo
            processoEmExecucao = calculaSjfPreemptivo(processos,processoEmExecucao,contador);
            if(processos[processoEmExecucao][2] <= contador){//verificando se algum processos chegou, se nenhum processo for menor que o contador significa que nenhum chegou ainda
                processos[processoEmExecucao][4]--;
                System.out.println("Tempo "+contador+": processo "+processos[processoEmExecucao][0]+" Restante: "+processos[processoEmExecucao][4]);
                espera(processos, processos[processoEmExecucao][0], contador);
                delay();
                //se o processo atual executou todos os tempos dele, entao incrementa processo em execucao
                if (processos[processoEmExecucao][4]  == 0) {
                    countFinal += 1;
                    if (countFinal == n_processos) {
                        break;
                    } //se o processo em execução for igual ao numero de processos -1
                    else {
                        processoEmExecucao = calculaSjfPreemptivo(processos,processoEmExecucao,contador);
                    }
                }
            }else{
                System.out.println("Tempo "+contador+ " nenhum processo está pronto");
            }

        }
        imprimeEspera(processos);
    }

    public static void prioridadeNaoPreemptivo(int [][]processos){
        int [][] processosOrganizados = calculaPrioridadeNaoPreemptivo(processos);
        int processoEmExecucao =0;

        //implementar código do prioridade não preemptivo

        for(int  contador=1; contador < 500; contador++ ){ //contador é o tempo
            if(processosOrganizados[processoEmExecucao][2] <= contador){//verificando se algum processos chegou, se nenhum processo for menor que o contador significa que nenhum chegou ainda
                processosOrganizados[processoEmExecucao][4]--;
                System.out.println("Tempo "+contador+": processo "+processosOrganizados[processoEmExecucao][0]+" Restante: "+processosOrganizados[processoEmExecucao][4]);
                espera(processosOrganizados, processosOrganizados[processoEmExecucao][0], contador);
                delay();
                //se o processo atual executou todos os tempos dele, entao incrementa processo em execucao
                if (processosOrganizados[processoEmExecucao][4]  == 0) {  //Pegando o processo pela posição processos[posição = linhas][atributo = colunas]
                    //se todos os processos executaram, termina o programa
                    if(processoEmExecucao==(n_processos-1)){
                        break;
                    } //se o processo em execução for igual ao numero de processos -1
                    else {
                        processoEmExecucao++;
                    }
                }
            }else{
                System.out.println("Tempo "+contador+ " nenhum processo está pronto");
            }

        }
        imprimeEspera(processos);
    }

    public static void prioridadePreemptivo(int[][]processos){
        int countFinal = 0;
        Arrays.sort(processos, Comparator.comparingInt((int[] row) -> row[3]).reversed()); //organizar/ordenando com base de no tempo prioridade (coluna 3) // organizando do mais longo para o mais curto
        Arrays.sort(processos, Comparator.comparingInt(row -> row[2])); // organizando por ordem do tempo de chegada.
        int processoEmExecucao = calculaPrioridadePreemptivo(processos,0,1);

        for(int  contador=1; contador < 100; contador++ ){ //contador é o tempo
            processoEmExecucao = calculaPrioridadePreemptivo(processos,processoEmExecucao,contador);
            if(processos[processoEmExecucao][2] <= contador){//verificando se algum processos chegou, se nenhum processo for menor que o contador significa que nenhum chegou ainda
                processos[processoEmExecucao][4]--;
                System.out.println("Tempo "+contador+": processo "+processos[processoEmExecucao][0]+" Restante: "+processos[processoEmExecucao][4]); // Escrevendo o tempo dos processos e o tempo restante
                espera(processos, processos[processoEmExecucao][0], contador);
                delay();
                //se o processo atual executou todos os tempos dele, entao incrementa processo em execucao
                if (processos[processoEmExecucao][4]  == 0) {
                    countFinal += 1;
                    if (countFinal == n_processos) {
                        break;
                    } //se o processo em execução for igual ao numero de processos -1
                    else {
                        processoEmExecucao = calculaPrioridadePreemptivo(processos,processoEmExecucao,contador);
                    }
                }
            }else{
                System.out.println("Tempo "+contador+ " nenhum processo está pronto");
            }

        }
        imprimeEspera(processos);
    }

    public static void Round_Robin(int[] execucao, int[] espera, int[] restante){
        int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();


        //implementar código do Round-Robin
        //...
        //
    }

    public static void imprimeEspera(int[][] processos) {
        float media = 0;
        for (int i = 0; i < n_processos; i ++) {
            media += processos[i][5];
        }
        media = media / n_processos;
        System.out.println("");
        for (int i = 0; i < n_processos; i++) {
            System.out.println("Processo ["+processos[i][0]+"]: Tempo de Espera: "+processos[i][5]);
        }
        System.out.println("");
        System.out.println("Tempo médio de espera: "+media);
    }

    public static void delay() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void espera(int[][] processos, int id_exec, int unidadeTempo) {
        for (int i = 0; i < n_processos; i++) {
            if ((processos[i][0] != id_exec) && (processos[i][2] <= unidadeTempo) && (processos[i][4] != 0)) {
                processos[i][5] += 1;
            }
        }
    }

    public static void esperaFcfs(int[][] processos, int id_exec) {
        for (int i = 0; i < n_processos; i++) {
            if ((processos[i][0] != id_exec) && (processos[i][4] != 0)) {
                processos[i][5] += 1;
            }
        }
    }

    public static int[][] copiaProcessos(int[][] processos) {
        int rows = processos.length; //pegando a quantidade de linhas para a variavel rows
        int cols = processos[0].length; //pegando a quantidade de colunas e passando para a var cols

        int[][] copy = new int[rows][cols];
        for (int i = 0; i < rows; i++) { // pra cada i menor que todas linhas, percorre todas as linhas campo por campo e copia pra nova matriz de nome copy
            System.arraycopy(processos[i], 0, copy[i], 0, cols);
        }

        return copy;//retorna copy da matriz para o processo original
    }

    public static void restauraProcessos(int[][] processos, int[][] processosOriginal) { //funçao que restaura a matriz para os valores originais dela e manda para a ''processos''
        int rows = processos.length;
        int cols = processos[0].length;

        for (int i = 0; i < rows; i++) {
            System.arraycopy(processosOriginal[i], 0, processos[i], 0, cols);
        }
    }

    public static int [][] calculaSjfNaoPreemptivo(int [][]processos){ //reordenando a matriz em ordem de chegada e execução
        Arrays.sort(processos, Comparator.comparingInt(row -> row[4])); //organizar/ordenando com base de no tempo restante/execução (coluna 4) // organizando do mais curto para o mais longo

        Arrays.sort(processos, Comparator.comparingInt(row -> row[2])); //ordenando com base no que chegou primeiro até o que chegou por ultimo, usando tempo de chegada

        return processos;

    }
    public static int calculaSjfPreemptivo(int[][] processos,int processoemexecucao, int unidadeTempo) {
        int maiorValor = processos[0][4];

        for (int i = 1; i < processos.length; i++) {
            if (processos[i][4] > maiorValor) {
                maiorValor = processos[i][4];
            }
        }

        for (int i = 0; i < processos.length; i++) {
            if ((processos[i][2] <= unidadeTempo) && (processos[i][4] != 0) && (processos[i][4] < maiorValor + 1)) { // fazendo os testes logicos, se o processo chegou e nao terminou e for menor que o tempo em execucao dai faz a troca
                processoemexecucao = i;
                maiorValor = processos[i][4]; // fazendo comparacoes no tempo de execucao
            }
        }
        return processoemexecucao;
    }


    public static int [][] calculaPrioridadeNaoPreemptivo(int [][]processos){ //reordenando a matriz em ordem de chegada e execução
        Arrays.sort(processos, Comparator.comparingInt((int[] row) -> row[3]).reversed()); //organizar/ordenando com base de no tempo restante/execução (coluna 4) // organizando do mais curto para o mais longo
        Arrays.sort(processos, Comparator.comparingInt(row -> row[2])); //ordenando com base no que chegou primeiro até o que chegou por ultimo, usando tempo de chegada

        return processos;

    }
    public static int calculaPrioridadePreemptivo(int[][] processos, int processoEmExecucao, int unidadeTempo) {
        int maiorValor = processos[0][3];

        for (int i = 1; i < processos.length; i++) {
            if (processos[i][3] < maiorValor) {
                maiorValor = processos[i][3];
            }
        }

        for (int i = 0; i < processos.length; i++) {
            if ((processos[i][2] <= unidadeTempo) && (processos[i][4] != 0) && (processos[i][3] > maiorValor - 1)) { // fazendo os testes logicos, se o processo chegou e nao terminou e for menor que o tempo em execucao dai faz a troca
                processoEmExecucao = i;
                maiorValor = processos[i][3]; // fazendo comparacoes no tempo de execucao
            }
        }
        return processoEmExecucao;
    }
}
